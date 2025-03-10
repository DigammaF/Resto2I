package views;

import events.TicketEditorEvent;
import language.TextContent;
import logic.Observer;
import models.Statement;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import views.style.EditorPanel;

import javax.swing.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.Properties;

public class StatementEditor extends EditorPanel implements Observer<TicketEditorEvent> {
    private final Statement statement;
    private JLabel amountLabel;
    private JLabel dateLabel;
    private JDatePickerImpl datePicker;
    private JLabel latePenaltyLabel;
    private JTextField latePenaltyField;

    public static class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {
        private final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd MM yyyy");

        @Override
        public Object stringToValue(String text) throws ParseException {
            return dateFormatter.parse(text);
        }

        @Override
        public String valueToString(Object value) {
            if (value instanceof Date) {
                Calendar date = (Calendar) value;
                return dateFormatter.format(date.getTime());
            }
            return "";
        }
    }

    public StatementEditor(Statement statement) {
        super();
        this.statement = statement;
        this.initComponents();
        this.initLayout();
    }

    private void initComponents() {
        AppContext context = AppContext.getAppContext();
        TextContent textContent = TextContent.getTextContent();
        this.updateStatementAmount();
        this.amountLabel = new JLabel(this.getAmountLabelText());
        this.dateLabel = new JLabel(textContent.get(context.getLanguage(), TextContent.Key.DUE_DATE));
        UtilDateModel utilDateModel = new UtilDateModel();
        utilDateModel.setValue(this.statement.getDue());
        utilDateModel.setSelected(true);
        JDatePanelImpl datePanel = new JDatePanelImpl(utilDateModel, new Properties());
        this.datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        this.latePenaltyLabel = new JLabel(textContent.get(context.getLanguage(), TextContent.Key.STATEMENT_EDITOR_LATE_PENALTY_POLICY_LABEL));
        this.latePenaltyField = new JTextField();
        this.latePenaltyField.setText(this.statement.getLatePenalty());
        this.latePenaltyField.addKeyListener(new Validate(this.latePenaltyField, text -> context.perform(_ -> this.statement.setLatePenalty(text))));
    }

    private void initLayout() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(this.amountLabel);
        JPanel datePanel = new JPanel();
        datePanel.setLayout(new BoxLayout(datePanel, BoxLayout.X_AXIS));
        datePanel.add(this.dateLabel);
        datePanel.add(this.datePicker);
        this.add(datePanel);
        JPanel latePenaltyPolicyPanel = new JPanel();
        latePenaltyPolicyPanel.setLayout(new BoxLayout(latePenaltyPolicyPanel, BoxLayout.X_AXIS));
        latePenaltyPolicyPanel.add(this.latePenaltyLabel);
        latePenaltyPolicyPanel.add(this.latePenaltyField);
        this.add(latePenaltyPolicyPanel);
        this.add(Box.createHorizontalStrut(500));
    }

    private void updateStatementAmount() {
        AppContext.getAppContext().perform(_ -> this.statement.setAmount(this.statement.getTicket().getTotalATICost()));
    }

    private String getAmountLabelText() {
        return String.format("%.2f€", this.statement.getAmount());
    }

    @Override
    public void onEvent(TicketEditorEvent event) {
        if (Objects.requireNonNull(event) == TicketEditorEvent.COST_CHANGE) {
            this.updateStatementAmount();
            this.amountLabel.setText(this.getAmountLabelText());
        }
    }
}
