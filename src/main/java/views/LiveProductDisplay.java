package views;

import logic.Logic;
import logic.Observable;
import logic.Observer;
import models.LiveProduct;
import models.Product;

import javax.swing.*;
import javax.swing.event.ListDataListener;
import java.awt.event.KeyAdapter;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class LiveProductDisplay extends JPanel implements Observable<LiveProductDisplay.LiveProductDisplayEvent> {
    private LiveProduct liveProduct;
    private ComboBoxModel<Product> productsModel;
    private JComboBox<Product> productsComboBox;
    private JLabel countLabel;
    private JTextField countField;
    private JButton removeButton;

    private List<Observer<LiveProductDisplayEvent>> observers;

    public abstract class LiveProductDisplayEvent { }
    public class RemovedLiveProductEvent extends LiveProductDisplayEvent {
        public LiveProductDisplay liveProductDisplay;

        public RemovedLiveProductEvent(LiveProductDisplay liveProductDisplay) { this.liveProductDisplay = liveProductDisplay; }
    }

    public LiveProductDisplay(LiveProduct liveProduct) {
        super();
        this.observers = new ArrayList<>();
        this.liveProduct = liveProduct;
        this.initComponents();
        this.initLayout();
    }

    private void initComponents() {
        AppContext context = AppContext.getAppContext();
        Vector<Product> products = new Vector<>(context.getRestaurant().getProducts());
        Vector<Product> usedProducts = new Vector<>(products.stream().filter(Product::isUsed).toList());
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
                this.notifyObservers(new RemovedLiveProductEvent(this));
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

    @Override
    public void addObserver(Observer<LiveProductDisplayEvent> observer) {
        this.observers.add(observer);
    }

    @Override
    public void removeObserver(Observer<LiveProductDisplayEvent> observer) {
        this.observers.remove(observer);
    }

    @Override
    public void notifyObservers(LiveProductDisplayEvent event) {
        List<Observer<LiveProductDisplay.LiveProductDisplayEvent>> independent_observers = new ArrayList<>(this.observers);
        for (Observer<LiveProductDisplay.LiveProductDisplayEvent> observer : independent_observers) {
            observer.onUpdate(event);
        }
    }
}
