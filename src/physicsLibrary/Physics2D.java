package physicsLibrary;

import java.awt.Dimension;
import java.awt.Point;
import java.io.Serializable;

/**
 * Physics Class: interfaces with GUI with java.awt.Point. 
 * Physics2D Class
 * 
 * @Primary_Author - Finn Welsford-Ackroyd (300379304).
 * @Code_Reviewer - Leyton Blackler (300368625).
 * @External_Tester - Riley Blair (300371586).
 */
public class Physics2D implements Serializable {

	private static final long serialVersionUID = 1L;
	protected Vector2D pos;
	protected Vector2D vel;
	protected Vector2D acc;
	protected Vector2D terminalVelocity;
	protected Vector2D drag;
	protected double horizontalAcc; //acceleration applied when left or right is pressed
	protected boolean[][] collisionGrid;
	protected Dimension blockSize;
	protected Dimension entitySize;
	protected boolean collisionChecking = false;
	protected double targetFrameTime;
	protected boolean movingRight = false;
	protected boolean movingLeft = false;
	protected double currentTime;
	protected boolean gravityDown;
	
	/**
	 * Constructor with all fields set to default values. Only accessible by Physics2DBuilder.
	 */
	protected Physics2D() {
		pos = new Vector2D(0, 0);
		vel = new Vector2D(0, 0);
		acc = new Vector2D(0, 3000);
		drag = new Vector2D(0, 0);
		horizontalAcc = 3000;
		terminalVelocity = new Vector2D(Double.MAX_VALUE, Double.MAX_VALUE);
		currentTime = System.currentTimeMillis();
		gravityDown = true;
		targetFrameTime = 0.0166666666666667; //60 frames per second
	}
	
	/**
	 * Returns the position vector as a java.awt.Point.
	 * @return
	 */
	public Point getPos() {
		return new Point((int) Math.round(pos.x), (int) Math.round(pos.y));
	}
	
	/**
	 * Sets the position vector to Point newPos.
	 * @param newPos
	 */
	public void setPos(Point newPos) {
		pos.x = newPos.x;
		pos.y = newPos.y;
	}
	
	/**
	 * Should be called when the game is unpaused - makes sure that the physics doesn't treat 
	 * a paused game as a huge timestep.
	 */
	public void resetTime() {
		currentTime = System.currentTimeMillis();
	}
	
	/**
	 * Calculates time passed since last updatePos() call and sends it to calculateNextPos().
	 * Returns current position after calculations using getPos().
	 * @return
	 */
	public Point updatePos() {
		//calculate seconds since last update
		double newTime = System.currentTimeMillis();
		double frameTime = (newTime - currentTime)/1000;
		currentTime = newTime;
		while(frameTime > 0) {
			double deltaTime = Math.min(frameTime, targetFrameTime);
			calculateNextPos(deltaTime);
			frameTime -= deltaTime;
		}
		return getPos();
	}
	
	/**
	 * Physics2D calculates the next position based on the amount of time that has passed since
	 * the last updatePos() call, updating its vector variables.
	 * @return
	 */
	private void calculateNextPos(double deltaTime) {
		//vf = vi + a*t
		Vector2D vi = vel.clone();
		Vector2D at = acc.clone();
		at.multiply(deltaTime);
		vel.add(at);
		
		//add drag to vel
		Vector2D scaledDrag = drag.clone();
		scaledDrag.multiply(deltaTime);
		vel.drag(scaledDrag);
		
		//cap vel at terminal velocity
		vel.cap(terminalVelocity);
		
		//d = ((vi + vf)/2)*t
		vi.add(vel);
		vi.multiply(0.5 * deltaTime);
		pos.add(vi);
		
		if(collisionChecking) checkCollisions();
	}

	/**
	 * Respond to right key press by increasing acceleration in that direction.
	 */
	public void pressRight() {
		if(!movingRight) {			
			acc.x += horizontalAcc;
			movingRight = true;
		}
	}
	
	/**
	 * Respond to left key press by increasing acceleration in that direction.
	 */
	public void pressLeft() {
		if(!movingLeft) {			
			acc.x -= horizontalAcc;
			movingLeft = true;
		}
	}
	
	/**
	 * When the left key is released, remove the velocity added by the key press.
	 */
	public void releaseLeft() {
		if(movingLeft) {
			acc.x += horizontalAcc;
			movingLeft = false;
		}
	}
	
	/**
	 * When the right key is released, remove the velocity added by the key press.
	 */
	public void releaseRight() {
		if(movingRight) {			
			acc.x -= horizontalAcc;
			movingRight = false;
		}
	}
	
	/**
	 * Switches gravity by inverting the acceleration y vector.
	 */
	public void switchGravity() {
		acc.y = acc.y*-1;
		if(gravityDown) {
			gravityDown = false;
		}else{
			gravityDown = true;
		}
	}
	
	/**
	 * Sets gravity to the value of the parameter. True = down, false = up.
	 * @param down
	 */
	public void setGravityDown(boolean down) {
		if(gravityDown != down) {
			switchGravity();
		}
	}
	
	/**
	 * Turns off gravity by setting the y-component of the acc vector to 0
	 */
	public void noGravity() {
		acc.y = 0;
	}
	
