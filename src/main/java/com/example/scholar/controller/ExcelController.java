package com.example.scholar.controller;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ExcelController {

	
	/**
	 * �������� ���� �ٿ�ε�
	 * @param session
	 * @param request
	 * @param response
	 */
	@RequestMapping("/excel")
	public void downloadExcel() {

		
	}
	
}
