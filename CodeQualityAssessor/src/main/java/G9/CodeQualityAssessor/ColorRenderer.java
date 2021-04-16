package G9.CodeQualityAssessor;

import java.awt.Color;
import java.awt.Component;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;


public class ColorRenderer extends DefaultTableCellRenderer implements TableCellRenderer{

	/**
	 * 
	 */
	private static final long serialVersionUID = -203974302716089912L;
	public Vector<org.apache.poi.ss.usermodel.Cell> red = new Vector<org.apache.poi.ss.usermodel.Cell>();
	public Vector<org.apache.poi.ss.usermodel.Cell> blue = new Vector<org.apache.poi.ss.usermodel.Cell>();
	public Vector<org.apache.poi.ss.usermodel.Cell> green = new Vector<org.apache.poi.ss.usermodel.Cell>();
	public Vector<org.apache.poi.ss.usermodel.Cell> white = new Vector<org.apache.poi.ss.usermodel.Cell>();
	
	public ColorRenderer() {
		super();
		setOpaque(true);
	}
	
		
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		
		Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		
		if (row==0) setBackground(Color.white);
		
		if(row>0) {
			if(column == 7 || column == 10) {
				for(org.apache.poi.ss.usermodel.Cell c : red) {
					if(c.getColumnIndex()==column && c.getRowIndex()==row) setBackground(Color.RED);
				}
				for(org.apache.poi.ss.usermodel.Cell c : blue) {
					if(c.getColumnIndex()==column && c.getRowIndex()==row) setBackground(Color.CYAN);
				}
				for(org.apache.poi.ss.usermodel.Cell c : green) {
					if(c.getColumnIndex()==column && c.getRowIndex()==row) setBackground(Color.green);
				}
				for(org.apache.poi.ss.usermodel.Cell c : white) {
					if(c.getColumnIndex()==column && c.getRowIndex()==row) setBackground(Color.white);
				}
			}
		}
		
		
		return cell;
	}

}
