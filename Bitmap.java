public class Bitmap {
    public final int width;
    public final int height;

    public final int[] pixels;
    private static final String chars = "" + //
            "ABCDEFGHIJKLMNOPQRSTUVWXYZ.,!?\"'/\\<>()[]{}" + //
            "abcdefghijklmnopqrstuvwxyz_               " + //
            "0123456789+-=*:;ÖÅÄå                      " + //
            "";


    public Bitmap(int width, int height) {
        this.width = width;
        this.height = height;
        pixels = new int[width * height];
    }

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

    public void draw4(String string, int x, int y, int col) {
        for (int i = 0; i < string.length(); i++) {

            int xx = 26;
            int yy = 1 ;
            draw(Art.font2, x + i * 6 * 4, y, xx * 6 * 4, yy * 8 * 4,
                    5 * 4, 8 * 4, col);
        }
    }
    public void draw5(String string, int x, int y, int col) {
        for (int i = 0; i < string.length(); i++) {

            int xx = 17;
            int yy = 2 ;
            draw(Art.font2, x + i * 6 * 4, y, xx * 6 * 4, yy * 8 * 4,
                    5 * 4, 8 * 4, col);
        }
    }

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

    public void draw(String string, int x, int y, int col) {
        for (int i = 0; i < string.length(); i++) {
            int ch = chars.indexOf(string.charAt(i));
            if (ch < 0) continue;

            int xx = ch % 42;
            int yy = ch / 42;
            draw(Art.font, x + i * 6, y, xx * 6, yy * 8, 5, 8, col);
        }
    }

    //draws from right to left
    public void flipDraw(Bitmap bitmap, int xOffs, int yOffs, int xo, int yo,
                         int w, int h, int col) {
        for (int y = 0; y < h; y++) {
            int yPix = y + yOffs;
            if (yPix < 0 || yPix >= height) continue;

            for (int x = 0; x < w; x++) {
                int xPix = xOffs - x + 16;
                if (xPix < 0 || xPix >= width) continue;

                int src = bitmap.pixels[(x + xo) + (y + yo) * bitmap.width];
                if (src >= 0) {
                    pixels[xPix + yPix * width] = src & col;
                }
            }
        }
    }

    public void scaleDraw(Bitmap bitmap, int scale, int xOffs, int yOffs,
                          int xo, int yo, int w, int h, int col) {
        for (int y = 0; y < h * scale; y++) {
            int yPix = y + yOffs;
            if (yPix < 0 || yPix >= height) continue;

            for (int x = 0; x < w * scale; x++) {
                int xPix = x + xOffs;
                if (xPix < 0 || xPix >= width) continue;

                int src = bitmap.pixels[(x / scale + xo)
                        + (y / scale + yo) * bitmap.width];
                if (src >= 0) {
                    pixels[xPix + yPix * width] = src * col;
                }
            }
        }
    }

    //rotates a 2D image
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
