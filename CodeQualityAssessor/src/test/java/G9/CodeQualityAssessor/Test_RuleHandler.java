package G9.CodeQualityAssessor;

	import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;

/*import junit.framework.Test;
	import junit.framework.TestCase;
	import junit.framework.TestSuite;*/

	import org.junit.After;
	import org.junit.AfterClass;
	import org.junit.Before;
	import org.junit.BeforeClass;
	import org.junit.Test;

import Limits.RuleHandler;
import junit.*;
	
public class Test_RuleHandler {

	    @Before  
	    public void setUp() throws Exception {  
	        System.out.println("Setup");
	        RuleHandler x = new RuleHandler();
	        
	        if(!new File("ruleFile.txt").exists()) {
	        	RuleHandler.createFile();
	        	RuleHandler.write("LOC_method > 10 || CYCLO_method > 50");
	        	RuleHandler.write("WMC_class > 10 || NOM_class > 50");

	        }
	        RuleHandler.createFile();
	    }  
	    
	    @Test
	    public void testGetRules() {
	    	assertTrue("LOC_method > 10 || CYCLO_method > 50\nWMC_class > 10 || NOM_class > 50\n".equals(RuleHandler.getRules()));
	    }
	    
	    //Will test methods of RuleHandler if applicable
	    @Test
	    public void testRuleHandler() {
	    	
	    	
	    	assertEquals(2, RuleHandler.getNumberRules());
	    	RuleHandler.clearData();
	    	assertEquals(0, RuleHandler.getRulesNumber());
	    	
	    	RuleHandler.setNumberRules(19);	    	
	    	assertEquals(19, RuleHandler.getRulesNumber());
	    	
	    	RuleHandler.addName("teste1");
	    	RuleHandler.addName("teste2");
	    	RuleHandler.addName("teste3");
	    	RuleHandler.addName("teste4");
	    	RuleHandler.addName("teste5");
	    	RuleHandler.addName("teste6");
	    	
	    	ArrayList<String> test = new ArrayList<String>();
	    	test.add("teste1");
	    	test.add("teste2");
	    	test.add("teste3");
	    	test.add("teste4");
	    	test.add("teste5");
	    	test.add("teste6");

	    	assertEquals(test, RuleHandler.getRuleNames());
	    }
	    
	    @Test
	    public void testWrite() {
	    	
	    }
	    
	    @Test
	    public void testCreateFile() {
	    	RuleHandler.createFile();
	    	assertEquals("ruleFile.txt", new File("ruleFile.txt").exists() ? "ruleFile.txt" : "does not exist");
	    }
	    
	    @Test
	    public void testDeleteFile() {
	    	RuleHandler.deleteFile();
	    	assertEquals("ruleFile.txt", new File("ruleFile.txt").exists() ? "does not exist" : "ruleFile.txt");
	    }
	    
	    @After  
	    public void tearDown() throws Exception {  
	        System.out.println("Ended Unitary Tests of RuleHandler at " + System.currentTimeMillis());  
	    }  
}
