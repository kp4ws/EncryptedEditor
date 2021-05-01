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
	private final Font MAIN_FONT = new Font("serif", Font.PLAIN, 20);
	private final Font SUB_FONT = new Font("serif", Font.PLAIN, 18);

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
	private Window window;
	private GUIServices guiServices;

	public static GUIManager getInstance()
	{
		return new GUIManager();
	}

	private GUIManager()
	{
		init();
		buildMenuBar();
		buildPanel();

		window.add(mainPanel);
		WindowUtility.createWindow(window);
	}

	private void init()
	{
		window = Window.getInstance(true, "Untitled");
		guiServices = new GUIServices();
		textChanged = false;
		
		headerLabel = new JLabel(window.getHeader(), SwingConstants.CENTER);
		headerLabel.setFont(MAIN_FONT);
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
		newItem.setFont(SUB_FONT);
		// Ctrl + N
		newItem.setAccelerator(KeyStroke.getKeyStroke('N', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		newItem.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (textChanged)
				{
					int option = JOptionPane.showConfirmDialog(mainPanel, "Do you want to save changes to Untitled?",
							"Select an Option", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE); // TODO

					if (option == 0)
					{
						if(guiServices.saveAsFile(mainPanel, mainTextArea))
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
		openItem.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				guiServices.openFile(mainPanel, mainTextArea);
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
				guiServices.saveFile(mainPanel, mainTextArea);
			}
		});

		saveAsItem = new JMenuItem("Save As");
		saveAsItem.setFont(SUB_FONT);
		saveAsItem.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				guiServices.saveAsFile(mainPanel, mainTextArea);
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
		undoItem.setFont(SUB_FONT);

		redoItem = new JMenuItem("Redo");
		redoItem.setAccelerator(KeyStroke.getKeyStroke('Y', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		redoItem.setFont(SUB_FONT);

		cutItem = new JMenuItem("Cut");
		cutItem.setAccelerator(KeyStroke.getKeyStroke('X', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		cutItem.setFont(SUB_FONT);

		copyItem = new JMenuItem("Copy");
		copyItem.setAccelerator(KeyStroke.getKeyStroke('C', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		copyItem.setFont(SUB_FONT);

		pasteItem = new JMenuItem("Paste");
		pasteItem.setAccelerator(KeyStroke.getKeyStroke('V', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		pasteItem.setFont(SUB_FONT);

		deleteItem = new JMenuItem("Delete");
		deleteItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0));
		deleteItem.setFont(SUB_FONT);

		encryptedMode = new JCheckBoxMenuItem("Encrypted Mode");
		encryptedMode.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
		encryptedMode.setSelected(true);
		encryptedMode.setFont(SUB_FONT);
		encryptedMode.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				window = Window.getInstance(!window.isEncrypted(), window.getTitle());
				headerLabel.setText(window.getHeader());
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
				JOptionPane.showMessageDialog(mainPanel,
						"Encrypted Editor is a simple text editor that allows the user to encrypt messages.");
			}
		});

		helpMenu = new JMenu("Help");
		helpMenu.setFont(MAIN_FONT);
		helpMenu.add(aboutItem);
	}
	
	private class GUIServices
	{
		private final File DEFAULT_DIRECTORY = new File("./res");
		private final FileNameExtensionFilter FILTER = new FileNameExtensionFilter("Text Documents (*.txt)", "txt");
		private JFileChooser fileChooser;
		private File file;

		public GUIServices()
		{
			fileChooser = new JFileChooser();
			fileChooser.setCurrentDirectory(DEFAULT_DIRECTORY);
			fileChooser.addChoosableFileFilter(FILTER);
			fileChooser.setFileFilter(FILTER);
		}

		public boolean newFile()
		{
			mainTextArea.setText("");
			window = Window.getInstance(true, "Untitled");
			return true;
		}

		public boolean openFile(JPanel panel, JTextArea textArea)
		{
			fileChooser.setDialogTitle("Open");
			int selection = fileChooser.showOpenDialog(panel);

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
					
					textArea.setText(fileData);
					
				} catch (FileNotFoundException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			
			return true;
		}

		public boolean saveFile(JPanel panel, JTextArea textArea)
		{
			if (file == null)
			{
				return saveAsFile(panel, textArea);
			}
			
			persist(panel, textArea);
			return true;
		}

		public boolean saveAsFile(JPanel panel, JTextArea textArea)
		{
			fileChooser.setDialogTitle("Save As");
			int selection = fileChooser.showSaveDialog(panel);

			if (selection == JFileChooser.APPROVE_OPTION)
			{
				file = new File(fileChooser.getSelectedFile() + "." + FILTER.getExtensions()[0]); // TODO dynamically
				
				String fileName = fileChooser.getSelectedFile().getName();
				String title = fileName.substring(0, fileName.indexOf("."));
				
				window.setTitle(title);
				
				// get extension
				persist(panel, textArea);
			}
			
			return true;
		}

		private void persist(JPanel panel, JTextArea textArea)
		{
			try (PrintWriter pWriter = new PrintWriter(file))
			{
				pWriter.print(textArea.getText());

			} catch (IOException e)
			{
				JOptionPane.showMessageDialog(panel, "Error occurred, please check logs.", "ERROR",
						JOptionPane.ERROR_MESSAGE);
			}
		}
		
		private void persistEncrypted(JPanel panel, JTextArea textArea)
		{
			
		}
	}
}
