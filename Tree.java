package ads1.ss15.pa;

import java.util.Vector;

public class Tree {
    public class Node {
        private Node _left, _right;
        private int _key;
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
        
        public boolean insert(int val) {
            if(val == _key)
                return false;
            boolean inserted; //need not be initialized because it is always set
            if(val > _key) {
                if(_right != null)
                    inserted = _right.insert(val);
                else {
                    _right = new Node(val);
                    inserted = true;
                }
            } else {
                if(_left != null)
                    inserted = _left.insert(val);
                else {
                    _left = new Node(val);
                    inserted = true;
                }
            }
            if(inserted) {
                _size++;
                return true;
            }
            return false;
        }
        
        public Node valueAtPosition(int k) {
            int ls = 0;
            if((_left != null && (ls = _left.getSize()) == k) || (_left == null && k == 0))
                return this;
            else if(_left != null && _left.getSize() > k)
                return _left.valueAtPosition(k);
            if(_right != null)
                if(ls < k)
                    return _right.valueAtPosition(k-ls-1);
            throw new IllegalStateException("VALUEATPOSITION ERROR: Key: " + _key + " _left: " + _left + " _right: " + _right + " k: " + k);
        }
        
        public int position(int val) {
            int leftSize = _left != null ? _left.getSize() : 0;
            if(val == _key) //Have we found the element we need?
                return leftSize; //Add left trees size
            if(val < _key) //if is in left tree search there, else it would be inserted there
                return _left != null ? _left.position(val) : 0;
            else //if is in right tree, search there and add lefts size + ourself
                return _right != null ? (_right.position(val) + leftSize + 1) : 1;
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
            _size = (_left != null ? _left.getSize() : 0) + (_right != null ? _right.getSize() : 0) + 1;
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
    int foundLo = Integer.MAX_VALUE, foundHi = Integer.MIN_VALUE;
    private boolean foundNodeStillValid = true;
    private Node lastValueAt = null;
    private int lastValueAtPosition = -1;
    
    public void inOrder() {
        if(!isEmpty())
            _root.inOrder();
        System.out.println();
    }
    
    public Tree() {
    }
    
    public void delete(int val) {
        foundNodeStillValid = false;
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
    
    public boolean isEmpty() {
        return (_root == null);
    }
    
    public int size() {
        if(isEmpty())
            return 0;
        else
            return _root.getSize();
    }
    
    public boolean exists(int val) {
        if(isEmpty())
            return false;
        if(foundNodeStillValid && foundNode != null) {
            find(val, foundNode, false);
        } else {
            find(val, _root, true);
        }
        return foundNode != null;
    }
    
    private void find(int val, Node cursor, boolean set) {
        if(set) {
            foundLo = Integer.MAX_VALUE;
            foundHi = Integer.MIN_VALUE;
        }
        for(; cursor != null && cursor.getKey() != val; cursor = (val > cursor.getKey() ? cursor.getRight() : cursor.getLeft())) {
            int key = cursor.getKey();
            if(key < foundLo)
                foundLo = key;
            if(key > foundHi)
                foundHi = key;
        }
        foundNode = cursor;
    }
    
    public int height() {
        if(isEmpty())
            return -1;
        else
            return _root.getHeight();
    }
    
    public void insert(int val) {
        foundNodeStillValid = false;
        if(_root == null) {
            _root = new Node(val);
        } else {
            _root.insert(val);
        }
    }
    
    public void minsert(int... val) {
        for(int x : val) {
            insert(x);
        }
    }
    
    private Node getRoot() {
        return _root;
    }
    
    public int valueAtPosition(int k) {
        if(k < 0 || k >= _root.getSize()) {
            throw new IllegalArgumentException("Cannot reach position " + k + ". (" + (k < 0 ? "must be > 0)" : "must be < " + _root.getSize() + ")"));
        }
        if(lastValueAtPosition == k)
            return lastValueAt.getKey();
        if(lastValueAt != null && lastValueAtPosition != -1 && k == lastValueAtPosition+1 && lastValueAt.getRight() != null && lastValueAt.getRight().getLeft() == null) {
            lastValueAt = lastValueAt.getRight();
        } else if(lastValueAt != null && lastValueAtPosition != -1 && k == lastValueAtPosition-1 && lastValueAt.getLeft() != null && lastValueAt.getLeft().getRight() == null) {
            lastValueAt = lastValueAt.getLeft();
        } else {
            lastValueAt = _root.valueAtPosition(k);
        }
        lastValueAtPosition = k;
        return lastValueAt.getKey();
    }
    
    public int position(int val) {
        return _root.position(val);
    }
    
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
    
    public void simpleBalance() {
        foundNodeStillValid = false;
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
