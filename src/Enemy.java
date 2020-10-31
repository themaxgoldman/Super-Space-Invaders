import java.awt.Dimension;

/**
 * Keeps track of an enemy within the GameplayModel
 * @author maxgoldman
 *
 */
public class Enemy extends GameObject {
    
    /**
     * No argument constructor, best not to use
     */
    public Enemy() {
        super();
        this.size = Consts.ENEMY_SIZE;
    }
    /**
     * Best constructor for creating a new enemy
     * @param p position of new enemy
     * @param o orientation of new enemy
     */
    public Enemy(PositionPoint p, Orientation o) {
        super(p, o, Consts.ENEMY_SIZE);
        if(o == Orientation.LEFT || o == Orientation.RIGHT) {
            throw new IllegalArgumentException("enemy orientation must be up or down");
        }
    }
    
    /**
     * Standard copy constructor
     * @param e Enemy to copy
     */
    public Enemy(Enemy e) {
        if(e == null) {
            throw new IllegalArgumentException("can't copy null");
        }
        this.position = new PositionPoint(e.getPosition());
        this.orientation = e.getOrientation();
        this.size = new Dimension(Consts.ENEMY_SIZE);
    }
    
    /**
     * Flips the orientation of the enemy
     * @return the new orientation
     */
    public Orientation flipOrientation() {
        orientation = orientation.getflipped();
        return orientation;
    }
    
    /**
     * Standard equals method
     * @param o object to check equality with
     * @return whether the two objects are equal
     */
    @Override
    public boolean equals(Object o) {
        if(o instanceof Enemy) {
            Enemy oEnemy = (Enemy) o;
            
            boolean pEquals = oEnemy.getPosition().equals(position);
            boolean oEquals = oEnemy.getOrientation() == orientation;
            boolean sEquals = oEnemy.getSize().equals(size);
            
            return pEquals && oEquals && sEquals;
        }
        return false;
    }
    
    /**
     * Standard toString method
     * @return String that represents the Enemy
     */
    @Override
    public String toString() {
        return "[Enemy: " + position.toString() + " " + orientation + " " + size.toString() +
                "]";
    }
}
