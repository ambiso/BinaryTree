public class Node {
	private Node _left, _right;
	private int _key;
	private int _size;
	
	public int getSize() {
		return _size;
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
	
	public Node(int key) {
		_key = key;
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
}
