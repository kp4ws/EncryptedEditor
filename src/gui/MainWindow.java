package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
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
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import manager.EncryptedEditorManager;

/**
 * MainWindow acts as the manager of the program
 * 
 * @author kentp
 *
 */
public class MainWindow extends JFrame
{
	//test
	
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

	public MainWindow()
	{
		// TODO: change to dark theme when encrypted and light theme when not
		// changeTheme();

		manager = new EncryptedEditorManager();
		setTitle(manager.getFileName() + " - Encrypted Editor");
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// center the window
		setLocationRelativeTo(null);
		buildMenuBar();
		buildPanel();
		add(panel);
		setVisible(true);
		textArea.setText(manager.readFile());
	}

	private void changeTheme()
	{
		UIManager.put("control", new Color(128, 128, 128));
		UIManager.put("info", new Color(128, 128, 128));
		UIManager.put("nimbusBase", new Color(18, 30, 49));
		UIManager.put("nimbusAlertYellow", new Color(248, 187, 0));
		UIManager.put("nimbusDisabledText", new Color(128, 128, 128));
		UIManager.put("nimbusFocus", new Color(115, 164, 209));
		UIManager.put("nimbusGreen", new Color(176, 179, 50));
		UIManager.put("nimbusInfoBlue", new Color(66, 139, 221));
		UIManager.put("nimbusLightBackground", new Color(18, 30, 49));
		UIManager.put("nimbusOrange", new Color(191, 98, 4));
		UIManager.put("nimbusRed", new Color(169, 46, 34));
		UIManager.put("nimbusSelectedText", new Color(255, 255, 255));
		UIManager.put("nimbusSelectionBackground", new Color(104, 93, 156));
		UIManager.put("text", new Color(230, 230, 230));
		try
		{
			for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels())
			{
				if ("Nimbus".equals(info.getName()))
				{
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		} catch (InstantiationException e)
		{
			e.printStackTrace();
		} catch (IllegalAccessException e)
		{
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e)
		{
			e.printStackTrace();
		} catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	private void buildPanel()
	{
		panel = new JPanel(new BorderLayout(1, 0));
		panel.add(label, BorderLayout.NORTH);
		// white space
		panel.add(new JLabel(), BorderLayout.WEST);
		textArea = new JTextArea(5, 2);
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
				// if there is a message pending and the message doesn't equal the manager's
				// message
				if (!textArea.getText().equals("") && !manager.getMessage().equals(textArea.getText()))
				{
					int option = JOptionPane.showConfirmDialog(panel,
							"Do you want to save changes to " + manager.getFileName() + "?");

					if (option == 0)
					{
						if (manager.getFileName().equals("Untitled"))
						{
							String fileName = JOptionPane.showInputDialog(panel, "Enter the filename: ");
							manager.setFileName(fileName);
							manager.setMessage(textArea.getText());
							manager.saveFile();
							JOptionPane.showMessageDialog(panel, "Save Successful");
						} else
						{
							manager.setMessage(textArea.getText());
							manager.saveFile();
							JOptionPane.showMessageDialog(panel, "Saved changes");
						}
					}
				}

				// reset fileName
				manager.setFileName("");
				// reset message
				manager.setMessage("");
				// reset text
				textArea.setText("");
				JOptionPane.showMessageDialog(panel, "New File Created");
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
				// if there is a message pending and the message doesn't equal the manager's
				// message
				if (!textArea.getText().equals("") && !manager.getMessage().equals(textArea.getText()))
				{
					int option = JOptionPane.showConfirmDialog(panel,
							"Do you want to save changes to " + manager.getFileName() + "?");

					if (option == 0)
					{
						if (manager.getFileName().equals("Untitled"))
						{
							String fileName = JOptionPane.showInputDialog(panel, "Enter the filename: ");
							manager.setFileName(fileName);
							manager.setMessage(textArea.getText());
							manager.saveFile();
							JOptionPane.showMessageDialog(panel, "Save Successful");
						} else
						{
							manager.setMessage(textArea.getText());
							manager.saveFile();
							JOptionPane.showMessageDialog(panel, "Saved changes");
						}

						// reset fileName
						manager.setFileName("");
						// reset message
						manager.setMessage("");
						// reset text
						textArea.setText("");
						JOptionPane.showMessageDialog(panel, "New File Created");
					} else if (option == 1)
					{
						// reset fileName
						manager.setFileName("");
						// reset message
						manager.setMessage("");
						// reset text
						textArea.setText("");
						JOptionPane.showMessageDialog(panel, "New File Created");
					}
				} else
				{
					// reset fileName
					manager.setFileName("");
					// reset message
					manager.setMessage("");
					// reset text
					textArea.setText("");
					JOptionPane.showMessageDialog(panel, "New File Created");
				}

				JOptionPane.showMessageDialog(panel, "Open File");
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
				if (manager.getFileName().equals(""))
				{
					String fileName = JOptionPane.showInputDialog(panel, "Enter the filename: ");
					manager.setFileName(fileName);
					manager.setMessage(textArea.getText());
					manager.saveFile();
					JOptionPane.showMessageDialog(panel, "Save Successful");
				} else
				{
					manager.setMessage(textArea.getText());
					manager.saveFile();
					JOptionPane.showMessageDialog(panel, "Save Successful");
				}

				// JOptionPane.showMessageDialog(panel, textArea.getText());
			}
		});

		saveAsItem = new JMenuItem("Save As");
		saveAsItem.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				String fileName = JOptionPane.showInputDialog(panel, "Enter the filename: ");
				manager.setFileName(fileName);
				manager.setMessage(textArea.getText());
				manager.saveFile();
				JOptionPane.showMessageDialog(panel, "Save Successful");
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
