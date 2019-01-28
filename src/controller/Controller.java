package controller;

import java.io.File;
import model.ModelInterface;

/**
 * Controller Class
 * 
 * @Primary_Author -
 * @Code_Reviewer - 
 * @External_Tester - 
 */
public class Controller implements ControllerInterface {

    /**
     * The game model that the controller calls methods on.
     */
    private ModelInterface model;

    /**
     * Creates a new controller.
     * @param model The game model that the controller calls methods on.
     */
    public Controller(ModelInterface model) {
        this.model = model;
    }

    /**
     * Starts the game model.
     */
    public void start() {
        this.model.setPaused(false);
    }

    /**
     * Pauses the game model.
     */
    public void pause() {
        this.model.setPaused(true);
    }

    /**
     * Saves the game.
     * @param fileName The name of the file to save the game as
     */
    public void save(String fileName) {
        this.model.save(fileName);
    }

    /**
     * Loads the game.
     * @param gameFile The file to load the game from
     */
    public void load(File gameFile) {
        this.model.load(gameFile);
    }

    /**
     * Sets the player as moving left.
     */
    public void setPlayerMovingLeft() {
        this.model.setPlayerMovingLeft();
    }

    /**
     * Sets the player as moving right.
     */
    public void setPlayerMovingRight() {
        this.model.setPlayerMovingRight();
    }

    /**
     * Sets the left key as released.
     */
    public void keyLeftRelease() {
        this.model.keyLeftRelease();
    }

    /**
     * Sets the right key as released.
     */
    public void keyRightRelease() {
        this.model.keyRightRelease();
    }

    /**
     * Inverts the gravity.
     */
    public void invertGravity() {
        this.model.invertGravity();
    }
    
    /**
     * Sets the level in the model
     * @param level The number of the level
     */
    public void setLevel(int level) {
    	this.model.setLevel(level);
    }
    
    /**
     * Respawns the player at the last checkpoint
     */
    public void respawn() {
    	this.model.respawn();
    }
}
