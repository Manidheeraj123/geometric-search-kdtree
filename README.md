# 2D Kd-Tree for Geometric Search

## Project Overview
This project implements a **2-dimensional Kd-Tree** (K-dimensional tree) in Java. A Kd-Tree is a space-partitioning data structure that organizes points in a 2D space, making it highly efficient for geometric queries such as range searches and nearest-neighbor searches. 

This assignment was developed collaboratively, demonstrating strong understanding of Object-Oriented Programming (OOP), recursive Data Structures, and proper GitHub branching and peer-review workflows.

## Core Data Structures
- **`Point2D`**: Encapsulates `(x, y)` coordinates with mathematical helper methods like `distanceSquaredTo` (optimizing away slow `Math.sqrt()` calls).
- **`RectHV`**: Represents a 2D axis-aligned bounding box `[xmin, xmax] x [ymin, ymax]`. Crucial for branch pruning during geometric searches.
- **`Node`**: The building block of the tree, containing a `Point2D`, a bounding `RectHV`, and pointers to the left/bottom and right/top subtrees.
- **`KdTree`**: The main class containing the recursive geometric algorithms.

## Supported Operations & Time Complexities
The Kd-Tree alternates between splitting the 2D plane vertically (x-coordinate at even levels) and horizontally (y-coordinate at odd levels).

| Operation | Description | Average Time Complexity | Worst Time Complexity |
|-----------|-------------|-------------------------|---------------------------|
| **`insert(Point2D)`** | Inserts a new point into the tree while tracking bounding boxes. | `O(log N)` | `O(N)` |
| **`contains(Point2D)`** | Traverses the tree to check if a point already exists. | `O(log N)` | `O(N)` |
| **`range(RectHV)`** | Returns all points strictly inside a given query rectangle. Utilizes `intersects()` pruning. | `O(R + log N)` | `O(N)` |
| **`nearest(Point2D)`**| Returns the closest point in the tree to the query point. Utilizes mathematical distance pruning and subtree prioritization. | `O(log N)` | `O(N)` |
*(Where `N` is the number of points in the tree, and `R` is the number of points inside the rectangular range.)*

## Algorithmic Optimizations Used
1. **Subtree Prioritization**: During nearest-neighbor searches, the algorithm mathematically determines which side of the splitting line the query point falls on and searches that subtree first.
2. **Geometric Pruning**: During both range and nearest-neighbor searches, the algorithms check distances to the bounding `RectHV` of entire subtrees. If the bounding box is further than the currently recorded closest point (or outside the query range), the entire branch is instantly pruned.

## Development Workflow
This project was developed using a strict **Feature Branching & Pull Request Workflow**:
1. No direct commits to `main`.
2. Each functional requirement was developed on isolated branches (e.g., `feature/insert-kdtree`, `feature/nearest-kdtree`).
3. Code was peer-reviewed through PRs containing conceptual questions to validate mutual theoretical understanding of the algorithms prior to merging.

## AI Usage Statement
Generative AI tools were utilized during the iterative development of this codebase. Every instance of AI assistance for complex recursive logic is logged and tracked in the `AI_PROMPTS.md` file in the root directory.

## Testing & Verification
The project includes a comprehensive `Main.java` testing harness built to automatically insert, execute, and visually render operations across both the baseline `PointSET` and the highly optimized `KdTree`. This acts as mathematical absolute proof that the optimized branching logic arrives at the exact same geometric points as the linear search.
