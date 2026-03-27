# Kd-Tree for Geometric Search

A complete university project demonstrating a 2D Kd-Tree implementation and comparing it against a brute-force approach, meeting all Sedgewick API standards.

## Project Deliverables Completed
1. **Geometric Data Structures**: Fully encapsulated `KdTree` using a private static inner `Node` class.
2. **Brute-Force Baseline**: Implemented `PointSET` using a Red-Black BST (`java.util.TreeSet`).
3. **Core Functionality**: Recursive algorithms implemented for `insert()`, `contains()`, `range()`, and `nearest()` utilizing mathematical bounding boxes natively inside the tree to prune redundant recursive paths in sub-linear logarithmic time.
4. **Visual Rendering**: Full support for standard drawing, including dynamic Red/Blue subdivision partition lines that adapt precisely to bounding sub-domains.
5. **Autograder Compliance**: Strict usage of the `edu.princeton.cs.algs4` library without custom API class overrides.
6. **Testing**: Comprehensive `Main.java` test harness evaluating structural insertions, distance mathematics, and range geometry.

## Git & AI Collaboration Workflow
This project was strictly developed under a professional GitHub flow:
- All features developed in isolated `feature/` branches.
- Code structurally reviewed via Pull Requests before merging to `main`.
- See `AI_PROMPTS.md` for our transparent AI interaction log.

## Running the Application
Ensure the `algs4.jar` is locally added to your build path. Run `Main.java` to execute the fundamental comparison test between the brute-force baseline and the optimized Kd-Tree.
