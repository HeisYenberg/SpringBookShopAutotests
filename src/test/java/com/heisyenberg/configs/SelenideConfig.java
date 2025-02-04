package com.heisyenberg.configs;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({"system:env", "classpath:selenide.properties"})
public interface SelenideConfig extends Config {
  @Key("BASE_URL")
  @DefaultValue("${selenide.baseUrl}")
  String baseUrl();

  @Key("selenide.browser")
  String browser();

  @Key("selenide.browserVersion")
  String browserVersion();

  @Key("selenide.headless")
  boolean headless();

  @Key("selenide.timeout")
  int timeout();

  @Key("selenide.pageLoadTimeout")
  int pageLoadTimeout();

  @Key("selenide.downloadsFolder")
  String downloadsFolder();

  @Key("selenide.reportsFolder")
  String reportsFolder();

  @Key("SELENOID_URL")
  @DefaultValue("${selenide.remote}")
  String selenoidUrl();
}
