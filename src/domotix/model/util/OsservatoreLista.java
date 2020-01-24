package domotix.model.util;

public interface OsservatoreLista<T> {
    void elaboraRimozione(T dato);
    void elaboraAggiunta(T dato);
}
