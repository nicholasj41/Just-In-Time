package levelLibrary;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

/**
 * LevelTests Class
 * 
 * @Primary_Author - Riley Blair (300371586).
 * @Code_Reviewer - Nicholas Jones (300384025).
 * @External_Tester - Finn Welsford-Ackroyd (300379304).
 */
public class LevelTests {
	/**
	 * Tests to ensure that a level of a particular size is made at that size.
	 */
	@Test
    public void testValidLevelSize() {
		int levelSize = 300;
        Level level = new Level(levelSize);
        
        assertEquals(levelSize, level.getLevelLayout().size());
    }
	
	/**
	 * Tests to ensure that a level of a particular type is made of that type.
	 */
	@SuppressWarnings("static-access")
	@Test
    public void testValidLevelType() {
		int levelSize = 300;
        Level level = new Level(levelSize, LevelType.CUBA);
        
        assertEquals(LevelType.CUBA, level.getLevelType().valueOf("CUBA"));
    }
	
	/**
	 * Tests to ensure that a level without a type defaults to the Cuba type.
	 */
	@SuppressWarnings("static-access")
	@Test
    public void testDefaultLevelType() {
		int levelSize = 300;
        Level level = new Level(levelSize);
        
        assertEquals(LevelType.CUBA, level.getLevelType().valueOf("CUBA"));
    }
	
	/**
	 * Tests to ensure that a level is a specific height.
	 */
	@Test
    public void testValidSegmentSize() {
        Level level = new Level(300);
        
        assertEquals(10, level.getLevelLayout().get(0).size());
    }
	
	/**
	 * Tests that two levels are (almost) never identical.
	 */
	@Test
	public void testRandomisedLevels() {
		Level level1 = new Level(300);
		Level level2 = new Level(300);
		
		assertFalse(level1.getLevelLayout().equals(level2.getLevelLayout()));
	}
	
	/**
	 * Tests that a ground block generates with the correct type.
	 */
	@SuppressWarnings("static-access")
	@Test
	public void testGroundGeneration() {
		Block block = new Block(BlockType.GROUND);
		
		assertEquals(BlockType.GROUND, block.getBlockType().valueOf("GROUND"));
		assertTrue(block.getIsSolid());
	}
	
	/**
	 * Tests that a battery block generates with the correct type.
	 */
	@SuppressWarnings("static-access")
	@Test
	public void testBatteryGeneration() {
		Block block = new Block(BlockType.BATTERY);
		
		assertEquals(BlockType.BATTERY, block.getBlockType().valueOf("BATTERY"));
		assertFalse(block.getIsSolid());
	}
	
	/**
	 * Tests that a bonus block generates with the correct type.
	 */
	@SuppressWarnings("static-access")
	@Test
	public void testBonusGeneration() {
		Block block = new Block(BlockType.BONUS);
		
		assertEquals(BlockType.BONUS, block.getBlockType().valueOf("BONUS"));
		assertFalse(block.getIsSolid());
	}
	
	/**
	 * Tests that a checkpoint block generates with the correct type.
	 */
	@SuppressWarnings("static-access")
	@Test
	public void testCheckpointGeneration() {
		Block block = new Block(BlockType.CHECKPOINT);
		
		assertEquals(BlockType.CHECKPOINT, block.getBlockType().valueOf("CHECKPOINT"));
		assertFalse(block.getIsSolid());
	}
	
	/**
	 * Tests that a spawnpoint block generates with the correct type.
	 */
	@SuppressWarnings("static-access")
	@Test
	public void testSpawnpointGeneration() {
		Block block = new Block(BlockType.SPAWNPOINT);
		
		assertEquals(BlockType.SPAWNPOINT, block.getBlockType().valueOf("SPAWNPOINT"));
		assertFalse(block.getIsSolid());
	}
	
	/**
	 * Tests the correct spacing of checkpoints.
	 */
	@Test
	public void testCheckpointSpacing() {
		int levelSize = 300;
		Level level = new Level(levelSize);
		for (int i = 30; i < levelSize - 10; i += 30) {
			boolean foundCheckpoint = false;
			for (int j = 0; j < 10; j++) {
				if (level.getLevelLayout().get(i).get(j) != null) {
					if (level.getLevelLayout().get(i).get(j).getBlockType() == BlockType.CHECKPOINT) {
						foundCheckpoint = true;
					}
				}
			}
			assertTrue(foundCheckpoint);
		}
	}
	
