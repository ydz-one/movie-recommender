import java.io.IOException;

/**
 * This class contains the main.
 * It handles retrieving input from the user and
 * providing results to the user via the console.
 *
 */
public class UserInterface {

	public static void main(String[] args) {
		String outputFile = "output-file.txt";

		try {
			Logger logger = Logger.getInstance();
			logger.setOutputFile(outputFile);
			logger.writeToFile("Tears in rain.");
			logger.writeToFile("Origami unicorn.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
