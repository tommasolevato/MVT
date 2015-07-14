package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.DirectedGraph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.BellmanFordShortestPath;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.alg.KruskalMinimumSpanningTree;
import org.jgrapht.graph.AsUndirectedGraph;
import org.jgrapht.graph.ClassBasedEdgeFactory;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.GraphDelegator;
import org.jgrapht.traverse.BreadthFirstIterator;

public class Graph {
	private org.jgrapht.Graph<State, Edge> myGraph;
	private Map<Integer, State> states;

	public Graph() {
		myGraph = new DefaultDirectedGraph<>(new ClassBasedEdgeFactory<>(Edge.class));
		states = new HashMap<>();
	}

	public void addNode(State toAdd) {
		states.put(toAdd.getId(), toAdd);
		myGraph.addVertex(toAdd);
	}

	public int getNumberOfNodes() {
		return myGraph.vertexSet().size();
	}

	public int getNumberOfEdges() {
		return myGraph.edgeSet().size();
	}

	public void addEdge(Edge toAdd) {
		myGraph.addEdge(toAdd.getSource(), toAdd.getDest(), toAdd);
	}

	public State getStateById(int id) {
		return states.get(id);
	}

	public void addAllNodes(List<State> toAdd) {
		for (State node : toAdd) {
			addNode(node);
		}
	}

	public void addAllEdges(List<Edge> toAdd) {
		for (Edge edge : toAdd) {
			addEdge(edge);
		}
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		BreadthFirstIterator<State, Edge> it = new BreadthFirstIterator<>(myGraph);
		while (it.hasNext()) {
			s.append(it.next().print() + "\n");
		}
		return s.toString();

	}

	public Graph spanningTree() {
		Graph tmp = copy();
		State initial = states.get(0);
		GraphDelegator<State, Edge> g = new GraphDelegator<>(tmp.myGraph);
		List<Edge> toRemove = new ArrayList<>(g.incomingEdgesOf(initial));
		for (Edge e : toRemove) {
			tmp.removeEdge(e);
		}
		KruskalMinimumSpanningTree<State, Edge> s = new KruskalMinimumSpanningTree<>(tmp.myGraph);
		Graph spanningTree = new Graph();
		spanningTree.addAllNodes(new ArrayList<State>(states.values()));
		spanningTree.addAllEdges(new ArrayList<Edge>(s.getMinimumSpanningTreeEdgeSet()));
		return spanningTree;
	}

	private State findRoot() {
		GraphDelegator<State, Edge> g = new GraphDelegator<>(spanningTree().myGraph);
		for (State s : states.values()) {
			if (g.inDegreeOf(s) == 0) {
				return s;
			}
		}
		throw new RuntimeException("There must be a root!");
	}

	private List<State> findLeaves() {
		GraphDelegator<State, Edge> g = new GraphDelegator<>(spanningTree().myGraph);
		List<State> toReturn = new ArrayList<>();
		for (State s : states.values()) {
			if (g.outDegreeOf(s) == 0) {
				toReturn.add(s);
			}
		}
		if (toReturn.size() == 0) {
			throw new RuntimeException("There must be at least one leaf!");
		}
		return toReturn;
	}

	public List<Graph> getAllEdgeCoverage() {
		List<Edge> toVisit = new ArrayList<>(myGraph.edgeSet());
		List<Edge> left = new ArrayList<>(myGraph.edgeSet());
		List<Graph> toReturn = new ArrayList<>();
		for (Edge e : toVisit) {
			if (states.get(0).equals(e.getSource())) {
				List<Edge> toAdd = new ArrayList<>();
				toAdd.add(e);
				toReturn.add(buildGraph(toAdd));
			} else {
				List<Edge> path = BellmanFordShortestPath.findPathBetween(myGraph, states.get(0), e.getSource());
				path.add(e);
				left.remove(e);
				left.removeAll(path);
				toReturn.add(buildGraph(path));
			}
		}
		return toReturn;

	}

	public List<Graph> getAllNodeCoverage() {
		Graph spanningTree = spanningTree();
		State root = spanningTree.findRoot();
		List<State> leaves = spanningTree.findLeaves();
		List<Graph> toReturn = new ArrayList<>();
		List<Edge> edge = spanningTree.getAllEdges();
		for (State leaf : leaves) {
			DijkstraShortestPath<State, Edge> finder = new DijkstraShortestPath<>(new AsUndirectedGraph<>((DirectedGraph)spanningTree.myGraph), root, leaf);
			GraphPath<State, Edge> path = finder.getPath();
			if (path != null) {
				List<Edge> l = path.getEdgeList();
				toReturn.add(buildGraph(l));
			}
			else {
				for(Edge e : edge) {
					if(leaf.equals(e.getDest())) {
						System.out.println(e.getSource().print());
					}
				}
			}
			if (toReturn.size() % 100 == 0) {
				System.out.println(toReturn.size());
			}
		}
		if (toReturn.size() % 100 != 0) {
			System.out.println(toReturn.size());
		}
		return toReturn;
	}

	private Graph buildGraph(List<Edge> edges) {
		Graph toReturn = new Graph();
		for (Edge e : edges) {
			toReturn.addNode(e.getSource());
			toReturn.addNode(e.getDest());
			toReturn.addEdge(e);
		}
		return toReturn;
	}

	private Graph copy() {
		Graph toReturn = new Graph();
		toReturn.addAllNodes(new ArrayList<>(states.values()));
		toReturn.addAllEdges(new ArrayList<>(myGraph.edgeSet()));
		return toReturn;
	}

	private void removeEdge(Edge toRemove) {
		myGraph.removeEdge(toRemove);
	}
	
	public List<Edge> getAllEdges() {
		return new ArrayList<>(myGraph.edgeSet());
	}
	
	public List<State> getAllNodes() {
		return new ArrayList<>(myGraph.vertexSet());
	}

}
