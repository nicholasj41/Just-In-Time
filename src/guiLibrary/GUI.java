package guiLibrary;

import controller.ControllerInterface;
import model.ModelInterface;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.metal.MetalLookAndFeel;
import java.awt.*;
import java.net.URL;

import static guiLibrary.GUI.GUIPanel.*;

/**
 * GUI
 *
 * @Primary_Author - Leyton Blackler (300368625).
 * @Code_Reviewer - Riley Blair (300371586).
 * @External_Tester - Celine Young (300378246).
 */
public class GUI extends JFrame {
    
	private static final long serialVersionUID = 1L;
	private ControllerInterface controller;
    private ModelInterface model;

    /**
     * enums are used for which way the player is facing.
	 * MAIN_MENU - Main Menu Panel
	 * LEVEL_SELECT - Level Select Panel
	 * GAME - Game Panel
	 * OPTIONS - Options Panel
	 */
    public enum GUIPanel {
        MAIN_MENU,
        LEVEL_SELECT,
        GAME,
        OPTIONS
    }

    private GUIPanel currentPanelType;

    private MainMenuPanel mainMenuPanel;
    private LevelSelectPanel levelSelectPanel;
    private GamePanel gamePanel;
    private OptionsPanel optionsPanel;

    /**
     * Creates the GUI
     * 
     * @param model - ModelInterface object
     * @param controller - ControllerInterface object
     */
    public GUI(ModelInterface model, ControllerInterface controller) {
        super("Just In Time");

        GUIConstants.Fonts.loadFonts();

        JFrame loadingScreen = createLoadingScreen();

        GUIConstants.Images.loadImages();

        this.model = model;
        this.controller = controller;

        setNativeLookAndFeel(true);

        addKeyListener(new GUIKeyListener(this, controller));
        setFocusable(true);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setIcon(GUIConstants.Filenames.ICON);

        //Create the main content panel for the JFrame.
        JPanel content = new JPanel();
        setContentPane(content);
        content.setLayout(new BoxLayout(content, BoxLayout.PAGE_AXIS));

        this.mainMenuPanel = new MainMenuPanel(this);
        this.levelSelectPanel = new LevelSelectPanel(model, this);
        this.gamePanel = new GamePanel(this);
        this.optionsPanel = new OptionsPanel(this);

        closeLoadingScreen(loadingScreen);

        setPanel(MAIN_MENU);
    }

    /**
     * Creates and returns the loading screen panel
     * 
     * @return The loading screen Panel
     */
    public static JFrame createLoadingScreen() {
        JFrame loadingScreen = new JFrame();
        JLabel label = new JLabel("Loading...");
        label.setBorder(new EmptyBorder(GUIConstants.Dimensions.PADDING, GUIConstants.Dimensions.PADDING * 2, GUIConstants.Dimensions.PADDING, GUIConstants.Dimensions.PADDING * 2));
        label.setOpaque(true);
        label.setBackground(GUIConstants.Colours.MENU_BACKGROUND);
        label.setForeground(GUIConstants.Colours.MENU_TITLE);
        label.setFont(GUIConstants.Fonts.ROBOTO_THIN.deriveFont(36f));
        loadingScreen.add(label, BorderLayout.CENTER);
        loadingScreen.setUndecorated(true);
        loadingScreen.pack();
        centreWindow(loadingScreen);
        loadingScreen.setVisible(true);
        return loadingScreen;
    }

    /**
     * Closes the Loading screen when another option is clicked on
     * 
     * @param loadingScreen - The Loading Screen Panel
     */
    public static void closeLoadingScreen(JFrame loadingScreen) {
        loadingScreen.setVisible(false);
        loadingScreen.dispose();
    }

    /**
     * Sets the canvas to the one passed
     * 
     * @param canvas - AbstractCanvas object
     */
    public void setCanvas(AbstractCanvas canvas) {
        this.gamePanel.setCanvas(canvas);
    }

    /**
     * Sets the icon of the game.  Shows in the task bar
     * 
     * @param filename - String 
     */
    private void setIcon(String filename) {
        URL imgURL = getClass().getClassLoader().getResource(filename);
        if (imgURL != null) {
            ImageIcon icon = new ImageIcon(imgURL);
            setIconImage(icon.getImage());
        }
    }

    /**
     * Centre's the window on the monitor
     * 
     * @param frame - JFrame object
     */
    public static void centreWindow(JFrame frame) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(screenSize.width / 2 - frame.getSize().width / 2, screenSize.height / 2 - frame.getSize().height / 2);
    }

    /**
     * Returns the current panel that's being viewed
     * 
     * @return currentPanelType - GUIPanel enum
     */
    public GUIPanel getCurrentPanelType() {
        return this.currentPanelType;
    }

    /**
     * Sets the panel to the panel passed to it
     * 
     * @param panel - GUIPanel enum
     */
    public void setPanel(GUIPanel panel) {
        Container previous = getContentPane();
        this.currentPanelType = panel;
        
        if (panel == MAIN_MENU) {
            dispose();
            setUndecorated(true);
        }

        if (previous == mainMenuPanel) {
            dispose();
            setUndecorated(false);
        }

        if (panel == MAIN_MENU) {
            setContentPane(mainMenuPanel);
            controller.pause();
            gamePanel.stop();
        }

        else if (panel == LEVEL_SELECT) {
            levelSelectPanel.refreshButtons();
            levelSelectPanel.revalidate();
            levelSelectPanel.repaint();
            setContentPane(levelSelectPanel);
            controller.pause();
            gamePanel.stop();
        }

        else if (panel == GAME) {
            setContentPane(gamePanel);
            controller.start();
            gamePanel.start();
        }

        else if (panel == OPTIONS) {
            setContentPane(optionsPanel);
            controller.pause();
            gamePanel.stop();
        }
        pack();
        revalidate();
        repaint();

        //Centre the window if:
        // - The panel is being set to the main menu.
        // - The panel is being set to the game and the previous panel was the main menu.
        // - The panel is being set to the level select and the previous panel was the main menu.
        // - The panel is not switching between either the game to options, or options to game.
        if ((panel == MAIN_MENU)
                || (previous == mainMenuPanel && panel == GAME)
                || (previous == mainMenuPanel && panel == LEVEL_SELECT)
                || ((previous != gamePanel && panel == OPTIONS) && (previous != optionsPanel && panel == GAME))) centreWindow(this);

        if (GUIConstants.FULLSCREEN) {
            dispose();
            setExtendedState(JFrame.MAXIMIZED_BOTH);
            setUndecorated(true);
        }

        setVisible(true);
    }

    /**
     * Sets the system native look and feel of the dialog messages in the GUI.
     * @param state Whether the GUI uses the systems native look and feel.
     */
    private void setNativeLookAndFeel(boolean state) {
        try {
            if (state) UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            else UIManager.setLookAndFeel(new MetalLookAndFeel());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Returns the level select panel
     * 
     * @return levelSelectPanel - LevelSelectPanel
     */
    public LevelSelectPanel getLevelSelectPanel() {
    	return this.levelSelectPanel;
    }

    /**
     * Returns the controller
     * 
     * @return controller - ControllerInterface
     */
    public ControllerInterface getController() {
        return this.controller;
    }

    /**
     * Returns the model
     * 
     * @return model - ModelInterface
     */
    public ModelInterface getModel() {
        return this.model;
    }

    /**
     * Returns the main menu panel
     * 
     * @return mainMenuPanel - MainMenuPanel
     */
    public MainMenuPanel getMainMenuPanel() {
        return this.mainMenuPanel;
    }
}