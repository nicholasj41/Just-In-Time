package levelLibrary;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Level class representing a two-dimensional list of blocks that form the world.
 * 
 * @Primary_Author - Riley Blair (300371586).
 * @Code_Reviewer - Nicholas Jones (300384025).
 * @External_Tester - Finn Welsford-Ackroyd (300379304).
 */
public class Level implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<List<Block>> layout;
	private int levelSize;
	private final int FLATSPACESIZE = 9;
	private boolean levelLocked = true;
	private LevelType levelType;
	
	/**
	 * Empty constructor for cloning.
	 */
	public Level() { }
	
	/**
	 * Constructor for a new level that is a random two-dimensional list of segments.
	 * 
	 * @param levelSize - The size of the level.
	 */
	public Level(int levelSize) {
		this.levelSize = levelSize - (FLATSPACESIZE * 2) - 2;
		// Set default level type to be Cuba.
		this.levelType = LevelType.CUBA;
		layout = createLevel();
	}
	
	/**
	 * Constructor for a new level that is a random two-dimensional list of segments with a level type.
	 * 
	 * @param levelSize - The size of the level.
	 * @param levelType - The level type to decide which graphics to use.
	 */
	public Level(int levelSize, LevelType levelType) {
		this.levelSize = levelSize - (FLATSPACESIZE * 2) - 2;
		this.levelType = levelType;
		layout = createLevel();
	}
	
	/**
	 * Constructor for a new level with a given layout.
	 * 
	 * @param layout - The specified layout of the level.
	 */
	public Level(List<List<Block>> layout) {
		this.levelSize = layout.size();
		this.levelType = LevelType.CUBA;
		this.layout = layout;
	}
	
	/**
	 * Gets the exact locations of where the NPCs should spawn.
	 * This is the closest ground position below the spawnpoint.
	 * 
	 * @return A 2D list of x & y coordinates for the spawn locations.
	 */
	public List<List<Integer>> getSpawnpoints() {
		List<List<Integer>> spawnpoints = new ArrayList<List<Integer>>();
		int count = 0;
		for (int x = 0; x < layout.size(); x++) {
			List<Block> segment = layout.get(x);
			// Loop through the segment to see if there is a block that is a spawnpoint.
			for (int y = 0; y < segment.size(); y++) {
				if (segment.get(y) != null) {
					if (segment.get(y).getBlockType() == BlockType.SPAWNPOINT) {
						// Add the x coordinate to the list of coordinates.
						spawnpoints.add(new ArrayList<Integer>());
						spawnpoints.get(count).add(0, x);
						for (int index = y+1; index < segment.size(); index++) {
							if (segment.get(index) != null) {
								if (segment.get(index).getBlockType() == BlockType.GROUND) {
									// Add the position before the closest ground position as the spawn location (y coordinate).
									spawnpoints.get(count).add(1, index-1);
									break;
								}
							}
						} 
						count++;
						break;
					}
				}
			}
		}
		return spawnpoints;
	}
	
	/**
	 * Creates the two-dimensional representation of the level.
	 * 
	 * @return A two-dimensional list of blocks that make up the side-scrolling level.
	 */
	private List<List<Block>> createLevel() {
		List<List<Block>> grid = new ArrayList<List<Block>>();
		int count = 0;
		
		// Adds flat space at the start of the level so there is a predictable space for the player to start on.
		addWall(grid);
		addFlatSpace(grid, false);
		while (count < levelSize) {
			// Selects a random segment that is to be replicated to form part of the level.
			int randomSegment = (int) (Math.random()*10);
			
			// Selects a random value within a range for the amount of times the segment should be repeated for.
			int random = ThreadLocalRandom.current().nextInt(5, 8 + 1);
			for (int i = 0; i < random; i++) {
				List<Block> seg = Segment.valueOf("SEGMENT" + randomSegment).getSegment();
				List<Block> segment = new ArrayList<Block>();
				for (Block block : seg) {
					segment.add(block);
				}
				// Add bonuses randomly within a continuous block of segments.				
				if (ThreadLocalRandom.current().nextBoolean()) {
					addBonus(segment);
				}
				grid.add(segment);
				count++;
				// Ensures that a level never exceeds its maximum size.
				if (count >= levelSize) { break; }
			}
		}
		// Adds flat space at the end of the level so there is a predictable space for the player to end at.
		addFlatSpace(grid, true);
		addWall(grid);
		addBatteries(grid);
		addCheckpoints(grid);
		addSpawnpoints(grid);
		return grid;
	}
	
	/**
	 * Adds a bonus randomly within a segment.
	 * 
	 * @param segment - The segment to add the bonus to.
	 */
	private void addBonus(List<Block> segment) {
		List<Integer> possibleBonusLocations = new ArrayList<Integer>();
		
		// Find all the valid spaces within a segment for placing a bonus.
		for (int index = 0; index < segment.size(); index++) {
			if (segment.get(index) == null) {
				possibleBonusLocations.add(index);
			}
		}
		// Place a bonus randomly in one of the valid segment spaces.
		if (possibleBonusLocations.size() > 0) {
			int randomLocation = ThreadLocalRandom.current().nextInt(0, possibleBonusLocations.size());
			segment.set(possibleBonusLocations.get(randomLocation), new Block(BlockType.BONUS));
		}
	}
	
	/**
	 * Adds a battery every 20 blocks along the level.
	 * 
	 * @param grid - The grid to add the batteries to.
	 */
	private void addBatteries(List<List<Block>> grid) {
		for (int index = 0; index < levelSize; index += 20) {
			List<Block> segment = grid.get(index);
			List<Integer> possibleBatteryLocations = new ArrayList<Integer>();
			
			// Find all the valid spaces within a segment for placing a battery.
			for (int i = 0; i < segment.size(); i++) {
				if (segment.get(i) == null) {
					possibleBatteryLocations.add(i);
				}
			}
			// Place a battery randomly in one of the valid segment spaces.
			if (possibleBatteryLocations.size() > 0) {
				int randomLocation = ThreadLocalRandom.current().nextInt(0, possibleBatteryLocations.size());
				segment.set(possibleBatteryLocations.get(randomLocation), new Block(BlockType.BATTERY));
			}
		}
	}
	
	/**
	 * Adds a checkpoint every 30 blocks along the level.
	 * 
	 * @param grid - The grid to add the checkpoints to.
	 */
	private void addCheckpoints(List<List<Block>> grid) {
		for (int index = 0; index < levelSize; index += 30) {
			List<Block> segment = grid.get(index);
			List<Integer> possibleCheckpointLocations = new ArrayList<Integer>();
			
			// Find all the valid spaces within a segment for placing a checkpoint.
			for (int i = 0; i < segment.size(); i++) {
				if (segment.get(i) == null) {
					possibleCheckpointLocations.add(i);
				}
			}
			// Place a checkpoint randomly in one of the valid segment spaces.
			if (possibleCheckpointLocations.size() > 0) {
				int randomLocation = ThreadLocalRandom.current().nextInt(0, possibleCheckpointLocations.size());
				segment.set(possibleCheckpointLocations.get(randomLocation), new Block(BlockType.CHECKPOINT));
			}
		}
	}
	
	/**
	 * Adds a spawnpoint every 15 blocks along the level.
	 * 
	 * @param grid - The grid to add the checkpoints to.
	 */
	private void addSpawnpoints(List<List<Block>> grid) {
		for (int index = 0; index < levelSize; index += 15) {
			List<Block> segment = grid.get(index);
			List<Integer> possibleSpawnpointLocations = new ArrayList<Integer>();
			boolean spawnpointBlocked = false;
			
			// Find all the valid spaces within a segment for placing a spawnpoint.
			for (int i = 0; i < segment.size(); i++) {
				if (segment.get(i) == null) {
					possibleSpawnpointLocations.add(i);
				}
				else if (segment.get(i).getBlockType() == BlockType.CHECKPOINT) {
					spawnpointBlocked = true;
				}
			}
			if (possibleSpawnpointLocations.size() > 0 && !spawnpointBlocked) {
				int randomLocation = ThreadLocalRandom.current().nextInt(0, possibleSpawnpointLocations.size());
				segment.set(possibleSpawnpointLocations.get(randomLocation), new Block(BlockType.SPAWNPOINT));
			}
		}
	}
	
	/**
	 * Adds flat space to the grid at the point it is called.
	 * This allows for expected terrain to be added at particular points.
	 * 
	 * @param grid - The grid to add the flat space to.
	 * @param atEnd - Whether or not the flat space is at the end of the level.
	 */
	private void addFlatSpace(List<List<Block>> grid, boolean atEnd) {
		for (int i = 0; i < FLATSPACESIZE; i++) {
			// Add segment 0 as this is a predictable segment layout and will be easy for the player to start and finish on.
			List<Block> seg = Segment.valueOf("SEGMENT0").getSegment();
			List<Block> segment = new ArrayList<Block>();
			for (Block block : seg) {
				segment.add(block);
			}
			if (atEnd && i == (int)(FLATSPACESIZE/2)) {
				segment.set(7, new Block(BlockType.LEVELEND));
			}
			grid.add(segment);
		}
	}
	
	/**
	 * Adds a wall to the grid at the point it is called.
	 * This allows for expected terrain to be added at particular points.
	 * 
	 * @param grid - The grid to add the wall to.
	 */
	private void addWall(List<List<Block>> grid) {		
		List<Block> wall = new ArrayList<Block>();
		for (int i = 0; i < 10; i++) {
			wall.add(new Block(BlockType.GROUND));
		}
		grid.add(wall);
	}
	
	/**
	 * Deep clone method for the level.
	 * 
	 * @return A deep cloned version of the current level.
	 */
	public Level clone() {
		List<List<Block>> clonedLayout = new ArrayList<List<Block>>();
		
		// Iterate through the level layout and deep clone the entire layout to a new arraylist.
		for (int i = 0; i < layout.size(); i++) {
			clonedLayout.add(new ArrayList<Block>());
			for (int j = 0; j < layout.get(i).size(); j++) {
				clonedLayout.get(i).add(layout.get(i).get(j) != null ? new Block(layout.get(i).get(j).getBlockType()) : null);
			}
		}
		
		Level clonedLevel = new Level(clonedLayout);
		
		// Set all the variables of the current level
		clonedLevel.setLevelSize(levelSize);
		clonedLevel.setLevelType(levelType);
		clonedLevel.setLevelLocked(levelLocked);
		
		return clonedLevel;
	}
	
	/**
	 * Get method for the type of level being represented.
	 * 
	 * @return The type of level.
	 */
	public LevelType getLevelType() {
		return levelType;
	}
	
	/**
	 * Set method for the type of level.
	 * 
	 * @param levelType - The type of level.
	 */
	public void setLevelType(LevelType levelType) {
		this.levelType = levelType;
	}
	
	/**
	 * Get method for the layout of the level.
	 * 
	 * @return The layout of the level.
	 */
	public List<List<Block>> getLevelLayout() {
		return layout;
	}
	
	/**
	 * Set method for the size of a level.
	 * 
	 * @param levelSize - The size of the level.
	 */
	public void setLevelSize(int levelSize) {
		this.levelSize = levelSize;
	}
	
	/**
	 * Marks the level as unlocked so that it can be played.
	 */
	public void unlockLevel() {
		levelLocked = false;
	}
	
	/**
	 * Marks the level as locked so that it cannot be played.
	 */
	public void lockLevel() {
		levelLocked = true;
	}
	
	/**
	 * Getter for the lock status of the level.
	 * 
	 * @return Whether or not the level is locked.
	 */
	public boolean isLocked() {
		return levelLocked;
	}
	
	/**
	 * Set method for the lock status of a level.
	 * 
	 * @param levelLocked - Whether or not the level is locked.
	 */
	public void setLevelLocked(boolean levelLocked) {
		this.levelLocked = levelLocked;
	}
	
	/**
	 * Get method for the size of the level.
	 * 
	 * @return The size of the level.
	 */
	public int getLevelSize() {
		return levelSize;
	}
}
