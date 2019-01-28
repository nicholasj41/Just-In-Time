package guiLibrary;

import animationLibrary.ScrollingAnimation;
import entityLibrary.NPC;
import entityLibrary.Player;
import levelLibrary.Block;
import levelLibrary.BlockType;
import levelLibrary.Level;
import levelLibrary.LevelType;

import animationLibrary.Animation;
import animationLibrary.CycleAnimation;
import animationLibrary.RotatingAnimation;
import model.ModelInterface;

import java.awt.*;
import java.util.List;

import static guiLibrary.GUIConstants.FPS;

/**
 * GUI Canvas
 *
 * @Primary_Author - Leyton Blackler (300368625).
 * @Code_Reviewer - Riley Blair (300371586).
 * @External_Tester - Celine Young (300378246).
 */
public class Canvas extends AbstractCanvas {
    private static final long serialVersionUID = 1L;

    /**
     * The model that the canvas drawing is based on.
     */
    private ModelInterface model;

    /**
     * The camera defines which section of the game is current viewable.
     */
    private Camera camera;

    /**
     * The animation for the player.
     */
    private Animation playerAnimation;

    /**
     * The animation for the battery bonuses.
     */
    private Animation batteryAnimation;

    /**
     * The animation for the coins.
     */
    private Animation coinAnimation;

    /**
     * The animation for the pigeons.
     */
    private Animation pigeonAnimation;

    /**
     * The animation for the background clouds.
     */
    private Animation cloudsAnimationBackground;

    /**
     * The animation for the foreground clouds.
     */
    private Animation cloudsAnimationForeground;

    /**
     * The animation for the red checkpoints.
     */
    private Animation checkpointAnimationRed;

    /**
     * The animation for the green checkpoints.
     */
    private Animation checkpointAnimationGreen;

    /**
     * The current level type.
     */
    private LevelType currentLevelType = LevelType.CUBA;

    /**
     * The current image for the background.
     */
    private Image background = GUIConstants.Images.CUBA_BACKGROUND;

    /**
     * The current image for the brick.
     */
    private Image brick = GUIConstants.Images.CUBA_BRICK;

    /**
     * The current image for the end level object.
     */
    private Image levelEndObject = GUIConstants.Images.CUBA_FOUNTAIN;

    /**
     * The last direction the player was facing.
     */
    private Player.State lastDirection = Player.State.RIGHT;

    /**
     * The canvas in which the main game is drawn onto.
     * @param model The model that is drawn.
     */
    public Canvas(ModelInterface model) {
        this.model = model;
        setPreferredSize(new Dimension(GUIConstants.Dimensions.GAME_WIDTH, GUIConstants.Dimensions.GAME_HEIGHT));
        setBackground(GUIConstants.Colours.CANVAS_BACKGROUND);

        this.camera = new Camera();

        //Create the player animation.
        this.playerAnimation = new CycleAnimation(50, GUIConstants.Images.PLAYER_IMAGES);
        this.playerAnimation.setSize(GUIConstants.Dimensions.BLOCK_SIZE, GUIConstants.Dimensions.BLOCK_SIZE);
        this.playerAnimation.play();

        //Create the batter animation.
        this.batteryAnimation = new RotatingAnimation(4, FPS, GUIConstants.Images.BATTERY_BONUS);
        this.batteryAnimation.setSize(GUIConstants.Dimensions.BLOCK_SIZE, GUIConstants.Dimensions.BLOCK_SIZE);
        this.batteryAnimation.play();

        //Create the coin animation.
        this.coinAnimation = new CycleAnimation(50, GUIConstants.Images.COIN_IMAGES);
        this.coinAnimation.setSize(GUIConstants.Dimensions.BLOCK_SIZE, GUIConstants.Dimensions.BLOCK_SIZE);
        this.coinAnimation.play();

        //Create the pigeon animation.
        this.pigeonAnimation = new CycleAnimation(80, GUIConstants.Images.PIGEON_IMAGES);
        this.pigeonAnimation.setSize((int) GUIConstants.scale(NPC.NPC_WIDTH), (int) GUIConstants.scale(NPC.NPC_HEIGHT));
        this.pigeonAnimation.play();

        //Create the clouds animation.
        this.cloudsAnimationBackground = new ScrollingAnimation(GUIConstants.Images.CLOUDS, ScrollingAnimation.Direction.HORIZONTAL, 10, FPS);
        this.cloudsAnimationBackground.play();

        //Create the clouds animation.
        this.cloudsAnimationForeground = new ScrollingAnimation(GUIConstants.Images.CLOUDS, ScrollingAnimation.Direction.HORIZONTAL, 6, FPS);
        this.cloudsAnimationForeground.play();

        //Create the red checkpoint animation.
        this.checkpointAnimationRed = new CycleAnimation(50, GUIConstants.Images.CHECKPOINT_IMAGES_RED);
        this.checkpointAnimationRed.setSize(GUIConstants.Dimensions.BLOCK_SIZE, GUIConstants.Dimensions.BLOCK_SIZE);
        this.checkpointAnimationRed.play();

        //Create the green checkpoint animation.
        this.checkpointAnimationGreen = new CycleAnimation(50, GUIConstants.Images.CHECKPOINT_IMAGES_GREEN);
        this.checkpointAnimationGreen.setSize(GUIConstants.Dimensions.BLOCK_SIZE, GUIConstants.Dimensions.BLOCK_SIZE);
        this.checkpointAnimationGreen.play();
    }

