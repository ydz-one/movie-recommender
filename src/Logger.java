import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Logger implements AutoCloseable{
	private static Logger instance;
	private static BufferedWriter bw;

	private Logger(String filename) throws IOException {
		instance = this;
		bw = new BufferedWriter(new FileWriter(filename));
	}

	public static Logger getInstance(String filename) throws IOException {
		if (instance == null) {
			instance = new Logger(filename);
		}
		return instance;
	}

	public void writeToFile(String str) throws IOException {
		bw.append(str);
		bw.newLine();
	}

	@Override
	public void close() throws IOException {
		bw.close();
	}
}
