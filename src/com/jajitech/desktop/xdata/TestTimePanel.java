/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jajitech.desktop.xdata;

/**
 *
 * @author lukman
 */
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import com.lavantech.gui.comp.TimePanel;

public class TestTimePanel extends JPanel implements ActionListener
{
    TimePanel timePanel;
    public TestTimePanel()
    {
        super(new BorderLayout());
        timePanel = new TimePanel(new GregorianCalendar());
        timePanel.addActionListener(this);

        //Lets try a custom needle 
        Polygon polygon = new Polygon();
        polygon.addPoint(5,5);
        polygon.addPoint(-5,5);
        polygon.addPoint(-2,0);
        polygon.addPoint(-5,-50);
        polygon.addPoint(0,-100);
        polygon.addPoint(5,-50);
        polygon.addPoint(2,0);
        polygon.addPoint(5,5);
        timePanel.getClockPanel().setHourNeedleShape(polygon);
        timePanel.getClockPanel().setMinNeedleShape(polygon);
        timePanel.getClockPanel().setHourNeedleHeightRatio(0.5); //shorter than default
        timePanel.getClockPanel().setHourNeedleWidthRatio(0.1); // thicker than default 
        timePanel.getClockPanel().setMinNeedleHeightRatio(0.7); //shorter than default
        timePanel.getClockPanel().setMinNeedleWidthRatio(0.1); // thicker than default 
        timePanel.getClockPanel().setHourNeedleColor(Color.black);
        timePanel.getClockPanel().setMinNeedleColor(Color.black);

        add(timePanel, BorderLayout.CENTER);
        //add(new JLabel("Try dragging the needle"), BorderLayout.SOUTH);
    }

    public void actionPerformed(ActionEvent evt)
    {
        System.out.println(timePanel.getCalendar().getTime());
    }
    
 
}
