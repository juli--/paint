//package declaration
package view.util;


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


//import java.awt components
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

//imports for printing the date to the tabbedPane 
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

//import java.swing components
import javax.swing.BorderFactory;



//import utility classes and settings
import model.settings.State;
import model.settings.ViewSettings;
import model.util.Util;

//controller class giving the interface for opening, closing and moving events.
import control.interfaces.MoveEvent;
import control.interfaces.TabbedListener;

//controller class which handles the tab - opening
import control.util.CTabbedPane;
import view.tabs.Tab;
//import rotatatble buttons and panels
import view.util.mega.MLabel;
import view.util.mega.MPanel;


/**
 * The tabbedPanel.
 * 
 * @author Julius Huelsmann
 * @version %I% %U%
 */
@SuppressWarnings("serial")
public class VTabbedPane extends MPanel {

    
    /**
     * The TabbedListener which can be added to the VTabbedPane for performing
     * special actions if the VTabbedPane is opened or closed or moving because
     * of close and open event.
     */
    private TabbedListener tl;
    
	/**
	 * array of headline MButtons.
	 */
	private VTabButton [] jbtn_stuffHeadline;
	
	/**
	 * MButton for closing the tabbedPane.
	 */
	private MLabel jlbl_close;
	
	/**
	 * array of Panels for each MButton.
	 */
	private MPanel  [] jpnl_stuff, jpnl_stuff_layer_2;
	
	/**
	 * MLabel for the background.
	 */
	private MLabel jpnl_background, jlbl_whiteBackground;
	
	/**
	 * MPanel for the content 
	 *     MButton for title
	 *     MPanel for content of tab.
	 */
	private MPanel jpnl_contains;
	
	/*
	 * settings
	 */
	
	/**
	 * The bounds of the title MButton and location of the Tab MPanel.
	 */
	private int titleX = 1, titleY = 1;
	
	
	/**
	 * The visible height.
	 */
	private int visibleHeight = 0;
	
	/**
	 * The currently open tab.
	 */
	private int openTab = 0;
	
	/**
	 * The controller class.
	 */
	private CTabbedPane control;
	
	/**
	 * 
	 */
	private int oldHeight = 0, oldVisibleHeight = 0;

	/**
	 * Whether pressed or not.
	 */
	private boolean press = false;
	
	/**
	 * The closing MPanel.
	 */
	private MPanel jpnl_close;
	
	/**
	 * MLabel containing the time, date and the working time.
	 */
	private MLabel jlbl_closeTime;
	
	/**
	 * MLabel for stroke.
	 */
	private MLabel jlbl_stroke;

	/**
	 * The owner of the VTabbedPane used for being able to open and to close
	 * the VTabbedPane by dragging the mouse.
	 */
	private Component c_owner;
	
	
	private MLabel jlbl_background_settings_1, jlbl_background_settings_2;
	
