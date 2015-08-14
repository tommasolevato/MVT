package petriNet;

public class Place {
	private String name;

	public Place(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Place)) {
			return false;
		}
		return name.equals(((Place) o).name);
	}

	@Override
	public int hashCode() {
		int result = 57;
		return result * name.hashCode();
	}
	
	@Override
	public String toString() {
		return name;
	}
}
