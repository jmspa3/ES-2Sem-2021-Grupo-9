package CSQualityControl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * <h1>CompareCodeSmellsFiles</h1> This is the main class to compare code smells
 * between 2 files.
 *
 * @author Nuno Dias
 * @version 1.0
 */
public class CompareCodeSmellsFiles {

	private String default_cs;
	private String resulting_cs;

	static XSSFCellStyle sky_blue;
	static XSSFCellStyle green;
	static XSSFCellStyle orange;
	static XSSFCellStyle red;

	/**
	 * Constructor of CompareCodeSmellsFiles class.
	 *
	 * @author Nuno Dias
	 * @version 1.0
	 * @param default_cs   String given by the user with the PATH to codeSmells.xlsx
	 *                     he wants to compare
	 * @param resulting_cs String given by the user with the PATH to the excel with
	 *                     the codesmells of the project chosen
	 */
	public CompareCodeSmellsFiles(String default_cs, String resulting_cs) {
		this.default_cs = default_cs;
		this.resulting_cs = resulting_cs;
	}

	/**
	 * This method is used to open both excel files with the PATH received, gets the
	 * first sheet of each file and sets the color schema. Also, it uses the
	 * "compareCodeSmells" method. at the end closes both files.
	 *
	 * @author Nuno Dias
	 * @version 1.0
	 * @return Nothing.
	 */
	public void compareExcelSheets() {
		try {
			FileInputStream default_file = new FileInputStream(new File(default_cs));
			FileInputStream resulting_file = new FileInputStream(new File(resulting_cs));

			XSSFWorkbook default_workbook = new XSSFWorkbook(default_file);
			XSSFWorkbook resulting_workbook = new XSSFWorkbook(resulting_file);

			XSSFSheet default_sheet = default_workbook.getSheetAt(0);
			XSSFSheet resulting_sheet = resulting_workbook.getSheetAt(0);

			QualityControlUtils.setColorSchema(resulting_sheet);

			compareCodeSmells(default_sheet, resulting_sheet);

			default_file.close();
			resulting_file.close();

			FileOutputStream outputStream = new FileOutputStream(resulting_cs);
			resulting_workbook.write(outputStream);
			resulting_workbook.close();
			default_workbook.close();
			outputStream.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method is used to go through each each line of both excels, and then
	 * compares the "is_god_class" and the "is_long_method" code smells using the
	 * "goThroughResultingExcel" to compare the default (first path) file with
	 * resulting file (second path).
	 *
	 * @author Nuno Dias
	 * @version 1.0
	 * @param default_sheet   the file we want to compare our resulting file to
	 * @param resulting_sheet the file being compared
	 * @return Nothing.
	 */
	private void compareCodeSmells(XSSFSheet default_sheet, XSSFSheet resulting_sheet) {
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

			String default_package_name = default_row.getCell(1).getStringCellValue();
			String default_class_name = default_row.getCell(2).getStringCellValue();
			String default_method_name = default_row.getCell(3).getStringCellValue();

			boolean default_is_god_class = default_row.getCell(default_gc_index).getBooleanCellValue();
			boolean default_is_long_method = default_row.getCell(default_lm_index).getBooleanCellValue();

			goThroughResultingExcel(resulting_rowIterator, resulting_gc_index, resulting_lm_index, default_package_name,
					default_class_name, default_method_name, default_is_god_class, default_is_long_method);

			resulting_rowIterator = resulting_sheet.iterator();
			resulting_rowIterator.next();
		}
	}

	/**
	 * This method is used to go through each each line of both excels, and then
	 * compares the "is_god_class" and the "is_long_method" code smells using the
	 * "goThroughResultingExcel" to compare the default (first path) file with
	 * resulting file (second path).
	 *
	 * @author Nuno Dias
	 * @version 1.0
	 * @param resulting_rowIterator  the row iterator of the resulting excel file
	 * @param resulting_gc_index     the index of "is_god_class" column
	 * @param resulting_lm_index     the index of "is_long_method" column
	 * @param default_package_name   the name of the package from the default excel
	 *                               file
	 * @param default_class_name     the name of the class from the default excel
	 *                               file
	 * @param default_method_name    the name of the method from the default excel
	 *                               file
	 * @param default_is_god_class   value (boolean) of the "is_god_class" cell from
	 *                               the default excel file
	 * @param default_is_long_method value (boolean) of the "is_long_method" cell
	 *                               from the default excel file
	 * @return Nothing.
	 */
	private void goThroughResultingExcel(Iterator<Row> resulting_rowIterator, int resulting_gc_index,
			int resulting_lm_index, String default_package_name, String default_class_name, String default_method_name,
			boolean default_is_god_class, boolean default_is_long_method) {

		while (resulting_rowIterator.hasNext()) {
			Row resulting_row = resulting_rowIterator.next();

			String resulting_package_name = resulting_row.getCell(1).getStringCellValue();
			String resulting_class_name = resulting_row.getCell(2).getStringCellValue();
			String resulting_method_name = resulting_row.getCell(3).getStringCellValue();

			boolean resulting_is_god_class = Boolean
					.parseBoolean(resulting_row.getCell(resulting_gc_index).getStringCellValue());
			boolean resulting_is_long_method = Boolean
					.parseBoolean(resulting_row.getCell(resulting_lm_index).getStringCellValue());

			if (default_package_name.equals(resulting_package_name) && default_class_name.equals(resulting_class_name)
					&& default_method_name.equals(resulting_method_name)) {

				QualityControlUtils.set_GOD_Class_Quality(default_is_god_class, resulting_is_god_class, resulting_row,
						resulting_gc_index);

				QualityControlUtils.set_LONG_Method_Quality(default_is_long_method, resulting_is_long_method,
						resulting_row, resulting_lm_index);

			}
		}

	}
}
