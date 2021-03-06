package domotix.model;

import domotix.model.bean.regole.Azione;

import java.util.*;

/**
 * Classe per l'elenco di azioni programmate accodate e da eseguire.
 * L'elenco associa ad ogni Azione un id per poter rimuovere (una volta eseguita l'azione).
 *
 * @author paolopasqua
 */
public class ElencoAzioniProgrammate {

    private Map<String, Azione> azioni;

    public ElencoAzioniProgrammate() {
        azioni = new HashMap<>();
    }

    /**
     * Aggiunge l'azione in elenco generando un id univoco (utilizza UUID)
     * L'identificativo generato viene ritornato
     * @param a azione da aggiungere
     * @return  stringa contenente l'id generato che identifica l'azione
     */
    public String add(Azione a) {
        String id = UUID.randomUUID().toString();
        add(id, a);
        return id;
    }

    /**
     * Aggiunge un'azione in elenco con l'id indicato, se questo o l'azione non e'  gia' contenuto
     * @param id    stringa id identificativa
     * @param a     azione da aggiungere
     */
    public void add(String id, Azione a) {
        if (!azioni.containsKey(id) && !azioni.containsValue(a))
            azioni.put(id, a);
    }

    /**
     * Rimuove l'azione identificata dall'id se presente
     * @param id    stringa id per identificare l'azione
     */
    public void remove(String id) {
        azioni.remove(id);
    }

    /**
     * Controlla se l'id appartiene all'elenco
     * @param id    stringa id da controllare
     * @return  true: id appartiene all'elenco; false: altrimenti
     */
    public boolean contains(String id) {
        return azioni.containsKey(id);
    }

    /**
     * COntrolla se l'Azione indicata appartiene all'elenco (utilizza il metodo equals di Azione)
     * @param a istanza di Azione da controllare
     * @return  true: istanza appartiene all'elenco; false: altrimenti
     */
    public boolean contains(Azione a) {
        return azioni.containsValue(a);
    }
    /**
     * Recupera l'istanza di Azione identificata dall'id indicato dal parametro.
     * @param id  stringa per identificare l'azione
     * @return  istanza della azione se presente; null altrimenti
     */
    public Azione getAzione(String id) {
        return azioni.get(id);
    }

    /**
     * Recupera tutte le azioni salvate in elenco con l'id associato
     * @return  array di azioni contenute
     */
    public Map<String, Azione> getCoppieIdAzione(){
        Map<String, Azione> ret = new HashMap<>(azioni);
        azioni.forEach( (id, azione) -> ret.put(id, azione) );
        return ret;
    }

    /**
     * Recupera tutte le azioni salvate in elenco
     * @return  array di azioni contenute
     */
    public List<Azione> getAzioni(){
        return new ArrayList<Azione>(azioni.values());
    }

    /**
     * Ritorna un array di tutte gli id contenuti in elenco
     * @return  array di id contenuti
     */
    public List<String> getIdAzioni() {
        return new ArrayList<>(azioni.keySet());
    }

    /**
     * Ritorna se l'elenco risulta vuoto o meno
     * @return  true: elenco e' vuoto; false: altrimenti
     */
    public boolean isEmpty() { return azioni.isEmpty(); }
}
