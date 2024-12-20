package views;

import views.style.StyledPanel;

import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame {
    private Menu menu;
    private StyledPanel mainPanel;

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
        this.mainPanel = new StyledPanel(15);
    }

    private void initLayout() {
        this.setLayout(new BorderLayout());
        this.add(this.menu, BorderLayout.WEST);
        this.add(this.mainPanel, BorderLayout.CENTER);
    }

    public void println(String text) { }
}
