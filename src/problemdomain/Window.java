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
	private final boolean ENCRYPTED_MODE;
	
	public static final Window getInstance(boolean mode)
	{
		return new Window(mode);
	}

	private Window(boolean mode)
	{
		ENCRYPTED_MODE = mode;
	}

	public boolean isEncryptedMode()
	{
		return ENCRYPTED_MODE;
	}
}
