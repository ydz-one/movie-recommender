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

________________________________________________________________________________

Instructions:

To run the program, compile all the .java files and execute the main() method in
UserInterface.java.  "movies.dat" and "ratings.dat" are the two input files
required for this program to run. Please ensure that these two files are in the
workspace when running the program.

________________________________________________________________________________

Design and Implementation Decisions:

I) Data Tier:

The Data tier of this program consists of the classes:

DataManager.java
Movie.java
MovieDatParser.java
MovieParser.java
MovieParserFactory.java
RatingDatParser.java
RatingParser.java
RatingParserFactory.java
User.java

User and Movie are the two POJOs that we created to store the relevant data for
each user and movie, respectively.

Our DataManager uses the concrete factory pattern for parsing files. We have two
separate factories: MovieParserFactory and RatingParserFactory. There are two
separate factories because a MovieParser and RatingParser return different
types: the former returns Map<Integer, Movie> while the latter returns
Map<Integer, User>.

The MovieParserFactory gets the appropriate movie parser depending on the file
type. For the assignment, we only have a parser for a "dat" file, but our
architecture is set up to incorporate other types of parsers. The same goes for
RatingParserFactory.

MovieParser is an abstract class. It has the abstract methods setFileName(String
fileName) and parseMovies(). Its only instance variable is fileName because
every parser will need to receive a file name (it is also protected to avoid
setting it to public if possible).

Our implementation of MovieDatParser reads in a dat file that splits the data by
the double colon :: and returns a Map of all movies.

RatingParser is similar to MovieParser except that the parseRatings method takes
in a Map<Integer, Movie> allMovies (key: movieID, value: Movie obj). In the
bigger picture, this means that we have parsed the movie files first to get this
result and apply it as an argument for parseRatings().

RatingDatParser reads in a dat file that splits the data by the colon :: and
returns a Map of all users. Our implementation also handles the case if there is
a movie in the ratings file not listed in the movies file - just in case.

Finally, once everything is parsed, the DataManager provides access to the users
and the movies info via getUsers() and getMovies().


II) Controller Tier:

The controller tier of this program consists of the classes:

DescendingScoreComparator.java
PearsonCorrelation.java
Recommender.java
SimilarityAlgorithm.java

The Recommender class is the primary controller class that the UserInterface
directly communicates with. It receives user requests from UserInterface and
accesses user and movie data from DataManager to run the prediction and
recommendation algorithms. To compute similarity between users, we have set up
a strategy design pattern to allow a concrete implementation of the
SimilarityAlgorithm to be selected on the fly.

The interface SimilarityAlgorithm contains one method: execute(User u, User v).
It is designed as an interface because there are different kinds of similarity
algorithms you may want to use. The PearsonCorrelation class implements
SimilarityAlgorithm and uses, as expected, the Pearson Correlation Coefficient
algorithm to calculate the similarity of User u and User v's ratings. We return
0 as the similarity if the denominator is ever 0.

The predictPreference() method in Recommender is used to predict a user
preference rating given a user and a movie that user hasn't seen. Because the
input movie ratings are provided as a integer between 0 and 5 inclusive, we
decided to cap the predicted movie rating in this same interval. So, this means
any negative predicted rating is rounded up to 0 and any rating above 5 is
rounded down to 5.

The DescendingScoreComparator class is a Comparator we made to sort a Map by
its value in descending order. It is used in the getTopEntries() method in
Recommender to find the neighbors with the top similarity score and the movies
with the top predicted preference score.


III) User Interface Tier:

The user interface tier of this program consists of the class:

UserInterface.java

This class is responsible for taking in user input and passing it into the
Recommender class in order to get meaningful results. It also handles any
exceptions that may arise.

Because the person using the program enters user information by providing user
IDs, we assume that they are also comfortable with working directly with
movie IDs. This is why the input and output of movie arguments and return values
are in the form of movie IDs rather than movie titles.


IV) Miscellaneous:

The Logger class is a singleton class that is used by UserInterface in order to
record user input into a text file.
