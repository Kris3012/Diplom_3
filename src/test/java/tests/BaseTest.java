package tests;

import com.codeborne.selenide.Configuration;
import org.junit.Before;
import org.openqa.selenium.chrome.ChromeOptions;

public class BaseTest {

    @Before
    public void setUp() {
        String browser = System.getProperty("browser", "chrome").toLowerCase();

        Configuration.browser = browser;

        Configuration.browserSize = "1920x1080";

        ChromeOptions options = new ChromeOptions();

        if ("yandex".equals(browser)) {
            Configuration.browserBinary = "/Applications/Yandex.app/Contents/MacOS/Yandex";
        } else {

            Configuration.browserBinary = null;
        }

        Configuration.browserCapabilities = options;
    }
}
