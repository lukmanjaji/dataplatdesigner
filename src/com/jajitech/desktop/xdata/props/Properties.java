/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jajitech.desktop.xdata.props;

import com.jajitech.desktop.xdata.constants.Constants;
import com.jajitech.desktop.xdata.saved.SavedProject;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

/**
 *
 * @author Jaji
 */
public class Properties extends JPanel {
    
    JPanel main;
    boolean isSaved;
    String name, section;
    //UIElementsReader reader = new UIElementsReader();
    public Properties(JPanel main, String name, String section)
    {
        super();
        this.main = main;
        this.name = name;
        this.section = section;
    }
    
    
    public JPanel getLabelProperties(final String id, final String name, final String section)
    {
            //construct components
            Constants.isSaved = false;
            JLabel jcomp1 = new JLabel ("Caption");
            final JTextArea jcomp2 = new JTextArea ();
            jcomp2.setLineWrap(true);
            jcomp2.setWrapStyleWord(true);
            JScrollPane pp = new JScrollPane(jcomp2);
            JButton jcomp3 = new JButton ("Update");
            JPanel jcomp4 = new JPanel();
            jcomp4.setBorder(new TitledBorder("Label Properties"));
            jcomp3.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent v)
                {
                    if(jcomp2.getText().length()==0)
                    {
                        new Constants().alert(null, "warn", "Enter caption for label");
                        return;
                    }
                    writeToSaved();
                    try
                    {
                         Component[] components = main.getComponents();
                        for (Component component1 : components)
                        {
                            if (component1.getClass().getName().equals("javax.swing.JPanel"))
                            {
                                if(component1.getName().equals(id))
                                {
                                    JPanel jp = (JPanel) component1;
                                    Component[] c = jp.getComponents();
                                    JLabel jl = (JLabel)c[0];
                                    jl.setText(jcomp2.getText());
                                    String pname = component1.getName();
                                    changeElementName(id, jcomp2.getText(), jp);
                                }
                            }
                        }
                    }
                    catch(Exception e){}
                }
            });

            setPreferredSize (new Dimension (204, 456));
            setLayout (null);
            add (jcomp1);
            add (pp);
            add (jcomp3);
            add (jcomp4);
            String caption[] = id.split("##");
            jcomp2.setText(caption[1]);

            //set component bounds (only needed by Absolute Positioning)
            jcomp1.setBounds (65, 50, 55, 15);
            pp.setBounds (20, 75, 160, 65);
            jcomp3.setBounds (54, 140, 100, 30);
            jcomp4.setBounds (10, 10, 185, 440);
            return this;
    }
    
    
    public JPanel getDateProperties(final String id, final String name, final String section)
    {
        JLabel jcomp1 = new JLabel ("Caption");
        final JTextField jcomp2 = new JTextField (5);
        final JCheckBox jcomp3 = new JCheckBox ("Required");
        JButton jcomp4 = new JButton ("Update");
        JPanel jcomp5 = new JPanel ();
        jcomp5.setBorder(new TitledBorder("Date Picker Properties"));
        String caption[] = id.split("##");
        jcomp2.setText(caption[1]);
        if(caption[3].trim().equals("true"))
        {
            jcomp3.setSelected(true);
        }
         jcomp4.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent v)
                {
                    if(jcomp2.getText().length()==0)
                    {
                        new Constants().alert(null, "warn", "Enter caption for Date Picker");
                        return;
                    }
                    writeToSaved();
                    try
                    {
                         Component[] components = main.getComponents();
                        for (Component component1 : components)
                        {
                            if (component1.getClass().getName().equals("javax.swing.JPanel"))
                            {
                                if(component1.getName().equals(id))
                                {
                                    JPanel jp = (JPanel) component1;
                                    Component[] c = jp.getComponents();
                                    JLabel jl = (JLabel)c[0];
                                    jl.setText(jcomp2.getText());
                                    changeElementName(component1.getName(),jcomp2.getText()+"##-##"+jcomp3.isSelected(),jp);
                                }
                            }
                        }
                        
                        
                    }
                    catch(Exception e){}
                }
            });
        //adjust size and set layout
        setPreferredSize (new Dimension (204, 456));
        setLayout (null);

        //add components
        add (jcomp1);
        add (jcomp2);
        add (jcomp3);
        add (jcomp4);
        add (jcomp5);

        //set component bounds (only needed by Absolute Positioning)
        jcomp1.setBounds (65, 55, 55, 15);
        jcomp2.setBounds (20, 73, 160, 25);
        jcomp3.setBounds (55, 110, 100, 25);
        jcomp4.setBounds (45, 145, 100, 30);
        jcomp5.setBounds (10, 10, 185, 440);
        return this;
    }
    
    
    
    public JPanel getTimeProperties(final String id, final String name, final String section)
    {
        JLabel jcomp1 = new JLabel ("Caption");
        final JTextField jcomp2 = new JTextField (5);
        final JCheckBox jcomp3 = new JCheckBox ("Required");
        JButton jcomp4 = new JButton ("Update");
        JPanel jcomp5 = new JPanel ();
        jcomp5.setBorder(new TitledBorder("Time Picker Properties"));
        String caption[] = id.split("##");
        jcomp2.setText(caption[1]);
        if(caption[3].trim().equals("true"))
        {
            jcomp3.setSelected(true);
        }
         jcomp4.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent v)
                {
                    if(jcomp2.getText().length()==0)
                    {
                        new Constants().alert(null, "warn", "Enter caption for Time Picker");
                        return;
                    }
                    writeToSaved();
                    try
                    {
                         Component[] components = main.getComponents();
                        for (Component component1 : components)
                        {
                            if (component1.getClass().getName().equals("javax.swing.JPanel"))
                            {
                                if(component1.getName().equals(id))
                                {
                                    JPanel jp = (JPanel) component1;
                                    Component[] c = jp.getComponents();
                                    JLabel jl = (JLabel)c[0];
                                    jl.setText(jcomp2.getText());
                                    changeElementName(component1.getName(),jcomp2.getText()+"##-##"+jcomp3.isSelected(),jp);
                                }
                            }
                        }
                        
                        
                    }
                    catch(Exception e){}
                }
            });
        //adjust size and set layout
        setPreferredSize (new Dimension (204, 456));
        setLayout (null);

        //add components
        add (jcomp1);
        add (jcomp2);
        add (jcomp3);
        add (jcomp4);
        add (jcomp5);

        //set component bounds (only needed by Absolute Positioning)
        jcomp1.setBounds (65, 55, 55, 15);
        jcomp2.setBounds (20, 73, 160, 25);
        jcomp3.setBounds (55, 110, 100, 25);
        jcomp4.setBounds (45, 145, 100, 30);
        jcomp5.setBounds (10, 10, 185, 440);
        return this;
    }
    
    public JPanel getImageProperties(final String id, final String name, final String section)
    {
        JLabel jcomp1 = new JLabel ("Caption");
        final JTextField jcomp2 = new JTextField (5);
        final JCheckBox jcomp3 = new JCheckBox ("Required");
        JButton jcomp4 = new JButton ("Update");
        JPanel jcomp5 = new JPanel ();
        jcomp5.setBorder(new TitledBorder("Image Properties"));
        String caption[] = id.split("##");
        jcomp2.setText(caption[1]);
        if(caption[3].trim().equals("true"))
        {
            jcomp3.setSelected(true);
        }
         jcomp4.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent v)
                {
                    if(jcomp2.getText().length()==0)
                    {
                        new Constants().alert(null, "warn", "Enter caption for Image");
                        return;
                    }
                    writeToSaved();
                    try
                    {
                         Component[] components = main.getComponents();
                        for (Component component1 : components)
                        {
                            if (component1.getClass().getName().equals("javax.swing.JPanel"))
                            {
                                if(component1.getName().equals(id))
                                {
                                    JPanel jp = (JPanel) component1;
                                    Component[] c = jp.getComponents();
                                    JLabel jl = (JLabel)c[0];
                                    jl.setText(jcomp2.getText());
                                    changeElementName(component1.getName(),jcomp2.getText()+"##-##"+jcomp3.isSelected(),jp);
                                }
                            }
                        }
                        
                        
                    }
                    catch(Exception e){}
                }
            });
        //adjust size and set layout
        setPreferredSize (new Dimension (204, 456));
        setLayout (null);

        //add components
        add (jcomp1);
        add (jcomp2);
        add (jcomp3);
        add (jcomp4);
        add (jcomp5);

        //set component bounds (only needed by Absolute Positioning)
        jcomp1.setBounds (65, 55, 55, 15);
        jcomp2.setBounds (20, 73, 160, 25);
        jcomp3.setBounds (55, 110, 100, 25);
        jcomp4.setBounds (45, 145, 100, 30);
        jcomp5.setBounds (10, 10, 185, 440);
        return this;
    }
    
    public JPanel getBarCodeProperties(final String id, final String name, final String section)
    {
        JLabel jcomp1 = new JLabel ("Caption");
        final JTextField jcomp2 = new JTextField (5);
        final JCheckBox jcomp3 = new JCheckBox ("Required");
        JButton jcomp4 = new JButton ("Update");
        JPanel jcomp5 = new JPanel ();
        jcomp5.setBorder(new TitledBorder("Barcode Properties"));
        String caption[] = id.split("##");
        jcomp2.setText(caption[1]);
        if(caption[3].trim().equals("true"))
        {
            jcomp3.setSelected(true);
        }
         jcomp4.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent v)
                {
                    if(jcomp2.getText().length()==0)
                    {
                        new Constants().alert(null, "warn", "Enter caption for Barcode");
                        return;
                    }
                    writeToSaved();
                    try
                    {
                         Component[] components = main.getComponents();
                        for (Component component1 : components)
                        {
                            if (component1.getClass().getName().equals("javax.swing.JPanel"))
                            {
                                if(component1.getName().equals(id))
                                {
                                    JPanel jp = (JPanel) component1;
                                    Component[] c = jp.getComponents();
                                    JLabel jl = (JLabel)c[0];
                                    jl.setText(jcomp2.getText());
                                    changeElementName(component1.getName(),jcomp2.getText()+"##-##"+jcomp3.isSelected(),jp);
                                }
                            }
                        }
                        
                        
                    }
                    catch(Exception e){}
                }
            });
        //adjust size and set layout
        setPreferredSize (new Dimension (204, 456));
        setLayout (null);

        //add components
        add (jcomp1);
        add (jcomp2);
        add (jcomp3);
        add (jcomp4);
        add (jcomp5);

        //set component bounds (only needed by Absolute Positioning)
        jcomp1.setBounds (65, 55, 55, 15);
        jcomp2.setBounds (20, 73, 160, 25);
        jcomp3.setBounds (55, 110, 100, 25);
        jcomp4.setBounds (45, 145, 100, 30);
        jcomp5.setBounds (10, 10, 185, 440);
        return this;
    }
    
    
    public JPanel getAudioProperties(final String id, final String name, final String section)
    {
        JLabel jcomp1 = new JLabel ("Caption");
        final JTextField jcomp2 = new JTextField (5);
        final JCheckBox jcomp3 = new JCheckBox ("Required");
        JButton jcomp4 = new JButton ("Update");
        JPanel jcomp5 = new JPanel ();
        jcomp5.setBorder(new TitledBorder("Audio Properties"));
        String caption[] = id.split("##");
        jcomp2.setText(caption[1]);
        if(caption[3].trim().equals("true"))
        {
            jcomp3.setSelected(true);
        }
         jcomp4.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent v)
                {
                    if(jcomp2.getText().length()==0)
                    {
                        new Constants().alert(null, "warn", "Enter caption for Audio");
                        return;
                    }
                    writeToSaved();
                    try
                    {
                         Component[] components = main.getComponents();
                        for (Component component1 : components)
                        {
                            if (component1.getClass().getName().equals("javax.swing.JPanel"))
                            {
                                if(component1.getName().equals(id))
                                {
                                    JPanel jp = (JPanel) component1;
                                    Component[] c = jp.getComponents();
                                    JLabel jl = (JLabel)c[0];
                                    jl.setText(jcomp2.getText());
                                    changeElementName(component1.getName(),jcomp2.getText()+"##-##"+jcomp3.isSelected(),jp);
                                }
                            }
                        }
                        
                        
                    }
                    catch(Exception e){}
                }
            });
        //adjust size and set layout
        setPreferredSize (new Dimension (204, 456));
        setLayout (null);

        //add components
        add (jcomp1);
        add (jcomp2);
        add (jcomp3);
        add (jcomp4);
        add (jcomp5);

        //set component bounds (only needed by Absolute Positioning)
        jcomp1.setBounds (65, 55, 55, 15);
        jcomp2.setBounds (20, 73, 160, 25);
        jcomp3.setBounds (55, 110, 100, 25);
        jcomp4.setBounds (45, 145, 100, 30);
        jcomp5.setBounds (10, 10, 185, 440);
        return this;
    }
    
    
    public JPanel getVideoProperties(final String id, final String name, final String section)
    {
        JLabel jcomp1 = new JLabel ("Caption");
        final JTextField jcomp2 = new JTextField (5);
        final JCheckBox jcomp3 = new JCheckBox ("Required");
        JButton jcomp4 = new JButton ("Update");
        JPanel jcomp5 = new JPanel ();
        jcomp5.setBorder(new TitledBorder("Video Properties"));
        String caption[] = id.split("##");
        jcomp2.setText(caption[1]);
        if(caption[3].trim().equals("true"))
        {
            jcomp3.setSelected(true);
        }
         jcomp4.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent v)
                {
                    if(jcomp2.getText().length()==0)
                    {
                        new Constants().alert(null, "warn", "Enter caption for Video");
                        return;
                    }
                    writeToSaved();
                    try
                    {
                         Component[] components = main.getComponents();
                        for (Component component1 : components)
                        {
                            if (component1.getClass().getName().equals("javax.swing.JPanel"))
                            {
                                if(component1.getName().equals(id))
                                {
                                    JPanel jp = (JPanel) component1;
                                    Component[] c = jp.getComponents();
                                    JLabel jl = (JLabel)c[0];
                                    jl.setText(jcomp2.getText());
                                    changeElementName(component1.getName(),jcomp2.getText()+"##-##"+jcomp3.isSelected(),jp);
                                }
                            }
                        }
                        
                        
                    }
                    catch(Exception e){}
                }
            });
        //adjust size and set layout
        setPreferredSize (new Dimension (204, 456));
        setLayout (null);

        //add components
        add (jcomp1);
        add (jcomp2);
        add (jcomp3);
        add (jcomp4);
        add (jcomp5);

        //set component bounds (only needed by Absolute Positioning)
        jcomp1.setBounds (65, 55, 55, 15);
        jcomp2.setBounds (20, 73, 160, 25);
        jcomp3.setBounds (55, 110, 100, 25);
        jcomp4.setBounds (45, 145, 100, 30);
        jcomp5.setBounds (10, 10, 185, 440);
        return this;
    }
    
    
    
    public JPanel getLocationProperties(final String id, final String name, final String section)
    {
        JLabel jcomp1 = new JLabel ("Caption");
        final JTextField jcomp2 = new JTextField (5);
        final JCheckBox jcomp3 = new JCheckBox ("Required");
        JButton jcomp4 = new JButton ("Update");
        JPanel jcomp5 = new JPanel ();
        jcomp5.setBorder(new TitledBorder("Location Properties"));
        String caption[] = id.split("##");
        jcomp2.setText(caption[1]);
        if(caption[3].trim().equals("true"))
        {
            jcomp3.setSelected(true);
        }
         jcomp4.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent v)
                {
                    if(jcomp2.getText().length()==0)
                    {
                        new Constants().alert(null, "warn", "Enter caption for Location");
                        return;
                    }
                    writeToSaved();
                    try
                    {
                         Component[] components = main.getComponents();
                        for (Component component1 : components)
                        {
                            if (component1.getClass().getName().equals("javax.swing.JPanel"))
                            {
                                if(component1.getName().equals(id))
                                {
                                    JPanel jp = (JPanel) component1;
                                    Component[] c = jp.getComponents();
                                    JLabel jl = (JLabel)c[0];
                                    jl.setText(jcomp2.getText());
                                    changeElementName(component1.getName(),jcomp2.getText()+"##-##"+jcomp3.isSelected(),jp);
                                }
                            }
                        }
                        
                        
                    }
                    catch(Exception e){}
                }
            });
        //adjust size and set layout
        setPreferredSize (new Dimension (204, 456));
        setLayout (null);

        //add components
        add (jcomp1);
        add (jcomp2);
        add (jcomp3);
        add (jcomp4);
        add (jcomp5);

        //set component bounds (only needed by Absolute Positioning)
        jcomp1.setBounds (65, 55, 55, 15);
        jcomp2.setBounds (20, 73, 160, 25);
        jcomp3.setBounds (55, 110, 100, 25);
        jcomp4.setBounds (45, 145, 100, 30);
        jcomp5.setBounds (10, 10, 185, 440);
        return this;
    }
    
    public JPanel getONProperties(final String id, final String name, final String section)
    {
        JLabel jcomp1 = new JLabel ("Caption");
        final JTextField jcomp2 = new JTextField (5);
        final JLabel jcomp3 = new JLabel ("Caption for ON");
        final JTextField on = new JTextField();
        final JLabel off = new JLabel("Caption for OFF");
        final JTextField otext = new JTextField();
        JButton jcomp4 = new JButton ("Update");
        JPanel jcomp5 = new JPanel ();
        jcomp5.setBorder(new TitledBorder("ON/OFF Properties"));
        String caption[] = id.split("##");
        jcomp2.setText(caption[1]);
        String lb[] = caption[2].split("@@");
        on.setText(lb[0]);
        otext.setText(lb[1]);
         jcomp4.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent v)
                {
                    if(jcomp2.getText().length()==0)
                    {
                        new Constants().alert(null, "warn", "Enter caption for ON/OFF");
                        return;
                    }
                    writeToSaved();
                    try
                    {
                         Component[] components = main.getComponents();
                        for (Component component1 : components)
                        {
                            if (component1.getClass().getName().equals("javax.swing.JPanel"))
                            {
                                if(component1.getName().equals(id))
                                {
                                    JPanel jp = (JPanel) component1;
                                    Component[] c = jp.getComponents();
                                    JLabel jl = (JLabel)c[0];
                                    jl.setText(jcomp2.getText());
                                    changeElementName(component1.getName(),jcomp2.getText()+"##"+on.getText()+"@@"+otext.getText()+"##"+otext.getText(),jp);
                                    
                                }
                            }
                        }
                        
                        
                    }
                    catch(Exception e){}
                }
            });
        //adjust size and set layout
        setPreferredSize (new Dimension (204, 456));
        setLayout (null);

        //add components
        add (jcomp1);
        add (jcomp2);
        add (jcomp3);
        add (on);
        add (off);
        add (otext);
        add (jcomp4);
        add (jcomp5);

        //set component bounds (only needed by Absolute Positioning)
        jcomp1.setBounds (65, 55, 55, 15);
        jcomp2.setBounds (20, 73, 160, 25);
        jcomp3.setBounds (65, 110, 100, 25);
        on.setBounds (20, 130, 160, 25);
        
        off.setBounds (65, 165, 100, 25);
        otext.setBounds (20, 187, 160, 25);
        
        jcomp4.setBounds (45, 220, 100, 30);
        jcomp5.setBounds (10, 10, 185, 440);
        return this;
    }
    
    public JPanel getSpinnerProperties(final String id, final String name, final String section)
    {
        JLabel jcomp1 = new JLabel ("Caption");
        final JTextField jcomp2 = new JTextField (5);
        final JCheckBox jcomp3 = new JCheckBox ("Required");
        JButton jcomp4 = new JButton ("Update");
        JPanel jcomp5 = new JPanel ();
        jcomp5.setBorder(new TitledBorder("Numeric Spinner Properties"));
        String caption[] = id.split("##");
        jcomp2.setText(caption[1]);
        if(caption[3].trim().equals("true"))
        {
            jcomp3.setSelected(true);
        }
         jcomp4.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent v)
                {
                    if(jcomp2.getText().length()==0)
                    {
                        new Constants().alert(null, "warn", "Enter caption for Numeric Spinner");
                        return;
                    }
                    writeToSaved();
                    try
                    {
                         Component[] components = main.getComponents();
                        for (Component component1 : components)
                        {
                            if (component1.getClass().getName().equals("javax.swing.JPanel"))
                            {
                                if(component1.getName().equals(id))
                                {
                                    JPanel jp = (JPanel) component1;
                                    Component[] c = jp.getComponents();
                                    JLabel jl = (JLabel)c[0];
                                    jl.setText(jcomp2.getText());
                                    changeElementName(component1.getName(),jcomp2.getText()+"##-##"+jcomp3.isSelected(),jp);
                                }
                            }
                        }
                        
                        
                    }
                    catch(Exception e){}
                }
            });
        //adjust size and set layout
        setPreferredSize (new Dimension (204, 456));
        setLayout (null);

        //add components
        add (jcomp1);
        add (jcomp2);
        add (jcomp3);
        add (jcomp4);
        add (jcomp5);

        //set component bounds (only needed by Absolute Positioning)
        jcomp1.setBounds (65, 55, 55, 15);
        jcomp2.setBounds (20, 73, 160, 25);
        jcomp3.setBounds (55, 110, 100, 25);
        jcomp4.setBounds (45, 145, 100, 30);
        jcomp5.setBounds (10, 10, 185, 440);
        return this;
    }
    
    
    
    
    public JPanel getTextAreaProperties(final String id, final String name, final String section)
    {
        JLabel jcomp1 = new JLabel ("Caption");
        final JTextField jcomp2 = new JTextField (5);
        final JCheckBox jcomp3 = new JCheckBox ("Required");
        JButton jcomp4 = new JButton ("Update");
        JPanel jcomp5 = new JPanel ();
        jcomp5.setBorder(new TitledBorder("TextArea Properties"));
        String caption[] = id.split("##");
        jcomp2.setText(caption[1]);
        if(caption[3].trim().equals("true"))
        {
            jcomp3.setSelected(true);
        }
         jcomp4.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent v)
                {
                    if(jcomp2.getText().length()==0)
                    {
                        new Constants().alert(null, "warn", "Enter caption for TextArea");
                        return;
                    }
                    writeToSaved();
                    try
                    {
                         Component[] components = main.getComponents();
                        for (Component component1 : components)
                        {
                            if (component1.getClass().getName().equals("javax.swing.JPanel"))
                            {
                                if(component1.getName().equals(id))
                                {
                                    JPanel jp = (JPanel) component1;
                                    Component[] c = jp.getComponents();
                                    JLabel jl = (JLabel)c[0];
                                    jl.setText(jcomp2.getText());
                                    changeElementName(component1.getName(),jcomp2.getText()+"##-##"+jcomp3.isSelected(),jp);
                                }
                            }
                        }
                        
                        
                    }
                    catch(Exception e){}
                }
            });
        //adjust size and set layout
        setPreferredSize (new Dimension (204, 456));
        setLayout (null);

        //add components
        add (jcomp1);
        add (jcomp2);
        add (jcomp3);
        add (jcomp4);
        add (jcomp5);

        //set component bounds (only needed by Absolute Positioning)
        jcomp1.setBounds (65, 55, 55, 15);
        jcomp2.setBounds (20, 73, 160, 25);
        jcomp3.setBounds (55, 110, 100, 25);
        jcomp4.setBounds (45, 145, 100, 30);
        jcomp5.setBounds (10, 10, 185, 440);
        return this;
    }
    
    
    
    public JPanel getRadioProperties(final String id, final String name, final String section)
    {
        JLabel jcomp1 = new JLabel ("Caption");
        final JTextField jcomp2 = new JTextField (5);
        final JCheckBox jcomp3 = new JCheckBox ("Required");
        JButton jcomp4 = new JButton ("Update");
        JPanel jcomp5 = new JPanel ();
        jcomp5.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
        JLabel jcomp6 = new JLabel ("Radio Button Options");
        JPanel jcomp7 = new JPanel ();
        jcomp7.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
        JLabel jcomp8 = new JLabel ("Enter each option on a new line");
        final JTextArea jcomp9 = new JTextArea (5, 5);
        JScrollPane s = new JScrollPane(jcomp9);
        JPanel title = new JPanel ();
        title.setBorder(new TitledBorder("Radio Button Properties"));
        String caption[] = id.split("##");
        System.out.println("this is id "+id);
        jcomp2.setText(caption[1]);
        if(caption[3].trim().equals("true"))
        {
            jcomp3.setSelected(true);
        }
        jcomp9.setText(caption[2].trim().replace("#$", "\n"));
        jcomp4.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent v)
                {
                    if(jcomp2.getText().length()==0)
                    {
                        new Constants().alert(null, "warn", "Enter caption for Radio Button");
                        return;
                    }
                    String p[] = jcomp9.getText().split("\n");
                    if(p.length < 2)
                    {
                        new Constants().alert(null, "warn", "Enter at least two options");
                        return;
                    }
                    String items = jcomp9.getText().replace("\n", "#$");
                    String it[] = items.split("#$");
                    System.out.println("tis is it "+it.length);
                    writeToSaved();
                    try
                    {
                         Component[] components = main.getComponents();
                        for (Component component1 : components)
                        {
                            if (component1.getClass().getName().equals("javax.swing.JPanel"))
                            {
                                if(component1.getName().equals(id))
                                {
                                    JPanel jp = (JPanel) component1;
                                    Component[] c = jp.getComponents();
                                    JLabel jl = (JLabel)c[0];
                                    jl.setText(jcomp2.getText());
                                    JPanel b = (JPanel)c[1];
                                    b.removeAll();b.repaint();b.invalidate();
                                    ButtonGroup bg = new ButtonGroup();
                                    for(String y: it)
                                    {
                                        System.out.println(y);
                                        JRadioButton bb = new javax.swing.JRadioButton(y);
                                        b.add(bb);
                                        bg.add(bb);
                                    }
                                    b.repaint();
                                    b.invalidate();
                                    b.updateUI();
                                    changeElementName(component1.getName(),jcomp2.getText()+"##"+items+"##"+jcomp3.isSelected(),jp);
                                }
                            }
                        }
                        
                        
                    }
                    catch(Exception e){}
                }
            });

        //adjust size and set layout
        setPreferredSize (new Dimension (204, 456));
        setLayout (null);

        //add components
        add (jcomp1);
        add (jcomp2);
        add (jcomp3);
        add (jcomp4);
        add (jcomp5);
        add (jcomp6);
        add (jcomp7);
        add (jcomp8);
        add (s);
        add(title);

        //set component bounds (only needed by Absolute Positioning)
        jcomp1.setBounds (65, 55, 55, 15);
        jcomp2.setBounds (20, 73, 160, 25);
        jcomp3.setBounds (50, 110, 100, 25);
        jcomp4.setBounds (55, 385, 100, 30);
        jcomp5.setBounds (11, 135, 180, 2);
        jcomp6.setBounds (65, 145, 105, 20);
        jcomp7.setBounds (11, 170, 180, 2);
        jcomp8.setBounds (25, 180, 170, 20);
        s.setBounds (25, 202, 160, 175);
        title.setBounds (10, 10, 185, 440);
        return this;
    }

    
    
    public JPanel getCheckBoxProperties(final String id, final String name, final String section)
    {
        JLabel jcomp1 = new JLabel ("Caption");
        final JTextField jcomp2 = new JTextField (5);
        final JCheckBox jcomp3 = new JCheckBox ("Required");
        JButton jcomp4 = new JButton ("Update");
        JPanel jcomp5 = new JPanel ();
        jcomp5.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
        JLabel jcomp6 = new JLabel ("Checkbox Options");
        JPanel jcomp7 = new JPanel ();
        jcomp7.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
        JLabel jcomp8 = new JLabel ("Enter each option on a new line");
        final JTextArea jcomp9 = new JTextArea (5, 5);
        JScrollPane s = new JScrollPane(jcomp9);
        JPanel title = new JPanel ();
        title.setBorder(new TitledBorder("Checkbox Properties"));
        String caption[] = id.split("##");
        jcomp2.setText(caption[1]);
        if(caption[3].trim().equals("true"))
        {
            jcomp3.setSelected(true);
        }
        jcomp9.setText(caption[2].trim().replace("#$", "\n"));
        jcomp4.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent v)
                {
                    if(jcomp2.getText().length()==0)
                    {
                        new Constants().alert(null, "warn", "Enter caption for Checkbox");
                        return;
                    }
                    if(jcomp9.getText().length()==0)
                    {
                        new Constants().alert(null, "warn", "Enter at least one option item");
                        return;
                    }
                    String items = jcomp9.getText().replace("\n", "#$");
                    String it[] = jcomp9.getText().split("\n");
                    writeToSaved();
                    try
                    {
                         Component[] components = main.getComponents();
                        for (Component component1 : components)
                        {
                            if (component1.getClass().getName().equals("javax.swing.JPanel"))
                            {
                                if(component1.getName().equals(id))
                                {
                                    JPanel jp = (JPanel) component1;
                                    Component[] c = jp.getComponents();
                                    JLabel jl = (JLabel)c[0];
                                    jl.setText(jcomp2.getText());
                                    JPanel b = (JPanel)c[1];
                                    b.removeAll();b.repaint();b.invalidate();
                                    for(int x=0;x<it.length;x++)
                                    {
                                        b.add(new javax.swing.JCheckBox((it[x])));
                                    }
                                    b.repaint();
                                    b.invalidate();
                                    b.updateUI();
                                    changeElementName(component1.getName(),jcomp2.getText()+"##"+items+"##"+jcomp3.isSelected(),jp);
                                }
                            }
                        }
                        
                        
                    }
                    catch(Exception e){}
                }
            });

        //adjust size and set layout
        setPreferredSize (new Dimension (204, 456));
        setLayout (null);

        //add components
        add (jcomp1);
        add (jcomp2);
        add (jcomp3);
        add (jcomp4);
        add (jcomp5);
        add (jcomp6);
        add (jcomp7);
        add (jcomp8);
        add (s);
        add(title);

        //set component bounds (only needed by Absolute Positioning)
        jcomp1.setBounds (65, 55, 55, 15);
        jcomp2.setBounds (20, 73, 160, 25);
        jcomp3.setBounds (50, 110, 100, 25);
        jcomp4.setBounds (55, 385, 100, 30);
        jcomp5.setBounds (11, 135, 180, 2);
        jcomp6.setBounds (65, 145, 105, 20);
        jcomp7.setBounds (11, 170, 180, 2);
        jcomp8.setBounds (25, 180, 170, 20);
        s.setBounds (25, 202, 160, 175);
        title.setBounds (10, 10, 185, 440);
        return this;
    }
    
    
    
    
    
    
    public JPanel getDropDownProperties(final String id, final String name, final String section)
    {
        JLabel jcomp1 = new JLabel ("Caption");
        final JTextField jcomp2 = new JTextField (5);
        final JCheckBox jcomp3 = new JCheckBox ("Required");
        JButton jcomp4 = new JButton ("Update");
        JPanel jcomp5 = new JPanel ();
        jcomp5.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
        JLabel jcomp6 = new JLabel ("Dropdown Items");
        JPanel jcomp7 = new JPanel ();
        jcomp7.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
        JLabel jcomp8 = new JLabel ("Enter each item on a new line");
        final JTextArea jcomp9 = new JTextArea (5, 5);
        JScrollPane s = new JScrollPane(jcomp9);
        JPanel title = new JPanel ();
        title.setBorder(new TitledBorder("DropDown Properties"));
        String caption[] = id.split("##");
        jcomp2.setText(caption[1]);
        if(caption[3].trim().equals("true"))
        {
            jcomp3.setSelected(true);
        }
        jcomp9.setText(caption[2].trim().replace(",", "\n"));
        jcomp4.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent v)
                {
                    if(jcomp2.getText().length()==0)
                    {
                        new Constants().alert(null, "warn", "Enter caption for Dropdown");
                        return;
                    }
                    if(jcomp9.getText().length()==0)
                    {
                        new Constants().alert(null, "warn", "Enter at least one option item");
                        return;
                    }
                    String items = jcomp9.getText().replace("\n", ",");
                    String it[] = jcomp9.getText().split("\n");
                    writeToSaved();
                    try
                    {
                         Component[] components = main.getComponents();
                        for (Component component1 : components)
                        {
                            if (component1.getClass().getName().equals("javax.swing.JPanel"))
                            {
                                if(component1.getName().equals(id))
                                {
                                    JPanel jp = (JPanel) component1;
                                    Component[] c = jp.getComponents();
                                    JLabel jl = (JLabel)c[0];
                                    jl.setText(jcomp2.getText());
                                    JComboBox b = (JComboBox)c[1];
                                    b.removeAllItems();
                                    for(int x=0;x<it.length;x++)
                                    {
                                        b.addItem(it[x]);
                                    }
                                    changeElementName(component1.getName(),jcomp2.getText()+"##"+items+"##"+jcomp3.isSelected(),jp);
                                }
                            }
                        }
                        
                        
                    }
                    catch(Exception e){}
                }
            });

        //adjust size and set layout
        setPreferredSize (new Dimension (204, 456));
        setLayout (null);

        //add components
        add (jcomp1);
        add (jcomp2);
        add (jcomp3);
        add (jcomp4);
        add (jcomp5);
        add (jcomp6);
        add (jcomp7);
        add (jcomp8);
        add (s);
        add(title);

        //set component bounds (only needed by Absolute Positioning)
        jcomp1.setBounds (65, 55, 55, 15);
        jcomp2.setBounds (20, 73, 160, 25);
        jcomp3.setBounds (50, 110, 100, 25);
        jcomp4.setBounds (55, 385, 100, 30);
        jcomp5.setBounds (11, 135, 180, 2);
        jcomp6.setBounds (65, 145, 105, 20);
        jcomp7.setBounds (11, 170, 180, 2);
        jcomp8.setBounds (25, 180, 170, 20);
        s.setBounds (25, 202, 160, 175);
        title.setBounds (10, 10, 185, 440);
        return this;
    }
    
    
    
    public JPanel getTextFieldProperties(final String id, final String name, final String section)
    {
        String[] jcomp4Items = {"Normal", "Numeric", "Email", "Phone Number", "Website Address"};

        //construct components
        JLabel jcomp1 = new JLabel ("Caption");
        final JTextField jcomp2 = new JTextField (5);
        JLabel jcomp3 = new JLabel ("Type");
        final JComboBox jcomp4 = new JComboBox (jcomp4Items);
        final JCheckBox required = new JCheckBox("Required");
        JButton jcomp5 = new JButton ("Update");
        JPanel title = new JPanel ();
        title.setBorder(new TitledBorder("TextField Properties"));
        String caption[] = id.split("##");
        jcomp2.setText(caption[1]);
        jcomp4.setSelectedItem(caption[2]);
        if(caption[3].trim().equals("true"))
        {
            required.setSelected(true);
        }
        
        jcomp5.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent v)
                {
                    if(jcomp2.getText().length()==0)
                    {
                        new Constants().alert(null, "warn", "Enter caption for Textfield");
                        return;
                    }
                    writeToSaved();
                    try
                    {
                         Component[] components = main.getComponents();
                        for (Component component1 : components)
                        {
                            if (component1.getClass().getName().equals("javax.swing.JPanel"))
                            {
                                if(component1.getName().equals(id))
                                {
                                    JPanel jp = (JPanel) component1;
                                    Component[] c = jp.getComponents();
                                    JLabel jl = (JLabel)c[0];
                                    jl.setText(jcomp2.getText());
                                    changeElementName(component1.getName(),jcomp2.getText()+"##"+jcomp4.getSelectedItem()+"##"+required.isSelected(),jp);
                                }
                            }
                        }
                        
                        
                    }
                    catch(Exception e){}
                }
            });
        
        
        setPreferredSize (new Dimension (204, 456));
        setLayout (null);

        //add components
        add (jcomp1);
        add (jcomp2);
        add (jcomp3);
        add (jcomp4);
        add (jcomp5);
        add (required);
        add (title);

        //set component bounds (only needed by Absolute Positioning)
        jcomp1.setBounds (65, 55, 55, 15);
        jcomp2.setBounds (20, 73, 160, 25);
        jcomp3.setBounds (65, 102, 100, 15);
        jcomp4.setBounds (20, 124, 160, 25);
        required.setBounds (20, 160, 160, 25);
        jcomp5.setBounds (50, 190, 100, 30);
        title.setBounds (10, 10, 185, 440);
        return this;
    }
    
    
    public void writeToSaved()
    {
        try{FileWriter f = new FileWriter("10110.jaj");f.write("");f.flush();f.close();}catch(Exception e){}
    }
    
    public void writeFile(String id, String toWrite, String name, String section)
    {
        try
        {
            FileWriter f = new FileWriter("projects/"+name+"/sections/"+section+"/"+id+".jaj");
            f.write(toWrite);
            f.close();
        }
        catch(Exception e)
        {
            
        }
    }
    
    public void changeElementName(String pname, String contents, JPanel panel)
    {
        if(pname.startsWith("label"))
        {
            String[] split = pname.split("##");
            panel.setName(split[0].trim()+"##"+contents+"##-##");
        }
        
        if(pname.startsWith("textfield"))
        {
            String[] split = pname.split("##");
            String name = split[0].trim();
            panel.setName(name+"##"+contents+"##");
        }
        if(pname.startsWith("textarea"))
        {
            String[] split = pname.split("##");
            String name = split[0].trim();
            panel.setName(name+"##"+contents+"##");
        }
        if(pname.startsWith("time"))
        {
            String[] split = pname.split("##");
            String name = split[0].trim();
            panel.setName(name+"##"+contents+"##");
        }
        if(pname.startsWith("date"))
        {
            String[] split = pname.split("##");
            String name = split[0].trim();
            panel.setName(name+"##"+contents+"##");
        }
        if(pname.startsWith("image"))
        {
            String[] split = pname.split("##");
            String name = split[0].trim();
            panel.setName(name+"##"+contents+"##");
        }
        if(pname.startsWith("barcode"))
        {
            String[] split = pname.split("##");
            String name = split[0].trim();
            panel.setName(name+"##"+contents+"##");
        }
        if(pname.startsWith("audio"))
        {
            String[] split = pname.split("##");
            String name = split[0].trim();
            panel.setName(name+"##"+contents+"##");
        }
        if(pname.startsWith("video"))
        {
            String[] split = pname.split("##");
            String name = split[0].trim();
            panel.setName(name+"##"+contents+"##");
        }
        if(pname.startsWith("drop"))
        {
            String[] split = pname.split("##");
            String name = split[0].trim();
            panel.setName(name+"##"+contents+"##");
        }
        if(pname.startsWith("checkbox"))
        {
            String[] split = pname.split("##");
            String name = split[0].trim();
            panel.setName(name+"##"+contents+"##");
        }
        if(pname.startsWith("radio"))
        {
            String[] split = pname.split("##");
            String name = split[0].trim();
            panel.setName(name+"##"+contents+"##");
        }
        if(pname.startsWith("location"))
        {
            String[] split = pname.split("##");
            String name = split[0].trim();
            panel.setName(name+"##"+contents+"##");
        }
        if(pname.startsWith("spinner"))
        {
            String[] split = pname.split("##");
            String name = split[0].trim();
            panel.setName(name+"##"+contents+"##");
        }
        if(pname.startsWith("onoff"))
        {
            String[] split = pname.split("##");
            String name = split[0].trim();
            panel.setName(name+"##"+contents+"##");
        }
        try
        {
            File f = new File("projects/"+name+"/autoSave.jaj");
            File ff = new File("projects/autoSave.jaj");
            if(f.exists() || ff.exists())
            {
                SavedProject.saveSectionWork();
            }
        }
        catch(Exception e)
        {
            
        }
    }
    
    
}
