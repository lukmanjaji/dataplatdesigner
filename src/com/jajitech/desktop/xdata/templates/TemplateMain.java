/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jajitech.desktop.xdata.templates;

import com.jajitech.desktop.xdata.BlurryLabel;
import com.jajitech.desktop.xdata.Main;
import java.awt.FlowLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Lukman
 */
public class TemplateMain extends JFrame {
    
    public TemplateMain(Main main)
    {
        super("Templates Manager");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel jc1 = new JPanel (new FlowLayout(FlowLayout.CENTER));
        jc1.setBorder(Main.etched);
        //jc1.setBackground(Color.white);
        BlurryLabel ll= new BlurryLabel();
        ll.setText("Browse Templates");
              
        ll.setFont(ll.getFont().deriveFont(18f));
        ll.setDrawBlur(true);
        ll.setBorder(new EmptyBorder(0, 5, 2, 5));
        ll.setIcon(new ImageIcon(getClass().getResource("images/img.png")));
        jc1.add(ll);
        getContentPane().add("North",jc1);
        
        JPanel panel = new TemplatePanel();
        getContentPane().add("Center", panel);
        setLocationRelativeTo(main);
        pack();
    }
    
}
