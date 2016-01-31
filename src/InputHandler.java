
import java.awt.event.*;

public class InputHandler implements KeyListener, MouseListener,
        MouseMotionListener {

    private final int scale;
    private final int height;
    private final int width;
    private boolean[] keys = new boolean[65536]; //max value
    public boolean up, down, left, right;
    public int x, y;
    public boolean click;

    public InputHandler(int width, int height, int scale) {
        this.width = width;
        this.height = height;
        this.scale = scale;
        this.click = false;
        x = 0;
        y = 0;
    }

    public void update() {
        up = keys[KeyEvent.VK_W] || keys[KeyEvent.VK_UP];
        down = keys[KeyEvent.VK_S] || keys[KeyEvent.VK_DOWN];
        left = keys[KeyEvent.VK_A] || keys[KeyEvent.VK_LEFT];
        right = keys[KeyEvent.VK_D] || keys[KeyEvent.VK_RIGHT];
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }

    public void mouseDragged(MouseEvent e) {

    }

    public void mouseMoved(MouseEvent e) {
        if (e.getX() <= width && e.getX() >= 0) {
            x = e.getX();
        }
        if (e.getY() <= height && e.getY() >= 0) {
            y = e.getY();
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        click = true;
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        click = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
