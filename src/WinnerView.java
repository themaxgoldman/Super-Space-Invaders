import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JPanel;

/**
 * Keeps track of view for nickname entry
 * @author maxgoldman
 *
 */
@SuppressWarnings("serial")
public class WinnerView extends JPanel implements View{
    
    private Images images;
    private ShooterType winner;
    
    private String currentNickname;
    private int winningScore;
    
    Sounds sounds;
    
    private boolean highScoreEntered;
    /**
     * Creates a view to enter high score
     * @param winner winner the winner of the game
     * @param winnerScore score of the game winner
     */
    public WinnerView(ShooterType winner, int winnerScore) {
        if(winner == ShooterType.ENEMY) {
            throw new IllegalArgumentException("enemy can't win the game");
        }
        if(winner == null) {
            throw new IllegalArgumentException("null can't win the game");
        }
        images = new Images();
        this.setPreferredSize(Consts.GAME_SIZE);
        this.winner = winner;
        
        highScoreEntered = false;
        
        currentNickname = "";
        setFocusable(true);
        
        WinnerView self = this;
        this.addKeyListener(new KeyListener (){
            public void keyPressed(KeyEvent e) {
                //Do nothing, using typed
            }
            public void keyReleased(KeyEvent e) {
                //Do nothing, using typed
            }
            public void keyTyped(KeyEvent e) {
                if(e.getKeyChar() == KeyEvent.VK_BACK_SPACE && currentNickname.length() != 0){
                    currentNickname = currentNickname.substring(0, currentNickname.length() - 1);
                    sounds.playSound("menu_move", false);
                }
                else if(e.getKeyChar() == KeyEvent.VK_ENTER) {
                    try{
                        HighScoreIO hsio = new HighScoreIO(Consts.HIGH_SCORE_FILEPATH);
                        hsio.addHighScore(new HighScore(currentNickname, winningScore));
                        highScoreEntered = true;
                        sounds.playSound("menu_select", false);
                    } catch(IOException ex) {
                        //Don't add this score
                    }
                }
                else if(e.getKeyChar() == KeyEvent.VK_SEMICOLON) {
                    sounds.playSound("menu_select", false);
                }
                else if(currentNickname.length() < 16){
                    currentNickname = currentNickname + e.getKeyChar();
                    sounds.playSound("menu_move", false);
                }
                self.repaint();
            }
        });
        winningScore = winnerScore;
        
        setFont();
        sounds = new Sounds();
    }
    
    /**
     * Handles the display of all parts of the view
     * @param g The relevant Graphics object
     */
    @Override
    public void paintComponent(Graphics g) {
        switch(winner) {
            case PLAYER1:
                g.drawImage(images.getImage("bottom_wins"), 0, 0, null);
                break;
            case PLAYER2:
                g.drawImage(images.getImage("top_wins"), 0, 0, null);
                break;
            default:
                throw new IllegalStateException("enemy can't win the game");
        }
        g.setColor(Consts.TEXT_COLOR);
        g.setFont(new Font("Joystix", Font.PLAIN, 18)); 
        g.drawString(currentNickname, Consts.W_TYPE_START_X, Consts.W_TYPE_START_Y);
        
        g.setFont(new Font("Joystix", Font.PLAIN, 30)); 
        String scoreLine = winningScore + " POINTS";
        int scoreLineWidth = g.getFontMetrics().stringWidth(scoreLine);
        int scoreLineX = (Consts.GAME_WIDTH / 2) - (scoreLineWidth / 2);
        g.drawString(scoreLine, scoreLineX, 310);
    }
    
    /**
     * Gets the preferred size of the view
     * @return Dimension representing the preferred size of the view
     */
    @Override
    public Dimension getPreferredSize() {
        return Consts.NON_GAME_VIEW_SIZE;
    }
    
    /**
     * Imports the Joystix font so it can be used in the game
     */
    private void setFont() {
        try {
            File fontFile = new File(Consts.FONT_PATH);
            Font font = Font.createFont(Font.TRUETYPE_FONT, fontFile);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(font);
            
        } catch(Exception e) {
            //Use system fonts
        }
    }
    
    /**
     * Stops all sounds created by the view
     */
    @Override
    public void stopSound() {
        //Doesn't play any sounds
    }
    
    /**
     * Tells whether the view is ready to exit
     * @return whether or not a high score has been entered
     */
    public boolean readyToSwitch() {
        return highScoreEntered;
    }
}

