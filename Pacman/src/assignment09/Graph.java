package assignment09;
import java.util.Arrays;

/**
 * Represents a graph data structure. Utilizes the Node subclass to do so.
 * 
 * @author Jaden
 *
 */


public class Graph 
{
	static class Node 
	{
		boolean visited;
		Node[] edges;
		
		public Node()
		{
			this.edges = new Node[0];
		}
		
		public Node(Node[] _edges)
		{
			this.edges = _edges;
		}
		
		public boolean isEdge(Node edge)
		{
			for (Node node : edges)
				if (node.equals(edge))
					return true;
			
			return false;
		}
		
		public boolean addEdge(Node edge)
		{
			if (isEdge(edge))
				return false;
			
			edges = Arrays.copyOfRange(edges, 0, edges.length + 1);
			edges[edges.length - 1] = edge;
			
			return true;
		}
	}
	
	
}
