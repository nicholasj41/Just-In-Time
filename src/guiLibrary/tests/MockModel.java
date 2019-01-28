package guiLibrary.tests;

import entityLibrary.NPC;
import entityLibrary.Player;
import levelLibrary.Level;
import model.ModelInterface;

import java.io.File;
import java.util.List;

public class MockModel implements ModelInterface {

    private int currentLevel = -1;
    
    @Override
    public void run() {

    }

    @Override
    public void setPaused(boolean state) {

    }

    @Override
    public void save(String fileName) {

    }

    @Override
    public void load(File gameFile) {

    }

    @Override
    public void setPlayerMovingLeft() {

    }

    @Override
    public void setPlayerMovingRight() {

    }

    @Override
    public void keyLeftRelease() {

    }

    @Override
    public void keyRightRelease() {

    }

    @Override
    public void invertGravity() {

    }

    @Override
    public void setLevel(int level) {
        this.currentLevel = level;
    }

    @Override
    public Level getCurrentLevel() {
        return null;
    }

    @Override
    public Player getPlayer() {
        return null;
    }

    @Override
    public boolean isGravityDown() {
        return false;
    }

    @Override
    public List<NPC> getNPCs() {
        return null;
    }

    @Override
    public Level[] getLevels() {
        return new Level[0];
    }

    @Override
    public String getTimeRemaining() {
        return null;
    }

    @Override
    public boolean isGameWon() {
        return false;
    }

    @Override
    public boolean isGameOver() {
        return false;
    }

    @Override
    public int getCurrentBatteryPercent() {
        return 0;
    }

    @Override
    public int getCurrentLevelNumber() {
        return currentLevel;
    }

    @Override
    public int getCoins() {
        return 0;
    }
    
    @Override
    public void respawn() {
        return;
    }
}
