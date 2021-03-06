import java.util.Random;

// Treap class
//
// CONSTRUCTION: with no initializer
//
// ******************PUBLIC OPERATIONS*********************
// void insert( x )       --> Insert x
// void remove( x )       --> Remove x
// boolean contains( x )  --> Return true if x is found
// Comparable findMin( )  --> Return smallest item
// Comparable findMax( )  --> Return largest item
// boolean isEmpty( )     --> Return true if empty; else false
// void makeEmpty( )      --> Remove all items
// void printTree( )      --> Print tree in sorted order
// ******************ERRORS********************************
// Throws UnderflowException as appropriate

/**
 * Implements a treap.
 * Note that all "matching" is based on the compareTo method.
 * @author Mark Allen Weiss
 */
public class Treap
{
	/**
	 * Construct the treap.
	 */
//	public Treap( )
//	{
//		nullNode = new TreapNode( null );
//		nullNode.leftChild = nullNode.rightChild = nullNode;
//		nullNode.priority = Integer.MAX_VALUE;
//		root = nullNode;
//	}

	/**
	 * Insert into the tree. Does nothing if x is already present.
	 * @param x the item to insert.
	 */
	public void insert(char k)
	{
		TreapNode x=new TreapNode(k);
		BinaryInsert(this.root,x);
		while (x.parent != null && x.priority > x.parent.priority){
			if (x.parent.rightChild == x){
				rotateLeft(x.parent);
			}else{
		    	rotateRight(x.parent);
			}
		}
	}

	public TreapNode rotateLeft(TreapNode n) {
		TreapNode v = n.rightChild;
		v.parent = n.parent;
	
		n.rightChild = v.leftChild;
	
		if(n.rightChild!=null) {
			n.rightChild.parent=n;
		}
	
		v.leftChild = n;
		n.parent = v;
	
		if(v.parent!=null) {
			if(v.parent.rightChild==n) {
				v.parent.rightChild = v;
			} else if(v.parent.leftChild==n) {
				v.parent.leftChild = v;
			}
		}
		return v;
	}

	public TreapNode rotateRight(TreapNode n) {

		TreapNode v = n.leftChild;
		v.parent = n.parent;

		n.leftChild = v.rightChild;

		if(n.leftChild!=null) {
			n.leftChild.parent=n;
		}

		v.rightChild = n;
		n.parent = v;


		if(v.parent!=null) {
			if(v.parent.rightChild==n) {
				v.parent.rightChild = v;
			} else if(v.parent.leftChild==n) {
				v.parent.leftChild = v;
			}
		}


		return v;
	}
	/**
	 * Find an item in the tree.
	 * @param x the item to search for.
	 * @return true if x is found.
	 */
//	public boolean contains(  x )
//	{
//		TreapNode current = root;
//		nullNode.element = x;
//
//		for( ; ; )
//		{
//			int compareResult = x.compareTo( current.element );
//
//			if( compareResult < 0 )
//				current = current.left;
//			else if( compareResult > 0 ) 
//				current = current.right;
//			else
//				return current != nullNode;
//		}
//	}
	/**
	 * Print the tree contents in sorted order.
	 */
//	public void printTree( )
//	{
//			printTree( root );
//	}

	/**
	 * Internal method to insert into a subtree.
	 * @param x the item to insert.
	 * @param t the node that roots the subtree.
	 * @return the new root of the subtree.
	 */
//	private TreapNode insert( AnyType x, TreapNode t )
//	{
//		if( t == nullNode )
//			return new TreapNode<AnyType>( x, nullNode, nullNode );
//
//		int compareResult = x.compareTo( t.element );
//
//		if( compareResult < 0 )
//		{
//			t.left = insert( x, t.left );
//			if( t.left.priority < t.priority )
//				t = rotateWithLeftChild( t );
//		}
//		else if( compareResult > 0  )
//		{
//			t.right = insert( x, t.right );
//			if( t.right.priority < t.priority )
//				t = rotateWithRightChild( t );
//		}
//		// Otherwise, it's a duplicate; do nothing
//
//		return t;
//	}

	/**
	 * Internal method to remove from a subtree.
	 * @param x the item to remove.
	 * @param t the node that roots the subtree.
	 * @return the new root of the subtree.
	 */


	/**
	 * Internal method to print a subtree in sorted order.
	 * @param t the node that roots the tree.
	 */
	private void printTree( TreapNode t )
	{
		System.out.println( t.key );
		if(t.leftChild!=null )
		{
			printTree( t.leftChild );
		}
		if(t.rightChild!=null )
		{
			printTree( t.rightChild );
		}
	}

	public void BinaryInsert(TreapNode subtreeRoot, TreapNode newNode) {
		// If  node to compare is null, the node is inserted. If the root is null, it is the root of the tree.
		if(subtreeRoot==null) {
			this.root=newNode;
		} else {

			// If compare node is smaller, continue with the left node
			if(newNode.key<subtreeRoot.key) {
				if(subtreeRoot.leftChild==null) {
					subtreeRoot.leftChild = newNode;
					newNode.parent = subtreeRoot;

				} else {
					BinaryInsert(subtreeRoot.leftChild,newNode);
				}

			} else if(newNode.key>subtreeRoot.key) {
				if(subtreeRoot.rightChild==null) {
					subtreeRoot.rightChild = newNode;
					newNode.parent = subtreeRoot;

				} else {
					BinaryInsert(subtreeRoot.rightChild,newNode);
				}
			} else {
				// do nothing: This node already exists
			}
		}
	}

	/**
	 * Rotate binary tree node with left child.
	 */
	private TreapNode leftRotate( TreapNode k2 )
	{
		TreapNode k1 = k2.leftChild;
		k2.leftChild = k1.rightChild;
		k1.rightChild = k2;
		return k1;
	}

	/**
	 * Rotate binary tree node with right child.
	 */
	private TreapNode rightRotate( TreapNode k1 )
	{
		TreapNode k2 = k1.rightChild;
		k1.rightChild = k2.leftChild;
		k2.leftChild = k1;
		return k2;
	}
	
	private TreapNode root;
	private TreapNode nullNode;


	// Test program
	public static void main( String [ ] args ) throws Exception
	{
		Treap tree = new Treap( );

		tree.insert('E');
		tree.insert('A');
		tree.insert('D');
		tree.insert('F');
		tree.insert('B');
		tree.insert('I');
		tree.insert('K');
		tree.insert('L');
		tree.insert('Z');
		
		System.out.println( "Inserts complete" );
		tree.printTree(tree.root);
	}
}