import java.util.Random;

public class PercolationStats
{
	private double[] percolationThresholds;
	private int gridSize;
	private int trials;
	Random random;
	
    public PercolationStats(int N, int T)
    {
        if (N <= 0 || T <= 0)
        {
        	throw new IllegalArgumentException();
        }
        
        percolationThresholds = new double[T];
        gridSize = N;
        trials = T;
        random = new Random();
        
        runTrials();
    }
    
    public double mean()
    {
    	double sum = 0;
    	
        for (int i = 0; i < percolationThresholds.length; i++)
        {
        	sum += percolationThresholds[i];
        }
        
        return sum / percolationThresholds.length;
    }
    
    public double stddev()
    {
    	if (trials == 1)
    	{
    		return Double.NaN;
    	}
    	
    	double sum = 0;
    	
       	for (int i = 0; i < percolationThresholds.length; i++)
        {
        	sum += (percolationThresholds[i] - mean()) * (percolationThresholds[i] - mean());
        }
        
        return Math.sqrt(sum / (percolationThresholds.length - 1));
    }
    
    public double confidenceLo()
    {
        return mean() - ((1.96 * stddev()) / Math.sqrt(trials));
    }
    
    public double confidenceHi()
    {
    	return mean() + ((1.96 * stddev()) / Math.sqrt(trials));
    }
    
    private void runTrials()
    {
    	for(int i = 0; i < trials; i++)
    	{
	    	Percolation trial = new Percolation(gridSize);
	    	double counter = 0;
	    	
	    	while (!trial.percolates())
	    	{
	    		int x = randomNumber(1, gridSize);
	    		int y = randomNumber(1, gridSize);
	    		if (!trial.isOpen(x, y))
	    		{
	    			trial.open(x, y);
	    			counter++;
	    		}
	    	}
	    	
	    	percolationThresholds[i] = counter / (gridSize * gridSize);
    	}
    }
    
    private int randomNumber(int min, int max)
    {
    	return random.nextInt(max - min + 1) + min;
    }
    
    public static void Main(String[] args)
    {
    	PercolationStats test = new PercolationStats(500, 500);
    	System.out.println(test.mean());
    	System.out.println("Hello World");
    }
}