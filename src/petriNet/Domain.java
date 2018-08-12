package petriNet;

import java.util.List;

public class Domain {
	private List<DomainInequality> inequalities;

	public Domain(List<DomainInequality> inequalities) {
		this.inequalities = inequalities;
	}

	public String print() {
		StringBuilder s = new StringBuilder();
		s.append("Domain:");
		for (DomainInequality i : inequalities) {
			s.append("\n\t" + i.print());
		}
		return s.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Domain)) {
			return false;
		}
		return inequalities.equals(((Domain) o).inequalities);
	}

	@Override
	public int hashCode() {
		return inequalities.hashCode();
	}
}
