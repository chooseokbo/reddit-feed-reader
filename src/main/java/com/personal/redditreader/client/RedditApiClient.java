package com.personal.redditreader.client;

import com.personal.redditreader.config.RedditConfig;
import com.personal.redditreader.model.RedditPost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
public class RedditApiClient {

    private static final Logger log = LoggerFactory.getLogger(RedditApiClient.class);

    private final RedditConfig config;
    private final WebClient authClient;
    private final WebClient apiClient;
    private String accessToken;

    public RedditApiClient(RedditConfig config) {
        this.config = config;
        this.authClient = WebClient.builder().build();
        this.apiClient = WebClient.builder()
                .baseUrl("https://oauth.reddit.com")
                .defaultHeader("User-Agent", config.getUserAgent())
                .build();
    }

    public void authenticate() {
        try {
            Map<?, ?> res = authClient.post()
                    .uri("https://www.reddit.com/api/v1/access_token")
                    .headers(h -> h.setBasicAuth(config.getClientId(), config.getClientSecret()))
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .header("User-Agent", config.getUserAgent())
                    .body(BodyInserters.fromFormData("grant_type", "password")
                            .with("username", config.getUsername())
                            .with("password", config.getPassword()))
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

            if (res != null && res.containsKey("access_token")) {
                this.accessToken = (String) res.get("access_token");
                log.info("Reddit auth ok");
            }
        } catch (Exception e) {
            log.error("Reddit auth failed", e);
        }
    }

    public List<RedditPost> fetchPosts(String subreddit, int limit) {
        if (accessToken == null) {
            authenticate();
        }
        if (accessToken == null) {
            return Collections.emptyList();
        }

        try {
            Map<?, ?> res = apiClient.get()
                    .uri("/r/{sub}/hot?limit={limit}", subreddit, limit)
                    .header("Authorization", "Bearer " + accessToken)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();
            return parsePosts(res, subreddit);
        } catch (Exception e) {
            log.error("Failed to fetch r/{}", subreddit, e);
            return Collections.emptyList();
        }
    }

    @SuppressWarnings("unchecked")
    private List<RedditPost> parsePosts(Map<?, ?> response, String subreddit) {
        if (response == null || !response.containsKey("data")) {
            return Collections.emptyList();
        }

        Map<?, ?> data = (Map<?, ?>) response.get("data");
        List<?> children = (List<?>) data.get("children");
        if (children == null) {
            return Collections.emptyList();
        }

        List<RedditPost> posts = new ArrayList<>();
        for (Object child : children) {
            Map<?, ?> d = (Map<?, ?>) ((Map<?, ?>) child).get("data");

            RedditPost post = new RedditPost();
            post.setId((String) d.get("name"));
            post.setSubreddit(subreddit);
            post.setTitle((String) d.get("title"));
            post.setAuthor((String) d.get("author"));
            post.setSelfText((String) d.get("selftext"));
            post.setUrl((String) d.get("url"));
            post.setPermalink("https://www.reddit.com" + d.get("permalink"));

            if (d.get("score") instanceof Number n) post.setScore(n.intValue());
            if (d.get("num_comments") instanceof Number n) post.setNumComments(n.intValue());
            if (d.get("created_utc") instanceof Number n) post.setCreatedUtc(Instant.ofEpochSecond(n.longValue()));

            posts.add(post);
        }
        return posts;
    }
}
