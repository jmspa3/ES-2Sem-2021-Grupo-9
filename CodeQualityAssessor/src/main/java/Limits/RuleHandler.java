package Limits;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * The RuleHandler is a static class that provides logic to write, save and read rules to a text file
 * 
 * @author Daniel
 */
public class RuleHandler {

	private static final String filePath = "ruleFile.txt";
	private static ArrayList<String> ruleNameList = new ArrayList<String>() ;
	private static int numberRules = 0;
	
	/**
	 * Creates an empty file with the name specified in the variable filePath
	 */
	public static void createFile() {
		try {
			File file = new File(filePath);
		if(file.createNewFile()) {
			System.out.println("Rule File created: "+file.getName());
		}else {
			System.out.println("Rule File alredy exists.");
		}
		}catch(IOException e) {
			System.out.println("An error occured.");
			e.printStackTrace();
		}
	}
	
	/**
	 * Deletes the file with the name specified in the variable filePath
	 */
	public static void deleteFile() {
		File file = new File(filePath);
		if(file.delete()) {
			System.out.println("Rule File deleted");
		}else {
			System.out.println("Failed to delete the file");
		}
	}
	
	/**
	 * Appends the parameter rule to the end of the file
	 * @param rule 
	 */
	public static void write(String rule) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true));
			writer.append(rule+"\n");
			writer.close();
		} catch (IOException e) {
			System.out.println("An error occured.");
			e.printStackTrace();
		}
	}
	

	
	/**
	 * Reads the file specified in the variable filePath and returns a String with all the text
	 * @return A string with all the text in the file
	 */
	public static String getRules() {
		clearData();
		String rules = "";
		 int n = 0;
		try {
			File file = new File(filePath);
			Scanner reader = new Scanner(file);
			while(reader.hasNextLine()) {
				rules += reader.nextLine()+"\n";
				n = n+1;
			}
			reader.close();
		}catch(FileNotFoundException e) {
			System.out.println("An error occured.");
			e.printStackTrace();
		}

		String[] r = rules.split("\n");
		for(String rule: r) {
			String[] arr = rule.split(";", 8);
			addName(arr[0]);
		}
		setNumberRules(n);
		return rules;
	}	
	
	/**
	 * Clears the data in the object
	 */
	public static void clearData() {
		numberRules = 0;
		ruleNameList.clear();
	}
	
	/**
	 * Sets the number of rules to the user input n
	 * @param n
	 */
	public static void setNumberRules(int n) {
		numberRules = n;
	}
	
	/**
	 * Adds the name of the rule to an array
	 * @param s
	 */
	public static void addName(String s) {
		ruleNameList.add(s);
	}
	
	/**
	 * 
	 * @return the number of rules in the Rule File
	 */
	public static int getRulesNumber() {
		return numberRules;
	}
	
	/**
	 * 
	 * @return the List of rule names
	 */
	public static ArrayList<String> getRuleNames() {
		return ruleNameList;
	}
}
