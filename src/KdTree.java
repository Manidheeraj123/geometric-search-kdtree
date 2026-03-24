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
        // TODO: Implement contains logic
        return false;
    }
}
