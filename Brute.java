public class Brute
{
    
    public Brute()
    {
        public static void main(String argv[])
        {
        
            ReadPoints();
            FindCollinearPoints();
            
            
        }
    }
    
    private void ReadPoints()
    {
        nPoints = StdIn.readInt();
        this.points = new Point[nPoints];
        
        for (int i = 0; i < nPoints; i++)
        {
            int x = StdIn.readInt();
            int y = StdIn.readInt();
            
            this.points[i] = new Point(x, y);
        }    
    }
    
    private void FindCollinearPoints()
    {
        Point p[4];
        
        for (int p0 = 0; p0 < this.points.length - 3; p0++)
        {
            p[0] = p0;
            for (int p1 = p0; p1 < this.points.length - 2; p1++)
            {
                p[1] = p1;
                for (int p2 = p1; p2 < this.points.length -1; p2++)
                {
                    p[2] = p2;
                    for (int p3 = p2; p3 < this.points.length; p3++)
                    {
                        p[3] = p3;
                        if (areCollinear(p))
                        {
                            SortPoints(p, 0, p.length);
                        }
                    }
                }
            }
        }    
    }
    
    private boolean areCollinear(Point[] p)
    {
        double previousSlope, thisSlope;
        
        for (i = 0; i < p.length-1; i++)
        {
            thisSlope = p[i].slopeTo(p[i+1]);

            if ((i > 0) && (thisSlope != previousSlope))
                return false;
                
            previousSlope = thisSlope;
        }
        
        return true;
    }
    
    private void SortPoints(Point[] a, int start, int end)
    {
        int n = end - start;
        if (n > 2)
        {
            int i = start+1;
            int eq = start;
            int gt = end;

            while (i < gt)
            {
                int r = p[start].compareTo(p[i]);
                if (r < 0) Swap(a, eq++, i++);
                else if (r > 0) Swap(a, --gt, i);
                else i++;
            }
            
            SortPoints(a, start, eq);
            SortPoints(a, gt, end);
        }
        if (n == 2)
        {
            if (a[start].compareTo(a[start+1]) < 0)
            {
                SwapPoints(a, start, start+1);
            }
        }
    }
    
    private Point points[];
}