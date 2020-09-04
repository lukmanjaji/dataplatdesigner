/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jajitech.desktop.xdata.controller;

import com.jajitech.desktop.xdata.constants.Constants;
import com.jajitech.desktop.xdata.pojos.Project;
import com.jajitech.desktop.xdata.saved.SavedProject;
import com.jajitech.desktop.xdata.ui.JMultiLineLabel;
import com.jajitech.desktop.xdata.ui.UIBuilder;
import com.jajitech.desktop.xdata.ui.UIElementsReader;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import static java.lang.System.getProperty;
import static java.lang.System.out;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import static java.util.Arrays.asList;
import java.util.List;
import java.util.Vector;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import static org.apache.commons.io.FileUtils.deleteDirectory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.xeustechnologies.jtar.TarEntry;
import org.xeustechnologies.jtar.TarOutputStream;

/**
 *
 * @author Jaji
 */
public class MainDAO {
    
    public MainDAO()
    {
        
    }
    Constants constant = new Constants();
    
    public void copy(String fromName, String toName) throws IOException
    {
        File    fromFile, toFile;
                
        // "get File objects from Strings"
        fromFile = new File(fromName);
        toFile = new File(toName);

        // "First make sure the source file exists, is a file,
        //  and is readable"
        if (!fromFile.exists())
        {
            throw new IOException(
                "FileCopy: no such source file: " + fromName);
        }
        if (!fromFile.isFile())
        {
            throw new IOException(
                "FileCopy: can't copy directory: " + fromName);
        }
        if (!fromFile.canRead())
        {
            throw new IOException(
                "FileCopy: source file is unreadable: " + fromName);
        }

        // "If the destination is a directory, use the source
        //  file name as the destination file name"
        if (toFile.isDirectory())
        {
            toFile = new File(toFile, fromFile.getName());
        }

        // "If the destination exists, make sure it is a writeable
        //  file and ask before overwriting it. If the destination
        //  doesn't exist, make sure the directory exists and is
        //  writeable"
        if (toFile.exists())
        {
            if (!toFile.canWrite())
            {
                throw new IOException(
                    "FileCopy: destination file is unwriteable: " +
                    toName);
            }
                        
         
            out.flush();  // flush the System.out buffer...

                
        }

        else
        {                       
            // "If the file doesn't exist, check if directory exists
            //  and is writeable. If getParent() returns null, then
            //  the directory is the current directory so look up the
            //  user.dir system property to find out what that is." 
                        
            // "Get the destination directory"
            // (with regard to how the file name was written ---
            // was it in a path or not?)
            String parent = toFile.getParent();

            // ...or current working directory"
            if (parent == null)
            {
                parent = getProperty("user.dir");
            }

            // "Convert it to a file"
            File dir = new File(parent);
                
            if (!dir.exists())
            {
                throw new IOException(
                    "FileCopy: destination directory does't exist: " 
                    + parent);
            }               
            if (dir.isFile())
            {
                throw new IOException(
                    "FileCopy: destionation is not a directory: " 
                    + parent);
            }
            if (!dir.canWrite())
            {
                throw new IOException(
                    "FileCopy: destination directory is unwriteable: " 
                    + parent);
            }
        }

        // whew!
        // "If we've gotten this far, then everything is okay.
        //  So we copy the file, a buffer of bytes at a time."

        // "Stream to read from source"
        FileInputStream from = null;

        // "Stream to write to destination"
        FileOutputStream to = null;
                                                        
        try
        {
            // "Create input stream"
            from = new FileInputStream(fromFile);

            // "Create output stream"
            to = new FileOutputStream(toFile);
        
            // "A buffer to hold file contents"
            byte[] buffer = new byte[4096];
                
            // "How many bytes in buffer"
            int     bytesRead;

            // "Read a chunk of bytes into the buffer, then write
            //  them out, looping until we reach the end of file
            //  (when read() returns -1). Note the combination 
            //  of assignment and comparison in this while loop.
            //  This is a common I/O programming idiom."

            // "Read bytes until EOF"
            while ((bytesRead = from.read(buffer)) != -1)
            {
                // "write bytes"
                to.write(buffer, 0, bytesRead);
            }
        }

        // "Always close the streams, even if exceptions were
        // thrown."
        finally
        {
            if (from != null)
            {
                try
                {
                    from.close();
                }
                catch(IOException exc)
                {
                }
            }
                        
            if (to != null)
            {
                try
                {
                    to.close();
                }
                catch(IOException exc)
                {
                }
            }
        }
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
        x.add(l);
        listData.add(x);
        list.setListData(listData);
    }
    
    
    public void buildProject(String name, JLabel code, JList status, Vector listData)
    {
        boolean dd = false;
        JSONArray jsonArray = new JSONArray();
        JSONObject object = new JSONObject();
        object.put("name", name);
        String id = readFile("projects/"+name+"/identifier.jaj");
        object.put("id", id);
        String singleList = readFile("projects/"+name+"/singleList.jaj");
        object.put("singleList", singleList);
        object.put("access", readFile("projects/"+name+"/access.jaj"));
        String account[] = readFile("account.jaj").split("##");
        object.put("user", account[0].trim());
        List l = this.getProjectSections("projects/"+name);
        String sections = "";
        setBuildText(status, "Building project...", 0 , listData);
        try
        {
            File ff = new File("projects/"+name+"/build");
            deleteDirectory(ff);
            File d = new File("projects/"+name+"/build");
            d.mkdir();
        }catch(Exception er){er.printStackTrace();dd=false;}
        for(int x=0;x<11;x++)
        {
            out.print(x);
        }
        //status.setText("25% completed...\n");
        File yas = null;
        for(Object x: l)
        {
            sections = sections + x.toString().toLowerCase().replace(" ", "_")+"##";
            try
            {
                this.copy("projects/"+name+"/sections/"+x+"/sectionData.jaj", "projects/"+name+"/build/"+x.toString().toLowerCase().replace(" ", "_")+".jaj");
                File in = new File("projects/"+name+"/sections/"+x+"/"+x.toString().toLowerCase()+".yas");
                if(in.exists())
                {
                    yas = in;
                    this.copy("projects/"+name+"/sections/"+x+"/"+x.toString().toLowerCase()+".yas", "projects/"+name+"/build/"+x.toString().toLowerCase()+".yas");
                }
                else
                {
                    yas = in;
                    yas.createNewFile();
                    this.copy("projects/"+name+"/sections/"+x+"/"+x.toString().toLowerCase()+".yas", "projects/"+name+"/build/"+x.toString().toLowerCase()+".yas");
                }
            }catch(Exception er){er.printStackTrace();dd=false;}
        }
        //status.setText("55% completed...\n");
        object.put("sections", sections);
        jsonArray.add(object);
        try
        {
            try (FileWriter fa = new FileWriter("projects/"+name+"/build/project.jaj")) {
                fa.write(jsonArray.toJSONString());
                fa.flush();
            }
            code.setText("Project Code: "+id);
            
            FileOutputStream dest = new FileOutputStream( "projects/"+name+"/build/"+id+".collector");
            TarOutputStream out = new TarOutputStream( new BufferedOutputStream( dest ) );
            File[] filesToTar=new File[(l.size()*2)+4];
            filesToTar[0]=new File("projects/"+name+"/certList.jaj");
            filesToTar[1]=new File("projects/"+name+"/accessList.jaj");
            filesToTar[2]=new File("projects/"+name+"/build/project.jaj");
            filesToTar[3]=new File("projects/"+name+"/singleList.jaj");
            int x=4;
            for(Object t: l)
            {
                String sec = t.toString();
                filesToTar[x] = new File("projects/"+name+"/build/"+sec.toLowerCase().replace(" ", "_")+".jaj");
                x++;
            }
            for(Object t: l)
            {
                filesToTar[x]= new File("projects/"+name+"/sections/"+t+"/"+t.toString().toLowerCase()+".yas");
                x++;
            }
            //status.setText("75% completed...\n");
            for(File f:filesToTar)
            {
               out.putNextEntry(new TarEntry(f, f.getName()));
                try (BufferedInputStream origin = new BufferedInputStream(new FileInputStream( f ))) {
                    int count;
                    byte data[] = new byte[2048];
                    while((count = origin.read(data)) != -1) {
                        out.write(data, 0, count);
                    }
                    out.flush();
                }
            }
            out.close();
            dd = true;
            //status.setText("75% completed...\n");
        }
        catch(Exception er)
        {
            er.printStackTrace();
        }
        File v = new File("projects/"+name+"/build");
        if(!v.exists())
        {
            buildProject(name,code, status, listData);
            out.println("used option b");
        }
        setBuildText(status, "Building project...", 1 , listData);
    }
    
    
    
    
    public String doHttpUrlConnectionAction(String desiredUrl) throws Exception
    {
        URL url = null;
        BufferedReader reader = null;
        StringBuilder stringBuilder;
        try
        {
            url = new URL(desiredUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(6*1000);
            try
            {
                connection.connect();
            }catch(Exception ee){//ee.printStackTrace();
                return "error";}
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            stringBuilder = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null)
            {
                stringBuilder.append(line + "\n");
            }
            return stringBuilder.toString();
        }
        catch (Exception e)
        {
          //e.printStackTrace();
          return "error";
        }
        finally
        {
          // close the reader; this can throw an exception too, so
          // wrap it in another try/catch block.
          if (reader != null)
          {
            try
            {
              reader.close();
            }
            catch (IOException ioe)
            {
              //ioe.printStackTrace();
              return "error";
            }
          }
        }
  }
    
    
    public void updateProject(Project map)
    {
        String id = map.getPid();
        String project_date = map.getProject_date().toString();
        String target = map.getTarget();
        String name[] = map.getName().split("##");
        out.println(name[1]+" && "+target+" && "+project_date);
        try
        {
            try (FileWriter ff = new FileWriter("projects/"+name[0]+"/project.jaj")) {
                String toWrite = "Project_Id:"+id+"\nProject_Name:"+name[1]+"\nTarget:"+target+"\nProject_Date:"+project_date;
                ff.write(toWrite);
                ff.flush();
            }
            File f = new File("projects/"+name[0]);
            File f2 = new File("projects/"+name[1]);
            f.renameTo(f2);
        }
        catch(Exception er)
        {
            er.printStackTrace();
        }
        
    }
    
    
    public String readFile(String path)
    {
        String output = "";
        try{
	FileInputStream fis = new FileInputStream(path);
            try (BufferedReader br = new BufferedReader(new InputStreamReader(fis))) {
                String line = null;
                while ((line = br.readLine()) != null) {
                    output = output+line;
                }   }
        }catch(Exception er){}
        return output;
    }
    
    
    
    
    
