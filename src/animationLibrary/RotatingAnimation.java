package animationLibrary;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 * Rotating Animation
 *
 * @Primary_Author - Leyton Blackler (300368625).
 * @Code_Reviewer - Riley Blair (300371586).
 * @External_Tester - Celine Young (300378246).
 */
public class RotatingAnimation extends Animation {

    /**
     * The image that is to be rotated.
     */
    private Image image;

    /**
     * The current angle of rotation the animation is at.
     */
    private double angle = 0;

    /**
     * The number of degrees to increase the angle by for each frame.
     */
    private double angleStep = 1;

    /**
     * Creates a new animation where an image is rotated.
     * 
     * @param period The period of rotation for the image (e.g. period = 1 will take 1 second for a full rotation).
     * @param fps The number of frames per second to animate.
     * @param image The image to use for the animation.
     */
    public RotatingAnimation(int period, int fps, Image image) {
        //Calculate and set the interval required to achieved the defined period of rotation.
        super(1000 / fps);
        //Set the initial image.
        super.current = image;
        //Calculate the angle increase for each frame in the animation.
        angleStep = 360 / (period * fps);
        //Load the image from the given path.
        this.image = image;
    }

    /**
     * Rotates the image by the calculated angle step.
     * 
     * @return The current frame in the animation after it has been animated.
     */
    @Override
    protected Image animate() {
        //Create a new transformation that rotates the image about its centre.
        AffineTransform transform = new AffineTransform();
        transform.translate(this.image.getWidth(null) / 2, this.image.getHeight(null) / 2);
        transform.rotate(Math.toRadians(angle));
        transform.translate(-image.getWidth(null) / 2, -image.getHeight(null) / 2);
        //Create a new image to display the rotated image.
        BufferedImage rotated = new BufferedImage(this.image.getWidth(null), this.image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2D = (Graphics2D) rotated.getGraphics();
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        //Apply and draw the transformation.
        g2D.drawImage(image, transform, null);
        //Increase the angle to rotate for the next frame.
        if (angle >= 360) angle = 0;
        angle += angleStep;
        return rotated;
    }

    /**
     * Flips the animation horizontally.
     */
    @Override
    public void flipHorizontally() {
        image = super.flip(image, FlipDirection.HORIZONTALLY);
    }

    /**
     * Flips the animation horizontally.
     */
    @Override
    public void flipVertically() {
        image = super.flip(image, FlipDirection.VERTICALLY);
    }

    /**
     * Sets a fixed size for the animation.
     * 
     * @param width The width of the animation.
     * @param height The height of the animation.
     */
    @Override
    public void setSize(int width, int height) {
        super.current = setSize(super.current, width, height);
        image = super.setSize(image, width, height);
    }
}
