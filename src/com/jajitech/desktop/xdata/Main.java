/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jajitech.desktop.xdata;

import com.jajitech.desktop.xdata.constants.Constants;
import com.jajitech.desktop.xdata.controller.MainDAO;
import com.jajitech.desktop.xdata.saved.SavedProject;
import com.jajitech.desktop.xdata.start.NewProject;
import com.jajitech.desktop.xdata.templates.TemplateMain;
import com.jajitech.desktop.xdata.ui.UIElementsReader;
import com.jajitech.desktop.xdata.waiter.Waiter;
import com.l2fprod.common.swing.JDirectoryChooser;
import com.nitido.utils.toaster.Toaster;
import de.javasoft.plaf.synthetica.SyntheticaLookAndFeel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JWindow;
import javax.swing.KeyStroke;
import javax.swing.ListCellRenderer;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import org.apache.commons.io.FilenameUtils;
import org.xeustechnologies.jtar.TarEntry;
import org.xeustechnologies.jtar.TarInputStream;

/**
 *
 * @author Jaji
 */
public class Main extends JFrame {
    
    JDesktopPane desk;
    public static EtchedBorder etched=new EtchedBorder(EtchedBorder.LOWERED);
    Constants constants = new Constants();
    Waiter waiter = new Waiter();
    public static BlurryLabel wlabel;
    MainDAO main = new MainDAO();
    public Vector vlist = null;
    JList virtualList = new JList();
    public JLabel msg;
    SavedProject saved;
    Toaster toast = new Toaster();
    JTextField status,user;
    GregorianCalendar calendar,calendar1;
    boolean connect=false;
    Date date;
    String strdate, stime;
    Open open;
    JTable openTable;
    JButton openButton;
    JTextField openText;
    TemplateMain templateWindow;
    
    
    
    public static void showSplash()
    {
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        JWindow w = new JWindow();
        JLabel lbImage    = new JLabel (new ImageIcon (Main.class.getResource("images/splash.png")));
        Color cl = new Color (0, 0, 0);
        lbImage.setBorder (new LineBorder (cl, 1));
        w.getContentPane().add (lbImage, BorderLayout.CENTER);
        w.pack();
        w.setSize (w.getSize().width, w.getSize().height);
        w.setLocation (d.width / 2 - w.getWidth() / 2, d.height / 2 - w.getHeight() / 2);
        for (int i = 0; i <= 1000; i++) { }
        
        w.setVisible(true);
        for (int i = 0; i <= 1000; i++) { }
        Thread tt = new Thread(new Runnable()
        {
            public void run()
            {
                w.setVisible(true);
                w.toFront();
                try{Thread.sleep(4000);}catch(Exception er){}
                w.dispose();
            }
        });
        tt.start();
    }
    

