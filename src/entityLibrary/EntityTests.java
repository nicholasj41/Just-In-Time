package entityLibrary;

import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.Test;

import levelLibrary.Level;
import physicsLibrary.Physics2D;

/**
 * Entity Testing Class
 * 
 * @Primary_Author - Nicholas Jones (300384025).
 * @Code_Reviewer - Celine Young (300378246).
 * @External_Tester - Riley Blair (300371586).
 */
public class EntityTests {
	
	/**
	 * Tests that a player correctly spawns at a given location.
	 */
	@Test
    public void testPlayerSpawn() {
        Player player = new Player(0, 0);
        assertEquals(0, player.getXPos());
        assertEquals(0, player.getYPos());
    }
	
	/**
	 * Tests that an NPC moves right by default.
	 */
	@Test
	public void testNPCIsMovingRight() {
		NPC npc = new NPC(0, 0, 0, 100);
		assertEquals(true, npc.isMovingRight());
	}

	/**
	 * Tests that the default speed of an NPC is 0.5.
	 */
	@Test
	public void testNPCSpeed() {
		NPC npc = new NPC(0, 0, 0, 100);
		assertEquals(0.5, npc.getSpeed(), 0);
	}
	
	/**
	 * Tests that an NPC correctly spawns at a given location.
	 */
	@Test
	public void testNPCSpawn() {
        NPC npc = new NPC(0, 0, 0, 100);
        assertEquals(0, npc.getStartX());
        assertEquals(0, npc.getStartY());
        assertEquals(0, npc.getLeftEnd());
        assertEquals(100, npc.getRightEnd());
    }
	
	/**
	 * Tests that an NPC correctly spawns at a given location within a level.
	 */
	@Test
	public void testNPCSpawnWithinLevel() {
		Level level = new Level(200);
		NPC npc = new NPC(5, 5, 0, 200, level);
        assertEquals(5, npc.getStartX());
        assertEquals(5, npc.getStartY());
        assertEquals(0, npc.getLeftEnd());
        assertEquals(200, npc.getRightEnd());
    }
	
	/**
	 * Tests that an NPC and player's bounding box is correctly returned.
	 */
	@Test
	public void testBoundingBox() {
		Level level = new Level(200);
		NPC npc = new NPC(0, 0, 0, 200, level);
		Player player = new Player(0, 0, level);
		
		double npcWidth = 23.5;
		double npcHeight = 27.5;
		double playerWidth = 19.0;
		double playerHeight = 33.0;
        
        BoundBox npcBoundingBox = npc.getBoundingBox();
        BoundBox playerBoundingBox = player.getBoundingBox();
        
        assertEquals(-npcWidth, npcBoundingBox.getMinX(), 0);
        assertEquals(npcWidth, npcBoundingBox.getMaxX(), 0);
        assertEquals(-npcHeight, npcBoundingBox.getMinY(), 0);
        assertEquals(npcHeight, npcBoundingBox.getMaxY(), 0);
        
        assertEquals(-playerWidth, playerBoundingBox.getMinX(), 0);
        assertEquals(playerWidth, playerBoundingBox.getMaxX(), 0);
        assertEquals(-playerHeight, playerBoundingBox.getMinY(), 0);
        assertEquals(playerHeight, playerBoundingBox.getMaxY(), 0);
    }
	
	/**
	 * Tests that an NPC and player's position is correctly returned.
	 */
	@Test
	public void testPosition() {
		Level level = new Level(200);
		NPC npc = new NPC(5, 7, 0, 200, level);
		Player player = new Player(1, 2, level);
        
        Point npcPosition = npc.getPos();
        Point playerPosition = player.getPos();
        
        assertEquals(5, npcPosition.x, 0);
        assertEquals(7, npcPosition.y, 0);
        assertEquals(1, playerPosition.x, 0);
        assertEquals(2, playerPosition.y, 0);
    }
	
	/**
	 * Tests that an NPC's physics class is returned correctly.
	 */
	@Test
	public void testPlayerNPCPhysics() {
		Level level = new Level(200);
		NPC npc = new NPC(5, 7, 0, 200, level);
		Player player = new Player(1, 2, level);
        
		Physics2D npcPhysics = npc.getPhysics();
		Physics2D playerPhysics = player.getPhysics();
        
        assertEquals(5, npcPhysics.getPos().getX(), 0);
        assertEquals(7, npcPhysics.getPos().getY(), 0);
        assertEquals(1, playerPhysics.getPos().getX(), 0);
        assertEquals(2, playerPhysics.getPos().getY(), 0);
    }
	
	/**
	 * Tests that an NPC correctly flips direction.
	 */
	@Test
	public void testNPCFlipDirection() {
		Level level = new Level(200);
		NPC npc = new NPC(5, 7, 0, 200, level);
        boolean movingRight1 = npc.isMovingRight();
        npc.flipDirection(); 
        assertNotEquals(movingRight1, npc.isMovingRight());
        npc.flipDirection(); 
        assertEquals(movingRight1, npc.isMovingRight());
    }
	
	/**
	 * Tests that a player correctly spawns at a given location within a level.
	 */
	@Test
	public void testPlayerSpawnWithinLevel() {
		Level level = new Level(200);
		Player player = new Player(5, 5, level);
        assertEquals(0, player.getXPos());
        assertEquals(0, player.getYPos());
        assertFalse(player.isPlayerDead());
    }
	
	/**
	 * Tests that killing a player makes the player unresponsive.
	 */
	@Test
	public void testPlayerDied() {
		Level level = new Level(200);
		Player player = new Player(5, 5, level);
        assertFalse(player.isPlayerDead());
        player.setPlayerDead(true);
        assertTrue(player.isPlayerDead());
    }
	
	/**
	 * Tests that the initial player state is idle.
	 */
	@Test
	public void testPlayerStateIdle() {
		Level level = new Level(200);
		Player player = new Player(5, 5, level);
        assertEquals(Player.State.valueOf("IDLE"), player.whichDirection());
    }	

}
