import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import javax.swing.JPanel;

/**
 * View that handles the displaying of the game
 * @author maxgoldman
 *
 */
@SuppressWarnings("serial")
public class GameView extends JPanel implements View{
    
    private GameplayModel model;
    private Queue<UserAction> newActions;
    
    private Images images;
    
    /**
     * No argument constructor creates GameView and creates the GameplayModel
     */
    public GameView() {
        super();
        this.setSize(Consts.GAME_VIEW_SIZE);
        model = new GameplayModel();
        this.setBounds(0, 0, Consts.GAME_WIDTH, Consts.GAME_HEIGHT);
        newActions = new LinkedList<UserAction>();
        this.setBackground(Color.BLACK);
        this.setFont();
        images = new Images();
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
     * Adds UserAction to the queue for the next update
     * @param a the UserAction to be added
     */
    public void addAction(UserAction a) {
        newActions.add(a);
    }
    
    /**
     * Handles the display of all parts of the view
     * @param The relevant Graphics object
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        model.advance(Consts.VIEW_UPDATE_INTERVAL, newActions);
        newActions.clear();
        Set<Sprite> newSprites = model.getSprites();
        if(newSprites == null) {
            return;
        }
        for(Sprite s: newSprites) {
            s.offset(Consts.X_OFFSET, Consts.Y_OFFSET);
            s.paint(g);
        }
        g.setColor(Consts.TEXT_COLOR);
        g.setFont(new Font("Joystix", Font.PLAIN, 30)); 
        g.drawString("Score: " + model.getPlayer2Score(), 10, 35);
        g.drawString("Score: " + model.getPlayer1Score(), 10, Consts.GAME_VIEW_HEIGHT - 15);
        
        g.drawLine(0, Consts.Y_OFFSET, Consts.GAME_VIEW_WIDTH, Consts.Y_OFFSET);
        g.drawLine(0, Consts.GAME_VIEW_HEIGHT - Consts.Y_OFFSET, Consts.GAME_VIEW_WIDTH, Consts.GAME_VIEW_HEIGHT - Consts.Y_OFFSET);
        
        for(int i = 0; i < model.getPlayer2Lives() - 1; i++) {
            g.drawImage(images.getImage("p2Sprite"), (Consts.GAME_VIEW_WIDTH - Consts.PLAYER_WIDTH - 20) - (int) (i * Consts.PLAYER_WIDTH * 1.2), 10, null);
        }
        for(int i = 0; i < model.getPlayer1Lives() - 1; i++) {
            int x = (Consts.GAME_VIEW_WIDTH - Consts.PLAYER_WIDTH - 20) - (int) (i * Consts.PLAYER_WIDTH * 1.2);
            int y = Consts.GAME_VIEW_HEIGHT - Consts.PLAYER_HEIGHT - 10;
            g.drawImage(images.getImage("p1Sprite"), x, y, null);
        }
    }
    
    /**
     * Returns the GameplayModel this view represents
     * @return the model
     */
    public GameplayModel getModel() {
        return model;
    }
    
    /**
     * Gets the preferred size of the view
     * @return Dimension representing the preferred size of the view
     */
    @Override
    public Dimension getPreferredSize() {
        return Consts.GAME_VIEW_SIZE;
    }
    
    /**
     * Stops all sounds created by the view
     */
    @Override
    public void stopSound() {
        model.stopAllCurrentSounds();
    }
    
}
