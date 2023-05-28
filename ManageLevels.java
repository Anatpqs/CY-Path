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
	 *  Inside the method, a buffered reader is created to read the file. 
	 *  The method then initializes a list called Levels to store the loaded levels, and declares several variables to keep track of the current level, row index, level count, and score.
	 *
	 * The method reads each line of the file using the readLine() method until there are no more lines to read. 
	 * The following steps are performed for each line:
	 *
	 * 1. If the line is ".", it indicates the end of the current level. If there is a valid current level, it is added to the list of levels (Levels) after setting its score. Then, a new level is created and the necessary variables are reset for the next level.
	 * 2. If there is no current level, the line contains the dimensions of the level in the format "rowsxcolumns". The line is split to extract the row and column values, and a new level is created with the specified dimensions. The next line is then read to retrieve the score, which is parsed and set for the current level.
	 * 3. If there is a current level, the line contains tile values separated by spaces. The line is split to extract the individual tile values. Each tile is then processed: if it is "-", indicating an empty tile, the corresponding position in the current level is set to -1; otherwise, the tile value is parsed into an integer and set in the current level.
	 * 4. The row index is incremented to move to the next row.
	 * Once all lines have been processed, the buffered reader is closed, and the list of levels (Levels) is returned.
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
     * Inside the method, a buffered writer is created to facilitate writing to the file. The method then iterates through each level in the listLevels and performs the following steps:
	 * 1. Writes the dimensions of the level (number of rows and columns) to the file, separated by "x".
	 * 2. Writes the score of the level to the file.
	 * 3. Iterates through each row and column of the level's grid and writes the value of each tile to the file. If a tile has a value of -1, it is represented as "-" in the file.
	 * 4. Writes a period (".") to mark the end of the current level in the file.
	 * Once all levels have been processed, the writer is closed, ensuring that all data is properly flushed and the file is saved.
	 * 
     * @param list of levels to be saved.
     * @param path to the file where the levels will be stored.
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
