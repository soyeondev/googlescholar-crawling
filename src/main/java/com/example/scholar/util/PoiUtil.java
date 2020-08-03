package com.example.scholar.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.ss.usermodel.Cell;

public class PoiUtil {

	private static final DecimalFormat df = new DecimalFormat("#,###,###");
	
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");

	public static String getStringCellValue(Cell cell) {
		if (cell == null) {
			return "";
		}

		String result = null;
		switch (cell.getCellTypeEnum()) {
		case FORMULA:
			result = cell.getCellFormula();
			break;
		case NUMERIC:
			result = String.valueOf((int)cell.getNumericCellValue());
			break;
		case STRING:
			result = cell.getStringCellValue().trim().replaceAll("-", "");
			break;
		case BLANK:
		default:
			result = "";
			break;
		}
		
		return result;
	}
	
	public static int getIntCellValue(Cell cell) {
		if (cell == null) {
			return 0;
		}

		String result = null;
		switch (cell.getCellTypeEnum()) {
		case FORMULA:
			result = cell.getCellFormula();
			break;
		case NUMERIC:
			result = String.valueOf((int)cell.getNumericCellValue());
			break;
		case STRING:
			result = cell.getStringCellValue().trim().replaceAll(",", "");
			break;
		case BLANK:
		default:
			result = "";
			break;
		}
		
		try {
			return Integer.parseInt(result);
		} catch (Exception e) {
			return 0;
		}
	}
	
	public static String formatNumber(Integer number) {
		if (number == null) {
			return "0";
		}
		return df.format(number);
	}

	public static String formatNumber(Short number, boolean nullIfZeo) {
		if (number == null || number == 0) {
			return "";
		}
		return df.format(number);
	}
	
	public static String formatPhone(String phone) {
		if (phone == null || phone.isEmpty()) {
			return "";
		}
		
		final String _phone = phone.trim().replaceAll("-", "");
		switch (_phone.length()) {
		case 10:
			return _phone.substring(0, 3) + "-" + _phone.substring(3, 6) + "-" + _phone.substring(6);
		case 11:
			return _phone.substring(0, 3) + "-" + _phone.substring(3, 7) + "-" + _phone.substring(7);
		default:
			return _phone;
		}
	}
	
	public static String formatDate(Date date) {
		if (date == null) {
			return "";
		}
		return dateFormat.format(date);
	}
}
