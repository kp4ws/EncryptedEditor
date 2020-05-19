
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import javax.swing.*;

import manager.EncryptedEditorManager;

public class EncryptedEditorGUI extends JFrame
{
	private static final long serialVersionUID = 1L;
	private final int WINDOW_WIDTH = 480;
	private final int WINDOW_HEIGHT = 360;
	private JLabel label;
	private JTextField field;
	private JButton saveButton;
	private JPanel panel;
	private JTextArea textArea;
	private JScrollPane scroll;
	private EncryptedEditorManager manager;

	public EncryptedEditorGUI()
	{
		setTitle("Encrypted Text Editor");
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		buildPanel();
		add(panel);
		setVisible(true);
	}

	private void buildPanel()
	{
		label = new JLabel("Enter Text:", SwingConstants.CENTER);
		//field = new JTextField(15);
		saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				
			}

		});

		panel = new JPanel(new BorderLayout(5, 0));
		panel.add(label, BorderLayout.NORTH);
		// panel.add(field, BorderLayout.WEST);
		panel.add(saveButton, BorderLayout.SOUTH);
		textArea = new JTextArea(5, 20);
		scroll = new JScrollPane(textArea);
		panel.add(scroll);
	}
	
	public static void main (String [] args)
	{
		new EncryptedEditorGUI();
	}
}