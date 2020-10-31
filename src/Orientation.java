
/**
 * Enum to keep track of the orientation of a GameObject
 * @author maxgoldman
 *
 */
public enum Orientation {
    UP, DOWN, LEFT, RIGHT;
    
    private Orientation flipped;
    
    static {
        UP.flipped = DOWN;
        DOWN.flipped = UP;
        LEFT.flipped = RIGHT;
        RIGHT.flipped = LEFT;
    }
    
    /**
     * Returns the flipped (opposite orientation)
     * @return flipped orientation
     */
    public Orientation getflipped() {
        return flipped;
    }
}
