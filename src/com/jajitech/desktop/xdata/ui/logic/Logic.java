/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jajitech.desktop.xdata.ui.logic;

import com.jajitech.desktop.xdata.DropOptions;
import com.jajitech.desktop.xdata.EscapeDialog;
import static com.jajitech.desktop.xdata.constants.Constants.APP_NAME;
import com.jajitech.desktop.xdata.constants.DropDownButton;
import com.jajitech.desktop.xdata.saved.SavedProject;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JToolBar.Separator;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author lukman
 */
public class Logic extends javax.swing.JPanel {

    /**
     * Creates new form NewJPanel
     */
    JFrame parentFrame;
    JDialog dialog;
    List lx;
    String elementName;
    String ename;
    String ecap;
    String eitem;
    String erider;
    Vector rows2;
    Vector header2;
    
    
    public void getLogic(Vector rows, Vector header)
    {
        String[] headerStrings = {"Action","Question","Items","."};
        header2.clear();
        try{
			for (int i = 0; i < headerStrings.length; i++)
				header2.add(headerStrings[i]);
				jTable1.getTableHeader().setReorderingAllowed(false);
            
                                
                                DefaultTableModel mds= new  javax.swing.table.DefaultTableModel(rows,header2) 
			{
				Class[] types = new Class [] {
					java.lang.String.class,java.lang.String.class,java.lang.String.class,java.lang.String.class
				};
				boolean[] canEdit = new boolean [] {
					false,false,false,false
				};
		
                                @Override
				public Class getColumnClass(int columnIndex) {
					return types [columnIndex];
				}
	
                                @Override
				public boolean isCellEditable(int rowIndex, int columnIndex) {
					return canEdit [columnIndex];
				}
			};
			jTable1.setModel(mds);
                        
                        TableColumn tb=jTable1.getColumnModel().getColumn(0);
			tb.setPreferredWidth(15);
                        
                        tb=jTable1.getColumnModel().getColumn(1);
			tb.setPreferredWidth(200);
                        
                        tb=jTable1.getColumnModel().getColumn(2);
			tb.setPreferredWidth(180);
                        
                        jTable1.getColumnModel().getColumn(3).setMinWidth(0);
                        jTable1.getColumnModel().getColumn(3).setMaxWidth(0);
                        
                        
                        
        }catch(Exception er){}
    }
    
    
    public void buildDatePanel(int index, JTextArea result)
    {
        final EscapeDialog d = new EscapeDialog(dialog, true);
        d.setTitle("Select Date/Time");
	d.setResizable(false);
	d.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
	d.addWindowListener(new WindowAdapter(){
	public void windowClosing(WindowEvent e){dialog.setEnabled(true);d.dispose();}});
        List lt = new ArrayList();
       
        JPanel l = new DatePanel(index, result, dialog, d);
        d.getContentPane().add("Center",l);
        d.pack();
        d.setLocationRelativeTo(dialog);
        d.setVisible(true);
    }
    
    
    
