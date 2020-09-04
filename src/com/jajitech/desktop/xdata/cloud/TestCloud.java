/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jajitech.desktop.xdata.cloud;

/**
 *
 * @author Javalove
 */
public class TestCloud {
    
    public static void main(String args[])
    {
        new TestCloud();
    }
    
    public TestCloud()
    {
        Cloud c = new Cloud();
        c.insertIntoCloud("2017 Test", "22158349");
        //String a = c.insertToCloud("ForTest", "71389470");
        //boolean a = c.sendToCloud("71389470");
        //System.out.println(a);
    }
    
}
