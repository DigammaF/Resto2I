package logic;

import language.TextContent;
import views.AppContext;

public enum ProductType {
    SOFT_DRINK, ALCOHOL, ENTREE, MEAL, DESSERT;

    @Override
    public String toString() {
        AppContext context = AppContext.getAppContext();
        TextContent textContent = TextContent.getTextContent();
        return switch (this) {
            case SOFT_DRINK -> textContent.get(context.getLanguage(), TextContent.Key.SOFT_DRINK);
            case ALCOHOL -> textContent.get(context.getLanguage(), TextContent.Key.ALCOHOL);
            case ENTREE -> textContent.get(context.getLanguage(), TextContent.Key.ENTREE);
            case MEAL -> textContent.get(context.getLanguage(), TextContent.Key.MEAL);
            case DESSERT -> textContent.get(context.getLanguage(), TextContent.Key.DESSERT);
        };
    }
}