	/**
	 * Tests the correct spacing of spawnpoints.
	 */
	@Test
	public void testSpawnpointSpacing() {
		int levelSize = 300;
		Level level = new Level(levelSize);
		for (int i = 15; i < levelSize - 15; i += 15) {
			boolean foundSpawnpoint = false;
			boolean foundCheckpoint = false;
			for (int j = 0; j < 10; j++) {
				if (level.getLevelLayout().get(i).get(j) != null) {
					if (level.getLevelLayout().get(i).get(j).getBlockType() == BlockType.SPAWNPOINT) {
						foundSpawnpoint = true;
					}
					if (level.getLevelLayout().get(i).get(j).getBlockType() == BlockType.CHECKPOINT) {
						foundCheckpoint = true;
					}
				}
			}
			if (!foundCheckpoint) {
				assertTrue(foundSpawnpoint);
			}
			else {
				assertFalse(foundSpawnpoint);
			}
		}
	}
	
	/**
	 * Tests the correct spacing of batteries.
	 */
	@Test
	public void testBatterySpacing() {
		int levelSize = 300;
		Level level = new Level(levelSize);
		for (int i = 20; i < levelSize - 20; i += 20) {
			boolean foundBattery = false;
			for (int j = 0; j < 10; j++) {
				if (level.getLevelLayout().get(i).get(j) != null) {
					if (level.getLevelLayout().get(i).get(j).getBlockType() == BlockType.BATTERY) {
						foundBattery = true;
					}
				}
			}
			assertTrue(foundBattery);
		}
	}
	
	/**
	 * Tests the locking of a level.
	 */
	@Test
	public void testLevelLock() {
		Level level = new Level(200);
		// level should be locked initially
		assertTrue(level.isLocked());
		level.unlockLevel();
		assertFalse(level.isLocked());
		level.lockLevel();
		assertTrue(level.isLocked());
	}
	
	/**
	 * Tests the spawnpoint locations API when there is a spawnpoint in a segment.
	 */
	@Test
	public void testVaildSpawnpointAPI() {
		List<Block> segment = new ArrayList<Block>(Arrays.asList(
			new Block(BlockType.GROUND),
			new Block(BlockType.GROUND),
			new Block(null),
			new Block(BlockType.SPAWNPOINT),
			new Block(null),
			new Block(null),
			new Block(null),
			new Block(null),		// This is the location (index 7) that the spawnpoint should be at, as it is the next block before the ground.
			new Block(BlockType.GROUND),
			new Block(BlockType.GROUND)
		));
		List<List<Block>> layout = new ArrayList<List<Block>>(Arrays.asList(segment));
		
		Level level = new Level(layout);
		List<List<Integer>> spawnpoints = level.getSpawnpoints();
		int x = spawnpoints.get(0).get(0);
		int y = spawnpoints.get(0).get(1);
		
		assertEquals(0, x);
		assertEquals(7, y);
	}
	
	/**
	 * Tests the spawnpoint locations API when there is a spawnpoint in multiple segments.
	 */
	@Test
	public void testMultipleVaildSpawnpointAPI() {
		List<Block> segment1 = new ArrayList<Block>(Arrays.asList(
			new Block(BlockType.GROUND),
			new Block(BlockType.GROUND),
			new Block(null),
			new Block(BlockType.SPAWNPOINT),
			new Block(null),
			new Block(null),
			new Block(null),
			new Block(null),		// This is the location (index 7) that the spawnpoint should be at, as it is the next block before the ground.
			new Block(BlockType.GROUND),
			new Block(BlockType.GROUND)
		));
		List<Block> segment2 = new ArrayList<Block>(Arrays.asList(
			new Block(BlockType.GROUND),
			new Block(BlockType.GROUND),
			new Block(null),
			new Block(BlockType.SPAWNPOINT),
			new Block(null),
			new Block(null),
			new Block(null), 	// This is the location (index 6) that the spawnpoint should be at, as it is the next block before the ground.
			new Block(BlockType.GROUND),		
			new Block(BlockType.GROUND),
			new Block(BlockType.GROUND)
		));
		List<List<Block>> layout = new ArrayList<List<Block>>(Arrays.asList(segment1, segment2));
		
		Level level = new Level(layout);
		List<List<Integer>> spawnpoints = level.getSpawnpoints();
		int x1 = spawnpoints.get(0).get(0);
		int y1 = spawnpoints.get(0).get(1);
		int x2 = spawnpoints.get(1).get(0);
		int y2 = spawnpoints.get(1).get(1);
		
		assertEquals(0, x1);
		assertEquals(7, y1);
		assertEquals(1, x2);
		assertEquals(6, y2);
	}
	
