
*Max Goldman 2017*
# Super Space Invaders

Super Space Invaders is a modification of the traditional Space Invaders game.

[Javadocs](https://ssi-javadocs.s3.amazonaws.com/index.html)

## Gameplay:

Two players play the game at once. The game is played as follows:
- Both players have a ship with 3 lives
- Getting hit by a blast will decrease a player's life total by 1
- Hitting an enemy will cause it to "flip". It's color changes and it faces the other direction, but what this essentially means is that the enemy will shoot the other player instead
- Players shoot through their own allies (hitting an enemy of your own color won't cause a collision
- Enemies will randomly swap positions (the total ally count for both sides is maintained)
- The enemies travel horizontally back and forth across the "field", the speed at which this happens increases as more enemies are hit, but is capped after a time.
- Enemies randomly shoot at the player they are directed towards, the frequency at which this occurs also increases as more enemies are hit, and is also capped.
- Players only can have 1 blast on the "field" at once. i.e. if a player "blasts'" they cannot "blast" again until that blast has collided with an object or left the field.

### Game Controls:
A: Moves the top player to the left
S: Top player shoots
D: Moves the top player to the right
LEFT: Moves the bottom player to the left
DOWN: Bottom player shoots
RIGHT: Moves the bottom player to the right

J (Cheat): Makes all enemies face down
K (Cheat): Resets enemies
L (Cheat): Makes all enemies face up

### Menu Controls:
DOWN: Moves menu selection down
UP: Moves menu selection up
ENTER: Selects menu option

### High Score Entry Controls:
ALL KEYS (Except ';'): Use to type nickname
ENTER: Inputs nickname

### Help Screen Controls:
SPACE: Returns to menu

### High Score Controls:
SPACE: Returns to menu

## Documentation:

### Model Overview:
The game is designed with a controller-view structure in mind. Basically, each different "view" (GameView, HelpView, HighScoreView, MenuView, WinnerView) maintains a different state the game can be in. A ViewController then handles the switching between each of these views when necessary. Essentially, each "view" is entirely unaware that the other views exists, they just do what is needed when created. This creates another level of encapsulation that prevented bugs, and when necessary made the debugging process easier.

The game itself operates within the GameView. However, none of the game logic is handled within the GameView. The GameView compiles a queue of UserAction's as they occur, and then at every update interval (40 fps) hands the queue over to the GameplayModel. The GameplayModel handles all of the logic of the game entirely independent of the GameView (except it does play sound). The Gameplay Model then responds to each action in the queue, in order (why a queue was used). After this the GameView asks the GameplayModel for a set of Sprites. It then displays them. So all the GameView every gets from the model is a set of Images and their locations. This encapsulation also helped to prevent bugs and make debugging much easier. It also allows for simpler JUnit testing of the GameplayModel

### Classes:

**Blast:**
*Subclass of GameObject*
Represents a blast from either an enemy or player. Blast holds the ShooterType information because this matter in the game logic.

*Unique Methods and Functionality:*
- `blastFromObject(GameObject o, Orientation o)` - gives a Blast shot from a given GameObject, uses the GameObjects orientation to choose the direction it's shot in.
 - `shouldDamage(GameObject o)` - determines whether or not a given GameObject should be damaged by the Blast. Combines ShooterType, the objects orientation, and the GameObject
 - `intersects(GameObject o)` method to determine this. Originally the game was slowed down by the sheer amount of collision detection that had to happen. I added in a condition that prevents the collision detection from happening if the objects aren't reasonably close. This cut down the collision calculations that needed to occur by approximately 98.5% (got rid of 25,000 collision detections that would've been done in 15 seconds. Only 400 needed to be done this way).


**Consts:**
Holds global constants for the entire game.


**Enemy:**
*Subclass of GameObject*
Represents an enemy in the game.

*Unique Methods and Functionality:*
`flipOrientation()` - flips the orientation of the enemy.


**GameObject:**
*Abstract class: Superclass of Blast, Enemy, and Player*
Superclass of all objects that affect gameplay, all subclasses subsequently must have a position, orientation, and size. Has many methods that handle related operations for them.

*Unique Methods and Functionality:*
`intersects(GameObject other)` - determines whether or not the two GameObjects are intersecting. Using a simple bounding box collision algorithm.


**GameplayModel:**
Handles all game logic.

*Unique Methods and Functionality:*
- `advance(int milliseconds, Queue<UserAction> actionQueue)` - goes through the following steps in order to advance the game by the given amount of time
1. Responds to all actions in the actionQueue
2. Moves all blasts by necessary amount, then removes the ones that are no longer needed (Collections concept)
3. Moves players by necessary amount
4. Checks for collisions between all Blasts and Players
5. Checks for collisions between all Blasts and Enemies
6. Move enemies sideways if time to do that
7. Decide if gonna make a switch between two Enemies, then execute the switch if decided
8. Decide if an enemy will shoot, then create the shot if decided.
- `respondToAction(UserAction a)` - modifies the model based on the given UserAction
- `getSprites()` - returns a set of Sprites that represent all GameObjects in the model
- `checkWinner()` - returns the winner of the game (null if no winner yet)


**GameState:**
Enum that represents the different states the game can be in


**GameView:**
*Subclass of JPanel, implements View*
Handles the displaying of the game

*Unique Methods and Functionality:*
- `addAction(UserAction a)` - adds a UserAction to the queue to be sent at the next update


**HelpView:**
*Subclass of JPanel, implements View*
Handles the displaying of help screen


**HighScore:**
*implements Comparator<HighScore>*
Keeps track of a high score, and nickname of person who got the score

**Unique Methods and Functionality:**
- `compare(HighScore h1, HighScore h2)` - compare method that makes sorting high scores from greatest to least easier


**HighScoreIO:**
Handles the writing a reading of HighScores to a file

**Unique Methods and Functionality:**
- `getScores()` - reads all HighScores from the file and returns List<HighScore> containing them
- `addHighScore(HighScore hs)` - writes HighScore to the file


**HighScoreView:**
*Subclass of JPanel, implements View*
Handles the displaying of HighScore screen


**Images:**
Imports all images initially in order to improve the efficiency of loading images. Stores all images in a map


**KeyPressAction:**
*Subclass of AbstractAction*
Handles the responses to most actions that take place by the user in the gameplay


**MainOption:**
Enum that represents the options in the main menu


**MenuView:**
*Subclass of JPanel, implements View*
Handles the displaying of Menu screen


**Orientation:**
Enum that represents the orientation of anything in the game


**Player:**
*Subclass of GameObject*
Represents a player in the game


**PositionPoint:**
Represents a position in the game. Clips x and y values so that the position will always be in scope of the game.

*Unique Methods and Functionality:*
- `distance(PositionPoint p1, PositionPoint p2)` - gives the distance between two PositionPoints


**ShooterType:**
Enum that represents the type of shooter a Blast has


**Sounds:**
Handles the playing of all sounds in the game. Because later in the game there can be so many simultaneous enemy shots, the sounds class maintains an array of 4 Clips of the enemy shot sound, to prevent the Clips from needing to be reloaded each time (which turned out to be pretty expensive). It also caps the amount of enemy shot sounds playing at a given time to 4.


**Sprite:**
Maintains an image or rectangle (if no image could be found) at a position for display purposes.


**UserAction:**
Represents the different types of UserActions that a user may do during the game.


**View:**
Interface for all Views, main purpose is to allow ViewController to cut off all sounds from the previous View when a switch between GameStates is made


**ViewController:**
Controls all the Views in the game, and switches between them when necessary.

**Unique Methods and Functionality:**
- `setGameState(GameState s)` - switches the state of the game to the given GameState
- `setInputMap(InputMap map)` - sets the given InputMap to handle all input needed by the game
- `setActionMapFor___(SomeView view)` - sets the ActionMap for the given View


**WinnerView:**
*Subclass of JPanel, implements View*
Handles the displaying of Winner screen, also takes input for the new nickname.
