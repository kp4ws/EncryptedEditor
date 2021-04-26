package gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.rmi.server.Operation;
import java.time.Year;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.FontUIResource;

import problemdomain.Window;
import utility.WindowUtility;




/**
 * GUIManager acts as the manager of the program and delegates work to the
 * appropriate methods.
 * 
 * @author kentp
 * @version 1.1
 */
public final class GUIManager
{
	public enum Option {YES, NO, CANCEL}
	// file info
	private String filePath;
	private boolean fileChanged;
	private boolean encrypted;

	// general use
	private JLabel label;
	private JPanel panel;
	private JTextArea textArea;
	private JScrollPane scroll;

	private final String WINDOOW_NAME_EXTENSION = " - Encrypted Editor";

	// Menu-bar containing list of sub-menus
	private JMenuBar menuBar;

	// File sub-menu components
	private JMenu fileMenu;
	private JMenuItem newItem;
	private JMenuItem openItem;
	private JMenuItem saveItem;
	private JMenuItem saveAsItem;
	private JMenuItem exitItem;

	// Edit sub-menu components
	private JMenu editMenu;
	private JMenuItem undoItem;
	private JMenuItem redoItem;
	private JMenuItem cutItem;
	private JMenuItem copyItem;
	private JMenuItem pasteItem;
	private JMenuItem deleteItem;
	private JCheckBoxMenuItem encryptedMode;

	// TODO: Format sub-menu components
	private JMenu formatMenu;

	// TODO: View sub-menu components
	private JMenu viewMenu;

	// TODO: Help sub-menu components
	private JMenu helpMenu;
	private JMenuItem aboutItem;

	private Window window;

	private final Font font = new Font("serif", Font.PLAIN, 20);

	public static GUIManager getInstance()
	{
		return new GUIManager();
	}

	private GUIManager()
	{
		window = Window.getInstance();
		encrypted = true;
		filePath = "";
		fileChanged = false;

		buildMenuBar();
		buildPanel();

		window.add(panel);

		WindowUtility.createWindow(window);
	}

	private void buildPanel()
	{
		panel = new JPanel(new BorderLayout(1, 0));
		panel.add(label, BorderLayout.NORTH);
		
		// white space on west side of the window
		panel.add(new JLabel(), BorderLayout.WEST);

		textArea = new JTextArea();
		
		textArea.getDocument().addDocumentListener(new DocumentListener()
		{

			@Override
			public void removeUpdate(DocumentEvent e)
			{
				fileChanged = true;
			}

			@Override
			public void insertUpdate(DocumentEvent e)
			{
				fileChanged = true;
			}

			@Override
			public void changedUpdate(DocumentEvent e)
			{
				// fileChanged = true;
			}
		});

		scroll = new JScrollPane(textArea);
		panel.add(scroll);
	}

	private void buildMenuBar()
	{
		buildFileMenu();
		buildEditMenu();
		buildFormatMenu();
		buildViewMenu();
		buildHelpMenu();

		menuBar = new JMenuBar();
		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		menuBar.add(formatMenu);
		menuBar.add(viewMenu);
		menuBar.add(helpMenu);

		window.setJMenuBar(menuBar);
	}

