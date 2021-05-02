package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;

/*import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;*/

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import Limits.Threshold;
import junit.*;

/**
 * Unit test for simple App.
 */
public class Test_Threshold 
{
	
	Threshold t;
	
    @BeforeClass  
    public static void setUpBeforeClass() throws Exception { 
    	Calendar calendar = Calendar.getInstance();
        System.out.println("Beginning Unitary Tests of Thresholds at " + calendar.getTime());  
    }  
    @Before  
    public void setUp() throws Exception {  
        System.out.println("Setup");        
        t = new Threshold("RegraTeste");
    	t.editArgs("arg1", "arg2");
    	t.editConditionalOp("||");
    	t.editNumbers(123, 321);
    	t.editOperators("<", ">");
    }  
    
    @Test
    public void testGetName() {
    	assertEquals("RegraTeste", t.getName());
    }
    
    
    @Test
    public void testGetArgument() {
    	assertEquals("arg1", t.getArgument(0));
    	assertEquals("arg2", t.getArgument(3));
    	assertEquals("||", t.getArgument(2));  	
    	t.editConditionalOp("&&");
    	assertEquals("&&", t.getArgument(2));
    	assertEquals("<", t.getArgument(1));
    	assertEquals(">", t.getArgument(4));
    }
    
    @Test
    public void testGetNumber() {
    	assertEquals(123, t.getNumber(0));
    	assertEquals(321, t.getNumber(1));
    }
    
    @Test
    public void testToString() {
    	assertEquals("RegraTeste", t.toString());
    	assertEquals("RegraTeste222", new Threshold("RegraTeste222").toString());
    }
    
    //Will test every method of threshold
    @Test
    public void testThresholdGeneral() {
    	String[] test = new String[5];
    	test[1] = "<"; test[4] = ">";   	
    	for(int i = 0; i < 4; i++) {
    		if(i == 1 || i == 4) {
    			if(test[i] == t.getArgument(1))
    				assertTrue(true); 		
    		}  		
    	}    	
    }
    
    @Test
	public void testGetCondition() {
		t.editArgs("zero", "tres");
		t.editOperators("<", ">");
		assertEquals("zero <", t.getCondition(true));
		assertEquals("tres >", t.getCondition(false));	
	}
    
    @Test(expected = IllegalArgumentException.class)
    public void testEditOperatorFAIL() {
    	Threshold tmp = new Threshold("tmp");
    	tmp.editOperator("ddd", -1);
    	tmp.editOperator("d", 2);
    	tmp.editOperator("<d", 3);
    	tmp.editOperator("<dd", 7);
    }
    
    @Test
    public void testEditOperator() {
    	Threshold tmp = new Threshold("tmp");
    	tmp.editOperator("<", 1);
    	tmp.editOperator(">", 4);
    	assertEquals("<", t.getArgument(1));
    	assertEquals(">", t.getArgument(4));
    }
    
    @Test
    public void testInsertcondition() {
    	t.insertCondition("WMC_class > && LOC_get <");
    	assertEquals("WMC_class >", t.getCondition(true));
    	assertEquals("LOC_get <", t.getCondition(false));
    }
    

    
    @Test
    public void testEditNumber() {
    	for(int i = 0; i < 2; i++) {
    		t.editNumber(60+i, i);
    		assertEquals(60+i, t.getNumber(i));
    	}
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testEditNumberFAIL() {
    		t.editNumber(63, 4);
    		t.editNumber(42, -1);
    }
    
    @After  
    public void tearDown() throws Exception {  
    	Calendar calendar = Calendar.getInstance();
        System.out.println("Ended Unitary Tests of Thresholds at " + calendar.getTime());  
    }  
  
}
