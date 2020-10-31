import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

/**
 * Handles different actions sent by views
 * @author maxgoldman
 *
 */
@SuppressWarnings("serial")
public class KeyPressAction extends AbstractAction {

    private UserAction a;
    private GameView gv;
    
    private Orientation o;
    private MenuView mv;
    
    private ViewController c;
    
    private boolean goBackToMenu;
    
    /**
     * Creates a KeyPressAction coming from a GameView
     * @param a UserAction the press represents
     * @param v sending GameView
     * @param c overarching ViewController
     */
    public KeyPressAction(UserAction a, GameView v, ViewController c) {
        this.a = a;
        this.gv = v;
        this.o = null;
        this.mv = null;
        this.c = c;
    }
    
    /**
     * Creates a KeyPressAction coming from a MenuView
     * @param o orientation that the menu selection moved
     * @param v sending MenuView
     * @param c overarching ViewController
     */
    public KeyPressAction(Orientation o, MenuView v, ViewController c) {
        this.a = null;
        this.gv = null;
        this.o = o;
        this.mv = v;
        this.c = c;
    }
    
    /**
     * Creates a KeyPressAction to return to a MenuView
     * @param c overarching ViewController
     * @param back true if intention is to return to MenuView
     */
    public KeyPressAction(ViewController c, boolean back) {
        this.c = c;
        this.goBackToMenu = back;
    }
    
    /**
     * Reacts when an action is performed
     * @param e event to respond to
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(a != null) {
            if(a == UserAction.Back) {
                c.setGameState(GameState.MENU);
                c.playSound("menu_select", false);
            }
            else {
                gv.addAction(a);
            }
        }
        else if(goBackToMenu) {
            c.setGameState(GameState.MENU);
            c.playSound("menu_select", false);
        }
        else {
            if(o == Orientation.DOWN) {
                mv.downMenu();
                c.playSound("menu_move", false);
            }
            else if(o == Orientation.UP){
                mv.upMenu();
                c.playSound("menu_move", false);
            }
            else {
                c.playSound("menu_select", false);
                switch(mv.getCurrentSelected()) {
                    case HELP:
                        c.setGameState(GameState.HELP);
                        break;
                    case QUIT:
                        System.exit(0);
                        break;
                    case NEW:
                        c.setGameState(GameState.GAME);
                        break;
                    case HIGHSCORES:
                        c.setGameState(GameState.HIGHSCORES);
                }
            }
        }
    }
}
