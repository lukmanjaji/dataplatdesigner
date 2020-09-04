/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jajitech.desktop.xdata.ui;

import com.jajitech.desktop.xdata.saved.PropertiesPanel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import net.atlanticbb.tantlinger.shef.HTMLEditorPane;

/**
 *
 * @author Javalove
 */
public class RTF extends JDialog {
    
    JFrame parent;
    String result = "";
    
    public RTF(JFrame parent, String caption, PropertiesPanel p)
    {
        this.parent = parent;
        setTitle("Rich Text Editor");
        final HTMLEditorPane editor = new HTMLEditorPane();
        editor.setText(caption);
        getContentPane().add("Center", editor);
        addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent v)
            {
                result = editor.getText();
                p.ctext = "<html><body>"+result+"</body></html>";
                p.reWriteElement();
            }
        });
        
        setSize(700,500);
        setLocationRelativeTo(parent);
        setVisible(true);
       
    }
    
    public String getCaption()
    {
        return result;
    }
    
}
