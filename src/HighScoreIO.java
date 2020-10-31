import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles all writing and retrieval of high scores
 * @author maxgoldman
 *
 */
public class HighScoreIO {

    private File scoresFile;
    
    /**
     * Instantiates a HighScore object
     * @param filePath the path where HighScores should be stored, 
     * creates file if one does not already exist
     * @throws IOException when file creation throws IOException
     */
    public HighScoreIO(String filePath) throws IOException{
        scoresFile = new File(filePath);
        if(!scoresFile.exists()) {
            scoresFile.createNewFile();
        }
    }
    
    /**
     * Gets all HighScores from the given file
     * @return List of the HighScores in the file
     */
    public List<HighScore> getScores() {
        ArrayList<HighScore> a = new ArrayList<HighScore>();
        try {
            BufferedReader in = new BufferedReader(new FileReader(scoresFile));
            String line = in.readLine();
            while(line != null) {
                String[] tokens = line.split(";");
                if(tokens.length == 2) {
                    a.add(new HighScore(tokens[0], Integer.parseInt(tokens[1])));
                }
                line = in.readLine();
            }
            in.close();
        } catch(Exception e) {
            //Don't give more high scores
        }
        
        
        return a;
    }
    
    /**
     * Writes a new HighScore to the file
     * @param hs HighScore to write to the file
     * @return true if successful, false otherwise
     */
    public boolean addHighScore(HighScore hs) {
        try {
            BufferedWriter bWriter = new BufferedWriter(new FileWriter(scoresFile, true));
            bWriter.write("\n" + hs.nickname + ";"+ hs.score + "\n");
            bWriter.close();
        } catch(IOException e) {
            return false;
        }
        return true;
    }
}
