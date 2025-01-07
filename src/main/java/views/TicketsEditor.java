package views;

import language.TextContent;
import models.Statement;
import models.Ticket;

import javax.swing.*;
import java.awt.*;

public class TicketsEditor extends JPanel {
    private JButton newTicketButton;
    private JPanel ticketsPanel;
    private JScrollPane ticketsScrollPane;

    private final TicketFilter ticketFilter;
    private final boolean allowNewTicket;

    @FunctionalInterface
    public interface TicketFilter {
        boolean execute(Ticket ticket);
    }

    public TicketsEditor() {
        super();
        this.allowNewTicket = true;
        this.ticketFilter = _ -> true;
        this.initComponents();
        this.initLayout();
    }

    public TicketsEditor(boolean allowNewTicket, TicketFilter ticketFilter) {
        super();
        this.allowNewTicket = allowNewTicket;
        this.ticketFilter = ticketFilter;
        this.initComponents();
        this.initLayout();
    }

    private void initComponents() {
        AppContext context = AppContext.getAppContext();
        TextContent textContent = TextContent.getTextContent();
        if (this.allowNewTicket) {
            this.newTicketButton = new JButton(textContent.get(context.getLanguage(), TextContent.Key.TICKETS_EDITOR_NEW_TICKET_BUTTON));
            this.newTicketButton.addActionListener(_ -> context.perform(entityManager -> {
                Ticket ticket = new Ticket();
                Statement statement = new Statement();
                if (context.getRestaurant().initTicketStatement(ticket, statement)) {
                    entityManager.persist(ticket);
                    entityManager.persist(statement);
                    entityManager.persist(statement.getClient());
                    this.ticketsPanel.add(new TicketDisplay(ticket));
                    this.revalidate();
                    this.repaint();
                } else {
                    context.getMainView().println(textContent.get(context.getLanguage(), TextContent.Key.CANNOT_CREATE_TICKET));
                }
            }));
        }
        this.ticketsPanel = new JPanel();
        this.ticketsScrollPane = new JScrollPane(this.ticketsPanel);

        for (Ticket ticket : context.getRestaurant().getTickets().stream().filter(this.ticketFilter::execute).toList()) {
            this.ticketsPanel.add(new TicketDisplay(ticket));
        }
    }

    private void initLayout() {
        this.ticketsPanel.setLayout(new BoxLayout(this.ticketsPanel, BoxLayout.Y_AXIS));
        this.setLayout(new BorderLayout());
        if (this.allowNewTicket) { this.add(this.newTicketButton, BorderLayout.NORTH); }
        this.add(ticketsScrollPane, BorderLayout.CENTER);
        this.add(Box.createHorizontalStrut(500), BorderLayout.SOUTH);
    }
}
