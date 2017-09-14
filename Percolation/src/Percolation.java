public class Percolation
{         
	private boolean[][] grid;
	private int gridSize;
	private WeightedQuickUnionUF union;
	private int top;
	private int bottom;
	
	
    public Percolation(int n)
    {
    	if (n <= 0)
    	{
    		throw new IllegalArgumentException("Cannot have negative grid size.");
    	}
    	
    	grid = new boolean[n + 1][n + 1];
    	gridSize = n;
    	union = new WeightedQuickUnionUF(n * n + 2);
    	top = 0;
    	bottom = n * n + 1;
    	
    	for (int i = 1; i <= n; i++)
    	{
    		grid[1][i] = true;
    		union.union(top, i);
    		union.union(bottom, n * n + 1 - i);
    	}
    }

    public void open(int i, int j)
    {
    	indexValidator(i, j);
    	
    	grid[i][j] = true;
    	
    	if ((i - 1) != 0 && grid[i - 1][j])
    	{
    		union.union(translate(i, j), translate(i - 1, j));
    	}
    	
    	if ((i + 1) <= gridSize && grid[i + 1][j])
    	{
    		union.union(translate(i, j), translate(i + 1, j));
    	}
    	
    	if ((j - 1) != 0 && grid[i][j - 1])
    	{
    		union.union(translate(i, j), translate(i, j - 1));
    	}
    	
    	if ((j + 1) <= gridSize && grid[i][j + 1])
    	{
    		union.union(translate(i, j), translate(i, j + 1));
    	}
    }

    public boolean isOpen(int i, int j)
    {
    	indexValidator(i, j);
    	
        return grid[i][j];
    }

    public boolean isFull(int i, int j)
    {
    	indexValidator(i, j);
    	
    	return union.connected(translate(i, j), top);
    }

    public boolean percolates()
    {
    	return union.connected(top, bottom);
    }
    
    private int translate(int row , int column)
    {
    	return ((row - 1) * gridSize) + column; 
    }
    
    private void indexValidator(int i, int j)
    {
    	if (i <= 0 || j <= 0 || i > gridSize || j > gridSize)
    	{
    		throw new IndexOutOfBoundsException();
    	}
    }
    
    public static void main(String[] args)
    {
    	PercolationStats test = new PercolationStats(100, 100);
    	System.out.println(test.mean());
    }
}
