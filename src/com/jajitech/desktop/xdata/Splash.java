/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jajitech.desktop.xdata;

/**
 *
 * @author Javalove
 */
import de.javasoft.plaf.synthetica.SyntheticaLookAndFeel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class Splash extends JWindow {

	private Dimension d = Toolkit.getDefaultToolkit().getScreenSize();

	public Splash () {

		JLabel lbImage    = new JLabel (new ImageIcon (getClass().getResource("images/splash.png")));
		Color cl = new Color (0, 0, 0);
		lbImage.setBorder (new LineBorder (cl, 1));

		getContentPane().add (lbImage, BorderLayout.CENTER);
		pack();

		setSize (getSize().width, getSize().height);
		setLocation (d.width / 2 - getWidth() / 2, d.height / 2 - getHeight() / 2);

		setVisible(true);

		for (int i = 0; i <= 1000; i++) { }

		toFront();
		
                Thread tt = new Thread(new Runnable()
                {
                    public void run()
                    {
                        setVisible (true);
                        try{Thread.sleep(4000);}catch(Exception er){}
                        dispose();
                        Main m = new Main();
                    }
                });
                tt.start();
               
                
		

	}

	 public static void main(String[] args)
    {
        
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
       
            if (output.equals("nimbus"))
            {
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
            }
            if ( output.equals("system"))
            {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            }
            if ( output.equals("classic"))
            {
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
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
       SwingUtilities.invokeLater(new Runnable() {
           public void run()
           {
               
       Splash main = new Splash();
           }});
       //main.setUndecorated(false);
    }

}

