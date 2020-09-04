/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jajitech.desktop.xdata.ui;

import com.jajitech.desktop.xdata.constants.Constants;
import com.jajitech.desktop.xdata.saved.SavedProject.Drawer;
import com.toedter.calendar.JDateChooser;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author Jaji
 */
public class UIBuilder {
    
    Constants cons = null;
    String random = "";
    int xx=1;
    Drawer dr;
    public UIBuilder()
    {
        cons = new Constants();
    }
    
    
    
    public void addToDrawer(final JComponent c)
    {
        c.addMouseListener(new MouseAdapter()
        {
            public void mouseEntered(MouseEvent v)
            {
                c.setBackground(Color.lightGray);
            }
        });
        
        c.addMouseListener(new MouseAdapter()
        {
            public void mouseExited(MouseEvent v)
            {
                c.setBackground(Color.white);
            }
        });
    }
    
    @SuppressWarnings("empty-statement")
    public void populateMatrix(JTable matrix, String data, boolean isRadio)
    {
        
        System.out.println("data from ui "+data);
        String dt[] = data.split("@@@");
        String header = dt[0].trim();
        Vector question = new Vector();
        String tt[] = dt[1].split("##");
        String qq[] = tt[0].trim().split("@@");
        for(String y: qq)
        {
            System.out.println("this is question in ui "+y);
            question.add(y);
        }
        clearTable(matrix);
        String[] headerStrings = header.split("@@");
        System.out.println(headerStrings.length + " is the length");
        Vector rows2 = new Vector();
        Vector header2 = new Vector();
        header2.add("");
        for (int i = 0; i < headerStrings.length; i++)
        {
            System.out.println("this is header "+i+" "+headerStrings[i]);
            header2.add(headerStrings[i]);
        }
        matrix.getTableHeader().setReorderingAllowed(false);
                        
        int x=0;
        final Vector cl = new Vector();
        final Vector canEdit = new Vector();
        
      
        for (Object q: question)
        {
            cl.add(String.class);
            canEdit.add(Boolean.FALSE);
            Vector v= new Vector();
            v.add(q);
            for(Object t: header2)
            {
                if(!t.equals(""))
                {
                    if(isRadio == true)
                    {
                        cl.add(Boolean.class);
                        v.add(Boolean.FALSE);
                    }
                    else
                    {
                        cl.add(String.class);
                        v.add("");
                    }
                    canEdit.add(Boolean.TRUE);
                }
            }
            rows2.add(v);
        }
               
        DefaultTableModel mds= new  javax.swing.table.DefaultTableModel(rows2,header2) 
        {
           @Override
            public Class getColumnClass(int columnIndex) {
                    return (Class) cl.get(columnIndex);
            }

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return (Boolean) canEdit.get(columnIndex);
            }
        };
        matrix.setModel(mds);


        TableColumn tb=matrix.getColumnModel().getColumn(0);
        tb.setPreferredWidth(150);
        
