package levelLibrary;

import javax.swing.JLabel;

/**
 * Block class representing an individual block within the levels of the game.
 * 
 * @Primary_Author - Riley Blair (300371586).
 * @Code_Reviewer - Nicholas Jones (300384025).
 * @External_Tester - Finn Welsford-Ackroyd (300379304).
 */
public class Block extends JLabel {
	private static final long serialVersionUID = 1L;
	private BlockType type;
	private boolean isSolid;    
	private boolean visited = false;
	
	/**
	 * Constructor for a block of a particular type.
	 * 
	 * @param type - The type of block that is to be created. This will affect how interactions occur with the block.
	 */
	public Block(BlockType type) {
		this.type = type;
		if (type == BlockType.GROUND) {
			isSolid = true;
		}
	}
	
	/**
	 * Get method for the block type enum.
	 * 
	 * @return The type of block being represented by this object.
	 */
	public BlockType getBlockType() {
		return type;
	}
	
	/**
	 * Get method for whether or not the block is solid.
	 * 
	 * @return Whether or not the block is solid. The ground should be the only solid block.
	 */
	public boolean getIsSolid() {
		return isSolid;
	}
	
	/**
	 * Get method for whether or not a block has been visited.
	 * Mainly for use with checkpoints.
	 * 
	 * @return Whether or not the current block has been visited.
	 */
	public boolean getVisited() {
		return visited;
	}
	
	/**
	 * Set method for the visited status of the current block.
	 * 
	 * @param visited - Whether or not the current block has been visited or not.
	 */
	public void setVisited(boolean visited) {
		this.visited = visited;
	}
}