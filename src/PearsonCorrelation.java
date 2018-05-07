import java.util.HashMap;
import java.util.HashSet;

/**
 * This class computes the Pearson Correlation Coefficient of two users.
 *
 */
public class PearsonCorrelation implements SimilarityAlgorithm {

	@Override
	public double execute(User u, User v) {
		double avgU = u.calcAvgRating();
		double avgV = v.calcAvgRating();
		
//		System.out.println("avgU: " + avgU);
//		System.out.println("avgV: " + avgV);
		
		HashMap<Integer, Double> ratingsU = u.getRatings();
		HashMap<Integer, Double> ratingsV = v.getRatings();
		
//		System.out.println("size of ratingsU: " + ratingsU.size());
//		System.out.println("size of ratingsV: " + ratingsV.size());
		
		HashSet<Integer> commonMovies = new HashSet<>();
		
		for (Integer movieID : ratingsU.keySet()) {
			if (ratingsV.containsKey(movieID)) {
				commonMovies.add(movieID);
			}
		}
		
		// System.out.println("commonMovies size: " + commonMovies.size());
		if (commonMovies.size() == 0) return 0;
		
		double numerator = 0.0;
		double denom1 = 0.0, denom2 = 0.0;
		
		for (Integer movieID : commonMovies) {
//			double v1 = ratingsU.get(movieID);
//			double v2 = ratingsV.get(movieID);
//			System.out.println("v1 is " + v1 + "...v2 is " + v2);
			numerator += (ratingsU.get(movieID) - avgU) * (ratingsV.get(movieID) - avgV);
			denom1 += Math.pow(((double)ratingsU.get(movieID) - avgU), 2);
			denom2 += Math.pow(((double)ratingsV.get(movieID) - avgV), 2);
		
//			System.out.println("111 " + numerator);
//			System.out.println("222 " + denom1);
//			System.out.println("333 " + denom2);
		}
		
//		System.out.println("111 " + numerator);
//		System.out.println("222 " + denom1);
//		System.out.println("333 " + denom2);
		
		if (denom1 == 0 && denom2 == 0) return 0;
		
		return numerator / (Math.sqrt(denom1) * Math.sqrt(denom2));
	}

}
