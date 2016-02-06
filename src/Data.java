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

    /**
     * default constructor which also can initialize twitter keys
     */
    public Data() {
        //change this variable to stream a specific user's timeline
        someone = "vt_hacks";
        time = 0;

        ConfigurationBuilder cb = new ConfigurationBuilder();

        /*configure this section via
        https://apps.twitter.com/app/new

        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(consumerKey)
                .setOAuthConsumerSecret
                        (consumerSecret);
                .setOAuthAccessToken
                        (accessToken)
                .setOAuthAccessTokenSecret
                        (accessTokenSecret);*/

        twitter = new TwitterFactory(cb.build()).getInstance();

        //configure these parameters to change twitter viewing
        tickFeed(someone, true);
    }

    /**
     * increments timer
     */
    public void tick() {
        time++;
    }

    /**
     * locates twitter information and assigns variables
     *
     * @param someone  Is the person to be used for the timeline
     * @param yourFeed Is whether or not to view your news feed
     */
    private void tickFeed(String someone, boolean yourFeed) {
        List<Status> feed;
        try {
            if (yourFeed)
                feed = twitter.getHomeTimeline();
            else
                feed = twitter.getUserTimeline(someone);

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
}
