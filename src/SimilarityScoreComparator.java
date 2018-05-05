import java.util.Comparator;
import java.util.Map.Entry;

/**
 * This comparator sorts entries with integer key and double value by value in descending order.
 * It is used in the Recommender class for finding the most similar neighbors for a user.
 *
 */
public class SimilarityScoreComparator implements Comparator<Entry<Integer, Double>>{
	@Override
	public int compare(Entry<Integer, Double> a, Entry<Integer, Double> b) {
		return b.getValue().compareTo(a.getValue());
	}
}
