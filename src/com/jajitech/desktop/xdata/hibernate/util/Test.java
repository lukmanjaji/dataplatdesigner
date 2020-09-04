/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jajitech.desktop.xdata.hibernate.util;

import com.jajitech.desktop.xdata.controller.MainDAO;
import com.jajitech.desktop.xdata.pojos.Project;
import java.util.Date;

/**
 *
 * @author Jaji
 */
public class Test {
    
    public static void main(String args[])
    {
        String url="jdbc:mysql://localhost/xdata?user=root&password=capslock";
        try
        {
            Project p = new Project();
            p.setName("Yasmine Data");
            p.setPid("76768d7s9d7s");
            p.setTarget("mobile");
            p.setProject_date(new Date().toString());
            MainDAO m = new MainDAO();
            //int x = m.saveQuestion(p);
            //System.out.println("id is "+x);
        }
        catch(Exception er)
        {
            er.printStackTrace();
        }
    }
    
}
