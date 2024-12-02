package views;

import language.TextContent;
import logic.Logic;
import models.Product;

import javax.swing.*;
import java.awt.*;

public class ProductsEditor extends JPanel {
    private JButton newProductButton;
    private JPanel productsPanel;
    private JScrollPane productsScrollPane;

    public ProductsEditor() {
        super();
        this.initComponent();
        this.initLayout();
    }

    private void initComponent() {
        AppContext context = AppContext.getAppContext();
        TextContent textContent = TextContent.getTextContent();
        this.newProductButton = new JButton(textContent.get(context.getLanguage(), TextContent.Key.PRODUCTS_EDITOR_NEW_PRODUCT_BUTTON));
        this.newProductButton.addActionListener(_ -> {
            context.perform(entityManager -> {
                Product product = new Product();
                Logic.addProduct(context.getRestaurant(), product);
                entityManager.persist(product);
                this.productsPanel.add(new ProductDisplay(product));
                this.revalidate();
                this.repaint();
            });
        });
        this.productsPanel = new JPanel();
        this.productsScrollPane = new JScrollPane(productsPanel);

        for (Product product : context.getRestaurant().getProducts()) {
            if (product.isUsed()) {
                this.productsPanel.add(new ProductDisplay(product));
            }
        }
    }

    private void initLayout() {
        this.productsPanel.setLayout(new BoxLayout(this.productsPanel, BoxLayout.Y_AXIS));
        this.setLayout(new BorderLayout());
        this.add(this.newProductButton, BorderLayout.NORTH);
        this.add(this.productsScrollPane, BorderLayout.CENTER);
        this.add(Box.createHorizontalStrut(500), BorderLayout.SOUTH);
    }
}
