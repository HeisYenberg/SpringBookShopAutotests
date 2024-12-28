package com.heisyenberg.configs;

import org.aeonbits.owner.Config;

@Config.Sources("classpath:datasource.properties")
public interface DataSourceConfig extends Config {
    @Key("datasource.url")
    String url();

    @Key("datasource.driver_class_name")
    String driverClassName();

    @Key("datasource.username")
    String username();

    @Key("datasource.password")
    String password();
}
