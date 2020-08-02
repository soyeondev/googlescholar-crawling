package com.example.scholar.controller;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PaperController {
	//WebDriver
    private WebDriver driver;
    
    //Properties
    public static final String WEB_DRIVER_ID = "webdriver.chrome.driver";
    public static final String WEB_DRIVER_PATH = "C:\\Program Files/chromedriver.exe";
    
    //크롤링 할 URL
    private String base_url;
    
	@RequestMapping("/")
	public String index() {
		return "index";
	}
    
	@ResponseBody
	@RequestMapping("/crawling.do")
	public String getDiningList(@RequestParam String url) {
		long start = System.currentTimeMillis();
		
	    //System Property SetUp
        System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);
        
        //Driver SetUp
        driver = new ChromeDriver();
        base_url = url;
		
        try {
        	driver.get(base_url);
            List<WebElement> contents = driver.findElements(By.className("gs_ri"));
           
            for(WebElement content: contents) {
            	String title = content.findElement(By.className("gs_rt")).getText();
            	System.out.println("---title---");
            	System.out.println(title);
            	
            	String link = content.findElement(By.className("gs_rt").tagName("a")).getAttribute("href");
            	System.out.println("---link---");
            	System.out.println(link);
            	
            	String paper_info = content.findElement(By.className("gs_a")).getText();
            	System.out.println("---author---");
            	Pattern patn_author = Pattern.compile("^([^0-9].*?)-");
            	Matcher matr_author = patn_author.matcher(paper_info);
            	
           	 	while(matr_author.find()) { 
      			  //map.put("title", matr.group(1));
      		      System.out.println(matr_author.group(1));
      		  
      		      if(matr_author.group(1) == null) { 
      		    	  break; 
      		      } 
      		    }
           	 	
           	 	System.out.println("---year---");
	         	Pattern patn_yr = Pattern.compile("-(.*?)-");
	         	Matcher matr_yr = patn_yr.matcher(paper_info);
	         	
	        	while(matr_yr.find()) { 
	   			  //map.put("title", matr.group(1));
	   		      System.out.println(matr_yr.group(1));
	   		  
	   		      if(matr_yr.group(1) == null) { 
	   		    	  break; 
	   		      } 
	   		      
	        	}

           	 	String abst = content.findElement(By.className("gs_rs")).getText();
            	System.out.println("---abst---");
            	System.out.println(abst);
            	String cites = content.findElement(By.className("gs_fl")).getText();
            	System.out.println("---cites---");
            	Pattern patn = Pattern.compile("(.*?)인용");
            	Matcher matr = patn.matcher(cites);
            	
            	 while(matr.find()) { 
       			  //map.put("title", matr.group(1));
       		      System.out.println(matr.group(1));
       		  
       		      if(matr.group(1) == null) { 
       		    	  break; 
       		      } 
       		    }
            	
            }
    
        } catch (Exception e) {
            
            e.printStackTrace();
        
        }
        
		String str = "";

		long end = System.currentTimeMillis();
		System.out.println("실행시간: " + (end - start) / 1000.0 + "초");

		return str;

	}

}
