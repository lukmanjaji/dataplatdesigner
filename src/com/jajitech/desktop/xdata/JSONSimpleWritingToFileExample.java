/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jajitech.desktop.xdata;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;




/**
 *
 * @author Jaji
 */
public class JSONSimpleWritingToFileExample {
    
    public static void main(String[] args) {

        /*
        JSONObject obj = new JSONObject();
        JSONArray list = new JSONArray();
        JSONObject info= new JSONObject();
        info.put("age", "4.6 billion years");
        info.put("water", "70.8%");
        info.put("land", "29.2%");
        list.add(info);
        obj.put("facts", list);
       
 try
 {
    FileWriter f = new FileWriter("json.jaj");
    f.write(obj.toJSONString());
    f.flush();
    f.close();
 }
 catch(Exception e){}
 */
        
        
		try {
                   
			JSONArray jsonArray = new JSONArray();
                        JSONObject jsonObj= new JSONObject();  // these 4 files add jsonObject to jsonArray
                        jsonObj.put("type", "label");
                        jsonObj.put("caption", "Usman");
                        jsonObj.put("requred", Boolean.TRUE);
                        jsonObj.put("items", "male,female");
                        jsonArray.add(jsonObj);
                        JSONObject ob = new JSONObject();
                        ob.put("type", "label");
                        ob.put("caption", "Lukman");
                        ob.put("requred", Boolean.TRUE);
                        ob.put("items", "male,female");
                        jsonArray.add(ob);
                        
                       System.out.println(jsonArray.toJSONString());
                    
                    FileWriter f = new FileWriter("json.jaj");
                    jsonArray.writeJSONString(f);
                    f.close();
                    
                    
                    
                    
                    
                    
		} catch (Exception e) {
			e.printStackTrace();
		}
                
    }
    
    public static  String readFile1(File fin) throws IOException {
	FileInputStream fis = new FileInputStream(fin);
 
	//Construct BufferedReader from InputStreamReader
	BufferedReader br = new BufferedReader(new InputStreamReader(fis));
 
	String line = null;
        String output = "";
	while ((line = br.readLine()) != null) {
		output = output+line;
	}
 
	br.close();
        return output;
}
 
    
}
