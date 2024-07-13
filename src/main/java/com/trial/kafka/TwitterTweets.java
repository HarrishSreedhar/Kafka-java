//package com.trial.kafka;
//
//import com.twitter.clientlib.ApiException;
//import com.twitter.clientlib.TwitterCredentialsOAuth2;
//import com.twitter.clientlib.api.TwitterApi;
//import com.twitter.clientlib.model.Get2TweetsIdResponse;
//import com.twitter.clientlib.model.ResourceUnauthorizedProblem;
//import twitter4j.OAuthAuthorization;
//
//import java.util.HashSet;
//import java.util.Set;
//
//public class TwitterTweets {
//
//    private String consumerKey = "D1pNq9GUy4EkNDT6D1vU5sGfw";
//    private String consumerSecret = "EDRXKiSLR8Qvqpsfl5ZQV0U9dAZ9n4hoeDrVNxl2PuRZBBwSsG";
//    private String token = "1812074180717756416-tkMyUr1pqZMq0xO3AhOAHFXHiBzz4g";
//    private String secret = "Ra5TdJNeleyGn0k33IgzQ4G1i9vMLY0PvgQQC6aUF6Hm5";
//
//    public void test(){
//
//        ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
//        configurationBuilder.setOAuthConsumerKey(ConsumerKey)
//                .setOAuthConsumerSecret(ConsumerSecret)
//                .setOAuthAccessToken(AccessToken)
//                .setOAuthAccessTokenSecret(AccessTokenSecret)
//                .setJSONStoreEnabled(true);
//        TwitterStream twitterStream = (new TwitterStreamFactory(configurationBuilder.build())).getInstance();
//
//        OAuthAuthorization t = OAuthAuthorization.newBuilder()
//                .oAuthConsumer(consumerKey, consumerSecret).build();
//
//    TwitterApi apiInstance = new TwitterApi(new TwitterCredentialsOAuth2(
//            System.getenv("TWITTER_OAUTH2_CLIENT_ID"),
//            System.getenv("TWITTER_OAUTH2_CLIENT_SECRET"),
//            System.getenv("TWITTER_OAUTH2_ACCESS_TOKEN"),
//            System.getenv("TWITTER_OAUTH2_REFRESH_TOKEN")));
//
//    Set<String> tweetFields = new HashSet<>();
//    tweetFields.add("author_id");
//    tweetFields.add("id");
//    tweetFields.add("created_at");
//
//    try {
//        // findTweetById
//        Get2TweetsIdResponse result = apiInstance.tweets().findTweetById("20")
//                .tweetFields(tweetFields)
//                .execute();
//        if(result.getErrors() != null && result.getErrors().size() > 0) {
//            System.out.println("Error:");
//
//            result.getErrors().forEach(e -> {
//                System.out.println(e.toString());
//                if (e instanceof ResourceUnauthorizedProblem) {
//                    System.out.println(((ResourceUnauthorizedProblem) e).getTitle() + " " + ((ResourceUnauthorizedProblem) e).getDetail());
//                }
//            });
//        } else {
//            System.out.println("findTweetById - Tweet Text: " + result.toString());
//        }
//    } catch (ApiException e) {
//        System.err.println("Status code: " + e.getCode());
//        System.err.println("Reason: " + e.getResponseBody());
//        System.err.println("Response headers: " + e.getResponseHeaders());
//        e.printStackTrace();
//    }
//}
