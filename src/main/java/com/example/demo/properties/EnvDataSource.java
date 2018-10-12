package com.example.demo.properties;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.jdbc.core.JdbcTemplate;
import javax.sql.DataSource;

@Component
public class EnvDataSource {

    @Bean(name="dbServer1JdbcTemplate")
    public JdbcTemplate getJdbcTemplate(@Qualifier("dbServer1") DataSource db1){
        JdbcTemplate template = new JdbcTemplate(db1);
        return template;
    }

    @Bean(name="cspdbJdbcTemplate")
    public JdbcTemplate getJdbcTemplateCsp(@Qualifier("dbServer3") DataSource db1){
        JdbcTemplate template = new JdbcTemplate(db1);
        return template;
    }

}
