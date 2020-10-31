import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * View that handles the displaying of the help screen
 * @author maxgoldman
 *
 */
@SuppressWarnings("serial")
public class HelpView extends JPanel implements View{
    
    private Images images;
    
    /**
     * Contructor creates a standard HelpView
     */
    public HelpView() {
        images = new Images();
        this.setPreferredSize(Consts.GAME_SIZE);
    }
    
    /**
     * Handles the display of all parts of the view
     * @param g The relevant Graphics object
     */
    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(images.getImage("help"), 0, 0, null);
    }
    
    /**
     * Stops all sounds created by the view,
     */
    @Override
    public void stopSound() {
        //Doesn't play any sounds
    }
    
    /**
     * Gets the preferred size of the view
     * @return Dimension representing the preferred size of the view
     */
    @Override
    public Dimension getPreferredSize() {
        return Consts.NON_GAME_VIEW_SIZE;
    }
}
