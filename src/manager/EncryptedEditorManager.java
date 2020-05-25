package manager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

public class EncryptedEditorManager
{
	private boolean encrypted;

	public EncryptedEditorManager()
	{
		//by default, encrypted mode is turned on
		encrypted = true;
	}

	public void writeEncryptedFile(int key, String message, String filePath)
	{
		String updatedMessage = message.replaceAll("\n", "%");
		PrintWriter pWriter;
		try
		{
			final String ALLOWED_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
			pWriter = new PrintWriter(filePath);
			Random rand = new Random();
	
			int randCharIndex = 0, randNum = 0, amountOfChar = 0, position = 0;
			String randomString = "";

			do
			{ // random number on left side of decoded message
				randNum = rand.nextInt(ALLOWED_CHARS.length());

				// reset random string line
				randomString = "";

				// Generate Random Line
				for (int i = 0; i < ALLOWED_CHARS.length(); i++)
				{
					if (randNum % key == 0 && i == randNum)
					{
						randomString += updatedMessage.charAt(amountOfChar);
						amountOfChar++;
					} else
					{
						randCharIndex = rand.nextInt(ALLOWED_CHARS.length());
						randomString += ALLOWED_CHARS.charAt(randCharIndex);
					}
				}

				// write data to file
				pWriter.printf("%d\t%s%n", randNum, randomString);

				// keep looping while the entire method isn't encoded
			} while (amountOfChar < updatedMessage.length());

			pWriter.close();
		} catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String readEncryptedFile(int key, String filePath)
	{
		File file = new File(filePath);
		// Creates a Scanner object that reads from the file object you created
		Scanner fileReader;
		String message = "";
		try
		{
			fileReader = new Scanner(file);
			// Read from the file:
			// This will keep looping while there is data in the file. If it reaches the end
			// of the file, it will exit out of the loop
		
			while (fileReader.hasNext())
			{
				// This is the number that is on the left side of the decoded message
				int num = fileReader.nextInt();
				// This creates a string of the line you are currently on
				String str = fileReader.nextLine();
				if (num % key == 0)
				{
					// This equals the character at the "num" value. It needs to be + 1 because it
					// takes into account the tab spacing in the message
					// if there is a newline character then message += "\n"
					if (str.charAt(num + 1) == '%')
					{
						message += "\n";
					}
					else
					{
						message += str.charAt(num + 1);						
					}
					
				}
			}
			fileReader.close();
		} catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return message;
	}

	public void writeFile(String message, String filePath)
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

	public String readFile(String filePath)
	{
		File file = new File(filePath);
		Scanner fileReader;
		String fileData = "";
		System.out.println("reading data from " + filePath);
		try
		{
			fileReader = new Scanner(file);

			while (fileReader.hasNext())
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

	public boolean fileExists(String filePath)
	{
		File file = new File(filePath);
		return file.exists() ? true : false;
	}

	public boolean isEncrypted()
	{
		return encrypted;
	}

	public void setEncrypted(boolean encrypted)
	{
		this.encrypted = encrypted;
	}

	public String getFileName(String filePath)
	{
		return new File(filePath).getName();
	}
}