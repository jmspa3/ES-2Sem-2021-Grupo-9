package CSQualityControl;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;

/**
 * QualityControlUtils
 * <p> This is the auxiliary class to compare code
 * smells between 2 files. Used on "CompareCodeSmellsFiles" class.
 *
 * @author Nuno Dias
 * @version 1.0
 */
public class QualityControlUtils {

	static XSSFCellStyle sky_blue = CompareCodeSmellsFiles.sky_blue;
	static XSSFCellStyle green = CompareCodeSmellsFiles.green;
	static XSSFCellStyle orange = CompareCodeSmellsFiles.orange;
	static XSSFCellStyle red = CompareCodeSmellsFiles.red;

	/**
	 * This method gets the column index of "is_god_class".
	 *
	 * @author Nuno Dias
	 * @version 1.0
	 * @param sheet the sheet from the excel file we want to get the index from
	 * @return {@link Integer}
	 */
	public static int get_is_god_class_colIndex(XSSFSheet sheet) {
		int god_class_index = 0;
		for (Cell s : sheet.getRow(0)) {
			if (s.getStringCellValue().toLowerCase().equals("is_god_class")) {
				god_class_index = s.getColumnIndex();
			}
		}
		return god_class_index;
	}

	/**
	 * This method gets the column index of "is_long_method".
	 *
	 * @author Nuno Dias
	 * @version 1.0
	 * @param sheet the sheet from the excel file we want to get the index from
	 * @return {@link Integer}
	 */
	public static int get_is_long_method_colIndex(XSSFSheet sheet) {
		int long_method_index = 0;
		for (Cell s : sheet.getRow(0)) {
			if (s.getStringCellValue().toLowerCase().equals("is_long_method")) {
				long_method_index = s.getColumnIndex();
			}
		}
		return long_method_index;
	}

	/**
	 * This method sets the "god_class" quality control, using a code of colors.
	 *
	 * @author Nuno Dias
	 * @version 1.0
	 * @param default_is_god_class   value (boolean) of the "is_god_class" cell from
	 *                               the default excel
	 * @param resulting_is_god_class value (boolean) of the "is_god_class" cell from
	 *                               the resulting excel
	 * @param resulting_row          row of the resulting excel currently in
	 * @param resulting_gc_index     index of the "is_god_class" column
	 * @return Nothing.
	 */
	static void set_GOD_Class_Quality(boolean default_is_god_class, boolean resulting_is_god_class, Row resulting_row,
			int resulting_gc_index) {

		if (default_is_god_class && resulting_is_god_class) {
//			GOD_CLASS "VP"
			resulting_row.getCell(resulting_gc_index).setCellStyle(green);
		} else if (!default_is_god_class && !resulting_is_god_class) {
//			GOD_CLASS "VN"			
			resulting_row.getCell(resulting_gc_index).setCellStyle(sky_blue);
		} else if (!default_is_god_class && resulting_is_god_class) {
//			GOD_CLASS "FP"
			resulting_row.getCell(resulting_gc_index).setCellStyle(orange);
		} else if (default_is_god_class && !resulting_is_god_class) {
//			GOD_CLASS "FN"
			resulting_row.getCell(resulting_gc_index).setCellStyle(red);
		}
	}

	/**
	 * This method sets the "long_method" quality control, using a code of colors.
	 *
	 * @author Nuno Dias
	 * @version 1.0
	 * @param default_is_long_method   value (boolean) of the "is_long_method" cell
	 *                                 from the default excel
	 * @param resulting_is_long_method value (boolean) of the "is_long_method" cell
	 *                                 from the resulting excel
	 * @param resulting_row            row of the resulting excel currently in
	 * @param resulting_lm_index       index of the "is_long_method" column
	 * @return Nothing.
	 */
	static void set_LONG_Method_Quality(boolean default_is_long_method, boolean resulting_is_long_method,
			Row resulting_row, int resulting_lm_index) {

		if (default_is_long_method && resulting_is_long_method) {
//			LONG_METHOD "VP"
			resulting_row.getCell(resulting_lm_index).setCellStyle(green);
		} else if (!default_is_long_method && !resulting_is_long_method) {
//			LONG_METHOD "VN"
			resulting_row.getCell(resulting_lm_index).setCellStyle(sky_blue);
		} else if (!default_is_long_method && resulting_is_long_method) {
//			LONG_METHOD "FP"
			resulting_row.getCell(resulting_lm_index).setCellStyle(orange);
		} else if (default_is_long_method && !resulting_is_long_method) {
//			LONG_METHOD "FN"
			resulting_row.getCell(resulting_lm_index).setCellStyle(red);
		}
	}

	/**
	 * This method sets the color schema used to show the quality control.
	 *
	 * @author Nuno Dias
	 * @version 1.0
	 * @param resulting_sheet the sheet from the resulting excel file
	 * @return Nothing.
	 */
	static void setColorSchema(XSSFSheet resulting_sheet) {

		sky_blue = resulting_sheet.getWorkbook().createCellStyle();
		green = resulting_sheet.getWorkbook().createCellStyle();
		orange = resulting_sheet.getWorkbook().createCellStyle();
		red = resulting_sheet.getWorkbook().createCellStyle();

		sky_blue.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		sky_blue.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());

		green.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		green.setFillForegroundColor(IndexedColors.GREEN.getIndex());

		orange.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		orange.setFillForegroundColor(IndexedColors.ORANGE.getIndex());

		red.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		red.setFillForegroundColor(IndexedColors.RED.getIndex());

	}

}
