package controller;

import java.io.File;
import java.io.IOException;

public interface ControllerInterface {

    /**
     * Starts the game model.
     */
    void start();

    /**
     * Pauses the game model.
     */
    void pause();

    /**
     * Saves the game.
     * @param fileName The name of the file to save the game as
     */
    void save(String fileName);

    /**
     * Loads the game.
     * @param gameFile The file to load the game from
     *
     * @throws IOException - Loading Failed.
     * @throws ClassNotFoundException - Savable Object Mismatch.
     */
    void load(File gameFile);

    /**
     * Sets the player as moving left.
     */
    void setPlayerMovingLeft();

    /**
     * Sets the player as moving right.
     */
    void setPlayerMovingRight();

    /**
     * Sets the left key as released.
     */
    void keyLeftRelease();

    /**
     * Sets the right key as released.
     */
    void keyRightRelease();

    /**
     * Inverts the gravity.
     */
    void invertGravity();

    /**
     * Sets the level in the model
     * @param level The number of the level
     */
    void setLevel(int level);
    
    /**
     * Respawns the player at the last checkpoint
     */
    void respawn();

}
