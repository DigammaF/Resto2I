package views;

import language.TextContent;
import logic.Logic;
import models.Client;
import views.style.Colors;
import views.style.FlatButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Optional;

public class ClientEditor extends JPanel {
    private Client client;
    private JLabel nameLabel;
    private JTextField nameField;
    private JLabel taxIDLabel;
    private JTextField taxIDField;
    private JLabel contactLabel;
    private JTextField contactField;
    private JLabel autoCompleteLabel;
    private JButton autoCompleteButton;
    private Client autoCompleteTarget;
    private boolean autoCompleted;

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
        this.nameField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                Optional<Client> best = Logic.suggestBest(nameField.getText(), context.getRestaurant().getClients(), Client::getName);
                if (best.isPresent()) {
                    autoCompleteTarget = best.get();
                    autoCompleteButton.setText(
                            textContent.get(context.getLanguage(), TextContent.Key.CLIENT_EDITOR_AUTO_COMPLETE_BUTTON) + " " + autoCompleteTarget.getName()
                    );
                } else {
                    autoCompleteTarget = null;
                    autoCompleteLabel.setText(textContent.get(context.getLanguage(), TextContent.Key.CLIENT_EDITOR_NO_PROFILE_LABEL));
                    autoCompleteButton.setText(textContent.get(context.getLanguage(), TextContent.Key.CLIENT_EDITOR_NO_AUTO_COMPLETE_BUTTON));
                }
            }
        });
        this.taxIDLabel = new JLabel(textContent.get(context.getLanguage(), TextContent.Key.TAXID));
        this.taxIDField = new JTextField();
        this.taxIDField.setText(this.client.getTaxID());
        this.taxIDField.addKeyListener(new Validate(this.taxIDField, text -> context.perform(_ -> this.client.setTaxID(text))));
        this.contactLabel = new JLabel(textContent.get(context.getLanguage(), TextContent.Key.CONTACT));
        this.contactField = new JTextField();
        this.contactField.setText(this.client.getContact());
        this.contactField.addKeyListener(new Validate(this.contactField, text -> context.perform(_ -> this.client.setContact(text))));
        this.autoCompleteLabel = new JLabel(textContent.get(context.getLanguage(), TextContent.Key.CLIENT_EDITOR_NO_PROFILE_LABEL));
        this.autoCompleteButton = new FlatButton(textContent.get(context.getLanguage(), TextContent.Key.CLIENT_EDITOR_NO_AUTO_COMPLETE_BUTTON));
        this.autoCompleteButton.addActionListener(_ -> {
            if (this.autoCompleteTarget != null && !this.autoCompleted) {
                this.autoCompleteLabel.setText(
                        textContent.get(context.getLanguage(), TextContent.Key.CLIENT_EDITOR_PROFILE_LOCKED_LABEL) + " " + this.autoCompleteTarget.getName()
                );
                this.autoCompleteButton.setText(textContent.get(context.getLanguage(), TextContent.Key.CLIENT_EDITOR_NEW_PROFILE_BUTTON));
                this.nameField.setText(this.autoCompleteTarget.getName());
                this.nameField.setBackground(Colors.UNMODIFIED_FIELD);
                this.taxIDField.setText(this.autoCompleteTarget.getTaxID());
                this.taxIDField.setBackground(Colors.UNMODIFIED_FIELD);
                this.contactField.setText(this.autoCompleteTarget.getContact());
                this.contactField.setBackground(Colors.UNMODIFIED_FIELD);
                this.client = this.autoCompleteTarget;
                this.autoCompleted = true;
            }
            else if (this.autoCompleted) {
                this.autoCompleted = false;
                this.autoCompleteLabel.setText(textContent.get(context.getLanguage(), TextContent.Key.CLIENT_EDITOR_NO_PROFILE_LABEL));
                context.getRestaurant().createClient().ifPresentOrElse(
                        (client -> { this.client = client; Logic.addClient(context.getRestaurant(), this.client); }),
                        () -> { context.getMainView().println(textContent.get(context.getLanguage(), TextContent.Key.CANNOT_CREATE_CLIENT)); }
                );
            }
        });
        this.autoCompleted = false;
    }

    private void initLayout() {
        this.setLayout(new GridLayout(0, 2));
        this.add(this.autoCompleteLabel); this.add(this.autoCompleteButton);
        this.add(this.nameLabel); this.add(this.nameField);
        this.add(this.taxIDLabel); this.add(this.taxIDField);
        this.add(this.contactLabel); this.add(this.contactField);
        this.add(Box.createHorizontalStrut(500));
    }
}
