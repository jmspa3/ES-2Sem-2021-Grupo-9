package Limits;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class RuleHandler {

	private static String filePath = "ruleFile.txt";
	
	
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
	
	public static void deleteFile() {
		File file = new File(filePath);
		if(file.delete()) {
			System.out.println("Rule File deleted");
		}else {
			System.out.println("Failed to delete the file");
		}
	}
	
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
}
