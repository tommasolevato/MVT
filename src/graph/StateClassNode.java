package graph;

import petriNet.Domain;
import petriNet.Marking;

public class StateClassNode implements Node {
	private int id;
	
	private Marking marking;
	private Domain domain;

	public StateClassNode(int id, Marking marking, Domain domain) {
		super();
		this.id = id;
		this.marking = marking;
		this.domain = domain;
	}

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
		if (o instanceof StateClassNode == false) {
			return false;
		}
		StateClassNode toCompare = (StateClassNode) o;
		if (toCompare.getId() == id && toCompare.marking.equals(marking) && toCompare.domain.equals(domain)) {
			return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		int result = 19;
		result = result * 31 + id;
		result = result * 31 + marking.hashCode();
		result = result * 31 + domain.hashCode();
		return result;
	}
	
}
