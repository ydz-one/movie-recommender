import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * This class computes the Pearson Correlation Coefficient of two users.
 *
 */
public class PearsonCorrelation implements SimilarityAlgorithm {

	@Override
	public double execute(User u, User v) {
		double avgU = u.calcAvgRating();
		double avgV = v.calcAvgRating();
		
		Map<Integer, Double> ratingsU = u.getRatings();
		Map<Integer, Double> ratingsV = v.getRatings();
		
		Set<Integer> commonMovies = new HashSet<>();
		
		// populate the commonMovies set with common movies between u and v
		for (Integer movieID : ratingsU.keySet()) {
			if (ratingsV.containsKey(movieID)) {
				commonMovies.add(movieID);
			}
		}
		
		if (commonMovies.size() == 0) return 0;
		
		double numerator = 0.0;
		double denom1 = 0.0, denom2 = 0.0;
		
		// calculate Pearson
		for (Integer movieID : commonMovies) {
			numerator += (ratingsU.get(movieID) - avgU) * (ratingsV.get(movieID) - avgV);
			denom1 += Math.pow(((double)ratingsU.get(movieID) - avgU), 2);
			denom2 += Math.pow(((double)ratingsV.get(movieID) - avgV), 2);
		}
		
		if (denom1 == 0 || denom2 == 0) return 0;
		
		return numerator / (Math.sqrt(denom1) * Math.sqrt(denom2));
	}

}
