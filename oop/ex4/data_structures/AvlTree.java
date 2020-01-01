package oop.ex4.data_structures;

/**
 * An implementation of the AVL tree data structure.
 */
public class AvlTree extends BinaryTree {

    /**
     * The default constructor.
     */
    public AvlTree() {
        super();
    }

    /**
     * A constructor that builds a new AVL tree containing all unique values in the input array.
     *
     * @param data the values to add to tree.
     */
    public AvlTree(int[] data) {
        super(data);
    }

    /**
     * A copy constructor that creates a deep copy of the given AvlTree.
     * The new tree contains all the values of the given tree, but not necessarily in the same structure.
     *
     * @param tree The AVL tree to be copied.
     */
    public AvlTree(AvlTree tree) {
        super(tree);
    }

    /**
     * Calculates the minimum number of nodes in an AVL tree of height h.
     * the number is a variation of the fibonacci series - determined by the AVL property
     *
     * @param h the height of the tree (a non-negative number) in question.
     * @return the minimum number of nodes in an AVL tree of the given height.
     */
    public static int findMinNodes(int h) {
        // size for trees with height 0, 1
        int low = 1;
        int high = 2;

        for (int i = 0; i < h; i++) {
            int sum = low + high + 1;
            low = high;
            high = sum;
        }

        return low;
    }

    /**
     * checks for AVL property violations - and fixes them if found.
     *
     * @param updateNode the node to start the iteration to it's ancestors.
     */
    void fixTree(TreeNode updateNode) {
        TreeNode current = updateNode;
        while (current != null) {
            // updates above after change
            checkAvlViolation(current);
            current.updateStats();
            current
                    = current.father;
        }

    }

    /**
     * checks for all kinds of violations to the AVL tree property.
     * contains RR, RL, LL, LR violations
     *
     * @param current the node to check for violations
     */
    private void checkAvlViolation(TreeNode current) {
        // checking for R violations
        if (current.balanceFactor() > 1) {
            if (current.right.balanceFactor() >= 0) {
                // RR
                rightRotation(current);
            } else {
                // RL
                leftRotation(current.right);
                rightRotation(current);
            }
            // checking for L violations
        } else if (current.balanceFactor() < -1) {
            if (current.left.balanceFactor() <= 0) {
                // LL
                leftRotation(current);
            } else {
                // LR
                rightRotation(current.left);
                leftRotation(current);
            }
        }
    }

    /**
     * performs a left rotation to fix the AVL property violation
     *
     * @param current the node to fix
     */
    private void leftRotation(TreeNode current) {
        // the node to replace the current
        TreeNode pivot = current.left;
        // switching the between the pivot and the current
        switchChildOfMine(current, pivot);
        // connecting the current as the child of the pivot
        connectLeftChild(current, pivot.right);
        connectRightChild(pivot, current);
        // updates their size and height
        current.updateStats();
        pivot.updateStats();
    }

    /**
     * performs a right rotation to fix the AVL property violation
     *
     * @param current the node to fix
     */
    private void rightRotation(TreeNode current) {
        // the node to replace the current
        TreeNode pivot = current.right;
        // switching the between the pivot and the current
        switchChildOfMine(current, pivot);
        // connecting the current as the child of the pivot
        connectRightChild(current, pivot.left);
        connectLeftChild(pivot, current);
        // updates their size and height
        pivot.updateStats();
        current.updateStats();

    }

    /**
     * connects a father a new right child
     * updates both parent and child pointers, and deals with nulls
     *
     * @param father the father to connect
     * @param child  the child to connect
     */
    private void connectRightChild(TreeNode father, TreeNode child) {
        if (father == null) {
            root = child;
            child.father = null;
            return;
        }

        father.right = child;
        if (child != null) {
            child.father = father;
        }
    }

    /**
     * connects a father a new left child
     * updates both parent and child pointers, and deals with nulls
     *
     * @param father the father to connect
     * @param child  the child to connect
     */
    private void connectLeftChild(TreeNode father, TreeNode child) {
        if (father == null) {
            root = child;
            child.father = null;
            return;
        }

        father.left = child;
        if (child != null) {
            child.father = father;
        }
    }

}