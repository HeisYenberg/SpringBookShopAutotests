package com.heisyenberg.tests;

import static com.codeborne.selenide.Selenide.clearBrowserCookies;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.heisyenberg.configs.SelenideConfig;
import com.heisyenberg.listeners.LoggerListener;
import com.heisyenberg.utils.LogLevel;
import com.heisyenberg.utils.LoggingUtil;
import io.qameta.allure.Epic;
import io.qameta.allure.selenide.AllureSelenide;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;

@Epic("Spring Book Shop")
@Listeners(LoggerListener.class)
public abstract class BaseTest {
  protected static void retry(final int retryCount, final Runnable runnable) {
    Throwable throwable = new Throwable();
    System.out.println("Retrying " + retryCount + " times");
    for (int i = 0; i < retryCount; i++) {
      try {
        runnable.run();
        return;
      } catch (Exception e) {
        LoggingUtil.log(LogLevel.WARN, "Exception while retrying", e);
        throwable = e;
        try {
          Thread.sleep(5000);
        } catch (InterruptedException ex) {
          throwable = ex;
        }
      }
    }
    throw new RuntimeException(throwable);
  }

  @BeforeClass
  public void configure() {
    SelenideConfig selenideConfig = ConfigFactory.create(SelenideConfig.class);
    ChromeOptions capabilities = new ChromeOptions();
    capabilities.addArguments("--guest");

    Configuration.browserCapabilities = capabilities;
    Configuration.baseUrl = selenideConfig.baseUrl();
    Configuration.browser = selenideConfig.browser();
    Configuration.browserVersion = selenideConfig.browserVersion();
    Configuration.headless = selenideConfig.headless();
    Configuration.timeout = selenideConfig.timeout();
    Configuration.pageLoadTimeout = selenideConfig.pageLoadTimeout();
    Configuration.downloadsFolder = selenideConfig.downloadsFolder();
    Configuration.reportsFolder = selenideConfig.reportsFolder();
    Configuration.remote = selenideConfig.selenoidUrl();

    SelenideLogger.addListener(
        "AllureSelenide", new AllureSelenide().screenshots(true).savePageSource(false));
  }

  @AfterMethod
  public void cleanUp() {
    clearBrowserCookies();
  }
}
