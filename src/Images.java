import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

/**
 * Images class stores all the images with easy keys for easy access,
 * gets rid of the need to import images from files every time an image
 * is needed.
 * @author maxgoldman
 *
 */
public class Images {
    
    private Map<String, Image> images;
    
    /**
     * Initializes and imports all images into memory
     */
    public Images() {
        images = new HashMap<String, Image>();
        try {
            images.put("p1Sprite", ImageIO.read(new File("files/images/p1Sprite.png")));
        } catch(IOException e) {
            //Don't add
        }
        try {
            images.put("p2Sprite", ImageIO.read(new File("files/images/p2Sprite.png")));
        } catch(IOException e) {
            //Don't add
        }
        try {
            images.put("enemy1Sprite_1_DOWN", ImageIO.read(new File("files/images/enemy1Sprite_1_DOWN.png")));
        } catch(IOException e) {
            //Don't add
        }
        try {
            images.put("enemy1Sprite_1_UP", ImageIO.read(new File("files/images/enemy1Sprite_1_UP.png")));
        } catch(IOException e) {
            //Don't add
        }
        try {
            images.put("blastSprite_UP", ImageIO.read(new File("files/images/blastSprite_UP.png")));
        } catch(IOException e) {
            //Don't add
        }
        try {
            images.put("blastSprite_DOWN", ImageIO.read(new File("files/images/blastSprite_DOWN.png")));
        } catch(IOException e) {
            //Don't add
        }
        try {
            images.put("main_NEW", ImageIO.read(new File("files/images/main_NEW.png")));
        } catch(IOException e) {
            //Don't add
        }
        try {
            images.put("main_HIGHSCORES", ImageIO.read(new File("files/images/main_HIGHSCORES.png")));
        } catch(IOException e) {
            //Don't add
        }
        try {
            images.put("main_HELP", ImageIO.read(new File("files/images/main_HELP.png")));
        } catch(IOException e) {
            //Don't add
        }
        try {
            images.put("main_QUIT", ImageIO.read(new File("files/images/main_QUIT.png")));
        } catch(IOException e) {
            //Don't add
        }
        try {
            images.put("help", ImageIO.read(new File("files/images/help.png")));
        } catch(IOException e) {
            //Don't add
        }
        try {
            images.put("top_wins", ImageIO.read(new File("files/images/top_wins.png")));
        } catch(IOException e) {
            //Don't add
        }
        try {
            images.put("bottom_wins", ImageIO.read(new File("files/images/bottom_wins.png")));
        } catch(IOException e) {
            //Don't add
        }
        try {
            images.put("high_scores", ImageIO.read(new File("files/images/high_scores.png")));
        } catch(IOException e) {
            //Don't add
        }
        try {
            images.put("blank_screen", ImageIO.read(new File("files/images/blank_screen.png")));
        } catch(IOException e) {
            //Don't add
        }
    }
    
    /**
     * Returns an image from the source files
     * @param name name of the image to get
     * @return the image
     */
    public Image getImage(String name) {
        return images.get(name);
    }
}
