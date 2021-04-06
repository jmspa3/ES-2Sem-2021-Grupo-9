package CSQualityControl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import javax.swing.plaf.synth.SynthOptionPaneUI;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class CompareCodeSmellsFiles {

	private String default_cs;
	private String resulting_cs;

	public CompareCodeSmellsFiles(String default_cs, String resulting_cs) {
		this.default_cs = default_cs;
		this.resulting_cs = resulting_cs;
	}

	private Object getCellValue(Cell cell) {
		switch (cell.getCellType()) {
		case STRING:
			return cell.getStringCellValue();
		case BOOLEAN:
			return cell.getBooleanCellValue();
		case NUMERIC:
			return cell.getNumericCellValue();
		default:
			return cell;
		}
	}

	// Read the excel sheet contents
	private ArrayList<String[]> getExcelSheets() {
		try {
			FileInputStream default_file = new FileInputStream(new File(default_cs));
			FileInputStream resulting_file = new FileInputStream(new File(resulting_cs));

			// Create Workbook instance holding reference to .xlsx file
			XSSFWorkbook default_workbook = new XSSFWorkbook(default_file);
			XSSFWorkbook resulting_workbook = new XSSFWorkbook(resulting_file);

			// Get first/desired sheet from the workbook
			XSSFSheet default_sheet = default_workbook.getSheetAt(0);
			XSSFSheet resulting_sheet = resulting_workbook.getSheetAt(0);

			compareCodeSmells(default_sheet, resulting_sheet);

			default_file.close();
			resulting_file.close();
			
			FileOutputStream outputStream = new FileOutputStream(resulting_cs);
            resulting_workbook.write(outputStream);
            resulting_workbook.close();
            outputStream.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void compareCodeSmells(XSSFSheet default_sheet, XSSFSheet resulting_sheet) {
		// Iterate through each rows one by one
		Iterator<Row> default_rowIterator = default_sheet.iterator();
		Iterator<Row> resulting_rowIterator = resulting_sheet.iterator();

		default_rowIterator.next();
		resulting_rowIterator.next();
		int count = 1;

		while (default_rowIterator.hasNext()) {
			Row default_row = default_rowIterator.next();

			String default_package_name = (String) getCellValue(default_row.getCell(1));
			String default_class_name = (String) getCellValue(default_row.getCell(2));
			String default_method_name = (String) getCellValue(default_row.getCell(3));
			boolean default_is_god_class = (boolean) getCellValue(default_row.getCell(7));

			while (resulting_rowIterator.hasNext()) {
				Row resulting_row = resulting_rowIterator.next();

				String resulting_package_name = (String) getCellValue(resulting_row.getCell(1));
				String resulting_class_name = (String) getCellValue(resulting_row.getCell(2));
				String resulting_method_name = (String) getCellValue(resulting_row.getCell(3));
				boolean resulting_is_god_class = Boolean.getBoolean((String) getCellValue(resulting_row.getCell(7)));

				if (default_package_name.equals(resulting_package_name)
						&& default_class_name.equals(resulting_class_name)
						&& default_method_name.equals(resulting_method_name)) {

					if (default_is_god_class && resulting_is_god_class) {
						Cell cell = resulting_row.createCell(resulting_row.getLastCellNum());
						cell.setCellValue("VP");
					} else if (!default_is_god_class && !resulting_is_god_class) {
						Cell cell = resulting_row.createCell(resulting_row.getLastCellNum());
						cell.setCellValue("VN");
					} else if (!default_is_god_class && resulting_is_god_class) {
						Cell cell = resulting_row.createCell(resulting_row.getLastCellNum());
						cell.setCellValue("FP");
					} else if (default_is_god_class && !resulting_is_god_class) {
						Cell cell = resulting_row.createCell(resulting_row.getLastCellNum());
						cell.setCellValue("FN");
					}
				}
			}
			resulting_rowIterator = resulting_sheet.iterator();
			resulting_rowIterator.next();

		}
	}

	public static void main(String[] args) {
		CompareCodeSmellsFiles ccs = new CompareCodeSmellsFiles("/Users/nunodias/Downloads/Code_Smells.xlsx",
				"/Users/nunodias/Documents/jasml_0.10/jasml_0.10_metrics.xlsx");
		ccs.getExcelSheets();
	}

}
