package Limits;

/**
 * Threshold defines a code smell, based on a condition
 * 
 * @author Jose/Tiago
 */

import java.util.Scanner;

public class Threshold {
	
	/**
	 * Threshold is the class that allows the creation, edition, deletion of Code Smell Rules
	 * 
	 * @author Tiago, Jose
	 */
	
	private String name = "";
	
	/**
	 * Practical Example 
	 * let X > 23 || Y == 9023, then:
	 * argument_operator[0] = X
	 * argument_operator[1] = >
	 * argument_operator[2] = ||
	 * argument_operator[3] = Y
	 * argument_operator[4] = ==
	 * editable_numbers[0] = 23
	 * editable_numbers[1] = 9023 	
	 *  
	 * This way of storing encourages more flexibility, rules were hard-coded but not anymore 
	 * Note, you cannot change the operator at index 2, and there is not a == operator present as it does not make sense
	*/
	
	private String [] argument_operator = new String[5];
	private int[] editable_numbers = new int[2];
	
		
	/**
	 * @param name Name of this specific Threshold
	 */
	public Threshold(String name) {
		 this.name = name;
	}
	
	/**
	 * @param op Conditional Operator (|| or &&)
	 */
	public void editConditionalOp(String op) {
		argument_operator[2] = op;
	}
	
	/**
	 * Edit all operators
	 * @param op1 First Logical Operator
	 * @param op2 Second Logical Operator
	 */
	public void editOperators(String op1, String op2) {
		argument_operator[1] = op1;
		argument_operator[4] = op2;
	}
	
	 /**
	 * OLD: No longer Test Method, due to changes in Features to be released.  Test method, this is not to be played with in user version
	 * @param arg1 First Argument 
	 * @param arg2 Second Argument 
	 */
	public void editArgs(String arg1, String arg2) {
		argument_operator[0] = arg1;
		argument_operator[3] = arg2;
	}	
	
	/**
	 * 1 for first condition, 4 for the second condition
	 * @param op Change specific Logical Operator
	 * @param index Index of specific Operator
	 */
	public void editOperator(String op, int index) {
		if(index != 1 && index != 4) throw new IllegalArgumentException();
		argument_operator[index] = op;
	}
	
	/**
	 * Return Argument based on given index
	 * @param index Index of Parameter
	 * @return Argument with that index
	 */
	public String getArgument(int index) {
		return argument_operator[index];
	}
	
	/**
	 * Returns the number based on given index (0 or 1)
	 * @param index Index of Number
	 * @return Number with that index
	 */
	public int getNumber(int index) {
		return editable_numbers[index];
	}

	/**
	 * This method inserts arguments AND all operators 
	 * @param condicao Insert full condition without numbers
	 */
	public void insertCondition(String condicao) {
		Scanner scanner =new Scanner(condicao);
		for(int i = 0; i <= argument_operator.length && scanner.hasNext(); i++) {
			argument_operator[i] = scanner.next();
			//System.out.println(argument_operator[i]);
		}
		scanner.close();
	}
	
	/**
	 * Method which returns first half or second half of the Threshold
	 * Insert True to return the first condition, False for the second
	 * @param First if true -> first condition, else second condition
	 * @return Desired condition
	 */
	public String getCondition(boolean first) {
		if (first) return (argument_operator[0] + " " + argument_operator[1]);
		else return (argument_operator[3] + " " + argument_operator[4]);
		
	}
	
	/**
	 * Edit number of condition based on index
	 * @param n Number that will replace
	 * @param index Index of the number that is going to be replaced
	 */
	public void editNumber(int n, int index) {
		if (!(index == 1  || index == 0)) throw new IllegalArgumentException("O índice está mal, por favor corrija-o (apenas pode ser 0 ou 1)");
		editable_numbers[index] = n;
	}
	
	/**
	 * Edit all numbers of the Threshold
	 * @param n1 Number related to the first condition
	 * @param n2 Number related to the second condition
	 */
	public void editNumbers(int n1, int n2) {
		editable_numbers[0] = n1;
		editable_numbers[1] = n2;
	}
	

	
	/**
	 * Overrides default toString to return the name of the Threshold
	 */
	@Override
	public String toString() {
		return this.name;
	}
	
	/**
	 * @return Name of the Threshold
	 */
	public String getName() {
		return this.name;
	}
	
	
}
