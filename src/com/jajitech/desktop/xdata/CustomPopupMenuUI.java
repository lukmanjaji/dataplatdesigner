/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jajitech.desktop.xdata;

/**
 *
 * @author Jaji
 */
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.Popup;
import javax.swing.border.AbstractBorder;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicPopupMenuUI;

public class CustomPopupMenuUI
  extends BasicPopupMenuUI
{
  public static ComponentUI createUI(JComponent paramJComponent)
  {
    return new CustomPopupMenuUI();
  }
  
  public Popup getPopup(JPopupMenu paramJPopupMenu, int paramInt1, int paramInt2)
  {
    Popup localPopup = super.getPopup(paramJPopupMenu, paramInt1, paramInt2);
    JPanel localJPanel = (JPanel)paramJPopupMenu.getParent();
    localJPanel.setBorder(new ShadowBorder(3, 3));
    localJPanel.setOpaque(false);
    return localPopup;
  }
  
  class ShadowBorder extends AbstractBorder {
        int xoff, yoff;
        Insets insets;

        public ShadowBorder(int x, int y) {
                this.xoff = x;
                this.yoff = y;
                insets = new Insets(0, 0, xoff, yoff);
        }

        public Insets getBorderInsets(Component c) {
                return insets;
        }

        public void paintBorder(Component comp, Graphics g, int x, int y, int width, int height) {
                g.setColor(Color.black);
                g.translate(x, y);
                g.fillRect(width - xoff, yoff, xoff, height - yoff);
                g.fillRect(xoff, height - yoff, width - xoff, yoff);
                g.translate(-x, -y);
        }
}

}
