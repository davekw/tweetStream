public class Screen extends Bitmap {
    private int feedPos;
    private int index;
    private boolean init;
    private boolean init2;
    private Bitmap panel;
    private Bitmap profile;
    private Bitmap background;

    /**
     * Constructor
     *
     * @param width  Is the screen width
     * @param height Is the screen height
     */
    public Screen(int width, int height) {
        super(width, height);
        init2 = false;
        index = -1;
        feedPos = width;
        init = false;
        panel = new Bitmap(width, height / 5);
    }

    /**
     * Clears the screen with a black background
     */
    public void clear() {
        for (int i = 0; i < pixels.length; i++)
            pixels[i] = 0x000000;
    }

    /**
     * renders the screen with information from the Data class
     *
     * @param data Is the information to draw on the screen
     */
    public void render(Data data) {
        clear(); //clears the screen

        //render background and profile picture after tweet rolls by
        if (index == -1 || feedPos < -data.statuses[index].length() * 24
                || !init || !init2) {
            feedPos = width + data.statuses.length;
            index = (index + 1) % data.statuses.length;
            renderBackground(data);
            renderProfile(data);
        }
        drawElements(data);
    }

    /**
     * draws the elements
     *
     * @param data coming from the tweet
     */
    private void drawElements(Data data) {
        drawBackground(data);
        drawProfile();
        drawFrame();
        drawBird(data);
        drawFollowerInfo(data);
        drawName(data);
        drawScreenName(data);
        drawDecor();
        drawStatus(data);
        drawTime(data);
    }

    /**
     * draws the time of the tweet
     *
     * @param data
     */
    private void drawTime(Data data) {
        //draws time information
        String time = data.times[index].substring(0, 3) + "," +
                data.times[index].substring(3, 11) + "at " +
                data.times[index].substring(11, 16);
        draw2(time, (width - time.length() * 24 - 20),
                height - panel.height + 10, 0xffdd66);
    }

    /**
     * draws the tweet of the person
     *
     * @param data
     */
    private void drawStatus(Data data) {
        //status
        draw2(data.statuses[index], feedPos, height - (panel.height + 24) / 2,
                0xffffff);

        feedPos--; //increments tweet position on screen
    }

    /**
     * draws the decorations
     */
    private void drawDecor() {
        //draws feed image
        draw(Art.feed, 20, 20);

        String lineChar = "_";
        for (int i = 0; i <= width / 24; i++) {
            //overline
            draw2(lineChar, i * 24, height - (panel.height) / 2 - 48, 0x888888);
            //underline
            draw2(lineChar, i * 24, height - (panel.height) / 2, 0x00ffff);
        }
    }

    /**
     * draws the screen name of the person who tweeted
     *
     * @param data Is the information about the tweet
     */
    private void drawScreenName(Data data) {
        //draw at sign
        draw(Art.at, 10, height - panel.height - 10, 0, 0, 64, 64, 0x000000);

        //draw the screen name
        draw2(data.names2[index], 20 + Art.at.width,
                height - panel.height + 10, 0x00ffff);
    }

    /**
     * draws the name of the person who tweeted
     *
     * @param data Is the data about the tweet
     */
    private void drawName(Data data) {
        draw3(data.names[index], Art.feed.width * 2,
                (panel.height - 64) / 2, 0xffffff);
    }

    /**
     * draws the follower info
     *
     * @param data Is the data about the tweet
     */
    private void drawFollowerInfo(Data data) {
        //drawFollowers
        String fol = "followers:" + data.followers[index];
        draw2(fol, width - fol.length() * 24 - 20, (panel.height - 32) / 2,
                0xffdd66);

        //drawFollowing
        String foln = "following:" + data.following[index];
        draw2(foln, width - foln.length() * 24 - 20,
                (panel.height - 32 - 64) / 2,
                0xffffff);
        drawHeart("9", (width - foln.length() / 2 * 24 - 32 + foln.length()),
                (panel.height - 32 - 16),
                0x00ffff);
    }

    /**
     * draws the bird and rotates it
     *
     * @param data Is the data holding the program time
     */
    private void drawBird(Data data) {
        if (profile.height <= height / 3)
            rotDraw(Art.bird, (width - Art.bird.width) / 2 - profile.width / 2,
                    (height - Art.bird.height) / 2 - profile.height / 2,
                    Math.sin(data.time / 100.0) * 20.0);
        else
            rotDraw(Art.bird, (width - Art.bird.width) / 2 - profile.width / 2,
                    (height - Art.bird.height) / 2 - height / 3,
                    Math.sin(data.time / 100.0) * 20.0);
    }

    /**
     * draws the profile picture and its frame
     */
    private void drawProfile() {

        //draws the profle frame
        Bitmap profileFrame = new Bitmap(profile.width + 30
                , profile.height + 30);
        draw(profileFrame, (width - profileFrame.width) / 2,
                (height - profileFrame.height) / 2, 0, 0, profileFrame.width,
                profileFrame.height, 0x000000);

        //draws the profile picture
        draw(profile, (width - profile.width) / 2,
                ((height - profile.height) / 2));
    }

    /**
     * draws the background image
     *
     * @param data Is information about the tweet
     */
    public void drawBackground(Data data) {
        for (int j = -background.height; j < height + background.height;
             j += background.height)
            for (int i = -background.width; i < width + background.width;
                 i += background.width)
                draw(background, (i + data.time % background.width),
                        (j + (int) (Math.sin(data.time / 100.0) * 50.0)
                                % background.height));
    }

    /**
     * draws the frame for the screen
     */
    public void drawFrame() {
        for (int i = 0; i < panel.width; i++) {
            for (int j = 0; j < panel.height; j++) {
                int col = 0x23456;

                pixels[i + j * panel.width] = col - j / 20;
                pixels[i + (j + height - panel.height) * panel.width]
                        = col - j / 10;
            }
        }
    }

    /**
     * renders the profile picture
     *
     * @param data Is information about the tweet
     */
    public void renderProfile(Data data) {
        profile = Art.loadUrl(data.profileUrls[index]);
        init = true;
    }

    /**
     * renders the background image
     *
     * @param data Is information about the tweet
     */
    public void renderBackground(Data data) {
        background = Art.loadUrl(data.backgroundUrls[index]);
        init2 = true;
    }
}
