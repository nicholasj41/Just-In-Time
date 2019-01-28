package guiLibrary;

import javax.swing.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.*;

/**
 * Button Class
 *
 * @Primary_Author - Leyton Blackler (300368625).
 * @Code_Reviewer - Riley Blair (300371586).
 * @External_Tester - Celine Young (300378246).
 */
public class Button extends JButton {

	private static final long serialVersionUID = 1L;
    private Font font;
    
    /**
     * Constructor for a new button.
     * 
     * @param name - The name to be displayed on the button.
     */
    public Button(String name) {
        super(name);
        setBorderPainted(false);
        setRolloverEnabled(true);
        setStyle();
    }

    /**
     * Sets the style of the button.
     * 
     * @param style - The style type to set the button to appear as.
     */
    public void setStyle() {
    	Dimension size = getSize();
    	size = new Dimension(GUIConstants.Dimensions.BUTTON_WIDTH, GUIConstants.Dimensions.BUTTON_HEIGHT);
    	font = GUIConstants.Fonts.ROBOTO_BOLD.deriveFont(GUIConstants.scale(12f));
    	setPreferredSize(size);
    	setMaximumSize(size);
    }

    /**
     * Overrides the paint component method to provide the correct button styles.
     * 
     * @param g - The graphics object used to draw the button.
     */
    @Override
    protected void paintComponent(Graphics g) {
    		Graphics2D g2D = (Graphics2D) g;
    		RoundRectangle2D button = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 8, 8);

    		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    		g2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

    		if (getModel().isPressed()) g2D.setColor(GUIConstants.Colours.BUTTON_BACKGROUND_PRESSED);
    		else if (getModel().isRollover()) g2D.setColor(GUIConstants.Colours.BUTTON_BACKGROUND_HOVER);
    		else g2D.setColor(GUIConstants.Colours.BUTTON_BACKGROUND_NORMAL);
    		// fills the button with the specified colour
    		g2D.fill(button);

    		// react to mouse hovers/clicks
    		if (getModel().isPressed()) g2D.setColor(GUIConstants.Colours.BUTTON_TEXT_PRESSED);
    		else if (getModel().isRollover()) g2D.setColor(GUIConstants.Colours.BUTTON_TEXT_HOVER);
    		else g2D.setColor(GUIConstants.Colours.BUTTON_TEXT_NORMAL);
    	
    		FontMetrics metrics = g.getFontMetrics(font);
    		int x = (getWidth() - metrics.stringWidth(getText())) / 2;
    		int y = ((getHeight() - metrics.getHeight()) / 2) + metrics.getAscent();

    		g2D.setFont(font);
    		g2D.drawString(getText(), x, y);

    		g2D.dispose();
    }
}
