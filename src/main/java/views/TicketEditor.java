package views;

import events.LiveProductDisplayEvents;
import events.LiveProductDisplayEvents.LiveProductDisplayEvent;
import events.TicketEditorEvent;
import language.TextContent;
import logic.Observable;
import logic.Observer;
import logic.ProductType;
import models.LiveProduct;
import models.Ticket;
import views.style.EditorPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class TicketEditor extends EditorPanel
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
        this.ticket = ticket;
        this.ticketEditorEventObservers = new ArrayList<>();
        this.initComponents();
        this.initLayout();
    }

    private void initComponents() {
        AppContext context = AppContext.getAppContext();
        TextContent textContent = TextContent.getTextContent();
        this.newButtons = new HashMap<ProductType, JButton>();

        for (ProductType currentProductType : Arrays.stream(ProductType.values()).sorted().toList()) {
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
        if (event instanceof LiveProductDisplayEvents.CostChanged) { this.notifyObservers(TicketEditorEvent.COST_CHANGE); }
        if (event instanceof LiveProductDisplayEvents.Removed removed) { removed.liveProductDisplay.removeObserver(this); }
    }

    private ActionListener makeActionListener(ProductType productTypeParam){
        AppContext context = AppContext.getAppContext();
        return (ActionEvent _) -> {
            context.getRestaurant().createLiveProduct(
                    this.ticket,
                    context.getRestaurant().getProducts()
                            .stream().filter(product -> product.getProductType() == productTypeParam)
                            .toList()
            ).ifPresent(liveProduct -> {
                liveProduct.setCount(1);
                LiveProductDisplay liveProductDisplay = new LiveProductDisplay(
                        liveProduct, product -> product.getProductType() == productTypeParam
                );
                liveProductDisplay.addObserver(this);
                this.liveProductsPanel.add(liveProductDisplay);
                this.revalidate();
                this.repaint();
                this.notifyObservers(TicketEditorEvent.COST_CHANGE);
            });
        };
    }

}
