import java.util.HashMap;
import java.util.LinkedHashMap;
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
	int neighborhoodSize;

	/**
	 * Constructor.
	 * @param movieDataFilename
	 * @param ratingDataFilename
	 */
	public Recommender(String movieDataFilename, String ratingDataFilename,
			SimilarityAlgorithm algo, int neighborhoodSize) {
		dm = new DataManager(movieDataFilename, ratingDataFilename);
		this.algo = algo;
		this.neighborhoodSize = neighborhoodSize;
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
	 * @return Map of neighbors. Key: user ID, value: similarity score
	 */
	public Map<Integer, Double> findNeighbors(int userID, int movieID, int neighborhoodSize) {
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

		// Store most similar neighbor user IDs and their respective similarity values
		Map<Integer, Double> topNeighbors = new LinkedHashMap<>();
		int count = 0;

		for (Entry<Integer, Double> entry : sortedNeighbors) {
			topNeighbors.put(entry.getKey(), entry.getValue());
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
	 * Calculate the average moving rating for a user.
	 * @param userID
	 * @return
	 */
	public double getAverageRating(int userID) {
		int count = 0;
		double total = 0;
		
		for (Entry<Integer, Double> rating : dm.getUsers().get(userID).getRatings().entrySet()) {
			total += rating.getValue();
			count++;
		}
		
		if (count == 0) {
			return 0;
		} else {
			return total / count;
		}
	}
	
	/**
	 * Given a user and a movie that they haven't watched, this method predicts the user's
	 * preference for it.
	 * @param userID
	 * @param movieID
	 * @return preference rating
	 */
	public double predictPreference(int userID, int movieID) {
		// check if valid user ID
		if (!dm.getUsers().containsKey(userID)) {
			throw new IllegalArgumentException("ERROR: Invalid user ID.");
		}
		
		// check if valid movie ID
		if (!dm.getMovies().containsKey(movieID)) {
			throw new IllegalArgumentException("ERROR: Invalid movie ID.");
		}
		
		// check if user has already rated the movie
		if (dm.getUsers().get(userID).getRatings().containsKey(movieID)) {
			throw new IllegalArgumentException("ERROR: User has already rated this movie");
		}
		
		// get neighbors
		Map<Integer, Double> neighbors = findNeighbors(userID, movieID, neighborhoodSize);
		
		// check if there is at least one neighbor
		if (neighbors.isEmpty()) {
			throw new IllegalStateException("ERROR: User has no neighbors who have rated this movie");
		}
		
		// compute numerator and denominator of the CF equation
		double numerator = 0;
		double denominator = 0;

		for (Entry<Integer, Double> neighbor : neighbors.entrySet()) {
			numerator += neighbor.getValue()
					* (dm.getUsers().get(neighbor.getKey()).getRatings().get(movieID) - getAverageRating(neighbor.getKey()));
			denominator += Math.abs(neighbor.getValue());
		}
		
		// throw exception if denominator is 0
		if (denominator == 0) {
			throw new IllegalStateException("ERROR: No meaningful neighbor preference data to make prediction");
		}
		
		double prediction = getAverageRating(userID) + numerator / denominator;
		
		// cap the max prediction value at 5 and min prediction value at 0
		if (prediction > 5) {
			prediction = 5;
		} else if (prediction < 0) {
			prediction = 0;
		}
		
		return prediction;
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