	/**
	 * Constructor initialize view and corresponding controller class..
	 * @param _c_owner 
	 * 					The owner of the VTabbedPane used for being able to 
	 * 					open and to close the VTabbedPane by dragging the mouse.
	 */
	public VTabbedPane(final Component _c_owner) {
		
		//initialize MPanel and alter settings
		super();
		super.setFocusable(false);
		super.setLayout(null);
		super.setOpaque(false);
		
		//save the owner
		this.c_owner = _c_owner;
		
		//initialize the container for the title MButton and the tab  MPanels
		jpnl_contains = new MPanel();
		jpnl_contains.setFocusable(false);
		jpnl_contains.setOpaque(true);
		jpnl_contains.setBackground(new Color(0, 0, 0, 0));
		jpnl_contains.setLayout(null);
		super.add(jpnl_contains);

        //initialize the Background MLabel
        jpnl_background = new MLabel();
        jpnl_background.setFocusable(false);
        jpnl_background.setOpaque(true);
        jpnl_background.setLayout(null);
        jpnl_background.setBackground(ViewSettings.GENERAL_CLR_BACKGROUND_DARK);
        jpnl_background.setBorder(BorderFactory.createMatteBorder(
                1, 0, 0, 1, ViewSettings.GENERAL_CLR_BORDER));
        jpnl_contains.add(jpnl_background);

        jlbl_stroke = new MLabel();
        jlbl_stroke.setBorder(null);
        jlbl_stroke.setOpaque(false);
        jpnl_contains.add(jlbl_stroke);

        jlbl_background_settings_2 = new MLabel();
        jlbl_background_settings_2.setBorder(null);
        jlbl_background_settings_2.setVisible(false);
        jlbl_background_settings_2.setOpaque(false);
        jpnl_contains.add(jlbl_background_settings_2);
        
        jlbl_background_settings_1 = new MLabel();
        jlbl_background_settings_1.setBorder(null);
        jlbl_background_settings_1.setOpaque(true);
        jlbl_background_settings_1.setVisible(false);
        jlbl_background_settings_1.setBackground(
        		ViewSettings.GENERAL_CLR_BACKGROUND_GREEN);
        jlbl_background_settings_1.setBorder(BorderFactory.createMatteBorder(
                1, 0, 0, 0, ViewSettings.GENERAL_CLR_BORDER));
        jpnl_contains.add(jlbl_background_settings_1);

        jpnl_close = new MPanel();
        jpnl_close.setFocusable(false);
        jpnl_close.setOpaque(true);
        jpnl_close.setBackground(Color.white);
        jpnl_close.setLayout(null);
        super.add(jpnl_close);
        
        jlbl_closeTime = new MLabel("Sonntag, 2014 09 14\t 20:17:40");
        jlbl_closeTime.setForeground(Color.black);
        jlbl_closeTime.setOpaque(false);
        jlbl_closeTime.setFocusable(false);
        jlbl_closeTime.setFont(new Font("Courier new",
                Font.ITALIC, (2 + 2 + 2) * (2)));
        jlbl_closeTime.setVisible(false);
        jpnl_close.add(jlbl_closeTime);
        
        jlbl_close = new MLabel();
        jlbl_close.setFocusable(false);
        jlbl_close.setOpaque(true);
        jlbl_close.addMouseMotionListener(new MouseMotionListener() {
            
            public void mouseMoved(final MouseEvent _event) { }
            public void mouseDragged(final MouseEvent _event) {
                moveTab(_event);
            }
        });
        jlbl_close.setBackground(ViewSettings.GENERAL_CLR_BACKGROUND_DARK);
        jlbl_close.addMouseListener(new MouseListener() {
            
            public void mouseReleased(final MouseEvent _event) {
                press = false;
                if (getHeight() <= (oldVisibleHeight + (oldVisibleHeight 
                        / ViewSettings
                        .TABBED_PANE_TITLE_PROPORTION_HEIGHT)) / 2) {
                    closeTabbedPane();
                } else if (getHeight() <= (oldVisibleHeight
                		+ (getVisibleHeightEnitelyOpen() -  oldVisibleHeight ) / 2)) {
                	System.out.println("open t");
                    openTabbedPane();
                } else {
                	System.out.println("open enti");
                	openTabbedPaneEntirely();
                }
            }
            public void mousePressed(final MouseEvent _event) {
                press = true;
            }
            public void mouseExited(final MouseEvent _event)  { }
            public void mouseEntered(final MouseEvent _event) { }
            public void mouseClicked(final MouseEvent _event) { }
        });
        jlbl_close.setBorder(BorderFactory.createMatteBorder(
                0, 0, 1, 0, ViewSettings.GENERAL_CLR_BORDER));
        jlbl_close.setFocusable(false);
        jpnl_close.add(jlbl_close);

        //background at the top where the buttons can be found.
        jlbl_whiteBackground = new MLabel();
        jlbl_whiteBackground.setBackground(Color.white);
        jlbl_whiteBackground.setOpaque(true);
        jlbl_whiteBackground.setFocusable(false);
        super.add(jlbl_whiteBackground);
        
		//initialize controller class
		control = new CTabbedPane(this);
		startTimeThread();
		
		super.setVisible(false);
	}
	

    /**
     * Set the TabbedListener. Only one listener can be added. If this method
     * is called twice, the argument passed the second time is enabled as
     * listener.
     * 
     * @param _tl the tabbedListener
     */
    public final void setTabbedListener(final TabbedListener _tl) {
    	this.tl = _tl;
    }
	
	@Override public final void setVisible(final boolean _b) {
	    
	    if (_b) {
	        openTab(openTab);
	    }
	    
	    super.setVisible(_b);
	}
	
