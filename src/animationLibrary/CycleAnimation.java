package animationLibrary;

import java.awt.*;

/**
 * Cycle Animation
 *
 * @Primary_Author - Leyton Blackler (300368625).
 * @Code_Reviewer - Riley Blair (300371586).
 * @External_Tester - Celine Young (300378246).
 */
public class CycleAnimation extends Animation {

    /**
     * The image to cycle through.
     */
    private Image[] images;

    /**
     * The index of the current image.
     */
    private int current = 0;

    /**
     * Creates a new animation where a set of images are cycled between.
     * 
     * @param interval The interval to wait between each image.
     * @param images The images to cycle through.
     */
    public CycleAnimation(int interval, Image[] images) {
        super(interval);
        //Set the initial image.
        super.current = images[0];
        this.images = images;
    }

    /**
     * Rotates the image by the calculated angle step.
     * 
     * @return The current frame in the animation after it has been animated.
     */
    @Override
    protected Image animate() {
        if (current >= images.length) current = 0;
        return this.images[current++];
    }

    /**
     * Flips the animation horizontally.
     */
    @Override
    public void flipHorizontally() {
        for (int i = 0; i < images.length; i++) {
            images[i] = super.flip(images[i], FlipDirection.HORIZONTALLY);
        }
    }

    /**
     * Flips the animation horizontally.
     */
    @Override
    public void flipVertically() {
        for (int i = 0; i < images.length; i++) {
            images[i] = super.flip(images[i], FlipDirection.VERTICALLY);
        }
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
        for (int i = 0; i < images.length; i++) {
            images[i] = super.setSize(images[i], width, height);
        }
    }
}
