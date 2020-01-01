package oop.ex4.data_structures;

import java.util.NoSuchElementException;

/**
 * implements an iterator over a binary tree
 */

class TreeIterator implements java.util.Iterator<Integer> {

    /**
    the current node in the iteration
     */
    private TreeNode current;

    /**
     * the default iterator constructor
     *
     * @param minimal the smallest node on the tree (leftmost)
     */
    TreeIterator(TreeNode minimal) {
        this.current = minimal;
    }

    /**
     * returns the next successor of the current node
     *
     * @return the next successor of the current node
     */

    @Override
    public Integer next() {
        int returnVal = current.data;
        if (!hasNext()){
            throw new NoSuchElementException();
        }
        current = current.successor();
        return returnVal;
    }

    /**
     * checks if there's a next element
     *
     * @return true if has next
     */

    @Override
    public boolean hasNext() {
        return current != null;
    }
}
