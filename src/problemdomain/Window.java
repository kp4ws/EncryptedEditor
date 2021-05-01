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
	private final String WINDOW_EXTENSION = " - Encrypted Editor";
	private final boolean ENCRYPTED_MODE;
	private final String WINDOW_TITLE;
	
	public static final Window getInstance(boolean mode, String title)
	{
		return new Window(mode, title);
	}

	private Window(boolean encrypted, String title)
	{
		ENCRYPTED_MODE = encrypted;
		WINDOW_TITLE = title + WINDOW_EXTENSION;
	}

	public String getWindowTitle()
	{
		return WINDOW_TITLE;
	}
	
	public boolean isEncrypted()
	{
		return ENCRYPTED_MODE;
	}
	
	public String getHeader()
	{
		return ENCRYPTED_MODE == true ? "Encrypted Mode: ON" : "Encrypted Mode: OFF";
	}
}
