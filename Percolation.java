public class Percolation {

   private boolean[] o;
   private boolean[] f;
   private boolean p;                     // true if a percolation path is found

   private int size;
   private int topSite;
   private int bottomSite;
   private WeightedQuickUnionUF U;

   public Percolation(int N)              // create N-by-N grid, with all sites blocked
   {
       int N2 = N*N;
       
       if (N <= 0) throw new java.lang.IllegalArgumentException();
       
       o = new boolean[N2];
       f = new boolean[N2];
       
       p = false;
       size = N;
       topSite = N2;
       bottomSite = N2+1;
       
       U = new WeightedQuickUnionUF(N2+2);
       
       for (int i = 0; i < N2; i++)
       {
          o[i] = false;
          f[i] = false;
       }
          
   }
   
   public void open(int r, int c)         // open site (row r, column c) if it is not already
   {
       int x = getIndex(r, c);
       
       if (o[x]) return;                  // do nothing if already open   
       
       // mark open
       o[x] = true;       
       
       // link to virtual top site if in first row
       if (r == 1)
       {
          U.union(x, topSite);
       }       
       
       int t = getTop(r, c);
       int b = getBottom(r, c);
       int l = getLeft(r, c);
       int h = getRight(r, c);       
 
       // mark as full if in first row or if any neighbor is full
       if ((r == 1)
           || ((t != -1) && f[t])   // top is full
           || ((l != -1) && f[l])   // left is full
           || ((h != -1) && f[h])   // right is full
           || ((b != -1) && f[b]))  // bottom is full
       {
          f[x] = true;

          if (r == size)
          {
             p = true;                      // found a percolation path
             U.union(x, bottomSite);        // if in last row, link to bottom site;       
          }
          
       }
                    
       if (t != -1) U.union(x, t);          // link to top neighbor
       
       if (l != -1) U.union(x, l);          // link to left neighbor
       
       if (h != -1) U.union(x, h);          // link to right neighbor
       
       if (b != -1) U.union(x, b);          // link to bottom neighbor
       
       // if this site is full, flow outward to neighbors
       if (f[x])
       {           
          flow(r, c);
       }
   }
   
   public boolean isOpen(int r, int c)    // is site (row r, column c) open?
   {
      return o[getIndex(r, c)];
   }
   
   public boolean isFull(int r, int c)    // is site (row r, column c) full?
   {
      return f[getIndex(r, c)];
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
   
   private int getLeft(int r, int c)
   {
       return (c > 1) ? getIndex(r, c-1) : -1;
   }
   
   private int getRight(int r, int c)
   {
       return (c < size) ? getIndex(r, c+1) : -1;
   }
   
   private int getTop(int r, int c)
   {
       return (r > 1) ? getIndex(r-1, c) : -1;
   }
   
   private int getBottom(int r, int c)
   {
       return (r < size) ? getIndex(r+1, c) : -1;
   }
   
   private void flow(int r, int c)
   {            
        int x = getIndex(r, c);
        
        if (!o[x]) return;
            
        f[x] = true;
        
        if ((r == size) && !p)
        {
            p = true;
            U.union(x, bottomSite);
        }
        
        int t;
        
        if (r < size)
        {
           t = getIndex(r+1, c);
           if (o[t] && !f[t]) flow(r+1, c);
        }
        
        if (c > 1)
        {
           t = getIndex(r, c-1);
           if (o[t] && !f[t]) flow(r, c-1);
        }
        
        if (c < size)
        {
           t = getIndex(r, c+1);
           if (o[t] && !f[t]) flow(r, c+1);
        }
        
        if (r > 1)
        {
           t = getIndex(r-1, c);
           if (o[t] && !f[t]) flow(r-1, c);
        }
        
   }
   
}