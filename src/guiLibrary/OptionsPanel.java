package guiLibrary;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Options Panel
 *
 * @Primary_Author - Leyton Blackler (300368625).
 * @Code_Reviewer - Riley Blair (300371586).
 * @External_Tester - Celine Young (300378246).
 */
public class OptionsPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private GUI gui;
	
	/**
	 * Constructor for a new options panel that appears as a menu and when the user presses 'esc'.
	 * 
	 * @param gui - The GUI to hold the panel.
	 */
    public OptionsPanel(GUI gui) {
        this.gui = gui;
        setPreferredSize(new Dimension(GUIConstants.Dimensions.GAME_WIDTH, GUIConstants.Dimensions.GAME_HEIGHT + GUIConstants.Dimensions.GAME_BAR_HEIGHT));
        setBackground(GUIConstants.Colours.MENU_BACKGROUND);

        //Align the panel along the y axis so the elements can be added vertically.
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        add(Box.createVerticalGlue());
        add(createTitlePanel());
        add(Box.createVerticalStrut(GUIConstants.Dimensions.PADDING * 2));
        add(createButtonsPanel());
        add(Box.createVerticalGlue());
    }
    
    /**
     * Creates the title panel section of the panel.
     * 
     * @return The JPanel that holds the content for the title.
     */
    private JPanel createTitlePanel() {
        JLabel label = new JLabel("Options");
        label.setFont(GUIConstants.Fonts.ROBOTO_THIN.deriveFont(GUIConstants.scale(46f)));
        label.setForeground(GUIConstants.Colours.MENU_TITLE);

        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
        panel.add(Box.createHorizontalGlue());
        panel.add(label);
        panel.add(Box.createHorizontalGlue());
        return panel;
    }
    
    /**
     * Creates the panel of buttons to be used within the options panel.
     * 
     * @return The panel holding a series of buttons that the user can click.
     */
    private JPanel createButtonsPanel() {
        //Create the panel to hold the buttons.
        JPanel panel = new JPanel();

        //Make the background of the buttons panel transparent.
        panel.setOpaque(false);

        //Align the buttons in the buttons panel along the y axis.
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        //Create the button used to return to the main menu.
        Button resumeGameButton = new Button("Resume Game");
        resumeGameButton.addActionListener(new ResumeGameActionListener());

        //Create the button used to return to save the game.
        Button saveGameButton = new Button("Save Game");
        saveGameButton.addActionListener(new SaveGameActionListener());

        //Create the button used to select a level.
        Button selectLevelButton = new Button("Select Level");
        selectLevelButton.addActionListener(new SelectLevelActionListener());

        //Create the button used to return to the main menu.
        Button returnToMenuButton = new Button("Return To Menu");
        returnToMenuButton.addActionListener(new ReturnToMenuActionListener());

        //Create the button used to quit the game.
        Button quitButton = new Button("Quit");
        quitButton.addActionListener(new QuitActionListener());

        panel.add(Box.createVerticalGlue());
        panel.add(resumeGameButton);
        panel.add(Box.createVerticalStrut(GUIConstants.Dimensions.PADDING));
        panel.add(saveGameButton);
        panel.add(Box.createVerticalStrut(GUIConstants.Dimensions.PADDING));
        panel.add(selectLevelButton);
        panel.add(Box.createVerticalStrut(GUIConstants.Dimensions.PADDING));
        panel.add(returnToMenuButton);
        panel.add(Box.createVerticalStrut(GUIConstants.Dimensions.PADDING));
        panel.add(quitButton);
        panel.add(Box.createVerticalGlue());

        panel.setMaximumSize(panel.getPreferredSize());

        JPanel buttons = new JPanel();
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.LINE_AXIS));
        buttons.setOpaque(false);
        buttons.add(Box.createHorizontalGlue());
        buttons.add(panel);
        buttons.add(Box.createHorizontalGlue());

        return buttons;
    }

    /**
     * The action performed when pressing the resume game button.
     */
    private class ResumeGameActionListener implements ActionListener {
    		
        @Override
        public void actionPerformed(ActionEvent e) {
            gui.setPanel(GUI.GUIPanel.GAME);
        }
    }

    /**
     * The action performed when pressing the resume game button.
     */
    private class SaveGameActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            gui.getController().save(JOptionPane.showInputDialog("Enter a name for the save file:"));
            revalidate();
            repaint();
        }
    }
    
    /**
     * The action performed when pressing the select level button.
     */
    private class SelectLevelActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            gui.setPanel(GUI.GUIPanel.LEVEL_SELECT);
            gui.getLevelSelectPanel().setParent(GUI.GUIPanel.OPTIONS);
        }
    }


    /**
     * The action performed when pressing the return to menu button.
     */
    private class ReturnToMenuActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            gui.setPanel(GUI.GUIPanel.MAIN_MENU);
        }
    }

    /**
     * The action performed when pressing the quit button.
     */
    private class QuitActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

}
