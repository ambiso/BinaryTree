package ads1.ss15.pa;

import java.util.Vector;

/**
 * @author 1427251
 */
public class Tree {
    
    /**This class provides the main storage function of a tree. Apart from
     * keeping track of its children and its own value it also stores the size
     * of its subtree consisting of itself, the left and the right tree
     * @see Tree
     */
    public class Node {
        private Node _left, _right;
        private final int _key;
        private int _size = 1;
        
        public Node(int key) {
            _key = key;
        }
        
        public Node getLeft() {
            return _left;
        }
        
        public Node getRight() {
            return _right;
        }
        
        public int getKey() {
            return _key;
        }
        
        public int getSize() {
            return _size;
        }
        
        public void setLeft(Node newLeft) {
            _left = newLeft;
        }
        
        public void setRight(Node newRight) {
            _right = newRight;
        }
        
        public void setSize(int size) {
            _size = size;
        }
        
        public void inOrder() {
            if(_left != null)
                _left.inOrder();
            System.out.print(" " + _key + "  ");
            if(_right != null)
                _right.inOrder();
        }
        
        private int max(int a, int b) {
            return a > b ? a : b;
        }
        
        public int getHeight() {
            int a = -1, b = -1;
            if(_left != null)
                a = _left.getHeight();
            if(_right != null)
                b = _right.getHeight();
            return max(a, b) + 1;
        }
        
        public boolean exists(int val) {
            if (_key == val)
                return true;
            if(val > _key && _right != null)
                return _right.exists(val);
            else if(_left != null)
                return _left.exists(val);
            else
                return false;
        }
        
        public int position(int val) {
            int leftSize = _left != null ? _left.getSize() : 0;
            if(val == _key) //Have we found the element we need?
                return leftSize; //Add left trees size
            if(val < _key) //if is in left tree search there, else it would be inserted there
                return _left != null ? _left.position(val) : 0;
            else //if is in right tree, search there and add lefts size + ourself
                return _right != null ? 
                      (_right.position(val) + leftSize + 1) : 1;
        }
        
        public void updateSize(int val) {
            if(_key != val) {
                if(val > _key) {
                    if(_right != null) {
                        _right.updateSize(val);
                    }
                } else if(_left != null) {
                    _left.updateSize(val);
                }
            }
            updateSize();
        }
        
        public void updateSize() {
            _size = (_left != null ? _left.getSize() : 0) + 
                    (_right != null ? _right.getSize() : 0) + 1;
        }
        
        public void valuesLoHi(int lo, int hi, Vector<Integer> ret) {
            if(lo < _key && _left != null)
                _left.valuesLoHi(lo, hi, ret);
            if(_key >= lo && _key <= hi)
                ret.add(_key);
            if(hi > _key && _right != null)
                _right.valuesLoHi(lo, hi, ret);
        }
        
        public void valuesHiLo(int lo, int hi, Vector<Integer> ret, int loV, int hiV) {
            if(!(_key < lo && loV > hi) && _left != null)
                _left.valuesHiLo(lo, hi, ret, loV, _key);
            if(_key <= hi || _key >= lo)
                ret.add(_key);
            if(!(_key > hi && hiV < lo) && _right != null)
                _right.valuesHiLo(lo, hi, ret, _key, hiV);
        }
        
        public void balance(Tree out) {
            if(_left != null)
                _left.balance(out);
            out.insert(_key);
            if(_right != null)
                _right.balance(out);
        }
    }
    
    private Node _root = null;
    private Node foundNode = null;
    int foundLo = Integer.MIN_VALUE, foundHi = Integer.MAX_VALUE;
    private boolean foundNodeStillValid = false, lastVATStillValid = false,
                    heightStillValid = false;
    private Node lastVATNode = null;
    private int lastVATPosition = -1, height = -1;
    
    
    public Tree() {
    
    }
    
    private void invalidate() {
        foundNodeStillValid = false;
        lastVATStillValid = false;
        heightStillValid = false;
    }
    
    /**
     * Will print an In-Order Traversal of the Tree
     */
    public void inOrder() {
        if(!isEmpty())
            _root.inOrder();
        System.out.println();
    }
    
