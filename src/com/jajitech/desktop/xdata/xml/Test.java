/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jajitech.desktop.xdata.xml;

import java.io.File;

/**
 *
 * @author Jaji
 */
public class Test {
    
    public static void main(String args[])
    {
        new Test();
    }
    
    public Test()
    {
        File f = new File("c:\\Users\\Jaji\\xData Projects\\jjj\\jjj.jaj");
        JAJFileReader j = new JAJFileReader(f);
        j.updateElement("1", "project-target", "mobile");
        j.save();
        
    }
}
