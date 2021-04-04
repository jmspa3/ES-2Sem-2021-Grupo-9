package G9.CodeQualityAssessor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javassist.compiler.Parser;
import metrics.Metrics;

import org.apache.poi.ss.usermodel.CellType;

public class ContentExcel { 
	
	HashSet<String> listPackages =new HashSet<String>();
	HashSet<String> listMethods =new HashSet<String>();
	HashSet<String> listClasses =new HashSet<String>();
	ArrayList<String> totalLines = new ArrayList<String>();
	ArrayList<String[]> r = new ArrayList<String[]>(); //colocar linhas
	
	    private Object getCellValue(Cell cell)  { 
	        switch (cell.getCellType()) { 
	        case  STRING: 
	            return cell.getStringCellValue(); 
	  
	        case BOOLEAN: 
	            return cell.getBooleanCellValue(); 
	  
	        case NUMERIC: 
	            return cell.getNumericCellValue(); 
	        } 
	  
	        return cell; 
	    } 
	    
	    
	    // Read the excel sheet contents 
	    public ArrayList<String[]> readBooksFromExcelFile(String excelFilePath) throws IOException { 
	    	ArrayList<String[]> matrix = new ArrayList<String[]>();
	    	ArrayList<String> lineMetrica; 
	    	
	        FileInputStream inputStream = new FileInputStream(new File(excelFilePath)); 
	       
	  
	        Workbook workbook = new XSSFWorkbook(inputStream); 
	        Sheet firstSheet = workbook.getSheetAt(0);
	        
	        Iterator<Row> iterator = firstSheet.iterator(); 
	  
	        while (iterator.hasNext()) { 
	        	lineMetrica = new ArrayList<String>();
	            Row nextRow = iterator.next(); 
	            Iterator<Cell> cellIterator = nextRow.cellIterator(); 
	  
	            while (cellIterator.hasNext()) { 
	                Cell nextCell = cellIterator.next(); 
	                int columnIndex = nextCell.getColumnIndex(); 
	  
	                try {
	                	lineMetrica.add((String)getCellValue(nextCell));
	                } catch (ClassCastException e) {
	                	lineMetrica.add(String.valueOf(getCellValue(nextCell)));
	                }
	                switch (columnIndex) { 
	                case 1:
	                	listPackages.add((String)getCellValue(nextCell)); 
	                	//System.out.println(listPackages);
	                    break; 
	                case 2: 
	                    listMethods.add((String)getCellValue(nextCell));
	                    break; 
	                case 3: 
	                	listClasses.add((String)getCellValue(nextCell));
	                    break; 
	                    
	                case 5: 
	                	totalLines.add((String)getCellValue(nextCell).toString());
	                	break; 
	                }
	            }
	            
	            String[] str = new String[11];
	            for(int i=0; i<lineMetrica.size(); i++) {
	            	str[i] = lineMetrica.get(i);
	            }
	            
	            matrix.add(str);
	        }
	        
	        //((FileInputStream)workbook).close(); 
	        inputStream.close(); 
	        return matrix;
	    }
	    
	    public int numberTotalPackages() {	
			return listPackages.size()-1;	
	    }
	    
	    public int numberTotalMethods() {
	    	return listMethods.size()-1;	
	    }
	    
	    public int numberTotalClasses() {
			return listClasses.size()-1;	
	    }
	    
	    public int numberTotalLines() {
	    	int count=0;
	    	for(int i=1; i<totalLines.size();i++) {
	    		count += Integer.parseInt(totalLines.get(i));
	    	}
			return count;	
	    }
	    
	    
	    private void writeBook(ArrayList<String> m, Row row) {
	    	//id
	        Cell cell = row.createCell(0);
	        cell.setCellValue(m.get(0));
	    	
	    	
	    	//package
	        cell = row.createCell(1);
	        cell.setCellValue(m.get(1));
	        //class
	        cell = row.createCell(2);
	        cell.setCellValue(m.get(2));
	        //method
	        cell = row.createCell(3);
	        cell.setCellValue(m.get(3));
	        //nom_class
	        cell = row.createCell(4);
	        cell.setCellValue(m.get(7));
	        //loc_class
	        cell = row.createCell(5);
	        cell.setCellValue(m.get(4));
	        //wmc_class
	        cell = row.createCell(6);
	        cell.setCellValue(m.get(8));
	        
	        //is_god_class
	        
	        //loc_method
	        cell = row.createCell(8);
	        cell.setCellValue(m.get(5));
	        //cyclo_method
	        cell = row.createCell(9);
	        cell.setCellValue(m.get(6));
	    
	        //is_long_method
	    }
	    
