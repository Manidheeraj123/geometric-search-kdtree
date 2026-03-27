import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdOut;

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
        StdOut.println("\nQuery Point: " + queryPoint);
        StdOut.println("Nearest in PointSET: " + brute.nearest(queryPoint));
        StdOut.println("Nearest in KdTree:   " + kdtree.nearest(queryPoint));

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
    }
}
