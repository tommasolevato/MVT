package mvt;

import java.util.List;
import java.io.File;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import graph.Graph;
import graph.MarkingGraphParser;
import graph.SCGGraphParser;

public class MVT {

	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		SCGGraphParser g = new SCGGraphParser();
		Graph parsed = g
				.parse(new File("/home/tommaso/Dropbox/MVT/MVT/RTAIsimulator/TCAnalyzer/output.graphml"));
		List<Graph> allNodeCoverage = parsed.getAllEdgeCoverage();
		for(Graph path : allNodeCoverage) {
			System.out.println(path);
			System.out.println("\n");
		}
	}

}
