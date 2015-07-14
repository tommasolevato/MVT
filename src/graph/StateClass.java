package graph;

import petriNet.Domain;
import petriNet.Marking;

public class StateClass implements State {
	private int id;
	
	private Marking marking;
	private Domain domain;

	public StateClass(int id, Marking marking, Domain domain) {
		super();
		this.id = id;
		this.marking = marking;
		this.domain = domain;
	}

	@Override
	public int getId() {
		return this.id;
	}

	public Marking getMarking() {
		return this.marking;
	}

	public String print() {
		return "Id: " + id + "\n" + marking.print() + "\n" + domain.print();
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof StateClass == false) {
			return false;
		}
		StateClass toCompare = (StateClass) o;
		if (toCompare.getId() == id && toCompare.marking.equals(marking) && toCompare.domain.equals(domain)) {
			return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		final int prime = 33;
		int result = 1;
		result = prime * result + Integer.valueOf(id).hashCode() + marking.hashCode() + domain.hashCode();
		return result;
	}
	
}