    /**
     * Renders the current state of the model. This method executes at the frame rate defined in the
     * GUIConstants class.
     */
    public void updateLevelTypeImages() {
        //If the images for the current level are correct, do not change the images.
        if (currentLevelType == model.getCurrentLevel().getLevelType()) return;

        //If the images for the current level are incorrect, change the images.
        Level level = model.getCurrentLevel();

        //Set the images to the Cuba Street images.
        if (level.getLevelType() == LevelType.CUBA) {
            background = GUIConstants.Images.CUBA_BACKGROUND;
            brick = GUIConstants.Images.CUBA_BRICK;
            levelEndObject = GUIConstants.Images.CUBA_FOUNTAIN;
            currentLevelType = LevelType.CUBA;
        }

        //Set the images to the Kelburn Park images.
        else if (level.getLevelType() == LevelType.KELBURN) {
            background = GUIConstants.Images.KELBURN_BACKGROUND;
            brick = GUIConstants.Images.KELBURN_BRICK;
            levelEndObject = GUIConstants.Images.CABLE_CAR;
            currentLevelType = LevelType.KELBURN;
        }

        //Set the images for the Cotton images.
        else if (level.getLevelType() == LevelType.COTTON) {
            background = GUIConstants.Images.COTTON_BACKGROUND;
            brick = GUIConstants.Images.COTTON_BRICK;
            levelEndObject = GUIConstants.Images.HAND_IN_BOX;
            currentLevelType = LevelType.COTTON;
        }
    }

    /**
     * Redraw the canvas based on the current state of the model.
     * @param g The graphics object to draw onto.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //Cast the Graphics object into a Graphics2D object.
        Graphics2D g2D = (Graphics2D) g;
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        //Set the images to use into the level to the appropriate images for the current level type.
        updateLevelTypeImages();
        //Get the player position and scale it.
        Player player = model.getPlayer();
        Point playerPosition = player.getPos();
        Point scaledPlayerPosition = new Point(GUIConstants.scale((int) playerPosition.getX()), GUIConstants.scale((int) playerPosition.getY()));
        //Shift the camera to the correct position based on the positon of the player.
        shiftCamera(scaledPlayerPosition);
        //Draw the background for the level.
        drawBackground(g2D);
        //Shift the canvas based on the camera position.
        g2D.translate(-camera.getX(), -camera.getY());
        //Draw clouds if the current level is either Cuba Street or Kelburn, do not draw foreground clouds for Cotton building.
        if (model.getCurrentLevel().getLevelType() != LevelType.COTTON) drawClouds(g2D, cloudsAnimationBackground.getImage(), GUIConstants.Dimensions.BLOCK_SIZE);
        //Draw the level layout.
        drawLevel(g2D, model.getCurrentLevel());
        //Draw the player.
        g2D.drawImage(determinePlayerImage(player), (int) (scaledPlayerPosition.getX() - GUIConstants.Dimensions.BLOCK_SIZE / 2), (int) (scaledPlayerPosition.getY() - GUIConstants.Dimensions.BLOCK_SIZE / 2), null);
        //Draw the NPCs.
        for (NPC npc : model.getNPCs()) {
        	Point npcPosition = npc.getPos();
            Point scaledNpcPosition = new Point(GUIConstants.scale((int) npcPosition.getX()), GUIConstants.scale((int) npcPosition.getY()));
            Image npcImage = pigeonAnimation.getImage(npc.isMovingRight(), !model.isGravityDown());
            g2D.drawImage(npcImage, (int) (scaledNpcPosition.getX() - GUIConstants.scale((int) NPC.NPC_WIDTH) / 2), (int) (scaledNpcPosition.getY() - GUIConstants.scale((int) NPC.NPC_HEIGHT) / 2), null);
        }
        //Draw clouds if the current level is either Cuba Street or Kelburn, do not draw foreground clouds for Cotton building.
        if (model.getCurrentLevel().getLevelType() != LevelType.COTTON) drawClouds(g2D, cloudsAnimationForeground.getImage(), 0);
        g2D.dispose();
    }

    /**
     * Determines the image to use for the player based on its current state.
     * @param player The instance of the current player.
     * @return The image to use for the player.
     */
    private Image determinePlayerImage(Player player) {
        boolean flipHorizontal = true;
        if (player.whichDirection() == Player.State.LEFT) {
            flipHorizontal = false;
            lastDirection = Player.State.LEFT;
        } else if (player.whichDirection() == Player.State.RIGHT) {
            flipHorizontal = true;
            lastDirection = Player.State.RIGHT;
        } else if (player.whichDirection() == Player.State.IDLE) {
            if (lastDirection == Player.State.LEFT) flipHorizontal = false;
            else if (lastDirection == Player.State.RIGHT) flipHorizontal = true;
        }
        return playerAnimation.getImage(flipHorizontal, !model.isGravityDown());
    }
    
