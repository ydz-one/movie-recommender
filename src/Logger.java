import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * This class logs the user input data and writes it out to an external file.
 *
 */
public class Logger {
	private static Logger instance;
	private static BufferedWriter bw;

	/**
	 * Constructor method
	 * @param filename
	 */
	private Logger() {
		instance = this;
		bw = null;
	}

	/**
	 * Getter method for instance
	 * @param filename
	 * @return instance
	 */
	public static Logger getInstance() {
		if (instance == null) {
			instance = new Logger();
		}
		return instance;
	}
	
	/**
	 * Sets the output file for BufferedWriter
	 * @param filename
	 * @throws IOException
	 */
	public void setOutputFile(String filename) throws IOException {
		bw = new BufferedWriter(new FileWriter(new File(filename), true));
	}

	/**
	 * This method is for writing to the file
	 * @param str
	 * @throws IOException 
	 */
	public void writeToFile(String str) throws IOException {
		if (bw == null) {
			throw new IllegalStateException("ERROR: Output file undefined!");
		}
		bw.append(str);
		bw.newLine();
	}
}
