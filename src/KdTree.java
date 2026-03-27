import java.util.ArrayList;
import java.util.List;

public class KdTree {
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
        // We assume bounds are [0.0, 1.0] as standard for Sedgewick projects
        root = insert(root, p, true, 0.0, 0.0, 1.0, 1.0);
    }

    private Node insert(Node n, Point2D p, boolean useX, double xmin, double ymin, double xmax, double ymax) {
        if (n == null) {
            size++;
            return new Node(p, new RectHV(xmin, ymin, xmax, ymax));
        }
        
        // If exact same point, don't insert duplicate
        if (n.getPoint().equals(p)) {
            return n;
        }

        // Compare based on active dimension
        if (useX) { // Level 0, 2, 4... -> compare X
            if (p.x() < n.getPoint().x()) {
                // Left subtree: limit max X
                n.setLb(insert(n.getLb(), p, false, xmin, ymin, n.getPoint().x(), ymax));
            } else {
                // Right subtree: limit min X
                n.setRt(insert(n.getRt(), p, false, n.getPoint().x(), ymin, xmax, ymax));
            }
        } else { // Level 1, 3, 5... -> compare Y
            if (p.y() < n.getPoint().y()) {
                // Bottom subtree: limit max Y
                n.setLb(insert(n.getLb(), p, true, xmin, ymin, xmax, n.getPoint().y()));
            } else {
                // Top subtree: limit min Y
                n.setRt(insert(n.getRt(), p, true, xmin, n.getPoint().y(), xmax, ymax));
            }
        }
        return n;
    }

    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Point cannot be null");
        return contains(root, p, true);
    }

    private boolean contains(Node n, Point2D p, boolean useX) {
        // Base case: reached a null node, point not found
        if (n == null) {
            return false;
        }

        // Check if current node's point matches exactly
        if (n.getPoint().equals(p)) {
            return true;
        }

        // Traverse based on active dimension (alternating X and Y)
        if (useX) { // Level 0, 2, 4... -> compare X
            if (p.x() < n.getPoint().x()) {
                return contains(n.getLb(), p, false); // Go left
            } else {
                return contains(n.getRt(), p, false); // Go right
            }
        } else { // Level 1, 3, 5... -> compare Y
            if (p.y() < n.getPoint().y()) {
                return contains(n.getLb(), p, true); // Go bottom
            } else {
                return contains(n.getRt(), p, true); // Go top
            }
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException("Rectangle cannot be null");
        List<Point2D> result = new ArrayList<>();
        range(root, rect, result, true);
        return result;
    }

    private void range(Node n, RectHV rect, List<Point2D> result, boolean useX) {
        // Base case: null node
        if (n == null) {
            return;
        }

        // Pruning: if query rectangle doesn't intersect this node's bounding rectangle, skip branch
        if (!rect.intersects(n.getRect())) {
            return;
        }

        // If query rectangle contains the current node's point, add it
        if (rect.contains(n.getPoint())) {
            result.add(n.getPoint());
        }

        // Recursively search both subtrees
        range(n.getLb(), rect, result, !useX);
        range(n.getRt(), rect, result, !useX);
    }

    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException("Point cannot be null");
        if (isEmpty()) return null;
        return nearest(root, p, root.getPoint(), true);
    }

    private Point2D nearest(Node n, Point2D p, Point2D closest, boolean useX) {
        if (n == null) {
            return closest;
        }

        // Pruning rule: if the closest known point is closer than the bounding rectangle of this node, 
        // we completely ignore this branch and its children!
        if (closest != null && closest.distanceSquaredTo(p) <= n.getRect().distanceSquaredTo(p)) {
            return closest;
        }

        // Update closest point if current node is closer
        if (n.getPoint().distanceSquaredTo(p) < closest.distanceSquaredTo(p)) {
            closest = n.getPoint();
        }

        // Determine which side of the splitting line the query point falls on.
        // We always search the same-side subtree FIRST because it's most likely to contain the closest point.
        Node first, second;
        if (useX) {
            if (p.x() < n.getPoint().x()) {
                first = n.getLb();   second = n.getRt();
            } else {
                first = n.getRt();   second = n.getLb();
            }
        } else {
            if (p.y() < n.getPoint().y()) {
                first = n.getLb();   second = n.getRt();
            } else {
                first = n.getRt();   second = n.getLb();
            }
        }

        // Search the first (closer) subtree
        closest = nearest(first, p, closest, !useX);
        // Search the second (further) subtree. It might get pruned if the first search finds a really close point!
        closest = nearest(second, p, closest, !useX);

        return closest;
    }
}
