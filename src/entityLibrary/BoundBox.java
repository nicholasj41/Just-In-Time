package entityLibrary;

import java.io.Serializable;

/**
 * Custom bounding box class that has the same implementation of the BoundingBox class, but implements 
 * the Serializable interface. Has a parameterless constructor to enable deserialization
 */
@SuppressWarnings("unused")
public class BoundBox implements Serializable {

	private double minX;
	private double minY;
	private double minZ;
	private double width;
	private double height;
	private double depth;
	private double maxX;
	private double maxY;
	private double maxZ;
	
	private static final long serialVersionUID = 1L;
	
	/**
	 *  Parameterless constructor for deserialization
	 */
	
	private BoundBox() {
	}
	
	/**
	 * Creates a Bounding Box for the Players and NPC's when created.
	 * 
	 * @param minX - Smallest x position
	 * @param minY - Smallest y position
	 * @param width - Width of the entity
	 * @param height - Height of the entity
	 */
	public BoundBox(double minX, double minY, double width, double height) {
		this.minX = minX;
        this.minY = minY;
        this.minZ = 0;
        this.width = width;
        this.height = height;
        this.depth = 0;
        this.maxX = minX + width;
        this.maxY = minY + height;
        this.maxZ = minZ + depth;
	}
	
	/**
	 * Indicates whether any of the dimensions(width, height or depth) of this bounds is less than zero.
	 */
	public boolean isEmpty() {
        return maxX < minX || maxY < minY || maxZ < minZ;
    }
	
	/**
	 * Tests if the interior of this Bounds intersects the interior of a specified rectangular area.
	 * 
	 * @param x 
	 * @param y
	 * @param w
	 * @param h
	 * @return boolean
	 */
	public boolean intersects(double x, double y, double w, double h) {
        return intersects(x, y, 0, w, h, 0);
    }
	
	/**
	 * Tests if the interior of this Bounds intersects the interior of a specified rectangular area in a 3D plane.
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @param w
	 * @param h
	 * @param d
	 * @return boolean
	 */
	public boolean intersects(double x, double y, double z, double w, double h, double d) {
        if (isEmpty() || w < 0 || h < 0 || d < 0) return false;
        return (x + w >= minX &&
                y + h >= minY &&
                z + d >= minZ &&
                x <= maxX &&
                y <= maxY &&
                z <= maxZ);
    }
	
	/**
	 * Tests if the interior of this Bounds intersects the interior of a specified Bounds, b.
	 * 
	 * @param b - Another BoundingBox object
	 * @return boolean
	 */
	public boolean intersects(BoundBox b) {
        if ((b == null) || b.isEmpty()) return false;
        return intersects(b.getMinX(), b.getMinY(), b.getMinZ(),
                b.getWidth(), b.getHeight(), b.getDepth());
    }
	
	/**
	 * Returns the smallest x value
	 * 
	 * @return minX
	 */
	public double getMinX() {
		return this.minX;
	}
	
	/**
	 * Returns the largest x value
	 * 
	 * @return maxX
	 */
	public double getMaxX() {
		return this.maxX;
	}
	
	/**
	 * Returns the smallest y value
	 * 
	 * @return minY
	 */
	public double getMinY() {
		return this.minY;
	}
	
	/**
	 * Returns the largest y value
	 * 
	 * @return maxY
	 */
	public double getMaxY() {
		return this.maxY;
	}
	
	/**
	 * Returns the smallest z value
	 * 
	 * @return minZ
	 */
	public double getMinZ() {
		return this.minZ;
	}
	
	/**
	 * Returns the width
	 * 
	 * @return width
	 */
	public double getWidth() {
		return this.width;
	}
	
	/**
	 * Returns the height
	 * 
	 * @return height
	 */
	public double getHeight() {
		return this.height;
	}
	
	/**
	 * Returns the depth
	 * 
	 * @return depth
	 */
	public double getDepth() {
		return this.depth;
	}
	
	/**
	 * Returns all the values in String format
	 * 
	 * @return String
	 */
	public String toString() {
        return "BoundBox ["
                + "minX:" + minX
                + ", minY:" + minY
                + ", minZ:" + minZ
                + ", width:" + width
                + ", height:" + height
                + ", depth:" + depth
                + ", maxX:" + maxX
                + ", maxY:" + maxY
                + ", maxZ:" + maxZ
                + "]";
    }

}
