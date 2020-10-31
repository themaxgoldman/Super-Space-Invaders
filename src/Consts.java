import java.awt.Color;
import java.awt.Dimension;

/**
 * Holds global constants for the game
 * @author maxgoldman
 *
 */
public final class Consts {
    
    //Game-wide constants
    public static final int GAME_WIDTH = 900;
    public static final int GAME_HEIGHT = 650;
    public static final Dimension GAME_SIZE = new Dimension(GAME_WIDTH, GAME_HEIGHT);
    public static final int X_CENTER = GAME_WIDTH/2;
    public static final int Y_CENTER = GAME_HEIGHT/2;
    
    //Player constants
    public static final int PLAYER_LIVES = 3;
    public static final int PLAYER_WIDTH = 50;
    public static final int PLAYER_HEIGHT = 20;
    public static final Dimension PLAYER_SIZE = new Dimension(PLAYER_WIDTH, PLAYER_HEIGHT);
    public static final int SCORE_MAX = Integer.MAX_VALUE;
    public static final int SCORE_MIN = 0;
    
    //Blast constants
    public static final int BLAST_WIDTH = 5;
    public static final int BLAST_HEIGHT = 15;
    public static final Dimension BLAST_SIZE = new Dimension(BLAST_WIDTH, BLAST_HEIGHT);
    
    //Enemy constants
    public static final int ENEMY_WIDTH = 30;
    public static final int ENEMY_HEIGHT = ENEMY_WIDTH;
    public static final Dimension ENEMY_SIZE = new Dimension(ENEMY_WIDTH, ENEMY_HEIGHT);
    
    //Game model constants
    public static final PositionPoint BOTTOM_PLAYER_START_POSITION = new PositionPoint(X_CENTER - (PLAYER_WIDTH / 2), GAME_HEIGHT - (20 + PLAYER_HEIGHT / 2));
    public static final PositionPoint TOP_PLAYER_START_POSITION = new PositionPoint(X_CENTER - (PLAYER_WIDTH / 2), 20);
    public static final int NO_MANS_LAND_HEIGHT = 75;
    public static final int ENEMIES_WIDE = 13;
    public static final int ENEMIES_HIGH = 6;
    public static final int TOTAL_ENEMIES = ENEMIES_WIDE * ENEMIES_HIGH;
    public static final int ENEMY_Y_MAX = BOTTOM_PLAYER_START_POSITION.getY() - NO_MANS_LAND_HEIGHT;
    public static final int ENEMY_Y_MIN = TOP_PLAYER_START_POSITION.getY() + PLAYER_HEIGHT + NO_MANS_LAND_HEIGHT;
    public static final int ENEMY_X_MAX = 800;
    public static final int ENEMY_X_MIN = 100;
    public static final int ENEMY_X_SPACE = (ENEMY_X_MAX - ENEMY_X_MIN) / ENEMIES_WIDE;
    public static final int ENEMY_Y_SPACE = (ENEMY_Y_MAX - ENEMY_Y_MIN) / ENEMIES_HIGH;
    public static final int PLAYER_BLAST_SPEED = 600;
    public static final int ENEMY_BLAST_SPEED = 300;
    public static final int PLAYER_MOVE_SPEED = 200;
    public static final int ENEMY_START_MOVE_INTERVAL = 1000; //milliseconds
    public static final int ENEMY_MOVE_DISTANCE = 20;
    public static final int MAX_COLLISION_CHECK_DISTANCE = 50;
    
    //View constants
    public static final int VIEW_UPDATE_INTERVAL = 25; //milliseconds
    public static final int CHECK_GAME_OVER_INTERVAL = 50; //milliseconds
    public static final String FONT_PATH = "files/fonts/joystix monospace.ttf";
    public static final int GAME_VIEW_WIDTH = GAME_WIDTH;
    public static final int GAME_VIEW_HEIGHT = GAME_HEIGHT + 100;
    public static final Dimension GAME_VIEW_SIZE = new Dimension(GAME_VIEW_WIDTH, GAME_VIEW_HEIGHT);
    public static final int X_OFFSET = GAME_VIEW_WIDTH - GAME_WIDTH;
    public static final int Y_OFFSET = (GAME_VIEW_HEIGHT - GAME_HEIGHT) / 2;
    public static final Dimension NON_GAME_VIEW_SIZE = new Dimension(900, 600);
    
    //Color constants
    public static final Color TEXT_COLOR = Color.WHITE;
    
    //WinnerView constants
    public static final int W_TYPE_START_X = 410;
    public static final int W_TYPE_START_Y = 357;
    public static final PositionPoint TYPE_START_POSITION = new PositionPoint(W_TYPE_START_X, W_TYPE_START_Y);
    
    //Sound constants
    public static final boolean SOUND_ON = true;
    
    //File constants
    public static final String HIGH_SCORE_FILEPATH = "files/highscores.txt";
    
    //HighScoreView constants
    public static final int HN_TYPE_START_X = 214;
    public static final int HS_TYPE_START_X = 558;
    public static final int H_TYPE_START_Y = 225;
    public static final int SCORES_TO_DISPLAY = 8;
}
