package manager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class EncryptedEditorManager
{
	private boolean encrypted;
	private String message;
	private String filePath;
	
	public EncryptedEditorManager()
	{
		encrypted = true;
		message = "";
		filePath = "";
	}

	private void encryptFile()
	{
		
	}

	private String decryptFile()
	{
		return new String();
	}
	
	private void saveFile()
	{
		try
		{
			PrintWriter pWriter = new PrintWriter(filePath);

			pWriter.print(message);
			pWriter.close();
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}
	
	private String readFile()
	{	
		File file = new File(filePath);
		Scanner fileReader;
		String fileData = "";
		System.out.println("reading data from " + filePath);
		try
		{
			fileReader = new Scanner(file);
			
			while(fileReader.hasNext())
			{
				fileData += fileReader.nextLine();
				fileData += "\n";
			}
			fileReader.close();
		} catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return fileData;
	}
	
	public void persist()
	{
		if (encrypted)
		{
			encryptFile();
		}
		else
		{
			saveFile();
		}
	}
	
	public String getFileData()
	{
		String fileData;
		if (encrypted)
		{
			fileData = decryptFile();
		}
		else
		{
			fileData = readFile();
		}
		
		return fileData;
	}
	
	public boolean fileExists(String fileName)
	{
		File file = new File(fileName);
		return file.exists() ? true : false;
	}
	
	public String getFileName()
	{
		return new File(filePath).getName();
	}
	
	public boolean isEncrypted()
	{
		return encrypted;
	}

	public void setEncrypted(boolean encrypted)
	{
		this.encrypted = encrypted;
	}
	
	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public String getFilePath()
	{
		return filePath;
	}
	
	public void setFilePath(String filePath)
	{
		this.filePath = filePath;
	}
	
}