package com.personal.redditreader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class RedditFeedReaderApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedditFeedReaderApplication.class, args);
    }
}
