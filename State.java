package application;

/**
 * State of the grid, with the cost, we used this to implement a priority queue with the A* algorithm
 *
 *  @author Théo, Julian, Anatole, Andrew, Paul
 * @version 1.0
 */
public class State implements Comparable<State> {
	
	//Matrix of the grid of the game
	int[][] grid;
	//Cost of the grid 
	Integer cost;
	
	public State(int[][] grid, Integer cost)
	{
		this.grid=grid;
		this.cost=cost;
	}
	
	/**
     * To compare the state in the priority Queue
     *
     * @param grid 
     * @param cost
     * @return 0 -> a=b; 1-> a>b , -1 -> a<b
     */
	
	@Override
	public int compareTo(State other)
	{
		return Integer.compare(this.cost, other.cost);
	}
}
