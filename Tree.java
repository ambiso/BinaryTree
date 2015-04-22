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
		if(isEmpty())
			return;
		
		Node toDelete = _root.findNode(val);
		if(toDelete == null)
			return;
		//NEED PARENT
		
		Node tmp = toDelete;
		if(tmp.getRight() == null) {
			//NO SUCCESSOR NEED PREDECESSOR
		} else {
			Node successor = tmp;
			while((tmp = tmp.getLeft()) != null) {
				successor = tmp;
			}			
			//REPLACE toDelete WITH successor
			
			//REMEMBER RIGHT TREE OF successor
			
			//REPLACE RIGHT TREE OF successor WITH toDelete's
			
			//EMPLACE OLD RIGHT TREE OF successor		
		}
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
	
	public Iterable<Integer> values(int lo, int hi) {
		return null;
	}
	
	public void simpleBalance() {
		
	}
}
