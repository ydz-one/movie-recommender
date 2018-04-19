import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * This class logs the user input data and writes it out to an external file.
 *
 */
public class Logger implements AutoCloseable{
	private static Logger instance;
	private static BufferedWriter bw;

	/**
	 * Constructor method
	 * @param filename
	 * @throws IOException
	 */
	private Logger(String filename) throws IOException {
		instance = this;
		bw = new BufferedWriter(new FileWriter(filename));
	}

	/**
	 * Getter method for instance
	 * @param filename
	 * @return instance
	 * @throws IOException
	 */
	public static Logger getInstance(String filename) throws IOException {
		if (instance == null) {
			instance = new Logger(filename);
		}
		return instance;
	}

	/**
	 * This method is for writing to the file
	 * @param str
	 * @throws IOException
	 */
	public void writeToFile(String str) throws IOException {
		bw.append(str);
		bw.newLine();
	}

	/**
	 * This method is for closing the BufferedWriter object.
	 */
	@Override
	public void close() throws IOException {
		bw.close();
	}
}
