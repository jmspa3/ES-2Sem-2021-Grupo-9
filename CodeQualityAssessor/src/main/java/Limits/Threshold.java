package Limits;

import java.util.Scanner;

public class Threshold {
	
	private String name = "";
	private String condition = "";
	
	public Threshold(String condition,String name) {
	 if(verifyCondition(condition)) {
		 this.name = name;
		 this.condition = condition;
		 System.out.println("this is the cond " + condition);
	 }else 
		 throw new IllegalArgumentException("Ocorreu um erro de sintaxe na sua regra, por favor tente novamente");
	}
	
	public void editCondition(String condition){
		this.condition = condition;
		 System.out.println("modified cond " + condition);
	}
	
	
	public boolean verifyCondition(String condition) {
		/*The purpose of this function is to verify whether the syntax of the text in "textField_condicao" 
		 * is valid or not (in terms of how Java processes conditions). These are the rules for the correct
		 * functioning of the function, which work in a cycle until the scanner can't read any more data:
		 * 1st member - Number OR String similar to the way we identify LOC_class and so on
		 * 2nd member - Mandatory operator (<, >, ==)
		 * 3rd member - Equal to first, but in combination of Number - String or String - Number (12 - 2 is valid mathematically but not in this context)
		 * 4th member - Mandatory OR or AND operator, otherwise if there are no more tokens to be read, NULL*/
		
		//v0.2
		
		Scanner scanner =new Scanner(condition);
		while (scanner.hasNext()) {  
			try {
				if(!scanner.hasNext()) throw new Exception();
			String first = scanner.next();
			boolean logicChecker_first = isInteger(first);
			
			if (notValidString(first)) {throw new Exception();}
			
			String operator = scanner.next();
			if (!validConditionOperator(operator)) {throw new Exception();}
			
			String third = scanner.next();  
			boolean logicChecker_third = isInteger(third);
			if (notValidString(third) || logicChecker_first == logicChecker_third) {throw new Exception();}
			
			if (scanner.hasNext()) {
				String logic = scanner.next();
				if (!validLogicOperator(logic)) {throw new Exception();}
				if(!scanner.hasNext()) throw new Exception("Erro de sintaxe no último operador, por favor retire-o ou complete a condição.");
				System.out.println("1 " + first + operator + third + logic);
			}
			System.out.println("2 " + first + operator + third );
			
			
			} catch (Exception sequence_broken) {
				System.out.println("A condição tem erros na sintaxe pelo que não posso guardá-la.");
				scanner.close(); 
				return false;
			}
		}  
		scanner.close();  
		return true;
	}
	
	public boolean notValidString(String in) {
		return (in.equals("||") || in.equals("&&") || in.equals("<") || in.equals(">") || in.equals("=="));		
	}
	
	public boolean validLogicOperator(String in) {
		return (in.equals("||") || in.equals("&&"));		
	}
	
	public boolean validConditionOperator(String in) {
		return (in.equals("<") || in.equals(">") || in.equals("=="));
	}
	
	public static boolean isInteger(String str) {
	    if (str == null) {
	        return false;
	    }
	    int length = str.length();
	    if (length == 0) {
	        return false;
	    }
	    int i = 0;
	    if (str.charAt(0) == '-') {
	        if (length == 1) {
	            return false;
	        }
	        i = 1;
	    }
	    for (; i < length; i++) {
	        char c = str.charAt(i);
	        if (c < '0' || c > '9') {
	            return false;
	        }
	    }
	    return true;
	}
	
}
