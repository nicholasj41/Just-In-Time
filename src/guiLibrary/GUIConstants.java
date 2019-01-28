package guiLibrary;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

/**
 * GUI Constants
 *
 * @Primary_Author - Leyton Blackler (300368625).
 * @Code_Reviewer - Riley Blair (300371586).
 * @External_Tester - Celine Young (300378246).
 */
public final class GUIConstants {

    /**
     * Fixed frame rate to render to the canvas at.
     */
    public static final int FPS = 30;

    /**
     * Scale to display the GUI at so it can be properly visible on different displays.
     * A scale of 0.5 is equivalent to a 480p resolution.
     * A scale of 1.0 is equivalent to a 720p resolution.
     * A scale of 1.5 is equivalent to a 1080p resolution.
     */
    public static double SCALE = 1.0;

    /**
     * Determines whether the game is displayed in fullscreen.
     */
    public static boolean FULLSCREEN = false;

    /**
     * Scales the given value to match the current scale factor.
     * @param value The value to scale.
     * @return The scaled result
     */
    public static int scale(int value) {
        return (int) (SCALE * value);
    }

    /**
     * Scales the given value to match the current scale factor.
     * @param value The value to scale.
     * @return The scaled result
     */
    public static double scale(double value) {
        return (double) (SCALE * value);
    }

    /**
     * Scales the given value to match the current scale factor.
     * @param value The value to scale.
     * @return The scaled result
     */
    public static float scale(float value) {
        return (float) (SCALE * value);
    }

    /**
     * The supported resolutions the game can be displayed in.
     */
    public enum Resolution {
        RESOLUTION_480P,
        RESOLUTION_720P,
        RESOLUTION_1080P,
        RESOLUTION_4K
    }

    /**
     * Sets the game to the given resolution.
     * @param resolution The resolution to scale the game to.
     */
    public static void setResolution(Resolution resolution) {
        if (resolution == Resolution.RESOLUTION_480P) SCALE = 0.5;
        else if (resolution == Resolution.RESOLUTION_720P) SCALE = 1.0;
        else if (resolution == Resolution.RESOLUTION_1080P) SCALE = 1.5;
        else if (resolution == Resolution.RESOLUTION_4K) SCALE = 3.0;
    }

    /**
     * Provides a set of colours to be used throughout the GUI and canvas,
     */
    public static class Colours {

        /**
         * Background colour for the GUI canvas.
         */
        public static final Color CANVAS_BACKGROUND = new Color(3, 169, 244);

        /**
         * Background colour for game buttons bar.
         */
        public static final Color GAME_BUTTON_BAR = new Color(2, 136, 209);

        /**
         * Background colour for the main menu.
         */
        public static final Color MENU_BACKGROUND = new Color(2, 136, 209);

        /**
         * Text colour for the menu title.
         */
        public static final Color MENU_TITLE = new Color(187, 222, 251);

        /**
         * Text colour for the game bar.
         */
        public static final Color GAME_BAR_TEXT = new Color(187, 222, 251);

        /**
         * Background colour for idle buttons.
         */
        public static final Color BUTTON_BACKGROUND_NORMAL = new Color(55, 71, 79);

        /**
         * Background colour for a button when the mouse hovers over it.
         */
        public static final Color BUTTON_BACKGROUND_HOVER = new Color(69, 90, 100);

        /**
         * Background colour for a button when the mouse presses it.
         */
        public static final Color BUTTON_BACKGROUND_PRESSED = new Color(55, 71, 79);

        /**
         * Text colour for idle buttons.
         */
        public static final Color BUTTON_TEXT_NORMAL = new Color(176, 190, 197);

        /**
         * Text colour for a button when the mouse hovers over it.
         */
        public static final Color BUTTON_TEXT_HOVER = new Color(236, 239, 241);

        /**
         * Text colour for a button when the mouse presses it.
         */
        public static final Color BUTTON_TEXT_PRESSED = new Color(176, 190, 197);
        
        /**
         * Overlay colour for a button when it is locked
         */
        public static final Color BUTTON_LOCKED = new Color(128, 128, 128, 127);
        
        /**
         * Colour overlay for a level button when the mouse hovers over it.
         */
        public static final Color LEVEL_BUTTON_HOVER = new Color(69, 90, 100, 127);

