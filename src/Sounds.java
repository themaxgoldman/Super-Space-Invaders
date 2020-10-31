import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;

/**
 * Sounds class handles the playing of all sounds in the game
 * @author maxgoldman
 *
 */
public class Sounds {

    //TODO: Handles case when sound loop is reached and game ends
    
    private Map<String, String> urls;
    
    private Clip[] enemyShotClips;
    private static int currentEnemyShotSounds = 0;
    private static int currentEnemyShotClip = 0;
    
    private Set<Clip> clipsPlaying;
    
    /**
     * Creates a Sounds object
     */
    public Sounds() {
        urls = new HashMap<String, String>();
        urls.put("background", "files/sounds/background.wav");
        urls.put("enemy_hit", "files/sounds/enemy_hit.wav");
        urls.put("enemy_shot", "files/sounds/enemy_shot.wav");
        urls.put("menu_move", "files/sounds/menu_move.wav");
        urls.put("menu_select", "files/sounds/menu_select.wav");
        urls.put("player_hit", "files/sounds/player_hit.wav");
        urls.put("player_shot", "files/sounds/player_shot.wav");
        urls.put("enemy_move", "files/sounds/enemy_move.wav");
        urls.put("enemy_too_many_shots", "files/sounds/enemy_too_many_shots.wav");
        
        enemyShotClips = new Clip[4];
        for(int i = 0; i < enemyShotClips.length; i++) {
            try {
                File file = new File(urls.get("enemy_shot")).getAbsoluteFile();
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
                enemyShotClips[i] = AudioSystem.getClip();
                enemyShotClips[i].open(audioInputStream);
                enemyShotClips[i].addLineListener(new LineListener() {
                    public void update(LineEvent event) {
                        if(event.getType() == LineEvent.Type.STOP) {
                            currentEnemyShotSounds--;
                            ((Clip) event.getSource()).setFramePosition(0);
                        }
                    }
                });
            } catch(Exception e) {
                //Don't play sound
            }
        }
        clipsPlaying = new HashSet<Clip>();
    }
    
    /**
     * Plays one of the game sounds
     * @param name name of the sound to play
     * @param loop whether or not the sound should loop indefinitely
     */
    public void playSound(String name, boolean loop) {
        if(!Consts.SOUND_ON) {
            return;
        }
        Clip clip = null;
        if(name.equals("enemy_shot")) {
            if(currentEnemyShotSounds >= enemyShotClips.length) {
                return;
            }
            Clip clipToUse = enemyShotClips[currentEnemyShotClip];
            if(clipToUse == null) {
                //Don't play sound
            }
            else if(loop) {
                throw new IllegalArgumentException("can't loop enemy shot");
            }
            else {
                clipToUse.start();
                currentEnemyShotSounds++;
                if(currentEnemyShotClip == enemyShotClips.length - 1) {
                    currentEnemyShotClip = 0;
                }
                else {
                    currentEnemyShotClip++;
                }
            }
        }
        else {
            try {
                File file = new File(urls.get(name)).getAbsoluteFile();
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
                clip = AudioSystem.getClip();
                clip.open(audioInputStream);
            } catch(Exception e) {
                //Just don't play sound
            }
            if(clip == null) {
                return;
            }
            if(loop) {
                clipsPlaying.add(clip);
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }
            else {
                clip.addLineListener(new LineListener() {
                    public void update(LineEvent event) {
                        if(event.getType() == LineEvent.Type.STOP) {
                            Clip source = (Clip) event.getSource();
                            source.close();
                            clipsPlaying.remove(source);
                            
                        }
                    }
                });
                clipsPlaying.add(clip);
                clip.start();
            }
        }
        
    }
    
    /**
     * Stops all sounds currently being played by this instance, 
     * uses array to prevent ConcurrentModificationException
     */
    public void stopAllSounds() {
        Object[] clipsArray = clipsPlaying.toArray();
        for(int i = 0; i < clipsArray.length; i++) {
            Clip c = (Clip) clipsArray[i];
            c.stop();
        }
    }
    
}
