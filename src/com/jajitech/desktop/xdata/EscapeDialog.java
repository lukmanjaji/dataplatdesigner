/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jajitech.desktop.xdata;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;

/**
 *
 * @author lukman
 */


public class EscapeDialog extends JDialog
{
    public EscapeDialog()
    {
        this((JFrame)null, false);
    }
    
    public EscapeDialog(JFrame owner)
    {
        this(owner, false);
    }
    
    public EscapeDialog(JFrame owner, boolean modal)
    {
        this(owner, null, modal);
    }
    
    public EscapeDialog(JFrame owner, String title)
    {
        this(owner, title, false);
    }
    
    public EscapeDialog(JFrame owner, String title, boolean modal)
    {
        super(owner, title, modal);
    }
    
    public EscapeDialog(JDialog owner)
    {
        this(owner, false);
    }
    
    public EscapeDialog(JDialog owner, boolean modal)
    {
        this(owner, null, modal);
    }
    
    public EscapeDialog(JDialog owner, String title)
    {
        this(owner, title, false);
    }
    
    public EscapeDialog(JDialog owner, String title, boolean modal)
    {
        super(owner, title, modal);
    }
    
    protected JRootPane createRootPane()
    {
        ActionListener actionListener = new ActionListener()
        {
            public void actionPerformed(ActionEvent actionEvent)
            {
                setVisible(false);
            }
        };
        JRootPane rootPane = new JRootPane(); 
        KeyStroke stroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
        rootPane.registerKeyboardAction(actionListener, stroke, JComponent.WHEN_IN_FOCUSED_WINDOW); return rootPane;
    }
}