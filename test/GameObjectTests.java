import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Test class for the game objects
 * @author maxgoldman
 *
 */
public class GameObjectTests {

    @Test@SuppressWarnings("unused")
    public void nullTests(){
        try {
            GameObject o = new Enemy(null, null);
        } catch(IllegalArgumentException e) {
            //Expected
        }
        try {
            GameObject o = new Enemy(null);
        } catch(IllegalArgumentException e) {
            //Expected
        }
        try {
            GameObject o = new Player(null, null, null);
        } catch(IllegalArgumentException e) {
            //Expected
        }
        try {
            GameObject o = new Player(null);
        } catch(IllegalArgumentException e) {
            //Expected
        }
        try {
            GameObject o = new Blast(null, null, null, false);
        } catch(IllegalArgumentException e) {
            //Expected
        }
        try {
            GameObject o = new Blast(null);
        } catch(IllegalArgumentException e) {
            //Expected
        }
    }
    
    @Test@SuppressWarnings("unused")
    public void playerTests() {
        PositionPoint p1 = new PositionPoint(50, 50);
        Player player1 = new Player(p1, Orientation.DOWN, ShooterType.PLAYER1);
        Player player1Copy = new Player(player1);
        
        assertEquals("equals", player1, new Player(p1, Orientation.DOWN, ShooterType.PLAYER1));
        assertEquals("copy constructor", player1, player1Copy);
        p1.translate(10, 10);
        assertNotEquals("encapsulation", player1, new Player(p1, Orientation.DOWN, ShooterType.PLAYER1));
        player1Copy.dropLife();
        assertNotEquals("more encapsulation", player1, player1Copy);
        
        assertEquals("life drop", player1Copy.getLives(), Consts.PLAYER_LIVES - 1);
        assertFalse("null equals", player1.equals(null));
        assertEquals("set score", player1.setScore(500), 500);
        assertEquals("change score", player1.changeScore(-200), 300);
        
        try {
            Player testPlayer = new Player(p1, Orientation.DOWN, ShooterType.ENEMY);
        } catch(IllegalArgumentException e) {
            //Expected
        }
        
    }
    
    @Test
    public void enemyTests() {
        PositionPoint p = new PositionPoint(75, 80);
        Enemy e1 = new Enemy(p, Orientation.DOWN);
        Enemy e2 = new Enemy(e1);
        
        assertEquals("equals", e1, e2);
        e2.flipOrientation();
        assertNotEquals("encapsulation", e1, e2);
        assertEquals("flip orientation", e2.getOrientation(), Orientation.UP);
        assertFalse("null equals", e1.equals(null));
    }
    
    @Test
    public void blastTests() {
        PositionPoint p = new PositionPoint(32, 80);
        Blast b1 = new Blast(p, Orientation.DOWN, ShooterType.PLAYER2, false);
        Blast b2 = new Blast(b1);
        Blast b3 = new Blast(p, Orientation.UP, ShooterType.PLAYER1, false);
        
        assertEquals("equals", b1, b2);
        assertTrue("move blast", b1.moveBlast(100));
        assertEquals("correct move", b1.getPosition(), new PositionPoint(32, 180));
        assertFalse("blast offscreen", b1.moveBlast(1000));
        assertTrue("move up", b3.moveBlast(20));
        assertEquals("correct move", b3.getPosition(), new PositionPoint(32, 60));
    }
}
