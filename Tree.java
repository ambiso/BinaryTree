public class Tree {
	private Node _root;
	
	public Tree() {
		_root = null;
	}
	
	public boolean isEmpty() {
		return (_root == null);
	}
	
	public int size() {
		return 0;
	}
	
	public boolean exists(int val) {
		if(isEmpty())
			return false;
		else
			return _root.find(val);
	}
	
	public int height() {
		return 0;
	}
	
	public void insert(int val) {
		if(_root == null) {
			_root = new Node(val);
		} else {
			_root.insert(val);
		}
	}
	
	public void delete(int val) {
		
	}
	
	public int valueAtPosition(int k) {
		return 0;
	}
	
	public int position(int val) {
		return 0;
	}
	
	Iterable<Integer> values(int lo, int hi) {
		return null;
	}
	
	public void simpleBalance() {
		
	}
}