    /**
     * Delete an element in the tree and replace it with a successor if one
     * can be found.
     * @param val The key to be deleted
     * @throws IllegalStateException if an internal logic error has occurred
     */
    public void delete(int val) {
        if(isEmpty())
            return;
        Node toDelete = null, parent = null, successor = null, sucParent = null, setChild = null;
        for(toDelete = _root; toDelete != null && toDelete.getKey() != val; toDelete = (val > toDelete.getKey() ? toDelete.getRight() : toDelete.getLeft())) {
            toDelete.setSize(toDelete.getSize()-1); //update size of nodes while moving down
            parent = toDelete;
        }
        
        if(toDelete == null) {
            _root.updateSize(val); //fix size if the element wasn't found.
            return;
        }
        
        invalidate();
        
        if(toDelete.getLeft() == null && toDelete.getRight() == null) {
            setChild = null;
        } else if(toDelete.getLeft() != null && toDelete.getRight() == null) { //left child
            setChild = toDelete.getLeft();
        } else if(toDelete.getLeft() == null && toDelete.getRight() != null) { //right child
            setChild = toDelete.getRight();
        } else { //2 children
            for(successor = toDelete.getRight(); successor != null && successor.getLeft() != null; successor = successor.getLeft()) {
                successor.setSize(successor.getSize()-1); //update size of Nodes while traversing down
                sucParent = successor;
            }
            if(successor == null)
                throw new IllegalStateException("Successor nonexistent even though there should be one.");
            setChild = successor; //for parent.child replacement and updating size of parent nodes
            if(sucParent != null) { //if successor is not just the right child
                sucParent.setLeft(successor.getRight()); //orphaned successor's child
                successor.setRight(toDelete.getRight()); //successor seizing toDeletes right tree
            } //else keep right tree
            successor.setLeft(toDelete.getLeft()); //successor seizing toDeletes left tree
            successor.updateSize();
        }
        
        if(parent == null) { //if we tried to delete the root node, reset it
            _root = setChild;
        } else {
            if(val > parent.getKey()) { //if delete is right
                parent.setRight(setChild);
            } else {
                parent.setLeft(setChild);
            }
        }
    }
    
    /**
     * @return boolean value whether or not the tree contains elements
     */
    public boolean isEmpty() {
        return (_root == null);
    }
    
    /**
     * @return the number of elements stored in the tree
     */
    public int size() {
        if(isEmpty())
            return 0;
        else
            return _root.getSize();
    }
    
    /**
     * Checks whether a key exists within the tree
     * @param val key to search for
     * @return true = key exists, false = key does not exist
     */
    public boolean exists(int val) {
        if(isEmpty())
            return false;
        if(foundNodeStillValid && foundNode != null && val > foundLo && val < foundHi) {
            foundNode = find(val, foundNode, false);
        } else {
            foundNode = find(val, _root, true);
        }
        foundNodeStillValid = true;
        return foundNode != null;
    }
    
    private Node find(int val, Node cursor, boolean set) {
        if(set) { //whether or not to reset the boundaries
            foundLo = Integer.MIN_VALUE;
            foundHi = Integer.MAX_VALUE;
        }
        while(cursor != null && cursor.getKey() != val) {
            int key = cursor.getKey();
            if(val > key) {
                if(key > foundLo)
                    foundLo = key;
                cursor = cursor.getRight();
            } else {
                if(key < foundHi)
                    foundHi = key;
                cursor = cursor.getLeft();
            }
        }
        return cursor;
    }
    
    /**
     * @return the number of elements vertically-1
     */
    public int height() {
        if(isEmpty())
            return -1;
        else if(!heightStillValid) {
            height = _root.getHeight();
        }
        return height;
    }
    
    /**
     * Will insert a given key into the tree if it does not exist already
     * @param val unique key to be inserted
     */
    public void insert(int val) {
        if(_root == null) {
            invalidate();
            _root = new Node(val);
        } else {
            Node cursor, parent = null;
            for(cursor = _root; cursor != null && cursor.getKey() != val; cursor = (val > cursor.getKey() ? cursor.getRight() : cursor.getLeft())) {
                cursor.setSize(cursor.getSize()+1);
                parent = cursor;
            }
            if(cursor != null && val == cursor.getKey()) {
                fixSize(val);
                return;
            }
            if(parent == null)
                throw new IllegalStateException("INSERT ERROR: Parent = null even though it shouldn't be.");
            invalidate();
            if(val > parent.getKey()) {
                parent.setRight(new Node(val));
            } else {
                parent.setLeft(new Node(val));
            }
        }
    }
    
