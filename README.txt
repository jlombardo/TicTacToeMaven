This is a Java/Mavenized version of the popular classic game Tic-Tac-Toe. It provides a nice, modular design plus some simple AI to deliver a game where any human player ("X") can challenge the computer ("0") to a game.

Currently there is no way to change player ID or to use the game to challenge another human player.

The object design follows the MVC design pattern and the Single Responsibility Principle. Notice that the GameEngine (Controller), Rail and Tile classes (Model) are not dependent on the GameWindow (View / GUI) or startup classes. That means that the game can use different pluggable UI classes without modifying the Controller or Model classes.