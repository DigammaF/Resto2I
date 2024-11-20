package views;

import Events.LiveProductDisplayEvent;
import Events.TicketEditorEvent;
import logic.Logic;
import logic.Observable;
import logic.Observer;
import logic.ProductType;
import models.LiveProduct;
import models.Ticket;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class TicketEditor extends JPanel
        implements Observable<TicketEditorEvent>, Observer<LiveProductDisplayEvent>
{
    private Ticket ticket;
    private JButton newSoftDrinkButton;
    private JButton newAlcoholButton;
    private JButton newEntreeButton;
    private JButton newMealButton;
    private JButton newDessertButton;
    private JPanel liveProductsPanel;
    private JScrollPane liveProductsScrollPane;

    private ArrayList<Observer<TicketEditorEvent>> ticketEditorEventObservers;

    public TicketEditor(Ticket ticket) {
        super();
        this.ticket = ticket;
        this.ticketEditorEventObservers = new ArrayList<>();
        this.initComponents();
        this.initLayout();
    }

    private void initComponents() {
        AppContext context = AppContext.getAppContext();
        this.newSoftDrinkButton = new JButton();
        this.newSoftDrinkButton.addActionListener(_ -> {
            context.perform(entityManager -> {
                LiveProduct liveProduct = new LiveProduct();
                liveProduct.setCount(1);
                Logic.addLiveProduct(this.ticket, liveProduct);
                entityManager.persist(liveProduct);
                this.liveProductsPanel.add(
                        new LiveProductDisplay(liveProduct, product -> product.getProductType() == ProductType.SOFT_DRINK)
                );
                this.revalidate();
                this.repaint();
            });
        });
        this.newAlcoholButton = new JButton();
        this.newAlcoholButton.addActionListener(_ -> {
            context.perform(entityManager -> {
                LiveProduct liveProduct = new LiveProduct();
                liveProduct.setCount(1);
                Logic.addLiveProduct(this.ticket, liveProduct);
                entityManager.persist(liveProduct);
                this.liveProductsPanel.add(
                        new LiveProductDisplay(liveProduct, product -> product.getProductType() == ProductType.ALCOHOL)
                );
                this.revalidate();
                this.repaint();
            });
        });
        this.newEntreeButton = new JButton();
        this.newEntreeButton.addActionListener(_ -> {
            context.perform(entityManager -> {
                LiveProduct liveProduct = new LiveProduct();
                liveProduct.setCount(1);
                Logic.addLiveProduct(this.ticket, liveProduct);
                entityManager.persist(liveProduct);
                this.liveProductsPanel.add(
                        new LiveProductDisplay(liveProduct, product -> product.getProductType() == ProductType.ENTREE)
                );
                this.revalidate();
                this.repaint();
            });
        });
        this.newMealButton = new JButton();
        this.newMealButton.addActionListener(_ -> {
            context.perform(entityManager -> {
                LiveProduct liveProduct = new LiveProduct();
                liveProduct.setCount(1);
                Logic.addLiveProduct(this.ticket, liveProduct);
                entityManager.persist(liveProduct);
                this.liveProductsPanel.add(
                        new LiveProductDisplay(liveProduct, product -> product.getProductType() == ProductType.MEAL)
                );
                this.revalidate();
                this.repaint();
            });
        });
        this.newDessertButton = new JButton();
        this.newDessertButton.addActionListener(_ -> {
            context.perform(entityManager -> {
                LiveProduct liveProduct = new LiveProduct();
                liveProduct.setCount(1);
                Logic.addLiveProduct(this.ticket, liveProduct);
                entityManager.persist(liveProduct);
                this.liveProductsPanel.add(
                        new LiveProductDisplay(liveProduct, product -> product.getProductType() == ProductType.DESSERT)
                );
                this.revalidate();
                this.repaint();
            });
        });
        this.liveProductsPanel = new JPanel();
        this.liveProductsScrollPane = new JScrollPane(liveProductsPanel);

        for (LiveProduct liveProduct : this.ticket.getLiveProducts()) {
            this.liveProductsPanel.add(new LiveProductDisplay(liveProduct));
        }
    }

    private void initLayout() {
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(this.liveProductsPanel, BoxLayout.X_AXIS));
        buttonsPanel.add(this.newSoftDrinkButton);
        buttonsPanel.add(this.newAlcoholButton);
        buttonsPanel.add(this.newEntreeButton);
        buttonsPanel.add(this.newMealButton);
        buttonsPanel.add(this.newDessertButton);
        this.liveProductsPanel.setLayout(new BoxLayout(this.liveProductsPanel, BoxLayout.Y_AXIS));
        this.setLayout(new BorderLayout());
        this.add(buttonsPanel, BorderLayout.NORTH);
        this.add(this.liveProductsScrollPane, BorderLayout.CENTER);
        this.add(Box.createHorizontalStrut(500), BorderLayout.SOUTH);
    }

    @Override
    public void addObserver(Observer<TicketEditorEvent> observer) {
        this.ticketEditorEventObservers.add(observer);
    }

    @Override
    public void removeObserver(Observer<TicketEditorEvent> observer) {
        this.ticketEditorEventObservers.remove(observer);
    }

    @Override
    public void notifyObservers(TicketEditorEvent event) {
        ArrayList<Observer<TicketEditorEvent>> observers = new ArrayList<>(this.ticketEditorEventObservers);
        for (Observer<TicketEditorEvent> observer : observers) { observer.onEvent(event); }
    }

    @Override
    public void onEvent(LiveProductDisplayEvent event) {
        switch (event) {
            case COST_CHANGE -> this.notifyObservers(TicketEditorEvent.COST_CHANGE);
            default -> { }
        }
    }
}
