import java.awt.*;
import javax.swing.*;

public class GameCanvas extends JComponent {
    private int width, height;
    private Player player1;
    private Player player2;

    public GameCanvas(int w, int h) {
        width = w;
        height = h;
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, width, height);
    }
}