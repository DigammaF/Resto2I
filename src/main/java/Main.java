import views.AppContext;
import views.MainView;

import java.util.Arrays;
import java.util.Objects;

public class Main {
    public static void main(String[] args) {
        AppContext.getAppContext(Objects.equals(args[0], "demoMode"));
        MainView view = new MainView();
        view.setVisible(true);
    }
}
