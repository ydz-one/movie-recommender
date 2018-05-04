import java.util.Map;

/**
 * This class processes movie and user rating data to provide predictions and recommendations.
 *
 */
public class Recommender {
	DataManager dm;
	
	/**
	 * Constructor.
	 * @param movieDataFilename
	 * @param ratingDataFilename
	 */
	public Recommender(String movieDataFilename, String ratingDataFilename) {
		dm = new DataManager(movieDataFilename, ratingDataFilename);
	}
	
	/**
	 * Finds a specified number of neighbors for a user based on similarity.
	 * @param userID
	 * @param movieID
	 * @param neighborhoodSize
	 * @return Map of neighbors
	 */
	public Map<Integer, User> findNeighbors(int userID, int movieID, int neighborhoodSize) {
		// TODO
		return null;
	}
	
	/**
	 * Runs a specified algorithm on two users to compute their similarity coefficient.
	 * @param userID1
	 * @param userID2
	 * @param similarity algorithm
	 * @return similarity coefficient between user1 and user2
	 */
	public double calculateSimilarity(int userID1, int userID2, SimilarityAlgorithm algo) {
		User a = dm.getUsers().get(userID1);
		User b = dm.getUsers().get(userID2);
		return algo.execute(a, b);
	}
	
	/**
	 * Given a user and a movie that they haven't watched, this method predicts the user's
	 * preference for it.
	 * @param userID
	 * @param movieID
	 * @return preference rating
	 */
	public double predictPreference(int userID, int movieID) {
		// TODO
		return 0;
	}
	
	/**
	 * Given a user and a preference threshold, this method find all movies the user hasn't
	 * watched with a preference rating above that threshold.
	 * @param userID
	 * @param threshold
	 * @return Map of movies
	 */
	public Map<Integer, Movie> recommendMovies(int userID, double threshold) {
		// TODO
		return null;
	}
}
