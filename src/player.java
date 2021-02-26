import java.awt.*;
import java.awt.event.KeyEvent;

public class player {
    private int dx;
    private int dy;
    private int x = 190;
    private int y = 700;
    private int w;
    private int h;
    private Image image;
    private int speed = 3;

    public player(Image i){
        loadImage(i);
    }
    private void loadImage(Image i){
        try {
            image = i;
            w = image.getWidth(null);
            h = image.getHeight(null);

        } catch ( Exception ex) {
            ex.printStackTrace();
        }
    }
    public void move() {

        x += dx;
        y += dy;

    }

    public int getX() {

        return x;
    }

    public int getY() {

        return y;
    }

    public void setSpeed(int s){
        this.speed = s;
    }
    public int getSpeed(){
        return this.speed;
    }



    public Image getImage() {

        return image;
    }
    public Rectangle getBounds() {

        return new Rectangle(x+5, y+3, w-12 , h-2);

    }

    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) {
            dx = -speed;
        }

        if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) {
            if(this.getX() == 400) dx = 0; else dx = speed;
        }

        if (key == KeyEvent.VK_W || key == KeyEvent.VK_UP) {
            if(this.getY() == 0) dx = 0; else dy = -speed;
        }

        if (key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) {
            if(this.getY() == 820) dx = 0; else dy = speed;
        }
    }

    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) {
            dx = 0;
        }

        if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) {
            dx = 0;
        }

        if (key == KeyEvent.VK_W || key == KeyEvent.VK_UP) {
            dy = 0;
        }

        if (key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) {
            dy = 0;
        }
    }
}
