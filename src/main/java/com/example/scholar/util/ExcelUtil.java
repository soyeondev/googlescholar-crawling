package com.example.scholar.util;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;

public class ExcelUtil {
	
	//�뿊�� �뙆�씪 �떎�슫濡쒕뱶
	public static void excelDownload(Workbook wb, HttpServletResponse response, String fileName) throws IOException {
		response.setHeader("Content-type", "ms-vnd/excel");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + URLEncoder.encode(fileName, "UTF-8") + "\"");
		response.setHeader("Content-Transfer-Encoding", "binary");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Expires", "0");
		
		wb.write(response.getOutputStream());
		wb.close();
	}
}
