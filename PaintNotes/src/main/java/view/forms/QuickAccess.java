package view.forms;

import java.awt.Color;
import java.awt.image.BufferedImage;

import javax.management.MXBean;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import model.settings.ViewSettings;
import control.util.MousePositionTracker;
import view.util.mega.MLabel;
import view.util.mega.MPanel;

public class QuickAccess extends MPanel {

	private MLabel jlbl_background;
	
	private static QuickAccess instance;
	public QuickAccess() {
		super();
		super.setLayout(null);
		super.setOpaque(false);
		MousePositionTracker mpt = new MousePositionTracker(this);
		super.addMouseListener(mpt);
		super.addMouseMotionListener(mpt);
		super.setSize(200, 200);
		
		jlbl_background = new MLabel();
		jlbl_background.setSize(getSize());
		jlbl_background.setOpaque(false);;
		super.add(jlbl_background);
		
		drawCircle(jlbl_background);
		
	}
	
	
	
	public static final QuickAccess getInstance() {
		if (instance == null){
			instance = new QuickAccess();
		}
		return instance;
	}
	private static final void drawCircle(JLabel _jlbl) {
		BufferedImage bi = new BufferedImage(
				_jlbl.getWidth(), 
				_jlbl.getHeight(), 
				BufferedImage.TYPE_INT_ARGB);
	
		for (int h = 0; h < bi.getWidth(); h ++) {
			double y = 1.0 *  (bi.getHeight() / 2 - h);
			for (int w = 0; w < bi.getWidth(); w ++) {
				double x = 1.0 *  (bi.getWidth() / 2 - w);

				double rad2 = bi.getWidth() / 2 - 20;
				double rad3 = bi.getWidth() / 2 - 30;
				
				if ((x * x - bi.getWidth()  *  bi.getWidth() / 4) 
						<= - (y * y)) {
					

					if ((x * x - rad2 * rad2) >= - (y * y)) {
						final int strokeDistance = 10;
	            		
						if (w == h || w == bi.getWidth()-h) {

		                	bi.setRGB(w, h, Color.gray.getRGB());
						} else if ( (w + h) 
		            			% strokeDistance == 0
		            			||  (w - h) % strokeDistance == 0) {

		                	bi.setRGB(w, h, new Color(220,230,250).getRGB());
		            	} else {

		                	bi.setRGB(w, h, ViewSettings.GENERAL_CLR_BACKGROUND.getRGB());
		            	}
						
//	                	bi.setRGB(w, h, ViewSettings.GENERAL_CLR_BACKGROUND.getRGB());
					} else if ((x * x - rad3 * rad3) <= - (y * y)) {

					final int strokeDistance = 10;

					if (w == h || w == bi.getWidth()-h) {

	                	bi.setRGB(w, h, Color.gray.getRGB());
					} else if ( (w + h) 
		            			% strokeDistance == 0
		            			||  (w - h) % strokeDistance == 0) {

		                	bi.setRGB(w, h, new Color(220,230,250).getRGB());
		            	} else {

		                	bi.setRGB(w, h, ViewSettings.GENERAL_CLR_BACKGROUND_DARK.getRGB());
		            	}
					}
//			}
	            		
	            		
	            		
	            		
//	          for (int iX = 0; iX < bi.getWidth(); iX ++) {
//	        	  int nix = Math.abs(iX - bi.getWidth());
//	        	  int iy = nix * nix - bi.getWidth() * bi.getWidth() / 4;
//
//              	bi.setRGB(iX,(int) Math.sqrt(Math.abs(iy)) , Color.black.getRGB());
//	          }
//			

	                	
		}else {

			bi.setRGB(w, h, new Color(0, 0, 0, 0).getRGB());
		}
			}}
		
		


		for (double Xh = 0; Xh < bi.getWidth(); Xh += 0.01) {

			double myY = 1.0 *  (bi.getHeight() / 2 - Xh);
//			double myXold = 1.0 *  (bi.getWidth() / 2 - w);
//			(bi.getWidth() / 2 - w) = sqrt(y² - (bi.getwi / 2)²)
//			w =  sqrt(y² - (bi.getwi / 2)²) - bi.getwi / 2
			double myX1 = Math.sqrt(Math.abs(myY * myY - Math.pow(bi.getWidth() / 2, 2) - bi.getWidth() / 2));

			myX1 += bi.getHeight() / 2;
			myY += bi.getHeight() / 2;
				//x² + y² <= r²

			myX1 -= 1;
			while (myY >= bi.getHeight()){
				myY --;
			}
			while (myY < 0){
				myY ++;
			}
			if (myX1 >= 0 && myY >= 0 && myX1 < bi.getWidth() && myY < bi.getHeight())
          	bi.setRGB((int) myX1, (int) myY, ViewSettings.GENERAL_CLR_BORDER.getRGB());
			
			myX1 +=1;
			int myX2 = -(int) (myX1 -bi.getHeight());

			if (myX2 >= 0 && myY >= 0 && myX2 < bi.getWidth() && myY < bi.getHeight())
          	bi.setRGB((int) myX2, (int) myY, ViewSettings.GENERAL_CLR_BORDER.getRGB());
		}
		
		
		
		
		
		

		for (double Xh = 0; Xh < bi.getWidth(); Xh += 0.1) {

			double rad2 = bi.getWidth() / 2 - 20;
			double myY = 1.0 *  (bi.getHeight() / 2 - Xh);
			double myX1 = Math.sqrt(Math.abs(myY * myY - Math.pow(rad2, 2) - bi.getWidth() / 2));

			myX1 += bi.getHeight() / 2;
			myY += bi.getHeight() / 2;
				//x² + y² <= r²

			myX1 -= 1;
			if (myX1 >= 0 && myY >= 0 && myX1 < bi.getWidth() && myY < bi.getHeight()) {

				if (myY > 20 && myY < bi.getHeight() - 20){

		          	bi.setRGB((int) myX1, (int) myY, ViewSettings.GENERAL_CLR_BORDER.getRGB());
				}
			}
			
			myX1 +=1;
			int myX2 = -(int) (myX1 -bi.getHeight());

			if (myX2 >= 0 && myY >= 0 && myX2 < bi.getWidth() && myY < bi.getHeight()) {

				if (myY > 20 && myY < bi.getHeight() - 20){

		          	bi.setRGB((int) myX2, (int) myY, ViewSettings.GENERAL_CLR_BORDER.getRGB());
				}
			}
		}
		
		

		for (double Xh = 0; Xh < bi.getWidth(); Xh += 0.1) {

			double rad3 = bi.getWidth() / 2 - 30;
			double myY = 1.0 *  (bi.getHeight() / 2 - Xh);
			double myX1 = Math.sqrt(Math.abs(myY * myY - Math.pow(rad3, 2) - bi.getWidth() / 2));

			myX1 += bi.getHeight() / 2;
			myY += bi.getHeight() / 2;
				//x² + y² <= r²

			myX1 -= 1;
			if (myX1 >= 0 && myY >= 0 && myX1 < bi.getWidth() && myY < bi.getHeight()) {

				if (myY < 30 || myY > bi.getHeight() - 30){
					
				} else {

	    				bi.setRGB((int) myX1, (int) myY, ViewSettings.GENERAL_CLR_BORDER.getRGB());
					}
			}
			
			myX1 +=1;
			int myX2 = -(int) (myX1 -bi.getHeight());

			if (myX2 >= 0 && myY >= 0 && myX2 < bi.getWidth() && myY < bi.getHeight())
			{
				if (myY < 30 || myY > bi.getHeight() - 30){
					
				} else

	          	bi.setRGB((int) myX2, (int) myY, ViewSettings.GENERAL_CLR_BORDER.getRGB());
			}
		}
		
		
		
		_jlbl.setIcon(new ImageIcon(bi));
	}
}
