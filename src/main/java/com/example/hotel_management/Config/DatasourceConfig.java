package com.example.hotel_management.Config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DatasourceConfig {

    /**
     * Connect SQL server database with this project
     * @return
     * Build dataSourceBuilder
     */
    @Bean
    public DataSource dataSource(){
        /*
         Port: 1433
         Database: NT
         SQL server username: sa
         SQL server password: 123
         */
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        dataSourceBuilder.url("jdbc:sqlserver://ho12.database.windows.net:1433;databaseName=NT;trustServerCertificate=true;");
        dataSourceBuilder.username("ho12");
        dataSourceBuilder.password("0941609091Th@");
        return dataSourceBuilder.build();
    }
}
