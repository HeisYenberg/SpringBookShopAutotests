package com.heisyenberg.configs;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({"system:env", "classpath:datasource.properties"})
public interface DataSourceConfig extends Config {
  @Key("DATASOURCE_URL")
  @DefaultValue("${datasource.url}")
  String url();

  @Key("datasource.driver_class_name")
  String driverClassName();

  @Key("datasource.username")
  String username();

  @Key("datasource.password")
  String password();
}
