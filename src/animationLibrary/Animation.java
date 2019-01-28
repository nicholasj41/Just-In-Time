package animationLibrary;

import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Abstract Animation Class
 *
 * @Primary_Author - Leyton Blackler (300368625).
 * @Code_Reviewer - Riley Blair (300371586).
 * @External_Tester - Celine Young (300378246).
 */
public abstract class Animation {

    /**
     * The timer used to create an animation over a period of time.
     */
    protected Timer timer;

    /**
     * The current frame of the animation.
     */
    protected Image current;

    protected enum FlipDirection {
        HORIZONTALLY,
        VERTICALLY
    }

    /**
     * Creates a new animation that will animate frames at the interval provided.
     * @param interval How often the animation should update/animate the next frame.
     */
    public Animation(int interval) {
        timer = new Timer(interval, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                current = animate();
            }
        });
    }

    /**
     * Performs the necessary operations to animate the next frame of the animation.
     * @return The next frame of the animation.
     */
    protected abstract Image animate();

    /**
     * Flips an image in the specified direction.
     * @param image The image to flip.
     * @param direction The direction to flip the image in.
     * @return The flipped image.
     */
    protected Image flip(Image image, FlipDirection direction) {
        //If the animation was playing, pause it before resizing.
        /*boolean running = false;
        if (timer.isRunning()) {
            running = true;
            pause();
        }*/
        //Flip the image.
        BufferedImage flipped = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2D = flipped.createGraphics();
        if (direction == FlipDirection.HORIZONTALLY) {
            g2D.drawImage(image, image.getWidth(null), 0, -image.getWidth(null), image.getHeight(null), null);
        } else if (direction == FlipDirection.VERTICALLY) {
            g2D.drawImage(image, 0, image.getHeight(null), image.getWidth(null), -image.getHeight(null), null);
        }
        //If the animation was playing, resume the animation.
        /*if (running) {
            play();
        }*/
        return flipped;
    }

    /**
     * Flips the animation horizontally.
     */
    public abstract void flipHorizontally();

    /**
     * Flips the animation vertically.
     */
    public abstract void flipVertically();

    /**
     * Sets a fixed size for the animation.
     * @param width The width of the animation.
     * @param height The height of the animation.
     */
    protected Image setSize(Image image, int width, int height) {
        //If the animation was playing, pause it before resizing.
        boolean running = false;
        if (timer.isRunning()) {
            running = true;
            pause();
        }
        //Resize the image.
        BufferedImage scaled = Scalr.resize((BufferedImage) image, Scalr.Method.ULTRA_QUALITY, width, height);//new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        //Graphics2D g2D = (Graphics2D) scaled.getGraphics();
        //g2D.drawImage(image.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);
        //If the animation was playing, resume the animation.
        if (running) {
            play();
        }
        return scaled;
    }

    /**
     * Sets a fixed size for the animation.
     * @param width The width of the animation.
     * @param height The height of the animation.
     */
    public abstract void setSize(int width, int height);

    /**
     * Starts the animation.
     */
    public void play() {
        this.timer.start();
    }

    /**
     * Paused the animation.
     */
    public void pause() {
        this.timer.stop();
    }

    /**
     * Gets the current frame in the animation as an image.
     * @return The current frame in the animation.
     */
    public Image getImage() {
        return this.current;
    }

    /**
     * Gets the current frame in the animation as an image and flips it in the directions specified.
     * @return The current frame in the animation.
     */
    public Image getImage(boolean flipHorizontally, boolean flipVertically) {
        Image image = new BufferedImage(current.getWidth(null), current.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2D = (Graphics2D) image.getGraphics();
        g2D.drawImage(current, 0, 0, null);
        if (flipHorizontally) image = flip(image, FlipDirection.HORIZONTALLY);
        if (flipVertically) image = flip(image, FlipDirection.VERTICALLY);
        return image;
    }

    /**
     * Utility method used to load an image.
     * @param path The file path of the image.
     * @return The image loaded from the file path.
     */
    protected Image loadImage(String path) {
        Image image = null;
        try {
            image = ImageIO.read(ClassLoader.getSystemResource(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

}
