package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.jgrapht.alg.BellmanFordShortestPath;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.event.EdgeTraversalEvent;
import org.jgrapht.event.TraversalListenerAdapter;
import org.jgrapht.graph.ClassBasedEdgeFactory;
import org.jgrapht.graph.DefaultDirectedGraph;
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
		TraversalListenerAdapter<State, Edge> l = new TraversalListenerAdapter<State, Edge>() {
			@Override
			public void edgeTraversed(EdgeTraversalEvent<State, Edge> e) {
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
					List<Edge> path = BellmanFordShortestPath.findPathBetween(myGraph, getInitialState(), e.getSource());
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
		State initial = getInitialState();
		Set<State> toVisit = new HashSet<>(states.values());
		toVisit.remove(initial);
		Set<State> left = new HashSet<>(states.values());
		left.remove(initial);
		List<Graph> toReturn = new ArrayList<>();
		for (State visiting : toVisit) {
			if (left.contains(visiting)) {
				DijkstraShortestPath<State, Edge> pathFinder = new DijkstraShortestPath<>(myGraph, initial, visiting);
				List<Edge> path = pathFinder.getPathEdgeList();
				for (Edge e : path) {
					left.remove(e.getDest());
				}
				toReturn.add(buildGraph(path));
				System.out.println(left.size());
			}
		}
		return toReturn;
	}
	
	private Graph path(State start, State end) {
		DijkstraShortestPath<State, Edge> pathFinder = new DijkstraShortestPath<>(myGraph, start, end);
		List<Edge> pathEdges = pathFinder.getPathEdgeList();
		return buildGraph(pathEdges);
	}

	private State getInitialState() {
		return states.get(0);
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

	public List<State> getAllNodes() {
		return new ArrayList<>(myGraph.vertexSet());
	}

}
