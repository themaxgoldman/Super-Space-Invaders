import java.awt.Dimension;

/**
 * Abstract GameObject class that all objects that affect gameplay extend
 * @author maxgoldman
 *
 */
public abstract class GameObject {
    
    protected PositionPoint position;
    protected Orientation orientation;
    protected Dimension size;
    
    /**
     * No argument constructor, set everything to default values
     */
    public GameObject() {
        this.position = new PositionPoint();
        this.orientation = Orientation.UP;
        this.size = new Dimension();
    }
    
    /**
     * Normal constructor for GameObject
     * @param p new position
     * @param o new orientation
     * @param s new size
     */
    public GameObject(PositionPoint p, Orientation o, Dimension s) {
        if(p == null || o == null || s == null) {
            throw new IllegalArgumentException("illegal null input");
        }
        this.position = new PositionPoint(p);
        this.orientation = o;
        this.size = new Dimension(s);
    }
    
    /**
     * Getter method for position (from top left corner)
     * @return position
     */
    public PositionPoint getPosition() {
        return new PositionPoint(this.position);
    }
    
    /**
     * Setter method for position (from top left corner)
     * @param p new position
     */
    public void setPosition(PositionPoint p) {
        if(p == null) {
            throw new IllegalArgumentException("can't set to null position");
        }
        this.position = new PositionPoint(p);
    }
    
    /**
     * Changes position (from top left corner), clips 
     * new position if necessary
     * @param dx change in x
     * @param dy change in y
     */
    public void changePosition(int dx, int dy) {
        this.position.translate(dx, dy);
    }
    
    /** 
     * Getter method for orientation
     * @return orientation
     */
    public Orientation getOrientation() {
        return this.orientation;
    }
    
    /**
     * Setter method for orientation
     * @param o new orientation
     */
    public void setOrientation(Orientation o) {
        if(o == null) {
            throw new IllegalArgumentException("can't set to null orientation");
        }
        this.orientation = o;
    }
    
    /**
     * Getter method for size
     * @return size
     */
    public Dimension getSize() {
        return new Dimension(this.size);
    }
    
    /**
     * Determines if two GameObjects are intersecting one another
     * @param other the other GameObject to be checking against
     * @return true if objects are intersecting, false otherwise
     */
    public boolean intersects(GameObject other) {
        if(other == null) {
            throw new IllegalArgumentException("can't intersect with null object");
        }
        class BoundingBox {
            public int xMin;
            public int yMin;
            public int xMax;
            public int yMax;
            
            public BoundingBox(PositionPoint p, int width, int height) {
                this.xMin = p.getX();
                this.yMin = p.getY();
                this.xMax = xMin + width;
                this.yMax = yMin + height;
            }
        }
        
        BoundingBox box1 = new BoundingBox(this.position, this.size.width, this.size.height);
        BoundingBox box2 = new BoundingBox(other.position, other.getSize().width, other.getSize().height);
        
        boolean xIntersect = box1.xMin <= box2.xMax && box1.xMax >= box2.xMin;
        boolean yIntersect = box1.yMin <= box2.yMax && box1.yMax >= box2.yMin;
        
        return xIntersect && yIntersect;
    }
}
