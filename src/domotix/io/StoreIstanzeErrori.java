package domotix.io;

import java.util.*;

/**
 * Classe per emulare uno stack per le istanze andate in errore nel salvataggio
 *
 * @author paolopasqua
 * @see OperazioniInizialiFinali
 */
public class StoreIstanzeErrori {
    private static StoreIstanzeErrori instance = null;

    /**
     * Recupera la unica istanza della classe
     * @return  unica istanza della classe
     */
    public static StoreIstanzeErrori getInstance() {
        if (instance == null)
            instance = new StoreIstanzeErrori();
        return instance;
    }

    private HashMap<String, Class> entita;

    public StoreIstanzeErrori() {
        entita = new HashMap<>();
    }

    /**
     * Inserisce una nuova istanza in errore. Ne viene necessario indicare l'identificativo dell'entita' e il tipo (oggetto Class)
     * @param idEntita  Identificativo dell'entita' in errore
     * @param classe    Tipo dell'entita' in errore
     */
    public void put(String idEntita, Class classe) {
        entita.put(idEntita, classe);
    }

    /**
     * Ritorna tutto il contenuto dello stack ripulendolo.
     * @return  Insieme di coppie Identificativo-Tipo contenuti nello stack
     */
    public Set<Map.Entry<String, Class>> popAll() {
        Set<Map.Entry<String, Class>> returnValue = entita.entrySet();
        entita = new HashMap<>();
        return returnValue;
    }

    /**
     * Ripulisce lo stack
     */
    public void clear() {
        entita.clear();
    }
}
