package application;

public class State implements Comparable<State> {
	
	int[][] grid;
	Integer cost;
	
	public State(int[][] grid, Integer cost)
	{
		this.grid=grid;
		this.cost=cost;
	}
	
	@Override
	public int compareTo(State other)
	{
		return Integer.compare(this.cost, other.cost);
	}
}