package mvt;

import graph.Graph;
import graph.MarkingGraphParser;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import petriNet.ExecutionLog;

public class MVT {
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		MarkingGraphParser g = new MarkingGraphParser();
		Graph parsed = g.parse(new File("D:/Documenti/VirtualBox VMs/Windows XP/output_MarkingGraph.graphml"));
		ExecutionLogParser e = new ExecutionLogParser();
		List<ExecutionLog> logs = e.parseFromDirectory(new File("C:/Users/Tommaso Levato/Desktop/test"));
		CoverageAnalyzer c = new CoverageAnalyzer(parsed, logs);
		c.computeCoverage();
	}
}
