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
		// https://stackoverflow.com/questions/13878437/how-to-fill-an-array-of-characters-from-user-input
		br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Enter the message you'd like to encode.");
		message = br.readLine();
		messageLength = message.length();
		messageArray = new char[messageLength];
		message.getChars(0, messageLength, messageArray, 0);

		br = new BufferedReader(new InputStreamReader(System.in));
		do {
			System.out.println("Enter the keyword you'd like to use to encode your message.");
			keyword = br.readLine();
			keywordLength = keyword.length();
			keywordArray = new char[keywordLength];
			keyword.getChars(0, keywordLength, keywordArray, 0);
			if (keywordLength > messageLength || keywordLength == 0) {
				System.out.println("Please make sure the keyword length is less than the message length.");
			}
		} while (keywordLength > messageLength || keywordLength == 0);

		// If the keyword's length is shorter than that of the message, then loop the
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
		for (int i = 0; i < messageLength; i++) {
			// look for the correct indices
			rowIndex = 0;
			columnIndex = 0;

			for (int j = 0; j < 27; j++) {
				if (theCipher[j][0] == messageArray[i]) {
					// save the index
					rowIndex = j;
					break;
				}
			}
			for (int k = 0; k < 27; k++) {
				if (theCipher[0][k] == keywordArray[i]) {
					// save the index
					columnIndex = k;
					break;
				}
			}

			encodedMessage[i] = theCipher[rowIndex][columnIndex];
		}
		System.out.println(encodedMessage);
	}

	public void run() throws IOException {
		encode();
	}
}
