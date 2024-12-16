package views;

import language.TextContent;
import views.style.DefaultPanel;

import javax.swing.*;
import java.awt.*;

/**
 *
 * Sidebar menu
 *
 */
public class Menu extends DefaultPanel {
    private JButton productsEditorButton;
    private JButton ticketsEditorButton;
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
        this.productsEditorButton = new JButton();
        this.productsEditorButton.addActionListener(_ -> {
            JPanel mainPanel = context.getMainView().getMainPanel();
            mainPanel.removeAll();
            mainPanel.add(new ProductsEditor());
            mainPanel.validate();
            mainPanel.repaint();
        });
        this.ticketsEditorButton = new JButton();
        this.ticketsEditorButton.addActionListener(_ -> {
            JPanel mainPanel = context.getMainView().getMainPanel();
            mainPanel.removeAll();
            mainPanel.add(new TicketsEditor());
            mainPanel.validate();
            mainPanel.repaint();
        });
        this.restaurantEditorButton = new JButton();
        this.restaurantEditorButton.addActionListener(_ -> {
            JPanel mainPanel = context.getMainView().getMainPanel();
            mainPanel.removeAll();
            mainPanel.add(new RestaurantEditor(context.getRestaurant()));
            mainPanel.validate();
            mainPanel.repaint();
        });
        this.languageSwitchButton = new JButton("FR/EN");
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
        this.ticketsEditorButton.setText(text.get(context.getLanguage(), TextContent.Key.MENU_TICKETS_EDITOR_BUTTON));
        this.restaurantEditorButton.setText(text.get(context.getLanguage(), TextContent.Key.MENU_RESTAURANT_EDITOR_BUTTON));
    }

    private void initLayout() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(this.productsEditorButton);
        this.add(this.ticketsEditorButton);
        this.add(this.restaurantEditorButton);
        this.add(this.languageSwitchButton);
    }

    @Override
    protected void initStyle(){
        super.initStyle();
        this.setBackground(Color.cyan);
    }
}