        for(int g=1; g<headerStrings.length; g++)
        {
            tb=matrix.getColumnModel().getColumn(g);
            tb.setPreferredWidth(50);
        }
        
    }
    
    
    public   void clearTable(JTable table)
    {
        int numRows = table.getRowCount();
        int numCols = table.getColumnCount();
        javax.swing.table.TableModel model = table.getModel();

        for (int i=0; i < numRows; i++) 
	{
            for (int j=0; j < numCols; j++)
	    {
                model.setValueAt(null, i, j);
            }
        }
    }
    
    public JPanel getRichTextPanel()
     {
        random = cons.randomNumeric(12);
        JPanel a = new JPanel();a.add(getComponentPanel());
        a.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
        a.setLayout(new BoxLayout(a,BoxLayout.Y_AXIS));
        a.setBackground(Color.white);
        JMultiLineLabel l = new JMultiLineLabel("Matrix Component");
        //l.setMaximumSize(new Dimension(Integer.MAX_VALUE, l.getMinimumSize().height));
        a.add(l);
        JPanel f = new JPanel();
        f.setBackground(Color.white);
        f.setLayout(new FlowLayout(FlowLayout.LEFT));
        JTable table = new JTable(5,5);
        table.setPreferredScrollableViewportSize(table.getPreferredSize());
        JScrollPane s = new JScrollPane(table);
        
        f.add(s);
        a.add(s);
        a.add(getComponentPanel());
        a.setName("matrix"+random+"##Matrix Component##Yes@@No@@Maybe@@@Are you okay@@Are you alight?@@Do you like food@@Do you drink alcohol##AT_LEAST_ONE@@Radio Button##no");
        populateMatrix(table, a.getName(), true);
       return a;
    }
    
    public JPanel getMatrixPanel()
     {
        random = cons.randomNumeric(12);
        JPanel a = new JPanel();a.add(getComponentPanel());
        a.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
        a.setLayout(new BoxLayout(a,BoxLayout.Y_AXIS));
        a.setBackground(Color.white);
        JMultiLineLabel l = new JMultiLineLabel("Matrix Component");
        //l.setMaximumSize(new Dimension(Integer.MAX_VALUE, l.getMinimumSize().height));
        a.add(l);
        JPanel f = new JPanel();
        f.setBackground(Color.white);
        f.setLayout(new FlowLayout(FlowLayout.LEFT));
        JTable table = new JTable(5,5);
        table.setPreferredScrollableViewportSize(table.getPreferredSize());
        JScrollPane s = new JScrollPane(table);
        
        f.add(s);
        a.add(s);
        a.add(getComponentPanel());
        a.setName("matrix"+random+"##Matrix Component##Yes@@No@@Maybe@@@Are you okay@@Are you alight?@@Do you like food@@Do you drink alcohol##AT_LEAST_ONE@@Radio Button##no");
        populateMatrix(table, a.getName(), true);
       return a;
    }
    
    public JPanel getRTFLabelPanel()
    {
        random = cons.randomNumeric(12);
        JPanel p = new JPanel();p.add(getComponentPanel());
        p.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
        //p.setLayout(new BoxLayout(p,BoxLayout.Y_AXIS));
        p.setName("rtflabel"+random+"##<label>##items##required##no");
        Color color = Color.decode("#C4D9EA");
        p.setBackground(color);
        JButton label = new JButton("<html><b>RIch Text Holder<br></b> </html>");
        label.setBackground(color);
        //label.setMaximumSize(new Dimension(Integer.MAX_VALUE, label.getMaximumSize().height));
        //label.setForeground(Color.red);
        label.setFont(new Font("Arial",Font.BOLD,15));
        p.add(label);
        p.add(getComponentPanel());
        addToDrawer(p);
        return p;
    }
    
    
    public JPanel getLabelPanel()
    {
        random = cons.randomNumeric(12);
        JPanel p = new JPanel();p.add(getComponentPanel());
        p.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
        p.setLayout(new BoxLayout(p,BoxLayout.Y_AXIS));
        p.setName("label"+random+"##<label>##items##required##no");
        Color color = Color.decode("#C4D9EA");
        p.setBackground(color);
        JMultiLineLabel label = new JMultiLineLabel("<label>");
        label.setBackground(color);
        //label.setMaximumSize(new Dimension(Integer.MAX_VALUE, label.getMaximumSize().height));
        //label.setForeground(Color.red);
        label.setFont(new Font("Arial",Font.BOLD,15));
        p.add(label);
        p.add(getComponentPanel());
        addToDrawer(p);
        return p;
    }
    
    public JPanel getBarCodePanel()
    {
        random = cons.randomNumeric(12);
        JPanel a = new JPanel();
        a.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
        a.setLayout(new BoxLayout(a,BoxLayout.Y_AXIS));
        a.add(getComponentPanel());
        a.setBackground(Color.white);
        JLabel l = new JLabel("Barcode");
        l.setIcon(new ImageIcon(getClass().getResource("images/bar16.png")));
        //l.setMaximumSize(new Dimension(Integer.MAX_VALUE, l.getMinimumSize().height));
        a.add(l);
        
        a.add(getComponentPanel());
        a.setName("barcode"+random+"##Scan Barcode##-##true##no");
        addToDrawer(a);
       return a;
    }
    
    public JPanel getAudioPanel()
    {
        random = cons.randomNumeric(12);
        JPanel a = new JPanel();
        a.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
        a.setLayout(new BoxLayout(a,BoxLayout.Y_AXIS));
        a.add(getComponentPanel());
        a.setBackground(Color.white);
        JLabel l = new JLabel("Audio Recorder");
        l.setIcon(new ImageIcon(getClass().getResource("images/audio16.png")));
        //l.setMaximumSize(new Dimension(Integer.MAX_VALUE, l.getMinimumSize().height));
        a.add(l);
       
        a.add(getComponentPanel());
        a.setName("audio"+random+"##Audio Recorder##-##true##no");
        addToDrawer(a);
       return a;
    }
    
    public JPanel getVideoPanel()
    {
        random = cons.randomNumeric(12);
        JPanel a = new JPanel();
        a.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
        a.setLayout(new BoxLayout(a,BoxLayout.Y_AXIS));
        a.add(getComponentPanel());
        a.setBackground(Color.white);
        JLabel l = new JLabel("Video Recorder");
        l.setIcon(new ImageIcon(getClass().getResource("images/video16.png")));
        //l.setMaximumSize(new Dimension(Integer.MAX_VALUE, l.getMinimumSize().height));
        a.add(l);
        
        a.add(getComponentPanel());
        a.setName("video"+random+"##Video Recorder##-##true##no");
        addToDrawer(a);
       return a;
    }
    
    
    
    public JPanel getSpinnerPanel()
    {
        random = cons.randomNumeric(12);
        JPanel a = new JPanel();a.add(getComponentPanel());
        a.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
        a.setLayout(new BoxLayout(a,BoxLayout.Y_AXIS));
        a.setBackground(Color.white);
        JMultiLineLabel l = new JMultiLineLabel("Numeric Spinner");
        //l.setMaximumSize(new Dimension(Integer.MAX_VALUE, l.getMinimumSize().height));
        a.add(l);
        JPanel f = new JPanel();
        f.setLayout(new BoxLayout(f,BoxLayout.X_AXIS));
        f.setBackground(Color.white);
        JSpinner  ll = new JSpinner();
        f.add(ll);
        //f.setMaximumSize(new Dimension(Integer.MAX_VALUE, f.getMinimumSize().height));
        a.add(f);
        a.add(getComponentPanel());
        a.setName("spinner"+random+"##Numeric Spinner##-##true##no");
        
       return a;
    }
    
    public JPanel getLocationPanel()
    {
        random = cons.randomNumeric(12);
        JPanel a = new JPanel();
        a.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
        a.setLayout(new BoxLayout(a,BoxLayout.Y_AXIS));
        a.setBackground(Color.white);
        a.add(getComponentPanel());
        JMultiLineLabel l = new JMultiLineLabel("Location");
        a.add(l);
        JPanel y = new JPanel(new BorderLayout());
        y.setBackground(Color.white);
        JLabel ll = new JLabel();
        ll.setIcon(new ImageIcon(getClass().getResource("images/map.png")));
        y.add("West", ll);
        a.add(y);
        a.add(getComponentPanel());
        a.setName("location"+random+"##Location##-##true##no");
       return a;
    }
    
     public JPanel getONPanel()
    {
        random = cons.randomNumeric(12);
        JPanel a = new JPanel();a.add(getComponentPanel());
        a.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
        a.setLayout(new BoxLayout(a,BoxLayout.Y_AXIS));
        a.setBackground(Color.white);
        JMultiLineLabel l = new JMultiLineLabel("ON/OFF");
        //l.setMaximumSize(new Dimension(Integer.MAX_VALUE, l.getMinimumSize().height));
        a.add(l);
        JPanel f = new JPanel(new FlowLayout(FlowLayout.LEFT));
        f.setBackground(Color.white);
        final JLabel ll = new JLabel();
        ll.setIcon(new ImageIcon(getClass().getResource("images/switch"+xx+".png")));
        ll.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent m)
            {
                if(xx==2)
                {
                    xx--;
                            ll.setIcon(new ImageIcon(getClass().getResource("images/switch"+xx+".png")));
                }
                else
                {
                    xx++;
                    ll.setIcon(new ImageIcon(getClass().getResource("images/switch"+xx+".png")));
                }
            }
        });
        f.add(ll);
        //f.setMaximumSize(new Dimension(Integer.MAX_VALUE, f.getMinimumSize().height));
        a.add(f);
        a.add(getComponentPanel());
        a.setName("onoff"+random+"##ON/OFF##ON@@OFF##No##no");
       return a;
    }
    
     public JPanel getImagePanel()
     {
        random = cons.randomNumeric(12);
        JPanel a = new JPanel();a.add(getComponentPanel());
        a.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
        a.setLayout(new BoxLayout(a,BoxLayout.Y_AXIS));
        a.setBackground(Color.white);
        JMultiLineLabel l = new JMultiLineLabel("Image");
        //l.setMaximumSize(new Dimension(Integer.MAX_VALUE, l.getMinimumSize().height));
        a.add(l);
        JPanel f = new JPanel();
        f.setBackground(Color.white);
        f.setLayout(new FlowLayout(FlowLayout.LEFT));
        f.add(new JButton("Gallery"));
        f.add(new JButton("Camera"));
        //f.setMaximumSize(new Dimension(Integer.MAX_VALUE, f.getMinimumSize().height));
        a.add(f);
        a.add(getComponentPanel());
        a.setName("image"+random+"##Image Picker##-##true##no");
       return a;
    }
    
    public JPanel getDatePanel()
    {
        random = cons.randomNumeric(12);
        JPanel a = new JPanel();a.add(getComponentPanel());
        a.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
        a.setLayout(new BoxLayout(a,BoxLayout.Y_AXIS));
        a.setBackground(Color.white);
        JMultiLineLabel l = new JMultiLineLabel("Date Picker");
        //l.setMaximumSize(new Dimension(Integer.MAX_VALUE, l.getMinimumSize().height));
        a.add(l);
        JDateChooser f = new JDateChooser();
        f.setDate(null);
        //f.setMaximumSize(new Dimension(Integer.MAX_VALUE, f.getMinimumSize().height));
        a.add(f);
        a.add(getComponentPanel());
        a.setName("date"+random+"##Date Picker##-##true##no");
       return a;
    }
    
    
    
    public JPanel getTimePanel()
    {
        random = cons.randomNumeric(12);
        JPanel a = new JPanel();a.add(getComponentPanel());
        a.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
        a.setLayout(new BoxLayout(a,BoxLayout.Y_AXIS));
        a.setBackground(Color.white);
        JMultiLineLabel l = new JMultiLineLabel("Time Picker");
       // l.setMaximumSize(new Dimension(Integer.MAX_VALUE, l.getMinimumSize().height));
        a.add(l);
        JPanel f = new JPanel();
        f.setBackground(Color.white);
        f.setLayout(new BoxLayout(f, BoxLayout.X_AXIS));
        JTextField hr = new JTextField();
        //hr.setEditable(false);;
        hr.setHorizontalAlignment(0);
        hr.setText("11");
        f.add(hr);
        JTextField mn = new JTextField();
        //mn.setEditable(false);;
        mn.setHorizontalAlignment(0);
        mn.setText("33");
        f.add(mn);
        String j[] = {"AM","PM"};
        JComboBox bx = new JComboBox(j);
        f.add(bx);
        //f.setMaximumSize(new Dimension(Integer.MAX_VALUE, f.getMinimumSize().height));
        a.add(f);
        a.add(getComponentPanel());
        a.setName("time"+random+"##Time Picker##-##true##no");
        return a;
    }
    
    
    
    public JPanel getRadioPanel()
    {
        random = cons.randomNumeric(12);
        JPanel a = new JPanel();a.add(getComponentPanel());
        a.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
        a.setLayout(new BoxLayout(a,BoxLayout.Y_AXIS));
        a.setBackground(Color.white);
        JMultiLineLabel l = new JMultiLineLabel("Radio Button");
        //l.setMaximumSize(new Dimension(Integer.MAX_VALUE, l.getMinimumSize().height));
        a.add(l);
        JPanel w = new JPanel(new BorderLayout());
        w.setBackground(Color.white);
        JPanel f = new JPanel();
        f.setBackground(Color.white);
        f.setLayout(new BoxLayout(f, BoxLayout.PAGE_AXIS));
        JRadioButton b1 = new JRadioButton("Option 1");
        f.add(b1);
        JRadioButton b3 = new JRadioButton("Option 2");
        f.add(b3);
        JRadioButton b2 = new JRadioButton("Option 3");
        ButtonGroup bg = new ButtonGroup();
        bg.add(b1);bg.add(b2);bg.add(b3);
        f.add(b2);
        //f.setMaximumSize(new Dimension(Integer.MAX_VALUE, f.getMinimumSize().height));
        w.add("West", f);
        a.add(w);
        a.add(getComponentPanel());
        a.setName("radio"+random+"##Radio Button##Option 1@@Option 1A@@Option 2##true##no");
        return a;
    }
    
    
    
   
    public JPanel getCheckBoxPanel()
    {
        random = cons.randomNumeric(12);
        JPanel a = new JPanel();a.add(getComponentPanel());
        a.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
        a.setLayout(new BoxLayout(a,BoxLayout.Y_AXIS));
        a.setBackground(Color.white);
        JMultiLineLabel l = new JMultiLineLabel("CheckBoxes");
        a.add(l);
        JPanel w = new JPanel(new BorderLayout());
        w.setBackground(Color.white);
        JPanel f = new JPanel();
        f.setBackground(Color.white);
        f.setLayout(new BoxLayout(f, BoxLayout.PAGE_AXIS));
        JCheckBox b1 = new JCheckBox("Option 1");
        f.add(b1);
        JCheckBox b2 = new JCheckBox("Option 2");
        f.add(b2);
        JCheckBox b3 = new JCheckBox("Option 3");
        f.add(b3);
        w.add("West", f);
        a.add(w);
        a.add(getComponentPanel());
        a.setName("checkbox"+random+"##CheckBox##Option 1@@Option 2@@Option 3##true##no");
        return a;
    }
    
    
    public JPanel getDropDownPanel()
    {
        random = cons.randomNumeric(12);
        JPanel a = new JPanel();a.add(getComponentPanel());
        a.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
        a.setLayout(new BoxLayout(a,BoxLayout.Y_AXIS));
        a.setBackground(Color.white);
        JMultiLineLabel l = new JMultiLineLabel("Dropdown");
        //l.setMaximumSize(new Dimension(Integer.MAX_VALUE, l.getMinimumSize().height));
        a.add(l);
        String it[] = {"Option 1","Option 2"};
        JComboBox f = new JComboBox(it);
        //f.setMaximumSize(new Dimension(Integer.MAX_VALUE, f.getMinimumSize().height));
        a.add(f);
        a.add(getComponentPanel());
        a.setName("drop"+random+"##Dropdown##Option 1@@Option 2##true##no");
       return a;
    }
    
    
    public JPanel getTextFieldPanel()
    {
        random = cons.randomNumeric(12);
        JPanel a = new JPanel();a.add(getComponentPanel());
        a.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
        a.setLayout(new BoxLayout(a,BoxLayout.Y_AXIS));
        a.setBackground(Color.white);
        JMultiLineLabel l = new JMultiLineLabel("TextField");
        //l.setMaximumSize(new Dimension(Integer.MAX_VALUE, l.getMinimumSize().height));
        a.add(l);
        JTextField f = new JTextField();
        
        a.add(f);
        a.add(getComponentPanel());
        a.setName("textfield"+random+"##TextField##Normal##true##no");
        addToDrawer(a);
        return a;
    }
    
    public JPanel getTextAreaPanel()
    {
        random = cons.randomNumeric(12);
        JPanel a = new JPanel();a.add(getComponentPanel());
        a.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
        a.setLayout(new BoxLayout(a,BoxLayout.Y_AXIS));
        a.setBackground(Color.white);
        JMultiLineLabel l = new JMultiLineLabel("TextArea");
        //l.setMaximumSize(new Dimension(Integer.MAX_VALUE, l.getMinimumSize().height));
        a.add(l);
        JTextArea f = new JTextArea(3,5);
        JScrollPane s = new JScrollPane(f);
        a.add(s);
        a.add(getComponentPanel());
        a.setName("textarea"+random+"##TextArea##Normal##true##no");
        return a;
    }
    
    
    public JPanel getComponentPanel()
    {
        JPanel a = new JPanel();
        a.setBackground(Color.white);
        a.setLayout(new FlowLayout(FlowLayout.RIGHT));
        
        return a;
   
    }
    
}