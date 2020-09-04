/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jajitech.desktop.xdata.ui;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jajitech.desktop.xdata.constants.Constants;
import com.jajitech.desktop.xdata.elements.FormElements;
import com.jajitech.desktop.xdata.saved.SavedProject;
import java.awt.Color;
import java.awt.Component;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Vector;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;

/**
 *
 * @author Jaji
 */
public class UIElementsReader {
    
    String elementID = null;
    public static File file = null;
    UIBuilder ui = new UIBuilder();
    Constants cons = new Constants();
    Vector elementsList;
            
    public UIElementsReader(String name, String section, Vector elementsList)
    {
        file = new File("projects/"+name+"/sections/"+section+"/sectionData.jaj");
        this.elementsList = elementsList;
    }
    
    public void loadCustomSection(String path)
    {
        file = new File(path);
    }
    
    public void readProperties()
    {
        try
        {
            Gson gson = new Gson();
            Type collectionType = new TypeToken<List<FormElements>>(){}.getType();
            List<FormElements> details = gson.fromJson(readSectionData(), collectionType);
            for (FormElements detail : details)
            {

            }
        }
        catch(Exception e){}
    }
    
    
    public void load(JList p)
    {
        p.setListData(new Vector());
        
        System.out.println(file.toString());
        Vector elements = new Vector();
        try
        {
            Gson gson = new Gson();
            Type collectionType = new TypeToken<List<FormElements>>(){}.getType();
            List<FormElements> details = gson.fromJson(readSectionData(), collectionType);
            if(details != null)
            {
            for (FormElements detail : details)
            {
                    String type = detail.getType();
                    String caption = detail.getCaption();
                    String items = detail.getItems();
                    String required = detail.isRequired();
                    String rider = detail.getRider();
                    if(type.startsWith("rtf"))
                    {
                        JPanel l = ui.getRTFLabelPanel();
                        l.setName(type+"##"+caption+"##"+items+"##"+required+"##"+rider);
                        if(rider.length() > 2)
                        {
                            Component c[] = l.getComponents();
                            JPanel a = (JPanel) c[0];
                            JLabel ll = new JLabel("");
                            ll.setIcon(new ImageIcon(getClass().getResource("images/switch.png")));a.add(ll);
                        }
                        Component[] c = l.getComponents();
                        Color color = Color.decode("#C4D9EA");
                        JButton label = new JButton("<html><b>RIch Text Holder<br></b> </html>");
                        label.setBackground(color);
                        SavedProject.Drawer d = new SavedProject.Drawer(l);
                        l.addMouseListener(d);
                        l.addMouseMotionListener(d);
                        elements.add(l);
                        elementsList.add(l);
                        
                    }
                    if(type.startsWith("textfield"))
                    {
                        JPanel l = ui.getTextFieldPanel();
                        if(rider.length() > 2)
                        {
                            Component c[] = l.getComponents();
                            JPanel a = (JPanel) c[0];
                            JLabel ll = new JLabel("");
                            ll.setIcon(new ImageIcon(getClass().getResource("images/switch.png")));a.add(ll);
                        }
                        l.setName(type+"##"+caption+"##"+items+"##"+required+"##"+rider);
                        Component[] c = l.getComponents();
                        JMultiLineLabel jl = (JMultiLineLabel)c[1];
                        jl.setText(caption);
                        SavedProject.Drawer d = new SavedProject.Drawer(l);
                        l.addMouseListener(d);
                        l.addMouseMotionListener(d);
                        elements.add(l);
                        elementsList.add(l);
                        
                    }
                    if(type.startsWith("textarea"))
                    {
                        JPanel l = ui.getTextAreaPanel();
                        if(rider.length() > 2)
                        {
                            Component c[] = l.getComponents();
                            JPanel a = (JPanel) c[0]; 
                            JLabel ll = new JLabel("");
                            ll.setIcon(new ImageIcon(getClass().getResource("images/switch.png")));a.add(ll);
                        }
                        l.setName(type+"##"+caption+"##"+items+"##"+required+"##"+rider);
                        Component[] c = l.getComponents();
                        JMultiLineLabel jl = (JMultiLineLabel)c[1];
                        jl.setText(caption);
                        SavedProject.Drawer d = new SavedProject.Drawer(l);
                        l.addMouseListener(d);
                        l.addMouseMotionListener(d);
                        elements.add(l);
                        elementsList.add(l);
                        
                        
                    }
                    if(type.startsWith("time"))
                    {
                        JPanel l = ui.getTimePanel();
                        if(rider.length() > 2)
                        {
                            Component c[] = l.getComponents();
                            JPanel a = (JPanel) c[0];
                            JLabel ll = new JLabel("");
                            ll.setIcon(new ImageIcon(getClass().getResource("images/switch.png")));a.add(ll);
                        }
                        l.setName(type+"##"+caption+"##"+items+"##"+required+"##"+rider);
                        Component[] c = l.getComponents();
                        JMultiLineLabel jl = (JMultiLineLabel)c[1];
                        jl.setText(caption);
                        SavedProject.Drawer d = new SavedProject.Drawer(l);
                        l.addMouseListener(d);
                        l.addMouseMotionListener(d);
                        elements.add(l);
                        elementsList.add(l);
                        
                        
                    }
                    if(type.startsWith("date"))
                    {
                        JPanel l = ui.getDatePanel();
                        if(rider.length() > 2)
                        {
                            Component c[] = l.getComponents();
                            JPanel a = (JPanel) c[0];
                            JLabel ll = new JLabel("");
                            ll.setIcon(new ImageIcon(getClass().getResource("images/switch.png")));a.add(ll);
                        }
                        l.setName(type+"##"+caption+"##"+items+"##"+required+"##"+rider);
                        Component[] c = l.getComponents();
                        JMultiLineLabel jl = (JMultiLineLabel)c[1];
                        jl.setText(caption);
                        SavedProject.Drawer d = new SavedProject.Drawer(l);
                        l.addMouseListener(d);
                        l.addMouseMotionListener(d);
                        elements.add(l);
                        elementsList.add(l);
                        
                        
                    }
                     if(type.startsWith("image"))
                    {
                        JPanel l = ui.getImagePanel();
                        if(rider.length() > 2)
                        {
                            Component c[] = l.getComponents();
                            JPanel a = (JPanel) c[0];
                            JLabel ll = new JLabel("");
                            ll.setIcon(new ImageIcon(getClass().getResource("images/switch.png")));a.add(ll);
                        }
                        l.setName(type+"##"+caption+"##"+items+"##"+required+"##"+rider);
                        Component[] c = l.getComponents();
                        JMultiLineLabel jl = (JMultiLineLabel)c[1];
                        jl.setText(caption);
                        SavedProject.Drawer d = new SavedProject.Drawer(l);
                        l.addMouseListener(d);
                        l.addMouseMotionListener(d);
                        elements.add(l);
                        elementsList.add(l);
                        
                        
                    }
                    if(type.startsWith("barcode"))
                    {
                        JPanel l = ui.getBarCodePanel();
                        if(rider.length() > 2)
                        {
                            Component c[] = l.getComponents();
                            JPanel a = (JPanel) c[0];
                            JLabel ll = new JLabel("");
                            ll.setIcon(new ImageIcon(getClass().getResource("images/switch.png")));a.add(ll);
                        }
                        l.setName(type+"##"+caption+"##"+items+"##"+required+"##"+rider);
                        Component[] c = l.getComponents();
                        JLabel jl = (JLabel)c[1];
                        jl.setText(caption);
                        SavedProject.Drawer d = new SavedProject.Drawer(l);
                        l.addMouseListener(d);
                        l.addMouseMotionListener(d);
                        elements.add(l);
                        elementsList.add(l);
                        
                        
                    }
                    if(type.startsWith("audio"))
                    {
                        JPanel l = ui.getAudioPanel();
                        if(rider.length() > 2)
                        {
                            Component c[] = l.getComponents();
                            JPanel a = (JPanel) c[0];
                            JLabel ll = new JLabel("");
                            ll.setIcon(new ImageIcon(getClass().getResource("images/switch.png")));a.add(ll);
                        }
                        l.setName(type+"##"+caption+"##"+items+"##"+required+"##"+rider);
                        Component[] c = l.getComponents();
                        JLabel jl = (JLabel)c[1];
                        jl.setText(caption);
                        SavedProject.Drawer d = new SavedProject.Drawer(l);
                        l.addMouseListener(d);
                        l.addMouseMotionListener(d);
                        elements.add(l);
                        elementsList.add(l);
                        
                        
                    }
                    if(type.startsWith("video"))
                    {
                        JPanel l = ui.getVideoPanel();
                        if(rider.length() > 2)
                        {
                            Component c[] = l.getComponents();
                            JPanel a = (JPanel) c[0];
                            JLabel ll = new JLabel("");
                            ll.setIcon(new ImageIcon(getClass().getResource("images/switch.png")));a.add(ll);
                        }
                        l.setName(type+"##"+caption+"##"+items+"##"+required+"##"+rider);
                        Component[] c = l.getComponents();
                        JLabel jl = (JLabel)c[1];
                        jl.setText(caption);
                        SavedProject.Drawer d = new SavedProject.Drawer(l);
                        l.addMouseListener(d);
                        l.addMouseMotionListener(d);
                        elements.add(l);
                        elementsList.add(l);
                        
                        
                    }
                     if(type.startsWith("location"))
                    {
                        JPanel l = ui.getLocationPanel();
                        if(rider.length() > 2)
                        {
                            Component c[] = l.getComponents();
                            JPanel a = (JPanel) c[0];
                            JLabel ll = new JLabel("");
                            ll.setIcon(new ImageIcon(getClass().getResource("images/switch.png")));a.add(ll);
                        }
                        l.setName(type+"##"+caption+"##"+items+"##"+required+"##"+rider);
                        Component[] c = l.getComponents();
                        JMultiLineLabel jl = (JMultiLineLabel)c[1];
                        jl.setText(caption);
                        SavedProject.Drawer d = new SavedProject.Drawer(l);
                        l.addMouseListener(d);
                        l.addMouseMotionListener(d);
                        elements.add(l);
                        elementsList.add(l);
                        
                        
                    }
                      if(type.startsWith("spinner"))
                    {
                        JPanel l = ui.getSpinnerPanel();
                        if(rider.length() > 2)
                        {
                            Component c[] = l.getComponents();
                            JPanel a = (JPanel) c[0];
                            JLabel ll = new JLabel("");
                            ll.setIcon(new ImageIcon(getClass().getResource("images/switch.png")));a.add(ll);
                        }
                        l.setName(type+"##"+caption+"##"+items+"##"+required+"##"+rider);
                        Component[] c = l.getComponents();
                        JMultiLineLabel jl = (JMultiLineLabel)c[1];
                        jl.setText(caption);
                        SavedProject.Drawer d = new SavedProject.Drawer(l);
                        l.addMouseListener(d);
                        l.addMouseMotionListener(d);
                        elements.add(l);
                        elementsList.add(l);
                        
                        
                    }
                      
                      if(type.startsWith("onoff"))
                      {
                        JPanel l = ui.getONPanel();
                        if(rider.length() > 2)
                        {
                            Component c[] = l.getComponents();
                            JPanel a = (JPanel) c[0];
                            JLabel ll = new JLabel("");
                            ll.setIcon(new ImageIcon(getClass().getResource("images/switch.png")));a.add(ll);
                        }
                        l.setName(type+"##"+caption+"##"+items+"##"+required+"##"+rider);
                        Component[] c = l.getComponents();
                        JMultiLineLabel jl = (JMultiLineLabel)c[1];
                        jl.setText(caption);
                        SavedProject.Drawer d = new SavedProject.Drawer(l);
                        l.addMouseListener(d);
                        l.addMouseMotionListener(d);
                        elements.add(l);
                        elementsList.add(l);
                        
                        
                    }
                    
                    if(type.startsWith("drop"))
                    {
                        JPanel l = ui.getDropDownPanel();
                        if(rider.length() > 2)
                        {
                            Component c[] = l.getComponents();
                            JPanel a = (JPanel) c[0];
                            JLabel ll = new JLabel("");
                            ll.setIcon(new ImageIcon(getClass().getResource("images/switch.png")));a.add(ll);
                        }
                        l.setName(type+"##"+caption+"##"+items+"##"+required+"##"+rider);
                        Component[] c = l.getComponents();
                        JMultiLineLabel jl = (JMultiLineLabel)c[1];
                        jl.setText(caption);
                        String it[] = items.split("@@");
                        JComboBox b = (JComboBox)c[2];
                                    b.removeAllItems();
                                    for(int x=0;x<it.length;x++)
                                    {
                                        b.addItem(it[x]);
                                    }
                        SavedProject.Drawer d = new SavedProject.Drawer(l);
                        l.addMouseListener(d);
                        l.addMouseMotionListener(d);
                        elements.add(l);
                        elementsList.add(l);
                    }
                    if(type.startsWith("checkbox"))
                    {
                        JPanel l = ui.getCheckBoxPanel();
                        if(rider.length() > 2)
                        {
                            Component c[] = l.getComponents();
                            JPanel a = (JPanel) c[0];
                            JLabel ll = new JLabel("");
                            ll.setIcon(new ImageIcon(getClass().getResource("images/switch.png")));a.add(ll);
                        }
                        l.setName(type+"##"+caption+"##"+items+"##"+required+"##"+rider);
                        Component[] c = l.getComponents();
                        JMultiLineLabel jl = (JMultiLineLabel)c[1];
                        jl.setText(caption);
                        String it[] = items.split("@@");
                        JPanel b = (JPanel)c[2];
                        Component t[] = b.getComponents();
                        JPanel g = (JPanel) t[0];
                                    g.removeAll();
                                    g.repaint();
                                    g.revalidate();
                        for (String it1 : it) {
                            g.add(new JCheckBox(it1));
                        }
                        //c[1].setMaximumSize(new Dimension(Integer.MAX_VALUE, c[1].getMinimumSize().height));
                        b.repaint();
                        b.revalidate();b.updateUI();
                        SavedProject.Drawer d = new SavedProject.Drawer(l);
                        l.addMouseListener(d);
                        l.addMouseMotionListener(d);
                        elements.add(l);
                        elementsList.add(l);
                        
                        
                    }
                    if(type.startsWith("radio"))
                    {
                        JPanel l = ui.getRadioPanel();
                        if(rider.length() > 2)
                        {
                            Component c[] = l.getComponents();
                            JPanel a = (JPanel) c[0];
                            JLabel ll = new JLabel("");
                            ll.setIcon(new ImageIcon(getClass().getResource("images/switch.png")));a.add(ll);
                        }
                        l.setName(type+"##"+caption+"##"+items+"##"+required+"##"+rider);
                        Component[] c = l.getComponents();
                        JMultiLineLabel jl = (JMultiLineLabel)c[1];
                        jl.setText(caption);
                        String it[] = items.split("@@");
                        JPanel b = (JPanel)c[2];
                        Component t[] = b.getComponents();
                        JPanel g = (JPanel) t[0];
                                    g.removeAll();
                                    g.repaint();
                                    g.revalidate();
                                    ButtonGroup bg = new ButtonGroup();
                        for (String it1 : it) {
                            JRadioButton bb = new javax.swing.JRadioButton(it1);
                                        g.add(bb);
                                        bg.add(bb);
                        }
                        //c[1].setMaximumSize(new Dimension(Integer.MAX_VALUE, c[1].getMinimumSize().height));

                        SavedProject.Drawer d = new SavedProject.Drawer(l);
                        l.addMouseListener(d);
                        l.addMouseMotionListener(d);
                        elements.add(l);
                        elementsList.add(l);
                        
                        
                    }
                    
                    
                    
                    
                    if(type.startsWith("matrix"))
                    {
                        JPanel l = ui.getMatrixPanel();
                        if(rider.length() > 2)
                        {
                            Component c[] = l.getComponents();
                            JPanel a = (JPanel) c[0];
                            JLabel ll = new JLabel("");
                            ll.setIcon(new ImageIcon(getClass().getResource("images/switch.png")));a.add(ll);
                        }
                        l.setName(type+"##"+caption+"##"+items+"##"+required+"##"+rider);
                        Component[] c = l.getComponents();
                        JMultiLineLabel jl = (JMultiLineLabel)c[1];
                        jl.setText(caption);
                        JScrollPane f =  (JScrollPane) c[2];
                        JViewport fv = f.getViewport();
                        JTable table = (JTable) fv.getView();
                        boolean isRadio = false;
                        if(required.contains("Radio"))
                        {
                            isRadio = true;
                        }
                        ui.populateMatrix(table, items, isRadio);
                        table.setPreferredScrollableViewportSize(table.getPreferredSize());
                        SavedProject.Drawer d = new SavedProject.Drawer(l);
                        l.addMouseListener(d);
                        l.addMouseMotionListener(d);
                        elements.add(l);
                        elementsList.add(l);
                    }
                    
                   
            }
            p.setListData(elements);
            
            System.out.println("i loaded");
            }
        }catch(Exception e){e.printStackTrace();}
    }
    
    
    public String readSectionData() throws IOException
    {
	FileInputStream fis = new FileInputStream(file);
	BufferedReader br = new BufferedReader(new InputStreamReader(fis));
	String line = null;
        String output = "";
	while ((line = br.readLine()) != null) {
		output = output+line;
	}
        System.out.println(output);
	br.close();
        return output;
    }
    
}
