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
    String last_key_pressed = "s";
    private ArrayList<Image> runCycleRight;
    private ArrayList<Image> runCycleLeft;

    public Player(double x, double y) {
        this.x = x;
        this.y = y;
        image = Toolkit.getDefaultToolkit().getImage("sprites/malloy/s-1-p-r.png");
        runCycleRight = new ArrayList<Image>();
        runCycleLeft = new ArrayList<Image>();
        runCycleRight.add(Toolkit.getDefaultToolkit().getImage("sprites/malloy/run-cycle/pixelated/right/wc-1-p.png"));
        runCycleRight.add(Toolkit.getDefaultToolkit().getImage("sprites/malloy/run-cycle/pixelated/right/wc-2-p.png"));
        runCycleRight.add(Toolkit.getDefaultToolkit().getImage("sprites/malloy/run-cycle/pixelated/right/wc-3-p.png"));
        runCycleRight.add(Toolkit.getDefaultToolkit().getImage("sprites/malloy/run-cycle/pixelated/right/wc-4-p.png"));
        runCycleLeft.add(Toolkit.getDefaultToolkit().getImage("sprites/malloy/run-cycle/pixelated/left/wc-1-p.png"));
        runCycleLeft.add(Toolkit.getDefaultToolkit().getImage("sprites/malloy/run-cycle/pixelated/left/wc-2-p.png"));
        runCycleLeft.add(Toolkit.getDefaultToolkit().getImage("sprites/malloy/run-cycle/pixelated/left/wc-3-p.png"));
        runCycleLeft.add(Toolkit.getDefaultToolkit().getImage("sprites/malloy/run-cycle/pixelated/left/wc-4-p.png"));
    }

    boolean isAKeyDown() {
        return aKey;
    }

    boolean isSKeyDown() {
        return sKey;
    }

    public void draw(Graphics2D g2d) {
        g2d.drawImage(image, (int) x, (int) y, 120, 120, null);
    }

    public void moveH(double n) {
        x += n;
    }

    public void moveV(double n) {
        y += n;
    }

    public void setX(double n) {
        x = n;
    }

    public void setY(double n) {
        y = n;
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

    public void changeRunCycle(int index) {
        if (last_key_pressed.equals("a")) {
            image = runCycleLeft.get(index);
        } else if (last_key_pressed.equals("s")) {
            image = runCycleRight.get(index);
        }
    }

    public void stopCycle() {
        if (last_key_pressed.equals("a")) {
            image = Toolkit.getDefaultToolkit().getImage("sprites/malloy/s-1-p-l.png");
        } else if (last_key_pressed.equals("s")) {
            image = Toolkit.getDefaultToolkit().getImage("sprites/malloy/s-1-p-r.png");
        }
    }
        

}
