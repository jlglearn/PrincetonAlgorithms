import java.util.Arrays;

public class Fast
{
    public Fast()
    {
    }
    
    public static void main(String argv[])
    {     
        ReadPoints();
        SortPoints();
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

        for (int p0 = 0; p0 < points.length; p0++)
        {                    
        
            // first create a working copy of the points
            // Note that all lines involving points with indices < than index of current point
            // have already been processed, so we don't need to explore them anymore
            
            Point[] p = new Point[points.length - p0];            
            for (int j = 0, i = p0; i < points.length; j++, i++)
                p[j] = points[i];
                    
            Arrays.sort(p, points[p0].SLOPE_ORDER);
            
            int i = 1;

            while ((i < p.length) && (p[0].compareTo(p[i]) == 0)) i++;
                        
            // i is either past the end of the array or
            // in the typical case points to the first
            // point not equal to the reference point
            
            if (i >= p.length) continue;
            
            double lastSlope = -1 * Double.POSITIVE_INFINITY;
            int streak = 1;
            
            for (int startCollinear = i; i < p.length; i++)
            {
                double slope = p[0].slopeTo(p[i]);
                
                if (slope == lastSlope)
                    streak++;
                
                if ((streak >= 3) &&
                    ((i == p.length-1) || (slope != lastSlope)))
                {
                    Point[] cp = new Point[streak+1];
                    
                    // the first collinear point is the reference point
                    cp[0] = p[0];
                    
                    // copy the rest of the collinear points
                    for (int j = 1, k = startCollinear; j < streak+1; j++, k++)
                        cp[j] = p[k];
                    
                    Arrays.sort(cp);
                    PrintCollinearPoints(cp);
                }
                
                if (slope != lastSlope)
                {
                    // reference point is first collinear point, i is second collinear point
                    streak = 1;
                    startCollinear = i;
                }
                
                lastSlope = slope;
            } 
        }
    }
    
    private static void PrintCollinearPoints(Point[] p)
    {
        for (int i = 0; i < p.length; i++)
            StdOut.print(((i > 0) ? " -> " : "") + p[i]);
            
        StdOut.println();
        
        for (int i = 0; i < p.length - 1; i++)
            p[i].drawTo(p[i+1]);
    }
            
    private static void PrintPoints()
    {
        StdOut.println("Read " + points.length + " points.");
        
        for (int i = 0; i < points.length; i++)
        {
            StdOut.println("[" + i + "]: " + points[i]);
        }
            
    }
    
    private static void SortPoints(Point p)
    {
        Arrays.sort(points, p.SLOPE_ORDER);
    }
    
    private static void SortPoints()
    {
        Arrays.sort(points);
    }
    
    private static Point points[];

}