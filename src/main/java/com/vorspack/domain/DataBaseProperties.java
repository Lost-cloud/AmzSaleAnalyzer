package com.vorspack.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("dbProps")
public class DataBaseProperties {

    @Value("#{environment['database.driver']}")
    private String driver;

    @Value("#{environment['database.url']}")
    private String url;

    @Value("#{environment['database.username']}")
    private String username;

    @Value("#{environment['database.password']}")
    private String password;

    @Value("#{environment['basicDataSource.maxIdle']}")
    private String basicDataSourceMaxIdle;

    @Value("#{environment['basicDataSource.maxWaitMillis']}")
    private String bdcMaxWaitMills;

    @Value("#{environment['basicDataSource.maxTotal']}")
    private String basicDataSourceMaxTotal;

    public String getBasicDataSourceMaxTotal() {
        return basicDataSourceMaxTotal;
    }

    public void setBasicDataSourceMaxTotal(String basicDataSourceMaxTotal) {
        this.basicDataSourceMaxTotal = basicDataSourceMaxTotal;
    }

    public void setBasicDataSourceMaxIdle(String basicDataSourceMaxIdle) {
        this.basicDataSourceMaxIdle = basicDataSourceMaxIdle;
    }

    public void setBdcMaxWaitMills(String bdcMaxWaitMills) {
        this.bdcMaxWaitMills = bdcMaxWaitMills;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDriver() {
        return driver;
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getBdcMaxWaitMills() {
        return bdcMaxWaitMills;
    }

    public String getBasicDataSourceMaxIdle() {
        return basicDataSourceMaxIdle;
    }

}
