package animationLibrary;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Scrolling Animation
 *
 * @Primary_Author - Leyton Blackler (300368625).
 * @Code_Reviewer - Riley Blair (300371586).
 * @External_Tester - Celine Young (300378246).
 */
public class ScrollingAnimation extends Animation {

    /**
     * The possible directions to scroll the image in.
     */
    public enum Direction {
        HORIZONTAL,
        VERTICAL
    }

    /**
     * The direction to scroll the image in.
     */
    private Direction direction;

    /**
     * The image that is to be scrolled.
     */
    private Image image;

    /**
     * The amount to scroll the image by for each frame.
     */
    private double shift;

    private double x = 0;

    private double y  = 0;

    /**
     * Creates a new animation where an image is rotated.
     * @param image The image to use in the animation.
     * @param period The period of rotation for the image (e.g. period = 1 will take 1 second for a full rotation).
     * @param fps The number of frames per second to animate.
     */
    public ScrollingAnimation(Image image, Direction direction, int period, int fps) {
        //Calculate and set the interval required to achieved the defined period of rotation.
        super(1000 / fps);
        //Set the initial image.
        super.current = image;
        //Calculate the position shift for each frame in the animation.
        if (direction == Direction.HORIZONTAL) {
            shift = ((double) image.getHeight(null)) / ((double) (period * fps));
        } else if (direction == Direction.VERTICAL) {
            shift = ((double) image.getWidth(null)) / ((double) (period * fps));
        }
        //Set the image.
        this.image = image;
        //Set the direction.
        this.direction = direction;
    }

    /**
     * Rotates the image by the calculated angle step.
     * @return The current frame in the animation after it has been animated.
     */
    @Override
    protected Image animate() {
        //Create a new image to display the rotated image.
        BufferedImage moved = new BufferedImage(this.image.getWidth(null), this.image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2D = (Graphics2D) moved.getGraphics();
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        if (direction == Direction.HORIZONTAL) {
            //Draw the image to the left.
            g2D.drawImage(image, (int) (x - image.getWidth(null)), (int) y, null);
        } else if (direction == Direction.VERTICAL) {
            //Draw the image above.
            g2D.drawImage(image, (int) x, (int) (y - image.getHeight(null)), null);
        }

        //Draw the image at x and y.
        g2D.drawImage(image, (int) x, (int) y, null);

        if (direction == Direction.HORIZONTAL) {
            //Increase the horizontal position.
            x += shift;
            //Reset horizontal position to 0 if it has scrolled to the end.
            if (x > image.getWidth(null)) x = 0;
        } else if (direction == Direction.VERTICAL) {
            //Increase the vertical position.
            y += shift;
            //Reset vertical position to 0 if it has scrolled to the end.
            if (y > image.getWidth(null)) y = 0;
        }
        return moved;
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
     * @param width The width of the animation.
     * @param height The height of the animation.
     */
    @Override
    public void setSize(int width, int height) {
        super.current = setSize(super.current, width, height);
        image = super.setSize(image, width, height);
    }
}
