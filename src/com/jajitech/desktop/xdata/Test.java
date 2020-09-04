/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jajitech.desktop.xdata;

import java.awt.FlowLayout;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Vector;
import javax.swing.JComboBox;
import org.jdesktop.swingx.JXButton;
import org.jdesktop.swingx.JXFrame;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import org.xeustechnologies.jtar.TarEntry;
import org.xeustechnologies.jtar.TarOutputStream;

/**
 *
 * @author Jaji
 */
public class Test {
    
    public static void main(String args[])
    {
       /* try
        {
            FileOutputStream dest = new FileOutputStream( "c:\\Jajiteh\\project.jajx" );
            TarOutputStream out = new TarOutputStream( new BufferedOutputStream( dest ) );
            tarFolder( null, "projects/Gender/", out );
        }
        catch(Exception e)
        {
            
        } */
        
        JXFrame fj = new JXFrame("Test");
        JXPanel jp = new JXPanel(new FlowLayout(FlowLayout.LEFT));
        Vector t = new Vector();
        t.add("Lukman");
        t.add("Yasmine");
        t.add("Tesneem");
        t.add("Jamal");
        JComboBox cb = new JComboBox(t);
        AutoCompleteDecorator.decorate(cb);
        jp.add(cb);
        JXButton b = new JXButton("Test");
        jp.add(b);
        fj.getContentPane().add("Center", jp);
        fj.setSize(250,200);
        fj.show();
    }
    
    
    static final int BUFFER = 2048;
    private static void tarFolder(String parent, String path, TarOutputStream out) throws IOException {
        System.out.print("there");
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
            System.out.print("there.its directory");
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
    
}
