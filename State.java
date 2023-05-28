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
	/**
	*
    *Creates a new state with the specified grid and cost.
    *@param grid The grid representing the state.
    *@param cost The cost associated with the state.
    */
	public State(int[][] grid, Integer cost)
	{
		this.grid=grid;
		this.cost=cost;
	}
	
	/**
	*
    *Compares this state with another state based on their costs.
    *@param other The other state to compare with.
    *@return A negative integer if this state's cost is less than the other state's cost,
	*/
	@Override
	public int compareTo(State other)
	{
		return Integer.compare(this.cost, other.cost);
	}
}