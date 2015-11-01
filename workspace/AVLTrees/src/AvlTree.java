
import java.util.ArrayList;

/**
 * This class is the complete and tested implementation of an AVL-tree.
 */
public class AvlTree {

	protected AvlNode root; // the root node

	public static void main(String[] args)
	{
		ArrayList<AvlNode> list = new ArrayList<AvlNode>();
		AvlTree tree = new AvlTree();
		tree.insert(5);
		tree.insert(1);
		tree.insert(4);
		tree.insert(6);
		tree.insert(2);
		tree.insert(9);
		tree.insert(11);
		tree.insert(12);
		tree.insert(26);
		list = tree.inorder();
		System.out.println(list.toString());
		
		tree.debug( tree.root );

	}

	/***************************** Core Functions ************************************/

	/**
	 * Add a new element with key "k" into the tree.
	 * 
	 * @param k
	 *            The key of the new node.
	 */
	public void insert(int k) {
		// create new node
		AvlNode n = new AvlNode(k);
		// start recursive procedure for inserting the node
		insertAVL(this.root,n);
	}

	/**
	 * Recursive method to insert a node into a tree.
	 * 
	 * @param subtreeRoot The node currently compared, usually you start with the root.
	 * @param newNode The node to be inserted.
	 */
	public void insertAVL(AvlNode subtreeRoot, AvlNode newNode) {
		// If  node to compare is null, the node is inserted. If the root is null, it is the root of the tree.
		if(subtreeRoot==null) {
			this.root=newNode;
		} else {

			// If compare node is smaller, continue with the left node
			if(newNode.key<subtreeRoot.key) {
				if(subtreeRoot.leftChild==null) {
					subtreeRoot.leftChild = newNode;
					newNode.parent = subtreeRoot;

					// Node is inserted now, continue checking the balance
					recursiveBalance(subtreeRoot);
				} else {
					insertAVL(subtreeRoot.leftChild,newNode);
				}

			} else if(newNode.key>subtreeRoot.key) {
				if(subtreeRoot.rightChild==null) {
					subtreeRoot.rightChild = newNode;
					newNode.parent = subtreeRoot;

					// Node is inserted now, continue checking the balance
					recursiveBalance(subtreeRoot);
				} else {
					insertAVL(subtreeRoot.rightChild,newNode);
				}
			} else {
				// do nothing: This node already exists
			}
		}
	}

	/**
	 * Check the balance for each node recursivly and call required methods for balancing the tree until the root is reached.
	 * 
	 * @param subtreeRoot : The node to check the balance for, usually you start with the parent of a leaf.
	 */
	public void recursiveBalance(AvlNode subtreeRoot) {

		if (subtreeRoot.balance() == -2) { //left subtree is taller
			if ( subtreeRoot.leftChild.rightChild.height >= subtreeRoot.leftChild.leftChild.height ) {
				subtreeRoot.leftChild = leftRotate(subtreeRoot.leftChild);
			}
			subtreeRoot = rightRotate(subtreeRoot);
		} else if (subtreeRoot.balance() == 2) { //right subtree is taller
			if ( subtreeRoot.rightChild.leftChild.height >= subtreeRoot.rightChild.rightChild.height ) {
				subtreeRoot.rightChild = rightRotate(subtreeRoot.rightChild);
			}
			subtreeRoot = leftRotate(subtreeRoot);
		}

		// we did not reach the root yet
		if(subtreeRoot.parent!=null) {
			recursiveBalance(subtreeRoot.parent);
		} else {
			this.root = subtreeRoot;
		}
	}

	/**
	 * Removes a node from the tree, if it is existent.
	 */
	public void remove(int node) {
		// First we must find the node, after this we can delete it.
		removeAVL(this.root,node);
	}

	/**
	 * Finds a node and calls a method to remove the node.
	 * 
	 * @param subtreeRoot The node to start the search.
	 * @param targetKey The KEY of node to remove.
	 */
	public void removeAVL(AvlNode subtreeRoot,int targetKey) {
		if(subtreeRoot==null) {
			return;
		} else {
			if(subtreeRoot.key>targetKey)  {
				removeAVL(subtreeRoot.leftChild,targetKey);
			} else if(subtreeRoot.key<targetKey) {
				removeAVL(subtreeRoot.rightChild,targetKey);
			} else if(subtreeRoot.key==targetKey) {
				// we found the node in the tree.. now lets go on!
				removeFoundNode(subtreeRoot);
			}
		}
	}

	/**
	 * Removes a node from a AVL-Tree, while balancing will be done if necessary.
	 * 
	 * @param targetNode The node to be removed.
	 */
	public void removeFoundNode(AvlNode targetNode) {
		AvlNode r;
		// at least one child of q, q will be removed directly
		if(targetNode.leftChild==null || targetNode.rightChild==null) {
			// the root is deleted
			if(targetNode.parent==null) {
				this.root=null;
				targetNode=null;
				return;
			}
			r = targetNode;
		} else {
			// q has two children --> will be replaced by successor
			r = successor(targetNode);
			targetNode.key = r.key;
		}

		AvlNode p;
		if(r.leftChild!=null) {
			p = r.leftChild;
		} else {
			p = r.rightChild;
		}

		if(p!=null) {
			p.parent = r.parent;
		}

		if(r.parent==null) {
			this.root = p;
		} else {
			if(r==r.parent.leftChild) {
				r.parent.leftChild=p;
			} else {
				r.parent.rightChild = p;
			}
			// balancing must be done until the root is reached.
			recursiveBalance(r.parent);
		}
		r = null;
	}