        /**
         * Colour overlay for a button when the mouse presses it.
         */
        public static final Color LEVEL_BUTTON_PRESSED = new Color(55, 71, 79, 127);
    }

    /**
     * Provides a set of constant dimensions to be used throughout the GUi and canvas.
     */
    public static class Dimensions {

        /**
         * The fixed width of the main menu.
         */
        public static final int MENU_WIDTH = (int) (SCALE * 400);

        /**
         * The fixed height of the main menu.
         */
        public static final int MENU_HEIGHT = (int) (SCALE * 200);

        /**
         * The size of the padding between components in the GUI.
         */
        public static final int PADDING = (int) (SCALE * 20);

        /**
         * The width of buttons on the main menu or options menu.
         */
        public static final int BUTTON_WIDTH = (int) (SCALE * 120);

        /**
         * The height of buttons on the main menu or options menu.
         */
        public static final int BUTTON_HEIGHT = (int) (SCALE * 40);

        /**
         * The height of buttons on the main menu or options menu.
         */
        public static final int GAME_BAR_HEIGHT = (PADDING / 2) + BUTTON_HEIGHT;

        /**
         * The fixed width of the game canvas.
         */
        public static final int GAME_WIDTH = (int) (SCALE * 1280);

        /**
         * The fixed height of the game canvas.
         */
        public static final int GAME_HEIGHT = (int) ((SCALE * 720) - GAME_BAR_HEIGHT);

        /**
         * The size of the square blocks.
         */
        public static final int BLOCK_SIZE = (int) ((double) GAME_HEIGHT / (double) 10);
    }

    /**
     * Provides a set of images to be used throughout the GUi and canvas.
     */
    public static class Images {

        /**
         * The default image to use if the images have not been loaded.
         */
        private static final Image DEFAULT = createDefaultImage();

        /**
         * The file path for the lock image.
         */
        private static final URL LOCK_URL = GUIConstants.class.getResource("/lock.png");

        /**
         * The image for the small lock.
         */
        public static Image LOCK_SMALL = DEFAULT;

        /**
         * The image for the big lock.
         */
        public static Image LOCK_BIG = DEFAULT;

        /**
         * The file path for the batter bonus image.
         */
        private static final URL BATTERY_BONUS_URL = GUIConstants.class.getResource("/battery.png");

        /**
         * The image for the battery bonus.
         */
        public static Image BATTERY_BONUS = DEFAULT;

        /**
         * The file path for the clouds image.
         */
        private static final URL CLOUDS_URL = GUIConstants.class.getResource("/background_clouds.png");

        /**
         * The image for the clouds.
         */
        public static Image CLOUDS = DEFAULT;

        /**
         * The file path for the Cuba Street background image.
         */
        private static final URL CUBA_BACKGROUND_URL = GUIConstants.class.getResource("/cuba2.png");

        /**
         * The image for the Cuba Street background.
         */
        public static Image CUBA_BACKGROUND = DEFAULT;

        /**
         * The file path for the Cuba Street brick image.
         */
        private static final URL CUBA_BRICK_URL = GUIConstants.class.getResource("/cuba_brick.png");

        /**
         * The image for the Cuba Street brick.
         */
        public static Image CUBA_BRICK = DEFAULT;

        /**
         * The file path for the Cuba Street fountain image.
         */
        private static final URL CUBA_FOUNTAIN_URL = GUIConstants.class.getResource("/cuba_fountain.png");

        /**
         * The image for the Cuba Street fountain.
         */
        public static Image CUBA_FOUNTAIN = DEFAULT;
        
        /**
         * The file path for the Kelburn cable car image.
         */
        private static final URL CABLE_CAR_URL = GUIConstants.class.getResource("/cable_car.png");

        /**
         * The image for the Kelburn cable car.
         */
        public static Image CABLE_CAR = DEFAULT;
        
        /**
         * The file path for the Cotton hand-in box image.
         */
        private static final URL HAND_IN_BOX_URL = GUIConstants.class.getResource("/hand_in_box.png");

        /**
         * The image for the CCotton hand-in box.
         */
        public static Image HAND_IN_BOX = DEFAULT;

        /**
         * Trees used in the Kelburn Park background were created by 0melapics.
         * Used with permission under a royalty free license.
         * http://www.freepik.com/free-vector/collection-of-different-trees_949887.htm
         */

