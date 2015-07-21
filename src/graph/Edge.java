package graph;

import petriNet.Transition;

public class Edge {
	private State source;
	private State dest;
	private Transition transition;

	public Edge(Transition transition, State source, State dest) {
		this.transition = transition;
		this.source = source;
		this.dest = dest;
	}

	public State getSource() {
		return this.source;
	}

	public State getDest() {
		return this.dest;
	}

	public Transition getTransition() {
		return transition;
	}
	
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof Edge)) {
			return false;
		}
		Edge toCompare = (Edge)o;
		return source.equals(toCompare.source) && dest.equals(toCompare.dest) && transition.equals(toCompare.transition);
	}
	
	@Override
	public int hashCode() {
		int result = 17;
		result = 31* result + source.hashCode();
		result = 31* result + dest.hashCode();
		result = 31* result + transition.hashCode();
		return result;
	}

}