	/**
	 * Initialize the time log thread.
	 */
	private void startTimeThread() {

        new Thread() {
            public void run() {
                
                double start = System.currentTimeMillis();
                while (true) {
                    try {
                        Thread.sleep(2 * (2 + 2 + 1));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    DateFormat dateFormat = new SimpleDateFormat(
                            "yyyy MM dd   HH:mm:ss");
                    Calendar cal = Calendar.getInstance();
                    
                    String wochentag = "";
                    switch (cal.get(Calendar.DAY_OF_WEEK)) {
                    case 0:
                        wochentag = "Samstag";
                        break;
                    case 1:
                        wochentag = "Sonntag";
                        break;
                    case 2:
                        wochentag = "Montag";
                        break;
                    case 2 + 1:
                        wochentag = "Dienstag";
                        break;
                    case 2 + 2:
                        wochentag = "Mittwoch";
                        break;
                    case 2 + 2 + 1:
                        wochentag = "Donnerstag";
                        break;
                    case (2 + 1) * 2:
                        wochentag = "Freitag";
                        break;
                    case (2 + 1) * 2 + 1:
                        wochentag = "Samstag";
                        break;
                    default:
                        wochentag = "Tolltag";
                        break;
                    }
                    
                    double currentTime = System.currentTimeMillis() - start;
                    String bearbeitung = "Bearbeitungszeit: ";

                    final int max_min = 60, max_h = 60, max_d = 24, 
                            max_s = 1000;
                    
                    int sekunden = (int) (currentTime / max_s);
                    int minuten = (int) (sekunden / max_min);
                    int stunden = (int) (minuten / max_h);
                    int tage = stunden / max_d;
                    
                    sekunden = sekunden % max_min;
                    minuten = minuten % max_h;
                    stunden = stunden % max_h;

                    if (tage != 0) {
                        bearbeitung += tage + " Tage, " + stunden + " h, "
                                + minuten + " m, " + sekunden + "s";
                    } else if (stunden != 0) {

                        bearbeitung +=  stunden + " h, "
                                + minuten + " m, " + sekunden + "s";
                    } else if (minuten != 0) {

                        bearbeitung += minuten + " m, " + sekunden + "s";
                    } else if (sekunden != 0) {

                        bearbeitung +=  sekunden + "s";
                    }

                    jlbl_closeTime.setText(wochentag 
                            + "   " + dateFormat.format(cal.getTime())
                            + "   " + "" + bearbeitung);
                }
            }
        } .start();
	}
	
	
	/**
	 * MOVE THE TAB.
	 * @param _e the event.
	 */
	private void moveTab(final MouseEvent _e) {

        jpnl_contains.setVisible(true);
        if (press) {
            super.setSize(getWidth(), _e.getYOnScreen()
                    - c_owner.getY()); 
            jpnl_close.setLocation(0, getHeight() - jlbl_close.getHeight());
            jpnl_background.setSize(getWidth(), getHeight());

            if (tl != null)  {
            	tl.moveListener(new MoveEvent(new Point(
                        ViewSettings.getView_bounds_page().x,
                        getHeight() + 1)));
            }
            setComponentZOrder(jpnl_close, 0);
            jlbl_closeTime.setVisible(false);
            
        } 
	}
	
	
	
	
	/**
	 * only super size setting method caller from outside the class.
	 * @param _width the width 
	 * @param _height the height.
	 */
	private void setComponentSize(final int _width, final int _height) {
	    super.setSize(_width, _height);
	}
	
	
	
	
	/**
	 * Open or close the TabbedPane.
	 * @param _bool	whether to open or to close the tabbedPane.
	 */
	public final void setTabbedPaneOpen(final boolean _bool) {
		
		if (_bool) {
			openTabbedPane();
		} else {
			closeTabbedPane();
		}
	}
	
	public final void setTabbedPaneEntirelyOpen() {
		openTabbedPaneEntirely();
	}
	
	
	public final static int ID_TABBED_PANE_CLOSED = 0, ID_TABBED_PANE_OPEN_1 = 1,
			ID_TABBED_PANE_OPEN_2 = 2;
	private int tabbedPaneOpenState = ID_TABBED_PANE_OPEN_1;
	
	private final int time_to_sleep_open_close = 3;
	private final int amountSteps = 25;
	
	/**
	 * close the tab.
	 */
	private void closeTabbedPane() {
	    
	    jpnl_contains.setVisible(false);
	    Thread t_closeTab = new Thread() {
	        public void run() {

	            int startHeight = getHeight();
	            final int max = amountSteps;
	            for (int i = 0; i < max; i++) {

	                setComponentSize(getWidth(), startHeight + (oldHeight 
	                        / ViewSettings.TABBED_PANE_TITLE_PROPORTION_HEIGHT 
	                        - startHeight) * i / max);
	                jpnl_close.setLocation(0, getHeight());
	                jpnl_background.setSize(getWidth(), getHeight()
	                        - jpnl_background.getY());

	                if (tl != null)  {
	                	tl.moveListener(new MoveEvent(new Point(ViewSettings
		                        .getView_bounds_page().x, getHeight() + 1)));
	                }
	                try {
	                    Thread.sleep(time_to_sleep_open_close);
	                } catch (InterruptedException e) {
	                    e.printStackTrace();
	                }
	                
	            }

	            setComponentSize(getWidth(), 
	                    oldHeight / ViewSettings
                        .TABBED_PANE_TITLE_PROPORTION_HEIGHT);
	            jpnl_close.setLocation(0, getHeight() - jlbl_close.getHeight());
	            jpnl_background.setSize(getWidth(), getHeight()
                        - jpnl_background.getY());

                if (tl != null)  {
                	tl.moveListener(new MoveEvent(new Point(
                			ViewSettings
	                        .getView_bounds_page().x,
    	                    getHeight() + getY() + 1)));
                }
	            setComponentZOrder(jpnl_close, 1);
	            jlbl_closeTime.setVisible(true);

                ViewSettings.getView_bounds_page().y
                = ViewSettings.getView_bounds_page_closed().y;
                ViewSettings.getView_bounds_page().height
                = ViewSettings.getView_bounds_page_closed().height;
	            //adapt image size.
                
                tl.closeListener();
                jlbl_closeTime.repaint();
            	tabbedPaneOpenState = ID_TABBED_PANE_CLOSED;
	        }
	    };
        jlbl_stroke.setBorder(null);
        jlbl_background_settings_1.setVisible(false);
        jlbl_background_settings_2.setVisible(false);
        for (int i = 0; i < jpnl_stuff_layer_2.length; i++) {
            jpnl_stuff_layer_2[i].setVisible(false);
		}
	    t_closeTab.start();
	    
	}
	
	/**
	 * open the tab.
	 */
	private void openTabbedPane() {

        jpnl_contains.setVisible(true);
        Thread t_closeTab = new Thread() {
            public void run() {

                int startHeight = getHeight();
                int startHeight2 = jpnl_close.getY();
                final int max = amountSteps;
                for (int i = 0; i < max; i++) {

                    setComponentSize(getWidth(), startHeight
                            + (oldHeight * 2 - startHeight)
                            * i / max);
                    jpnl_close.setLocation(0, startHeight2 
                            + (oldVisibleHeight - startHeight2) * i / max);
                    
                    jlbl_closeTime.setBounds(0 , 2, jpnl_close.getWidth() 
                            / 2, jpnl_close.getHeight());
                    
                    jpnl_background.setSize(getWidth(), 
                            startHeight2 + (oldVisibleHeight - startHeight2) 
                            * i / max
                            - jpnl_background.getY());
                    

                    if (tl != null)  {
                    	tl.moveListener(new MoveEvent(new Point(
                                ViewSettings.getView_bounds_page().x, 
                                startHeight2 
                                + (oldVisibleHeight - startHeight2) 
                                * i / max)));
                    }
                    
                    try {
                        Thread.sleep(time_to_sleep_open_close);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                
                setComponentSize(getWidth(), oldHeight);
                jpnl_close.setLocation(0, 
                        oldVisibleHeight);
                jpnl_background.setSize(getWidth(), oldVisibleHeight 
                        - jpnl_background.getY());
                jpnl_background.repaint();

                if (tl != null)  {
                	tl.moveListener(new MoveEvent(new Point(
                            ViewSettings.getView_bounds_page().x, 
                            oldVisibleHeight 
                            + jpnl_close.getHeight() + getY() + 1)));
                }
                setComponentZOrder(jpnl_close, 1);


                ViewSettings.getView_bounds_page().y
                = ViewSettings.getView_bounds_page_open().y;
                ViewSettings.getView_bounds_page().height
                = ViewSettings.getView_bounds_page_open().height;
                

                if (tl != null)  {
                	tl.openListener();
                }
                jlbl_closeTime.repaint();
            	tabbedPaneOpenState = ID_TABBED_PANE_OPEN_1;
            }
        };
        jlbl_stroke.setBorder(null);
        jlbl_background_settings_1.setVisible(false);
        jlbl_background_settings_2.setVisible(false);

        for (int i = 0; i < jpnl_stuff_layer_2.length; i++) {

            jpnl_stuff_layer_2[i].setVisible(false);
		}
        t_closeTab.start();

	}
	
	

	
	/**
	 * open the tab.
	 */
	private void openTabbedPaneEntirely() {

        jlbl_stroke.setBorder(BorderFactory.createMatteBorder(
                0, 0, 1, 0, ViewSettings.GENERAL_CLR_BORDER));
        jlbl_background_settings_1.setVisible(true);
        jlbl_background_settings_2.setVisible(true);
        jpnl_contains.setVisible(true);
        
        for (int i = 0; i < jpnl_stuff_layer_2.length; i++) {

            jpnl_stuff_layer_2[i].setVisible(true);
		}
        
        Thread t_closeTab = new Thread() {
            public void run() {

                int startHeight = getHeight();
                int startHeight2 = jpnl_close.getY();
                final int max = amountSteps;
                for (int i = 0; i < max; i++) {

                    setComponentSize(getWidth(), startHeight
                            + (getHeightEnitelyOpen() - startHeight)
                            * i / max);
                    jpnl_close.setLocation(0, startHeight2 
                            + (getVisibleHeightEnitelyOpen() - startHeight2) * i / max);
                    
                    jlbl_closeTime.setBounds(0 , 2, jpnl_close.getWidth() 
                            / 2, jpnl_close.getHeight());
                    
                    jpnl_background.setSize(getWidth(), 
                            startHeight2 + (oldVisibleHeight
                            		+ titleY + 5
                            		- startHeight2) 
                            * i / max
                            - jpnl_background.getY());
                    

                    if (tl != null)  {
                    	tl.moveListener(new MoveEvent(new Point(
                                ViewSettings.getView_bounds_page().x, 
                                startHeight2 
                                + (getVisibleHeightEnitelyOpen() - startHeight2) 
                                * i / max)));
                    }
                    
                    try {
                        Thread.sleep(time_to_sleep_open_close);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                
                setComponentSize(getWidth(), getHeightEnitelyOpen());
                jpnl_close.setLocation(0, 
                        getVisibleHeightEnitelyOpen());
                jpnl_background.setSize(getWidth(), oldVisibleHeight 
                		+ titleY + 5
                        - jpnl_background.getY());
                jlbl_stroke.setSize(jpnl_background.getSize());
                jpnl_background.repaint();

                if (tl != null)  {
                	tl.moveListener(new MoveEvent(new Point(
                            ViewSettings.getView_bounds_page().x, 
                            getVisibleHeightEnitelyOpen() 
                            + jpnl_close.getHeight() + getY() + 1)));
                }
                setComponentZOrder(jpnl_close, 1);


                ViewSettings.getView_bounds_page().y
                = ViewSettings.getView_bounds_page_open().y;
                ViewSettings.getView_bounds_page().height
                = ViewSettings.getView_bounds_page_open().height;

                if (jlbl_background_settings_2.isVisible())
                Util.getStroke(jlbl_background_settings_2);

                if (tl != null)  {
                	tl.openListener();
                }
                jlbl_closeTime.repaint();
            	tabbedPaneOpenState = ID_TABBED_PANE_OPEN_2;
            }
        };
        t_closeTab.start();

	}
	
	
	/**
	 * add a new tab and its title to tabbedPane.
	 * @param _title the title of the new tab.
	 */
	public final void addTab(final String _title) {

		//if the MPanel is not initialized yet.
		if (jpnl_stuff == null) {
			
		    //create arrays for content MPanel and headline MButton
			jpnl_stuff = new MPanel[1];
			jpnl_stuff_layer_2 = new MPanel[1];
			jbtn_stuffHeadline = new VTabButton[1];

			//initialize title button and content MPanel
			jbtn_stuffHeadline[0] = initJbtn_title(0, _title);
			jpnl_stuff[0] = initJpnl_tab();
			jpnl_stuff_layer_2[0] = initJpnl_tab_layer2();
			
		} else {
			
		    //create new arrays
			MPanel [] jpnl_stuff2 = new MPanel [jpnl_stuff.length + 1];
			MPanel [] jpnl_stuff2_layer_2 = new MPanel[jpnl_stuff.length + 1];
			VTabButton[] jbtn_stuff2 
			= new VTabButton[jpnl_stuff.length + 1];

			//save old MPanels
			for (int i = 0; i < jpnl_stuff.length; i++) {
				jpnl_stuff2[i] = jpnl_stuff[i];
				jbtn_stuff2[i] = jbtn_stuffHeadline[i];
				jpnl_stuff2_layer_2[i] = jpnl_stuff_layer_2[i];
				jpnl_stuff2[i].setVisible(true);
			}
			
			//initialize the new MPanels.
			jbtn_stuff2[jpnl_stuff.length] = initJbtn_title(
			        jpnl_stuff.length, _title);
			jpnl_stuff2[jpnl_stuff.length] = initJpnl_tab();
			jpnl_stuff2_layer_2[jpnl_stuff.length] = initJpnl_tab_layer2();

            if (_title.equals("")) {
                
                jbtn_stuff2[jpnl_stuff.length].setVisible(false);
                jbtn_stuff2[jpnl_stuff.length].setSize(
                        jbtn_stuff2[jpnl_stuff.length].getWidth(), 1);
            }
			//save the new created MPanel and MButton arrays.
			jpnl_stuff = jpnl_stuff2;
			jbtn_stuffHeadline = jbtn_stuff2;
			jpnl_stuff_layer_2 = jpnl_stuff2_layer_2;
		}
		
		//set position of background to last painted
        jpnl_contains.setComponentZOrder(
                jpnl_background, jpnl_stuff.length - 1);
	
//        if (jpnl_stuff.length == 1) {
//
//            //open the first tab by default and repaint the TabbedPane
//    		openTab(openTab);
//    		flip();
//        }
        
	}
	

	
	/**
     * add component at special tab index.
     * 
     * @param _index the tab index.
     * @param _c the component which is added.
     * 
     * @return the added component
     */
    public final Component addToTab(final int _index, final Component _c) {
    	
    
           //if the MPanel for the stuff does not exist yet, not a single
        //tab has been added before and it is thus impossible to add
        //a component to a special tab.
        if (jpnl_stuff == null) {
            
            //print error message
            State.getLogger().severe(
                    "add an item to a not existing MPanel (no item added yet)");
    
            //return null
            return null;
    		
    	} else if (_index < 0 || _index >= jpnl_stuff.length) {
    
            //print error message
            State.getLogger().severe("add an item to a not existing MPanel "
            		+ "(index out of range)");
    
            //return null
            return null;
            
    	} else if (_c == null) {
    
            //print error message
            State.getLogger().severe("add an item to a not existing MPanel "
            		+ "(Component to add is null)");
    
            //return null
            return null;
    	} else {
    		_c.setFocusable(false);
    		return jpnl_stuff[_index].add(_c);
    	}
    }
	
	/**
     * add component at special tab index.
     * 
     * @param _index the tab index.
     * @param _c the component which is added.
     * 
     * @return the added component
     */
    public final Component addToTabLayer2(final int _index, final Component _c) {
    	
    
           //if the MPanel for the stuff does not exist yet, not a single
        //tab has been added before and it is thus impossible to add
        //a component to a special tab.
        if (jpnl_stuff == null) {
            
            //print error message
            State.getLogger().severe(
                    "add an item to a not existing MPanel (no item added yet)");
    
            //return null
            return null;
    		
    	} else if (_index < 0 || _index >= jpnl_stuff.length) {
    
            //print error message
            State.getLogger().severe("add an item to a not existing MPanel "
            		+ "(index out of range)");
    
            //return null
            return null;
            
    	} else if (_c == null) {
    
            //print error message
            State.getLogger().severe("add an item to a not existing MPanel "
            		+ "(Component to add is null)");
    
            //return null
            return null;
    	} else {
    		_c.setFocusable(false);
    		return jpnl_stuff_layer_2[_index].add(_c);
    	}
    }


    /**
	 * initialize a new headline for MButton which is added to TabbedPane.
	 * 
	 * @param _index the current index of tab
	 * @param _text the headline
	 * @return the MButton.
	 */
	private VTabButton initJbtn_title(final int _index, final String _text) {
	
		VTabButton jbtn = new VTabButton(_index);
		jbtn.setVisible(true);
		jbtn.setBackground(Color.white);
		jbtn.setBorder(null);
        jbtn.addActionListener(control);
        jbtn.addMouseListener(control);
		jbtn.setFocusable(false);
		jbtn.setText(_text);
		jbtn.setContentAreaFilled(false);
		jbtn.setOpaque(true);
		jpnl_contains.add(jbtn);
		return jbtn;
	}

	
	/**
	 * initialize a new MPanel for the tab.
	 * @return the ready MPanel.
	 */
	private MPanel initJpnl_tab() {

		MPanel jpnl = new MPanel();
		jpnl.setVisible(true);
		jpnl.setLayout(null);
		jpnl.setFocusable(false);
		jpnl.setOpaque(false);
		jpnl_contains.add(jpnl);
		return jpnl;
	}
	
	/**
	 * initialize a new MPanel for the tab.
	 * @return the ready MPanel.
	 */
	private MPanel initJpnl_tab_layer2() {

		MPanel jpnl = new MPanel();
		jpnl.setVisible(false);
		jpnl.setLayout(null);
		jpnl.setFocusable(false);
		jpnl.setOpaque(false);
//		jpnl.setBackground(new Color(new Random().nextInt(250),
//				new Random().nextInt(250), new Random().nextInt(250)));
		jpnl_contains.add(jpnl);
		return jpnl;
	}

	
	/**
	 * open a special tab and highlight is headline.
	 * 
	 * @param _index the index which is to be highlighted
	 */
	public final void openTab(final int _index) {
	    
		//if new opening method is selected or not (new opening consists in 
		//sliding open).
	    final boolean newOpening = true;
	    
	    if (newOpening) {
	        
	        //the last tab
	        final int lastTab = openTab;
            
            //save currently open tab
            openTab = _index;

            //go through the list of headline MButtons and tab MPanels
            for (int i = 0; i < jbtn_stuffHeadline.length; i++) {

                
                //set the standard background and a border at the bottom for 
                //each not selected panel and button
                jbtn_stuffHeadline[i].setBackground(Color.white);
                jbtn_stuffHeadline[i].setBorder(BorderFactory.createMatteBorder(
                        0, 0, 1, 0, ViewSettings.GENERAL_CLR_BORDER));
                jbtn_stuffHeadline[i].unstroke();

                //make the tab MPanel visible
                jpnl_stuff[i].setLocation(getWidth() * (i - lastTab), 
                        jpnl_stuff[i].getY());
                jpnl_stuff[i].setVisible(true);
                //make the tab MPanel visible
                jpnl_stuff_layer_2[i].setLocation(getWidth() * (i - lastTab), 
                        jpnl_stuff_layer_2[i].getY());
                if (getTabbedPaneOpenState() == ID_TABBED_PANE_OPEN_2) {

                    jpnl_stuff_layer_2[i].setVisible(true);
                }
            }
            
            //set the selected background for the currently selected tab
            //and create a border everywhere except at the bottom
            jbtn_stuffHeadline[_index].setBackground(
                    ViewSettings.GENERAL_CLR_BACKGROUND_DARK);
            jbtn_stuffHeadline[_index].setBorder(
                    BorderFactory.createMatteBorder(
                    1, 1, 0, 1, ViewSettings.GENERAL_CLR_BORDER));
            jpnl_contains.setComponentZOrder(jbtn_stuffHeadline[_index], 1);
            jpnl_contains.setComponentZOrder(jlbl_stroke, 2);

            
            if (getTabbedPaneOpenState() == ID_TABBED_PANE_OPEN_2) {
            	super.setSize(getWidth(), getVisibleHeightEnitelyOpen()
            			);
            } else {

                super.setSize(getWidth(), visibleHeight);
            }
            new Thread() {
                public void run() {

                    final int max = 150;
                    
                    //go through the list of headline MButtons and tab 
                    //MPanels
                    jpnl_contains.setComponentZOrder(jpnl_stuff[openTab],1);
                    jpnl_contains.setComponentZOrder(jpnl_stuff_layer_2[openTab],2);
                    for (int percent = 0; percent <= max; percent++) {
                        for (int i = 0; i < jbtn_stuffHeadline.length; i++) {

                            int cStartLocation = getWidth() * (i - lastTab);
                            int cEndLocation = getWidth() * (i - _index);

                            //make the tab MPanel visible
                            jpnl_stuff[i].setLocation(cStartLocation
                                    + (cEndLocation - cStartLocation)
                                    * percent / max,
                                    jpnl_stuff[i].getY());
                            

                            if (getTabbedPaneOpenState() == ID_TABBED_PANE_OPEN_2) {

                                //make the tab MPanel visible
                                jpnl_stuff_layer_2[i].setLocation(cStartLocation
                                        + (cEndLocation - cStartLocation)
                                        * percent / max,
                                        jpnl_stuff_layer_2[i].getY());
                            }
                            
                            
//                            jpnl_stuff[i].repaint();
//                            jlbl_background_settings_1.repaint();
                        }
                        
//                        jlbl_background_settings_1.setLocation(jlbl_background_settings_1.getLocation());
//                        jpnl_contains.setComponentZOrder(jpnl_stuff[openTab], 1);
                        try {
                            Thread.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            setSize(getWidth(), oldHeight);
                        }
                    }
                    for (int i = 0; i < jbtn_stuffHeadline.length; i++) {
                        int cStartLocation = getWidth() * (i - lastTab);
                        int cEndLocation = getWidth() * (i - _index);

                        jpnl_stuff[i].setLocation(cStartLocation
                                + cEndLocation - cStartLocation,
                                jpnl_stuff[i].getY());
                        
                        jpnl_stuff_layer_2[i].setLocation(cStartLocation
                                + cEndLocation - cStartLocation,
                                jpnl_stuff_layer_2[i].getY());
                    }

                    setSize(getWidth(), oldHeight);
                    stroke();
                    jpnl_contains.setComponentZOrder(jpnl_stuff[openTab], 1);
                    jpnl_contains.setComponentZOrder(jpnl_stuff_layer_2[openTab], 2);

//                    Status.getLogger().severe("hier bin ich "
//                    		+ "(der ehemalige windows fehler,"
//                    		+ " der nun auskommentiert ist.)");
                    //TODO: Windows Fehler 1 (solvedAtWindows)
                    //TODO: in combination mit dem repaint. das
                    //repaint hier zerstoert die anzeige.
                    //das wird weggenommen. warum auch immer.
                    //                    jpnl_stuff[openTab].repaint();

                }

            } .start();

	    } else {

            //save currently open tab
            openTab = _index;
            
            //go through the list of headline MButtons and tab MPanels
            for (int i = 0; i < jbtn_stuffHeadline.length; i++) {

                //set the tab invisible
                jpnl_stuff[i].setVisible(false);

                if (getTabbedPaneOpenState() == ID_TABBED_PANE_OPEN_2) {

                    jpnl_stuff_layer_2[i].setVisible(false);
                }
                
                //set the standard background and a border at the bottom for 
                //each not selected panel and button
                jbtn_stuffHeadline[i].setBackground(Color.white);
                jbtn_stuffHeadline[i].setBorder(BorderFactory.createMatteBorder(
                        0, 0, 1, 0, ViewSettings.GENERAL_CLR_BORDER));
            }
            
            //set the selected background for the currently selected tab
            //and create a border everywhere except at the bottom
            jbtn_stuffHeadline[_index].setBackground(
                    ViewSettings.GENERAL_CLR_BACKGROUND_DARK);
            jbtn_stuffHeadline[_index].setBorder(
                    BorderFactory.createMatteBorder(
                    1, 1, 0, 1, ViewSettings.GENERAL_CLR_BORDER));
            
            //make the tab MPanel visible
            jpnl_stuff[_index].setVisible(true);

            if (getTabbedPaneOpenState() == ID_TABBED_PANE_OPEN_2) {

                jpnl_stuff_layer_2[_index].setVisible(true);
            }
            
            //set the size of open tab
            flip();
	    }
	}
	
	/**
     * set the size of the TabbedPane and its components by flip method.
     * 
     * @param _width the width
     * @param _height the height
     * @param _visibleHeight the visible height.
     */
    public final void setSize(final int _width, final int _height, 
            final int _visibleHeight) {

        //save the height  
        this.oldHeight = _height;
        this.oldVisibleHeight = _visibleHeight;
        
        //set total size
    	super.setSize(_width, _height);
    	
    	//save the visible height
    	this.visibleHeight = _visibleHeight;
    	
    	//set the size and location of the components depending on 
    	//the current rotation
    	flip();
    }

    
    
    /**
     * Apply stroke.
     */
    public final void stroke() {

    	jbtn_stuffHeadline[getOpenTab()].stroke();
        Util.getStroke(jlbl_stroke);

        Util.getStrokeRec(jlbl_close);
    
        MPanel stuff = jpnl_stuff[getOpenTab()];
        for (Component i : stuff.getComponents()) {
        	if (i instanceof Tab) {
        		
        		for (Component j : ((Tab) i).getComponents()) {
        			
        			if (j instanceof Item1Button) {
        				
        				((Item1Button) j).stroke();
        			} else if (j instanceof Item1Menu) {
        				
        				((Item1Menu) j).stroke();
        			}
        		}
        	}
        }

        //tell e.g. the initialization thread that the initialization
        //process has proceeded one step.
        State.increaseInitializationFinished();
    }

    /**
     * set size and location of contents depending on the current
     * rotation given in parameter.
     * 
     */
    public final void flip() {

        final int titleWidth = getWidth() / ViewSettings
                .TABBED_PANE_TITLE_PROPORTION_WIDTH,
                selectedtitleWidth = getWidth() / (ViewSettings
                        .TABBED_PANE_TITLE_PROPORTION_WIDTH),
                titleHeight = getHeight() / ViewSettings
                        .TABBED_PANE_TITLE_PROPORTION_HEIGHT;
        
        //set the size of container MPanel (whole width and height)
        jpnl_contains.setSize(getWidth(), getHeight());
        
        //set background size (only visible width and height shown)
        jpnl_background.setSize(getWidth(), visibleHeight 
                - titleHeight + 5
//                - titleY
                );

        jlbl_whiteBackground.setSize(getWidth(), visibleHeight);

        jpnl_background.setLocation(0, titleHeight + titleY - 1);
        jlbl_stroke.setLocation(jpnl_background.getLocation());
        jlbl_stroke.setSize(jpnl_background.getSize());
        

        jlbl_background_settings_1.setLocation(jpnl_background.getX(),
        		jpnl_background.getY() + jpnl_background.getHeight()
        		+ 0);
        jlbl_background_settings_1.setSize(
        		jpnl_background.getWidth(),
        		getVisibleHeightEnitelyOpen()- jlbl_background_settings_1.getY());
        jlbl_background_settings_2.setLocation(jlbl_background_settings_1.getLocation());
        jlbl_background_settings_2.setSize(jlbl_background_settings_1.getSize());
//        jlbl_stroke.setSize(jpnl_background.getWidth(), 
//        		(int) Toolkit.getDefaultToolkit().getScreenSize().getHeight());
        
//        Util.getStroke(jlbl_stroke, jlbl_stroke.getX() + super.getX(), 
//        		jlbl_stroke.getY() + super.getY());
	        
	        //because the border should be visible 
            jpnl_contains.setLocation(0, 0);
            jlbl_close.setSize(getWidth(),
                    ViewSettings.getView_heightTB_opener());
            jpnl_close.setBounds(0, visibleHeight, getWidth(), 
                    ViewSettings.getView_heightTB_opener());
            

            //set size and location of headlines and tabs.
	        for (int index = 0; jbtn_stuffHeadline != null 
	                && index < jbtn_stuffHeadline.length; index++) {
	            
	            //set sizes. if highlighted, the headline is greater than
	            //usually.
	            if (index == openTab) {

	                jbtn_stuffHeadline[index].setSize(
	                        selectedtitleWidth, titleHeight);
	            } else {

	                jbtn_stuffHeadline[index].setSize(titleWidth, titleHeight);
	            }

	            jpnl_stuff[index].setLocation(0, titleHeight + titleY);
	            
                jpnl_stuff[index].setSize(
                        getWidth(), getHeight() - getHeight() / ViewSettings
                                .TABBED_PANE_TITLE_PROPORTION_HEIGHT - titleY);
                
	            jpnl_stuff_layer_2[index].setLocation(0,  visibleHeight);
	            
                
                jpnl_stuff_layer_2[index].setSize(getWidth(), 
                		getVisibleHeightEnitelyOpen() - 
                		jpnl_stuff_layer_2[index].getY());

                System.out.println(jpnl_stuff_layer_2[index].getBounds());
                //set locations depending on previous locations.
                //if index == 0 there is no previous location, it has to be
                //addressed in particular
                if (index == 0) {
                   jbtn_stuffHeadline[0].setLocation(titleX, titleY); 
                } else {
                    jbtn_stuffHeadline[index].setLocation(
                            jbtn_stuffHeadline[index - 1].getX() 
                            + jbtn_stuffHeadline[index - 1].getWidth(),
                            titleY);
                }
	        }

	}


    /**
     * set size and location of contents depending on the current
     * rotation given in parameter.
     * 
     */
    public final void flipSons() {

    	
    	flip();
            

            //set size and location of headlines and tabs.
	        for (int index = 0; jbtn_stuffHeadline != null 
	                && index < jbtn_stuffHeadline.length; index++) {
	            
	        	for (Component k : jpnl_stuff[index].getComponents()) {
	        		if (k instanceof Tab) {
	        			((Tab) k).setSize(k.getWidth(), k.getHeight());
	        		}
	        		
	        	}
	        }

	}
    
    
    


    /**
     * @return the openTab
     */
    public final int getOpenTab() {
        return openTab;
    }
    
    

	public int getVisibleHeightEnitelyOpen() {
		return visibleHeight * 3;
	}
	

	public int getHeightEnitelyOpen() {
		return oldHeight * 3;
	}


	/**
	 * @return the tabbedPaneOpenState
	 */
	public int getTabbedPaneOpenState() {
		return tabbedPaneOpenState;
	}


}
