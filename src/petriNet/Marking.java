package petriNet;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Marking {
	Map<Place, Integer> id;

	public Marking(List<Place> places) {
		this.id = new TreeMap<Place, Integer>(new Comparator<Place>() {
			@Override
			public int compare(Place o1, Place o2) {
				if (o1.getId() < o2.getId()) {
					return -1;
				}
				if (o1.getId() > o2.getId()) {
					return 1;
				}
				return 0;
			}
		});

		for (Place toAdd : places) {
			addPlace(toAdd);
		}
	}

	public String print() {
		StringBuilder s = new StringBuilder();
		s.append("Marking: ");
		for (Place p : id.keySet()) {
			s.append("p" + p.getId() + "=" + id.get(p) + " ");
		}
		return s.toString();
	}

	// FIXME: bruttino
	private void addPlace(Place toAdd) {
		if (id.containsKey(toAdd)) {
			id.replace(toAdd, id.get(toAdd) + 1);
		} else {
			id.put(toAdd, 1);
		}
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Marking)) {
			return false;
		}
		return id.equals(((Marking) o).id);
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

}
