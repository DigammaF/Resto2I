package views;

import language.TextContent;
import logic.ProductType;
import logic.Tax;
import models.Product;

import javax.swing.*;
import java.awt.event.ItemEvent;

public class ProductDisplay extends JPanel {
    private final Product product;
    private JButton availableButton;
    private JLabel productNameLabel;
    private JTextField productNameTextField;
    private JLabel costLabel;
    private JTextField costField;
    private JComboBox<Tax> taxComboBox;
    private JComboBox<ProductType> productTypeComboBox;
    private JButton removeButton;

    public Product getProduct() {
        return product;
    }

    public ProductDisplay(Product product) {
        super();
        this.product = product;
        this.initComponents();
        this.initLayout();
    }

    private void initComponents() {
        AppContext context = AppContext.getAppContext();
        TextContent textContent = TextContent.getTextContent();
        this.availableButton = new JButton(this.getAvailableButtonText());
        this.availableButton.addActionListener(_ -> {
            if (context.perform(_ -> this.product.setAvailable(!this.product.isAvailable()))) {
                this.availableButton.setText(this.getAvailableButtonText());
            }
        });
        this.productNameLabel = new JLabel(textContent.get(context.getLanguage(), TextContent.Key.NAME));
        this.productNameTextField = new JTextField(10);
        this.productNameTextField.setText(product.getName());
        this.productNameTextField.addKeyListener(new Validate(
                this.productNameTextField, enteredText -> context.perform(_ -> product.setName(enteredText))
        ));
        this.costLabel = new JLabel(textContent.get(context.getLanguage(), TextContent.Key.COST));
        this.costField = new JTextField();
        this.costField.setText(Double.toString(this.product.getCost()));
        this.costField.addKeyListener(new Validate(this.costField, text -> context.perform(_ -> this.product.setCost(Double.parseDouble(text)))));
        ComboBoxModel<Tax> taxComboBoxModel = new DefaultComboBoxModel<>(Tax.values());
        this.taxComboBox = new JComboBox<>(taxComboBoxModel);
        this.taxComboBox.addItemListener(event -> {
            if (event.getStateChange() == ItemEvent.SELECTED) {
                product.setTax((Tax) taxComboBox.getSelectedItem());
            }
        });
        this.taxComboBox.setSelectedItem(this.product.getTax());
        ComboBoxModel<ProductType> productTypeComboBoxModel = new DefaultComboBoxModel<>(ProductType.values());
        this.productTypeComboBox = new JComboBox<>(productTypeComboBoxModel);
        this.productTypeComboBox.addItemListener(event -> {
            if (event.getStateChange() == ItemEvent.SELECTED) {
                product.setProductType((ProductType) event.getItem());
            }
        });
        this.productTypeComboBox.setSelectedItem(this.product.getProductType());
        this.removeButton = new JButton("X");
        ProductDisplay productDisplay = this;
        this.removeButton.addActionListener(_ -> {
            if (context.perform(_ -> product.setUsed(false))) {
                productDisplay.getParent().remove(productDisplay);
                context.getMainView().validate();
                context.getMainView().repaint();
            }
        });
    }

    private void initLayout() {
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.add(this.availableButton);
        this.add(this.productNameLabel);
        this.add(this.productNameTextField);
        this.add(this.costLabel);
        this.add(this.costField);
        this.add(this.taxComboBox);
        this.add(this.productTypeComboBox);
        this.add(this.removeButton);
    }

    private String getAvailableButtonText() {
        AppContext context = AppContext.getAppContext();
        TextContent textContent = TextContent.getTextContent();
        return textContent.get(context.getLanguage(), TextContent.Key.AVAILABLE) + ": " + (this.product.isAvailable()
                ? textContent.get(context.getLanguage(), TextContent.Key.YES)
                : textContent.get(context.getLanguage(), TextContent.Key.NO));
    }
}
