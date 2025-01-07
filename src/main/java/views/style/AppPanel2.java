//ARCHIVE, UTILISER STYLEDPANELS ET SES ENFANTS

package views.style;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Panels inside an editor (eg user inputs fields) will inherit from this class.
 */
public class AppPanel2 extends JPanel {
    private static final int PADDING_SIZE=50;//15;

    public AppPanel2(){
        super();
        this.initStyle();

    }

    protected void initStyle(){
        this.setBackground(Color.red);
        this.setBorder(new EmptyBorder(PADDING_SIZE, PADDING_SIZE, PADDING_SIZE, PADDING_SIZE));
    }
}
