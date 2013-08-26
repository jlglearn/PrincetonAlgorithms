public class Percolation {

	private int[] grid;
	private int[] size;
	private int gridSize;
	private int topSite;
	private int bottomSite;
	
	private int getIndex(int i, int j)
	{
		if ((i < 1) || (i > gridSize) ||
		    (j < 1) || (j > gridSize))
		{
			
		}
		
		return (i-1)*gridSize + (j-1);
	}
	
	private int findRoot(int i, int j)
	{
		int p = getIndex(i,j);
		while( p != grid[p] )
		{
			grid[p] = grid[grid[p]];
			p = grid[p];
		}
		return p;
	}
	
	// connect sites s1 and s2.  NOTE: s1 and s2 are already valid indices into grid array
	private void connect(int s1, int s2)
	{
		// TODO
	}
	
	private boolean checkConnectivity(int i, int j)
	{
		int x = getIndex(i,j);
		
		// attempt to connect to 4 neighbors (connect will do nothing if neighbor is blocked)
		// top neighbor
		if ( i > 1 ) connect(x, getIndex(i-1,j));
		// left neighbor
		if ( j > 1 ) connect(x, getIndex(i,j-1));
		// right neighbor
		if ( j < gridSize ) connect(x, getIndex(i,j+1));
		// bottom neighbor
		if ( i < gridSize ) connect(x, getIndex(i+1,j));
		
		return (grid[x] == topSite);
	}	
	
   	// create N-by-N grid, with all sites blocked
	public Percolation(int N)
	{
		// grid: -1 if site is blocked; else points to site's root (points to itself if root or open but disconnected)
		// grid has two special sites (at indices N*N and N*N+1) to hold virtual top and bottom sites, respectively
		grid = new int[(N*N)+2];
		size = new int[N*N];
		
		gridSize = N;
		topSite = N*N;
		bottomSite = (N*N)+1;
		
		for( int i = 0; i < (N*N); i++)
		{	
			grid[i]=-1; // -1 indicates site closed
			size[i] = 1;
		}
		
		// virtual top site open and full by default (points to top site, that it is, itself)
		grid[topSite] = topSite;
		
		// virtual bottom site is open but empty: points to itself
		grid[bottomSite] = bottomSite;
	}
   
	// open site (row i, column j) if it is not already
	public void open(int i, int j)
	{
		int x = getIndex(i,j);
		if (grid[x] == -1)
		{
			grid[x] = x;
			checkConnectivity(i,j);
		}
	}
   
	// is site (row i, column j) open?
	public boolean isOpen(int i, int j)
	{
		return (grid[getIndex(1,j)] != -1);
	}
   
	// is site (row i, column j) full?
	public boolean isFull(int i, int j)
	{
		// full if open (!= -1) and points to top site
		return (grid[getIndex(i,j)] == topSite);
	}

	// does the system percolate?
	public boolean percolates()
	{
		// percolates if virtual bottom site is connecte to virtual top site
		return (grid[bottomSite] == topSite);
	}
}