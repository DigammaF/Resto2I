package views;

import language.TextContent;
import logic.Observable;
import logic.Observer;
import models.Product;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDisplay extends JPanel implements Observable<ProductDisplay.ProductDisplayEvent> {
    private Product product;
    private JLabel productNameLabel;
    private JTextField productNameTextField;
    private JButton removeButton;

    private List<Observer<ProductDisplayEvent>> observers;

    public Product getProduct() {
        return product;
    }

    public abstract class ProductDisplayEvent {
        public ProductDisplay productDisplay;
        public ProductDisplayEvent(ProductDisplay product) { this.productDisplay = product; }
    }
    public class RemovedProductEvent extends ProductDisplayEvent {
        public RemovedProductEvent(ProductDisplay product) { super(product); }
    }

    public ProductDisplay(Product product) {
        super();
        this.product = product;
        this.observers = new ArrayList<>();
        this.initComponents();
        this.initLayout();
    }

    private void initComponents() {
        AppContext context = AppContext.getAppContext();
        TextContent text = TextContent.getTextContent();
        ProductDisplay productDisplay = this;
        this.productNameLabel = new JLabel(text.get(context.getLanguage(), TextContent.Key.PRODUCT_DISPLAY_NAME_LABEL));
        this.productNameTextField = new JTextField(10);
        this.productNameTextField.setText(product.getName());
        this.productNameTextField.addKeyListener(new Validate(
                this.productNameTextField, enteredText -> context.perform(_ -> product.setName(enteredText))
        ));
        this.removeButton = new JButton("X");
        this.removeButton.addActionListener(_ -> {
            if (context.perform(_ -> {
                product.setUsed(false);
            })) {
                notifyObservers(new RemovedProductEvent(productDisplay));
            }
        });
    }

    private void initLayout() {
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.add(this.productNameLabel);
        this.add(this.productNameTextField);
        this.add(this.removeButton);
    }

    public void addObserver(Observer<ProductDisplayEvent> observer) {
        this.observers.add(observer);
    }

    public void removeObserver(Observer<ProductDisplayEvent> observer) {
        this.observers.remove(observer);
    }

    @Override
    public void notifyObservers(ProductDisplayEvent event) {
        List<Observer<ProductDisplayEvent>> independent_observers = new ArrayList<>(this.observers);
        for (Observer<ProductDisplayEvent> observer : independent_observers) {
            observer.onUpdate(event);
        }
    }
}
