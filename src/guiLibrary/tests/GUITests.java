package guiLibrary.tests;

import controller.Controller;
import controller.ControllerInterface;
import guiLibrary.GUI;
import model.ModelInterface;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import javax.swing.*;

/**
 * GUI Tests Class
 *
 * @Primary_Author - Leyton Blackler (300368625).
 * @Code_Reviewer - Riley Blair (300371586).
 * @External_Tester - Celine Young (300378246).
 */
public class GUITests {

    private static final boolean DISPLAY_DIALOG_MESSAGES = false;
    private static final int DELAY = 2000;

    private GUI constructGUI() {
        ModelInterface model = new MockModel();
        ControllerInterface controller = new Controller(model);
        GUI gui = new GUI(model, controller);
        model.run();
        return gui;
    }

    private void closeGUI(GUI gui) {
        gui.dispose();
        gui.setVisible(false);
    }

    @Test
    public void testPressStart() {
        String testName = "Press Start Test";
        System.out.println("===:: " + testName + " ::=== ");
        if (DISPLAY_DIALOG_MESSAGES) {
            JOptionPane.showMessageDialog(null, "This test will simulate:\n\n" +
                            "- Launching an empty GUI (an empty container for the model).\n" +
                            "- Pressing the start button.\n\n" +
                            "The test will run automatically, do not interact with the GUI.\n" +
                            "Press OK to begin the test.",
                    testName, JOptionPane.INFORMATION_MESSAGE);
        }
        System.out.println("Displaying GUI...");
        GUI gui = constructGUI();
        try { Thread.sleep(DELAY); } catch (InterruptedException e) { e.printStackTrace(); }
        System.out.println("Pressing start button...");
        gui.getMainMenuPanel().simulateStart();
        try { Thread.sleep(DELAY); } catch (InterruptedException e) { e.printStackTrace(); }
        System.out.println("Closing GUI...\n");
        closeGUI(gui);
    }

    private void runLevel(GUI gui, int level) {
        try { Thread.sleep(DELAY); } catch (InterruptedException e) { e.printStackTrace(); }
        System.out.println("Pressing start button...");
        gui.getMainMenuPanel().simulateStart();
        try { Thread.sleep(DELAY); } catch (InterruptedException e) { e.printStackTrace(); }
        if (level == 1) {
            System.out.println("Pressing level one button...");
            gui.getLevelSelectPanel().simulateLevelOne();
        } else if (level == 2) {
            System.out.println("Pressing level two button...");
            gui.getLevelSelectPanel().simulateLevelTwo();
        } else if (level == 3) {
            System.out.println("Pressing level three button...");
            gui.getLevelSelectPanel().simulateLevelThree();
        }
    }

    @Test
    public void testPlayLevelOne() {
        String testName = "Play Level One Test";
        System.out.println("===:: " + testName + " ::=== ");
        if (DISPLAY_DIALOG_MESSAGES) {
            JOptionPane.showMessageDialog(null, "This test will simulate:\n\n" +
                            "- Launching an empty GUI (an empty container for the model).\n" +
                            "- Pressing the start button.\n" +
                            "- Pressing the level one button.\n\n" +
                            "The test will run automatically, do not interact with the GUI.\n" +
                            "Press OK to begin the test.",
                    testName, JOptionPane.INFORMATION_MESSAGE);
        }
        System.out.println("Displaying GUI...");
        GUI gui = constructGUI();
        runLevel(gui, 1);
        assertEquals(1, gui.getModel().getCurrentLevelNumber());
        try { Thread.sleep(DELAY); } catch (InterruptedException e) { e.printStackTrace(); }
        System.out.println("Closing GUI...\n");
        closeGUI(gui);
    }

    @Test
    public void testPlayLevelTwo() {
        String testName = "Play Level Two Test";
        System.out.println("===:: " + testName + " ::=== ");
        if (DISPLAY_DIALOG_MESSAGES) {
            JOptionPane.showMessageDialog(null, "This test will simulate:\n\n" +
                            "- Launching an empty GUI (an empty container for the model).\n" +
                            "- Pressing the start button.\n" +
                            "- Pressing the level two button.\n\n" +
                            "The test will run automatically, do not interact with the GUI.\n" +
                            "Press OK to begin the test.",
                    testName, JOptionPane.INFORMATION_MESSAGE);
        }
        System.out.println("Displaying GUI...");
        GUI gui = constructGUI();
        runLevel(gui, 2);
        assertEquals(2, gui.getModel().getCurrentLevelNumber());
        try { Thread.sleep(DELAY); } catch (InterruptedException e) { e.printStackTrace(); }
        System.out.println("Closing GUI...\n");
        closeGUI(gui);
    }

    @Test
    public void testPlayLevelThree() {
        String testName = "Play Level Three Test";
        System.out.println("===:: " + testName + " ::=== ");
        if (DISPLAY_DIALOG_MESSAGES) {
            JOptionPane.showMessageDialog(null, "This test will simulate:\n\n" +
                            "- Launching an empty GUI (an empty container for the model).\n" +
                            "- Pressing the start button.\n" +
                            "- Pressing the level three button.\n\n" +
                            "The test will run automatically, do not interact with the GUI.\n" +
                            "Press OK to begin the test.",
                    testName, JOptionPane.INFORMATION_MESSAGE);
        }
        System.out.println("Displaying GUI...");
        GUI gui = constructGUI();
        runLevel(gui, 3);
        assertEquals(3, gui.getModel().getCurrentLevelNumber());
        try { Thread.sleep(DELAY); } catch (InterruptedException e) { e.printStackTrace(); }
        System.out.println("Closing GUI...\n");
        closeGUI(gui);
    }

}
