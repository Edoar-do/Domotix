package domotix.model.util;

import domotix.controller.util.StringUtil;
import domotix.model.bean.device.Dispositivo;

import java.util.HashMap;
import java.util.Map;

/**
 * Classe per implementare l'interfaccia ObserverList su liste contenenti oggetti di tipo Dispositivo.
 * Realizza quindi un resoconto degli oggetti contenuti in piu' liste Dispositivo in modo da presentare un
 * elenco senza ripetizioni ed aggiornato.
 */
public class SommarioDispositivi implements Visitable, ObserverList<Dispositivo> {
    private Map<String, Dispositivo> elenco;

    /**
     * Costruttore di default per generare un sommario vuoto
     */
    public SommarioDispositivi() {
        this.elenco = new HashMap<>();
    }

    /**
     * Recupera il Dispositivo associato alla chiave indicata, se presente.
     * Altrimenti in caso di mancata associazione ritorna null.
     *
     * @param key   chiave identificativa del dispositivo da ritornare
     * @return  dispositivo associato alla chiave se presente, null altrimenti
     */
    public Dispositivo getDispositivo(String key) {
        return elenco.get(key);
    }

    /**
     * Ritorna un array di tutti i dispositivi contenuti nel sommario.
     *
     * @return  elenco di dispositivi
     */
    public Dispositivo[] getDispositivi() {
        return elenco.values().toArray(new Dispositivo[0]);
    }

    /**
     * Verifica la presenza di un dispositivo indicato dalla chiave passata come parametro.
     *
     * @param key   chiave di cui verificare la presenza
     * @return  true se la chiave identifica un dispositivo contenuto; false altrimenti
     */
    public boolean contains(String key) {
        return elenco.get(key) != null;
    }

    @Override
    public void elaboraRimozione(Dispositivo dato) {
        //lato osservatore

        //controllo presenza
        if (elenco.containsKey(dato.getNome())) {
            //rimuovo solo se il numero di associazioni e' zero
            if (dato.getNumeroAssociazioni() == 0)
                elenco.remove(dato.getNome());
        }
    }

    @Override
    public void elaboraAggiunta(Dispositivo dato) {
        //lato osservatore

        //se gia' contenuto allora non eseguo nulla
        if (!elenco.containsKey(dato.getNome())) {
            elenco.put(dato.getNome(), dato);
        }
    }

    @Override
    public Object fattiVisitare(Visitor v) {
        return v.visitaSommarioDispositivi(this);
    }
}
