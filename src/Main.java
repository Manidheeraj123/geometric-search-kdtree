import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;

public class Main {
    public static void main(String[] args) {
        // Initialize brute-force and optimized data structures
        PointSET brute = new PointSET();
        KdTree kdtree = new KdTree();

        // Standard test points to ensure the trees work properly
        Point2D[] points = {
            new Point2D(0.7, 0.2),
            new Point2D(0.5, 0.4),
            new Point2D(0.2, 0.3),
            new Point2D(0.4, 0.7),
            new Point2D(0.9, 0.6)
        };

        // Insert into both data structures
        for (Point2D p : points) {
            brute.insert(p);
            kdtree.insert(p);
        }

        StdOut.println("Total points inserted: " + kdtree.size());

        // Test Nearest Neighbor
        Point2D queryPoint = new Point2D(0.8, 0.3);
        Point2D nearestPoint = kdtree.nearest(queryPoint);
        StdOut.println("\nQuery Point: " + queryPoint);
        StdOut.println("Nearest in PointSET: " + brute.nearest(queryPoint));
        StdOut.println("Nearest in KdTree:   " + nearestPoint);

        // Test Range Search
        RectHV queryRect = new RectHV(0.1, 0.1, 0.6, 0.6);
        StdOut.println("\nPoints inside rectangle " + queryRect + ":");
        
        StdOut.print("PointSET: ");
        for (Point2D p : brute.range(queryRect)) {
            StdOut.print(p + " ");
        }
        StdOut.println();

        StdOut.print("KdTree:   ");
        for (Point2D p : kdtree.range(queryRect)) {
            StdOut.print(p + " ");
        }
        StdOut.println();
        
        // ----------------------------------------------------
        // Test Visual Rendering (draw)
        // ----------------------------------------------------
        StdOut.println("\nRendering KdTree visually (Check the pop-up window)...");
        
        // Standard Setup for Sedgewick Princeton API
        StdDraw.setXscale(0, 1);
        StdDraw.setYscale(0, 1);
        StdDraw.enableDoubleBuffering();
        StdDraw.clear();
        
        // 1. Draw the KdTree points and alternating Red/Blue partition lines!
        kdtree.draw();
        
        // 2. Draw the Range Query Rectangle (thick black outline)
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.005);
        queryRect.draw();
        
        // 3. Draw the Nearest Query Point in GREEN to help visualize it
        StdDraw.setPenColor(StdDraw.GREEN);
        StdDraw.setPenRadius(0.02);
        queryPoint.draw();
        
        // Display everything on the canvas
        StdDraw.show();
    }
}
