package petriNet;

import java.util.Comparator;
import java.util.List;

public class Marking {
	List<Place> places;

	public Marking(List<Place> places) {
		this.places = places;
		this.places.sort(new Comparator<Place>() {
			@Override
			public int compare(Place o1, Place o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});
	}

	public String print() {
		StringBuilder s = new StringBuilder();
		s.append("Marking: ");
		for (Place p : places) {
			s.append(p.toString() + " ");
		}
		return s.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Marking)) {
			return false;
		}
		return places.equals(((Marking) o).places);
	}

	@Override
	public int hashCode() {
		return places.hashCode();
	}
}
