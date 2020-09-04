/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jajitech.desktop.xdata.ui.logic;

import static com.jajitech.desktop.xdata.constants.Constants.APP_NAME;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.border.BevelBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author lukman
 */
public class CaptionPanel extends javax.swing.JPanel {

    /**
     * Creates new form HidePanel
     */
    List lx,ly;
    JTable mtable;
    JDialog host, parent;
    Logic logic;
    public CaptionPanel(List lx, JTable mtable, JDialog host, JDialog parent, List lt, Logic logic) {
        initComponents();
        this.lx = lx;
        this.mtable = mtable;
        this.host = host;
        this.parent = parent;
        this.ly = lt;
        this.logic = logic;
        getQuestions(jTable1);
        
        
        TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(jTable1.getModel());
                        jTable1.setRowSorter(sorter);
                        jTextField1.getDocument().addDocumentListener(new DocumentListener(){

                        @Override
                        public void insertUpdate(DocumentEvent e) {
                            String text = jTextField1.getText();

                            if (text.trim().length() == 0) {
                                sorter.setRowFilter(null);
                            } else {
                                sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                            }
                        }

                        @Override
                        public void removeUpdate(DocumentEvent e) {
                            String text = jTextField1.getText();

                            if (text.trim().length() == 0) {
                                sorter.setRowFilter(null);
                            } else {
                                sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                            }
                        }

                        @Override
                        public void changedUpdate(DocumentEvent e) {
                            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                        }
                        });
        
        
        
        jButton1.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent v)
            {
                if(jTable1.getSelectedRowCount() < 1)
                {
                    JOptionPane.showMessageDialog(host, "You have not selected a question", APP_NAME, JOptionPane.WARNING_MESSAGE);
                    return;
                }
                
                if(jTextArea1.getText().length() < 1)
                {
                    JOptionPane.showMessageDialog(host, "You have not entered the replacing caption", APP_NAME, JOptionPane.WARNING_MESSAGE);
                    return;
                }
                
                DefaultTableModel tm = (DefaultTableModel) mtable.getModel();
                Vector r2 = new Vector();
                for(int b=0; b<mtable.getRowCount(); b++)
                {
                    Vector r2a = new Vector();
                    r2a.add(mtable.getValueAt(b, 0));
                    r2a.add(mtable.getValueAt(b, 1));
                    r2a.add(mtable.getValueAt(b, 2));
                    r2a.add(mtable.getValueAt(b, 3));
                    r2.add(r2a);
                }

                String d[] = jTable1.getValueAt(jTable1.getSelectedRow(), 1).toString().split("##");
                Vector rd = new Vector();
                rd.add("Caption");
                rd.add(d[1]);
                rd.add(jTextArea1.getText());
                rd.add(d[0]);
                r2.add(rd);
                parent.setEnabled(true);
                host.dispose();
                logic.getLogic(r2, new Vector());
                
            }
        });
        
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
    
    public void getQuestions(final JTable lt)
	{
		try
		{
			clearTable(lt);
			//ArrayList<String> list = main.getAccess();
                        ArrayList<String> list = new ArrayList();
                        
			String[] headerStrings = {"Question","."};
			Vector rows2 = new Vector();
			Vector header2 = new Vector();
                        try{
			for (int i = 0; i < headerStrings.length; i++)
				header2.add(headerStrings[i]);
				lt.getTableHeader().setReorderingAllowed(false);
			for (Object q : lx)
                        {
                                Vector v = new Vector();
				String qq[] = q.toString().split("##");
                                String vv = qq[0];
                                v.add(qq[1]);
                                v.add(q);
				rows2.add(v);
			}}catch(Exception er){}
			
			DefaultTableModel mds= new  javax.swing.table.DefaultTableModel(rows2,header2) 
			{
				Class[] types = new Class [] {
					java.lang.String.class,java.lang.String.class
				};
				boolean[] canEdit = new boolean [] {
					false,false
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
			lt.setModel(mds);
			
			
			TableColumn tb=lt.getColumnModel().getColumn(0);
			tb.setPreferredWidth(400);
                        
                       jTable1.getColumnModel().getColumn(1).setMinWidth(0);
                       jTable1.getColumnModel().getColumn(1).setMaxWidth(0);
			
			
		}
		catch(Exception er)
		{
			er.printStackTrace();
		}
	
	
	}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField1 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/jajitech/desktop/xdata/saved/images/search.png"))); // NOI18N

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

        jButton1.setText("Done");

        jLabel2.setText("New Caption");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane2.setViewportView(jTextArea1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel2)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 514, Short.MAX_VALUE)
                                    .addComponent(jTextField1)))
                            .addComponent(jScrollPane2))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
