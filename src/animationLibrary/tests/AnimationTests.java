package animationLibrary.tests;

import animationLibrary.Animation;
import animationLibrary.CycleAnimation;
import animationLibrary.RotatingAnimation;
import animationLibrary.ScrollingAnimation;
import org.junit.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Animation Tests Class
 *
 * @Primary_Author - Leyton Blackler (300368625).
 * @Code_Reviewer - Riley Blair (300371586).
 * @External_Tester - Celine Young (300378246).
 */
public class AnimationTests {

    private static final boolean DISPLAY_DIALOG_MESSAGES = false;
    private static final int FPS = 60;

    private TestCanvas constructCanvas(String testName, Animation animation, int time) {
        JFrame window = new JFrame();
        window.setTitle(testName);
        TestCanvas canvas = new TestCanvas(animation, time);
        canvas.setPreferredSize(new Dimension(400, 400));
        canvas.setBackground(Color.WHITE);
        window.add(canvas, BorderLayout.CENTER);
        window.pack();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        window.setLocation(screenSize.width / 2 - window.getSize().width / 2, screenSize.height / 2 - window.getSize().height / 2);
        window.setVisible(true);
        return canvas;
    }

    private void closeCanvas(TestCanvas canvas) {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(canvas);
        frame.dispose();
        frame.setVisible(false);
    }

    private static Image createTestImage(Color color1, Color color2) {
        int width = 250;
        int height = 250;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2D = image.createGraphics();
        g2D.setColor(Color.RED);
        GradientPaint gradient = new GradientPaint(0, 0, color1, width, height, color2);
        g2D.setPaint(gradient);
        g2D.fillRect(width / 2 - 125, height / 2 - 125, 250, 250);
        g2D.dispose();
        return image;
    }

    @Test
    public void testRotatingAnimation() {
        String testName = "Rotating Animation Test";
        if (DISPLAY_DIALOG_MESSAGES) {
            JOptionPane.showMessageDialog(null, "This test will simulate:\n\n" +
                            "- Rotating a square image within its image bounds.\n\n" +
                            "Press OK to begin the test.",
                    testName, JOptionPane.INFORMATION_MESSAGE);
        }

        int period = 4;
        Animation rotating = new RotatingAnimation(period, FPS, createTestImage(Color.CYAN, Color.RED));
        TestCanvas canvas = constructCanvas(testName, rotating, period * 2 * 1000);
        canvas.run();
        closeCanvas(canvas);
    }

    @Test
    public void testScrollingAnimationHorizontal() {
        String testName = "Horizontal Rotating Animation Test";
        if (DISPLAY_DIALOG_MESSAGES) {
            JOptionPane.showMessageDialog(null, "This test will simulate:\n\n" +
                            "- Scrolling a square image horizontally, wrapping around.\n\n" +
                            "Press OK to begin the test.",
                    testName, JOptionPane.INFORMATION_MESSAGE);
        }

        runScrolling(testName, ScrollingAnimation.Direction.HORIZONTAL);
    }

    @Test
    public void testScrollingAnimationVertical() {
        String testName = "Vertical Rotating Animation Test";
        if (DISPLAY_DIALOG_MESSAGES) {
            JOptionPane.showMessageDialog(null, "This test will simulate:\n\n" +
                            "- Scrolling a square image vertically, wrapping around.\n\n" +
                            "Press OK to begin the test.",
                    testName, JOptionPane.INFORMATION_MESSAGE);
        }
        runScrolling(testName, ScrollingAnimation.Direction.VERTICAL);
    }

    private void runScrolling(String testName, ScrollingAnimation.Direction direction) {
        int period = 4;
        Animation scrolling = new ScrollingAnimation(createTestImage(Color.CYAN, Color.RED), direction, period, FPS);
        TestCanvas canvas = constructCanvas(testName, scrolling, period * 2 * 1000);
        canvas.run();
        closeCanvas(canvas);
    }

    @Test
    public void testCycleAnimation() {
        String testName = "Cycle Animation Test";
        if (DISPLAY_DIALOG_MESSAGES) {
            JOptionPane.showMessageDialog(null, "This test will simulate:\n\n" +
                            "- Cycling between 3 different colour squares.\n\n" +
                            "Press OK to begin the test.",
                    testName, JOptionPane.INFORMATION_MESSAGE);
        }

        int period = 4;
        Image[] images = {
                createTestImage(Color.CYAN, Color.RED),
                createTestImage(Color.GREEN, Color.BLUE),
                createTestImage(Color.ORANGE, Color.MAGENTA)
        };
        Animation cycling = new CycleAnimation(500, images);
        TestCanvas canvas = constructCanvas(testName, cycling, period * 2 * 1000);
        canvas.run();
        closeCanvas(canvas);
    }
}
