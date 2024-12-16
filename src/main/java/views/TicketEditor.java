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
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Function;

public class TicketEditor extends JPanel
        implements Observable<TicketEditorEvent>, Observer<LiveProductDisplayEvent>
{
    private Ticket ticket;
    private JPanel liveProductsPanel;

    /**
     * Contains all the new buttons corresponding to each ProductType.
     * eg the new "soft drink" button will be a JButton value listed under the "ProductType.SOFT_DRINK" key.
     */
    private HashMap<ProductType, JButton> newButtons;

    private JScrollPane liveProductsScrollPane;

    private ArrayList<Observer<TicketEditorEvent>> ticketEditorEventObservers;

    public TicketEditor(Ticket ticket) {
        super();
        System.out.println("debug : TicketEditor créé");
        this.ticket = ticket;
        this.ticketEditorEventObservers = new ArrayList<>();
        this.initComponents();
        this.initLayout();
    }

    private void initComponents() {
        AppContext context = AppContext.getAppContext();
        TextContent textContent = TextContent.getTextContent();
        this.newButtons = new HashMap<ProductType, JButton>();

        for (ProductType currentProductType : ProductType.values()) {
            JButton newButton = new JButton();
            this.newButtons.put(currentProductType, newButton);
            newButton.setText(currentProductType.toString());
            newButton.addActionListener(
                    makeActionListener(currentProductType)
            );
        }

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

        for (JButton currentButton : newButtons.values()) {
            buttonsPanel.add(currentButton);
        }

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
        System.out.println("debug : onEvent appelé");
        System.out.println(event);
        if (event instanceof LiveProductDisplayEvents.CostChanged) { this.notifyObservers(TicketEditorEvent.COST_CHANGE); }
        if (event instanceof LiveProductDisplayEvents.Removed removed) { removed.liveProductDisplay.removeObserver(this); }
    }



    private ActionListener makeActionListener(ProductType productTypeParam){
        AppContext context = AppContext.getAppContext();
        return (ActionEvent e) -> {
            context.perform(entityManager -> {
                LiveProduct liveProduct = new LiveProduct();
                liveProduct.setCount(1);
                Logic.addLiveProduct(this.ticket, liveProduct);
                entityManager.persist(liveProduct);
                LiveProductDisplay liveProductDisplay = new LiveProductDisplay(
                        liveProduct, product -> product.getProductType() == productTypeParam
                );
                liveProductDisplay.addObserver(this);
                this.liveProductsPanel.add(liveProductDisplay);
                this.revalidate();
                this.repaint();
            });
        };
    }

}
