README: CIT 594 - HW5 (default)
This readme illuminates the following:

-UML Class Diagram
-UML Sequence Diagram

@authors zyud, oliviahw

________________________________________________________________________________

README FOR UML Class Diagram:

0) Overall Design
The design for this program follows a three-tier architecture model with a
presentation tier, application tier, and data tier.

The presentation tier consists of the UserInterface class; the application tier
consists of the Recommender class; and the data tier consists of the
DataManager class, MovieDataParser class, and RatingDataParser class, with the
first handling data storage and the latter two handling data parsing.

By adopting this highly modular model of software design, we expect all parts of
the program to be easily modifiable with minimal impact on other parts. Also,
the modularity of the program would make it easier for unit testing and human
comprehension.

1) UserInterface
This class handles retrieving input from the user and providing results to the
user via the console.

2) Logger
This class logs the user input data and writes it out to an external file.

3) Recommender
This class provides the methods to calculate movie preference predictions and
movie recommendations from movie and user rating data.

4) DataManager
This class handles the storage and processing of the data parsed from
MovieDataParser and RatingDataParser and provides access to them for other
classes.

5) MovieDataParser
This class provides methods to parse and clean the movies.dat file into a
collection of Movie objects to be stored in DataManager.

6) RatingDataParser
This class provides methods to parse and clean the ratings.dat file into a
collection of User objects to be stored in DataManager.

7) Movie
This class is a POJO that holds relevant data for a movie.

8) User
This class is a POJO that holds relevant data for a user.
________________________________________________________________________________

README FOR UML Sequence Diagram:
