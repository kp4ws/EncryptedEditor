package gui;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

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

import manager.EncryptedEditorManager;

/**
 * MainWindow acts as the manager of the program
 * 
 * @author kentp
 *
 */
public class EncryptedEditorGUI extends JFrame
{
	// general use
	private JLabel label;
	private JPanel panel;
	private JTextArea textArea;
	private JScrollPane scroll;

	private static final long serialVersionUID = 1L;

	private EncryptedEditorManager manager;

	private final int WINDOW_WIDTH = 640;
	private final int WINDOW_HEIGHT = 480;

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
		buildMenuBar();
		buildPanel();
		add(panel);
		setVisible(true);
	}

	private void buildPanel()
	{
		panel = new JPanel(new BorderLayout(1, 0));
		panel.add(label, BorderLayout.NORTH);
		// white space on west side of the window
		panel.add(new JLabel(), BorderLayout.WEST);
		textArea = new JTextArea();
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
				if (!manager.getMessage().equals(textArea.getText()))
				{
					option = JOptionPane.showConfirmDialog(panel, "Do you want to save changes?");
				}

				switch (option)
				{
				//yes
				case 0:
					//TODO: save data
					break;
					
				// no
				case 1:
				// cancel
				case 2:

					// reset values
				default:
					manager.setEncrypted(true);
					manager.setFilePath("");
					manager.setMessage("");
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
				String input = JOptionPane.showInputDialog(panel, "Enter the filename: ");
				manager.setFilePath(input);
				textArea.setText(manager.getFileData());
				setTitle(manager.getFileName() + " - Encrypted Editor");
				;
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
				// if file doesn't exist currently
				if (manager.getFilePath().equals(""))
				{
					String input = JOptionPane.showInputDialog(panel, "Enter the filename: ");

					if (input != null)
					{
						// eliminates illegal characters
						String filePath = input.replaceAll("[\\\\/:*?\"<>|]", "");
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
							manager.setFilePath(filePath);
							manager.setMessage(textArea.getText());
							manager.persist();
							setTitle(manager.getFileName() + " - Encrypted Editor");
						}
					}

				} else
				{
					manager.setMessage(textArea.getText());
					manager.persist();
				}
			}
		});

		saveAsItem = new JMenuItem("Save As");
		saveAsItem.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				String input = JOptionPane.showInputDialog(panel, "Enter the filename: ");

				if (input != null)
				{
					// eliminates illegal characters
					String filePath = input.replaceAll("[\\\\/:*?\"<>|]", "");
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
						manager.setFilePath(filePath);
						manager.setMessage(textArea.getText());
						manager.persist();
						setTitle(manager.getFileName() + " - Encrypted Editor");
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
}
