package application;

/**
 * State of the grid, with the cost, we used this to implement a priority queue with the A* algorithm
 *
 *  @author ThÃ©o, Julian, Anatole, Andrew, Paul
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
     * @param grid, grid of the state
     * @param cost, cost of the state
     * @return 0 for a=b; 1 for a>b , -1 for a<b
     */
	
	@Override
	public int compareTo(State other)
	{
		return Integer.compare(this.cost, other.cost);
	}
}
