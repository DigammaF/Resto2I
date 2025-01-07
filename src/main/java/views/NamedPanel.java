package views;

import javax.swing.*;

public class NamedPanel extends JPanel {
    private final JLabel nameLabel;
    private final JPanel panel;

    public NamedPanel(String name, JPanel panel) {
        super();
        this.nameLabel = new JLabel(name);
        this.panel = panel;
        this.initLayout();
    }

    private void initLayout() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(nameLabel);
        this.add(panel);
    }


}
