package com.jajitech.desktop.xdata;


import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.imageio.ImageIO;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Jaji
 */
public class UploadImage {
 
 /**
 * @param args the command line arguments
 */
 public static void main(String[] args) {
 
 UploadImage im = new UploadImage();
 }
 
 public UploadImage()
 {
     try {
 // Read from a file
 File file = new File("images/test.png");
 if(file.exists())
 {
     System.out.println("stupid..read this file man");
 }
 BufferedImage image = ImageIO.read(getClass().getResource("test.png"));
 
 //convert image to a byte array
 ByteArrayOutputStream baos = new ByteArrayOutputStream();
 ImageIO.write(image, "png", baos);
 baos.flush();
 byte[] imageInByte = baos.toByteArray();
 baos.close();
 
 //Get connection to image upload web service
 URL url = new URL("http://localhost:8080/xdataweb/upload.jsp?filename=myimage");
 String response = "";
 HttpURLConnection conn = (HttpURLConnection) url.openConnection();
 // Set connection parameters.
 conn.setDoInput(true);
 conn.setDoOutput(true);
 conn.setUseCaches(false);
 
 //Set content type to PNG
 conn.setRequestProperty("Content-Type", "image/png");
 ByteArrayOutputStream out = (ByteArrayOutputStream)conn.getOutputStream();
 // Write out the bytes of the content string to the stream.
 out.write(imageInByte);
 out.flush();
 out.close();
 // Read response from the input stream.
 BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
 String temp;
 while ((temp = in.readLine()) != null) {
 response += temp + "\n";
 }
 temp = null;
 in.close();
 System.out.println("Server response:\n'" + response + "'");
 
 } catch (Exception e) {
 e.printStackTrace();;
 }
 }
 
 
}
