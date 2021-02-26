import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class menu extends JPanel {
    static final int PANEL_WIDTH = 420;
    static final int PANEL_HEIGHT = 840;
    private final JButton meh;
    private final JLabel change = new JLabel("Restart for changes to take effect", SwingConstants.CENTER);

    menu(JButton RESUME, JButton DIFFICULTY, JButton RESTART){
        this.addKeyListener(new NAdapter());
        this.setLayout(new GridBagLayout());
        meh = RESUME;
        change.setForeground(Color.black);
        JLabel diff = new JLabel("DIFFICULTY :");
        RESTART.setBackground(new Color(255,255,255));
        RESUME.setBackground(new Color(255,255,255));
        DIFFICULTY.setBackground(new Color(255,255,255));
        diff.setForeground(new Color(255,255,255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20,10,20,10);

        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 10;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        this.add(RESUME, gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.weightx = 10;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        this.add(RESTART, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.NONE;
        diff.setForeground(new Color(255,255,255));
        this.add(diff, gbc);

        gbc.gridx = 3;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.NONE;
        DIFFICULTY.setPreferredSize(new Dimension(90,23));
        this.add(DIFFICULTY, gbc);

        gbc.gridx = 2;
        gbc.gridy = 5;
        gbc.gridwidth = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        this.add(change, gbc);


//        change.setVisible(changesMade.get());
        this.setBackground(Color.BLACK);
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        g.drawRect(0,0, PANEL_WIDTH - 1, PANEL_HEIGHT-1);
    }

    public JLabel getChange(){
        return change;
    }

    private class NAdapter extends KeyAdapter {
        @Override
        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode();
            if(key == KeyEvent.VK_ESCAPE){
                meh.doClick();
            }
        }
    }

    }
