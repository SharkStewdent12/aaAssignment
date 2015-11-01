import java.util.Random;

//James was here
public class TreapNode {

	TreapNode leftChild;
	TreapNode rightChild; 
	TreapNode parent;
	char key;
	int priority;
	private static Random randomObj = new Random( );

	TreapNode( char key ) {
		this.key = key;
		priority = randomObj.nextInt();
		this.leftChild = null;
		this.rightChild = null;
		this.parent = null;
	}



}
