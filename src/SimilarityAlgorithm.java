/**
 * This is the interface for similarity algorithms. It is used as part of the strategy
 * design pattern in Recommender.
 *
 */
public interface SimilarityAlgorithm {
	public double execute(User u, User v);
}
