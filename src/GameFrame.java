import org.apache.commons.lang3.time.StopWatch;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;


public class GameFrame extends JFrame {

    GameFrame(){
        JButton RESTART = new JButton("RESTART");
        JButton Difficulty = new JButton("EASY");
        JButton DifficultyMenu = new JButton("EASY");
        JButton startGame = new JButton("START GAME");
        AtomicReference<String> lvl = new AtomicReference<>(Difficulty.getText());
        JPanel Cont = new JPanel();
        JPanel begin = new JPanel();
        JButton esc1 = new JButton("PAUSE");
        JButton esc2 = new JButton("RESUME");
        menu mens = new menu(esc2, Difficulty, RESTART);
        AtomicReference<StopWatch> w = new AtomicReference<>(new StopWatch());
        AtomicReference<Board> balls = new AtomicReference<>(new Board(lvl, esc1, w));
        esc1.setBackground(Color.BLACK);
        esc1.setForeground(Color.white);
        CardLayout cl = new CardLayout();
        Cont.setLayout(cl);
        balls.get().add(esc1);
        DifficultyMenu.setBackground(new Color(255,255,255));

        startGame.setBackground(new Color(255,255,255));
        startGame.setForeground(new Color(0, 0, 0, 255));
        startGame.setFont(new Font("Helvetica", Font.PLAIN , 30));
        begin.setBackground(Color.BLACK);
        Cont.add(balls.get(), "1");
        Cont.add(mens, "2");
        Cont.add(begin, "3");
        cl.show(Cont, "3");
        startGame.addActionListener(e -> {
            balls.set(new Board(lvl, esc1, w));
            w.get().start();
            balls.get().toggleRun();
            balls.get().add(esc1);
            Cont.add(balls.get(), "1");
            cl.show(Cont, "1");
            balls.get().requestFocusInWindow();
        });
        RESTART.addActionListener(e -> {
            w.set(StopWatch.create());
            Cont.remove(2);
            balls.set(new Board(lvl, esc1, w));
            w.get().start();
            balls.get().add(esc1);
            balls.get().toggleRun();
            Cont.add(balls.get(), "1");
            cl.show(Cont, "1");
            balls.get().requestFocusInWindow();
            mens.getChange().setForeground(new Color(0x77000000, true));
        });
        Difficulty.addActionListener(e -> {
            switch (Difficulty.getText()) {
                case "EASY":
                    Difficulty.setText("NORMAL");
                    break;
                case "NORMAL":
                    Difficulty.setText("HARD");
                    break;
                case "HARD":
                    Difficulty.setText("EASY");
                    break;
            }
            mens.getChange().setForeground(new Color(0x77FFC800, true));
            lvl.set(Difficulty.getText());
        });
        DifficultyMenu.addActionListener(e -> {
            switch (DifficultyMenu.getText()) {
                case "EASY":
                    DifficultyMenu.setText("NORMAL");
                    break;
                case "NORMAL":
                    DifficultyMenu.setText("HARD");
                    break;
                case "HARD":
                    DifficultyMenu.setText("EASY");
                    break;
            }
            lvl.set(DifficultyMenu.getText());
        });
        esc1.addActionListener(e -> {
            cl.show(Cont, "2");
            if(!w.get().isSuspended()) w.get().suspend();
            balls.get().toggleRun();
            mens.requestFocusInWindow();
            Difficulty.setText(lvl.get());
        });
        esc2.addActionListener(e -> {
            cl.show(Cont, "1");
            w.get().resume();
            balls.get().toggleRun();
            balls.get().requestFocusInWindow();
        });
        begin.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20,10,20,10);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        JLabel diffi = new JLabel("DIFFICULTY : ");
        diffi.setForeground(new Color(165, 165, 165, 255));
        diffi.setFont(new Font("Helvetica", Font.PLAIN , 15));
        gbc.gridx = 0;
        gbc.gridy = 5;
        begin.add(diffi, gbc);

        gbc.gridx = 3;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        begin.add(DifficultyMenu, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 5;
        begin.add(startGame,gbc);

        this.add(Cont);
        this.setTitle("A second-rate 2D game");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
    }
    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();
        service.execute(() -> EventQueue.invokeLater (GameFrame::new));
    }

}