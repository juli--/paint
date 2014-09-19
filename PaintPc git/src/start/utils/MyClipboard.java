package start.utils;


/**
 *
 * @author Jigar
 */
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import model.objects.painting.PaintObjectWriting;
import settings.Status;


/**
 * Class for clipboard transfer.
 * 
 * @author Julius Huelsmann
 * @version %I%, %U%
 */
public final class MyClipboard implements ClipboardOwner {
    
    
    /**
     * Just for testing purpose.
     * @param _args the args.
     */
    public static void main(final String[]_args) {
        BufferedImage bi = (BufferedImage) getInstance().paste();

        if (!Status.isDebug()) {

            JFrame jf = new JFrame();
            jf.setSize(bi.getWidth(), bi.getHeight());
            jf.setLayout(null);
            jf.setLocationRelativeTo(null);
            jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
            
            JLabel jlbl = new JLabel();
            jlbl.setSize(jf.getSize());
            jlbl.setIcon(new ImageIcon(bi));
            jf.add(jlbl);
           
            jf.setVisible(true);
            jf.setResizable(false);
        }
    }
    
    /**
     * The only instance of this class.
     */
    private static MyClipboard instance;
    
    
    /**
     * contains whether the clipboard is owned or not.
     */
    private boolean own_clipboard;
    
    
    /**
     * the paintObjectWriting which can be saved / loaded.
     */
    private PaintObjectWriting pw;
    
    /**
     * Empty utility class Constructor.
     */
    public MyClipboard() { }
    
    
    
    /**
     * CopyImage.
     * @param _i the image.
     */
    public void copyImage(final Image _i) {

        //copy to clipboard
        TransferableImage trans = new TransferableImage(_i);
        Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
        c.setContents(trans, this);
        
        //set that i own the clipboard
        this.own_clipboard = true;
        
        //reset the last PaintObjectWriting if just an image is copied
        //in case of Writing copied, the pw is set after exporting Image;
        //thus, this command does not hinder that process.
        this.pw = null;
    }

    
    /**
     * copy po writing.
     * @param _pw the poWriting
     * @param _i its image.
     */
    public void copyPO_writing(final PaintObjectWriting _pw, final Image _i) {

        //copy image
        copyImage(_i);
        //save the paintObjectWriting
        this.pw = _pw;
        
    }
    
    
    /**
     * Paste.
     * @return the pasted image (if exisiting).
     */
    public Object paste() {

        //save the clipboard content.
        Object o = null;
        try {
            o = Toolkit.getDefaultToolkit()
                    .getSystemClipboard().getData(DataFlavor.imageFlavor);
        } catch (HeadlessException e) {
            e.printStackTrace();
        } catch (UnsupportedFlavorException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        if (own_clipboard) {
            
            return pw;
        }
        if (o instanceof BufferedImage) {
            System.out.println("bi! owned: " + own_clipboard);
            
            
        } else if (o instanceof String) {
            System.out.println("string! owned:" + own_clipboard);
        } else {
            System.out.println("nothing on clipboard! owned: " + own_clipboard);
        }

        return o;
    }


    /**
     * this method guarantees that only one instance of this
     * class can be created ad runtime.
     * 
     * @return the only instance of this class.
     */
    public static MyClipboard getInstance() {
        
        //if class is not instanced yet instantiate
        if (instance == null) {
            instance = new MyClipboard();
        }
        
        //return the only instance of this class.
        return instance;
    }
    
    /**
     *  {@inheritDoc}
     */
    @Override public void lostOwnership(final Clipboard _clip, 
            final Transferable _trans) {
        
        //i lost ownership of the clipboard; that means that another 
        //application has copied something to clipboard.
        this.own_clipboard = false;
    }

    
    /**
     * The item that is copied to clipboard.
     * @author Julius Huelsmann
     * @version %I%, %U%
     *
     */
    private class TransferableImage implements Transferable {

        
        /**
         * The image that is to be transmitted.
         */
        private Image i;

        
        /**
         * Constructor: saves image.
         * @param _i the iamge.
         */
        public TransferableImage(final Image _i) {
            this.i = _i;
        }


        /**
         * 
         * get tranfer data.
         * @param _flavor the DataFlavor.
         * @return the transfer data.
         * 
         * @throws UnsupportedFlavorException uex
         * @throws IOException ioex
         * 
         */
        public Object getTransferData(final DataFlavor _flavor)
        throws UnsupportedFlavorException, IOException {
            if (_flavor.equals(DataFlavor.imageFlavor) && i != null) {
                return i;
            } else {
                throw new UnsupportedFlavorException(_flavor);
            }
        }

        /**
         * get tranfer data.
         * @return the DataFlavor
         */
        public DataFlavor[] getTransferDataFlavors() {
            DataFlavor[] flavors = new DataFlavor[ 1 ];
            flavors[ 0 ] = DataFlavor.imageFlavor;
            return flavors;
        }

        
        /**
         * is data supported.
         * @param _flavor the flavor
         * @return whether data flav supported.
         */
        public boolean isDataFlavorSupported(final DataFlavor _flavor) {
            DataFlavor[] flavors = getTransferDataFlavors();
            for (int j = 0; j < flavors.length; j++) {
                if (_flavor.equals(flavors[ j ])) {
                    return true;
                }
            }

            return false;
        }
    }
}