    private void fixSize(int val) {
        Node cursor;
        for(cursor = _root; cursor != null && cursor.getKey() != val; cursor = (val > cursor.getKey() ? cursor.getRight() : cursor.getLeft())) {
            cursor.setSize(cursor.getSize()-1);
        }
        if(cursor == null)
            throw new IllegalStateException("FIXSIZE ERROR: Cursor = null even though it shouldn't be.");
    }
    
    /**
     * Will insert any number of unique given keys into the tree.
     * @param val argument list of keys to be inserted
     */
    public void minsert(int... val) {
        for(int x : val) {
            insert(x);
        }
    }
    
    private Node getRoot() {
        return _root;
    }
    /**
     * Will efficiently determine a key at a certain position within the In-
     * Order-Traversal.
     * @param k the position in the In-Order-Traversal (0 to size())
     * @return key stored at position k
     */
    public int valueAtPosition(int k) {
        if(k < 0 || k >= _root.getSize()) {
            throw new IllegalArgumentException("Cannot reach position " + k + ". (" + (k < 0 ? "must be > 0)" : "must be < " + _root.getSize() + ")"));
        }
        
        if(lastVATStillValid) {
            if(k == lastVATPosition) {
                return lastVATNode.getKey();
            } else if(k == lastVATPosition-1 && lastVATNode.getLeft() != null && lastVATNode.getLeft().getRight() == null) {
                lastVATPosition = k;
                lastVATNode = lastVATNode.getLeft();
                return lastVATNode.getKey();
            } else if(k == lastVATPosition+1 && lastVATNode.getRight() != null && lastVATNode.getRight().getLeft() == null) {
                lastVATPosition = k;
                lastVATNode = lastVATNode.getRight();
                return lastVATNode.getKey();
            }
        }
        
        lastVATPosition = k;
        Node cursor = _root;
        int leftSize = 0;
        while(!((cursor.getLeft() != null && (leftSize = cursor.getLeft().getSize()) == k) || (cursor.getLeft() == null && k == 0))) {
            if(cursor.getLeft() != null && cursor.getLeft().getSize() > k) {
                cursor = cursor.getLeft();
            } else if(cursor.getRight() != null && leftSize < k) {
                k = k-leftSize-1;
                cursor = cursor.getRight();
            } else {
                throw new IllegalStateException("VALUEATPOSITION ERROR: INTERNAL LOGIC FAILED");
            }
            leftSize = 0;
        }
        lastVATNode = cursor;
        lastVATStillValid = true;
        return cursor.getKey();
    }
    
    /**
     * Will determine the postion of a key in the In-Order-Traversal
     * @param val key to search for
     * @return postion of a key in the In-Order-Traversal
     */
    public int position(int val) {
        return _root.position(val);
    }
    
    /**
     * Will return all elements greater or equal to lo and less or equal to hi
     * @param lo elements greater or equal to lo will be included
     * @param hi elements less or equal to hi will be included
     * @return sorted vector of elements
     */
    public Iterable<Integer> values(int lo, int hi) {
        Vector<Integer> col = new Vector<Integer>(size());
        if(isEmpty())
            return col;
        if(lo > hi) {
            _root.valuesHiLo(lo, hi, col, Integer.MIN_VALUE, Integer.MAX_VALUE);
        } else {
            _root.valuesLoHi(lo, hi, col);
        }
        return col;
    }
    
    /**
     * Will balance the tree so that the height difference of the left and right
     * subtree never exceeds + or -1
     */
    public void simpleBalance() {
        invalidate();
        if(isEmpty())
            return;
        if(_root.getSize() < 3)
            return;
        Tree balanced = new Tree();
        balanceRecursion(balanced, 0, size()-1);
        _root = balanced.getRoot();
    }
    
    private void balanceRecursion(Tree in, int low, int high) {
        int med = (low+high)/2;
        in.insert(valueAtPosition(med));
        if(med-1 >= low)
            balanceRecursion(in, low, med-1);
        if(med+1 <= high)
            balanceRecursion(in, med+1, high);
    }
}
