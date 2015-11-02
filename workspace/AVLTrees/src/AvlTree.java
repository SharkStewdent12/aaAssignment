
import java.util.ArrayList;

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

//		tree.insert(14);
//		tree.insert(7);
//		tree.insert(12);
//		tree.insert(8);
//
//		tree.insert(21);
//		tree.insert(27);
//		tree.insert(22);
//		tree.insert(25);
//		tree.insert(29);	
		
//		tree.insert(2);
//		tree.insert(1);
//		tree.insert(2);
//		tree.insert(3);
//		tree.insert(1);
//		tree.insert(4);
//		tree.insert(4);
//		tree.insert(2);
		


		list = tree.inorder();
		System.out.println(list.toString());

		tree.debug( tree.root );

	}


	public void insert(int k) {
		// create new node
		AvlNode n = new AvlNode(k);
		// start recursive procedure for inserting the node
		insertAVL(this.root,n);
	}


	public void insertAVL(AvlNode subtreeRoot, AvlNode newNode) {
		// If  node to compare is null, the node is inserted. If the root is null, it is the root of the tree.
		if(subtreeRoot==null) {
			this.root=newNode;
		} else {
			// Key smaller, send left
			if(newNode.key<subtreeRoot.key) { //free leaf, add node
				if(subtreeRoot.leftChild==null) {
					subtreeRoot.leftChild = newNode;
					newNode.parent = subtreeRoot;

					root.recursiveFixHeight();;
					avlBalance(subtreeRoot);
				} else { //insert into left subtree
					insertAVL(subtreeRoot.leftChild,newNode);
				}
				// Key larger (or equal), send right
			} else {
				if(subtreeRoot.rightChild==null) { //free leaf, add node
					subtreeRoot.rightChild = newNode;
					newNode.parent = subtreeRoot;

					root.recursiveFixHeight();
					avlBalance(subtreeRoot);
				} else { //insert into right subtree
					insertAVL(subtreeRoot.rightChild,newNode);
				}
			}

//			this.root.recursiveFixHeight();
//			avlBalance(subtreeRoot);


			//			root.recursiveFixHeight();
			//			newNode.reviseHeight();
			//			subtreeRoot.reviseHeight();
			//			if (subtreeRoot.leftChild != null){
			//				subtreeRoot.leftChild.reviseHeight();
			//			}
			//			if (subtreeRoot.rightChild != null){
			//				subtreeRoot.rightChild.reviseHeight();
			//			}			

		}
	}


	public void avlBalance(AvlNode subtreeRoot) {		
		
		if (Math.abs(subtreeRoot.balance()) > 2) {
			System.out.println("Extreme imbalance: node "+subtreeRoot.key+" ("+subtreeRoot.balance()+")");
		}

		if (subtreeRoot.balance() == -2) { //left subtree is taller
			if ( subtreeRoot.leftChild.rightChild.getHeight() >= subtreeRoot.leftChild.leftChild.getHeight() ) {
				subtreeRoot.leftChild = leftRotate(subtreeRoot.leftChild);
			}
			subtreeRoot = rightRotate(subtreeRoot);
		} else if (subtreeRoot.balance() == 2) { //right subtree is taller
			if ( subtreeRoot.rightChild.leftChild.getHeight() >= subtreeRoot.rightChild.rightChild.getHeight() ) {
				subtreeRoot.rightChild = rightRotate(subtreeRoot.rightChild);
			}
			subtreeRoot = leftRotate(subtreeRoot);
		}
		
		if (subtreeRoot.parent == null) {
			System.out.println("change root");
			this.root = subtreeRoot;
		} 
		
//		root.recursiveFixHeight();

		// we did not reach the root yet
//		if(subtreeRoot.parent!=null) {
//			avlBalance(subtreeRoot.parent);
//		} else {
//			this.root = subtreeRoot;
			//System.out.println("------------ Balancing finished ----------------");
//		}

	}


	public void remove(int node) {
		// First we must find the node, after this we can delete it.
		removeAVL(this.root,node);
	}


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
			avlBalance(r.parent);
		}
		r = null;
	}


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

		newSubtreeRoot.reviseHeight();
		origSubtreeRoot.reviseHeight();

		return newSubtreeRoot;
	}


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

		newSubtreeRoot.reviseHeight();
		origSubtreeRoot.reviseHeight();
		
		return newSubtreeRoot;
	}



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

		System.out.println("Left: "+l+" Key: "+n+" Right: "+r+" Parent: "+p+" Balance: "+n.balance() + " Height: "+n.getHeight());

		if(n.leftChild!=null) {
			debug(n.leftChild);
		}
		if(n.rightChild!=null) {
			debug(n.rightChild);
		}
	}


	final protected ArrayList<AvlNode> inorder() {
		ArrayList<AvlNode> ret = new ArrayList<AvlNode>();
		inorder(root, ret);
		return ret;
	}


	final protected void inorder(AvlNode n, ArrayList<AvlNode> io) {
		if (n == null) {
			return;
		}
		inorder(n.leftChild, io);
		io.add(n);
		inorder(n.rightChild, io);
	}
}

