/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jajitech.desktop.xdata.saved;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jajitech.desktop.xdata.BlurryLabel;
import com.jajitech.desktop.xdata.EscapeDialog;
import com.jajitech.desktop.xdata.Main;
import static com.jajitech.desktop.xdata.Main.etched;
import com.jajitech.desktop.xdata.access.Access;
import com.jajitech.desktop.xdata.cloud.Cloud;
import com.jajitech.desktop.xdata.constants.Constants;
import com.jajitech.desktop.xdata.constants.DropDownButton;
import com.jajitech.desktop.xdata.controller.MainDAO;
import com.jajitech.desktop.xdata.pojos.Project;
import com.jajitech.desktop.xdata.templates.ShareTemplatePanel;
import com.jajitech.desktop.xdata.ui.UIBuilder;
import com.jajitech.desktop.xdata.ui.UIElementsWriter;
import com.jajitech.desktop.xdata.ui.logic.Logic;
import com.jajitech.desktop.xdata.waiter.Waiter;
import com.l2fprod.common.swing.JDirectoryChooser;
import com.nitido.utils.toaster.Toaster;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Point2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.TransferHandler;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.xeustechnologies.jtar.TarEntry;
import org.xeustechnologies.jtar.TarInputStream;
import org.xeustechnologies.jtar.TarOutputStream;

/**
 *
 * @author Jaji
 */
public class SavedProject extends JInternalFrame {
    
    String id = null;
    static int previousIndex = -1;
    JPanel component;
    MainDAO main = new MainDAO();
    JList virtualList;
    static JList list;
    JTextArea  bstatus = new JTextArea();
    static JPanel mainDesigner = new JPanel();
    public static JList mainD = new JList();
    static Vector elementsList = new Vector();
    static JPanel propertiesPanel,build,side;
    static JPopupMenu pop;
    static UIBuilder ui;
    static JScrollPane designScroller;
    static String elementName = "";
    String pid = "";
    static String name = "";
    static String date = "";
    static String target = "";
    static int elementCount = 0;
    Toaster msg = null;
    Constants constants = new Constants();
    static JCheckBox auto;
    Waiter waiter = new Waiter();
    static Toaster toast = new Toaster();
    public JDirectoryChooser chooser;
    JFrame parentFrame;
    JPopupMenu pop2;
    ImageIcon icon = new ImageIcon(getClass().getResource("images/icon.png"));
    static final int BUFFER = 2048;
    String parent;
    Vector rows2, header2;
    JLabel project_code;
    JMenuItem cloud;
    static JRadioButton e2, e1;
    static com.jajitech.desktop.xdata.saved.PropertiesPanel props;
    
