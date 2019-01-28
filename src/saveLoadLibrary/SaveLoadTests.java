package saveLoadLibrary;

import static org.junit.Assert.*;

import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import org.junit.Test;

/**
 * Class for testing the SaveLoad Library
 * 
 * @Primary_Author - Celine Young (300378246).
 * @Code_Reviewer - Finn Welsford-Ackroyd (300379304).
 * @External_Tester - Riley Blair (300371586).
 */
public class SaveLoadTests implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * Attempts to save the instance of the TestGame class. Checks whether there exists a file with the specified
	 * file name after the method is executed.
	 * 
	 * @throws IOException - Saving Failed
	 */
	@Test
	public void testSave() throws IOException {
		SaveGame.save(new TestGame("test", Math.random(), Math.random()), "test1Output");
		assertTrue(new File("test1Output.txt").isFile());
	}
	
	/**
	 * Attempts to save and then load an instance of the TestGame class. Checks whether the original instance of 
	 * the game and the loaded toString() returns are the same.
	 * 
	 * @throws IOException - Saving/Loading Failed.
	 * @throws ClassNotFoundException - Savable Object Mismatch.
	 */
	@Test
	public void testValidLoad1() throws IOException, ClassNotFoundException {
		Savable savedGame = new TestGame("test", Math.random(), Math.random());
		String savedGameString = savedGame.toString();
		SaveGame.save(savedGame, "test1Output");
			
		// Check that a save file was created.
		assertTrue(new File("test1Output.txt").isFile());
			
		Savable loadedGame = LoadGame.load(new File("test1Output.txt"));
		String loadedGameString = loadedGame.toString();
			
		assertEquals(savedGameString, loadedGameString);
	}
	
	/**
	 * Attempts to save and then load an instance of the TestGame class. Checks whether the original instance of 
	 * the game and the loaded variables are the same.
	 * 
	 * @throws IOException - Saving/Loading Failed.
	 * @throws ClassNotFoundException - Savable Object Mismatch.
	 */
	@Test
	public void testValidLoad2() throws IOException, ClassNotFoundException {
		Savable savedGame = new TestGame("test", Math.random(), Math.random());
		SaveGame.save(savedGame, "test1Output");
			
		// Check that a save file was created.
		assertTrue(new File("test1Output.txt").isFile());
			
		Savable loadedGame = LoadGame.load(new File("test1Output.txt"));
			
		assertEquals("Backgrounds Not Identical", ((TestGame)savedGame).getBackground(), ((TestGame)loadedGame).getBackground());
		assertEquals("Name Not Identical", ((TestGame)savedGame).getName(), ((TestGame)loadedGame).getName());
		assertEquals("Random Number 1 Not Identical", ((TestGame)savedGame).getRandNum(), ((TestGame)loadedGame).getRandNum(), 0);
		assertEquals("Random Number 2 Not Identical", ((TestGame)savedGame).getRandNum2(), ((TestGame)loadedGame).getRandNum2(), 0);
		//assertEquals("Content Not Identical", ((TestGame)savedGame).getContent(), ((TestGame)loadedGame).getContent());
		//assertEquals("GUI Not Identical", ((TestGame)savedGame).getGUI(), ((TestGame)loadedGame).getGUI());
	}
	
	/**
	 * Attempts to load a saved file of the game where the background colour was red. 
	 * Checks whether the background colour of the game object is now red.
	 * 
	 * @throws IOException - Loading Failed.
	 * @throws ClassNotFoundException - Savable Object Mismatch.
	 */
	@Test
	public void testLoadRed() throws ClassNotFoundException, IOException {
		TestGame testGame = (TestGame)LoadGame.load(new File("testLoadRed.txt"));
		assertEquals(Color.red, testGame.getBackground());
	}
	
	/**
	 * Attempts to load a saved file of the game where the background colour was green. 
	 * Checks whether the background colour of the game object is now green.
	 * 
	 * @throws IOException - Loading Failed.
	 * @throws ClassNotFoundException - Savable Object Mismatch.
	 */
	@Test
	public void testLoadGreen() throws ClassNotFoundException, IOException {
		TestGame testGame = (TestGame)LoadGame.load(new File("testLoadGreen.txt"));
		assertEquals(Color.green, testGame.getBackground());
	}
	
	/**
	 * Attempts to load a saved file of the game where the background colour was blue. 
	 * Checks whether the background colour of the game object is now blue
	 * 
	 * @throws IOException - Loading Failed.
	 * @throws ClassNotFoundException - Savable Object Mismatch.
	 */
	@Test
	public void testLoadBlue() throws ClassNotFoundException, IOException {
		TestGame testGame = (TestGame)LoadGame.load(new File("testLoadBlue.txt"));
		assertEquals(Color.blue, testGame.getBackground());
	}
	
	/**
	 * Attempts to load an invalid saved file.
	 * Should throw an error as the file is invalid.
	 * 
	 * @throws IOException - File Writing Failed
	 */
	@Test
	public void testInvalidLoad() throws IOException {
		FileOutputStream fos = new FileOutputStream("testInvalidLoad.txt"); 
		ObjectOutputStream out = new ObjectOutputStream(fos);
		out.writeObject(new String("TestInvalid"));
		out.flush();
		out.close();
		
		try {
			@SuppressWarnings("unused")
			TestGame testGame = (TestGame)LoadGame.load(new File("testInvalidLoad.txt"));
		} catch (ClassNotFoundException | IOException e) {
			assertTrue("Exception Correctly Thrown", true);
		}
	}
}
