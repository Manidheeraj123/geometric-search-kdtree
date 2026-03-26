# AI Assistance Log

| Component / Task | Prompt Used | Modifications Made |
|------------------|-------------|---------------------|
| `KdTree.insert()` | "guide step-by-step through implementing recursive `insert()`" | AI provided standard top-down recursive insertion with alternating boolean flags and bounding box tracking. |
| `KdTree.nearest()` | "guide me step-by-step through implementing the recursive nearest() method with distance pruning" | AI provided optimal recursive nearest-neighbor logic with subtree-ordering and distance-squared pruning. |
| `KdTree.contains()` | "implement contains() method step-by-step using recursive helper" | AI implemented public `contains(Point2D p)` with private recursive helper `contains(Node n, Point2D p, boolean useX)` that mirrors insert's dimension alternation (X at even levels, Y at odd levels) and returns true on exact match or false on null node. |
