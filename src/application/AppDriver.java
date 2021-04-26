package application;

import gui.GUIManager;

/**
 * Starts up the application and instantiates the GUI Manager.
 * 
 * @author kentp
 * @version 1.1
 */
public final class AppDriver
{

	/**
	 * Main method of the program
	 * 
	 * @param args Arguments passed in from command line
	 */
	public static void main(String[] args)
	{
		GUIManager.getInstance();
	}
}
