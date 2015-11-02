import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;

import javax.swing.JFrame;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.algorithms.layout.TreeLayout;
import edu.uci.ics.jung.graph.DelegateTree;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.RenderContext;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;

public class TreapVisualisation {

	public static void dispTreap( TreapNode root ) {

		DelegateTree<String, String> tree = new DelegateTree<String,String>();

		tree.setRoot( Character.toString(root.key) + "-" + Integer.toString(root.priority) );

		buildTree( root, tree );

		BasicVisualizationServer<String, String> vs = new BasicVisualizationServer<String, String>(
				new TreeLayout<String,String>(tree), new Dimension(1100, 640));


		Transformer<String,Paint> vertexPaint = new Transformer<String,Paint>() {
			public Paint transform(String i) {
				return Color.GRAY;
			}
		};  



		RenderContext<String, String> renderContext = vs.getRenderContext();
		renderContext.setVertexFillPaintTransformer(vertexPaint);

		Transformer<String, String> transformer = new ToStringLabeller<String>();
		renderContext.setEdgeLabelTransformer(transformer);
		Transformer<String, String> vertexTransformer = new ToStringLabeller<String>();
		renderContext.setVertexLabelTransformer(vertexTransformer);
		vs.getRenderer().getVertexLabelRenderer().setPosition(Position.CNTR);



		JFrame frame = new JFrame();
		frame.getContentPane().add(vs);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);

	}

	public static void buildTree( TreapNode curNode, DelegateTree<String, String> tree){
		System.out.println("Node: "+Character.toString(curNode.key));

		if (curNode.leftChild == null) {
			//do nothing
		} else {
			tree.addChild("L"+Character.toString(curNode.key),Character.toString(curNode.key)+ "-" + Integer.toString(curNode.priority), Character.toString(curNode.leftChild.key)+ "-" + Integer.toString(curNode.leftChild.priority));
			buildTree( curNode.leftChild, tree );
		}

		if (curNode.rightChild == null) {
			//do nothing
		} else {
			tree.addChild("R"+Character.toString(curNode.key),Character.toString(curNode.key)+ "-" + Integer.toString(curNode.priority), Character.toString(curNode.rightChild.key)+ "-" + Integer.toString(curNode.rightChild.priority));
			buildTree( curNode.rightChild, tree );
		}

	}

}
