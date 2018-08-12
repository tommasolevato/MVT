package petriNet;

public class DomainInequality {
	private float minFiringTime;
	private float maxFiringTime;
	private Transition subtracting;
	private Transition toSubtract;
	private boolean newlyEnabled;

	public DomainInequality(float minFiringTime, float maxFiringTime, Transition subtracting, Transition toSubtract,
			boolean newlyEnabled) {
		this.minFiringTime = minFiringTime;
		this.maxFiringTime = maxFiringTime;
		this.subtracting = subtracting;
		this.toSubtract = toSubtract;
		this.newlyEnabled = newlyEnabled;
	}

	public String print() {
		StringBuilder s = new StringBuilder();
		s.append(minFiringTime + " <= " + "t" + subtracting.getId());
		if (toSubtract != null) {
			s.append("-t" + toSubtract.getId());
		}
		s.append(" <= " + maxFiringTime);
		if (newlyEnabled == true) {
			s.append(" [n]");
		}
		return s.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof DomainInequality)) {
			return false;
		}
		DomainInequality d = (DomainInequality) o;
		return minFiringTime == d.minFiringTime && maxFiringTime == d.maxFiringTime && subtracting.equals(d.subtracting)
				&& toSubtract.equals(d.toSubtract) && newlyEnabled == d.newlyEnabled;
	}

	@Override
	public int hashCode() {
		int result = 41;
		result = result * 11 + Float.floatToIntBits(minFiringTime);
		result = result * 11 + Float.floatToIntBits(maxFiringTime);
		result = result * 11 + subtracting.hashCode();
		result = result * 11 + (toSubtract == null ? 0 : toSubtract.hashCode());
		result = result * 11 + (newlyEnabled == true ? 1 : 0);
		return result;
	}
}
