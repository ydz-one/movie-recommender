import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Map.Entry;

/**
 * This class processes movie and user rating data to provide predictions and recommendations.
 *
 */
public class Recommender {
	DataManager dm;
	SimilarityAlgorithm algo;
	
	/**
	 * Constructor.
	 * @param movieDataFilename
	 * @param ratingDataFilename
	 */
	public Recommender(String movieDataFilename, String ratingDataFilename,
			SimilarityAlgorithm algo) {
		dm = new DataManager(movieDataFilename, ratingDataFilename);
		this.algo = algo;
	}
	
	/**
	 * Sets the similarity algorithm.
	 * @param algo
	 */
	public void setSimilarityAlgorithm(SimilarityAlgorithm algo) {
		this.algo = algo;
	}
	
	
	/**
	 * Finds a specified number of neighbors for a user based on similarity.
	 * @param userID
	 * @param movieID
	 * @param neighborhoodSize
	 * @return Map of neighbors
	 */
	public List<Integer> findNeighbors(int userID, int movieID, int neighborhoodSize) {
		if (neighborhoodSize <= 0) {
			throw new IllegalArgumentException("ERROR: neighborhoodSize cannot be non-positive");
		}

		// Map of neighbors. Key: UserID, value: similarity
		Map<Integer, Double> neighbors = new HashMap<>();

		for (Entry<Integer, User> entry : dm.getUsers().entrySet()) {
			// Check if this potential neighbor has rated the target movie
			if (entry.getValue().getRatings().containsKey(movieID)) {
				// Calculate similarity between target user and potential neighbor
				double similarity = calculateSimilarity(userID, entry.getKey());
				neighbors.put(entry.getKey(), similarity);
			}
		}

		// Sort all neighbors by similarity using a tree set with a custom comparator
		SortedSet<Entry<Integer, Double>> sortedNeighbors = 
				new TreeSet<Entry<Integer, Double>>(new SimilarityScoreComparator());
		sortedNeighbors.addAll(neighbors.entrySet());

		// Store most similar neighbor user IDs in list and return it
		List<Integer> topNeighbors = new ArrayList<>();
		int count = 0;

		for (Entry<Integer, Double> entry : sortedNeighbors) {
			topNeighbors.add(entry.getKey());
			count++;
			
			if (count >= neighborhoodSize) {
				break;
			}
		}

		return topNeighbors;
	}
	
	/**
	 * Runs a previously specified algorithm on two users to compute their similarity coefficient.
	 * @param userID1
	 * @param userID2
	 * @param similarity algorithm
	 * @return similarity coefficient between user1 and user2
	 */
	public double calculateSimilarity(int userID1, int userID2) {
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
