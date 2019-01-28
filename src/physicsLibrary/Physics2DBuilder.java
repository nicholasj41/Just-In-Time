package physicsLibrary;

import java.awt.Dimension;

/**
 * Builder design pattern for constructors with too many parameters.
 * Ensures setter methods can only be used in construction, and not all
 * parameters have to be set.
 * https://en.wikipedia.org/wiki/Builder_pattern
 * @author Finn
 */
public class Physics2DBuilder {
	private Physics2D physics;
	
	public Physics2DBuilder() {
		physics = new Physics2D();
	}
	
	/**
	 * Set initial position vector in Physics2D object.
	 * @param x: x-position
	 * @param y: y-position
	 */
	public void setPos(double x, double y) {
		physics.pos = new Vector2D(x, y);
	}
	
	/**
	 * Set initial velocity vector in Physics2D object.
	 * @param x: x-component of velocity
	 * @param y: y-component of velocity
	 */
	public void setVel(double x, double y) {
		physics.vel = new Vector2D(x, y);
	}
	
	/**
	 * Set initial acceleration vector in Physics2D object.
	 * @param x: x-component of acceleration
	 * @param y: y-component of acceleration
	 */
	public void setAcc(double x, double y) {
		physics.acc = new Vector2D(x, y);
	}
	
	/**
	 * Set drag vector in Physics2D object. Drag is applied to velocity on each
	 * call of nextPos().
	 * @param x: x-component of drag
	 * @param y: y-component of drag
	 */
	public void setDrag(double x, double y) {
		physics.drag = new Vector2D(x, y);
	}
	
	/**
	 * Set horizontal acceleration in Physics2D object. This is the acceleration
	 * that is applied when the pressLeft/pressRight methods are called.
	 * @param h: horizontal acceleration
	 */
	public void setHorizontalAcc(double h) {
		physics.horizontalAcc = h;
	}
	
	/**
	 * Set terminal velocity in the x and y direction. This is the highest (and lowest
	 * in the negative direction) velocity that the object will reach.
	 * @param x: terminal velocity in x direction
	 * @param y: terminal velocity in y direction
	 */
	public void setTerminalVelocity(double x, double y) {
		physics.terminalVelocity = new Vector2D(x, y);
	}
	
	/**
	 * Sets target frame time to @param f. This is the maximum timestep that will be used
	 * to calculate the next position of the entity. For a target frame rate of x fps, set
	 * this to 1/x.
	 */
	public void setTargetFrameTime(double f) {
		physics.targetFrameTime = f;
	}
	
	/**
	 * Enables collision checking in a grid-based system.
	 * @param collisionGrid: 2D boolean grid array of solid and non-solid blocks
	 * @param blockSize: size of block, in pixels
	 * @param entitySize: size of object that is using Physics2D.
	 */
	public void addCollisionGrid(boolean[][] collisionGrid, Dimension blockSize, Dimension entitySize) {
		physics.collisionGrid = collisionGrid;
		physics.blockSize = blockSize;
		physics.entitySize = entitySize;
		physics.collisionChecking = true;
	}
	
	/**
	 * Build Physics2D object with the specified values. Any non-specified values will use
	 * the defaults of:
	 * pos = (0, 0)
	 * vel = (0, 0)
	 * acc = (0, 3000)
	 * drag = (0, 0)
	 * horizontalAcc = 3000;
	 * terminalVelocity = (Double.MAX_VALUE, Double.MAX_VALUE)
	 * targetFrameRate = 1/60
	 * and no collision checking.
	 * @return the new Physics2D object.
	 */
	public Physics2D build() {
		return physics;
	}
}
