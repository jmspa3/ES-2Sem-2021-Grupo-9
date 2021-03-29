package G9.CodeQualityAssessor;

import java.io.File;
import java.io.FileInputStream;
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
	    public List<Metrica> readBooksFromExcelFile(String excelFilePath) throws IOException { 
	        List<Metrica> listMetrica = new ArrayList<Metrica>(); 
	        FileInputStream inputStream = new FileInputStream(new File(excelFilePath)); 
	       
	  
	        Workbook workbook = new XSSFWorkbook(inputStream); 
	        Sheet firstSheet = workbook.getSheetAt(0); 
	        Iterator<Row> iterator = firstSheet.iterator(); 
	  
	        while (iterator.hasNext()) { 
	            Row nextRow = iterator.next(); 
	            Iterator<Cell> cellIterator = nextRow.cellIterator(); 
	            //Metrica m = new Metrica(); 
	  
	            while (cellIterator.hasNext()) { 
	                Cell nextCell = cellIterator.next(); 
	                int columnIndex = nextCell.getColumnIndex(); 
	  
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
	        
	        }
	        
	        //((FileInputStream)workbook).close(); 
	        inputStream.close(); 
	        return null; 
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
	    
	    
	    private void writeBook(Metrica m, Row row) {
	        Cell cell = row.createCell(1);
	        cell.setCellValue(m.getPackages());
	     
	        cell = row.createCell(2);
	        cell.setCellValue(m.getClasses());
	     
	        cell = row.createCell(3);
	        cell.setCellValue(m.getMethods());
	        
	        cell = row.createCell(4);
	        cell.setCellValue(m.getNOM_CLASS());
	        
	        cell = row.createCell(5);
	        cell.setCellValue(m.getLOC_CLASS());
	        
	        cell = row.createCell(6);
	        cell.setCellValue(m.getWMC_CLASS());
	        
	    }
	    
	    //method that writes in Excel
	    
	    public void writeExcel(List<Metrica> listMetrica, String excelFilePath) throws IOException {
	        Workbook workbook = new HSSFWorkbook();
	        Sheet sheet = workbook.createSheet();
			
			createHeaderRow(sheet);
	     
	        int rowCount = 0;
	     
	        for (Metrica aBook : listMetrica) {
	            Row row = sheet.createRow(++rowCount);
	            writeBook(aBook, row);
	        }
	     
	        try (FileOutputStream outputStream = new FileOutputStream(excelFilePath)) {
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
	    
	    public List<Metrica> getListBook() {
	        Metrica book3 = new Metrica(1,2,3,4,5,6,7,8);
	        Metrica book4 = new Metrica(1,2,3,4,5,6,7,8);
	     
	        List<Metrica> listBook = Arrays.asList( book3, book4);
	     
	        return listBook;
	    }
}
