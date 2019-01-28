package guiLibrary;

import controller.ControllerInterface;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * GUI Key Listener
 *
 * @Primary_Author - Leyton Blackler (300368625).
 * @Code_Reviewer - Riley Blair (300371586).
 * @External_Tester - Celine Young (300378246).
 */
public class GUIKeyListener extends KeyAdapter {

	private GUI gui;
	private ControllerInterface controller;

	private boolean leftPressed = false;
	private boolean rightPressed = false;
	private boolean spacePressed = false;

	public GUIKeyListener(GUI gui, ControllerInterface controller) {
		this.gui = gui;
		this.controller = controller;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		//Get the code of the key pressed by the user.
		int keyCode = e.getKeyCode();

		//Only check for the key released if the GUI is displaying the game.
		if (gui.getCurrentPanelType() == GUI.GUIPanel.GAME) {

			//If the left movement keys are released.
			if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A) {
				leftPressed = false;
				controller.keyLeftRelease();
			}

			//If the right movement keys are released.
			else if (keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D) {
				rightPressed = false;
				controller.keyRightRelease();
			}

			else if (keyCode == KeyEvent.VK_SPACE) spacePressed = false;
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		//Get the code of the key pressed by the user.
		int keyCode = e.getKeyCode();

		//Keyboard input for if the GUI is displaying the game.
		if (gui.getCurrentPanelType() == GUI.GUIPanel.GAME) {

			//Move the player left if the left arrow key or 'A' is pressed.
			if (!leftPressed && (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A)) {
				leftPressed = true;
				controller.setPlayerMovingLeft();
			}

			//Move the player right if the right arrow key or 'D' is pressed.
			else if (!rightPressed && (keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D)) {
				rightPressed = true;
				controller.setPlayerMovingRight();
			}

			else if (!spacePressed && keyCode == KeyEvent.VK_SPACE) {
				spacePressed = true;
				controller.invertGravity();
			}
		}

		//Open the options menu when if the escape key is pressed.
		if (keyCode == KeyEvent.VK_ESCAPE) {
			if (gui.getCurrentPanelType() == GUI.GUIPanel.GAME) {
				gui.setPanel(GUI.GUIPanel.OPTIONS);
			} else if (gui.getCurrentPanelType() == GUI.GUIPanel.OPTIONS) {
				gui.setPanel(GUI.GUIPanel.GAME);
			}
		}
	}
}
