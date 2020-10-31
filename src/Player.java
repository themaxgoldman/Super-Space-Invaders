import java.awt.Dimension;

/**
 * Player class keeps track of a player in the game
 * @author maxgoldman
 *
 */
public class Player extends GameObject {
    
    private int score;
    private int lives;
    private ShooterType player;
    private int numShots;
    
    /**
     * No argument constructor ideally should not be used,
     * defaults to (0,0) facing up
     */
    public Player() {
        super();
        this.size = Consts.PLAYER_SIZE;
        this.score = 0;
        this.lives = Consts.PLAYER_LIVES;
        this.numShots = 0;
    }
    
    /**
     * Best constructor for creating a new Player
     * @param p new player's position
     * @param o direction that the new player is facing
     * @param t type of the Player (PLAYER1 or PLAYER2)
     */
    public Player(PositionPoint p, Orientation o, ShooterType t) {
        super(p, o, Consts.PLAYER_SIZE);
        this.score = 0;
        this.lives = Consts.PLAYER_LIVES;
        if(t == ShooterType.ENEMY) {
            throw new IllegalArgumentException("player can't be of type enemy");
        }
        this.player = t;
        this.numShots = 0;
    }
    
    /**
     * Standard copy constructor
     * @param p Player to copy
     */
    public Player(Player p) {
        if(p == null) {
            throw new IllegalArgumentException("can't copy null");
        }
        this.position = new PositionPoint(p.getPosition());
        this.orientation = p.getOrientation();
        this.size = new Dimension(Consts.PLAYER_SIZE);
        this.score = p.getScore();
        this.lives = p.getLives();
    }
    
    /**
     * Getter method for player's score
     * @return player's score
     */
    public int getScore() {
        return this.score;
    }
    
    /**
     * Setter method for player's score, clips if necessary
     * @param newScore new score
     * @return new score (after clipping)
     */
    public int setScore(int newScore) {
        this.score = newScore;
        if(this.score < Consts.SCORE_MIN) {
            this.score = Consts.SCORE_MIN;
        }
        else if(this.score > Consts.SCORE_MAX) {
            this.score = Consts.SCORE_MAX;
        }
        return this.score;
    }
    
    /**
     * Changes the player's score, clips if necessary
     * @param dScore amount to change score by
     * @return new score
     */
    public int changeScore(int dScore) {
        return this.setScore(this.score + dScore);
    }
    
    /**
     * Get the type of the player
     * @return the player's type
     */
    public ShooterType getType() {
        return player;
    }
    
    /**
     * Getter method for player's lives
     * @return player's lives
     */
    public int getLives() {
        return this.lives;
    }
    
    /**
     * Decrements life total by 1
     * @return new life total
     */
    public int dropLife() {
        lives--;
        return lives;
    }
    
    /**
     * Changes position (from top left corner), clips 
     * new position if necessary
     * @param dx change in x
     * @param dy change in y
     */
    @Override
    public void changePosition(int dx, int dy) {
        super.changePosition(dx, dy);
        if(this.position.getX() > Consts.GAME_WIDTH - Consts.PLAYER_WIDTH) {
            setPosition(new PositionPoint(Consts.GAME_WIDTH - Consts.PLAYER_WIDTH, position.getY()));
        }
    }
    
    /**
     * Should be called whenever the player shoots
     */
    public void addShot() {
        this.numShots++;
    }
    
    /**
     * Gets the number of times the player has shot
     * @return the number of times the player has shot
     */
    public int getNumShots() {
        return this.numShots;
    }
    
    /**
     * Standard equals method
     * @param o object to check equality with
     * @return whether the two objects are equal
     */
    @Override
    public boolean equals(Object o) {
        if(o instanceof Player) {
            Player oPlayer = (Player) o;
            
            boolean pEquals = oPlayer.getPosition().equals(position);
            boolean oEquals = oPlayer.getOrientation() == orientation;
            boolean sEquals = oPlayer.getSize().equals(size);
            boolean scoreEquals = oPlayer.getScore() == score;
            boolean lifeEquals = oPlayer.getLives() == lives;
            
            return pEquals && oEquals && sEquals && scoreEquals && lifeEquals;
        }
        return false;
    }
    
    /**
     * Standard toString method
     * @return String that represents the Blast
     */
    @Override
    public String toString() {
        return "[Player: " + position.toString() + " " + orientation + " " + size.toString() +
                " score: " + score + " lives: " + lives + "]";
    }

}
