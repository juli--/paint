//package declaration
package view.forms;


/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


//import declarations
import java.awt.Color;
import java.awt.dnd.DropTarget;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;

import model.settings.Constants;
import model.settings.State;
import model.settings.ViewSettings;
import model.util.paint.Utils;
import view.util.VScrollPane;
import view.util.mega.MLabel;
import view.util.mega.MPanel;
import control.ControlPaint;
import view.util.mega.MButton;

/**
 * 
 * @author Julius Huelsmann
 * @version %I%, %U%
 */
@SuppressWarnings("serial") public final class Page extends MPanel {

    
    /*
     * MLabel which is being painted
     */
    
	/**
	 * MLabel on which is painted and MLabel for background (e.g. raster).
	 */
	private PaintLabel jlbl_painting;
	
	/*
	 * Stuff for scrolling the MLabel
	 */

    /**
     * Internal panel which is not added to graphical user interface.
     * The panel serves to be able to scroll and handles the location change 
     * further to the Painting MLabel
     */
    private MPanel jpnl_toMove;
    
	/**
	 * two ScrollPanels for both dimensions.
	 */
	private VScrollPane sp_ub, sp_lr;

	/*
	 * Stuff for selection
	 */
	
    /**
     * MButtons for resizing the selection.
     */
    private MButton[][] jbtn_resize;

    /**
     * Contains the size of a selection while resizing.
     */
    private MLabel jlbl_resizeSelectionSize;

    /**
     * Selection MLabel.
     */
    private MLabel jlbl_selectionBG;

    /**
     * Selection MLabel.
     */
    private MLabel jlbl_selectionPainting;

    /**
     * The border MLabel.
     */
    private MLabel jlbl_border;
	
    /**
     * 
     * MLabel jlbl_background is the background MLabel (which is shown if 
     * image is transparent). 
     * 			It is either white or has got the typical alpha background
     * 			raster.
     */
    private MLabel jlbl_backgroundAlpha;
    
    
    
    /**
     * MLabel which contains the background that the user selects in the view
     * tab.
     * 
     * This background can either consist of raster or lines. It is possible
     * to set page margins that determine at which location of the picture
     * the painting begins and where it ends.
     */
    private MLabel jlbl_backgroundStructure;
    
    /**
     * 
     */
    private New jpnl_new;
    
    /**
     * 
     */
    private QuickAccess quickAccess;
    
