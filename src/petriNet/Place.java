package petriNet;

public class Place {
	private int id;

	public Place(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Place)) {
			return false;
		}
		return id == ((Place) o).id;
	}

	@Override
	public int hashCode() {
		int prime = 57;
		return prime * id;
	}

}
