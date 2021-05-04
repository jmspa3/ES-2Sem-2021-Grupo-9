package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
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
	File file;
	File javafile;
	String javaprogram = "public class javatestxxx {\r\n"
    		+ "    double price;\r\n"
    		+ "    // nested class\r\n"
    		+ "    \r\n"
    		+ "    public javatestxxx() {\r\n"
    		+ "    	price = 19.9;\r\n"
    		+ "    }\r\n"
    		+ "    \r\n"
    		+ "\r\n"
    		+ "    \r\n"
    		+ "    public void fu1() {\r\n"
    		+ "    	System.out.println(\"vava\");\r\n"
    		+ "    }\r\n"
    		+ "    \r\n"
    		+ "    public void fu2() {\r\n"
    		+ "    	System.out.println(\"vava\");\r\n"
    		+ "    }\r\n"
    		+ "    \r\n"
    		+ "    public void fu3() {\r\n"
    		+ "    	System.out.println(\"vava\");\r\n"
    		+ "    }\r\n"
    		+ "    \r\n"
    		+ "    public void fu4() {\r\n"
    		+ "    	System.out.println(\"vava\");\r\n"
    		+ "    }\r\n"
    		+ "    \r\n"
    		+ "    public void fu5() {\r\n"
    		+ "    	System.out.println(\"vava\");\r\n"
    		+ "    }\r\n"
    		+ "    \r\n"
    		+ "    public class Processor{\r\n"
    		+ "    	\r\n"
    		+ "    	javatestxxx.RAM dl;\r\n"
    		+ "    	String model; \r\n"
    		+ "    	String strin2g; \r\n"
    		+ "    	int number, test;\r\n"
    		+ "         // members of nested class\r\n"
    		+ "        double cores;\r\n"
    		+ "        String manufacturer;\r\n"
    		+ "        \r\n"
    		+ "   	public Processor(javatestxxx.RAM dl, String strin2g, int number, int test) {\r\n"
    		+ "    		this.strin2g = strin2g;\r\n"
    		+ "    		this.number = number;\r\n"
    		+ "    		this.test = test;\r\n"
    		+ "    		this.dl = dl;\r\n"
    		+ "    		model = \"test example\";\r\n"
    		+ "    	}\r\n"
    		+ "    	\r\n"
    		+ "    	void futilefunction(javatestxxx.RAM dl) {\r\n"
    		+ "    		this.dl = dl;\r\n"
    		+ "    	}\r\n"
    		+ "\r\n"
    		+ "\r\n"
    		+ "        double getCache(){\r\n"
    		+ "            return 4.3;\r\n"
    		+ "        }\r\n"
    		+ "		\r\n"
    		+ "		double getStmst(boolean tr){\r\n"
    		+ "		double re = 0.0;\r\n"
    		+ "		String c = \"ddd\";\r\n"
    		+ "		if(tr) c = \"eee\";\r\n"
    		+ "		switch (c) {\r\n"
    		+ "		case \"eee\": re = 9.9; break;\r\n"
    		+ "		case \"ddd\": re = 10.0; break;\r\n"
    		+ "		default: re = 3.3; break;\r\n"
    		+ "		\r\n"
    		+ "		}\r\n"
    		+ "		return re;\r\n"
    		+ "		}\r\n"
    		+ "    }\r\n"
    		+ "\r\n"
    		+ "    // nested protected class\r\n"
    		+ "    public class RAM{\r\n"
    		+ "    	\r\n"
    		+ "\r\n"
    		+ "    	javatestxxx cpu;\r\n"
    		+ "        // members of protected nested class\r\n"
    		+ "        double memory;\r\n"
    		+ "        String manufacturer;\r\n"
    		+ "        javatestxxx.Processor dl;\r\n"
    		+ "        \r\n"
    		+ "        public RAM(javatestxxx.Processor dl, String ob) {\r\n"
    		+ "    		this.dl = dl;\r\n"
    		+ "    		memory = 9.9;\r\n"
    		+ "    		manufacturer = \"G.SKILL\";\r\n"
    		+ "    	}\r\n"
    		+ "    	\r\n"
    		+ "    	public void setRAM(javatestxxx.Processor dl, String man, double d, int test, javatestxxx cp, String strings ) {\r\n"
    		+ "    		cpu = cp;\r\n"
    		+ "    		manufacturer = man;\r\n"
    		+ "    		memory = d;\r\n"
    		+ "    	}\r\n"
    		+ "        double getClockSpeed(){\r\n"
    		+ "            return 5.5;\r\n"
    		+ "        }\r\n"
    		+ "    }\r\n"
    		+ "    \r\n"
    		+ "\r\n"
    		+ "}";
	@Before
	public void setup() {

		//mi = new MetricInfo(currentpath);
		/*for(String x: mi.pathnames) {
			files.add(x);
		}*/
		
	}
	
	public void createJavaFileDir() {
		currentpath = System.getProperty("user.dir");
		currentpath = currentpath + "\\src\\main\\java\\Limits\\";
        file = new File(currentpath + "\\testDIRtestxxx\\");
        javafile = new File(currentpath + "\\testDIRtestxxx\\javatestxxx.java");
        /*try {
			file.createNewFile();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/

        boolean flag = file.mkdirs(); 
        try {
			javafile.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        try {
            FileWriter myWriter = new FileWriter(currentpath + "\\testDIRtestxxx\\javatestxxx.java");
            myWriter.write(javaprogram);
            myWriter.close();
          } catch (IOException e) {
            e.printStackTrace();
        }
		
	}
	
	public void deleteJavaFileDir() {
		javafile.delete();
		file.delete();
	}
	
	@Test
	public void testMetricInfo() {
		createJavaFileDir();
		currentpath = System.getProperty("user.dir");
		currentpath = currentpath + "\\src\\main\\java\\Limits\\";
		mi = new MetricInfo(currentpath);
		System.out.println(currentpath);
		deleteJavaFileDir();
	}
	
	@Test//(expected = NullPointerException.class)
	public void testFindJavaFilePaths() {
		//mi.findJavaFilePaths(new File(currentpath));
		createJavaFileDir();
		mi = new MetricInfo(currentpath);
		mi.findJavaFilePaths(new File(currentpath));
		deleteJavaFileDir();

		
	}
	
	@Test 
	public void testGetMetrics() {
		createJavaFileDir();
		mi = new MetricInfo(currentpath);
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
		deleteJavaFileDir();

	}
	
	@Test
	public void testRunTroughJavaFilesPrint() {
		createJavaFileDir();
		mi = new MetricInfo(currentpath);
		try {
			mi.runTroughJavaFilesPrint();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		deleteJavaFileDir();

	}
	
	/*@After
	public void deleteTemp() {
        File file1 = new File(currentpath + "\\testDIRtestxxx\\javatestxxx.java"); 
		file1.delete();
		
        File file = new File(currentpath + "\\testDIRtestxxx\\"); 
		file.delete();
	}*/
}
