package language;

import java.util.*;

public class TextContent {
    private final Map<Key, Map<Language, String>> texts;

    public enum Key {
        MENU_PRODUCTS_EDITOR_BUTTON,
        MENU_TICKETS_EDITOR_BUTTON,
        MENU_RESTAURANT_EDITOR_BUTTON,
        PRODUCT_DISPLAY_NAME_LABEL,
        RESTAURANT_EDITOR_NAME_LABEL,
        RESTAURANT_EDITOR_ADDRESS_LABEL,
        RESTAURANT_EDITOR_PHONE_LABEL,
        RESTAURANT_EDITOR_EMAIL_LABEL,
        RESTAURANT_EDITOR_TAXID_LABEL,
        RESTAURANT_EDITOR_SIREN_LABEL,
        RESTAURANT_EDITOR_LATE_PENALTY_POLICY_LABEL,
    }

    public enum Language {
        FR, EN
    }

    private static TextContent textContent;

    static public TextContent getTextContent() {
        if (textContent == null) { textContent = new TextContent(); }
        return textContent;
    }

    private TextContent() {
        this.texts = new HashMap<>();
        this.texts.put(Key.MENU_PRODUCTS_EDITOR_BUTTON, make("Product editor", "Editeur de produit"));
        this.texts.put(Key.MENU_TICKETS_EDITOR_BUTTON, make("Ticket editor", "Editeur de ticket"));
        this.texts.put(Key.MENU_RESTAURANT_EDITOR_BUTTON, make("Restaurant editor", "Editeur de restaurant"));
        this.texts.put(Key.PRODUCT_DISPLAY_NAME_LABEL, make("Name", "Nom"));
        this.texts.put(Key.RESTAURANT_EDITOR_NAME_LABEL, make("Name", "Nom"));
        this.texts.put(Key.RESTAURANT_EDITOR_ADDRESS_LABEL, make("Address", "Adresse"));
        this.texts.put(Key.RESTAURANT_EDITOR_PHONE_LABEL, make("Phone", "Téléphone"));
        this.texts.put(Key.RESTAURANT_EDITOR_EMAIL_LABEL, make("Email", "Email"));
        this.texts.put(Key.RESTAURANT_EDITOR_TAXID_LABEL, make("Tax ID", "Numéro de TVA"));
        this.texts.put(Key.RESTAURANT_EDITOR_SIREN_LABEL, make("SIREN", "SIREN"));
        this.texts.put(Key.RESTAURANT_EDITOR_LATE_PENALTY_POLICY_LABEL, make("Late penalty policy", "Politique de pénalité de retard"));
    }

    static private Map<Language, String> make(String en, String fr) {
        Map<Language, String> map = new HashMap<Language, String>();
        map.put(Language.EN, en);
        map.put(Language.FR, fr);
        return map;
    }

    public String get(Language language, Key key) {
        return this.texts.get(key).get(language);
    }
}
