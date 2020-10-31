/**
 * Enum keeps track of the type of shooter for a blast
 * @author maxgoldman
 *
 */
public enum ShooterType {
    PLAYER1, PLAYER2, ENEMY;
    
    /**
    * Returns the type of shooter from a GameObject
    * @param o GameObject to convert
    * @return ShooterType of the GameObject
    */
    public static ShooterType typeFromGameObject(GameObject o) {
        if(o instanceof Enemy) {
            return ENEMY;
        }
        if(o instanceof Player ) {
            return ((Player) o).getType();
        }
        throw new IllegalArgumentException("no ShooterType for this object");
    }
}
