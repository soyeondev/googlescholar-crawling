package com.example.scholar.controller;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.scholar.util.ExcelUtil;
import com.example.scholar.util.PoiUtil;

@Controller
public class PaperController {
	//WebDriver
    private WebDriver driver;
    
    //Properties
    public static final String WEB_DRIVER_ID = "webdriver.chrome.driver";
    public static final String WEB_DRIVER_PATH = "C:\\Program Files/chromedriver.exe";
    
    //ũ�Ѹ� �� URL
    private String base_url;
    
	@RequestMapping("/")
	public String index() {
		return "index";
	}
    
	
	// �������� ��� �̸�
	private final String[] dataHeaderList = {
			"��ȣ", "������", "����", 	"����⵵", "�ʷ�",
			"�ο�Ƚ��", "��ũ"
	};
	
	/**
	 * ũ�Ѹ� ����
	 * @param url
	 * @param response
	 */
	@ResponseBody
	@RequestMapping("/crawling.do")
	public String getDiningList(@RequestParam String url,
			HttpServletResponse response) {
		long start = System.currentTimeMillis();
		
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet();
		CellStyle cs = wb.createCellStyle();
		
		int rowIdx = 0;
		int colIdx = 0;
		
		// ��� ����
		XSSFRow header = sheet.createRow(rowIdx++);
		for(String headerStr : dataHeaderList) {
			Cell cell = header.createCell(colIdx++);
			cell.setCellValue(headerStr);
			cell.setCellStyle(cs);
		}
		
		colIdx = 0;
		
		try {
		
	    //System Property SetUp
        System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);
        
        //Driver SetUp
        driver = new ChromeDriver();
        base_url = url;
		
        driver.get(base_url);
        
        List<WebElement> anchors = driver.findElements(By.className("gs_nma"));
        System.out.println(anchors.toString());
        
        WebDriverWait wait = new WebDriverWait(driver, 100);
        
        int j = 0;
        
        for(WebElement anchor: anchors) {
	        	if(j == 2) break;
	        	
        		anchor.click();
        		List<WebElement> contents = driver.findElements(By.className("gs_ri"));

        		int i = 1;
        		
        		for(WebElement content: contents) {
            		XSSFRow row = sheet.createRow(rowIdx);
            		
            		// ��ȣ
            		row.createCell(colIdx++).setCellValue(PoiUtil.formatNumber(rowIdx));
            		
            		//�� ����
            		String title = content.findElement(By.className("gs_rt")).getText();
                	System.out.println("---title---");
                	System.out.println(title);
            		row.createCell(colIdx++).setCellValue(title);
            		
            		// �� ����
            		String paper_info = content.findElement(By.className("gs_a")).getText();
                	System.out.println("---author---");
                	Pattern patn_author = Pattern.compile("^([^0-9].*?)-");
                	Matcher matr_author = patn_author.matcher(paper_info);
                	
               	 	while(matr_author.find()) { 
          			  //map.put("title", matr.group(1));
          		      System.out.println(matr_author.group(1));
          		      row.createCell(colIdx++).setCellValue(matr_author.group(1));
          		  
          		      if(matr_author.group(1) == null) { 
          		    	  break; 
          		      } 
          		    }
               	 	
               	 	// ���� ����
               	 	System.out.println("---year---");
    	         	Pattern patn_yr = Pattern.compile("-(.*?)-");
    	         	Matcher matr_yr = patn_yr.matcher(paper_info);
    	         	
    	        	while(matr_yr.find()) { 
    	   			  //map.put("title", matr.group(1));
    	   		      System.out.println(matr_yr.group(1));
    	   		      row.createCell(colIdx++).setCellValue(matr_yr.group(1));
    	   		      
    	   		      if(matr_yr.group(1) == null) { 
    	   		    	  break; 
    	   		      } 
    	   		      
    	        	}

    	        	String abst = null;
    	        	// �ʷ�
    	        	abst = wait.until(ExpectedConditions.elementToBeClickable(By.className("gs_rs"))).getText();
    	        	
    	        	/*if(i == 1) {
    	        		abst = content.findElement(By.xpath("//*[@id=\"gs_res_ccl_mid\"]/div[7]/div/div[2]")).getText();
    	        	} else {
    	        		abst = content.findElement(By.xpath("//*[@id=\"gs_res_ccl_mid\"]/div[7]/div["+i+"]/div[2]")).getText();    	        		
    	        	}
	        		
    	        	i++;*/

    	        	System.out.println("---abst---");
	        		System.out.println(abst);
	        		row.createCell(colIdx++).setCellValue(abst);    	        		

    	        	
	        		//}
                	
                	// �ο�Ƚ��
                	String cites = content.findElement(By.className("gs_fl")).getText();
                	System.out.println("---cites---");
                	Pattern patn = Pattern.compile("(.*?)�ο�");
                	Matcher matr = patn.matcher(cites);
            	    while(matr.find()) { 
          			  //map.put("title", matr.group(1));
          		      System.out.println(matr.group(1));
          		      row.createCell(colIdx++).setCellValue(matr.group(1));
          		  
          		      if(matr.group(1) == null) { 
          		    	  break; 
          		      } 
          		    }
                	
                	// ��ũ
            		String link = content.findElement(By.className("gs_rt").tagName("a")).getAttribute("href");
                	System.out.println("---link---");
                	System.out.println(link);
                	row.createCell(colIdx++).setCellValue(link);
               	 	
            		rowIdx++;
    				colIdx = 0;
    				
            }
        		
        		j++;
            
        	}

            String fileName = "������Ʈ.xlsx";
			// ���� ���ϴٿ�ε�
			ExcelUtil.excelDownload(wb, response, fileName);
    
        } catch (Exception e) {
            
            e.printStackTrace();
        
        }
        
		String str = "";

		long end = System.currentTimeMillis();
		System.out.println("����ð�: " + (end - start) / 1000.0 + "��");

		return str;

	}

}
