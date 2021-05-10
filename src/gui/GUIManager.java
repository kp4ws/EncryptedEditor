
package gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
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
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import problemdomain.WindowModel;
import problemdomain.WindowModel.WindowProperties;
import utility.EncryptUtility;

/**
 * GUIManager acts as the manager of the program and delegates work to the
 * appropriate methods.
 * 
 * @author kentp
 * @version 1.1
 */
public final class GUIManager
{

	// constants
	private final Font MAIN_FONT = new Font("serif", Font.PLAIN, 20);
	private final Font SUB_FONT = new Font("serif", Font.PLAIN, 18);

	private JLabel headerLabel;
	private JPanel mainPanel;
	private JTextArea mainTextArea;
	private JScrollPane scrollPane;

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
	private JCheckBoxMenuItem encryptedMode;

	// Help sub-menu components
	private JMenu helpMenu;
	private JMenuItem aboutItem;

	// cached references
	private WindowProperties windowProperties;
	private MainWindow mainWindow;
	private FileServices fileServices;
	private static GUIManager managerInstance;

	public static GUIManager getInstance()
	{
		if (managerInstance == null)
		{
			managerInstance = new GUIManager();
		}

		return managerInstance;
	}

	private GUIManager()
	{

		WindowModel model = new WindowModel();
		windowProperties = model.getWindowProperties();
		mainWindow = new MainWindow(model, this);
		fileServices = new FileServices();

		buildMenuBar();
		buildPanel();

		mainWindow.add(mainPanel);
		mainWindow.display();
	}

	public JLabel getHeaderLabel()
	{
		return headerLabel;
	}

	public JTextArea getTextArea()
	{
		return mainTextArea;
	}

	private void buildPanel()
	{
		mainPanel = new JPanel(new BorderLayout(1, 0));
		headerLabel = new JLabel(windowProperties.getHeader(), SwingConstants.CENTER);
		headerLabel.setFont(MAIN_FONT);
		mainPanel.add(headerLabel, BorderLayout.NORTH);

		// white space on west side of the window
		mainPanel.add(new JLabel(), BorderLayout.WEST);

		mainTextArea = new JTextArea();
		mainTextArea.setFont(MAIN_FONT);

		mainTextArea.getDocument().addDocumentListener(new DocumentListener()
		{

			@Override
			public void removeUpdate(DocumentEvent e)
			{
				windowProperties.setTextChanged(true);
			}

			@Override
			public void insertUpdate(DocumentEvent e)
			{
				windowProperties.setTextChanged(true);
			}

			@Override
			public void changedUpdate(DocumentEvent e)
			{
			}
		});

		scrollPane = new JScrollPane(mainTextArea);
		mainPanel.add(scrollPane);
	}

	private void buildMenuBar()
	{
		buildFileMenu();
		buildEditMenu();
		buildHelpMenu();

		menuBar = new JMenuBar();
		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		menuBar.add(helpMenu);

		mainWindow.setJMenuBar(menuBar);
	}

	private void buildFileMenu()
	{
		newItem = new JMenuItem("New");
		newItem.setFont(SUB_FONT);
		// Ctrl + N
		newItem.setAccelerator(KeyStroke.getKeyStroke('N', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		newItem.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				fileServices.newFile();
			}

		});

