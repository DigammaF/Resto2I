package views.style;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class StyledButton extends JButton {
    public static int DEFAULT_MARGIN_SIZE = 15;

    private int marginSize;

    public StyledButton(String text, int marginSize){
        super(text);
        this.marginSize = marginSize;
        this.initStyle();
    }

    public StyledButton(int marginSize){
        super();
        this.marginSize = marginSize;
        this.initStyle();
    }

    protected void initStyle(){
        this.setBorder(new EmptyBorder(this.marginSize, this.marginSize, this.marginSize, this.marginSize));
    }
}
