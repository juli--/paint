package view.util;

//import java.awt components
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;




//import rotatatble buttons and panels
import view.util.mega.MButton;
import control.interfaces.ActivityListener;

//import the controller showing mouseOver animations 
import control.util.CItem2;

import model.settings.Constants;
//import utility class for image resizing.
import model.util.paint.Utils;

/**
 * MButton for inserting a form into image.
 * 
 * @author Julius Huelsmann
 * @version %I%, %U%
 */
@SuppressWarnings("serial")
public class Item2 extends MButton {

    /**
     * The icon.
     */
    private String icon = Constants.VIEW_PATH_I2_STANDARD, title;
    
    

	
	/**
	 * Set ActivityListener for the Scroll-Pane view class. If set twice, the
	 * secondly added ActivityListner is used and the first one is forgotten.
	 * If the activityListener is to be removed, call setActivityListener(null).
	 * 
	 * @param _activityListener	The activityListener which is added
	 */
    public final void setItemActivityListener(
    		final ActivityListener _activityListener) {
    	CItem2.getInstance().addItemActivityListener(_activityListener);
    }
    /**
     * form for items that display a form which can be inserted into image.
     */
	public Item2() {
		super();
		super.setOpaque(false);
		super.setContentAreaFilled(false);
		super.addMouseListener(CItem2.getInstance());
		super.setFocusable(false);
		super.setBorder(BorderFactory.createLineBorder(Color.lightGray));
		super.setIcon(new ImageIcon(icon));
	}
	
    /**
     * Set the icon of the MButton.
     * @param _s the path
     */
	public final void setIcon(final String _s) {

	    this.icon = _s;
        super.setIcon(new ImageIcon(Utils.resizeImage(
                getWidth(), getHeight(), _s)));
	}
	
	
	/**
	 * Return the icon path.
	 * @return the icon path.
	 */
	public final String getIconPath() {
	    return icon;
	}

    /**
     * @return the title
     */
    public final String getTitle() {
        return title;
    }

    /**
     * @param _title the title to set
     */
    public final void setTitle(final String _title) {
        this.title = _title;
    }
	
}
