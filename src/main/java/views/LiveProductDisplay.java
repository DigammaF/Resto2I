package views;

import events.LiveProductDisplayEvents;
import events.LiveProductDisplayEvents.LiveProductDisplayEvent;
import logic.Logic;
import logic.Observable;
import logic.Observer;
import logic.ProductType;
import models.LiveProduct;
import models.Product;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Vector;

public class LiveProductDisplay extends JPanel
        implements Observable<LiveProductDisplayEvent>
{
    private final LiveProduct liveProduct;
    private final ProductFilter productFilter;
    private JComboBox<Product> productsComboBox;
    private JLabel countLabel;
    private JTextField countField;
    private JButton removeButton;

    private final ArrayList<Observer<LiveProductDisplayEvent>> liveProductDisplayEventObservers;

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
        // TODO: check that every action affecting cost also sends the appropriate event
        AppContext context = AppContext.getAppContext();
        ComboBoxModel<Product> productsModel = new DefaultComboBoxModel<>(
                new Vector<>(
                        context.getRestaurant().getProducts()
                                .stream().filter(product -> product.isUsed() && this.productFilter.execute(product))
                                .toList()
                )
        );
        this.productsComboBox = new JComboBox<>(productsModel);
        this.countLabel = new JLabel("X");
        this.countField = new JTextField();
        this.countField.setText(Integer.toString(liveProduct.getCount()));
        this.countField.addKeyListener(new Validate(
                this.countField,
                text -> {
                    if (context.perform(_ -> this.liveProduct.setCount(Integer.parseInt(text)))) {
                        this.notifyObservers(new LiveProductDisplayEvents.CostChanged());
                        return true;
                    } else {
                        return false;
                    }
                }
        ));
        this.removeButton = new JButton("X");
        LiveProductDisplay liveProductDisplay = this;
        this.removeButton.addActionListener(_ -> {
            if (context.perform(entityManager -> {
                Logic.remLiveProduct(this.liveProduct.getTicket(), this.liveProduct);
                entityManager.remove(liveProduct);
            })) {
                liveProductDisplay.getParent().remove(liveProductDisplay);
                context.getMainView().validate();
                context.getMainView().repaint();
                this.notifyObservers(new LiveProductDisplayEvents.CostChanged());
                this.notifyObservers(new LiveProductDisplayEvents.Removed(this));
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
