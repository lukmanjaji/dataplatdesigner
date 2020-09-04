/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jajitech.desktop.xdata.saved;

import com.jajitech.desktop.xdata.EscapeDialog;
import com.jajitech.desktop.xdata.constants.Constants;
import com.jajitech.desktop.xdata.controller.MainDAO;
import static com.jajitech.desktop.xdata.saved.SavedProject.mainD;
import com.jajitech.desktop.xdata.ui.JMultiLineLabel;
import com.jajitech.desktop.xdata.ui.MatrixEditor;
import com.jajitech.desktop.xdata.ui.NumericTextField;
import com.jajitech.desktop.xdata.ui.RTF;
import com.jajitech.desktop.xdata.ui.UIBuilder;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JViewport;
import javax.swing.border.TitledBorder;

/**
 *
 * @author Usman
 */
public class PropertiesPanel extends javax.swing.JPanel {

    /**
     * Creates new form PropertiesPanel
     */
    JFrame parent;
    JPanel center;
    public String ctext;
    String creq, isRider;
    String citems, name, section, cname;
    JPanel data;
    Vector list = new Vector();
    JPopupMenu pop;
    JTextArea built;
    MainDAO main = new MainDAO();
    public String mQuestions = "";
    public String mreq = "";
    String mtype = "";
    String matrixItems = "";
    Vector elementsList = new Vector();
    
    
    public PropertiesPanel(final JFrame parent, String name, Vector elementsList) {
        initComponents();
        rider.setVisible(false);
        items.setListData(new Vector());
        type.removeAllItems();
        type.addItem("Normal");
        type.addItem("Numeric");
        type.addItem("Email");
        type.addItem("Phone Number");
        type.addItem("Website Address");
        caption.setLineWrap(true);
        caption.setWrapStyleWord(true);
        this.parent = parent;
        this.name = name;
        this.elementsList = elementsList;
        caption.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyReleased(KeyEvent ke)
            {
                Component[] c = data.getComponents();
                if(!data.getName().startsWith("barcode") && !data.getName().startsWith("audio") && !data.getName().startsWith("video"))
                {
                    final JMultiLineLabel jl = (JMultiLineLabel)c[1];
                    jl.setText(caption.getText());
                    ctext = caption.getText();
                }
                else
                {
                    final JLabel jl = (JLabel)c[1];
                    jl.setText(caption.getText());
                    ctext = caption.getText();
                }
                writeToSaved();
                reWriteElement();
                mainD.setFixedCellHeight(10);
                mainD.setFixedCellHeight(-1);
                mainD.repaint();
                mainD.revalidate();
                
            }
        });
        
        required.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent v)
            {
                creq = ""+required.isSelected();
                writeToSaved();
                reWriteElement();
            }
        });
        
        type.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent v)
            {
                writeToSaved();
                reWriteElement();
            }
        });
        
        
        buildPopupMenu();
        items.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent me)
            {
                if(me.getClickCount() == 2)
                {
                    if(items.getSelectedIndex() < 0)
                    {
                        return;
                    }
                    editItem();
                }
            }
            public void mousePressed(MouseEvent me)
            {
                checkMouseTrigger (me);
            }
            public void mouseReleased (MouseEvent me) { checkMouseTrigger (me); }
        private void checkMouseTrigger (MouseEvent me) {
          if (me.isPopupTrigger ())
            pop.show (me.getComponent (), me.getX (), me.getY ());
        }
        });
        
        matrix.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent v)
            {
                openMatrixEditor();
            }
        });
        
    }
    
    
    public void openMatrixEditor()
    {
        if(list.size() < 1)
        {
            JOptionPane.showMessageDialog(parent, "Please set matrix columns before opening editor", Constants.APP_NAME, JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        final EscapeDialog d = new EscapeDialog(parent, true);
        d.setTitle("Matix Editor");
        //d.setResizable(false);
	d.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
	d.addWindowListener(new WindowAdapter(){
	public void windowClosing(WindowEvent e){parent.setEnabled(true);d.dispose();}});
        
        JPanel m = new MatrixEditor(mQuestions, parent, this);
        d.getContentPane().add("Center", m);
        d.pack();
        d.setLocationRelativeTo(parent);
        d.setVisible(true);
    }
    
    
    public void editItem()
    {
        Object entry = JOptionPane.showInputDialog(parent, "Enter New Item", "Edit Item", JOptionPane.QUESTION_MESSAGE, null , null, items.getSelectedValue());
        if(entry == null || entry.equals(""))
        {
            return;
        }
        int index = items.getSelectedIndex();
        list.setElementAt(entry, items.getSelectedIndex());
        items.setListData(list);
        writeToSaved();
        reWriteElement();
        parent.setEnabled(true);
        mainD.setFixedCellHeight(10);
        mainD.setFixedCellHeight(-1);
        mainD.repaint();
        mainD.revalidate();
    }
    
    
    public void buildPopupMenu()
    {
        pop = new JPopupMenu();
        JMenuItem m1 = new JMenuItem("Add Item");
        JMenuItem m2 = new JMenuItem("Edit Selected Item");
        JMenuItem m3 = new JMenuItem("Remove Item");
        pop.add(m1);
        pop.add(m2);
        pop.add(m3);
        pop.addSeparator();
        JMenuItem m4 = new JMenuItem("Clear Items");
        pop.add(m4);
        pop.addSeparator();
        JMenuItem m5 = new JMenuItem("Save as custom");
        pop.add(m5);
        
        m5.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent v)
            {
                if(list.size() < 1)
                {
                    return;
                }
                String entry = JOptionPane.showInputDialog(parent, "Enter custom name", "Save As", JOptionPane.QUESTION_MESSAGE);
                entry = entry.replace(" ", "_");
                if(entry == null || entry.equals(""))
                {
                    return;
                }
                String it = PropertiesPanel.this.itemsToString();
                it = it.replace("@@","\n");
                try
                {
                  FileWriter fr = new FileWriter("res/bin/custom/"+entry+".def");
                  fr.write(it);
                  fr.close();
                  SavedProject.toast.showToaster("Custom items saved successfully...");
                }catch(Exception er){}
            }
        });
        
        
        m1.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent v)
            {
                if(!data.getName().startsWith("drop") && !data.getName().startsWith("checkbox") && !data.getName().startsWith("radio") && !data.getName().startsWith("matrix"))
                {
                    return;
                }
                String entry = JOptionPane.showInputDialog(parent, "Enter New Item", "Add Item", JOptionPane.QUESTION_MESSAGE);
                if(entry == null || entry.equals(""))
                {
                    return;
                }
                list.add(entry);
                items.setListData(list);
                writeToSaved();
                reWriteElement();
                parent.setEnabled(true);
                mainD.setFixedCellHeight(10);
                mainD.setFixedCellHeight(-1);
                mainD.repaint();
                mainD.revalidate();
                
            }
        });
        
        m2.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent v)
            {
                if(items.getSelectedIndex() < 0)
                    {
                        return;
                    }
                    editItem();
            }
        });
        
        m3.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent v)
            {
                int x = JOptionPane.showConfirmDialog(parent, "Remove selected item (s) ?", "Remove Item", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
                if(x == JOptionPane.CANCEL_OPTION || x == JOptionPane.NO_OPTION)
                {
                    return;
                }
                
                int d[] = items.getSelectedIndices();
                for (int i = d.length-1; i >=0; i--) {
                    list.removeElementAt(d[i]);
          } 
                items.setListData(list);
                writeToSaved();
                reWriteElement();
                parent.setEnabled(true);
                mainD.setFixedCellHeight(10);
                mainD.setFixedCellHeight(-1);
                mainD.repaint();
                mainD.revalidate();
            }
        });
        
        m4.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent v)
            {
                int x = JOptionPane.showConfirmDialog(parent, "Clear Items..?", "Clear Item", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
                if(x == JOptionPane.CANCEL_OPTION || x == JOptionPane.NO_OPTION)
                {
                    return;
                }
                list.removeAllElements();
                items.setListData(list);
                writeToSaved();
                reWriteElement();
                parent.setEnabled(true);
                mainD.setFixedCellHeight(10);
                mainD.setFixedCellHeight(-1);
                mainD.repaint();
                mainD.revalidate();
            }
        });
        
        
    }
    
    public void setSection(String section)
    {
        this.section = section;
    }
    
    public void setData(JPanel data)
    {
        this.data = data;
        System.out.println("this is panel name from properties "+data.getName());
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        caption = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        required = new javax.swing.JCheckBox();
        jLabel3 = new javax.swing.JLabel();
        inbuilt = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel5 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel6 = new javax.swing.JLabel();
        type = new javax.swing.JComboBox<>();
        matrix = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        items = new javax.swing.JList<>();
        rider = new javax.swing.JButton();

        jLabel1.setText("Caption");

        caption.setColumns(20);
        caption.setRows(5);
        jScrollPane1.setViewportView(caption);

        jLabel2.setText("Required");

        required.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                requiredActionPerformed(evt);
            }
        });

        jLabel3.setText("Items");

        inbuilt.setText("Inbuilt Items");
        inbuilt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inbuiltActionPerformed(evt);
            }
        });

        jLabel4.setText("Right click list for options");

        jLabel5.setText("TextField Constraints");

        jLabel6.setText("Type");

        type.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        type.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                typeActionPerformed(evt);
            }
        });

        matrix.setText("Matrix Editor");
        matrix.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                matrixActionPerformed(evt);
            }
        });

        items.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane3.setViewportView(items);

        rider.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/jajitech/desktop/xdata/ui/logic/images/switch.png"))); // NOI18N
        rider.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                riderActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator4)
                    .addComponent(jSeparator3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator2)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jSeparator1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(matrix)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(inbuilt, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 75, Short.MAX_VALUE)
                        .addComponent(jLabel6)
                        .addGap(36, 36, 36)
                        .addComponent(type, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(70, 70, 70))
                    .addComponent(jScrollPane3)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(5, 5, 5)
                        .addComponent(required, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(rider, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(5, 5, 5)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(required, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rider))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(matrix)
                    .addComponent(inbuilt))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(type, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(24, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void requiredActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_requiredActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_requiredActionPerformed

    
    public void loadBuiltItems(String toLoad)
    {
        built.setText("");
        if(toLoad.equals("Africa"))
        {
            built.setText(readBuiltItems("africa.def"));
        }
        if(toLoad.equals("Asia"))
        {
            built.setText(readBuiltItems("asia.def"));
        }
        if(toLoad.equals("Europe"))
        {
            built.setText(readBuiltItems("europe.def"));
        }
        if(toLoad.equals("North America"))
        {
            built.setText(readBuiltItems("north_america.def"));
        }
        if(toLoad.equals("Oceania"))
        {
            built.setText(readBuiltItems("oceania.def"));
        }
        if(toLoad.equals("South America"))
        {
            built.setText(readBuiltItems("south_america.def"));
        }
        if(toLoad.equals("World"))
        {
            built.setText(readBuiltItems("world.def"));
        }
        if(toLoad.equals("Languages"))
        {
            built.setText(readBuiltItems("languages.def"));
        }
        if(toLoad.equals("Alphabets"))
        {
            built.setText(readBuiltItems("alphabets.def"));
        }
        if(toLoad.equals("Poor - Good"))
        {
            built.setText(readBuiltItems("poor_good.def"));
        }
        if(toLoad.equals("African Regions"))
        {
            built.setText(readBuiltItems("african_regions.def"));
        }
        if(toLoad.equals("Gender"))
        {
            built.setText(readBuiltItems("gender.def"));
        }
        if(toLoad.equals("Yes/No"))
        {
            built.setText(readBuiltItems("yes_no.def"));
        }
        if(toLoad.equals("Month"))
        {
            built.setText(readBuiltItems("months.def"));
        }
        if(toLoad.equals("Days of the week"))
        {
            built.setText(readBuiltItems("week.def"));
        }
        if(toLoad.equals("Religion"))
        {
            built.setText(readBuiltItems("religion.def"));
        }
        if(toLoad.equals("Relationship Status"))
        {
            built.setText(readBuiltItems("relationship.def"));
        }
        if(toLoad.equals("Education Level"))
        {
            built.setText(readBuiltItems("education.def"));
        }
    }
    
    public String readBuiltItems(String toRead)
    {
        String read = readFile("res/bin/"+toRead);
        return read;        
    }
    
    
    
    public String readFile(String path)
    {
        String output = "";
        try{
	FileInputStream fis = new FileInputStream(path);
	BufferedReader br = new BufferedReader(new InputStreamReader(fis));
	String line = null;
	while ((line = br.readLine()) != null) {
		output = output+line+"\n";
	}

	br.close();
        }catch(Exception er){}
        return output.trim();
    }
    
    public void getCustomItems()
    {
        File f = new File("res/bin/custom/");
        String h[] = f.list();
    }
    
    
    public void loadCustomItem(String toRead)
    {
        String read = readFile("res/bin/custom/"+toRead+".def");
        built.setText(read);        
    }
    
    
    private void inbuiltActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inbuiltActionPerformed
        // TODO add your handling code here:
        if(!data.getName().startsWith("checkbox") && !data.getName().startsWith("radio") && !data.getName().startsWith("drop") && !data.getName().startsWith("matrix"))
        
                {
                    return;
                }
        final EscapeDialog d = new EscapeDialog(parent, true);
        d.setTitle("Inbuilt Items");
        //d.setResizable(false);
	d.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
	d.addWindowListener(new WindowAdapter(){
	public void windowClosing(WindowEvent e){parent.setEnabled(true);d.dispose();}});
        
        JPanel s = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton add = new JButton("Add");
        s.add(add);
        add.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent v)
            {
                list.clear();
                String o[] = built.getText().trim().split("\n");
                for(String y: o)
                {
                    list.add(y);
                }
                items.setListData(list);
                    writeToSaved();
                    reWriteElement();
                    parent.setEnabled(true);
                    d.dispose();
                    mainD.setFixedCellHeight(10);
                mainD.setFixedCellHeight(-1);
                mainD.repaint();
                mainD.revalidate();
            }
        });
        
        JButton app = new JButton("Append");
        app.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent v)
            {
                String o[] = built.getText().trim().split("\n");
                for(String y: o)
                {
                    list.add(y);
                }
                items.setListData(list);
                writeToSaved();
                    reWriteElement();
                    parent.setEnabled(true);
                    d.dispose();
                    mainD.setFixedCellHeight(10);
                mainD.setFixedCellHeight(-1);
                mainD.repaint();
                mainD.revalidate();
            }
        });
        s.add(app);
        
        JButton can = new JButton("Cancel");
        can.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent v)
            {
                parent.setEnabled(true);
                d.dispose();
            }
        });
        s.add(can);
        
        d.add("South", s);
        
        
        
        JPanel a = new JPanel(new BorderLayout());
        final JPanel n = new JPanel(new FlowLayout(FlowLayout.CENTER));
        n.setBorder(new TitledBorder("Countries Items"));
        n.add(new JLabel("Country Options"));
        String ct[] = {"", "Africa", "Asia", "Europe", "North America", "Oceania", "South America", "World"};
        final JComboBox c = new JComboBox(ct);
        c.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent v)
            {
                loadBuiltItems(c.getSelectedItem().toString());
            }
        });
        n.add(c);
        
        final JPanel m = new JPanel(new FlowLayout(FlowLayout.CENTER));
        m.setBorder(new TitledBorder("Custom Items"));
        m.add(new JLabel("Select"));
        String h[] = new File("res/bin/custom/").list();
        Vector e = new Vector();
        e.add("");
        for(String g: h)
        {
            e.add(g.replace(".def", ""));
        }
        final JComboBox  ms = new JComboBox(e);
        ms.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent v)
            {
                if(ms.getSelectedIndex()==0)
                {
                    return;
                }
                built.setText("");
                loadCustomItem(ms.getSelectedItem().toString());
            }
        });
        m.add(ms);
        
        
        final JPanel y = new JPanel(new FlowLayout(FlowLayout.CENTER));
        y.setBorder(new TitledBorder("Numeric Items"));
        y.add(new JLabel("Start"));
        final NumericTextField st = new NumericTextField(4);
        //((AbstractDocument) st.getDocument()).setDocumentFilter(new NumericAndLengthFilter(10));
        y.add(st);
        y.add(new JLabel("   "));
        y.add(new JLabel("End"));
        final NumericTextField sp = new NumericTextField(4);
        y.add(sp);
        sp.addKeyListener(new KeyAdapter()
        {
            public void keyReleased(KeyEvent ke)
            {
                if(st.getText().length()==0)
                {
                    return;
                }
                built.setText("");
                int x = Integer.parseInt(st.getText());
                int y = Integer.parseInt(sp.getText());
                for (int t=x; t<=y; t++)
                {
                    built.append(""+t+"\n");
                }
                
                
            }
        });
        
        st.addKeyListener(new KeyAdapter()
        {
            public void keyReleased(KeyEvent ke)
            {
                if(sp.getText().length()==0)
                {
                    return;
                }
                built.setText("");
                int x = Integer.parseInt(st.getText());
                int y = Integer.parseInt(sp.getText());
                for (int t=x; t<=y; t++)
                {
                    built.append(""+t+"\n");
                }
                
                
            }
        });
        
        final JList list = new JList();
        Vector v = new Vector();
        v.add("Countries");
        v.add("African Regions");
        v.add("Alphabets");
        v.add("Gender");
        v.add("Languages");
        v.add("Poor - Good");
        v.add("Numbers");
        v.add("Yes/No");
        v.add("Month");
        v.add("Days of the week");
        v.add("Religion");
        v.add("Relationship Status");
        v.add("Education Level");
        v.add("Custom");
        list.setListData(v);
        JScrollPane p = new JScrollPane(list);
        d.add("West", p);
        list.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent me)
            {
                built.setText("");
                if(list.getSelectedIndex() == 0 || list.getSelectedIndex() == 6 || list.getSelectedIndex() ==13)
                {
                    try{center.remove(n);center.remove(y);}catch(Exception er){}
                    if(list.getSelectedIndex() == 0)
                    {
                        center.add("North", n);
                    }
                    if(list.getSelectedIndex() == 6)
                    {
                        center.add("North", y);
                    }
                    if(list.getSelectedIndex() == 13)
                    {
                        center.add("North", m);
                    }
                }
                else
                {
                    try{center.remove(n);center.remove(y);center.remove(m);}catch(Exception er){}
                    loadBuiltItems(list.getSelectedValue().toString());
                }
                d.repaint();
                d.revalidate();
            }
        });
        
        
        list.addKeyListener(new KeyAdapter()
        {
            public void keyReleased(KeyEvent me)
            {
                if(me.getKeyCode() == 38 || me.getKeyCode() == 40)
                {
                built.setText("");
                if(list.getSelectedIndex() == 0 || list.getSelectedIndex() == 6 || list.getSelectedIndex() ==13)
                {
                    try{center.remove(n);center.remove(y);}catch(Exception er){}
                    if(list.getSelectedIndex() == 0)
                    {
                        center.add("North", n);
                    }
                    if(list.getSelectedIndex() == 6)
                    {
                        center.add("North", y);
                    }
                    if(list.getSelectedIndex() == 13)
                    {
                        center.add("North", m);
                    }
                }
                else
                {
                    try{center.remove(n);center.remove(y);center.remove(m);}catch(Exception er){}
                    loadBuiltItems(list.getSelectedValue().toString());
                }
                d.repaint();
                d.revalidate();
            }
            }
        });
        
        
        
        
        center = new JPanel(new BorderLayout());
        built = new JTextArea(5,5);
        built.setLineWrap(false);
        JScrollPane ss = new JScrollPane(built);
        center.add(ss, "Center");
        d.add("Center", center);
        
        
        d.setSize(650,600);
        d.setLocationRelativeTo(parent);
        d.setVisible(true); 
    }//GEN-LAST:event_inbuiltActionPerformed

    
    public void removeRider()
    {
        System.out.println("this is isrider "+isRider);
        rider.setVisible(false);
        isRider = "no";
        writeToSaved();
        reWriteElement();
    }
    
    private void typeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_typeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_typeActionPerformed

    private void riderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_riderActionPerformed
