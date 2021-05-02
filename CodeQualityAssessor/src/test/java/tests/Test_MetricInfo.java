package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/*import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;*/

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.github.javaparser.ast.CompilationUnit;

import Limits.RuleHandler;
import junit.*;
import metrics.Metric;
import metrics.MetricInfo;
import metrics.MetricUtils;

public class Test_MetricInfo {
	
	MetricInfo mi;
	String currentpath;
	ArrayList<String> files = new ArrayList<String>();
	
	@Before
	public void setup() {
		currentpath = System.getProperty("user.dir");
		currentpath = currentpath + "\\src\\main\\java\\Limits\\";
		mi = new MetricInfo(currentpath);
		/*for(String x: mi.pathnames) {
			files.add(x);
		}*/
		
	}
	
	@Test
	public void testMetricInfo() {
		currentpath = System.getProperty("user.dir");
		currentpath = currentpath + "\\src\\main\\java\\Limits\\";
		mi = new MetricInfo(currentpath);
		System.out.println(currentpath);
	}
	
	@Test//(expected = NullPointerException.class)
	public void testFindJavaFilePaths() {
		//mi.findJavaFilePaths(new File(currentpath));
		mi.findJavaFilePaths(new File(currentpath));
		
	}
	
	@Test 
	public void testGetMetrics() {
		List<Metric> lm = null;
		try {
			lm = mi.getMetrics();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		for(Metric m: lm) {
			//System.out.println(m.getClass_Name());
			//assertEquals(0, 0);
		}
	}
	
	@Test
	public void testRunTroughJavaFilesPrint() {
		try {
			mi.runTroughJavaFilesPrint();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