	private void buildFileMenu()
	{
		newItem = new JMenuItem("New");
		// Ctrl + N
		newItem.setAccelerator(KeyStroke.getKeyStroke('N', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		newItem.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				int option;
				
				//if (fileChanged)
				{
					 option = JOptionPane.showConfirmDialog(panel, "Do you want to save changes?"));
				}
				
				switch (option)
				{

				// yes
				case YES:
					// TODO: save changes
					/*
					 * String input = JOptionPane.showInputDialog(panel, "Enter the filename: ")
					 * .replaceAll("[\\\\/:*?\"<>|]", "");
					 * 
					 * if (input != null) { int option = -1;
					 * 
					 * if (manager.fileExists(filePath)) { option =
					 * JOptionPane.showConfirmDialog(panel, filePath +
					 * " already exists.\nDo you want to replace it?"); } }
					 */
					break;

				// cancel
				case NO:
					break;

				// no
				case CANCEL:

					// reset values
				default:
					encrypted = true;
					filePath = "";
					textArea.setText("");
					// setTitle(NEW_WINDOW_NAME);
					textArea.setText("");
				}

			}

		});

		openItem = new JMenuItem("Open");
		// Ctrl + O
		openItem.setAccelerator(KeyStroke.getKeyStroke('O', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		openItem.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				String input = JOptionPane.showInputDialog(panel, "Enter the filename: ");

				if (input != null)
				{
					filePath = input;
					/*
					 * if (manager.fileExists(filePath)) { if (encrypted) { String key =
					 * JOptionPane.showInputDialog(panel, "");
					 * textArea.setText(manager.readEncryptedFile(Integer.parseInt(key), filePath));
					 * } else { textArea.setText(manager.readFile(input)); }
					 * 
					 * setTitle(manager.getFileName(filePath) + " - Encrypted Editor"); } else {
					 * JOptionPane.showMessageDialog(panel,
					 * "File not found. Check the file name and try again."); }
					 */

				}
			}
		});

		saveItem = new JMenuItem("Save");
		saveItem.setMnemonic(KeyEvent.VK_S);
		// Ctrl + S
		saveItem.setAccelerator(KeyStroke.getKeyStroke('S', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		saveItem.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				// if the file isn't currently opened in the window
				if (filePath.equals(""))
				{
					// eliminates illegal characters
					String input = JOptionPane.showInputDialog(panel, "Enter the filename: ")
							.replaceAll("[\\\\/:*?\"<>|]", "");

					/*
					 * if (input != null) {
					 * 
					 * int option = -1;
					 * 
					 * if (manager.fileExists(filePath)) { option =
					 * JOptionPane.showConfirmDialog(panel, filePath +
					 * " already exists.\nDo you want to replace it?"); }
					 * 
					 * switch (option) { // user presses 'no' case 1: // user presses 'cancel' case
					 * 2:
					 * 
					 * break;
					 * 
					 * // continue default: filePath = input; save(); } }
					 */

				} else
				{
					save();
				}
			}
		});

		saveAsItem = new JMenuItem("Save As");
		saveAsItem.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				// eliminates illegal characters
				String input = JOptionPane.showInputDialog(panel, "Enter the filename: ").replaceAll("[\\\\/:*?\"<>|]",
						"");

				if (input != null)
				{
					int option = -1;
					/*
					 * if (manager.fileExists(filePath)) { option =
					 * JOptionPane.showConfirmDialog(panel, filePath +
					 * " already exists.\nDo you want to replace it?"); }
					 */
					switch (option)
					{
					// user presses 'no'
					case 1:
						// user presses 'cancel'
					case 2:

						break;

					// continue
					default:
						filePath = input;
						save();
					}
				}

			}
		});

		exitItem = new JMenuItem("Exit");
		exitItem.setMnemonic(KeyEvent.VK_X);
		exitItem.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				int option = JOptionPane.showConfirmDialog(panel, "Are you sure you want to exit?");
				if (option == 0)
				{
					System.exit(0);
				}
			}
		});

		fileMenu = new JMenu("File");
		fileMenu.setFont(font);
		fileMenu.setMnemonic(KeyEvent.VK_F);

		fileMenu.add(newItem);
		fileMenu.add(openItem);
		fileMenu.add(saveItem);
		fileMenu.add(saveAsItem);
		fileMenu.add(new JSeparator());

		fileMenu.add(exitItem);

	}

	private void buildEditMenu()
	{
		undoItem = new JMenuItem("Undo");
		undoItem.setAccelerator(KeyStroke.getKeyStroke('Z', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));

		redoItem = new JMenuItem("Redo");
		redoItem.setAccelerator(KeyStroke.getKeyStroke('Y', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		cutItem = new JMenuItem("Cut");
		cutItem.setAccelerator(KeyStroke.getKeyStroke('X', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));

		copyItem = new JMenuItem("Copy");
		copyItem.setAccelerator(KeyStroke.getKeyStroke('C', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));

		pasteItem = new JMenuItem("Paste");
		pasteItem.setAccelerator(KeyStroke.getKeyStroke('V', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));

		deleteItem = new JMenuItem("Delete");
		deleteItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0));
		deleteItem.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				JOptionPane.showMessageDialog(panel, "Delete");
			}
		});

		encryptedMode = new JCheckBoxMenuItem("Encrypted Mode");
		encryptedMode.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
		encryptedMode.setSelected(true);
		// By default, encrypted mode is on
		label = new JLabel("Encrypted Mode: ON", SwingConstants.CENTER);
		label.setFont(font);
		encryptedMode.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (encrypted)
				{
					label.setText("Encrypted Mode: ON");
					encrypted = true;
				} else
				{
					label.setText("Encrypted Mode: OFF");
					encrypted = false;
				}
			}
		});

		editMenu = new JMenu("Edit");
		editMenu.setFont(font);

		editMenu.add(undoItem);
		editMenu.add(redoItem);
		editMenu.add(new JSeparator());

		editMenu.add(cutItem);
		editMenu.add(copyItem);
		editMenu.add(pasteItem);
		editMenu.add(deleteItem);
		editMenu.add(new JSeparator());

		editMenu.add(encryptedMode);
	}

	private void buildFormatMenu()
	{
		formatMenu = new JMenu("Format");
		formatMenu.setFont(font);
	}

	private void buildViewMenu()
	{
		viewMenu = new JMenu("View");
		viewMenu.setFont(font);
	}

	private void buildHelpMenu()
	{
		aboutItem = new JMenuItem("About Encrypted Editor");
		aboutItem.setFont(font);

		aboutItem.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				JOptionPane.showMessageDialog(panel, "Encrypted Editor is currently a work in progress."
						+ "\nIdeally I plan to create an interface similar to notepad and has the ability to encrypt messages that the user enters");
			}
		});

		helpMenu = new JMenu("Help");
		helpMenu.setFont(font);
		helpMenu.add(aboutItem);
	}

	private void save()
	{
		if (encrypted)
		{
			String input = JOptionPane.showInputDialog(panel, "Enter the key: ");

			if (input != null)
			{
				// manager.writeEncryptedFile(Integer.parseInt(input), textArea.getText(),
				// filePath);
			}

		} else
		{
			// manager.writeFile(textArea.getText(), filePath);
		}

		// setTitle(manager.getFileName(filePath) + WINDOOW_NAME_EXTENSION);
	}
}
