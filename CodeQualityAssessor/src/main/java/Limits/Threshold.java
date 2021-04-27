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
	  Note, you cannot change the operator at index 2, and there is not a == operator present as it does not make sense
	 */
	
	private String [] argument_operator = new String[5];
	private int[] editable_numbers = new int[2];
	
	
	public Threshold(String name) {
		 this.name = name;
	}
	
	public void editConditionalOp(String op) {
		argument_operator[2] = op;
	}
	
	//Edit all operators
	public void editOperators(String op1, String op2) {
		argument_operator[1] = op1;
		argument_operator[4] = op2;
	}
	
	//No longer Test Method, due to changes in Features to be released. -OLD: Test method, this is not to be played with in user version
	public void editArgs(String arg1, String arg2) {
		argument_operator[0] = arg1;
		argument_operator[3] = arg2;
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

	public void insertCondition(String condicao) {
		Scanner scanner =new Scanner(condicao);
		for(int i = 0; i <= argument_operator.length && scanner.hasNext(); i++) {
			argument_operator[i] = scanner.next();
			System.out.println(argument_operator[i]);
		}
		scanner.close();
	}
	
	//Insert True to return the first condition, False for the second
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
	

	
	@Override
	public String toString() {
		return this.name;
	}
	
	public String getName() {
		return this.name;
	}
	
	
}
