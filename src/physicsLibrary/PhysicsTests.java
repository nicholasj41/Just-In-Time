package physicsLibrary;

import static org.junit.Assert.*;

import java.awt.Dimension;
import java.awt.Point;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.Test;

public class PhysicsTests {	
	
	/**
	 * Makes a Physics2D object using Physics2DBuilder and checks that the object
	 * is not null
	 */
	@Test
	public void testPhysicstNotNull() {
		Physics2DBuilder builder = new Physics2DBuilder();
		Physics2D physics = builder.build();
		assertNotNull(physics);
	}
	
	/**
	 * Makes a Physics2D object using Physics2DBuilder and checks that the object
	 * matches what was built
	 */
	@Test
	public void testBuilderFunctionality() {
		Physics2DBuilder builder = new Physics2DBuilder();
		builder.setPos(1, 2);
		builder.setVel(3, 4);
		builder.setAcc(5, 6);
		builder.setDrag(7, 8);
		builder.setHorizontalAcc(9);
		builder.setTerminalVelocity(10, 11);
		Physics2D physics = builder.build();
		//check position
		assertEquals(1, physics.pos.x, 0);
		assertEquals(2, physics.pos.y, 0);
		//check velocity
		assertEquals(3, physics.vel.x, 0);
		assertEquals(4, physics.vel.y, 0);
		//check acceleration
		assertEquals(5, physics.acc.x, 0);
		assertEquals(6, physics.acc.y, 0);
		//check drag
		assertEquals(7, physics.drag.x, 0);
		assertEquals(8, physics.drag.y, 0);
		//check horizontal acceleration
		assertEquals(9, physics.horizontalAcc, 0);
		//check terminal velocity
		assertEquals(10, physics.terminalVelocity.x, 0);
		assertEquals(11, physics.terminalVelocity.y, 0);
	}
	
	/**
	 * Makes a Physics2D object using Physics2DBuilder and check that the points are correctly returned.
	 */
	@Test
	public void testGetPositions() {
		Physics2DBuilder builder = new Physics2DBuilder();
		builder.setPos(1, 2);
		
		Point point = builder.build().getPos();
		
		assertEquals(1, point.x);
		assertEquals(2, point.y);
	}
	
	/**
	 * Makes a Physics2D object using Physics2DBuilder and check that the points are updated.
	 */
	@Test
	public void testUpdatePositions() {
		Physics2DBuilder builder = new Physics2DBuilder();
		builder.setPos(1, 2);
		
		Point point = builder.build().updatePos();
		// Y position should be updated.
		assertEquals(1, point.x);
		assertEquals(2, point.y);
	}
	
	/**
	 * Tests that the acceleration is updated when moving right.
	 */
	@Test
	public void testValidMoveRight() {
		Physics2DBuilder builder = new Physics2DBuilder();
		builder.setPos(1, 2);
		double initialAcceleration = builder.build().acc.x;
		builder.build().pressRight();
		
		assertNotEquals(initialAcceleration, builder.build().acc.x);
		assertTrue(builder.build().movingRight);
		assertTrue(builder.build().isMovingRight());
		
		
		builder.build().releaseRight();
		
		assertEquals(initialAcceleration, builder.build().acc.x, 0);
		assertFalse(builder.build().movingRight);
		assertFalse(builder.build().isMovingRight());
	}
	
	/**
	 * Tests that the acceleration is updated when moving left.
	 */
	@Test
	public void testValidMoveLeft() {
		Physics2DBuilder builder = new Physics2DBuilder();
		builder.setPos(1, 2);
		double initialAcceleration = builder.build().acc.x;
		builder.build().pressLeft();
		
		assertNotEquals(initialAcceleration, builder.build().acc.x);
		assertTrue(builder.build().movingLeft);
		assertTrue(builder.build().isMovingLeft());
		
		builder.build().releaseLeft();
		
		assertEquals(initialAcceleration, builder.build().acc.x, 0);
		assertFalse(builder.build().movingLeft);
		assertFalse(builder.build().isMovingLeft());
	}
	
	/**
	 * Tests that the acceleration is not updated when already moving right.
	 */
	@Test
	public void testInvalidMoveRight() {
		Physics2DBuilder builder = new Physics2DBuilder();
		builder.setPos(1, 2);
		double acceleration1 = builder.build().acc.x;
		builder.build().pressRight();
		double acceleration2 = builder.build().acc.x;
		builder.build().pressRight();
		double acceleration3 = builder.build().acc.x;
		
		assertNotEquals(acceleration1, acceleration2);
		assertEquals(acceleration2, acceleration3, 0);
		assertTrue(builder.build().movingRight);
		
		builder.build().releaseRight();
		double acceleration4 = builder.build().acc.x;
		
		assertFalse(builder.build().movingRight);
		assertNotEquals(acceleration3, acceleration4);
		assertEquals(acceleration1, acceleration4, 0);
	}
	
