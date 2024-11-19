package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Validate extends KeyAdapter {
    private final JTextField field;
    private final EnterKeyAction enterKeyAction;

    @FunctionalInterface
    public interface EnterKeyAction {
        boolean execute(String text);
    }

    public Validate(JTextField field, EnterKeyAction enterKeyAction) {
        this.field = field;
        this.enterKeyAction = enterKeyAction;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getKeyChar() == '\n') {
            if (this.enterKeyAction.execute(this.field.getText())) {
                this.field.setBackground(Color.green);
            }
        } else {
            super.keyTyped(e);
            this.field.setBackground(Color.red);
        }
    }
}
