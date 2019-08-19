import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Coding {
	Cipher cipher = new Cipher();
	char[][] theCipher;

	BufferedReader br;
	String message;
	char[] messageArray;
	String keyword;
	char[] keywordArray;
	int keywordLength;
	int messageLength;
	char[] keywordRedone;
	char[] encodedMessage;

	private void encode() throws IOException {
		System.out.println("Welcome to the Vigenere Cipher program!");
		br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter the message you'd like to encode. Symbols and numbers will be ignored.");
		message = br.readLine().toLowerCase();
		messageLength = message.length();
		messageArray = new char[messageLength];
		message.getChars(0, messageLength, messageArray, 0);

		br = new BufferedReader(new InputStreamReader(System.in));
		do {
			System.out.println("Enter the keyword you'd like to use to encode your message.");
			keyword = br.readLine().toLowerCase();
			keywordLength = keyword.length();
			keywordArray = new char[keywordLength];
			keyword.getChars(0, keywordLength, keywordArray, 0);
			if (keywordLength > messageLength || keywordLength == 0) {
				System.out.println(
						"Please make sure the keyword is one word and that its length is less than the message length.");
			}
		} while (keywordLength > messageLength || keywordLength == 0 || keyword.contains(""));

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
					if (messageArray[i] == theCipher[0][0]) {
						rowIndex = 0;

					} else {
						rowIndex = j;
						break;
					}
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
		System.out.println("Here is your encoded message: ");
		System.out.println(encodedMessage);
	}

	public void run() throws IOException {
		encode();
	}
}
