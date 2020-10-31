import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Test class for position point
 * @author maxgoldman
 *
 */
public class PositionPointTests {
    
    @Test
    public void normalPointTests() {
        PositionPoint validPoint = new PositionPoint(50, 75);
        
        assertEquals("xTest", validPoint.getX(), 50);
        assertEquals("yTest", validPoint.getY(), 75);
        assertEquals("equals test", validPoint, new PositionPoint(50, 75));
        assertNotEquals("null test", validPoint, null);
        try {
            assertNotEquals("null copy test", validPoint, new PositionPoint(null));
        } catch(IllegalArgumentException e) {
            //Expected result
        }
        
    }
    
    @Test
    public void invalidPointTests() {
        PositionPoint lowXHighY = new PositionPoint(-100, 15000);
        PositionPoint lowYHighX = new PositionPoint(20000, -10);
        
        assertEquals("low x high y", lowXHighY, new PositionPoint(0, Consts.GAME_VIEW_HEIGHT));
        assertEquals("low y high x", lowYHighX, new PositionPoint(Consts.GAME_VIEW_WIDTH, 0));
    }
    
    @Test
    public void translateTests() {
        PositionPoint point = new PositionPoint(5, 5);
        
        point.translate(10, 5);
        assertEquals("positive translation", point, new PositionPoint(15, 10));
        point.translate(-2, -6);
        assertEquals("negative translation", point, new PositionPoint(13, 4));
        point.translateX(20);
        assertEquals("translate x", point, new PositionPoint(33, 4));
        point.translateY(-2);
        assertEquals("translate y", point, new PositionPoint(33, 2));
        point.translate(-10, -10);
        assertEquals("translate out of bounds", point, new PositionPoint(23, 0));
    }
    
    @Test
    public void stringTest() {
        PositionPoint point = new PositionPoint(10, 11);
        
        assertEquals("string test 1", point.toString(), "(10,11)");
        point.translate(3, 4);
        assertEquals("string test 2", point.toString(), "(13,15)");
    }
}
