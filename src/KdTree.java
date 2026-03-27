import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import java.util.ArrayList;
import java.util.List;

public class KdTree {
    private static class Node {
        private final Point2D p;      // the point
        private final RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree

        public Node(Point2D p, RectHV rect) {
            this.p = p;
            this.rect = rect;
        }
    }

    private Node root;
    private int size;

    public KdTree() {
        this.root = null;
        this.size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Point cannot be null");
        root = insert(root, p, true, 0.0, 0.0, 1.0, 1.0);
    }

    private Node insert(Node n, Point2D p, boolean useX, double xmin, double ymin, double xmax, double ymax) {
        if (n == null) {
            size++;
            return new Node(p, new RectHV(xmin, ymin, xmax, ymax));
        }
        
        if (n.p.equals(p)) {
            return n;
        }

        if (useX) { 
            if (p.x() < n.p.x()) {
                n.lb = insert(n.lb, p, false, xmin, ymin, n.p.x(), ymax);
            } else {
                n.rt = insert(n.rt, p, false, n.p.x(), ymin, xmax, ymax);
            }
        } else { 
            if (p.y() < n.p.y()) {
                n.lb = insert(n.lb, p, true, xmin, ymin, xmax, n.p.y());
            } else {
                n.rt = insert(n.rt, p, true, xmin, n.p.y(), xmax, ymax);
            }
        }
        return n;
    }

    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Point cannot be null");
        return contains(root, p, true);
    }

    private boolean contains(Node n, Point2D p, boolean useX) {
        if (n == null) {
            return false;
        }

        if (n.p.equals(p)) {
            return true;
        }

        if (useX) { 
            if (p.x() < n.p.x()) {
                return contains(n.lb, p, false); 
            } else {
                return contains(n.rt, p, false); 
            }
        } else { 
            if (p.y() < n.p.y()) {
                return contains(n.lb, p, true); 
            } else {
                return contains(n.rt, p, true); 
            }
        }
    }

    public void draw() {
        draw(root, true);
    }

    private void draw(Node n, boolean useX) {
        if (n == null) return;
        
        // Draw the point itself in regular black
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        n.p.draw();
        
        // Draw the splitting line depending on the active dimension
        StdDraw.setPenRadius(); // reset to default thin line
        if (useX) {
            StdDraw.setPenColor(StdDraw.RED);
            // vertical line bounded by the node's rectangle limits
            StdDraw.line(n.p.x(), n.rect.ymin(), n.p.x(), n.rect.ymax());
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            // horizontal line bounded by the node's rectangle limits
            StdDraw.line(n.rect.xmin(), n.p.y(), n.rect.xmax(), n.p.y());
        }
        
        // Recursively draw subtrees
        draw(n.lb, !useX);
        draw(n.rt, !useX);
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException("Rectangle cannot be null");
        List<Point2D> result = new ArrayList<>();
        range(root, rect, result, true);
        return result;
    }

    private void range(Node n, RectHV rect, List<Point2D> result, boolean useX) {
        if (n == null) {
            return;
        }

        if (!rect.intersects(n.rect)) {
            return;
        }

        if (rect.contains(n.p)) {
            result.add(n.p);
        }

        range(n.lb, rect, result, !useX);
        range(n.rt, rect, result, !useX);
    }

    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Point cannot be null");
        if (isEmpty()) return null;
        return nearest(root, p, root.p, true);
    }

    /**
     * Draws the KdTree structure to standard drawing.
     * Points are drawn in black, vertical splits in red, horizontal splits in blue.
     */
    public void draw() {
        draw(root, true);
    }

    private void draw(Node n, boolean useX) {
        if (n == null) {
            return;
        }

        // Draw the point in black
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        n.p.draw();

        // Draw the subdivision line
        if (useX) {
            // Vertical line at x = n.p.x()
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setPenRadius();
            StdDraw.line(n.p.x(), n.rect.ymin(), n.p.x(), n.rect.ymax());
        } else {
            // Horizontal line at y = n.p.y()
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.setPenRadius();
            StdDraw.line(n.rect.xmin(), n.p.y(), n.rect.xmax(), n.p.y());
        }

        // Recursively draw left/bottom and right/top subtrees
        draw(n.lb, !useX);
        draw(n.rt, !useX);
    }

    private Point2D nearest(Node n, Point2D p, Point2D closest, boolean useX) {
        if (n == null) {
            return closest;
        }

        if (closest != null && closest.distanceSquaredTo(p) <= n.rect.distanceSquaredTo(p)) {
            return closest;
        }

        if (n.p.distanceSquaredTo(p) < closest.distanceSquaredTo(p)) {
            closest = n.p;
        }

        Node first, second;
        if (useX) {
            if (p.x() < n.p.x()) {
                first = n.lb;   second = n.rt;
            } else {
                first = n.rt;   second = n.lb;
            }
        } else {
            if (p.y() < n.p.y()) {
                first = n.lb;   second = n.rt;
            } else {
                first = n.rt;   second = n.lb;
            }
        }

        closest = nearest(first, p, closest, !useX);
        closest = nearest(second, p, closest, !useX);

        return closest;
    }
}
