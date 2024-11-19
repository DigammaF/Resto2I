package logic;

import jakarta.persistence.*;
import jakarta.persistence.criteria.*;
import jakarta.persistence.metamodel.Metamodel;

import java.util.List;
import java.util.Map;

public class DemoEntityManager implements EntityManager {
    public DemoEntityManager() {}
    public void persist(Object entity) {}
    public void remove(Object entity) {}
    public void flush() {}
    public void close() {}
    public void refresh(Object entity) {}
    @Override
    public <T> T merge(T t) { return null; }

    @Override
    public CacheStoreMode getCacheStoreMode() {
        return null;
    }

    @Override
    public Map<String, Object> getProperties() {
        return Map.of();
    }

    @Override
    public Query createQuery(String s) {
        return null;
    }

    @Override
    public <T> TypedQuery<T> createQuery(CriteriaQuery<T> criteriaQuery) {
        return null;
    }

    @Override
    public <T> TypedQuery<T> createQuery(CriteriaSelect<T> criteriaSelect) {
        return null;
    }

    @Override
    public Query createQuery(CriteriaUpdate<?> criteriaUpdate) {
        return null;
    }

    @Override
    public Query createQuery(CriteriaDelete<?> criteriaDelete) {
        return null;
    }

    @Override
    public <T> TypedQuery<T> createQuery(String s, Class<T> aClass) {
        return null;
    }

    @Override
    public Query createNamedQuery(String s) {
        return null;
    }

    @Override
    public <T> TypedQuery<T> createNamedQuery(String s, Class<T> aClass) {
        return null;
    }

    @Override
    public <T> TypedQuery<T> createQuery(TypedQueryReference<T> typedQueryReference) {
        return null;
    }

    @Override
    public Query createNativeQuery(String s) {
        return null;
    }

    @Override
    public <T> Query createNativeQuery(String s, Class<T> aClass) {
        return null;
    }

    @Override
    public Query createNativeQuery(String s, String s1) {
        return null;
    }

    @Override
    public StoredProcedureQuery createNamedStoredProcedureQuery(String s) {
        return null;
    }

    @Override
    public StoredProcedureQuery createStoredProcedureQuery(String s) {
        return null;
    }

    @Override
    public StoredProcedureQuery createStoredProcedureQuery(String s, Class<?>... classes) {
        return null;
    }

    @Override
    public StoredProcedureQuery createStoredProcedureQuery(String s, String... strings) {
        return null;
    }

    @Override
    public void joinTransaction() {

    }

    @Override
    public boolean isJoinedToTransaction() {
        return false;
    }

    @Override
    public <T> T unwrap(Class<T> aClass) {
        return null;
    }

    @Override
    public Object getDelegate() {
        return null;
    }

    @Override
    public boolean isOpen() {
        return false;
    }

    @Override
    public EntityTransaction getTransaction() {
        return null;
    }

    @Override
    public EntityManagerFactory getEntityManagerFactory() {
        return null;
    }

    @Override
    public Metamodel getMetamodel() {
        return null;
    }

    @Override
    public <T> EntityGraph<T> createEntityGraph(Class<T> aClass) {
        return null;
    }

    @Override
    public EntityGraph<?> createEntityGraph(String s) {
        return null;
    }

    @Override
    public EntityGraph<?> getEntityGraph(String s) {
        return null;
    }

    @Override
    public <T> List<EntityGraph<? super T>> getEntityGraphs(Class<T> aClass) {
        return List.of();
    }

    @Override
    public <C> void runWithConnection(ConnectionConsumer<C> connectionConsumer) {

    }

    @Override
    public <C, T> T callWithConnection(ConnectionFunction<C, T> connectionFunction) {
        return null;
    }

    @Override
    public CacheRetrieveMode getCacheRetrieveMode() {
        return null;
    }

    @Override
    public CriteriaBuilder getCriteriaBuilder() {
        return null;
    }

    @Override
    public void setProperty(String s, Object o) {

    }

    @Override
    public void setCacheStoreMode(CacheStoreMode cacheStoreMode) {

    }

    @Override
    public void setCacheRetrieveMode(CacheRetrieveMode cacheRetrieveMode) {

    }

    @Override
    public LockModeType getLockMode(Object o) {
        return null;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public void detach(Object o) {

    }

    @Override
    public void clear() {

    }

    @Override
    public void refresh(Object o, LockModeType lockModeType, Map<String, Object> map) {

    }

    @Override
    public void refresh(Object o, LockModeType lockModeType) {

    }

    @Override
    public void refresh(Object o, RefreshOption... refreshOptions) {

    }

    @Override
    public void refresh(Object o, Map<String, Object> map) {

    }

    @Override
    public void lock(Object o, LockModeType lockModeType, LockOption... lockOptions) {

    }

    @Override
    public void lock(Object o, LockModeType lockModeType, Map<String, Object> map) {

    }

    @Override
    public void lock(Object o, LockModeType lockModeType) {

    }

    @Override
    public FlushModeType getFlushMode() {
        return null;
    }

    @Override
    public <T> T find(Class<T> aClass, Object o) {
        return null;
    }

    @Override
    public <T> T find(Class<T> aClass, Object o, Map<String, Object> map) {
        return null;
    }

    @Override
    public <T> T find(Class<T> aClass, Object o, FindOption... findOptions) {
        return null;
    }

    @Override
    public <T> T find(EntityGraph<T> entityGraph, Object o, FindOption... findOptions) {
        return null;
    }

    @Override
    public <T> T find(Class<T> aClass, Object o, LockModeType lockModeType) {
        return null;
    }

    @Override
    public <T> T find(Class<T> aClass, Object o, LockModeType lockModeType, Map<String, Object> map) {
        return null;
    }

    @Override
    public <T> T getReference(T t) {
        return null;
    }

    @Override
    public <T> T getReference(Class<T> aClass, Object o) {
        return null;
    }

    @Override
    public void setFlushMode(FlushModeType flushModeType) {

    }
}
