/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jajitech.desktop.xdata.waiter;

import com.jajitech.desktop.xdata.BlurryLabel;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JWindow;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

/**
 *
 * @author Jaji
 */
public class Waiter extends JWindow {
    
    BlurryLabel wlabel;
    
    public Waiter()
    {
        JPanel pp= new JPanel();
	pp.setBackground(Color.white);
	pp.setBorder(new LineBorder(Color.black,2));
	wlabel = new BlurryLabel ();
	wlabel.setFont(wlabel.getFont().deriveFont(24f));
        wlabel.setDrawBlur(true);
        //wlabel.setIcon(new ImageIcon(getClass().getResource("images/loading_check.gif")));
        wlabel.setBorder(new EmptyBorder(0, 5, 2, 5));
        pp.setPreferredSize (new Dimension (316, 79));
        pp.setLayout (null);
        pp.add (wlabel);
        wlabel.setBounds (15, 15, 290, 45);
        getContentPane().add("Center",pp);
        pack();
        setLocationRelativeTo(this);
        hide();
    }
    
    public void initWaiter()
    {	
        Thread t = new Thread(new Runnable(){ 
        public void run() 
        { 
            show(); 
        } 
    });
    t.start();	
    }
    
    public void setText(String text)
    {
        wlabel.setText(text);
    }
    
}
