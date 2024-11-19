package views;

import language.TextContent;
import models.Restaurant;

import javax.swing.*;
import java.awt.*;

public class RestaurantEditor extends JPanel {
    private final Restaurant restaurant;
    private JTextField nameTextField;
    private JLabel nameLabel;
    private JTextField addressTextField;
    private JLabel addressLabel;
    private JTextField phoneTextField;
    private JLabel phoneLabel;
    private JTextField emailTextField;
    private JLabel emailLabel;
    private JTextField taxIDTextField;
    private JLabel taxIDLabel;
    private JTextField SIRENTextField;
    private JLabel SIRENLabel;
    private JTextField latePenaltyPolicyField;
    private JLabel latePenaltyPolicyLabel;

    public RestaurantEditor(Restaurant restaurant) {
        this.restaurant = restaurant;
        this.initComponents();
        this.initLayout();
    }

    private void initComponents() {
        AppContext context = AppContext.getAppContext();
        TextContent textContent = TextContent.getTextContent();
        this.nameLabel = new JLabel(textContent.get(context.getLanguage(), TextContent.Key.RESTAURANT_EDITOR_NAME_LABEL));
        this.nameTextField = new JTextField(restaurant.getName());
        this.nameTextField.addKeyListener(new Validate(this.nameTextField, text -> {
            context.perform(_ -> { this.restaurant.setName(text); });
        }));
        this.addressLabel = new JLabel(textContent.get(context.getLanguage(), TextContent.Key.RESTAURANT_EDITOR_ADDRESS_LABEL));
        this.addressTextField = new JTextField(restaurant.getAddress());
        this.addressTextField.addKeyListener(new Validate(this.addressTextField, text -> {
            context.perform(_ -> { this.restaurant.setAddress(text); });
        }));
        this.phoneLabel = new JLabel(textContent.get(context.getLanguage(), TextContent.Key.RESTAURANT_EDITOR_PHONE_LABEL));
        this.phoneTextField = new JTextField(restaurant.getPhone());
        this.phoneTextField.addKeyListener(new Validate(this.phoneTextField, text -> {
            context.perform(_ -> { this.restaurant.setPhone(text); });
        }));
        this.emailLabel = new JLabel(textContent.get(context.getLanguage(), TextContent.Key.RESTAURANT_EDITOR_EMAIL_LABEL));
        this.emailTextField = new JTextField(restaurant.getEmail());
        this.emailTextField.addKeyListener(new Validate(this.emailTextField, text -> {
            context.perform(_ -> { this.restaurant.setEmail(text); });
        }));
        this.taxIDLabel = new JLabel(textContent.get(context.getLanguage(), TextContent.Key.RESTAURANT_EDITOR_TAXID_LABEL));
        this.taxIDTextField = new JTextField(restaurant.getTaxID());
        this.taxIDTextField.addKeyListener(new Validate(this.taxIDTextField, text -> {
            context.perform(_ -> { this.restaurant.setTaxID(text); });
        }));
        this.SIRENLabel = new JLabel(textContent.get(context.getLanguage(), TextContent.Key.RESTAURANT_EDITOR_SIREN_LABEL));
        this.SIRENTextField = new JTextField(restaurant.getSIREN());
        this.SIRENTextField.addKeyListener(new Validate(this.SIRENTextField, text -> {
            context.perform(_ -> { this.restaurant.setSIREN(text); });
        }));
        this.latePenaltyPolicyLabel = new JLabel(textContent.get(context.getLanguage(), TextContent.Key.RESTAURANT_EDITOR_LATE_PENALTY_POLICY_LABEL));
        this.latePenaltyPolicyField = new JTextField(restaurant.getLatePenaltyPolicy());
        this.latePenaltyPolicyField.addKeyListener(new Validate(this.latePenaltyPolicyField, text -> {
            context.perform(_ -> { this.restaurant.setLatePenaltyPolicy(text); });
        }));
    }

    private void initLayout() {
        this.setLayout(new GridLayout(0, 2));
        this.add(this.nameLabel);
        this.add(this.nameTextField);
        this.add(this.addressLabel);
        this.add(this.addressTextField);
        this.add(this.phoneLabel);
        this.add(this.phoneTextField);
        this.add(this.emailLabel);
        this.add(this.emailTextField);
        this.add(this.taxIDLabel);
        this.add(this.taxIDTextField);
        this.add(this.SIRENLabel);
        this.add(this.SIRENTextField);
        this.add(this.latePenaltyPolicyLabel);
        this.add(this.latePenaltyPolicyField);
    }
}
