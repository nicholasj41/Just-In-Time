package saveLoadLibrary;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;

/**
 * Temp class for testing until the main game class is setup with fields to be saved
 * Game has basic GUI, where if you press a button, the background changes to that colour
 * And you can save the game whenever you like
 *
 */

public class TestGame implements Savable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * The name of the game
	 */
	private String name;
	
	/**
	 * A random number
	 */
	private double randNum;
	
	/**
	 * Another random number
	 */
	private double randNum2;
	
	/*
	 * The gui
	 */
	private JFrame gui;
	
	/**
	 * The content panel of the gui
	 */
	private JPanel content;
	
	/**
	 * The background colour of the gui
	 */
	private Color background;

	public TestGame(String name, double randNum, double randNum2) {
		this.name = name;
		this.randNum = randNum;
		this.randNum2 = randNum2;
		this.gui = new JFrame();
		background = Color.white;
		initGUI();
	}
	
	/**
	 * Initialises the GUI, with the default background colour being white 
	 */
	
	private void initGUI() {
		gui.setResizable(false);
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gui.setPreferredSize(new Dimension(900, 700));
		// adds white jpanel
		content = new JPanel(new BorderLayout());
		background = Color.white;
		gui.add(content);
		// create a toolbar
		makeToolBar();
		draw();
		gui.pack();
		gui.setVisible(true);
	}
	
	/**
	 * Redraws the GUI 
	 */
	
	private void draw() {
		content.setBackground(background);
		gui.revalidate();
		gui.repaint();
	}
	
	/**
	 * Toolbar with four buttons - red, green, blue and save 
	 */
	
	private void makeToolBar() {
		JToolBar toolBar = new JToolBar();
		addColourButtons(toolBar);
		addSaveLoadButtons(toolBar);
		
		// add toolBar to JFrame
		gui.add(toolBar, BorderLayout.NORTH);
	}
	
	/**
	 * Adds the saving and loading buttons to the tool bar
	 * @param toolBar The toolbar to add the buttons to
	 */
	public void addSaveLoadButtons(JToolBar toolBar) {
		// saves the game state
		JButton save = new JButton("Save");
		// loads a game
		JButton load = new JButton("Load");
		
		toolBar.add(save);
		toolBar.add(load);
	}
	
	/**
	 * Adds the buttons that change the colour of the background
	 * @param toolBar The toolbar to add the buttons to
	 */
	public void addColourButtons(JToolBar toolBar) {
		// changes the background to be red
		JButton red = new JButton("Red");
		// changes the background to be red
		JButton green = new JButton("Green");

		// changes the background to be blue
		JButton blue = new JButton("Blue");
		
		// add buttons to toolBar
		toolBar.add(red);
		toolBar.add(green);
		toolBar.add(blue);
	}
	
	/**
	 * Returns a description of this game
	 */
	public String toString() {
		StringBuilder str = new StringBuilder("TestGame object with\nname: " + name);
		str.append("\nrandNum: " + randNum);
		str.append("\nrandNum2: " + randNum2);
		str.append("\n background: " + background);
		
		return str.toString();
	}
	
	/**
	 * Returns the name of this game
	 * @return String
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Returns randNum
	 * @return double
	 */
	public double getRandNum() {
		return randNum;
	}
	
	/**
	 * Returns randNum2
	 * @return double
	 */
	public double getRandNum2() {
		return randNum2;
	}
	
	/**
	 * Returns the colour of the GUI in this game
	 * @return Color
	 */
	public Color getBackground() {
		return background;
	}
}