    public String readAccessCert(String project, String type) throws IOException
    {
	FileInputStream fis = new FileInputStream("projects/"+project+"/"+type);
        String output;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(fis))) {
            String line = null;
            output = "";
            while ((line = br.readLine()) != null) {
                output = output+line;
            }
        }
        return output;
    }
    
    
    
    public String readSectionData(String project) throws IOException
    {
	FileInputStream fis = new FileInputStream("projects/"+project+"/accessList.jaj");
        String output;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(fis))) {
            String line = null;
            output = "";
            while ((line = br.readLine()) != null) {
                output = output+line;
            }
        }
        return output;
    }
     
     
     public String readCertList(String project) throws IOException
    {
	FileInputStream fis = new FileInputStream("projects/"+project+"/certList.jaj");
        String output;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(fis))) {
            String line = null;
            output = "";
            while ((line = br.readLine()) != null) {
                output = output+line;
            }
        }
        return output;
    }
    
    
    
    public String saveProject(Project map)
    {
        String id = map.getPid();
        String project_date = map.getProject_date().toString();
        String target = map.getTarget();
        String name = map.getName();
        File f = new File("projects/"+name);
        f.mkdir();
        f = new File("projects/"+name+"/sections");
        f.mkdir();
        int qid = 1;
        try
        {
            try (FileWriter ff = new FileWriter("projects/"+name+"/project.jaj")) {
                String toWrite = "Project_Id:"+id+"\nProject_Name:"+name+"\nTarget:"+target+"\nProject_Date:"+project_date;
                ff.write(toWrite);
            }
            
            FileWriter f1 = new FileWriter("projects/"+name+"/accessList.jaj");
            f1.write("");
            f1.close();
            
            FileWriter f1q = new FileWriter("projects/"+name+"/singleList.jaj");
            f1q.write("");
            f1q.close();
            
            FileWriter f2 = new FileWriter("projects/"+name+"/access.jaj");
            f2.write("Public");
            f2.close();
            
            FileWriter f2a = new FileWriter("projects/"+name+"/identifier.jaj");
            f2a.write(id);
            f2a.close();
            
            FileWriter f3 = new FileWriter("projects/"+name+"/certList.jaj");
            f3.write("");
            f3.close();
        }
        catch(Exception er)
        {
            er.printStackTrace();
            return "";
        }
        /*
        Transaction transaction = null;
        boolean done = false;
        int qid=0;
        try
        {
            Session session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.save(map);
            session.persist(map);
            qid = map.getId();
            transaction.commit();
            session.close();
        }
        catch (HibernateException e) {
            
            done = false;
            //transaction .rollback();
            e.printStackTrace();
            qid = 0;
        } finally {
            
        }
                */
        return id+"##"+name+"##"+target+"##"+project_date;
    }
    
    
    public void doDB()
    {
        /*
        Transaction transaction = null;
        boolean done = false;
        String res = null;
        java.sql.Date dt = null;
        try
        {
            Session session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.close();
        }
        catch (HibernateException e) {
            
            done = false;
            //transaction .rollback();
            e.printStackTrace();
        } finally {
            
        }
        */

    }
    
    
    
    public void loadSection(String name, String section, JList main, Vector elementsList)
    {
        UIElementsReader ui = new UIElementsReader(name, section, elementsList);
        ui.load(main);
    }
    
    
    public List getProjectSections(String path)
    {
        List v = new ArrayList();
        try
        {
            File file = new File(path+"/"+"sections");
            String[] directories = file.list((File current, String name) -> new File(current, name).isDirectory());
            String d[] = Arrays.toString(directories).replace("[", "").replace("]", "").split(", ");
          v.addAll(asList(d));
        }
        catch(Exception er)
        {
            er.printStackTrace();
        }
        return v;
    }
    
    public void addToElementsList(JPanel c, int type)
    {
        
    }
    
    
    
    public JPanel pasteElement(String elementName)
    {
        out.println(elementName);
        UIBuilder ui = new UIBuilder();
        String name[] = elementName.split("##");
        String type = name[0];
        JPanel y = null;
                    String caption = name[1];
                    String items = name[2];
                    String required = name[3];
                    String eid = constant.randomNumeric(12);
                    
                    if(type.startsWith("label"))
                    {
                        elementName = "label"+eid+"##"+caption+"##"+items+"##"+required;
                        JPanel l = ui.getLabelPanel();
                        l.setName(elementName);
                        Component[] c = l.getComponents();
                        JMultiLineLabel jl = (JMultiLineLabel)c[1];
                        jl.setText(caption);
                        SavedProject.Drawer d = new SavedProject.Drawer(l);
                        l.addMouseListener(d);
                        l.addMouseMotionListener(d);
                        y = l;
                    }
                    if(type.startsWith("textfield"))
                    {
                        elementName = "textfield"+eid+"##"+caption+"##"+items+"##"+required;
                        JPanel l = ui.getTextFieldPanel();
                        l.setName(elementName);
                        Component[] c = l.getComponents();
                        JMultiLineLabel jl = (JMultiLineLabel)c[1];
                        jl.setText(caption);
                        SavedProject.Drawer d = new SavedProject.Drawer(l);
                        l.addMouseListener(d);
                        l.addMouseMotionListener(d);
                        y = l;
                    }
                    if(type.startsWith("textarea"))
                    {
                        elementName = "textarea"+eid+"##"+caption+"##"+items+"##"+required;
                        JPanel l = ui.getTextAreaPanel();
                        l.setName(elementName);
                        Component[] c = l.getComponents();
                        JMultiLineLabel jl = (JMultiLineLabel)c[1];
                        jl.setText(caption);
                        SavedProject.Drawer d = new SavedProject.Drawer(l);
                        l.addMouseListener(d);
                        l.addMouseMotionListener(d);
                        y = l;
                    }
                    if(type.startsWith("time"))
                    {
                        elementName = "time"+eid+"##"+caption+"##"+items+"##"+required;
                        JPanel l = ui.getTimePanel();
                        l.setName(elementName);
                        Component[] c = l.getComponents();
                        JMultiLineLabel jl = (JMultiLineLabel)c[1];
                        jl.setText(caption);
                        SavedProject.Drawer d = new SavedProject.Drawer(l);
                        l.addMouseListener(d);
                        l.addMouseMotionListener(d);
                        y = l;
                    }
                    if(type.startsWith("date"))
                    {
                        elementName = "date"+eid+"##"+caption+"##"+items+"##"+required;
                        JPanel l = ui.getDatePanel();
                        l.setName(elementName);
                        Component[] c = l.getComponents();
                        JMultiLineLabel jl = (JMultiLineLabel)c[1];
                        jl.setText(caption);
                        SavedProject.Drawer d = new SavedProject.Drawer(l);
                        l.addMouseListener(d);
                        l.addMouseMotionListener(d);
                        y = l;
                    }
                     if(type.startsWith("image"))
                    {
                        elementName = "image"+eid+"##"+caption+"##"+items+"##"+required;
                        JPanel l = ui.getImagePanel();
                        l.setName(elementName);
                        Component[] c = l.getComponents();
                        JMultiLineLabel jl = (JMultiLineLabel)c[1];
                        jl.setText(caption);
                        SavedProject.Drawer d = new SavedProject.Drawer(l);
                        l.addMouseListener(d);
                        l.addMouseMotionListener(d);
                        y = l;
                    }
                     if(type.startsWith("location"))
                    {
                        elementName = "location"+eid+"##"+caption+"##"+items+"##"+required;
                        JPanel l = ui.getLocationPanel();
                        l.setName(elementName);
                        Component[] c = l.getComponents();
                        JLabel jl = (JLabel)c[1];
                        jl.setText(caption);
                        SavedProject.Drawer d = new SavedProject.Drawer(l);
                        l.addMouseListener(d);
                        l.addMouseMotionListener(d);
                        y = l;
                    }
                      if(type.startsWith("spinner"))
                    {
                        elementName = "spinner"+eid+"##"+caption+"##"+items+"##"+required;
                        JPanel l = ui.getSpinnerPanel();
                        l.setName(elementName);
                        Component[] c = l.getComponents();
                        JMultiLineLabel jl = (JMultiLineLabel)c[1];
                        jl.setText(caption);
                        SavedProject.Drawer d = new SavedProject.Drawer(l);
                        l.addMouseListener(d);
                        l.addMouseMotionListener(d);
                        y = l;
                    }
                      
                      if(type.startsWith("onoff"))
                      {
                          elementName = "onoff"+eid+"##"+caption+"##"+items+"##"+required;
                        JPanel l = ui.getONPanel();
                        l.setName(elementName);
                        Component[] c = l.getComponents();
                        JMultiLineLabel jl = (JMultiLineLabel)c[1];
                        jl.setText(caption);
                        SavedProject.Drawer d = new SavedProject.Drawer(l);
                        l.addMouseListener(d);
                        l.addMouseMotionListener(d);
                        y = l;
                    }
                    
                    if(type.startsWith("drop"))
                    {
                        elementName = "drop"+eid+"##"+caption+"##"+items+"##"+required;
                        JPanel l = ui.getDropDownPanel();
                        l.setName(elementName);
                        Component[] c = l.getComponents();
                        JMultiLineLabel jl = (JMultiLineLabel)c[1];
                        jl.setText(caption);
                        String it[] = items.split("@@");
                        JComboBox b = (JComboBox)c[2];
                                    b.removeAllItems();
            for (String it1 : it) {
                b.addItem(it1);
            }
                        SavedProject.Drawer d = new SavedProject.Drawer(l);
                        l.addMouseListener(d);
                        l.addMouseMotionListener(d);
                        y = l;
                    }
                    if(type.startsWith("checkbox"))
                    {
                        elementName = "checkbox"+eid+"##"+caption+"##"+items+"##"+required;
                        JPanel l = ui.getCheckBoxPanel();
                        l.setName(elementName);
                        Component[] c = l.getComponents();
                        JMultiLineLabel jl = (JMultiLineLabel)c[1];
                        jl.setText(caption);
                        String it[] = items.split("@@");
                        JPanel b = (JPanel)c[2];
                        Component t[] = b.getComponents();
                        JPanel g = (JPanel) t[0];
                                    g.removeAll();
                                    g.repaint();
                                    g.revalidate();
                        for (String it1 : it) {
                            g.add(new JCheckBox(it1));
                        }
                        
                        SavedProject.Drawer d = new SavedProject.Drawer(l);
                        l.addMouseListener(d);
                        l.addMouseMotionListener(d);
                        y = l;
                    }
                    if(type.startsWith("barcode"))
                    {
                        elementName = "barcode"+eid+"##"+caption+"##"+items+"##"+required;
                        JPanel l = ui.getBarCodePanel();
                        l.setName(elementName);
                        Component[] c = l.getComponents();
                        JLabel jl = (JLabel)c[0];
                        jl.setText(caption);
                        SavedProject.Drawer d = new SavedProject.Drawer(l);
                        l.addMouseListener(d);
                        l.addMouseMotionListener(d);
                        y = l;
                    }
                    if(type.startsWith("audio"))
                    {
                        elementName = "audio"+eid+"##"+caption+"##"+items+"##"+required;
                        JPanel l = ui.getBarCodePanel();
                        l.setName(elementName);
                        Component[] c = l.getComponents();
                        JLabel jl = (JLabel)c[0];
                        jl.setText(caption);
                        SavedProject.Drawer d = new SavedProject.Drawer(l);
                        l.addMouseListener(d);
                        l.addMouseMotionListener(d);
                        y = l;
                    }
                    if(type.startsWith("video"))
                    {
                        elementName = "video"+eid+"##"+caption+"##"+items+"##"+required;
                        JPanel l = ui.getBarCodePanel();
                        l.setName(elementName);
                        Component[] c = l.getComponents();
                        JLabel jl = (JLabel)c[0];
                        jl.setText(caption);
                        SavedProject.Drawer d = new SavedProject.Drawer(l);
                        l.addMouseListener(d);
                        l.addMouseMotionListener(d);
                        y = l;
                    }
                    
                    
                    if(type.startsWith("matrix"))
                    {
                        JPanel l = ui.getMatrixPanel();
                        l.setName(type+"##"+caption+"##"+items+"##"+required);
                        Component[] c = l.getComponents();
                        JMultiLineLabel jl = (JMultiLineLabel)c[1];
                        jl.setText(caption);
                        JScrollPane f =  (JScrollPane) c[2];
                        JViewport fv = f.getViewport();
                        JTable table = (JTable) fv.getView();
                        boolean isRadio = false;
                        if(required.contains("Radio"))
                        {
                            isRadio = true;
                        }
                        ui.populateMatrix(table, items, isRadio);
                        table.setPreferredScrollableViewportSize(table.getPreferredSize());
                        SavedProject.Drawer d = new SavedProject.Drawer(l);
                        l.addMouseListener(d);
                        l.addMouseMotionListener(d);
                        y = l;
                    }
                    
                    
                    
                    
                    if(type.startsWith("radio"))
                    {
                        elementName = "radio"+eid+"##"+caption+"##"+items+"##"+required;
                        JPanel l = ui.getRadioPanel();
                        l.setName(elementName);
                        Component[] c = l.getComponents();
                        JMultiLineLabel jl = (JMultiLineLabel)c[1];
                        jl.setText(caption);
                        String it[] = items.split("@@");
                        JPanel b = (JPanel)c[2];
                        Component t[] = b.getComponents();
                        JPanel g = (JPanel) t[0];
                                    g.removeAll();
                                    g.repaint();
                                    g.revalidate();
                                    ButtonGroup bg = new ButtonGroup();
                        for (String it1 : it) {
                            JRadioButton bb = new javax.swing.JRadioButton(it1);
                                        g.add(bb);
                                        bg.add(bb);
                        }
                       
                        SavedProject.Drawer d = new SavedProject.Drawer(l);
                        l.addMouseListener(d);
                        l.addMouseMotionListener(d);
                        y = l;
                    }
                    
                    
                    return y;
    }
    
    public void flushTemp()
    {
        Vector v = new Vector();
        try
        {
            File file = new File("temp/");
            String[] directories = file.list((File current, String name) -> new File(current, name).isDirectory());
          String d[] = Arrays.toString(directories).replace("[", "").replace("]", "").split(", ");
          v.addAll(asList(d));
        }
        catch(Exception er)
        {
            er.printStackTrace();
        }
        for(int x=0; x<v.size();x++)
        {
            try
            {
                File f = new File("temp/"+v.elementAt(x));
                deleteDirectory(f);
            }
            catch(Exception er){}
        }
    }
    
    
    
    
    public Vector getSavedProjects()
    {
        Vector v = new Vector();
        try
        {
            File file = new File("projects/");
            String[] directories = file.list((File current, String name) -> new File(current, name).isDirectory());
          String d[] = Arrays.toString(directories).replace("[", "").replace("]", "").split(", ");
          v.addAll(asList(d));
          v.remove("temp");
        }
        catch(Exception er)
        {
            er.printStackTrace();
        }
        /*
        Vector v2 = new Vector();
         Transaction transaction = null;
        boolean done = false;
        String res = null;
        java.sql.Date dt = null;
        try
        {
            Session session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            String queryString = "select name,id from Project";
            Query query = session.createQuery(queryString);
            List<Object[]> r= (List<Object[]>)query.list();
                for(Object[] m: r){
                    String name = m[0].toString();
                    String id = m[1].toString();
                    v2.add(id);
                    v.add(name);
                }
                        session.close();
                }
        catch (HibernateException e) {
            
            done = false;
            //transaction .rollback();
            e.printStackTrace();
        } finally {
            
        }
        list.setListData(v2);
        */
        return v;
    }
    
    /*
    public String getProjectInfo(String columnName, String column2, String testValue)
    {
        Transaction transaction = null;
        boolean done = false;
        String res = null;
        java.sql.Date dt = null;
        try
        {
            Session session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            String queryString = "select "+columnName+" from Project where "+column2+" = "+testValue;
            Query query = session.createQuery(queryString);
            List l = query.list();
            res = l.get(0).toString();
            session.close();
        }
        catch (HibernateException e) {
            
            done = false;
            //transaction .rollback();
            e.printStackTrace();
        } finally {
            
        }
        
        return res;
    }
    */
    
    
    
    public void editSection(String pid, String section, String old)
    {
        
        try
        {
            File f = new File("projects/"+pid+"/sections/"+old);
            File f2 = new File("projects/"+pid+"/sections/"+section);
            f.renameTo(f2);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    
    
    
    
    public void saveSection(String pid, String section)
    {
        try
        {
            File f = new File("projects/"+pid+"/sections/"+section);
            f.mkdir();
            File ff = new File("projects/"+pid+"/sections/"+section+"/sectionData.jaj");
            ff.createNewFile();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
        
        /*
        Transaction transaction = null;
        boolean done = false;
        int qid=0;
        try
        {
            Session session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            Sections s = new Sections();
            s.setPid(pid);
            s.setSection_name(section);
            s.setSection_date(new Date());
            session.save(s);
            transaction.commit();
            session.close();
        }
        catch (HibernateException e) {
            
            done = false;
            //transaction .rollback();
            e.printStackTrace();
            qid = 0;
        } finally {
            
        }
                */
    }
    
    
    public void getSections(String name, JList mlist)
    {
        Vector v = new Vector();
        try
        {
            
            File file = new File("projects/"+name+"/sections/");
            String[] dd = file.list();
            if(dd.length<1)
            {
                mlist.setListData(v);
                return;
            }
            
            
            
            String[] directories = file.list((File current, String name1) -> new File(current, name1).isDirectory());
          String d[] = Arrays.toString(directories).replace("[", "").replace("]", "").split(", ");
          v.addAll(asList(d));
          mlist.setListData(v);
        }
        catch(Exception er)
        {
            er.printStackTrace();
        }
        
        /*
        Transaction transaction = null;
        boolean done = false;
        String res = null;
        java.sql.Date dt = null;
        List l = null;
        Vector v = new Vector();
        Vector v2 = new Vector();
        try
        {
            Session session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            String queryString = "select id, section_name from Sections where pid="+pid;
            Query query = session.createQuery(queryString);
            List<Object[]> r= (List<Object[]>)query.list();
                for(Object[] m: r){
                    String id = m[0].toString();
                    String name = m[1].toString();
                    v2.add(id);
                    v.add(name);
                }
        }
        catch (HibernateException e) {
            
            done = false;
            //transaction .rollback();
            e.printStackTrace();
        } finally {
            
        }
        mlist.setListData(v);
        virtualList.setListData(v2);
                */
    }
    
    
}
