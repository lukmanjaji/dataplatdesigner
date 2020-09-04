/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jajitech.desktop.xdata.saved;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Usman
 */
public class RiderEditor extends JDialog {
    
    public RiderEditor(final JFrame parent, PropertiesPanel props)
    {
        super(parent);
        setTitle("Rider Editor");
        JPanel panel = new RiderPanel(parent, this, props);
        getContentPane().add("Center",panel);
        pack();
        setLocationRelativeTo(parent);
        addWindowListener(new WindowAdapter(){public void windowClosing(WindowEvent v){parent.setEnabled(true);dispose();}});
    }
    
}
