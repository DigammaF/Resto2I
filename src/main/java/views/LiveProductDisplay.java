package views;

import events.LiveProductDisplayEvents;
import events.LiveProductDisplayEvents.LiveProductDisplayEvent;
import logic.Logic;
import logic.Observable;
import logic.Observer;
import logic.ProductType;
import models.LiveProduct;
import models.Product;
import views.style.FlatButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.Vector;

public class LiveProductDisplay extends JPanel
        implements Observable<LiveProductDisplayEvent>
{
    private final LiveProduct liveProduct;
    private final ProductFilter productFilter;
    private JComboBox<Product> productsComboBox;
    private JLabel countLabel;
    private JTextField countField;
    private JButton removeButton;

    private final ArrayList<Observer<LiveProductDisplayEvent>> liveProductDisplayEventObservers;

    @FunctionalInterface
    public interface ProductFilter {
        boolean execute(Product product);
    }

    public LiveProductDisplay(LiveProduct liveProduct, ProductFilter productFilter) {
        super();
        this.liveProductDisplayEventObservers = new ArrayList<>();
        this.liveProduct = liveProduct;
        this.productFilter = productFilter;
        this.initComponents();
        this.initLayout();

        SwingUtilities.invokeLater(this::initSizes);
    }

    public LiveProductDisplay(LiveProduct liveProduct) {
        super();
        this.liveProductDisplayEventObservers = new ArrayList<>();
        this.liveProduct = liveProduct;
        this.productFilter = _ -> true;
        this.initComponents();
        this.initLayout();

        SwingUtilities.invokeLater(this::initSizes);

    }

    private void initComponents() {
        AppContext context = AppContext.getAppContext();
        ComboBoxModel<Product> productsModel = new DefaultComboBoxModel<>(
                new Vector<>(
                        context.getRestaurant().getProducts()
                                .stream().filter(product -> product.isUsed() && this.productFilter.execute(product))
                                .toList()
                )
        );
        this.productsComboBox = new JComboBox<>(productsModel);
        this.productsComboBox.addItemListener(event -> {
            if (event.getStateChange() == ItemEvent.SELECTED) {
                this.liveProduct.setProduct((Product) event.getItem());
                this.notifyObservers(new LiveProductDisplayEvents.CostChanged());
            }
        });
        this.productsComboBox.setSelectedItem(liveProduct.getProduct());
        this.countLabel = new JLabel("X");
        this.countField = new JTextField();

        this.countField.setText(Integer.toString(liveProduct.getCount()));

        this.countField.addKeyListener(new Validate(
                this.countField,
                text -> {
                    if (context.perform(_ -> this.liveProduct.setCount(Integer.parseInt(text)))) {
                        this.notifyObservers(new LiveProductDisplayEvents.CostChanged());
                        return true;
                    } else {
                        return false;
                    }
                }
        ));



        this.removeButton = new FlatButton("X");
        this.removeButton.addActionListener(_ -> {
            Logic.remLiveProduct(this.liveProduct.getTicket(), this.liveProduct);
            this.getParent().remove(this);
            context.getMainView().validate();
            context.getMainView().repaint();
            this.notifyObservers(new LiveProductDisplayEvents.CostChanged());
            this.notifyObservers(new LiveProductDisplayEvents.Removed(this));
        });




    }

    private void initLayout() {

        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.add(productsComboBox);
        this.add(Box.createHorizontalGlue());
        this.add(countLabel);
        this.add(countField);
        this.add(removeButton);


    }

    /** limit the size of countField*/
    private void initSizes(){
        this.revalidate();
        this.repaint();
        this.setVisible(true);

        Dimension labelDims = this.countLabel.getPreferredSize();
        Dimension countDims = this.countField.getPreferredSize();
        System.out.println(" count old Dims : " + countDims.width + "     "+ countDims.height );

        int newWidth = 3*(labelDims.width);

        this.countField.setPreferredSize(new Dimension(newWidth,countDims.height));
        countDims = this.countField.getPreferredSize();
        System.out.println(" new dims  : " + countDims.width + " "+ countDims.height );

        this.revalidate();
        this.repaint();
        this.setVisible(true);
    }

    public ProductFilter getProductFilter(ProductType productType) {
        return product -> product.getProductType() == productType;
    }

    @Override
    public void addObserver(Observer<LiveProductDisplayEvent> observer) {
        this.liveProductDisplayEventObservers.add(observer);
    }

    @Override
    public void removeObserver(Observer<LiveProductDisplayEvent> observer) {
        this.liveProductDisplayEventObservers.remove(observer);
    }

    @Override
    public void notifyObservers(LiveProductDisplayEvent event) {
        ArrayList<Observer<LiveProductDisplayEvent>> observers = new ArrayList<>(this.liveProductDisplayEventObservers);
        for (Observer<LiveProductDisplayEvent> observer: observers) { observer.onEvent(event); }
    }

    //only for debug atm
    public void setColor(){
        this.countLabel.setForeground(Color.MAGENTA);
        this.countField.setForeground(Color.MAGENTA);
        this.countLabel.setBackground(Color.CYAN);
        this.countField.setBackground(Color.CYAN);
    }
}
