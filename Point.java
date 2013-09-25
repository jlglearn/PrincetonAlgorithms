/*************************************************************************
 * Name:
 * Email:
 *
 * Compilation:  javac Point.java
 * Execution:
 * Dependencies: StdDraw.java
 *
 * Description: An immutable data type for points in the plane.
 *
 *************************************************************************/

import java.util.Comparator;

public class Point implements Comparable<Point> {

    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER = new BySlopeOrder(this.x, this.y);       // YOUR DEFINITION HERE

    private final int x;                              // x coordinate
    private final int y;                              // y coordinate

    // create the point (x, y)
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    // plot this point to standard drawing
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(Point p) {
        double dx = p.x - this.x;
        double dy = p.y - this.y;
        
        if ((dx == 0) && (dy == 0)) return -1 * Double.POSITIVE_INFINITY;
        if (dx == 0) return Double.POSITIVE_INFINITY;
        if (dy == 0) return 0;
        return dy / dx;
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point p) {
    if (p.y < this.y) return -1;
    if (p.y > this.y) return 1;
    if (p.x < this.x) return -1;
    if (p.x > this.x) return 1;
    return 0;
    }

    // return string representation of this point
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }
    
    private static class BySlopeOrder implements Comparator<Point>
    {
        public BySlopeOrder(int x, int y)
        {
            this.x = x;
            this.y = y;
        }
        
        public int compare(Point p, Point q)
        {
            double dxp = p.x - this.x;
            double dyp = p.y - this.y;
            double dxq = q.x - this.x;
            double dyq = q.y - this.y;
            double slopep, slopeq;
            
            if ((dxp == 0) && (dyp == 0))
                slopep = -1 * Double.POSITIVE_INFINITY;
            else if (dxp == 0)
                slopep = Double.POSITIVE_INFINITY;
            else if (dyp == 0)
                slopep = 0.0;
            else
                slopep = dyp/dxp;
                
            if ((dxq == 0) && (dyq == 0))
                slopeq = -1 * Double.POSITIVE_INFINITY;
            else if (dxq == 0)
                slopeq = Double.POSITIVE_INFINITY;
            else if (dyq == 0)
                slopeq = 0.0;
            else slopeq = dyq/dxq;
            
            if (slopep < slopeq) return -1;
            if (slopep > slopeq) return 1;
            return 0;
        }
        
        private int x;
        private int y;
    }

    // unit test
    public static void main(String[] args) {
        /* YOUR CODE HERE */
    }
}
