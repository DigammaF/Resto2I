package views;

import logic.Logic;
import logic.Observer;
import models.Product;

import javax.swing.*;
import java.awt.*;

public class ProductsEditor extends JPanel implements Observer<ProductDisplay.ProductDisplayEvent> {
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
        this.newProductButton = new JButton("+");
        this.newProductButton.addActionListener(_ -> {
            context.perform(entityManager -> {
                Product product = new Product();
                Logic.addproduct(context.getRestaurant(), product);
                entityManager.persist(product);
                this.addProductDisplay(new ProductDisplay(product));
                this.revalidate();
                this.repaint();
            });
        });
        this.productsPanel = new JPanel();
        this.productsScrollPane = new JScrollPane(productsPanel);

        for (Product product : context.getRestaurant().getProducts()) {
            if (product.isUsed()) {
                this.addProductDisplay(new ProductDisplay(product));
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

    @Override
    public void onUpdate(ProductDisplay.ProductDisplayEvent event) {
        if (event instanceof ProductDisplay.RemovedProductEvent) {
            this.removeProductDisplay(event.productDisplay);
            this.revalidate();
            this.repaint();
        }
    }

    private void addProductDisplay(ProductDisplay productDisplay) {
        this.productsPanel.add(productDisplay);
        productDisplay.addObserver(this);
    }

    private void removeProductDisplay(ProductDisplay productDisplay) {
        this.productsPanel.remove(productDisplay);
        productDisplay.removeObserver(this);
    }
}
