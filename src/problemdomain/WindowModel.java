package problemdomain;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

/**
 * Acts as the Subject and adheres to the Observer Pattern.
 * 
 * @author kentp
 * @version 1.1
 */
public final class WindowModel {
	private static final String MODE = "mode";
	private static final String TITLE = "title";
	private static final String RESET = "reset";

	private WindowProperties windowProperties;
	private ArrayList<PropertyChangeListener> listener = new ArrayList<PropertyChangeListener>();

	public WindowModel() {
		windowProperties = new WindowProperties();
	}

	public WindowProperties getWindowProperties() {
		return windowProperties;
	}

	private void notifyListeners(Object object, String property, String oldValue, String newValue) {
		for (PropertyChangeListener l : listener) {
			l.propertyChange(new PropertyChangeEvent(this, property, oldValue, newValue));
		}
	}

	public void addChangeListener(PropertyChangeListener newListener) {
		listener.add(newListener);
	}

	/**
	 * Contains the window properties for the main window.
	 * 
	 * @author kentp
	 * @version 1.1
	 */
	public final class WindowProperties {
		private boolean encrypted;
		private boolean textChanged;
		private String windowTitle;
		private final String NEW_WINDOW_TITLE = "Untitled";

		public WindowProperties() {
			reset();
		}

		public WindowProperties(boolean encrypted, boolean textChanged, String windowTitle) {
			this.encrypted = encrypted;
			this.textChanged = textChanged;
			this.windowTitle = windowTitle;
		}

		public void reset() {
			this.encrypted = true;
			this.textChanged = false;
			this.windowTitle = NEW_WINDOW_TITLE;
			notifyListeners(this, RESET, null, null);

		}

		public boolean isEncrypted() {
			return encrypted;
		}

		public void setEncryptedMode(boolean encryptedMode) {
			notifyListeners(this, MODE, Boolean.toString(this.encrypted),
					Boolean.toString(this.encrypted = encryptedMode));
		}

		public boolean isTextChanged() {
			return textChanged;
		}

		public void setTextChanged(boolean textChanged) {
			this.textChanged = textChanged;
		}

		public String getTitle() {
			return windowTitle;
		}

		public void setTitle(String windowTitle) {
			notifyListeners(this, TITLE, this.windowTitle, this.windowTitle = windowTitle);
		}

		public String getHeader() {
			return (encrypted == true ? "Encrypted Mode: ON" : "Encrypted Mode: OFF");
		}
	}
}