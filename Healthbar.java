// https://www.youtube.com/watch?v=tABiMTmS3cQ

import java.awt.*;
import java.awt.geom.*;
import java.io.*;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class Healthbar implements DrawingObject {
    private double x, y;
    private int health;
    public Healthbar(int x, int y) {
        this.x = x;
        this.y = y;
        health = 100;
    }

    public void draw(Graphics2D g2d) {
        g2d.setColor(Color.GREEN);
        g2d.fillRect((int) x, (int) y, health, 10);
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
        

}