    public SavedProject(String ids, JFrame parentFrame)
    {
        super(null,false,true,false,true);
        setFrameIcon(new ImageIcon(getClass().getResource("images/textfield16.png")));
        //this.addInternalFrameListener(new InternalFrameListener())
        this.parentFrame = parentFrame;
        String idss[] = ids.split("##");
        //Drawer dr = new Drawer(mainDesigner);
        Drawer dr = new Drawer(mainD);
        ComponentListener l = new ComponentAdapter() {

        @Override
        public void componentResized(ComponentEvent e) {
            // next line possible if list is of type JXList
            // list.invalidateCellSizeCache();
            // for core: force cache invalidation by temporarily setting fixed height
            mainD.setFixedCellHeight(10);
            mainD.setFixedCellHeight(-1);
        }

    };

    mainD.addComponentListener(l);
        try
        {
            pid = idss[0].trim();
            name = idss[1].trim();
            target = idss[2];
            date = idss[3];
        }catch(Exception ee){
        props = new PropertiesPanel(parentFrame, name, elementsList);
            try
            {
                char[] buffer = new char[1024];
                FileReader configFile = new FileReader("projects/"+ids+"/project"+".jaj");
                configFile.read( buffer );
                String value[] = String.copyValueOf( buffer ).trim().split("\n");
                id = value[0].trim().replace("Project_Id:", "");
                name = value[1].trim().replace("Project_Name:", "");
                target = value[2].trim().replace("Target:", "");
                date =  value[3].trim().replace("Project_Date:", "");
            }
            catch(Exception eee)
            {
                eee.printStackTrace();
            }
            
            
            mainD.addMouseListener(dr);
            mainD.addMouseMotionListener(dr);
            //mainD.setFixedCellHeight(mainD);
            //mainD.setFixedCellWidth(200);
            mainD.setFixedCellHeight(-1);
            //list.setFixedCellHeight(-1);
            mainD.setTransferHandler(new ValueImportTransferHandler());
            
             this.addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameClosing(InternalFrameEvent e) {
                if(checkChanges() == false)
                {
                    int x= JOptionPane.showConfirmDialog(null,"Save changes before closing project...?",Constants.APP_NAME,JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.WARNING_MESSAGE);
                    if(x==JOptionPane.CANCEL_OPTION)
                    {
                        return;
                    }
                    if(x==JOptionPane.YES_OPTION)
                    {
                        saveSectionWork();
                    }
                    super.internalFrameClosing(e);
                }
                 Component[] components = SavedProject.this.parentFrame.getJMenuBar().getComponents();
                        for (Component component1 : components)
                        {
                            if (component1.getClass().getName().equals("javax.swing.JButton"))
                            {
                                SavedProject.this.parentFrame.getJMenuBar().remove(component1);
                                SavedProject.this.parentFrame.getJMenuBar().repaint();
                                SavedProject.this.parentFrame.getJMenuBar().invalidate();
                            }
                        }
                        elementsList.clear();
                mainD.setListData(new Vector());
            }
            
            @Override
            public void internalFrameClosed(InternalFrameEvent e) {
                //do some cleaning here
                super.internalFrameClosed(e);
                elementCount = 0;
            }
        });
            
            
        }
        
        this.id = id;
        setTitle(name);
        virtualList = new JList();
        
        buildPopup();
        ui = new UIBuilder();
        
    }
    
    
    public void resetAll()
    {
        elementCount = 0;
    }
    
    public boolean checkChanges()
    {
        if(new File("10110.jaj").exists())
        {
            return false;
        }
        return true;
    }
    
    
     public static void showPropertiesPanel(int previousIndex)
     {
        //System.out.println("this is previousIndex "+previousIndex+" and this is the new index "+mainD.getSelectedIndex());
        
        if(mainD.getSelectedIndex() == previousIndex)
        {
            return;
        }
        //System.out.println("this is the index at click time "+mainD.getSelectedIndex());
        previousIndex = mainD.getSelectedIndex();
        Object item = mainD.getSelectedValue();
        JPanel jp = (JPanel) item;
        props.setSection(list.getSelectedValue().toString());
        props.setData(jp);
        props.showProperties();
        
        /*
        Properties prop = new Properties(mainDesigner, name, list.getSelectedValue().toString());
         try
         {
            JPanel panel = null;
            String type = elementName;
            System.out.println(elementName);
            if(type.contains("label"))
            {
                panel = prop.getLabelProperties(elementName,name,list.getSelectedValue().toString());
            }
            if(type.contains("textfield"))
            {
                panel = prop.getTextFieldProperties(elementName, name, list.getSelectedValue().toString());
            }
            if(type.contains("textarea"))
            {
                panel = prop.getTextAreaProperties(elementName, name, list.getSelectedValue().toString());
            }
             if(type.contains("drop"))
            {
                panel = prop.getDropDownProperties(elementName, name, list.getSelectedValue().toString());
            }
             if (type.contains("checkbox")) {
                 panel = prop.getCheckBoxProperties(elementName, name, list.getSelectedValue().toString());
             }
             if (type.contains("radio")) {
                 panel = prop.getRadioProperties(elementName, name, list.getSelectedValue().toString());
             }
              if (type.contains("time")) {
                 panel = prop.getTimeProperties(elementName, name, list.getSelectedValue().toString());
             }
               if (type.contains("date")) {
                 panel = prop.getDateProperties(elementName, name, list.getSelectedValue().toString());
             }
               if (type.contains("image")) {
                 panel = prop.getImageProperties(elementName, name, list.getSelectedValue().toString());
             }
               if (type.contains("barcode")) {
                 panel = prop.getBarCodeProperties(elementName, name, list.getSelectedValue().toString());
             }
            if (type.contains("audio")) {
                 panel = prop.getAudioProperties(elementName, name, list.getSelectedValue().toString());
             }
            if (type.contains("video")) {
                 panel = prop.getVideoProperties(elementName, name, list.getSelectedValue().toString());
             }
             if (type.contains("location")) {
                 panel = prop.getLocationProperties(elementName, name, list.getSelectedValue().toString());
             }
             if (type.contains("spinner")) {
                 panel = prop.getSpinnerProperties(elementName, name, list.getSelectedValue().toString());
             }
             if (type.contains("onoff")) {
                 panel = prop.getONProperties(elementName, name, list.getSelectedValue().toString());
             }
             
            propertiesPanel.removeAll();
            propertiesPanel.add(panel);
            propertiesPanel.repaint();
            propertiesPanel.revalidate();
         }
         catch(Exception e)
         {
             e.printStackTrace();
         }
        */
    }
    
     
     
    public void buildPopup()
    {
        pop = new JPopupMenu();
        JMenu m1 = new JMenu("Add Elements");
            JMenuItem label = new JMenuItem("Rich Text", new ImageIcon(getClass().getResource("images/label16.png")));
            JMenuItem text = new JMenuItem("Text Box", new ImageIcon(getClass().getResource("images/textfield16.png")));
            JMenuItem textarea = new JMenuItem("Textarea", new ImageIcon(getClass().getResource("images/textarea16.png")));
            JMenuItem combo = new JMenuItem("Drop Down Box", new ImageIcon(getClass().getResource("images/combobox16.png")));
            JMenuItem check = new JMenuItem("Checkbox", new ImageIcon(getClass().getResource("images/checkbox16.png")));
            JMenuItem listm = new JMenuItem("Time Picker", new ImageIcon(getClass().getResource("images/time16.png")));
            JMenuItem radio = new JMenuItem("Radio Button", new ImageIcon(getClass().getResource("images/radio16.png")));
            JMenuItem date = new JMenuItem("Date Picker", new ImageIcon(getClass().getResource("images/date16.png")));
            JMenuItem pic = new JMenuItem("Image", new ImageIcon(getClass().getResource("images/picture16.png")));
            JMenuItem loc = new JMenuItem("Location", new ImageIcon(getClass().getResource("images/location16.png")));
            JMenuItem spin = new JMenuItem("Spinner", new ImageIcon(getClass().getResource("images/spinner16.png")));
            JMenuItem on = new JMenuItem("ON/OFF", new ImageIcon(getClass().getResource("images/on16.png")));
            JMenu md = new JMenu("Advaanced");
            JMenuItem d1 = new JMenuItem("Barcode Scanner", new ImageIcon(getClass().getResource("images/bar16.png")));
            md.add(d1);
            JMenuItem d2 = new JMenuItem("Audio", new ImageIcon(getClass().getResource("images/audio16.png")));
            md.add(d2);
            JMenuItem d3 = new JMenuItem("Video", new ImageIcon(getClass().getResource("images/video16.png")));
            md.add(d3);
            JMenuItem d4 = new JMenuItem("Matrix", new ImageIcon(getClass().getResource("images/matrix16.png")));
            md.add(d4);
            JMenuItem d5 = new JMenuItem("Rich Text", new ImageIcon(getClass().getResource("images/rtf16.png")));
            //md.add(d5 );
            on.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent v)
                {
                    addElementToDesigner("<html><b>On/");
                }
            });
            d1.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent v)
                {
                    addElementToDesigner("<html><b>Barcode");
                }
            });
            d2.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent v)
                {
                    addElementToDesigner("<html><b>Audio");
                }
            });
            d3.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent v)
                {
                    addElementToDesigner("<html><b>Video");
                }
            });
            d4.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent v)
                {
                    addElementToDesigner("<html><b>Matrix");
                }
            });
            d5.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent v)
                {
                    addElementToDesigner("<html><b>Rich");
                }
            });
            spin.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent v)
                {
                    addElementToDesigner("<html><b>Numeric");
                }
            });
            loc.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent v)
                {
                    addElementToDesigner("<html><b>Location");
                }
            });
            pic.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent v)
                {
                    addElementToDesigner("<html><b>Image");
                }
            });
            listm.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent v)
                {
                    addElementToDesigner("<html><b>Time");
                }
            });
            date.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent v)
                {
                    addElementToDesigner("<html><b>Date");
                }
            });
            radio.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent v)
                {
                    addElementToDesigner("<html><b>Radio");
                }
            });
            check.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent v)
                {
                    addElementToDesigner("<html><b>Checkbox");
                }
            });
            combo.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent v)
                {
                    addElementToDesigner("<html><b>Drop Down");
                }
            });
            label.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent v)
                {
                    addElementToDesigner("<html><b>Label");
                }
            });
            text.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent v)
                {
                    addElementToDesigner("<html><b>TextField");
                }
            });
            textarea.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent v)
                {
                    addElementToDesigner("<html><b>TextArea");
                }
            });
            m1.add(label);
            m1.add(text);
            m1.add(textarea);
            m1.add(combo);
            m1.add(check);
            m1.add(radio);
            m1.add(listm);
            m1.add(date);
            m1.add(pic);
            m1.add(loc);
            m1.add(spin);
            m1.add(on);
            m1.add(md);
        pop.add(m1);
        pop2 = pop.getComponentPopupMenu();
        JMenuItem m2 = new JMenuItem("Delete Element");
        m2.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent v)
                {
                    deleteElement("normal");
                }
            });
        
        JMenu rm = new JMenu("Make Rider To");
        
            JMenuItem above = new JMenuItem("Element Above");
            above.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent v)
                {
                  if(mainD.getSelectedIndex() == -1 || mainD.getSelectedIndex() == 0)
                  {
                      return;
                  }
                  Object ab = elementsList.elementAt(mainD.getSelectedIndex());
                  JPanel riderELement = (JPanel) ab;
                  Object toc = elementsList.elementAt(mainD.getSelectedIndex()-1);
                  JPanel parentElement = (JPanel) toc;
                  makeRider(parentElement, riderELement, mainD.getSelectedIndex()-1, mainD.getSelectedIndex());
                }
            });
            rm.add(above);
            
            JMenuItem below = new JMenuItem("Element Below");
            below.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent v)
                {
                  if(mainD.getSelectedIndex() == elementsList.size()-1)
                  {
                      System.out.println("i returned");
                      return;
                  }
                  Object ab = elementsList.elementAt(mainD.getSelectedIndex());
                  JPanel riderELement = (JPanel) ab;
                  Object toc = elementsList.elementAt(mainD.getSelectedIndex()+1);
                  JPanel parentElement = (JPanel) toc;
                  makeRider(parentElement, riderELement, mainD.getSelectedIndex()+1, mainD.getSelectedIndex());
                }
            });
            rm.add(below);
            rm.addSeparator();
            
            JMenuItem choose = new JMenuItem("Choose Element");
            rm.add(choose);
            
            //pop.add(rm);
        pop.addSeparator();
        
        JMenuItem sv = new JMenuItem("Save");
        sv.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent v)
            {
                Thread t = new Thread(new Runnable()
                {
                    public void run()
                    {
                        //msg.showToaster(new ImageIcon(getClass().getResource("images/alerttoast.jpg")),"Message forwarded");
                        saveSectionWork();
                    }});
                t.start();
                
            }
        });
        pop.add(sv);
        pop.addSeparator();
        JMenuItem bd = new JMenuItem("Build");
        bd.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent v)
            {
                if(list.getModel().getSize()<1)
                {
                    return;
                }
                
                File f = new File("account.jaj");
                if(!f.exists())
                {
                    constants.alert(parentFrame, "warn", "Please sign in before building project");
                    return;
                }
                
                buildProject();
            }
        });
        //pop.add(bd);
        
        cloud = new JMenuItem("Deploy to Cloud");
        //cloud.setEnabled(false);
        cloud.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent v)
            {
                if(mainD.isVisible() == false)
                {
                    return;
                }
                saveSectionWork();
               sendToCloud();
            }
        });
        
        pop.add(cloud);
        File f = new File("projects/"+name+"/build");
        if(f.exists())
        {
            cloud.setEnabled(true);
        }
        pop.addSeparator();
        JMenuItem sos = new JMenuItem("Refresh");
        sos.addActionListener(new ActionListener()
        {
        public void actionPerformed(ActionEvent v)
                {
                    Thread tt = new Thread(new Runnable()
                    {
                        public void run()
                        {
                            loadSection();
                        }
                    });
                    tt.start();
                }
        });
        pop.add(sos);
        pop.addSeparator();
        auto = new JCheckBox("Auto Save");
        File ff = new File("projects/"+name+"/autoSave.jaj");
        if(ff.exists())
        {
            auto.setSelected(true);
        }
        auto.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent v)
            {
                if(auto.isSelected())
                {
                    try
                    {
                        FileWriter f = new FileWriter("projects/"+name+"/autoSave.jaj");
                        f.write("");;
                        f.flush();
                        f.close();
                    }
                    catch(Exception e){}
                }
                else
                {
                    try{File ff = new File("projects/"+name+"/autoSave.jaj");ff.delete();}catch(Exception r){}
                }
            }
        });
        pop.add(auto);
            pop.addSeparator();
        JMenu style = new JMenu("Section Tools");
            JMenuItem s1 = new JMenuItem("Add New");
            s1.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent v)
                {
                    addSection();
                }
            });
            style.add(s1);
            JMenuItem s2 = new JMenuItem("Rename Section");
            s2.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent v)
                {
                    editSection();
                }
            });
            style.add(s2);
            JMenuItem s3 = new JMenuItem("Delete Section");
            s3.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent v)
                {
                    if(!mainD.isVisible())
                    {
                        return;
                    }
                    int x= JOptionPane.showConfirmDialog(null,"Are you sure you want to delete section...?",Constants.APP_NAME,JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.WARNING_MESSAGE);
                    if(x==JOptionPane.CANCEL_OPTION)
                    {
                        return;
                    }
                    if(x==JOptionPane.YES_OPTION)
                    {
                        deleteSection();
                        elementsList.clear();
                    }
                }
            });
            style.addSeparator();
            JMenuItem s3a = new JMenuItem("Section Instructions");
            style.add(s3);
            style.addSeparator();
            style.add(s3a);
            style.addSeparator();
            s3a.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent v)
                {
                    if(list.isEnabled() == true)
                    {
                        return;
                    }
                    File f = new File("projects/"+name+"/sections/"+list.getSelectedValue()+"/"+list.getSelectedValue().toString().toLowerCase()+".yas");
                    if(!f.exists())
                    {
                        try{f.createNewFile();}catch(Exception er){}
                    }
                    //Ekit editor = new Ekit(SavedProject.this.parentFrame, name, f);
                    //editor.setTitle("Section Instructions for: "+list.getSelectedValue());
                    //editor.setVisible(true);
                }
            });
            JMenu entries = new JMenu("Entries");
                e1 = new JRadioButton("Single");
                e2 = new JRadioButton("Multiple");
                e2.setSelected(true);
                ButtonGroup bg = new ButtonGroup();
                bg.add(e1);
                bg.add(e2);
                e1.addActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent v)
                    {
                        if(list.getSelectedIndex() < 0)
                        {
                            return;
                        }
                        String singleList = main.readFile("projects/"+name+"/singleList.jaj");
                        if(e1.isSelected())
                        {
                            if(!singleList.contains(list.getSelectedValue().toString()))
                            {
                                singleList = singleList  + list.getSelectedValue() + "@@\n";
                            }
                        }
                        try
                        {
                            FileWriter f1q = new FileWriter("projects/"+name+"/"+"singleList.jaj");
                            f1q.write(singleList);
                            f1q.close();
                        }catch(Exception er){er.printStackTrace();}
                    }
                });
                
                e2.addActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent v)
                    {
                        if(list.getSelectedIndex() < 0)
                        {
                            return;
                        }
                        String singleList = main.readFile("projects/"+name+"/singleList.jaj");
                        if(e2.isSelected())
                        {
                            singleList = singleList.replace(list.getSelectedValue().toString()+"@@", "");
                        }
                        try
                        {
                            FileWriter f1q = new FileWriter("projects/"+name+"/"+"singleList.jaj");
                            f1q.write(singleList);
                            f1q.close();
                        }catch(Exception er){er.printStackTrace();}
                    }
                });
                
                
                entries.add(e1);
                entries.add(e2);
            style.add(entries);
            
        pop.add(style);
        JMenuItem so = new JMenuItem("Close Section");
        so.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent v)
            {
                if(checkChanges() == false)
                {
                    int x= JOptionPane.showConfirmDialog(null,"Save changes before closing...?",Constants.APP_NAME,JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.WARNING_MESSAGE);
                    if(x==JOptionPane.CANCEL_OPTION)
                    {
                        return;
                    }
                    if(x==JOptionPane.YES_OPTION)
                    {
                        saveSectionWork();
                        
                    }
                }
                try{File f = new File("10110.jaj");f.delete();}catch(Exception ee){ee.printStackTrace();}
                elementsList.clear();
                mainD.setListData(new Vector());
                list.setEnabled(true);
                mainD.setVisible(false);
                JList li = new JList();
                        list.setSelectionBackground(li.getSelectionBackground());
                        list.setSelectionForeground(li.getSelectionForeground());
                        list.setForeground(li.getForeground());
                        list.setBackground(li.getBackground());
            }
        });
        pop.add(so);
        pop.addSeparator();
        JMenuItem lc = new JMenuItem("Logic");
        lc.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent v)
            {
                buildLogic();
            }
        });
        pop.add(lc);
        pop.addSeparator();
        JMenu ib = new JMenu("Import");
        JMenuItem im = new JMenuItem("Section");
        im.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent v)
            {
                if(mainD.isVisible())
                {
                    constants.alert(parentFrame, "warn", "Please close this section before a new section can be imported");
                    return;
                }
                importSection();
            }
        });
        ib.add(im);
        pop2 = new JPopupMenu();
        pop2.add(ib);
        JMenuItem mn = new JMenuItem("Project as section");
        mn.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent v)
            {
                if(mainD.isVisible())
                {
                    constants.alert(parentFrame, "warn", "Please close this section before a new section can be imported");
                    return;
                }
                try{importProjectAsSection(parentFrame);}catch(Exception e){}
            }
        });
        ib.add(mn);
        pop.add(ib);
        JMenuItem ex = new JMenuItem("Export Section");
        ex.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent v)
            {
                if(list.getSelectedIndex() < 0)
                {
                    return;
                }
                
                Thread t = new Thread(new Runnable()
                {
                    public void run()
                    {
                        String path = "";
                        chooser= new JDirectoryChooser();
                        chooser.setDialogTitle("Export Section To...");
                        chooser.setMultiSelectionEnabled(false);
                        int r=chooser.showOpenDialog(parentFrame);
                        if(r==JDirectoryChooser.APPROVE_OPTION)
                        {
                            File[] selectedFiles = chooser.getSelectedFiles();
                            for (int i = 0, c = selectedFiles.length; i < c; i++)
                            {
                                path += selectedFiles[i];
                            }
                        }
                        else
                        {
                            return;
                        }
                        System.out.println("here path "+path);
                        exportSection(path, true);
                    }
                });
                t.start();
            }});
        pop.add(ex);
        pop.addSeparator();
        JMenuItem ca = new JMenuItem("Select All");
        //pop.add(ca);
        JMenuItem ct = new JMenuItem();
        ct.setText("Cut");
        ct.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent v)
            {
                cutElementToClipBoard();
            }
        });
        ct.setAccelerator (KeyStroke.getKeyStroke(KeyEvent.VK_X, Event.CTRL_MASK));
        pop.add(ct);
        JMenuItem cp = new JMenuItem();
        cp.setText("Copy");
        cp.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent v)
            {
                copyElementToClipBoard();
            }
        });
        cp.setAccelerator (KeyStroke.getKeyStroke(KeyEvent.VK_C, Event.CTRL_MASK));
        pop.add(cp);
        JMenuItem pt = new JMenuItem();
        pt.setText("Paste");
        pt.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent v)
            {
                pasteElementFromClipBoard(0);
            }
        });
        pt.setAccelerator (KeyStroke.getKeyStroke(KeyEvent.VK_V, Event.CTRL_MASK));
        pop.add(pt);
        
        
        JMenuItem pt0 = new JMenuItem();
        pt0.setText("Paste Before");
        pt0.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent v)
            {
                pasteElementFromClipBoard(1);
            }
        });
        pt0.setAccelerator (KeyStroke.getKeyStroke(KeyEvent.VK_B, Event.CTRL_MASK));
        pop.add(pt0);
        
        JMenuItem pt1 = new JMenuItem();
        pt1.setText("Paste After");
        pt1.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent v)
            {
                pasteElementFromClipBoard(2);
            }
        });
        pt1.setAccelerator (KeyStroke.getKeyStroke(KeyEvent.VK_T, Event.CTRL_MASK));
        pop.add(pt1);
        
        
        pop.addSeparator();
        JMenuItem ck = new JMenuItem("Close Project");
        ck.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent v)
            {
                if(checkChanges() == false)
                {
                    int x= JOptionPane.showConfirmDialog(null,"Save changes before closing project...?",Constants.APP_NAME,JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.WARNING_MESSAGE);
                    if(x==JOptionPane.CANCEL_OPTION)
                    {
                        return;
                    }
                    if(x==JOptionPane.YES_OPTION)
                    {
                        saveSectionWork();
                    }
                }
                 Component[] components = SavedProject.this.parentFrame.getJMenuBar().getComponents();
                        for (Component component1 : components)
                        {
                            if (component1.getClass().getName().equals("javax.swing.JButton"))
                            {
                                SavedProject.this.parentFrame.getJMenuBar().remove(component1);
                                SavedProject.this.parentFrame.getJMenuBar().repaint();
                                SavedProject.this.parentFrame.getJMenuBar().invalidate();
                            }
                        }
                        elementsList.clear();
                mainD.setListData(new Vector());
                dispose();
            }
        });
        pop.add(ck);
        
        pop.addSeparator();
        JMenuItem p = new JMenuItem("Project Properties");
        p.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent v)
            {
               openProjectProperties();
            }
        });
        pop.add(p);
        pop.addSeparator();
        JMenu mh = new JMenu("Templates");
            JMenuItem h1 = new JMenuItem("Share Section");
        h1.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent v)
            {
               shareTemplate(1);
            }
        });
        
        JMenuItem h2 = new JMenuItem("Share Project");
        h2.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent v)
            {
               shareTemplate(2);
            }
        });
        mh.add(h1);
        mh.add(h2);
        pop.add(mh);
        //parentFrame.getJMenuBar().add(menu);
        parentFrame.getJMenuBar().repaint();
        parentFrame.getJMenuBar().invalidate();
        
    }
    
    
    String hh="";
    public void shareTemplate(int type)
    {
        final EscapeDialog d = new EscapeDialog(parentFrame, true);
        final ShareTemplatePanel l = new ShareTemplatePanel(type);
        final Cloud c = new Cloud();
        final JComboBox jc = new JComboBox();
        Thread t = new Thread(new Runnable()
        {
            public void run()
            {
                waiter.initWaiter();
                waiter.toFront();
                waiter.setText("Please wait...");
                hh = c.getTemplateCategories();
                while(hh.equals(""))
                {
                    System.out.println("it is wnat it is");
                }
                if(hh.startsWith("Error"))
                {
                    d.dispose();
                    waiter.dispose();
                    JOptionPane.showMessageDialog(parentFrame,hh,"Error", JOptionPane.ERROR_MESSAGE);
                    d.dispose();
                    return;
                }
                
                 String categories[] = hh.split("@@");
                 
        for(String y: categories)
        {
            String p[] = y.split("###");
            l.jComboBox1.addItem(p[0]);
            jc.addItem(p[1]);
        }
        l.jComboBox1.addActionListener(e ->{
            jc.setSelectedIndex(l.jComboBox1.getSelectedIndex());
        });
                
                 waiter.dispose();
            }
        });
        t.start();
        hh="";
        d.setTitle("Share As Template");
	d.setResizable(false);
	d.setDefaultCloseOperation(EscapeDialog.DO_NOTHING_ON_CLOSE);
	d.addWindowListener(new WindowAdapter(){
	public void windowClosing(WindowEvent e){parentFrame.setEnabled(true);d.dispose();}});
        d.getContentPane().add("Center",l);
        d.pack();
        d.setLocationRelativeTo(parentFrame);
        l.jButton1.addActionListener(e -> {
        String tp = "project";
        if(l.jRadioButton1.isSelected() == true)
        {
            tp = "section";
        }
        String title = l.jTextField1.getText();
        String cat = l.jComboBox1.getSelectedItem().toString();
        String tags = l.jTextArea1.getText();
        if(title.length() < 1 || tags.length() < 1)
        {
            JOptionPane.showMessageDialog(parentFrame, "Please enter display title and taggs");
            return;
        }
            
            try
            {
                  if(tp.equals("section"))
                  {
                      File f = new File("share/"+main.readFile("projects/"+name+"/identifier.jaj")+"/"+list.getSelectedValue());
                        if(!f.exists())
                        {
                            f.mkdirs();
                        }
                      String path ="share/"+main.readFile("projects/"+name+"/identifier.jaj")+"/"+list.getSelectedValue();
                      exportSection(new File(path).getAbsolutePath(), true);
                      FileWriter f1 = new FileWriter("share/"+main.readFile("projects/"+name+"/identifier.jaj")+"/"+list.getSelectedValue()+"/info.tes");
                      String ct = title+"###"+jc.getSelectedItem()+"###"+tags;
                      f1.write(ct);
                      f1.close();
                  }
                  else
                  {
                      File f = new File("share/"+main.readFile("projects/"+name+"/identifier.jaj")+"/"+name);
                        if(!f.exists())
                        {
                            f.mkdirs();
                        }
                      String path ="share/"+main.readFile("projects/"+name+"/identifier.jaj")+"/"+name;
                      exportProject("projects/"+name, new File(path).getAbsolutePath(), true);
                      FileWriter f1 = new FileWriter("share/"+main.readFile("projects/"+name+"/identifier.jaj")+"/"+name+"/info.tes");
                      String ct = title+"###"+jc.getSelectedItem()+"###"+tags;
                      f1.write(ct);
                      f1.close();
                  }
            }
            catch(Exception er)
            {
                er.printStackTrace();
            }
        });
        d.setVisible(true);
        
        
    }
    
    
    
    
    
    
    public void makeRider(JPanel parentElement, JPanel riderElement, int parentIndex, int riderIndex)
    {
        String r[] = riderElement.getName().split("##");
        String p[] = parentElement.getName().split("##");
        String r1 = r[0].trim();
        String r2 = r[1].trim();
        String r3 = r[2].trim();
        String r4 = r[3].trim();
        String r5 = "isRider";
        String rider = r1+"##"+r2+"##"+r3+"##"+r4+"##"+r5;
        
        String p1 = p[0].trim();
        String p2 = p[1].trim();
        String p3 = p[2].trim();
        String p4 = p[3].trim();
        String p5 = rider;
        String parent = p1+"##"+p2+"##"+p3+"##"+p4+"##"+p5;
        parentElement.setName(parent);
        riderElement.setName(rider);
        Component c[] = parentElement.getComponents();
        JPanel a = (JPanel) c[0];
        JLabel l = new JLabel("");
        l.setIcon(new ImageIcon(getClass().getResource("images/switch.gif")));
        a.add(l);
        
        Component c1[] = riderElement.getComponents();
        JPanel a1 = (JPanel) c1[0];
        JLabel l1 = new JLabel("");
        l1.setIcon(new ImageIcon(getClass().getResource("images/update.gif")));
        a1.add(l1);
        
        elementsList.setElementAt(parentElement, parentIndex);
        elementsList.setElementAt(riderElement, riderIndex);
        mainD.setListData(elementsList);
        mainD.setSelectedIndex(riderIndex);
        File f = new File("projects/autoSave.jaj");
        if(f.exists() == true)
        {
            saveSectionWork();
        }
    }
    
    
    public static void LogicStatus(JPanel parentElement, int index, String status)
    {
        String p[] = parentElement.getName().split("##");
        String p1 = p[0].trim();
        String p2 = p[1].trim();
        String p3 = p[2].trim();
        String p4 = p[3].trim();
        String p5 = ""+status;
        String parent = p1+"##"+p2+"##"+p3+"##"+p4+"##"+p5;
        Component c[] = parentElement.getComponents();
        parentElement.setName(parent);
        elementsList.setElementAt(parentElement, index);
        mainD.setListData(elementsList);
        mainD.setSelectedIndex(index);
        boolean cc = false;
        try{
            File ft = new File("autoSave.jaj");
            if(ft.exists())
            {
                cc=true;
            }FileWriter f = new FileWriter("10110.jaj");f.write("");f.flush();f.close();}catch(Exception e){}
        if(auto.isSelected()==true)
        {
            saveSectionWork();
        }
    }
    
    public void buildLogic()
    {
        if(elementName.startsWith("rtf") || elementName.startsWith("image") || elementName.startsWith("location") || elementName.startsWith("on") || elementName.startsWith("barcode") || elementName.startsWith("audio") || elementName.startsWith("video") || elementName.startsWith("matrix"))
        {
            return;
        }
        
        String p[] = elementName.split("##");
        String ename = p[0].trim();
        String ecap = p[1].trim();
        String eitem = p[2].trim();
        String ereq = p[3].trim();
        String erider = p[4].trim();
        
        if(elementName.startsWith("textfield") && eitem.equals("Normal"))
        {
            constants.alert(parentFrame, "warn", "Logic can only be set on TextFields with Numeric constraint.");
            return;
        }
        
        Vector e = new Vector();
        e.add("Select");
        if(elementName.startsWith("textfield") || elementName.startsWith("spinner") || elementName.startsWith("date") || elementName.startsWith("time"))
        {
            e.add("=");
            e.add(">");
            e.add("<");
            e.add(">=");
            e.add("<=");
        }
        
        if(elementName.startsWith("date") || elementName.startsWith("time"))
        {
            e.add("between");
        }
        
        
        if(elementName.startsWith("radio") || elementName.startsWith("drop"))
        {
            e.add("=");
        }
        
        if(elementName.startsWith("check"))
        {
            e.add("contains all");
            e.add("contains any");
        }
        
        
        
        List lx = new ArrayList();
        Component[] components = mainD.getComponents();
        ListModel model = mainD.getModel();
				int n = model.getSize();
				if(n<1)
				{
				  return;
				}
        for(int x=0; x<model.getSize(); x++)
        {
            Object item = model.getElementAt(x);
            JPanel jp = (JPanel) item;
            String pname = jp.getName();
            lx.add(pname);
        }
        
        Object j = model.getElementAt(mainD.getSelectedIndex());
        JPanel jp = (JPanel) j;
        
        
        
        final EscapeDialog d = new EscapeDialog(parentFrame, true);
        d.setTitle("Component Logic for: "+ecap);
	d.setResizable(false);
	d.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
	d.addWindowListener(new WindowAdapter(){
	public void windowClosing(WindowEvent e){parentFrame.setEnabled(true);d.dispose();}});
        System.out.println("item is "+elementName);
        List ly = lx;
        ly.remove(elementName);
        JPanel l = new Logic(e, parentFrame, d, ly, elementName, jp, mainD.getSelectedIndex(), erider);
        d.getContentPane().add("Center",l);
        d.pack();
        d.setLocationRelativeTo(parentFrame);
        d.setVisible(true);
        
    }
    
    
    
    public void showRiderWindow()
    {
        System.out.println(elementsList.elementAt(mainD.getSelectedIndex()));                       
    }
    
    
    public void buildProject()
    {
        parentFrame.setEnabled(false);
        final JDialog d = new JDialog(parentFrame);
        d.setTitle("Build");
	d.setResizable(false);
	d.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
	d.addWindowListener(new WindowAdapter(){
	public void windowClosing(WindowEvent e){parentFrame.setEnabled(true);d.dispose();}});
        
        JPanel a = new JPanel();
        side = new JPanel();
        side.setLayout(new FlowLayout(FlowLayout.LEFT));
        side.setBackground(Color.white);
        side.setBorder(Main.etched);
        JPanel top = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        top.setBackground(Color.white);
        top.setBorder(etched);
        JLabel l = new JLabel("Review Project Details");
        top.add(l);
        l.setFont(new Font("Arial",Font.BOLD,14));
        final JPanel build = new JPanel();
        build.setBorder(etched);
        build.setLayout(new CardLayout());
        JPanel p1 = build1();
        JPanel p2 = build2();
        JPanel p3 = build3();
        JPanel p4 = build4(d);
        build.add("comp1", p1);
        build.add("comp2", p2);
        build.add("comp3", p3);
        build.add("comp4", p4);
        JPanel x = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        final JButton b = new JButton("Next");
        b.setToolTipText("1");
        final JButton c = new JButton("Previous");
        c.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent v)
            {
                CardLayout cl=(CardLayout)(build.getLayout());
                cl.show(build,"comp"+(Integer.parseInt(b.getToolTipText())-1));
                b.setToolTipText((Integer.parseInt(b.getToolTipText())-1)+"");
                if(b.getToolTipText().equals("1"))
                {
                    c.setEnabled(false);
                    b.setEnabled(true);
                }
                setProgressText((Integer.parseInt(b.getToolTipText())));
            }
        });
        b.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent v)
            {
                CardLayout cl=(CardLayout)(build.getLayout());
                cl.show(build,"comp"+(Integer.parseInt(b.getToolTipText())+1));
                b.setToolTipText(""+(Integer.parseInt(b.getToolTipText())+1));
                c.setEnabled(true);
                if(b.getToolTipText().equals("4"))
                {
                    b.setEnabled(false);
                }
                setProgressText((Integer.parseInt(b.getToolTipText())));
            }
        });
        c.setEnabled(false);
        x.add(c);
        x.add(b);
        d.getContentPane().add("South",x);
        a.setPreferredSize (new Dimension (542, 436));
        a.setLayout (null);
        a.add (side);
        a.add (top);
        a.add (build);
        side.setBounds (0, 0, 175, 430);
        top.setBounds (179, 0, 360, 45);
        build.setBounds (179, 49, 360, 380);
        CardLayout cl=(CardLayout)(build.getLayout());
        cl.show(build,"comp1");
        setProgressText(1);
        d.getContentPane().add("Center",a);
        d.pack();
        d.setLocationRelativeTo(parentFrame);
        d.setVisible(true);               
    }
    
    public void finishBuild()
    {
        Thread t = new Thread(new Runnable()
        {
            public void run()
            {
                /*
                JWindow w = new JWindow();
                JPanel a = new JPanel();
                a.setBorder(new LineBorder(Color.green.darker(),1));
                final JTextArea jcomp1 = new JTextArea (5, 5);
                JScrollPane s = new JScrollPane(jcomp1);
                JButton jcomp2 = new JButton ("", new ImageIcon(getClass().getResource("images/update.gif")));
                a.setPreferredSize (new Dimension (290, 229));
                a.setLayout (null);
                a.add (s);
                a.add (jcomp2);
                s.setBounds (5, 5, 245, 220);
                jcomp2.setBounds (255, 5, 30, 30);
                w.getContentPane().add("Center",a);
                w.pack();
                w.setLocationRelativeTo(parentFrame);
                w.setVisible(true);
                jcomp1.setText("");
                */
                waiter.initWaiter();
                waiter.setText("Building...");
                //main.buildProject(name, project_code);
                waiter.dispose();
            }
        });
        t.start();
    }
    
    public JPanel build4(final JDialog d)
    {
        JPanel a = new JPanel();
        a.setLayout(new FlowLayout(FlowLayout.LEFT));
        final JButton jcomp1 = new JButton ("Build Project");
        jcomp1.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent v)
            {
                parentFrame.setEnabled(true);
                d.dispose();
                finishBuild();
                cloud.setEnabled(true);
                toast.showToaster(icon, "Project built successfully...");
                
            }
        });
        JPanel jcomp2 = new JPanel();
        jcomp2.setBorder(etched);
        JLabel l = new JLabel("<html><b>If this is the first time building project, only<br> the changed sections will be updated in the cloud.</b></html>");
        l.setForeground(Color.red);
        jcomp2.add(l);
        a.setPreferredSize (new Dimension (360, 380));
        a.setLayout (null);
        a.add (jcomp1);
        a.add (jcomp2);
        jcomp1.setBounds (115, 105, 110, 30);
        jcomp2.setBounds (35, 195, 290, 85);
        return a;
    }
    
    public JPanel build3()
    {
        String access = main.readFile("projects/"+name+"/access.jaj");
        JPanel a = new JPanel();
        JLabel jcomp1 = new JLabel ("<html><b>The following will certify the data collected");
        JComponent jcomp2 = null;
        if(access.equals("Private"))
        {
            jcomp2 = new JTable();
            getCertList((JTable)jcomp2);
        }
        else
        {
            jcomp2 = new JLabel("Certification not needed for public projects");
        }
        JScrollPane s = new JScrollPane(jcomp2);
        a.setPreferredSize (new Dimension (360, 380));
        a.setLayout (null);
        a.add (jcomp1);
        a.add (s);
        jcomp1.setBounds (40, 5, 275, 25);
        s.setBounds (15, 35, 330, 335);
        return a;
    }
    
    public JPanel build2()
    {
        String access = main.readFile("projects/"+name+"/access.jaj");
        JPanel a = new JPanel();
        JLabel jcomp1 = new JLabel ("<html><b>The following are authorized to access project</b></html>");
        JComponent jcomp2 = null;
        if(access.equals("Private"))
        {
            jcomp2 = new JTable();
            getAccessList((JTable)jcomp2);
        }
        else
        {
            jcomp2 = new JLabel("This project is publicly accessible");
        }
        JScrollPane s = new JScrollPane(jcomp2);
        a.setPreferredSize (new Dimension (360, 380));
        a.setLayout (null);
        a.add (jcomp1);
        a.add (s);
        jcomp1.setBounds (40, 5, 275, 25);
        s.setBounds (15, 35, 330, 335);
        return a;
    }
    
    public JPanel build1()
    {
        JPanel a = new JPanel();
        String[] jcomp4Items = {"Web","Mobile"};
        final JLabel jcomp1 = new JLabel ("Project Name");
        final JTextField jcomp2 = new JTextField (5);
        jcomp2.setText(name);
        JLabel jcomp3 = new JLabel ("Build For");
        final JComboBox jcomp4 = new JComboBox (jcomp4Items);
        jcomp4.setSelectedItem(target);
        a.setPreferredSize (new Dimension (360, 380));
        a.setLayout (null);
        a.add (jcomp1);
        a.add (jcomp2);
        a.add (jcomp3);
        a.add (jcomp4);
        jcomp1.setBounds (35, 55, 100, 25);
        jcomp2.setBounds (35, 80, 300, 25);
        jcomp3.setBounds (35, 110, 100, 25);
        jcomp4.setBounds (35, 135, 295, 25);
        return a;
    }
    
    public void setProgressText(int x)
    {
        side.removeAll();
        side.repaint();
        side.invalidate();
        String text = "";
        if(x==1)
        {
            text = "<html><b>Project Info.</b><br>"
                    + "Project Access<br>"
                    + "Data Certification<br>"
                    + "Send Build";
        }
        if(x==2)
        {
            text = "<html>Project Info.<br>"
                    + "<b>Project Access</b><br>"
                    + "Data Certification<br>"
                    + "Send Build";
        }
        if(x==3)
        {
            text = "<html>Project Info.<br>"
                    + "Project Access<br>"
                    + "<b>Data Certification</b><br>"
                    + "Send Build";
        }
        if(x==4)
        {
            text = "<html>Project Info.<br>"
                    + "Project Access<br>"
                    + "Data Certification<br>"
                    + "<b>Send Build</b>";
        }
        side.add(new JLabel(text));
                
    }
    
    
    
    public void openProjectProperties()
    {
        final EscapeDialog d = new EscapeDialog(parentFrame, true);
        d.setTitle("Properties");
	//d.setResizable(false);
	d.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
	d.addWindowListener(new WindowAdapter(){
	public void windowClosing(WindowEvent e){parentFrame.setEnabled(true);d.dispose();}});
        JTabbedPane t =  new JTabbedPane();
        JPanel a = new JPanel();
        String[] jcomp1Items = {"General", "Access", "Data Certification"};
        final JList jcomp1 = new JList (jcomp1Items);
        jcomp1.setSelectedIndex(0);
        JScrollPane s = new JScrollPane(jcomp1);
        final JPanel jcomp2 = new JPanel(new CardLayout());
         jcomp1.addKeyListener(new KeyAdapter()
        {
            public void keyReleased(KeyEvent k)
            {
                CardLayout cl=(CardLayout)(jcomp2.getLayout());
        	cl.show(jcomp2,jcomp1.getSelectedValue().toString());
            }
        });
        buildGeneralPanel(jcomp2,d);
        buildAccessPanel(jcomp2, d);
        buildCertPanel(jcomp2, d);
        jcomp2.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
        a.setPreferredSize (new Dimension (560, 500));
        a.setLayout (null);
        a.add (s);
        a.add (jcomp2);
        s.setBounds (5, 5, 150, 460);
        jcomp2.setBounds (160, 5, 500, 550);
        t.addTab("Project Properties", a);
        d.getContentPane().add("Center",t);
        d.setSize(685, 643);
        d.setLocationRelativeTo(null);
        CardLayout cl=(CardLayout)(jcomp2.getLayout());
	//cl.show(a,"General");
        jcomp1.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent v)
            {
                CardLayout cl=(CardLayout)(jcomp2.getLayout());
        	cl.show(jcomp2,jcomp1.getSelectedValue().toString());
            }
        });
        
        
        d.setVisible(true);
    }
    
    public JPanel getDropSectionPanel(final JTextArea text, JDialog dd)
    {
        final JPanel a = new JPanel();
        final JList jcomp1 = new JList ();
        final Vector vv = new Vector();
        for(int t=0;t<list.getModel().getSize();t++)
        {
            vv.add(list.getModel().getElementAt(t));
        }
        jcomp1.setListData(vv);
        final CheckListManager checkListManager = new CheckListManager(jcomp1); 
        JScrollPane s = new JScrollPane(jcomp1);
        final JButton jcomp2 = new JButton ("Add");
        jcomp2.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent v)
            {
                String sec = "";
                for(int t=0;t<vv.size();t++)
                {
                    boolean g = checkListManager.getSelectionModel().isSelectedIndex(t);
                    if(g==true)
                    {
                        sec = sec+list.getModel().getElementAt(t)+";";
                    }
                }
                text.setText(sec);
            }
        });
        final JCheckBox jcomp3 = new JCheckBox ("Select All");
        jcomp3.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent v)
            {
                if(jcomp3.isSelected())
                {
                    checkListManager.selectAll(vv);
                }
                else
                {
                    checkListManager.deSelectAll(vv);
                }
            }
        });
        a.setPreferredSize (new Dimension (170, 330));
        a.setLayout (null);
        a.add (s);
        a.add (jcomp2);
        a.add (jcomp3);
        s.setBounds (4, 34, 165, 260);
        jcomp2.setBounds (35, 299, 100, 30);
        jcomp3.setBounds (35, 5, 100, 25);
        return a;
    }
    
    public void addUserAccess(final JDialog dd, final JTable table, final String type, final boolean isAccess)
    {
        dd.setEnabled(false);
        final JDialog d= new JDialog(parentFrame);
	d.setTitle("");
	d.setResizable(false);
	d.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
	d.addWindowListener(new WindowAdapter(){
	public void windowClosing(WindowEvent e){dd.setEnabled(true);d.dispose();}});
        JPanel jc1 = new JPanel (new FlowLayout(FlowLayout.RIGHT));
        jc1.setBorder(Main.etched);
        //jc1.setBackground(Color.white);
        BlurryLabel ll= new BlurryLabel();
        ll.setText("Add User");
        if(type.equals("edit"))
        {
            ll.setText("Edit User");
        }
        ll.setFont(ll.getFont().deriveFont(18f));
        ll.setDrawBlur(true);
        ll.setBorder(new EmptyBorder(0, 5, 2, 5));
        ll.setIcon(new ImageIcon(getClass().getResource("images/users.png")));
        jc1.add(ll);
        d.getContentPane().add("North",jc1);
        
        JPanel a = new JPanel();
        JLabel jcomp1 = new JLabel ("User");
        final JTextField jcomp2 = new JTextField (5);
        JLabel jcomp3 = new JLabel ("Section (s)");
        final JTextArea jcomp4 = new JTextArea (5, 5);
        jcomp4.setEditable(false);
        jcomp4.setLineWrap(true);
        jcomp4.setWrapStyleWord(true);
        JScrollPane s = new JScrollPane(jcomp4);
        final DropDownButton jcomp5 = new DropDownButton (new ImageIcon(getClass().getResource("images/switch.gif")));
        JPanel x = getDropSectionPanel(jcomp4,d);
        jcomp5.addComponent(x);
        JLabel jcomp6 = new JLabel ("Pass Code");
        final JTextField jcomp7 = new JTextField (5);
        jcomp7.setEditable(false);
        JButton jcomp8 = new JButton ("Generate");
        if(type.equals("edit"))
        {
            jcomp2.setText(table.getValueAt(table.getSelectedRow(), 1).toString());
            jcomp4.setText(table.getValueAt(table.getSelectedRow(), 2).toString());
            jcomp7.setText(table.getValueAt(table.getSelectedRow(), 3).toString());
        }
        jcomp8.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent v)
            {
                jcomp7.setText(constants.randomNumeric(6));
            }
        });
        JPanel pp = new JPanel ();
        pp.setBorder(etched);
        JButton jcomp10 = new JButton ("Cancel");
        jcomp10.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent v)
            {
                dd.setEnabled(true);
                d.dispose();
            }
        });
        JButton jcomp11 = new JButton ("Add");
        if(type.equals("edit"))
        {
            jcomp11.setText("Edit");
        }
        jcomp11.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent v)
            {
                if(jcomp2.getText().length()==0)
                {
                    constants.alert(d, "warn", "Please enter user's name");
                    return;
                }
                if(jcomp4.getText().length()==0)
                {
                    constants.alert(d, "warn", "Click the green button and select sections");
                    return;
                }
                
                if(jcomp7.getText().length()==0)
                {
                    constants.alert(d, "warn", "Please generate pass code for user");
                    return;
                }
                JSONArray jsonArray = new JSONArray();
                if(!type.equals("edit"))
                {
                for(int x=0; x<table.getRowCount();x++)
                {
                    String user = table.getValueAt(x, 1).toString();
                    String sec = table.getValueAt(x, 2).toString();
                    String pass = table.getValueAt(x, 3).toString();
                    JSONObject obj = new JSONObject();
                    obj.put("user", user);
                    obj.put("section", sec);
                    obj.put("code", pass);
                    if(isAccess == true)
                    {
                        obj.put("type", "access");
                    }
                    else
                    {
                        obj.put("type", "cert");
                    }
                    jsonArray.add(obj);
                }
                JSONObject obj = new JSONObject();
                obj.put("user",jcomp2.getText());
                obj.put("section",jcomp4.getText());
                obj.put("code",jcomp7.getText());
                if(isAccess == true)
                    {
                        obj.put("type", "access");
                    }
                    else
                    {
                        obj.put("type", "cert");
                    }
                jsonArray.add(obj);
                }
                else
                {
                    table.setValueAt(jcomp2.getText(), table.getSelectedRow(), 1);
                    table.setValueAt(jcomp4.getText(), table.getSelectedRow(), 2);
                    table.setValueAt(jcomp7.getText(), table.getSelectedRow(), 3);
                     for(int x=0; x<table.getRowCount();x++)
                    {
                        String user = table.getValueAt(x, 1).toString();
                        String sec = table.getValueAt(x, 2).toString();
                        String pass = table.getValueAt(x, 3).toString();
                        JSONObject obj = new JSONObject();
                        obj.put("user", user);
                        obj.put("section", sec);
                        obj.put("code", pass);
                        if(isAccess == true)
                        {
                            obj.put("type", "access");
                        }
                        else
                        {
                            obj.put("type", "cert");
                        }
                            jsonArray.add(obj);
                        }
                }
                try
                {
                    FileWriter f = null;
                    if(isAccess == true)
                    {
                        f = new FileWriter("projects/"+name+"/accessList.jaj");
                    }
                    else
                    {
                        f = new FileWriter("projects/"+name+"/certList.jaj");
                    }
                    f.write(jsonArray.toJSONString());
                    f.flush();
                    f.close();
                }
                catch(Exception er)
                {
                    er.printStackTrace();
                }
                if(isAccess == true)
                {
                    SavedProject.this.getAccessList(table);
                }
                else
                {
                    SavedProject.this.getCertList(table);
                }
                dd.setEnabled(true);
                d.dispose();
                toast.showToaster(icon, "User added...");
                     
            }
        });
        a.setPreferredSize (new Dimension (264, 226));
        a.setLayout (null);
        a.add (jcomp1);
        a.add (jcomp2);
        a.add (jcomp3);
        a.add (s);
        a.add (jcomp5);
        a.add (jcomp6);
        a.add (jcomp7);
        a.add (jcomp8);
        a.add (pp);
        a.add (jcomp10);
        a.add (jcomp11);
        jcomp1.setBounds (5, 8, 35, 20);
        jcomp2.setBounds (77, 8, 180, 25);
        jcomp3.setBounds (5, 35, 65, 20);
        s.setBounds (77, 35, 155, 75);
        jcomp5.setBounds (235, 35, 20, 20);
        jcomp6.setBounds (5, 114, 65, 20);
        jcomp7.setBounds (77, 113, 180, 25);
        jcomp8.setBounds (156, 139, 100, 25);
        pp.setBounds (-45, 170, 325, 5);
        jcomp10.setBounds (160, 190, 100, 30);
        jcomp11.setBounds (77, 190, 80, 30);
        d.getContentPane().add("Center",a);
        d.pack();
        d.setLocationRelativeTo(parentFrame);
        d.setVisible(true);
        
    }
    
    
   class MyItemListener implements ItemListener
  {
       JTable table;
       public MyItemListener(JTable table)
       {
           this.table = table;
       }
    public void itemStateChanged(ItemEvent e) {
      Object source = e.getSource();
      if (source instanceof AbstractButton == false) return;
      boolean checked = e.getStateChange() == ItemEvent.SELECTED;
      for(int x = 0, y = table.getRowCount(); x < y; x++)
      {
        table.setValueAt(new Boolean(checked),x,0);
      }
    }
  }
   
   
   
   class CheckBoxHeader extends JCheckBox
    implements TableCellRenderer, MouseListener {
  protected CheckBoxHeader rendererComponent;
  protected int column;
  protected boolean mousePressed = false;
  public CheckBoxHeader(ItemListener itemListener) {
    rendererComponent = this;
    rendererComponent.addItemListener(itemListener);
  }
  public Component getTableCellRendererComponent(
      JTable table, Object value,
      boolean isSelected, boolean hasFocus, int row, int column) {
    if (table != null) {
      JTableHeader header = table.getTableHeader();
      if (header != null) {
        rendererComponent.setForeground(header.getForeground());
        rendererComponent.setBackground(header.getBackground());
        rendererComponent.setFont(header.getFont());
        header.addMouseListener(rendererComponent);
      }
    }
    setColumn(column);
    rendererComponent.setText("Check All");
    setBorder(UIManager.getBorder("TableHeader.cellBorder"));
    return rendererComponent;
  }
  protected void setColumn(int column) {
    this.column = column;
  }
  public int getColumn() {
    return column;
  }
  protected void handleClickEvent(MouseEvent e) {
    if (mousePressed) {
      mousePressed=false;
      JTableHeader header = (JTableHeader)(e.getSource());
      JTable tableView = header.getTable();
      TableColumnModel columnModel = tableView.getColumnModel();
      int viewColumn = columnModel.getColumnIndexAtX(e.getX());
      int column = tableView.convertColumnIndexToModel(viewColumn);
  
      if (viewColumn == this.column && e.getClickCount() == 1 && column != -1) {
        doClick();
      }
    }
  }
  public void mouseClicked(MouseEvent e) {
    handleClickEvent(e);
    ((JTableHeader)e.getSource()).repaint();
  }
  public void mousePressed(MouseEvent e) {
    mousePressed = true;
  }
  public void mouseReleased(MouseEvent e) {
  }
  public void mouseEntered(MouseEvent e) {
  }
  public void mouseExited(MouseEvent e) {
  }
}
    
    
    public void buildAccessPanel(JPanel panel, final JDialog d)
    {
        /*
        JPanel a = new JPanel();
        String[] jcomp2Items = {"Public", "Private"};

        //construct components
        JLabel jcomp1 = new JLabel ("<html><b>Project Access</b></html>");
        final JComboBox jcomp2 = new JComboBox (jcomp2Items);
        String access = "";
        try
        {
            access = main.readFile("projects/"+name+"/access.jaj");
            jcomp2.setSelectedItem(access);
        }catch(Exception er){}
        jcomp2.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent v)
            {
                try
                {
                    FileWriter f = new FileWriter("projects/"+name+"/access.jaj");
                    f.write(jcomp2.getSelectedItem().toString());
                    f.close();
                }catch(Exception er){}
            }
        });
        JPanel jcomp3 = new JPanel ();
        jcomp3.setBorder(etched);
        final JTable jcomp4 = new JTable ();
        getAccessList(jcomp4);
        TableColumn tc = jcomp4.getColumnModel().getColumn(0);
        tc.setHeaderRenderer(new CheckBoxHeader(new MyItemListener(jcomp4)));
        JScrollPane s = new JScrollPane(jcomp4);
        final JButton jcomp5 = new JButton ("Delete");
        final JButton jcomp6 = new JButton ("Edit");
        final JButton jcomp7 = new JButton ("Add");
        jcomp5.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent v)
            {
                if(jcomp4.getSelectedRowCount()<1)
                {
                    constants.alert(d, "warn", "Please select users to delete");
                    return;
                }
                for(int t=0;t<jcomp4.getRowCount();t++)
                {
                    Boolean b = (Boolean)jcomp4.getValueAt(t, 0);
                    if(b==true)
                    {
                        DefaultTableModel dt = (DefaultTableModel)jcomp4.getModel();
                        dt.removeRow(t);
                        System.out.println("row removed");
                    }
                }
                 JSONArray jsonArray = new JSONArray();
                for(int x=0; x<jcomp4.getRowCount();x++)
                {
                    String user = jcomp4.getValueAt(x, 1).toString();
                    String sec = jcomp4.getValueAt(x, 2).toString();
                    String pass = jcomp4.getValueAt(x, 3).toString();
                    JSONObject obj = new JSONObject();
                    obj.put("user", user);
                    obj.put("section", sec);
                    obj.put("code", pass);
                    jsonArray.add(obj);
                }
                 try
                {
                    FileWriter f = new FileWriter("projects/"+name+"/accessList.jaj");
                    f.write(jsonArray.toJSONString());
                    f.flush();
                    f.close();
                }
                catch(Exception er)
                {
                    er.printStackTrace();
                }
                getAccessList(jcomp4);
                
            }
        });
        
        if(jcomp2.getSelectedIndex()==0)
        {
            jcomp4.setEnabled(false);
                    jcomp5.setEnabled(false);
                    jcomp6.setEnabled(false);
                    jcomp7.setEnabled(false);
                    
        }
        jcomp7.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent v)
            {
                addUserAccess(d, jcomp4, "new", true);
            }
        });
        jcomp6.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent v)
            {
                addUserAccess(d, jcomp4, "edit", true);
            }
        });
        jcomp2.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent v)
            {
                if(jcomp2.getSelectedIndex()==0)
                {
                    jcomp4.setEnabled(false);
                    jcomp5.setEnabled(false);
                    jcomp6.setEnabled(false);
                    jcomp7.setEnabled(false);
                }
                else
                {
                    jcomp4.setEnabled(true);
                    jcomp5.setEnabled(true);
                    jcomp6.setEnabled(true);
                    jcomp7.setEnabled(true);
                }
            }
        });
        a.setPreferredSize (new Dimension (365, 460));
        a.setLayout (null);
        //add components
        a.add (jcomp1);
        a.add (jcomp2);
        a.add (jcomp3);
        a.add (s);
        a.add (jcomp5);
        a.add (jcomp6);
        a.add (jcomp7);

        //set component bounds (only needed by Absolute Positioning)
        jcomp1.setBounds (20, 11, 100, 25);
        jcomp2.setBounds (130, 13, 210, 25);
        jcomp3.setBounds (0, 49, 367, 5);
        s.setBounds (20, 60, 330, 355);
        jcomp5.setBounds (248, 422, 100, 30);
        jcomp6.setBounds (134, 422, 100, 30);
        jcomp7.setBounds (22, 422, 100, 30);
        */
        panel.add("Access", UserAccess(d));
    }
    
    public class FileListCellRenderer extends DefaultListCellRenderer {

    private static final long serialVersionUID = -7799441088157759804L;
    private FileSystemView fileSystemView;
    private JLabel label;
    private Color textSelectionColor = Color.BLACK;
    private Color backgroundSelectionColor = Color.CYAN;
    private Color textNonSelectionColor = Color.BLACK;
    private Color backgroundNonSelectionColor = Color.WHITE;
    boolean type = false;

    FileListCellRenderer(boolean type) {
        label = new JLabel();
        label.setOpaque(true);
        fileSystemView = FileSystemView.getFileSystemView();
        this.type = type;
    }

    @Override
    public Component getListCellRendererComponent(
        JList list,
        Object value,
        int index,
        boolean selected,
        boolean expanded) {
        
        ImageIcon ic = null;
        if(type == true)
        {
            ic = new ImageIcon(getClass().getResource("images/users.png"));
        }
        else
        {
            ic = new ImageIcon(getClass().getResource("images/cf.png"));
        }
        label.setText(value.toString());
        label.setIcon(ic);
        label.setToolTipText(value.toString());

        if (selected) {
            label.setBackground(list.getSelectionBackground());
            label.setForeground(list.getSelectionForeground());
        } else {
            label.setBackground(backgroundNonSelectionColor);
            label.setForeground(textNonSelectionColor);
        }

        return label;
    }
}
    
    
    
    
     public void getAccessCert(final JList lt, String type, JList c, JList sections)
    {
        try
        {
                lt.setListData(new Vector());
                String users = main.readAccessCert(name, type);
                List<Access> details = null;
                try
                {
                    Gson gson = new Gson();
                    Type collectionType = new TypeToken<List<Access>>(){}.getType();
                    details = gson.fromJson(users, collectionType);
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
                DefaultListModel  md = new DefaultListModel();
                DefaultListModel  sec = new DefaultListModel();
                DefaultListModel  code = new DefaultListModel();

                for (Access detail : details)
                {
                        md.addElement(detail.getUser());
                        sec.addElement(detail.getSection());
                        code.addElement(detail.getCode());
                }
                lt.setModel(md);
                c.setModel(code);
                sections.setModel(sec);
                
        }
        catch(Exception er)
        {
                er.printStackTrace();
        }
    }
     
     
     
     public void addNewUserAccess(final JDialog dd, final JList table, final JList code, final JList sections, final String type, final boolean isAccess)
    {
        final EscapeDialog d= new EscapeDialog(dd, true);
	d.setTitle("");
	d.setResizable(false);
	d.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
	d.addWindowListener(new WindowAdapter(){
	public void windowClosing(WindowEvent e){dd.setEnabled(true);d.dispose();}});
        JPanel jc1 = new JPanel (new FlowLayout(FlowLayout.RIGHT));
        jc1.setBorder(Main.etched);
        //jc1.setBackground(Color.white);
        BlurryLabel ll= new BlurryLabel();
        ll.setText("Add User");
        if(type.equals("edit"))
        {
            ll.setText("Edit User");
        }
        ll.setFont(ll.getFont().deriveFont(18f));
        ll.setDrawBlur(true);
        ll.setBorder(new EmptyBorder(0, 5, 2, 5));
        ll.setIcon(new ImageIcon(getClass().getResource("images/users.png")));
        jc1.add(ll);
        d.getContentPane().add("North",jc1);
        
        JPanel a = new JPanel();
        JLabel jcomp1 = new JLabel ("User");
        final JTextField jcomp2 = new JTextField (5);
        JLabel jcomp3 = new JLabel ("Section (s)");
        final JTextArea jcomp4 = new JTextArea (5, 5);
        jcomp4.setEditable(false);
        jcomp4.setLineWrap(true);
        jcomp4.setWrapStyleWord(true);
        JScrollPane s = new JScrollPane(jcomp4);
        final DropDownButton jcomp5 = new DropDownButton (new ImageIcon(getClass().getResource("images/switch.gif")));
        JPanel x = getDropSectionPanel(jcomp4,d);
        jcomp5.addComponent(x);
        JLabel jcomp6 = new JLabel ("Pass Code");
        final JTextField jcomp7 = new JTextField (5);
        jcomp7.setEditable(false);
        JButton jcomp8 = new JButton ("Generate");
        if(type.equals("edit"))
        {
            jcomp2.setText(table.getSelectedValue().toString());
            jcomp4.setText(sections.getSelectedValue().toString());
            jcomp7.setText(code.getSelectedValue().toString());
        }
        jcomp8.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent v)
            {
                jcomp7.setText(constants.randomNumeric(6));
            }
        });
        JPanel pp = new JPanel ();
        pp.setBorder(etched);
        JButton jcomp10 = new JButton ("Cancel");
        jcomp10.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent v)
            {
                dd.setEnabled(true);
                d.dispose();
            }
        });
        JButton jcomp11 = new JButton ("Add");
        if(type.equals("edit"))
        {
            jcomp11.setText("Edit");
        }
        jcomp11.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent v)
            {
                if(jcomp2.getText().length()==0)
                {
                    constants.alert(d, "warn", "Please enter user's name");
                    return;
                }
                if(jcomp4.getText().length()==0)
                {
                    constants.alert(d, "warn", "Click the green button and select sections");
                    return;
                }
                
                if(jcomp7.getText().length()==0)
                {
                    constants.alert(d, "warn", "Please generate pass code for user");
                    return;
                }
                JSONArray jsonArray = new JSONArray();
                if(!type.equals("edit"))
                {
                for(int x=0; x<table.getModel().getSize();x++)
                {
                    table.setSelectedIndex(x);
                    sections.setSelectedIndex(x);
                    code.setSelectedIndex(x);
                    String user = table.getSelectedValue().toString();
                    String sec = sections.getSelectedValue().toString();
                    String pass = code.getSelectedValue().toString();
                    JSONObject obj = new JSONObject();
                    obj.put("user", user);
                    obj.put("section", sec);
                    obj.put("code", pass);
                    if(isAccess == true)
                    {
                        obj.put("type", "access");
                    }
                    else
                    {
                        obj.put("type", "cert");
                    }
                    jsonArray.add(obj);
                }
                JSONObject obj = new JSONObject();
                obj.put("user",jcomp2.getText());
                obj.put("section",jcomp4.getText());
                obj.put("code",jcomp7.getText());
                if(isAccess == true)
                    {
                        obj.put("type", "access");
                    }
                    else
                    {
                        obj.put("type", "cert");
                    }
                jsonArray.add(obj);
                }
                else
                {
                    y = table.getSelectedIndex();
                    DefaultListModel m = (DefaultListModel) table.getModel(); 
                    DefaultListModel s = (DefaultListModel) sections.getModel();
                    DefaultListModel c = (DefaultListModel) code.getModel();
                    m.setElementAt(jcomp2.getText(), table.getSelectedIndex());
                    s.setElementAt(jcomp4.getText(), sections.getSelectedIndex());
                    c.setElementAt(jcomp7.getText(), code.getSelectedIndex());

                    for(int x=0; x<m.getSize();x++)
                    {
                        table.setSelectedIndex(x);
                        sections.setSelectedIndex(x);
                        code.setSelectedIndex(x);
                        String user = table.getSelectedValue().toString();
                        String sec = sections.getSelectedValue().toString();
                        String pass = code.getSelectedValue().toString();
                        JSONObject obj = new JSONObject();
                        obj.put("user", user);
                        obj.put("section", sec);
                        obj.put("code", pass);
                        if(isAccess == true)
                        {
                            obj.put("type", "access");
                        }
                        else
                        {
                            obj.put("type", "cert");
                        }
                            jsonArray.add(obj);
                    }
                    
                }
                try
                {
                    FileWriter f = null;
                    if(isAccess == true)
                    {
                        f = new FileWriter("projects/"+name+"/accessList.jaj");
                    }
                    else
                    {
                        f = new FileWriter("projects/"+name+"/certList.jaj");
                    }
                    f.write(jsonArray.toJSONString());
                    f.flush();
                    f.close();
                }
                catch(Exception er)
                {
                    er.printStackTrace();
                }
                
                if(isAccess == true)
                {
                    getAccessCert(table, "accessList.jaj", code, sections);
                    if(type.equals("edit"))
                    {  
                        table.setSelectedIndex(y);
                        code.setSelectedIndex(y);
                        sections.setSelectedIndex(y);
                    }
                }
                else
                {
                    getAccessCert(table, "certList.jaj", code, sections);
                    if(type.equals("edit"))
                    {  
                        table.setSelectedIndex(y);
                        code.setSelectedIndex(y);
                        sections.setSelectedIndex(y);
                    }
                }
                
                dd.setEnabled(true);
                d.dispose();
                toast.showToaster(icon, "User added...");
                     
            }
        });
        a.setPreferredSize (new Dimension (264, 226));
        a.setLayout (null);
        a.add (jcomp1);
        a.add (jcomp2);
        a.add (jcomp3);
        a.add (s);
        a.add (jcomp5);
        a.add (jcomp6);
        a.add (jcomp7);
        a.add (jcomp8);
        a.add (pp);
        a.add (jcomp10);
        a.add (jcomp11);
        jcomp1.setBounds (5, 8, 35, 20);
        jcomp2.setBounds (77, 8, 180, 25);
        jcomp3.setBounds (5, 35, 65, 20);
        s.setBounds (77, 35, 155, 75);
        jcomp5.setBounds (235, 35, 20, 20);
        jcomp6.setBounds (5, 114, 65, 20);
        jcomp7.setBounds (77, 113, 180, 25);
        jcomp8.setBounds (156, 139, 100, 25);
        pp.setBounds (-45, 170, 325, 5);
        jcomp10.setBounds (160, 190, 100, 30);
        jcomp11.setBounds (77, 190, 80, 30);
        d.getContentPane().add("Center",a);
        d.pack();
        d.setLocationRelativeTo(parentFrame);
        d.setVisible(true);
        
    }
    
    
    
    int y = 0;
    
    public void filterModel(DefaultListModel<String> model, String filter) {
        
        List lx = new ArrayList();
        for(int t=0; t<model.getSize();t++)
        {
            lx.add((Object)model.elementAt(t));
        }
        
        for (Object d : lx) {
            String s = d.toString();
            System.out.println(d);
            if (!s.startsWith(filter)) {
                if (model.contains(s)) {
                    model.removeElement(s);
                }
            } else {
                if (!model.contains(s)) {
                    model.addElement(s);
                }
            }
        }
    }
    
    public JPanel UserAccess(final JDialog d)
    {
        JPanel a = new JPanel();
        JScrollPane jScrollPane1 = new javax.swing.JScrollPane();
        final JList jList1 = new javax.swing.JList<>();
        final JTextField jTextField2 = new javax.swing.JTextField();
        JLabel jLabel2 = new javax.swing.JLabel();
        JLabel jLabel3 = new javax.swing.JLabel();
        JTextField jTextField3 = new javax.swing.JTextField();
        JButton jButton1 = new javax.swing.JButton();
        JButton jButton2 = new javax.swing.JButton();
        JButton jButton3 = new javax.swing.JButton();
        JPanel jPanel1 = new javax.swing.JPanel();
        JScrollPane jScrollPane2 = new javax.swing.JScrollPane();
        JTextArea jTextArea1 = new javax.swing.JTextArea();
        JPanel pan = new javax.swing.JPanel();
        JComboBox pcom = new javax.swing.JComboBox<>();
        pcom.addItem("Public");
        pcom.addItem("Private");
        jScrollPane1.setViewportView(jList1);
        JTable t = new JTable();
        jList1.setCellRenderer(new FileListCellRenderer(true));
        jList1.setModel(new DefaultListModel());
        final JList sections = new JList();
        final JList code = new JList();
        getAccessCert(jList1,"accessList.jaj", code, sections);
        jList1.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent v)
            {
                sections.setSelectedIndex(jList1.getSelectedIndex());
                code.setSelectedIndex(jList1.getSelectedIndex());
                jTextField3.setText(code.getSelectedValue().toString());
                jTextArea1.setText(sections.getSelectedValue().toString());
            }
        });
        
       
        
        jList1.addKeyListener(new KeyAdapter()
        {
            public void keyReleased(KeyEvent v)
            {
                sections.setSelectedIndex(jList1.getSelectedIndex());
                code.setSelectedIndex(jList1.getSelectedIndex());
                jTextField3.setText(code.getSelectedValue().toString());
                jTextArea1.setText(sections.getSelectedValue().toString());
            }
        });
        String access = "";
        try
        {
            access = main.readFile("projects/"+name+"/access.jaj");
            pcom.setSelectedItem(access);
        }catch(Exception er){}
        pcom.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent v)
            {
                try
                {
                    FileWriter f = new FileWriter("projects/"+name+"/access.jaj");
                    f.write(pcom.getSelectedItem().toString());
                    f.close();
                }catch(Exception er){}
            }
        });
        

        jLabel2.setText("");
        jLabel2.setIcon(new ImageIcon(getClass().getResource("images/search.png")));
        jTextField3.setEditable(false);
        jLabel3.setText("Passcode");
        jTextArea1.setBorder(null);
        jTextArea1.setLineWrap(true);
        jTextArea1.setWrapStyleWord(true);
        jTextArea1.setBackground(a.getBackground());
        jTextArea1.setEditable(false);
        jScrollPane2.setBorder(null);
        jScrollPane2.setBackground(null);
        
        

        jButton1.setText("Edit");

        jButton2.setText("Delete");
        
        jButton2.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent v)
            {
                if(jList1.getSelectedIndex() < 0)
                {
                    return;
                }
                int x = JOptionPane.showConfirmDialog(d, "Are you sure you want to delete?",Constants.APP_NAME, JOptionPane.YES_NO_CANCEL_OPTION);
                if(x==JOptionPane.CANCEL_OPTION)
                {
                    return;
                }
                if(x==JOptionPane.YES_OPTION)
                {
                    int z = jList1.getSelectedIndex();
                    DefaultListModel md =(DefaultListModel) jList1.getModel();
                    DefaultListModel c =(DefaultListModel) code.getModel();
                    DefaultListModel s =(DefaultListModel) sections.getModel();
                    md.removeElementAt(jList1.getSelectedIndex());
                    s.removeElementAt(sections.getSelectedIndex());
                    c.removeElementAt(code.getSelectedIndex());
                    
                    JSONArray jsonArray = new JSONArray();
                    for(int y=0; x<jList1.getModel().getSize();x++)
                    {
                        jList1.setSelectedIndex(x);
                        sections.setSelectedIndex(x);
                        code.setSelectedIndex(x);
                        String user = jList1.getSelectedValue().toString();
                        String sec = sections.getSelectedValue().toString();
                        String pass = code.getSelectedValue().toString();
                        JSONObject obj = new JSONObject();
                        obj.put("user", user);
                        obj.put("section", sec);
                        obj.put("code", pass);
                        obj.put("type", "access");
                        jsonArray.add(obj);
                    }
                    try{
                    FileWriter f = new FileWriter("projects/"+name+"/accessList.jaj");
                    f.write(jsonArray.toJSONString());
                    f.flush();
                    f.close();}catch(Exception er){}
                    jList1.setSelectedIndex(z);
                    sections.setSelectedIndex(z);
                    code.setSelectedIndex(z);
                }
            }
        });

        jButton3.setText("Add");
        
        jButton3.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent v)
            {
                addNewUserAccess(d, jList1, code, sections, "new",true);
            }
        });
        
        jButton1.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent v)
            {
                addNewUserAccess(d, jList1, code, sections, "edit", true);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Authorized Sections"));

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane2.setViewportView(jTextArea1);

       javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pan.setBorder(javax.swing.BorderFactory.createTitledBorder("Project Access"));

        javax.swing.GroupLayout panLayout = new javax.swing.GroupLayout(pan);
        pan.setLayout(panLayout);
        panLayout.setHorizontalGroup(
            panLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pcom, 0, 160, Short.MAX_VALUE)
                .addContainerGap())
        );
        panLayout.setVerticalGroup(
            panLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(pcom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(45, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(a);
        a.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField2))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextField3)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(pan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(pan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        
        return a;
    }// </editor-fold>                        

    
    
    public JPanel testAccess(final JDialog d)
    {
        JPanel a = new JPanel();
        JButton jButton2 = new javax.swing.JButton();
        JPanel panel1 = new javax.swing.JPanel();
        JLabel jLabel1 = new javax.swing.JLabel();
        final JComboBox jComboBox1 = new javax.swing.JComboBox<>();
        JPanel panel2 = new javax.swing.JPanel();
        JScrollPane jScrollPane1 = new javax.swing.JScrollPane();
        final JTable jTable1 = new javax.swing.JTable();
        JButton jButton1 = new javax.swing.JButton();
        JButton jButton3 = new javax.swing.JButton();
        JButton jButton4 = new javax.swing.JButton();
        JPanel pan = new javax.swing.JPanel();
        JComboBox pcom = new javax.swing.JComboBox<>();

        jButton2.setText("jButton2");

        jLabel1.setText("Project Access");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Public", "Private" }));

        String access = "";
        try
        {
            access = main.readFile("projects/"+name+"/access.jaj");
            jComboBox1.setSelectedItem(access);
        }catch(Exception er){}
        jComboBox1.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent v)
            {
                try
                {
                    FileWriter f = new FileWriter("projects/"+name+"/access.jaj");
                    f.write(jComboBox1.getSelectedItem().toString());
                    f.close();
                }catch(Exception er){}
            }
        });
        
        
        javax.swing.GroupLayout panel1Layout = new javax.swing.GroupLayout(panel1);
        panel1.setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        jScrollPane1.setViewportView(jTable1);
        getAccessList(jTable1);
        jButton1.setText("Delete");
        jButton1.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent v)
            {
                if(jTable1.getSelectedRowCount()<1)
                {
                    constants.alert(d, "warn", "Please select users to delete");
                    return;
                }
                for(int t=0;t<jTable1.getRowCount();t++)
                {
                    Boolean b = (Boolean)jTable1.getValueAt(t, 0);
                    if(b==true)
                    {
                        DefaultTableModel dt = (DefaultTableModel)jTable1.getModel();
                        dt.removeRow(t);
                    }
                }
                 JSONArray jsonArray = new JSONArray();
                for(int x=0; x<jTable1.getRowCount();x++)
                {
                    String user = jTable1.getValueAt(x, 1).toString();
                    String sec = jTable1.getValueAt(x, 2).toString();
                    String pass = jTable1.getValueAt(x, 3).toString();
                    JSONObject obj = new JSONObject();
                    obj.put("user", user);
                    obj.put("section", sec);
                    obj.put("code", pass);
                    obj.put("type", "access");
                    jsonArray.add(obj);
                }
                 try
                {
                    FileWriter f = new FileWriter("projects/"+name+"/accessList.jaj");
                    f.write(jsonArray.toJSONString());
                    f.flush();
                    f.close();
                }
                catch(Exception er)
                {
                    er.printStackTrace();
                }
                getAccessList(jTable1);
                
            }
        });

        panel1.setBorder(new TitledBorder("Project Access"));
        panel2.setBorder(new TitledBorder("Authorized Collectors"));
        jButton3.setText("Edit");

        jButton4.setText("Add");
        
        jButton4.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent v)
            {
                addUserAccess(d, jTable1, "new", true);
            }
        });
        jButton3.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent v)
            {
                addUserAccess(d, jTable1, "edit", true);
            }
        });

        javax.swing.GroupLayout panel2Layout = new javax.swing.GroupLayout(panel2);
        panel2.setLayout(panel2Layout);
        panel2Layout.setHorizontalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 468, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel2Layout.createSequentialGroup()
                        .addComponent(jButton4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)))
                .addContainerGap())
        );

        panel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jButton1, jButton3, jButton4});

        panel2Layout.setVerticalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 339, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton3)
                    .addComponent(jButton4))
                .addContainerGap(27, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(a);
        a.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(panel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        return a;
    }
    
    public JPanel testGeneral(final JDialog d)
    {
        JPanel a= new JPanel();
        
        JPanel panel1 = new javax.swing.JPanel();
        final JCheckBox jCheckBox1 = new javax.swing.JCheckBox();
        JLabel jLabel1 = new javax.swing.JLabel();
        JPanel panel2 = new javax.swing.JPanel();
        JLabel jLabel2 = new javax.swing.JLabel();
        final JTextField jTextField1 = new javax.swing.JTextField();
        JLabel jLabel3 = new javax.swing.JLabel();
        final JCheckBox jCheckBox2 = new javax.swing.JCheckBox();
        final JCheckBox jCheckBox3 = new javax.swing.JCheckBox();
        JPanel panel3 = new javax.swing.JPanel();
        JButton jButton1 = new javax.swing.JButton();
        JButton jButton2 = new javax.swing.JButton();
        JButton jButton3 = new javax.swing.JButton();
        JButton jButton4 = new javax.swing.JButton();
        JSeparator jSeparator1 = new javax.swing.JSeparator();
        JLabel jLabel4 = new javax.swing.JLabel();
        JLabel jLabel5 = new javax.swing.JLabel();
        File f = new File("projects/"+name+"/autoSave.jaj");
        if(f.exists())
        {
            jCheckBox1.setSelected(true);
        }
        jCheckBox1.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                if(jCheckBox1.isSelected())
                {
                    
                        FileWriter f = new FileWriter("projects/"+name+"/autoSave.jaj");
                        f.write("");
                        f.flush();
                        f.close();
                } 
                else
                {
                  File f = new File("projects/"+name+"/autoSave.jaj")  ;
                  f.delete();
                }
                }
                catch(Exception er){er.printStackTrace();}
            }
        });

        jTextField1.addFocusListener(new FocusAdapter()
        {
            @Override
            public void focusLost(FocusEvent v)
            {
                Project p = new Project();
                p.setName(name+"##"+jTextField1.getText());
                String target = "";
                if(jCheckBox2.isSelected() == true)
                {
                    target = "Web@@Mobile";
                }
                else
                {
                    target = "Mobile";
                }
                p.setTarget(target);
                p.setProject_date(date);
                p.setPid(pid);
                main.updateProject(p);
                name = jTextField1.getText();
                setTitle(name);
            }
        });
        jTextField1.setText(name);
        jCheckBox3.setSelected(true);
        if(target.contains("Web"))
        {
            jCheckBox2.setSelected(true);
        }
        jCheckBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                
                Project p = new Project();
                p.setName(name+"##"+jTextField1.getText());
                String t = "";
                if(jCheckBox2.isSelected() == true)
                {
                    t = "Web@@Mobile";
                }
                else
                {
                    t = "Mobile";
                }
                p.setTarget(t);
                p.setProject_date(date);
                p.setPid(pid);
                main.updateProject(p);
                target = t;
                setTitle(name);
            }
        });
        
        panel1.setBorder(new TitledBorder("Auto Save"));
        panel2.setBorder(new TitledBorder("Project Details"));
        panel3.setBorder(new TitledBorder("Options"));

        jCheckBox1.setText("Enable");
        
        jLabel1.setText("This will turn on automatic saving for this project.");

        javax.swing.GroupLayout panel1Layout = new javax.swing.GroupLayout(panel1);
        panel1.setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCheckBox1)
                    .addComponent(jLabel1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jCheckBox1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addContainerGap())
        );

        jLabel2.setText("Project Name");

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                
            }
        });

        jLabel3.setText("Build Target");

        jCheckBox2.setText("Web");
        jCheckBox3.setText("Mobile");
        jCheckBox3.setEnabled(false);
        

        javax.swing.GroupLayout panel2Layout = new javax.swing.GroupLayout(panel2);
        panel2.setLayout(panel2Layout);
        panel2Layout.setHorizontalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panel2Layout.createSequentialGroup()
                        .addComponent(jCheckBox2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jCheckBox3)))
                .addContainerGap())
        );
        panel2Layout.setVerticalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel2Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox2)
                    .addComponent(jLabel3)
                    .addComponent(jCheckBox3))
                .addContainerGap())
        );

        jButton1.setText("Delete Project");
        jButton1.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent v)
            {
                deleteProject(d);
            }
        });

        jButton2.setText("Import Section");
        jButton2.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent v)
            {
                importSection();
            }
        });

        jButton3.setText("Import Project");
        jButton3.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent v)
            {
                try{importProjectAsSection(d);}catch(Exception e){}
            }
        });

        jButton4.setText("Export Project");
        jButton4.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent v)
            {
                try
                {
                   String path = "";
                        chooser= new JDirectoryChooser();
                        chooser.setDialogTitle("Export Project To...");
                        chooser.setMultiSelectionEnabled(false);
                        int r=chooser.showOpenDialog(parentFrame);
                        if(r==JDirectoryChooser.APPROVE_OPTION)
                        {
                            File[] selectedFiles = chooser.getSelectedFiles();
                            for (int i = 0, c = selectedFiles.length; i < c; i++)
                            {
                                path += selectedFiles[i];
                            }
                        }
                        else
                        {
                            return;
                        };
                    exportProject("projects/"+jTextField1.getText(), path, true);
                }catch(Exception er){er.printStackTrace();}
            }
        });

        jLabel4.setText("Note: Project cannot be recovered once deleted.");

        jLabel5.setText("You can import a project as a section of another project.");

        javax.swing.GroupLayout panel3Layout = new javax.swing.GroupLayout(panel3);
        panel3.setLayout(panel3Layout);
        panel3Layout.setHorizontalGroup(
            panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(panel3Layout.createSequentialGroup()
                .addGroup(panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel4))
                    .addGroup(panel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel5))
                    .addGroup(panel3Layout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addGroup(panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(panel3Layout.createSequentialGroup()
                                .addComponent(jButton1)
                                .addGap(30, 30, 30)
                                .addComponent(jButton3))
                            .addGroup(panel3Layout.createSequentialGroup()
                                .addComponent(jButton2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton4)))))
                .addContainerGap(51, Short.MAX_VALUE))
        );

        panel3Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jButton1, jButton2});

        panel3Layout.setVerticalGroup(
            panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1)
                    .addComponent(jButton3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton4)
                    .addComponent(jButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(a);
        a.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(38, Short.MAX_VALUE))
        );
        return a;
    }
    
    
    public void buildCertPanel(JPanel panel, final JDialog d)
    {
        
        /*
        JPanel a = new JPanel();
        //construct components
        JLabel jcomp1 = new JLabel ("<html><b>Enter the details of certifying officers below (if any)</b></html>");   
        JPanel jcomp3 = new JPanel ();
        jcomp3.setBorder(etched);
        final JTable jcomp4 = new JTable ();
        getCertList(jcomp4);
        TableColumn tc = jcomp4.getColumnModel().getColumn(0);
        tc.setHeaderRenderer(new CheckBoxHeader(new MyItemListener(jcomp4)));
        JScrollPane s = new JScrollPane(jcomp4);
        final JButton jcomp5 = new JButton ("Delete");
        final JButton jcomp6 = new JButton ("Edit");
        final JButton jcomp7 = new JButton ("Add");
        jcomp5.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent v)
            {
                if(jcomp4.getSelectedRowCount()<1)
                {
                    constants.alert(d, "warn", "Please select users to delete");
                    return;
                }
                for(int t=0;t<jcomp4.getRowCount();t++)
                {
                    Boolean b = (Boolean)jcomp4.getValueAt(t, 0);
                    if(b==true)
                    {
                        DefaultTableModel dt = (DefaultTableModel)jcomp4.getModel();
                        dt.removeRow(t);
                    }
                }
                 JSONArray jsonArray = new JSONArray();
                for(int x=0; x<jcomp4.getRowCount();x++)
                {
                    String user = jcomp4.getValueAt(x, 1).toString();
                    String sec = jcomp4.getValueAt(x, 2).toString();
                    String pass = jcomp4.getValueAt(x, 3).toString();
                    JSONObject obj = new JSONObject();
                    obj.put("user", user);
                    obj.put("section", sec);
                    obj.put("code", pass);
                    jsonArray.add(obj);
                }
                 try
                {
                    FileWriter f = new FileWriter("projects/"+name+"/certList.jaj");
                    f.write(jsonArray.toJSONString());
                    f.flush();
                    f.close();
                }
                catch(Exception er)
                {
                    er.printStackTrace();
                }
                getAccessList(jcomp4);
                
            }
        });
        
      
        jcomp7.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent v)
            {
                addUserAccess(d, jcomp4, "new", false);
            }
        });
        jcomp6.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent v)
            {
                addUserAccess(d, jcomp4, "edit", false);
            }
        });
       
        a.setPreferredSize (new Dimension (365, 460));
        a.setLayout (null);
        //add components
        a.add (jcomp1);
        a.add (jcomp3);
        a.add (s);
        a.add (jcomp5);
        a.add (jcomp6);
        a.add (jcomp7);

        //set component bounds (only needed by Absolute Positioning)
        jcomp1.setBounds (20, 11, 600, 25);
        jcomp3.setBounds (0, 49, 367, 5);
        s.setBounds (20, 60, 330, 355);
        jcomp5.setBounds (248, 422, 100, 30);
        jcomp6.setBounds (134, 422, 100, 30);
        jcomp7.setBounds (22, 422, 100, 30);
        */
        panel.add("Data Certification",UserCert(d));
    }
    
    
    public JPanel UserCert(final JDialog d)
    {
        JPanel a = new JPanel();
        
        
        
        final JTextField jTextField2 = new javax.swing.JTextField();
        JLabel jLabel2 = new javax.swing.JLabel();
        JLabel jLabel3 = new javax.swing.JLabel();
        final JTextField jTextField3 = new javax.swing.JTextField();
        JButton jButton1 = new javax.swing.JButton();
        JButton jButton2 = new javax.swing.JButton();
        JButton jButton3 = new javax.swing.JButton();
        JPanel jPanel1 = new javax.swing.JPanel();
        JScrollPane jScrollPane2 = new javax.swing.JScrollPane();
        final JTextArea jTextArea1 = new javax.swing.JTextArea();
        JScrollPane jScrollPane3 = new javax.swing.JScrollPane();
        final JList jList2 = new javax.swing.JList<>();
        jList2.setCellRenderer(new FileListCellRenderer(false));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/jajitech/desktop/xdata/images/search.png"))); // NOI18N
        
        jTextArea1.setBorder(null);
        jTextArea1.setLineWrap(true);
        jTextArea1.setWrapStyleWord(true);
        jTextArea1.setBackground(a.getBackground());
        jTextArea1.setEditable(false);
        jScrollPane2.setBorder(null);
        jScrollPane2.setBackground(null);
        
        JList code = new JList();
        JList sections = new JList();
        getAccessCert(jList2,"certList.jaj", code, sections);
        
        jList2.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent v)
            {
                sections.setSelectedIndex(jList2.getSelectedIndex());
                code.setSelectedIndex(jList2.getSelectedIndex());
                jTextField3.setText(code.getSelectedValue().toString());
                jTextArea1.setText(sections.getSelectedValue().toString());
            }
        });
        
       
        
        jList2.addKeyListener(new KeyAdapter()
        {
            public void keyReleased(KeyEvent v)
            {
                sections.setSelectedIndex(jList2.getSelectedIndex());
                code.setSelectedIndex(jList2.getSelectedIndex());
                jTextField3.setText(code.getSelectedValue().toString());
                jTextArea1.setText(sections.getSelectedValue().toString());
            }
        });

        jLabel3.setText("Passcode");

        jButton1.setText("Edit");

        jButton2.setText("Delete");
        
        
        jButton2.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent v)
            {
                if(jList2.getSelectedIndex() < 0)
                {
                    return;
                }
                int x = JOptionPane.showConfirmDialog(d, "Are you sure you want to delete?",Constants.APP_NAME, JOptionPane.YES_NO_CANCEL_OPTION);
                if(x==JOptionPane.CANCEL_OPTION)
                {
                    return;
                }
                if(x==JOptionPane.YES_OPTION)
                {
                    int z = jList2.getSelectedIndex();
                    DefaultListModel md =(DefaultListModel) jList2.getModel();
                    DefaultListModel c =(DefaultListModel) code.getModel();
                    DefaultListModel s =(DefaultListModel) sections.getModel();
                    md.removeElementAt(jList2.getSelectedIndex());
                    s.removeElementAt(sections.getSelectedIndex());
                    c.removeElementAt(code.getSelectedIndex());
                    
                    JSONArray jsonArray = new JSONArray();
                    for(int y=0; x<jList2.getModel().getSize();x++)
                    {
                        jList2.setSelectedIndex(x);
                        sections.setSelectedIndex(x);
                        code.setSelectedIndex(x);
                        String user = jList2.getSelectedValue().toString();
                        String sec = sections.getSelectedValue().toString();
                        String pass = code.getSelectedValue().toString();
                        JSONObject obj = new JSONObject();
                        obj.put("user", user);
                        obj.put("section", sec);
                        obj.put("code", pass);
                        obj.put("type", "access");
                        jsonArray.add(obj);
                    }
                    try{
                    FileWriter f = new FileWriter("projects/"+name+"/accessList.jaj");
                    f.write(jsonArray.toJSONString());
                    f.flush();
                    f.close();}catch(Exception er){}
                    jList2.setSelectedIndex(z);
                    sections.setSelectedIndex(z);
                    code.setSelectedIndex(z);
                }
            }
        });
        

        jButton3.setText("Add");
                
        jButton3.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent v)
            {
                addNewUserAccess(d, jList2, code, sections, "new",false);
            }
        });
        
        jButton1.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent v)
            {
                addNewUserAccess(d, jList2, code, sections, "edit", false);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Sections To Certify"));

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane2.setViewportView(jTextArea1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );


        jScrollPane3.setViewportView(jList2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(a);
        a.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField2))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton2))
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 331, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap(31, Short.MAX_VALUE))
        );
        return a;
    }
            
    
    
    public JPanel testCert(final JDialog d)
    {
        JPanel a = new JPanel();
        
        JPanel mainPanel = new javax.swing.JPanel();
        JLabel jLabel1 = new javax.swing.JLabel();
        JSeparator jSeparator1 = new javax.swing.JSeparator();
        JScrollPane jScrollPane2 = new javax.swing.JScrollPane();
        final JTable mainTable = new javax.swing.JTable();
        getCertList(mainTable);
        JButton jButton2 = new javax.swing.JButton();
        JButton jButton3 = new javax.swing.JButton();
        JButton jButton4 = new javax.swing.JButton();
        jLabel1.setText("Enter the details of certifying officers by using the button options below.");
        jScrollPane2.setViewportView(mainTable);
        jButton2.setText("Delete");
        jButton3.setText("Edit");
        jButton4.setText("Add");
        
        
        jButton2.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent v)
            {
                if(mainTable.getSelectedRowCount()<1)
                {
                    constants.alert(d, "warn", "Please select users to delete");
                    return;
                }
                for(int t=0;t<mainTable.getRowCount();t++)
                {
                    Boolean b = (Boolean)mainTable.getValueAt(t, 0);
                    if(b==true)
                    {
                        DefaultTableModel dt = (DefaultTableModel)mainTable.getModel();
                        dt.removeRow(t);
                    }
                }
                 JSONArray jsonArray = new JSONArray();
                for(int x=0; x<mainTable.getRowCount();x++)
                {
                    String user = mainTable.getValueAt(x, 1).toString();
                    String sec = mainTable.getValueAt(x, 2).toString();
                    String pass = mainTable.getValueAt(x, 3).toString();
                    JSONObject obj = new JSONObject();
                    obj.put("user", user);
                    obj.put("section", sec);
                    obj.put("code", pass);
                    obj.put("type", "cert");
                    jsonArray.add(obj);
                }
                 try
                {
                    FileWriter f = new FileWriter("projects/"+name+"/certList.jaj");
                    f.write(jsonArray.toJSONString());
                    f.flush();
                    f.close();
                }
                catch(Exception er)
                {
                    er.printStackTrace();
                }
                getCertList(mainTable);
                
            }
        });
        
      
        jButton4.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent v)
            {
                addUserAccess(d, mainTable, "new", false);
            }
        });
        jButton3.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent v)
            {
                addUserAccess(d, mainTable, "edit", false);
            }
        });

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator1)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2))
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 456, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel1)
                                .addGap(27, 27, 27)))))
                .addContainerGap())
        );

        mainPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jButton2, jButton3, jButton4});

        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 405, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton3)
                    .addComponent(jButton4))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(a);
        a.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );
        return a;
    }
    
    
    
    
    
    public void buildGeneralPanel(JPanel panel, final JDialog d)
    {
        JPanel a = testGeneral(d);
        //construct preComponents
        /*
        String[] jcomp9Items = {"All", "Web",  "Mobile"};
        final JCheckBox jcomp1 = new JCheckBox ("<html><b>Turn Auto Save On</b></html>");
        File f = new File("projects/"+name+"/autoSave.jaj");
        if(f.exists())
        {
            jcomp1.setSelected(true);
        }
        jcomp1.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                if(jcomp1.isSelected())
                {
                    
                        FileWriter f = new FileWriter("projects/"+name+"/autoSave.jaj");
                        f.write("");
                        f.flush();
                        f.close();
                } 
                else
                {
                  File f = new File("projects/"+name+"/autoSave.jaj")  ;
                  f.delete();
                }
                }
                catch(Exception er){er.printStackTrace();}
            }
        });
        JPanel oninfo = new JPanel();
        oninfo.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
        JPanel jcomp3 = new JPanel();
        oninfo.add(new JLabel("Turn on project wide automatic saving of sections"));
        jcomp3.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
        JLabel jcomp4 = new JLabel ("<html><b>Project Details</b></html>");
        JPanel jcomp5 = new JPanel();
        jcomp5.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
        JLabel jcomp6 = new JLabel ("Project Name");
        final JTextField jcomp7 = new JTextField (5);
        jcomp7.setText(name);
        JLabel jcomp8 = new JLabel ("Build Target");
        final JComboBox jcomp9 = new JComboBox (jcomp9Items);
        jcomp9.setSelectedItem(target);
        jcomp7.addFocusListener(new FocusAdapter()
        {
            @Override
            public void focusLost(FocusEvent v)
            {
                Project p = new Project();
                p.setName(name+"##"+jcomp7.getText());
                p.setTarget(jcomp9.getSelectedItem().toString());
                p.setProject_date(date);
                p.setPid(pid);
                main.updateProject(p);
                name = jcomp7.getText();
                setTitle(name);
            }
        });
        jcomp9.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent v)
            {
                Project p = new Project();
                p.setName(name+"##"+jcomp7.getText());
                p.setTarget(jcomp9.getSelectedItem().toString());
                p.setProject_date(date);
                p.setPid(pid);
                main.updateProject(p);
                target = (String)jcomp9.getSelectedItem();
                setTitle(name);
            }
        });
        JPanel jcomp10 = new JPanel();
        jcomp10.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
        JLabel jcomp11 = new JLabel ("<html><b>Project Options</b></html>");
        JButton jcomp12 = new JButton ("Delete Project");
        jcomp12.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent v)
            {
                deleteProject(d);
            }
        });
        JButton jcomp13 = new JButton ("Import Section");
        jcomp13.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent v)
            {
                importSection();
            }
        });
        JButton jcomp14 = new JButton ("Import Project As Sections");
        jcomp14.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent v)
            {
                try{importProjectAsSection(d);}catch(Exception e){}
            }
        });
        JButton jcomp16 = new JButton("Export Project");
        jcomp16.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent v)
            {
                try
                {
                   String path = "";
                        chooser= new JDirectoryChooser();
                        chooser.setDialogTitle("Export Project To...");
                        chooser.setMultiSelectionEnabled(false);
                        int r=chooser.showOpenDialog(parentFrame);
                        if(r==JDirectoryChooser.APPROVE_OPTION)
                        {
                            File[] selectedFiles = chooser.getSelectedFiles();
                            for (int i = 0, c = selectedFiles.length; i < c; i++)
                            {
                                path += selectedFiles[i];
                            }
                        }
                        else
                        {
                            return;
                        };
                    exportProject("projects/"+jcomp7.getText(), path);
                }catch(Exception er){er.printStackTrace();}
            }
        });
        
        
        
        JPanel jcomp15 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        jcomp15.add(new JLabel("<html><b>-</b>Project cannot be recovered once deleted<br><b>-</b>You can import an entire project (.jajx) file as sections <br>of an existing project</html>"));
        jcomp15.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
        a.setPreferredSize (new Dimension (365, 460));
        a.setLayout (null);
        a.add (jcomp1);
        a.add (oninfo);
        a.add (jcomp3);
        a.add (jcomp4);
        a.add (jcomp5);
        a.add (jcomp6);
        a.add (jcomp7);
        a.add (jcomp8);
        a.add (jcomp9);
        a.add (jcomp10);
        a.add (jcomp11);
        a.add (jcomp12);
        a.add (jcomp13);
        a.add (jcomp14);
        a.add (jcomp15);
        a.add (jcomp16);

        //set component bounds (only needed by Absolute Positioning)
        jcomp1.setBounds (10, 10, 145, 25);
        oninfo.setBounds (30, 40, 305, 40);
        jcomp3.setBounds (0, 90, 365, 5);
        jcomp4.setBounds (10, 98, 103, 25);
        jcomp5.setBounds (0, 125, 366, 5);
        jcomp6.setBounds (40, 145, 80, 20);
        jcomp7.setBounds (130, 145, 200, 25);
        jcomp8.setBounds (40, 175, 80, 25);
        jcomp9.setBounds (130, 175, 200, 25);
        jcomp10.setBounds (0, 210, 365, 5);
        jcomp11.setBounds (10, 215, 100, 25);
        jcomp12.setBounds (40, 250, 115, 30);
        jcomp13.setBounds (165, 250, 125, 30);
        jcomp14.setBounds (80, 290, 195, 30);
        jcomp15.setBounds (30, 366, 300, 60);
        jcomp16.setBounds (115, 330, 130, 30);
        */
        panel.add("General", a);
    }
    
    
    
    public void deleteProject(final JDialog d)
    {
        int x = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete? This cannot be undone.",Constants.APP_NAME, JOptionPane.YES_NO_CANCEL_OPTION);
        if(x==JOptionPane.CANCEL_OPTION)
        {
            return;
        }
        if(x==JOptionPane.YES_OPTION)
        {
            try
            {
                File f = new File("projects/"+name);
                FileUtils.deleteDirectory(f);
                parentFrame.setEnabled(true);
                d.dispose();
                dispose();
                
            }
            catch(Exception e){}
        }
    }
    
    
    
    
    
    
    public void importSection()
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
                    int i=atfile.showOpenDialog(parentFrame);
                    java.io.File file1 = atfile.getSelectedFile();
                    String s = file1.getPath();
                    if(i==1)
                    {
			return;
                    }
                    else
                    {
                        String folder = FilenameUtils.removeExtension(new File(s).getName());
                        System.out.println(folder);
                        File f = new File("projects/"+name+"/sections/"+folder);
                        f.mkdir();
                        TarInputStream tis = new TarInputStream(new BufferedInputStream(new FileInputStream(s)));
                        TarEntry entry;
                        while((entry = tis.getNextEntry()) != null)
                        {
                            int count;
                            byte data[] = new byte[2048];
                            FileOutputStream fos = new FileOutputStream("projects/"+name + "/sections/" + entry.getName());
                            BufferedOutputStream dest = new BufferedOutputStream(fos);
                            while((count = tis.read(data)) != -1)
                            {
                                dest.write(data, 0, count);
                            }
                            dest.flush();
                            dest.close();
                        }
                        tis.close();
                    }
                    refreshSectionList();
                    toast.showToaster(new ImageIcon(getClass().getResource("images/icon.png")),"Section imported...");
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }
    
    
    
    private void untar(TarInputStream tis) throws IOException {
        String destFolder = "projects/temp/";
        BufferedOutputStream dest = null;
        int x=0;
        TarEntry entry;
        while(( entry = tis.getNextEntry() ) != null) {
            System.out.println( "Extracting: " + entry.getName());
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
            //File source = ""
           // Files.move(source, target, REPLACE_EXISTING);
        }
    }
    
    
    
    
    public void importProjectAsSection(final Container d) throws IOException
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
                    atfile.setDialogTitle("Import Section");
                    int i=atfile.showOpenDialog(parentFrame);
                    java.io.File file1 = atfile.getSelectedFile();
                    String s = file1.getPath();
                    if(i==1)
                    {
			return;
                    }
                    else
                    {
                        String destFolder = "temp/";
                        File zf = new File( s );
                        String fname = zf.getName();
                        System.out.println(fname);
                        TarInputStream tis = new TarInputStream( new BufferedInputStream( new FileInputStream( zf ) ) );
                        BufferedOutputStream dest = null;
                        int x=0;
                        TarEntry entry;
                        while(( entry = tis.getNextEntry() ) != null) {
                            System.out.println( "Extracting: " + entry.getName());
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
                            fname = FilenameUtils.removeExtension(fname);
                            //String source = "temp/"+fname+"/sections";
                            //String des = "projects/"+name+"/sections";
                            //FileUtils.copyDirectory(new File(source), new File(des));
                            
                    }
                    //refreshSectionList();
                    List v = main.getProjectSections("temp/"+fname);
                    selectSectionsToImport(v,fname, d);
                    //toast.showToaster(new ImageIcon(getClass().getResource("images/icon.png")),"Section imported...");
                }
                }
                    
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
            
        });
        t.start();
    }
    
    
    public void selectSectionsToImport(List v, final String fname, final Container dd)
    {
        
        dd.setEnabled(false);
        final JDialog d= new JDialog(parentFrame);
	d.setTitle("Import Sections");
	d.setResizable(false);
	d.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
	d.addWindowListener(new WindowAdapter(){
	public void windowClosing(WindowEvent e){dd.setEnabled(true);d.dispose();}});
        JPanel jc1 = new JPanel (new FlowLayout(FlowLayout.RIGHT));
        jc1.setBorder(Main.etched);
        //jc1.setBackground(Color.white);
        BlurryLabel ll= new BlurryLabel();
        ll.setText("Select");
              
        ll.setFont(ll.getFont().deriveFont(18f));
        ll.setDrawBlur(true);
        ll.setBorder(new EmptyBorder(0, 5, 2, 5));
        ll.setIcon(new ImageIcon(getClass().getResource("images/import.png")));
        jc1.add(ll);
        d.getContentPane().add("North",jc1);
        
        JPanel a = new JPanel();
        final JList jcomp1 = new JList ();
        final Vector vv = new Vector();
        for(Object f: v)
        {
            vv.add(f);
        }
        jcomp1.setListData(vv);
        final CheckListManager checkListManager = new CheckListManager(jcomp1); 
        JScrollPane s = new JScrollPane(jcomp1);
        final JButton jcomp2 = new JButton ("Import");
        jcomp2.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent v)
            {
                List lx = new ArrayList();
                for(int t=0;t<vv.size();t++)
                {
                    boolean g = checkListManager.getSelectionModel().isSelectedIndex(t);
                    if(g==true)
                    {
                        lx.add(t);
                    }
                }
                if(lx.isEmpty())
                {
                    constants.alert(dd, "warn", "Please select sections to import");
                    return;
                }
                for(Object id: lx)
                {
                    String toImport = jcomp1.getModel().getElementAt(Integer.parseInt(id.toString())).toString();
                    Path movefrom = FileSystems.getDefault().getPath("temp/"+fname+"/sections/"+toImport+"/sectionData.jaj");
                    File f = new File("projects/"+name+"/sections/"+toImport);
                    f.mkdir();
                    f = new File("projects/"+name+"/sections/"+toImport+"/sectionData.jaj");
                    Path moveTo = FileSystems.getDefault().getPath("projects/"+name+"/sections/"+toImport+"/sectionData.jaj");
                    try
                    {
                        f.createNewFile();
                        Files.move(movefrom, moveTo, StandardCopyOption.REPLACE_EXISTING);
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
                try
                {
                    File f = new File("temp/"+fname);
                    FileUtils.deleteDirectory(f);
                }catch(Exception er){}
                toast.showToaster(new ImageIcon(getClass().getResource("images/icon.png")),"Project Sections imported...");
                refreshSectionList();
                dd.setEnabled(true);
                d.dispose();
            }
        });
        final JCheckBox jcomp3 = new JCheckBox ("Select All");
        jcomp3.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent v)
            {
                if(jcomp3.isSelected())
                {
                    checkListManager.selectAll(vv);
                }
                else
                {
                    checkListManager.deSelectAll(vv);
                }
            }
        });
        a.setPreferredSize (new Dimension (170, 330));
        a.setLayout (null);
        a.add (s);
        a.add (jcomp2);
        a.add (jcomp3);
        s.setBounds (4, 34, 165, 260);
        jcomp2.setBounds (35, 299, 100, 30);
        jcomp3.setBounds (35, 5, 100, 25);
        d.getContentPane().add("Center",a);
        d.pack();
        d.setLocationRelativeTo(dd);
        d.setVisible(true);
        //File f = new File("temp/"+fname);
        //FileUtils.deleteDirectory(f);
    }
                
    
    
    public void exportProject(final String path, final String destination, boolean isQuiet)
    {
        parent = null;
        TarOutputStream out = null;
        Thread t = new Thread(new Runnable()
        {
            public void run()
            {
                waiter.initWaiter();
                waiter.setText("Preparing...");
                waiter.setVisible(true);
                BufferedInputStream origin = null;
                File f = new File( path );
                String files[] = f.list();

                // is file
                if( files == null ) {
                    files = new String[1];
                    files[0] = f.getName();
                }

        parent = ( ( parent == null ) ? ( f.isFile() ) ? "" : f.getName() + "/" : parent + f.getName() + "/" );

        try{
            FileOutputStream dest = new FileOutputStream( destination+"/"+name+".jajx" );
            TarOutputStream out = new TarOutputStream( new BufferedOutputStream( dest ) );
        
        for( int i = 0; i < files.length; i++ ) {
            System.out.println( "Adding: " + files[i]);
            File fe = f;
            byte data[] = new byte[BUFFER];

            if( f.isDirectory() ) {
                fe = new File( f, files[i] );
            }
            

            if( fe.isDirectory() ) {
                String[] fl = fe.list();
                if( fl != null && fl.length != 0 ) {
                    tarFolder( parent, fe.getPath(), out );
                } else {
                    TarEntry entry = new TarEntry( fe, parent + files[i] + "/" );
                    out.putNextEntry( entry );
                }
                continue;
            }

            FileInputStream fi = new FileInputStream( fe );
            origin = new BufferedInputStream( fi );

            TarEntry entry = new TarEntry( fe, parent + files[i] );
            out.putNextEntry( entry );

            int count;
            int bc = 0;
            while(( count = origin.read( data ) ) != -1) {
                out.write( data, 0, count );
                bc += count;
            }

            out.flush();

            origin.close();
            waiter.dispose();
        }
        out.close();
         }
        catch(Exception er){er.printStackTrace();}
                if(isQuiet == false)
                {
                    toast.showToaster(new ImageIcon(getClass().getResource("images/icon.png")), "Project exported successfully...!");
                }
            }
        });
        t.start();
    }
    
    
    
    
    
     public void exportSection(final String path, final boolean isQuiet)
    {
        System.out.println(path);
        final String export = list.getSelectedValue().toString();
        Thread t = new Thread(new Runnable()
        {
            public void run()
            {
                waiter.initWaiter();
                waiter.setText("Preparing...");
                try
                {
                    FileOutputStream dest = new FileOutputStream(path+"/"+export+".section" );
                    TarOutputStream out = new TarOutputStream( new BufferedOutputStream( dest ));
                    File[] toExport=new File[1];
                    toExport[0] = new File("projects/"+name+"/sections/"+list.getSelectedValue()+"/sectionData.jaj");
                    for(File f:toExport)
                    {
                        out.putNextEntry(new TarEntry(f, export+"/"+f.getName()));
                        BufferedInputStream origin = new BufferedInputStream(new FileInputStream( f ));
                        int count;
                        byte data[] = new byte[2048];
                        while((count = origin.read(data)) != -1)
                        {
                            out.write(data, 0, count);
                        }
                        out.flush();
                        origin.close();
                    }
                    out.close();
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
                waiter.dispose();
                if(isQuiet == false)
                {
                    toast.showToaster(new ImageIcon(getClass().getResource("images/icon.png")), "Section exported successfully...!");
                }
            }
        }); 
        t.start();
    }
    
    
    
    public void deleteSection()
    {
        try
        {
            File f = new File("projects/"+name+"/sections/"+list.getSelectedValue());
            FileUtils.deleteDirectory(f);
            mainDesigner.removeAll();
                mainD.setVisible(false);
                mainDesigner.repaint();
                mainDesigner.invalidate();
                list.setEnabled(true);
                JList li = new JList();
                this.refreshSectionList();
                        list.setSelectionBackground(li.getSelectionBackground());
                        list.setSelectionForeground(li.getSelectionForeground());
                        list.setForeground(li.getForeground());
                        list.setBackground(li.getBackground());
        }
        catch(Exception er)
        {
            er.printStackTrace();
        }
    }
    
    
    public static void saveSectionWork()
    {
        e2.setSelected(true);
        try
        {
        List lx = new ArrayList();
        Component[] components = mainD.getComponents();
        ListModel model = mainD.getModel();
				int n = model.getSize();
				if(n<1)
				{
				  return;
				}
        for(int x=0; x<model.getSize(); x++)
        {
            Object item = model.getElementAt(x);
            JPanel jp = (JPanel) item;
            String pname = jp.getName();
            lx.add(pname);
        }
        
                          
                        UIElementsWriter saver = new UIElementsWriter(name, list.getSelectedValue().toString());
                        saver.save(lx);
                         try{File f = new File("10110.jaj");f.delete();}catch(Exception ee){ee.printStackTrace();}
                         
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public void showMessage(String message)
    {
        toast.showToaster(icon, message);
    }
    
    public void open()
    {
        PopupMenuListener pml = new PopupMenuListener();
        
        JPanel leftPanel = new JPanel(new BorderLayout());
        JPanel p = createPropertiesPanel();
        p.addMouseListener(pml);
        leftPanel.add("Center",p);
        
        JPanel rightPanel = new JPanel(new BorderLayout());
        JPanel s = createControlPanel();
        rightPanel.add("Center",s);
        
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setLeftComponent(leftPanel);
        splitPane.setRightComponent(rightPanel);
        JPanel lastPanel = createDesignerPanel();
        
        
        JSplitPane last = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        last.setLeftComponent(splitPane);
        last.setRightComponent(lastPanel);
        last.setDividerLocation(650);
        splitPane.setDividerLocation(450);
        //splitPane.setResizeWeight(0.5);
        //last.setResizeWeight(0.5);
        getContentPane().add(last, BorderLayout.CENTER);
    }
    
    
    public void editSection()
    {
        if(list.getSelectedIndex() < 0)
        {
            return;
        }
        String s = JOptionPane.showInputDialog(null, "Edit Section Title",list.getSelectedValue());
                if(s==null || s.equals(""))
                {
                    return;
                }
                main.editSection(name, s, list.getSelectedValue().toString());
                refreshSectionList();
                Object c = (Object)s;
                list.setSelectedValue(c,true);
    }
    
    
    
    public void addSection()
    {
        String s = JOptionPane.showInputDialog(null, "Enter Section Title");
                if(s==null || s.equals(""))
                {
                    return;
                }
                
                
                
                if(s.contains("!") || s.contains("_") || s.contains("-") || s.contains("@") || s.contains("#") || s.contains("*") || s.contains("$") || s.contains("%") || s.contains("^") || s.contains("(") || s.contains(")") || s.contains("+") || s.contains("=") || s.contains("{") || s.contains("}") || s.contains("[") || s.contains("]") || s.contains("|") || s.contains("\\") || s.contains(":") || s.contains(";") || s.contains("'") || s.contains("<") || s.contains(">") || s.contains(",") || s.contains(".") || s.contains("?") || s.contains("/") || s.contains("`") || s.contains("~"))
                {
                    JOptionPane.showMessageDialog(SavedProject.this,  "Special characters are not allowed here. Please check section name and try again.", Constants.APP_NAME, JOptionPane.WARNING_MESSAGE);
                    return;
                }
                
                
                
                main.saveSection(name, s);
                refreshSectionList();
    }
    
    
    public void loadSection()
    {
        main.loadSection(name, list.getSelectedValue().toString(), mainD, elementsList);
        String singleList = main.readFile("projects/"+name+"/singleList.jaj");
                if(singleList.contains(list.getSelectedValue().toString()))
                {
                    e1.setSelected(true);
                }
                else
                {
                    e2.setSelected(true);
                }
    }
    
    
    public JPanel createPropertiesPanel()
    {
        JPanel p = new JPanel(new BorderLayout());
        JPanel title = new JPanel(new GridLayout(1,1));
        JPanel z = new JPanel();
        BlurryLabel l = new BlurryLabel();
        l.setText("Sections");
        z.add(l);
        z.setBackground(Color.lightGray);
        title.add(z);
        p.add("North",title);
        
        JTabbedPane tb = new JTabbedPane();
        Dimension minimumSize = new Dimension(200, 400);
        tb.setMinimumSize(minimumSize);
        tb.setTabPlacement(JTabbedPane.BOTTOM);
        JPanel q = new JPanel();
        list= new JList();
        list.addMouseListener (new MouseAdapter () {
            public void mouseClicked(MouseEvent me)
            {
                if(list.getSelectedIndex()<0 && list.isEnabled() == false)
                {
                    return;
                }
                if(me.getClickCount() ==2 && list.isEnabled() == true)
                {
                    
                    loadSection();
                    
                    list.setBackground(Color.lightGray);
                    list.setSelectionBackground(Color.lightGray);
                    list.setSelectionForeground(Color.white);
                    list.setEnabled(false);
                    mainD.setVisible(true);
                    mainDesigner.repaint();
                    mainDesigner.invalidate();
                    mainDesigner.updateUI();
                }
            }
        public void mousePressed (MouseEvent me) { 
            checkMouseTrigger (me); }
        public void mouseReleased (MouseEvent me) { checkMouseTrigger (me); }
        private void checkMouseTrigger (MouseEvent me) {
          if (me.isPopupTrigger ())
            pop.show (me.getComponent (), me.getX (), me.getY ());
        }
      }
      );
        list.setCellRenderer(new LabelCellRenderer1());
        refreshSectionList();
        JScrollPane s= new JScrollPane(list);
        q.add(s);
        tb.addTab("Sections", s);
        
        JPanel d = new JPanel();
        d.addMouseListener (new MouseAdapter () {
        public void mousePressed (MouseEvent me) { checkMouseTrigger (me); }
        public void mouseReleased (MouseEvent me) { checkMouseTrigger (me); }
        private void checkMouseTrigger (MouseEvent me) {
          if (me.isPopupTrigger ())
            pop.show (me.getComponent (), me.getX (), me.getY ());
        }
      }
      );
        //tb.addTab("", new ImageIcon(getClass().getResource("images/prop.png")), d, "Properties");
        p.add("Center",tb);
        return p;
    }
    
    public void refreshSectionList()
    {
        main.getSections(name, list);
    }
    
    public JPanel createControlPanel()
    {
        JPanel p = new JPanel(new BorderLayout());
        JPanel title = new JPanel();
        title.setBackground(Color.lightGray);
        BlurryLabel l = new BlurryLabel();
        l.setText("Elements");
        title.add(l);
        p.add("North",title);
        
        
        
        JTabbedPane tb = new JTabbedPane();
        Dimension minimumSize = new Dimension(200, 400);
        tb.setMinimumSize(minimumSize);
        tb.setTabPlacement(JTabbedPane.BOTTOM);
        
        final JToolBar icons = new JToolBar();
        icons.setLayout(new GridLayout(6,2));
        icons.setRollover(true);
        icons.setBorder(null);
        icons.setFloatable(false);
        
        final JButton label = new JButton("", new ImageIcon(getClass().getResource("images/rtf.png")));
        label.setToolTipText("<html><b>rtfLabel</b><br>Can represent text to be used as headings or descriptions. A label spans only one line<br>"
                + "and when it has no more available space it ends with '...'</html>");
        this.addDragAndDrop(label);
        icons.add(label);
        
        final JButton textfield = new JButton("", new ImageIcon(getClass().getResource("images/textfield.png")));
        icons.add(textfield);
        textfield.setToolTipText("<html><b>TextField</b><br>Used for numeric and alphanumeric data entry. Rules can be set for this component<br>"
                + "for example to allow the entry of only numeric values.</html>");
        this.addDragAndDrop(textfield);
        
        final JButton textarea = new JButton("", new ImageIcon(getClass().getResource("images/textarea.png")));
        icons.add(textarea);
        textarea.setToolTipText("<html><b>TextArea</b><br>Multiline editable text component<br></html>");
        this.addDragAndDrop(textarea);
        
        final JButton combo = new JButton("", new ImageIcon(getClass().getResource("images/combobox.png")));
        icons.add(combo);
        combo.setToolTipText("<html><b>Drop Down Box</b><br>Can represent a list of options with only one of the options selectable.<br></html>");
        this.addDragAndDrop(combo);
        
        final JButton check = new JButton("", new ImageIcon(getClass().getResource("images/checkbox.png")));
        icons.add(check);
        check.setToolTipText("<html><b>Checkbox</b><br>A two state component which returns true or false value based on its checked state<br></html>");
        this.addDragAndDrop(check);
        
        final JButton radio = new JButton("", new ImageIcon(getClass().getResource("images/radiobutton.png")));
        icons.add(radio);
        radio.setToolTipText("<html><b>Radio Button</b><br>Similar to a checkbox but belongs to a group of options. Only one member of the group can<br>selected.</html>");
        this.addDragAndDrop(radio);
        
        final JButton list = new JButton("", new ImageIcon(getClass().getResource("images/time.png")));
        icons.add(list);
        list.setToolTipText("<html><b>Time</b><br>Time Picker</html>");
        this.addDragAndDrop(list);
        
        final JButton date = new JButton("", new ImageIcon(getClass().getResource("images/date.png")));
        icons.add(date);
        date.setToolTipText("<html><b>Date Picker</b><br>For selecting dates</html>");
        this.addDragAndDrop(date);
        
        final JButton pic = new JButton("", new ImageIcon(getClass().getResource("images/picture.png")));
        icons.add(pic);
        pic.setToolTipText("<html><b>Image</b><br>Represents an image captured from a camera or uploaded from the user's file system.</html>");
        this.addDragAndDrop(pic);
        
        final JButton location = new JButton("", new ImageIcon(getClass().getResource("images/location.png")));
        icons.add(location);
        location.setToolTipText("<html><b>Location</b><br>Represents the exact longititude and latitude of a user on a map</html>");
        this.addDragAndDrop(location);
        
        final JButton spinner = new JButton("", new ImageIcon(getClass().getResource("images/numericspinner.png")));
        icons.add(spinner);
        spinner.setToolTipText("<html><b>Numeric Spinner</b><br>For selecting numeric values</html>");
        this.addDragAndDrop(spinner);
        
        final JButton onoff = new JButton("", new ImageIcon(getClass().getResource("images/onoff.png")));
        icons.add(onoff);
        onoff.setToolTipText("<html><b>On/Off Switch</b><br>Works like the checkbox</html>");
        this.addDragAndDrop(onoff);
        
        tb.addTab("Basic", icons);
        
        JToolBar t2 = new JToolBar();
        t2.setLayout(new GridLayout(6,1));
        t2.setRollover(true);
        t2.setBorder(null);
        t2.setFloatable(false);
        
        final JButton barcode = new JButton("", new ImageIcon(getClass().getResource("images/barcode.png")));
        t2.add(barcode);
        barcode.setToolTipText("<html><b>Barcode Scanner</b><br>Scan barcodes</html>");
        this.addDragAndDrop(barcode);
        
        final JButton audio = new JButton("", new ImageIcon(getClass().getResource("images/audio.png")));
        t2.add(audio);
        audio.setToolTipText("<html><b>Audio Recorder</b><br>Records Audio</html>");
        this.addDragAndDrop(audio);
        
        final JButton video = new JButton("", new ImageIcon(getClass().getResource("images/video.png")));
        t2.add(video);
        video.setToolTipText("<html><b>Video Recorder</b><br>Records Video</html>");
        this.addDragAndDrop(video);
        
        final JButton matrix = new JButton("", new ImageIcon(getClass().getResource("images/matrix.png")));
        t2.add(matrix);
        matrix.setToolTipText("<html><b>Matrix Component</b><br></html>");
        this.addDragAndDrop(matrix);
        
        final JButton rtf = new JButton("", new ImageIcon(getClass().getResource("images/rtf.png")));
        //t2.add(rtf);
        rtf.setToolTipText("<html><b>Rich Text</b><br>For displayng instructionsin rich text format</html>");
        this.addDragAndDrop(rtf);
        
        tb.addTab("Advanced", t2);
        
        p.add("Center",tb);
        return p;
    }
    
    public JPanel createDesignerPanel()
    {
        JPanel p = new JPanel(new BorderLayout());
        p.addMouseListener (new MouseAdapter () {
        public void mousePressed (MouseEvent me) { checkMouseTrigger (me); }
        public void mouseReleased (MouseEvent me) { checkMouseTrigger (me); }
        private void checkMouseTrigger (MouseEvent me) {
          if (me.isPopupTrigger ())
            pop.show (me.getComponent (), me.getX (), me.getY ());
        }
      }
      );
        JPanel title = new JPanel();
        title.addMouseListener (new MouseAdapter () {
        public void mousePressed (MouseEvent me) { checkMouseTrigger (me); }
        public void mouseReleased (MouseEvent me) { checkMouseTrigger (me); }
        private void checkMouseTrigger (MouseEvent me) {
          if (me.isPopupTrigger ())
            pop.show (me.getComponent (), me.getX (), me.getY ());
        }
      }
      );
        title.setBackground(Color.lightGray);
        BlurryLabel l = new BlurryLabel();
        l.setText("Designer");
        title.add(l);
        p.add("North",title);
        JPanel s = new JPanel(new BorderLayout());
        JScrollPane ss = new JScrollPane();
        JPanel center = new JPanel();
        center.setBackground(Color.white);
        BlurryLabel ll= new BlurryLabel();
        ll.setText("Drag and drop elements here");
        ll.setFont(ll.getFont().deriveFont(18f));
        ll.setDrawBlur(true);
        ll.setBorder(new EmptyBorder(0, 5, 2, 5));
        //ll.setIcon(new ImageIcon(getClass().getResource("images/users.png")));
        center.setBorder(new BevelBorder(BevelBorder.LOWERED));
        center.setLayout(new BorderLayout());
        center.add("Center",ll);
        center.addMouseListener (new MouseAdapter () {
        public void mousePressed (MouseEvent me) { checkMouseTrigger (me); }
        public void mouseReleased (MouseEvent me) { checkMouseTrigger (me); }
        private void checkMouseTrigger (MouseEvent me) {
          if (me.isPopupTrigger ())
            pop.show (me.getComponent (), me.getX (), me.getY ());
        }
      }
      );
        
        mainD.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyReleased(KeyEvent k)
            {
                if(k.getKeyCode()==127)
                {
                    deleteElement("normal");
                }
                if(k.getKeyCode()== 38 || k.getKeyCode() == 40)
                {
                    ListModel model = mainD.getModel();
                    Object item = model.getElementAt(mainD.getSelectedIndex());
                    JPanel jp = (JPanel) item;
                    String pname = jp.getName();
                    elementName = pname;
                    showPropertiesPanel(previousIndex);
                }
                
            }
        });
        
        mainD.setCellRenderer(new CustomCellRenderer());
        designScroller = new JScrollPane(mainD);
        JPanel toAdd = getDesignerPanel(designScroller);
        toAdd.setName("test");
        //designScroller.getVerticalScrollBar().setPreferredSize(new Dimension(4, Integer.MAX_VALUE));
        //BoxLayout b = new BoxLayout(mainDesigner,BoxLayout.Y_AXIS);
        
        //mainDesigner.setSize(mainDesigner.getSize());
        
        JPanel properties = new JPanel(new BorderLayout());
        properties.addMouseListener (new MouseAdapter () {
        public void mousePressed (MouseEvent me) { checkMouseTrigger (me); }
        public void mouseReleased (MouseEvent me) { checkMouseTrigger (me); }
        private void checkMouseTrigger (MouseEvent me) {
          if (me.isPopupTrigger ())
            pop.show (me.getComponent (), me.getX (), me.getY ());
        }
      }
      );
        final JToolBar tool = new JToolBar();
        tool.setRollover(true);
        tool.setBorder(null);
        tool.setFloatable(false);
        tool.addMouseListener (new MouseAdapter () {
        public void mousePressed (MouseEvent me) { checkMouseTrigger (me); }
        public void mouseReleased (MouseEvent me) { checkMouseTrigger (me); }
        private void checkMouseTrigger (MouseEvent me) {
          if (me.isPopupTrigger ())
            pop.show (me.getComponent (), me.getX (), me.getY ());
        }
      }
      );
        tool.setLayout(new FlowLayout(FlowLayout.RIGHT));
        
        JButton cx = new JButton("", new ImageIcon(getClass().getResource("images/up.png")));
        cx.setToolTipText("Move Element Up");
        tool.add(cx);
        cx.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent v)
            {
                int index = mainD.getSelectedIndex(); 
                if( index == -1 || index == 0 ) 
                {
                    return;
                }
                Object a = mainD.getSelectedValue();
                Object b = elementsList.get(index-1);
                elementsList.setElementAt(b, index);
                elementsList.setElementAt(a, index-1);
                mainD.setListData(elementsList);
                mainD.setSelectedIndex(index-1);
                mainD.ensureIndexIsVisible(mainD.getSelectedIndex());
            }
        });
        
        JButton cs = new JButton("", new ImageIcon(getClass().getResource("images/down.png")));
        cs.setToolTipText("Move Element Down");
        tool.add(cs);
        cs.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent v)
            {
                int index = mainD.getSelectedIndex(); 
                if( index == elementsList.size()-1 ) 
                {
                    return;
                }
                Object a = mainD.getSelectedValue();
                Object b = elementsList.get(index+1);
                elementsList.setElementAt(b, index);
                elementsList.setElementAt(a, index+1);
                mainD.setListData(elementsList);
                mainD.setSelectedIndex(index+1);
                mainD.ensureIndexIsVisible(mainD.getSelectedIndex());
            }
        });
        
        JButton c = new JButton("", new ImageIcon(getClass().getResource("images/cut.png")));
        c.setToolTipText("Cut");
        tool.add(c);
        c.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent v)
            {
                cutElementToClipBoard();
            }
        });
        
        JButton cp = new JButton("", new ImageIcon(getClass().getResource("images/copy.png")));
        tool.add(cp);
        cp.setToolTipText("Copy");
        cp.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent v)
            {
                copyElementToClipBoard();
            }
        });
        
        final DropDownButton ps = new DropDownButton("", new ImageIcon(getClass().getResource("images/paste.png")));
        tool.add(ps);
        JMenuItem pt = new JMenuItem();
        pt.setText("Paste");
        pt.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent v)
            {
                pasteElementFromClipBoard(0);
            }
        });
        pt.setAccelerator (KeyStroke.getKeyStroke(KeyEvent.VK_V, Event.CTRL_MASK));
        ps.addComponent(pt);
        
        
        JMenuItem pt0 = new JMenuItem();
        pt0.setText("Paste Before");
        pt0.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent v)
            {
                pasteElementFromClipBoard(1);
            }
        });
        pt0.setAccelerator (KeyStroke.getKeyStroke(KeyEvent.VK_B, Event.CTRL_MASK));
        ps.addComponent(pt0);
        
        JMenuItem pt1 = new JMenuItem();
        pt1.setText("Paste After");
        pt1.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent v)
            {
                pasteElementFromClipBoard(2);
            }
        });
        pt1.setAccelerator (KeyStroke.getKeyStroke(KeyEvent.VK_T, Event.CTRL_MASK));
        ps.addComponent(pt1);

        final DropDownButton r = new DropDownButton("", new ImageIcon(getClass().getResource("images/add16.png")));
        r.setBorder(null);
        addToDropDown(r);
        
        r.setToolTipText("Add Element");
        
        JButton b = new JButton("", new ImageIcon(getClass().getResource("images/delete.png")));
        b.setToolTipText("Delete Element");
        b.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent v)
            {
                deleteElement("normal");
            }
        });
        
        JButton f= new JButton("", new ImageIcon(getClass().getResource("images/save16.png")));
        f.setToolTipText("Save");
        f.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent v)
            {
                saveSectionWork();
            }
        });
        
        JButton fff= new JButton("", new ImageIcon(getClass().getResource("images/instruction.png")));
        fff.setToolTipText("Section Instructions");
        fff.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent v)
            {
                if(list.isEnabled() == true)
                    {
                        return;
                    }
                    File f = new File("projects/"+name+"/sections/"+list.getSelectedValue()+"/"+list.getSelectedValue().toString().toLowerCase()+".yas");
                    if(!f.exists())
                    {
                        try{f.createNewFile();}catch(Exception er){}
                    }
                    //Ekit editor = new Ekit(SavedProject.this.parentFrame, name, f);
                    //editor.setTitle("Section Instructions for: "+list.getSelectedValue());
                    //editor.setVisible(true);
            }
        });
        
        JButton i= new JButton("", new ImageIcon(getClass().getResource("images/build16.png")));
        i.setToolTipText("Build");
        i.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent v)
            {
               buildProject();
            }
        });
        
        JButton ii= new JButton("", new ImageIcon(getClass().getResource("images/cloud.png")));
        ii.setToolTipText("Send to Cloud");
        ii.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent v)
            {
               sendToCloud();
            }
        });
        
        JButton m= new JButton("", new ImageIcon(getClass().getResource("images/properties.png")));
        m.setToolTipText("Project Properties");
        m.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent v)
            {
               openProjectProperties();
            }
        });
        
        JButton o= new JButton("", new ImageIcon(getClass().getResource("images/refresh.png")));
        o.setToolTipText("Refresh Section");
        o.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent v)
            {
                Thread tt = new Thread(new Runnable()
                    {
                        public void run()
                        {
                            elementsList.clear();
                            loadSection();
                        }
                    });
                    tt.start();
            }
        });
        
        JButton u= new JButton("", new ImageIcon(getClass().getResource("images/update.gif")));
        u.setToolTipText("Close Section");
        u.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent v)
            {
                if(checkChanges() == false)
                {
                    int x= JOptionPane.showConfirmDialog(null,"Save changes before closing...?",Constants.APP_NAME,JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.WARNING_MESSAGE);
                    if(x==JOptionPane.CANCEL_OPTION)
                    {
                        return;
                    }
                    if(x==JOptionPane.YES_OPTION)
                    {
                        saveSectionWork();
                        
                    }
                }
                try{File f = new File("10110.jaj");f.delete();}catch(Exception ee){ee.printStackTrace();}
                
                elementsList.clear();
                mainD.setListData(new Vector());
                list.setEnabled(true);
                mainD.setVisible(false);
                JList li = new JList();
                        list.setSelectionBackground(li.getSelectionBackground());
                        list.setSelectionForeground(li.getSelectionForeground());
                        list.setForeground(li.getForeground());
                        list.setBackground(li.getBackground());
            }
        });
        
        final DropDownButton k= new DropDownButton("", new ImageIcon(getClass().getResource("images/importdata.png")));
        JMenuItem mw = new JMenuItem("Section");
        mw.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent v)
            {
                if(mainD.isVisible())
                {
                    constants.alert(parentFrame, "warn", "Please close this section before a new section can be imported");
                    return;
                }
                importSection();
            }
        });
        k.addComponent(mw);
        JMenuItem mw2 = new JMenuItem("Project as section");
        mw2.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent v)
            {
                try{importProjectAsSection(parentFrame);}catch(Exception e){}
            }
        });
        k.addComponent(mw2);
       
        
        tool.add(r);
        tool.add(b);
        tool.add(f);
        tool.add(fff);
        tool.add(o);
        tool.add(k);
        tool.add(u);
        //tool.add(i);
        tool.add(ii);
        tool.add(m);
        
        //tool.setPreferredSize(new Dimension(50,40));
        tool.setFloatable(false);
        properties.add("North", tool);
        
        propertiesPanel = new JPanel();
        propertiesPanel.add(props);
        properties.add("Center",propertiesPanel);
        
        JPanel south = new JPanel();
        south.setBorder(etched);
        project_code = new JLabel();
        File ff = new File("projects/"+name+"/.built");
        if(ff.exists())
        {
            project_code.setText("Project Code: "+main.readFile("projects/"+name+"/identifier.jaj"));
        }
        project_code.setFont(new Font("Arial",Font.BOLD, 17));
        south.add(project_code);
        properties.add("South", south);
        
        JSplitPane split = new JSplitPane();
        //designScroller.setSize(new Dimension(designScroller.getWidth(), properties.getHeight()));
        //mainDesigner.setBorder(BorderFactory.createCompoundBorder(                        new DropShadowBorder(Color.BLACK, 0, 5, .5f, 12, false, true, true, true),  split.getBorder()));
        //mainDesigner.setBorder(new LineBorder(Color.blue, 5));
        center.add(mainD, "Center");
        ss.setViewportView(center);
        //ss.getVerticalScrollBar().setPreferredSize(new Dimension(6, Integer.MAX_VALUE));
        split.setLeftComponent(ss);
        split.setRightComponent(properties);
        split.setDividerLocation(800);
        s.add("Center",split);
        mainD.setVisible(false);
        p.add("Center",s);
        return p;
    }
    
    
    
    public void sendToCloud()
    {
        parentFrame.setEnabled(false);
        final JDialog w = new JDialog(parentFrame);
        w.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        w.setTitle("Deploy");
        w.setAlwaysOnTop(true);
        JPanel a = new JPanel();
        JScrollPane jScrollPane1 = new javax.swing.JScrollPane();
        final JList window = new javax.swing.JList<>();
        JButton jButton1 = new javax.swing.JButton();

        window.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = {};
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(window);

        jButton1.setText("Close");
        jButton1.addActionListener(e -> {parentFrame.setEnabled(true);w.dispose();});

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(a);
        a.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 421, Short.MAX_VALUE)
                        .addComponent(jButton1)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        final Vector text = new Vector();
        window.setCellRenderer(new CustomCellRenderer());
        
        w.getContentPane().add("Center",a);
        a.setBorder(new LineBorder(Color.BLUE, 2));
        w.pack();
        w.setLocationRelativeTo(parentFrame);
        w.setVisible(true);
        String account[] = main.readFile("account.jaj").split("##");
        final String username = account[0];
        final String password = account[1];
        Thread t = new Thread(new Runnable()
        {
            public void run()
            {
                w.getContentPane().setCursor(new Cursor(Cursor.WAIT_CURSOR));
                String code = main.readFile("projects/"+name+"/identifier.jaj");
                Cloud cloud = new Cloud();
                setBuildText(window, "Creating project build", 0, text);
                main.buildProject(name, project_code, window, text);
                setBuildText(window, "Project build success...Signing in to LS server...", 0 , text);
                String msg = cloud.Login(username, password, name, code);
                System.out.println("msg is \n"+msg);
                if(msg.startsWith("wrong"))
                {
                    setBuildText(window, "**You have provided wrong username and password. Please update your details then refresh to continue**", 2 , text);
                    return;
                }
                if(msg.startsWith("Error"))
                {
                    System.out.println("this is msg "+msg);
                    setBuildText(window, msg, 2 , text);
                    return;
                }
                setBuildText(window, "*Authentication successful...Sending project to cloud...*", 1 , text);
                String f = cloud.sendToCloud(name, code);
                if(f.startsWith("Error"))
                {
                    setBuildText(window, f, 2 , text);
                    return;
                }
                if(f.equals("true"))
                {
                    w.setAlwaysOnTop(false);
                    setBuildText(window, "****Project already exisits in cloud****", 3 , text);
                    int g = JOptionPane.showConfirmDialog(w, "Project already exisits...Overwrite?",Constants.APP_NAME, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if(g==JOptionPane.NO_OPTION)
                    {
                        setBuildText(window, "*Process aborted by user*", 2 , text);
                        return;
                    }
                    else
                    {
                        setBuildText(window, "*Overwrite permission granted*", 0 , text);
                        String done = cloud.insertIntoCloud(name, code);
                        if(done.startsWith("Error"))
                        {
                            setBuildText(window, done, 2 , text);
                            return;
                        }
                    }
                }
                else
                {
                    String done = cloud.insertIntoCloud(name, code);
                    if(done.startsWith("error"))
                    {
                       setBuildText(window, done, 2 , text);
                        return;
                    }
                }
                setBuildText(window, "Project sent succesfully....Poject Code: "+code, 1 , text);
                w.getContentPane().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
        t.start();
    }
    
    
    public void setBuildText(JList list, String text, int type, Vector listData)
    {
        JPanel x = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel l = new JLabel(text);
        if(type == 1)
        {
            l.setForeground(Color.green.darker());
        }
        if(type == 2)
        {
            l.setForeground(Color.red);
        }
        if(type == 3)
        {
            l.setForeground(Color.blue.darker());
        }
        x.add(l);
        listData.add(x);
        list.setListData(listData);
    }
    
    
    public void copyElementToClipBoard()
    {
        StringSelection stringSelection = new StringSelection (elementName);
        Clipboard clpbrd = Toolkit.getDefaultToolkit ().getSystemClipboard ();
        clpbrd.setContents (stringSelection, null);
    }
    
    public void cutElementToClipBoard()
    {
        copyElementToClipBoard();
        deleteElement("cut");
    }
    
    public void pasteElementFromClipBoard(int t)
    {
        try
        {
         Toolkit defaultToolkit = Toolkit.getDefaultToolkit();
        Clipboard systemClipboard = defaultToolkit.getSystemClipboard();
         Object text = systemClipboard.getData(DataFlavor.stringFlavor);
        JPanel r = main.pasteElement(text.toString());
        System.out.println("this is paste name "+r.getName());
        elementCount++;
        if(t==0)
        {
            elementsList.add(r);
            mainD.setListData(elementsList);
            int height = (int)mainD.getPreferredSize().getHeight();
            Rectangle rect = new Rectangle(0,height,10,10);
            mainD.scrollRectToVisible(rect);
        }
        if(t==1)
        {
            if(mainD.getSelectedIndex() < 0)
            {
                return;
            }
            elementsList.insertElementAt(r, mainD.getSelectedIndex()-1);
            mainD.setListData(elementsList);
        }
        if(t==2)
        {
            if(mainD.getSelectedIndex() < 0)
            {
                return;
            }
            elementsList.insertElementAt(r, mainD.getSelectedIndex()+1);
            mainD.setListData(elementsList);
        }
        mainD.repaint();
        mainD.revalidate();
        
        File f = new File("projects/autoSave.jaj");
        if(f.exists() == true)
        {
            saveSectionWork();
        }
        
        }
        catch(Exception er){er.printStackTrace();}
    }
    
    
    public void addToDropDown(DropDownButton r)
    {
        JMenuItem label = new JMenuItem("Rich Text", new ImageIcon(getClass().getResource("images/rtf16.png")));
            JMenuItem text = new JMenuItem("Text Box", new ImageIcon(getClass().getResource("images/textfield16.png")));
            JMenuItem textarea = new JMenuItem("Textarea", new ImageIcon(getClass().getResource("images/textarea16.png")));
            JMenuItem combo = new JMenuItem("Drop Down Box", new ImageIcon(getClass().getResource("images/combobox16.png")));
            JMenuItem check = new JMenuItem("Checkbox", new ImageIcon(getClass().getResource("images/checkbox16.png")));
            JMenuItem listm = new JMenuItem("Time Picker", new ImageIcon(getClass().getResource("images/time16.png")));
            JMenuItem radio = new JMenuItem("Radio Button", new ImageIcon(getClass().getResource("images/radio16.png")));
            JMenuItem date = new JMenuItem("Date Picker", new ImageIcon(getClass().getResource("images/date16.png")));
            JMenuItem pic = new JMenuItem("Image", new ImageIcon(getClass().getResource("images/picture16.png")));
            JMenuItem loc = new JMenuItem("Location", new ImageIcon(getClass().getResource("images/location16.png")));
            JMenuItem spin = new JMenuItem("Spinner", new ImageIcon(getClass().getResource("images/spinner16.png")));
            JMenuItem on = new JMenuItem("ON/OFF", new ImageIcon(getClass().getResource("images/on16.png")));
        JMenu md = new JMenu("Advaanced");
            JMenuItem d1 = new JMenuItem("Barcode Scanner", new ImageIcon(getClass().getResource("images/bar16.png")));
            md.add(d1);
            JMenuItem d2 = new JMenuItem("Audio", new ImageIcon(getClass().getResource("images/audio16.png")));
            md.add(d2);
            JMenuItem d3 = new JMenuItem("Video", new ImageIcon(getClass().getResource("images/video16.png")));
            md.add(d3);
            JMenuItem d4 = new JMenuItem("Matrix", new ImageIcon(getClass().getResource("images/matrix16.png")));
            md.add(d4);
            JMenuItem d5 = new JMenuItem("Rich Text", new ImageIcon(getClass().getResource("images/rtf16.png")));
            //md.add(d5);
            r.addComponent(label);
            r.addComponent(text);
            r.addComponent(textarea);
            r.addComponent(combo);
            r.addComponent(check);
            r.addComponent(listm);
            r.addComponent(radio);
            r.addComponent(date);
            r.addComponent(pic);
            r.addComponent(loc);
            r.addComponent(spin);
            r.addComponent(on);
            r.addComponent(md);
            d1.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent v)
                {
                    addElementToDesigner("<html><b>Barcode");
                }
            });
            d2.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent v)
                {
                    addElementToDesigner("<html><b>Audio");
                }
            });
            d3.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent v)
                {
                    addElementToDesigner("<html><b>Video");
                }
            });
            d4.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent v)
                {
                    addElementToDesigner("<html><b>Matrix");
                }
            });
            d5.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent v)
                {
                    addElementToDesigner("<html><b>Rich");
                }
            });
            on.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent v)
                {
                    addElementToDesigner("<html><b>On/");
                }
            });
            spin.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent v)
                {
                    addElementToDesigner("<html><b>Numeric");
                }
            });
            loc.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent v)
                {
                    addElementToDesigner("<html><b>Location");
                }
            });
            pic.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent v)
                {
                    addElementToDesigner("<html><b>Image");
                }
            });
            listm.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent v)
                {
                    addElementToDesigner("<html><b>Time");
                }
            });
            date.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent v)
                {
                    addElementToDesigner("<html><b>Date");
                }
            });
            radio.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent v)
                {
                    addElementToDesigner("<html><b>Radio");
                }
            });
            check.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent v)
                {
                    addElementToDesigner("<html><b>Checkbox");
                }
            });
            combo.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent v)
                {
                    addElementToDesigner("<html><b>Drop Down");
                }
            });
            label.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent v)
                {
                    addElementToDesigner("<html><b>Label");
                }
            });
            text.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent v)
                {
                    addElementToDesigner("<html><b>TextField");
                }
            });
            textarea.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent v)
                {
                    addElementToDesigner("<html><b>TextArea");
                }
            });
    }
    
    
    public void deleteElement(String type)
    {
        if(elementName.equals(""))
        {
            new Constants().alert(this, "warn", "No element selected");
            return;
        }
        if(!type.equals("cut"))
        {
            int x = JOptionPane.showConfirmDialog(this, "Delete...?",com.jajitech.desktop.xdata.constants.Constants.APP_NAME,JOptionPane.YES_NO_CANCEL_OPTION);
            if(x==JOptionPane.CANCEL_OPTION)
            {
                return;
            }
            if(x==JOptionPane.NO_OPTION)
            {
                return;
            }
        }
        
        int x[] = mainD.getSelectedIndices();
        List v = new ArrayList();
        for(int h: x)
        {
            ListModel model = mainD.getModel();
            Object item = model.getElementAt(h);
            JPanel jp = (JPanel) item;
            v.add(jp);
        }
        
        for(Object n: elementsList)
        {
            System.out.println(n);
        }
        
        for(Object f: v)
        {
            System.out.println("i removed "+f);
            elementsList.remove(f);
        }
        mainD.setListData(elementsList);
        File f = new File("projects/autoSave.jaj"); 
        if(auto.isSelected()==true || f.exists() == true)
        {
            saveSectionWork();
        }
    }
    
    
    public JPanel getDesignerPanel(JScrollPane main)
    {
        
        JPanel a = new JPanel();
        main.setBackground(Color.white);
        //JLabel nexus = new JLabel (new ImageIcon(getClass().getResource("images/nexus.png")));
        a.setPreferredSize (new Dimension (385, 1031));
        //a.setLayout (new BorderLayout());
        a.add (main);
        //a.add (nexus);
        main.setBounds (42, 107, 305, 425);
        //nexus.setBounds (5, 5, 377, 620);
        return a;
    }
    
    
    public void addDragAndDrop(JButton button)
    {
        button.setTransferHandler(new ValueExportTransferHandler(button.getToolTipText()));
        button.addMouseMotionListener(new MouseAdapter() {
                    @Override
                    public void mouseDragged(MouseEvent e) {
                        JButton button = (JButton) e.getSource();
                        TransferHandler handle = button.getTransferHandler();
                        handle.exportAsDrag(button, e, TransferHandler.COPY);
                    }
                });
    }
    
    
    public static int getDesignerPanelCount()
    {
        
        int x = 0;
        Component[] components = mainDesigner.getComponents();
        for (Component component1 : components)
        {
            if (component1.getClass().getName().equals("javax.swing.JPanel")) {
                x++;
            }
        }
    return x;
    }
    
    
    
    public static void writeFile(int id, String type, String text)
    {
        try
        {
            FileWriter f = new FileWriter("projects/"+name+"/sections/"+list.getSelectedValue()+"/"+id+".jaj");
            String toWrite="type##"+type+"\ntext##"+text;
            f.write(toWrite);
            f.close();
        }catch(Exception e){}
    }
    
    
    public static void addElementToDesigner(String type)
    {
        /*AdjustmentListener al = new AdjustmentListener()
        {
            public void adjustmentValueChanged(AdjustmentEvent e) {  
            e.getAdjustable().setValue(e.getAdjustable().getMaximum());  
        }
        };
        designScroller.getVerticalScrollBar().addAdjustmentListener(al);*/
        elementCount++;
        if(type.startsWith("<html><b>rtfLabel"))
        {
            JPanel c = ui.getRTFLabelPanel();
            Drawer d = new Drawer(c);
            c.addMouseListener(d);
            c.addMouseMotionListener(d);
            elementsList.add(c);
        }
        if(type.startsWith("<html><b>Matrix"))
        {
            JPanel c = ui.getMatrixPanel();
            Drawer d = new Drawer(c);
            c.addMouseListener(d);
            c.addMouseMotionListener(d);
            elementsList.add(c);
        }
        if(type.startsWith("<html><b>Rich"))
        {
            JPanel c = ui.getRichTextPanel();
            Drawer d = new Drawer(c);
            c.addMouseListener(d);
            c.addMouseMotionListener(d);
            elementsList.add(c);
        }
        if(type.startsWith("<html><b>Barcode"))
        {
            JPanel c = ui.getBarCodePanel();
            Drawer d = new Drawer(c);
            c.addMouseListener(d);
            c.addMouseMotionListener(d);
            elementsList.add(c);
           
        }
        if(type.startsWith("<html><b>Audio"))
        {
            JPanel c = ui.getAudioPanel();
            Drawer d = new Drawer(c);
            c.addMouseListener(d);
            c.addMouseMotionListener(d);
            elementsList.add(c);
           
        }
        if(type.startsWith("<html><b>Video"))
        {
            JPanel c = ui.getVideoPanel();
            Drawer d = new Drawer(c);
            c.addMouseListener(d);
            c.addMouseMotionListener(d);
            elementsList.add(c);
           
        }
        if(type.startsWith("<html><b>TextField"))
        {
            JPanel c = ui.getTextFieldPanel();
            Drawer d = new Drawer(c);
            c.addMouseListener(d);
            c.addMouseMotionListener(d);
            //mainDesigner.add(c);
            elementsList.add(c);
            
            //mainDesigner.add(Box.createVerticalStrut(1));
            //mainDesigner.repaint();
            //mainDesigner.revalidate();
            //int height = (int)mainDesigner.getPreferredSize().getHeight();
            //Rectangle rect = new Rectangle(0,height,10,10);
            //mainD.scrollRectToVisible(rect);
            //writeFile(elementCount, "textfield","<TextField>\nProps##Normal");
            
        }
         if(type.startsWith("<html><b>TextArea"))
        {
            JPanel c = ui.getTextAreaPanel();
            Drawer d = new Drawer(c);
            c.addMouseListener(d);
            c.addMouseMotionListener(d);
            elementsList.add(c);
            
        }
         if(type.startsWith("<html><b>Drop"))
        {
            JPanel c = ui.getDropDownPanel();
            Drawer d = new Drawer(c);
            c.addMouseListener(d);
            c.addMouseMotionListener(d);
            elementsList.add(c);
            
        }
          if(type.startsWith("<html><b>Checkbox"))
        {
            JPanel c = ui.getCheckBoxPanel();
            Drawer d = new Drawer(c);
            c.addMouseListener(d);
            c.addMouseMotionListener(d);
            elementsList.add(c);
            
        }
          if(type.startsWith("<html><b>Radio"))
        {
            JPanel c = ui.getRadioPanel();
            Drawer d = new Drawer(c);
            c.addMouseListener(d);
            c.addMouseMotionListener(d);
            elementsList.add(c);
            
        }
           if(type.startsWith("<html><b>Time"))
        {
            JPanel c = ui.getTimePanel();
            Drawer d = new Drawer(c);
            c.addMouseListener(d);
            c.addMouseMotionListener(d);
            elementsList.add(c);
            
        }
           if(type.startsWith("<html><b>Date"))
        {
            JPanel c = ui.getDatePanel();
            Drawer d = new Drawer(c);
            c.addMouseListener(d);
            c.addMouseMotionListener(d);
            elementsList.add(c);
            
        }
        if(type.startsWith("<html><b>Image"))
        {
            JPanel c = ui.getImagePanel();
            Drawer d = new Drawer(c);
            c.addMouseListener(d);
            c.addMouseMotionListener(d);
            elementsList.add(c);
            
        }
        if(type.startsWith("<html><b>Location"))
        {
            JPanel c = ui.getLocationPanel();
            Drawer d = new Drawer(c);
            c.addMouseListener(d);
            c.addMouseMotionListener(d);
            elementsList.add(c);
            
        }
        if(type.startsWith("<html><b>Numeric"))
        {
            JPanel c = ui.getSpinnerPanel();
            Drawer d = new Drawer(c);
            c.addMouseListener(d);
            c.addMouseMotionListener(d);
            elementsList.add(c);
        }
        if(type.startsWith("<html><b>On"))
        {
            JPanel c = ui.getONPanel();
            Drawer d = new Drawer(c);
            c.addMouseListener(d);
            c.addMouseMotionListener(d);
            elementsList.add(c);
        }
        mainD.setListData(elementsList);
        int lastIndex = mainD.getModel().getSize() - 1;
        if (lastIndex >= 0) {
           mainD.ensureIndexIsVisible(lastIndex);
}
        
        
        //mainDesigner.add(Box.createRigidArea(new Dimension(5,0)));
        //designScroller.getVerticalScrollBar().removeAdjustmentListener(al);
        boolean c = false;
        try{
            File ft = new File("autoSave.jaj");
            if(ft.exists())
            {
                c=true;
            }FileWriter f = new FileWriter("10110.jaj");f.write("");f.flush();f.close();}catch(Exception e){}
        if(auto.isSelected()==true)
        {
            saveSectionWork();
        }
    }
    
    
    
    
    
    public static class ValueExportTransferHandler extends TransferHandler {

        public static final DataFlavor SUPPORTED_DATE_FLAVOR = DataFlavor.stringFlavor;
        private String value;

        public ValueExportTransferHandler(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        @Override
        public int getSourceActions(JComponent c) {
            return DnDConstants.ACTION_COPY_OR_MOVE;
        }

        @Override
        protected Transferable createTransferable(JComponent c) {
            Transferable t = new StringSelection(getValue());
            return t;
        }

        @Override
        protected void exportDone(JComponent source, Transferable data, int action) {
            super.exportDone(source, data, action);
            // Decide what to do after the drop has been accepted
        }

    }

    public static class ValueImportTransferHandler extends TransferHandler {

        public static final DataFlavor SUPPORTED_DATE_FLAVOR = DataFlavor.stringFlavor;

        public ValueImportTransferHandler() {
        }

        @Override
        public boolean canImport(TransferHandler.TransferSupport support) {
            return support.isDataFlavorSupported(SUPPORTED_DATE_FLAVOR);
        }

        @Override
        public boolean importData(TransferHandler.TransferSupport support) {
            boolean accept = false;
            if (canImport(support)) {
                try {
                    Transferable t = support.getTransferable();
                    Object value = t.getTransferData(SUPPORTED_DATE_FLAVOR);
                    addElementToDesigner(value.toString());
                } catch (Exception exp) {
                    exp.printStackTrace();
                }
            }
            return accept;
        }
    }
    
    
    
    
    public static class Drawer implements MouseListener, MouseMotionListener{



	//flags used to turn on/off draggable scrolling directions

	public static final int DRAGABLE_HORIZONTAL_SCROLL_BAR = 1;

	public static final int DRAGABLE_VERTICAL_SCROLL_BAR = 2;

	

	//defines the intensite of automatic scrolling.

	private int scrollingIntensity = 10;

	

	//value used to descrease scrolling intensity during animation

	private double damping = 0.05;



	//indicates the number of milliseconds between animation updates. 

	private int animationSpeed = 20;

	

	//Animation timer

	private javax.swing.Timer animationTimer = null;

	

	//the time of the last mouse drag event

	private long lastDragTime = 0;

	

	private Point lastDragPoint = null;

	

	//animation rates

	private double pixelsPerMSX;

	private double pixelsPerMSY;



	//flag which defines the draggable scroll directions

	private int scrollBarMask = DRAGABLE_HORIZONTAL_SCROLL_BAR | DRAGABLE_VERTICAL_SCROLL_BAR;

		

	//the draggable component

	private final Component draggableComponent;

	

	//the JScrollPane containing the component - programmatically determined. 

	private JScrollPane scroller = null;

	

	//the default cursor

	private Cursor defaultCursor;

	

	//List of drag speeds used to calculate animation speed

	//Uses the Point2D class to represent speeds rather than locations

	private java.util.List<Point2D.Double> dragSpeeds = new ArrayList<Point2D.Double>();
        
        private boolean isClicked = false;

        
        
	

	public Drawer(Component c){

		draggableComponent = c;

		defaultCursor = draggableComponent.getCursor();

		draggableComponent.addPropertyChangeListener(new PropertyChangeListener(){



			@Override

			public void propertyChange(PropertyChangeEvent arg0) {

				setScroller();

				defaultCursor = draggableComponent.getCursor();

			}

		});

		setScroller();

	}

	

	private void setScroller(){

		Component c = getParentScroller(draggableComponent);

		if ( c != null ){

			scroller = (JScrollPane)c;

			

		}else{

			scroller = null;

		}

	}



	/**



	 * Sets the Draggable elements - the Horizontal or Vertical Direction. One

	 * can use a bitmasked 'or' (HORIZONTAL_SCROLL_BAR | VERTICAL_SCROLL_BAR ).

	 * @param mask One of HORIZONTAL_SCROLL_BAR, VERTICAL_SCROLL_BAR, or HORIZONTAL_SCROLL_BAR | VERTICAL_SCROLL_BAR 

	 */

	public void setDraggableElements(int mask){

		scrollBarMask = mask;

	}



	/**

	 * Sets the scrolling intensity - the default value being 5. Note, that this has an

	 * inverse relationship to intensity (1 has the biggest difference, higher numbers having

	 * less impact). 

	 * @param intensity The new intensity value (Note the inverse relationship).

	 */

	public void setScrollingIntensity(int intensity){

		scrollingIntensity = intensity;

	}



	/**

	 * Sets how frequently the animation will occur in milliseconds. Default 

	 * value is 30 milliseconds. 60+ will get a bit flickery.

	 * @param timing The timing, in milliseconds.

	 */

	public void setAnimationTiming(int timing){

		animationSpeed = timing;

	}

	

	/**

	 * Sets the animation damping. 

	 * @param damping The new value

	 */

	public void setDamping(double damping){

		this.damping = damping;

	}

	

	/**

	 * Empty implementation

	 */

	public void mouseEntered(MouseEvent e){
        
        Component c = e.getComponent();
            if(c instanceof JPanel )
            {
                JPanel aa = (JPanel) c;
                aa.setBorder(new javax.swing.border.LineBorder(Color.orange,5));
            }
        
        }



	/**

	 * Empty implementation

	 */

	public void mouseExited(MouseEvent e){
        
        Component c = e.getComponent();
            if(c instanceof JPanel )
            {
                if(isClicked == false)
                {
                    JPanel aa = (JPanel) c;
                    aa.setBorder(null);
                }
                isClicked = false;
            }
        
        }



	/**

	 * Mouse pressed implementation

	 */

	public void mousePressed(MouseEvent e){	
checkMouseTrigger (e);
		if ( animationTimer != null && animationTimer.isRunning() ){

			animationTimer.stop();

		}

		//draggableComponent.setCursor(new Cursor(Cursor.MOVE_CURSOR));

		dragSpeeds.clear();

		lastDragPoint = e.getPoint();


	}

	/**

	 * Mouse released implementation. This determines if further animation

	 * is necessary and launches the appropriate times. 

	 */

	public void mouseReleased(MouseEvent e){
checkMouseTrigger (e);
		draggableComponent.setCursor(defaultCursor);

		if ( scroller == null ){

			return;

		}



		//make sure the mouse ended in a dragging event

		long durationSinceLastDrag = System.currentTimeMillis() - lastDragTime;

		if ( durationSinceLastDrag > 20 ){

			return;

		}



		//get average speed for last few drags

		pixelsPerMSX = 0;

		pixelsPerMSY = 0;

		int j = 0;

		for ( int i = dragSpeeds.size() - 1; i >= 0 && i > dragSpeeds.size() - 6; i--, j++ ){

			pixelsPerMSX += dragSpeeds.get(i).x;

			pixelsPerMSY += dragSpeeds.get(i).y;

		}

		pixelsPerMSX /= -(double)j;

		pixelsPerMSY /= -(double)j;



		//start the timer

		if ( Math.abs(pixelsPerMSX) > 0 || Math.abs(pixelsPerMSY) > 0 ){

			animationTimer = new javax.swing.Timer(animationSpeed, new ScrollAnimator());

			animationTimer.start();

		}



	}



	/**

	 * Empty implementation

	 */
        private void checkMouseTrigger (MouseEvent me) {
          if (me.isPopupTrigger ())
            pop.show (me.getComponent (), me.getX (), me.getY ());
        }
	public void mouseClicked(MouseEvent e){
            checkMouseTrigger (e);
           
    
    ListModel model = mainD.getModel();
    Object item = model.getElementAt(mainD.getSelectedIndex());
    JPanel jp = (JPanel) item;
    String pname = jp.getName();
    elementName = pname;
    showPropertiesPanel(previousIndex);
    isClicked = true;
            
        
        }

	

	/**

	 * MouseDragged implementation. Sets up timing and frame animation.

	 */

	public void mouseDragged(MouseEvent e){
checkMouseTrigger (e);
		if ( scroller == null ){

			return;

		}

		Point p = e.getPoint();

		int diffx = p.x - lastDragPoint.x;

		int diffy = p.y - lastDragPoint.y;

		lastDragPoint = e.getPoint();

		

		//scroll the x axis

		if ( (scrollBarMask & DRAGABLE_HORIZONTAL_SCROLL_BAR ) != 0 ){

			getHorizontalScrollBar().setValue( getHorizontalScrollBar().getValue() - diffx);

		}

		//the Scrolling affects mouse locations - offset the last drag point to compensate

		lastDragPoint.x = lastDragPoint.x - diffx;

		

		//scroll the y axis

		if ( (scrollBarMask & DRAGABLE_VERTICAL_SCROLL_BAR ) != 0 ){

			getVerticalScrollBar().setValue( getVerticalScrollBar().getValue() - diffy);

		}

		//the Scrolling affects mouse locations - offset the last drag point to compensate

		lastDragPoint.y = lastDragPoint.y - diffy;

		

		//add a drag speed

		dragSpeeds.add( new Point2D.Double(

				(e.getPoint().x - lastDragPoint.x), 

				(e.getPoint().y - lastDragPoint.y) ) );



		lastDragTime = System.currentTimeMillis();

		

	}



	/**

	 * Empty

	 */

	public void mouseMoved(MouseEvent e){checkMouseTrigger (e);}





	/**

	 * Private inner class which accomplishes the animation. 

	 * @author Greg Cope

	 *

	 */

	private class ScrollAnimator implements ActionListener{



		/**

		 * Performs the animation through the setting of the JScrollBar values.

		 */

		public void actionPerformed(ActionEvent e){

			

			//damp the scrolling intensity

			pixelsPerMSX -= pixelsPerMSX * damping;

			pixelsPerMSY -= pixelsPerMSY * damping;

			

			//check to see if timer should stop.

			if ( Math.abs(pixelsPerMSX) < 0.01 && Math.abs(pixelsPerMSY) < 0.01  ){

				animationTimer.stop();

				return;

			}

			

			//calculate new X value

			int nValX = getHorizontalScrollBar().getValue() + (int)(pixelsPerMSX * scrollingIntensity);

			int nValY = getVerticalScrollBar().getValue() + (int)(pixelsPerMSY * scrollingIntensity);



			//Deal with out of scroll bounds

			if ( nValX <= 0 ){

				nValX = 0;

			}else if ( nValX >= getHorizontalScrollBar().getMaximum()){

				nValX = getHorizontalScrollBar().getMaximum();

			}

			if ( nValY <= 0 ){

				nValY = 0;

			}else if ( nValY >= getVerticalScrollBar().getMaximum() ){

				nValY = getVerticalScrollBar().getMaximum();

			}

			

			//Check again to see if timer should stop

			if ( (nValX == 0 || nValX == getHorizontalScrollBar().getMaximum()) && Math.abs(pixelsPerMSY) < 1  ){

				animationTimer.stop();

				return;

			}

			if ( (nValY == 0 || nValY == getVerticalScrollBar().getMaximum()) && Math.abs(pixelsPerMSX) < 1  ){

				animationTimer.stop();

				return;

			}

			

			//Set new values

			if ( (scrollBarMask & DRAGABLE_HORIZONTAL_SCROLL_BAR ) != 0 ){

				getHorizontalScrollBar().setValue(nValX);

			}

			if ( (scrollBarMask & DRAGABLE_VERTICAL_SCROLL_BAR ) != 0 ){

				getVerticalScrollBar().setValue(nValY);

			}



		}



	}



	/**

	 * Utility to retrieve the Horizontal Scroll Bar.

	 * @return

	 */

	private JScrollBar getHorizontalScrollBar(){

		return scroller.getHorizontalScrollBar();

	}



	/**

	 * Utility to retrieve the Vertical Scroll Bar

	 * @return

	 */	

	private JScrollBar getVerticalScrollBar(){

		return scroller.getVerticalScrollBar();

	}

	

	/**

	 * 

	 * @param c

	 * @return

	 */

	private Component getParentScroller(Component c){

		Container parent = c.getParent();

		if ( parent != null && parent instanceof Component ){

			Component parentC = (Component)parent;

			if ( parentC instanceof JScrollPane ){

				return parentC;

			}else{

				return getParentScroller(parentC);

			}

		}

		return null;

	}

}
    
    
class LabelCellRenderer1 extends JLabel 
            implements ListCellRenderer {

	

  public LabelCellRenderer1() {
    setOpaque(true);
  }

 
  public Component
  getListCellRendererComponent(
               JList list, 
               Object value, 
               int index, 
               boolean isSelected, 
               boolean cellHasFocus) {
	ImageIcon m1= new ImageIcon(getClass().getResource("images/done.png"));
	setText(value.toString());
        setIcon(m1);
	setFont(new Font("Dialog",Font.BOLD,12));
	
    setBackground(isSelected ?
            list.getSelectionBackground() : Color.white);
    setForeground(isSelected ?
            Color.white : list.getSelectionBackground());
    return this;
  }
}
    
    class PopupMenuListener extends MouseAdapter {
       	public void mousePressed(MouseEvent me) {
            showPopup(me);
        }

        public void mouseReleased(MouseEvent me) {
            showPopup(me);
        }

        private void showPopup(MouseEvent me) {
            if (me.isPopupTrigger()) {
                pop.show(me.getComponent(),
                               me.getX(), me.getY());
            }
        }
    }
    
    
    public void tarFolder(String parent, String path, TarOutputStream out) throws IOException {
        BufferedInputStream origin = null;
        File f = new File( path );
        String files[] = f.list();

        // is file
        if( files == null ) {
            files = new String[1];
            files[0] = f.getName();
        }

        parent = ( ( parent == null ) ? ( f.isFile() ) ? "" : f.getName() + "/" : parent + f.getName() + "/" );

        for( int i = 0; i < files.length; i++ ) {
            System.out.println( "Adding: " + files[i] );
            File fe = f;
            byte data[] = new byte[BUFFER];

            if( f.isDirectory() ) {
                fe = new File( f, files[i] );
            }

            if( fe.isDirectory() ) {
                String[] fl = fe.list();
                if( fl != null && fl.length != 0 ) {
                    tarFolder( parent, fe.getPath(), out );
                } else {
                    TarEntry entry = new TarEntry( fe, parent + files[i] + "/" );
                    out.putNextEntry( entry );
                }
                continue;
            }

            FileInputStream fi = new FileInputStream( fe );
            origin = new BufferedInputStream( fi );

            TarEntry entry = new TarEntry( fe, parent + files[i] );
            out.putNextEntry( entry );

            int count;
            int bc = 0;
            while(( count = origin.read( data ) ) != -1) {
                out.write( data, 0, count );
                bc += count;
            }

            out.flush();

            origin.close();
        }
    }
    
    
    
    public class CheckListCellRenderer extends JPanel implements ListCellRenderer{ 
    private ListCellRenderer delegate; 
    private ListSelectionModel selectionModel; 
    private JCheckBox checkBox = new JCheckBox(); 
 
    public CheckListCellRenderer(ListCellRenderer renderer, ListSelectionModel selectionModel){ 
        this.delegate = renderer; 
        this.selectionModel = selectionModel; 
        setLayout(new BorderLayout()); 
        setOpaque(false); 
        checkBox.setOpaque(false); 
    } 
 
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus){ 
        Component renderer = delegate.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus); 
        checkBox.setSelected(selectionModel.isSelectedIndex(index)); 
        removeAll(); 
        add(checkBox, BorderLayout.WEST); 
        add(renderer, BorderLayout.CENTER); 
        return this; 
    } 
}
    
    public class CheckListManager extends MouseAdapter implements ListSelectionListener, ActionListener{ 
    private DefaultListSelectionModel selectionModel = new DefaultListSelectionModel(); 
    private JList list = new JList(); 
    int hotspot = new JCheckBox().getPreferredSize().width; 
 
    public CheckListManager(JList list){ 
        this.list = list; 
        list.setCellRenderer(new CheckListCellRenderer(list.getCellRenderer(), selectionModel)); 
        list.registerKeyboardAction(this, KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), JComponent.WHEN_FOCUSED); 
        list.addMouseListener(this); 
        selectionModel.addListSelectionListener(this); 
    } 
 
    public DefaultListSelectionModel getSelectionModel(){ 
        return selectionModel; 
    } 
 
    private void toggleSelection(int index){ 
        if(index<0) 
            return; 
 
        if(selectionModel.isSelectedIndex(index)) 
            selectionModel.removeSelectionInterval(index, index); 
        else 
            selectionModel.addSelectionInterval(index, index); 
    } 
    
    private void selectAll(Vector v)
    { 
        int index=0;
            for(index=0;index<v.size();index++ )
            {
                selectionModel.addSelectionInterval(index, index); 
            }
    } 
    private void deSelectAll(Vector v)
    { 
        int index=0;
            for(index=0;index<v.size();index++ )
            {
                selectionModel.removeSelectionInterval(index, index); 
            }
    }
 
    /*------------------------------[ MouseListener ]-------------------------------------*/ 
 
    public void mouseClicked(MouseEvent me){ 
        int index = list.locationToIndex(me.getPoint()); 
        if(index<0) 
            return; 
        if(me.getX()>list.getCellBounds(index, index).x+hotspot) 
            return; 
        toggleSelection(index); 
    } 
 
    /*-----------------------------[ ListSelectionListener ]---------------------------------*/ 
 
    public void valueChanged(ListSelectionEvent e){ 
        list.repaint(list.getCellBounds(e.getFirstIndex(), e.getLastIndex())); 
    } 
 
    /*-----------------------------[ ActionListener ]------------------------------*/ 
 
    public void actionPerformed(ActionEvent e){ 
        toggleSelection(list.getSelectedIndex()); 
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
    
    public void getAccessList(final JTable lt)
	{
		try
		{
			clearTable(lt);
			//ArrayList<String> list = main.getAccess();
                        ArrayList<String> list = new ArrayList();
                        String users = main.readSectionData(name);
                        List<Access> details = null;
                        try
                        {
                            Gson gson = new Gson();
                            Type collectionType = new TypeToken<List<Access>>(){}.getType();
                            details = gson.fromJson(users, collectionType);
                        }
                        catch(Exception e)
                        {
                            e.printStackTrace();
                        }
			String[] headerStrings = {"","User","Section Access","Code"};
			rows2 = new Vector();
			header2 = new Vector();
                        try{
			for (int i = 0; i < headerStrings.length; i++)
				header2.add(headerStrings[i]);
				lt.getTableHeader().setReorderingAllowed(false);
			for (Access detail : details)
                        {
				Vector v= new Vector();
				v.add(Boolean.FALSE);
				v.add(detail.getUser());
				v.add(detail.getSection());
				v.add(detail.getCode());
				rows2.add(v);
			}}catch(Exception er){}
			
			DefaultTableModel mds= new  javax.swing.table.DefaultTableModel(rows2,header2) 
			{
				Class[] types = new Class [] {
					Boolean.class,java.lang.String.class,java.lang.String.class,java.lang.String.class
				};
				boolean[] canEdit = new boolean [] {
					true,false, false,false
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
			tb.setPreferredWidth(30);
                        tb.setHeaderRenderer(new CheckBoxHeader(new MyItemListener(lt)));
			
			 tb=lt.getColumnModel().getColumn(1);
			tb.setPreferredWidth(100);
			
			tb=lt.getColumnModel().getColumn(2);
			tb.setPreferredWidth(200);
			
			tb=lt.getColumnModel().getColumn(3);
			tb.setPreferredWidth(100);
			
			lt.getColumnModel().
      getColumn(0).setCellRenderer(
				new TableCellRenderer() {
                            // the method gives the component  like whome the cell must be rendered
                            public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean isFocused, int row, int col) {
						boolean marked = (Boolean) value;
						final JCheckBox rendererComponent = new JCheckBox();
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
			
			
			
		}
		catch(Exception er)
		{
			er.printStackTrace();
		}
	
	
	}
    
    
    
    public void getCertList(final JTable lt)
	{
		try
		{
			clearTable(lt);
			//ArrayList<String> list = main.getAccess();
                        ArrayList<String> list = new ArrayList();
                        String users = main.readFile("projects/"+name+"/certList.jaj");
                        List<Access> details = null;
                        try
                        {
                            Gson gson = new Gson();
                            Type collectionType = new TypeToken<List<Access>>(){}.getType();
                            details = gson.fromJson(users, collectionType);
                        }
                        catch(Exception e)
                        {
                            e.printStackTrace();
                        }
			String[] headerStrings = {"","Name","Section","Code"};
			rows2 = new Vector();
			header2 = new Vector();
			for (int i = 0; i < headerStrings.length; i++)
				header2.add(headerStrings[i]);
				lt.getTableHeader().setReorderingAllowed(false);
                                try{
			for (Access detail : details)
                        {
				Vector v= new Vector();
				v.add(Boolean.FALSE);
				v.add(detail.getUser());
				v.add(detail.getSection());
				v.add(detail.getCode());
				rows2.add(v);
			}
                                }catch(Exception e){}
			DefaultTableModel mds= new  javax.swing.table.DefaultTableModel(rows2,header2) 
			{
				Class[] types = new Class [] {
					Boolean.class,java.lang.String.class,java.lang.String.class,java.lang.String.class
				};
				boolean[] canEdit = new boolean [] {
					true,false, false,false
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
			tb.setPreferredWidth(30);
                        tb.setHeaderRenderer(new CheckBoxHeader(new MyItemListener(lt)));
			
			 tb=lt.getColumnModel().getColumn(1);
			tb.setPreferredWidth(100);
			
			tb=lt.getColumnModel().getColumn(2);
			tb.setPreferredWidth(200);
			
			tb=lt.getColumnModel().getColumn(3);
			tb.setPreferredWidth(100);
			
			lt.getColumnModel().
      getColumn(0).setCellRenderer(
				new TableCellRenderer() {
                            // the method gives the component  like whome the cell must be rendered
                            public Component getTableCellRendererComponent(
							JTable table, Object value, boolean isSelected,
							boolean isFocused, int row, int col) {
						boolean marked = (Boolean) value;
						final JCheckBox rendererComponent = new JCheckBox();
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
			
		}
		catch(Exception er)
		{
			er.printStackTrace();
		}
	
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
    
    
}
