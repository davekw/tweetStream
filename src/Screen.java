public class Screen extends Bitmap {
    private int feedPos;
    private int index;
    private boolean init;
    private boolean init2;
    private Bitmap panel;
    private Bitmap profile;
    private Bitmap background;
    private int rot;
    private Bitmap banner;
    private int source;

    public Screen(int width, int height, Data data) {
        super(width, height);
        source = -1;
        init2 = false;
        rot = 0;
        index = -1;
        feedPos = width;
        init = false;
        panel = new Bitmap(width, height / 5);
    }

    public void clear() {
        for (int i = 0; i < pixels.length; i++)
            pixels[i] = 0x000000;
    }


    public void render(Data data, InputHandler inputHandler) {
        clear();
        //renderFeedButton(data, inputHandler);
        if (index == -1 || feedPos < -data.statuses[index].length() * 24
                || !init || !init2) {
            feedPos = width + data.statuses.length;
            index = (index + 1) % data.statuses.length;
            renderBackground(data);
            renderProfile(data);
            //renderBanner(data);
        }

        drawBackground(data);
        //drawBanner(data);
        feedPos--;

        drawProfileFrame(data);
        draw(profile, (width - profile.width) / 2,
                ((height - profile.height) / 2));
        drawFrame();

        //renderBird
        if (profile.height <= height / 3)
            rotDraw(Art.bird, (width - Art.bird.width) / 2 - profile.width / 2,
                    (height - Art.bird.height) / 2 - profile.height / 2,
                    Math.sin(data.time / 100.0) * 20.0);
        else
            rotDraw(Art.bird, (width - Art.bird.width) / 2 - profile.width / 2,
                    (height - Art.bird.height) / 2 - height / 3,
                    Math.sin(data.time / 100.0) * 20.0);
        //drawFollowers
        String fol = "followers:" + data.followers[index];
        draw2(fol, width - fol.length() * 24 - 10, (panel.height - 32) / 2,
                0xffdd66);

        //drawFollowing
        String foln = "following:" + data.following[index];
        draw2(foln, width - foln.length() * 24 - 10,
                (panel.height - 32 - 64) / 2,
                0xffffff);
        draw5("9", (width - foln.length() / 2 * 24 - 32 + foln.length()),
                (panel.height - 32 - 16),
                0x00ffff);

        //render name
        if (data.names[index].length() * 48 <= width)
            draw3(data.names[index], (width -
                            data.names[index].length() * 48) / 2,
                    (panel.height - 64) / 2, 0xffffff);
        else
            draw2(data.names[index], (width -
                            data.names[index].length() * 32) / 2,
                    (panel.height - 64) / 2, 0xffffff);

        //render screenName
        if (data.names2[index].length() * 24 <= width)
            draw2(data.names2[index],
                    (width - data.names[index].length() * 32) / 5,
                    height - panel.height + 10, 0x00ffff);

        //render at sign
        draw(Art.at, (width - data.names[index].length() * 32) / 5 - 64,
                height - panel.height - 10, 0, 0, 64, 64, 0x000000);


        rotDraw(Art.feed, 20, 20, Math.sin(rot / 10.0) * 10.0);
        //status
        draw2(data.statuses[index], feedPos, height - (panel.height + 24) / 2,
                0xffffff);
        //overline
        draw4("________________________________________________________________"
                , 0, height - (panel.height) / 2 - 48, 0x888888);
        //underline
        draw4("________________________________________________________________"
                , 0, height - (panel.height) / 2, 0x00ffff);

        //descriptions
        /*draw2(data.descriptions[index],
                (width - data.descriptions[index].length()) / 2,
                panel.height + 24 + 2,
                0x000000);*/

        draw2(data.times[index].substring(0, 3) + ","
                        + data.times[index].substring(3, 11) + "at "
                        + data.times[index].substring(11, 16),
                (width - 28 * 24)
                , height - panel.height + 10,
                0xffdd66);
    }

    private void drawProfileFrame(Data data) {

        Bitmap profileFrame = new Bitmap(profile.width + 30
                , profile.height + 30);
        draw(profileFrame, (width - profileFrame.width) / 2,
                (height - profileFrame.height) / 2, 0, 0, profileFrame.width,
                profileFrame.height, 0x000000);
    }

    public void drawBackground(Data data) {
        for (int j = -background.height; j < height + background.height;
             j += background.height)
            for (int i = -background.width; i < width + background.width;
                 i += background.width)
                draw(background, (i + data.time % background.width),
                        (j + (int) (Math.sin(data.time / 100.0) * 50.0)
                                % background.height));
    }

    public void drawBanner(Data data) {
        int dy = (int) (Math.sin(data.time / 10.0) * 10.0) - 10;
        draw(banner, 0, (height - banner.height) - dy);
        draw(banner, width - banner.width, (height - banner.height) + dy);
        draw(banner, (width - banner.width) / 3, (height - banner.height) + dy);
        draw(banner, (width - banner.width) / 3 * 2,
                (height - banner.height) - dy);

        draw(banner, 0, -dy);
        draw(banner, width - banner.width, dy);
        draw(banner, (width - banner.width) / 3, dy);
        draw(banner, (width - banner.width) / 3 * 2, -dy);
    }

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

    public void renderBanner(Data data) {
        banner = Art.loadUrl(data.bannerUrls[index]);
        init = true;
    }

    public void renderProfile(Data data) {
        profile = Art.loadUrl(data.profileUrls[index]);
        init = true;
    }

    public void renderBackground(Data data) {
        background = Art.loadUrl(data.backgroundUrls[index]);
        init2 = true;
    }

    public void renderFeedButton(Data data, InputHandler inputHandler) {
        if (inputHandler.x < 20 + Art.feed.width &&
                inputHandler.y < 20 + Art.feed.height) {
            rot++;
            if (inputHandler.click) {
                data.makeSource(-1 * source);
            }
        } else {
            rot = 0;
        }

    }
}

