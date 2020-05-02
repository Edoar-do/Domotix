package domotix.model.bean.device;

import domotix.controller.util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/** @author Edoardo Coppola */
public class Modalita implements Visitable {
    private static final String TOSTRING_TEMPLATE = "%s";
    private String nome;
    private HashMap<String, Parametro> parametri;

    public Modalita(String nome){
        this.nome = nome;
        parametri = new HashMap<>();
    }

    /**
     * Metodo che recupera il nome della Modalita.
     * @return Il nome
     */
    public String getNome(){
        return nome;
    }

    /**
     * Metodo che imposta il nome della Modalita.
     * @param nome Il nome
     */
    public void setNome(String nome){
        this.nome = nome;
    }

    /**
     * Ritorna l'elenco dei parametri per la modalita
     * @return  ArrayList dei Parametro contenuti
     */
    public ArrayList<Parametro> getParametri() {
        return new ArrayList<>(parametri.values());
    }

    /**
     * Ritorna il parametro identificato dal nome indicato se presente.
     * @param nome  stringa contenente il nome per identificare il parametro
     * @return  parametro identificato dal nome se presente; null altrimenti
     */
    public Parametro getParametro(String nome) {
        return parametri.get(nome);
    }

    /**
     * Ritorna se la modalita e' parametrica o meno, ovvero se possiede parametri oppure no.
     *
     * @return  true: la modalita contiene almeno un parametro; false: altrimenti
     */
    public boolean isParametrica() {
        return !parametri.isEmpty();
    }

    /**
     * Verifica se il nome indicato identifica un parametro per la modalita
     *
     * @param nome  stringa contenente il nome di cui cercare un parametro
     * @return  true: il nome identifica un parametro contenuto; false: altrimenti
     */
    public boolean containsParametro(String nome) {
        return parametri.containsKey(nome);
    }

    /**
     * Aggiunge un parametro alla modalita se non gia' presente (identificato per nome)
     * @param parametro parametro da aggiungere
     * @return  true; parametro aggiunto con successo; false: altrimenti
     */
    public boolean addParametro(Parametro parametro) {
        if (parametri.containsKey(parametro.getNome()))
            return false;

        parametri.put(parametro.getNome(), parametro);
        return true;
    }

    /**
     * Sovrascrive un parametro alla modalita se gia' presente (identificato per nome).
     * @param parametro parametro da aggiungere
     * @return true; parametro modificato con successo; false: altrimenti
     */
    public boolean setNuovoParametro(Parametro parametro) {
        if (!this.containsParametro(parametro.getNome())) return false;
        parametri.put(parametro.getNome(), parametro);
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        if (super.equals(obj))  return true;

        if (obj instanceof Modalita) {
            Modalita modalita = (Modalita)obj;
            AtomicBoolean esito = new AtomicBoolean(true);

            esito.set(this.nome.equals(modalita.getNome()));

            return esito.get();
        }

        return false;
    }

    @Override
    public Object fattiVisitare(Visitor v) {
        return v.visitaModalita(this);
    }
}
