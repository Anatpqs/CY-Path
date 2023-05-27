package application;
/**
 * <p> Class Level used to create levels
 * @author Julian
 *
 */
public class Level {
    private String name;
    private int[][] grid;
    private int row;
    private int column;
    private int score;
    
    /**
     * <p> Method that create a level with his name, row, column.
     * 
     * @param name : name of the level
     * @param row : number of row in the level
     * @param column : number of column in the level
     */
    public Level(String name, int row, int column) {
        this.name = name;
        this.row = row;
        this.column = column;
        this.grid = new int[row][column];
        this.score = 0;
    }
    /**
     * <p> Method that set the value of the selected tile.
     * 
     * @param value : new value of the tile
     */
    public void setTile(int row, int column, int value) {
        grid[row][column] = value;
    }
    /**
     * 
     * @param i : row index of the tile 
     * @param j : column index of the tile
     * @return : return the changed grid of the level with the new value of the changed tile
     */
    public int getTile(int i, int j) {
        if (i >= 0 && i < row && j >= 0 && j < column) {
            return grid[i][j];
        } else {
            throw new IndexOutOfBoundsException("Coordonnées de Tuile invalides.");
        }
    }
    /**
     * 
     * @return the grid
     */
    public int[][] getGrid() {
        return grid;
    }
    /**
     * 
     * @return the name
     */
    public String getName() {
        return name;
    }
    /**
     * 
     * @return the row 
     */
    public int getRow() {
        return row;
    }
    /**
     * 
     * @return the column
     */
    public int getColumn() {
        return column;
    }
    /**
     * 
     * @return the score
     */
    public int getScore() {
        return score;
    }
    /**
     * Set the score
     * @param score
     */
    public void setScore(int score) {
        this.score = score;
    }    
}
