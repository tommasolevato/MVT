package petriNet;

import java.util.List;

public class ExecutionLog {
	private List<Transition> transitions;

	public List<Transition> getTransitions() {
		return transitions;
	}

	public ExecutionLog(List<Transition> transitions) {
		this.transitions = transitions;
	}
}
