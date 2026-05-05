// https://www.youtube.com/watch?v=tABiMTmS3cQ

import java.awt.*;
import java.awt.geom.*;
import java.io.*;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class Bullet implements DrawingObject {
    private double x, y;
    private Image image;

    public Bullet(double x, double y) {
        this.x = x;
        this.y = y;
        image = Toolkit.getDefaultToolkit().getImage("sprites/props/bullet.png");
    }

    public void draw(Graphics2D g2d) {
        g2d.drawImage(image, (int) x, (int) y, 17, 8, null);
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
