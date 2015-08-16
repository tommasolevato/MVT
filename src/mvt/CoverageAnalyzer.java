package mvt;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import graph.Graph;
import graph.Node;
import petriNet.ExecutionLog;
import petriNet.Transition;

public class CoverageAnalyzer {
	private Graph toAnalyze;
	private List<ExecutionLog> logs;
	
	public CoverageAnalyzer(Graph toAnalyze, List<ExecutionLog> logs) {
		this.toAnalyze = toAnalyze;
		this.logs = logs;
	}
	
	public void computeCoverage() {
		Set<Node> allStates = new HashSet<>(toAnalyze.getAllNodes());
		Set<Node> visited = new HashSet<>();
		for(ExecutionLog next : logs) {
			List<Transition> transitions = next.getTransitions();
			Node source = toAnalyze.getInitialState();
			visited.add(source);
			for(Transition t : transitions) {
				source = toAnalyze.whatNodeIGetTo(source, t);
				if(source == null) {
					throw new RuntimeException("This execution is not feasible!");
				}
				visited.add(source);
			}
		}
		System.out.println("Coverage = " + ((double)visited.size() / allStates.size())*100 + "%" );
	}
}
