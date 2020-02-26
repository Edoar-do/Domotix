package domotix.model.util;

import java.util.List;

/**
 * Interfaccia per descrivere una classe lista osservabile da oggetti ObserverList dello stesso tipo parametrico.
 *
 * @param <T>   tipo di elementi contenuti dalla lista osservabile
 * @see ObservableList
 */
public interface ObservableList<T> {
    /**
     * Aggiunge un osservatore alla lista
     *
     * @param oss   osservatore da aggiungere
     */
    void aggiungiOsservatore(ObserverList<T> oss);

    /**
     * Rimuove un osservatore dalla lista
     *
     * @param oss   osservatore da rimuovere
     */
    void rimuoviOsservatore(ObserverList<T> oss);

    /**
     * Recupera l'elenco di osservatori legati alla lista
     *
     * @return  elenco di osservatori
     */
    List<ObserverList<T>> getOsservatori();

    /**
     * Rimuove tutti gli osservatori legati alla lista
     */
    void svuotaOsservatori();

    /**
     * Metodo per propagare a tutti gli osservatori legati alla lista la rimozione
     * di un oggetto del tipo parametrico trattato.
     *
     * @param dato  oggetto di cui si e' effettuata la rimozione
     */
    void informaRimozione(T dato);

    /**
     * Metodo per propagare a tutti gli osservatori legati alla lista l'aggiunta
     * di un oggetto del tipo parametrico trattato
     *
     * @param dato  oggetto di cui si e' effettuata l'aggiunta
     */
    void informaAggiunta(T dato);
}
