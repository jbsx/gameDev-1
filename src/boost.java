import java.awt.*;

public class boost {
    private final int x;
    private int y;
    private int w;
    private int h;
    private boolean taken = false;

    public boost(Image g, int i){
        this(g, -1, i);
    }
    public boost(Image g, int u, int i){
        loadImage(g);
        this.x =  u < 0 ? (int) (Math.random() * (420)) : u;
        this.y = i;
    }

    private void loadImage(Image i){
        w = i.getWidth(null);
        h = i.getHeight(null);

    }
    public void move() { y += 3; }

    public int getX() { return x;  }

    public int getY() { return y; }

    public void setTaken(boolean nice){
        taken = nice;
    }
    public boolean getTaken(){
        return !taken;
    }

    public Rectangle getBounds() { return new Rectangle(x+7, y+5, w-7, h-5); }

}
