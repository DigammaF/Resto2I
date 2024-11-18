package logic;

public interface Observer<T> {
    void onUpdate(T event);
}
