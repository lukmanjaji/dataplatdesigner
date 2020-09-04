/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jajitech.desktop.xdata.pojos;

import java.io.Serializable;
import java.util.Date;


/**
 *
 * @author Jaji
 */

public class Sections implements Serializable {
    

    int id;
    String section_name;
    String pid;
    private Date section_date;

    public String getSection_name() {
        return section_name;
    }

    public void setSection_name(String section_name) {
        this.section_name = section_name;
    }

    public Date getSection_date() {
        return section_date;
    }

    public void setSection_date(Date section_date) {
        this.section_date = section_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    
    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }
    
    
    
}
