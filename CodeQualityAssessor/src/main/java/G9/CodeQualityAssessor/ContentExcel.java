package G9.CodeQualityAssessor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ContentExcel { 
	
	HashSet<String> listPackages =new HashSet<String>();
	HashSet<String> listMethods =new HashSet<String>();
	HashSet<String> listClasses =new HashSet<String>();
	ArrayList<String> totalLines = new ArrayList<String>();
	
	    private Object getCellValue(Cell cell)  { 
	        switch (cell.getCellType()) { 
	        case Cell.CELL_TYPE_STRING: 
	            return cell.getStringCellValue(); 
	  
	        case Cell.CELL_TYPE_BOOLEAN: 
	            return cell.getBooleanCellValue(); 
	  
	        case Cell.CELL_TYPE_NUMERIC: 
	            return cell.getNumericCellValue(); 
	        } 
	  
	        return null; 
	    } 
	    
	    
	    // Read the excel sheet contents and get the contents in 
	    // a list 
	    public List<Metrica> readBooksFromExcelFile(String excelFilePath) throws IOException { 
	        List<Metrica> listMetrica = new ArrayList<Metrica>(); 
	        FileInputStream inputStream = new FileInputStream(new File(excelFilePath)); 
	       
	  
	        Workbook workbook = new XSSFWorkbook(inputStream); 
	        Sheet firstSheet = workbook.getSheetAt(0); 
	        Iterator<Row> iterator = firstSheet.iterator(); 
	  
	        while (iterator.hasNext()) { 
	            Row nextRow = iterator.next(); 
	            Iterator<Cell> cellIterator = nextRow.cellIterator(); 
	            Metrica m = new Metrica(); 
	  
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
	                	totalLines.add((String)getCellValue(nextCell));
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
	    
	    
}
