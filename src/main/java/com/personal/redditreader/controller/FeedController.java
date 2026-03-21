package com.personal.redditreader.controller;

import com.personal.redditreader.model.RedditPost;
import com.personal.redditreader.service.FeedService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feed")
public class FeedController {

    private final FeedService feedService;

    public FeedController(FeedService feedService) {
        this.feedService = feedService;
    }

    @PostMapping("/refresh")
    public List<RedditPost> refresh() {
        return feedService.refreshFeed();
    }

    @GetMapping("/posts")
    public List<RedditPost> posts() {
        return feedService.getPosts();
    }

    @GetMapping("/posts/{subreddit}")
    public List<RedditPost> postsBySub(@PathVariable String subreddit) {
        return feedService.getPostsBySubreddit(subreddit);
    }
}
