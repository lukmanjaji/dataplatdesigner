/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jajitech.web.xdata.amazon.s3;




/**
 *
 * @author Usman
 */

public class AmazonS3Storage { }
    /*
    
    String home = "";
    String project_code;
    String section;
    String entry_name;
    String projects_bucket = "projects";
    boolean isMedia;
    AmazonS3Client client;
    String key = "xdata-s3-key";
    
    public AmazonS3Storage()
    {
        AWSCredentials credentials = new BasicAWSCredentials("AKIAIVLM6UQWC3RRKPAQ", "widYPPjUA2wK+gEprKYp0Eb0O0fc1n2pp6GGawOk");
        client = new AmazonS3Client(credentials);
    }
    
    
    public boolean storeToProjectsBucket(String project_code, File file)
    {
        boolean done = false;
        String fileName = projects_bucket + "/" + project_code + "/project.collector";
	try
        {
            client.putObject(new PutObjectRequest("xdataplat", fileName, file).withCannedAcl(CannedAccessControlList.PublicRead));
            client.shutdown();
            done = true;
        }
        catch(Exception er)
        {
            er.printStackTrace();
        }
        return done;
    }
    
    public boolean exists(String code) {
    try {
        client.getObjectMetadata("xdataplat", "projects/"+code+"/project.collector"); 
    } catch(AmazonServiceException e) {
        return false;
    }
    return true;
}
       
    
}
*/