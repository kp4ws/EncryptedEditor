package gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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

	// constants
	private final Font MAIN_FONT;

	// general use
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

	private boolean textChanged;

	private ArrayList<String> errList;

	private Window window;
	private FileServices fileServices;

	public static GUIManager getInstance()
	{
		return new GUIManager();
	}

	private GUIManager()
	{
		errList = new ArrayList<String>();
		window = Window.getInstance(true);
		fileServices = new FileServices();
		MAIN_FONT = new Font("serif", Font.PLAIN, 20);
		textChanged = false;

		buildMenuBar();
		buildPanel();

		// TODO Apply general UI style

		window.add(mainPanel);

		WindowUtility.createWindow(window);
	}

	private void buildPanel()
	{
		mainPanel = new JPanel(new BorderLayout(1, 0));
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
				textChanged = true;
			}

			@Override
			public void insertUpdate(DocumentEvent e)
			{
				textChanged = true;
			}

			@Override
			public void changedUpdate(DocumentEvent e)
			{
				// fileChanged = true;
			}
		});

		scrollPane = new JScrollPane(mainTextArea);
		mainPanel.add(scrollPane);
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
				fileServices.newFile();
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
				fileServices.openFile();
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
				fileServices.saveFile();
			}
		});

		saveAsItem = new JMenuItem("Save As");
		saveAsItem.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				fileServices.saveAsFile();
			}
		});

		exitItem = new JMenuItem("Exit");
		exitItem.setMnemonic(KeyEvent.VK_X);
		exitItem.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				int option = JOptionPane.showConfirmDialog(mainPanel, "Are you sure you want to exit?");
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

		encryptedMode = new JCheckBoxMenuItem("Encrypted Mode");
		encryptedMode.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
		encryptedMode.setSelected(true);

		headerLabel = new JLabel("Encrypted Mode: ON", SwingConstants.CENTER);
		headerLabel.setFont(MAIN_FONT);
		encryptedMode.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				window = Window.getInstance(!window.isEncryptedMode());

				if (window.isEncryptedMode())
				{
					headerLabel.setText("Encrypted Mode: ON");

				} else
				{
					headerLabel.setText("Encrypted Mode: OFF");
				}
			}
		});

		editMenu = new JMenu("Edit");
		editMenu.setFont(MAIN_FONT);

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
		formatMenu.setFont(MAIN_FONT);
	}

	private void buildViewMenu()
	{
		viewMenu = new JMenu("View");
		viewMenu.setFont(MAIN_FONT);
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
				JOptionPane.showMessageDialog(mainPanel, "Encrypted Editor is currently a work in progress."
						+ "\nIdeally I plan to create an interface similar to notepad and has the ability to encrypt messages that the user enters");
			}
		});

		helpMenu = new JMenu("Help");
		helpMenu.setFont(MAIN_FONT);
		helpMenu.add(aboutItem);
	}

	private class FileServices
	{

		private final File DEFAULT_DIRECTORY;
		private final FileNameExtensionFilter FILTER;
		private JFileChooser fileChooser;
		private File file;

		public FileServices()
		{
			DEFAULT_DIRECTORY = new File("./res");
			FILTER = new FileNameExtensionFilter("Text Documents (*.txt)", "txt");

			fileChooser = new JFileChooser();
			fileChooser.setCurrentDirectory(DEFAULT_DIRECTORY);
			fileChooser.addChoosableFileFilter(FILTER);
			fileChooser.setFileFilter(FILTER);
		}

		public void newFile()
		{
			if (textChanged)
			{

			}

			mainTextArea.setText("");
		}

		public void openFile()
		{
			fileChooser.setDialogTitle("Open");
			int selection = fileChooser.showOpenDialog(mainPanel);

			if (selection == JFileChooser.APPROVE_OPTION)
			{
				file = fileChooser.getSelectedFile();
				try (Scanner fileReader = new Scanner(file))
				{
					String fileData = "";
					while(fileReader.hasNext())
					{
						fileData += fileReader.nextLine() + "\n";
					}
					
					mainTextArea.setText(fileData);
					
				} catch (FileNotFoundException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}

		public void saveFile()
		{
			if (file == null)
			{
				saveAsFile();
				return;
			}

			persist();
		}

		public void saveAsFile()
		{
			fileChooser.setDialogTitle("Save As");
			int selection = fileChooser.showSaveDialog(mainPanel);

			if (selection == JFileChooser.APPROVE_OPTION)
			{
				file = new File(fileChooser.getSelectedFile() + "." + FILTER.getExtensions()[0]); // TODO dynamically
																									// get extension
				persist();
			}
		}

		private void updateHeader()
		{

		}

		private void persist()
		{
			try (PrintWriter pWriter = new PrintWriter(file))
			{
				pWriter.println(mainTextArea.getText());

			} catch (IOException e)
			{
				JOptionPane.showMessageDialog(mainPanel, "Error occurred, please check logs.", "ERROR",
						JOptionPane.ERROR_MESSAGE);
				errList.add(e.toString());
			}
		}
	}
}
