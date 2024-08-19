package com.example.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DynamicUrlHandler {

    private static final Logger logger = LogManager.getLogger(DynamicUrlHandler.class);

    private static final String BASE_URL_KEY = "base.url";
    private static final String EMPLOYEES_ENDPOINT = "/employees/";

    public static String dynamicUrl;

    static {
        try {
            // Fetch the latest dynamic ID
            String dynamicId = DynamicIdFetcher.fetchDynamicId();
            if (dynamicId == null) {
                throw new IOException("Failed to retrieve dynamic ID.");
            }
            // Generate the full dynamic URL
            dynamicUrl = generateDynamicUrl(dynamicId);
            logger.info("Dynamic URL generated: " + dynamicUrl);
        } catch (Exception e) {
            logger.error("Failed to generate Dynamic URL: " + e.getMessage(), e);
        }
    }

    private static String generateDynamicUrl(String dynamicId) throws IOException {
        String baseUrl = "https://crudcrud.com/api/" + dynamicId;
        return baseUrl + EMPLOYEES_ENDPOINT;
    }

    // Method to save the dynamic URL to a file (optional, if needed)
    public static void saveDynamicUrlToFile(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        Files.createDirectories(path.getParent());
        Files.write(path, dynamicUrl.getBytes(StandardCharsets.UTF_8));
        logger.info("URL saved to file: " + filePath);
    }
}
