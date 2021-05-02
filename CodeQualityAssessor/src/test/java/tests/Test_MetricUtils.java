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

import com.github.javaparser.ast.CompilationUnit;

import Limits.RuleHandler;
import junit.*;
import metrics.Metric;
import metrics.MetricUtils;

public class Test_MetricUtils {

	MetricUtils mu;
	
	@Test 
	public void testMetricUtils() {
		CompilationUnit cu = new CompilationUnit("Test_MetricUtils");
		mu = new MetricUtils(cu);
	}
	
	
}
