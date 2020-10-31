import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Queue;
import java.util.Random;
import java.util.Set;

import javax.swing.Timer;

/**
 * Keeps track of the state of the game when running
 * @author maxgoldman
 *
 */
public class GameplayModel {

    private Player player1; // Bottom player
    private Player player2; // Top player
    private Enemy[][] enemies;
    private Set<Blast> blasts;
    private int enemiesHit;
    
    private boolean player1HasShot;
    private boolean player2HasShot;
    
    private int rightEnemyX;
    private int leftEnemyX;
    private boolean shouldMove;
    private Orientation moveDirection;
    
    private ShooterType winner;
    
    private int scoreMultiplier;
    
    private Images images;
    private Sounds sounds;
    private boolean noMoreEnemyShotSounds;
    
    /**
     * Main constructor for GameplayModel, sets model for new game
     */
    public GameplayModel() {
        player1 = new Player(new PositionPoint(Consts.BOTTOM_PLAYER_START_POSITION), Orientation.UP, ShooterType.PLAYER1);
        player2 = new Player(new PositionPoint(Consts.TOP_PLAYER_START_POSITION), Orientation.DOWN, ShooterType.PLAYER2);
        this.enemies = new Enemy[Consts.ENEMIES_WIDE][Consts.ENEMIES_HIGH];
        this.blasts = new HashSet<Blast>();
        this.resetEnemies();
        this.enemiesHit = 0;
        
        this.player1HasShot = false;
        this.player2HasShot = false;
        
        this.leftEnemyX = enemies[0][0].getPosition().getX();
        this.rightEnemyX = enemies[Consts.ENEMIES_WIDE - 1][0].getPosition().getX();
        this.shouldMove = false;
        this.moveDirection = Orientation.RIGHT;
        
        this.winner = null;
        
        images = new Images();
        sounds = new Sounds();
        noMoreEnemyShotSounds = false;
        
        //Timer tells model when enemies should move
        Timer timer = new Timer(Consts.ENEMY_START_MOVE_INTERVAL, null);
        timer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                shouldMove = true;
                int newInterval = Consts.ENEMY_START_MOVE_INTERVAL - (Math.min(enemiesHit, 250) / 50) * 160;
                if(timer.getDelay() != newInterval) {
                    timer.setDelay(newInterval);
                    scoreMultiplier++;
                }
            }
        });
        timer.setInitialDelay(Consts.ENEMY_START_MOVE_INTERVAL);
        timer.start();
        
        scoreMultiplier = 1;
    }
    
    /**
     * Resets enemies to beginning configuration
     */
    private void resetEnemies() {
        for(int i = 0; i < Consts.ENEMIES_WIDE; i++) {
            for(int j = 0; j < Consts.ENEMIES_HIGH; j++) {
                int x = Consts.ENEMY_X_MIN + i * Consts.ENEMY_X_SPACE + Consts.ENEMY_X_SPACE / 2 - Consts.ENEMY_WIDTH / 2;
                int y = Consts.ENEMY_Y_MIN + j * Consts.ENEMY_Y_SPACE  + Consts.ENEMY_Y_SPACE / 2 - Consts.ENEMY_HEIGHT / 2;
                PositionPoint p = new PositionPoint(x, y);
                if(j < Consts.ENEMIES_HIGH / 2) {
                    enemies[i][j] = new Enemy(p, Orientation.DOWN);
                }
                else {
                    enemies[i][j] = new Enemy(p, Orientation.UP);
                }
            }
        }
    }
    
    private void resetPlayerPositions() {
        player1.setPosition(new PositionPoint(Consts.BOTTOM_PLAYER_START_POSITION));
        player2.setPosition(new PositionPoint(Consts.TOP_PLAYER_START_POSITION));
    }
    
    /**
     * Updates all elements of the game over the time period given
     * @param milliseconds time to advance 
     * @param actionQueue queue of the UserActions that have occured since the last update
     * being processed
     */
     public void advance(int milliseconds, Queue<UserAction> actionQueue) {
        for(UserAction action: actionQueue) {
            respondToAction(action);
        }
        
        Set<Blast> blastsToRemove = new HashSet<Blast>();
        //Move blasts
        for(Blast b: blasts) {
            boolean shouldDelete;
            ShooterType shooter = b.getShooter();
            if(shooter == ShooterType.PLAYER1 || shooter == ShooterType.PLAYER2) {
                shouldDelete = !b.moveBlast((int) (Consts.PLAYER_BLAST_SPEED * (milliseconds / 1000.0)));
            }
            else {
                shouldDelete = !b.moveBlast((int) (Consts.ENEMY_BLAST_SPEED * (milliseconds / 1000.0)));
            }
            if(shouldDelete) {
                blastsToRemove.add(b);
                if(shooter == ShooterType.PLAYER1) {
                    player1HasShot = false;
                }
                else if(shooter == ShooterType.PLAYER2) {
                    player2HasShot = false;
                }
            }
        
        }
        for(Blast b: blastsToRemove) {
            blasts.remove(b);
        }
        
        //Move players
        switch(player1.orientation) {
            case RIGHT:
                player1.changePosition((int) (Consts.PLAYER_MOVE_SPEED * (milliseconds / 1000.0)), 0);
                break;
            case LEFT:
                player1.changePosition((int) (Consts.PLAYER_MOVE_SPEED * (milliseconds / -1000.0)), 0);
                break;
            default:
                //Do nothing
        }
        switch(player2.orientation) {
        case RIGHT:
            player2.changePosition((int) (Consts.PLAYER_MOVE_SPEED * (milliseconds / 1000.0)), 0);
            break;
        case LEFT:
            player2.changePosition((int) (Consts.PLAYER_MOVE_SPEED * (milliseconds / -1000.0)), 0);
            break;
        default:
            //Do nothing
        }
        
        //Check for collisions
        blastsToRemove.clear();
        boolean shouldWipeBlasts = false;
        for(Blast b: blasts) {
            if(b.shouldDamage(player1)) {
                sounds.playSound("player_hit", false);
                blastsToRemove.add(b);
                player1.dropLife();
                if(player1.getLives() == 0) {
                    sounds.stopAllSounds();
                    winner = ShooterType.PLAYER2;
                }
                player2HasShot = false;
                resetPlayerPositions();
                resetEnemies();
                shouldWipeBlasts = true;
            }
            else if(b.shouldDamage(player2)) {
                sounds.playSound("player_hit", false);
                blastsToRemove.add(b);
                player2.dropLife();
                if(player2.getLives() == 0) {
                    sounds.stopAllSounds();
                    winner = ShooterType.PLAYER1;
                }
                player1HasShot = false;
                resetPlayerPositions();
                resetEnemies();
                shouldWipeBlasts = true;
            }
            else {
                for(int i = 0; i < Consts.ENEMIES_WIDE; i++) {
                    for(int j = 0; j < Consts.ENEMIES_HIGH; j++) {
                        if(b.shouldDamage(enemies[i][j])) {
                            enemiesHit++;
                            if(b.getShooter() == ShooterType.PLAYER1) {
                                player1HasShot = false;
                                player1.changeScore(10 * scoreMultiplier);
                            }
                            else if(b.getShooter() == ShooterType.PLAYER2) {
                                player2HasShot = false;
                                player2.changeScore(10 * scoreMultiplier);
                            }
                            if(b.isSuper()){
                                for(int l = Math.max(0, i - 1); l <= Math.min(i + 1, Consts.ENEMIES_WIDE - 1); l++) {
                                    for(int m = Math.max(0, j - 1); m <= Math.min(j + 1, Consts.ENEMIES_HIGH - 1); m++) {
                                        if(b.getShooter() == ShooterType.PLAYER1) {
                                            enemies[l][m].setOrientation(Orientation.UP);
                                        }
                                        else {
                                            enemies[l][m].setOrientation(Orientation.DOWN);
                                        }
                                    }
                                }
                            }
                            else {
                                enemies[i][j].flipOrientation();
                            }
                            
                            blastsToRemove.add(b);
                            sounds.playSound("enemy_hit", false);
                            
                        }
                    }
                }
            }
        }
        if(shouldWipeBlasts) {
            player1HasShot = false;
            player2HasShot = false;
            blasts.clear();
        }
        else {
            for(Blast b: blastsToRemove) {
                blasts.remove(b);
            }
        }
        
        //Move enemies sideways
        if(shouldMove) {
            this.leftEnemyX = enemies[0][0].getPosition().getX();
            this.rightEnemyX = enemies[Consts.ENEMIES_WIDE - 1][0].getPosition().getX();
            if(this.leftEnemyX - Consts.ENEMY_MOVE_DISTANCE <= 0) {
                this.moveDirection = Orientation.RIGHT;
            }
            else if(this.rightEnemyX + Consts.ENEMY_WIDTH + Consts.ENEMY_MOVE_DISTANCE >= Consts.GAME_WIDTH) {
                this.moveDirection = Orientation.LEFT;
            }
            
            int directionMultiplier = 1;
            if(this.moveDirection == Orientation.LEFT) {
                directionMultiplier = -1;
            }
            for(int i = 0; i < Consts.ENEMIES_WIDE; i++) {
                for(int j = 0; j < Consts.ENEMIES_HIGH; j++) {
                    enemies[i][j].changePosition(Consts.ENEMY_MOVE_DISTANCE * directionMultiplier, 0);
                }
            }
            sounds.playSound("enemy_move", false);
            this.shouldMove = false;
        }
        
        //Randomly decide if two enemies will switch places
        Random rand = new Random();
        boolean shouldSwitch = 1 == rand.nextInt(10);
        if(shouldSwitch) {
            int i1 = rand.nextInt(Consts.ENEMIES_WIDE);
            int j1 = rand.nextInt(Consts.ENEMIES_HIGH);
            int i2 = rand.nextInt(Consts.ENEMIES_WIDE);
            int j2 = rand.nextInt(Consts.ENEMIES_HIGH);
            
            if(enemies[i1][j1].getOrientation() != enemies[i2][j2].getOrientation()) {
                PositionPoint temp = enemies[i1][j1].getPosition();
                enemies[i1][j1].setPosition(enemies[i2][j2].getPosition());
                enemies[i2][j2].setPosition(temp);
                
                Enemy tempE = enemies[i1][j1];
                enemies[i1][j1] = enemies[i2][j2];
                enemies[i2][j2] = tempE;
            }
        }
        
        //Randomly decide if enemies will shoot
        double enemiesShotMultiplier = Math.min(100, enemiesHit) / 100.0;
        int topBound = 10 + (int) (enemiesShotMultiplier * 140.0);
        boolean shouldShoot = rand.nextInt(1000) < topBound;
        if(shouldShoot) {
            int i = rand.nextInt(Consts.ENEMIES_WIDE);
            int j = rand.nextInt(Consts.ENEMIES_HIGH);
            blasts.add(Blast.blastFromObject(enemies[i][j], enemies[i][j].getOrientation(), false));
            if(enemiesHit >= 85 && !noMoreEnemyShotSounds) {
                sounds.playSound("enemy_too_many_shots", true);
                noMoreEnemyShotSounds = true;
            }
            else if(enemiesHit < 85) {
                sounds.playSound("enemy_shot", false);
            }
        }
    }
    
    /**
     * Receives and processes a UserAction 
     * @param a the action to respond to
     */
     private void respondToAction(UserAction a) {
        switch(a) {
            case p1StartMoveRight:
                if(player1.getOrientation() == Orientation.UP) {
                    player1.setOrientation(Orientation.RIGHT);
                }
                break;
            case p2StartMoveRight:
                if(player2.getOrientation() == Orientation.DOWN) {
                    player2.setOrientation(Orientation.RIGHT);
                }
                break;
            case p1EndMoveRight:
                if(player1.getOrientation() == Orientation.RIGHT) {
                    player1.setOrientation(Orientation.UP);
                }
                break;
            case p2EndMoveRight:
                if(player2.getOrientation() == Orientation.RIGHT) {
                    player2.setOrientation(Orientation.DOWN);
                }
                break;
            case p1StartMoveLeft:
                if(player1.getOrientation() == Orientation.UP) {
                    player1.setOrientation(Orientation.LEFT);
                }
                break;
            case p2StartMoveLeft:
                if(player2.getOrientation() == Orientation.DOWN) {
                    player2.setOrientation(Orientation.LEFT);
                }
                break;
            case p1EndMoveLeft:
                if(player1.getOrientation() == Orientation.LEFT) {
                    player1.setOrientation(Orientation.UP);
                }
                break;
            case p2EndMoveLeft:
                if(player2.getOrientation() == Orientation.LEFT) {
                    player2.setOrientation(Orientation.DOWN);
                }
                break;
            case p1Shoot:
                if(!player1HasShot) {
                    Random rand = new Random();
                    boolean superBlast = rand.nextInt(5) == 1;
                    blasts.add(Blast.blastFromObject(player1, Orientation.UP, superBlast));
                    player1HasShot = true;
                    player1.addShot();
                    sounds.playSound("player_shot", false);
                }
                break;
            case p2Shoot:
                if(!player2HasShot) {
                    Random rand = new Random();
                    boolean superBlast = rand.nextInt(5) == 1;
                    blasts.add(Blast.blastFromObject(player2, Orientation.DOWN, superBlast));
                    player2HasShot = true;
                    player2.addShot();
                    sounds.playSound("player_shot", false);
                }
                break;
            case CheatAllUp:
                for(int i = 0; i < Consts.ENEMIES_WIDE; i++) {
                    for(int j = 0; j < Consts.ENEMIES_HIGH; j++) {
                        enemies[i][j].setOrientation(Orientation.UP);
                    }
                }
                break;
            case CheatAllDown:
                for(int i = 0; i < Consts.ENEMIES_WIDE; i++) {
                    for(int j = 0; j < Consts.ENEMIES_HIGH; j++) {
                        enemies[i][j].setOrientation(Orientation.DOWN);
                    }
                }
                break;
            case CheatReset:
                resetEnemies();
                break;
                
            default:
                //Nothing to do for other actions
        }
    }
    
    /**
     * Gets a set of sprites for the view to render to represent the model
     * @return the set of sprites
     */
    public Set<Sprite> getSprites(){
        Set<Sprite> sprites = new HashSet<Sprite>();
        
        //Add players
        sprites.add(new Sprite(player1, images));
        sprites.add(new Sprite(player2, images));
        
        //Add blasts
        for(Blast b: blasts) {
            sprites.add(new Sprite(b, images));
        }
        
        //Add enemies
        for(int i = 0; i < Consts.ENEMIES_WIDE; i++) {
            for(int j = 0; j < Consts.ENEMIES_HIGH; j++) {
                sprites.add(new Sprite(enemies[i][j], images));
            }
        }
        
        return sprites;
    }
    
    /**
     * Getter method for player1's score
     * @return the score
     */
    public int getPlayer1Score() {
        return player1.getScore();
    }
    
    /**
     * Getter method for player1's life total
     * @return player1's life total
     */
    public int getPlayer1Lives() {
        return player1.getLives();
    }
    
    /**
     * Getter method for player2's score
     * @return the score
     */
    public int getPlayer2Score() {
        return player2.getScore();
    }
    
    /**
     * Getter method for player2's life total
     * @return player2's life total
     */
    public int getPlayer2Lives() {
        return player2.getLives();
    }
    
    /**
     * Getter method for the amount of enemies hit
     * @return  number of enemies hit
     */
    public int getEnemiesHit() {
        return enemiesHit;
    }
    
    /**
     * Returns winner of the game if someone has won
     * @return ShooterType of winner if there is a winner, null otherwise 
     * (will never return ShooterType.ENEMY)
     */
    public ShooterType checkWinner() {
        if(winner == ShooterType.ENEMY) {
            throw new IllegalStateException("enemy cannot win");
        }
        return winner;
    }
    
    /**
     * Stops all sounds currently being played by the game
     */
    public void stopAllCurrentSounds() {
        sounds.stopAllSounds();
    }
    
}
