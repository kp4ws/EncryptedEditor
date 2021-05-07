
package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
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
import javax.swing.JTextField;
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
public final class GUIManager {

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
	private GUIServices guiServices;
	private static GUIManager managerInstance;

	public static GUIManager getInstance() {
		if (managerInstance == null) {
			managerInstance = new GUIManager();
		}

		return managerInstance;
	}

	private GUIManager() {

		WindowModel model = new WindowModel();
		windowProperties = model.getWindowProperties();
		mainWindow = new MainWindow(model, this);
		guiServices = new GUIServices();

		buildMenuBar();
		buildPanel();

		mainWindow.add(mainPanel);
		mainWindow.display();
	}

	public JLabel getHeaderLabel() {
		return headerLabel;
	}

	public JTextArea getTextArea() {
		return mainTextArea;
	}

	private void buildPanel() {
		mainPanel = new JPanel(new BorderLayout(1, 0));
		headerLabel = new JLabel(windowProperties.getHeader(), SwingConstants.CENTER);
		headerLabel.setFont(MAIN_FONT);
		mainPanel.add(headerLabel, BorderLayout.NORTH);

		// white space on west side of the window
		mainPanel.add(new JLabel(), BorderLayout.WEST);

		mainTextArea = new JTextArea();
		mainTextArea.setFont(MAIN_FONT);

		mainTextArea.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				windowProperties.setTextChanged(true);
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				windowProperties.setTextChanged(true);
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
			}
		});

		scrollPane = new JScrollPane(mainTextArea);
		mainPanel.add(scrollPane);
	}

	private void buildMenuBar() {
		buildFileMenu();
		buildEditMenu();
		buildHelpMenu();

		menuBar = new JMenuBar();
		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		menuBar.add(helpMenu);

		mainWindow.setJMenuBar(menuBar);
	}

	private void buildFileMenu() {
		newItem = new JMenuItem("New");
		newItem.setFont(SUB_FONT);
		// Ctrl + N
		newItem.setAccelerator(KeyStroke.getKeyStroke('N', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		newItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (windowProperties.isTextChanged()) {
					int option = JOptionPane.showConfirmDialog(mainPanel,
							"Do you want to save changes to " + windowProperties.getTitle(), "Select an Option",
							JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);

					if (option == 0) {
						//if (guiServices.saveAsFile()) 
						{
							guiServices.newFile();
						}
						return;
					}
				}

				guiServices.newFile();
			}

		});

		openItem = new JMenuItem("Open");
		openItem.setFont(SUB_FONT);
		// Ctrl + O
		openItem.setAccelerator(KeyStroke.getKeyStroke('O', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		openItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				guiServices.openFile();
			}
		});

		saveItem = new JMenuItem("Save");
		saveItem.setFont(SUB_FONT);
		saveItem.setMnemonic(KeyEvent.VK_S);
		// Ctrl + S
		saveItem.setAccelerator(KeyStroke.getKeyStroke('S', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		saveItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				guiServices.saveFile();
			}
		});

		saveAsItem = new JMenuItem("Save As");
		saveAsItem.setFont(SUB_FONT);
		saveAsItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				guiServices.saveAsFile();
			}
		});

		exitItem = new JMenuItem("Exit");
		exitItem.setFont(SUB_FONT);
		exitItem.setMnemonic(KeyEvent.VK_X);
		exitItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				int option = JOptionPane.showConfirmDialog(mainPanel, "Are you sure you want to exit?",
						"Select an Option", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
				if (option == 0) {
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

	private void buildEditMenu() {

		encryptedMode = new JCheckBoxMenuItem("Encrypted Mode");
		encryptedMode.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
		encryptedMode.setSelected(true);
		encryptedMode.setFont(SUB_FONT);
		encryptedMode.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				windowProperties.setEncryptedMode(!windowProperties.isEncrypted());
			}
		});

		editMenu = new JMenu("Edit");
		editMenu.setFont(MAIN_FONT);
		editMenu.add(encryptedMode);
	}

	private void buildHelpMenu() {
		aboutItem = new JMenuItem("About Encrypted Editor");
		aboutItem.setFont(MAIN_FONT);
		aboutItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(mainPanel,
						"Encrypted Editor is a simple text editor that allows the user to encrypt messages.");
			}
		});

		helpMenu = new JMenu("Help");
		helpMenu.setFont(MAIN_FONT);
		helpMenu.add(aboutItem);
	}

	private void displayGenericError() {
		JOptionPane.showMessageDialog(mainPanel, "Error occurred, please try again.", "ERROR",
				JOptionPane.ERROR_MESSAGE);
	}

	private class GUIServices {
		private final File DEFAULT_DIRECTORY = new File("./res");
		private final FileNameExtensionFilter TXT_FILTER = new FileNameExtensionFilter("Text Documents (*.txt)", "txt");
		private final FileNameExtensionFilter ENC_FILTER = new FileNameExtensionFilter("Encrypted Documents (*.enc)",
				"enc");
		private JFileChooser fileChooser;
		private File file;

		public GUIServices() {
			fileChooser = new JFileChooser();
			fileChooser.setCurrentDirectory(DEFAULT_DIRECTORY);
			fileChooser.addChoosableFileFilter(TXT_FILTER);
			fileChooser.addChoosableFileFilter(ENC_FILTER);
		}

		public void newFile() {
			windowProperties.reset();
		}

		public void openFile() {
			fileChooser.setDialogTitle("Open");
			int selection = fileChooser.showOpenDialog(mainPanel);

			if (selection == JFileChooser.APPROVE_OPTION) {
				file = fileChooser.getSelectedFile();

				try (Scanner fileReader = new Scanner(file)) {
					String fileData = "";
					while (fileReader.hasNext()) {
						fileData += fileReader.nextLine();
					}

					if (file.getName().contains(".enc")) {
						openEncrypted(fileData);
					} else {
						mainTextArea.setText(fileData);
					}
					windowProperties.setTitle(fileChooser.getSelectedFile().getName());

				} catch (FileNotFoundException e) {
					displayGenericError();
				}

			}
		}

		private void openEncrypted(String fileData) {

			try {
				String key = JOptionPane.showInputDialog("Enter the secret key: ");

				if (key != null && !key.equals("")) {
					String plainText = "";
					plainText = EncryptUtility.decryptMessage(Integer.parseInt(key), fileData);
					mainTextArea.setText(plainText);
				}

			} catch (NumberFormatException e) {
				displayGenericError();
			}

		}

		public void saveFile() {

			if (file == null) {
				saveAsFile();
			}

			persist();
		}

		public void saveAsFile() {
			fileChooser.setDialogTitle("Save As");
			int selection = fileChooser.showSaveDialog(mainPanel);

			if (selection == JFileChooser.APPROVE_OPTION) {
				persist();
				windowProperties.setTitle(fileChooser.getSelectedFile().getName());
			}
		}

		private void persist() {

			if (windowProperties.isEncrypted()) {
				file = new File(fileChooser.getSelectedFile() + ".enc");
				persistEncrypted();
				return;
			}

			file = new File(fileChooser.getSelectedFile() + ".txt");

			try (PrintWriter pWriter = new PrintWriter(file)) {

				pWriter.print(mainTextArea.getText());

			} catch (FileNotFoundException e) {
				displayGenericError();
			}
		}

		private void persistEncrypted() {

			try (PrintWriter pWriter = new PrintWriter(file)) {
				String key = JOptionPane.showInputDialog("Enter the secret key: ");
				if (key != null && !key.equals("")) {
					String cipherText = EncryptUtility.encryptMessage(Integer.parseInt(key), mainTextArea.getText());
					pWriter.print(cipherText);
				}

			} catch (FileNotFoundException e) {
				displayGenericError();
			} catch (NumberFormatException e) {
				displayGenericError();
			}
		}
	}
}