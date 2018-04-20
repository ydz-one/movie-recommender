README: CIT 594 - HW5 (Milestone 2)
This readme illuminates the following:

- Logger (Singleton)
- Design changes

@authors zyud, oliviahw
(Yuduo Zhao, Olivia Woo)

________________________________________________________________________________

README for Logger (Singleton):

Now that we have a better sense of what our Logger class needs, it has a Logger
and a BufferedWriter as instance variables.

The Logger is implemented as a singleton. Its constructor is private and
provides a getInstance() method to allow a single instance.

________________________________________________________________________________

README for Design Changes:

Upon implementation, there are already small changes beginning to take place in
comparison to our class diagram.

For example, the DataManager does not need the method fillMovieRatings() because
we accomplish this beforehand in the RatingDataParser.

We plan to move the try-catches of MovieDataParser and RatingDataParser to the
User Interface (main) for the sake of DRYness. When an exception is thrown in
the parser classes, a specific exception message will be passed up to the User
Interface.


Desired Design Patterns:

Since we have learned more about design patterns, we have now revised the design
to implement the following three design patterns:

1. Three-Tiered Architecture
Presentation -uses-> Controller -user-> Data
Our equivalent: UserInterface -uses-> Recommender -uses-> DataManager

2. Concrete Factory
We plan to use the concrete factory pattern for the parsers in this program to
be able to create Parser classes on the fly that can parse files of different
formats. We will create two factories, one for creating a parser for movie data
and one for creating a parser for ratings data.

To implement this design pattern, we will create a MovieDataParserFactory class
and a RatingDataParserFactory class that are responsible for creating their
respective parsers. We will also need a MovieDataParser interface and
RatingDataParser interface which the parsers will implement. For this
assignment, since the input files are only provided in .dat format, we would
only need to create the MovieDataDatParser and RatingDataDatParser classes.

3. Strategy Pattern
We plan to implement a strategy pattern in the Recommender class to allow for
the selection of different algorithms for computing user similarity. Although we
are only using the Pearson Correlation formula for this assignment, the strategy
pattern allows us to be able specify any algorithm to use on the fly.

To do this, we will create a SimilarityAlgorithm interface, which will be
implemented by any concrete similarity algorithm classes we create, such as the
class PearsonCorrelation.

Additionally, we will add a method in the Recommender class called
setSimularityAlgorithm that takes in a SimularityAlgorithm object to be used in
computing prediction and recommendation results.
