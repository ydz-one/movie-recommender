import java.util.HashMap;

/**
 * This class is a POJO that holds relevant data for a user.
 *
 */
public class User {
	private int id;
	//All of this user's ratings (Key: movie id, Value: rating)
	private HashMap<Integer, Double> ratings;
	
	/**
	 * Constructor method
	 * @param id
	 * @param name
	 * @param ratings
	 */
	public User(int id, HashMap<Integer, Double> ratings) {
		this.id = id;
		this.ratings = ratings;
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
	public HashMap<Integer, Double> getRatings() {
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
		
		return sum / ratings.size();
	}
	
}