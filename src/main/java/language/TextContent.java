package language;

import java.util.*;

public class TextContent {
    /*

        Singleton. Used to provide multiple translations for each text item.
        Contains enums that identifies languages and text items.

     */
    private final Map<Key, Map<Language, String>> texts;

    public enum Key {
        EDIT, EMITTED, YES, NO, AVAILABLE,
        NAME, COST, ADDRESS, PHONE, EMAIL, TAXID, SIREN,
        CONTACT, SOFT_DRINK, ALCOHOL, ENTREE, MEAL, DESSERT,
        DATE,
        MENU_PRODUCTS_EDITOR_BUTTON,
        MENU_TICKETS_EDITOR_BUTTON,
        MENU_RESTAURANT_EDITOR_BUTTON,
        RESTAURANT_EDITOR_LATE_PENALTY_POLICY_LABEL,
        TICKET_DISPLAY_TABLE_NUMBER_LABEL,
        TICKETS_EDITOR_NEW_TICKET_BUTTON,
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
        this.texts.put(Key.EDIT, make("Edit", "Editer"));
        this.texts.put(Key.EMITTED, make("Emitted", "Emit"));
        this.texts.put(Key.YES, make("yes", "oui"));
        this.texts.put(Key.NO, make("no", "non"));
        this.texts.put(Key.AVAILABLE, make("Available", "Disponible"));
        this.texts.put(Key.NAME, make("Name", "Nom"));
        this.texts.put(Key.COST, make("Cost", "Coût"));
        this.texts.put(Key.ADDRESS, make("Address", "Adresse"));
        this.texts.put(Key.PHONE, make("Phone number", "Numéro de téléphone"));
        this.texts.put(Key.EMAIL, make("EMail", "EMail"));
        this.texts.put(Key.TAXID, make("Tax ID", "Numéro de TVA"));
        this.texts.put(Key.SIREN, make("SIREN", "SIREN"));
        this.texts.put(Key.CONTACT, make("Contact", "Contacte"));
        this.texts.put(Key.SOFT_DRINK, make("Soft Drink", "Boisson sans alcool"));
        this.texts.put(Key.ALCOHOL, make("Alcohol", "Alcool"));
        this.texts.put(Key.ENTREE, make("Entree", "Entrée"));
        this.texts.put(Key.MEAL, make("Meal", "Plat"));
        this.texts.put(Key.DESSERT, make("Dessert", "Dessert"));
        this.texts.put(Key.DATE, make("Date", "Date"));
        this.texts.put(Key.MENU_PRODUCTS_EDITOR_BUTTON, make("Product editor", "Editeur de produit"));
        this.texts.put(Key.MENU_TICKETS_EDITOR_BUTTON, make("Ticket editor", "Editeur de ticket"));
        this.texts.put(Key.MENU_RESTAURANT_EDITOR_BUTTON, make("Restaurant editor", "Editeur de restaurant"));
        this.texts.put(Key.RESTAURANT_EDITOR_LATE_PENALTY_POLICY_LABEL, make("Late penalty policy", "Politique de pénalité de retard"));
        this.texts.put(Key.TICKET_DISPLAY_TABLE_NUMBER_LABEL, make("Table number", "Numéro de table"));
        this.texts.put(Key.TICKETS_EDITOR_NEW_TICKET_BUTTON, make("New ticket", "Nouvelle commande"));
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