		openItem = new JMenuItem("Open");
		openItem.setFont(SUB_FONT);
		// Ctrl + O
		openItem.setAccelerator(KeyStroke.getKeyStroke('O', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		openItem.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				fileServices.openFile();
			}
		});

		saveItem = new JMenuItem("Save");
		saveItem.setFont(SUB_FONT);
		saveItem.setMnemonic(KeyEvent.VK_S);
		// Ctrl + S
		saveItem.setAccelerator(KeyStroke.getKeyStroke('S', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		saveItem.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				fileServices.saveFile();
			}
		});

		saveAsItem = new JMenuItem("Save As");
		saveAsItem.setFont(SUB_FONT);
		saveAsItem.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				fileServices.saveAsFile();
			}
		});

		exitItem = new JMenuItem("Exit");
		exitItem.setFont(SUB_FONT);
		exitItem.setMnemonic(KeyEvent.VK_X);
		exitItem.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				int option = JOptionPane.showConfirmDialog(mainPanel, "Are you sure you want to exit?",
						"Select an Option", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
				if (option == 0)
				{
					System.exit(0);
				}
			}
		});

		fileMenu = new JMenu("File");
		fileMenu.setFont(MAIN_FONT);
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

		encryptedMode = new JCheckBoxMenuItem("Encrypted Mode");
		encryptedMode.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
		encryptedMode.setSelected(true);
		encryptedMode.setFont(SUB_FONT);
		encryptedMode.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				windowProperties.setEncryptedMode(!windowProperties.isEncrypted());
			}
		});

		editMenu = new JMenu("Edit");
		editMenu.setFont(MAIN_FONT);
		editMenu.add(encryptedMode);
	}

	private void buildHelpMenu()
	{
		aboutItem = new JMenuItem("About Encrypted Editor");
		aboutItem.setFont(MAIN_FONT);
		aboutItem.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				JOptionPane.showMessageDialog(mainPanel,
						"Encrypted Editor is a simple text editor that allows the user to encrypt messages.");
			}
		});

		helpMenu = new JMenu("Help");
		helpMenu.setFont(MAIN_FONT);
		helpMenu.add(aboutItem);
	}

	private void displayGenericError()
	{
		JOptionPane.showMessageDialog(mainPanel, "Error occurred, please try again.", "ERROR",
				JOptionPane.ERROR_MESSAGE);
	}

	private boolean confirmSaveMessage()
	{
		int option = JOptionPane.showConfirmDialog(mainPanel,
				"Do you want to save changes to " + windowProperties.getTitle(), "Select an Option",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
		return (option == 0 ? true : false);
	}

	/**
	 * File services for the GUI manager.
	 * 
	 * @author kentp
	 * @version 1.1
	 */
	private class FileServices
	{
		private final File DEFAULT_DIRECTORY = new File("./res");
		private final FileNameExtensionFilter TXT_FILTER = new FileNameExtensionFilter("Text Documents (*.txt)", "txt");
		private final FileNameExtensionFilter ENC_FILTER = new FileNameExtensionFilter("Encrypted Documents (*.enc)",
				"enc");
		private JFileChooser fileChooser;
		private File file;

		public FileServices()
		{
			fileChooser = new JFileChooser();
			fileChooser.setCurrentDirectory(DEFAULT_DIRECTORY);
			fileChooser.addChoosableFileFilter(TXT_FILTER);
			fileChooser.addChoosableFileFilter(ENC_FILTER);
		}

		public boolean newFile()
		{
			if (windowProperties.isTextChanged() && confirmSaveMessage())
			{
				if (fileServices.saveAsFile())
				{
					windowProperties.reset();
					file = null;
				} else
				{
					return false;
				}
			} else
			{
				windowProperties.reset();
				file = null;
			}

			return true;
		}

		public boolean openFile()
		{
			boolean result = false;

			if (windowProperties.isTextChanged() && confirmSaveMessage())
			{
				if (file == null)
				{
					if (!fileServices.saveAsFile())
						return false;
				} else
				{
					fileServices.saveFile();
				}
			}

			fileChooser.setDialogTitle("Open");
			int selection = fileChooser.showOpenDialog(mainPanel);

			if (selection == JFileChooser.APPROVE_OPTION)
			{
				file = fileChooser.getSelectedFile();

				if (file.getName().contains(".enc"))
				{
					result = openEncrypted();
				} else
				{
					result = open();
				}
			}

			if (result)
			{
				windowProperties.setTitle(fileChooser.getSelectedFile().getName());
			}

			return result;
		}

		private boolean open()
		{
			try (Scanner fileReader = new Scanner(file))
			{
				String fileData = "";
				while (fileReader.hasNext())
				{
					fileData += fileReader.nextLine() + "\n";
				}
				mainTextArea.setText(fileData);
				return true;
			} catch (FileNotFoundException e)
			{
				displayGenericError();
			}
			return false;
		}

		private boolean openEncrypted()
		{
			try (Scanner fileReader = new Scanner(file))
			{
				String key = JOptionPane.showInputDialog("Enter the secret key: ");

				if (key != null && !key.equals(""))
				{
					String plainText = "";
					while (fileReader.hasNext())
					{
						plainText += EncryptUtility.decryptMessage(Integer.parseInt(key), fileReader.nextInt(),
								fileReader.nextLine());
					}

					mainTextArea.setText(plainText);
					return true;
				}

			} catch (NumberFormatException e)
			{
				displayGenericError();
			} catch (FileNotFoundException e)
			{
				displayGenericError();
			}

			return false;
		}

		public boolean saveFile()
		{
			if (file == null)
			{
				return saveAsFile();
			}

			if (windowProperties.isEncrypted())
			{
				if (!file.getName().contains(".enc"))
				{
					file = new File(fileChooser.getSelectedFile() + ".enc");
				}
				return persistEncrypted();
			} else
			{
				return persist();
			}
		}

		public boolean saveAsFile()
		{
			boolean result = false;

			fileChooser.setDialogTitle("Save As");
			int selection = fileChooser.showSaveDialog(mainPanel);

			if (selection == JFileChooser.APPROVE_OPTION)
			{

				if (windowProperties.isEncrypted())
				{
					file = new File(fileChooser.getSelectedFile() + ".enc");
					result = persistEncrypted();
				} else
				{
					file = new File(fileChooser.getSelectedFile() + ".txt");
					result = persist();
				}
			}

			if (result)
			{
				windowProperties.setTitle(fileChooser.getSelectedFile().getName());
			}

			return result;
		}

		private boolean persist()
		{
			try (PrintWriter pWriter = new PrintWriter(file))
			{
				pWriter.print(mainTextArea.getText());
				return true;

			} catch (FileNotFoundException e)
			{
				displayGenericError();
			}
			return false;
		}

		private boolean persistEncrypted()
		{

			try (PrintWriter pWriter = new PrintWriter(file))
			{
				String key = JOptionPane.showInputDialog("Enter the secret key: ");
				if (key != null && !key.equals(""))
				{
					String cipherText = EncryptUtility.encryptMessage(Integer.parseInt(key), mainTextArea.getText());
					pWriter.print(cipherText);
					return true;
				} else
				{
					pWriter.close();
					file.delete();
				}

			} catch (FileNotFoundException e)
			{
				displayGenericError();
			} catch (NumberFormatException e)
			{
				displayGenericError();
			}
			return false;
		}
	}
}