package views;

import language.TextContent;
import logic.Logic;
import models.MenuItem;

import javax.swing.*;
import java.awt.event.ActionListener;

public class MenuItemDisplay extends JPanel {
    private MenuItem menuItem;
    private JLabel nameLabel;
    private JTextField nameTextField;
    private JLabel tagsLabel;
    private JTextField tagsTextField;
    private JButton removeButton;

    public MenuItemDisplay(MenuItem menuItem) {
        super();
        this.menuItem = menuItem;
        this.initComponents();
        this.initLayout();
    }

    private void initComponents() {
        AppContext context = AppContext.getAppContext();
        TextContent textContent = TextContent.getTextContent();
        this.nameLabel = new JLabel(textContent.get(context.getLanguage(), TextContent.Key.NAME));
        this.nameTextField = new JTextField(this.menuItem.getName());
        this.nameTextField.addKeyListener(new Validate(
                this.nameTextField,
                text -> context.perform(_ -> this.menuItem.setName(text))
        ));
        this.tagsLabel = new JLabel(textContent.get(context.getLanguage(), TextContent.Key.TAGS));
        this.tagsTextField = new JTextField(this.menuItem.getAllowedTags());
        this.tagsTextField.addKeyListener(new Validate(
                this.tagsTextField,
                text -> context.perform(_ -> this.menuItem.setAllowedTags(text))
        ));
        this.removeButton = new JButton("X");
        this.removeButton.addActionListener(_ -> {
            if (context.perform(_ -> {
                Logic.remMenuitem(this.menuItem.getMenu(), this.menuItem);
            })) {
                this.getParent().remove(this);
                context.getMainView().validate();
                context.getMainView().repaint();
            } else {
                context.getMainView().println(textContent.get(context.getLanguage(), TextContent.Key.CANNOT_WRITE_DATABASE));
            }
        });
    }

    private void initLayout() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(this.nameLabel);
        this.add(this.nameTextField);
        this.add(this.tagsLabel);
        this.add(this.tagsTextField);
        this.add(this.removeButton);
    }
}
