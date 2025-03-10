package language;

import java.util.*;

/**
 *
 * Singleton. Used to provide multiple translations for each text item.
 * Contains enums that identifies languages and text items.
 *
 */
public class TextContent {
    private final Map<Key, Map<Language, String>> texts;

    public enum Key {
        EDIT, EMITTED, YES, NO, AVAILABLE,
        NAME, COST, ADDRESS, PHONE, EMAIL, TAXID, SIREN,
        CONTACT, SOFT_DRINK, ALCOHOL, ENTREE, MEAL, DESSERT,
        DATE, DUE_DATE, DEFAULT, CLIENT, TICKET, STATEMENT, TAGS,
        CLAIMED, NOT,

        MENU_PRODUCTS_EDITOR_BUTTON,
        MENU_MENUS_EDITOR_BUTTON,
        MENU_TICKETS_EDITOR_BUTTON,
        MENU_TICKETS_ARCHIVE_BUTTON,
        MENU_RESTAURANT_EDITOR_BUTTON,

        RESTAURANT_EDITOR_LATE_PENALTY_POLICY_LABEL,

        TICKET_DISPLAY_TABLE_NUMBER_LABEL,

        TICKETS_EDITOR_NEW_TICKET_BUTTON,

        TICKET_EDITOR_NO_LIVE_PRODUCT_WARNING,

        STATEMENT_EDITOR_LATE_PENALTY_POLICY_LABEL,

        PRODUCTS_EDITOR_NEW_PRODUCT_BUTTON,

        CLIENT_EDITOR_AUTO_COMPLETE_BUTTON,
        CLIENT_EDITOR_NO_AUTO_COMPLETE_BUTTON,
        CLIENT_EDITOR_NO_PROFILE_LABEL,
        CLIENT_EDITOR_PROFILE_LOCKED_LABEL,
        CLIENT_EDITOR_NEW_PROFILE_BUTTON,

        MENUS_EDITOR_NEW_MENU,
        MENUS_EDITOR_DESCRIPTION,

        CANNOT_CREATE_CLIENT, CANNOT_CREATE_LIVE_PRODUCT, CANNOT_CREATE_PRODUCT,
        CANNOT_CREATE_STATEMENT, CANNOT_CREATE_TICKET, CANNOT_CREATE_LIVE_MENU,
        CANNOT_CREATE_MENU_ITEM, CANNOT_CREATE_MENU,

        CANNOT_WRITE_DATABASE,

        SHARED_TABLE_NUMBER_WARNING,

        TAX_CONDITIONED, TAX_INSTANT, TAX_ALCOHOL
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
        this.texts.put(Key.DUE_DATE, make("Due date", "Date limite"));
        this.texts.put(Key.DEFAULT, make("Default", "Defaut"));
        this.texts.put(Key.CLIENT, make("Client", "Client"));
        this.texts.put(Key.TICKET, make("Ticket", "Commande"));
        this.texts.put(Key.STATEMENT, make("Statement", "Facture"));
        this.texts.put(Key.TAGS, make("Tags", "Etiquettes"));
        this.texts.put(Key.CLAIMED, make("Claimed", "Réclamé"));
        this.texts.put(Key.NOT, make("not", "non"));

        this.texts.put(Key.MENU_PRODUCTS_EDITOR_BUTTON, make("Product editor", "Editeur de produit"));
        this.texts.put(Key.MENU_MENUS_EDITOR_BUTTON, make("Menu editor", "Editeur de menu"));
        this.texts.put(Key.MENU_TICKETS_EDITOR_BUTTON, make("Ticket editor", "Editeur de commande"));
        this.texts.put(Key.MENU_TICKETS_ARCHIVE_BUTTON, make("Ticket archive", "Archive de commande"));
        this.texts.put(Key.MENU_RESTAURANT_EDITOR_BUTTON, make("Restaurant editor", "Editeur de restaurant"));

        this.texts.put(Key.RESTAURANT_EDITOR_LATE_PENALTY_POLICY_LABEL, make("Late penalty policy", "Politique de pénalité de retard"));

