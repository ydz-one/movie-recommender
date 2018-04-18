import java.io.IOException;

public class UserInterface {

	public static void main(String[] args) {
		String outputFile = "output-file.txt";

		try (Logger logger = Logger.getInstance(outputFile)) {
			logger.writeToFile("Tears in rain.");
			logger.writeToFile("Origami unicorn.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
