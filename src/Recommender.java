import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Map.Entry;
import java.util.Set;

/**
 * This class processes movie and user rating data to provide predictions and recommendations.
 *
 */
public class Recommender {
	private DataManager dm;
	private SimilarityAlgorithm algo;
	private int neighborhoodSize;

	/**
	 * Constructor method.
	 * @param movieDataFilename
	 * @param ratingDataFilename
	 * @throws FileNotFoundException 
	 */
	public Recommender(String movieDataFilename, String ratingDataFilename,
			SimilarityAlgorithm algo, int neighborhoodSize) throws FileNotFoundException {
		this.dm = new DataManager(movieDataFilename, ratingDataFilename);
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
	
		// iterate thru all ratings of this movie to get neighbors
		for (Entry<Integer, Double> entry : dm.getMovies().get(movieID).getRatings().entrySet()) {
			//System.out.println("in recommender, line 58: " + userID + " " + entry.getKey());
			double similarity = calculateSimilarity(userID, entry.getKey());
			//System.out.println("sim: " + similarity);
			neighbors.put(entry.getKey(), similarity);
		}

		return getTopEntries(neighbors, neighborhoodSize);
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
	 * This method returns the k number of entries with the highest value in a map.
	 * If the number of input elements in map is less than k, it returns a sorted map.  
	 * @param entries The input entries
	 * @param k The number of top entries to be returned
	 * @return the top entries from the input map
	 */
	public Map<Integer, Double> getTopEntries(Map<Integer, Double> entries, int k) {
		// Sort all entries by value using a TreeSet with a custom comparator
		SortedSet<Entry<Integer, Double>> sortedEntries = 
				new TreeSet<Entry<Integer, Double>>(new DescendingScoreComparator());
		sortedEntries.addAll(entries.entrySet());

		// Store top entries
		Map<Integer, Double> topEntries = new LinkedHashMap<>();
		int count = 0;

		for (Entry<Integer, Double> entry : sortedEntries) {
			topEntries.put(entry.getKey(), entry.getValue());
			count++;
			
			if (count >= k) {
				break;
			}
		}

		return topEntries;
	}
	
	/**
	 * Given a user and a movie that they haven't watched, this method predicts the user's
	 * preference for it.
	 * @param userID
	 * @param movieID
	 * @return the predicted preference rating
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
		
		User user = dm.getUsers().get(userID);
		
		// check if user has already rated the movie
		if (user.getRatings().containsKey(movieID)) {
			throw new IllegalArgumentException("ERROR: User has already rated this movie");
		}
		
		// check whether user has no ratings
		if (user.getRatings().isEmpty()) {
		    throw new IllegalStateException("ERROR: User has not rated any movies.");
		}
		
		// check whether user has rated all movies the same
		int index = 0;
		double reference = -1;

		for (Entry<Integer, Double> entry : user.getRatings().entrySet()) {
			if (index == 0) {
				reference = entry.getValue();
			} else {
				if (reference != entry.getValue()) {
					break;
				}
			}
			index++;
		}
		
		if (index > 1 && index == user.getRatings().size()) {
			throw new IllegalStateException("ERROR: User rated their movies the same rating. Prediction not possible.");
		}
		
		// get neighbors. Key: user ID of neighbor, value: similarity score
		Map<Integer, Double> neighbors = findNeighbors(userID, movieID, neighborhoodSize);
		
		// check if there is at least one neighbor
		if (neighbors.isEmpty()) {
			// return Double.MIN_VALUE if no one has rated this movie before
			return Double.MIN_VALUE;
		}
		
		// compute numerator and denominator of the CF equation
		double numerator = 0;
		double denominator = 0;

		for (Entry<Integer, Double> neighbor : neighbors.entrySet()) {
			numerator += neighbor.getValue() * (dm.getUsers().get(neighbor.getKey()).getRatings().get(movieID) 
							- dm.getUsers().get(neighbor.getKey()).calcAvgRating());
			denominator += Math.abs(neighbor.getValue());
		}
		
		// throw exception if denominator is 0
		if (Double.isNaN(denominator) || denominator == 0) {
			// return Double.MIN_VALUE if denominator is 0
			return Double.MIN_VALUE;
		}
		
		double prediction = dm.getUsers().get(userID).calcAvgRating() + numerator / denominator;
		
		// cap the max prediction value at 5 and min prediction value at 0
		if (prediction > 5) {
			prediction = 5;
		} else if (prediction < 0) {
			prediction = 0;
		}
		
		return prediction;
	}
	
	/**
	 * Given a user and a preference threshold, this method prints out the threshold number of top
	 * movies the user hasn't watched.
	 * @param userID
	 * @param threshold
	 * @return Map of movies. Key: movieID, value: predicted rating
	 */
	public Map<Integer, Double> recommendMovies(int userID, int threshold) {
		System.out.println("Calculating recommended movies...");
		// find set of movies the user hasn't rated
		Set<Integer> moviesRated = dm.getUsers().get(userID).getRatings().keySet();
		Set<Integer> moviesNotRated = new HashSet<>(dm.getMovies().keySet());
		moviesNotRated.removeAll(moviesRated);
		
		// check if user has rated all movies
		if (moviesNotRated.isEmpty()) {
			throw new IllegalStateException("ERROR: User has rated all movies. Nothing to recommend.");
		}
		
		// calculate predictions for all moviesNotRated
		Map<Integer, Double> predictions = new HashMap<>(); // key: movieID, value: predicted rating
		
		for (Integer movieID : moviesNotRated) {
			double prediction = predictPreference(userID, movieID);
			
			// skip this movie if no one has ever rated it
			if (prediction == Double.MIN_VALUE) {
				continue;
			}

			predictions.put(movieID, prediction);
		}
		
		// return top movies
		return getTopEntries(predictions, threshold);
	}
}
