public class Node {
	private Node _left, _right;
	private int _key;
	private int _size;
	
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
