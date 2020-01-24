package domotix.model.util;

import domotix.model.bean.device.Dispositivo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ElencoDispositivi implements ListaOsservabile<Dispositivo>, OsservatoreLista<Dispositivo> {
    private Map<String, Dispositivo> elenco;

    private boolean ruolo = true; //false --> osservatore; true --> osservabile
    private ArrayList<OsservatoreLista<Dispositivo>> osservatori;

    public ElencoDispositivi(Map<String, Dispositivo> elencoIniziale) {
        elenco = elencoIniziale;
        osservatori = new ArrayList<>();
    }

    public ElencoDispositivi() {
        this(new HashMap<>());
    }

    /*Gestione del ruolo:
    * Se attributo ruolo e' false allora si assume che l'elenco faccia da osservatore.
    * In questo caso i metodi add e remove pubblici sono inibiti in quanto la gestione della lista si rifa' sui dati passati dalle liste osservate.
    *
    * Se attributo ruolo e' true allora si assume che l'elenco faccia da oggetto osservabile.
    * In questo caso, ad ogni aggiunta o rimozione si deve passare il dato a tutti gli osservatori in lista.
    *
    * L'attributo ruolo e' di partenza true (quindi lista osservabile), questo viene posto a false (osservatore) quando si aggiunge un
    * osservatore di tipo ElencoDispositivi. Il ruolo e' posto quindi false all'osservatore appena aggiunto.
    * */
    protected boolean getRuolo() {
        return ruolo;
    }
    protected void setRuolo(boolean ruolo) {
        this.ruolo = ruolo;
    }


    public Dispositivo getDispositivo(String key) {
        return elenco.get(key);
    }

    public Dispositivo[] getDispositivi() {
        return elenco.values().toArray(new Dispositivo[0]);
    }

    public boolean contains(String key) {
        return elenco.get(key) != null;
    }

    public void remove(String key) {
        //lato osservabile

        if (ruolo == true) { //eseguo solo se osservabile
            Dispositivo dispositivo = elenco.get(key);

            if (dispositivo != null) {
                //rimuovo solo se presente
                elenco.remove(key);

                //decremento il numero associazioni e informo gli osservatori
                dispositivo.decrNumAssociazioni();
                informaRimozione(dispositivo);
            }
        }
    }

    public void remove(Dispositivo dispositivo) {
        this.remove(dispositivo.getNome());
    }

    public boolean add(Dispositivo dispositivo, String key) {
        //lato osservabile

        if (ruolo == true) { //eseguo solo se osservabile
            if (contains(key))
                return false;

            //se non presente aggiungo
            elenco.put(key, dispositivo);

            //incremento il numero di associazioni e informo gli osservatori
            dispositivo.incrNumAssociazioni();
            informaAggiunta(dispositivo);
            return true;
        }
        return false;
    }

    public boolean add(Dispositivo dispositivo) {
        return add(dispositivo, dispositivo.getNome());
    }

    @Override
    public void aggiungiOsservatore(OsservatoreLista<Dispositivo> oss) {
        if (oss instanceof ElencoDispositivi)
            ((ElencoDispositivi)oss).setRuolo(false);

        elenco.forEach((s, dispositivo) -> oss.elaboraAggiunta(dispositivo)); //in modo da informare immediatamente l'osservatore dei dati gia' contenuti
        osservatori.add(oss);
    }

    @Override
    public void rimuoviOsservatore(OsservatoreLista<Dispositivo> oss) {
        osservatori.remove(oss);
    }

    @Override
    public List<OsservatoreLista<Dispositivo>> getOsservatori() {
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
}
