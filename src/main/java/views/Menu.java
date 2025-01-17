package views;

import language.TextContent;
import models.Ticket;
import views.style.Colors;
import views.style.DefaultPanel;
import views.style.FlatButton;
import views.style.Paddings;

import javax.swing.*;
import java.awt.*;

/**
 *
 * Sidebar menu
 *
 */
public class Menu extends DefaultPanel {
    private JButton productsEditorButton;
    private JButton menusEditorButton;
    private JButton ticketsEditorButton;
    private JButton ticketsArchiveButton;
    private JButton restaurantEditorButton;
    private JButton languageSwitchButton;

    public Menu() {
        super();
        this.initComponents();
        this.initTexts();
        this.initLayout();
        this.initStyle();
    }

    private void initComponents() {
        AppContext context = AppContext.getAppContext();
        this.productsEditorButton = new FlatButton("");
        this.productsEditorButton.addActionListener(_ -> {
            JPanel mainPanel = context.getMainView().getMainPanel();
            mainPanel.removeAll();
            mainPanel.add(new ProductsEditor());
            mainPanel.validate();
            mainPanel.repaint();
        });
        this.menusEditorButton = new FlatButton("");
        this.menusEditorButton.addActionListener(_ -> {
            JPanel mainPanel = context.getMainView().getMainPanel();
            mainPanel.removeAll();
            mainPanel.add(new MenusEditor());
            mainPanel.validate();
            mainPanel.repaint();
        });
        this.ticketsEditorButton = new FlatButton("");
        this.ticketsEditorButton.addActionListener(_ -> {
            JPanel mainPanel = context.getMainView().getMainPanel();
            mainPanel.removeAll();
            mainPanel.add(new TicketsEditor(true, ticket -> !ticket.isEmitted()));
            mainPanel.validate();
            mainPanel.repaint();
        });
        this.ticketsArchiveButton = new FlatButton("");
        this.ticketsArchiveButton.addActionListener(_ -> {
            JPanel mainPanel = context.getMainView().getMainPanel();
            mainPanel.removeAll();
            mainPanel.add(new TicketsEditor(false, Ticket::isEmitted));
            mainPanel.validate();
            mainPanel.repaint();
        });
        this.restaurantEditorButton = new FlatButton("");
        this.restaurantEditorButton.addActionListener(_ -> {
            JPanel mainPanel = context.getMainView().getMainPanel();
            mainPanel.removeAll();
            mainPanel.add(new RestaurantEditor(context.getRestaurant()));
            mainPanel.validate();
            mainPanel.repaint();
        });
        this.languageSwitchButton = new FlatButton("FR/EN");
        this.languageSwitchButton.addActionListener(_ -> {
            switch (context.getLanguage()) {
                case EN -> context.setLanguage(TextContent.Language.FR);
                case FR -> context.setLanguage(TextContent.Language.EN);
            }
            initTexts();
        });
    }

    private void initTexts() {
        AppContext context = AppContext.getAppContext();
        TextContent text = TextContent.getTextContent();
        this.productsEditorButton.setText(text.get(context.getLanguage(), TextContent.Key.MENU_PRODUCTS_EDITOR_BUTTON));
        this.menusEditorButton.setText(text.get(context.getLanguage(), TextContent.Key.MENU_MENUS_EDITOR_BUTTON));
        this.ticketsEditorButton.setText(text.get(context.getLanguage(), TextContent.Key.MENU_TICKETS_EDITOR_BUTTON));
        this.ticketsArchiveButton.setText(text.get(context.getLanguage(), TextContent.Key.MENU_TICKETS_ARCHIVE_BUTTON));
        this.restaurantEditorButton.setText(text.get(context.getLanguage(), TextContent.Key.MENU_RESTAURANT_EDITOR_BUTTON));
    }

    private void initLayout() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(this.productsEditorButton);
        int SPACING = Paddings.MENUSPACING;
        this.add(Box.createVerticalStrut(SPACING));

        this.add(this.menusEditorButton);
        this.add(Box.createVerticalStrut(SPACING));

        this.add(this.ticketsEditorButton);
        this.add(Box.createVerticalStrut(SPACING));

        this.add(this.ticketsArchiveButton);
        this.add(Box.createVerticalStrut(SPACING));

        this.add(this.restaurantEditorButton);
        this.add(Box.createVerticalStrut(SPACING));

        this.add(this.languageSwitchButton);
    }

    @Override
    protected void initStyle(){
        super.initStyle();
        this.setBackground(Colors.SECONDARY_BACKGROUND_COLOR);
    }
}
