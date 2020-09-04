/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jajitech.desktop.xdata.start;

import com.jajitech.desktop.xdata.BlurryLabel;
import com.jajitech.desktop.xdata.CreateNew;
import com.jajitech.desktop.xdata.EscapeDialog;
import com.jajitech.desktop.xdata.Main;
import com.jajitech.desktop.xdata.constants.Constants;
import com.jajitech.desktop.xdata.controller.MainDAO;
import com.jajitech.desktop.xdata.pojos.Project;
import com.jajitech.desktop.xdata.saved.SavedProject;
import com.jajitech.desktop.xdata.waiter.Waiter;
import com.jajitech.desktop.xdata.xml.JAJFileWriter;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.Date;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

/**
 *
 * @author Jaji
 */
public class NewProject extends EscapeDialog {
    
    Constants constants = new Constants();
    Waiter waiter = new Waiter();
    MainDAO main = new MainDAO();
    int pid = 0;
    JFrame parent = null;
    JTextField text;
    
    public NewProject(final JFrame parent, final JDesktopPane desk)
    {
        this.parent = parent;
        File f = new File(System.getProperty("user.home")+"\\"+Constants.APP_NAME+" Projects");
        if(!f.exists())
        {
            f.mkdir();
        }
        final EscapeDialog d= new EscapeDialog(parent, true);
	d.setTitle("New Project");
	d.setResizable(false);
	d.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
	d.addWindowListener(new WindowAdapter(){
	public void windowClosing(WindowEvent e){parent.setEnabled(true);d.dispose();}});
        
	JPanel jc1 = new JPanel (new FlowLayout(FlowLayout.RIGHT));
        jc1.setBorder(Main.etched);
        //jc1.setBackground(Color.white);
        BlurryLabel ll= new BlurryLabel();
        ll.setText("New Project");
              
        ll.setFont(ll.getFont().deriveFont(18f));
        ll.setDrawBlur(true);
        ll.setBorder(new EmptyBorder(0, 5, 2, 5));
        ll.setIcon(new ImageIcon(getClass().getResource("images/new32.png")));
        jc1.add(ll);
        d.getContentPane().add("North",jc1);
        
        CreateNew c = new CreateNew();
        text = c.jTextField1;
        JPanel a = new JPanel();
        String[] targetItems = {"All", "Web", "Mobile"};

        //construct components
        JLabel jcomp1 = new JLabel ("Project Name");
        final JTextField name = new JTextField (5);
        JLabel jcomp3 = new JLabel ("Project ID");
        final JTextField location = new JTextField (5);
        String uniqueID = constants.randomNumeric(8);
        location.setText(uniqueID);
        location.setEditable(false);
        location.setEditable(false);
        JButton browse = new JButton ("...");
        JLabel jcomp6 = new JLabel ("Target Platform");
        final JList target = new JList (targetItems);
        target.setSelectedIndex(0);
        JPanel info = new JPanel ();
        JLabel des = new JLabel("You can change target platforms in the project properties");
        info.add(des);
        info.setBorder(new TitledBorder("Note"));
        JButton cancel = c.jButton1;
        JButton finish = c.jButton2;
        
        text.addKeyListener(new KeyAdapter()
        {
            public void keyPressed(KeyEvent v)
            {
                System.out.println(v.getKeyCode());
                if(v.getKeyCode() == 10)
                {
                    finishCreate(location.getText(), d, desk);
                }
            }
        });
        
        finish.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent v)
            {
                finishCreate(location.getText(), d, desk);
                
            }
        });
        
        cancel.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent v)
            {
                parent.setEnabled(true);
                d.dispose();
            }
        });
        

        //adjust size and set layout
        a.setPreferredSize (new Dimension (439, 270));
        a.setLayout (null);

        //add components
        a.add (jcomp1);
        a.add (name);
        a.add (jcomp3);
        a.add (location);
        //a.add (browse);
        a.add (jcomp6);
        a.add (target);
        a.add (info);
        a.add (cancel);
        a.add (finish);

        //set component bounds (only needed by Absolute Positioning)
        jcomp1.setBounds (10, 12, 105, 20);
        name.setBounds (115, 10, 320, 25);
        jcomp3.setBounds (10, 40, 100, 20);
        location.setBounds (115, 37, 320, 25);
        //browse.setBounds (402, 37, 31, 25);
        jcomp6.setBounds (10, 65, 105, 20);
        target.setBounds (115, 65, 320, 90);
        info.setBounds (10, 165, 425, 70);
        cancel.setBounds (335, 240, 100, 30);
        finish.setBounds (230, 240, 100, 30);
        
        d.getContentPane().add("Center",c);
        d.pack();
        d.setLocationRelativeTo(this);
        d.setVisible(true);
    }
    
    public void finishCreate(String location, JDialog d, JDesktopPane desk)
    {
        if(text.getText().length() < 1)
                {
                    constants.alert(parent, "warn", "Please enter a project name");
                    return;
                }
                Vector r = main.getSavedProjects();
                for(Object y: r)
                {
                    if(y.toString().equalsIgnoreCase(text.getText()))
                    {
                        constants.alert(parent, "error", "Project already exisits...");
                        return;
                    } 
                }
                
                
                final Project p = new Project();
                p.setName(text.getText());
                p.setPid(location);
                p.setProject_date(new Date().toGMTString());
                p.setTarget("All");
                String txt = text.getText();
                Thread tt = new Thread(new Runnable()
                {
                    public void run()
                    {
                        parent.setEnabled(true);
                        d.dispose();
                        waiter.initWaiter();
                        waiter.setText("Saving project...");
                        String pi = main.saveProject(p); 
                        waiter.setText("Opening...");
                        waiter.dispose();
                        openSaved(txt, desk);
                        
                    }});
                tt.start();
    }
    
    public boolean saveNewProject(String name, String location, String platform)
    {
       JAJFileWriter writer = new JAJFileWriter();
       boolean b = writer.saveNewProject(name, location, platform);
       if(b==false)
       {
           new Constants().alert(null, "error", "Error creating new project. Check that you have write access to the project location or project already exists.");
       }
       return b;
    }
    
    public void openSaved(String id, JDesktopPane desk)
    {
       try
       {
       SavedProject saved = new SavedProject(id, parent);
       saved.open();
       desk.add(saved);
       saved.setVisible(true);
       saved.show();
      
           saved.setMaximum(true);
       }
       catch(Exception e){e.printStackTrace();}
    }
    
}
