package physicsLibrary;

import java.io.Serializable;

/**
 * Vector2D Class
 * 
 * @Primary_Author - Finn Welsford-Ackroyd (300379304).
 * @Code_Reviewer - Leyton Blackler (300368625).
 * @External_Tester - Nicholas Jones (300384025).
 */
public class Vector2D implements Serializable {

	private static final long serialVersionUID = 1L;
	protected double x;
	protected double y;
	
	/**
	 * Constructor taking an x and y coordinate.
	 * 
	 * @param x - The x coordinate.
	 * @param y - The y coordinate.
	 */
	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Constructor without a given point. It defaults to 0.
	 */
	public Vector2D() {
		this.x = 0;
		this.y = 0;
	}

	/**
	 * Adds Vector2D other to this Vector2D
	 * 
	 * @param other - The other Vector2D object.
	 */
	public void add(Vector2D other) {
		this.x += other.x;
		this.y += other.y;
	}

	/**
	 * Subtracts Vector2D other from this Vector2D.
	 * 
	 * @param other - The other Vector2D object.
	 */
	public void minus(Vector2D other) {
		this.x -= other.x;
		this.y -= other.y;
	}

	/**
	 * Multiplies this Vector2D by a scalar, represented as a double.
	 * 
	 * @param scalar - The amount to multiply the points by.
	 */
	public void multiply(double scalar) {
		x = x * scalar;
		y = y * scalar;
	}

	/**
	 * Modifies this vector so that: this >= -cap && this <= cap.
	 * The method caps this vector's x and y magnitudes to the values in the cap Vector in both negative and positive directions. 
	 * E.g: (-10, 10).cap(2,2) -> this = (-2, 2)
	 * 
	 * @param cap - The cap value.
	 */
	public void cap(Vector2D cap) {
		//passed values must be positive
		if(cap.x < 0){
			cap.x *= -1;
		}
		if(cap.y < 0){
			cap.y *= -1;
		}
		//cap x direction
		if(x > cap.x) {
			x = cap.x;
		}else if(x < (cap.x*-1)) {
			x = cap.x * -1;
		}
		//cap y direction
		if(y > cap.y) {
			y = cap.y;
		}else if(y < (cap.y*-1)) {
			y = cap.y * -1;
		}
	}

	/**
	 * Drag method
	 * 
	 * @param drag - Added or subtracted from this Vector2D so that this.x and this.y component approach 0. 
	 * eg (-1, 5).drag(2, 2) -> this = (0, 3)
	 */
	public void drag(Vector2D drag) {
		//passed values must be positive
		if(drag.x < 0){
			drag.x *= -1;
		}
		if(drag.y < 0){
			drag.y *= -1;
		}
		// apply drag to x
		if(x > 0){
			x -= drag.x;
			if(x < 0) x = 0; //reset x to 0 if gone too far
		}else if(x < 0){
			x += drag.x;
			if(x > 0) x = 0;
		}
		// apply drag to y
		if(y > 0){
			y -= drag.y;
			if(y < 0) y = 0; //reset y to 0 if gone too far
		}else if(y < 0){
			y += drag.y;
			if(y > 0) y = 0;
		}
	}

	/**
	 * Returns a clone of this Vector2D.
	 * 
	 * @return a clone of the current Vector2D object.
	 */
	public Vector2D clone() {
		return new Vector2D(x, y);
	}
}