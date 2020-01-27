package domotix.model.util;

import java.util.List;

public interface ObservableList<T> {
    void aggiungiOsservatore(ObserverList<T> oss);
    void rimuoviOsservatore(ObserverList<T> oss);
    List<ObserverList<T>> getOsservatori();
    void svuotaOsservatori();
    void informaRimozione(T dato);
    void informaAggiunta(T dato);
}
