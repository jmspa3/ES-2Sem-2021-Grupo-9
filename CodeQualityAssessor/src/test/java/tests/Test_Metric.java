package tests;

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
import metrics.Metric;

public class Test_Metric {
	Metric metric;
	
	@Before
	public void testGeneral() {
		metric = new Metric();
	}

	@Test
	public void testSetGetId() {
		metric.setId(90);
		assertEquals(90, Integer.parseInt(metric.getId()));
	}

	@Test
	public void testSetGetMethod_package() {
		metric.setMethod_package("packageexemplo");
		assertEquals("packageexemplo", metric.getMethod_package());		
	}

	@Test
	public void testSetGetClass_Name() {
		metric.setClass_Name("classtest");
		assertEquals("classtest", metric.getClass_Name());
	}

	@Test
	public void testSetGetMethod_name() {
		metric.setMethod_name("methodnametest");
		assertEquals("methodnametest", metric.getMethod_name());
	}

	@Test
	public void testSetGetLOC_class() {
		metric.setLOC_class(1);
		assertEquals(1, Integer.parseInt(metric.getLOC_class()));
	}

	@Test
	public void testSetGetLOC_method() {
		metric.setLOC_method(2);
		assertEquals(2, Integer.parseInt(metric.getLOC_method()));
	}

	@Test
	public void testSetGetCYCLO_method() {
		metric.setCYCLO_method(3);
		assertEquals(3, Integer.parseInt(metric.getCYCLO_method()));
	}

	@Test
	public void testSetGetNOM_class() {
		metric.setNOM_class(4);
		assertEquals(4, Integer.parseInt(metric.getNOM_class()));
	}

	@Test
	public void testSetGetWMC_class() {
		metric.setWMC_class(5);
		assertEquals(5, Integer.parseInt(metric.getWMC_class()));
	}
	
	@Test
	public void testGetValueByMetricName() {
		ArrayList<String> value = new ArrayList<String>();
		value.add("LOC_class");
		value.add("LOC_method");
		value.add("CYCLO_method");
		value.add("NOM_class");
		value.add("WMC_class");
		
		metric.setLOC_class(1);
		metric.setLOC_method(2);
		metric.setCYCLO_method(3);
		metric.setNOM_class(4);
		metric.setWMC_class(5);
		
		int iterator = 1;
		
		for(String y: value) {
			assertEquals(iterator, metric.getValueByMetricName(y));
			iterator++;
		}
		
		assertEquals(-1, metric.getValueByMetricName("default"));
	}
	
}
