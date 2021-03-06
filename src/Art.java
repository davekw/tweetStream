import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.net.URLConnection;

public class Art {
    public static Bitmap font2 = loadBitmap("/font2.png");
    public static Bitmap font3 = loadBitmap("/font3.png");
    public static Bitmap at = loadBitmap("/at.png");
    public static Bitmap bird = loadPure("/bird.png");
    public static Bitmap feed = loadPure("/feed2.png");

    /**
     * Creates bitmap that needs post coloring
     *
     * @param fileName The name of the file to be mapped
     * @return a bitmap object to be on the screen
     */
    public static Bitmap loadBitmap(String fileName) {
        try {
            BufferedImage img = ImageIO.read(Art.class.getResource(fileName));

            int w = img.getWidth();
            int h = img.getHeight();

            Bitmap result = new Bitmap(w, h);
            img.getRGB(0, 0, w, h, result.pixels, 0, w);
            for (int i = 0; i < result.pixels.length; i++) {
                int in = result.pixels[i];
                int col = (in & 0xf) >> 2;
                if (in == 0xffff00ff) col = -1;
                result.pixels[i] = col;
            }
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates bitmap that uses original coloring
     *
     * @param fileName The name of the file to be mapped
     * @return a bitmap object to be placed on the screen
     */
    public static Bitmap loadPure(String fileName) {
        try {
            BufferedImage img = ImageIO.read(Art.class.getResource(fileName));

            int w = img.getWidth();
            int h = img.getHeight();

            Bitmap result = new Bitmap(w, h);
            img.getRGB(0, 0, w, h, result.pixels, 0, w);
            for (int i = 0; i < result.pixels.length; i++) {
                int in = result.pixels[i];
                int col = (in & 0xffffff);
                if (in == 0xffff00ff) col = -1;
                result.pixels[i] = col;
            }
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates bitmap from url with no post coloring
     *
     * @param urlString The name of the url image to be mapped
     * @return a bitmap object to be placed on the screen
     */
    public static Bitmap loadUrl(String urlString) {
        try {
            URL url = new URL(urlString);

            URLConnection connection = url.openConnection();
            connection.setRequestProperty("User-Agent", "xxxxxx");

            BufferedImage img = ImageIO.read(url);

            int w = img.getWidth();
            int h = img.getHeight();

            Bitmap result = new Bitmap(w, h);
            img.getRGB(0, 0, w, h, result.pixels, 0, w);
            for (int i = 0; i < result.pixels.length; i++) {
                int in = result.pixels[i];
                int col = (in & 0xffffff);
                if (in == 0xffff00ff) col = -1;
                result.pixels[i] = col;
            }
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
