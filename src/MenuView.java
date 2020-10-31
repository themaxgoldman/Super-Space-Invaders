import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * Keeps track of a MenuView and its state
 * @author maxgoldman
 *
 */
@SuppressWarnings("serial")
public class MenuView extends JPanel implements View{

    private MainOption currentSelected;
    private Images images;
    
    /**
     * Main constructor for MenuView
     * @param o current selection in MenuView
     */
    public MenuView(MainOption o) {
        currentSelected = o;
        images = new Images();
        this.setPreferredSize(new Dimension(Consts.GAME_WIDTH, 600));
    }
    
    /**
     * Getter method for currentSelected
     * @return currently selected option
     */
    public MainOption getCurrentSelected() {
        return currentSelected;
    }
    
    /**
     * Drops the currently selected menu item by 1
     */
    public void downMenu() {
        switch(currentSelected) {
            case NEW:
                currentSelected = MainOption.HIGHSCORES;
                break;
            case HIGHSCORES:
                currentSelected = MainOption.HELP;
                break;
            case HELP:
                currentSelected = MainOption.QUIT;
                break;
            case QUIT:
                currentSelected = MainOption.NEW;
                break;
        }
        this.repaint();
    }
    
    /**
     * Raises the currently selected menu item by 1
     */
    public void upMenu() {
        switch(currentSelected) {
            case NEW:
                currentSelected = MainOption.QUIT;
                break;
            case HIGHSCORES:
                currentSelected = MainOption.NEW;
                break;
            case HELP:
                currentSelected = MainOption.HIGHSCORES;
                break;
            case QUIT:
                currentSelected = MainOption.HELP;
                break;
        }
        this.repaint();
    }
    
    /**
     * Handles the display of all parts of the view
     * @param g The relevant Graphics object
     */
    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(images.getImage("main_" + currentSelected), 0, 0, null);
    }
    
    /**
     * Stops all sounds created by the view
     */
    @Override
    public void stopSound() {
        //Do nothing, doesn't play sounds itself
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
