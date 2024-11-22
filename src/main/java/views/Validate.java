package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Validate extends KeyAdapter {
    /*

        Colors <field> accordingly to its database-related state
            red: field modified but not commited
            green: field modified and commited
            unmodified: field not modified

        <enterKeyAction> is a callback that will be called with
        the field's content when the user presses Enter

        ! The field's content is trimmed before being passed
        to <enterKeyAction>

     */
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
            if (this.enterKeyAction.execute(this.field.getText().trim())) {
                this.field.setBackground(Color.green);
            }
        } else {
            super.keyTyped(e);
            this.field.setBackground(Color.red);
        }
    }
}
