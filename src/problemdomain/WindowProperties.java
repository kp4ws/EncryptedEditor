package problemdomain;

import javax.swing.JFrame;

/**
 * Java bean class used to hold the properties for the window
 * 
 * @author kentp
 * @version 1.1
 */
public final class WindowProperties {
	private final String WINDOW_EXTENSION = " - Encrypted Editor";
	private boolean encrypted;
	private boolean textChanged;
	private String windowTitle;

	public WindowProperties() {
		reset();
	}

	public WindowProperties(boolean encrypted, String windowTitle) {
		this.encrypted = encrypted;
		this.windowTitle = windowTitle + WINDOW_EXTENSION;
	}
	
	public void reset()
	{
		this.encrypted = true;
		this.windowTitle = "Untitled" + WINDOW_EXTENSION;
	}

	public String getHeader() {
		return encrypted == true ? "Encrypted Mode: ON" : "Encrypted Mode: OFF";
	}

	public boolean isEncrypted() {
		return encrypted;
	}

	public void setEncryptedMode(boolean encryptedMode) {
		this.encrypted = encryptedMode;
	}

	public boolean isTextChanged() {
		return textChanged;
	}

	public void setTextChanged(boolean textChanged) {
		this.textChanged = textChanged;
	}

	public String getWindowTitle() {
		return windowTitle;
	}

	public void setWindowTitle(String windowTitle) {
		this.windowTitle = windowTitle + WINDOW_EXTENSION;
	}

}