    public static void main(String[] args)
    {
        
        showSplash();
        try{Thread.sleep(3000);}catch(Exception er){}
        Toolkit.getDefaultToolkit().getSystemEventQueue().push(new MyEventQueue()); 
        String output = "";
        try{
	FileInputStream fis = new FileInputStream("skin.jaj");
	BufferedReader br = new BufferedReader(new InputStreamReader(fis));
	String line = null;
	while ((line = br.readLine()) != null) {
		output = output+line;
	}

	br.close();
        }catch(Exception er){output = "system"; try{FileWriter r = new FileWriter("skin.jaj");r.write(output);r.close();}catch(Exception e){}}
        
        
        
        
       try {
       //com.l2fprod.common.swing.plaf.aqua.AquaLookAndFeelAddons com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel
            if (output.equals("nimbus"))
            {
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
            }
            
            if (output.equals("goodies"))
            {
                UIManager.setLookAndFeel("com.jgoodies.looks.plastic.Plastic3DLookAndFeel");
            }
            
            if ( output.equals("system"))
            {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            }
            
            if ( output.equals("synthetica"))
            {
                SyntheticaLookAndFeel.setWindowsDecorated(true);
                UIManager.setLookAndFeel("de.javasoft.plaf.synthetica.SyntheticaBlueLightLookAndFeel");
            }

        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {

        }
       File f =new File("projects/");
       if(!f.exists())
       {
           f.mkdir();
       }
       f = new File("temp/");
       if(!f.exists())
       {
           f.mkdir();
       }
       f = new File("projects/temp/");
       if(!f.exists())
       {
           f.mkdir();
       }
       f = new File("server.jaj");
       if(!f.exists())
       {
           try{FileWriter fr = new FileWriter("server.jaj");
           fr.write("default####default");
           fr.close();}catch(Exception er){}
       }
       SwingUtilities.invokeLater(new Runnable() {
           public void run()
           {
       Main main = new Main();
           }});
       //main.setUndecorated(false);
    }
    
    public Main()
    {
        super(Constants.APP_NAME);
	//try{UIManager.put("PopupMenuUI","CustomPopupMenuUI");}catch(Exception y){}
	setIconImage(new ImageIcon(getClass().getResource("images/icon.png")).getImage());
        setSize(850,600);
	//setExtendedState(JFrame.MAXIMIZED_BOTH);
	setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	addWindowListener(new WindowAdapter(){public void windowClosing(WindowEvent e){UnloadWindow();}});
        makeMenuBar(); 
	//makeToolBar();
        desk= new JDesktopPane();
	desk.setBackground(Color.darkGray);
	getContentPane().add("Center",desk);
        getContentPane().add("South",getStatusBar());
        setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
        setVisible(true);
        vlist = new Vector();
        toast = new Toaster();
        //toast.setMessageColor(Color.blue.darker());
			//toast.setToasterColor(Color.white);
			//toast.setDisplayTime(5000);
			//toast.setToasterHeight(35);
			//toast.setToasterWidth(200);
        doDB();
    }
    
    
    
    
    public JPanel getStatusBar()
    {
        JPanel x= new JPanel(new FlowLayout(FlowLayout.RIGHT));
        x.setBorder(new BevelBorder(BevelBorder.LOWERED));
        JPanel m= new JPanel();
        status = new JTextField (5);
        status.setBackground(Color.red);
        status.setText("Connecting...");
        status.setForeground(Color.magenta.darker());
        status.setHorizontalAlignment(0);
        status.setForeground(Color.white);
        user = new JTextField (5);
        user.setHorizontalAlignment(0);
        final JTextField time = new JTextField (5);
        final JTextField dat = new JTextField (5);
        status.setEditable(false);
        status.setBorder(new BevelBorder(BevelBorder.LOWERED));
        user.setEditable(false);
        user.setBorder(new BevelBorder(BevelBorder.LOWERED));
        user.setText("");
        time.setEditable(false);
        time.setBorder(new BevelBorder(BevelBorder.LOWERED));
        dat.setEditable(false);
        dat.setBorder(new BevelBorder(BevelBorder.LOWERED));
        //////////////////Date and Time//////////////////////
        ActionListener al = new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
                Thread tt = new Thread(new Runnable()
                {
                    public void run()
                    {
                        try
                {
                    String results = main.doHttpUrlConnectionAction(Constants.URL);
                    if(results.trim().equals("jaji"))
                    {
                        status.setBackground(Color.green.darker());
                        status.setText("Connected...");
                    }
                    else
                    {
                         status.setBackground(Color.red);
                         status.setText("Connecting...");
                    }
                }
                catch(Exception er)
                {
                    //er.printStackTrace();
                    status.setBackground(Color.red);
                    status.setText("Connecting...");
                }
                    }
                });
                tt.start();
                
                try
                {
                    File d = new File("account.jaj");
                    if(d.exists())
                    {
                        String f[] = main.readFile("account.jaj").split("##");
                        user.setText(f[0].trim());
                    }
                    else
                    {
                        user.setText("<Not Signed In>");
                    }
                }catch(Exception er){}
            }
        };
                                    javax.swing.Timer timer = new javax.swing.Timer(8000,al);
                                    timer.start();
            m.setPreferredSize (new Dimension (564, 15));
            m.setLayout (null);
            m.add (status);
            m.add (user);
            //m.add (time);
            status.setBounds (460, 1, 100, 15);
            user.setBounds (279, 1, 180, 15);
            //time.setBounds (205, 0, 70, 15);
            //dat.setBounds (140, 0, 60, 15);
            x.add(m);
            return x;
    }
    
    
    public void doDB()
    {
        main.doDB();
    }
    
    public void makeMenuBar()
    {
        JMenuBar mb= new JMenuBar();
	JMenu mn= new JMenu("File");
        JMenu tm = new JMenu("New");
        JMenuItem m1 = new JMenuItem("New Blank", new ImageIcon(getClass().getResource("images/new16.png")));
        m1.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent v)
        {
            NewProject p = new NewProject(Main.this, desk);
        }
        });
        m1.setAccelerator (KeyStroke.getKeyStroke(KeyEvent.VK_N, Event.CTRL_MASK));
        tm.add(m1);
        JMenuItem m11 = new JMenuItem("New from Template", new ImageIcon(getClass().getResource("images/template16.png")));
        m11.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent v)
        {
            try{
            if(templateWindow.isVisible() == true)
            {
                templateWindow.toFront();
                return;
            }}catch(Exception er){}
            templateWindow = new TemplateMain(Main.this);
            templateWindow.setVisible(true);
        }
        });
        m11.setAccelerator (KeyStroke.getKeyStroke(KeyEvent.VK_P, Event.CTRL_MASK));
        tm.add(m11);
        
        JMenuItem m2 = new JMenuItem("Open Project", new ImageIcon(getClass().getResource("images/open16.png")));
        m2.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent v)
        {
            Thread t = new Thread(new Runnable()
            {
                public void run()
                {
                    String file = "";
                    //openSavedDialog();
                    openSavedDialog2();
                    //SavedProject saved = new SavedProject(new File(file));
                    //saved.open();
                    //desk.add(saved);
                    //saved.setVisible(true);
                    //saved.show();
                    try
                    {
                        //saved.setMaximum(true);
                    }
                    catch(Exception e){}
                             }
            });
            t.start();
        }
        });
        m2.setAccelerator (KeyStroke.getKeyStroke(KeyEvent.VK_O, Event.CTRL_MASK));
        JMenuItem m3a = new JMenuItem("Import Project", new ImageIcon(getClass().getResource("images/import.png")));
        m3a.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent v)
            {
                importProject();
            }
        });
        m3a.setAccelerator (KeyStroke.getKeyStroke(KeyEvent.VK_I, Event.CTRL_MASK));
        JMenuItem m3aa = new JMenuItem("Section Viewer", new ImageIcon(getClass().getResource("images/16file.png")));
        m3aa.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent v)
            {
                sectionViewer();
            }
        });
        m3aa.setAccelerator (KeyStroke.getKeyStroke(KeyEvent.VK_E, Event.CTRL_MASK));
        
        JMenuItem m3 = new JMenuItem("Settings", new ImageIcon(getClass().getResource("images/settings16.png")));
        m3.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent v)
            {
                settings();
            }
        });
        m3.setAccelerator (KeyStroke.getKeyStroke(KeyEvent.VK_T, Event.CTRL_MASK));
        JMenuItem m4 = new JMenuItem("Help Topics", new ImageIcon(getClass().getResource("images/help16.png")));
        m4.setAccelerator (KeyStroke.getKeyStroke(KeyEvent.VK_H, Event.CTRL_MASK));
        JMenuItem m5 = new JMenuItem("Exit", new ImageIcon(getClass().getResource("images/exit16.png")));
        m5.setAccelerator (KeyStroke.getKeyStroke(KeyEvent.VK_Q, Event.CTRL_MASK));
        
        JMenuItem m6 = new JMenuItem("Sign in", new ImageIcon(getClass().getResource("images/signin16.png")));
        m6.setAccelerator (KeyStroke.getKeyStroke(KeyEvent.VK_S, Event.CTRL_MASK));
        m6.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent v)
            {
                signIn();
            }
        });
        
        m5.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent v)
            {
                UnloadWindow();
            }
        });
        
        
        JMenu nm = new JMenu("Look & Feel");
            JRadioButton nm1 = new JRadioButton("System");
            nm.add(nm1);
            JRadioButton nm2 = new JRadioButton("Synthetica");
            nm.add(nm2);
            JRadioButton nm3 = new JRadioButton("Nimbus");
            nm.add(nm3);
            JRadioButton nm3a = new JRadioButton("Plastic");
            nm.add(nm3a);
            ButtonGroup bg = new ButtonGroup();
            nm.add(nm1);nm.add(nm2);nm.add(nm3);
            String output="";
            try{
	FileInputStream fis = new FileInputStream("skin.jaj");
	BufferedReader br = new BufferedReader(new InputStreamReader(fis));
	String line = null;
	while ((line = br.readLine()) != null) {
		output = output+line;
	}

	br.close();
        }catch(Exception er){}
            if(output.equals("system"))
            {
                nm1.setSelected(true);
            }
            if(output.equals("synthetica"))
            {
                nm2.setSelected(true);
            }
            if(output.equals("nimbus"))
            {
                nm3.setSelected(true);
            }
            if(output.equals("goodies"))
            {
                nm3a.setSelected(true);
            }
            
            nm1.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent v)
                {
                    try
                    {
                        FileWriter f = new FileWriter("skin.jaj");
                        f.write("system");
                        f.close();
                        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                        SwingUtilities.updateComponentTreeUI(Main.this);
                    }catch(Exception er){}
                }
            });
            
            nm3a.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent v)
                {
                    try
                    {
                        FileWriter f = new FileWriter("skin.jaj");
                        f.write("goodies");
                        f.close();
                        UIManager.setLookAndFeel("com.jgoodies.looks.plastic.Plastic3DLookAndFeel");
                        SwingUtilities.updateComponentTreeUI(Main.this);
                    }catch(Exception er){}
                }
            });
            
            
            
            
            
            
            
            nm2.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent v)
                {
                    try {
                        
                        FileWriter f = new FileWriter("skin.jaj");
                        f.write("synthetica");
                        f.close();
                        
                            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                            if ("Nimbus".equals(info.getName()))
                            {
                               SyntheticaLookAndFeel.setWindowsDecorated(true);
                               UIManager.setLookAndFeel("de.javasoft.plaf.synthetica.SyntheticaBlueLightLookAndFeel");
                               setDefaultLookAndFeelDecorated(true);
                               SwingUtilities.updateComponentTreeUI(Main.this);
                               break;
                            }
        }} catch (IOException | ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {

        }
                }
            });
            
            nm3.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent v)
                {
                    try {
        for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
        if ("Nimbus".equals(info.getName()))
        {
            FileWriter f = new FileWriter("skin.jaj");
                        f.write("nimbus");
                        f.close();
                        
            UIManager.setLookAndFeel(info.getClassName());
            SwingUtilities.updateComponentTreeUI(Main.this);
            break;
        }
        //setDefaultLookAndFeelDecorated(true);
        }} catch (IOException | ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {

        }
                }
            });
            
            
            
            
            
            JMenu set = new JMenu("Setup");
            //set.add(m6);
            set.add(m3);
            
            JMenu help = new JMenu("Help");
            JMenuItem ab = new JMenuItem("About", new ImageIcon(getClass().getResource("images/about.png")));
            ab.setAccelerator (KeyStroke.getKeyStroke(KeyEvent.VK_B, Event.CTRL_MASK));
            ab.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent v)
                {
                    final EscapeDialog  d = new EscapeDialog(Main.this, true);
                    d.setUndecorated(true);
                    JLabel l = new JLabel();
                    l.setIcon(new ImageIcon(getClass().getResource("images/splash.png")));
                    l.addMouseListener(new MouseAdapter()
                    {
                        public void mouseClicked(MouseEvent v)
                        {
                            setEnabled(true);
                            d.dispose();
                        }
                    });
                    d.getContentPane().add("Center", l);
                    d.pack();
                    d.setLocationRelativeTo(Main.this);
                    d.setVisible(true);
                    
                }
            });
            help.add(ab);
            help.add(m4);
            
        
        mn.add(m1);
        mn.add(m11);
        mn.addSeparator();
        mn.add(m2);
        mn.addSeparator();
        mn.add(m3a);
        mn.add(m3aa);
        mn.addSeparator();
        //mn.add(m3);
       // mn.addSeparator();
        mn.add(nm);
        mn.addSeparator();
        //mn.add(m6);
        //mn.add(m4);
        mn.add(m5);
        mb.add(mn);
        mb.add(set);
        mb.add(help);
        setJMenuBar(mb);
    }
    
    public void sectionViewer()
    {
        final String random = constants.randomNumeric(11);
        final EscapeDialog d= new EscapeDialog(this,true);
	d.setTitle("Section Viewer");
	d.setResizable(false);
	d.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
	d.addWindowListener(new WindowAdapter(){
	public void windowClosing(WindowEvent e){setEnabled(true);d.dispose();main.flushTemp();
        }});
        
        SectionViewer viewer = new SectionViewer();
        JList designer = viewer.jList1;
        designer.setListData(new Vector());
        designer.setCellRenderer(new CustomCellRenderer());
        final JPanel mainDesigner = new JPanel();
        JPanel a = new JPanel();
        JButton b = viewer.jButton1;
        b.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent v)
            {

            Thread t = new Thread(new Runnable()
            {
                public void run()
                {
                try
                {
                    JFileChooser atfile= new JFileChooser("My Documents");
                    FileNameExtensionFilter filter = new FileNameExtensionFilter("SECTION FILES", "section", "section");
                    atfile.setFileFilter(filter);
                    atfile.setApproveButtonText("OK");
                    atfile.setDialogTitle("Import Section");
                    int i=atfile.showOpenDialog(Main.this);
                    java.io.File file1 = atfile.getSelectedFile();
                    String s = file1.getPath();
                    if(i==1)
                    {
			return;
                    }
                    else
                    {
                        String folder = constants.randomNumeric(11);
                        String folder2 = FilenameUtils.removeExtension(new File(s).getName());
                        File f = new File("temp/"+folder2);
                        f.mkdir();
                        TarInputStream tis = new TarInputStream(new BufferedInputStream(new FileInputStream(s)));
                        TarEntry entry;
                        while((entry = tis.getNextEntry()) != null)
                        {
                            int count;
                            byte data[] = new byte[2048];
                            FileOutputStream fos = new FileOutputStream("temp/"+entry.getName());
                            BufferedOutputStream dest = new BufferedOutputStream(fos);
                            while((count = tis.read(data)) != -1)
                            {
                                dest.write(data, 0, count);
                            }
                            dest.flush();
                            dest.close();
                        }
                        tis.close();
                        UIElementsReader r = new UIElementsReader(null,null, new Vector());
                        String ss = "temp/"+folder2+"/sectionData.jaj";
                        r.file = new File(ss);
                        //r.load(mainDesigner);
                        r.load(designer);
                        f.delete();
                    }
                }catch(Exception e){e.printStackTrace();}
                }});t.start();
            }
        });
        a.add(b);
       // d.getContentPane().add("North",a);
       
        mainDesigner.setBackground(Color.white);
        JScrollPane designScroller = new JScrollPane(mainDesigner);
        SavedProject.Drawer dr = new SavedProject.Drawer(designer);
        designer.addMouseListener(dr);
	designer.addMouseMotionListener(dr);
        
        d.getContentPane().add("Center",viewer);
        d.pack();
        d.setLocationRelativeTo(this);
        d.setVisible(true);       
    }
    
    
    class CustomCellRenderer implements ListCellRenderer {
        
   public Component getListCellRendererComponent
    (JList list, Object value, int index,
     boolean isSelected,boolean cellHasFocus) {
     
     JPanel x = (JPanel) value;
     
     //x.setLayout(new FlowLayout(FlowLayout.LEFT));
     Component component = (Component)value;
     component.setBackground(isSelected ?
            list.getSelectionBackground() : list.getBackground());
	component.setForeground(isSelected ? Color.orange : list.getForeground());
     return component;
     }
   }
    
    public JPanel getDesignerPanel(JScrollPane main)
    {
        JPanel a = new JPanel();
        main.setBackground(Color.white);
        JLabel nexus = new JLabel (new ImageIcon(getClass().getResource("images/nexus.png")));
        a.setPreferredSize (new Dimension (385, 631));
        a.setLayout (null);
        a.add (main);
        a.add (nexus);
        main.setBounds (42, 107, 305, 425);
        nexus.setBounds (5, 5, 377, 620);
        return a;
    }
    
    public void signIn()
    {
        setEnabled(false);
        final JDialog d= new JDialog(this);
	d.setTitle("Sign In");
	d.setResizable(false);
	d.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
	d.addWindowListener(new WindowAdapter(){
	public void windowClosing(WindowEvent e){setEnabled(true);d.dispose();}});
        JPanel jc1 = new JPanel (new FlowLayout(FlowLayout.RIGHT));
        jc1.setBorder(Main.etched);
        //jc1.setBackground(Color.white);
        BlurryLabel ll= new BlurryLabel();
        ll.setText("Sign In");
              
        ll.setFont(ll.getFont().deriveFont(18f));
        ll.setDrawBlur(true);
        ll.setBorder(new EmptyBorder(0, 5, 2, 5));
        ll.setIcon(new ImageIcon(getClass().getResource("images/signin32.png")));
        jc1.add(ll);
        d.getContentPane().add("North",jc1);
        JPanel a = new JPanel();
        JLabel jcomp1 = new JLabel ("Email");
        final JTextField jcomp2 = new JTextField (5);
        JLabel jcomp3 = new JLabel ("Password");
        final JPasswordField jcomp4 = new JPasswordField (5);
        JButton jcomp5 = new JButton ("Cancel");
        jcomp5.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent v)
            {
                setEnabled(true);
                d.dispose();
            }
        });
        try
        {
            String file[] = main.readFile("account.jaj").split("##");
            jcomp2.setText(file[0].trim());
            jcomp4.setText(file[1].trim());
        }catch(Exception er){}
        JButton jcomp6 = new JButton ("Sign in");
        jcomp6.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent v)
            {
                if(jcomp2.getText().length()==0 || jcomp4.getText().length()==0)
                {
                    constants.alert(Main.this, "warn", "Please enter your email and password");
                    return;
                }
                try
                {
                    FileWriter f = new FileWriter("account.jaj");
                    f.write(jcomp2.getText()+"##"+jcomp4.getText());
                    f.flush();
                    f.close();
                    setEnabled(true);
                    d.dispose();  
                }catch(Exception er){}
            }
        });
        a.setPreferredSize (new Dimension (293, 136));
        a.setLayout (null);
        a.add (jcomp1);
        a.add (jcomp2);
        a.add (jcomp3);
        a.add (jcomp4);
        a.add (jcomp5);
        a.add (jcomp6);
        jcomp1.setBounds (10, 10, 40, 15);
        jcomp2.setBounds (10, 25, 280, 25);
        jcomp3.setBounds (10, 55, 100, 20);
        jcomp4.setBounds (10, 75, 280, 25);
        jcomp5.setBounds (190, 105, 100, 30);
        jcomp6.setBounds (87, 105, 100, 30);
        d.getContentPane().add("Center",a);
        d.pack();
        d.setLocationRelativeTo(null);
        d.setVisible(true);
    }
    
    
    public void importProject()
    {
        Thread t = new Thread(new Runnable()
        {
            public void run()
            {
                try
                {
                    JFileChooser atfile= new JFileChooser("My Documents");
                    FileNameExtensionFilter filter = new FileNameExtensionFilter("JAJX FILES", "jajx", "jajx");
                    atfile.setFileFilter(filter);
                    atfile.setApproveButtonText("OK");
                    atfile.setDialogTitle("Import Project");
                    int i=atfile.showOpenDialog(Main.this);
                    java.io.File file1 = atfile.getSelectedFile();
                    String s = file1.getPath();
                    if(i==1)
                    {
			return;
                    }
                    else
                    {
                        String destFolder = "projects/";
                        File zf = new File( s );
                        TarInputStream tis = new TarInputStream( new BufferedInputStream( new FileInputStream( zf ) ) );
                        untar( tis, destFolder );
                    }
                    toast.showToaster(new ImageIcon(getClass().getResource("images/icon.png")),"Project imported...");
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }
    
    private void untar(TarInputStream tis, String destFolder) throws IOException {
        BufferedOutputStream dest = null;
        int x=0;
        TarEntry entry;
        while(( entry = tis.getNextEntry() ) != null) {
            System.out.println( "Extracting: " + entry.getName() );
            int count;
            int BUFFER = 2048;
            byte data[] = new byte[BUFFER];

            if( entry.isDirectory() ) {
                System.out.println(x);
                if(x > 0)
                {
                new File( destFolder + "/" + entry.getName() ).mkdirs();
                x++;
                }                    
                continue;
            } else {
                int di = entry.getName().lastIndexOf( '/' );
                if( di != -1 ) {
                    new File( destFolder + "/" + entry.getName().substring( 0, di ) ).mkdirs();
                }
            }

            FileOutputStream fos = new FileOutputStream( destFolder + "/" + entry.getName() );
            dest = new BufferedOutputStream( fos );

            while(( count = tis.read( data ) ) != -1) {
                dest.write( data, 0, count );
            }

            dest.flush();
            dest.close();
        }
    }
    
    
    public void openSavedDialog2()
    {
       try
       {
           if(saved.isVisible())
           {
               constants.alert(this, "warn", "Please close the existing project before opening a new one");
               return;
           }
       }catch(Exception e){}
       
        final EscapeDialog d= new EscapeDialog(this, true);
	d.setTitle("Open Project");
	d.setResizable(false);
	d.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
	d.addWindowListener(new WindowAdapter(){
	public void windowClosing(WindowEvent e){setEnabled(true);d.dispose();}});
        JPanel jc1 = new JPanel (new FlowLayout(FlowLayout.RIGHT));
        jc1.setBorder(Main.etched);
        //jc1.setBackground(Color.white);
        BlurryLabel ll= new BlurryLabel();
        ll.setText("Open Project");
              
        ll.setFont(ll.getFont().deriveFont(18f));
        ll.setDrawBlur(true);
        ll.setBorder(new EmptyBorder(0, 5, 2, 5));
        ll.setIcon(new ImageIcon(getClass().getResource("images/open32.png")));
        jc1.add(ll);
        d.getContentPane().add("North",jc1);
        
        open = new Open();
        openTable = open.jTable1;
        openButton = open.jButton1;
        openText = open.jTextField1;
        openButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent v)
            {
                setEnabled(true);
                            d.dispose();
                            waiter.initWaiter();
                            waiter.setText("Opening project...");
                            openSaved(openTable.getValueAt(openTable.getSelectedRow(), 0).toString());
                            waiter.dispose();
            }
        });
        
        openTable.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent v)
            {
                if(v.getClickCount() == 2)
                {
                    setEnabled(true);
                            d.dispose();
                            waiter.initWaiter();
                            waiter.setText("Opening project...");
                            openSaved(openTable.getValueAt(openTable.getSelectedRow(), 0).toString());
                            waiter.dispose();
                }
            }
        });
        
        loadProjects();
        d.getContentPane().add("Center", open);
        d.pack();
        d.setLocationRelativeTo(this);
        d.setVisible(true);
    }
    
    public void loadProjects()
    {
        Vector rows2 = new Vector();
        Vector header2 = new Vector();
        String[] headerStrings = {"Project","Last Modified"};
        rows2 = new Vector();
        header2 = new Vector();
        for (int i = 0; i < headerStrings.length; i++)
        {
                header2.add(headerStrings[i]);
        }
        openTable.getTableHeader().setReorderingAllowed(false);
        Vector p = main.getSavedProjects();
        for(Object project: p)
        {
            Vector r = new Vector();
            r.add(project);
            File f = new File("projects/"+project);
            SimpleDateFormat m = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            r.add(m.format(f.lastModified()));
            rows2.add(r);
        }
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
			openTable.setModel(mds);
			
			
			TableColumn tb=openTable.getColumnModel().getColumn(0);
			tb.setPreferredWidth(200);
                        
			tb=openTable.getColumnModel().getColumn(1);
			tb.setPreferredWidth(100);
                        
                        TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(openTable.getModel());
                        openTable.setRowSorter(sorter);
                        openText.getDocument().addDocumentListener(new DocumentListener(){

                        @Override
                        public void insertUpdate(DocumentEvent e) {
                            String text = openText.getText();

                            if (text.trim().length() == 0) {
                                sorter.setRowFilter(null);
                            } else {
                                sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                            }
                        }

                        @Override
                        public void removeUpdate(DocumentEvent e) {
                            String text = openText.getText();

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
    
    
    public void settings()
    {
        final EscapeDialog d= new EscapeDialog(this, true);
	d.setTitle("Settings");
	d.setResizable(false);
	d.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
	
        JPanel jc1 = new JPanel (new FlowLayout(FlowLayout.RIGHT));
        jc1.setBorder(Main.etched);
        //jc1.setBackground(Color.white);
        BlurryLabel ll= new BlurryLabel();
        ll.setText("Settings");
              
        ll.setFont(ll.getFont().deriveFont(18f));
        ll.setDrawBlur(true);
        ll.setBorder(new EmptyBorder(0, 5, 2, 5));
        ll.setIcon(new ImageIcon(getClass().getResource("images/settings32.png")));
        jc1.add(ll);
        d.getContentPane().add("North",jc1);
        
        SettingsForm f = new SettingsForm();
        
        final JTextField ls = f.ls;
        final JTextField fl = f.fl;
        final JTextField email = f.email;
        final JPasswordField password = f.password;
        
        try
        {
            String file[] = main.readFile("account.jaj").split("##");
            email.setText(file[0].trim());
            password.setText(file[1].trim());
            
            file = main.readFile("server.jaj").split("####");
            ls.setText(file[0]);
            fl.setText(file[1]);
            
            
        }catch(Exception er){}
        
        d.addWindowListener(new WindowAdapter(){
	public void windowClosing(WindowEvent e){
            try
                {
                    FileWriter f = new FileWriter("account.jaj");
                    f.write(email.getText()+"##"+password.getText());
                    f.flush();
                    f.close();
                    
                    f = new FileWriter("server.jaj");
                    f.write(ls.getText()+"####"+fl.getText());
                    f.flush();
                    f.close();
                    Constants.LS_SERVER = ls.getText();
                    Constants.FL_SERVER = fl.getText();
                    setEnabled(true);
                    d.dispose();  
                }catch(Exception er){}
            setEnabled(true);d.dispose();}});
        
        
        d.getContentPane().add("Center", f);
        d.pack();
        d.setLocationRelativeTo(this);
        d.setVisible(true);
    }
    
    
    public void openSavedDialog()
    {
        
        try
       {
           if(saved.isVisible())
           {
               constants.alert(this, "warn", "Please close the existing project before opening a new one");
               return;
           }
       }catch(Exception e){}
        
        
        setEnabled(false);
        final JDialog d= new JDialog(this);
	d.setTitle("Open Project");
	d.setResizable(false);
	d.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
	d.addWindowListener(new WindowAdapter(){
	public void windowClosing(WindowEvent e){setEnabled(true);d.dispose();}});
        JPanel jc1 = new JPanel (new FlowLayout(FlowLayout.RIGHT));
        jc1.setBorder(Main.etched);
        //jc1.setBackground(Color.white);
        BlurryLabel ll= new BlurryLabel();
        ll.setText("Open Project");
              
        ll.setFont(ll.getFont().deriveFont(18f));
        ll.setDrawBlur(true);
        ll.setBorder(new EmptyBorder(0, 5, 2, 5));
        ll.setIcon(new ImageIcon(getClass().getResource("images/open32.png")));
        jc1.add(ll);
        d.getContentPane().add("North",jc1);
        
        JPanel a = new JPanel();
        final JList list = new JList ();
        JScrollPane s = new JScrollPane(list);
        vlist.clear();
        list.setListData(main.getSavedProjects());
        a.setPreferredSize (new Dimension (308, 369));
        a.setLayout (null);
        a.add (s);
        s.setBounds (5, 5, 295, 360);
        d.getContentPane().add("Center",a);
        list.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent v)
            {
                virtualList.setSelectedIndex(list.getSelectedIndex());
                if(v.getClickCount()==2)
                {
                    if(list.getSelectedValue().toString().equals(""))
                    {
                        return;
                    }
                    Thread tt = new Thread(new Runnable()
                    {
                        public void run()
                        {
                            setEnabled(true);
                            d.dispose();
                            waiter.initWaiter();
                            waiter.setText("Opening project...");
                            openSaved(list.getSelectedValue().toString());
                            waiter.dispose();
                        }
                    });
                            tt.start();
                            waiter.dispose();
            }
        }});
        
        list.addKeyListener(new KeyAdapter()
        {
            public void keyReleased(KeyEvent v)
            {
                 virtualList.setSelectedIndex(list.getSelectedIndex());
            }
        });
        
        JPanel b = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton b1 = new JButton("Open");
        b.add(b1);
        JButton b2 = new JButton("Cancel");
        b2.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent v)
            {
                setEnabled(true);
                d.dispose();
            }
        });
        b1.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent v)
            {
                if(list.getSelectedIndex() < 0)
                {
                    return;
                }
                if(list.getSelectedValue().toString().equals(""))
                    {
                        return;
                    }
               Thread tt = new Thread(new Runnable()
                    {
                        public void run()
                        {
                            setEnabled(true);
                            d.dispose();
                            waiter.initWaiter();
                            waiter.setText("Opening project...");
                            openSaved(list.getSelectedValue().toString());
                            waiter.dispose();
                        }
                    });
                            tt.start();
            }
        });
        b.add(b2);
        d.getContentPane().add("South",b);
        d.pack();
        d.setLocationRelativeTo(this);
        d.setVisible(true);
                
        
    }
    
    
    public void openSaved(String id)
    {

       saved = new SavedProject(id, this);
       saved.open();
       desk.add(saved);
       saved.setVisible(true);
       saved.show();
       Thread tt = new Thread(new Runnable()
       {
           public void run()
           {
               saved.chooser = new JDirectoryChooser();
           }
       });tt.start();
       try
       {
           saved.setMaximum(true);
       }
       catch(Exception e){e.printStackTrace();}
    }
    
    
    
    
    public void makeToolBar()
    {
        JPanel tool= new JPanel();
	tool.setLayout(new FlowLayout(FlowLayout.LEFT));
	//tool.setFloatable(false);
	//tool.setRollover(true);		
	JButton b1= new JButton("New Project",new ImageIcon(getClass().getResource("images/new16.png")));
	b1.setToolTipText("New Project");
        b1.setHorizontalTextPosition(SwingConstants.CENTER);
        b1.setVerticalTextPosition(SwingConstants.BOTTOM);
        b1.setRequestFocusEnabled(false);
	tool.add(b1);        
        JButton b2= new JButton(new ImageIcon(getClass().getResource("images/open16.png")));
	b2.setToolTipText("Open Project");
	tool.add(b2);        
        JButton b3= new JButton(new ImageIcon(getClass().getResource("images/settings16.png")));
	b3.setToolTipText("Settings");
	tool.add(b3);        
        //JButton b4= new JButton(new ImageIcon(getClass().getResource("images/help32.png")));
	//b4.setToolTipText("Help");
	//tool.add(b4);        
        //JButton b5= new JButton(new ImageIcon(getClass().getResource("images/exit32.png")));
	//b5.setToolTipText("Exit");
	//tool.add(b5);
        getContentPane().add("North",tool);
    }
    
    
    public void UnloadWindow()
    {
	
	try{

	int x= JOptionPane.showConfirmDialog(this,"Are you sure you want to exit...?",Constants.APP_NAME,JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
	if(x==JOptionPane.YES_OPTION)
	{
		setVisible(false);
		dispose();
		System.exit(0);
	}

	else{return;}

	}catch(Exception t){}
    }
    
}
