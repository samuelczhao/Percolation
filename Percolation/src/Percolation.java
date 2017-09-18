//public class Percolation
//{         
//	private boolean[][] grid;
//	private int gridSize;
//	private WeightedQuickUnionUF union;
//	private WeightedQuickUnionUF union1;
//	private int top;
//	private int bottom;
//	
//	
//    public Percolation(int n)
//    {
//    	if (n <= 0)
//    	{
//    		throw new IllegalArgumentException("Cannot have negative grid size.");
//    	}
//    	
//    	grid = new boolean[n + 1][n + 1];
//    	gridSize = n;
//    	union = new WeightedQuickUnionUF(n * n + 2);
//    	union1 = new WeightedQuickUnionUF(n * n + 2);
//    	top = 0;
//    	bottom = n * n + 1;
//    }
//
//    public void open(int i, int j)
//    {
//    	indexValidator(i, j);
//    	
//    	grid[i][j] = true;
//    	
//    	if (i - 1 == 0)
//    	{
//    		union.union(translate(i, j), top);
//    		union1.union(translate(i, j), top);
//    	}
//    	
//    	if (i == gridSize)
//    	{
//    		union.union(translate(i, j), bottom);
//    	}
//    	
//    	// look for blocks around it to union with
//    	if ((i - 1) != 0 && grid[i - 1][j])
//    	{
//    		union.union(translate(i, j), translate(i - 1, j));
//    		union1.union(translate(i, j), translate(i - 1, j));
//    	}
//    	
//    	if ((i + 1) <= gridSize && grid[i + 1][j])
//    	{
//    		union.union(translate(i, j), translate(i + 1, j));
//    		union1.union(translate(i, j), translate(i + 1, j));
//    	}
//    	
//    	if ((j - 1) != 0 && grid[i][j - 1])
//    	{
//    		union.union(translate(i, j), translate(i, j - 1));
//    		union1.union(translate(i, j), translate(i, j - 1));
//    	}
//    	
//    	if ((j + 1) <= gridSize && grid[i][j + 1])
//    	{
//    		union.union(translate(i, j), translate(i, j + 1));
//    		union1.union(translate(i, j), translate(i, j + 1));
//    	}
//    }
//
//    public boolean isOpen(int i, int j)
//    {
//    	indexValidator(i, j);
//    	
//        return grid[i][j];
//    }
//
//    public boolean isFull(int i, int j)
//    {
//    	indexValidator(i, j);
//    	
//    	return union1.connected(translate(i, j), top);
//    }
//
//    public boolean percolates()
//    {
//    	return union.connected(top, bottom);
//    }
//    
//    private int translate(int row , int column)
//    {
//    	return ((row - 1) * gridSize) + column; 
//    }
//    
//    private void indexValidator(int i, int j)
//    {
//    	if (i <= 0 || j <= 0 || i > gridSize || j > gridSize)
//    	{
//    		throw new IndexOutOfBoundsException();
//    	}
//    }
//}
public class Percolation 
{
	private boolean[][] open, full;
	private int n;

	public Percolation(int n) 
	{
		if (n <= 0) 
		{
			throw new IllegalArgumentException("Cannot have negative grid size.");
		}

		open = new boolean[n + 2][n + 2];
		full = new boolean[n + 2][n + 2];
		this.n = n;
		
		for (int i = 1; i < n + 1; i++)
		{
			open[0][i] = open[n + 1][i] = full[0][i] = true;
		}
	}

	public void open(int i, int j) 
	{
		indexValidator(i, j);
		if (open[i][j])
		{
			return;
		}
		
		open[i][j] = true;
		
		if (full[i - 1][j] || full[i + 1][j] || full[i][j - 1] || full[i][j + 1])
		{
			full(i, j);
		}
	}

	private void full(int i, int j) 
	{
		if (i > n + 1 || !open[i][j] || full[i][j])
		{
			return;
		}
		
		full[i][j] = true;
		full(i - 1, j);
		full(i + 1, j);
		full(i, j - 1);
		full(i, j + 1);
	}

	public boolean isOpen(int i, int j) 
	{
		indexValidator(i, j);

		return open[i][j];
	}

	public boolean isFull(int i, int j) 
	{
		indexValidator(i, j);

		return full[i][j];
	}

	public boolean percolates() 
	{
		return full[n + 1][1];
	}

	private void indexValidator(int i, int j) 
	{
		if (i <= 0 || j <= 0 || i > n || j > n) 
		{
			throw new IndexOutOfBoundsException();
		}
	}

}
