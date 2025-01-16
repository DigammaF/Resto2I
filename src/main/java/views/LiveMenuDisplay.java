package views;

import events.LiveMenuDisplayEvents;
import logic.Logic;
import logic.Observable;
import logic.Observer;
import models.LiveMenu;
import models.LiveMenuItem;
import models.Product;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class LiveMenuDisplay extends JPanel implements Observable<LiveMenuDisplayEvents.LiveMenuDisplayEvent> {
    private LiveMenu liveMenu;
    private JLabel liveMenuNameLabel;
    private List<JComboBox<Product>> liveMenuItemComboBoxes;
    private JButton removeButton;

    private List<Observer<LiveMenuDisplayEvents.LiveMenuDisplayEvent>> observers;

    public LiveMenuDisplay(LiveMenu liveMenu) {
        this.liveMenu = liveMenu;
        this.observers = new ArrayList<>();
        this.initComponents();
        this.initLayout();
    }

    private void initComponents() {
        AppContext context = AppContext.getAppContext();
        this.liveMenuNameLabel = new JLabel(this.liveMenu.getName());
        this.liveMenuItemComboBoxes = new ArrayList<>();
        for (LiveMenuItem liveMenuItem : this.liveMenu.getLiveMenuItems()) {
            ComboBoxModel<Product> productsModel = new DefaultComboBoxModel<>(
                    new Vector<>(
                            context.getRestaurant().getProducts()
                                    .stream().filter(product -> product.isUsed() && liveMenuItem.allowed(product))
                                    .toList()
                    )
            );
            JComboBox<Product> liveMenuItemComboBox = getProductJComboBox(liveMenuItem, productsModel);
            this.liveMenuItemComboBoxes.add(liveMenuItemComboBox);
        }
        this.removeButton = new JButton("X");
        this.removeButton.addActionListener(_ -> {
            Logic.remLiveMenu(this.liveMenu.getTicket(), this.liveMenu);
            this.getParent().remove(this);
            context.getMainView().validate();
            context.getMainView().repaint();
            this.notifyObservers(new LiveMenuDisplayEvents.CostChanged());
            this.notifyObservers(new LiveMenuDisplayEvents.Removed(this));
        });
    }

    private JComboBox<Product> getProductJComboBox(LiveMenuItem liveMenuItem, ComboBoxModel<Product> productsModel) {
        JComboBox<Product> liveMenuItemComboBox = new JComboBox<>(productsModel);
        liveMenuItemComboBox.setSelectedItem(liveMenuItem.getLiveProduct().getProduct());
        liveMenuItemComboBox.addItemListener(event -> {
            if (event.getStateChange() == ItemEvent.SELECTED) {
                liveMenuItem.getLiveProduct().setProduct((Product) event.getItem());
                this.notifyObservers(new LiveMenuDisplayEvents.CostChanged());
            }
        });
        return liveMenuItemComboBox;
    }

    private void initLayout() {

    }

    @Override
    public void addObserver(Observer<LiveMenuDisplayEvents.LiveMenuDisplayEvent> observer) {
        this.observers.add(observer);
    }

    public void removeObserver(Observer<LiveMenuDisplayEvents.LiveMenuDisplayEvent> observer) {
        this.observers.remove(observer);
    }

    public void notifyObservers(LiveMenuDisplayEvents.LiveMenuDisplayEvent event) {
        for (Observer<LiveMenuDisplayEvents.LiveMenuDisplayEvent> observer : this.observers) {
            observer.onEvent(event);
        }
    }
}
