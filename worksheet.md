# Task 0

Clone this repository (well done!)

# Task 1

Take a look at the two repositories:

  * (A) https://bitbucket.org/altmattr/personalised-correlation/src/master/
  * (B) https://github.com/Whiley/WhileyCompiler

And answer the following questions about them:

  * These repositories are at two different websites - github and bitbucket - what are these sites?  What service do they provide? Which is better?
  * Who made the last commit to repository A?
  * Who made the first commit to repository A?
  * Who made the first and last commits to repository B?
  * Are either/both of these projects active at the moment?  🤔 If not, what do you think happened?
  * 🤔 Which file in each project has had the most activity?

# Task 2

Setup a new IntelliJ project with a main method that will print the following message to the console when run:

~~~~~
Sheep and Wolves
~~~~~

# Task 3

Draw a 20 by 20 grid on a 1280x720 window. Have the grid take up the 720x720 square on the left of the window.  Each cell in the grid should be 35 pixels high and wide and the grid should be drawn 10 pixels off the top and left borders of the screen.  To do this, you should use the `Graphics` class from the Java libraries.  Be sure to consult the tips page for this task (it is a link in iLearn).  Without it, you will be very confused.

# ☆ Task 4

Create a 2D array to represent the grid and connect the drawn grid to the array in some way.

# Task 5

Modify your program so that mousing over a cell will "highlight" it.  Highlighted cells should be drawn in grey.

# Task 6

Ensure your program, if it does not already, has a `Cell` class and that your grid array is an array of `Cell` objects.  It should still display as before.  What are reasonable methods and fields for the `Cell` class?  Now create a `Grid` class to subsume your 2D array of `Cell`s.  What fields and methods should this class have?

# Task 7

Our `Cell` class is really a specialised rectangle and the Java API already has a `Rectangle` class.  Have `Cell` inherit from `java.awt.Rectangle` (https://docs.oracle.com/javase/8/docs/api/java/awt/Rectangle.html).  It will be good to call `super` in the `Cell` constructor and to use the `contains` method that comes in `Rectangle` instead of your own.  NB:  The `contains` we wrote was graceful when given a `null` pointer for the point, the one from `Rectangle` is not, you will need to "protect" it in some way.

# Task 8

Define a `Stage` class that can contain one `Grid` object and many `Character` objects.  There must be three separate characters, each a subclass of a `Character` _interface_ and each must have its own `paint` method.  The `paint` method must take a `Graphics` parameter and draw the character on that graphic.  Have the `paint` method specified in the `Character` interface and have each subclass define it.

Since `Character`s are drawing themselves, they need to know where they are on the screen so each will have a `Cell` field (that is set in the constructor) indicating where on the grid they are.

Have the program start with 1 grid and 3 characters:

  * Sheep (drawn white)
  * Wolf (drawn red)
  * Shepherd (drawn green)

# Task 9

Have a close look at your `Shepherd`, `Sheep` and `Wolf` classes.  If they are anything like mine they are _all the same except for the colour they use_.  This repetition is "a bad thing" because if the same thing is done in three different places, we need to remember that updating one requires us to update all three.

Is there a place that you could put all the common parts?

🤔 Will this work given what you currently have?  If not, what would we need to change?

# Task 10

Draw a picture of the inheritance hierarchy you have created.  You should (loosely) use [UML notation](http://www.csci.csusb.edu/dick/cs201/uml.html) for your diagram.  You are using UML In this case, and all through this course, only for "a rough sketch of an idea".

# Task 11

Start this task from the solution to Task 10.  The abstract `Character` class we were left with had to pick a default colour.  This was an entirely arbitrary choice.  Whenever you see arbitrary default values, you are seeing bad code.  But don't worry, Java 8 has us covered.  Java 8 introduced `Optional` values so that instead of arbitrary choices (or worse - `null`!) you can have an empty value.  [Read up on `Optional` values (just read until "Default Values and Actions")](http://www.oracle.com/technetwork/articles/java/java8-optional-2175753.html) and then change the `display` colour in the `Character` class to be an `Optional<Color>` instead of a `Color`.  You will need to make changes in the subclasses as well to support this.

🤔 Does the `Character` class even _need_ to be abstract?  Why or why not?

Your company builds a lot of games like this one and you now have to incorporate your work with the company's game processing code.  We have included a jar of this code in the `\lib` directory and javadoc explaining that code in the `\doc` directory.  In there you will see a `GameBoard` interface that represents things that a game might be played on, like a Chess board or the grid you have created.  You will also see an `GamePiece` interface that represents things that can move around such a game board, like a Chess piece or one of your characters.

## Task 12 - Step One

Have `Grid` implement `GameBoard` and have `Character` implement `GamePiece`.

## Task 12 - Step Two

With that done, you are now able to make use of the `RelativeMove`s provided by the library.  You should add functionality to play a set list of moves automatically.  I.e. when the game starts, it will play some hard-coded moves.  To do this you will need a list of moves to play, something like

~~~~~
private java.util.List<bos.RelativeMove> moves;
~~~~~

in your `Stage` class.

You will also need to have the `paint` method (that runs over and over again) make a move whenever a certain amount of time is up (say 2 seconds).

Note, we are now doing some processing between painting so we should move from the

~~~~~
loop forever {
  paint
}
~~~~~

paint loop to an update-and-paint loop like

~~~~~
loop forever {
  update game state
  paint
}
~~~~~

Something like the following `update` method in `Stage` (assuming you have a `timeOfLastMove` field in `Stage`) will work.

~~~~~
    public void update() {
        if (moves.size() > 0 && timeOfLastMove.plus(Duration.ofSeconds(2)).isBefore(Instant.now())){
            timeOfLastMove = Instant.now();
            moves.remove(0).perform();
        } else if (moves.size() == 0  && timeOfLastMove.plus(Duration.ofSeconds(20)).isBefore(Instant.now())) {
            System.exit(0);
        }
~~~~~

Fill your `moves` object with example moves and see if you can get your program to automatically play the moves you entered.
