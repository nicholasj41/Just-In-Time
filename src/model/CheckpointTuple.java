package model;

import java.awt.Point;
import java.util.List;

import entityLibrary.NPC;
import levelLibrary.Level;
import saveLoadLibrary.Savable;

/**
 * Tuple Class for storing information about the last checkpoint the player has passed
 */

public class CheckpointTuple implements Savable {

	private static final long serialVersionUID = 1L;
	private Point playerPos;
	private int coinCount;
	private long timeRemaining;
	private Level currentLevel;
	private long nextBatteryDec;
	private int batteryPercent;
	private List<NPC> npcs;
	private Level[] levels;
	
	public CheckpointTuple(Point playerPos, int coinCount, int batteryPercent, long timeRemaining, long nextBatteryDec, Level currentLevel) {
		this.playerPos = playerPos;
		this.coinCount = coinCount;
		this.batteryPercent = batteryPercent;
		this.timeRemaining = timeRemaining;
		this.nextBatteryDec = nextBatteryDec;
		this.currentLevel = currentLevel;
	}
	
	/**
	 * Returns the players current position
	 * @return
	 */
	public Point getPlayerPos() {
		return this.playerPos;
	}
	
	/**
     * Returns the number of coins the player has
     * @return int
     */
	public int getCoinCount() {
		return this.coinCount;
	}
	
	/**
     * Gets the amount of battery the player currently has as a percentage.
     * @return int
     */
	public int getBatteryPercent() {
		return this.batteryPercent;
	}
	
	/**
     * Returns the time remaining to complete this level
     * @return long
     */
	public long getTimeRemaining() {
		return this.timeRemaining;
	}
	
	/**
	 * Return the current level
	 * @return Level
	 */
	public Level getCurrentLevel() {
		return this.currentLevel;
	}
	
	/**
     * Returns when the time till next battery decrease is
     * @return long 
     */
	public long getNextBatteryDec() {
		return this.nextBatteryDec;
	}
	
	/**
     * Sets the NPCs currently in the level.
     * @param npcs The NPCs to be set as
     */
	public void setNpcs(List<NPC> npcs) {
		this.npcs = npcs;
	}
	
	/**
     * Gets the NPCs currently in the level.
     * @return The NPCs currently in the level.
     */
	public List<NPC> getNpcs() {
		return this.npcs;
	}
	
	/**
	 * Sets the array of levels
	 * @param levels Updated array of levels
	 */
	public void setLevels(Level[] levels) {
		this.levels = levels;
	}
	
	/**
	 * Returns the array of levels
	 * @return Level[]
	 */
	public Level[] getLevels() {
		return this.levels;
	}
}
