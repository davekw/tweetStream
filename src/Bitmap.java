public class Bitmap {
    public final int width;
    public final int height;
    public final int[] pixels;
    private static final String chars = "" + //
            "ABCDEFGHIJKLMNOPQRSTUVWXYZ.,!?\"'/\\<>()[]{}" + //
            "abcdefghijklmnopqrstuvwxyz_               " + //
            "0123456789+-=*:;ÖÅÄå                      " + //
            "";

    /**
     * constructor
     *
     * @param width  Is width of bitmap
     * @param height Is height of bitmap
     */
    public Bitmap(int width, int height) {
        this.width = width;
        this.height = height;
        pixels = new int[width * height];
    }

    /**
     * simple drawing algorithm with offset x and y on screen
     *
     * @param bitmap Is the input bitmap
     * @param xOffs  Is the x offset on screen
     * @param yOffs  Ist the y offset on screen
     */
    public void draw(Bitmap bitmap, int xOffs, int yOffs) {
        for (int y = 0; y < bitmap.height; y++) {
            int yPix = y + yOffs;
            if (yPix < 0 || yPix >= height) continue;

            for (int x = 0; x < bitmap.width; x++) {
                int xPix = x + xOffs;
                if (xPix < 0 || xPix >= width) continue;

                int src = bitmap.pixels[x + y * bitmap.width];
                if (src > 0)
                    pixels[xPix + yPix * width] = src;
            }
        }
    }

    /**
     * drawing algorithm with additional parameters for cropping
     *
     * @param bitmap Is the input Bitmap
     * @param xOffs  Is the x screen offset
     * @param yOffs  Is the y screen offset
     * @param xo     Is the initial x
     * @param yo     Is the initial y
     * @param w      Is the change in x
     * @param h      Is he change in y
     * @param col    Is the color
     */
    public void draw(Bitmap bitmap, int xOffs, int yOffs, int xo, int yo,
                     int w, int h, int col) {
        for (int y = 0; y < h; y++) {
            int yPix = y + yOffs;
            if (yPix < 0 || yPix >= height) continue;

            for (int x = 0; x < w; x++) {
                int xPix = x + xOffs;
                if (xPix < 0 || xPix >= width) continue;

                int src = bitmap.pixels[(x + xo) + (y + yo) * bitmap.width];
                if (src >= 0) {
                    pixels[xPix + yPix * width] = src * col;
                }
            }
        }
    }

    /**
     * draws small font
     *
     * @param string Is the input text
     * @param x      Is the x screen offset
     * @param y      Is the y screen offset
     * @param col    Is the color
     */
    public void draw2(String string, int x, int y, int col) {
        for (int i = 0; i < string.length(); i++) {
            int ch = chars.indexOf(string.charAt(i));

            if (ch < 0) continue;

            int xx = ch % 42;
            int yy = ch / 42;
            draw(Art.font2, x + i * 6 * 4, y, xx * 6 * 4, yy * 8 * 4,
                    5 * 4, 8 * 4, col);
        }
    }

    /**
     * draws large font
     *
     * @param string Is the input text
     * @param x      Is the x screen offset
     * @param y      Is the y screen offset
     * @param col    Is the color
     */
    public void draw3(String string, int x, int y, int col) {
        for (int i = 0; i < string.length(); i++) {
            int ch = chars.indexOf(string.charAt(i));

            if (ch < 0) continue;

            int xx = ch % 42;
            int yy = ch / 42;
            draw(Art.font3, x + i * 6 * 8, y, xx * 6 * 8, yy * 8 * 8, 5 * 8,
                    8 * 8, col);
        }
    }

    /**
     * draws heart character
     *
     * @param string Is the input text
     * @param x      Is the x screen offset
     * @param y      Is the y screen offset
     * @param col    Is the color
     */
    public void drawHeart(String string, int x, int y, int col) {
        for (int i = 0; i < string.length(); i++) {

            int ch = chars.indexOf(string.charAt(i));
            if (ch < 0) continue;

            int xx = 17;
            int yy = 2;
            draw(Art.font2, x + i * 6 * 4, y, xx * 6 * 4, yy * 8 * 4,
                    5 * 4, 8 * 4, col);
        }
    }

    /**
     * draws a picture with a rotation
     *
     * @param bitmap Is the input bitmap
     * @param xOffs  Is the screen x offset
     * @param yOffs  Is the screen y offset
     * @param angle  Is the angle of the image
     */
    public void rotDraw(Bitmap bitmap, int xOffs, int yOffs, double angle) {
        final double radians = angle * Math.PI / 180.0,
                cos = Math.cos(radians), sin = Math.sin(radians);
        for (int y = 0; y < bitmap.height; y++) {
            int yPix = y + yOffs;
            if (yPix < 0 || yPix >= height) continue;

            for (int x = 0; x < bitmap.width; x++) {
                int xPix = x + xOffs;
                if (xPix < 0 || xPix >= width) continue;

                final int
                        centerY = bitmap.height / 2,
                        centerX = bitmap.width / 2,
                        n = y - centerY,
                        m = x - centerX,
                        k = (int) (n * cos - m * sin) + centerY,
                        j = (int) (m * cos + n * sin) + centerX;

                if (!(k >= 0 && k < bitmap.height
                        && j >= 0 && j < bitmap.width))
                    continue;
                int src = bitmap.pixels[j + k * bitmap.width];
                if (src > 0) {
                    pixels[(xPix + yPix * width)] = src;
                }
            }
        }
    }
}
