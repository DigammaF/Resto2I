package views;

import views.style.Colors;

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
    private final Action enterKeyAction;
    private Process postProcess;

    @FunctionalInterface
    public interface Action {
        boolean execute(String text);
    }

    @FunctionalInterface
    public interface Process {
        void process(String text);
    }

    public Validate(JTextField field, Action enterKeyAction) {
        this.field = field;
        this.enterKeyAction = enterKeyAction;
        this.postProcess = _ -> { };
    }

    public Validate withPostAction(final Process postProcess) {
        this.postProcess = postProcess;
        return this;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getKeyChar() == '\n') {
            if (this.enterKeyAction.execute(this.field.getText().trim())) {
                this.field.setBackground(Colors.COMMITED_FIELD);
                this.postProcess.process(this.field.getText().trim());
            }
        } else {
            super.keyTyped(e);
            this.field.setBackground(Colors.MODIFIED_FIELD);
        }
    }
}
