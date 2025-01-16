package logic;

import language.TextContent;
import views.AppContext;

public enum Tax {
    CONDITIONED,
    INSTANT,
    ALCOHOL;

    public double applied(double value) {
        return switch (this) {
            case CONDITIONED -> value * 1.055;
            case INSTANT -> value * 1.1;
            case ALCOHOL -> value * 1.2;
        };
    }

    @Override
    public String toString() {
        AppContext context = AppContext.getAppContext();
        TextContent textContent = TextContent.getTextContent();
        return switch (this) {
            case CONDITIONED -> "5.5% " + textContent.get(context.getLanguage(), TextContent.Key.TAX_CONDITIONED);
            case INSTANT -> "10% " + textContent.get(context.getLanguage(), TextContent.Key.TAX_INSTANT);
            case ALCOHOL -> "20% " + textContent.get(context.getLanguage(), TextContent.Key.TAX_ALCOHOL);
        };
    }
}
