package graph;

import java.util.HashMap;
import java.util.Map;

import petriNet.Marking;

public class MarkingState implements State {
	private static int incrementalId = 0;
	private static Map<Marking, Integer> ids = new HashMap<>();
	private Marking marking;
	private int id;

	public MarkingState(Marking marking) {
		super();
		this.marking = marking;
		if (ids.containsKey(marking)) {
			this.id = ids.get(marking);
		} else {
			ids.put(marking, incrementalId);
			this.id = incrementalId;
			incrementalId++;
		}
	}

	@Override
	public int getId() {
		return ids.get(marking);
	}

	@Override
	public String print() {
		return "Id: " + id + "\n" + marking.print();
	}

	public static int getStateId(Marking toCompute) {
		return ids.get(toCompute);
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof MarkingState)) {
			return false;
		}
		MarkingState toCompare = (MarkingState) o;
		return marking.equals(toCompare.marking) && id == toCompare.id;
	}

	@Override
	public int hashCode() {
		int result = 13;
		result = result * 23 + id;
		result = result * 23 + marking.hashCode();
		return result;
	}

}
