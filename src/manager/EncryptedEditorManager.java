package manager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

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

	public void writeEncryptedFile(int key)
	{
		PrintWriter pWriter;
		try
		{
			pWriter = new PrintWriter(filePath);
			final String ALLOWED_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
			Random rand = new Random();
			
			int randCharIndex = 0, randNum, amountOfChar = 0;
			String randomString;
			boolean messageEncoded = false;
			do
			{
				// random number on left side of decoded message
				randNum = rand.nextInt(ALLOWED_CHARS.length());
				// reset random string line
				randomString = "";

				// generate random string line
				while (randomString.length() < ALLOWED_CHARS.length())
				{
					randCharIndex = rand.nextInt(ALLOWED_CHARS.length());
					randomString += ALLOWED_CHARS.charAt(randCharIndex);
				}

				StringBuilder encodedString = new StringBuilder(randomString);

				// encode character in the line
				if (randNum % key == 0)
				{
					encodedString.setCharAt(randNum, message.charAt(amountOfChar));
					amountOfChar++;
				}

				// write data to file
				pWriter.printf("%d\t%s", randNum, encodedString);
				pWriter.println();

				// checks if the entire message has been encoded
				if (amountOfChar == message.length())
				{
					messageEncoded = true;
				}

				// keep looping while messageEncoded = false
			} while (!messageEncoded);

			pWriter.close();
			System.out.println("Generated message.");
		} catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String readEncryptedFile(int key)
	{

		File file = new File(filePath);
		// Creates a Scanner object that reads from the file object you created
		Scanner fileReader;
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
					message += str.charAt(num + 1);
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

	public void writeFile()
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

	public String readFile()
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

	public boolean fileExists(String fileName)
	{
		File file = new File(fileName);
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

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public String getFileName()
	{
		return new File(filePath).getName();
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