package views;

import language.TextContent;
import models.Menu;
import views.style.FlatButton;

import javax.swing.*;

public class MenusEditor extends JPanel {
    private JButton newButton;
    private JPanel menusPanel;

    public MenusEditor() {
        super();
        this.initComponents();
        this.initLayout();
    }

    private void initComponents() {
        AppContext context = AppContext.getAppContext();
        TextContent textContent = TextContent.getTextContent();
        this.newButton = new FlatButton("+");
        this.newButton.addActionListener(_ -> {
            if (context.perform(_ -> {
                context.getRestaurant().createMenu().ifPresentOrElse(
                        (this::addMenu),
                        () -> {
                            context.getMainView().println(textContent.get(context.getLanguage(), TextContent.Key.CANNOT_CREATE_MENU));
                        }
                );
            })) {
                context.getMainView().validate();
                context.getMainView().repaint();
            } else {
                context.getMainView().println(textContent.get(context.getLanguage(), TextContent.Key.CANNOT_WRITE_DATABASE));
            }
        });
        this.menusPanel = new JPanel();

        for (Menu menu : context.getRestaurant().getMenus().stream().filter(Menu::isUsed).toList()) {
            this.addMenu(menu);
        }
    }

    private void initLayout() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(this.newButton);
        this.menusPanel.setLayout(new BoxLayout(this.menusPanel, BoxLayout.Y_AXIS));
        this.add(this.menusPanel);
    }

    private void addMenu(Menu menu) {
        AppContext context = AppContext.getAppContext();
        TextContent textContent = TextContent.getTextContent();
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.X_AXIS));
        menuPanel.add(new JLabel(menu.getName()));
        JButton editButton = new FlatButton(textContent.get(context.getLanguage(), TextContent.Key.EDIT));
        editButton.addActionListener(_ -> {
            JPanel mainPanel = context.getMainView().getMainPanel();
            mainPanel.removeAll();
            mainPanel.add(new MenuEditor(menu));
            mainPanel.validate();
            mainPanel.repaint();
        });
        menuPanel.add(editButton);
        JButton removeButton = new FlatButton("X");
        removeButton.addActionListener(_ -> {
            if (context.perform(_ -> {
                menu.setUsed(false);
            })) {
                this.menusPanel.remove(menuPanel);
                context.getMainView().validate();
                context.getMainView().repaint();
            } else {
                context.getMainView().println(textContent.get(context.getLanguage(), TextContent.Key.CANNOT_WRITE_DATABASE));
            }
        });
        this.menusPanel.add(menuPanel);
    }
}