        /**
         * The file path for the Kelburn Park background image.
         */
        private static final URL KELBURN_BACKGROUND_URL = GUIConstants.class.getResource("/kelburn_background.png");

        /**
         * The image for the Kelburn Park background.
         */
        public static Image KELBURN_BACKGROUND = DEFAULT;

        /**
         * The file path for the Kelburn Park brick image.
         */
        private static final URL KELBURN_BRICK_URL = GUIConstants.class.getResource("/kelburn_brick.png");

        /**
         * The image for the Kelburn Park brick.
         */
        public static Image KELBURN_BRICK = DEFAULT;

        /**
         * The file path for the Cotton background image.
         */
        private static final URL COTTON_BACKGROUND_URL = GUIConstants.class.getResource("/cotton_background.png");

        /**
         * The image for the Cotton background.
         */
        public static Image COTTON_BACKGROUND = DEFAULT;

        /**
         * The file path for the Cotton brick image.
         */
        private static final URL COTTON_BRICK_URL = GUIConstants.class.getResource("/cotton_brick.png");

        /**
         * The image for the Cotton brick.
         */
        public static Image COTTON_BRICK = DEFAULT;

        /**
         * The images for the level buttons.
         */
        public static Image[] LEVEL_IMAGES = {
        		DEFAULT,
        		DEFAULT,
        		DEFAULT
        };

        /**
         * The images for the player.
         */
        public static Image[] PLAYER_IMAGES = {
                DEFAULT
        };

        /**
         * The images for the battery level.
         */
        public static Image[] BATTERY_LEVEL_IMAGES = {
                DEFAULT,
                DEFAULT,
                DEFAULT,
                DEFAULT,
                DEFAULT,
                DEFAULT
        };

        /**
         * The images for the coins.
         */
        public static Image[] COIN_IMAGES = {
                DEFAULT,
                DEFAULT,
                DEFAULT,
                DEFAULT,
                DEFAULT,
                DEFAULT,
                DEFAULT,
                DEFAULT,
                DEFAULT,
                DEFAULT
        };

        /**
         * The images for the pigeons.
         */
        public static Image[] PIGEON_IMAGES = {
                DEFAULT,
                DEFAULT,
                DEFAULT,
                DEFAULT,
                DEFAULT,
                DEFAULT,
                DEFAULT,
                DEFAULT
        };

        /**
         * The images for the red checkpoints.
         */
        public static Image[] CHECKPOINT_IMAGES_RED = {
                DEFAULT,
                DEFAULT,
                DEFAULT,
                DEFAULT,
                DEFAULT,
                DEFAULT,
                DEFAULT,
                DEFAULT,
                DEFAULT,
                DEFAULT,
                DEFAULT,
                DEFAULT,
                DEFAULT,
                DEFAULT,
                DEFAULT
        };

        /**
         * The images for the green checkpoints.
         */
        public static Image[] CHECKPOINT_IMAGES_GREEN = {
                DEFAULT,
                DEFAULT,
                DEFAULT,
                DEFAULT,
                DEFAULT,
                DEFAULT,
                DEFAULT,
                DEFAULT,
                DEFAULT,
                DEFAULT,
                DEFAULT,
                DEFAULT,
                DEFAULT,
                DEFAULT,
                DEFAULT
        };

