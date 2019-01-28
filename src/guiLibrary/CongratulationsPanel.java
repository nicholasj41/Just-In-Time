package guiLibrary;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Congratulations Screen
 *
 * @Primary_Author - Leyton Blackler (300368625).
 * @Code_Reviewer - Riley Blair (300371586).
 * @External_Tester - Celine Young (300378246).
 */
public class CongratulationsPanel extends JPanel {
	private static final long serialVersionUID = 1L;

    /**
     * The GUI that the panel is displayed on.
     */
    private GUI gui;

    /**
     * Creates a panel to be used to congratulate the player upon winning.
     * @param gui The GUI that the screen is displayed on.
     */
    public CongratulationsPanel(GUI gui) {
        this.gui = gui;
        setPreferredSize(new Dimension(GUIConstants.Dimensions.GAME_WIDTH, GUIConstants.Dimensions.GAME_HEIGHT));
        setBackground(GUIConstants.Colours.CANVAS_BACKGROUND);

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        add(Box.createVerticalGlue());
        add(createTitlePanel());
        add(Box.createVerticalStrut(GUIConstants.Dimensions.PADDING));
        add(createButtonsPanel());
        add(Box.createVerticalGlue());
    }

    /**
     * Creates a section for the title.
     * @return The panel for the title section.
     */
    private JPanel createTitlePanel() {
        JLabel label = new JLabel("Congratulations! You won!");
        label.setFont(GUIConstants.Fonts.ROBOTO_THIN.deriveFont(GUIConstants.scale(46f)));
        label.setForeground(GUIConstants.Colours.MENU_TITLE);
        JPanel titlePanel = new JPanel();
        titlePanel.setOpaque(false);
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.LINE_AXIS));
        titlePanel.add(Box.createHorizontalGlue());
        titlePanel.add(label);
        titlePanel.add(Box.createHorizontalGlue());
        return titlePanel;
    }

    /**
     * Creates a section for the buttons.
     * @return The panel for the buttons section.
     */
    private JPanel createButtonsPanel() {
        //Create the panel to hold the buttons.
        JPanel panel = new JPanel();

        //Make the background of the buttons panel transparent.
        panel.setOpaque(false);

        //Align the buttons in the buttons panel along the y axis.
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        
        //Create the button used to return to the main menu.
        Button returnToMenuButton = new Button("Return To Menu");
        returnToMenuButton.addActionListener(new CongratulationsPanel.ReturnToMenuActionListener());

        //Create the button used to quit the game.
        Button quitButton = new Button("Quit");
        quitButton.addActionListener(new CongratulationsPanel.QuitActionListener());

        panel.add(Box.createVerticalGlue());
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
