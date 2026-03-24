public class RectHV {
    private final double xmin, ymin, xmax, ymax;

    public RectHV(double xmin, double ymin, double xmax, double ymax) {
        if (xmin > xmax || ymin > ymax) {
            throw new IllegalArgumentException("Invalid rectangle coordinates");
        }
        this.xmin = xmin;
        this.ymin = ymin;
        this.xmax = xmax;
        this.ymax = ymax;
    }

    public double xmin() { return xmin; }
    public double ymin() { return ymin; }
    public double xmax() { return xmax; }
    public double ymax() { return ymax; }

    public boolean contains(Point2D p) {
        return (p.x() >= xmin && p.x() <= xmax && p.y() >= ymin && p.y() <= ymax);
    }

    public double distanceSquaredTo(Point2D p) {
        double dx = 0.0, dy = 0.0;
        if (p.x() < xmin) dx = xmin - p.x();
        else if (p.x() > xmax) dx = p.x() - xmax;
        
        if (p.y() < ymin) dy = ymin - p.y();
        else if (p.y() > ymax) dy = p.y() - ymax;
        
        return dx * dx + dy * dy;
    }
}
