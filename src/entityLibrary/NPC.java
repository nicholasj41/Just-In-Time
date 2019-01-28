package entityLibrary;

import java.awt.Dimension;
import java.awt.Point;
import java.io.Serializable;
import levelLibrary.Level;
import physicsLibrary.LevelPhysicsAPI;
import physicsLibrary.Physics2D;
import physicsLibrary.Physics2DBuilder;

/**
 * NPC Class
 * 
 * @Primary_Author - Nicholas Jones (300384025).
 * @Code_Reviewer - Celine Young (300378246).
 * @External_Tester - Leyton Blackler (300368625).
 */
public class NPC implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Controls which way the NPC moves.  True = Facing and Moving Right, False = Facing and Moving Left
	 */
	private boolean movingRight = true;
	
	/**
	 * Left boundary of where the NPC can move
	 */
	private int leftEnd;

	/**
	 * Right boundary of where the NPC can move
	 */
	private int rightEnd;
	
	/**
	 * Speed of the NPC that it moves at per tick of the timer
	 */
	private double speed = 0.5;
	
	/**
	 * Physics Library that applies the movement to the NPC, and Gravity
	 */
	private Physics2D physics;
	
	/**
	 * The bounding box of the NPC.  Used for detecting whether the Player collides with the NPC and dies.
	 */
	private BoundBox boundingBox;
	
	/**
	 * Spawn X position of the NPC
	 */
	private int startX;
	
	/**
	 * Spawn Y Position of the NPC
	 */
	private int startY;
	
	/**
	 * The Level that the Player is currently playing on
	 */
	private Level level;
	/**
	 * Actual Width of the NPC.  Used in the Bounding Box
	 */
	private double NPCWidth = 47;
	
	/**
	 * Actual Height of the NPC.  Used in the Bounding Box
	 */
	private double NPCHeight = 55;
	
	/**
	 * Block Width of the NPC
	 */
	public static final double NPC_WIDTH = 67;
	
	/**
	 * Block Height of the NPC
	 */
	public static final double NPC_HEIGHT = 67;
	
	
	/**
	 * Creates the NPC, and builds the Physics for that NPC.
	 * 
	 * @param xPos - X Spawn Position
	 * @param yPos - Y Spawn Position
	 * @param leftEnd - Point where the NPC turns around and heads in the opposite direction
	 * @param rightEnd - Point where the NPC turns around and heads in the opposite direction
	 * @param level - Used for the collision detection between brick blocks
	 */
	public NPC(int startX, int startY, int leftEnd, int rightEnd, Level level) {
		Physics2DBuilder builder = new Physics2DBuilder();
		this.startX = startX;
		this.startY = startY;
		this.leftEnd = leftEnd;
		this.rightEnd = rightEnd;
		this.level = level;
		builder.setPos(startX, startY);
		builder.setAcc(0, 300000);
		builder.setTerminalVelocity(120, 180);
		builder.setHorizontalAcc(600000);
		builder.setDrag(0, 0);
		boolean[][] blockList = LevelPhysicsAPI.levelToBooleanArray(level);
		builder.addCollisionGrid(blockList, new Dimension(67,67), new Dimension((int) NPC_WIDTH, (int) NPC_HEIGHT));
		physics = builder.build();
		boundingBox = new BoundBox(this.physics.getPos().x - NPCWidth/2, this.physics.getPos().y - NPCHeight/2, NPCWidth, NPCHeight);
	}

	// =================MOVEMENT================
	
	/**
	 * Controls which way the NPC moves.  When the NPC reaches the leftEnd of the boundary, 
	 * change direction to head in the right direction.  Vice Versa for when it reaches the rightEnd.
	 * 
	 * Controls moving left and right.  
	 * 
	 * Updates the bounding box for the NPC, depending
	 * on the current x and y position.
	 */
	public void moveHorizontal() {
		this.physics.updatePos();
		
		if (this.physics.getPos().x >= rightEnd) { 
			movingRight = false;
		}
		else if (this.physics.getPos().x <= leftEnd) {
			movingRight = true;
		}
		
		if (movingRight) {
			this.physics.releaseLeft();
			this.physics.pressRight();
		} else {
			this.physics.releaseRight();
			this.physics.pressLeft();
		}
		
		boundingBox = new BoundBox(this.physics.getPos().x - NPCWidth/2, this.physics.getPos().y - NPCHeight/2, NPCWidth, NPCHeight);	
	}
	
	/**
	 * Performs a deep clone of this NPC
	 */
	@Override
	public NPC clone() { return new NPC(startX, startY, leftEnd, rightEnd, level.clone()); }
	
	/**
	 * Changes the moving right boolean to the opposite.
	 * Used when colliding with a wall, and is unable to make it to either leftEnd, or rightEnd boundaries.
	 */
	public void flipDirection() { movingRight = !movingRight; }

	// ==================GETTERS================
	
	/**
	 * Returns the bounding box of the NPC
	 * 
	 * @return boundingBox - BoundingBox object
	 */
	public BoundBox getBoundingBox() { return this.boundingBox; }
	
	/**
	 * Returns the position of the NPC from the Physics Library
	 * 
	 * @return physics.getPos() - a Point object
	 */
	public Point getPos() { return this.physics.getPos(); }

	/**
	 * Returns the speed of the NPC
	 * 
	 * @return speed - Double
	 */
	public double getSpeed() { return this.speed; }
	
	
	/**
	 * Returns the physics for this NPC
	 * 
	 * @return physics - a Physics2D Object
	 */
	public Physics2D getPhysics() { return this.physics; }
	
	/**
	 * Returns a boolean that's used for checking whether the NPC is moving or facing right.
	 * 
	 * @return movingRight - Boolean
	 */
	public boolean isMovingRight() { return this.movingRight; }
	
	//===================TEST CONSTRUCTOR============================
	
	public NPC(int startX, int startY, int leftEnd, int rightEnd) {
		this.startX = startX;
		this.startY = startY;
		this.leftEnd = leftEnd;
		this.rightEnd = rightEnd;
	}
	
	public int getStartX() {
		return this.startX;
	}
	
	public int getStartY() {
		return this.startY;
	}
	
	public int getLeftEnd() {
		return this.leftEnd;
	}
	
	public int getRightEnd() {
		return this.rightEnd;
	}


}
