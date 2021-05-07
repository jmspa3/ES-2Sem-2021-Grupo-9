package G9.CodeQualityAssessor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import Limits.RuleHandler;
import metrics.Metric;
import metrics.MetricInfo;

/**
 * ContentExcel handles all logic to create an Excel file and read an Excel file to GUI Table
 */
public class ContentExcel {

	HashSet<String> listPackages = new HashSet<String>();
	int numMethods;
	HashSet<String> listClasses = new HashSet<String>();
	HashMap<String, Integer> totalLines = new HashMap<String, Integer>();
	ArrayList<String[]> r = new ArrayList<String[]>(); // colocar linhas

	Vector<Cell> data = new Vector<Cell>();
	int tableWidth = 0;
	int tableHeight = 0;
	
	int ruleNumber;
	ArrayList<String> ruleNameList = new ArrayList<String>();
	
	/**
	 * Reads the excel file and saves the cell information in a Vector
	 * 
	 * @param excelFilePath a String representing the excel Path
	 * @throws IOException
	 * @author Daniel
	 */
	public void setData(String excelFilePath) throws IOException{
		
			FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
	
			Workbook workbook = new XSSFWorkbook(inputStream);
			Sheet sheet = workbook.getSheetAt(0);
			numMethods = sheet.getLastRowNum();
			
			Iterator<Row> rowIterator = sheet.iterator();
			while(rowIterator.hasNext()) {
				Row row = rowIterator.next();
				Iterator<Cell> cellIterator = row.cellIterator();
				
				String key = null;
				while(cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					data.add(cell);
					
					switch (cell.getColumnIndex()) {
					case 1:
						listPackages.add(cell.getStringCellValue());
						break;
					case 2:
						key = cell.getStringCellValue();
						listClasses.add(cell.getStringCellValue());
						break;
					case 5:
						try {
							if (!totalLines.containsKey(key)) {
								totalLines.put(key, Integer.parseInt(cell.getStringCellValue()));
							}
						} catch (NumberFormatException e) {
							// no problem
						}
						break;
					}
					
				}
			}	
			workbook.close();
	}
	
	
	/**
	 * Returns the total number of packages that are safe in a hashset(this not repeat the name of the same package) 
	 */

	public int numberTotalPackages() {
		return listPackages.size() - 1;
	}
	
	/**
	 * Returns the total number of methods
	 */

	public int numberTotalMethods() {
		return numMethods;
	}
	
	/**
	 * Returns the total number of classes that are safe in a hashset.
	 */

	public int numberTotalClasses() {
		return listClasses.size() - 1;
	}

	/**
	 * Returns the total number of lines.
	 */ 
	
	public int numberTotalLines() {
		int count = 0;

		for (int value : totalLines.values()) {
			count += value;
		}
		return count;
	}

	/**
	 * Creates the cell in the book of the excel and for the specified cell puts the value of the corresponding metric.s
	 * 
	 * @param m 
	 * @param row
	 * @param cs
	 */ 
	
	private void writeBook(Metric m, Row row, CodeSmells cs) {
		
		// id
		Cell cell = row.createCell(0);
		cell.setCellValue(m.getId());
		// package
		cell = row.createCell(1);
		cell.setCellValue(m.getMethod_package());
		// class
		cell = row.createCell(2);
		cell.setCellValue(m.getClass_Name());
		// method
		cell = row.createCell(3);
		cell.setCellValue(m.getMethod_name());
		// nom_class
		cell = row.createCell(4);
		cell.setCellValue(m.getNOM_class());
		// loc_class
		cell = row.createCell(5);
		cell.setCellValue(m.getLOC_class());
		// wmc_class
		cell = row.createCell(6);
		cell.setCellValue(m.getWMC_class());
		// is_god_class
		//cell = row.createCell(7);
		//cell.setCellValue(cs.detect("is_God_Class", m));
		// loc_method
		cell = row.createCell(7);
		cell.setCellValue(m.getLOC_method());
		// cyclo_method
		cell = row.createCell(8);
		cell.setCellValue(m.getCYCLO_method());
		// is_long_method
		//cell = row.createCell(10);
		//cell.setCellValue(cs.detect("is_Long_Method", m));
		
		int it = 1;
		for(String x: ruleNameList) {
		if (it <= ruleNumber) {
				cell = row.createCell(8 + it);
				cell.setCellValue(cs.detect(x, m));
				it++;
			}
		}
	}

	/**
	 * Writes content in the excel.
	 * 
	 * @param excelFilePath
	 */ 
	
