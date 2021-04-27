package CSQualityControl;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;

public class QualityControlUtils {

	static XSSFCellStyle sky_blue = CompareCodeSmellsFiles.sky_blue;
	static XSSFCellStyle green = CompareCodeSmellsFiles.green;
	static XSSFCellStyle orange = CompareCodeSmellsFiles.orange;
	static XSSFCellStyle red = CompareCodeSmellsFiles.red;

	static int get_is_god_class_colIndex(XSSFSheet sheet) {
		int god_class_index = 0;
		for (Cell s : sheet.getRow(0)) {
			if (s.getStringCellValue().toLowerCase().equals("is_god_class")) {
				god_class_index = s.getColumnIndex();
			}
		}
		return god_class_index;
	}

	static int get_is_long_method_colIndex(XSSFSheet sheet) {
		int long_method_index = 0;
		for (Cell s : sheet.getRow(0)) {
			if (s.getStringCellValue().toLowerCase().equals("is_long_method")) {
				long_method_index = s.getColumnIndex();
			}
		}
		return long_method_index;
	}

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
