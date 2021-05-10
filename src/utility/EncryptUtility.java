package utility;

import java.util.Random;

/**
 * Utility class using a custom built algorithm to encrypt and decrypt messages.
 * 
 * @author kentp
 * @version 1.1
 */
public final class EncryptUtility
{
	private static final String VALID_CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
	private static final char NEW_LINE = '%';

	public static String encryptMessage(int key, String message)
	{
		// Convert \n to %
		String plainText = message.replaceAll("\n", Character.toString(NEW_LINE));

		String cipherText = "";
		Random rand = new Random();

		int amount = 0;
		while (amount < plainText.length())
		{
			int randNum = rand.nextInt(VALID_CHARACTERS.length());
			String randomString = "";

			for (int i = 0; i < VALID_CHARACTERS.length(); i++)
			{

				if (randNum % key == 0 && i == randNum)
				{
					randomString += plainText.charAt(amount++);
				} else
				{
					randomString += VALID_CHARACTERS.charAt(rand.nextInt(VALID_CHARACTERS.length()));
				}
			}

			cipherText += String.format("%d\t%s%n", randNum, randomString);
		}

		return cipherText;
	}

	public static String decryptMessage(int key, int randNum, String randomString)
	{
		String plainText = "";

		if (randNum % key == 0)
		{
			if (randomString.charAt(randNum + 1) == NEW_LINE)
			{
				plainText = "\n";
			} else
			{
				plainText += randomString.charAt(randNum + 1);
			}
		}

		return plainText;
	}
}
