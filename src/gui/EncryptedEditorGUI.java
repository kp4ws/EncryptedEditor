package gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Enumeration;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
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

import manager.EncryptedEditorManager;

/**
 * MainWindow acts as the manager of the program
 * 
 * @author kentp
 *
 */
public class EncryptedEditorGUI extends JFrame
{
	// file info
	private String filePath;
	private boolean fileChanged;

	// general use
	private JLabel label;
	private JPanel panel;
	private JTextArea textArea;
	private JScrollPane scroll;

	private static final long serialVersionUID = 1L;

	private EncryptedEditorManager manager;

	private final int FONT_SIZE = 14;
	private final int WINDOW_WIDTH = 800;
	private final int WINDOW_HEIGHT = 600;

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

	public EncryptedEditorGUI()
	{
		// TODO: change to dark theme when encrypted and light theme when not

		manager = new EncryptedEditorManager();
		// originally the file will not have a name
		setTitle("Untitled - Encrypted Editor");
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// center the window
		setLocationRelativeTo(null);
		// setUIFont();
		buildMenuBar();
		buildPanel();
		add(panel);
		setVisible(true);
	}

	public void setUIFont()
	{
		UIManager.put("Button.font", FONT_SIZE);
		UIManager.put("ToggleButton.font", FONT_SIZE);
		UIManager.put("RadioButton.font", FONT_SIZE/* font of your liking */);
		UIManager.put("CheckBox.font", FONT_SIZE/* font of your liking */);
		UIManager.put("ColorChooser.font", FONT_SIZE/* font of your liking */);
		UIManager.put("ComboBox.font", FONT_SIZE/* font of your liking */);
		UIManager.put("Label.font", FONT_SIZE/* font of your liking */);
		UIManager.put("List.font", FONT_SIZE/* font of your liking */);
		UIManager.put("MenuBar.font", FONT_SIZE/* font of your liking */);
		UIManager.put("MenuItem.font", FONT_SIZE/* font of your liking */);
		UIManager.put("RadioButtonMenuItem.font", FONT_SIZE/* font of your liking */);
		UIManager.put("CheckBoxMenuItem.font", FONT_SIZE/* font of your liking */);
		UIManager.put("Menu.font", FONT_SIZE/* font of your liking */);
		UIManager.put("PopupMenu.font", FONT_SIZE/* font of your liking */);
		UIManager.put("OptionPane.font", FONT_SIZE/* font of your liking */);
		UIManager.put("Panel.font", FONT_SIZE/* font of your liking */);
		UIManager.put("ProgressBar.font", FONT_SIZE/* font of your liking */);
		UIManager.put("ScrollPane.font", FONT_SIZE/* font of your liking */);
		UIManager.put("Viewport.font", FONT_SIZE/* font of your liking */);
		UIManager.put("TabbedPane.font", FONT_SIZE/* font of your liking */);
		UIManager.put("Table.font", FONT_SIZE/* font of your liking */);
		UIManager.put("TableHeader.font", FONT_SIZE/* font of your liking */);
		UIManager.put("TextField.font", FONT_SIZE/* font of your liking */);
		UIManager.put("PasswordField.font", FONT_SIZE/* font of your liking */);
		UIManager.put("TextArea.font", FONT_SIZE/* font of your liking */);
		UIManager.put("TextPane.font", FONT_SIZE/* font of your liking */);
		UIManager.put("EditorPane.font", FONT_SIZE/* font of your liking */);
		UIManager.put("TitledBorder.font", FONT_SIZE/* font of your liking */);
		UIManager.put("ToolBar.font", FONT_SIZE/* font of your liking */);
		UIManager.put("ToolTip.font", FONT_SIZE/* font of your liking */);
		UIManager.put("Tree.font", FONT_SIZE/* font of your liking */);
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
				fileChanged = true;
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

		setJMenuBar(menuBar);
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
				int option = -1;
				if (fileChanged)
				{
					option = JOptionPane.showConfirmDialog(panel, "Do you want to save changes?");
				}

				switch (option)
				{

				// yes
				case 0:
					// TODO: save data
					break;

				// cancel
				case 2:
					break;

				// no
				case 1:

					// reset values
				default:
					manager.setEncrypted(true);
					filePath = "";
					textArea.setText("");
					setTitle("Untitled - Encrypted Editor");
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
				filePath = JOptionPane.showInputDialog(panel, "Enter the filename: ");
				textArea.setText(getData());
				setTitle(manager.getFileName(filePath) + " - Encrypted Editor");

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

					if (input != null)
					{

						int option = -1;

						if (manager.fileExists(filePath))
						{
							option = JOptionPane.showConfirmDialog(panel,
									filePath + " already exists.\nDo you want to replace it?");
						}

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
							saveData();

							// change this into a method
							// sasdfetTitle(filePath + " - Encrypted Editor");
						}
					}

				} else
				{
					saveData();
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

					if (manager.fileExists(filePath))
					{
						option = JOptionPane.showConfirmDialog(panel,
								filePath + " already exists.\nDo you want to replace it?");
					}

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
						saveData();
						// setTitle(manager.getFileName() + " - Encrypted Editor");
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
		encryptedMode.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				manager.setEncrypted(encryptedMode.isSelected());

				if (manager.isEncrypted())
				{
					label.setText("Encrypted Mode: ON");
				} else
				{
					label.setText("Encrypted Mode: OFF");
				}
			}
		});

		editMenu = new JMenu("Edit");

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
	}

	private void buildViewMenu()
	{
		viewMenu = new JMenu("View");
	}

	private void buildHelpMenu()
	{
		aboutItem = new JMenuItem("About Encrypted Editor");
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
		helpMenu.add(aboutItem);
	}

	private void saveData()
	{
		if (manager.isEncrypted())
		{
			int key = Integer.parseInt(JOptionPane.showInputDialog(panel, "Enter the key: "));
			manager.writeEncryptedFile(key, textArea.getText(), filePath);
		} else
		{
			manager.writeFile(textArea.getText(), filePath);
		}
	}

	private String getData()
	{
		if (manager.isEncrypted())
		{
			int key = Integer.parseInt(JOptionPane.showInputDialog(panel, "Enter the key: "));
			return manager.readEncryptedFile(key, filePath);
		} else
		{
			return manager.readFile(filePath);
		}
	}
}
