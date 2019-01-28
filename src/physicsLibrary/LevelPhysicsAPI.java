package physicsLibrary;
import levelLibrary.Level;

import java.util.List;

import levelLibrary.Block;

public class LevelPhysicsAPI {
	/**
	 * Converts a Level to the array of booleans that Physics2D uses for collision detection.
	 * Sends the level layout to blockListToBooleanArray.
	 * Relies on the level layout remaining public, blockListToBooleanArray is more robust.
	 * 
	 * @param level - The level to convert.
	 * 
	 * @return The boolean array representation.
	 */
	public static boolean[][] levelToBooleanArray(Level level) {
		List<List<Block>> blockList = level.getLevelLayout();
		return blockListToBooleanArray(blockList);
	}
	
	/**
	 * Converts a nested List to the 2D array of booleans that Physics2D uses for collision detection.
	 * The 2D boolean array represents a grid where true = solid block,	 and false = non-solid block.
	 * 
	 * @param blockList - The list of blocks making a level.
	 * 
	 * @return The boolean representation of the level.
	 */
	public static boolean[][] blockListToBooleanArray(List<List<Block>> blockList) {
		int xSize = blockList.size();
		int ySize = blockList.get(0).size();
		boolean[][] blockArray = new boolean[xSize][ySize];
		for(int x = 0; x < xSize; x++){
			for(int y = 0; y < ySize; y++){
				Block block = blockList.get(x).get(y);
				if(block == null || !block.getIsSolid()){
					blockArray[x][y] = false;
				}else{
					blockArray[x][y] = true;
				}
			}
		}
		return blockArray;
	}
	
	/**
	 * Creates a random boolean array representation of a level.
	 * 
	 * @return - The string representation of the level as a boolean array.
	 */
	public static String randomLevelToString() {
		Level level = new Level(30);
		boolean[][] layout = levelToBooleanArray(level);
		StringBuilder out = new StringBuilder();
		out.append('{');
		for(int x = 0; x < layout.length; x++) {
			out.append('{');
			for(int y = 0; y < layout[0].length; y++) {
				out.append(layout[x][y]);
				if(y != layout[0].length - 1) {
					out.append(',');
				}
			}
			out.append('}');
			if(x != layout.length - 1) {
				out.append(",\n");
			}
		}
		out.append('}');
		String str = out.toString();
		System.out.println(str);
		return str;
	}
}
