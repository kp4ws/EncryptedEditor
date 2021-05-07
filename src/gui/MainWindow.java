package gui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JFrame;

import problemdomain.WindowModel;
import problemdomain.WindowModel.WindowProperties;

/**
 * Main window of the application and acts as an Observer. Adheres to the
 * Observer Pattern.
 * 
 * @author kentp
 * @version 1.1
 */
public final class MainWindow extends JFrame implements PropertyChangeListener {
	private static final long serialVersionUID = 1L;
	private final int WINDOW_WIDTH = 800;
	private final int WINDOW_HEIGHT = 600;
	private final String WINDOW_EXTENSION = " - Encrypted Editor";
	private final String MODE = "mode";
	private final String TITLE = "title";
	private final String RESET = "reset";

	WindowProperties properties;
	GUIManager manager;

	public MainWindow(WindowModel model, GUIManager manager) {
		model.addChangeListener(this);
		this.manager = manager;
		this.properties = model.getWindowProperties();

		setTitle(properties.getTitle() + WINDOW_EXTENSION);
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}

	public void display() {
		setVisible(true);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String event = evt.getPropertyName();

		switch (event) {
		case TITLE:
			setTitle(evt.getNewValue().toString() + WINDOW_EXTENSION);
			break;

		case MODE:
			manager.getHeaderLabel().setText(properties.getHeader());
			break;

		case RESET:
			setTitle(properties.getTitle() + WINDOW_EXTENSION);
			manager.getHeaderLabel().setText(properties.getHeader());
			manager.getTextArea().setText("");
			break;

		default:
			break;
		}
	}
}
