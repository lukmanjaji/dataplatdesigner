/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jajitech.desktop.xdata.ui;

import java.awt.Color;
import java.awt.Label;

/**
 *
 * @author Javalove
 */
public class RTFLabel extends Label {
    
    private static final long serialVersionUID = 1L;
    public RTFLabel(String text){
        setText(text);
        setCursor(null);  
        //setOpaque(true);
        setBackground(Color.white);
        //setFocusable(false);  
        
    }
    
}
