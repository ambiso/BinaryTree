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
	
	public void insert(int val) {
		if(val >= _key) {
			if(_right != null)
				_right.insert(val);
			else
				_right = new Node(val);
		} else {
			if(_left != null)
				_left.insert(val);
			else
				_left = new Node(val);
		}
		_size++;
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
		throw new IllegalStateException("ERROR: Key: " + _key + " _left: " + _left + " _right: " + _right + " k: " + k);
	}
	
	public int position(int val) {
		return 0;
	}
	
	private Node successor() {
		return null;
	}
	
	public void updateInfo() {
		
	}
}
