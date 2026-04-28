public class GameCanvas extends JComponent {
    private int width, height;
    private PlayerSprite player1;
    private PlayerSprite player2;

    public GameCanvas(int w, int h) {
        width = w;
        height = h;
    }

    @Overrid 
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, width, height);
    }
}