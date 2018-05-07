import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class DataManagerTest {
	DataManager dm;
	String moviesFile = "movies-test.dat";
	String ratingsFile = "ratings-test.dat";

	@Before
	public void setUp() throws Exception {
		dm = new DataManager(moviesFile, ratingsFile);
	}

	@Test
	public void testGetUsers() {
		assertEquals(5, dm.getUsers().size());
	}
	
	@Test
	public void testGetMovies() {
		assertEquals(4, dm.getMovies().size());
	}
	
	

}
