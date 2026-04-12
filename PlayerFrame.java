import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class PlayerFrame extends JFrame {
    private int width, height;
    private Container contentPane;
    private PlayerSprite me;
    private DrawingComponent dc;
    private Timer animationTimer;
    private boolean up = false;
    private boolean down = false;
    private boolean left = false;
    private boolean right = false;

    public PlayerFrame(int w, int h) {
        width = w;
        height = h;
        up = false;
        down = false;
        left = false;
        right = false;
    }

    public void setUpGUI() {
        contentPane = this.getContentPane();
        this.setTitle("-----");
        contentPane.setPreferredSize(new Dimension(width, height));
        createSprites();
        dc = new DrawingComponent();
        contentPane.add(dc);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        setUpKeyListener();
        setUpAnimationTimer();

    }

    private void createSprites() {
        me = new PlayerSprite(100, 400, 50, Color.BLUE);
    }

    private void setUpAnimationTimer() {
        int interval = 10;
        ActionListener al = new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                double speed = 5;
                if (up) {
                    me.moveV(-speed);
                } 
                if (down) {
                    me.moveV(speed);
                }
                if (left) {
                    me.moveH(-speed);
                }
                if (right) {
                    me.moveH(speed);
                }
                dc.repaint();
            }
        };
        animationTimer = new Timer(interval, al);
        animationTimer.start();
    }

    private void setUpKeyListener() {
        KeyListener kl = new KeyListener() {
            public void keyTyped(KeyEvent ke) {

            }
            public void keyPressed(KeyEvent ke) {
                int keyCode = ke.getKeyCode();
                switch (keyCode) {
                    case KeyEvent.VK_W:
                        up = true;
                        break;
                    case KeyEvent.VK_S:
                        down = true;
                        break;
                    case KeyEvent.VK_A:
                        left = true;
                        break;
                    case KeyEvent.VK_D:
                        right = true;
                        break;
                }
            }
            public void keyReleased(KeyEvent ke) {
                int keyCode = ke.getKeyCode();
                switch (keyCode) {
                    case KeyEvent.VK_W:
                        up = false;
                        break;
                    case KeyEvent.VK_S:
                        down = false;
                        break;
                    case KeyEvent.VK_A:
                        left = false;
                        break;
                    case KeyEvent.VK_D:
                        right = false;
                        break;
                }
            }
        };
        contentPane.addKeyListener(kl);
        contentPane.setFocusable(true);
    }

    private class DrawingComponent extends JComponent {
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            me.drawSprite(g2d);
        }
    }

    public static void main(String[] args) {
        PlayerFrame pf = new PlayerFrame(640,480);
        pf.setUpGUI();
    }
}
