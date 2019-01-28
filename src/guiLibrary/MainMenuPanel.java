package guiLibrary;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * GUI Main Menu Panel
 *
 * @Primary_Author - Leyton Blackler (300368625).
 * @Code_Reviewer - Riley Blair (300371586).
 * @External_Tester - Celine Young (300378246).
 */
public class MainMenuPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private GUI gui;
	private StartActionListener startAction;

    public MainMenuPanel(GUI gui) {
        this.gui = gui;
        setPreferredSize(new Dimension(GUIConstants.Dimensions.MENU_WIDTH, GUIConstants.Dimensions.MENU_HEIGHT));
        setBackground(GUIConstants.Colours.MENU_BACKGROUND);

        //Align the panel along the y axis so the elements can be added vertically.
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        add(Box.createVerticalGlue());
        add(Box.createVerticalStrut(GUIConstants.Dimensions.PADDING / 2));
        add(createTitlePanel());
        add(Box.createVerticalStrut(GUIConstants.Dimensions.PADDING / 2));
        add(createButtonsPanel());
        add(Box.createVerticalGlue());
    }

    /**
     * Creates title panel and returns it as a JPanel
     * 
     * @return panel - Title Panel
     */
    private JPanel createTitlePanel() {
        JLabel label = new JLabel("Just In Time");
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
     * Creates the buttons panel and returns it as a JPanel
     * 
     * @return panel - Buttons Panel
     */
    private JPanel createButtonsPanel() {
        //Create the panel to hold the buttons.
        JPanel panel = new JPanel();
        //Make the background of the buttons panel transparent.
        panel.setOpaque(false);

        //Align the buttons in the buttons panel along the x axis.
        panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));

        //Create the button used to start a new game.
        Button startButton = new Button("Start");
        startAction = new StartActionListener();
        startButton.addActionListener(startAction);

        //Create the button used to quit the game.
        Button quitButton = new Button("Quit");
        quitButton.addActionListener(new QuitActionListener());
        
        panel.add(Box.createHorizontalGlue());
        panel.add(startButton);
        panel.add(Box.createHorizontalStrut(GUIConstants.Dimensions.PADDING));
        panel.add(quitButton);
        panel.add(Box.createHorizontalGlue());
        
        return panel;
    }

    /**
     * The action performed when pressing the start button.
     */
    public class StartActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
        	gui.setPanel(GUI.GUIPanel.LEVEL_SELECT);
            gui.getLevelSelectPanel().setParent(GUI.GUIPanel.MAIN_MENU);
        }
    }

    /**
     * Simulates pressing the start button.
     */
    public void simulateStart() {
        startAction.actionPerformed(null);
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
