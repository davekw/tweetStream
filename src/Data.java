import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.util.List;

public class Data {

    public int time;
    private Twitter twitter;
    public String[] statuses;
    public String[] names;
    public String[] profileUrls;
    public String[] backgroundUrls;
    public String someone;
    public String[] names2;
    public String[] bannerUrls;
    public String[] locations;
    public String[] times;
    public String[] descriptions;
    public String[] cols;
    public int[] followers;
    public int[] following;


    public Data() {
        someone = "frailmary";
        time = 0;
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("2UtoUBRAmiD6BOO8l6eE5jWzq")
                .setOAuthConsumerSecret
                        ("ZiLwrNIBJLFSZOLVAwe26Vjn3tGuETGRt6F4IjRhmrZJ70erCA")
                .setOAuthAccessToken
                        ("1277886414-jkEdqFpYPOBkqsDlQJF2uxQ2Mnr2BCj5rZrSojE")
                .setOAuthAccessTokenSecret
                        ("yq9BZMfpEbc27pO7Q2qRBq3ZB2ywR7WHCSssjCibKAhQI");
        twitter = new TwitterFactory(cb.build()).getInstance();

        makeSource(1);
    }

    public void tick() {
        time++;
    }

    public void makeSource(int source) {
        if (source == 1) {
            tickSomeone(someone);
        } else {
            tickStatus();
        }
    }

    private void tickSomeone(String someone) {
        try {
            List<Status> feed = twitter.getUserTimeline(someone);
            statuses = new String[feed.size()];
            names = new String[feed.size()];
            names2 = new String[feed.size()];
            locations = new String[feed.size()];
            times = new String[feed.size()];
            bannerUrls = new String[feed.size()];
            profileUrls = new String[feed.size()];
            backgroundUrls = new String[feed.size()];
            descriptions = new String[feed.size()];
            cols = new String[feed.size()];
            followers = new int[feed.size()];
            following = new int[feed.size()];
            for (int i = 0; i < feed.size(); i++) {
                following[i] = feed.get(i).getUser().getFriendsCount();
                cols[i] = feed.get(i).getUser().getProfileLinkColor();
                followers[i] = feed.get(i).getUser().getFollowersCount();
                descriptions[i] = feed.get(i).getUser().getDescription();
                locations[i] = feed.get(i).getUser().getLocation();
                statuses[i] = feed.get(i).getText();
                times[i] = feed.get(i).getCreatedAt().toString();
                statuses[i] = feed.get(i).getText();
                names[i] = feed.get(i).getUser().getName();
                names2[i] = feed.get(i).getUser().getScreenName();
                bannerUrls[i] =
                        feed.get(i).getUser().getProfileBannerMobileURL();
                profileUrls[i] = feed.get(i).getUser().
                        getOriginalProfileImageURLHttps();
                backgroundUrls[i] = feed.get(i).getUser().
                        getProfileBackgroundImageUrlHttps();
            }
        } catch (TwitterException e) {
            e.printStackTrace();
        }

    }

    private void tickStatus() {
        try {
            List<Status> feed = twitter.getHomeTimeline();
            statuses = new String[feed.size()];
            names = new String[feed.size()];
            names2 = new String[feed.size()];
            locations = new String[feed.size()];
            times = new String[feed.size()];
            bannerUrls = new String[feed.size()];
            profileUrls = new String[feed.size()];
            descriptions = new String[feed.size()];
            backgroundUrls = new String[feed.size()];
            cols = new String[feed.size()];
            followers = new int[feed.size()];
            following = new int[feed.size()];
            for (int i = 0; i < feed.size(); i++) {
                followers[i] = feed.get(i).getUser().getFollowersCount();
                following[i] = feed.get(i).getUser().getFriendsCount();
                cols[i] = feed.get(i).getUser().getProfileLinkColor();
                descriptions[i] = feed.get(i).getUser().getDescription();
                locations[i] = feed.get(i).getUser().getLocation();
                statuses[i] = feed.get(i).getText();
                times[i] = feed.get(i).getCreatedAt().toString();
                names[i] = feed.get(i).getUser().getName();
                names2[i] = feed.get(i).getUser().getScreenName();
                bannerUrls[i] =
                        feed.get(i).getUser().getProfileBannerMobileURL();
                profileUrls[i] = feed.get(i).getUser().
                        getOriginalProfileImageURLHttps();
                backgroundUrls[i] = feed.get(i).getUser().
                        getProfileBackgroundImageUrlHttps();
            }
        } catch (TwitterException e) {
            e.printStackTrace();
        }

    }
}
