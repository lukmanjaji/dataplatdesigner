/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jajitech.desktop.xdata.ui;

import com.jajitech.desktop.xdata.saved.PropertiesPanel;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.Vector;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

/**
 *
 * @author Usman
 */
public class MatrixEditor extends javax.swing.JPanel {

    /**
     * Creates new form MatrixEditor
     */
    Vector questions = new Vector();
    JFrame parent;
    PropertiesPanel props;
    
    public MatrixEditor(String q, JFrame parent, PropertiesPanel props) {
        initComponents();
        this.parent = parent;
        this.props = props;
        list.setFixedCellHeight(-1);
            list.repaint(); 
        list.setCellRenderer(new CustomCellRenderer());
        ComponentListener l = new ComponentAdapter() {

        @Override
        public void componentResized(ComponentEvent e) {
            // next line possible if list is of type JXList
            // list.invalidateCellSizeCache();
            // for core: force cache invalidation by temporarily setting fixed height
            list.setFixedCellHeight(10);
            list.setFixedCellHeight(-1);
        }

    };

    list.addComponentListener(l);
    
    String h[] = q.split("@@");
    for(String r: h)
    {
        questions.add(getListPanel(r));
    }
    
        list.setListData(questions);
        list.setFixedCellHeight(10);
            
        top.setBorder(new TitledBorder("Edit Questions"));
        bottom.setBorder(new TitledBorder("Constraints"));
        up.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent v)
            {
                swapElementsUp();
                updateMatrix();
            }
        });
        down.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent v)
            {
                swapElementsDown();
                updateMatrix();
            }
        });
        
        mtype.removeAllItems();
        mtype.addItem("Radio Button");
        mtype.addItem("Text Field");
        
        mval.removeAllItems();
        mval.addItem("AT_LEAST_ONE_ROW FILLED");
        mval.addItem("ALL_ROWS_FILLED");
        mval.addItem("NONE");
        addq.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent v)
            {
                Object entry = JOptionPane.showInputDialog(MatrixEditor.this.parent, "Enter New Question", "Add Question", JOptionPane.QUESTION_MESSAGE);
                if(entry == null || entry.equals(""))
                {
                    return;
                }
                questions.add(getListPanel(entry.toString()));
                list.setListData(questions);
                updateMatrix();
            }
        });
        
        editq.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent v)
            {
                if(list.getSelectedIndex() == -1)
                {
                    return;
                }
                Object t = list.getSelectedValue();
                JPanel y = (JPanel) t;
                Component c[] = y.getComponents();
                JMultiLineLabel l = (JMultiLineLabel) c[0];
                Object entry = JOptionPane.showInputDialog(MatrixEditor.this.parent, "Enter New Question", "Add Question", JOptionPane.QUESTION_MESSAGE, null, null, l.getText());
                if(entry == null || entry.equals(""))
                {
                    return;
                }
                
                int index = list.getSelectedIndex();
                questions.setElementAt(getListPanel(entry.toString()), index);
                list.setListData(questions);
                updateMatrix();
            }
        });
        
        deleteq.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent v)
            {
                int x = JOptionPane.showConfirmDialog(MatrixEditor.this.parent, "Remove selected question (s) ?", "Remove Question", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
                if(x == JOptionPane.CANCEL_OPTION || x == JOptionPane.NO_OPTION)
                {
                    return;
                }
                
                int d[] = list.getSelectedIndices();
                for (int i = d.length-1; i >=0; i--) {
                    questions.removeElementAt(d[i]);
                }
                updateMatrix();
            }
        });
        

        mtype.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent v)
            {
                String r = MatrixEditor.this.props.mreq;
                String g[] = r.split("@@");
                MatrixEditor.this.props.mreq = g[0]+"@@"+mtype.getSelectedItem();
                MatrixEditor.this.props.reWriteElement();
            }
        });
        
        mval.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent v)
            {
                String r = MatrixEditor.this.props.mreq;
                String g[] = r.split("@@");
                MatrixEditor.this.props.mreq = mval.getSelectedItem()+"@@"+g[1];
                MatrixEditor.this.props.reWriteElement();
                if(!mval.getSelectedItem().equals("NONE"))
                {
                    MatrixEditor.this.props.required.setSelected(true);
                }
                else
                {
                    MatrixEditor.this.props.required.setSelected(false);
                }
            }
        });
        
        String r = MatrixEditor.this.props.mreq;
        String g[] = r.split("@@");
        mtype.setSelectedItem(g[1].trim());
        mval.setSelectedItem(g[0].trim());
        
    }
    
    public void updateMatrix()
    {
        props.mQuestions = returnQuestions();
        props.reWriteElement();
    }
    
    public String returnQuestions()
    {
        String q = "";
        for(Object t: questions)
        {
            JPanel l = (JPanel) t;
            Component y[] = l.getComponents();
            JMultiLineLabel p = (JMultiLineLabel) y[0];
            if(q.equals(""))
            {
                q = p.getText();
            }
            else
            {
                q = q + "@@" + p.getText();
            }
        }
        return q;
    }
    
    private void swapElementsUp()
    {
        int index = list.getSelectedIndex(); 
        if( index == -1 || index == 0 ) 
        {
            return;
        }
        Object a = list.getSelectedValue();
        Object b = questions.get(index-1);
        questions.setElementAt(b, index);
        questions.setElementAt(a, index-1);
        list.setListData(questions);
        list.setSelectedIndex(index-1);
        list.ensureIndexIsVisible(list.getSelectedIndex());
    }
    
    private void swapElementsDown()
    {
        int index = list.getSelectedIndex(); 
        if( index == questions.size()-1 ) 
        {
            return;
        }
        Object a = list.getSelectedValue();
        Object b = questions.get(index+1);
        questions.setElementAt(b, index);
        questions.setElementAt(a, index+1);
        list.setListData(questions);
        list.setSelectedIndex(index+1);
        list.ensureIndexIsVisible(list.getSelectedIndex());
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        top = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane2 = new javax.swing.JScrollPane();
        list = new javax.swing.JList<>();
        addq = new javax.swing.JButton();
        editq = new javax.swing.JButton();
        deleteq = new javax.swing.JButton();
        down = new javax.swing.JButton();
        up = new javax.swing.JButton();
        bottom = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        mtype = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        mval = new javax.swing.JComboBox<>();

        jScrollPane2.setViewportView(list);

        addq.setText("Add Question");

        editq.setText("Edit Question");

        deleteq.setText("Delete Question");

        down.setText("Move Down");

        up.setText("Move Up");

        javax.swing.GroupLayout topLayout = new javax.swing.GroupLayout(top);
        top.setLayout(topLayout);
        topLayout.setHorizontalGroup(
            topLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(topLayout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(addq)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(editq)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(deleteq)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(topLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(topLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, topLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(up)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(down)))
                .addContainerGap())
        );

        topLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {down, up});

        topLayout.setVerticalGroup(
            topLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(topLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(topLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addq)
                    .addComponent(editq)
                    .addComponent(deleteq))
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(topLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(up)
                    .addComponent(down))
                .addGap(5, 5, 5))
        );

        jLabel2.setText("Matrix Type");

        mtype.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel3.setText("Validator");

        mval.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout bottomLayout = new javax.swing.GroupLayout(bottom);
        bottom.setLayout(bottomLayout);
        bottomLayout.setHorizontalGroup(
            bottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bottomLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(bottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(mtype, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                .addGroup(bottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(mval, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        bottomLayout.setVerticalGroup(
            bottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bottomLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(bottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(bottomLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(mval, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mtype, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(34, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(top, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bottom, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(top, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bottom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addq;
    private javax.swing.JPanel bottom;
    private javax.swing.JButton deleteq;
    private javax.swing.JButton down;
    private javax.swing.JButton editq;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JList<String> list;
    private javax.swing.JComboBox<String> mtype;
    private javax.swing.JComboBox<String> mval;
    private javax.swing.JPanel top;
    private javax.swing.JButton up;
    // End of variables declaration//GEN-END:variables

    public JPanel getListPanel(String text)
    {
        JPanel a = new JPanel();
        a.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
        JMultiLineLabel l = new JMultiLineLabel(text);
        a.setLayout(new BoxLayout(a,BoxLayout.Y_AXIS));
        a.add(l);
        return a;
    }
    
    
class CustomCellRenderer implements ListCellRenderer {
        
   public Component getListCellRendererComponent
    (JList list, Object value, int index,
     boolean isSelected,boolean cellHasFocus) {
     
     JPanel x = (JPanel) value;
     x.setBackground(isSelected ?
            list.getSelectionBackground() : Color.lightGray);
	x.setForeground(isSelected ? Color.orange : list.getForeground());
     
    list.setFixedCellHeight(10);
    list.setFixedCellHeight(-1);
    list.repaint();
    list.revalidate();
        return x;
     }
   }

}
