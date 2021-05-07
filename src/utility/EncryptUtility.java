package utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

/**
 * Utility class using a custom built algorithm to encrypt and decrypt messages.
 * 
 * @author kentp
 * @version 1.1
 */
public final class EncryptUtility {

	private static final String VALID_CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
	private static final char NEW_LINE = '%';
	private static final char TAB = '$';

	public static String encryptMessage(int key, String message) {

		// Convert \n to %
		String plainText = message.replaceAll("\n", Character.toString(NEW_LINE));
		// Convert \t to $
		plainText = message.replaceAll("\t", Character.toString(TAB));

		String cipherText = "";
		Random rand = new Random();

		int amount = 0;
		while (amount < plainText.length()) {
			int randNum = rand.nextInt(VALID_CHARACTERS.length());
			String randomString = "";

			for (int i = 0; i < VALID_CHARACTERS.length(); i++) {

				if (randNum % key == 0 && i == randNum) {
					randomString += plainText.charAt(amount++);
				} else {
					randomString += VALID_CHARACTERS.charAt(rand.nextInt(VALID_CHARACTERS.length()));
				}
			}

			cipherText += String.format("%d\t%s%n", randNum, randomString);
		}

		return cipherText;
	}

	public static String decryptMessage(int key, String cipherText) {

		String plainText = "";
		Scanner textReader = new Scanner(cipherText);

		while (textReader.hasNext()) {
			int randNum = textReader.nextInt();
			String randomString = textReader.nextLine();
			
			if (randNum % key == 0) {

				if (randomString.charAt(randNum + 1) == NEW_LINE) {
					plainText += "\n";
				} else if (randomString.charAt(randNum + 1) == TAB) {
					plainText += "\t";
				} else {
					plainText += randomString.charAt(randNum);
				}
			}
		}
		textReader.close();
		return plainText;

	}
}