removeRider();
/*        
        parent.setEnabled(false);
        RiderEditor r = new RiderEditor(parent, this);
        r.setVisible(true);
*/
    }//GEN-LAST:event_riderActionPerformed

    private void matrixActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_matrixActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_matrixActionPerformed

    
    
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
    
    public void reWriteElement()
    {
        System.out.println("i am about to rewrite element");
        if(!cname.startsWith("matrix"))
        {
            data.setName(cname+"##"+ctext+"##"+this.itemsToString()+"##"+creq+"##"+isRider);
        }
        else
        {
            data.setName(cname+"##"+ctext+"##"+this.itemsToString()+"@@@"+mQuestions+"##"+mreq+"##"+isRider);
            matrixItems = this.itemsToString()+"@@@"+mQuestions;
        }
        Component[] c = data.getComponents();
        JPanel a = (JPanel) c[0];
        System.out.println("this is isrider from ppanel "+isRider);
        
        if(isRider.length() > 2)
        {
            JLabel ll = new JLabel("");
            ll.setForeground(Color.red);
            ll.setIcon(new ImageIcon(getClass().getResource("images/switch.png")));
            a.repaint();
            a.revalidate();
        }
        else{
            a.removeAll();
        }
        
        
        if(cname.startsWith("checkbox") || cname.startsWith("radio"))
        {
            final JPanel jl = (JPanel)c[2];
            Component y[] = jl.getComponents();
            JPanel k = (JPanel) y[0];
            k.removeAll();
            for(Object t: list)
            {
                 if(cname.startsWith("checkbox"))
                 {
                     JCheckBox cc = new JCheckBox();
                     cc.setText(t.toString());
                     k.add(cc);
                 }
                 if(cname.startsWith("radio"))
                 {
                     JRadioButton cc = new JRadioButton();
                     cc.setText(t.toString());
                     k.add(cc);
                 }
            }
            SavedProject.mainD.repaint();
            SavedProject.mainD.revalidate();
        }
        if(cname.startsWith("drop"))
        {
            final JComboBox jl = (JComboBox)c[2];
            jl.removeAllItems();
            for(Object t: list)
            {
                jl.addItem(t);
            }
        }
        if(cname.startsWith("matrix"))
        {
            System.out.println("this is matrix items "+matrixItems);
            JScrollPane f =  (JScrollPane) c[2];
            JViewport fv = f.getViewport();
            JTable table = (JTable) fv.getView();
            UIBuilder ui = new UIBuilder();
            boolean isRadio = false;
            if(mreq.contains("Radio"))
            {
                isRadio = true;
            }
            ui.populateMatrix(table, matrixItems, isRadio);
            SavedProject.mainD.repaint();
            SavedProject.mainD.revalidate();
        }
            
        
        
        try
        {
            File f = new File("projects/"+name+"/autoSave.jaj");
            File ff = new File("projects/autoSave.jaj");
            if(f.exists() || ff.exists())
            {
                System.out.println("i'm saving section work");
                SavedProject.saveSectionWork();
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    
    public void showProperties()
    {
        String props = data.getName();
        if(!props.startsWith("matrix") && !props.startsWith("rtf"))
        {
            matrix.setVisible(false);
            jScrollPane1.setVisible(true);
            jLabel3.setText("Items");
            repaint();
            revalidate();
        }
        if(props.startsWith("matrix") && !props.startsWith("rtf"))
        {
             matrix.setVisible(true);
             jScrollPane1.setVisible(true);
            repaint();
            jLabel3.setText("Matrix Column Items");
            revalidate();
        }
        if(props.startsWith("rtf"))
        {
            matrix.setVisible(false);
            jScrollPane1.setVisible(false);
            JButton rtf = new JButton("Edit Rich Text");
            rtf.setBounds(100,40,150,30);
            rtf.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent v)
                {
                    RTF r = new RTF(parent, ctext, PropertiesPanel.this);
                    ctext = r.getCaption();
                    reWriteElement();
                }
            });
            this.add(rtf);
            repaint();
            revalidate();
        }
        System.out.println("this is name at showProperties "+props);
        if(!props.startsWith("textfield"))
        {
            type.setEnabled(false);
        }
        else
        {
            type.setEnabled(true);
        }
        if(!props.startsWith("matrix"))
        {
            String p[] = props.split("##");
            cname = p[0];
            ctext = p[1];
            setCaption();
            creq = p[3];
            setRequired();
            citems = p[2];
            setItems();
            String rText = p[4];
            setRider(rText);
        }
        else
        {
            String p[] = props.split("##");
            cname = p[0];
            ctext = p[1];
            setCaption();
            creq = p[3];
            mreq = creq;
            String rText = p[4];
            setRider(rText);
            System.out.println("this is cname "+cname);
            System.out.println("this is ctext "+ctext);
            System.out.println("this is mreq "+mreq);
            System.out.println("this is mreq "+mreq);
            if(!mreq.startsWith("NONE"))
            {
                creq = "true";
            }
            else
            {
                creq = "false";
            }
            System.out.println("this is creq "+creq);
            citems = p[2];
            matrixItems = citems;
            String q[] = citems.split("@@@");
            citems = q[0].trim();
            mQuestions = q[1].trim();
            setItems();
            setRequired();
        }
        
    }
    
    public void setRider(String rText)
    {
        isRider = rText;
        if(isRider.equals("no") || isRider.equals("isRider"))
        {
            rider.setVisible(false);
        }
        else
        {
           rider.setVisible(true);
           rider.setToolTipText("Remove Logic");
        }
    }
    
    
    public String itemsToString()
    {
        String s = "";
        if(!data.getName().startsWith("textfield"))
        {
            for(Object t: list)
            {
                s = s + "@@"+t.toString();
            }
        }
        else
        {
            System.out.println("i set type for textfield");
            s = type.getSelectedItem().toString();
        }

        if(s.startsWith("@"))
        {
            s = s.substring(2);
        }
        System.out.println("this is s in itemstring "+s);
        return s;
    }
    
    public void setItems()
    {
        System.out.println(citems);
        list.clear();
        items.setListData(new Vector());
        if(cname.startsWith("check") || cname.startsWith("radio") || cname.startsWith("drop") || cname.startsWith("textfield") || cname.startsWith("matrix"))
        {
            if(cname.startsWith("textfield"))
            {
                type.setSelectedItem(citems.trim());
            }
            else
            {
                parseItems();
                items.setListData(list);
                //items.setText(citems);
            }
        }
    }
    
    public void parseItems()
    {
        System.out.println(citems);
        String y[] = citems.trim().split("@@");
        for(String t: y)
        {
            list.add(t);
            System.out.println(t);
        }
    }
    
    public void setRequired()
    {
        if(creq.trim().equals("true"))
        {
            required.setSelected(true);
        }
        else
        {
            required.setSelected(false);
        }
    }
    
    public void setCaption()
    {
        caption.setText(ctext);
    }
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea caption;
    private javax.swing.JButton inbuilt;
    private javax.swing.JList<String> items;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JButton matrix;
    public javax.swing.JCheckBox required;
    private javax.swing.JButton rider;
    private javax.swing.JComboBox<String> type;
    // End of variables declaration//GEN-END:variables
}
