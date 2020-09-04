/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jajitech.desktop.xdata.constants;

import java.awt.Container;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import javax.swing.JOptionPane;

/**
 *
 * @author Jaji
 */
public class Constants {
    
    
    public static String APP_NAME = "DataPlat Studio";
    public static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvwxyz";
    public static final String NUMERIC_STRING = "0123456789";
   
/* 
    public static final String URL = "http://afrianswers.com/xdataweb/check.jsp";
    public static final String WEB_URL = "http://dataplat.net/register.jsp";
    public static final String HOST = "afrianswers.com";
    public static final String SAVE_ENTRY_URL = "afrianswers.com/xdataweb/saveEntry.jsp";
    public static final String USERNAME = "javalove";
    public static final String PASSWORD = "capslock";

    */
    
   
   
    public static String URL = "";
    public static final String WEB_URL = "http://dataplat.net/register.jsp";
    public static final String HOST = "localhost";
    public static String SAVE_ENTRY_URL = "";
    public static final String USERNAME = "root";
    public static final String PASSWORD = "";
    public static String FL_SERVER, LS_SERVER;
    
    public String readFile(String path)
    {
        String output = "";
        try{
	FileInputStream fis = new FileInputStream(path);
            try (BufferedReader br = new BufferedReader(new InputStreamReader(fis))) {
                String line = null;
                while ((line = br.readLine()) != null) {
                    output = output+line;
                }   }
        }catch(Exception er){}
        return output;
    }
    
    public Constants()
    {
        String a[] = readFile("server.jaj").split("####");
        LS_SERVER = a[0];
        FL_SERVER = a[1];
        if(!LS_SERVER.endsWith("/"))
        {
            LS_SERVER = LS_SERVER+"/";
        }
        if(!FL_SERVER.endsWith("/"))
        {
            FL_SERVER = FL_SERVER+"/";
        }
        LS_SERVER = LS_SERVER + "xdataweb/";
        FL_SERVER = FL_SERVER + "dataplat/";
        URL = LS_SERVER+"check.jsp";
        SAVE_ENTRY_URL = LS_SERVER + "saveEntry.jsp";
    }

    public static boolean isSaved = true;
    
    public String randomAlphaNumeric(int count)
    {
	StringBuilder builder = new StringBuilder();
	while (count-- != 0)
        {
            int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
	}
	return builder.toString();
    }
    
    public String randomNumeric(int count)
    {
	StringBuilder builder = new StringBuilder();
	while (count-- != 0)
        {
            int character = (int)(Math.random()*NUMERIC_STRING.length());
            builder.append(NUMERIC_STRING.charAt(character));
	}
	return builder.toString();
    }
    
    
    public boolean alert(Container c, String type, String message)
    {
        if(type.equals("info"))
        {
            JOptionPane.showMessageDialog(c, message, APP_NAME, JOptionPane.INFORMATION_MESSAGE);
            return true;
        }
        if(type.equals("warn"))
        {
            JOptionPane.showMessageDialog(c, message, APP_NAME, JOptionPane.WARNING_MESSAGE);
            return true;
        }
         if(type.equals("error"))
        {
            JOptionPane.showMessageDialog(c, message, APP_NAME, JOptionPane.ERROR_MESSAGE);
            return true;
        }
        if(type.equals("confirm"))
        {
            int x= JOptionPane.showConfirmDialog(c,message,Constants.APP_NAME,JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
            if(x==JOptionPane.YES_OPTION)
            {
                return true;
            }
        }
        return false;
    }
    
    
}
