import java.util.Arrays;

public class Fast
{
    private static Point[] points;
    
    public Fast()
    {
    }
    
    public static void main(String[] argv)
    {
        if (argv.length == 0) readPoints();
        else readPoints(argv[0]);
            
        sortPoints();
        prepareDraw();
        drawPoints();
        findCollinearPoints();
    }
    
    private static void prepareDraw()
    {
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
    }
    
    
    private static void readPoints(String filename)
    {
        In inStream = new In(filename);
        int nPoints = inStream.readInt();
        points = new Point[nPoints];
        
        for (int i = 0; i < nPoints; i++)
        {
            int x = inStream.readInt();
            int y = inStream.readInt();
            points[i] = new Point(x, y);
        } 
    }
    
    private static void readPoints()
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
    
    private static void drawPoints()
    {
        for (int i = 0; i < points.length; i++)
        {
            points[i].draw();
        }
    }
    
    private static void findCollinearPoints()
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
            int startCollinear = i;
            
            for ( ; i < p.length; i++)
            {
                double slope = p[0].slopeTo(p[i]);
                
                if (slope == lastSlope)
                    streak++;
                
                if ((streak >= 3)
                    && ((i == p.length-1) || (slope != lastSlope)))
                {
                    Point[] cp = new Point[streak+1];
                    
                    // the first collinear point is the reference point
                    cp[0] = p[0];
                    
                    // copy the rest of the collinear points
                    for (int j = 1, k = startCollinear; j < streak+1; j++, k++)
                        cp[j] = p[k];
                    
                    Arrays.sort(cp);
                    printCollinearPoints(cp);
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
    
    private static void printCollinearPoints(Point[] p)
    {
        for (int i = 0; i < p.length; i++)
        {
            String s = " -> ";
            if (i == 0) s = "";
            
            StdOut.print(s + p[i]);
        }
            
        StdOut.println();
        p[0].drawTo(p[p.length - 1]);
    }
            
    private static void printPoints()
    {
        StdOut.println("Read " + points.length + " points.");
        
        for (int i = 0; i < points.length; i++)
        {
            StdOut.println("[" + i + "]: " + points[i]);
        }
            
    }
    
    private static void sortPoints(Point p)
    {
        Arrays.sort(points, p.SLOPE_ORDER);
    }
    
    private static void sortPoints()
    {
        Arrays.sort(points);
    }

}