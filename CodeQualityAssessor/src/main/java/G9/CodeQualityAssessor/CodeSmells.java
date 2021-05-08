package G9.CodeQualityAssessor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import metrics.Metric;


/**
 * Code Smells deals with the treatment of rules specified by the user.
 * @author Carlos, Carolina
 *
 */

public class CodeSmells {
	
	HashMap<String, List<String>> rules = new HashMap<String, List<String>>();
	
	/**
	 * Split the rules file in the specified conditions and save in a HashMap that receives a String of the line and the condition. 
	 * 
	 * @param r a String representing the rules in the file 
	 */
	
	public CodeSmells (String r) {
		String [] lines = r.split("\\r?\\n");
		for(int i=0; i<lines.length; i++) {
			String[] splitedLine = lines[i].split(";");
			List<String> conditions = Arrays.asList(splitedLine);
//			conditions.remove(0);
			rules.put(splitedLine[0], conditions);
		}
	}
	
	/**
	 * Method that detects which is the metric, equality, value from the condition specified in the HashMap.
	 * Returns a boolean from the condition specified, if true it means that the condition for the rule was well done and if false it means that it wasn't.
	 * @param rulename a String representing the name of the rule (ie. is_Long_Method)
	 * @param metric represents a Metric
	 */

	
	public String detect(String ruleName, Metric metric) {
		List<String> condition = rules.get(ruleName);
		Boolean isSmelly = null;
		String operator= null;
		
		for (int i =1; i<condition.size(); i+=4) {
			
			String metricName = condition.get(i);
			String equality = condition.get(i+1);
			String value = condition.get(i+2);
			
			boolean smellyHelper = compareLogic(metricName, equality, value, metric);
			
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
		
		return String.valueOf(isSmelly);
	}
	
	/**
	 * Method that returns a boolean between the value of the metric and the specified value in the many cases of equality.
	 * @param metricName a String representing the name of the rule (ie. is_Long_Method)
	 * @param equality a String representing the equality(>,<,=)
	 * @param value a String representing the value.
	 * @param metric 
	 */


	private boolean compareLogic(String metricName, String equality, String value, Metric metric) {
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
