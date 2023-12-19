/* Copyright (c) Colorado School of Mines, 2013.*/
/* All rights reserved.                       */


package cseis.test;

import cseis.graph.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TestGraph2D extends JFrame implements MouseListener, MouseMotionListener {
  private csGraph2D myGraph;
  private csGraph2D myGraphVert;
  private csGraph2D myGraphHorz;
  private JScrollPane myScrollPaneVert;
  private JScrollPane myScrollPaneHorz;
  private static final int FLAG_NEW = 1;
  private static final int FLAG_VERT = 2;
  private static final int FLAG_HORZ = 3;
  private static final int FLAG_SEA  = 4;
  private int myMousePressedX;
  private int myMousePressedY;
  private int myMouseDraggedX;
  private int myMouseDraggedY;
  private boolean myIsMouseDragged;
  csGraphPane myPaneVert;
  csGraphPane myPaneHorz;
  csGraphPane myPaneSea;
  csGraph2D myGraphSea;
  csGraphPane myGraphPane;

  TestGraph2D() {
    super("Test graph");

    myScrollPaneVert = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    myScrollPaneHorz = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

    myGraph = new csGraph2D();
    myGraphVert = new csGraph2D();
    myGraphHorz = new csGraph2D();
    myPaneVert = new csGraphPane(myGraphVert);
    myPaneHorz = new csGraphPane(myGraphHorz);
    myGraph.addMouseListener(this);
    myGraph.addMouseMotionListener(this);
//    csGraphAttributes graphAttr = myGraph.getGraphAttributes();
//    graphAttr.isGraphViewPort = false;
//    myGraph.setGraphAttributes(graphAttr);
//    myGraph.setFixedDim(false, false, 100, 100);
    myIsMouseDragged = false;
    JButton bAdd = new JButton("+");
    JButton bAddVert = new JButton("|||");
    JButton bAddHorz = new JButton("---");
    JButton bAddSea = new JButton("sss");
    JButton bFixX = new JButton("x");
    JButton bFixY = new JButton("y");
    JButton bFixXY = new JButton("both");
    JButton bFixNONE = new JButton("none");

    myGraphSea = new csGraph2D();
    myPaneSea = new csGraphPane(myGraphSea);
    setGraphSea();

    bFixX.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        myGraph.setFixedDim(true,false,1000,0);
        myGraphVert.setFixedDim(true,false,1000,0);
        myGraphHorz.setFixedDim(true,false,1000,0);
        myPaneVert.setNewSize();
        myPaneHorz.setNewSize();
        myGraphSea.setFixedDim(true,false,2000,0);
        myPaneSea.setNewSize();
      }
    });
    bFixY.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        myGraph.setFixedDim(false,true,0,1000);
        myGraphVert.setFixedDim(false,true,0,1000);
        myGraphHorz.setFixedDim(false,true,0,1000);
        myPaneVert.setNewSize();
        myPaneHorz.setNewSize();
        myGraphSea.setFixedDim(true,false,0,2000);
        myPaneSea.setNewSize();
      }
    });
    bFixXY.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        myGraph.setFixedDim(true,true,1000,1000);
        myGraphVert.setFixedDim(true,true,1000,1000);
        myGraphHorz.setFixedDim(true,true,1000,1000);
        myPaneVert.setNewSize();
        myPaneHorz.setNewSize();
        myGraphSea.setFixedDim(true,true,2000,2000);
        myPaneSea.setNewSize();
      }
    });
    bFixNONE.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        myGraph.setFixedDim(false,false,0,0);
        myGraphVert.setFixedDim(false,false,0,0);
        myGraphHorz.setFixedDim(false,false,0,0);
        myPaneVert.setNewSize();
        myPaneHorz.setNewSize();
        myGraphSea.setFixedDim(false,false,0,0);
        myPaneSea.setNewSize();
      }
    });
    bAdd.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        addCurve(FLAG_NEW);
      }
    });
    bAddVert.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        addCurve(FLAG_VERT);
      }
    });
    bAddHorz.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        addCurve(FLAG_HORZ);
      }
    });
    bAddSea.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        addCurve(FLAG_SEA);
      }
    });
    JToolBar toolBar = new JToolBar();
    toolBar.add(bAdd);
    toolBar.add(bAddVert);
    toolBar.add(bAddHorz);
    toolBar.add(bAddSea);
    toolBar.add(bFixX);
    toolBar.add(bFixY);
    toolBar.add(bFixXY);
    toolBar.add(bFixNONE);

