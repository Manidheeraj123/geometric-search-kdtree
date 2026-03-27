import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

/**
 * A brute-force geometric search data structure for 2D points.
 * Uses a TreeSet to store points and implements naive O(N) linear search
 * for range and nearest-neighbor queries.
 * 
 * This is a reference implementation that trades performance for simplicity.
 * For larger datasets, consider using KdTree for O(log N) query times.
 */
public class PointSET {
    private TreeSet<Point2D> points;

    /**
     * Constructs an empty set of points.
     */
    public PointSET() {
        points = new TreeSet<>();
    }

    /**
     * Returns true if the set is empty.
     *
     * @return true if the set contains no points
     */
    public boolean isEmpty() {
        return points.isEmpty();
    }

    /**
     * Returns the number of points in the set.
     *
     * @return the number of points
     */
    public int size() {
        return points.size();
    }

    /**
     * Adds the point to the set (if it is not already in the set).
     *
     * @param p the point to insert
     * @throws IllegalArgumentException if point is null
     */
    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("Point cannot be null");
        }
        points.add(p);
    }

    /**
     * Returns true if the set contains the point p.
     *
     * @param p the point to check
     * @return true if the set contains p
     * @throws IllegalArgumentException if point is null
     */
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("Point cannot be null");
        }
        return points.contains(p);
    }

    /**
     * Draws all points in the set to standard drawing.
     */
    public void draw() {
        // Optional: can implement drawing of all points in the set
        // For now, leaving empty as requested
    }

    /**
     * Returns all points that are inside the rectangle.
     * 
     * Time complexity: O(N) brute-force linear search through all points.
     *
     * @param rect the query rectangle
     * @return iterable of all points inside rect
     * @throws IllegalArgumentException if rectangle is null
     */
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException("Rectangle cannot be null");
        }
        
        List<Point2D> result = new ArrayList<>();
        
        // Brute-force: iterate through all points and check containment
        for (Point2D p : points) {
            if (rect.contains(p)) {
                result.add(p);
            }
        }
        
        return result;
    }

    /**
     * Returns the nearest point to p in the set.
     * Returns null if the set is empty.
     * 
     * Time complexity: O(N) brute-force linear search through all points.
     *
     * @param p the query point
     * @return the nearest point to p in the set, or null if empty
     * @throws IllegalArgumentException if point is null
     */
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("Point cannot be null");
        }
        
        if (isEmpty()) {
            return null;
        }
        
        Point2D nearest = null;
        double minDistSquared = Double.POSITIVE_INFINITY;
        
        // Brute-force: iterate through all points and track closest
        for (Point2D candidate : points) {
            double distSquared = p.distanceSquaredTo(candidate);
            if (distSquared < minDistSquared) {
                minDistSquared = distSquared;
                nearest = candidate;
            }
        }
        
        return nearest;
    }
}
