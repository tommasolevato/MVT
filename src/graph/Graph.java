package graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jgrapht.alg.BellmanFordShortestPath;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.event.EdgeTraversalEvent;
import org.jgrapht.event.TraversalListenerAdapter;
import org.jgrapht.graph.ClassBasedEdgeFactory;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.traverse.BreadthFirstIterator;

import petriNet.Transition;

public class Graph {
	private org.jgrapht.Graph<Node, Edge> myGraph;
	private List<Node> nodes;

	public Graph() {
		myGraph = new DefaultDirectedGraph<>(new ClassBasedEdgeFactory<>(Edge.class));
		nodes = new ArrayList<Node>();
	}

	public void addNode(Node toAdd) {
		nodes.add(toAdd);
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

	public void addAllNodes(List<Node> toAdd) {
		for (Node node : toAdd) {
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
		BreadthFirstIterator<Node, Edge> it = new BreadthFirstIterator<>(myGraph);
		TraversalListenerAdapter<Node, Edge> l = new TraversalListenerAdapter<Node, Edge>() {
			@Override
			public void edgeTraversed(EdgeTraversalEvent<Node, Edge> e) {
				System.out.println(e.getEdge().getTransition());
			}
		};
		it.addTraversalListener(l);
		while (it.hasNext()) {
			s.append(it.next().print() + "\n");
		}
		return s.toString();
	}

	public List<Graph> getAllEdgeCoverage() {
		List<Edge> toVisit = new ArrayList<>(myGraph.edgeSet());
		List<Edge> left = new ArrayList<>(myGraph.edgeSet());
		List<Graph> toReturn = new ArrayList<>();
		for (Edge e : toVisit) {
			if (left.contains(e)) {
				if (getInitialState().equals(e.getSource())) {
					List<Edge> toAdd = new ArrayList<>();
					toAdd.add(e);
					toReturn.add(buildGraph(toAdd));
				} else {
					List<Edge> path = BellmanFordShortestPath.findPathBetween(myGraph, getInitialState(),
							e.getSource());
					path.add(e);
					left.remove(e);
					left.removeAll(path);
					toReturn.add(buildGraph(path));
				}
			}
		}
		return toReturn;
	}

	public List<Graph> getAllNodeCoverage() {
		Node initial = getInitialState();
		Set<Node> toVisit = new HashSet<>(nodes);
		toVisit.remove(initial);
		Set<Node> left = new HashSet<>(nodes);
		left.remove(initial);
		List<Graph> toReturn = new ArrayList<>();
		for (Node visiting : toVisit) {
			if (left.contains(visiting)) {
				DijkstraShortestPath<Node, Edge> pathFinder = new DijkstraShortestPath<>(myGraph, initial, visiting);
				List<Edge> path = pathFinder.getPathEdgeList();
				for (Edge e : path) {
					left.remove(e.getDest());
				}
				toReturn.add(buildGraph(path));
			}
		}
		return toReturn;
	}

	public Node whatNodeIGetTo(Node from, Transition with) {
		for (Edge e : myGraph.edgeSet()) {
			if (e.getSource().equals(from) && e.getTransition().equals(with)) {
				return e.getDest();
			}
		}
		return null;
	}

	public Node getInitialState() {
		return nodes.get(0);
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

	public List<Edge> getAllEdges() {
		return new ArrayList<>(myGraph.edgeSet());
	}

	public List<Node> getAllNodes() {
		return new ArrayList<>(myGraph.vertexSet());
	}

}
