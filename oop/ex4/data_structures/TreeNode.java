package oop.ex4.data_structures;

/**
 * represents a single node in an integer binary tree
 */
class TreeNode {
    /**
     * the initial size of every node
     */
    private static final int INITIAL_SIZE = 1;

    /**
     * a pointer to the left child of the node
     */
    TreeNode left;
    /**
     * a pointer to the right child of the node
     */
    TreeNode right;
    /**
     * the node's size - number of nodes in it's subtree
     */
    int size;
    /**
     * the data that the node contains - int's in this implementation
     */
    int data;
    /**
     * a pointer to the node's father
     */
    TreeNode father;
    /**
     * the height of the node - distance to it's furthest child. zero when leaf
     */
    int height;

    /**
     * constructor to a tree node
     *
     * @param data   the data to insert to the node
     * @param father the node's father pointer
     */
    TreeNode(int data, TreeNode father) {
        this.data = data;
        this.father = father;
        this.size = INITIAL_SIZE;
    }

    /**
     * @return the node's successor - with the minimal larger data
     */
    TreeNode successor() {
        if (right == null) {
            TreeNode ancestor = father;
            while (ancestor != null) {
                if (ancestor.data > data) {
                    return ancestor;
                }
                ancestor = ancestor.father;
            }
            // if all fathers are smaller, its the maximal node.
            return null;
        }

        // assuming there's a right child
        else {
            TreeNode descendant = right;
            while (descendant.left != null) {
                descendant = descendant.left;
            }
            return descendant;
        }
    }

    /**
     * @return the height of the right child. default -1 if there is no child
     */
    private int getRightHeight() {
        if (this.right == null) {
            return -1;
        }
        return this.right.height;
    }

    /**
     * @return the height of the left child. default -1 if there is no child
     */
    private int getLeftHeight() {
        if (this.left == null) {
            return -1;
        }
        return this.left.height;
    }

    /**
     * updates the height - the max of the children's height plus 1.
     */
    void updateHeight() {
        height = Math.max(getRightHeight(), getLeftHeight()) + 1;
    }

    /**
     * @return the right child size - zero if null
     */
    private int getRightSize() {
        if (right == null) {
            return 0;
        }
        return right.size;
    }

    /**
     * @return the left child size - zero if null
     */
    private int getLeftSize() {
        if (left == null) {
            return 0;
        }
        return left.size;
    }

    /**
     * updates the node's size - sum of child's size plus 1
     */
    void updateSize() {
        size = getRightSize() + getLeftSize() + 1;
    }

    /**
     * checks for the number of children of the node
     *
     * @return the number of children of the node
     */
    int getChildrenNumber() {
        int count = 0;
        if (right != null) {
            count++;
        }
        if (left != null) {
            count++;
        }
        return count;
    }

    /**
     * updates the size and height of a node
     */
    void updateStats() {
        updateSize();
        updateHeight();
    }

    /**
     * checks the difference between the right and left children's height - for balance purposes
     *
     * @return the difference as an int.
     */
    int balanceFactor() {
        return getRightHeight() - getLeftHeight();
    }


}
