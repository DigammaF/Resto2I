package views;

import Events.LiveProductDisplayEvents;
import Events.LiveProductDisplayEvents.LiveProductDisplayEvent;
import Events.TicketEditorEvent;
import language.TextContent;
import logic.Logic;
import logic.Observable;
import logic.Observer;
import logic.ProductType;
import models.LiveProduct;
import models.Ticket;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class TicketEditor extends JPanel
        implements Observable<TicketEditorEvent>, Observer<LiveProductDisplayEvent>
{
    private Ticket ticket;

    /**
     * Contains all the new buttons corresponding to each ProductType.
     * eg the new "soft drink" button will be a JButton value listed under the "ProductType.SOFT_DRINK" key.
     */
    private HashMap<ProductType, JButton> newButtons;

            //TODO à suppr
            private JButton newSoftDrinkButton;
            private JButton newAlcoholButton;
            private JButton newEntreeButton;
            private JButton newMealButton;
            private JButton newDessertButton;
            private JPanel liveProductsPanel;
            //TODO à suppr

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
        TextContent textContent = TextContent.getTextContent();

        //remplacer tout ce qui est ci-dessous par des appels à initComponent()
        this.newSoftDrinkButton = new JButton();
        this.newSoftDrinkButton.setText(textContent.get(context.getLanguage(), TextContent.Key.SOFT_DRINK));
        this.newSoftDrinkButton.addActionListener(_ -> {
            context.perform(entityManager -> {
                LiveProduct liveProduct = new LiveProduct();
                liveProduct.setCount(1);
                Logic.addLiveProduct(this.ticket, liveProduct);
                entityManager.persist(liveProduct);
                LiveProductDisplay liveProductDisplay = new LiveProductDisplay(
                        liveProduct, product -> product.getProductType() == ProductType.SOFT_DRINK
                );
                liveProductDisplay.addObserver(this);
                this.liveProductsPanel.add(liveProductDisplay);
                this.revalidate();
                this.repaint();
            });
        });

        this.newAlcoholButton = new JButton();
        this.newAlcoholButton.setText(textContent.get(context.getLanguage(), TextContent.Key.ALCOHOL));
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
                this.repaint(); // TODO keep changing those callbacks to include .addObserver
            });
        });

        this.newEntreeButton = new JButton();
        this.newEntreeButton.setText(textContent.get(context.getLanguage(), TextContent.Key.ENTREE));
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
        this.newMealButton.setText(textContent.get(context.getLanguage(), TextContent.Key.MEAL));
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
        this.newDessertButton.setText(textContent.get(context.getLanguage(), TextContent.Key.DESSERT));
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
            LiveProductDisplay liveProductDisplay = new LiveProductDisplay(liveProduct);
            liveProductDisplay.addObserver(this);
            this.liveProductsPanel.add(liveProductDisplay);
        }
    }

    private void initLayout() {
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));
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
        System.out.println(event);
        if (event instanceof LiveProductDisplayEvents.CostChanged) { this.notifyObservers(TicketEditorEvent.COST_CHANGE); }
        if (event instanceof LiveProductDisplayEvents.Removed removed) { removed.liveProductDisplay.removeObserver(this); }
    }

    private void initComponent(){
        for (ProductType currentProductType : ProductType.values()) {
            JButton newButton = new JButton();
            this.newButtons.put(currentProductType, newButton);
            newButton.setText(
                    textContent.get(context.getLanguage(), TextContent.Key.SOFT_DRINK)
            );
            newButton.addActionListener(
                    makeActionListener(currentProductType);
            );
        }
    }
    private void makeActionListener(ProductType productType){
        //todo
    }
}
