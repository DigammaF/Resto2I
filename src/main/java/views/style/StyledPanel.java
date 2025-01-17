package views.style;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class StyledPanel extends JPanel {


    private final int paddingSize;

    public StyledPanel(int paddingSize) {
        super();
        this.paddingSize = paddingSize;
        this.initStyle();
    }

    protected void initStyle(){
        this.setBackground(Colors.MAIN_BACKGROUND_COLOR);
        this.setBorder(new EmptyBorder(this.paddingSize, this.paddingSize, this.paddingSize, this.paddingSize));
    }
}