	/**
	 * Tests that the acceleration is not updated when already moving left.
	 */
	@Test
	public void testInvalidMoveLeft() {
		Physics2DBuilder builder = new Physics2DBuilder();
		builder.setPos(1, 2);
		double acceleration1 = builder.build().acc.x;
		builder.build().pressLeft();
		double acceleration2 = builder.build().acc.x;
		builder.build().pressLeft();
		double acceleration3 = builder.build().acc.x;
		
		assertNotEquals(acceleration1, acceleration2);
		assertEquals(acceleration2, acceleration3, 0);
		assertTrue(builder.build().movingLeft);
		
		builder.build().releaseLeft();
		double acceleration4 = builder.build().acc.x;
		
		assertFalse(builder.build().movingLeft);
		assertNotEquals(acceleration3, acceleration4);
		assertEquals(acceleration1, acceleration4, 0);
	}
	
	/**
	 * Tests adding a collision grid.
	 */
	@SuppressWarnings("deprecation")
	@Test
	public void testCollisionGrid() {
		Physics2DBuilder builder = new Physics2DBuilder();
		boolean[][] layout = new boolean[30][30];
		Dimension blockSize = new Dimension(30,30);
		Dimension playerSize = new Dimension(15, 30);
		
		// Create a random 2D array of booleans.
		for (int i = 0; i < 30; i++) {
			for (int j = 0; j < 30; j++) {
				layout[i][j] = ThreadLocalRandom.current().nextBoolean();
			}
		}
		builder.addCollisionGrid(layout, blockSize, playerSize);
		
		assertEquals(layout, builder.build().collisionGrid);
		assertEquals(blockSize, builder.build().blockSize);
		assertEquals(playerSize, builder.build().entitySize);
		assertTrue(builder.build().collisionChecking);
	}
	
	/**
	 * Tests creating Vector2Ds.
	 */
	@Test
	public void testDefaultVector2D() {
		Vector2D vector = new Vector2D();
		assertEquals(0, vector.x, 0);
		assertEquals(0, vector.y, 0);
	}
	
	/**
	 * Tests Vector2D add method.
	 */
	@Test
	public void testVector2DAdd() {
		Vector2D vector1 = new Vector2D(1,2);
		Vector2D vector2 = new Vector2D(1,2);
		
		vector1.add(vector2);
		
		assertEquals(2, vector1.x, 0);
		assertEquals(4, vector1.y, 0);
	}
	
	/**
	 * Tests Vector2D minus method.
	 */
	@Test
	public void testVector2DMinus() {
		Vector2D vector1 = new Vector2D(2,4);
		Vector2D vector2 = new Vector2D(1,2);
		
		vector1.minus(vector2);
		
		assertEquals(1, vector1.x, 0);
		assertEquals(2, vector1.y, 0);
	}
	
	/**
	 * Tests Vector2D cap method.
	 */
	@Test
	public void testVector2DCap1() {
		Vector2D vector1 = new Vector2D(-10,10);
		Vector2D vector2 = new Vector2D(2,2);
		
		vector1.cap(vector2);
		
		assertEquals(-2, vector1.x, 0);
		assertEquals(2, vector1.y, 0);
	}
	
	/**
	 * Tests Vector2D cap method.
	 */
	@Test
	public void testVector2DCap2() {
		Vector2D vector1 = new Vector2D(10,10);
		Vector2D vector2 = new Vector2D(2,2);
		
		vector1.cap(vector2);
		
		assertEquals(2, vector1.x, 0);
		assertEquals(2, vector1.y, 0);
	}
	
	/**
	 * Tests Vector2D cap method.
	 */
	@Test
	public void testVector2DCap3() {
		Vector2D vector1 = new Vector2D(10,10);
		Vector2D vector2 = new Vector2D(-2,2);
		
		vector1.cap(vector2);
		
		assertEquals(2, vector1.x, 0);
		assertEquals(2, vector1.y, 0);
	}
	
	/**
	 * Tests Vector2D cap method.
	 */
	@Test
	public void testVector2DCap4() {
		Vector2D vector1 = new Vector2D(10,10);
		Vector2D vector2 = new Vector2D(2,-2);
		
		vector1.cap(vector2);
		
		assertEquals(2, vector1.x, 0);
		assertEquals(2, vector1.y, 0);
	}
	
