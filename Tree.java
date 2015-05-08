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
		
		public boolean find(int val) {
			if (_key == val)
				return true;
			if(val > _key && _right != null)
				return _right.find(val);
			else if(_left != null)
				return _left.find(val);
			else
				return false;
		}
		
		public boolean insert(int val) {
			if(val == _key)
				return false;
			boolean inserted = true;
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
		
		public Node findNode(int val) {
			if(val == _key)
				return this;
			if(val > _key) {
				if(_right != null)
					return _right.findNode(val);
				else
					return null;
			} else {
				if(_left != null)
					return _left.findNode(val);
				else
					return null;
			}
		}
		
		public int valueAtPosition(int k) {
			int ls = 0;
			if((_left != null && (ls = _left.getSize()) == k) || (_left == null && k == 0))
				return _key;
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
		
		public void valuesHiLo(int lo, int hi, Vector<Integer> ret, int lowV, int uppV) {
			if(!(_key < lo && lowV > hi) && _left != null)
				_left.valuesHiLo(lo, hi, ret, lowV, _key);
			if(_key <= hi || _key >= lo)
				ret.add(_key);
			if(!(_key > hi && uppV < lo) && _right != null)
				_right.valuesHiLo(lo, hi, ret, _key, uppV);
		}
		
		public void balance(Tree out) {
			if(_left != null)
				_left.balance(out);
			out.insert(_key);
			if(_right != null)
				_right.balance(out);
		}
	}
	
	private Node _root;
	
	public void inOrder() {
		if(!isEmpty())
			_root.inOrder();
		System.out.println();
	}
	
	public Tree() {
		_root = null;
	}
	
	public void delete(int val) {
		if(isEmpty())
			return;
		Node toDelete = null, parent = null, successor = null, sucParent = null, setChild = null;
		for(toDelete = _root; toDelete != null && toDelete.getKey() != val; toDelete = (val > toDelete.getKey() ? toDelete.getRight() : toDelete.getLeft())) {
			parent = toDelete;
		}
		
		if(toDelete == null)
			return;
		int updateSizeKey = -1;
		if(toDelete.getLeft() == null && toDelete.getRight() == null) { //no child
			if(parent != null) { //if we're deleting the root node, but it doesn't have any children we don't care about the size
				updateSizeKey = parent.getKey();
			}
			setChild = null;
		} else if(toDelete.getLeft() != null && toDelete.getRight() == null) { //left child
			setChild = toDelete.getLeft();
		} else if(toDelete.getLeft() == null && toDelete.getRight() != null) { //right child
			setChild = toDelete.getRight();
		} else { //2 children
			for(successor = toDelete.getRight(); successor != null && successor.getLeft() != null; successor = successor.getLeft()) {
				sucParent = successor;
			}
			setChild = successor; //for parent.child replacement and updating size of parent nodes
			
			if(sucParent != null) { //if successor is not just the right child
				sucParent.setLeft(successor.getRight()); //orphaned successor's child
				successor.setRight(toDelete.getRight()); //successor seizing toDeletes right tree
				successor.updateSize(sucParent.getKey()); //fix broken sizes between successor and sucPar
			} //else keep right tree
			successor.setLeft(toDelete.getLeft()); //successor seizing toDeletes left tree
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
		if(setChild != null) {
			updateSizeKey = setChild.getKey();
		}
		if(_root != null) {
			_root.updateSize(updateSizeKey);
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
		else
			return _root.find(val);
	}
	
	public int height() {
		if(isEmpty())
			return -1;
		else
			return _root.getHeight();
	}
	
	public void insert(int val) {
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
		return _root.valueAtPosition(k);
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
