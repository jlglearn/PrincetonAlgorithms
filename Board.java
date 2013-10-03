public class Board
{
    private int dim;
    private int[][] board;
    
    public Board(int[][] blocks)
    {
        assert(blocks.length == blocks[0].length);
        dim = blocks.length;
        int dim2 = dim * dim;
        
        board = new int[dim][dim];
        
        for (int i = 0; i < dim; i++)
        {
            for (int j = 0; j < dim; j++)
            {
                assert((blocks[i][j] >= 0) && (blocks[i][j] < dim2));
                board[i][j] = blocks[i][j];
            }
        }
    }
    
    public int manhattan()
    {
        int d = 0;
        for (int i = 0; i < dim; i++)
            for (int j = 0; j < dim; j++)
                d += mdistance(i, j);
            
        return d;
    }
    
    private int mdistance(int row, int col)
    {
        int x = board[row][col];
        
        if (x == 0)
        {   return 0;   }
                    
        return Math.abs(((x - 1) / dim) - row) + Math.abs(((x - 1) % dim) - col);
    }
    
    public static void main(String[] argv)
    {
        if (StdIn.isEmpty())
        {
            StdOut.println("Empty input stream");
            throw new java.lang.IllegalArgumentException();
        }
        
        int n = StdIn.readInt();
        if ((n < 2) || (n >= 128))
        {
            StdOut.println("n must be >= 2 and <128");
            throw new java.lang.IllegalArgumentException();
        }
        
        int dim = n;
        int board[][] = new int[n][n];
        boolean seen[] = new boolean[n * n];
        
        for (int i = 0; i < n * n; i++)
            seen[i] = false;
        
        for (int i = 0; i < n; i++)
        {
            for (int j = 0; j < n; j++)
            {
                if (StdIn.isEmpty())
                {
                    StdOut.println("Unexpected end of input stream");
                    throw new java.lang.IllegalArgumentException();
                }
                
                int x = StdIn.readInt();
                
                if ((x < 0) || (x > (n * n - 1)))
                {
                    StdOut.println("Value out of range (" + i + "=" + x + ")");
                    throw new java.lang.IllegalArgumentException();
                }
                
                if (seen[x])
                {
                    StdOut.println("Duplicate value " + x);
                    throw new java.lang.IllegalArgumentException();
                }
                
                board[i][j] = x;
                seen[x] = true;
            }
        }
        
        Board B = new Board(board);
        StdOut.println("Manhattan() = " + B.manhattan());
        
    }
}