package model;

import controller.Controller;
import entityLibrary.BoundBox;
import entityLibrary.NPC;
import entityLibrary.Player;
import guiLibrary.GUI;
import guiLibrary.GUIConstants;
import levelLibrary.Block;
import levelLibrary.BlockType;
import levelLibrary.Level;
import levelLibrary.LevelType;
import saveLoadLibrary.LoadGame;
import saveLoadLibrary.Savable;
import saveLoadLibrary.SaveGame;
import view.View;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.*;
import javax.swing.Timer;

import static levelLibrary.LevelType.COTTON;
import static levelLibrary.LevelType.CUBA;
import static levelLibrary.LevelType.KELBURN;

/**
 * Model Class
 *
 * @Primary_Author -
 * @Code_Reviewer -
 * @External_Tester -
 */
@SuppressWarnings("unused")
public class Model extends Observable implements ModelInterface {

	/**
     * Whether the instance of the game is still running.
     * (Setting to false permanently stop the model from operating.)
     */
	private boolean running = true;

    /**
     * Whether the instance of the game has been paused.
     * (Setting to true temporarily stops the the model from operating.)
     */
	private volatile boolean paused = false;

    /**
     * The different generated levels used in the game.
     */
    private Level[] levels = new Level[3];

    /**
     * The level current being simulated by the game model.
     */
    private Level currentLevel;
    
    /**
     * The last checkpoint the player has passed
     */
    private CheckpointTuple currentCheckpoint;

    /**
     * The number of coins the player currently has.
     */
    private int coinCount = 0;

    /**
     * Determines the current direction of the gravity.
     */
    private boolean gravityDown = false;
    
