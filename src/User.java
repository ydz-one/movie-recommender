import java.util.HashMap;
import java.util.Map;

/**
 * This class is a POJO that holds relevant data for a user.
 *
 */
public class User {
	private int id;
	//All of this user's ratings (Key: movie id, Value: rating)
	private Map<Integer, Double> ratings;
	
	/**
	 * Constructor method
	 * @param id
	 * @param name
	 * @param ratings
	 */
	public User(int id) {
		this.id = id;
		this.ratings = new HashMap<>();
	}
	
	/**
	 * Getter method for user id
	 * @return
	 */
	public int getId() {
		return id;
	}

	/**
	 * Getter method for ratings HashMap
	 * @return
	 */
	public Map<Integer, Double> getRatings() {
		return ratings;
	}

	/**
	 * This method adds a rating to the user's ratings HM
	 * @param movieID
	 * @param rating
	 */
	public void addRating(int movieID, double rating) {
		if (!ratings.containsKey(movieID)) {
			ratings.put(movieID, rating);
		}
	}
	
	/**
	 * This method calculates the average rating made by this user
	 * @return the average
	 */
	public double calcAvgRating() {
		double sum = 0;
		
		for (Integer movieID : ratings.keySet()) {
			sum += ratings.get(movieID);
		}
		
		if (ratings.size() == 0) return 0;
		
		return (sum * 1.0) / (ratings.size() * 1.0);
	}
	
}