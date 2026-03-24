public class Node {
    private Point2D p;
    private RectHV rect;
    private Node lb; // left/bottom subtree
    private Node rt; // right/top subtree

    public Node(Point2D p, RectHV rect) {
        this.p = p;
        this.rect = rect;
    }

    public Point2D getPoint() { return p; }
    public RectHV getRect() { return rect; }
    
    public Node getLb() { return lb; }
    public void setLb(Node lb) { this.lb = lb; }
    
    public Node getRt() { return rt; }
    public void setRt(Node rt) { this.rt = rt; }
}