    /**
     * The game timer
     */
    private Timer timer = new Timer(1000 / 60, new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			tick();
		}
	});

    /**
     * The current instances of NPCs in the level.
     */
	private List<NPC> npcs = new ArrayList<>();
	
	/**
     * The original instances of NPCs in the level.
     */
	private List<NPC> originalNpcs = new ArrayList<>();
	
	/**
	 * The list of spawn points that NPCs can spawn at
	 */
	private List<List<Integer>> spawnPoints;

    /**
     * The instance of the player controller by the user.
     */
	private Player player;

	/**
	 * Whether the game has been won or not
	 */
	private boolean gameWon = false;

    /**
     * The time in which the player must complete the level before it's game over.
     */
	private long levelTimeUp;

	/**
	 * The time for the next battery decrease
	 */
	private long timeForNextBatteryDecrease;

	/**
	 * The remaining percentage of the battery
	 */
    private int batteryPercent = 100;
    
    /**
     * Whether the player is dead
     */
    private boolean isDead = false;
    
    public static final int BLOCK_SIZE = 67;

    /**
     * Creates a new instance of the game model.
     */
    public Model() {
        //Generate the Cuba Street level.
        levels[0] = new Level(300, CUBA);
        //Unlocks first level
        levels[0].setLevelLocked(false);
        
        //Generate the outdoors level.
        levels[1] = new Level(400, KELBURN);

        //Generate the Cotton building level.
        levels[2] = new Level(500, COTTON);
        
        //Set the starting level as level 1.
        setLevel(1);
    }

    /**
     * The main game loop.
     */
    public void run() {
    	timer.start();

    	//Run the garbage collector once a second.
    	Timer gcTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.gc();
            }
        });
    	gcTimer.start();
    }
    
    /**
     * Gets called every tick of the game timer and updates the physics of all of 
     * the entities
     */
    private void tick() {
    	if (isGameOver()) return;
    	player.getPhysics().updatePos();
    	player.setBoundingBox();
    	
    	long nextFrame = System.currentTimeMillis();
    	
    	for(NPC npc : npcs) {
    		
    		npc.moveHorizontal();
    		
    		//Every second that passes, the NPC will change direction regardless of colliding with a wall
    		if (nextFrame < System.currentTimeMillis()){
    			npc.flipDirection();
    			continue;
    		}
    		
    		//Check if the Player is colliding with an NPC
    		checkPlayerCollisionWithNPC(npc);
    		
    	}

		nextFrame += 1000 / 10;
    	
		//Check if the player is colliding with an item.
        checkPlayerCollisionWithItems();

    	if (timeForNextBatteryDecrease < System.currentTimeMillis()) {
    	    batteryPercent--;
    	    timeForNextBatteryDecrease = System.currentTimeMillis() + 200;
        }
    }
    
    /**
     * Checks whether the player is colliding with an npc
     * 
     * @param npc The npc to check against
     */
    
    public void checkPlayerCollisionWithNPC(NPC npc) {
		if(npc.getBoundingBox().intersects(player.getBoundingBox())) { isDead = true; }
    }
    
    /**
     * Checks whether the player is colliding with an item
     */
    
    public void checkPlayerCollisionWithItems() {
        BoundBox playerBounds = player.getBoundingBox();
        List<List<Block>> layout = this.currentLevel.getLevelLayout();
        // position of the player
        Point playerPoint = player.getPos();
        
        int leftCol = (playerPoint.x / BLOCK_SIZE) - 1;
        int rightCol = leftCol + 2;

        if (leftCol < 0) leftCol = 0;
        if (rightCol > layout.size()) rightCol = layout.size();
        // only checks the blocks relative to the players position
        for (int col = leftCol; col <= rightCol; col++) {
        	for (int row = 0; row < layout.get(0).size(); row++) {
        		Block block = layout.get(col).get(row);
        		if (block == null) continue;
        		BlockType type = block.getBlockType();
        		double x = col * BLOCK_SIZE;
        		double y = row * BLOCK_SIZE;
        		// processes the item depending on its block type
        		if (type == BlockType.BONUS || type == BlockType.BATTERY || type == BlockType.CHECKPOINT || type == BlockType.LEVELEND) {
        			x += 15;
        			y += 15;
        			BoundBox itemBounds = new BoundBox(x, y, BLOCK_SIZE - 30, BLOCK_SIZE - 30);
        			if (playerBounds.intersects(itemBounds)) {
        				if (type == BlockType.BONUS) {
        					layout.get(col).set(row, null);
        					coinCount++;
        				} else if (type == BlockType.BATTERY) {
        					layout.get(col).set(row, null);
        					batteryPercent += 20;
        				// update checkpoint field
        				} else if (type == BlockType.CHECKPOINT) {
        					Block checkpoint = layout.get(col).get(row);
        					if (!checkpoint.getVisited()) {
        						currentCheckpoint = new CheckpointTuple(player.getPos(), coinCount, batteryPercent, getLevelTimeUp(), getTimeForNextBatteryDecrease(), currentLevel.clone());
        						layout.get(col).get(row).setVisited(true);
        					}
        				// switch to next level if at the end of a level
        				} else if (type == BlockType.LEVELEND) {
        					int levelNumber = getCurrentLevelNumber();
        					if (levelNumber == 3) { this.gameWon = true; }
        					else if (levelNumber == 2 ) {
        						levels[2].setLevelLocked(false);
        						setLevel(3); 
        					}
        					else if (levelNumber == 1 ) { 
        						levels[1].setLevelLocked(false);
        						setLevel(2);
        					}
        				}
        			}
        		}
        	}
        }
    }
    
    /**
     * Respawns the player at the last checkpoint
     */
    public void respawn() {
    	//Allows the game to run
    	isDead = false;
    	//Set the gravity as downwards.
        gravityDown = true;
        //Reverts all of the fields back
    	setLevelTimeUp(currentCheckpoint.getTimeRemaining());
    	setTimeForNextBatteryDecrease(currentCheckpoint.getNextBatteryDec());
    	batteryPercent = currentCheckpoint.getBatteryPercent(); 
    	coinCount = currentCheckpoint.getCoinCount();
    	currentLevel = currentCheckpoint.getCurrentLevel();
    	
    	npcs = originalNpcs;
    	// resets the gravity for all npcs
    	for (NPC npc : npcs) {
        	npc.getPhysics().setGravityDown(true);
        }
    	
    	//Reset player position
    	player = new Player((int) currentCheckpoint.getPlayerPos().getX(), (int) currentCheckpoint.getPlayerPos().getY(), currentLevel);
    }
    
    /**
     * Return if the game is won
     * @return Boolean
     */  
    public boolean isGameWon() {
        return gameWon;
    }
    
    /**
     * Sets if the game is won
     * @param gameWon
     */
    public void setGameWon(boolean gameWon) {
        this.gameWon = gameWon;
    }
    
    /**
     * Sets if the game is won
     * @param gameWon
     */
    public boolean isGameOver() {
        return (batteryPercent <= 0 || System.currentTimeMillis() > levelTimeUp || isDead);
    }
    
    /**
     * Returns the time remaining in a string format
     * @return String
     */
    public String getTimeRemaining() {
        long millisecondsLeft = levelTimeUp - System.currentTimeMillis();
        if (millisecondsLeft < 0) return "00:00";
        String time = "";
        int minutes = (int) Math.floor(millisecondsLeft / (1000 * 60));
        if (minutes < 10) time += "0";
        time += minutes + ":";
        int seconds = (int) (millisecondsLeft / 1000);
        while (seconds >= 60) seconds -= 60;
        if (seconds < 10) time += "0";
        time += seconds;
        return time;
    }
    
    /**
     * Sets the level of the game
     * @param level The number of the level to be set as
     */
    public void setLevel(int level) {
    	// restarts the game 
    	setGameWon(false);
    	
        //Set the current level.
        currentLevel = levels[level - 1].clone();

        //Set the time the player has to complete the level.
        levelTimeUp = System.currentTimeMillis() + (60 + ((level - 1) * 20)) * 1000;
        
        //Get the spawn points for the NPCs
        spawnPoints = currentLevel.getSpawnpoints();

        //Create a new instance of the player at the start.
        player = new Player((int) (BLOCK_SIZE * 1.5), (int) (BLOCK_SIZE * 7.5), currentLevel);

        //Set the battery as full.
        batteryPercent = 100;

        timeForNextBatteryDecrease = System.currentTimeMillis();

        //Set the gravity as downwards.
        gravityDown = true;

        //Set the coin count to 0.
        coinCount = 0;

        //Generate NPCs.
        npcs.clear();
        
        //Set Player being Dead to false
        isDead = false;
                
        for (int i = 0; i < spawnPoints.size(); i++) {
            npcs.add(new NPC(spawnPoints.get(i).get(0) * BLOCK_SIZE, spawnPoints.get(i).get(1) * BLOCK_SIZE, spawnPoints.get(i).get(0) * BLOCK_SIZE, spawnPoints.get(i).get(0) * BLOCK_SIZE + (BLOCK_SIZE * (3)), currentLevel));
        }
        
        // creates a cloned arraylist of the npcs at their start position
        originalNpcs = new ArrayList<>();
        
        for (NPC npc : npcs) {
        	originalNpcs.add(npc.clone());
        }
        
        //Set the currentCheckpoint
        currentCheckpoint = new CheckpointTuple(player.getPos(), coinCount, batteryPercent, getLevelTimeUp(), getTimeForNextBatteryDecrease(), currentLevel.clone());
    }

    /**
     * Sets the whether the game is currently paused.
     *
     * @param paused Whether the game is currently paused.
     */
    public void setPaused(boolean paused) {
        this.paused = paused;
        if(paused) {
        	timer.stop();
        }else {
        	timer.start();
        	player.getPhysics().resetTime();
        	for(NPC npc : npcs) {
        		npc.getPhysics().resetTime();
        	}
        }
    }

    /**
     * Saves the game.
     * 
     * @param fileName The name of the file the game will be saved in
     */
    public void save(String fileName) {
    	// clones the levels
    	Level[] clonedLevels = { levels[0].clone(), levels[1].clone(), levels[2].clone() };
		currentCheckpoint.setLevels(clonedLevels);
		// clones the npcs
		List<NPC> clonedNpcs = new ArrayList<NPC>();
		for (NPC npc : originalNpcs) {
			clonedNpcs.add(npc.clone());
		}
		currentCheckpoint.setNpcs(clonedNpcs);
    	try {
    		SaveGame.save(currentCheckpoint, fileName);
    	} catch (IOException e) { e.printStackTrace(); }
    }

    /**
     * Loads the game.
     * @param gameFile The file to load the game from.
     * 
	 * @throws IOException - Loading Failed.
	 * @throws ClassNotFoundException - Savable Object Mismatch.
     */
    public void load(File gameFile) {
        try {
            CheckpointTuple savedGame = (CheckpointTuple) LoadGame.load(gameFile);
            if (savedGame == null) {
                return;
            }
            // sets currentCheckpoint back to saved game
            currentCheckpoint = savedGame;
            levels = currentCheckpoint.getLevels();
            npcs = currentCheckpoint.getNpcs();
            // resets the fields from the saved checkpoint
            respawn();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets the player as moving left.
     */
    public void setPlayerMovingLeft() {
        player.getPhysics().pressLeft();
    }

    /**
     * Sets the player as moving left.
     */
    public void setPlayerMovingRight() {
    	player.getPhysics().pressRight();
    }

    /**
     * Sets the left key as released.
     */
    public void keyLeftRelease() {
    	player.getPhysics().releaseLeft();
    }

    /**
     * Sets the right key as released.
     */
    public void keyRightRelease() {
        player.getPhysics().releaseRight();
    }

    /**
     * Moves the player right.
     */
    public void invertGravity() {
        //TODO: Invert the gravity.
        gravityDown = !gravityDown;
    	player.getPhysics().switchGravity();
    	for(NPC npc : npcs) {
    		npc.getPhysics().switchGravity();
    	}
    }
    
    /**
     * Returns whether the gravity is down
     * @return Booleans
     */
    public boolean isGravityDown() {
        return gravityDown;
    }

    /**
     * Gets the current player.
     * @return The current player.
     */
    public Player getPlayer() {
        return this.player;
    }

    /**
     * Gets the current level being simulated in the game.
     * @return The current level being simulated in the game.
     */
    public Level getCurrentLevel() {
        if (this.currentLevel == null) throw new Error("No current level exists.");
        return this.currentLevel;
    }

    /**
     * Set the current level being simulated in the game.
     */
    public void setCurrentLevel(Level currentLevel) {
        this.currentLevel = currentLevel;
    }

    /**
     * Gets the amount of battery the player currently has as a percentage.
     * @return The amount of battery the player currently has as a percentage.
     */
    public int getCurrentBatteryPercent() {
        return batteryPercent;
    }
    
    /**
     * Sets the amount of battery the player has as a percentage.
     */
    public void setBatteryPercent(int batteryPercent) {
        this.batteryPercent = batteryPercent;
    }
    
    /**
     * Gets the current checkpoint being simulated in the game.
     * @return The current checkpoint being simulated in the game.
     */
    public CheckpointTuple getCurrentCheckpoint() {
        if (this.currentCheckpoint == null) throw new Error("No current checkpoint exists.");
        return this.currentCheckpoint;
    }
    
    /**
     * Sets the current checkpoint being simulated in the game.
     * @param currentCheckpoint The current checkpoint to be set to.
     */
    public void setCurrentCheckpoint(CheckpointTuple currentCheckpoint) {
        this.currentCheckpoint = currentCheckpoint;
    }

    /**
     * Gets the NPCs currently in the level.
     * @return The NPCs currently in the level.
     */
    public List<NPC> getNPCs() {
        return npcs;
    }
    
    /**
     * Set the array of levels
     * @param levels The array of levels
     */
    public void setLevels(Level[] levels) {
    	this.levels = levels;
    }
    
    /**
     * Gets the levels in the game
     * @return The array of levels
     */
    public Level[] getLevels() {
    	return this.levels;
    }
    
    /**
     * Sets when the time to complete the level is up relative to the system time
     * @param levelTimeUp 
     */
    
    public void setLevelTimeUp(long levelTimeUp) {
    	this.levelTimeUp = System.currentTimeMillis() + levelTimeUp;
    }
    
    /**
     * Returns when the time to complete the level is up relative to the system time
     * @return long
     */
    
    public long getLevelTimeUp() {
    	return this.levelTimeUp - System.currentTimeMillis();
    }
    
    /**
     * Deep clones the current game by cloning all fields 
     * @return deepCloned model
     */
    
    /**
     * Sets when the time till next battery decrease is relative to the system time
     * @param timeForNextBatteryDecrease
     */
    
    public void setTimeForNextBatteryDecrease(long timeForNextBatteryDecrease) {
    	this.timeForNextBatteryDecrease = System.currentTimeMillis() + timeForNextBatteryDecrease;
    }
    
    /**
     * Returns when the time till next battery decrease is relative to the system time
     * @return long 
     */
    
    public long getTimeForNextBatteryDecrease() {
    	return this.timeForNextBatteryDecrease - System.currentTimeMillis();
    }
    
    /**
     * Returns the number of the current level
     * @return int
     */
    public int getCurrentLevelNumber() {
        if (currentLevel.getLevelType() == CUBA) return 1;
        else if (currentLevel.getLevelType() == KELBURN) return 2;
        else if (currentLevel.getLevelType() == COTTON) return 3;
        return -1;
    }
    
    /**
     * Returns the number of coins the player has
     * @return int
     */
    @Override
    public int getCoins() {
        return coinCount;
    }

    public static void main(String[] args) {
        //Set the resolution if arguments are provided, will default to 720p if no arguments are given.
        if (args.length >= 1) {
            if (args[0].equalsIgnoreCase("-480p")) GUIConstants.setResolution(GUIConstants.Resolution.RESOLUTION_480P);
            else if (args[0].equalsIgnoreCase("-720p"))
                GUIConstants.setResolution(GUIConstants.Resolution.RESOLUTION_720P);
            else if (args[0].equalsIgnoreCase("-1080p"))
                GUIConstants.setResolution(GUIConstants.Resolution.RESOLUTION_1080P);
            else if (args[0].equalsIgnoreCase("-4k")) GUIConstants.setResolution(GUIConstants.Resolution.RESOLUTION_4K);
            if (args.length == 2) {
                if (args[1].equalsIgnoreCase("-f")) GUIConstants.FULLSCREEN = true;
            }
        }

        //Set up the MVC structure.
        Model model = new Model();
        Controller controller = new Controller(model);
        new View(model, controller);
        model.run();
    }
}