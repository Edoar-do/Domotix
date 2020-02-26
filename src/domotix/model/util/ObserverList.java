package domotix.model.util;

/**
 * Interfaccia per descrivere una classe osservatrice di un oggetto ObservableList dello stesso tipo parametrico.
 *
 * @param <T>   tipo di elementi contenuti negli oggetti ObservableList
 * @see ObservableList
 */
public interface ObserverList<T> {
    /**
     * Metodo per l'elaborazione di una rimozione dell'oggetto indicato come parametro.
     *
     * @param dato  oggetto di cui si e' effettuata la rimozione.
     */
    void elaboraRimozione(T dato);

    /**
     * Metodo per l'elaborazione di un'aggiunta dell'oggetto indicato come parametro.
     * @param dato  oggetto di cui si e' effettuata l'aggiunta
     */
    void elaboraAggiunta(T dato);
}
