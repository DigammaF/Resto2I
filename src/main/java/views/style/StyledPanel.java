package views.style;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class StyledPanel extends JPanel {
    public static final int DEFAULT_PADDING_SIZE = 15;//15;
    public static int WIDE_PADDING_SIZE = 30;
    public static int NARROW_PADDING_SIZE = 5;

    private final int paddingSize;

    public StyledPanel(int paddingSize) {
        super();
        this.paddingSize = paddingSize;
        this.initStyle();
    }

    protected void initStyle(){
        this.setBackground(Color.pink); //test, todo del
        this.setBorder(new EmptyBorder(this.paddingSize, this.paddingSize, this.paddingSize, this.paddingSize));
    }
}
