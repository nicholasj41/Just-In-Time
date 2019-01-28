package guiLibrary;

import controller.ControllerInterface;
import levelLibrary.LevelType;
import model.ModelInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Game Over Screen
 *
 * @Primary_Author - Leyton Blackler (300368625).
 * @Code_Reviewer - Riley Blair (300371586).
 * @External_Tester - Celine Young (300378246).
 */
public class GameOverPanel extends JPanel {
	private static final long serialVersionUID = 1L;

    /**
     * The model that the game over screen checks to see if it is game over.
     */
	private ModelInterface model;

    /**
     * The controller used to send input to the model.
     */
    private ControllerInterface controller;

    /**
     * Creates a panel to inform the player that it is game over.
     * @param model The model of the current instance of the game.
     * @param controller The controller used to send input to the model.
     */
    public GameOverPanel(ModelInterface model, ControllerInterface controller) {
        this.model = model;
        this.controller = controller;
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
        JLabel label = new JLabel("Game Over");
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

        //Create the button used to try again from the start.
        Button tryAgainStartButton = new Button("Restart Level");
        tryAgainStartButton.addActionListener(new GameOverPanel.TryAgainStartActionListener());

        //Create the button used to try again from the start.
        Button tryAgainCheckpointButton = new Button("From Checkpoint");
        tryAgainCheckpointButton.addActionListener(new GameOverPanel.TryAgainCheckpointActionListener());

        panel.add(Box.createVerticalGlue());
        panel.add(tryAgainStartButton);
        panel.add(Box.createVerticalStrut(GUIConstants.Dimensions.PADDING));
        panel.add(tryAgainCheckpointButton);
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
    private class TryAgainStartActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            LevelType level = model.getCurrentLevel().getLevelType();
            if (level == LevelType.CUBA) controller.setLevel(1);
            else if (level == LevelType.KELBURN) controller.setLevel(2);
            else if (level == LevelType.COTTON) controller.setLevel(3);
        }
    }

    /**
     * The action performed when pressing the resume game button.
     */
    private class TryAgainCheckpointActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            controller.respawn();
        }
    }

}
