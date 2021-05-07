package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;

/*import junit.framework.Test;
	import junit.framework.TestCase;
	import junit.framework.TestSuite;*/

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import G9.CodeQualityAssessor.CodeSmells;
import Limits.RuleHandler;
import metrics.Metric;

public class Test_CodeSmells {
	private CodeSmells cs;
	private Metric metric;

	@Before
	public void setUp() throws Exception {

		System.out.println("Setup");
		
		metric = new Metric();
		metric.setId(90);
		metric.setMethod_package("packageexemplo");
		metric.setClass_Name("classtest");
		metric.setMethod_name("methodnametest");
		metric.setLOC_class(1);
		metric.setLOC_method(2);
		metric.setCYCLO_method(3);
		metric.setNOM_class(4);
		metric.setWMC_class(5);

		
	}


	@Test
	public void testCodeSmells() {

	}

	@Test
	public void testDetect() {
		cs = new CodeSmells("is_Long_Method;LOC_method;>;50;||;CYCLO_method;<;10\r\n"
				+ "is_God_Class;WMC_class;=;50;||;NOM_class;>;12\r\n"
				+ "is_Long_Method2;LOC_method;>;50;||;CYCLO_method;=;10\r\n"
				+ "is_God_Class2;WMC_class;<;50;||;NOM_class;=;12\r\n"
				+ "is_Long_Method3;LOC_method;=;50;&&;CYCLO_method;<;10\r\n"
				+ "is_God_Class3;WMC_class;<;50;&&;NOM_class;=;12");

		String bool = cs.detect("is_Long_Method", metric);
		String bool1 = cs.detect("is_God_Class", metric);
		String bool2 = cs.detect("is_Long_Method2", metric);
		String bool3 = cs.detect("is_God_Class2", metric);
		String bool4 = cs.detect("is_Long_Method3", metric);
		String bool5 = cs.detect("is_God_Class3", metric);
		
		assertEquals("true", bool);
		assertEquals("false", bool1);
		assertEquals("false", bool2);
		assertEquals("true", bool3);
		assertEquals("false", bool4);
		assertEquals("false", bool5);
		

	}

	@Test
	public void testFuncao2() {
	}
}
