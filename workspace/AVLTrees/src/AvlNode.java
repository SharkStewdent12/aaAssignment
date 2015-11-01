/** Here is the AVL-Node class for Completenesse **/
public class AvlNode {
	public AvlNode left;
	public AvlNode right;
	public AvlNode parent;
	public int key;
	public int height;

	public AvlNode(int k) {
		left = right = parent = null;  
		key = k;
		height = 0;
	}
	public String toString() {
		return "" + key;
	}

	public int balance() {

		int leftHeight, rightHeight;

		if (left == null) {
			leftHeight = 0;
		} else { //has left and right children
			leftHeight = left.height + 1;
		}

		if (right == null) {
			rightHeight = 0;
		} else { //has left and right children
			rightHeight = right.height + 1;
		}

		return rightHeight - leftHeight;
	}

	public void reviseHeight() {

		if ( (left == null) && (right==null))  { //leaf node, no children
			height = 0;
		} else if (left == null) { //only has right child
			height = right.height + 1;
		} else if (right == null) { //only has left child
			height = left.height + 1;
		} else { //has left and right children
			height = Math.max(left.height, right.height) + 1;
		}
	}

}
