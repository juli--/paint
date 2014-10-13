package control.tabs;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.forms.Page;
import view.forms.tabs.Look;
import view.forms.tabs.Paint;
import view.forms.tabs.Write;
import model.objects.pen.Pen;
import model.objects.pen.normal.BallPen;
import model.objects.pen.normal.Pencil;
import model.settings.Constants;
import model.settings.Status;



/**
 * 
 * @author Julius Huelsmann
 * @version %I%, %U%
 */
public final class CWrite implements ActionListener {

    /**
     * Empty utility class constructor.
     */
    private CWrite() { }

    
    private final static Color 
    clr_green = new Color(2 * 2 * 2 * 2 * 2 * 2, 
            (int) (Math.pow(2, 2 + 2 + 2 + 1) 
                    + Math.pow(2, 2 + 2 + 2) + 2 + 2 + 1), 
            (2 + 1) * (2 + 1) * (2 + 1) + (2 + 2 + 2 + 1) 
            * (2 + 2 + 2 + 2 + 2)),
            clr_pink = new Color(255, 153, 254),
            clr_blue = new Color(153, 162, 255),
            clr_darkBlue = new Color(112, 146, 190);
    
    /**
     * 
     */
    public final static Pen 
    penWrite1 = new Pencil(Constants.PEN_ID_LINES, 2, Color.gray),
    penWrite2 = new BallPen(Constants.PEN_ID_LINES, 2, clr_green),
    penProof1 = new Pencil(Constants.PEN_ID_LINES, 2, clr_blue), 
    penProof2 = new BallPen(Constants.PEN_ID_LINES, 2, Color.gray), 
    penExmpl1 = new Pencil(Constants.PEN_ID_LINES, 2, clr_pink), 
    penExmpl2 = new BallPen(Constants.PEN_ID_LINES, 2, clr_darkBlue),
    penCmmnt1 = new Pencil(Constants.PEN_ID_LINES, 2, Color.gray),
    penCmmnt2 = new BallPen(Constants.PEN_ID_LINES, 2, clr_pink);
    
    
    
    /**
     * The only instance of this class.
     */
    private static CWrite instance;
    
    
    @Override public void actionPerformed(final ActionEvent _event) {

        if (_event.getSource().equals(Write.getInstance()
                .getTb_beispiel().getActionCause())) {
            deactivate();
            Paint.getInstance().getJbtn_color1().setBackground(
                    penExmpl1.getClr_foreground());
            Paint.getInstance().getJbtn_color2().setBackground(
                    penExmpl2.getClr_foreground());
            Status.setPenSelected1(Pen.clonePen(penExmpl1));
            Status.setPenSelected2(Pen.clonePen(penExmpl2));
            
            System.out.println(penExmpl1.getClr_foreground());
            System.out.println(Status.getPenSelected1().getClr_foreground());

        } else if (_event.getSource().equals(Write.getInstance()
                .getTb_bemerkung().getActionCause())) {
            deactivate();
            Paint.getInstance().getJbtn_color1().setBackground(
                    penCmmnt1.getClr_foreground());
            Paint.getInstance().getJbtn_color2().setBackground(
                    penCmmnt2.getClr_foreground());
            Status.setPenSelected1(Pen.clonePen(penCmmnt1));
            Status.setPenSelected2(Pen.clonePen(penCmmnt2));

        } else if (_event.getSource().equals(Write.getInstance()
                .getTb_beweis().getActionCause())) {
            deactivate();
            Paint.getInstance().getJbtn_color1().setBackground(
                    penProof1.getClr_foreground());
            Paint.getInstance().getJbtn_color2().setBackground(
                    penProof2.getClr_foreground());
            Status.setPenSelected1(Pen.clonePen(penProof1));
            Status.setPenSelected2(Pen.clonePen(penProof2));
        } else if (_event.getSource().equals(Write.getInstance()
                .getTb_headline1().getActionCause())) {
                
        } else if (_event.getSource().equals(Write.getInstance()
                .getTb_headline2().getActionCause())) {
                
        } else if (_event.getSource().equals(Write.getInstance()
                .getTb_headline3().getActionCause())) {
                
        } else if (_event.getSource().equals(Write.getInstance()
                .getTb_satz().getActionCause())) {
            deactivate();
            Paint.getInstance().getJbtn_color1().setBackground(
                    penWrite1.getClr_foreground());
            Paint.getInstance().getJbtn_color2().setBackground(
                    penWrite2.getClr_foreground());
            Status.setPenSelected1(Pen.clonePen(penWrite1));
            Status.setPenSelected2(Pen.clonePen(penWrite2));
            //TODO: update paint gui.
        }

        Status.setIndexOperation(Constants.CONTROL_PAINTING_INDEX_PAINT_1);
        CPaintStatus.getInstance().deactivate();
        Paint.getInstance().getIt_stift1().getTb_open().setActivated(true);
        Paint.getInstance().getTb_color1().setActivated(true);
    }
    
    
    public void deactivate() {
        Write.getInstance().getTb_beispiel().setActivated(false);
        Write.getInstance().getTb_bemerkung().setActivated(false);
        Write.getInstance().getTb_beweis().setActivated(false);
        Write.getInstance().getTb_satz().setActivated(false);
    }
    
    
    /**
     * Return the only instance of this class.
     * @return the only instance of this class.
     */
    public static CWrite getInstance() {
        if (instance == null) {
            instance = new CWrite();
        }
        return instance;
    }

}
