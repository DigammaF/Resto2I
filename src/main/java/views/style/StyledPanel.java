package views.style;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class StyledPanel extends JPanel {
    private int paddingSize;

    public StyledPanel(int paddingSize) {
        super();
        this.paddingSize = paddingSize;
        this.initStyle();
    }

    protected void initStyle(){
        this.setBorder(new EmptyBorder(this.paddingSize, this.paddingSize, this.paddingSize, this.paddingSize));
    }
}
