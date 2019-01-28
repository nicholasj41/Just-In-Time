package saveLoadLibrary;

import guiLibrary.GUI;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * LoadGame Class
 * 
 * @Primary_Author - Celine Young (300378246).
 * @Code_Reviewer - Finn Welsford-Ackroyd (300379304).
 * @External_Tester - Riley Blair (300371586).
 */
public class LoadGame {
	
	/**
	 * Loads the game from gameFile into game object.
	 *
	 * @param gameFile - The gameFile to be loaded from.
	 * 
	 * @throws IOException - Loading Failed.
	 * @throws ClassNotFoundException - Savable Object Mismatch.
	 */
	public static Savable load(File gameFile) throws IOException, ClassNotFoundException {

		FileInputStream fis;
		Savable game = null;
		// makes a file input stream by opening a connection to the gameFile
		fis = new FileInputStream(gameFile);
		ObjectInputStream in = new ObjectInputStream(fis);
		Object obj = in.readObject();
		// casts the game object
		if (obj instanceof Savable) {
			game = (Savable)obj;
		}
		in.close();
		// if the game isn't an instance of Savable, return null
		return game;
	}
}