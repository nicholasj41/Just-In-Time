package saveLoadLibrary;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * SaveGame Class
 * 
 * @Primary_Author - Celine Young (300378246).
 * @Code_Reviewer - Finn Welsford-Ackroyd (300379304).
 * @External_Tester - Riley Blair (300371586).
 */
public class SaveGame {
	
	/**
	 * Saves this version of the game to a file with the name that is specified
	 * 
	 * @param game - Game object to be saved.
	 * @param fileName - The name of the saved file.
	 * 
	 * @throws IOException - Error to be thrown if the file is not successfully created.
	 */
	public static void save(Savable game, String fileName) throws IOException {
		if (!(game instanceof Serializable)) {
			System.out.println("Not serializable");
			return;
		}
		// creates a new file with the specified file name
		FileOutputStream fos = new FileOutputStream(fileName + ".txt"); 
		// creates byte stream
		ObjectOutputStream out = new ObjectOutputStream(fos);
		// write data and closes stream
		out.writeObject(game);
		out.flush();
		out.close();
	}
}
