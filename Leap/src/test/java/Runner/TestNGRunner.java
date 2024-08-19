package Runner;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import com.example.util.DynamicUrlHandler;
import com.intuit.karate.Results;
import com.intuit.karate.Runner;
import io.qameta.allure.Allure;
import org.testng.Assert;

public class TestNGRunner {

    @BeforeSuite
    public void setup() {
        try {
            // Generate the dynamic URL using the method from DynamicUrlHandler
            String dynamicUrl = DynamicUrlHandler.dynamicUrl;
            
            // Debugging output
            System.out.println("Dynamic URL set to: " + dynamicUrl);

            // Set the dynamic URL as a system property or environment variable
            System.setProperty("karate.env", dynamicUrl);

            // Print the dynamic URL for debugging
            System.out.println("Karate Env URL after setup: " + dynamicUrl);
        } catch (Exception e) {
            System.err.println("Error during setup: " + e.getMessage());
            throw new RuntimeException("Failed to setup the dynamic URL", e);
        }
    }

    @Test
    public void testAll() {
        // Run the Karate test suite
        Results results = Runner.path("classpath:Features/Employee.feature").parallel(1);

        // Add an Allure step to log results
        Allure.addAttachment("Test Suite Results", "application/json", results.getReportDir());

        // Fail the test if there are any failures in the suite
        Assert.assertTrue(results.getFailCount() == 0, "There are test failures");
    }
}
