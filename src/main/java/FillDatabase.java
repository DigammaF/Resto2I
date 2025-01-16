import views.AppContext;

public class FillDatabase {
    public static void main(String[] args) {
        AppContext context = AppContext.getAppContext();
        context.perform(_ -> context.getRestaurant().fillWithDummyValues());
    }
}
