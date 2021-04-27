package G9.CodeQualityAssessor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import metrics.Metric;


/**
 * 
 * @author carlos, carolina
 *
 */

public class CodeSmells {
	HashMap<String, List<String>> rules = new HashMap<String, List<String>>();
	
	public CodeSmells (String r) {
		String [] lines = r.split("\\r?\\n");
		for(int i=0; i<lines.length; i++) {
			String[] splitedLine = lines[i].split(";");
			List<String> conditions = Arrays.asList(splitedLine);
//			conditions.remove(0);
			rules.put(splitedLine[0], conditions);
		}
	}

	
	public boolean detect(String ruleName, Metric metric) {
		List<String> condition = rules.get(ruleName);
		Boolean isSmelly = null;
		String operator= null;
		
		for (int i =1; i<condition.size(); i+=4) {
			
			String metricName = condition.get(i);
			String equality = condition.get(i+1);
			String value = condition.get(i+2);
			
			boolean smellyHelper = funcao2(metricName, equality, value, metric);
			
			if (isSmelly == null) {
				isSmelly=smellyHelper;
			}else {
				if(operator.equals("||")) {
					isSmelly = isSmelly || smellyHelper;
				}else if(operator.equals("&&")) {
					isSmelly = isSmelly && smellyHelper;
				}else {
					System.out.println("Rule File not correctly configured");
				}
			}
			if(i+3<condition.size()) {
				operator= condition.get(i+3);
			} 
		}
		
		return isSmelly;
	}


	private boolean funcao2(String metricName, String equality, String value, Metric metric) {
		switch(equality) {
			case ">": return (metric.getValueByMetricName(metricName) > Integer.parseInt(value));
			case "<": return (metric.getValueByMetricName(metricName) < Integer.parseInt(value));
			case "=": return (metric.getValueByMetricName(metricName) == Integer.parseInt(value));
			default : 
				System.out.println("Rule File not correctly configured");
				return false;
		}
	}
}
