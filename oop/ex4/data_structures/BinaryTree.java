package oop.ex4.data_structures;

import java.util.Iterator;

/**
 * An implementation of the binary tree data structure
 */
public class BinaryTree implements Iterable<Integer> {

    /**
     * the root node of the AVL tree
     */
    TreeNode root;

    /**
     * The default constructor.
     */
    public BinaryTree() {

    }

    /**
     * A constructor that builds a new binary tree containing all unique values in the input array.
     *
     * @param data the values to add to tree.
     */
    public BinaryTree(int[] data) {
        if (data != null) {
            for (int val : data) {
                add(val);
            }
        }
    }

    /**
     * A copy constructor that creates a deep copy of the given BinaryTree.
     * The new tree contains all the values of the given tree, but not necessarily in the same structure.
     *
     * @param tree The binary tree to be copied.
     */
    public BinaryTree(BinaryTree tree) {
        if (tree != null) {
            root = copyTree(tree.root, null);
        }
    }

    /**
     * Add a new node with the given key to the tree.
     *
     * @param newValue the value of the new node to add.
     * @return true if the value to add is not already in the tree and it was successfully added, false otherwise.
     */
    public boolean add(int newValue) {

        if (root == null) {
            root = new TreeNode(newValue, null);
            return true;
        }

        TreeNode closest = getClosestNode(newValue);

        if (newValue == closest.data) {
            return false;
        }

        if (newValue < closest.data) {
            // assuming left child is empty
            closest.left = new TreeNode(newValue, closest);
        } else {
            // assuming right child is empty
            closest.right = new TreeNode(newValue, closest);
        }

        updateTree(closest);

        return true;
    }

    /**
     * Check whether the tree contains the given input value.
     *
     * @param searchVal value to search for
     * @return if val is found in the tree, return the depth of the node (0 for the root) with the given value if
     * it was found in the tree, -1 otherwise.
     */
    public int contains(int searchVal) {
        TreeNode current = root;
        int depth = 0;

        while (current != null) {
            if (current.data == searchVal) {
                return depth;
            }

            if (searchVal < current.data) {
                current = current.left;
            } else {
                current = current.right;
            }

            depth++;
        }
        // exits when reaches null - so the data is not found
        return -1;
    }

    /**
     * Removes the node with the given value from the tree, if it exists.
     *
     * @param toDelete the value to remove from the tree.
     * @return true if the given value was found and deleted, false otherwise.
     */
    public boolean delete(int toDelete) {

        TreeNode deleteNode = getClosestNode(toDelete);
        // if the data is not in the tree
        if (deleteNode == null || deleteNode.data != toDelete) {
            return false;
        }

        // if there is only one child, adding the child with the child
        if (deleteNode.getChildrenNumber() <= 1) {
            if (deleteNode.right != null) {
                switchChildOfMine(deleteNode, deleteNode.right);
            } else {
                switchChildOfMine(deleteNode, deleteNode.left);
            }

            TreeNode father = deleteNode.father;
            if (father != null) {
                updateTree(father);
            }

        }
        else {
            TreeNode successor = deleteNode.successor();
            deleteNode.data = successor.data;
            // deleting the node of the successor - after moving his data to the deleteNode
            // assuming successor doesn't have a left child

            switchChildOfMine(successor, successor.right);
            updateTree(successor.father);
        }

        return true;
    }

    /**
     * updates the tree's size and height after an add or delete - and calls the fix method.
     *
     * @param updateNode the node to start the update from
     */
    private void updateTree(TreeNode updateNode) {
        TreeNode fixNode = updateNode;
        // updates the stats for every binary tree
        while (updateNode != null) {
            updateNode.updateSize();
            updateNode.updateHeight();
            updateNode = updateNode.father;
        }
        // performing fixes in every type of the binary tree
        fixTree(fixNode);
    }

    /**
     * a method that is overridden by sub-classes - and performs the fix of the tree.
     * in binary tree - an empty method
     *
     * @param fixNode the node where the change occurred - where the height and size changed.
     */
    void fixTree(TreeNode fixNode) {
        // can be implemented in several tree types to balance the tree
    }

    /**
     * returns the number of nodes in the tree.
     * @return the number of nodes in the tree.
     */
    public int size() {
        if (root == null) {
            return 0;
        }
        return root.size;
    }

    /**
     * returns an iterator for the binary tree
     * @return an iterator for the binary Tree.
     * The returned iterator iterates over the tree nodes in an ascending order,
     * and does NOT implement the remove() method.
     */
    public Iterator<Integer> iterator() {
        return new TreeIterator(getMinNode());
    }

    /**
     * replaces two nodes - updates father and child pointers
     *
     * @param previousChild the old node to replace
     * @param newChild      the new node to take it's place
     */
    void switchChildOfMine(TreeNode previousChild, TreeNode newChild) {
        TreeNode father = previousChild.father;
        // handle previous node is root
        if (father == null) {
            root = newChild;
            // in case we're deleting the root and there are no more nodes
            if (newChild != null) {
                newChild.father = null;
            }
            return;
        }
        // checking if the previous is the right or left child of the father
        if (father.left == previousChild) {
            father.left = newChild;

        } else {
            father.right = newChild;
        }
        // avoid null pointer
        if (newChild != null) {
            newChild.father = father;
        }
    }

    /**
     * @return the minimal node in the tree
     */
    private TreeNode getMinNode() {
        TreeNode current = root;
        if (current == null) return null;

        while (current.left != null) {
            current = current.left;
        }
        return current;
    }

    /**
     * returns the closest node to the current value
     * if the value has a node - returns its node
     * otherwise - returns that will be the father of the new value in the tree
     *
     * @param newValue the value to compare in the tree
     * @return the closest node to the value - its node or its father to be
     */
    private TreeNode getClosestNode(int newValue) {
        TreeNode current = root;
        while (current != null) {
            if (newValue == current.data) {
                break;
            }

            if (newValue < current.data) {
                if (current.left == null) {
                    break;
                }
                current = current.left;

            } else {
                if (current.right == null) {
                    break;
                }
                current = current.right;
            }
        }
        return current;
    }

    /**
     * a recursive function that copies a binary tree
     *
     * @param originalCur the current node that is copied
     * @param copyFather  it's father's copy in the new created tree - to connect them
     * @return the current node connected to all of it's subtree
     */
    private static TreeNode copyTree(TreeNode originalCur, TreeNode copyFather) {
        if (originalCur == null){
            return null;
        }
        TreeNode newNode = new TreeNode(originalCur.data, copyFather);
        // updates the nodes data
        newNode.size = originalCur.size;
        newNode.height = originalCur.height;
        // recursive calls to children
        newNode.left = copyTree(originalCur.left, newNode);
        newNode.right = copyTree(originalCur.right, newNode);
        return newNode;

    }

    /**
     * Calculates the maximum number of nodes in an binary tree of height h.
     *
     * @param h the height of the tree (a non-negative number) in question.
     * @return the maximum number of nodes in an AVL tree of the given height.
     */
    public static int findMaxNodes(int h) {
        return (int) (Math.pow(2, h + 1) - 1);
    }

}
