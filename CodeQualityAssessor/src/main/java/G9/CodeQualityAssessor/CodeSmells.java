package G9.CodeQualityAssessor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
	
	
	//get 
	private ArrayList<Integer> getMetricValue(List<ArrayList<String>>metrics, String metrica) {
		ArrayList<Integer> values = new ArrayList<>();
		switch(metrica) {
			case "NOM_class":
				for(int i=0;i<metrics.size(); i++) {
					values.add(Integer.parseInt(metrics.get(i).get(8)));
				}
				
				
			case "LOC_class":
				for(int i=0;i<metrics.size(); i++) {
					values.add(Integer.parseInt(metrics.get(i).get(4)));
				}
				
				
			case "WMC_class":
				for(int i=0;i<metrics.size(); i++) {
					values.add(Integer.parseInt(metrics.get(i).get(9)));
				}
				
				
			case "LOC_method":
				for(int i=0;i<metrics.size(); i++) {
					values.add(Integer.parseInt(metrics.get(i).get(5)));
				}
				
				
			case "CYCLO_method":
				for(int i=0;i<metrics.size(); i++) {
					values.add(Integer.parseInt(metrics.get(i).get(6)));
				}			
		}
		
		return values;
	}
	
	//Detection of code smells 
	
	private HashMap<String, ArrayList<Boolean>> detection(List<ArrayList<String>> metrics){
		ArrayList<String[]>data = getRulesLine(new RuleHandler().getRules());
		HashMap<String, ArrayList<Boolean>> d= new HashMap<>();
		
		for(String [] line: data) {

			
				ArrayList<Integer> iList= getMetricValue(metrics,nomeMetrica);
				d.put(line[0], compare(iList,igualdade, valor));
					
			}
			
			
			
		}
		return null;
		
	}


	private ArrayList<Boolean> detect (String [] line) {
		List<ArrayList<Boolean>> codesmell = new ArrayList<>(); 

		if(igualdade != ">")
			
		for (int i=0; i<iList.size(); i++) {
			
		}
		return null;
	}
	
	
	

}
