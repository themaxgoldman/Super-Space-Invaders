/**
 * Keeps track of a position within the game model,
 * which maintains the game space as 10000 x 10000.
 * @author maxgoldman
 *
 */
public class PositionPoint {
    
    private int x;
    private int y;
    
    /**
     * No argument constructor defaults to (0,0)
     */
    public PositionPoint() {
        x = 0;
        y = 0;
    }
    
    /**
     * Constructor creates point from x and y values,
     * clips values if they are out of range
     * @param x x position
     * @param y y position
     */
    public PositionPoint(int x, int y) {
        this.x = clipWidth(x);
        this.y = clipHeight(y);
    }
    
    /**
     * Standard copy constructor
     * @param p PositionPoint to copy
     */
    public PositionPoint(PositionPoint p) {
        if(p == null) {
            throw new IllegalArgumentException("can't copy null");
        }
        this.x = p.getX();
        this.y = p.getY();
    }
    
    /**
     * Private clip method clips width to be within range
     * @param value value to be clipped
     * @return clipped value
     */
    private int clipWidth(int value) {
        if(value >= Consts.GAME_VIEW_WIDTH) {
            return Consts.GAME_VIEW_WIDTH;
        }
        if(value <= 0) {
            return 0;
        }
        return value;
    }
    
    /**
     * Private method clips height to be within range
     * @param value to be clipped
     * @return clipped value
     */
    private int clipHeight(int value) {
        if(value >= Consts.GAME_VIEW_HEIGHT) {
            return Consts.GAME_VIEW_HEIGHT;
        }
        if(value <= 0) {
            return 0;
        }
        return value;
    }
    
    /**
     * Getter method for point's x value
     * @return x value
     */
    public int getX() {
        return this.x;
    }
    
    /**
     * Setter method for point's x value
     * @param value new x value
     */
    public void setX(int value) {
        this.x = clipWidth(value);
    }
    
    /**
     * Getter method for point's y value
     * @return y value
     */
    public int getY() {
        return this.y;
    }
    
    /**
     * Setter method for point's y value
     * @param value new y value
     */
    public void setY(int value) {
        this.y = clipHeight(value);
    }
    
    /**
     * Translates the point, clips new value if out of range
     * @param dx change in x
     * @param dy change in y
     */
    public void translate(int dx, int dy) {
        this.x = clipWidth(this.x + dx);
        this.y = clipHeight(this.y + dy);
    }
    
    /**
     * Translates point in x direction
     * @param dx change in x
     */
    public void translateX(int dx) {
        this.translate(dx, 0);
    }
    
    /**
     * Translates point in y direction
     * @param dy change in y
     */
    public void translateY(int dy) {
        this.translate(0, dy);
    }
    
    /**
     * Gets the distance between two points
     * @param p1 first point
     * @param p2 second point
     * @return distance between the two points
     */
    public static int distance(PositionPoint p1, PositionPoint p2) {
        return (int) Math.sqrt(Math.pow(p1.getX() - p2.getX(), 2) + Math.pow(p1.getY() - p2.getY(), 2));
    }
    
    /**
     * Standard equals method
     * @param o object to check equality with
     * @return whether the two objects are equal
     */
    @Override
    public boolean equals(Object o) {
        if(o instanceof PositionPoint) {
            PositionPoint oPoint = (PositionPoint) o;
            return x == oPoint.getX() && y == oPoint.getY();
        }
        return false;
    }
    
    /**
     * Standard toString method
     * @return String that represents the Blast
     */
    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }
}
