package domotix.model.util;

import java.util.List;

public interface ListaOsservabile<T> {
    void aggiungiOsservatore(OsservatoreLista<T> oss);
    void rimuoviOsservatore(OsservatoreLista<T> oss);
    List<OsservatoreLista<T>> getOsservatori();
    void svuotaOsservatori();
    void informaRimozione(T dato);
    void informaAggiunta(T dato);
}
