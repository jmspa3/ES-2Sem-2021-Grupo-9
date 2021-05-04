package tests;

import javax.swing.JTable;

import org.junit.Before;
import org.junit.Test;

import CSQualityControl.CompareCodeSmellsFiles;
import G9.CodeQualityAssessor.ColorRenderer;

public class Test_CompareCodeSmellsFiles {
	
	CompareCodeSmellsFiles ccs;
	@Before
	public void setUp() throws Exception {
		ccs = new CompareCodeSmellsFiles(
				"Code_Smells.xlsx",
				"jasml_0.10_metrics.xlsx");
		
		System.out.println("Setup");
		
		
		
	}

	
	@Test
	public void testCompareExcelSheets() {
		
		ccs.compareExcelSheets();
	}

}
