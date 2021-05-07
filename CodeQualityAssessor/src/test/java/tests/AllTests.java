package tests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import junit.framework.TestSuite;

@RunWith(Suite.class)
@SuiteClasses({ Test_Threshold.class, Test_RuleHandler.class, Test_Metric.class, Test_MetricInfo.class, Test_MetricUtils.class, Test_CompareCodeSmellsFiles.class,
	Test_CodeSmells.class,Test_ColorRenderer.class,Test_ContentExcel.class })

public class AllTests extends TestSuite {
}
