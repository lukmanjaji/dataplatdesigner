/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jajitech.desktop.xdata.ui;


import java.awt.Color;
import javax.swing.JTextArea;
import javax.swing.UIManager;

/**
 *
 * @author Usman
 */


public class JMultiLineLabel extends JTextArea{
    private static final long serialVersionUID = 1L;
    public JMultiLineLabel(String text){
        super(text);
        setEditable(false);  
        setCursor(null);  
        setOpaque(true);
        setBackground(Color.white);
        //setFocusable(false);  
        setFont(UIManager.getFont("Label.font"));      
        setWrapStyleWord(true);  
        setLineWrap(true);
        
    }
} 
