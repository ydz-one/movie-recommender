README: CIT 594 - HW5 (Milestone 3)
This readme illuminates the following:

- Submission File List
- Instructions
- Design and Implementation Decisions

@authors zyud, oliviahw
(Yuduo Zhao, Olivia Woo)

________________________________________________________________________________

Submission File List:

DataManager.java
DescendingScoreComparator.java
Logger.java
Movie.java
MovieDatParser.java
MovieParser.java
MovieParserFactory.java
PearsonCorrelation.java
RatingDatParser.java
RatingParser.java
RatingParserFactory.java
Recommender.java
SimilarityAlgorithm.java
User.java
UserInterface.java

_______________________________________________________________________________

Instructions:


_______________________________________________________________________________

Design and Implementation Decisions:

Our DataManager uses the concrete factory pattern for parsing files. We have two separate factories: MovieParserFactory and RatingParserFactory. There are two separate factories because a MovieParser and RatingParser return different types: the former returns Map<Integer, Movie> while the latter returns Map<Integer, User>.

The MovieParserFactory gets the appropriate movie parser depending on the file type. For the assignment, we only have a parser for a "dat" file, but our architecture is set up to incorporate other types of parsers. The same goes for RatingParserFactory.

MovieParser is an abstract class. It has the abstract methods setFileName(String fileName) and parseMovies(). Its only instance variable is fileName because every parser will need to receive a file name (it is also protected to avoid setting it to public if possible).

Our implementation of MovieDatParser reads in a dat file that splits the data by the double colon :: and returns a Map of all movies.

RatingParser is similar to MovieParser except that the parseRatings method takes in a Map<Integer, Movie> allMovies (key: movieID, value: Movie obj). In the bigger picture, this means that we have parsed the movie files first to get this result and apply it as an argument for parseRatings().

RatingDatParser reads in a dat file that splits the data by the colon :: and returns a Map of all users. Our implementation also handles the case if there is a movie in the ratings file not listed in the movies file - just in case.

Finally, once everything is parsed, the DataManager provides access to the users and the movies info via getUsers() and getMovies().


...


The interface SimilarityAlgorithm contains one method: execute(User u, User v). It is designed as an interface because there are different kinds of similarity algorithms you may want to use. The PearsonCorrelation class implements SimilarityAlgorithm and uses, as expected, the Pearson Correlation Coefficient algorithm to calculate the similarity of User u and User v's ratings.

We return 0 as the similarity if the denominator is ever 0.