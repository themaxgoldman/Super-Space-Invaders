import java.awt.Dimension;

/**
 * Blast class keeps track of a blast in the game
 * @author maxgoldman
 *
 */
public class Blast extends GameObject {

    private ShooterType shooter;
    private boolean isSuperBlast;
    
    /**
     * No argument constructor, best not to use
     */
    public Blast() {
        super();
        this.size = Consts.BLAST_SIZE;
        this.shooter = ShooterType.ENEMY;
        this.isSuperBlast = false;
    }
    
    /**
     * Best constructor for creating a new Blast
     * @param p new blast's position
     * @param o new blast's orientation
     * @param t type of shooter that shot new blast
     * @param s whether or not the shot is a super shot
     */
    public Blast(PositionPoint p, Orientation o, ShooterType t, boolean s) {
        super(p, o, Consts.BLAST_SIZE);
        this.shooter = t;
        this.isSuperBlast = s;
    }
    
    /**
     * Standard copy constructor for blast
     * @param b blast to copy
     */
    public Blast(Blast b) {
        if(b == null) {
            throw new IllegalArgumentException("can't copy null");
        }
        this.orientation = b.getOrientation();
        this.position = new PositionPoint(b.getPosition());
        this.size = new Dimension(b.size);
        this.shooter = b.getShooter();
        this.isSuperBlast = b.isSuperBlast;
    }
    
    /**
     * Getter method for shooter
     * @return type of shooter
     */
    public ShooterType getShooter() {
        return this.shooter;
    }
    
    /**
     * Moves the blast a certain distance from its current position
     * @param distance distance to move the blast in its movement direction
     * @return false if blast is now offscreen, true otherwise
     */
    public boolean moveBlast(int distance) {
        int multiplier = 1;
        if(getOrientation() == Orientation.UP) {
            multiplier = -1;
        }
        int newY = this.position.getY() + (multiplier * distance);
        if(getOrientation() == Orientation.UP) {
            if(newY < 0) {
                return false;
            }
            position.setY(newY);
            return true;
        }
        if(getOrientation() == Orientation.DOWN) {
            if((newY + getSize().getHeight()) > Consts.GAME_HEIGHT) {
                return false;
            }
            position.setY(newY);
            return true;
        }
        throw new IllegalStateException("Invalid orientation");
    }
    
    /**
     * Determines if the blast should damage the given object, checks for collision
     * @param o the object to check for damage possibility
     * @return true if object should be damage, false otherwise
     */
    public boolean shouldDamage(GameObject o) {
        if(o == null) {
            throw new IllegalArgumentException("can't check damage conditions with null");
        }
        if(shooter == ShooterType.ENEMY && o instanceof Enemy) {
            return false;
        }
        if(o.getOrientation() == orientation) {
            return false;
        }
        if(o instanceof Player) {
            if(((Player) o).getType() == ShooterType.PLAYER1 && this.getShooter() == ShooterType.PLAYER1) {
                return false;
            }
            if(((Player) o).getType() == ShooterType.PLAYER2 && this.getShooter() == ShooterType.PLAYER2) {
                return false;
            }
        }
        if(PositionPoint.distance(this.position, o.position) > Consts.MAX_COLLISION_CHECK_DISTANCE) {
            return false;
        }
        return this.intersects(o);
    }
    
    /**
     * Gets a blast that is shot from the given object, throws exception if invalid direction
     * @param o object shooting the blast
     * @param direction direction the blast is being shot in (up or down)
     * @param isSuper whether or not the new blast should be a super shot
     * @return the new blast
     */
    public static Blast blastFromObject(GameObject o, Orientation direction, boolean isSuper) {
        if(o == null || direction == null) {
            throw new IllegalArgumentException("can't create blast from null");
        }
        if(direction == Orientation.LEFT || direction == Orientation.RIGHT) {
            throw new IllegalArgumentException("Invalid direction");
        }
        Dimension objectSize = o.getSize();
        PositionPoint objectPosition = o.getPosition();
        int xPosition = objectPosition.getX() + (objectSize.width / 2) - (Consts.BLAST_WIDTH / 2);
        int yPosition = -1;
        if(direction == Orientation.UP) {
            yPosition = objectPosition.getY() - Consts.BLAST_HEIGHT - 1;
        }
        else {
            yPosition = objectPosition.getY() + objectSize.height + 1;
        }
        
        return new Blast(new PositionPoint(xPosition, yPosition), direction, ShooterType.typeFromGameObject(o), isSuper);
    }
    
    /**
     * Returns whether or not the blast is a super blast
     * @return true if super blast, false otherwise
     */
    public boolean isSuper() {
        return this.isSuperBlast;
    }
    
    /**
     * Standard equals method
     * @param o object to check equality with
     * @return whether the two objects are equal
     */
    @Override
    public boolean equals(Object o) {
        if(o instanceof Blast) {
            Blast oBlast = (Blast) o;
            
            boolean pEquals = oBlast.getPosition().equals(position);
            boolean oEquals = oBlast.getOrientation() == orientation;
            boolean sEquals = oBlast.getSize().equals(size);
            boolean shooterEquals = oBlast.getShooter() == shooter;
            boolean superEquals = oBlast.isSuperBlast = isSuperBlast;
            
            return pEquals && oEquals && sEquals && shooterEquals && superEquals;
        }
        return false;
    }
    
    /**
     * Standard toString method
     * @return String that represents the Blast
     */
    @Override
    public String toString() {
        return "[Blast: " + position.toString() + " " + orientation + " " + size.toString() +
                " shooter: " + shooter + "]";
    }
}