	public void writeExcel(String excelFilePath) throws IOException {
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet();
		
		CodeSmells cs = new CodeSmells(RuleHandler.getRules());
		ruleNameList = RuleHandler.getRuleNames();
		ruleNumber = ruleNameList.size();
		createHeaderRow(sheet);
		//RuleHandler.addName(t.getName());
		//it++;
		//RuleHandler.setNumberRules(it);
		List<Metric> listMetrica = getListBook(excelFilePath);
		System.out.println(ruleNumber);
		tableWidth += ruleNumber;
		int rowCount = 0;



		for (Metric m : listMetrica) {
			Row row = sheet.createRow(++rowCount);
			writeBook(m, row, cs);
		}
		File file = new File(excelFilePath);

		try (FileOutputStream outputStream = new FileOutputStream(
				excelFilePath + File.separator + file.getName() + "_metrics.xlsx")) {
			workbook.write(outputStream);
		}
		workbook.close();
	}
	
	/**
	 * Creates the specified Header in the cell and in the row 0  with a style. (i.e methodid, classes, package, etc)
	 * 
	 * @param sheet
	 */ 

	private void createHeaderRow(Sheet sheet) {

		CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
		Font font = sheet.getWorkbook().createFont();
		font.setBold(true);
		cellStyle.setFont(font);

		Row row = sheet.createRow(0);

		Cell cellMethodID = row.createCell(0);
		cellMethodID.setCellStyle(cellStyle);
		cellMethodID.setCellValue("MethodID");

		Cell cellPackage = row.createCell(1);
		cellPackage.setCellStyle(cellStyle);
		cellPackage.setCellValue("Package");

		Cell cellClass = row.createCell(2);
		cellClass.setCellStyle(cellStyle);
		cellClass.setCellValue("Class");

		Cell cellMethod = row.createCell(3);
		cellMethod.setCellStyle(cellStyle);
		cellMethod.setCellValue("Method");

		Cell cellNOM_class = row.createCell(4);
		cellNOM_class.setCellStyle(cellStyle);
		cellNOM_class.setCellValue("NOM_class");

		Cell cellLOC_class = row.createCell(5);
		cellLOC_class.setCellStyle(cellStyle);
		cellLOC_class.setCellValue("LOC_class");

		Cell cellWMC_class = row.createCell(6);
		cellWMC_class.setCellStyle(cellStyle);
		cellWMC_class.setCellValue("WMC_class");

/*		Cell is_God_class = row.createCell(7);
		is_God_class.setCellStyle(cellStyle);
		is_God_class.setCellValue("is_God_class");*/

		Cell cellLOC_method = row.createCell(7);
		cellLOC_method.setCellStyle(cellStyle);
		cellLOC_method.setCellValue("LOC_method");

		Cell cellCyclo_method = row.createCell(8);
		cellCyclo_method.setCellStyle(cellStyle);
		cellCyclo_method.setCellValue("CYCLO_method");

/*		Cell is_Long_method = row.createCell(10);
		is_Long_method.setCellStyle(cellStyle);
		is_Long_method.setCellValue("is_Long_method");*/
		
		int it = 1;
		for(String x: ruleNameList) {
			if(it <= ruleNumber) {
				Cell cell = row.createCell(8 + it);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(x);
			it++;
			}
		}

	}

	/**
	 * 
	 * Returns the list in the book of the excel, it means the list of metrics.
	 * @param projectPath
	 */ 
	

	public List<Metric> getListBook(String projectPath) throws FileNotFoundException {
		MetricInfo m = new MetricInfo(projectPath);
		List<Metric> list = m.getMetrics();
		return list;
	}
	

/*	public void insertData(String excelFilePath) {
		try {
			// Get the excel file.
			FileInputStream file = new FileInputStream(new File(excelFilePath));

			// Get first sheet from the workbook.
			// If there have >1 sheet in your workbook, you can change it here IF you want
			// to edit other sheets.
			Workbook workbook = new XSSFWorkbook(file);
			Sheet firstSheet = workbook.getSheetAt(0);
			// Get the row of your desired cell.
			// Let's say that your desired cell is at row 2.
			Row row = firstSheet.getRow(1);
			// Get the column of your desired cell in your selected row.
			// Let's say that your desired cell is at column 2.
			Cell column = row.getCell(1);
			// If the cell is String type.If double or else you can change it.
			String updatename = column.getStringCellValue();
			// New content for desired cell.
			updatename = "Lala";
			// Print out the updated content.
			System.out.println(updatename);
			// Set the new content to your desired cell(column).
			column.setCellValue(updatename);
			// Close the excel file.
			file.close();
			// Where you want to save the updated sheet.
			FileOutputStream out = new FileOutputStream(new File(excelFilePath));
			workbook.write(out);
			out.close();
			workbook.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}*/
	
	
	public void setExcelWidthAndHeight(String excelFilePath) throws IOException {
		FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
		Workbook workbook = new XSSFWorkbook(inputStream);
		Sheet sheet = workbook.getSheetAt(0);
		
		tableHeight = 1 + sheet.getLastRowNum();
		
		RuleHandler.getRules();
		ruleNumber = RuleHandler.getRulesNumber();
		
		tableWidth = 9 + ruleNumber;
		
		workbook.close();
	}
}
