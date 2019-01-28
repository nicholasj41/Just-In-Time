package animationLibrary.tests;

import animationLibrary.Animation;

import javax.swing.*;
import java.awt.*;

public class TestCanvas extends JPanel {
	private static final long serialVersionUID = 1L;
	private Animation animation;
    private int time;
    private long endTime;

    public TestCanvas(Animation animation, int time) {
        this.animation = animation;
        this.time = time;
    }

    public void run() {
        animation.play();
        endTime = System.currentTimeMillis() + time;
        long nextFrame = System.currentTimeMillis();
        while (System.currentTimeMillis() < endTime) {
            if (nextFrame < System.currentTimeMillis()) continue;
            revalidate();
            repaint();
            nextFrame += 1000 / 60;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        String timeLeft = Long.toString((endTime - System.currentTimeMillis()) / 1000);
        int textWidth = g2D.getFontMetrics().stringWidth(timeLeft);
        g2D.drawString(timeLeft, this.getWidth() / 2 - textWidth / 2, 40);
        Image current = animation.getImage();
        int x = (this.getWidth() / 2) - (current.getWidth(null) / 2);
        int y = (this.getHeight() / 2) - (current.getHeight(null) / 2);
        g2D.drawImage(current, x, y, null);
    }
}
