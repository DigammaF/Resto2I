package views;

import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame {
    private Menu menu;
    private JPanel mainPanel;

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public MainView() {
        super();
        AppContext context  = AppContext.getAppContext();
        context.setMainView(this);
        this.setTitle("Resto2I");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.initComponents();
        this.initLayout();
    }

    private void initComponents() {
        this.menu = new Menu();
        this.mainPanel = new JPanel();
    }

    private void initLayout() {
        this.setLayout(new BorderLayout());
        this.add(this.menu, BorderLayout.WEST);
        this.add(this.mainPanel, BorderLayout.CENTER);
    }
}
