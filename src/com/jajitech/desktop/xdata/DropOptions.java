/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jajitech.desktop.xdata;

import static com.jajitech.desktop.xdata.constants.Constants.APP_NAME;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
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
public class DropOptions extends javax.swing.JPanel {

    /**
     * Creates new form DropOptions
     */
    JTextArea result;
    
    
    
    public void makeTable(Vector v, Vector header)
    {
        
        
        DefaultTableModel mds= null;
        if(type.equals("check"))
        {
        mds = new  javax.swing.table.DefaultTableModel(v,header) 
        {
                Class[] types = new Class [] {

                        java.lang.Boolean.class ,java.lang.String.class
                };
                boolean[] canEdit = new boolean [] {
                        true, false
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
        }
        else
        {
            mds = new  javax.swing.table.DefaultTableModel(v,header) 
			{
				Class[] types = new Class [] {
                                    
					java.lang.String.class
				};
				boolean[] canEdit = new boolean [] {
					false
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
        }
			jTable1.setModel(mds);
                        
                        if(type.equals("check"))
        {
            TableColumn tb=jTable1.getColumnModel().getColumn(0);
			jTable1.getColumnModel().getColumn(0).setCellRenderer(
				new TableCellRenderer() {
                            // the method gives the component  like whome the cell must be rendered
                            public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean isFocused, int row, int col) {
						boolean marked = (Boolean) value;
						final JCheckBox rendererComponent = new JCheckBox();
                                                rendererComponent.setBorder(new BevelBorder(BevelBorder.RAISED));
						if (marked) {
							rendererComponent.setSelected(true);
						}
						
						rendererComponent.addActionListener(new ActionListener()
						{
							public void actionPerformed(ActionEvent v)
							{
								if(!rendererComponent.isSelected())
								{
									//jall.setSelected(false);
								}
							}
						});
						
						
						return rendererComponent;
					}
				});
                        
                        tb=jTable1.getColumnModel().getColumn(0);
			tb.setPreferredWidth(75);
                        
                        tb=jTable1.getColumnModel().getColumn(1);
			tb.setPreferredWidth(400);
        }
        
        
        
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
    }
    
    String type ="";
    
    public DropOptions(String elementName, JTextArea result, JDialog host, JDialog parent, String type) {
        initComponents();
        clearTable(jTable1);
        this.type = type;
        this.result = result;
        String g[] = elementName.split("##");
        String y[] = g[2].split("@@");
        Vector v = new Vector();
        for(String b: y)
        {
            Vector t = new Vector();
            if(type.equals("check"))
            {
                t.add(false);
            }
            t.add(b);
            v.add(t);
        }
        Vector header = new Vector();
        header.add(" ");
        if(type.equals("check"))
        {
            header.add(" ");
        }
        makeTable(v, header);
        
        
        
        jButton1.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if(jTable1.getSelectedRowCount() < 1)
                {
                    return;
                }
                if(!type.equals("check"))
                {
                    result.setText(jTable1.getValueAt(jTable1.getSelectedRow(), 0).toString());
                }
                else
                {
                    String j = "";
                    for(int x=0; x<jTable1.getRowCount(); x++)
                    {
                        boolean y = (Boolean) jTable1.getValueAt(x, 0);
                        if(y==true)
                        {
                            j = j + jTable1.getValueAt(x, 1).toString()+";; ";
                        }
                    }
                    result.setText(j);
                }
                host.setEnabled(true);
                parent.dispose();
            }
        });
        
        jTable1.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent m)
            {
                if(m.getClickCount() ==2)
                {
                    if(jTable1.getSelectedRowCount() < 1)
                    {
                        return;
                    }
                    if(type.equals("check"))
                    {
                        boolean f = false;
                        for(int x=0; x<jTable1.getRowCount(); x++)
                        {
                             boolean y = (Boolean) jTable1.getValueAt(x, 0);
                             if(y == true)
                             {
                                 f = true;
                             }
                        }
                        if(f==true)
                        {
                            JOptionPane.showMessageDialog(parent, "Please click the OK button", APP_NAME, JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                        else
                        {
                            return;
                        }
                    }
                    else
                    {
                        result.setText(jTable1.getValueAt(jTable1.getSelectedRow(), 0).toString());
                    }
                    host.setEnabled(true);
                    parent.dispose();
                }
            {
        }
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/jajitech/desktop/xdata/saved/images/search.png"))); // NOI18N

        jButton1.setText("OK");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTable1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jScrollPane2.setViewportView(jTable1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 302, Short.MAX_VALUE)
                            .addComponent(jTextField1))))
                .addContainerGap(26, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
