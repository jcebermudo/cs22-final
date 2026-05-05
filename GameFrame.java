// https://www.youtube.com/watch?v=tABiMTmS3cQ
import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.imageio.ImageIO;
import java.util.ArrayList;

public class GameFrame extends JFrame {
    private int width, height;
    private GameCanvas gameCanvas;
    private DrawingComponent dc;
    private Timer animationTimer;
    private Player me;
    private Player partner;
    private Socket socket;
    private int playerID;
    private ReadFromServer rfsRunnable;
    private WriteToServer wtsRunnable;
    private int cycleType = 1;
    private int runCycleIndex = 0;
    private int runCycleTimer = 0;
    private boolean isJumping = false;
    private double gravitationalPull = 1;
    private double newSpeed = 0;
    private int floorHeight = 570;
    private boolean isShooting = false;
    private int shootCycleIndex = 0;
    private int shootCycleTimer = 0;
    private ArrayList<Bullet> bullets = new ArrayList<Bullet>();
    private ArrayList<Integer> bulletDirections = new ArrayList<Integer>();
    private int bulletDirection = 1; // 0 is left, 1 is right
    private int tempCurrentIndex = 0;

    // bullet pass (network sync)
    private boolean bulletSync = false;
    private int bulletX = 0;
    private int bulletY = 0;

    public GameFrame(int w, int h) {
        width = w;
        height = h;
        gameCanvas = new GameCanvas(w, h);
    }

    public void setUpGUI() {
        Container contentPane = this.getContentPane();
        contentPane.add(gameCanvas);
        this.setTitle("----- Player #" + playerID + " -----");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        contentPane.setPreferredSize(new Dimension(width, height));
        createSprites();
        dc = new DrawingComponent();
        contentPane.add(dc);
        this.pack();
        this.setVisible(true);
        setUpKeyListener(contentPane);
        setUpAnimationTimer();
    }

    private void createSprites() {
        if (playerID == 1) {
            me = new Player(50, floorHeight);
            partner = new Player(130, floorHeight);
        } else {
            partner = new Player(130, floorHeight);
            me = new Player(50, floorHeight);
        }
    }

