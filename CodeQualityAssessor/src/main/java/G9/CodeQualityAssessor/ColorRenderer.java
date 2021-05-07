package G9.CodeQualityAssessor;

import java.awt.Color;
import java.awt.Component;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;



/**
 * The ColorRenderer class defines how the GUI Table will be rendered.
 * <p>
 * The code smell detection cells will render with different colors based on accuracy.
 * </p>
 * @author Daniel
 *
 */
public class ColorRenderer extends DefaultTableCellRenderer implements TableCellRenderer{

	/**
	 * 
	 */
	private static final long serialVersionUID = -203974302716089912L;
	public Vector<org.apache.poi.ss.usermodel.Cell> greenCells = new Vector<org.apache.poi.ss.usermodel.Cell>();
	public Vector<org.apache.poi.ss.usermodel.Cell> redCells = new Vector<org.apache.poi.ss.usermodel.Cell>();
	public Vector<org.apache.poi.ss.usermodel.Cell> blueCells = new Vector<org.apache.poi.ss.usermodel.Cell>();
	public Vector<org.apache.poi.ss.usermodel.Cell> orangeCells = new Vector<org.apache.poi.ss.usermodel.Cell>();
	public Vector<org.apache.poi.ss.usermodel.Cell> whiteCells = new Vector<org.apache.poi.ss.usermodel.Cell>();
	int god_col;
	int long_col;
	
	public ColorRenderer() {
		super();
		setOpaque(true);
	}
	
	public void setColumns(int go_d, int lon_g) {
		god_col = go_d;
		long_col = lon_g;
	}
	
	/**
	 * Overrides the DefaultTableCellRenderer method to paint code smell columns
	 * @author Daniel
	 */
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		
		Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		
		if (row==0) setBackground(Color.white);
		
		if(row>0) {
			if(column == god_col || column == long_col) {
				for(org.apache.poi.ss.usermodel.Cell c : greenCells) {
					if(c.getColumnIndex()==column && c.getRowIndex()==row) setBackground(Color.green);
				}
				for(org.apache.poi.ss.usermodel.Cell c : redCells) {
					if(c.getColumnIndex()==column && c.getRowIndex()==row) setBackground(Color.red);
				}
				for(org.apache.poi.ss.usermodel.Cell c : blueCells) {
					if(c.getColumnIndex()==column && c.getRowIndex()==row) setBackground(Color.cyan);
				}
				for(org.apache.poi.ss.usermodel.Cell c : orangeCells) {
					if(c.getColumnIndex()==column && c.getRowIndex()==row) setBackground(Color.orange);
				}
				for(org.apache.poi.ss.usermodel.Cell c : whiteCells) {
					if(c.getColumnIndex()==column && c.getRowIndex()==row) setBackground(Color.white);
				}
			}
		}
			
		return cell;
	}

}
