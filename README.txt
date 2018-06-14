Movie Recommender: Group Project for CIT 594

This readme illuminates the following:

-UML Class Diagram
-UML Sequence Diagram

@authors zyud, oliviahw
(Yuduo Zhao, Olivia Woo)

________________________________________________________________________________

README for UML Class Diagram:

0) Overall Design:

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


1) UserInterface:

This class handles retrieving input from the user and providing results to the
user via the console.


2) Logger:

This class logs the user input data and writes it out to an external file.


3) Recommender:

This class provides the methods to calculate movie preference predictions and
movie recommendations from movie and user rating data.


4) DataManager:

This class handles the storage and processing of the data parsed from
MovieDataParser and RatingDataParser and provides access to them for other
classes.


5) MovieDataParser:

This class provides methods to parse and clean the movies.dat file into a
collection of Movie objects to be stored in DataManager.


6) RatingDataParser:

This class provides methods to parse and clean the ratings.dat file into a
collection of User objects with to be stored in DataManager.


7) Movie:

This class is a POJO that holds relevant data for a movie.


8) User:

This class is a POJO that holds relevant data for a user.

________________________________________________________________________________

README for UML Sequence Diagram:

1) Setting Up:

A minor side note: Due to the instructions, in which we must generate a
prediction given user u and item i, our Movie object is specified as i and User
object is specified as u.

The UML Sequence Diagram beings with the UserInterface (which contains the main).
It creates a Recommender object, which in turn needs to create a DataManager
object.

To create the DataManager, it must first collect all the movie objects via
MovieDataParser. The DataManager collects all the user objects via
RatingDataParser: In order to eliminate the need for a Rating object, after
executing parseMovies(), the DataManager has the movie information it needs to
read the ratings data file and to populate User objects with all the appropriate
movie ratings.

Following parseRatings(), the DataManager also has all the information it needs
to execute fillMovieRatings(), which will fill the ratings HashMaps of the users
and movies.

The DataManager is now ready to be given to the Recommender object, and the
Recommender object is ready to be used.


2) Generating Prediction:

In UserInterface, we generate a prediction for User u and Movie i via
Recommender's predictPreference() method. This method requires several processes
prior to returning the result; thus, we represent these processes within the
overall predictPreference arrow-loop.

The Recommender gets all users and movies stored in the DataManager object. This
information is used to find the neighbors of user u.

calcPearsonSim() calculates the similarity between u and each of the other users.
This information is used to evaluate predictPreference().

Finally, the result of the prediction is returned to the UserInterface.
