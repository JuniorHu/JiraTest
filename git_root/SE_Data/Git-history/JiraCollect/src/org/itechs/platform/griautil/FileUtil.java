package org.itechs.platform.griautil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

	/**
	 * Issuse会在txt中占多行，每个issue中间插入reg
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String url = "D:/data.txt";
		//FileUtil.readFileByLines(url);
		FileUtil fu = new FileUtil();
		fu.readFileByIssue(url);
		File f= new File(url);
		
	}
	 public String readFileByIssue(String fileName) {  
	        File file = new File(fileName);  
	        BufferedReader reader = null;  
	        String issue_str= "";
	        try {  
	           
	            reader = new BufferedReader(new FileReader(file));  
	            String tempString = null;  
	            int line = 1;  
	         
	            // 一次读入一行，直到读入null为文件结束  
	            while ((tempString = reader.readLine()) != null) {  
	             
	                	issue_str = issue_str + tempString;
	            }  
	            reader.close();  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        } finally {  
	            if (reader != null) {  
	                try {  
	                    reader.close();  
	                } catch (IOException e1) {  
	                }  
	            }  
	        }  
	        return issue_str;
	    }  
   
}