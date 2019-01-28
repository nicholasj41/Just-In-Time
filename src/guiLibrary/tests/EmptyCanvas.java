package guiLibrary.tests;

import guiLibrary.AbstractCanvas;
import guiLibrary.GUIConstants;

import java.awt.*;

/**
 * Creates an empty canvas
 */
public class EmptyCanvas extends AbstractCanvas {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Creates an empty canvas with the size game_width and game_height
	 */
	public EmptyCanvas() {
        setPreferredSize(new Dimension(GUIConstants.Dimensions.GAME_WIDTH, GUIConstants.Dimensions.GAME_HEIGHT));
        setBackground(GUIConstants.Colours.CANVAS_BACKGROUND);
    }
}