	/**
	 * Returns x velocity value for determining entity movement direction
	 * @return
	 */
	public double getHorizontalVelocity() {
		return vel.x;
	}
	
	/**
	 * Returns true if gravity is pointing down, and false otherwise
	 * @return
	 */
	public boolean isGravityDown() {
		return acc.y < 0;
	}
	
	/**
	 * Returns true if entity is moving right, and false otherwise
	 * 
	 * @return If the entity is moving right.
	 */
	public boolean isMovingRight() {
		return movingRight;
	}
	
	/**
	 * Returns true if entity is moving left, and false otherwise
	 * 
	 * @return If the entity is moving left.
	 */
	public boolean isMovingLeft() {
		return movingLeft;
	}

	/**
	 * Checks if the entity is colliding with any of the solid grid pieces.
	 */
	private void checkCollisions() {
		//only need to check the boxes in the grid that the entity occupies:
		//get leftmost and rightmost columns that the entity occupies
		int leftCol = (int) Math.floor((pos.x - entitySize.width/2)/blockSize.width);
		//rightCol sometimes includes the tile to the right of the wall the entity is flush with
		int rightCol = (int) Math.floor((pos.x + entitySize.width/2)/blockSize.width);
		if(pos.x + entitySize.width/2 == blockSize.width*rightCol) { //this removes that error
			rightCol--;
		}
		//get lowest and highest rows that the entity occupies
		int lowRow = (int) Math.floor((pos.y - entitySize.height/2)/blockSize.height);
		//highRow had the same issue that rightCol had.
		int highRow = (int) Math.floor((pos.y + entitySize.height/2)/blockSize.height);
		if(pos.y + entitySize.height/2 == blockSize.height*highRow) {
			highRow--;
		}
		for(int x = leftCol; x <= rightCol; x++){
			for(int y = lowRow; y <= highRow; y++){
				//check for collision with solid block
				if(collisionGrid[x][y]){ //true == solid block
					resolveCollision(x, y);
				}
			}
		}
	}
	
	/**
	 * Moves the entity out of the solid block to the point it collided with.
	 * @param x
	 * @param y
	 */
	public void resolveCollision(int x, int y) {
		//check for an x overlap
		double xOverlap = 0;
		if(vel.x > 0){ //moving right, check overlap between right side of entity and left side of block
			//if the collision edge faces another block it should not be checked
			if(x-1 >= 0 && collisionGrid[x-1][y]) {
				xOverlap = Double.MAX_VALUE;
			}else {
				double entityRight = pos.x + entitySize.width/2;
				double blockLeft = x*blockSize.width;
				xOverlap = entityRight - blockLeft;
			}
		}else if(vel.x < 0){ //moving left, check overlap between left side of entity and right side of block
			if(x+1 < collisionGrid.length && collisionGrid[x+1][y]) {
				xOverlap = Double.MAX_VALUE;
			}else {
				double entityLeft = pos.x - entitySize.width/2;
				double blockRight = (x+1)*blockSize.width;
				xOverlap = blockRight - entityLeft;
			}
		}else { //vel.x == 0. Can't have a meaningful overlap with no speed.
			xOverlap = Double.MAX_VALUE;
		}
		//check for a y overlap
		double yOverlap = 0;
		if(vel.y > 0){ //moving down, check overlap between bottom side of entity and top side of block
			if(y-1 >= 0 && collisionGrid[x][y-1]) {
				yOverlap = Double.MAX_VALUE;
			}else {
				double entityBottom = pos.y + entitySize.height/2;
				double blockTop = y*blockSize.height;
				yOverlap = entityBottom - blockTop;
			}
		}else if(vel.y < 0){ //moving up, check overlap between top side of entity and bottom side of block
			if(y+1 < collisionGrid[0].length && collisionGrid[x][y+1]) {
				yOverlap = Double.MAX_VALUE;
			}else {
				double entityTop = pos.y - entitySize.height/2;
				double blockBottom = (y+1)*blockSize.height;
				yOverlap = blockBottom - entityTop;
			}
		}else { //vel.y == 0. Can't have a meaningful overlap with no speed.
			yOverlap = Double.MAX_VALUE;
		}
		//ignore some incorrectly detected collisions, e.g when the overlap is bigger than what should be possible
		//the entity cannot travel more than one terminal velocity per tick
		if(xOverlap > terminalVelocity.x*targetFrameTime) {
			xOverlap = Double.MAX_VALUE;
		}
		if(yOverlap > terminalVelocity.y*targetFrameTime) {
			yOverlap = Double.MAX_VALUE;
		}
		//Max value means ignore - if both overlaps are to be ignored, exit early.
		if(xOverlap == Double.MAX_VALUE && yOverlap == Double.MAX_VALUE) {
			return;
		}

		if(xOverlap < yOverlap){ //smallest overlap gets adjusted flush with the side it hit
			if(vel.x > 0){
				pos.x -= xOverlap;
			}else{
				pos.x += xOverlap;
			}
			vel.x = 0;
		}else{
			if(vel.y > 0){
				pos.y -= yOverlap;
			}else{
				pos.y += yOverlap;
			}
			vel.y = 0;
		}
	}
}
