package com.example.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DynamicIdFetcher {

    private static final String CRUDCRUD_HOME_URL = "https://crudcrud.com";
    private static final Logger logger = Logger.getLogger(DynamicIdFetcher.class.getName());

    public static String fetchDynamicId() throws IOException {
        HttpURLConnection connection = null;
        String dynamicId = null;

        try {
            URL url = new URL(CRUDCRUD_HOME_URL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String inputLine;
                    StringBuilder response = new StringBuilder();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }

                    // Parse the HTML to find the dynamic ID
                    dynamicId = parseDynamicIdFromHtml(response.toString());
                }
            } else {
                logger.severe("Failed to connect to CrudCrud: " + responseCode);
                throw new IOException("Failed to retrieve dynamic ID from server. Response code: " + responseCode);
            }

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error fetching dynamic ID: ", e);
            throw new IOException("Failed to retrieve dynamic ID", e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        if (dynamicId == null) {
            throw new IOException("Dynamic ID could not be extracted from the HTML.");
        }

        return dynamicId;
    }

    private static String parseDynamicIdFromHtml(String html) {
        // Regex to find the dynamic ID in the HTML content
        String pattern = "https://crudcrud.com/api/([a-f0-9]{32})";
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(html);

        if (matcher.find()) {
            return matcher.group(1);
        }

        return null;
    }
}
