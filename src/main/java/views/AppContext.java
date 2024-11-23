package views;

import jakarta.persistence.*;
import language.TextContent;
import logic.DemoEntityManager;
import models.Restaurant;

/**
 *
 * <p>
 * Singleton meant to offer global access to important resources
 * Can be started in demo mode, which is a kind of offline mode:
 * in this mode, the application doesn't rely on the existence of
 * an external database anymore.
 * </p>
 * <p>
 * Use <code>.perform</code> to perform an action that requires an <code>EntityManager Transaction</code>.
 * </p>
 *
 */
public class AppContext {
    private TextContent.Language language;
    private final EntityManagerFactory entityManagerFactory;
    private final EntityManager entityManager;
    private Restaurant restaurant;
    private MainView mainView;
    private boolean demoMode;

    public boolean isDemoMode() {
        return demoMode;
    }

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
        if (appContext == null) { appContext = new AppContext(false); }
        return appContext;
    }

    public static AppContext getAppContext(boolean demoMode) {
        if (appContext == null) { appContext = new AppContext(demoMode); }
        return appContext;
    }

    private AppContext(boolean demoMode) {
        this.demoMode = demoMode;
        this.language = TextContent.Language.FR;

        if (this.demoMode) {
            this.entityManagerFactory = null;
            this.entityManager = new DemoEntityManager();
            this.restaurant = new Restaurant();
        } else {
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
    }

    @FunctionalInterface
    public interface PersistenceAction {
        void execute(EntityManager entityManager);
    }

    /**
     *
     * <p>
     * Use .perform to perform an action that requires an EntityManager Transaction.
     * Just remember that an action has to be fully performed before the next one starts!
     * <br/>
     * <strong>No nested transactions!</strong>
     * <br/>
     * Therefore, one shouldn't trigger an Observable event in a perform callback, as the event will
     * immediately trigger some method calls: those methods are not supposed to be aware
     * of this context, and might want to perform transactions of their own, crashing the
     * application.
     * <br/>
     * Use the following pattern
     * <p><code>
     *     if (context.perform(...)) { notifyObservers(...) }
     * </code></p>
     * </p>
     *
     * @param action EntityManager -> void
     * @return true if the transaction was commited, false if it was rollback
     */
    public boolean perform(PersistenceAction action) {
        if (this.demoMode) { action.execute(this.entityManager); return true; }
        try {
            this.entityManager.getTransaction().begin();
            action.execute(entityManager);
            this.entityManager.getTransaction().commit();
        } catch (Exception exception) { this.entityManager.getTransaction().rollback(); exception.printStackTrace(); return false; }
        return true;
    }
}
