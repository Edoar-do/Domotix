package domotix.model.util;

public interface ObserverList<T> {
    void elaboraRimozione(T dato);
    void elaboraAggiunta(T dato);
}
