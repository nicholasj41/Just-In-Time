package guiLibrary;

import guiLibrary.tests.EmptyCanvas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.awt.*;

import static guiLibrary.GUIConstants.FPS;

/**
 * Game Panel
 *
 * @Primary_Author - Leyton Blackler (300368625).
 * @Code_Reviewer - Riley Blair (300371586).
 * @External_Tester - Celine Young (300378246).
 */
public class GamePanel extends JPanel {
	
	/**
	 * Enum for the different types of panels the game panel can display
	 */
    private enum Content {
        GAME,
        GAME_OVER,
        CONGRATULATIONS
    }

    private static final long serialVersionUID = 1L;
    private GUI gui;
    private GamePanel gamePanel;
    private AbstractCanvas canvas;
    private JPanel gameOver;
    private JPanel congratulations;
    private JPanel content;
    private Timer timer;

    JLabel levelLabel = new JLabel();
    JLabel timeLabel = new JLabel();
    JLabel coinsLabel = new JLabel();

    private JLabel batteryLevel = new JLabel();

    private Content currentScreen = Content.GAME;

    public GamePanel(GUI gui) {
        this.gamePanel = this;
        this.gui = gui;
        this.canvas = new EmptyCanvas();
        this.gameOver = new GameOverPanel(gui.getModel(), gui.getController());
        this.congratulations = new CongratulationsPanel(gui);
        this.content = new JPanel();
        this.content.setLayout(new BoxLayout(content, BoxLayout.PAGE_AXIS));
        this.content.add(canvas);
        //this.content.setPreferredSize(new Dimension(GUIConstants.Dimensions.GAME_WIDTH, GUIConstants.Dimensions.GAME_HEIGHT));
        //this.content.setMinimumSize(new Dimension(GUIConstants.Dimensions.GAME_WIDTH, GUIConstants.Dimensions.GAME_HEIGHT));
        //this.content.setMaximumSize(new Dimension(GUIConstants.Dimensions.GAME_WIDTH, GUIConstants.Dimensions.GAME_HEIGHT));
        setBackground(GUIConstants.Colours.GAME_BUTTON_BAR);
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        add(Box.createVerticalStrut(GUIConstants.Dimensions.PADDING / 4));
        add(createButtonsPanel());
        add(Box.createVerticalStrut(GUIConstants.Dimensions.PADDING / 4));
        add(content);

        //Render the canvas at the frame rate defined in the GUIConstants class.
        timer = new Timer(1000 / FPS, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //Check the current battery level.
                checkBatteryLevel();

                if (!gui.getModel().isGameOver() && currentScreen != Content.CONGRATULATIONS) {
                    levelLabel.setText("Level:    " + gui.getModel().getCurrentLevelNumber());
                    timeLabel.setText("Time left:    " + gui.getModel().getTimeRemaining());
                    coinsLabel.setText("Coins:    " + gui.getModel().getCoins());
                }

                if (gui.getModel().isGameWon()) {
                    if (currentScreen != Content.CONGRATULATIONS) {
                        content.removeAll();
                        content.add(congratulations);
                        currentScreen = Content.CONGRATULATIONS;
                    }
                } else {
                    if (gui.getModel().isGameOver()) {
                        if (currentScreen != Content.GAME_OVER) {
                            content.removeAll();
                            content.add(gameOver);
                            currentScreen = Content.GAME_OVER;
                        }
                    }

                    //If it's not game over, then render the canvas.
                    else {
                        if (currentScreen != Content.GAME) {
                            content.removeAll();
                            content.add(canvas);
                            currentScreen = Content.GAME;
                        }
                    }
                }

                //Repaint the window to avoid graphical glitches with the button.
                SwingUtilities.getWindowAncestor(gamePanel).repaint();
                revalidate();
                repaint();
                //content.revalidate();
                //content.repaint();
            }
        });
    }
    
    /**
     * Sets the canvas to be displayed
     * @param canvas The canvas to be set
     */
    public void setCanvas(AbstractCanvas canvas) {
        this.content.remove(this.canvas);
        this.content.add(canvas);
        this.canvas = canvas;
        //SwingUtilities.getWindowAncestor(this).pack();
    }
    
    /**
     * Checks the battery level and displays the correct image
     */
    private void checkBatteryLevel() {
        int batteryPercent = gui.getModel().getCurrentBatteryPercent();
        if (batteryPercent > 80) this.batteryLevel.setIcon(new ImageIcon(GUIConstants.Images.BATTERY_LEVEL_IMAGES[5]));
        else if (batteryPercent > 60)
            this.batteryLevel.setIcon(new ImageIcon(GUIConstants.Images.BATTERY_LEVEL_IMAGES[4]));
        else if (batteryPercent > 40)
            this.batteryLevel.setIcon(new ImageIcon(GUIConstants.Images.BATTERY_LEVEL_IMAGES[3]));
        else if (batteryPercent > 20)
            this.batteryLevel.setIcon(new ImageIcon(GUIConstants.Images.BATTERY_LEVEL_IMAGES[2]));
        else if (batteryPercent > 0)
            this.batteryLevel.setIcon(new ImageIcon(GUIConstants.Images.BATTERY_LEVEL_IMAGES[1]));
        else if (batteryPercent == 0)
            this.batteryLevel.setIcon(new ImageIcon(GUIConstants.Images.BATTERY_LEVEL_IMAGES[0]));
    }

    /**
     * Starts rendering the game to the canvas at the frame rate defined in the GUIConstants class.
     */
    public void start() {
        timer.start();
    }

    /**
     * Stops rendering the game to the canvas.
     */
    public void stop() {
        timer.stop();
    }
    
    /**
     * Creates the buttons panel that displays the information about the how much time the player has left,
     * the amount of coins the player has, the time remaining and the battery remaining
     * @return JPanel
     */
    private JPanel createButtonsPanel() {
        //Create the panel to hold the buttons.
        JPanel panel = new JPanel();

        //Make the background of the buttons panel transparent.
        panel.setOpaque(false);

        //Align the buttons in the buttons panel along the x axis.
        panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));

        //Create the button used to start a new game.
        Button optionsButton = new Button("Options");
        optionsButton.addActionListener(new OptionsActionListener());

        batteryLevel = new JLabel(new ImageIcon(GUIConstants.Images.BATTERY_LEVEL_IMAGES[5]));

        levelLabel = new JLabel("Level: " + gui.getModel().getCurrentLevelNumber());
        timeLabel = new JLabel("Time left:    " + gui.getModel().getTimeRemaining());
        coinsLabel = new JLabel("Coins:    " + gui.getModel().getCoins());
        JLabel batteryLabel = new JLabel("Battery remaining:    ");

        Font font = GUIConstants.Fonts.ROBOTO_BOLD.deriveFont(GUIConstants.scale(14f));
        levelLabel.setFont(font);
        timeLabel.setFont(font);
        coinsLabel.setFont(font);
        batteryLabel.setFont(font);

        levelLabel.setForeground(GUIConstants.Colours.GAME_BAR_TEXT);
        timeLabel.setForeground(GUIConstants.Colours.GAME_BAR_TEXT);
        coinsLabel.setForeground(GUIConstants.Colours.GAME_BAR_TEXT);
        batteryLabel.setForeground(GUIConstants.Colours.GAME_BAR_TEXT);

        panel.add(Box.createHorizontalStrut(GUIConstants.Dimensions.PADDING * 4));
        panel.add(levelLabel);
        panel.add(Box.createHorizontalStrut(GUIConstants.Dimensions.PADDING * 4));
        panel.add(timeLabel);
        panel.add(Box.createHorizontalStrut(GUIConstants.Dimensions.PADDING * 4));
        panel.add(coinsLabel);
        panel.add(Box.createHorizontalStrut(GUIConstants.Dimensions.PADDING * 4));
        panel.add(batteryLabel);
        panel.add(batteryLevel);
        panel.add(Box.createHorizontalGlue());
        panel.add(optionsButton);
        panel.add(Box.createHorizontalStrut(GUIConstants.Dimensions.PADDING / 4));

        return panel;
    }

    /**
     * The action performed when pressing the options button.
     */
    private class OptionsActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            gui.setPanel(GUI.GUIPanel.OPTIONS);
        }
    }

}
