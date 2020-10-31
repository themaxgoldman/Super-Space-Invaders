import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.KeyStroke;
import javax.swing.Timer;

/**
 * Handles views
 * @author maxgoldman
 *
 */
public class ViewController {
    
    private Sounds sounds;
    private JFrame frame;
    
    private View currentView;
    
    private ShooterType gameWinner;
    private int winningScore;
    
    /**
     * Creates a new View Controller, and all subsequent states
     */
    public ViewController() {
        gameWinner = null;
        sounds = new Sounds();
        
        
        frame = new JFrame("Super Space Invaders");
        frame.setLocation(100, 100);
        frame.setBackground(Color.BLACK);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        

        sounds.playSound("background", true);
        gameWinner = ShooterType.PLAYER2;
        setGameState(GameState.MENU);
        frame.setVisible(true);
        
        winningScore = -1;
    }
    
    /**
     * Switches the game state
     * @param s the state to switch the game state to
     */
    public void setGameState(GameState s) {
        switch(s) {
            case MENU:
                if(currentView != null) {
                    currentView.stopSound();
                }
                MenuView menu = new MenuView(MainOption.NEW);
                currentView = menu;
                setInputMap(menu.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW));
                setActionMapForMenu(menu);
                frame.getContentPane().removeAll();
                frame.add(menu);
                frame.pack();
                frame.revalidate();
                break;
            case GAME:
                if(currentView != null) {
                    currentView.stopSound();
                }
                GameView game = new GameView();
                currentView = game;
                setInputMap(game.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW));
                setActionMapForGame(game);
                frame.getContentPane().removeAll();
                frame.add(game);
                frame.pack();
                Timer gameOverTimer = new Timer(Consts.VIEW_UPDATE_INTERVAL, null); 
                gameOverTimer.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        game.repaint();
                        GameplayModel m = game.getModel();
                        ShooterType winner = m.checkWinner();
                        if(winner != null) {
                            gameWinner = winner;
                            gameOverTimer.stop();
                            winningScore = m.getPlayer1Score() + m.getPlayer2Score();
                            setGameState(GameState.WINNER);
                        }
                    }
                });
                gameOverTimer.start();
                break;
            case HELP:
                if(currentView != null) {
                    currentView.stopSound();
                }
                HelpView help = new HelpView();
                currentView = help;
                setInputMap(help.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW));
                setActionMapForHelp(help);
                frame.getContentPane().removeAll();
                frame.add(help);
                frame.pack();
                break;
            case WINNER:
                if(currentView != null) {
                    currentView.stopSound();
                }
                WinnerView winner = new WinnerView(gameWinner, winningScore);
                currentView = winner;
                frame.getContentPane().removeAll();
                frame.add(winner);
                frame.pack();
                winner.setFocusable(true);
                winner.requestFocus();
                Timer nicknameEnteredTimer = new Timer(Consts.VIEW_UPDATE_INTERVAL, null); 
                nicknameEnteredTimer.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if(winner.readyToSwitch()) {
                            nicknameEnteredTimer.stop();
                            setGameState(GameState.HIGHSCORES);
                        }
                    }
                });
                nicknameEnteredTimer.start();
                break;
            case HIGHSCORES:
                if(currentView != null) {
                    currentView.stopSound();
                }
                HighScoreView highscore = new HighScoreView();
                currentView = highscore;
                setInputMap(highscore.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW));
                setActionMapForHighScores(highscore);
                frame.getContentPane().removeAll();
                frame.add(highscore);
                frame.pack();
                break;
        }
        
    }
    
    /**
     * Plays a sound
     * @param name name of the sound to play
     * @param loop whether or not the sound should loop indefinitely
     */
    public void playSound(String name, boolean loop) {
        sounds.playSound(name, loop);
    }
    
    /**
     * Sets the given input map for the controls the game needs
     * @param map the input map to reset
     */
    private void setInputMap(InputMap map) {
        map.clear();
        map.put(KeyStroke.getKeyStroke("pressed DOWN"), "downP");
        map.put(KeyStroke.getKeyStroke("pressed UP"), "upP");
        map.put(KeyStroke.getKeyStroke("pressed LEFT"), "leftP");
        map.put(KeyStroke.getKeyStroke("released LEFT"), "leftR");
        map.put(KeyStroke.getKeyStroke("pressed RIGHT"), "rightP");
        map.put(KeyStroke.getKeyStroke("released RIGHT"), "rightR");
        map.put(KeyStroke.getKeyStroke("pressed A"), "aP");
        map.put(KeyStroke.getKeyStroke("released A"), "aR");
        map.put(KeyStroke.getKeyStroke("pressed S"), "sP");
        map.put(KeyStroke.getKeyStroke("pressed D"), "dP");
        map.put(KeyStroke.getKeyStroke("released D"), "dR");
        map.put(KeyStroke.getKeyStroke("released L"), "lR");
        map.put(KeyStroke.getKeyStroke("released J"), "jR");
        map.put(KeyStroke.getKeyStroke("released K"), "kR");
        map.put(KeyStroke.getKeyStroke("released M"), "mR");
        map.put(KeyStroke.getKeyStroke("pressed ENTER"), "enterP");
        map.put(KeyStroke.getKeyStroke("pressed SPACE"), "spaceP");
        map.put(KeyStroke.getKeyStroke("pressed ESCAPE"), "escapeP");
    }
    
    /**
     * Sets the action map to the game settings for the given view
     * @param view the GameView for which the action map will be set
     */
    private void setActionMapForGame(GameView view) {
        ActionMap map = view.getActionMap();
        map.clear();
        map.put("downP", new KeyPressAction(UserAction.p1Shoot, view, this));
        map.put("leftP", new KeyPressAction(UserAction.p1StartMoveLeft, view, this));
        map.put("leftR", new KeyPressAction(UserAction.p1EndMoveLeft, view, this));
        map.put("rightP", new KeyPressAction(UserAction.p1StartMoveRight, view, this));
        map.put("rightR", new KeyPressAction(UserAction.p1EndMoveRight, view, this));
        map.put("aP", new KeyPressAction(UserAction.p2StartMoveLeft, view, this));
        map.put("aR", new KeyPressAction(UserAction.p2EndMoveLeft, view, this));
        map.put("sP", new KeyPressAction(UserAction.p2Shoot, view, this));
        map.put("dP", new KeyPressAction(UserAction.p2StartMoveRight, view, this));
        map.put("dR", new KeyPressAction(UserAction.p2EndMoveRight, view, this));
        map.put("lR", new KeyPressAction(UserAction.CheatAllUp, view, this));
        map.put("jR", new KeyPressAction(UserAction.CheatAllDown, view, this));
        map.put("kR", new KeyPressAction(UserAction.CheatReset, view, this));
        map.put("escapeP", new KeyPressAction(UserAction.Back, view, this));
    }
    
    /**
     * Sets the action map to the menu settings for the given view
     * @param view the MenuView for which the action map will be set
     */
    private void setActionMapForMenu(MenuView view) {
        ActionMap map = view.getActionMap();
        map.clear();
        map.put("downP", new KeyPressAction(Orientation.DOWN, view, this));
        map.put("upP", new KeyPressAction(Orientation.UP, view, this));
        map.put("enterP", new KeyPressAction(Orientation.RIGHT, view, this));
    }
    
    /**
     * Sets the action map to the help settings for the given view
     * @param view the HelpView for which the action map will be set
     */
    private void setActionMapForHelp(HelpView view) {
        ActionMap map = view.getActionMap();
        map.clear();
        map.put("spaceP", new KeyPressAction(this, true));
    }
    
    /**
     * Sets the action map the the highscore settings for the given view
     * @param view the HighScoreView for which the action map will be sit
     */
    private void setActionMapForHighScores(HighScoreView view) {
        ActionMap map = view.getActionMap();
        map.clear();
        map.put("spaceP", new KeyPressAction(this, true));
    }
}
