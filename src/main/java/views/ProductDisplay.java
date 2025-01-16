package views;

import language.TextContent;
import logic.ProductType;
import logic.Tax;
import models.Product;
import views.style.Colors;
import views.style.Paddings;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.util.Objects;

public class ProductDisplay extends JPanel {
    private final Product product;
    private JButton availableButton;
    private JLabel productNameLabel;
    private JTextField productNameTextField;
    private JLabel costLabel;
    private JTextField costField;
    private JComboBox<Tax> taxComboBox;
    private JComboBox<ProductType> productTypeComboBox;
    private JLabel tagsLabel;
    private JTextField tagsTextField;
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
                this.availableButton.setBackground(Colors.COMMITED_FIELD);
            } else {
                this.availableButton.setBackground(Colors.MODIFIED_FIELD);
            }
        });
        if (this.product.isAvailable() == Product.DEFAULT_AVAILABLE) { this.availableButton.setBackground(Colors.STRANGE_VALUE_FIELD); }
        this.productNameLabel = new JLabel(textContent.get(context.getLanguage(), TextContent.Key.NAME));
        this.productNameTextField = new JTextField(10);
        this.productNameTextField.setText(product.getName());
        this.productNameTextField.addKeyListener(new Validate(
                this.productNameTextField, enteredText -> context.perform(_ -> product.setName(enteredText))
        ));
        if (Objects.equals(this.product.getName(), Product.DEFAULT_NAME)) { this.productNameTextField.setBackground(Colors.STRANGE_VALUE_FIELD); }
        this.costLabel = new JLabel(textContent.get(context.getLanguage(), TextContent.Key.COST));
        this.costField = new JTextField(10);
        this.costField.setText(Double.toString(this.product.getCost()));
        this.costField.addKeyListener(new Validate(this.costField, text -> context.perform(_ -> this.product.setCost(Double.parseDouble(text)))));
        if (this.product.getCost() == Product.DEFAULT_COST) { this.costField.setBackground(Colors.STRANGE_VALUE_FIELD); }
        ComboBoxModel<Tax> taxComboBoxModel = new DefaultComboBoxModel<>(Tax.values());
        this.taxComboBox = new JComboBox<>(taxComboBoxModel);
        this.taxComboBox.addItemListener(event -> {
            if (event.getStateChange() == ItemEvent.SELECTED) {
                product.setTax((Tax) taxComboBox.getSelectedItem());
                this.taxComboBox.setBackground(Colors.UNMODIFIED_FIELD);
            }
        });
        this.taxComboBox.setSelectedItem(this.product.getTax());
        ComboBoxModel<ProductType> productTypeComboBoxModel = new DefaultComboBoxModel<>(ProductType.values());
        this.productTypeComboBox = new JComboBox<>(productTypeComboBoxModel);
        this.productTypeComboBox.addItemListener(event -> {
            if (event.getStateChange() == ItemEvent.SELECTED) {
                product.setProductType((ProductType) event.getItem());
                this.productTypeComboBox.setBackground(Colors.UNMODIFIED_FIELD);
            }
        });
        this.productTypeComboBox.setSelectedItem(this.product.getProductType());
        if (this.product.getProductType() == Product.DEFAULT_PRODUCT_TYPE) { this.productTypeComboBox.setBackground(Colors.STRANGE_VALUE_FIELD); }
        this.tagsLabel = new JLabel(textContent.get(context.getLanguage(), TextContent.Key.TAGS));
        this.tagsTextField = new JTextField(10);
        this.tagsTextField.setText(this.product.getTags());
        this.tagsTextField.addKeyListener(new Validate(this.tagsTextField, text -> context.perform(_ -> this.product.setTags(text))));
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
            this.add(Box.createHorizontalStrut(Paddings.OUTSIDE));
        this.add(this.availableButton);
            this.add(Box.createHorizontalStrut(Paddings.MEDIUM));
        this.add(this.productNameLabel);
            this.add(Box.createHorizontalStrut(Paddings.SMALL));
        this.add(this.productNameTextField);
            this.add(Box.createHorizontalStrut(Paddings.MEDIUM));
        this.add(this.costLabel);
            this.add(Box.createHorizontalStrut(Paddings.SMALL));
        this.add(this.costField);
            this.add(Box.createHorizontalStrut(Paddings.MEDIUM));
        this.add(this.taxComboBox);
            this.add(Box.createHorizontalStrut(Paddings.MEDIUM));
        this.add(this.productTypeComboBox);
            this.add(Box.createHorizontalStrut(Paddings.SMALL));
        this.add(this.tagsLabel);
            this.add(Box.createHorizontalStrut(Paddings.SMALL));
        this.add(this.tagsTextField);
            this.add(Box.createHorizontalStrut(Paddings.MEDIUM));
        this.add(this.removeButton);
            this.add(Box.createHorizontalStrut(Paddings.OUTSIDE));
    }

    private String getAvailableButtonText() {
        AppContext context = AppContext.getAppContext();
        TextContent textContent = TextContent.getTextContent();
        return textContent.get(context.getLanguage(), TextContent.Key.AVAILABLE) + ": " + (this.product.isAvailable()
                ? textContent.get(context.getLanguage(), TextContent.Key.YES)
                : textContent.get(context.getLanguage(), TextContent.Key.NO));
    }
}
