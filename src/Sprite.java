import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

/**
 * Sprite class, handles display of GameObjects
 * @author maxgoldman
 *
 */
public class Sprite {
    
    private PositionPoint location;
    private Dimension size;
    private boolean filled;
    private Color c;
    
    private Image image;
    
    /**
     * No argument constructor, best not to use
     */
    public Sprite() {
        size = new Dimension();
        location = new PositionPoint();
    }
    
    /**
     * Best constructor to create Sprite,
     * creates Sprite from a GameObject
     * @param o GameObject to create Sprite from
     * @param images Images object to find images in
     */
    public Sprite(GameObject o, Images images) {
        size = new Dimension(o.getSize());
        location = new PositionPoint(o.getPosition());
        if(o instanceof Player) {
            if(((Player) o).getType() == ShooterType.PLAYER1) {
                image = images.getImage("p1Sprite");
            }
            
            else if(((Player) o).getType() == ShooterType.PLAYER2) {
                image = images.getImage("p2Sprite");
            }
        }
        if(o instanceof Enemy) {
            image = images.getImage("enemy1Sprite_1_" + o.getOrientation());
        }
        if(o instanceof Blast) {
            image = images.getImage("blastSprite_" + o.getOrientation());
        }
        if(o.orientation == Orientation.UP || o instanceof Blast || o instanceof Player) {
            filled = true;
        }
        else {
            filled = false;
        }
    }
    
    /**
     * Paints the sprite in the given Graphics context
     * @param g graphics context to paint in
     */
    public void paint(Graphics g) {
        if(image != null) {
            g.drawImage(image, location.getX(), location.getY(), null);
            return;
        }
        g.setColor(c);
        if(!filled) {
            g.drawRect(location.getX(), location.getY(), size.width, size.height);
        }
        else {
            g.fillRect(location.getX(), location.getY(), size.width, size.height);
        }
    }
    
    /**
     * Offsets the position of the Sprite
     * @param xOffset amount to offset in x direction
     * @param yOffset amount to offset in y direction
     */
    public void offset(int xOffset, int yOffset) {
        location.translate(xOffset, yOffset);
    }
    
    /**
     * Standard toString method
     * @return String that represents the Blast
     */
    @Override
    public String toString() {
        return "[Sprite: " + location.toString() + " " + size.toString() + "]";
    }
}
