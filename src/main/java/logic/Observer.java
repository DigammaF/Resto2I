package logic;

public interface Observer<T> {
    void onEvent(T event);
}
