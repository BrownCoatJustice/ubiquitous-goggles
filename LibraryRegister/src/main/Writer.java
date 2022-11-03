package main;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Writer {
	public static void main(String[] args) throws ParseException, IOException {
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet();

		String borrowDate = "3-11-2022";
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		Calendar cal = Calendar.getInstance();
		cal.setTime(sdf.parse(borrowDate));
		cal.add(Calendar.WEEK_OF_MONTH, 2);
		String returnDate = sdf.format(cal.getTime());

		Object bookData[][] = { { "Id.", "Issue Name", "Issued On", "Return Date" },
				{ 1, "Voldemort's Tea Party", borrowDate, returnDate },
				{ 2, "Harry's Tea Party", borrowDate, returnDate }, { 3, "Ron's Tea Party", borrowDate, returnDate } };
		int rows = bookData.length;
		int cols = bookData[0].length;
		System.out.println(rows + "\n" + cols);

		for (int r = 0; r < rows; r++) {
			XSSFRow row = sheet.createRow(r);

			for (int c = 0; c < cols; c++) {
				XSSFCell cell = row.createCell(c);
				Object value = bookData[r][c];

				if (value instanceof String)
					cell.setCellValue((String) value);
				if (value instanceof Integer)
					cell.setCellValue((Integer) value);
				if (value instanceof Boolean)
					cell.setCellValue((Boolean) value);
			}
		}
		String path = "sheet.xlsx";
		FileOutputStream os = new FileOutputStream(path);
		wb.write(os);
		os.close();
		wb.close();
	}
}
