/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jajitech.desktop.xdata.hibernate.util;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JPanel;
import javax.swing.JSeparator;

/**
 *
 * @author Jaji
 */
public class JTitle extends JPanel{ 
    public JTitle(Component comp){ 
        setLayout(new GridBagLayout()); 
        add(comp, 
                new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.CENTER 
                        , GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0)); 
        add(new JSeparator(), 
                new GridBagConstraints(1, 0, 1, 1, 1, 0, GridBagConstraints.WEST 
                        , GridBagConstraints.HORIZONTAL, new Insets(0, 3, 0, 0), 0, 0)); 
    } 
}