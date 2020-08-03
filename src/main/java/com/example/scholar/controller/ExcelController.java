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
	 * 논문데이터 엑셀 다운로드
	 * @param session
	 * @param request
	 * @param response
	 */
	@RequestMapping("/excel")
	public void downloadExcel() {

		
	}
	
}
