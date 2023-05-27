package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The ManageLevels class provides methods to load and save levels from/to a text file.
 * Levels are represented by the Level class.
 * @author Julian
 *
 */
public class ManageLevels {
	/**
	 *  
	 * @param filePath : path to the txt file containing the levels
	 * @return : return a list of levels from the txt file
	 * @throws IOException
	 */
    public static List<Level> loadLevels(String filePath) throws IOException {
    	// Create a buffered reader to read the file
        BufferedReader lecteur = new BufferedReader(new FileReader(filePath));

        List<Level> Levels = new ArrayList<>();
        Level currentLevel = null;
        int rawIndex = 0; 
        int countLevel = 1; 
        int score = 0;

        String row;
        while ((row = lecteur.readLine()) != null) {
            if (row.equals(".")) {
                // Add the current level to the list of levels
                if (currentLevel != null) {
                	currentLevel.setScore(score);
                    Levels.add(currentLevel);
                }
                // Create a new level
                currentLevel = null;
                rawIndex = 0; 
                countLevel++; 
                
            } else if (currentLevel == null) {
                // Create a new level with the specified dimensions
                String[] dimensions = row.split("x");
                int rows = Integer.parseInt(dimensions[0]);
                int columns = Integer.parseInt(dimensions[1]);
                currentLevel = new Level("level " + countLevel, rows, columns); // Add name to levels
                
                // Read next row to retrieve score
                row = lecteur.readLine();
                score = Integer.parseInt(row.trim());
                currentLevel.setScore(score);
                
            } else if (currentLevel != null) {
                    String[] tiles = row.split(" ");
                    for (int colonneIndex = 0; colonneIndex < tiles.length; colonneIndex++) {
                        String tile = tiles[colonneIndex];
                        if (tile.equals("-")) {
                            // Check an empty tile
                            currentLevel.setTile(rawIndex, colonneIndex, -1);
                        } else {
                            // Convert the tile value into a integer
                            int numbertile = Integer.parseInt(tile);
                            currentLevel.setTile(rawIndex, colonneIndex, numbertile);
                        }
                    }
                    rawIndex++;
            }
        }

        lecteur.close();
        return Levels;
    }
    /**
     * 
     * @param Levels : list of level
     * @param filePath the path to the text file where the levels will be saved
     * @throws IOException
     */
    public static void saveLevels(List<Level> listLevels, String filePath) throws IOException {
    	// Create a buffered writer to write to the file
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));

        for (Level level : listLevels) {
            writer.write(level.getRow() + "x" + level.getColumn());
            writer.newLine();
            writer.write(Integer.toString(level.getScore()));
            writer.newLine();

            for (int i = 0; i < level.getRow(); i++) {
                for (int j = 0; j < level.getColumn(); j++) {
                    int tile = level.getTile(i, j);
                    if (tile == -1) {
                        writer.write("-");
                    } else {
                        writer.write(String.valueOf(tile));
                    }
                    writer.write(" ");
                }
                writer.newLine();
            }

            writer.write(".");
            writer.newLine();
        }

        writer.close();
    }

}