    private void setUpAnimationTimer() {
        int interval = 10;
        ActionListener al = new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                double speed = 5;
                if (me.isAKeyDown()) {
                    cycleType = 4;
                    me.moveH(-speed);
                    runCycleTimer++;
                    if (runCycleTimer >= 6) {
                        runCycleIndex++;
                        me.changeCycle(cycleType, runCycleIndex);
                        if (runCycleIndex >= 3) {
                            runCycleIndex = 0;
                        }
                        runCycleTimer = 0;
                    }
                } else if (me.isSKeyDown()) {
                    cycleType = 4;
                    me.moveH(speed);
                    runCycleTimer++;
                    if (runCycleTimer >= 7) {
                        runCycleIndex++;
                        me.changeCycle(cycleType, runCycleIndex);
                        if (runCycleIndex >= 3) {
                            runCycleIndex = 0;
                        }
                        runCycleTimer = 0;
                    }
                }
                if (isJumping) {
                    cycleType = 2;
                    newSpeed -= gravitationalPull;
                    me.moveV(-newSpeed);
                    me.changeCycle(cycleType, -1);
                    if (me.getY() >= floorHeight) {
                        me.setY(floorHeight);
                        newSpeed = 0;
                        cycleType = 1;
                        me.changeCycle(cycleType, -1);
                        isJumping = false;
                    }
                }
                if (isShooting) {
                    shootCycleTimer++;
                    if (shootCycleTimer >= 5) {
                        cycleType = 3;
                        me.changeCycle(cycleType, shootCycleIndex);
                        shootCycleIndex++;
                        if (shootCycleIndex >= 3) {
                            shootCycleIndex = 0;
                            cycleType = 1;
                            me.changeCycle(cycleType, -1);
                            bulletDirections.add(me.getDirection());
                            bulletSync = true;
                            bulletDirection = me.getDirection();
                            if (bulletDirection == 0) {
                                bulletX = (int) (me.getX() + 40);
                                bulletY = (int) (me.getY() + 50);
                            } else {
                                bulletX = (int) (me.getX() - 40);
                                bulletY = (int) (me.getY() + 50);
                            }
                            bullets.add(new Bullet(bulletX, bulletY));
                            isShooting = false;
                        }
                        shootCycleTimer = 0;
                    } 
                }
                for (int i = bullets.size() - 1; i >= 0; i--) {
                    Bullet b = bullets.get(i);
                    int bDirect = bulletDirections.get(i);
                    if (bDirect == 0) {
                        b.moveH(25);
                    } else {
                        b.moveH(-25);
                    }
                    if (b.getX() >= width || b.getX() <= 0) {
                        bullets.remove(i);
                        bulletDirections.remove(i);
                    }
                }
                dc.repaint();
            }
        };
        animationTimer = new Timer(interval, al);
        animationTimer.start();
    }

    private void setUpKeyListener(Container contentPane) {
        KeyListener kl = new KeyListener() {
            public void keyTyped(KeyEvent ke) {

            }
            public void keyPressed(KeyEvent ke) {
                int keyCode = ke.getKeyCode();
                switch (keyCode) {
                    case KeyEvent.VK_A:
                        me.setBind("a");
                        me.setLastKeyPressed("a");
                        break;
                    case KeyEvent.VK_S:
                        me.setBind("s");
                        me.setLastKeyPressed("s");
                        break;
                    case KeyEvent.VK_SPACE:
                        me.setBind("space");
                        if (!isJumping) {
                            isJumping = true;
                            newSpeed = 23.5;
                        }
                        break;
                    case KeyEvent.VK_Q:
                        isShooting = true;
                        me.setBind("q");
                        break;
                }
            }
            public void keyReleased(KeyEvent ke) {
                int keyCode = ke.getKeyCode();
                switch (keyCode) {
                    case KeyEvent.VK_A:
                        me.unbind("a");
                        cycleType = 1;
                        me.changeCycle(cycleType, -1);
                        break;
                    case KeyEvent.VK_S:
                        me.unbind("s");
                        cycleType = 1;
                        me.changeCycle(cycleType, -1);
                        break;
                    case KeyEvent.VK_SPACE:
                        me.unbind("space");
                        break;
                }
            }
        };
        contentPane.addKeyListener(kl);
        contentPane.setFocusable(true);
    }

    private void connectToServer() {
        try {
            socket = new Socket("localhost", 1000);
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            playerID = in.readInt();
            System.out.println("You are player #" + playerID);
            setUpGUI();
            if (playerID == 1) {
                System.out.println("Waiting for Player #2 to connect...");
            }
            rfsRunnable = new ReadFromServer(in);
            wtsRunnable = new WriteToServer(out);
            rfsRunnable.waitForStartMsg();
        } catch (IOException ex) {
            System.out.println("IOException from connectToServer()");
        }
    }

    private class DrawingComponent extends JComponent {
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.drawImage(Toolkit.getDefaultToolkit().getImage("bg/bg.png"), 0, 0, 1024, 768, null);
            for (Bullet bullet : bullets) {
                bullet.draw(g2d);
            }
            me.draw(g2d);
            partner.draw(g2d);
        }
    }

    private class ReadFromServer implements Runnable {
        private DataInputStream dataIn;
        public ReadFromServer(DataInputStream in) {
            dataIn = in;
            System.out.println("RFS Runnable created");
        }

        public void run() {
            try {
                while (true) {
                    if (partner != null) {
                        partner.setX(dataIn.readDouble());
                        partner.setY(dataIn.readDouble());
                        partner.setDirection(dataIn.readInt());
                        int tempCycleType = dataIn.readInt();
                        int tempInnerIndex = dataIn.readInt();
                        partner.changeCycle(tempCycleType, tempInnerIndex);
                        boolean tempBulletSync = dataIn.readBoolean();
                        int tempBulletX = dataIn.readInt();
                        int tempBulletY = dataIn.readInt();
                        int tempBulletDirection = dataIn.readInt();
                        if (tempBulletSync) {
                            bulletDirections.add(tempBulletDirection);
                            bullets.add(new Bullet(tempBulletX, tempBulletY));
                            bulletSync = false;
                        }
                    }
                }
            } catch (IOException ex) {
                System.out.println("IOException from RFS run()");
            }
        }

        public void waitForStartMsg() {
            try {
                String startMsg = dataIn.readUTF();
                System.out.println("Message from server: " + startMsg);
                Thread readThread = new Thread(rfsRunnable);
                Thread writeThread = new Thread(wtsRunnable);
                readThread.start();
                writeThread.start();
            } catch (IOException ex) {
                System.out.println("IOException from waitForStartMsg()");
            }
        }
    }

    private class WriteToServer implements Runnable {
        private DataOutputStream dataOut;
        public WriteToServer(DataOutputStream out) {
            dataOut = out;
            System.out.println("WTS Runnable created");
        }

        public void run() {
            try {
                while (true) {
                    if (me != null) {
                    dataOut.writeDouble(me.getX());
                    dataOut.writeDouble(me.getY());
                    dataOut.writeInt(me.getDirection());
                    dataOut.writeInt(cycleType);
                    if (cycleType == 1) {
                        dataOut.writeInt(-1);
                    } else if (cycleType == 2) {
                        dataOut.writeInt(-1);
                    } else if (cycleType == 3) {
                        dataOut.writeInt(shootCycleIndex);
                    } else {
                        dataOut.writeInt(runCycleIndex);
                    }
                    dataOut.writeBoolean(bulletSync);
                    dataOut.writeInt(bulletX);
                    dataOut.writeInt(bulletY);
                    dataOut.writeInt(bulletDirection);
                    dataOut.flush();
                    }
                    try {
                        Thread.sleep(25);
                    } catch (InterruptedException ex) {
                        System.out.println("InterruptedException from WTS run()");
                    }
                }
            }
            catch (IOException ex) {
                System.out.println("IOException from WTS run()");
            }
        }
    }

    public static void main(String[] args) {
        GameFrame pf = new GameFrame(1024,768);
        pf.connectToServer();
    }
}
