package views;

import language.TextContent;
import logic.Logic;
import models.Ticket;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Date;

public class TicketsEditor extends JPanel {
    private JButton newTicketButton;
    private JPanel ticketsPanel;
    private JScrollPane ticketsScrollPane;

    public TicketsEditor() {
        super();
        this.initComponents();
        this.initLayout();
    }

    private void initComponents() {
        AppContext context = AppContext.getAppContext();
        TextContent textContent = TextContent.getTextContent();
        this.newTicketButton = new JButton(textContent.get(context.getLanguage(), TextContent.Key.TICKETS_EDITOR_NEW_TICKET_BUTTON));
        this.newTicketButton.addActionListener(_ -> {
            context.perform(entityManager -> {
                Ticket ticket = new Ticket(
                        false, context.getRestaurant(), new Date(), 0, new ArrayList<>()
                );
                Logic.addTicket(ticket, context.getRestaurant());
                entityManager.persist(ticket);
                this.ticketsPanel.add(new TicketDisplay(ticket));
                this.revalidate();
                this.repaint();
            });
        });
        this.ticketsPanel = new JPanel();
        this.ticketsScrollPane = new JScrollPane(this.ticketsPanel);

        for (Ticket ticket : context.getRestaurant().getTickets()) {
            this.ticketsPanel.add(new TicketDisplay(ticket));
        }
    }

    private void initLayout() {
        this.ticketsPanel.setLayout(new BoxLayout(this.ticketsPanel, BoxLayout.Y_AXIS));
        this.setLayout(new BorderLayout());
        this.add(this.newTicketButton, BorderLayout.NORTH);
        this.add(ticketsScrollPane, BorderLayout.CENTER);
        this.add(Box.createHorizontalStrut(500), BorderLayout.SOUTH);
    }
}
