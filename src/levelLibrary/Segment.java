package levelLibrary;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Segment enumeration class that handles all the different types of segments that build the level. 
 * 
 * @Primary_Author - Riley Blair (300371586).
 * @Code_Reviewer - Nicholas Jones (300384025).
 * @External_Tester - Finn Welsford-Ackroyd (300379304).
 */
public enum Segment {
	SEGMENT0 (
		new Block(BlockType.GROUND),
		new Block(BlockType.GROUND),
		null,
		null,
		null,
		null,
		null,
		null,
		new Block(BlockType.GROUND),
		new Block(BlockType.GROUND) 
	),
	SEGMENT1 (
		new Block(BlockType.GROUND),
		null,
		null,
		null,
		null,
		null,
		null,
		null,
		new Block(BlockType.GROUND),
		new Block(BlockType.GROUND) 
	),
	SEGMENT2 (
		new Block(BlockType.GROUND),
		new Block(BlockType.GROUND),
		null,
		null,
		null,
		null,
		null,
		null,
		null,
		new Block(BlockType.GROUND) 
	),
	SEGMENT3 (
		new Block(BlockType.GROUND),
		null,
		null,
		null,
		null,
		null,
		null,
		null,
		null,
		new Block(BlockType.GROUND) 
	),
	SEGMENT4 (
		new Block(BlockType.GROUND),
		new Block(BlockType.GROUND),
		new Block(BlockType.GROUND),
		null,
		null,
		null,
		null,
		null,
		new Block(BlockType.GROUND),
		new Block(BlockType.GROUND) 
	),
	SEGMENT5 (
		new Block(BlockType.GROUND),
		new Block(BlockType.GROUND),
		null,
		null,
		null,
		null,
		null,
		new Block(BlockType.GROUND),
		new Block(BlockType.GROUND),
		new Block(BlockType.GROUND) 
	),
	SEGMENT6 (
		new Block(BlockType.GROUND),
		new Block(BlockType.GROUND),
		new Block(BlockType.GROUND),
		null,
		null,
		null,
		null,
		new Block(BlockType.GROUND),
		new Block(BlockType.GROUND),
		new Block(BlockType.GROUND) 
	),
	SEGMENT7 (
		new Block(BlockType.GROUND),
		new Block(BlockType.GROUND),
		null,
		null,
		new Block(BlockType.GROUND),
		null,
		null,
		null,
		new Block(BlockType.GROUND),
		new Block(BlockType.GROUND) 
	),
	SEGMENT8 (
		new Block(BlockType.GROUND),
		new Block(BlockType.GROUND),
		null,
		null,
		null,
		new Block(BlockType.GROUND),
		null,
		null,
		new Block(BlockType.GROUND),
		new Block(BlockType.GROUND) 
	),
	SEGMENT9 (
		new Block(BlockType.GROUND),
		new Block(BlockType.GROUND),
		null,
		null,
		new Block(BlockType.GROUND),
		new Block(BlockType.GROUND),
		null,
		null,
		new Block(BlockType.GROUND),
		new Block(BlockType.GROUND) 
	);
	
	private final ArrayList<Block> segment;
	
	/**
	 * Constructor for a new segment enumeration. 
	 * Creates the one-dimensional arraylist to be added to the two-dimensional arraylist in a level.
	 * 
	 * @param block1 - The first block in the segment.
	 * @param block2 - The second block in the segment.
	 * @param block3 - The third block in the segment.
	 * @param block4 - The fourth block in the segment.
	 * @param block5 - The fifth block in the segment.
	 * @param block6 - The sixth block in the segment.
	 * @param block7 - The seventh block in the segment.
	 * @param block8 - The eighth block in the segment.
	 * @param block9 - The ninth block in the segment.
	 * @param block10 - The tenth block in the segment.
	 */
	Segment(Block block1, Block block2, Block block3, Block block4, Block block5, Block block6, Block block7, Block block8, Block block9, Block block10) {
		this.segment = new ArrayList<Block>(Arrays.asList(block1, block2, block3, block4, block5, block6, block7, block8, block9, block10));
	}
	
	/**
	 * Get method for getting the one-dimensional arraylist representation of the segment.
	 * 
	 * @return The one-dimensional arraylist representation of the segment.
	 */
	public ArrayList<Block> getSegment(){
		return this.segment;
	}
}