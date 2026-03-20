package com.personal.redditreader.model;

import java.time.Instant;

public class RedditPost {

    private String id;
    private String subreddit;
    private String title;
    private String author;
    private String selfText;
    private String url;
    private String permalink;
    private int score;
    private int numComments;
    private Instant createdUtc;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getSubreddit() { return subreddit; }
    public void setSubreddit(String subreddit) { this.subreddit = subreddit; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getSelfText() { return selfText; }
    public void setSelfText(String selfText) { this.selfText = selfText; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public String getPermalink() { return permalink; }
    public void setPermalink(String permalink) { this.permalink = permalink; }

    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }

    public int getNumComments() { return numComments; }
    public void setNumComments(int numComments) { this.numComments = numComments; }

    public Instant getCreatedUtc() { return createdUtc; }
    public void setCreatedUtc(Instant createdUtc) { this.createdUtc = createdUtc; }
}
