package G9.CodeQualityAssessor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.*;

public class Teste {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ContentExcel getContentFromExcelSheets = new ContentExcel(); 
		List<Metrica> extractedMetricaData = new ArrayList<Metrica>(); 
		try {
			extractedMetricaData = getContentFromExcelSheets.readBooksFromExcelFile("excelFileContents.xlsx"); 
			
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		for (int i=0; i < extractedMetricaData.size(); i++) {
			System.out.println(extractedMetricaData.get(i).toString());
		}
	}
	
	private Object getCellValue(Cell cell) {
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
	
	public List<Metrica> extract (String excelfile) throws IOException  {
		List <Metrica> listmetric = new ArrayList<Metrica>();
		FileInputStream in = new FileInputStream(new File(excelfile));
		Workbook workbook = new XSSFWorkbook(in);
		Sheet firstsheet= workbook.getSheetAt(0);
		Iterator<Row>iterator=firstsheet.iterator();
		while(iterator.hasNext()) {
			Row nextRow = iterator.next();
			Iterator<Cell>cellIterator=nextRow.cellIterator();
			Metrica m= new Metrica();
			while (cellIterator.hasNext()) {
				Cell nextCell = cellIterator.next(); 
				int columnIndex = nextCell.getColumnIndex();
				  switch (columnIndex) {
				   case 4:
					   m.setNOM_CLASS((int)getCellValue(nextCell));
					   break;
				   case 5:
					   m.setLOC_CLASS((int)getCellValue(nextCell));
					   break;
				   case 6:
					   m.setWMC_CLASS((int)getCellValue(nextCell));
					   break;
				   case 8:
					   m.setLOC_method((int)getCellValue(nextCell));
					   break;
				   case 9:
					   m.setCYCLO_method((int)getCellValue(nextCell));
					   break;
				  }
			}
			listmetric.add(m);
		}
		((FileInputStream) workbook).close();
		in.close();
		return listmetric;
	}
	
	public void writeExcel(List<Metrica> metricas, String excelFilePath) throws IOException {
	    Workbook workbook = new HSSFWorkbook();
	    Sheet sheet = workbook.createSheet();
	    excelFilePath = excelFilePath + "_metrics";
	    int rowCount = 0;
	 
	    for (Metrica a : metricas) {
	        Row row = sheet.createRow(++rowCount);
	        writeBook(a, row);
	    }
	 
	    try (FileOutputStream outputStream = new FileOutputStream(excelFilePath)) {
	        workbook.write(outputStream);
	    }
	}
	
	private void writeBook(Metrica m, Row row) {
	    Cell cell = row.createCell(1);
	    cell.setCellValue(m.getNOM_CLASS());
	 
	    cell = row.createCell(2);
	    cell.setCellValue(m.getLOC_CLASS());
	 
	    cell = row.createCell(3);
	    cell.setCellValue(m.getWMC_CLASS());
	    
	    cell = row.createCell(4);
	    cell.setCellValue(m.getLOC_method());
	    
	    cell = row.createCell(5);
	    cell.setCellValue(m.getCYCLO_method());  
	}
	
	/*private void createHeaderRow(Sheet sheet) {
		 
	    CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
	    Font font = sheet.getWorkbook().createFont();
	    font.setBold(true);
	    font.setFontHeightInPoints((short) 16);
	    cellStyle.setFont(font);
	 
	    Row row = sheet.createRow(0);
	    Cell cellNOMc = row.createCell(1);
	    cellNOMc.setCellStyle(cellStyle);
	    cellNOMc.setCellValue("NOM_CLASS");
	 
	    Cell cellLOCc = row.createCell(2);
	    cellAuthor.setCellStyle(cellStyle);
	    cellAuthor.setCellValue("LOC_CLASS");
	 
	    Cell cellWMCCc = row.createCell(3);
	    cellPrice.setCellStyle(cellStyle);
	    cellPrice.setCellValue("WMC_CLASS");
	    
	    Cell cellLOCm = row.createCell(4);
	    cellPrice.setCellStyle(cellStyle);
	    cellPrice.setCellValue("LOC_method");
	    
	    Cell cellCyclom = row.createCell(5);
	    cellPrice.setCellStyle(cellStyle);
	    cellPrice.setCellValue("CYCLO_method");
	}*/

}
