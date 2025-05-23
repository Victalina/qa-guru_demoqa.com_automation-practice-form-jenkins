package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import helpers.Attach;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Map;

import static com.codeborne.selenide.Selenide.closeWebDriver;

public class TestBase {

  @BeforeAll
  static void setupConfig(){
    Configuration.baseUrl = "https://demoqa.com";
    Configuration.browserSize = "1920x1080";
    Configuration.pageLoadStrategy = "eager";
    Configuration.remote = "https://user1:1234@selenoid.autotests.cloud/wd/hub";
    SelenideLogger.addListener("allure", new AllureSelenide());
  }

  @BeforeEach
  void setupVideo(){
    DesiredCapabilities capabilities = new DesiredCapabilities();
    capabilities.setCapability("selenoid:options", Map.<String, Object>of(
            "enableVNC", true,
            "enableVideo", true
    ));
    Configuration.browserCapabilities = capabilities;
  }

  @AfterEach
  void addAttachments(){
    Attach.screenshotAs("Last screenshot");
    Attach.pageSource();
    Attach.browserConsoleLogs();
    Attach.addVideo();
  }

  void teardown(){
    closeWebDriver();
  }
}
