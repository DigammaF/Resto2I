package views;

import events.LiveMenuDisplayEvents;
import language.TextContent;
import logic.Logic;
import logic.Observable;
import logic.Observer;
import models.LiveMenu;
import models.LiveMenuItem;
import models.Product;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class LiveMenuDisplay extends JPanel implements Observable<LiveMenuDisplayEvents.LiveMenuDisplayEvent> {
    private LiveMenu liveMenu;
    private JLabel nameLabel;
    private JLabel costLabel;
    private List<Item> items;
    private JButton removeButton;

    private List<Observer<LiveMenuDisplayEvents.LiveMenuDisplayEvent>> observers;

    private class Item {
        public JComboBox<Product> comboBox;
        public JButton claimedButton;

        public Item(JComboBox<Product> comboBox, JButton claimedButton) {
            this.comboBox = comboBox;
            this.claimedButton = claimedButton;
        }
    }

    public LiveMenuDisplay(LiveMenu liveMenu) {
        this.liveMenu = liveMenu;
        this.observers = new ArrayList<>();
        this.initComponents();
        this.initLayout();
    }

    private void initComponents() {
        AppContext context = AppContext.getAppContext();
        TextContent textContent = TextContent.getTextContent();
        this.nameLabel = new JLabel(this.liveMenu.getName());
        this.costLabel = new JLabel(textContent.get(context.getLanguage(), TextContent.Key.COST) + ": " + String.valueOf(this.liveMenu.getATICost()) + "€");
        this.items = new ArrayList<>();
        for (LiveMenuItem liveMenuItem : this.liveMenu.getLiveMenuItems()) {
            ComboBoxModel<Product> model = new DefaultComboBoxModel<>(
                    new Vector<>(
                            context.getRestaurant().getProducts()
                                    .stream().filter(product -> product.isUsed() && liveMenuItem.allowed(product))
                                    .toList()
                    )
            );
            JComboBox<Product> comboBox = getProductJComboBox(liveMenuItem, model);
            JButton claimedButton = new JButton();
            claimedButton.setText(getClaimedButtonText(liveMenuItem));
            claimedButton.addActionListener(_ -> {
                liveMenuItem.setClaimed(!liveMenuItem.isClaimed());
                claimedButton.setText(getClaimedButtonText(liveMenuItem));
            });
            this.items.add(new Item(comboBox, claimedButton));
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

    private String getClaimedButtonText(LiveMenuItem liveMenuItem) {
        return liveMenuItem.isClaimed() ? "✔️" : "❌";
    }

    private void initLayout() {
        AppContext context = AppContext.getAppContext();
        TextContent textContent = TextContent.getTextContent();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(this.nameLabel);
        this.add(this.costLabel);

        for (Item item : this.items) {
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
            panel.add(item.comboBox);
            panel.add(new JLabel(textContent.get(context.getLanguage(), TextContent.Key.CLAIMED)));
            panel.add(item.claimedButton);
            this.add(panel);
        }
        this.add(this.removeButton);
    }

    @Override
    public void addObserver(Observer<LiveMenuDisplayEvents.LiveMenuDisplayEvent> observer) {
        this.observers.add(observer);
    }

    public void removeObserver(Observer<LiveMenuDisplayEvents.LiveMenuDisplayEvent> observer) {
        this.observers.remove(observer);
    }

    public void notifyObservers(LiveMenuDisplayEvents.LiveMenuDisplayEvent event) {
        List<Observer<LiveMenuDisplayEvents.LiveMenuDisplayEvent>> observers = this.observers.stream().toList();
        for (Observer<LiveMenuDisplayEvents.LiveMenuDisplayEvent> observer : observers) {
            observer.onEvent(event);
        }
    }
}