	/**
	 * Tests Vector2D cap method.
	 */
	@Test
	public void testVector2DCap5() {
		Vector2D vector1 = new Vector2D(10,-10);
		Vector2D vector2 = new Vector2D(2,-2);
		
		vector1.cap(vector2);
		
		assertEquals(2, vector1.x, 0);
		assertEquals(-2, vector1.y, 0);
	}
	
	/**
	 * Tests Vector2D drag method.
	 */
	@Test
	public void testVector2DDrag1() {
		Vector2D vector1 = new Vector2D(-1,5);
		Vector2D vector2 = new Vector2D(2,2);
		
		vector1.drag(vector2);
		
		assertEquals(0, vector1.x, 0);
		assertEquals(3, vector1.y, 0);
	}
	
	/**
	 * Tests Vector2D drag method.
	 */
	@Test
	public void testVector2DDrag2() {
		Vector2D vector1 = new Vector2D(10,10);
		Vector2D vector2 = new Vector2D(2,2);
		
		vector1.drag(vector2);
		
		assertEquals(8, vector1.x, 0);
		assertEquals(8, vector1.y, 0);
	}
	
	/**
	 * Tests Vector2D drag method.
	 */
	@Test
	public void testVector2DDrag3() {
		Vector2D vector1 = new Vector2D(10,10);
		Vector2D vector2 = new Vector2D(-2,2);
		
		vector1.drag(vector2);
		
		assertEquals(8, vector1.x, 0);
		assertEquals(8, vector1.y, 0);
	}
	
	/**
	 * Tests Vector2D drag method.
	 */
	@Test
	public void testVector2DDrag4() {
		Vector2D vector1 = new Vector2D(10,10);
		Vector2D vector2 = new Vector2D(2,-2);
		
		vector1.drag(vector2);
		
		assertEquals(8, vector1.x, 0);
		assertEquals(8, vector1.y, 0);
	}
	
	/**
	 * Tests Vector2D drag method.
	 */
	@Test
	public void testVector2DDrag5() {
		Vector2D vector1 = new Vector2D(10,-10);
		Vector2D vector2 = new Vector2D(2,-2);
		
		vector1.drag(vector2);
		
		assertEquals(8, vector1.x, 0);
		assertEquals(-8, vector1.y, 0);
	}
	
	/**
	 * Tests Vector2D drag method.
	 */
	@Test
	public void testVector2DDrag6() {
		Vector2D vector1 = new Vector2D(1,-2);
		Vector2D vector2 = new Vector2D(2,3);
		
		vector1.drag(vector2);
		
		assertEquals(0, vector1.x, 0);
		assertEquals(0, vector1.y, 0);
	}
	
	/**
	 * Tests Vector2D drag method.
	 */
	@Test
	public void testVector2DDrag7() {
		Vector2D vector1 = new Vector2D(-2,1);
		Vector2D vector2 = new Vector2D(1,2);
		
		vector1.drag(vector2);
		
		assertEquals(-1, vector1.x, 0);
		assertEquals(0, vector1.y, 0);
	}
	
	/**
	 * Tests flipping gravity direction.
	 */
	@Test
	public void testSwitchGravity() {
		Physics2DBuilder builder = new Physics2DBuilder();
		Vector2D acceleration1 = builder.build().acc;
		
		builder.build().switchGravity();
		
		Vector2D acceleration2 = builder.build().acc;
		
		assertEquals(acceleration1.x, acceleration2.x, 0);
		assertEquals(acceleration1.y, acceleration2.y, -1);
		assertEquals(Math.abs(acceleration1.y), Math.abs(acceleration2.y), 0);
	}
	
	/**
	 * Tests disabling gravity.
	 */
	@Test
	public void testDisableGravity() {
		Physics2DBuilder builder = new Physics2DBuilder();
		Double acceleration1 = builder.build().acc.y;
		
		builder.build().noGravity();
		
		Double acceleration2 = builder.build().acc.y;
		
		assertEquals(3000, acceleration1, 0);
		assertEquals(0, acceleration2, 0);
	}
	
	/**
	 * Tests that the horizontal velocity is being correctly returned.
	 */
	@Test
	public void testGetHorizontalVelocity() {
		Physics2DBuilder builder = new Physics2DBuilder();
		Double velocity1 = builder.build().vel.x;
		Double velocity2 = builder.build().getHorizontalVelocity();
		
		assertEquals(velocity1, velocity2, 0);
	}
	
	/**
	 * Tests that the horizontal velocity is being correctly returned.
	 */
	@Test
	public void testGetGravityDirection() {
		Physics2DBuilder builder = new Physics2DBuilder();
		
		assertFalse(builder.build().isGravityDown());
		
		builder.build().switchGravity();
		
		assertTrue(builder.build().isGravityDown());
	}
}