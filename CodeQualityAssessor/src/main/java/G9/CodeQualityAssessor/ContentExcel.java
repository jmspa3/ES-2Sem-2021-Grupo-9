package G9.CodeQualityAssessor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ContentExcel {
	    private Object getCellValue(Cell cell) 
	    { 
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
	    public List<Metrica> 
	    readBooksFromExcelFile(String excelFilePath) 
	        throws IOException 
	    { 
	        List<Metrica> listMetrica 
	            = new ArrayList<Metrica>(); 
	        FileInputStream inputStream 
	            = new FileInputStream(new File(excelFilePath)); 
	  
	        Workbook workbook = new XSSFWorkbook(inputStream); 
	        Sheet firstSheet = workbook.getSheetAt(0); 
	        Iterator<Row> iterator = firstSheet.iterator(); 
	  
	        while (iterator.hasNext()) { 
	            Row nextRow = iterator.next(); 
	            Iterator<Cell> cellIterator 
	                = nextRow.cellIterator(); 
	            Metrica emp = new Metrica(); 
	  
	            while (cellIterator.hasNext()) { 
	                Cell nextCell = cellIterator.next(); 
	                int columnIndex = nextCell.getColumnIndex(); 
	  
	               /*ª switch (columnIndex) { 
	                case 1: 
	                    emp.setEmployeeName( 
	                        (String)getCellValue(nextCell)); 
	                    break; 
	                case 2: 
	                    emp.setEmployeeDesignation( 
	                        (String)getCellValue(nextCell)); 
	                    break; 
	                case 3: 
	                    emp.setSalary(Double.valueOf( 
	                        (String)getCellValue(nextCell))); 
	                    break; 
	                } 
	            } 
	            listEmployees.add(emp); */
	            }
	        } 
	  
	        ((FileInputStream)workbook).close(); 
	        inputStream.close(); 
	  
	        return listMetrica; 
	    } 

	    }
