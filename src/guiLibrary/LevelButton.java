package guiLibrary;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JButton;

/**
 * Class for a button that is used to select the level
 *
 */

public class LevelButton extends JButton {
	
	/**
	 * Default serial ID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Whether the level is locked or not
	 */
	private boolean locked;

	public LevelButton(String name) {
		super(name);
		setBorderPainted(false);
		setRolloverEnabled(true);
		locked = false;
		setStyle();
	}

	/**
	 * Sets the style of the button.
	 */
	public void setStyle() {
		Dimension size = getSize();
		size = new Dimension(2 * GUIConstants.Dimensions.BUTTON_WIDTH, GUIConstants.Dimensions.BUTTON_WIDTH);
		setPreferredSize(size);
		setMaximumSize(size);
	}
	
	/**
     * Sets whether the button is locked or not
     * @param locked
     */
    public void setLocked(boolean locked) {
    	this.locked = locked;
    }
    
    /**
     * Displays the button
     */
    @Override
    protected void paintComponent(Graphics g) {
		Graphics2D g2D = (Graphics2D) g;
		RoundRectangle2D button = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 8, 8);

		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		// sets the default fill colour to be 0% opacity black
		g2D.setColor(new Color(0, 0, 0, 0));

		if (getText().equals("One")) g2D.drawImage(GUIConstants.Images.LEVEL_IMAGES[0], null, null);
		else if (getText().equals("Two")) g2D.drawImage(GUIConstants.Images.LEVEL_IMAGES[1], null, null);
		else g2D.drawImage(GUIConstants.Images.LEVEL_IMAGES[2], null, null);

		// draws a grey overlay if button is locked
		if (locked) {
			g2D.setColor(GUIConstants.Colours.BUTTON_LOCKED);
			g2D.fill(button);
		} else {
			// react to rollover and presses
			if (getModel().isPressed()) g2D.setColor(GUIConstants.Colours.LEVEL_BUTTON_PRESSED);
			else if (getModel().isRollover()) g2D.setColor(GUIConstants.Colours.LEVEL_BUTTON_HOVER);
		}

		g2D.fill(button);

		if (locked) {
			if (getModel().isRollover()) {
				int x = getWidth() / 2 - GUIConstants.Images.LOCK_BIG.getWidth(null) / 2;
				int y = getHeight() / 2 - GUIConstants.Images.LOCK_BIG.getHeight(null) / 2;
				g2D.drawImage(GUIConstants.Images.LOCK_BIG, x, y, null);
			} else {
				int x = getWidth() / 2 - GUIConstants.Images.LOCK_SMALL.getWidth(null) / 2;
				int y = getHeight() / 2 - GUIConstants.Images.LOCK_SMALL.getHeight(null) / 2;
				g2D.drawImage(GUIConstants.Images.LOCK_SMALL, x, y, null);
			}
		}
    }
}
