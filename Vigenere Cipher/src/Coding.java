import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Coding {
	Cipher cipher = new Cipher();
	char[][] theCipher;

	BufferedReader br;
	String message;
	String codedMessage;
	char[] messageArray;
	String keyword;
	char[] keywordArray;
	int keywordLength;
	int messageLength;
	char[] keywordRedone;
	char[] encodedMessage;
	char[] decodedMessage;

	Scanner sc = new Scanner(System.in);
	private int input;

	private void encode() throws IOException {
		br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("\nEnter the message you'd like to encode. Symbols and numbers will be ignored.\n");
		message = br.readLine().toLowerCase();
		messageLength = message.length();
		messageArray = new char[messageLength];
		message.getChars(0, messageLength, messageArray, 0);

		br = new BufferedReader(new InputStreamReader(System.in));
		do {
			System.out.println("\nEnter the keyword you'd like to use to encode your message.\n");
			keyword = br.readLine().toLowerCase();
			keywordLength = keyword.length();
			keywordArray = new char[keywordLength];
			keyword.getChars(0, keywordLength, keywordArray, 0);
			if (keywordLength > messageLength || keywordLength == 0) {
				System.out.println(
						"Please make sure the keyword is one word and that its length is less than the message length.");
			}
		} while (keywordLength > messageLength || keywordLength == 0 || keyword.contains(" "));

		// If the keyword's length is shorter than that of the message, loop the
		// keyword back until the two are of equal length
		if (keywordLength < messageLength) {
			keywordRedone = new char[messageLength];
			int j = 0;
			for (int i = 0; i < messageLength; i++) {
				if (j >= keywordLength) {
					j = 0;
				}
				keywordRedone[i] = keywordArray[j];
				j++;
			}
			this.keywordArray = keywordRedone;
		}

		theCipher = cipher.createCipher();
		encodedMessage = new char[messageLength];
		// encode the message
		int rowIndex = 0;
		int columnIndex = 0;
		/*
		 * decrement handles the possibility of trying to encode a message that contains
		 * spaces if there are spaces, the row and column indices will return 0,
		 * therefore the next value in the encoded message will be a space when this
		 * happens, the decrement increases by 1, which lets the keyword array to go
		 * back one spot so that the letter that was skipped because of the resulting
		 * space is still used.
		 * 
		 * For example: Let's say you want to encode the phrase "hi sir" using the
		 * keyword "man". Normally, the way this would be encoded without the decrement
		 * would go as follows: "h" would be encoded with "m", "i" would be encoded with
		 * "a", and the "space" together with the "n" would be skipped because of how
		 * the loop is set up. With the decrement, it lets the loop know that if a space
		 * was hit in the last iteration, it should go back and still use the letter
		 * from the keyword that was skipped. Therefore "s" would still be encoded with
		 * the initially skipped keyword character "n".
		 */

		int decrement = 0;

		for (int i = 0; i < messageLength; i++) {
			// look for the correct indices
			rowIndex = 0;
			columnIndex = 0;

			for (int j = 0; j < 27; j++) {
				if (theCipher[j][0] == messageArray[i]) {
					// save the index
					if (messageArray[i] == ' ') {
						rowIndex = 0;
						break;
					}
					rowIndex = j;
					break;
				}
			}
			for (int k = 0; k < 27; k++) {
				if (theCipher[0][k] == keywordArray[i - decrement]) {
					// save the index
					if (rowIndex == 0) {
						columnIndex = 0;
						decrement++;
						break;
					}
					columnIndex = k;
					break;
				}
			}

			encodedMessage[i] = theCipher[rowIndex][columnIndex];
		}
		System.out.println("\nHere is your encoded message: ");
		System.out.println(encodedMessage);
		System.out.println("");
	}

	private void decode() throws IOException {
		br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("\nEnter the coded message you'd like to decode. Symbols and numbers will be ignored.\n");
		codedMessage = br.readLine().toLowerCase();
		messageLength = codedMessage.length();
		messageArray = new char[messageLength];
		codedMessage.getChars(0, messageLength, messageArray, 0);

		br = new BufferedReader(new InputStreamReader(System.in));
		do {
			System.out.println("\nEnter the keyword used to encode the codedMessage.\n");
			keyword = br.readLine().toLowerCase();
			keywordLength = keyword.length();
			keywordArray = new char[keywordLength];
			keyword.getChars(0, keywordLength, keywordArray, 0);
			if (keywordLength > messageLength || keywordLength == 0) {
				System.out.println(
						"Please make sure the keyword is one word and that its length is less than the codedMessage length.");
			}
		} while (keywordLength > messageLength || keywordLength == 0 || keyword.contains(" "));

		if (keywordLength < messageLength) {
			keywordRedone = new char[messageLength];
			int j = 0;
			for (int i = 0; i < messageLength; i++) {
				if (j >= keywordLength) {
					j = 0;
				}
				keywordRedone[i] = keywordArray[j];
				j++;
			}
			this.keywordArray = keywordRedone;
		}

		theCipher = cipher.createCipher();
		decodedMessage = new char[messageLength];
		// decode the codedMessage
		int rowIndex = 0;
		int columnIndex = 0;

		int decrement = 0;

		for (int i = 0; i < messageLength; i++) {
			// look for the correct indices
			rowIndex = 0;
			columnIndex = 0;

			for (int k = 0; k < 27; k++) {
				if (theCipher[0][k] == keywordArray[i - decrement]) {
					// save the index
					columnIndex = k;
					break;
				}
			}

			for (int j = 0; j < 27; j++) {
				if (messageArray[i] == ' ') {
					rowIndex = 0;
					decrement++;
					break;
				}
				if (theCipher[j][columnIndex] == messageArray[i]) {
					// save the index
					rowIndex = j;
					break;
				}

			}

			decodedMessage[i] = theCipher[rowIndex][0];
		}
		System.out.println("\nHere is your decoded message:");
		System.out.println(decodedMessage);
		System.out.println("");
	}

	public void run() throws IOException {
		System.out.println("Welcome to the Vigenere Cipher program!");
		do {
			System.out.println(
					"Enter the number of the action you would like to do.\n1) Encode\n2) Decode\n3) Both\n4) Exit");
			input = sc.nextInt();
			switch (input) {
			case 1:
				encode();
				break;
			case 2:
				decode();
				break;
			case 3:
				encode();
				decode();
				break;
			case 4:
				System.exit(0);
			default:
				System.out.println("Invalid Input!");
			}

		} while (true);

	}
}
