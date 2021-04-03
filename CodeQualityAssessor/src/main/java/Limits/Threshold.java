package Limits;

import java.util.Scanner;

public class Threshold {
	
	private String name = "";
	
	/*let X > 23 || Y == 9023, then:
	  argument_operator[0] = X
	  argument_operator[1] = >
	  argument_operator[2] = ||
	  argument_operator[3] = Y
	  argument_operator[4] = ==
	  editable_numbers[0] = 23
	  editable_numbers[1] = 9023 	
	  
	  This way of storing encourages more flexibility, even if the rules are hard-coded by themselves
	 */
	
	private String [] argument_operator = new String[5];
	private int[] editable_numbers = new int[2];
	
	
	public Threshold(String condition,String name) {
	 if(verifyCondition(condition)) {
		 this.name = name;
		 //this.condition = condition;
		 System.out.println("this is the cond " + condition);
	 }else 
		 throw new IllegalArgumentException("Ocorreu um erro de sintaxe na sua regra, por favor tente novamente");
	}
	
	public Threshold(String name) {
		 this.name = name;
	}
	
	public void editCondition(String condition){
		//this.condition = condition;
		 System.out.println("modified cond " + condition);
	}
	
	//1 for first condition, 4 for the second condition
	public void editOperator(String op, int index) {
		if(index != 1 || index != 4) throw new IllegalArgumentException();
		argument_operator[index] = op;
	}
	
	public String getArgument(int index) {
		return argument_operator[index];
	}
	
	public int getNumber(int index) {
		return editable_numbers[index];
	}
	
	//Edit all operators
	public void editOperators(String op1, String op2) {
		argument_operator[1] = op1;
		argument_operator[4] = op2;
	}
	
	//Test method, this is not to be played with in user version
	public void editArgs(String arg1, String arg2) {
		argument_operator[0] = arg1;
		argument_operator[3] = arg2;
	}
	
	public void insertCondition(String condicao) {
		Scanner scanner =new Scanner(condicao);
		for(int i = 0; i <= argument_operator.length && scanner.hasNext(); i++) {
			argument_operator[i] = scanner.next();
			System.out.println(argument_operator[i]);
		}
		scanner.close();
	}
	
	//insert True to return the first condition, False for the second
	public String getCondition(boolean first) {
		if (first) return (argument_operator[0] + " " + argument_operator[1]);
		else return (argument_operator[3] + " " + argument_operator[4]);
		
	}
	
	//Edit number of condition based on index
	public void editNumber(int n, int index) {
		if (index > 2 || index < 0) throw new IllegalArgumentException("O índice está mal, por favor corrija-o (apenas pode ser 0 ou 1)");
		editable_numbers[index] = n;
	}
	
	//Edit all numbers of the Threshold
	public void editNumbers(int n1, int n2) {
		editable_numbers[0] = n1;
		editable_numbers[1] = n2;
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
		
		int it = 1;
		
		Scanner scanner =new Scanner(condition);
		while (scanner.hasNext() || it == 7) {  
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
			
			it++;
			} catch (Exception sequence_broken) {
				System.out.println("A condição tem erros na sintaxe pelo que não posso guardá-la.");
				scanner.close(); 
				return false;
			}
		}  
		scanner.close();  
		return true;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
	
	public String getCondition() {
		return "false";
		//return this.condition;
	}
	
	public String getName() {
		return this.name;
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
