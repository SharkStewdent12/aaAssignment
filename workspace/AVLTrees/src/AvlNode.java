public class AvlNode {
	public AvlNode leftChild;
	public AvlNode rightChild;
	public AvlNode parent;
	public int key;
	public int height;

	public AvlNode(int k) {
		leftChild = rightChild = parent = null;  
		key = k;
		height = 0;
	}
	public String toString() {
		return "" + key;
	}

	public int balance() {

		int leftHeight, rightHeight;

		if (leftChild == null) {
			leftHeight = 0;
		} else { //has left and right children
			leftHeight = leftChild.height + 1;
		}

		if (rightChild == null) {
			rightHeight = 0;
		} else { //has left and right children
			rightHeight = rightChild.height + 1;
		}

		return rightHeight - leftHeight;
	}

	public void reviseHeight() {
		
		if ( (leftChild == null) && (rightChild==null))  { //leaf node, no children
			height = 0;
		} else if (leftChild == null) { //only has right child
			height = rightChild.height + 1;
		} else if (rightChild == null) { //only has left child
			height = leftChild.height + 1;
		} else { //has left and right children			
			height = Math.max(leftChild.height, rightChild.height) + 1;
		}
	}
	
	public void recursiveFixHeight(){
		
		if ( (leftChild == null) && (rightChild==null))  { //leaf node, no children
			height = 0;
		} else if (leftChild == null) { //only has right child
			rightChild.recursiveFixHeight();
			height = rightChild.height + 1;
		} else if (rightChild == null) { //only has left child
			leftChild.recursiveFixHeight();
			height = leftChild.height + 1;
		} else { //has left and right children			
			leftChild.recursiveFixHeight();
			rightChild.recursiveFixHeight();
			height = Math.max(leftChild.height, rightChild.height) + 1;
		}
	}

}
