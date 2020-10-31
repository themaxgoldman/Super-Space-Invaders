import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

/**
 * Keeps track of high score view
 * @author maxgoldman
 *
 */
@SuppressWarnings("serial")
public class HighScoreView extends JPanel implements View {
    
    Images images;
    private List<HighScore> highScores;
    
    /**
     * Instantiates a new HighScoreView
     */
    public HighScoreView() {
        images = new Images();
        try{
            HighScoreIO hsio = new HighScoreIO(Consts.HIGH_SCORE_FILEPATH);
            highScores = hsio.getScores();
        } catch(IOException e) {
            highScores = new ArrayList<HighScore>();
        }
        setFont();
    }
    
    /**
     * Handles the display of all parts of the view
     * @param g The relevant Graphics object
     */
    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(images.getImage("blank_screen"), 0, 0, null);
        highScores.sort(new HighScore());
        int max = Math.min(highScores.size(), Consts.SCORES_TO_DISPLAY);
        boolean switcher = true;
        
        g.setColor(Consts.TEXT_COLOR);
        g.setFont(new Font("Joystix", Font.PLAIN, 32));
        g.drawString("Nickname: ", Consts.HN_TYPE_START_X, Consts.H_TYPE_START_Y);
        g.drawString("Score: ", Consts.HS_TYPE_START_X, Consts.H_TYPE_START_Y);
        
        for(int i = 0; i < max; i++) {
            String nickname = highScores.get(i).nickname;
            String score = Integer.toString(highScores.get(i).score);
            
            g.setFont(new Font("Joystix", Font.PLAIN, 28));
            if(switcher) {
                g.setColor(new Color(38, 255, 0));
            }
            else {
                g.setColor(Consts.TEXT_COLOR);
            }
            switcher = !switcher;
            
            g.drawString(nickname, Consts.HN_TYPE_START_X , Consts.H_TYPE_START_Y + 40 * (i + 1));
            g.drawString(score, Consts.HS_TYPE_START_X , Consts.H_TYPE_START_Y + 40 * (i + 1));
        }
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
            //Just use default font
        }
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
     * Stops all sounds created by the view
     */
    @Override
    public void stopSound() {
        //Doesn't play sound
    }
}
