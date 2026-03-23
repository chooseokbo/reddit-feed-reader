# Reddit Feed Reader

Personal feed reader that pulls posts from multiple subreddits into one place.
Saves me from jumping between subreddits manually.

## How it works

- Spring Boot app running locally
- Authenticates via Reddit OAuth2 (script app)
- Read-only, just fetches and displays posts

## How to run

Requires Java 17+ and Maven.

Register a script app at https://www.reddit.com/prefs/apps and set env vars:

```bash
export REDDIT_CLIENT_ID=your_client_id
export REDDIT_CLIENT_SECRET=your_client_secret
export REDDIT_USERNAME=your_username
export REDDIT_PASSWORD=your_password

mvn clean package
java -jar target/reddit-feed-reader-1.0.0.jar
```

## Endpoints

```
POST /api/feed/refresh                          - fetch latest posts
GET  /api/feed/posts?sort=score&minScore=100    - get all posts, sorted/filtered
GET  /api/feed/posts/{subreddit}                - get posts by subreddit
```

`sort` options: `new` (default), `score`, `comments`

## Subreddits

r/wallstreetbets, r/stocks, r/investing, r/cryptocurrency, r/bitcoin, r/ethtrader

