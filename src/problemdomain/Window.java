package problemdomain;

import javax.swing.JFrame;

/**
 * The main window of the program. Uses the Singleton pattern to ensure there is
 * only ever 1 instance of this class created.
 * 
 * @author kentp
 * @version 1.1
 */
public final class Window extends JFrame
{
	private static Window windowInstance = null;

	public static final Window getInstance()
	{
		if (windowInstance == null)
		{
			windowInstance = new Window();
		}

		return windowInstance;
	}

	private Window() {}
}
