/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jajitech.desktop.xdata;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jajitech.desktop.xdata.elements.FormElements;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

/**
 *
 * @author Jaji
 */
public class JSONParser2 {
    
    public static void main(String[] args) {
        
        try {
 
            String obj = readFile1(new File("json.jaj"));
            
                   Gson gson = new Gson();
                   Type collectionType = new TypeToken<List<FormElements>>(){}.getType();
                   List<FormElements> details = gson.fromJson(obj, collectionType);
            for (FormElements detail : details) {
                
                System.out.println(detail.getCaption());
            }
 
 
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
