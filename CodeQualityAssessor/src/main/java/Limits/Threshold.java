package Limits;

public class Threshold {
	
	private String name = "";
	private String condition = "";
	
	public Threshold(String condition,String name) {
	 this.name = name;
	 this.condition = condition;
	}
	
	public void editCondition(String condition){
		this.condition = condition;
	}
}