	/**
	 * Constructor which initializes the page with use of the ControlPaint.
	 * @param _cv		the ControlPaint. 
	 */
	public Page() {

	}

	
	/**
	 * initializes graphical user interface components
     * and thus creates view. Called directly after creation.
     * @param _controlPaint the basic controller class.
	 */
	public void initialize(final ControlPaint _controlPaint) {

        //alter settings
        super.setOpaque(true);
        super.setBackground(Color.white);
        super.setLayout(null);
        super.setFocusable(false);
        super.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(
                        0, 0, 0, 1, ViewSettings.GENERAL_CLR_BORDER), 

                BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(
                                0, 0, 1, 0, ViewSettings.GENERAL_CLR_BORDER),
                        BorderFactory.createMatteBorder(
                                0, 0, 1, 1, Color.white))));
        super.setVisible(false);
        super.add(PopupChangeSize.getInstance(_controlPaint.getcTabSelection(), this));

        //form for creating new page.
        jpnl_new = new New(_controlPaint.getControlnew());
        super.add(jpnl_new);
        super.add(Console.getInstance());
        
        quickAccess = new QuickAccess(_controlPaint.getControlQuickAccess());
        super.add(quickAccess);

        //initialize JPanel jpnl_toMove
        jpnl_toMove = new MPanel() {
            
            @Override public void setLocation(final int _x, final int _y) {

            	//in here the ScrollPane is changing the location of the 
            	//painting label
                jlbl_painting.setLocation(_x, _y);
            }
        };
        jlbl_painting = new PaintLabel(jpnl_toMove);
        new DropTarget(jlbl_painting, _controlPaint.getcTabPaint());

        //ScrollPanel for up and down
        sp_ub = new VScrollPane(jpnl_toMove, this, true, jlbl_painting);
        sp_ub.setActivityListener(_controlPaint.getUtilityControlScrollPane());
        _controlPaint.getView().add(sp_ub);

        sp_lr = new VScrollPane(jpnl_toMove, this, false, jlbl_painting);
        sp_lr.setActivityListener(_controlPaint.getUtilityControlScrollPane());
        _controlPaint.getView().add(sp_lr);
        
        jlbl_resizeSelectionSize = new MLabel();
        jlbl_resizeSelectionSize.setOpaque(true);
        jlbl_resizeSelectionSize.setFont(ViewSettings.GENERAL_FONT_HEADLINE_2);
        jlbl_resizeSelectionSize.setBackground(Color.white);
        jlbl_resizeSelectionSize.setForeground(Color.black);
        jlbl_resizeSelectionSize.setSize((int) Math.pow(2, 2 + 2 + 2 + 1), 
                (int) Math.pow(2, 2 + 2 + 1));
        jlbl_resizeSelectionSize.setLocation(
                -jlbl_resizeSelectionSize.getWidth(), 
                -jlbl_resizeSelectionSize.getHeight());
        jlbl_resizeSelectionSize.setBorder(BorderFactory.createLineBorder(
                Color.black));
        super.add(jlbl_resizeSelectionSize);
        
        //initialize resize MButton
        jbtn_resize = new MButton[2 + 1][2 + 1];
        for (int x = 0; x < jbtn_resize.length; x++) {
            for (int y = 0; y < jbtn_resize[x].length; y++) {
        
                final int buttonSize = 10;
                
                jbtn_resize[x][y] = new MButton();
                jbtn_resize[x][y].setSize(buttonSize, buttonSize);
                jbtn_resize[x][y].setContentAreaFilled(false);
                jbtn_resize[x][y].setBorder(
                        BorderFactory.createLineBorder(Color.gray));
                jbtn_resize[x][y].setBackground(Color.white);
                jbtn_resize[x][y].setOpaque(true);
                jbtn_resize[x][y].addMouseMotionListener(
                		_controlPaint.getControlPaintSelection());
                jbtn_resize[x][y].addMouseListener(
                		_controlPaint.getControlPaintSelection());
             //   jbtn_resize[x][y].addMouseListener(controlPaint);
                super.add(jbtn_resize[x][y]);
            }
        }
        
        
        //the center button
        jbtn_resize[1][1].setBorder(null);
        jbtn_resize[1][1].setSize((2 + 2 + 2) * (2 + 2 + 1),
                (2 + 2 + 2) * (2 + 2 + 1));
        jbtn_resize[1][1].setIcon(new ImageIcon(Utils.resizeImage(
                jbtn_resize[1][1].getWidth(), jbtn_resize[1][1].getHeight(),
                Constants.VIEW_JBTN_RESIZE_PATH)));
        jbtn_resize[1][1].setOpaque(false);
        
        jlbl_selectionBG = new MLabel();
        jlbl_selectionBG.setOpaque(false);
        jlbl_selectionBG.setFocusable(false);
        super.add(jlbl_selectionBG);
        
        jlbl_selectionPainting = new MLabel();
        jlbl_selectionPainting.setOpaque(false);
        jlbl_selectionPainting.setFocusable(false);
        super.add(jlbl_selectionPainting);

        //Border MLabel added to 
        jlbl_border = new MLabel();
        jlbl_border.setOpaque(true);
        jlbl_border.setBackground(new Color(0, 0, 0, 2 * 2 * 2 * 2 * 2));
        jlbl_border.setBorder(BorderFactory.createLineBorder(Color.black));
        super.add(jlbl_border);

        jlbl_backgroundStructure = new MLabel();
        jlbl_backgroundStructure.setBackground(Color.white);
        jlbl_backgroundStructure.setFocusable(false);
        jlbl_backgroundStructure.setBorder(null);
        jlbl_backgroundStructure.setOpaque(false);
        super.add(jlbl_backgroundStructure); 

        //MLabel for the painting and the raster
        jlbl_painting.setFocusable(false);
        jlbl_painting.setBorder(null);
        jlbl_painting.addMouseMotionListener(_controlPaint);
        jlbl_painting.addMouseListener(_controlPaint);
        jlbl_painting.setPaintListener(_controlPaint.getControlPic());
        jlbl_painting.setOpaque(false);
        super.add(jlbl_painting);


        jlbl_backgroundAlpha = new MLabel();
        jlbl_backgroundAlpha.setBackground(Color.white);
        jlbl_backgroundAlpha.setFocusable(false);
        jlbl_backgroundAlpha.setBorder(null);
        jlbl_backgroundAlpha.setOpaque(true);
        super.add(jlbl_backgroundAlpha); 


        _controlPaint.getcTabPaint().updateResizeLocation();
        

	}
	

    
    
    /**
     * recalculate center bounds of ScrollPanes (if location has changed 
     * without using the ScrollPanes).
     */
    public void refrehsSps() {
    	
    	//
    	// recalculate the scrolling-step
    	//
    	final double zoomWidth = 1.0 * State.getImageShowSize().width 
    			/ State.getImageSize().width;
    	final double zoomHeight = 1.0 * State.getImageShowSize().width 
    			/ State.getImageSize().width;
    	
    	final int factor = 20;
    	
    	sp_ub.setMoveStep((int) Math.max(factor, factor * zoomWidth));
    	sp_ub.setMoveStep((int) Math.max(factor, factor * zoomHeight));
    	
    	// recalculate center bounds.
        sp_ub.recalculateCenterBounds();
        sp_lr.recalculateCenterBounds();
    }
    
    
    
    /**
     * hide the buttons.
     */
    public void removeButtonss() { 
        final int newLocation = -100;
        for (int a = 0; a < jbtn_resize.length; a++) {
            for (int b = 0; b < jbtn_resize[a].length; b++) {
                jbtn_resize[a][b].setLocation(newLocation, newLocation);
            }
        }
    }
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override public void setSize(final int _width, final int _height) {
		
	    //set standard size
	    super.setSize(_width, _height);
	    flip();
	}
	

	
	
	/**
	 * .
	 */
	public void flip() {
		

		jpnl_toMove.setBounds(0, 0,
				State.getImageShowSize().width,
				State.getImageShowSize().height);

//		if (sp_ub.getWidth() == 0 || sp_ub.getHeight() == 0) {
			
		sp_ub.setSize(ViewSettings.VIEW_SIZE_SP, 
				ViewSettings.getSizeJFrame().height 
				- ViewSettings.VIEW_SIZE_SP
				- ViewSettings.DISTANCE_TO_WINDOW);
        sp_lr.setSize(ViewSettings.getSizeJFrame().width
        		+ sp_ub.getWidth() * 1 - ViewSettings.DISTANCE_TO_WINDOW * 2, 
                ViewSettings.VIEW_SIZE_SP
                - ViewSettings.DISTANCE_TO_WINDOW);
//        jpnl_toMove.setLocation(
//        		(int) jlbl_painting.getLocation().getX(), 
//        		(int) jpnl_toMove.getLocation().getY());
//		}

        jlbl_backgroundAlpha.setBounds(0, 0, getWidth() - 1, getHeight() - 1);
        jlbl_backgroundStructure.setBounds(
        		0, 0, getWidth() - 1, getHeight() - 1);
        jlbl_selectionBG.setBounds(0, 0, getWidth() - 1, getHeight() - 1);
//        jlbl_selectionBG.setBackground(new Color(50, 50, 250, 0));
//        jlbl_selectionBG.setIcon(null);
//        jlbl_selectionBG.setOpaque(true);
        jlbl_selectionPainting.setBounds(0, 0, getWidth() - 1, getHeight() - 1);
        
        jlbl_backgroundAlpha.setIcon(new ImageIcon(
        		State.getBi_transparency()));

        
        int paintWidth = getWidth() - 1;
        int paintHeight = getHeight() - 1;
//        final int maxZoom = (int) Math.pow(ViewSettings.ZOOM_MULITPLICATOR, 
//                ViewSettings.MAX_ZOOM_IN);
//        paintWidth = (int)((int) (paintWidth / maxZoom) * maxZoom);
//        paintHeight = (int)((int) (paintHeight / maxZoom) * maxZoom);
        
		//the order of painting is important! It is necessary that the 
        //paintinglabel's bounds and the locations of the ScrollPanes are 
        //set here
        jlbl_painting.setBounds(
        		(int) jlbl_painting.getLocation().getX(),
        		(int) jlbl_painting.getLocation().getY(), 
        		paintWidth, paintHeight);
		sp_ub.setLocation(ViewSettings.getSizeJFrame().width 
				- sp_ub.getWidth() - ViewSettings.DISTANCE_TO_WINDOW,
				ViewSettings.VIEW_SIZE_SP);
		sp_lr.setLocation(ViewSettings.DISTANCE_TO_WINDOW, 
				ViewSettings.getSizeJFrame().height 
				- sp_lr.getHeight() - ViewSettings.DISTANCE_TO_WINDOW);
            	

        refrehsSps();

	}
	
	


	
	/*
	 * getter methods
	 */
	

    /**
     * @return the jlbl_painting
     */
    public PaintLabel getJlbl_painting() {
        return jlbl_painting;
    }


    /**
     * @return the jlbl_selection
     */
    public MLabel getJlbl_selectionBG() {
        return jlbl_selectionBG;
    }
    

    /**
     * @return the jlbl_selectionPainting
     */
    public MLabel getJlbl_selectionPainting() {
        return jlbl_selectionPainting;
    }


    /**
     * @return the jbtn_resize
     */
    public MButton[][] getJbtn_resize() {
        return jbtn_resize;
    }


    /**
     * @return the jlbl_border
     */
    public MLabel getJlbl_border() {
        return jlbl_border;
    }


    /**
     * @return the jlbl_background
     */
    public MLabel getJlbl_background() {
        return jlbl_backgroundAlpha;
    }



    /**
     * @return the jlbl_background
     */
    public MLabel getJlbl_backgroundStructure() {
        return jlbl_backgroundStructure;
    }


    /**
     * @return the jlbl_resizeSelectionSize
     */
    public MLabel getJlbl_resizeSelectionSize() {
        return jlbl_resizeSelectionSize;
    }


	/**
	 * @return the jpnl_new
	 */
	public New getJpnl_new() {
		return jpnl_new;
	}


	/**
	 * @param _jpnl_new the jpnl_new to set
	 */
	public void setJpnl_new(final New _jpnl_new) {
		this.jpnl_new = _jpnl_new;
	}


	/**
	 * simple getter method.
	 * @return the QuickAccess
	 */
	public QuickAccess getQuickAccess() {
		return quickAccess;
	}


	/**
	 * simple getter method.
	 * @return the MPanel
	 */
	public MPanel getJpnl_toMove() {
		return jpnl_toMove;
	}
}

