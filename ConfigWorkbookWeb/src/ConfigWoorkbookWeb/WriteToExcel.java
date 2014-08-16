package ConfigWoorkbookWeb;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import org.xml.sax.SAXException;

public class WriteToExcel {
	// Initialising workbook
	private HSSFWorkbook workbook;
	private Properties prop = ConfigurationProperties.getPropValues();

	// Constructor of this class
	public WriteToExcel() {
		workbook = new HSSFWorkbook();
	}
	
	// This method creates objects in an sheet
	public void createObjectSheet(String sheetName, Map<Integer, Object[]> data)
			throws SAXException,IOException, ParserConfigurationException {
		HSSFSheet sheet = workbook.createSheet(sheetName);

		for (Integer key : data.keySet()) {
			Row row = sheet.createRow(key);

			int cellnum = 0;
			for (Object obj : data.get(key)) {
				Cell cell = row.createCell(cellnum++);
				convertTypes(cell,obj);
				
			}
		}
		
	}

	// This method creates other sheets
	public void createOtherSheets(String sheetName, ArrayList<Object[]> data) throws IOException
			 {
		HSSFSheet sheet = workbook.createSheet(sheetName);

		int rownum = 0;
		for (Object[] objArr : data) {
			Row row = sheet.createRow(rownum++);

			int cellnum = 0;
			for (Object obj : objArr) {
				Cell cell = row.createCell(cellnum++);
				convertTypes(cell,obj);
			}
		}
		

	}
	
	private void convertTypes(Cell cell,Object obj) throws IOException{
		
		if (obj instanceof Date)
			cell.setCellValue((Date) obj);
		else if (obj instanceof Boolean)
			cell.setCellValue((Boolean) obj);
		else if (obj instanceof String)
			cell.setCellValue((String) obj);
		else if (obj instanceof Double)
			cell.setCellValue((Double) obj);
		else if (obj instanceof Integer)
			cell.setCellValue((Integer) obj);
		// Writing in excel file
				FileOutputStream out = new FileOutputStream(new File(
						"E:/new.xls"));
				workbook.write(out);
				out.close();
	}
}
