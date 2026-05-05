// https://www.youtube.com/watch?v=tABiMTmS3cQ

import java.awt.*;
import java.awt.geom.*;
import java.io.*;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class Player implements DrawingObject {
    private double x, y;
    private boolean aKey, sKey, spaceKey = false;
    private Color color;
    private Image image;
    private Healthbar healthbar;
    String last_key_pressed = "s";
    private ArrayList<Image> runCycleRight;
    private ArrayList<Image> runCycleLeft;
    private ArrayList<Image> shootCycleRight;
    private ArrayList<Image> shootCycleLeft;
    private int currentIndex = 0;
    private int direction = 1;

    public Player(double x, double y) {
        this.x = x;
        this.y = y;
        image = Toolkit.getDefaultToolkit().getImage("sprites/malloy/s-1-p-r.png");
        runCycleRight = new ArrayList<Image>();
        runCycleLeft = new ArrayList<Image>();
        runCycleRight.add(Toolkit.getDefaultToolkit().getImage("sprites/malloy/run/right/run-r-1.png"));
        runCycleRight.add(Toolkit.getDefaultToolkit().getImage("sprites/malloy/run/right/run-r-2.png"));
        runCycleRight.add(Toolkit.getDefaultToolkit().getImage("sprites/malloy/run/right/run-r-3.png"));
        runCycleRight.add(Toolkit.getDefaultToolkit().getImage("sprites/malloy/run/right/run-r-4.png"));
        runCycleLeft.add(Toolkit.getDefaultToolkit().getImage("sprites/malloy/run/left/run-l-1.png"));
        runCycleLeft.add(Toolkit.getDefaultToolkit().getImage("sprites/malloy/run/left/run-l-2.png"));
        runCycleLeft.add(Toolkit.getDefaultToolkit().getImage("sprites/malloy/run/left/run-l-3.png"));
        runCycleLeft.add(Toolkit.getDefaultToolkit().getImage("sprites/malloy/run/left/run-l-4.png"));
        shootCycleLeft = new ArrayList<Image>();
        shootCycleRight = new ArrayList<Image>();
        shootCycleLeft.add(Toolkit.getDefaultToolkit().getImage("sprites/malloy/shoot/left/gs-l-1.png"));
        shootCycleLeft.add(Toolkit.getDefaultToolkit().getImage("sprites/malloy/shoot/left/gs-l-2.png")); 
        shootCycleLeft.add(Toolkit.getDefaultToolkit().getImage("sprites/malloy/shoot/left/gs-l-3.png"));
        shootCycleRight.add(Toolkit.getDefaultToolkit().getImage("sprites/malloy/shoot/right/gs-r-1.png"));
        shootCycleRight.add(Toolkit.getDefaultToolkit().getImage("sprites/malloy/shoot/right/gs-r-2.png"));
        shootCycleRight.add(Toolkit.getDefaultToolkit().getImage("sprites/malloy/shoot/right/gs-r-3.png"));
        healthbar = new Healthbar((int) x, (int) (y-30));
    }

    public boolean isAKeyDown() {
        return aKey;
    }

    public boolean isSKeyDown() {
        return sKey;
    }

    public void draw(Graphics2D g2d) {
        g2d.drawImage(image, (int) x - 36, (int) y, 200, 150, null);
        healthbar.draw(g2d);
    }

    public void moveH(double n) {
        x += n;
        healthbar.setX((int) x - 36);
    }

    public void moveV(double n) {
        y += n;
        healthbar.setY((int) y - 30);
    }

    public void setX(double n) {
        x = n;
        healthbar.setX((int) x - 36);
    }

    public void setY(double n) {
        y = n;
        healthbar.setY((int) y - 30);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    /** This method sets the bind of the instantiated object to a specified key. */
    public void setBind(String key) {
        if (key.equals("a")) {
            aKey = true;
        } else if (key.equals("s")) {
            sKey = true;
        } else if (key.equals("space")) {
            spaceKey = true;
        }
    }

    public void unbind(String key) {
        if (key.equals("a")) {
            aKey = false;
        } else if (key.equals("s")) {
            sKey = false;
        } else if (key.equals("space")) {
            spaceKey = false;
        }
    }

    /** This method returns the last key pressed to maintain the current side of the character's face. */
    public String getLastKeyPressed() {
        return last_key_pressed;
    }

    /** This method records the last key pressed by the player. */
    public void setLastKeyPressed(String key) {
        last_key_pressed = key;
    }

    public void changeCycle(int index, int innerIndex) {
        if (index == 1) {
            stopCycle();
        } else if (index == 2) {
            setJump();
        } else if (index == 3) {
            changeShootCycle(innerIndex);
        } else {
        if (last_key_pressed.equals("a")) {
            image = runCycleLeft.get(innerIndex);
        } else if (last_key_pressed.equals("s")) {
            image = runCycleRight.get(innerIndex);
        }}
    }

    public void changeShootCycle(int index) {
        if (last_key_pressed.equals("a")) {
            image = shootCycleLeft.get(index);
        } else if (last_key_pressed.equals("s")) {
            image = shootCycleRight.get(index);
        }
    }

    public void stopCycle() {
        if (last_key_pressed.equals("a")) {
            image = Toolkit.getDefaultToolkit().getImage("sprites/malloy/standing/s-l.png");
        } else if (last_key_pressed.equals("s")) {
            image = Toolkit.getDefaultToolkit().getImage("sprites/malloy/standing/s-r.png");
        }
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

// 0 is right 1 is left
    public int getDirection() {
        if (last_key_pressed.equals("a")) {
            direction = 1;
        } else if (last_key_pressed.equals("s")) {
            direction = 0;
        }
        return direction;
    }

    public void setDirection(int d) {
        direction = d;
        if (direction == 0) {
            last_key_pressed = "s";
        } else if (direction == 1) {
            last_key_pressed = "a";
        }
    }

    public void setJump() {
        if (last_key_pressed.equals("s")) {
            image = Toolkit.getDefaultToolkit().getImage("sprites/malloy/jump/jump-r.png");
        } else if (last_key_pressed.equals("a")) {
            image = Toolkit.getDefaultToolkit().getImage("sprites/malloy/jump/jump-l.png");
        }
    }

}
