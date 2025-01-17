package logic;

import language.TextContent;
import views.AppContext;

import java.util.ArrayList;
import java.util.List;

public enum ProductType {
    DEFAULT, SOFT_DRINK, ALCOHOL, ENTREE, MEAL, DESSERT;

    private TextContent.Key getTextKey() {
        return switch (this) {
            case DEFAULT -> TextContent.Key.DEFAULT;
            case SOFT_DRINK -> TextContent.Key.SOFT_DRINK;
            case ALCOHOL -> TextContent.Key.ALCOHOL;
            case ENTREE -> TextContent.Key.ENTREE;
            case MEAL -> TextContent.Key.MEAL;
            case DESSERT -> TextContent.Key.DESSERT;
        };
    }

    @Override
    public String toString() {
        AppContext context = AppContext.getAppContext();
        TextContent textContent = TextContent.getTextContent();
        return textContent.get(context.getLanguage(), this.getTextKey());
    }

    public List<String> toAllStrings() {
        TextContent textContent = TextContent.getTextContent();
        List<String> list = new ArrayList<>();
        for (TextContent.Language language : TextContent.Language.values()) {
            list.add(textContent.get(language, this.getTextKey()));
        }
        return list;
    }
}