        this.texts.put(Key.TICKET_DISPLAY_TABLE_NUMBER_LABEL, make("Table number", "Numéro de table"));

        this.texts.put(Key.TICKETS_EDITOR_NEW_TICKET_BUTTON, make("New ticket", "Nouvelle commande"));

        this.texts.put(Key.TICKET_EDITOR_NO_LIVE_PRODUCT_WARNING, make("No product in category ", "Pas de produit dans la catégorie "));

        this.texts.put(Key.STATEMENT_EDITOR_LATE_PENALTY_POLICY_LABEL, make("Late penalty policy", "Politique de pénalité de retard"));

        this.texts.put(Key.PRODUCTS_EDITOR_NEW_PRODUCT_BUTTON, make("New product", "Nouveau produit"));

        this.texts.put(Key.CLIENT_EDITOR_AUTO_COMPLETE_BUTTON, make("Auto complete with: ", "Compléter automatiquement avec: "));
        this.texts.put(Key.CLIENT_EDITOR_NO_AUTO_COMPLETE_BUTTON, make("No auto complete available", "Pas de complétion disponible"));
        this.texts.put(Key.CLIENT_EDITOR_NO_PROFILE_LABEL, make("No profile", "Pas de profil"));
        this.texts.put(Key.CLIENT_EDITOR_PROFILE_LOCKED_LABEL, make("Modifying profile: ", "Modification du profile: "));
        this.texts.put(Key.CLIENT_EDITOR_NEW_PROFILE_BUTTON, make("New profile", "Nouveau profile"));

        this.texts.put(Key.MENUS_EDITOR_NEW_MENU, make("Create new menu", "Créer un nouveau menu"));
        this.texts.put(Key.MENUS_EDITOR_DESCRIPTION, make("Each section describes a set of available products (all tags must be present on the product)", "Chaque section décrit un ensemble de produits disponibles (toutes les étiquettes doivent être présentes sur le produit)"));

        this.texts.put(Key.CANNOT_CREATE_CLIENT, make("Cannot create client profile", "Impossible de créer un profil de client"));
        this.texts.put(Key.CANNOT_CREATE_LIVE_PRODUCT, make("No usable product", "Pas de produit utilisable"));
        this.texts.put(Key.CANNOT_CREATE_PRODUCT, make("No usable product", "Pas de produit utilisable"));
        this.texts.put(Key.CANNOT_CREATE_STATEMENT, make("Cannot create statement", "Impossible de créer la facture"));
        this.texts.put(Key.CANNOT_CREATE_TICKET, make("Cannot create ticket", "Impossible de créer la commande"));
        this.texts.put(Key.CANNOT_CREATE_LIVE_MENU, make("Cannot create menu", "Impossible de créer le menu"));
        this.texts.put(Key.CANNOT_CREATE_MENU_ITEM, make("Cannot create menu item", "Impossible de créer l'objet dans le menu"));
        this.texts.put(Key.CANNOT_CREATE_MENU, make("Cannot create menu", "Impossible de créer le menu"));

        this.texts.put(Key.CANNOT_WRITE_DATABASE, make("Cannot write in database", "Impossible d'écrire dans la base de données"));
        
        this.texts.put(Key.SHARED_TABLE_NUMBER_WARNING, make("Two tables share the same number", "Deux tables partagent le même numéro"));

        this.texts.put(Key.TAX_INSTANT, make("Instant", "Instantané"));
        this.texts.put(Key.TAX_CONDITIONED, make("Conditioned", "Conditionné"));
        this.texts.put(Key.TAX_ALCOHOL, make("Alcohol", "Alcool"));
    }

    static private Map<Language, String> make(String en, String fr) {
        Map<Language, String> map = new HashMap<>();
        map.put(Language.EN, en);
        map.put(Language.FR, fr);
        return map;
    }

    public String get(Language language, Key key) {
        return this.texts.get(key).get(language);
    }
}
