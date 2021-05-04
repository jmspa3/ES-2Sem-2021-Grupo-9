/**
 * 
 */
package tests;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import javax.swing.JTable;

import org.junit.Before;
import org.junit.Test;


import G9.CodeQualityAssessor.ContentExcel;

/**
 * @author josep
 *
 */
public class Test_ContentExcel {
	private ContentExcel content ;
	@Before
	public void setUp() throws Exception {
		content = new ContentExcel();
		System.out.println("Setup");
		
		
		
	}

	

	@Test
	public void testSetData() {
		content = new ContentExcel();
		try {
			content.setData("jasml_0.10_metrics.xlsx");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals(6,content.numberTotalPackages());
		assertEquals(47,content.numberTotalClasses());
		assertEquals(255,content.numberTotalMethods());
		assertEquals(5709,content.numberTotalLines());
		
		
	}
	
	@Test
	public void testWriteExcel() {
		content = new ContentExcel();
		try {
			new ContentExcel().writeExcel("/Users/nunodias/Documents/GitHub/ES-2Sem-2021-Grupo-9/CodeQualityAssessor");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	

	
	
	

	
	
}
