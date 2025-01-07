import views.AppContext;
import views.MainView;

import java.util.Objects;

public class Main {
    public static void main(String[] args) {
        AppContext.getAppContext(args.length == 0 || Objects.equals(args[0], "demoMode"));
        MainView view = new MainView();
        view.setVisible(true);
    }
}
