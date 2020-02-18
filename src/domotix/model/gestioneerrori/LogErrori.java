package domotix.model.gestioneerrori;

import domotix.controller.OperazioniIniziali;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe per emulare uno stack per i messaggi di errore che possono presentarsi nella fase di lettura e scrittura dei dati.
 *
 * @author paolopasqua
 * @see OperazioniIniziali
 */
public class LogErrori {

    private static LogErrori instance = null;

    /**
     * Recupera la unica istanza della classe
     * @return  unica istanza della classe
     */
    public static LogErrori getInstance() {
        if (instance == null)
            instance = new LogErrori();
        return instance;
    }

    private ArrayList<String> errori;

    public LogErrori() {
        errori = new ArrayList<>();
    }

    /**
     * Inserisce un nuovo messaggio di errore.
     * @param messaggio  Messaggio di errore
     */
    public void put(String messaggio) {
        errori.add(messaggio);
    }

    /**
     * Ritorna tutto il contenuto dello stack ripulendolo.
     * @return  Insieme di stringhe messaggio contenuti nello stack
     */
    public List<String> popAll() {
        ArrayList<String> tmp = (ArrayList<String>)errori.clone();
        errori.clear();
        return tmp;
    }

    /**
     * Ripulisce lo stack
     */
    public void clear() {
        errori.clear();
    }

}
