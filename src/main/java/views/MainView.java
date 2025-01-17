package views;

import views.style.Colors;
import views.style.Paddings;
import views.style.StyledPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class MainView extends JFrame {
    private Menu menu;
    private StyledPanel mainPanel;
    private TextArea notificationsTextArea;
    private JPanel notificationsPanel;
    private JPanel westPanel;

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
        this.initSizes();
        this.initStyle();
        this.addResizeListener();

    }

    private void initComponents() {
        this.menu = new Menu();
        this.mainPanel = new StyledPanel(15);
        this.notificationsTextArea = new TextArea();
        this.notificationsTextArea.setEditable(false);
        this.westPanel = new JPanel();

        this.westPanel = new JPanel();
        this.notificationsPanel = new JPanel(new BorderLayout());

        this.notificationsPanel.add(this.notificationsTextArea);
    }

    protected void initStyle(){
        westPanel.setBackground(Colors.SECONDARY_BACKGROUND_COLOR);
    }

    private void initLayout() {
        this.setLayout(new BorderLayout());

        this.westPanel.setLayout(new BoxLayout(this.westPanel, BoxLayout.Y_AXIS));

        this.westPanel.add(this.menu);

        //empty space
        this.westPanel.add(Box.createVerticalGlue());

        this.add(this.westPanel, BorderLayout.WEST);
        this.add(this.mainPanel, BorderLayout.CENTER);

        this.westPanel.add(this.notificationsPanel);

        updateNotificationPanelHeight();
    }

    private void initSizes(){

        int mainViewWidth = this.getWidth();

       // int menuWidth = (int) (mainViewWidth * 0.5);
        this.westPanel.setPreferredSize(new Dimension(200, this.getHeight()));

        this.westPanel.revalidate();
        this.westPanel.repaint();

    }

    private void addResizeListener() {
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                updateNotificationPanelHeight();
            }
        });
    }

    private void updateNotificationPanelHeight() {
        int notificationHeight = (int) (this.getHeight() * 0.2); // 20%
        this.notificationsPanel.setPreferredSize(new Dimension(westPanel.getWidth(), notificationHeight));
        this.notificationsPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, notificationHeight));
        this.notificationsPanel.setMinimumSize(new Dimension(0, notificationHeight));

        this.westPanel.revalidate();
        this.westPanel.repaint();
    }

    public void println(String text) {
        this.notificationsTextArea.append(text + "\n");
    }
}
