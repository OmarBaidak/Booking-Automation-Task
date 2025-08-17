package utilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;



public class BrowserInitializer {
    private WebDriver driver;

    public WebDriver initializeBrowser(@NotNull String browserType) throws IOException {
        Properties properties = new Properties();
        FileInputStream fis = new FileInputStream("src/main/resources/config.properties");
        properties.load(fis);
        fis.close();


        String url = properties.getProperty("url");

        switch (browserType.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions options = new ChromeOptions();
                options.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);

                driver = new ChromeDriver(options);
                break;
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;
            default:
                throw
                        new IllegalArgumentException("Unsupported browser type: " + browserType);
        }

        driver.manage().window().maximize();
        driver.get(url);
        return driver;
    }

    public void quitBrowser() {
        driver.quit();
    }
}
