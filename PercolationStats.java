public class PercolationStats {
   public PercolationStats(int N, int T)    // perform T independent computational experiments on an N-by-N grid
   {
       if ((N <= 0) || (T <= 0))
          throw new java.lang.IllegalArgumentException();
          
       trials = T;
       gridSize = N;
       trialData = new double[N];
       Random random = new Random();
       double d = N*N;
       
       for (int iTrial = 0; i < T; iTrial++ )
       {
            P = new Percolation(N);
            
            for( int i = 0; !P.percolates(); )
            {
                int r = 1+random.nextInt(0,gridSize);
                int c = 1+random.nextInt(0,gridSize);
                
                if (P.isOpen(r,c))
                    // do nothing if already open
                    continue;
                    
                i++;
                P.open(r,c);
                
                if (P.percolates())
                {
                    double p = ((double) i) / d;
                    trialData[iTrial] = p;
                }
            }
       }
   }
   
   public double mean()                     // sample mean of percolation threshold
   {
        double d = 0.0;
        for(int i = 0; i < trials; i++ )
        {
            d += trialData[i];
        }
        
        return d / trials;
   }
   
   public double stddev()                   // sample standard deviation of percolation threshold
   {
        double s2 = 0.0;
        double m = mean();
        
        for ( int i = 0; i < trials; i++ )
        {
            s2 += Math.pow(trialData[i] - m, 2);
        }
        
        return Math.sqrt(s2/(trials-1));
   }
   
   public double confidenceLo()             // returns lower bound of the 95% confidence interval
   {    
        return mean() - ((1.96 * stddev())/Math.sqrt(trials));
   }
   
   public double confidenceHi()             // returns upper bound of the 95% confidence interval
   {
        return mean() + ((1.96 * stddev())/Math.sqrt(trials));
   }
   
   public static void main(String[] args)   // test client, described below
   {
        int N = parseInt(args[1]);
        int T = parseInt(args[2]);
        
        PercolationStats PS = new PercolationStats(N,T);
        
        system.out.printf("Number of trials: %d\nmean: %0.06f\nstddev: %0.06f\n95% Interval: %0.06f, %0.06f\n",
                          T, PS.mean(), PS.stddev(), PS.confidenceLo(), PS.confidenceHi());
   }
   
   private double[] trialData;
   private int trials;
   private int gridSize;
   private Percolation P;
}