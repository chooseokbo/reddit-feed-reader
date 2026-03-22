# Reddit Feed Reader

Personal feed reader that pulls posts from multiple subreddits into one place.
Saves me from jumping between subreddits manually.

## How it works

- Spring Boot app running locally
- Authenticates via Reddit OAuth2 (script app)
- Read-only, just fetches and displays posts

## How to run

Requires Java 17+ and Maven.

```bash
# copy config and fill in your Reddit app credentials
cp src/main/resources/application-example.yml src/main/resources/application.yml

# build & run
mvn clean package
java -jar target/reddit-feed-reader-1.0.0.jar
```

Register a script app at https://www.reddit.com/prefs/apps

## Endpoints

```
POST /api/feed/refresh           - fetch latest posts
GET  /api/feed/posts             - get all posts
GET  /api/feed/posts/{subreddit} - get posts by subreddit
```

## Subreddits

r/wallstreetbets, r/stocks, r/investing, r/cryptocurrency, r/bitcoin, r/ethtrader

