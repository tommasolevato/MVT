package petriNet;

public class Transition {
	private String name;

	public Transition(String name) {
		this.name = name;
	}

	public String getId() {
		return name;
	}

	@Override
	public String toString() {
		return "Transition: " + name;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Transition)) {
			return false;
		}
		return name.equals(((Transition) o).name);
	}

	@Override
	public int hashCode() {
		int result = 43;
		return result * 11 + name.hashCode();
	}
}
