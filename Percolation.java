public class Percolation {

   private boolean[] o;
   private int size;
   private int topSite;
   private int bottomSite;
   private WeightedQuickUnionUF U;

   public Percolation(int N)              // create N-by-N grid, with all sites blocked
   {
       if (N <= 0) throw new java.lang.IllegalArgumentException();
       
       o = new boolean[N*N];
       size = N;
       topSite = N*N;
       bottomSite = N*N+1;
       U = new WeightedQuickUnionUF(N*N+2);
       
       for (int i = 0; i < N*N; i++)
          o[i] = false;
          
   }
   
   public void open(int r, int c)         // open site (row r, column c) if it is not already
   {
       int x = getIndex(r,c);
       
       if (o[x]) return;                  // do nothing if already open   
           
       // top neighbor
       if ((r > 1) && isOpen(r-1, c))
          U.union(x, getIndex(r-1,c));
          
       // bottom neighbor
       if ((r < size) && isOpen(r+1,c))
          U.union(x, getIndex(r+1,c));
          
       // left neighbor
       if ((c > 1) && isOpen(r,c-1))
          U.union(x, getIndex(r,c-1));
          
       // right neighbor
       if ((c < size) && isOpen(r,c+1))
          U.union(x, getIndex(r,c+1));
          
       // link to virtual top site if in first row
       if (r == 1)
          U.union(x, topSite);
          
       // link to virtual bottom site if in last row
       if (r == size)
          U.union(x, bottomSite);
          
       // mark open
       o[x] = true;
       
   }
   
   public boolean isOpen(int r, int c)    // is site (row r, column c) open?
   {
      return o[getIndex(r,c)];
   }
   
   public boolean isFull(int r, int c)    // is site (row r, column c) full?
   {
      return U.connected(topSite, getIndex(r,c));
   }

   public boolean percolates()            // does the system percolate?
   {
      return U.connected(topSite, bottomSite);
   }
   
   private int getIndex(int r, int c)
   {
       if ((r < 1) || (r > size) || (c < 1) || (c > size))
          throw new java.lang.IndexOutOfBoundsException();
       
       return (r-1)*size + (c-1);
   }
}