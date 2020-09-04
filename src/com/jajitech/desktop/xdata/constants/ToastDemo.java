/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jajitech.desktop.xdata.constants;

/**
 *
 * @author Jaji
 */
import java.awt.Color;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;
 
public class ToastDemo extends JPanel implements ActionListener {
 
        private static final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    private final Date now = new Date();
    private final Timer timer = new Timer(1000, this);
    private final JLabel text = new JLabel();

    public ToastDemo() {
        super(true);
        timer.start();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        now.setTime(System.currentTimeMillis());
        text.setText(String.format("<html><body><font size='50'>%s</font></body></html>",sdf.format(now)));
    }

    public static void main(String[] args) {

        JFrame f = new JFrame();
        //setTranslucency( f );
        f.setUndecorated( true );
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setBackground(new Color(0f, 0f, 0f, 1f / 3f));
        f.add(new ToastDemo());
        f.pack();
        f.setVisible(true);
    }
    // taken from: http://java.sun.com/developer/technicalArticles/GUI/translucent_shaped_windows/
    private static void setTranslucency( Window window){
        try {
               Class<?> awtUtilitiesClass = Class.forName("com.sun.awt.AWTUtilities");
               Method mSetWindowOpacity = awtUtilitiesClass.getMethod("setWindowOpacity", Window.class, float.class);
               mSetWindowOpacity.invoke(null, window, Float.valueOf(0.75f));
            } catch (NoSuchMethodException ex) {
               ex.printStackTrace();
            } catch (SecurityException ex) {
               ex.printStackTrace();
            } catch (ClassNotFoundException ex) {
               ex.printStackTrace();
            } catch (IllegalAccessException ex) {
               ex.printStackTrace();
            } catch (IllegalArgumentException ex) {
               ex.printStackTrace();
            } catch (InvocationTargetException ex) {
               ex.printStackTrace();
            }
    }
}