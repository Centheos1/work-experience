package com.fitnessplayground.dao.domain;

import javax.persistence.*;

@Entity
public class Credentials {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;
    private String tpName;
    private String username;
    private String email;
    private String password;
    private String apiKey;
    private String apiSecret;
    private String appName;
    private String appId;
    private String token;
    private String refreshToken;
    private String tokenExpiresIn;

    public Credentials() {
    }

    public String getTpName() {
        return tpName;
    }

    public void setTpName(String tpName) {
        this.tpName = tpName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getApiSecret() {
        return apiSecret;
    }

    public void setApiSecret(String apiSecret) {
        this.apiSecret = apiSecret;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getTokenExpiresIn() {
        return tokenExpiresIn;
    }

    public void setTokenExpiresIn(String tokenExpiresIn) {
        this.tokenExpiresIn = tokenExpiresIn;
    }

    @Override
    public String toString() {
        return "Credential{" +
                "id=" + id +
                ", tpName='" + tpName + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", apiKey='" + apiKey + '\'' +
                ", apiSecret='" + apiSecret + '\'' +
                ", appName='" + appName + '\'' +
                ", appId='" + appId + '\'' +
                ", token='" + token + '\'' +
                ", tokenExpiresIn='" + tokenExpiresIn + '\'' +
                '}';
    }
}
