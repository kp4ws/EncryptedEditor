package utility;

import problemdomain.Window;import java.awt.Font;

import javax.swing.JFrame;

/**
 * Acts as a utility class for operations such as creating the window.
 * 
 * @author kentp
 * @version 1.1
 */
public final class WindowUtility
{
	private static final String NEW_WINDOW_NAME = "Untitled - Encrypted Editor";
	private static final int WINDOW_WIDTH = 800;
	private static final int WINDOW_HEIGHT = 600;

	private WindowUtility() {}
	
	public static void createWindow(Window window)
	{
		window.setTitle(NEW_WINDOW_NAME);
		window.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}
}
