public class Tree {
	private Node _root;
	
	public void inOrder() {
		if(isEmpty())
			System.out.print("[]");
		else
			_root.inOrder();
		System.out.println();
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
		//IMPLEMENTATION
	}
	
	public int valueAtPosition(int k) {
		if(k < 0 || k > _root.getHeight()) {
			throw new IllegalArgumentException("Cannot reach position " + k + ". (" + (k < 0 ? "too small)" : "too large)"));
		}
		return _root.valueAtPosition(k);
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
