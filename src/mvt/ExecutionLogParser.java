package mvt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import petriNet.ExecutionLog;
import petriNet.Transition;

public class ExecutionLogParser {
	ExecutionLog parse(File toParse) {
		List<Transition> transitions = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(toParse))) {
			String line;
			while ((line = br.readLine()) != null) {
				transitions.add(new Transition(line));
				br.readLine();
			}
			br.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		return new ExecutionLog(transitions);
	}

	List<ExecutionLog> parseFromDirectory(File directory) {
		List<ExecutionLog> toReturn = new ArrayList<>();
		for (File toParse : directory.listFiles()) {
			toReturn.add(parse(toParse));
		}
		return toReturn;
	}
}
