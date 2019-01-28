package view;

import controller.Controller;
import guiLibrary.Canvas;
import guiLibrary.GUI;
import model.Model;

/**
 * View Class
 * 
 * @Primary_Author -
 * @Code_Reviewer - 
 * @External_Tester - 
 */
public class View {

    private GUI gui;

    public View(Model model, Controller controller) {
        this.gui = new GUI(model, controller);
        this.gui.setCanvas(new Canvas(model));
    }
}