	/**
	 * Left rotation using the given node.
	 * 
	 * 
	 * @param origSubtreeRoot
	 *            The node for the rotation.
	 * 
	 * @return The root of the rotated tree.
	 */
	public AvlNode leftRotate(AvlNode origSubtreeRoot) {

		AvlNode newSubtreeRoot = origSubtreeRoot.rightChild;
		newSubtreeRoot.parent = origSubtreeRoot.parent;

		origSubtreeRoot.rightChild = newSubtreeRoot.leftChild;

		if(origSubtreeRoot.rightChild!=null) {
			origSubtreeRoot.rightChild.parent=origSubtreeRoot;
		}

		newSubtreeRoot.leftChild = origSubtreeRoot;
		origSubtreeRoot.parent = newSubtreeRoot;

		if(newSubtreeRoot.parent!=null) {
			if(newSubtreeRoot.parent.rightChild==origSubtreeRoot) {
				newSubtreeRoot.parent.rightChild = newSubtreeRoot;
			} else if(newSubtreeRoot.parent.leftChild==origSubtreeRoot) {
				newSubtreeRoot.parent.leftChild = newSubtreeRoot;
			}
		}


		return newSubtreeRoot;
	}

	/**
	 * Right rotation using the given node.
	 * 
	 * @param origSubtreeRoot
	 *            The node for the rotation
	 * 
	 * @return The root of the new rotated tree.
	 */
	public AvlNode rightRotate(AvlNode origSubtreeRoot) {

		AvlNode newSubtreeRoot = origSubtreeRoot.leftChild;
		newSubtreeRoot.parent = origSubtreeRoot.parent;

		origSubtreeRoot.leftChild = newSubtreeRoot.rightChild;

		if(origSubtreeRoot.leftChild!=null) {
			origSubtreeRoot.leftChild.parent=origSubtreeRoot;
		}

		newSubtreeRoot.rightChild = origSubtreeRoot;
		origSubtreeRoot.parent = newSubtreeRoot;


		if(newSubtreeRoot.parent!=null) {
			if(newSubtreeRoot.parent.rightChild==origSubtreeRoot) {
				newSubtreeRoot.parent.rightChild = newSubtreeRoot;
			} else if(newSubtreeRoot.parent.leftChild==origSubtreeRoot) {
				newSubtreeRoot.parent.leftChild = newSubtreeRoot;
			}
		}


		return newSubtreeRoot;
	}


	/***************************** Helper Functions ************************************/

	/**
	 * Returns the successor of a given node in the tree (search recursivly).
	 * 
	 * @param q The predecessor.
	 * @return The successor of node q.
	 */
	public AvlNode successor(AvlNode q) {
		if(q.rightChild!=null) {
			AvlNode r = q.rightChild;
			while(r.leftChild!=null) {
				r = r.leftChild;
			}
			return r;
		} else {
			AvlNode p = q.parent;
			while(p!=null && q==p.rightChild) {
				q = p;
				p = q.parent;
			}
			return p;
		}
	}


//	/**
//	 * Return the maximum of two integers.
//	 */
//	private int maximum(int a, int b) {
//		if(a>=b) {
//			return a;
//		} else {
//			return b;
//		}
//	}

	/** 
	 * Only for debugging purposes. Gives all information about a node.

	 * @param n The node to write information about.
	 */
	public void debug(AvlNode n) {
		int l = 0;
		int r = 0;
		int p = 0;
		if(n.leftChild!=null) {
			l = n.leftChild.key;
		}
		if(n.rightChild!=null) {
			r = n.rightChild.key;
		}
		if(n.parent!=null) {
			p = n.parent.key;
		}

		System.out.println("Left: "+l+" Key: "+n+" Right: "+r+" Parent: "+p+" Balance: "+n.balance());

		if(n.leftChild!=null) {
			debug(n.leftChild);
		}
		if(n.rightChild!=null) {
			debug(n.rightChild);
		}
	}


	/**
	 * Calculates the Inorder traversal of this tree.
	 * 
	 * @return A Array-List of the tree in inorder traversal.
	 */
	final protected ArrayList<AvlNode> inorder() {
		ArrayList<AvlNode> ret = new ArrayList<AvlNode>();
		inorder(root, ret);
		return ret;
	}

	/**
	 * Function to calculate inorder recursivly.
	 * 
	 * @param n
	 *            The current node.
	 * @param io
	 *            The list to save the inorder traversal.
	 */
	final protected void inorder(AvlNode n, ArrayList<AvlNode> io) {
		if (n == null) {
			return;
		}
		inorder(n.leftChild, io);
		io.add(n);
		inorder(n.rightChild, io);
	}
}

