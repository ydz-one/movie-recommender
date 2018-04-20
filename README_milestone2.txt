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

The Logger is implemented as a singleton. Its constructor is private and provides
a getInstance() method to allow a single instance.

________________________________________________________________________________

README for design changes:

Upon implementation, there are already small changes beginning to take place
in comparison to our class diagram.

For example, the DataManager does not need the method fillMovieRatings()
because we accomplish this beforehand in the RatingDataParser.

We plan to move the try-catches of MovieDataParser and RatingDataParser to
the User Interface (main) for the sake of DRYness. When an exception is thrown
in the parser classes, a specific exception message will be passed up to the
User Interface.


Desired Design Patterns:

As we learn more about design patterns, we hope to consider the following
three design patterns:

1. Three-Tiered Architecture
Presentation -uses-> Controller -user-> Data
Our equivalent: UserInterface -uses-> Recommender -uses-> DataManager

2. Concrete Factory
We hope to implement a concrete factory that can create a "new" parser -
either the MovieDataParser or RatingDataParser - on the fly depending on
the specific need.

3. Strategy Pattern (not yet covered in class)

