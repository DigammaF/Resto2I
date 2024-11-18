package views;

import jakarta.persistence.*;
import language.TextContent;
import models.Restaurant;

public class AppContext {
    private TextContent.Language language;
    private final EntityManagerFactory entityManagerFactory;
    private final EntityManager entityManager;
    private Restaurant restaurant;
    private MainView mainView;

    public MainView getMainView() {
        return mainView;
    }

    public void setMainView(MainView mainView) {
        this.mainView = mainView;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public TextContent.Language getLanguage() {
        return language;
    }

    public void setLanguage(TextContent.Language language) {
        this.language = language;
    }

    private static AppContext appContext;

    public static AppContext getAppContext() {
        if (appContext == null) { appContext = new AppContext(); }
        return appContext;
    }

    private AppContext() {
        this.language = TextContent.Language.FR;
        this.entityManagerFactory = Persistence.createEntityManagerFactory("resto2I");
        this.entityManager = entityManagerFactory.createEntityManager();
        try {
            this.restaurant = this.entityManager.createQuery("SELECT restaurant FROM Restaurant restaurant", Restaurant.class).getSingleResult();
        } catch (NoResultException _) {
            this.perform(_ -> {
                this.restaurant = new Restaurant();
                entityManager.persist(this.restaurant);
            });
        }
    }

    @FunctionalInterface
    public interface PersistenceAction {
        void execute(EntityManager entityManager);
    }

    public boolean perform(PersistenceAction action) {
        try {
            this.entityManager.getTransaction().begin();
            action.execute(entityManager);
            this.entityManager.getTransaction().commit();
        } catch (Exception exception) { this.entityManager.getTransaction().rollback(); return false; }
        return true;
    }
}
