import java.awt.*;

public class obstacle {
    private final int x;
    private int y;
    private int w;
    private int h;
    private int speed;

    public obstacle(int vel, Image g, int i){
        this(vel, g,-1, i);
        speed = vel;
    }
    public obstacle(int vel, Image g, int u, int i){
        loadImage(g);
        this.x =  u < 0 ? (int) (Math.random() * (420)) : u;
        this.y = i;
        speed = vel;
    }

    private void loadImage(Image i){
        w = i.getWidth(null);
        h = i.getHeight(null);
    }
    public void move() { y += speed; }

    public int getX() { return x;  }

    public int getY() { return y; }

    public Rectangle getBounds() { return new Rectangle(x, y, w, h); }

}
