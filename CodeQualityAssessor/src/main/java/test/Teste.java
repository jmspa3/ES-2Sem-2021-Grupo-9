package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.poi.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import G9.CodeQualityAssessor.ContentExcel;
import G9.CodeQualityAssessor.Metrica;

import org.apache.poi.*;

public class Teste {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//System.out.println(Cell.getCellType());
		ContentExcel getContentFromExcelSheets = new ContentExcel(); 
		List<Metrica> extractedMetricaData = new ArrayList<Metrica>(); 
		try {
			getContentFromExcelSheets.readBooksFromExcelFile("C:\\Users\\carol\\Downloads\\Code_Smells1.xlsx"); 
			getContentFromExcelSheets.numberTotalPackages();
			
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		/*ContentExcel conte = new ContentExcel(); 
		List<Metrica> listBook = conte.getListBook();
		String excelFilePath = "C:\\Users\\carol\\OneDrive\\Documents\\novo.xls";  //escreve check
		 
		try {
			conte.writeExcel(listBook, excelFilePath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
	}
	
	
	
}