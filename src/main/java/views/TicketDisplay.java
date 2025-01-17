package views;

import language.TextContent;
import models.Ticket;
import views.style.Colors;
import views.style.FlatButton;

import javax.swing.*;
import java.awt.*;

public class TicketDisplay extends JPanel {
    private final Ticket ticket;
    private JLabel dateLabel;
    private JButton emittedButton;
    private JLabel tableNumberLabel;
    private JTextField tableNumberField;
    private JButton editButton;

    public TicketDisplay(Ticket ticket) {
        super();
        this.ticket = ticket;
        this.initComponents();
        this.initLayout();
    }

    private void initComponents() {
        AppContext context = AppContext.getAppContext();
        TextContent textContent = TextContent.getTextContent();
        this.dateLabel = new JLabel(this.ticket.getDate().toString());
        this.emittedButton = new FlatButton(this.getEmittedButtonText());
        this.emittedButton.addActionListener(_ -> {
            if (context.perform(_ -> this.ticket.setEmitted(!this.ticket.isEmitted()))) {
                this.emittedButton.setText(this.getEmittedButtonText());
            }
        });
        this.tableNumberLabel = new JLabel(textContent.get(context.getLanguage(), TextContent.Key.TICKET_DISPLAY_TABLE_NUMBER_LABEL));
        this.tableNumberField = new JTextField();
        this.tableNumberField.setText(Integer.toString(this.ticket.getTableNumber()));
        if (this.ticket.getTableNumber() == Ticket.DEFAULT_TABLE_NUMBER) { this.tableNumberField.setBackground(Colors.STRANGE_VALUE_FIELD); }
        this.tableNumberField.addKeyListener(new Validate(
                this.tableNumberField,
                text -> context.perform(_ -> this.ticket.setTableNumber(Integer.parseInt(text)))
        ).withPostAction(tableNumber -> {
            if (context.getRestaurant().getTickets().stream().anyMatch(
                    ticket -> !ticket.isEmitted() && ticket != this.ticket && String.valueOf(ticket.getTableNumber()).equals(tableNumber)
            )) {
                this.tableNumberField.setBackground(Colors.STRANGE_VALUE_FIELD);
                context.getMainView().println(textContent.get(context.getLanguage(), TextContent.Key.SHARED_TABLE_NUMBER_WARNING));
            }
        }));
        this.editButton = new FlatButton(textContent.get(context.getLanguage(), TextContent.Key.EDIT));
        this.editButton.addActionListener(_ -> {
            JPanel mainPanel = context.getMainView().getMainPanel();
            mainPanel.removeAll();
            mainPanel.setLayout(new FlowLayout());

            TicketEditor ticketEditor = new TicketEditor(this.ticket);
            mainPanel.add(new NamedPanel(textContent.get(context.getLanguage(), TextContent.Key.TICKET), ticketEditor));
            StatementEditor statementEditor = new StatementEditor(this.ticket.getStatement());
            ticketEditor.addObserver(statementEditor);
            mainPanel.add(new NamedPanel(textContent.get(context.getLanguage(), TextContent.Key.STATEMENT), statementEditor));
            mainPanel.add(new NamedPanel(textContent.get(context.getLanguage(), TextContent.Key.CLIENT), new ClientEditor(this.ticket.getStatement().getClient())));
            mainPanel.revalidate();
            mainPanel.repaint();
        });
    }

    private void initLayout() {
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.add(this.dateLabel);
        this.add(this.emittedButton);
        this.add(this.tableNumberLabel);
        this.add(this.tableNumberField);
        this.add(this.editButton);
    }

    private String getEmittedButtonText() {
        AppContext context = AppContext.getAppContext();
        TextContent textContent = TextContent.getTextContent();
        return textContent.get(context.getLanguage(), TextContent.Key.EMITTED) + ": " + (this.ticket.isEmitted()
                ? textContent.get(context.getLanguage(), TextContent.Key.YES)
                : textContent.get(context.getLanguage(), TextContent.Key.NO));
    }
}
