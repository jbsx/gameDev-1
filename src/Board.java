import org.apache.commons.lang3.time.StopWatch;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class Board extends JPanel implements ActionListener {
    private final Timer timer;
    private final List<obstacle> Obstacles;
    private final List<boost> Boost;
    static final int PANEL_WIDTH = 420;
    static final int PANEL_HEIGHT = 840;
    private Image sprite;
    private final int DELAY = 15;
    private boolean running = false;
    private final player Player;
    private boolean isNoob = false;
    private Image playerImage;
    private Image obstImage;
    private Image boostImage;
    private final Rectangle nah;
    private final AtomicReference<String> level;
    private final int easyVel = 4;
    private final int norVel = 6;
    private final int hardVel = 7;
    private final JButton men;
    private final AtomicReference<StopWatch> watch;
    private JLabel i = new JLabel();
    private Color col;

    public Board(AtomicReference<String> lvl, JButton esc, AtomicReference<StopWatch> w) {

        this.addKeyListener(new TAdapter());
        this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        loadImages();
        men = esc;
        watch = w;
        this.add(i);
        level = lvl;
        Obstacles = new ArrayList<>(2);
        Player = new player(playerImage);
        timer = new Timer(DELAY, this);
        timer.start();
        i.setForeground(Color.WHITE);
        if(level.get().equals("EASY")){
            Obstacles.add(new obstacle(easyVel, obstImage, (PANEL_WIDTH/4), 0));
            col = new Color(0,0,255);
        }
        if(level.get().equals("NORMAL")) {
            Obstacles.add(new obstacle(norVel, obstImage, (PANEL_WIDTH/4), 0));
            col = new Color(255,150,0);
        }
        if(level.get().equals("HARD")) {
            Obstacles.add(new obstacle(hardVel, obstImage, (PANEL_WIDTH/4), 0));
            col = new Color(255,0,0);
        }
        Boost = new ArrayList<>(1);
        Boost.add(new boost(boostImage, (PANEL_WIDTH/4), 0));
        nah = new Rectangle(0,0, PANEL_WIDTH - 1, PANEL_HEIGHT-1);

    }

    public void toggleRun(){
        if(!isNoob) {
            running = !running;
        }
    }

    public void loadImages(){
        URL pURL, oURL, bURL, gURL;
        try{
            gURL = new URL("https://i.ibb.co/5Mzc51G/noob.png");
            pURL = new URL("https://i.ibb.co/bmDYfZ4/Player.png");
            oURL = new URL("https://i.ibb.co/qd7Mb6w/obstacle.png");
            bURL = new URL("https://i.ibb.co/B4Lst3F/Boost.png");
            ImageIcon pii = new ImageIcon(ImageIO.read(pURL));
            ImageIcon oii = new ImageIcon(ImageIO.read(oURL));
            ImageIcon bii = new ImageIcon(ImageIO.read(bURL));
            ImageIcon gii = new ImageIcon(ImageIO.read(gURL));
            playerImage = pii.getImage();
            obstImage = oii.getImage();
            boostImage = bii.getImage();
            sprite = gii.getImage();
        }catch(Exception ignored){
        }
    }

    public long getChusp(){
        long g = 0;
        if(watch.get().isStarted()) {
            watch.get().split();
            g = watch.get().getSplitTime();
            watch.get().unsplit();
        }
        return g;
    }

    private void check() {
        for (obstacle Obst : Obstacles){
            if (Player.getBounds().intersects(Obst.getBounds())) {
                isNoob = true;
                running = false;
            }
        }
        for (boost breh : Boost) {
            if (Player.getBounds().intersects(breh.getBounds()) && breh.getTaken()) {
                Player.setSpeed(Player.getSpeed()+1);
                breh.setTaken(true);
            }
        }
        if(!nah.contains(Player.getBounds())) {
            isNoob = true;
            running = false;
        }
    }


    private void gameOver(Graphics g){
        int spriteX = 10;
        int spriteY = 300;
        g.drawImage(sprite, spriteX, spriteY, this);
        g.setFont(new Font("Helvetica", Font.PLAIN, 25));
        g.drawString("Time Lasted: " + this.getChusp()/1000 + " second(s)", 70, 300);
        this.requestFocusInWindow();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        draw(g);
        g.setColor(Color.white);
//        g.setFont(new Font("Courier New", Font.BOLD, 15));
//        g.drawString("Timer :" + this.getChusp()/1000 + "." + ((this.getChusp()%1000)/10), 10, 20);
        if(isNoob) gameOver(g);
        g.setColor(col);
        g.drawRect(0, 0, PANEL_WIDTH - 1, PANEL_HEIGHT - 1);
        Toolkit.getDefaultToolkit().sync();
    }

    private void draw(Graphics g) {
        g.drawImage(Player.getImage(), Player.getX(), Player.getY(), this);
        for(obstacle obst : Obstacles) {
            g.drawImage(obstImage, obst.getX(), obst.getY(), null);
        }
        for(boost breh : Boost){
            if (breh.getTaken()) g.drawImage(boostImage, breh.getX(), breh.getY(), null);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running && !watch.get().isSuspended()) {
            if(this.getChusp()>1000){
            if ((Integer.toString((int)(this.getChusp()))).charAt((Integer.toString((int) this.getChusp())).length()-3) == 7) {
                switch (level.get()) {
                    case "EASY":
                        Obstacles.add(new obstacle(easyVel, obstImage, 3 * (PANEL_WIDTH / 4), 0));
                        break;
                    case "NORMAL":
                        Obstacles.add(new obstacle(norVel, obstImage, 3 * (PANEL_WIDTH / 4), 0));
                        break;
                    case "HARD":
                        Obstacles.add(new obstacle(hardVel, obstImage, 3 * (PANEL_WIDTH / 4), 0));
                        break;
                    }
                }
            }
            Player.move();
            for (obstacle obst : Obstacles) {
                obst.move();
                if (obst.getY() >= PANEL_HEIGHT / 12 && obst.getY() <= ((PANEL_HEIGHT / 12)+3) ) {
                    switch (level.get()) {
                        case "EASY":
                            Obstacles.add(new obstacle(easyVel, obstImage, 0));
                            break;
                        case "NORMAL":
                            Obstacles.add(new obstacle(norVel, obstImage, 0));
                            break;
                        case "HARD":
                            Obstacles.add(new obstacle(hardVel, obstImage, 0));
                            break;
                    }
                    break;
                }
                if (obst.getY() < 0) {
                    Obstacles.remove(obst);
                    break;
                }
            }
            for (boost breh : Boost) {
                breh.move();
                if (breh.getY() >= PANEL_HEIGHT *1.5  && breh.getY() <= ((PANEL_HEIGHT * 1.5)+2)) {
                    Boost.add(new boost(boostImage, 0));
                    break;
                }
                if (breh.getY() < 0) {
                    Boost.remove(breh);
                    break;
                }
            }
            check();
            repaint();
            i.setText("Timer :" + String.valueOf(this.getChusp()/1000) + "." + String.valueOf((this.getChusp()%1000)/10));
        }
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            Player.keyReleased(e);
            int key = e.getKeyCode();
            if(key == KeyEvent.VK_ESCAPE){
                men.doClick();
            }
        }

        @Override
        public void keyPressed(KeyEvent e) {
            Player.keyPressed(e);
        }
        }
    }