    /**
     * Shifts the camera to be in line with the player position
     * @param playerPosition The players current position
     */
    private void shiftCamera(Point playerPosition) {
        //Calculate the threshold at the start before the camera begins shifting.
        int startThreshold = GUIConstants.Dimensions.GAME_WIDTH / 2;
        //Calculate the threshold at the end when the camera stops shifting.
        int endThreshold = (model.getCurrentLevel().getLevelLayout().size() * GUIConstants.Dimensions.BLOCK_SIZE) - GUIConstants.Dimensions.GAME_WIDTH / 2;
        //Shift the camera the appropriate amount.
        if (playerPosition.getX() > startThreshold) {
            if (playerPosition.getX() > endThreshold) {
                camera.setX(endThreshold - GUIConstants.Dimensions.GAME_WIDTH / 2);
            } else {
                camera.setX(((int) playerPosition.getX()) - startThreshold);
            }
        } else {
            camera.setX(0);
            camera.setY(0);
        }
    }

    /**
     * Scales the background image to fit the screen while maintaining its original aspect ratio.
     * @param g2D The Graphics2D object to draw the level onto.
     */
    private void drawBackground(Graphics2D g2D) {
        //Shift the background at half the rate of the main level components.
        g2D.translate(-camera.getX() / 2, -camera.getY());
        //Wrap the background around if it does not cover the entire game view.
        int backgroundShown = 0;
        while (backgroundShown < GUIConstants.Dimensions.GAME_WIDTH + Math.abs(camera.getX())) {
            g2D.drawImage(background, backgroundShown, GUIConstants.Dimensions.BLOCK_SIZE, null);
            backgroundShown += background.getWidth(null);
        }
        g2D.translate(camera.getX() / 2, camera.getY());
    }

    /**
     * Draws the clouds.
     * @param g2D The graphics object to draw onto.
     * @param clouds The image of the clouds.
     * @param y The y position to draw the clouds at.
     */
    private void drawClouds(Graphics2D g2D, Image clouds, int y) {
        //Wrap the clouds around if it does not cover the entire game view.
        int cloudsShown = 0;
        while (cloudsShown < GUIConstants.Dimensions.GAME_WIDTH + Math.abs(camera.getX())) {
            g2D.drawImage(clouds, cloudsShown, y, null);
            cloudsShown += GUIConstants.Images.CLOUDS.getWidth(null);
        }
    }

    /**
     * Draws the current level on the canvas.
     * @param g2D The Graphics2D object to draw the level onto.
     * @param level The representation of the level in the model.
     */
    private void drawLevel(Graphics2D g2D, Level level) {
        List<List<Block>> layout = level.getLevelLayout();
        cols:
        for (int col = 0; col < layout.size(); col++) {
            for (int row = 0; row < layout.get(0).size(); row++) {
                if (col == 0 || col == layout.size() - 1) g2D.setColor(Color.RED);
                else g2D.setColor(Color.GREEN);
                Block block = layout.get(col).get(row);
                int x = (int) Math.round(((double) col * (double) GUIConstants.Dimensions.BLOCK_SIZE));
                int y = (int) Math.round((double) row * (double) GUIConstants.Dimensions.BLOCK_SIZE);
                //If the blocks are to the right of the camera, do not draw any more blocks.
                if (x > GUIConstants.Dimensions.GAME_WIDTH + camera.getX()) return;
                //If the blocks are to the left of the camera, skip the column.
                if (x < camera.getX() - GUIConstants.Dimensions.BLOCK_SIZE) continue cols;
                if (block != null) {
                    //Draw the block according to the block type.
                    if (block.getBlockType() == BlockType.GROUND) {
                        Image image = brick;
                        g2D.drawImage(image, x, y, null);
                    } else if (block.getBlockType() == BlockType.BONUS) {
                        g2D.drawImage(coinAnimation.getImage(), x, y, null);
                    } else if (block.getBlockType() == BlockType.BATTERY) {
                        g2D.drawImage(batteryAnimation.getImage(), x, y, null);
                    } else if (block.getBlockType() == BlockType.LEVELEND) {
                        g2D.drawImage(levelEndObject, x - levelEndObject.getWidth(null) / 2, y - levelEndObject.getHeight(null) + GUIConstants.Dimensions.BLOCK_SIZE, null);
                    } else if (block.getBlockType() == BlockType.CHECKPOINT) {
                        if (block.getVisited()) g2D.drawImage(checkpointAnimationGreen.getImage(), x, y, null);
                        else g2D.drawImage(checkpointAnimationRed.getImage(), x, y, null);
                    }
                }
            }
        }
    }
}
