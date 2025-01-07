//ARCHIVE, UTILISER STYLEDPANELS ET SES ENFANTS

package views.style;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;


//TODO voir si possible de fédérer AppPanel1 et AppPanel2 sous une seule classe paramétrée, eg avec un design pattern ?

/**
 * Panels directly inside the MainView will inherit from this class.
 */
public class AppPanel1 extends JPanel {
    private static final int PADDING_SIZE=50;

    public AppPanel1(){
        super();
        this.initStyle();

     }

    protected void initStyle(){
        this.setBackground(Color.red);
        this.setBorder(new EmptyBorder(PADDING_SIZE, PADDING_SIZE, PADDING_SIZE, PADDING_SIZE));
    }
}