        /**
         * Loads the images into the fields.
         */
        public static void loadImages() {
            try {
                //Load the images from their respective file paths.
                LOCK_SMALL = ImageIO.read(LOCK_URL);
                LOCK_BIG = ImageIO.read(LOCK_URL);
                BATTERY_BONUS = ImageIO.read(BATTERY_BONUS_URL);
                CLOUDS = ImageIO.read(CLOUDS_URL);
                CUBA_BACKGROUND = ImageIO.read(CUBA_BACKGROUND_URL);
                CUBA_BRICK = ImageIO.read(CUBA_BRICK_URL);
                CUBA_FOUNTAIN = ImageIO.read(CUBA_FOUNTAIN_URL);
                CABLE_CAR = ImageIO.read(CABLE_CAR_URL);
                HAND_IN_BOX = ImageIO.read(HAND_IN_BOX_URL);
                KELBURN_BACKGROUND = ImageIO.read(KELBURN_BACKGROUND_URL);
                KELBURN_BRICK = ImageIO.read(KELBURN_BRICK_URL);
                COTTON_BACKGROUND = ImageIO.read(COTTON_BACKGROUND_URL);
                COTTON_BRICK = ImageIO.read(COTTON_BRICK_URL);

                for (int i = 0; i < BATTERY_LEVEL_IMAGES.length; i++) {
                    BATTERY_LEVEL_IMAGES[i] = (ImageIO.read(GUIConstants.class.getResource("/battery_level_" + i + ".png")));
                    int width = Dimensions.BUTTON_HEIGHT * (BATTERY_LEVEL_IMAGES[i].getWidth(null) / BATTERY_LEVEL_IMAGES[i].getHeight(null));
                    BATTERY_LEVEL_IMAGES[i] = BATTERY_LEVEL_IMAGES[i].getScaledInstance(width, Dimensions.BUTTON_HEIGHT, Image.SCALE_SMOOTH);
                }
                
                for (int i = 0; i < LEVEL_IMAGES.length; i++) {
                    LEVEL_IMAGES[i] = (ImageIO.read(GUIConstants.class.getResource("/levelButtons/levelButton_" + (i + 1) + ".png")));
                    LEVEL_IMAGES[i] = LEVEL_IMAGES[i].getScaledInstance(Math.round(2 * Dimensions.BUTTON_WIDTH), Math.round(Dimensions.BUTTON_WIDTH), Image.SCALE_SMOOTH);
                }

                LOCK_SMALL = LOCK_SMALL.getScaledInstance(Math.round(Dimensions.BUTTON_WIDTH / 2), Math.round(Dimensions.BUTTON_WIDTH / 2), Image.SCALE_SMOOTH);
                LOCK_BIG = LOCK_BIG.getScaledInstance(Math.round((int) (Dimensions.BUTTON_WIDTH / 1.5)), Math.round((int) (Dimensions.BUTTON_WIDTH / 1.5)), Image.SCALE_SMOOTH);

                for (int i = 0; i < PLAYER_IMAGES.length; i++) {
                    PLAYER_IMAGES[i] = (ImageIO.read(GUIConstants.class.getResource("/player.png")));
                }

                for (int i = 0; i < COIN_IMAGES.length; i++) {
                    COIN_IMAGES[i] = (ImageIO.read(GUIConstants.class.getResource("/coin/coin_" + (i + 1) + ".png")));
                }

                for (int i = 0; i < PIGEON_IMAGES.length; i++) {
                    PIGEON_IMAGES[i] = (ImageIO.read(GUIConstants.class.getResource("/pigeon/pigeon_" + (i + 1) + ".png")));
                }

                for (int i = 0; i < CHECKPOINT_IMAGES_RED.length; i++) {
                    CHECKPOINT_IMAGES_RED[i] = (ImageIO.read(GUIConstants.class.getResource("/checkpoint/checkpoint_" + (i + 1) + ".png")));
                    CHECKPOINT_IMAGES_GREEN[i] = (ImageIO.read(GUIConstants.class.getResource("/visitedPoint/checkpoint_" + (i + 1) + ".png")));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            //Scale the background images.
            int backgroundHeight = Dimensions.GAME_HEIGHT - (2 * Dimensions.BLOCK_SIZE);
            int backgroundWidth = (backgroundHeight * 32) / 9; //get width using 32:9 aspect ratio of background image
            CUBA_BACKGROUND = CUBA_BACKGROUND.getScaledInstance(backgroundWidth, backgroundHeight, Image.SCALE_SMOOTH);
            KELBURN_BACKGROUND = KELBURN_BACKGROUND.getScaledInstance(backgroundWidth, backgroundHeight, Image.SCALE_SMOOTH);
            COTTON_BACKGROUND = COTTON_BACKGROUND.getScaledInstance(backgroundWidth, backgroundHeight, Image.SCALE_SMOOTH);
            CLOUDS = CLOUDS.getScaledInstance(backgroundWidth, backgroundHeight, Image.SCALE_SMOOTH);

            //Scale the cuba fountain.
            int fountainHeight = (4 * Dimensions.BLOCK_SIZE);
            int fountainWidth = (int) ((((double) CUBA_FOUNTAIN.getWidth(null) / (double) CUBA_FOUNTAIN.getHeight(null))) * ((double) fountainHeight));
            CUBA_FOUNTAIN = CUBA_FOUNTAIN.getScaledInstance(fountainWidth, fountainHeight, Image.SCALE_SMOOTH);

            //Scale the cable car.
            int cableCarHeight = (2 * Dimensions.BLOCK_SIZE);
            int cableCarWidth = (int) ((((double) CABLE_CAR.getWidth(null) / (double) CABLE_CAR.getHeight(null))) * ((double) cableCarHeight));
            CABLE_CAR = CABLE_CAR.getScaledInstance(cableCarWidth, cableCarHeight, Image.SCALE_SMOOTH);

            //Scale the hand in box.
            int handInBoxHeight = (3 * Dimensions.BLOCK_SIZE);
            int handInBoxWidth = (int) ((((double) HAND_IN_BOX.getWidth(null) / (double) HAND_IN_BOX.getHeight(null))) * ((double) handInBoxHeight));
            HAND_IN_BOX = HAND_IN_BOX.getScaledInstance(handInBoxWidth, handInBoxHeight, Image.SCALE_SMOOTH);

            //Scale the tile images.
            CUBA_BRICK = CUBA_BRICK.getScaledInstance(Math.round(Dimensions.BLOCK_SIZE), Math.round(Dimensions.BLOCK_SIZE), Image.SCALE_SMOOTH);
            KELBURN_BRICK = KELBURN_BRICK.getScaledInstance(Math.round(Dimensions.BLOCK_SIZE), Math.round(Dimensions.BLOCK_SIZE), Image.SCALE_SMOOTH);
            COTTON_BRICK = COTTON_BRICK.getScaledInstance(Math.round(Dimensions.BLOCK_SIZE), Math.round(Dimensions.BLOCK_SIZE), Image.SCALE_SMOOTH);
        }

        /**
         * Creates a default image to use when the images have not yet been loaded.
         * @return
         */
        private static Image createDefaultImage() {
            BufferedImage image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2D = image.createGraphics();
            g2D.setColor(Color.WHITE);
            g2D.fillRect(0, 0, 99, 99);
            g2D.setColor(Color.RED);
            g2D.drawRect(0, 0, 99, 99);
            g2D.drawLine(0, 0, 100, 100);
            g2D.drawLine(0, 100, 100, 0);
            g2D.dispose();
            return image;
        }
    }

    /**
     * Provides a set of fonts to use throughout the GUI and canvas.
     */
    public static class Fonts {

        /**
         * Roboto Thin, initialised as default sans serif style until the font has been loaded.
         */
        public static Font ROBOTO_THIN = new Font(Font.SANS_SERIF, Font.PLAIN, 12);

        /**
         * Roboto Regular, initialised as default sans serif style until the font has been loaded.
         */
        public static Font ROBOTO_REGULAR = new Font(Font.SANS_SERIF, Font.PLAIN, 12);

        /**
         * Roboto Medium, initialised as default sans serif style until the font has been loaded.
         */
        public static Font ROBOTO_MEDIUM = new Font(Font.SANS_SERIF, Font.PLAIN, 12);

        /**
         * Roboto Bold, initialised as default sans serif style until the font has been loaded.
         */
        public static Font ROBOTO_BOLD = new Font(Font.SANS_SERIF, Font.BOLD, 12);

        /**
         * Loads the fonts into the fields.
         */
        public static void loadFonts() {
            try {
                ROBOTO_THIN = Font.createFont(Font.TRUETYPE_FONT, GUIConstants.class.getClassLoader().getResourceAsStream("Roboto-Thin.ttf"));
                ROBOTO_REGULAR = Font.createFont(Font.TRUETYPE_FONT, GUIConstants.class.getClassLoader().getResourceAsStream("Roboto-Regular.ttf"));
                ROBOTO_MEDIUM = Font.createFont(Font.TRUETYPE_FONT, GUIConstants.class.getClassLoader().getResourceAsStream("Roboto-Medium.ttf"));
                ROBOTO_BOLD = Font.createFont(Font.TRUETYPE_FONT, GUIConstants.class.getClassLoader().getResourceAsStream("Roboto-Bold.ttf"));

                GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                ge.registerFont(ROBOTO_THIN);
                ge.registerFont(ROBOTO_REGULAR);
                ge.registerFont(ROBOTO_MEDIUM);
                ge.registerFont(ROBOTO_BOLD);
            } catch (IOException | FontFormatException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Provides a set of file names.
     */
    public static class Filenames {
        /**
         * File name for the image used as the window icon.
         */
        public static final String ICON = "battery.png";
    }
}
