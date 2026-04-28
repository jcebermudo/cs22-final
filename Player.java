// https://www.youtube.com/watch?v=tABiMTmS3cQ

import java.awt.*;
import java.awt.geom.*;

public class Player implements DrawingObject {
    private double x, y;
    private boolean aKey, sKey, spaceKey = false;
    private Color color;

    public PlayerSprite(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void drawSprite(Graphics2D g2d) {
        Rectangle2D.Double square = new Rectangle2D.Double(x,y,size,size);
        g2d.setColor(color);
        g2d.fill(square);
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
