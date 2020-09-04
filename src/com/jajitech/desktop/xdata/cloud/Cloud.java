/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jajitech.desktop.xdata.cloud;

import com.jajitech.desktop.xdata.controller.MainDAO;
import java.io.File;
import java.net.URLEncoder;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author Jaji
 */
public class Cloud {
    
    String url = "",drive="org.gjt.mm.mysql.Driver";
    MainDAO main = new MainDAO();
    String FL_SERVER = "";
    String LS_SERVER = "";
    
    public Cloud()
    {
        getServers();
    }
    
    public void getServers()
    {
        String a[] = main.readFile("server.jaj").split("####");
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
    }
    
    public String getStoredPassword(String user, String project_name, String code)
    {
        String pass = "";
        String url = LS_SERVER+"xDataHandler.jsp?c=0";
        try
        {
            url = this.addParams(url, "p_code", code);
            url = this.addParams(url, "email", user);
            url = this.addParams(url, "source", "getPass");
            HttpPost post = new HttpPost(url);
            HttpClient client = HttpClients.createDefault();
            HttpResponse response = client.execute(post);
            String res = EntityUtils.toString(response.getEntity()).trim();
            pass = res;
        }
        catch(Exception er)
        {
            er.printStackTrace();
            pass = "Error making remote connection for credentials.";
        }
        return pass;
    }
    
    public String getTemplateCategories()
    {
        String pass = "";
        String url = LS_SERVER+"xDataHandler.jsp?c=0";
        try
        {
            url = this.addParams(url, "source", "cat");
            HttpPost post = new HttpPost(url);
            HttpClient client = HttpClients.createDefault();
            HttpResponse response = client.execute(post);
            String res = EntityUtils.toString(response.getEntity()).trim();
            pass = res;
        }
        catch(Exception er)
        {
            er.printStackTrace();
            pass = "Error making remote connection for templates.";
        }
        System.out.println("here is pass "+pass);
        return pass;
    }
    
    
    
    
    public String addParams(String url, String name, String value)
    {
        url = url + "&"+name+"="+URLEncoder.encode(value);
        return url;
    }
    
    public String insertIntoCloud(String project_name, String code)
    {
        String res = "...";
        String url = LS_SERVER+"xDataHandler.jsp?c=0";
        try
        {
            String a[] = main.readFile("account.jaj").split("##");
            File jajFile = new File("projects/"+project_name+"/build/"+code+".collector");
            System.out.println(jajFile.length()+" is the length ");
            url = this.addParams(url, "p_code", code);
            url = this.addParams(url, "email", a[0]);
            url = this.addParams(url, "name", project_name);
            url = this.addParams(url, "source", "insert");
            url = this.addParams(url, "fl", FL_SERVER);
            System.out.println(url);
            FileBody fileBody = new FileBody(jajFile, ContentType.DEFAULT_BINARY);
            HttpPost post = new HttpPost(url);
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            builder.addPart("file", fileBody);
            HttpEntity entity = builder.build();
            post.setEntity(entity);
            HttpClient client = HttpClients.createDefault();
            HttpResponse response = client.execute(post);
            res = EntityUtils.toString(response.getEntity()).trim();
            System.out.println("this is the doddamn response mehn "+res);
        }
        catch(Exception er)
        {
            res = "Error making remote connection to FL...";
            er.printStackTrace();
        }
        return res;
    }
    
    public String sendToCloud(String project_name, String code)
    {
        String done = "";
        /*
        AmazonS3Storage client = new AmazonS3Storage();
        done = client.exists(code);
        */
        String url = LS_SERVER+"xDataHandler.jsp?c=0";
        try
        {
            url = this.addParams(url, "p_code", code);
            url = this.addParams(url, "source", "checkExists");
            HttpPost post = new HttpPost(url);
            HttpClient client = HttpClients.createDefault();
            HttpResponse response = client.execute(post);
            done = EntityUtils.toString(response.getEntity()).trim();
        }
        catch(Exception er)
        {
            done = "Error establishing remote connection. Please check your internet.";
            er.printStackTrace();
        }
        return done;

    }
    
    
    public String Login(String user, String pass, String project_name, String code)
    {
        String found = "false";
        try
        {
            String storedPassword = this.getStoredPassword(user, project_name, code);
            
            if(storedPassword.startsWith("Error"))
            {
                return storedPassword;
            }
            
            boolean rf = new com.jajitech.web.xdata.security.salted.SecurityManager().verifyHash(pass, storedPassword);
            if(rf==true)
            {
                found = "done";
            }
            else
            {
                found = "wrong";
            }
        }
        catch(Exception er)
        {
            found = er.getMessage();
            er.printStackTrace();
        }
        return found;
    }   
}