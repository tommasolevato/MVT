package petriNet;

public class Transition {
	private int id;

	public Transition(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
	
	@Override
	public String toString() {
		return "Transition: t" + id;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Transition)) {
			return false;
		}
		return id == ((Transition) o).id;
	}

	@Override
	public int hashCode() {
		int result = 43;
		return result * 11 + id;
	}

}
