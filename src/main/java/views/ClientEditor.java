package views;

import language.TextContent;
import models.Client;

import javax.swing.*;
import java.awt.*;

public class ClientEditor extends JPanel {
    private Client client;
    private JLabel nameLabel;
    private JTextField nameField;
    private JLabel taxIDLabel;
    private JTextField taxIDField;
    private JLabel contactLabel;
    private JTextField contactField;

    public ClientEditor(Client client) {
        super();
        this.client = client;
        this.initComponents();
        this.initLayout();
    }

    private void initComponents() {
        AppContext context = AppContext.getAppContext();
        TextContent textContent = TextContent.getTextContent();
        this.nameLabel = new JLabel(textContent.get(context.getLanguage(), TextContent.Key.NAME));
        this.nameField = new JTextField();
        this.nameField.setText(this.client.getName());
        this.nameField.addKeyListener(new Validate(this.nameField, text -> context.perform(_ -> this.client.setName(text))));
        this.taxIDLabel = new JLabel(textContent.get(context.getLanguage(), TextContent.Key.TAXID));
        this.taxIDField = new JTextField();
        this.taxIDField.setText(this.client.getTaxID());
        this.taxIDField.addKeyListener(new Validate(this.taxIDField, text -> context.perform(_ -> this.client.setTaxID(text))));
        this.contactLabel = new JLabel(textContent.get(context.getLanguage(), TextContent.Key.CONTACT));
        this.contactField = new JTextField();
        this.contactField.setText(this.client.getContact());
        this.contactField.addKeyListener(new Validate(this.contactField, text -> context.perform(_ -> this.client.setContact(text))));
    }

    private void initLayout() {
        this.setLayout(new GridLayout(0, 2));
        this.add(this.nameLabel); this.add(this.nameField);
        this.add(this.taxIDLabel); this.add(this.taxIDField);
        this.add(this.contactLabel); this.add(this.contactField);
        this.add(Box.createHorizontalStrut(500));
    }
}
