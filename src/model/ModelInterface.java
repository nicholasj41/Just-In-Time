package model;

import entityLibrary.NPC;
import entityLibrary.Player;
import levelLibrary.Level;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface ModelInterface {

    void run();

    void setPaused(boolean state);

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
     * Gets the current level being simulated in the game.
     * @return The current level being simulated in the game.
     */
    Level getCurrentLevel();
    
    /**
     * Gets the current player.
     * @return The current player.
     */
    Player getPlayer();
    
    /**
     * Returns whether the gravity is down
     * @return Booleans
     */
    boolean isGravityDown();
    
    /**
     * Returns the list of npcs for this level
     * @return List<NPC>
     */
    List<NPC> getNPCs();

    /**
     * Gets the levels in the game
     * @return The array of levels
     */
    Level[] getLevels();
    
    /**
     * Returns the time remaining in a string format
     * @return String
     */
    String getTimeRemaining();

    /**
     * Return if the game is won
     * @return Boolean
     */
    boolean isGameWon();

    /**
     * Sets if the game is won
     * @param gameWon
     */
    boolean isGameOver();
    
    /**
     * Gets the amount of battery the player currently has as a percentage.
     * @return The amount of battery the player currently has as a percentage.
     */
    int getCurrentBatteryPercent();
    
    /**
     * Returns the number of the current level
     * @return int
     */
    int getCurrentLevelNumber();
    
    /**
     * Returns the number of coins the player has
     * @return int
     */
    int getCoins();
    
    /**
     * Respawns the player at the last checkpoint
     */
	void respawn();

}
