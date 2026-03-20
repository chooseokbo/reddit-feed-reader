package com.personal.redditreader.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "reddit")
public class RedditConfig {

    private String clientId;
    private String clientSecret;
    private String username;
    private String password;
    private String userAgent;
    private List<String> subreddits;

    public String getClientId() { return clientId; }
    public void setClientId(String clientId) { this.clientId = clientId; }

    public String getClientSecret() { return clientSecret; }
    public void setClientSecret(String clientSecret) { this.clientSecret = clientSecret; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getUserAgent() { return userAgent; }
    public void setUserAgent(String userAgent) { this.userAgent = userAgent; }

    public List<String> getSubreddits() { return subreddits; }
    public void setSubreddits(List<String> subreddits) { this.subreddits = subreddits; }
}
