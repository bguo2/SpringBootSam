package com.example.demo.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class EnvProperties {

    @Bean(name = "dbServer1")
    @ConfigurationProperties("db1.datasource")
    @Primary
    public DataSource dataSource1(){

        return DataSourceBuilder.create().build();
    }

    @Bean(name = "dbServer2")
    @ConfigurationProperties("db2.datasource")
    public DataSource dataSource2(){
        return DataSourceBuilder.create().build();
    }

    @Bean(name= "dbServer3")
    @ConfigurationProperties("db3.datasource")
    public DataSource dataSource3() {
        return DataSourceBuilder.create().build();
    }
}
