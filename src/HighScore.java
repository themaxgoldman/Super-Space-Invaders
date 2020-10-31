import java.util.Comparator;

/**
 * Keeps track of a high score
 * @author maxgoldman
 *
 */
public class HighScore implements Comparator<HighScore>{
    
    /**
     * nickname associated with the high score
     */
    public String nickname;
    /**
     * the score
     */
    public int score;
    
    /**  
     * No argument constructor, should only be used as Comparator
     */
    public HighScore() {
        nickname = "";
        score = -1;
    }
    
    /**
     * Best constructor to use, instantiates a high score
     * @param n nickname associated with the high score
     * @param s score
     */
    public HighScore(String n, int s) {
        nickname = n;
        score = s;
    }
    
    /**
     * Compares the two given HighScores based on the scores
     * @param h1 first HighScore for comparison
     * @param h2 second HighScore for comparison
     * @return 0 if equal, a positive value if h2 greater than h1, 
     * or a negative value if h2 less than h1
     */
    @Override
    public int compare(HighScore h1, HighScore h2) {
        return -1 * Integer.compare(h1.score, h2.score);
    }
    
    /**
     * Standard toString method
     * @return String that represents the Blast
     */
    @Override
    public String toString() {
        return "[nickname: " + nickname + ", score: " + score + "]";
    }
}
