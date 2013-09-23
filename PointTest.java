public class PointTest
{
    public static void main(String[] args) {
        
        int nPoints;
        Point[] points;
        
        nPoints = StdIn.readInt();
        points = new Point[nPoints];
        
        for (int i = 0; i < nPoints; i++)
        {
            int x = StdIn.readInt();
            int y = StdIn.readInt();
            points[i] = new Point(x, y);
        }
        
        for (int i = 0; i < nPoints - 3; i++)
        {
            double slope1 = points[i].slopeTo(points[i+1]);
            double slope2 = points[i].slopeTo(points[i+2]);
            double slope3 = points[i].slopeTo(points[i+3]);
            
            if ((slope1 == slope2) && (slope2 == slope3))
            {
                System.out.format("Points %s, %s, %s, %s are collinear (slope=%f)%n",
                                  points[i], points[i+1], points[i+2], points[i+4], slope1);
            }
        }
                
    }    
}