//    myGraph.setFixedDim( false,false,200,200 );
//    myGraphVert.setFixedDim( false,false,200,200 );
//    myGraphHorz.setFixedDim( false,false,200,200 );
//    myScrollPaneVert.setViewportView(myGraphVert);
//    myScrollPaneHorz.setViewportView(myGraphHorz);


//    getContentPane().add( toolBar, BorderLayout.NORTH );
//    getContentPane().add( myScrollPaneVert, BorderLayout.EAST );
//   getContentPane().add( myScrollPaneHorz, BorderLayout.SOUTH );
//    getContentPane().add( myPaneVert, BorderLayout.EAST );
//    getContentPane().add( myPaneHorz, BorderLayout.SOUTH );

    myGraphPane = new csGraphPane(myGraph);
    getContentPane().add( myGraphPane, BorderLayout.CENTER );
//    getContentPane().add( myPaneSea, BorderLayout.WEST );
//    getContentPane().add( myPaneSea, BorderLayout.SOUTH );

//    pack();
    setSize(600,600);
    super.addWindowListener(new WindowAdapter() {
      public void windowClosing( WindowEvent e ) {
        System.exit(0);
      }
    });
    addComponentListener( new ComponentAdapter() {
      public void componentResized(ComponentEvent e ) {
        System.out.println("Test graph: Resized to " + getSize());
//        myGraph.setNewVariableDim(getSize().width,getSize().height);
      }
    });
    addCurve( FLAG_NEW );
  }
  public void addCurve( int flag ) {
    float[] valuesX = {-2,1,2,3,4,5,6,7,8,9,10};
    float[] valuesY = {-2,3,5,1,9,7,4,5,7,4,3};
    for( int i = 0; i < valuesX.length; i++ ) {
      valuesX[i] *= 0.02;
      valuesY[i] *= 50;
    }
    try {
      if( flag == FLAG_NEW ) {
//        myGraph.getGraphAttributes().graphPadding = 0;
        myGraph.addCurve(flag,valuesX, valuesY, null);
      }
      else if(flag == FLAG_VERT) {
        myGraphVert.addCurve(flag,valuesX, valuesY, null);
      }
      else if(flag == FLAG_HORZ) {
        myGraphHorz.addCurve(flag,valuesX, valuesY, null);
      }
      else if(flag == FLAG_SEA) {
        myGraphSea.addCurve(flag,valuesX, valuesY, null);
      }
    }
    catch( Exception e ) {
      e.printStackTrace();
    }
  }
  public static void main( String[] args ) {
    TestGraph2D t = new TestGraph2D();
    t.setVisible(true);
  }
  private void reset() {
    System.out.println("RESET ");
    myGraph.repaint();
  }
  private void zoom() {
    System.out.println("ZOOM ");
//    myGraph.setNewVariableDim(WIDTH, WIDTH)
//    myGraph.reset(
//            Math.min(myMousePressedX,myMouseDraggedX),
//            Math.min(myMousePressedY,myMouseDraggedY),
//            Math.max(myMousePressedX,myMouseDraggedX),
//            Math.max(myMousePressedY,myMouseDraggedY));
  }
  @Override
  public void mouseClicked(MouseEvent e) {
    reset();
  }

  @Override
  public void mousePressed(MouseEvent e) {
    myMousePressedX = e.getX();
    myMousePressedY = e.getY();
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    if( myIsMouseDragged ) zoom();
    myIsMouseDragged = false;
  }

  @Override
  public void mouseEntered(MouseEvent e) {
  }

  @Override
  public void mouseExited(MouseEvent e) {
  }

  @Override
  public void mouseDragged(MouseEvent e) {
    myIsMouseDragged = true;
    myMouseDraggedX = e.getX();
    myMouseDraggedY = e.getY();
  }

  @Override
  public void mouseMoved(MouseEvent e) {
  }
  private void setGraphSea() {
    csGraphAttributes attr = myGraphSea.getGraphAttributes();
    attr.isGraphViewPort = true;
    attr.insetBottom = 0;
    attr.insetRight  = 0;
    attr.insetTop    = 0;
    attr.insetLeft   = 50;
    attr.borderPadding = 0;
    attr.showBorder = false;
    attr.showAxisXAnnotation = false;
    attr.showAxisYAnnotation = true;
    attr.showZeroAxis = false;
    attr.title = "";
    attr.xLabel = "";
    attr.showInnerBorder = false;
    attr.graphPadding = 0;
  }
}


