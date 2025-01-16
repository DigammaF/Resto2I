package views;

import events.LiveMenuDisplayEvents;
import events.LiveProductDisplayEvents;
import events.LiveProductDisplayEvents.LiveProductDisplayEvent;
import events.TicketEditorEvent;
import language.TextContent;
import logic.Observable;
import logic.Observer;
import logic.ProductType;
import models.LiveMenu;
import models.LiveProduct;
import models.Menu;
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
    private final Ticket ticket;
    private JPanel liveProductsPanel;
    private JPanel liveMenusPanel;
    private JPanel addMenuPanel;

    /**
     * Contains all the new buttons corresponding to each ProductType.
     * e.g. the new "soft drink" button will be a JButton value listed under the "ProductType.SOFT_DRINK" key.
     */
    private HashMap<ProductType, JButton> newButtons;

    private final ArrayList<Observer<TicketEditorEvent>> ticketEditorEventObservers;

    private class LiveMenuDisplayObserver implements Observer<LiveMenuDisplayEvents.LiveMenuDisplayEvent> {
        private TicketEditor ticketEditor;
        public LiveMenuDisplayObserver(TicketEditor ticketEditor) { this.ticketEditor = ticketEditor; }
        public void onEvent(LiveMenuDisplayEvents.LiveMenuDisplayEvent event) { this.ticketEditor.onEvent(event); }
    }

    private LiveMenuDisplayObserver liveMenuDisplayObserver;

    public TicketEditor(Ticket ticket) {
        super();
        this.ticket = ticket;
        this.ticketEditorEventObservers = new ArrayList<>();
        this.liveMenuDisplayObserver = new LiveMenuDisplayObserver(this);
        this.initComponents();
        this.initLayout();
    }

    private void initComponents() {
        AppContext context = AppContext.getAppContext();
        TextContent textContent = TextContent.getTextContent();
        this.newButtons = new HashMap<>();

        for (ProductType currentProductType : Arrays.stream(ProductType.values()).sorted().toList()) {
            JButton newButton = new JButton();
            this.newButtons.put(currentProductType, newButton);
            newButton.setText(currentProductType.toString());
            newButton.addActionListener(
                    makeActionListener(currentProductType)
            );
        }

        this.liveProductsPanel = new JPanel();

        for (LiveProduct liveProduct : this.ticket.getLiveProducts()) {
            LiveProductDisplay liveProductDisplay = new LiveProductDisplay(liveProduct);
            liveProductDisplay.addObserver(this);
            this.liveProductsPanel.add(liveProductDisplay);
        }

        this.liveMenusPanel = new JPanel();

        for (LiveMenu liveMenu : this.ticket.getLiveMenus()) {
            LiveMenuDisplay liveMenuDisplay = new LiveMenuDisplay(liveMenu);
            liveMenuDisplay.addObserver(this.liveMenuDisplayObserver);
            this.liveMenusPanel.add(liveMenuDisplay);
        }

        this.addMenuPanel = new JPanel();

        for (Menu menu : context.getRestaurant().getMenus()) {
            JButton button = getButton(menu, context, textContent);
            this.addMenuPanel.add(button);
        }
    }

    private JButton getButton(Menu menu, AppContext context, TextContent textContent) {
        JButton button = new JButton();
        button.setText(menu.getName());
        button.addActionListener(_ -> {
            context.getRestaurant().createLiveMenu(this.ticket, menu).ifPresentOrElse(
                    (liveMenu -> {
                        LiveMenuDisplay liveMenuDisplay = new LiveMenuDisplay(liveMenu);
                        liveMenuDisplay.addObserver(this.liveMenuDisplayObserver);
                        this.liveMenusPanel.add(liveMenuDisplay);
                        context.getMainView().validate();
                        context.getMainView().repaint();
                        this.notifyObservers(TicketEditorEvent.COST_CHANGE);
                    }),
                    () -> {
                        context.getMainView().println(textContent.get(context.getLanguage(), TextContent.Key.CANNOT_CREATE_LIVE_MENU));
                    }
            );
        });
        return button;
    }

    private void initLayout() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));
        for (JButton currentButton : newButtons.values()) {
            buttonsPanel.add(currentButton);
        }
        this.add(buttonsPanel);
        this.liveProductsPanel.setLayout(new BoxLayout(this.liveProductsPanel, BoxLayout.Y_AXIS));
        this.add(this.liveProductsPanel);
        this.addMenuPanel.setLayout(new BoxLayout(this.addMenuPanel, BoxLayout.X_AXIS));
        this.add(this.addMenuPanel);
        this.liveMenusPanel.setLayout(new BoxLayout(this.liveMenusPanel, BoxLayout.X_AXIS));
        this.add(this.liveMenusPanel);
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

    public void onEvent(LiveMenuDisplayEvents.LiveMenuDisplayEvent event) {
        if (event instanceof LiveMenuDisplayEvents.CostChanged) { this.notifyObservers(TicketEditorEvent.COST_CHANGE); }
        if (event instanceof LiveMenuDisplayEvents.Removed removed) { removed.liveMenuDisplay.removeObserver(this.liveMenuDisplayObserver); }
    }

    private ActionListener makeActionListener(ProductType productTypeParam){
        AppContext context = AppContext.getAppContext();
        return (ActionEvent _) -> context.getRestaurant().createLiveProduct(
                this.ticket,
                context.getRestaurant().getProducts()
                        .stream().filter(product -> product.getProductType() == productTypeParam)
                        .toList()
        ).ifPresentOrElse(
            liveProduct -> {
                liveProduct.setCount(1);
                LiveProductDisplay liveProductDisplay = new LiveProductDisplay(
                        liveProduct, product -> product.getProductType() == productTypeParam
                );
                liveProductDisplay.addObserver(this);
                this.liveProductsPanel.add(liveProductDisplay);
                this.revalidate();
                this.repaint();
                this.notifyObservers(TicketEditorEvent.COST_CHANGE);
            },
            () -> {
                TextContent textContent = TextContent.getTextContent();
                context.getMainView().println(textContent.get(context.getLanguage(), TextContent.Key.TICKET_EDITOR_NO_LIVE_PRODUCT_WARNING) + productTypeParam);
            }
        );
    }

}
