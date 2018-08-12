package graph;

import petriNet.Transition;

public class Edge {
	private Node source;
	private Node dest;
	private Transition transition;

	public Edge(Transition transition, Node source, Node dest) {
		this.transition = transition;
		this.source = source;
		this.dest = dest;
	}

	public Node getSource() {
		return this.source;
	}

	public Node getDest() {
		return this.dest;
	}

	public Transition getTransition() {
		return transition;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Edge)) {
			return false;
		}
		Edge toCompare = (Edge) o;
		return source.equals(toCompare.source) && dest.equals(toCompare.dest)
				&& transition.equals(toCompare.transition);
	}

	@Override
	public int hashCode() {
		int result = 17;
		result = 31 * result + source.hashCode();
		result = 31 * result + dest.hashCode();
		result = 31 * result + transition.hashCode();
		return result;
	}
}
