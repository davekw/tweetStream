import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Driver extends Canvas implements Runnable {
    private static int width;
    private static int height;
    private Screen screen;
    private BufferedImage img;
    private int[] pixels;
    private boolean running;
    private Thread thread;
    private Data data;

    /**
     * default constructor
     */
    public Driver() {
        //size
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        width = (int) screenSize.getWidth();
        height = (int) screenSize.getHeight();
        Dimension size = new Dimension(width, height);
        setSize(size);

        //screen
        data = new Data();
        screen = new Screen(width, height);

        //image
        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        pixels = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();
    }

    /**
     * start
     */
    public synchronized void start() {
        if (running) return;
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    /**
     * stop
     */
    public synchronized void stop() {
        if (!running) return;
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

        double unprocessedSeconds = 0;
        long lastTime = System.nanoTime();
        double secondsPerTick = 1 / 60.0;
        int tickCount = 0;

        while (running) {
            long now = System.nanoTime();
            long passedTime = now - lastTime;
            lastTime = now;
            if (passedTime < 0) passedTime = 0;
            if (passedTime > 100000000) passedTime = 100000000;

            unprocessedSeconds += passedTime / 1000000000.0;

            boolean ticked = false;
            while (unprocessedSeconds > secondsPerTick) {
                tick();
                unprocessedSeconds -= secondsPerTick;
                ticked = true;

                tickCount++;
                if (tickCount % 60 == 0) {
                    lastTime += 1000;
                }
            }

            if (ticked) {
                render();
            } else {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    /**
     * data processing
     */
    private void tick() {
        data.tick();
    }

    /**
     * render
     */
    public void render() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }

        //render screen
        screen.render(data);

        //update canvas img
        for (int i = 0; i < width * height; i++) {
            pixels[i] = screen.pixels[i];
        }

        Graphics g = bs.getDrawGraphics();
        g.drawImage(img, 0, 0, width, height, null);
        g.dispose();
        bs.show();
    }

    /**
     * main
     *
     * @throws Exception
     */
    public static final void main(final String[] args) throws Exception {
        Driver session = new Driver();
        JFrame frame = new JFrame("tweetStream");
        frame.setResizable(false);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(session, BorderLayout.CENTER);

        frame.setContentPane(panel);
        frame.pack();

        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        session.start();
    }
}
