public class Tree {
	private Node _root;
	
	public void print() {
		if(isEmpty())
			System.out.println("[]");
		else
			_root.print();
	}
	
	public Tree() {
		_root = null;
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
			return 0;
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
