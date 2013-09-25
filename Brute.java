public class Brute
{
    public Brute()
    {
    }
    
    public static void main(String argv[])
    {
        ReadPoints();
        // PrintPoints();
        PrepareDraw();
        DrawPoints();
        FindCollinearPoints();
    }
    
    private static void PrepareDraw()
    {
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
    }
    
    
    private static void ReadPoints()
    {
        int nPoints = StdIn.readInt();
        points = new Point[nPoints];
        
        for (int i = 0; i < nPoints; i++)
        {
            int x = StdIn.readInt();
            int y = StdIn.readInt();
            
            points[i] = new Point(x, y);
        }    
        
        SortPoints(0, points.length);
    }
    
    private static void PrintPoints()
    {
        StdOut.println("Read " + points.length + " points:");
        for (int i = 0; i < points.length; i++)
        {
            StdOut.println("[" + i + "]:" + points[i]);
        }
    }
    
    private static void DrawPoints()
    {
        for (int i = 0; i < points.length; i++)
        {
            points[i].draw();
        }
    }
    
    private static void FindCollinearPoints()
    {        
        for (int p0 = 0; p0 < points.length - 3; p0++)
        {
            
            for (int p1 = p0+1; p1 < points.length - 2; p1++)
            {
                for (int p2 = p1+1; p2 < points.length -1; p2++)
                {
                    for (int p3 = p2+1; p3 < points.length; p3++)
                    {
                        if (areCollinear(points[p0], points[p1], points[p2], points[p3]))
                        {
                            StdOut.println(points[p0] + " -> " + 
                                           points[p1] + " -> " + 
                                           points[p2] + " -> " + 
                                           points[p3]);
                                           
                            points[p0].drawTo(points[p1]);
                            points[p1].drawTo(points[p2]);
                            points[p2].drawTo(points[p3]);
                        }
                    }
                }
            }
        }    
    }
    
    private static boolean areCollinear(Point p0, Point p1, Point p2, Point p3)
    {
        double slope2 = p1.slopeTo(p2);
        return (p0.slopeTo(p1) == slope2) && (slope2 == p2.slopeTo(p3));
    }
    
    public static void Swap(int i, int j)
    {
        Point t = points[i];
        points[i] = points[j];
        points[j] = t;
    }
            
    private static void SortPoints(int start, int end)
    {
        int n = end - start;
        if (n > 2)
        {
            int i = start+1;
            int eq = start;
            int gt = end;

            while (i < gt)
            {
                int r = points[i].compareTo(points[start]);
                if (r > 0) Swap(eq++, i++);
                else if (r < 0) Swap(--gt, i);
                else i++;
            }
            
            SortPoints(start, eq);
            SortPoints(gt, end);
        }
        if (n == 2)
        {
            if (points[start].compareTo(points[start+1]) < 0)
            {
                Swap(start, start+1);
            }
        }
    }
    
    private static Point points[];
}