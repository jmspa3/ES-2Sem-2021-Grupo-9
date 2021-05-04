package tests;

import javax.swing.JTable;

import org.junit.Before;
import org.junit.Test;

import G9.CodeQualityAssessor.ColorRenderer;

public class Test_ColorRenderer {

	
	@Before
	public void setUp() throws Exception {

		System.out.println("Setup");
		
		
		
	}

	
	@Test
	public void testColorRenderer() {
		ColorRenderer render = new ColorRenderer();
		render.getTableCellRendererComponent(new JTable() , (Object) "value", true, true, 2, 7);
		render.getTableCellRendererComponent(new JTable() , (Object) "value", true, true, 2, 10);
		render.getTableCellRendererComponent(new JTable() , (Object) "value", true, true, 0, 7);
		render.getTableCellRendererComponent(new JTable() , (Object) "value", true, true, 0, 10);
		
	}

	
	
}
