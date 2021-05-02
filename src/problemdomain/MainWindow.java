package problemdomain;

import javax.swing.JFrame;

public class MainWindow extends JFrame
{
	private static final long serialVersionUID = 1L;
	private static final int WINDOW_WIDTH = 800;
	private static final int WINDOW_HEIGHT = 600;
	
	public MainWindow()
	{
		init();
	}
	
	private void init()
	{
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}
	
	public void createWindow(WindowProperties windowProperties)
	{
		setTitle(windowProperties.getWindowTitle());
		setVisible(true);
	}
}
