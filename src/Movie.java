import java.util.HashMap;

/**
 * This class is a POJO that holds relevant data for a movie.
 *
 */
public class Movie {
	private int id;
	private String title;
	//All users' ratings for this movie (Key: user id, Value: rating)
	private HashMap<Integer, Double> ratings;
	
	/**
	 * Constructor method
	 * @param id
	 * @param title
	 * @param ratings
	 */
	public Movie(int id, String title) {
		this.id = id;
		this.title = title;
		this.ratings = new HashMap<>();
	}

	/**
	 * Getter for movie id
	 * @return
	 */
	public int getId() {
		return id;
	}

	/**
	 * Getter for title
	 * @return
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Getter for ratings HashMap
	 * @return hashMap of ratings
	 */
	public HashMap<Integer, Double> getRatings() {
		return ratings;
	}
	
	/**
	 * Setter for ratings HashMap
	 * @param ratings
	 */
	public void setRatings(HashMap<Integer, Double> ratings) {
		this.ratings = ratings;
	}
	
	/**
	 * This method adds a rating to the movie's ratings HM
	 * @param userID
	 * @param rating
	 */
	public void addRating(int userID, double rating) {
		if (!ratings.containsKey(userID)) {
			ratings.put(userID, rating);
		}
	}
	
}