import java.util.Arrays;

public class Brute
{
    private static Point[] points;
    
    public Brute()
    {
    }
    
    public static void main(String[] argv)
    {
        if (argv.length == 0) readPoints();
        else readPoints(argv[0]);
        
        sortPoints();
        //printPoints();
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
    
    private static void printPoints()
    {
        StdOut.println("Read " + points.length + " points:");
        for (int i = 0; i < points.length; i++)
        {
            StdOut.println("[" + i + "]:" + points[i]);
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
                            StdOut.println(points[p0] + " -> " 
                                           + points[p1] + " -> " 
                                           + points[p2] + " -> " 
                                           + points[p3]);
                                           
                            points[p0].drawTo(points[p3]);
                        }
                    }
                }
            }
        }    
    }
    
    private static boolean areCollinear(Point p0, Point p1, Point p2, Point p3)
    {
        double slope1 = p0.slopeTo(p1);
        return (slope1 == p0.slopeTo(p2)) && (slope1 == p0.slopeTo(p3));
    }
            
    private static void sortPoints()
    {
        Arrays.sort(points);
    }
}