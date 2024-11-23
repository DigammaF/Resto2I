import views.AppContext;
import views.MainView;

public class Main {
    public static void main(String[] args) {
        AppContext.getAppContext(true);
        MainView view = new MainView();
        view.setVisible(true);
    }
}
