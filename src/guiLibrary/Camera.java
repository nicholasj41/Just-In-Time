package guiLibrary;

/**
 * Camera Class
 *
 * @Primary_Author - Leyton Blackler (300368625).
 * @Code_Reviewer - Riley Blair (300371586).
 * @External_Tester - Celine Young (300378246).
 */
public class Camera {

    /**
     * The x displacement of the camera.
     */
    private int x = 0;

    /**
     * The y displacement of the camera.
     */
    private int y = 0;

    /**
     * Gets the current x displacement of the camera.
     * @return The current x displacement of the camera.
     */
    public int getX() {
        return this.x;
    }

    /**
     * Gets the current y displacement of the camera.
     * @return The current y displacement of the camera.
     */
    public int getY() {
        return this.y;
    }

    /**
     * Sets the current x displacement of the camera.
     * @param x The new x displacement of the camera.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Sets the current y displacement of the camera.
     * @param y The new y displacement of the camera.
     */
    public void setY(int y) {
        this.y = y;
    }

}
