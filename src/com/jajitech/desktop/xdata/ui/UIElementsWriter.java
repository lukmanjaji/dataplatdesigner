/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jajitech.desktop.xdata.ui;

import com.jajitech.desktop.xdata.constants.Constants;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Jaji
 */
public class UIElementsWriter {
    
    String name;
    String section;
    File file = null;
    Constants cons = null;
    
    public UIElementsWriter(String name, String section)
    {
        this.name=name;
        this.section=section;
        file = new File("projects/"+name+"/sections/"+section+"/sectionData.jaj");
        cons = new Constants();
    }
    
    public void save(List toSave)
    {
        JSONArray jsonArray = new JSONArray();
        String details = "";
        for (Object toSave1 : toSave)
        {
            try{
                details = toSave1.toString();
                String[] split = details.split("##");
                JSONObject obj = new JSONObject();
                obj.put("type", split[0]);
                obj.put("caption", split[1].trim());
                obj.put("items",  split[2].trim());
                if(!details.startsWith("rtflabel"))
                {
                    obj.put("required",  split[3].trim());
                }
                else
                {
                    obj.put("required",  "false");
                }
                obj.put("rider", split[4].trim());
                jsonArray.add(obj);}
            catch(Exception er)
            {
                System.out.println(details);
            }
        }
        try
            {
                FileWriter f = new FileWriter(file);
                f.write(jsonArray.toJSONString());
                f.flush();
                f.close();
            }
            catch(Exception er)
            {
                er.printStackTrace();
            }
            /*
            if(details.startsWith("textfield"))
            {
                String[] split = details.split("##");
                JSONObject obj = new JSONObject();
                obj.put("type", split[0].trim());
                obj.put("caption", split[1].trim());
                obj.put("items", split[2].trim());
                obj.put("required", split[3].trim());
                jsonArray.add(obj);
            }
            
            if(details.startsWith("textarea"))
            {
                String[] split = details.split("##");
                JSONObject obj = new JSONObject();
                obj.put("type", split[1].trim());
                obj.put("caption", split[1].trim());
                obj.put("items", split[2].trim());
                obj.put("required", split[3].trim());
                jsonArray.add(obj);
            }
             if(details.startsWith("time"))
            {
                String[] split = details.split("##");
                JSONObject obj = new JSONObject();
                obj.put("type", "time");
                obj.put("caption", split[1].trim());
                obj.put("items", split[2].trim());
                obj.put("required", split[3].trim());
                jsonArray.add(obj);
            }
             if(details.startsWith("date"))
            {
                String[] split = details.split("##");
                JSONObject obj = new JSONObject();
                obj.put("type", "date");
                obj.put("caption", split[1].trim());
                obj.put("items", split[2].trim());
                obj.put("required", split[3].trim());
                jsonArray.add(obj);
            }
             if(details.startsWith("image"))
            {
                String[] split = details.split("##");
                JSONObject obj = new JSONObject();
                obj.put("type", "image");
                obj.put("caption", split[1].trim());
                obj.put("items", split[2].trim());
                obj.put("required", split[3].trim());
                jsonArray.add(obj);
            }
             if(details.startsWith("location"))
            {
                String[] split = details.split("##");
                JSONObject obj = new JSONObject();
                obj.put("type", "location");
                obj.put("caption", split[1].trim());
                obj.put("items", split[2].trim());
                obj.put("required", split[3].trim());
                jsonArray.add(obj);
            }
             if(details.startsWith("spinner"))
            {
                String[] split = details.split("##");
                JSONObject obj = new JSONObject();
                obj.put("type", "spinner");
                obj.put("caption", split[1].trim());
                obj.put("items", split[2].trim());
                obj.put("required", split[3].trim());
                jsonArray.add(obj);
            }
              if(details.startsWith("onoff"))
            {
                String[] split = details.split("##");
                JSONObject obj = new JSONObject();
                obj.put("type", "onoff");
                obj.put("caption", split[1].trim());
                obj.put("items", split[2].trim());
                obj.put("required", split[3].trim());
                jsonArray.add(obj);
            }
            
            if(details.startsWith("drop"))
            {
                String[] split = details.split("##");
                JSONObject obj = new JSONObject();
                obj.put("type", "drop");
                obj.put("caption", split[1].trim());
                obj.put("items", split[2].trim());
                obj.put("required", split[3].trim());
                jsonArray.add(obj);
            }
            
            if(details.startsWith("checkbox"))
            {
                String[] split = details.split("##");
                JSONObject obj = new JSONObject();
                obj.put("type", "checkbox");
                obj.put("caption", split[1].trim());
                obj.put("items", split[2].trim());
                obj.put("required", split[3].trim());
                jsonArray.add(obj);
            }
            if(details.startsWith("radio"))
            {
                String[] split = details.split("##");
                JSONObject obj = new JSONObject();
                obj.put("type", "radio");
                obj.put("caption", split[1].trim());
                obj.put("items", split[2].trim());
                obj.put("required", split[3].trim());
                jsonArray.add(obj);
            }
                    */
            
        
    }
    
    public String readSectionData() throws IOException
    {
	FileInputStream fis = new FileInputStream(file);
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
