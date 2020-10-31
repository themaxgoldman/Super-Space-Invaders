import static org.junit.jupiter.api.Assertions.*;

import org.junit.Test;

/**
 * Tests interaction between elements of the game
 * @author maxgoldman
 *
 */
public class InteractionTests {

    @Test
    public void intersectsTests() {
        PositionPoint p1 = new PositionPoint(50, 50);
        PositionPoint p2 = new PositionPoint(40, 40);
        PositionPoint p4 = new PositionPoint(80, 80);
        PositionPoint p5 = new PositionPoint(20, 20);
        
        GameObject o1 = new Player(p1, Orientation.DOWN, ShooterType.PLAYER1);
        GameObject o2 = new Enemy(p2, Orientation.DOWN);
        GameObject o3 = new Player(p4, Orientation.UP, ShooterType.PLAYER2);
        GameObject o4 = new Enemy(p5, Orientation.DOWN);
        
        assertTrue(o1.intersects(o2));
        assertFalse(o2.intersects(o3));
        assertFalse(o3.intersects(o1));
        assertTrue(o4.intersects(o1));
    }
    
    @Test
    public void damageTests() {
        PositionPoint p1 = new PositionPoint(50, 50);
        PositionPoint p2 = new PositionPoint(150, 150);
        
        Blast b1 = new Blast(p1, Orientation.DOWN, ShooterType.PLAYER2, false);
        Blast b2 = new Blast(b1);
        
        Player pl1 = new Player(p1, Orientation.UP, ShooterType.PLAYER1);
        Player pl2 = new Player(p1, Orientation.DOWN, ShooterType.PLAYER2);
        Player pl3 = new Player(p2, Orientation.UP, ShooterType.PLAYER1);
        
        Enemy e1 = new Enemy(p1, Orientation.UP);
        Enemy e2 = new Enemy(p1, Orientation.DOWN);
        Enemy e3 = new Enemy(p2, Orientation.UP);
        
        assertTrue(b1.shouldDamage(pl1));
        assertFalse(b1.shouldDamage(b2));
        assertFalse(b1.shouldDamage(pl3));
        assertFalse(b2.shouldDamage(b1));
        assertFalse(b1.shouldDamage(pl2));
        assertTrue(b1.shouldDamage(e1));
        assertFalse(b1.shouldDamage(e2));
        assertFalse(b1.shouldDamage(e3));
    }
    
    @Test
    public void createBlastTests() {
        PositionPoint p = new PositionPoint(50,50);
        
        PositionPoint pe1 = new PositionPoint(73,71);
        PositionPoint pe2 = new PositionPoint(73,34);
        PositionPoint pe3 = new PositionPoint(63,81);
        PositionPoint pe4 = new PositionPoint(63,34);
        
        Player pl1 = new Player(p, Orientation.DOWN, ShooterType.PLAYER2);
        Player pl2 = new Player(p, Orientation.UP, ShooterType.PLAYER1);
        Enemy e1 = new Enemy(p, Orientation.DOWN);
        Enemy e2 = new Enemy(p, Orientation.UP);
        
        Blast expected1 = new Blast(pe1, Orientation.DOWN, ShooterType.PLAYER2, false);
        Blast expected2 = new Blast(pe2, Orientation.UP, ShooterType.PLAYER1, false);
        Blast expected3 = new Blast(pe3, Orientation.DOWN, ShooterType.ENEMY, false);
        Blast expected4 = new Blast(pe4, Orientation.UP, ShooterType.ENEMY, false);
        
        assertEquals(Blast.blastFromObject(pl1, Orientation.DOWN, false), expected1);
        assertEquals(Blast.blastFromObject(pl2, Orientation.UP, false), expected2);
        assertEquals(Blast.blastFromObject(e1, Orientation.DOWN, false), expected3);
        assertEquals(Blast.blastFromObject(e2, Orientation.UP, false), expected4);
    }
    
    
}
