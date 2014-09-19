//package declaration
package model.objects.pen.special;

//import declarations
import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;

import settings.Constants;
import settings.ViewSettings;
import model.objects.pen.Pen;


/**
 * .
 * @author Julius Huelsmann
 * @version %I%, %U%
 */
@SuppressWarnings("serial")
public class PenSelection extends Pen {

    /**
     * values for moving the border.
     */
    private int currentBorderValue = 0, 
            currentBorderStart = 0;

    /**
     * The last printed point.
     */
    private Point pnt_lastPrinted;

    /**
     * Constructor calls super constructor.
     */
	public PenSelection() {
		super(Constants.PEN_ID_POINT, 1, Color.black);
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override protected final void paintPoint(final Point _p, 
	        final BufferedImage _bi, final boolean _final, 
	        final Point _pnt_shift, final BufferedImage _g) {

	    //first printed point
	    if (pnt_lastPrinted == null) {

	        
	        _bi.setRGB(_p.x, _p.y, 
	                ViewSettings.SELECTION_BORDER_CLR_BORDER[0].getRGB()); 
	        pnt_lastPrinted = _p;

	    } else {
	        
	        int distanceX =  Math.abs(_p.x - pnt_lastPrinted.x);
	        int distanceY = Math.abs(_p.y - pnt_lastPrinted.y);
	        
	        int distance = Math.max(distanceX, distanceY);
	        
	        if (distance >= ViewSettings.SELECTION_BORDER_BLOCK_SIZE) {


	            //generate vector from pnt_lastPrinted towards the new one
	            //and norm it afterwards to the length of the maximal selection
	            //border size
	            Point vector = new Point(_p.x - pnt_lastPrinted.x, 
	                    _p.y - pnt_lastPrinted.y);
	            double length = Math.sqrt(
	                    Math.pow(vector.x, 2) + Math.pow(vector.y, 2));
	            vector.x = (int) (vector.x 
	                    * ViewSettings.SELECTION_BORDER_BLOCK_SIZE / length);
	            vector.y = (int) (vector.y
                        * ViewSettings.SELECTION_BORDER_BLOCK_SIZE / length);
	            
	            
	            for (int i = 1; 
	                    i <= ViewSettings.SELECTION_BORDER_BLOCK_SIZE; i++) {

	                //calculate the color
	                int s = (currentBorderValue 
	                        / ViewSettings.SELECTION_BORDER_BLOCK_SIZE)
	                        % ViewSettings.SELECTION_BORDER_CLR_BORDER.length;
	                
	                //if in range paint BufferedImage
	                int x = pnt_lastPrinted.x + vector.x 
                            * i / ViewSettings.SELECTION_BORDER_BLOCK_SIZE;
	                int y = pnt_lastPrinted.y + vector.y 
                            * i / ViewSettings.SELECTION_BORDER_BLOCK_SIZE;
	                
	                if (x >= _bi.getWidth()) {
	                    x = _bi.getWidth() - 1;
	                }
                    if (x < 0) {
                        x = 0;
                    } 
	                
	                if (y >= _bi.getHeight()) {
                        y = _bi.getHeight() - 1;
                    }
	                
	                if (y < 0) {
	                    y = 0;
	                }
	                
	                _bi.setRGB(x, y, ViewSettings.SELECTION_BORDER_CLR_BORDER[s]
	                        .getRGB()); 
	                currentBorderValue++;
	            }
	            
	            pnt_lastPrinted.x = pnt_lastPrinted.x + vector.x;
	            pnt_lastPrinted.y = pnt_lastPrinted.y + vector.y;
	            
	            
	            paintPoint(_p, _bi, _final, _pnt_shift, _g);
	        }
	    }

	}
	
	
	/**
	 * reset the current border value.
	 */
	public final void resetCurrentBorderValue() {
	    this.currentBorderStart += 1;
//	    ViewSettings.SELECTION_BORDER_MOVE_SPEED_PX;
	    this.currentBorderValue = currentBorderStart;
	}


}