	    //method that writes in Excel
	    
	    public void writeExcel(String excelFilePath) throws IOException {
	        Workbook workbook = new XSSFWorkbook();
	        Sheet sheet = workbook.createSheet();
			
			createHeaderRow(sheet);
			
			List<ArrayList<String>> listMetrica = getListBook(excelFilePath+"");
			
	     
	        int rowCount = 0;
	     
	        for (ArrayList<String> m : listMetrica) {
	            Row row = sheet.createRow(++rowCount);
	            writeBook(m, row);
	        }
	     
//	        try (FileOutputStream outputStream = new FileOutputStream(excelFilePath)) {
	        try (FileOutputStream outputStream = new FileOutputStream("C:\\Users\\carlo\\Desktop\\jasml_0.10\\teste.xlsx")) {
	            workbook.write(outputStream);
	        }
	        
	    }
	    
	    
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
	        cellPackage.setCellValue("package");
	     
	        Cell cellClass = row.createCell(2);
	        cellClass.setCellStyle(cellStyle);
	        cellClass.setCellValue("class");
	     
	        Cell cellMethod = row.createCell(3);
	        cellMethod.setCellStyle(cellStyle);
	        cellMethod.setCellValue("method");
	        
	        Cell cellNOM_class = row.createCell(4);
	        cellNOM_class.setCellStyle(cellStyle);
	        cellNOM_class.setCellValue("NOM_class");
	        
	        Cell cellLOC_class = row.createCell(5);
	        cellLOC_class.setCellStyle(cellStyle);
	        cellLOC_class.setCellValue("LOC_class");
	        
	        Cell cellWMC_class = row.createCell(6);
	        cellWMC_class.setCellStyle(cellStyle);
	        cellWMC_class.setCellValue("WMC_class");
	        
	        Cell is_God_class = row.createCell(7);
	        is_God_class.setCellStyle(cellStyle);
	        is_God_class.setCellValue("is_God_class");
	        
	        Cell cellLOC_method = row.createCell(8);
	        cellLOC_method.setCellStyle(cellStyle);
	        cellLOC_method.setCellValue("LOC_method");
	        
	        Cell cellCyclo_method = row.createCell(9);
	        cellCyclo_method.setCellStyle(cellStyle);
	        cellCyclo_method.setCellValue("CYCLO_method");
	        
	        Cell is_Long_method = row.createCell(10);
	        is_Long_method.setCellStyle(cellStyle);
	        is_Long_method.setCellValue("is_Long_method");
	        
	        
	    }
	    
	    //dummy data
	    
	    public List<ArrayList<String>> getListBook(String projectPath) throws FileNotFoundException {
	    	Metrics m = new Metrics(projectPath);
	    	List<ArrayList<String>> list = m.getMetrics();
	    	System.out.println(list);
	    	return list;
	    }
	    
	    
	    public void insertData(String excelFilePath) {
	    	  try {
	    	        //Get the excel file.
	    	        FileInputStream file = new FileInputStream(new File(excelFilePath));


	    	        //Get first sheet from the workbook.
	    	        //If there have >1 sheet in your workbook, you can change it here IF you want to edit other sheets.
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
	    	        //New content for desired cell.
	    	        updatename="Lala";
	    	        //Print out the updated content.
	    	        System.out.println(updatename);
	    	        //Set the new content to your desired cell(column).
	    	        column.setCellValue(updatename); 
	    	        //Close the excel file.
	    	        file.close();
	    	        //Where you want to save the updated sheet.
	    	        FileOutputStream out = new FileOutputStream(new File(excelFilePath));
	    	        workbook.write(out);
	    	        out.close();

	    	    } catch (FileNotFoundException e) {
	    	        e.printStackTrace();
	    	    } catch (IOException e) {
	    	        e.printStackTrace();
	    	    }
	    }
}