    public Logic(Vector e, JFrame parentFrame, JDialog dialog, List lx, String elementName, final JPanel element, final int index, String rider) {
        initComponents();
        header2= new Vector();
        rows2 = new Vector();
        for(Object v: e)
        {
            jComboBox1.addItem(v.toString());
        }
        jLabel3.setVisible(false);
        if(elementName.startsWith("date") || elementName.startsWith("time") || elementName.startsWith("drop") || elementName.startsWith("radio") || elementName.startsWith("check"))
        {
            jLabel3.setVisible(true); 
            jTextArea1.setEditable(false);
        }
        else
        {
            jLabel3.setVisible(false);
        }
        
        
        
        jTextArea1.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent v)
            {
                int index = -1;
                if(elementName.startsWith("date") && jComboBox1.getSelectedItem() != "between")
                {
                    index = 0;
                    buildDatePanel(index, jTextArea1);
                }
                if(elementName.startsWith("time") && jComboBox1.getSelectedItem() != "between")
                {
                    index = 1;
                    buildDatePanel(index, jTextArea1);
                }
                if(elementName.startsWith("date") && jComboBox1.getSelectedItem() == "between")
                {
                    index = 2;
                    buildDatePanel(index, jTextArea1);
                }
                if(elementName.startsWith("time") && jComboBox1.getSelectedItem() == "between")
                {
                    index = 3;
                    buildDatePanel(index, jTextArea1);
                }
                
                if(elementName.startsWith("drop") || elementName.startsWith("radio"))
                {
                    buildDropOptions("drop");
                }
                
                if(elementName.startsWith("check"))
                {
                    buildDropOptions("check");
                }
            }
        });
        
        
        this.parentFrame = parentFrame;
        this.dialog = dialog;
        this.lx = lx;
        this.elementName = elementName;
        String p[] = this.elementName.split("##");
        ename = p[0].trim();
        ecap = p[1].trim();
        eitem = p[2].trim();
        erider = p[3].trim();
        erider = rider;
        System.out.print("this is erider "+erider);
        if(erider.length() > 2)
        {
            String f[] = erider.split("@#_#@");
            for(String b: f)
            {
                if(b.length() > 3)
                {
                    System.out.println("here is b "+b);
                    String r[] = b.split(";;;;");
                    Vector v = new Vector();
                    jComboBox1.setSelectedItem(r[0]);
                    jTextArea1.setText(r[1]);
                    System.out.println("this is r3 "+r[3]);
                    String n[] = r[3].split("#@");
                    v.add(r[2]);
                    v.add(n[0]);
                    v.add(r[4]);
                    v.add(n[1]);
                    
                    rows2.add(v);
                }
                
            }
        }
        getLogic(rows2,header2);
        
        final DropDownButton ps = new DropDownButton("Options", new ImageIcon(com.jajitech.desktop.xdata.saved.SavedProject.class.getResource("images/properties.png")));
        
        JMenuItem pt = new JMenuItem();
        pt.setText("Hide Question");
        pt.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent v)
            {
                hideQuestion();
            }
        });
        ps.addComponent(pt);
        
        
        JMenuItem pt0 = new JMenuItem();
        pt0.setText("Change Question Items");
        pt0.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent v)
            {
                //pasteElementFromClipBoard(1);
            }
        });
        //ps.addComponent(pt0);
        
        JMenuItem pt01 = new JMenuItem();
        pt01.setText("Change Question Caption");
        pt01.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent v)
            {
               changeCaption();
            }
        });
        ps.addComponent(pt01);
        Separator st = new Separator();
        ps.addComponent(st);
        JMenuItem pt02 = new JMenuItem();
        pt02.setText("Remove Action");
        pt02.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent v)
            {
                removeAction();
            }
        });
        ps.addComponent(pt02);
        
        pp.add(ps);
        
        jButton2.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent v)
            {
                if(jTextArea1.getText().length() == 0 || jComboBox1.getSelectedIndex() == 0)
                {
                    JOptionPane.showMessageDialog(dialog, "You have not set any condition or parameter", APP_NAME, JOptionPane.WARNING_MESSAGE);
                    return;
                }
                
                if(jTable1.getRowCount() < 1)
                {
                    JOptionPane.showMessageDialog(dialog, "You have not set any actions", APP_NAME, JOptionPane.WARNING_MESSAGE);
                    return;
                }
                String rider="";
                for(int t=0; t<jTable1.getRowCount(); t++)
                {
                    rider = rider+jComboBox1.getSelectedItem()+";;;;"+jTextArea1.getText()+";;;;"+jTable1.getValueAt(t, 0)+";;;;"+jTable1.getValueAt(t, 1)+"#@"+jTable1.getValueAt(t, 3)+";;;;"+jTable1.getValueAt(t, 2)+"@#_#@";
                }
                Component c[] = element.getComponents();
                JPanel a = (JPanel) c[0]; 
                JLabel ll = new JLabel("");
                ll.setIcon(new ImageIcon(getClass().getResource("images/switch.png")));a.add(ll);
                element.repaint();
                element.revalidate();
                SavedProject.LogicStatus(element, index, rider);
                parentFrame.setEnabled(true);
                dialog.dispose();
            }
        });
        
        
        
    }
    
    
    public void buildDropOptions(String type)
    {
        final EscapeDialog d = new EscapeDialog(dialog, true);
        d.setTitle("Parameters");
	d.setResizable(false);
	d.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
	d.addWindowListener(new WindowAdapter(){
	public void windowClosing(WindowEvent e){dialog.setEnabled(true);d.dispose();}});
        List lt = new ArrayList();
       
        JPanel l = new DropOptions(elementName, jTextArea1, dialog, d,  type);
        d.getContentPane().add("Center",l);
        d.pack();
        d.setLocationRelativeTo(dialog);
        d.setVisible(true);
    }
    
    
    
    
    public void removeAction()
    {
        if(jTable1.getSelectedColumnCount() <1)
        {
            return;
        }
        DefaultTableModel tm = (DefaultTableModel) jTable1.getModel();
        tm.removeRow(jTable1.getSelectedRow());
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
    
    
    public void hideQuestion()
    {
        final EscapeDialog d = new EscapeDialog(dialog, true);
        d.setTitle("Select Question To Hide");
	d.setResizable(false);
	d.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
	d.addWindowListener(new WindowAdapter(){
	public void windowClosing(WindowEvent e){dialog.setEnabled(true);d.dispose();}});
        List lt = new ArrayList();
        for(int h=0; h<jTable1.getRowCount(); h++)
        {
            String b = jTable1.getValueAt(h, 0).toString();
            if(b.equals("Hide"))
            {
                lt.add(jTable1.getValueAt(h, 3));
            }
        }
        JPanel l = new HidePanel(lx, jTable1, d, dialog, lt, this);
        d.getContentPane().add("Center",l);
        d.pack();
        d.setLocationRelativeTo(dialog);
        d.setVisible(true);
    }
    
    
    
    public void changeCaption()
    {
        final EscapeDialog d = new EscapeDialog(dialog, true);
        d.setTitle("Change Caption");
	d.setResizable(false);
	d.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
	d.addWindowListener(new WindowAdapter(){
	public void windowClosing(WindowEvent e){dialog.setEnabled(true);d.dispose();}});
        List lt = new ArrayList();
        for(int h=0; h<jTable1.getRowCount(); h++)
        {
            String b = jTable1.getValueAt(h, 0).toString();
            if(b.equals("Captiom"))
            {
                lt.add(jTable1.getValueAt(h, 3));
            }
        }
        JPanel l = new CaptionPanel(lx, jTable1, d, dialog, lt, this);
        d.getContentPane().add("Center",l);
        d.pack();
        d.setLocationRelativeTo(dialog);
        d.setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        pp = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Logic", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 13), new java.awt.Color(51, 51, 255))); // NOI18N

        jLabel1.setText("Select Condition");

        jLabel2.setText("Enter Parameter");

        jLabel3.setForeground(new java.awt.Color(255, 51, 0));
        jLabel3.setText("To set parameter for this element, click on the field above");

        jTextArea1.setColumns(20);
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(5);
        jTextArea1.setWrapStyleWord(true);
        jScrollPane2.setViewportView(jTextArea1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(14, 14, 14))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel3))
                    .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Action", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 13), new java.awt.Color(0, 0, 255))); // NOI18N

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        pp.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 609, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(pp, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(pp, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
                .addContainerGap())
        );

        jButton2.setText("Save");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton2)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JPanel pp;
    // End of variables declaration//GEN-END:variables
}
