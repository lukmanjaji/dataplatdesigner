/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jajitech.desktop.xdata.waiter;

/**
 *
 * @author Jaji
 */
/**
 * Java Toaster is a java utility class for your swing applications
 * that show an animate box coming from the bottom of your screen
 * with a notification message and/or an associated image 
 * (like msn online/offline notifications).
 * 
 * Toaster panel in windows system follow the taskbar; So if
 * the taskbar is into the bottom the panel coming from the bottom
 * and if the taskbar is on the top then the panel coming from the top.
 * 
 * This is a simple example of utilization:
 * 
 * import com.nitido.utils.toaster.*; 
 * import javax.swing.*; 
 * 
 * public class ToasterTest 
 * { 
 *
 *  public static void main(String[] args) 
 *  { 
 *   // Initialize toaster manager... 
 *   Toaster toasterManager = new Toaster(); 
 *
 *   // Show a simple toaster 
 *   toasterManager.showToaster( new ImageIcon( "mylogo.gif" ), "A simple toaster with an image" ); 
 *  } 
 * } 
 */

public class Toaster {
    /*
    private int toasterWidth = 300;
    private int toasterHeight = 50;
    private int step = 20;
    private int stepTime = 20;
    private int displayTime = 3000;
    private int currentNumberOfToaster = 0;
    private int maxToaster = 0;
    private int maxToasterInSceen;
    private Font font = new Font("Arial", 1, 12);
    private Color borderColor = new Color(245, 153, 15);
    private Color toasterColor;
    private Color messageColor;
    int margin;
    boolean useAlwaysOnTop = true;
    private static final long serialVersionUID = 1L;

    public Toaster() {
        this.toasterColor = Color.RED;
        this.messageColor = Color.YELLOW;
        this.useAlwaysOnTop = true;

        try {
            JWindow.class.getMethod("setAlwaysOnTop", new Class[]{Boolean.class});
        } catch (Exception var2) {
            this.useAlwaysOnTop = false;
        }

    }

    public void showToaster(Icon var1, String var2) {
        SingleToaster var3 = new SingleToaster(this);
        if(var1 != null) {
            SingleToaster.access$800(var3).setIcon(var1);
        }

        SingleToaster.access$900(var3).setText(var2);
        var3.animate();
    }

    public void showToaster(String var1) {
        this.showToaster((Icon)null, var1);
    }

    public Font getToasterMessageFont() {
        return this.font;
    }

    public void setToasterMessageFont(Font var1) {
        this.font = var1;
    }

    public Color getBorderColor() {
        return this.borderColor;
    }

    public void setBorderColor(Color var1) {
        this.borderColor = var1;
    }

    public int getDisplayTime() {
        return this.displayTime;
    }

    public void setDisplayTime(int var1) {
        this.displayTime = var1;
    }

    public int getMargin() {
        return this.margin;
    }

    public void setMargin(int var1) {
        this.margin = var1;
    }

    public Color getMessageColor() {
        return this.messageColor;
    }

    public void setMessageColor(Color var1) {
        this.messageColor = var1;
    }

    public int getStep() {
        return this.step;
    }

    public void setStep(int var1) {
        this.step = var1;
    }

    public int getStepTime() {
        return this.stepTime;
    }

    public void setStepTime(int var1) {
        this.stepTime = var1;
    }

    public Color getToasterColor() {
        return this.toasterColor;
    }

    public void setToasterColor(Color var1) {
        this.toasterColor = var1;
    }

    public int getToasterHeight() {
        return this.toasterHeight;
    }

    public void setToasterHeight(int var1) {
        this.toasterHeight = var1;
    }

    public int getToasterWidth() {
        return this.toasterWidth;
    }

    public void setToasterWidth(int var1) {
        this.toasterWidth = var1;
    }
    */
}
