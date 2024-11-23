package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 *
 * Colors field accordingly to its database-related state
 *      <ul>
 *      <li>red: field modified but not commited</li>
 *      <li>green: field modified and commited</li>
 *      <li>unmodified: field not modified</li>
 *      </ul>
 * <p>
 * enterKeyAction is a callback that will be called with
 *         the field's content when the user presses Enter
 * <p>
 *         ! The field's content is trimmed before being passed
 *         to enterKeyAction
 *
 */
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
            if (this.enterKeyAction.execute(this.field.getText().trim())) {
                this.field.setBackground(Color.green);
            }
        } else {
            super.keyTyped(e);
            this.field.setBackground(Color.red);
        }
    }
}