	/**
	 * Tests the spawnpoint locations API when there is a spawnpoint in a segment and no empty spaces.
	 */
	@Test
	public void testVaildSurroundedSpawnpointAPI() {
		List<Block> segment = new ArrayList<Block>(Arrays.asList(
			new Block(BlockType.GROUND),
			new Block(BlockType.GROUND),
			new Block(BlockType.GROUND),
			new Block(BlockType.SPAWNPOINT), // This is the location (index 3) that the spawnpoint should be at, as it is the next block before the ground.
			new Block(BlockType.GROUND),
			new Block(BlockType.GROUND),
			new Block(BlockType.GROUND),
			new Block(BlockType.GROUND),		
			new Block(BlockType.GROUND),
			new Block(BlockType.GROUND)
		));
		List<List<Block>> layout = new ArrayList<List<Block>>(Arrays.asList(segment));
		
		Level level = new Level(layout);
		List<List<Integer>> spawnpoints = level.getSpawnpoints();
		int x = spawnpoints.get(0).get(0);
		int y = spawnpoints.get(0).get(1);
		
		assertEquals(0, x);
		assertEquals(3, y);
	}
	
	/**
	 * Tests the spawnpoint locations API when there is no spawnpoint in a segment.
	 */
	@Test
	public void testInvalidSpawnpointAPI() {
		List<Block> segment = new ArrayList<Block>(Arrays.asList(
			new Block(BlockType.GROUND),
			new Block(BlockType.GROUND),
			new Block(null),
			new Block(null),
			new Block(null),
			new Block(null),
			new Block(null),
			new Block(null),
			new Block(BlockType.GROUND),
			new Block(BlockType.GROUND)
		));
		List<List<Block>> layout = new ArrayList<List<Block>>(Arrays.asList(segment));
		
		Level level = new Level(layout);
		List<List<Integer>> spawnpoints = level.getSpawnpoints();
		
		// There are no spawnpoints in the segment, so the list of spawnpoint coordinates should be empty.
		assertTrue(spawnpoints.isEmpty());
	}
	
	/**
	 * Tests that the end of the level is correctly generating.
	 */
	@Test
	public void testLevelEnd() {
		Level level = new Level(200);
		List<List<Block>> layout = level.getLevelLayout();
		
		assertEquals(BlockType.LEVELEND, layout.get(layout.size()-6).get(7).getBlockType());
		assertNull(layout.get(layout.size()-5).get(7));
	}
	
	/**
	 * Tests the deep clone method of the level.
	 */
	@Test
	public void testLevelClone() {
		Level level = new Level(200);
		Level clonedLevel = level.clone();
		
		assertNotEquals(level, clonedLevel);
		assertEquals(level.getLevelType(), clonedLevel.getLevelType());
		assertEquals(level.isLocked(), clonedLevel.isLocked());
		assertEquals(level.getLevelSize(), clonedLevel.getLevelSize());
	}
	
	/**
	 * Tests the creation of an empty level.
	 */
	@Test
	public void testEmptyLevel() {
		Level level = new Level();
		
		assertNull(level.getLevelLayout());
		assertEquals(0, level.getLevelSize());
		assertNull(level.getLevelType());
	}
	
	/**
	 * Tests altering the visited status of a block.
	 */
	@Test
	public void testChangeVisited() {
		Level level = new Level(200);
		
		assertFalse(level.getLevelLayout().get(0).get(0).getVisited());
		level.getLevelLayout().get(0).get(0).setVisited(true);
		assertTrue(level.getLevelLayout().get(0).get(0).getVisited());
	}
}
