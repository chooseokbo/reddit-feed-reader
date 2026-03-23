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
    public List<RedditPost> posts(
            @RequestParam(defaultValue = "new") String sort,
            @RequestParam(defaultValue = "0") int minScore) {
        return feedService.getPosts(sort, minScore);
    }

    @GetMapping("/posts/{subreddit}")
    public List<RedditPost> postsBySub(
            @PathVariable String subreddit,
            @RequestParam(defaultValue = "new") String sort,
            @RequestParam(defaultValue = "0") int minScore) {
        return feedService.getPostsBySubreddit(subreddit, sort, minScore);
    }
}
