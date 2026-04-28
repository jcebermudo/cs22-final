import java.awt.*;

/** This interface is used for all the basic and composite shapes made. This ensures that every class under it has a "draw()" method for it to appear on the canvas. */
public interface DrawingObject {
    void draw(Graphics2D g2d);
}