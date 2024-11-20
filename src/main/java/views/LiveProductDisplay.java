package views;

import Events.LiveProductDisplayEvent;
import logic.Logic;
import logic.Observable;
import logic.Observer;
import logic.ProductType;
import models.LiveProduct;
import models.Product;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Vector;

public class LiveProductDisplay extends JPanel
        implements Observable<LiveProductDisplayEvent>
{
    private LiveProduct liveProduct;
    private ProductFilter productFilter;
    private ComboBoxModel<Product> productsModel;
    private JComboBox<Product> productsComboBox;
    private JLabel countLabel;
    private JTextField countField;
    private JButton removeButton;

    private ArrayList<Observer<LiveProductDisplayEvent>> liveProductDisplayEventObservers;

    @FunctionalInterface
    public interface ProductFilter {
        boolean execute(Product product);
    }

    public LiveProductDisplay(LiveProduct liveProduct, ProductFilter productFilter) {
        super();
        this.liveProductDisplayEventObservers = new ArrayList<>();
        this.liveProduct = liveProduct;
        this.productFilter = productFilter;
        this.initComponents();
        this.initLayout();
    }

    public LiveProductDisplay(LiveProduct liveProduct) {
        super();
        this.liveProductDisplayEventObservers = new ArrayList<>();
        this.liveProduct = liveProduct;
        this.productFilter = _ -> true;
        this.initComponents();
        this.initLayout();
    }

    private void initComponents() {
        AppContext context = AppContext.getAppContext();
        Vector<Product> products = new Vector<>(context.getRestaurant().getProducts());
        Vector<Product> usedProducts = new Vector<>(products.stream().filter(product -> product.isUsed() && this.productFilter.execute(product)).toList());
        this.productsModel = new DefaultComboBoxModel<>(usedProducts);
        this.productsComboBox = new JComboBox<>(this.productsModel);
        this.countLabel = new JLabel("X");
        this.countField = new JTextField();
        this.countField.setText(Integer.toString(liveProduct.getCount()));
        this.countField.addKeyListener(new Validate(
                this.countField,
                text -> context.perform(_ -> this.liveProduct.setCount(Integer.parseInt(text)))
        ));
        this.removeButton = new JButton("X");
        this.removeButton.addActionListener(_ -> {
            if (context.perform(entityManager -> {
                Logic.remLiveProduct(this.liveProduct.getTicket(), this.liveProduct);
                entityManager.remove(liveProduct);
            })) {
                Container parent = this.getParent();
                parent.remove(this);
                parent.revalidate();
                parent.repaint();
            }
        });
    }

    private void initLayout() {
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.add(productsComboBox);
        this.add(countLabel);
        this.add(countField);
        this.add(removeButton);
    }

    public ProductFilter getProductFilter(ProductType productType) {
        return product -> product.getProductType() == productType;
    }

    @Override
    public void addObserver(Observer<LiveProductDisplayEvent> observer) {
        this.liveProductDisplayEventObservers.add(observer);
    }

    @Override
    public void removeObserver(Observer<LiveProductDisplayEvent> observer) {
        this.liveProductDisplayEventObservers.remove(observer);
    }

    @Override
    public void notifyObservers(LiveProductDisplayEvent event) {
        ArrayList<Observer<LiveProductDisplayEvent>> observers = new ArrayList<>(this.liveProductDisplayEventObservers);
        for (Observer<LiveProductDisplayEvent> observer: observers) { observer.onEvent(event); }
    }
}
