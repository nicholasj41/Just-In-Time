package entityLibrary;

import java.awt.Dimension;
import java.awt.Point;
import java.io.Serializable;

import levelLibrary.Level;
import physicsLibrary.LevelPhysicsAPI;
import physicsLibrary.Physics2D;
import physicsLibrary.Physics2DBuilder;

/**
 * Player Class
 * 
 * @Primary_Author - Nicholas Jones (300384025).
 * @Code_Reviewer - Celine Young (300378246).
 * @External_Tester - Leyton Blackler (300368625).
 */
public class Player implements Serializable {
		
	private static final long serialVersionUID = 1L;
	
	/**
	 * Physics Library that applies the movement to the Player, and Gravity
	 */
	private Physics2D physics;
	
	/**
	 * The bounding box of the Player.  Used for detecting whether the Player collides with the NPC and dies.
	 */
	private BoundBox boundingBox;
	
	/**
	 * Spawn X position of the NPC
	 */
	private int xPos;
	
	/**
	 * Spawn Y Position of the NPC
	 */
	private int yPos;
	
	/**
	 * Boolean used for checking whether the player is dead or not.
	 */
	private boolean isDead = false;
	
	/**
	 * Width of the Player.  Used in Bounding Box
	 */
	private double playerWidth = 38;
	
	/**
	 * Height of the Player.  Used in Bounding Box
	 */
	private double playerHeight = 66;
	
	/**
	 * enums are used for which way the player is facing.
	 * LEFT - Facing Left
	 * RIGHT - Facing Right
	 * IDLE - So that the NPC doesn't default to a certain way to face after a key is released.
	 * 
	 * @author Nicholas
	 */
	public enum State { LEFT, RIGHT, IDLE }
	
	/**
	 * Creates the Player, and builds the Physics for the Player.
	 * 
	 * @param xPos - X Spawn Position
	 * @param yPos - Y Spawn Position
	 * @param level - Used for the collision detection between the bricks and Player
	 */
	public Player(int xPos, int yPos, Level level) {
		Physics2DBuilder builder = new Physics2DBuilder();
		builder.setPos(xPos, yPos);
		builder.setAcc(0, 6000);
		builder.setTerminalVelocity(500, 800);
		builder.setHorizontalAcc(1500);
		builder.setDrag(900, 0);
		boolean[][] blockList = LevelPhysicsAPI.levelToBooleanArray(level);
		builder.addCollisionGrid(blockList, new Dimension(67,67), new Dimension((int) playerWidth, (int) playerHeight));
		physics = builder.build();
		boundingBox = new BoundBox(this.physics.getPos().x - playerWidth/2, this.physics.getPos().y - playerHeight/2, playerWidth, playerHeight);
	}

	/**
	 * Updates the boundingBox every tick.  So that the boundingBox isn't left behind
	 * when the player moves.
	 */
	public void setBoundingBox(){
		boundingBox = new BoundBox(this.physics.getPos().x - playerWidth/2, this.physics.getPos().y - playerHeight/2, playerWidth, playerHeight);
	}
	
	/**
	 * When the Player dies, it sets isDead boolean to true,
	 * and when the game is restarted, changes it back to false.
	 * 
	 * @param r
	 */
	public void setPlayerDead(boolean r) { this.isDead = r; }
	
	//==================GETTERS================
	
	/**
	 * Returns the isDead boolean.
	 * 
	 * @return isDead - Boolean
	 */
	public boolean isPlayerDead() { return this.isDead; }

	/**
	 * Returns the boundingBox of the Player.  Used for checking whether the Player collides
	 * with an item or an NPC.
	 * 
	 * @return boundingBox - BoundingBox object
	 */
	public BoundBox getBoundingBox() { return this.boundingBox; }
	
	/**
	 * Returns the position that the Player is currently at through the physics library
	 * 
	 * @return physics.getPos() - Point object
	 */
	public Point getPos() { return physics.getPos(); }
	
	/**
	 * Returns the Physics of the Player
	 * 
	 * @return physics - Physics2D object
	 */
	public Physics2D getPhysics() { return this.physics; }
	
	/**
	 * Returns which direction the player is moving, depending on the Player's velocity.
	 * 
	 * @return enum
	 */
	public State whichDirection() { 
		if(physics.getHorizontalVelocity() > 0) { return State.RIGHT; }
		else if(physics.getHorizontalVelocity() < 0) { return State.LEFT; }
		else { return State.IDLE; }
	}
	
	/**
	 * Returns a boolean for checking whether the Gravity is going down, or up.
	 * 
	 * @return physics.isGravityDown - Boolean
	 */
	public boolean isGravityDown() {
		return physics.isGravityDown();
	}
	
	//====================TEST CONSTRUCTOR========================
	public Player(int xPos, int yPos) {
		this.xPos = xPos;
		this.yPos = yPos;
	}
	
	public int getXPos() {
		return this.xPos;
	}
	
	public int getYPos() {
		return this.yPos;
	}
}
