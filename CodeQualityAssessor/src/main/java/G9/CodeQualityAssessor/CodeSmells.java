package G9.CodeQualityAssessor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import Limits.RuleHandler;

public class CodeSmells {
	
	private ArrayList<String[]>data = new ArrayList<>(); 
	
	
	private ArrayList<String[]> getRulesLine (String rules){
		String [] lines = rules.split("\\r?\\n");
		for(int i=0; i<lines.length; i++) {
			data.add(lines[i].split(";"));
		}
		return data;
	}
	
	
	//Detection of code smells 
	
	private HashMap<String, ArrayList<Boolean>> detection(List<Metric> metrics){
		ArrayList<String[]>data = getRulesLine(new RuleHandler().getRules());
		LinkedHashMap<String, ArrayList<Boolean>> isSmell= new LinkedHashMap<>();
		
		for(String [] line: data) {			
			isSmell.put(line[0], detect(line, metrics));
		}
		return isSmell;
	}
	
	private ArrayList<Boolean> detect (String[] line, List<Metric> metrics){
		ArrayList<Boolean> smellyHelper = new ArrayList<Boolean>();
		
		for (int i =1; i<line.length; i+=4) {
			String metricName = line[i];
			String igualdade = line[i+1];
			String value = line[i+2];
			String operator= line[i+3];
			
			ArrayList<Boolean> smellList = detectSingleMetric(metrics, metricName,value);
			
			if (smellyHelper.isEmpty()) {
				smellyHelper=smellList;
			}else {
				for (int j = 0; j<smellyHelper.size();j++) {
					if (smellyHelper.get(j) || smellList.get(j)) {
						smellyHelper.add(j, true);
					}else {
						smellyHelper.add(j, false);
					}
				}
			}				
		}
	}
	
	private ArrayList<Boolean> detectSingleDetect (List<Metric> metrics,String metricName,String value) {
		ArrayList<Boolean> smells = new ArrayList<Boolean>();
		
		for (Metric metric : metrics) {
			if(metric.get(metricName) > Integer.parseInt(value)) {
				smells.add(true);
			}else {
				smells.add(false);
			}
		}
		return smells;
	}
	
}
