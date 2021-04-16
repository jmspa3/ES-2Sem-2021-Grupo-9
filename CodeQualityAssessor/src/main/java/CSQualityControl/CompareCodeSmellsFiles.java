package CSQualityControl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class CompareCodeSmellsFiles {

	private String default_cs;
	private String resulting_cs;

	static XSSFCellStyle sky_blue;
	static XSSFCellStyle green;
	static XSSFCellStyle orange;
	static XSSFCellStyle red;

	public CompareCodeSmellsFiles(String default_cs, String resulting_cs) {
		this.default_cs = default_cs;
		this.resulting_cs = resulting_cs;
	}

	// Read the excel sheet contents
	private void compareExcelSheets() {
		try {
			FileInputStream default_file = new FileInputStream(new File(default_cs));
			FileInputStream resulting_file = new FileInputStream(new File(resulting_cs));

			// Create Workbook instance holding reference to .xlsx file
			XSSFWorkbook default_workbook = new XSSFWorkbook(default_file);
			XSSFWorkbook resulting_workbook = new XSSFWorkbook(resulting_file);

			// Get first/desired sheet from the workbook
			XSSFSheet default_sheet = default_workbook.getSheetAt(0);
			XSSFSheet resulting_sheet = resulting_workbook.getSheetAt(0);

			QualityControlUtils.setColorSchema(resulting_sheet);

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
	}

	public void compareCodeSmells(XSSFSheet default_sheet, XSSFSheet resulting_sheet) {
		// Iterate through each rows one by one
		Iterator<Row> default_rowIterator = default_sheet.iterator();
		Iterator<Row> resulting_rowIterator = resulting_sheet.iterator();

		int default_gc_index = QualityControlUtils.get_is_god_class_colIndex(default_sheet);
		int default_lm_index = QualityControlUtils.get_is_long_method_colIndex(default_sheet);
		int resulting_gc_index = QualityControlUtils.get_is_god_class_colIndex(resulting_sheet);
		int resulting_lm_index = QualityControlUtils.get_is_long_method_colIndex(resulting_sheet);

		default_rowIterator.next();
		resulting_rowIterator.next();

		while (default_rowIterator.hasNext()) {
			Row default_row = default_rowIterator.next();

			String default_package_name = (String) QualityControlUtils.getCellValue(default_row.getCell(1));
			String default_class_name = (String) QualityControlUtils.getCellValue(default_row.getCell(2));
			String default_method_name = (String) QualityControlUtils.getCellValue(default_row.getCell(3));
			boolean default_is_god_class = (boolean) QualityControlUtils
					.getCellValue(default_row.getCell(default_gc_index));
			boolean default_is_long_method = (boolean) QualityControlUtils
					.getCellValue(default_row.getCell(default_lm_index));

			goThroughResultingExcel(resulting_rowIterator, resulting_gc_index, resulting_lm_index, default_package_name,
					default_class_name, default_method_name, default_is_god_class, default_is_long_method);

			resulting_rowIterator = resulting_sheet.iterator();
			resulting_rowIterator.next();
		}
	}

	// corrigir o código getcellvalue
	private void goThroughResultingExcel(Iterator<Row> resulting_rowIterator, int resulting_gc_index,
			int resulting_lm_index, String default_package_name, String default_class_name, String default_method_name,
			boolean default_is_god_class, boolean default_is_long_method) {

		while (resulting_rowIterator.hasNext()) {
			Row resulting_row = resulting_rowIterator.next();

			String resulting_package_name = resulting_row.getCell(1).getStringCellValue();
			// (String) QualityControlUtils.getCellValue(resulting_row.getCell(1))
			String resulting_class_name = (String) QualityControlUtils.getCellValue(resulting_row.getCell(2));
			String resulting_method_name = (String) QualityControlUtils.getCellValue(resulting_row.getCell(3));
			boolean resulting_is_god_class = Boolean
					.getBoolean((String) QualityControlUtils.getCellValue(resulting_row.getCell(resulting_gc_index)));
			boolean resulting_is_long_method = Boolean
					.getBoolean((String) QualityControlUtils.getCellValue(resulting_row.getCell(resulting_lm_index)));

			if (default_package_name.equals(resulting_package_name) && default_class_name.equals(resulting_class_name)
					&& default_method_name.equals(resulting_method_name)) {

				QualityControlUtils.set_GOD_Class_Quality(default_is_god_class, resulting_is_god_class, resulting_row, resulting_gc_index);

				QualityControlUtils.set_LONG_Method_Quality(default_is_long_method, resulting_is_long_method, resulting_row,
						resulting_lm_index);

			}
		}

	}

	

	public static void main(String[] args) {
		CompareCodeSmellsFiles ccs = new CompareCodeSmellsFiles(
				"/Users/nunodias/Downloads/Spreadsheet Files/Code_Smells.xlsx",
				"/Users/nunodias/Documents/jasml_0.10/jasml_0.10_metrics.xlsx");
		ccs.compareExcelSheets();
	}

}
