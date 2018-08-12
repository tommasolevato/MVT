package graph;

import petriNet.Marking;

public class MarkingNode implements Node {
	private Marking marking;

	public MarkingNode(Marking marking) {
		super();
		this.marking = marking;
	}

	@Override
	public String print() {
		return marking.print();
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof MarkingNode)) {
			return false;
		}
		MarkingNode toCompare = (MarkingNode) o;
		return marking.equals(toCompare.marking);
	}

	@Override
	public int hashCode() {
		int result = 13;
		result = result * 23 + marking.hashCode();
		return result;
	}
}
