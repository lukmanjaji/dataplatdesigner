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
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JToolBar;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;

public class DropDownButton
  extends JButton
  implements ActionListener
{
  private JPopupMenu popup = new JPopupMenu();
  private JToolBar tb = new DropDownButton.ToolBar();
  private JButton mainButton;
  private JButton arrowButton;
  private ActionListener mainButtonListener = new ActionListener()
  {
    public void actionPerformed(ActionEvent paramAnonymousActionEvent)
    {
      Component localComponent = DropDownButton.this.popup.getComponent(0);
      if ((localComponent instanceof JMenuItem))
      {
        JMenuItem localJMenuItem = (JMenuItem)localComponent;
        localJMenuItem.doClick(0);
      }
    }
  };
  
  public DropDownButton(Icon paramIcon)
  {
    this();
    this.mainButton = new RolloverButton(paramIcon, 25, false);
    this.arrowButton = new RolloverButton(new DropDownButton.DownArrow(), 11, false);
    init();
  }
  
  public DropDownButton(String paramString, Icon paramIcon)
  {
    this();
    this.mainButton = new RolloverButton(paramIcon, 25, false);
    this.arrowButton = new RolloverButton(new DropDownButton.DownArrow(), 11, false);
    init();
  }
  
  public DropDownButton(Icon paramIcon, int paramInt)
  {
    this();
    this.mainButton = new RolloverButton(paramIcon, paramInt, false);
    this.arrowButton = new RolloverButton(new DropDownButton.DownArrow(), 11, false);
    init();
  }
  
  public DropDownButton(RolloverButton paramRolloverButton1, RolloverButton paramRolloverButton2)
  {
    this();
    this.mainButton = paramRolloverButton1;
    this.arrowButton = paramRolloverButton2;
    init();
  }
  
  private DropDownButton()
  {
    setBorder(null);
  }
  
  public void updateUI()
  {
    super.updateUI();
    setBorder(null);
  }
  
  protected Border getRolloverBorder()
  {
    return BorderFactory.createRaisedBevelBorder();
  }
  
  private void initRolloverListener()
  {
    MouseAdapter local2 = new MouseAdapter()
    {
      Border mainBorder = null;
      Border arrowBorder = null;
      
      public void mouseEntered(MouseEvent paramAnonymousMouseEvent)
      {
        this.mainBorder = DropDownButton.this.mainButton.getBorder();
        this.arrowBorder = DropDownButton.this.mainButton.getBorder();
        DropDownButton.this.mainButton.setBorder(new CompoundBorder(DropDownButton.this.getRolloverBorder(), this.mainBorder));
        DropDownButton.this.arrowButton.setBorder(new CompoundBorder(DropDownButton.this.getRolloverBorder(), this.arrowBorder));
        DropDownButton.this.mainButton.getModel().setRollover(true);
        DropDownButton.this.arrowButton.getModel().setRollover(true);
      }
      
      public void mouseExited(MouseEvent paramAnonymousMouseEvent)
      {
        DropDownButton.this.mainButton.setBorder(this.mainBorder);
        DropDownButton.this.arrowButton.setBorder(this.arrowBorder);
        DropDownButton.this.mainButton.getModel().setRollover(false);
        DropDownButton.this.arrowButton.getModel().setRollover(false);
      }
    };
    this.mainButton.addMouseListener(local2);
    this.arrowButton.addMouseListener(local2);
  }
  
  private void init()
  {
    initRolloverListener();
    
    DropDownButton.DisabledDownArrow localDisabledDownArrow = new DropDownButton.DisabledDownArrow();
    this.arrowButton.setDisabledIcon(localDisabledDownArrow);
    this.arrowButton.setMaximumSize(new Dimension(11, 100));
    this.mainButton.addActionListener(this);
    this.arrowButton.addActionListener(this);
    
    setMargin(new Insets(0, 0, 0, 0));
    
    this.tb.setBorder(null);
    this.tb.setMargin(new Insets(0, 0, 0, 0));
    this.tb.setFloatable(false);
    this.tb.add(this.mainButton);
    this.tb.add(this.arrowButton);
    add(this.tb);
    
    setFixedSize(this.mainButton, this.arrowButton);
  }
  
  private void setFixedSize(JButton paramJButton1, JButton paramJButton2)
  {
    int i = (int)(paramJButton1.getPreferredSize().getWidth() + paramJButton2.getPreferredSize().getWidth());
    
    int j = (int)Math.max(paramJButton1.getPreferredSize().getHeight(), paramJButton2.getPreferredSize().getHeight());
    
    setMaximumSize(new Dimension(i, j));
    setMinimumSize(new Dimension(i, j));
    setPreferredSize(new Dimension(i, j));
  }
  
  public void removeComponent(Component paramComponent)
  {
    this.popup.remove(paramComponent);
  }
  
  public Component addComponent(Component paramComponent)
  {
    return this.popup.add(paramComponent);
  }
  
  public void setVisible(boolean paramBoolean)
  {
    this.popup.setVisible(paramBoolean);
  }
  
  public void setRunFirstItem(boolean paramBoolean)
  {
    this.mainButton.removeActionListener(this);
    if (!paramBoolean) {
      this.mainButton.addActionListener(this);
    } else {
      this.mainButton.addActionListener(this.mainButtonListener);
    }
  }
  
  public void actionPerformed(ActionEvent paramActionEvent)
  {
    JPopupMenu localJPopupMenu = getPopupMenu();
    localJPopupMenu.show(this, 0, getHeight());
  }
  
  protected JPopupMenu getPopupMenu()
  {
    return this.popup;
  }
  
  private static class DownArrow implements Icon
  {
    Color arrowColor = Color.black;
    
    public void paintIcon(Component paramComponent, Graphics paramGraphics, int paramInt1, int paramInt2)
    {
      paramGraphics.setColor(this.arrowColor);
      paramGraphics.drawLine(paramInt1, paramInt2, paramInt1 + 4, paramInt2);
      paramGraphics.drawLine(paramInt1 + 1, paramInt2 + 1, paramInt1 + 3, paramInt2 + 1);
      paramGraphics.drawLine(paramInt1 + 2, paramInt2 + 2, paramInt1 + 2, paramInt2 + 2);
    }
    
    public int getIconWidth()
    {
      return 6;
    }
    
    public int getIconHeight()
    {
      return 4;
    }
  }
  
  private static class DisabledDownArrow
    extends DropDownButton.DownArrow
  {
    public DisabledDownArrow()
    {
      super();
      this.arrowColor = new Color(140, 140, 140);
    }
    
    public void paintIcon(Component paramComponent, Graphics paramGraphics, int paramInt1, int paramInt2)
    {
      super.paintIcon(paramComponent, paramGraphics, paramInt1, paramInt2);
      paramGraphics.setColor(Color.white);
      paramGraphics.drawLine(paramInt1 + 3, paramInt2 + 2, paramInt1 + 4, paramInt2 + 1);
      paramGraphics.drawLine(paramInt1 + 3, paramInt2 + 3, paramInt1 + 5, paramInt2 + 1);
    }
  }
  
  public static class ToolBar extends JToolBar
  {
    public void updateUI()
    {
      super.updateUI();
      setBorder(null);
    }
  }
  
  
  public class RolloverButton
  extends JButton
{
  public RolloverButton()
  {
    init();
    initRolloverListener();
  }
  
  public RolloverButton(Icon paramIcon, int paramInt)
  {
    super(paramIcon);
    init();
    initRolloverListener();
    
    setFixedSize(paramInt);
  }
  
  public RolloverButton(Icon paramIcon, int paramInt, boolean paramBoolean)
  {
    super(paramIcon);
    init();
    if (paramBoolean) {
      initRolloverListener();
    }
    setFixedSize(paramInt);
  }
  
  public RolloverButton(int paramInt, boolean paramBoolean)
  {
    init();
    if (paramBoolean) {
      initRolloverListener();
    }
    setFixedSize(paramInt);
  }
  
  public RolloverButton(Action paramAction, int paramInt)
  {
    super((Icon)paramAction.getValue("SmallIcon"));
    init();
    initRolloverListener();
    addActionListener(paramAction);
    setFixedSize(paramInt);
  }
  
  private void init()
  {
    setRequestFocusEnabled(false);
    setRolloverEnabled(true);
  }
  
  protected void setFixedSize(int paramInt)
  {
    setPreferredSize(new Dimension(paramInt, paramInt));
    setMaximumSize(new Dimension(paramInt, paramInt));
    setMinimumSize(new Dimension(paramInt, paramInt));
  }
  
  protected void initRolloverListener()
  {
    MouseAdapter local1 = new MouseAdapter()
    {
      Border curBorder = null;
      
      public void mouseEntered(MouseEvent paramAnonymousMouseEvent)
      {
        this.curBorder = RolloverButton.this.getBorder();
        
        RolloverButton.this.setBorder(new CompoundBorder(RolloverButton.this.getRolloverBorder(), this.curBorder));
        RolloverButton.this.getModel().setRollover(true);
      }
      
      public void mouseExited(MouseEvent paramAnonymousMouseEvent)
      {
        RolloverButton.this.setBorder(this.curBorder);
        RolloverButton.this.getModel().setRollover(false);
      }
    };
    addMouseListener(local1);
  }
  
  protected Border getRolloverBorder()
  {
    Border localBorder = BorderFactory.createRaisedBevelBorder();
    
    return localBorder;
  }
}
}
