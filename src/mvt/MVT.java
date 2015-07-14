package mvt;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.io.File;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

import graph.Edge;
import graph.Graph;
import graph.MarkingGraphParser;
import graph.SCGGraphParser;
import graph.State;

public class MVT {

	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		MarkingGraphParser g = new MarkingGraphParser();
		Graph parsed = g
				.parse(new File("/home/tommaso/Dropbox/MVT/MVT/RTAIsimulator/TCAnalyzer/output_MarkingGraph.graphml"));
		List<Graph> allNodeCoverage = parsed.getAllNodeCoverage();
		Set<State> test = new HashSet<State>();
		for(Graph path : allNodeCoverage) {
			for(State s : path.getAllNodes()) {
				test.add(s);
			}
			//test.addAll(path.getAllEdges());
			//System.out.println(path);
			//System.out.println("\n");
		}
		System.out.println(test.size());
	}

}
