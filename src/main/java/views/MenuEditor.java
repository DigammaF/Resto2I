package views;

import language.TextContent;
import models.Menu;
import models.MenuItem;

import javax.swing.*;

public class MenuEditor extends JPanel {
    private Menu menu;
    private JButton availableButton;
    private JLabel nameLabel;
    private JTextField nameTextField;
    private JLabel costLabel;
    private JTextField costTextField;
    private JButton newItemButton;
    private JPanel itemsPanel;

    public MenuEditor(Menu menu) {
        super();
        this.menu = menu;
        this.initComponents();
        this.initLayout();
    }

    private void initComponents() {
        AppContext context = AppContext.getAppContext();
        TextContent textContent = TextContent.getTextContent();
        this.availableButton = new JButton(this.getAvailableButtonText());
        this.nameLabel = new JLabel(textContent.get(context.getLanguage(), TextContent.Key.NAME));
        this.nameTextField = new JTextField(this.menu.getName());
        this.nameTextField.addKeyListener(new Validate(
                this.nameTextField,
                text -> context.perform(_ -> this.menu.setName(text))
        ));
        this.costLabel = new JLabel(textContent.get(context.getLanguage(), TextContent.Key.COST));
        this.costTextField = new JTextField(String.valueOf(this.menu.getCost()));
        this.costTextField.addKeyListener(new Validate(
                this.costTextField,
                text -> context.perform(_ -> this.menu.setCost(Integer.parseInt(text)))
        ));
        this.newItemButton = new JButton("+");
        this.newItemButton.addActionListener(_ -> {
            if (!context.perform(_ -> {
                context.getRestaurant().createMenuItem(this.menu).ifPresentOrElse(
                        (menuItem -> {
                            MenuItemDisplay menuItemDisplay = new MenuItemDisplay(menuItem);
                            this.itemsPanel.add(menuItemDisplay);
                            context.getMainView().validate();
                            context.getMainView().repaint();
                        }),
                        () -> {
                            context.getMainView().println(textContent.get(context.getLanguage(), TextContent.Key.CANNOT_CREATE_MENU_ITEM));
                        }
                );
            })) {
                context.getMainView().println(textContent.get(context.getLanguage(), TextContent.Key.CANNOT_WRITE_DATABASE));
            }
        });
        this.itemsPanel = new JPanel();
    }

    private void initLayout() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel namePanel = new JPanel();
        namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.X_AXIS));
        namePanel.add(this.nameLabel);
        namePanel.add(this.nameTextField);
        this.add(namePanel);

        JPanel costPanel = new JPanel();
        costPanel.setLayout(new BoxLayout(costPanel, BoxLayout.X_AXIS));
        costPanel.add(this.costLabel);
        costPanel.add(this.costTextField);
        this.add(costPanel);

        this.add(this.newItemButton);
        this.add(this.itemsPanel);
    }

    private String getAvailableButtonText() {
        AppContext context = AppContext.getAppContext();
        TextContent textContent = TextContent.getTextContent();
        return
                textContent.get(context.getLanguage(), TextContent.Key.AVAILABLE)
                + ": " + textContent.get(context.getLanguage(), this.menu.isAvailable() ? TextContent.Key.YES : TextContent.Key.NO)
        ;
    }
}
