package utility;

import java.security.SecureRandom;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.xml.bind.DatatypeConverter;

/**
 * 
 * @author kentp
 * @version 1.1
 */
public class EncryptUtility
{

	private static final String AES = "AES";

	// We are using a Block cipher(CBC mode)
	private static final String AES_CIPHER_ALGORITHM = "AES/CBC/PKCS5PADDING";

	private static Scanner message;

	// Function to create a
	// secret key
	public static SecretKey createAESKey() throws Exception
	{
		SecureRandom securerandom = new SecureRandom();
		KeyGenerator keygenerator = KeyGenerator.getInstance(AES);

		keygenerator.init(256, securerandom);
		SecretKey key = keygenerator.generateKey();

		return key;
	}

	// Function to initialize a vector
	// with an arbitrary value
	public static byte[] createInitializationVector()
	{

		// Used with encryption
		byte[] initializationVector = new byte[16];
		SecureRandom secureRandom = new SecureRandom();
		secureRandom.nextBytes(initializationVector);
		return initializationVector;
	}

	// This function takes plaintext,
	// the key with an initialization
	// vector to convert plainText
	// into CipherText.
	public static byte[] do_AESEncryption(String plainText, SecretKey secretKey, byte[] initializationVector)
			throws Exception
	{
		Cipher cipher = Cipher.getInstance(AES_CIPHER_ALGORITHM);

		IvParameterSpec ivParameterSpec = new IvParameterSpec(initializationVector);

		cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);

		return cipher.doFinal(plainText.getBytes());
	}

	// This function performs the
	// reverse operation of the
	// do_AESEncryption function.
	// It converts ciphertext to
	// the plaintext using the key.
	public static String do_AESDecryption(byte[] cipherText, SecretKey secretKey, byte[] initializationVector)
			throws Exception
	{
		Cipher cipher = Cipher.getInstance(AES_CIPHER_ALGORITHM);

		IvParameterSpec ivParameterSpec = new IvParameterSpec(initializationVector);

		cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);

		byte[] result = cipher.doFinal(cipherText);

		return new String(result);
	}
	
	// Driver code
	public static void main(String args[]) throws Exception
	{
		String plainText = "This is the message " + "I want To Encrypt.";

		// Encrypting the message
		// using the symmetric key
		byte[] cipherText = do_AESEncryption(plainText, Symmetrickey, initializationVector);

		System.out.println(
				"The ciphertext or " + "Encrypted Message is: " + DatatypeConverter.printHexBinary(cipherText));

		// Decrypting the encrypted
		// message
		String decryptedText = do_AESDecryption(cipherText, Symmetrickey, initializationVector);

		System.out.println("Your original message is: " + decryptedText);
	}
}