package views;

import logic.Logic;
import models.LiveProduct;
import models.Product;
import models.Ticket;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class TicketEditor extends JPanel {
    private Ticket ticket;
    private JButton newLiveProductButton;
    private JPanel liveProductsPanel;
    private JScrollPane liveProductsScrollPane;

    public TicketEditor(Ticket ticket) {
        super();
        this.ticket = ticket;
        this.initComponents();
        this.initLayout();
    }

    private void initComponents() {
        AppContext context = AppContext.getAppContext();
        this.newLiveProductButton = new JButton("+");
        this.newLiveProductButton.addActionListener(_ -> {
           context.perform(entityManager -> {
               LiveProduct liveProduct = new LiveProduct();
               liveProduct.setCount(1);
               Logic.addLiveProduct(this.ticket, liveProduct);
               entityManager.persist(liveProduct);
               this.liveProductsPanel.add(new LiveProductDisplay(liveProduct));
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
        this.liveProductsPanel.setLayout(new BoxLayout(this.liveProductsPanel, BoxLayout.Y_AXIS));
        this.setLayout(new BorderLayout());
        this.add(this.newLiveProductButton, BorderLayout.NORTH);
        this.add(this.liveProductsScrollPane, BorderLayout.CENTER);
        this.add(Box.createHorizontalStrut(500), BorderLayout.SOUTH);
    }
}
