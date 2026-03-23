package com.personal.redditreader.service;

import com.personal.redditreader.client.RedditApiClient;
import com.personal.redditreader.config.RedditConfig;
import com.personal.redditreader.model.RedditPost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class FeedService {

    private static final Logger log = LoggerFactory.getLogger(FeedService.class);

    private final RedditApiClient apiClient;
    private final RedditConfig config;
    private final List<RedditPost> posts = new CopyOnWriteArrayList<>();

    public FeedService(RedditApiClient apiClient, RedditConfig config) {
        this.apiClient = apiClient;
        this.config = config;
    }

    public List<RedditPost> refreshFeed() {
        List<RedditPost> fetched = new ArrayList<>();
        for (String sub : config.getSubreddits()) {
            log.info("Fetching r/{}", sub);
            fetched.addAll(apiClient.fetchPosts(sub, 10));
        }

        posts.clear();
        posts.addAll(fetched);
        log.info("Loaded {} posts", posts.size());
        return getPosts("new", 0);
    }

    public List<RedditPost> getPosts(String sort, int minScore) {
        var stream = posts.stream();

        if (minScore > 0) {
            stream = stream.filter(p -> p.getScore() >= minScore);
        }

        Comparator<RedditPost> comparator = switch (sort) {
            case "score" -> Comparator.comparingInt(RedditPost::getScore).reversed();
            case "comments" -> Comparator.comparingInt(RedditPost::getNumComments).reversed();
            default -> Comparator.comparing(RedditPost::getCreatedUtc, Comparator.nullsLast(Comparator.reverseOrder()));
        };

        return stream.sorted(comparator).toList();
    }

    public List<RedditPost> getPostsBySubreddit(String subreddit, String sort, int minScore) {
        var stream = posts.stream()
                .filter(p -> subreddit.equalsIgnoreCase(p.getSubreddit()));

        if (minScore > 0) {
            stream = stream.filter(p -> p.getScore() >= minScore);
        }

        Comparator<RedditPost> comparator = switch (sort) {
            case "score" -> Comparator.comparingInt(RedditPost::getScore).reversed();
            case "comments" -> Comparator.comparingInt(RedditPost::getNumComments).reversed();
            default -> Comparator.comparing(RedditPost::getCreatedUtc, Comparator.nullsLast(Comparator.reverseOrder()));
        };

        return stream.sorted(comparator).toList();
    }
}
