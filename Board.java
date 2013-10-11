public class Board
{
    private int brow, bcol;
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
                if (blocks[i][j] == 0)
                {
                    brow = i;
                    bcol = j;
                }
                
              
            }
        }
    }
    
    public int dimension()
    {   return dim; }
    
    public boolean isGoal()
    {   return manhattan() == 0; }
    
    public int blankRow() 
    {   return brow; }
    
    public int blankCol()
    {   return bcol; }
    
    public Board twin()
    {
        int[][] blocks = new int[dim][dim];
        for (int i = 0; i < dim; i++)
            for (int j = 0; j < dim; j++)
                blocks[i][j] = board[i][j];

        int row = (int) ((double)dim * Math.random());
        int col = 0;
                
        while (true)
        {
            if (blocks[row][col] == 0) col++;
            if (col + 1 < dim) break;
            row = ++row % dim;
        }
        
        int t = blocks[row][col];
        blocks[row][col] = blocks[row][col+1];
        blocks[row][col+1] = t;
        
        Board B = new Board(blocks);
        return B;
    }
    
    public boolean equals(Object B)
    {
        if (B == this) return true;
        if (B == null) return false;
        if (B.getClass() != this.getClass()) return false;
        Board that = (Board) B;
        if (dim != that.dim) return false;
        for (int i = 0; i < dim; i++)
            for (int j = 0; j < dim; j++)
                if (board[i][j] != that.board[i][j])
                    return false;
        return true;
    }
    
    public Iterable<Board> neighbors()
    {
        return new MyNeighborsIterator(B);
    }
    
    public String toString()
    {
        String s;
        s = "" + dim + "\n";
        for (int i = 0; i < dim; i++)
        {
            for (int j = 0; j < dim; j++)
            {
                if (j > 0) s = s + "  ";
                s = s + board[i][j];
                if ((j % dim) == (dim - 1)) s = s + "\n";
            }
        }
        return s;
    }
    
    public int manhattan()
    {
        int d = 0;
        for (int i = 0; i < dim; i++)
            for (int j = 0; j < dim; j++)
                d += mdistance(i, j);
            
        return d;
    }
    public int copy()
    {
        int blocks[][] = new int[dim][dim];
        for (int i = 0; i < dim; i++)
            for (int j = 0; j < dim; j++)
                blocks[i][j] = board[i][j];
        Board newBoard = new Board(blocks);
        blocks = null;
        return newBoard;
    }
    
    private void swapUp()
    {
        if (brow == 0) throw new java.lang.IllegalArgumentException();
        int t = board[brow - 1][bcol];
        board[brow - 1][bcol] = 0;
        board[brow][bcol] = t;
        brow--;
    }
    
    private void swapDown()
    {
        if (brow >= (dim - 1)) throw new java.lang.IllegalArgumentException();
        int t = board[brow + 1][bcol];
        board[brow + 1][bcol] = 0;
        board[brow][bcol] = t;
        brow++;
    }
    
    private void swapLeft()
    {
        if (bcol == 0) throw new java.lang.IllegalArgumentException();
        int t = board[brow][bcol - 1];
        board[brow][bcol - 1] = 0;
        board[brow][bcol] = t;
        bcol--;
    }
    
    private void swapRight()
    {
        if (bcol >= (dim - 1)) throw new java.lang.IllegalArgumentException();
        int t = board[brow][bcol + 1];
        board[brow][bcol + 1] = 0;
        board[brow][bcol] = t;
        brow++;
    }
    
    private int mdistance(int row, int col)
    {
        int x = board[row][col];
        
        if (x == 0)
        {   return 0;   }
                    
        return Math.abs(((x - 1) / dim) - row) + Math.abs(((x - 1) % dim) - col);
    }
    
    static class MyNeighborsIterator(Board B) implements Iterator<Board>
    {
        private Board B;
        private int i, n;
        
        public MyNeighborsIterator(Board B)
        {
            i = 0;
            n = 4;
            if (B.blankRow == 0) n--;
            if (B.blankRow == (B.dimension() - 1)) n--;
            if (B.blankCol == 0) n--;
            if (B.blankCol == (B.dimension() - 1)) n--;
            this.B = B;
        }
        
        public boolean hasNext()
        {   return i < n;   }
        
        public void remove()
        {}
        
        public Board next()
        {
            if (i >= n) throw new java.lang.NoSuchElementException();
            
            Board newBoard = B.copy();
            
            switch(i)
            {
                case 0: // swap up
                    if (newBoard.blankRow() > 0)
                    {
                        newBoard.swapUp();
                        break;
                    }
                    // fall through
                case 1:
                    if (newBoard.blankRow() < newBoard.dimension() - 1)
                    {
                        newBoard.swapDown();
                        break;
                    }
                    // fall through
                case 2:
                    if (newBoard.blankCol() > 0)
                    {
                        newBoard.swapLeft();
                        break;
                    }
                    // fall through
                case 3:
                    if (newBoard.blankCol() < newBoard.dimension() - 1)
                    {
                        newBoard.swapRight();
                        break;
                    }
                    // fall through
                default:
                    newBoard = null;
                    throw new java.lang.NoSuchElementException();
            }
            
            i++;
            return newBoard;
        }
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
        StdOut.println(B);
        StdOut.println("Manhattan() = " + B.manhattan());
        
    }
}