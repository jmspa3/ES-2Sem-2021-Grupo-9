package tests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import junit.framework.TestSuite;

@RunWith(Suite.class)
@SuiteClasses({ Test_Threshold.class, Test_RuleHandler.class, Test_Metric.class })

public class AllTests extends TestSuite {
}
