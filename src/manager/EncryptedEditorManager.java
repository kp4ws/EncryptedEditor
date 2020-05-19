package manager;

import java.util.*;
import java.io.*;

public class EncryptedEditorManager
{
	private boolean encrypt;
	private String fileName;
	private String message;
	
	public EncryptedEditorManager()
	{
		encrypt = true;
		fileName = "Untitled";
		message = "";
	}

	public void encryptFile()
	{
		
	}

	public void decryptFile()
	{

	}
	
	public void saveFile()
	{
		FileWriter fWriter;
		try
		{
			fWriter = new FileWriter(fileName);
			PrintWriter pWriter = new PrintWriter(fWriter);
			
			pWriter.println(message);
			pWriter.close();

		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String readFile()
	{
		File file = new File(fileName);
		try
		{
			Scanner fileReader = new Scanner(file);
			String fileData = "";
			
			while(fileReader.hasNext())
			{
				fileData += fileReader.nextLine();
				fileData += "\n";
			}
			fileReader.close();
			
			
			return fileData;
		} catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "Error Occured";
	}
	
	public boolean isEncrypted()
	{
		return encrypt;
	}
	
	public void setEncrypted(boolean encryption)
	{
		encrypt = encryption;
	}
	
	public String getFileName()
	{
		return fileName;
	}
	
	public void setFileName(String newFileName)
	{
		fileName = newFileName;
	}
	
	public String getMessage()
	{
		return message;
	}
	
	public void setMessage(String newMessage)
	{
		message = newMessage;
	}
}