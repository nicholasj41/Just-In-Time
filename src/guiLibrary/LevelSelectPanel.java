package guiLibrary;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import levelLibrary.Level;
import model.ModelInterface;

import static guiLibrary.GUIConstants.Dimensions.PADDING;

/**
 * Level Selection Panel
 *
 * @Primary_Author - Leyton Blackler (300368625).
 * @Code_Reviewer - Riley Blair (300371586).
 * @External_Tester - Celine Young (300378246).
 */
public class LevelSelectPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private ModelInterface model;
	private GUI gui;
	private GUI.GUIPanel parent;
	
	// action listeners for back, save and load button
	private LevelOneActionListener levelOneAction;
	private LevelTwoActionListener levelTwoAction;
	private LevelThreeActionListener levelThreeAction;
	
	// action listeners for back, save and load button
	private BackActionListener backAction;
	private SaveActionListener saveAction;
	private LoadActionListener loadAction;

	private List<LevelButton> levelButtons;
	
	public LevelSelectPanel(ModelInterface model, GUI gui) {
		this.model = model;
		this.gui = gui;
		setPreferredSize(new Dimension(GUIConstants.Dimensions.GAME_WIDTH, GUIConstants.Dimensions.GAME_HEIGHT + GUIConstants.Dimensions.GAME_BAR_HEIGHT));
		setBackground(GUIConstants.Colours.MENU_BACKGROUND);
		
		//Align the panel along the x axis so the elements can be added horizontally
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        this.levelButtons = new ArrayList<>();

        add(Box.createVerticalGlue());
        add(createTitlePanel());
        add(Box.createVerticalStrut(PADDING * 4));
        add(createLevelsPanel());
        add(Box.createVerticalStrut(PADDING * 4));
        add(createButtonsPanel());
        add(Box.createVerticalGlue());
	}
	
	/**
	 * Creates the title panel to be inserted at the top of this panel
	 * 
	 * @return JPanel
	 */
	
	private JPanel createTitlePanel() {
        JLabel header = new JLabel("Level Select");
        header.setFont(GUIConstants.Fonts.ROBOTO_THIN.deriveFont(GUIConstants.scale(46f)));
        header.setForeground(GUIConstants.Colours.MENU_TITLE);

        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
        panel.add(Box.createHorizontalGlue());
        panel.add(header);
        panel.add(Box.createHorizontalGlue());
        return panel;
    }
	
	/**
	 * Creates the level buttons panel that displays all of the levels the user can choose
	 * 
	 * @return JPanel
	 */
	
	private JPanel createLevelsPanel() {
		//Create the panel to hold the level buttons
		JPanel panel = new JPanel();

		//Make the background of the level buttons panel transparent.
		panel.setOpaque(false);

		//Align the buttons in the buttons panel along the y axis.
		panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));

		Level[] levels = model.getLevels();
		
		//Create the button used to return to the main menu.
		LevelButton levelOne = new LevelButton("One");
		levelButtons.add(levelOne);
		levelOneAction = new LevelOneActionListener();
		levelOne.addActionListener(levelOneAction);
		if (levels.length == 0) levelOne.setLocked(false);
		else if (levels[0].isLocked()) levelOne.setLocked(true);

		//Create the button used to return to save the game.
		LevelButton levelTwo = new LevelButton("Two");
		levelButtons.add(levelTwo);
		levelTwoAction = new LevelTwoActionListener();
		levelTwo.addActionListener(levelTwoAction);
		if (levels.length == 0) levelTwo.setLocked(false);
		else if (levels[1].isLocked()) levelTwo.setLocked(true);
		
		//Create the button used to load a game.
		LevelButton levelThree = new LevelButton("Three");
		levelButtons.add(levelThree);
		levelThreeAction = new LevelThreeActionListener();
		levelThree.addActionListener(levelThreeAction);
		if (levels.length == 0) levelThree.setLocked(false);
		else if (model.getLevels()[2].isLocked()) levelThree.setLocked(true);

		// add all of the buttons to the panel
		panel.add(Box.createHorizontalGlue());
		panel.add(levelOne);
		panel.add(Box.createHorizontalGlue());
		panel.add(levelTwo);
		panel.add(Box.createHorizontalGlue());
		panel.add(levelThree);
		panel.add(Box.createHorizontalGlue());

		return panel;
	}
	
	/**
	 * Creates the buttons panel that displays the different options the user can click
	 * 
	 * @return JPanel
	 */
	
	private JPanel createButtonsPanel() {
		//Create the panel to hold the buttons.
		JPanel panel = new JPanel();

		//Make the background of the buttons panel transparent.
		panel.setOpaque(false);

		//Align the buttons in the buttons panel along the y axis.
		panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));

		//Create the button used to return to previous menu.
		Button backButton = new Button("Back");
		backAction = new BackActionListener();
		backButton.addActionListener(backAction);

		//Create the button used to return to save the game.
		Button saveButton = new Button("Save Game");
		saveAction = new SaveActionListener();
		saveButton.addActionListener(saveAction);

		//Create the button used to load a game.
		Button loadButton = new Button("Load");
		loadAction = new LoadActionListener();
		loadButton.addActionListener(loadAction);
		
		// add all of the buttons to the panel
		panel.add(Box.createHorizontalGlue());
		panel.add(backButton);
		panel.add(Box.createHorizontalStrut(PADDING));
		panel.add(saveButton);
		panel.add(Box.createHorizontalStrut(PADDING));
		panel.add(loadButton);
		panel.add(Box.createHorizontalGlue());
				
		// create the panel that holds the button panel
		JPanel buttons = new JPanel();
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.LINE_AXIS));
        buttons.setOpaque(false);
        buttons.add(Box.createHorizontalGlue());
        buttons.add(panel);
        buttons.add(Box.createHorizontalGlue());
        
        return buttons;
	}

	/**
	 * Re-validates and repaints the level buttons.
	 */
	public void refreshButtons() {
		Level[] levels = model.getLevels();
		for (int i = 0; i < levels.length; i++) {
			levelButtons.get(i).setLocked(levels[i].isLocked());
			levelButtons.get(i).revalidate();
			levelButtons.get(i).repaint();
		}
	}
	
	/**
     * The action performed when pressing the level one button.
     */
	private class LevelOneActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// will only enter the else statement during testing
			if (model.getLevels().length != 0) {
				if (!model.getLevels()[0].isLocked()) {
					gui.getController().setLevel(1);
					gui.setPanel(GUI.GUIPanel.GAME);
					revalidate();
					repaint();
				}
			} else {
				gui.getController().setLevel(1);
				gui.setPanel(GUI.GUIPanel.GAME);
				revalidate();
				repaint();
			}
		}
	}
	
	/**
	 * Simulates pressing the level one button
	 */
	
	public void simulateLevelOne() {
        levelOneAction.actionPerformed(null);
    }
    
	/**
	 * The action performed when pressing the level two button.
	 */
	private class LevelTwoActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// will only enter the else statement during testing
			if (model.getLevels().length != 0) {
				if (!model.getLevels()[1].isLocked()) {
					gui.getController().setLevel(2);
					gui.setPanel(GUI.GUIPanel.GAME);
					revalidate();
					repaint();
				}
			} else {
				gui.getController().setLevel(2);
				gui.setPanel(GUI.GUIPanel.GAME);
				revalidate();
				repaint();
			}
		}
	}

	/**
	 * Simulates pressing the level two button
	 */
	
	public void simulateLevelTwo() {
        levelTwoAction.actionPerformed(null);
    }
	
	/**
	 * The action performed when pressing the level three button.
	 */
	private class LevelThreeActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// will only enter the else statement during testing
			if (model.getLevels().length != 0) {
				if (!model.getLevels()[2].isLocked()) {
					gui.getController().setLevel(3);
					gui.setPanel(GUI.GUIPanel.GAME);
					revalidate();
					repaint();
				}
			} else {
				gui.getController().setLevel(3);
				gui.setPanel(GUI.GUIPanel.GAME);
				revalidate();
				repaint();
			}
		}
	}
	
	/**
	 * Simulates pressing the level three button
	 */
	
	public void simulateLevelThree() {
        levelThreeAction.actionPerformed(null);
    }

    /**
     * The action performed when pressing the back button.
     */
    private class BackActionListener implements ActionListener {
    	@Override
    	public void actionPerformed(ActionEvent e) {
    		gui.setPanel(parent);
    	}
    }
    
    /**
	 * Simulates pressing the back button
	 */
    
    public void simulateBack() {
        backAction.actionPerformed(null);
    }
    
    /**
     * The action performed when pressing the save game button.
     */
    private class SaveActionListener implements ActionListener {
    	@Override
    	public void actionPerformed(ActionEvent e) {
    		gui.getController().save(JOptionPane.showInputDialog("Enter a name for the save file:"));
    		revalidate();
    		repaint();
    	}
    }
    
    /**
	 * Simulates pressing the save button
	 */
    
    public void simulateSave() {
        saveAction.actionPerformed(null);
    }
    
    /**
     * The action performed when pressing the load game button.
     */
    private class LoadActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
        	// filters out all the folders other than text files
        	JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Text files", "txt");
            chooser.setFileFilter(filter);
            int returnVal = chooser.showOpenDialog(LevelSelectPanel.this);
            // sends to the controller to process
            if(returnVal == JFileChooser.APPROVE_OPTION) {
				gui.getController().load(chooser.getSelectedFile());
				gui.setPanel(GUI.GUIPanel.GAME);
            }
        }
    }
    
    /**
	 * Simulates pressing the load button
	 */
    
    public void simulateLoad() {
        loadAction.actionPerformed(null);
    }

    /**
     * Sets the parent of this panel
     * @param parent The menu panel where the level select button was clicked
     */
    
    public void setParent(GUI.GUIPanel parent) {
    	this.parent = parent;
    }
    
}
