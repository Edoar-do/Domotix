package domotix.model.util;

import domotix.model.bean.device.Dispositivo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Classe per la gestione di un elenco di Dispositivo ed osservabile secondo l'interfaccia ObservableList
 *
 * @see Dispositivo
 * @see ObservableList
 */
public class ElencoDispositivi implements ObservableList<Dispositivo> {
    private Map<String, Dispositivo> elenco;
    private ArrayList<ObserverList<Dispositivo>> osservatori;

    /**
     * Costruttore che prevede un elenco iniziale di dispositivi da aggiungere come elenco in creazione
     * @param elencoIniziale    elenco di dispositivi con cui popolare l'elenco in creazione
     */
    public ElencoDispositivi(Map<String, Dispositivo> elencoIniziale) {
        elenco = elencoIniziale;
        osservatori = new ArrayList<>();
    }

    /**
     * Costruttore di default che genera un elenco di dispositivi vuoto
     */
    public ElencoDispositivi() {
        this(new HashMap<>());
    }

    /**
     * Recupera un dispositivo dalla chiave indicata.
     * Se la chiave e' inesistente allora ritornera' null.
     *
     * @param key   chiave con cui ricercare un dispositivo
     * @return  dispositivo associato alla chiave; null se chiave sconosciuta.
     */
    public Dispositivo getDispositivo(String key) {
        return elenco.get(key);
    }

    /**
     * Ritorna un array di tutti i dispositivi contenuti nell'elenco
     *
     * @return  array di tutti i dispositivi
     */
    public Dispositivo[] getDispositivi() {
        return elenco.values().toArray(new Dispositivo[0]);
    }

    /**
     * Verifica se la chiave indicata e' contenuta nell'elenco.
     *
     * @param key   chiave di cui verificare la presenza
     * @return  true: la chiave indicata e' presente in elenco; false altrimenti
     */
    public boolean contains(String key) {
        return elenco.get(key) != null;
    }

    /**
     * Rimuove il dispositivo indicato dalla chiave indicata, se presente in elenco.
     * Questa azione scatena il decremento del numero di associazioni sul dispositivo stesso e il
     * richiamo di tutti gli ObserverList associati all'elenco per informare la rimozione.
     *
     * @param key   chiave con cui identificare il dispositivo da rimuovere
     */
    public void remove(String key) {
        Dispositivo dispositivo = elenco.get(key);

        if (dispositivo != null) {
            //rimuovo solo se presente
            elenco.remove(key);

            //decremento il numero associazioni e informo gli osservatori
            dispositivo.decrNumAssociazioni();
            informaRimozione(dispositivo);
        }
    }

    /**
     * Rimuove il dispositivo indicato dal dispositivo indicato, utilizzando il nome come chiave.
     * Questa azione scatena il decremento del numero di associazioni sul dispositivo stesso e il
     * richiamo di tutti gli ObserverList associati all'elenco per informare la rimozione.
     *
     * @param dispositivo   dispositivo da rimuovere
     */
    public void remove(Dispositivo dispositivo) {
        this.remove(dispositivo.getNome());
    }

    /**
     * Aggiunge il dispositivo in elenco con la chiave associata indicata.
     * Viene svolto il controllo di presenza in modo da bloccare l'aggiunta se gia' presente.
     * Questa azione scatena l'incremento del numero di associazioni sul dispositivo stesso e il
     * richiamo di tutti gli ObserverList associati all'elenco per informare l'aggiunta.
     *
     * @param dispositivo   dispositivo da aggiungere
     * @param key   chiave con cui associare il dispositivo
     * @return  true: aggiunta andata a buon fine; false altrimenti (chiave gia' presente).
     */
    public boolean add(Dispositivo dispositivo, String key) {
        if (contains(key))
            return false;

        //se non presente aggiungo
        elenco.put(key, dispositivo);

        //incremento il numero di associazioni e informo gli osservatori
        dispositivo.incrNumAssociazioni();
        informaAggiunta(dispositivo);
        return true;
    }

    /**
     * Aggiunge il dispositivo in elenco utilizzando il nome come chiave associata.
     * Viene svolto il controllo di presenza in modo da bloccare l'aggiunta se gia' presente.
     * Questa azione scatena l'incremento del numero di associazioni sul dispositivo stesso e il
     * richiamo di tutti gli ObserverList associati all'elenco per informare l'aggiunta.
     *
     * @param dispositivo   dispositivo da aggiungere
     * @return  true: aggiunta andata a buon fine; false altrimenti (chiave gia' presente).
     */
    public boolean add(Dispositivo dispositivo) {
        return add(dispositivo, dispositivo.getNome());
    }

    @Override
    public void aggiungiOsservatore(ObserverList<Dispositivo> oss) {
        if (!osservatori.contains(oss)) {
            elenco.forEach((s, dispositivo) -> oss.elaboraAggiunta(dispositivo)); //in modo da informare immediatamente l'osservatore dei dati gia' contenuti
            osservatori.add(oss);
        }
    }

    @Override
    public void rimuoviOsservatore(ObserverList<Dispositivo> oss) {
        osservatori.remove(oss);
    }

    @Override
    public List<ObserverList<Dispositivo>> getOsservatori() {
        return osservatori;
    }

    @Override
    public void svuotaOsservatori() {
        osservatori.clear();
    }

    @Override
    public void informaRimozione(Dispositivo dato) {
        osservatori.forEach(osservatore -> osservatore.elaboraRimozione(dato));
    }

    @Override
    public void informaAggiunta(Dispositivo dato) {
        osservatori.forEach(osservatore -> osservatore.elaboraAggiunta(dato));
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        for (Dispositivo dispositivo : getDispositivi()) {
            buffer.append(dispositivo.toString() + "\n");
        }
        return buffer.toString();
    }
}
