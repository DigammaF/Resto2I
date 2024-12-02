package views;

import language.TextContent;
import models.Client;
import models.Ticket;

import javax.swing.*;
import java.awt.*;

public class TicketDisplay extends JPanel {
    private Ticket ticket;
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
        this.emittedButton = new JButton(this.getEmittedButtonText());
        this.emittedButton.addActionListener(_ -> {
            if (context.perform(_ -> this.ticket.setEmitted(!this.ticket.isEmitted()))) {
                this.emittedButton.setText(this.getEmittedButtonText());
            }
        });
        this.tableNumberLabel = new JLabel(textContent.get(context.getLanguage(), TextContent.Key.TICKET_DISPLAY_TABLE_NUMBER_LABEL));
        this.tableNumberField = new JTextField();
        this.tableNumberField.setText(Integer.toString(this.ticket.getTableNumber()));
        this.tableNumberField.addKeyListener(new Validate(this.tableNumberField, text -> context.perform(_ -> this.ticket.setTableNumber(Integer.parseInt(text)))));
        this.editButton = new JButton(textContent.get(context.getLanguage(), TextContent.Key.EDIT));
        this.editButton.addActionListener(_ -> {
            JPanel mainPanel = context.getMainView().getMainPanel();
            mainPanel.removeAll();
            mainPanel.setLayout(new FlowLayout());

            TicketEditor ticketEditor = new TicketEditor(this.ticket);
            mainPanel.add(new NamedPanel("Ticket", ticketEditor));
            StatementEditor statementEditor = new StatementEditor(this.ticket.getStatement());
            ticketEditor.addObserver(statementEditor);
            mainPanel.add(new NamedPanel("Statement", statementEditor));
            mainPanel.add(new NamedPanel("Client", new ClientEditor(this.ticket.getStatement().getClient())));
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
