package domotix.model;

import domotix.controller.util.StringUtil;
import domotix.model.bean.UnitaImmobiliare;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Classe di accesso al Model per le UnitaImmobiliari salvate.
 * Implementa il modello Singleton per avere un'unica istanza di questa classe.
 *
 * @see UnitaImmobiliare
 */
public class ElencoUnitaImmobiliari {

    private static ElencoUnitaImmobiliari instance = null;
    private HashMap<String, UnitaImmobiliare> unita;

    /**
     * Recupera l'unica istanza dell'elenco.
     * @return  Unica istanza dell'elenco.
     */
    public static ElencoUnitaImmobiliari getInstance() {
        if (instance == null)
            instance = new ElencoUnitaImmobiliari();
        return instance;
    }

    private ElencoUnitaImmobiliari() {
        unita = new HashMap<>();
    }

    /**
     * Recupera tutte le unita' salvate in elenco
     * @return  array di unita' contenute
     */
    public ArrayList<UnitaImmobiliare> getUnita(){
        return new ArrayList<>(unita.values());
    }

    /**
     * Recupera l'istanza di UnitaImmobiliare identificata dal nome indicato dal parametro.
     * @param nome  stringa per identificare l'unita' immobiliare
     * @return  istanza dell'unita' immobiliare se presente; null altrimenti
     */
    public UnitaImmobiliare getUnita(String nome) {
        return unita.get(nome);
    }

    /**
     * Verifica se presente l'unita immobiliare identificata dal nome.
     * @param nome  stringa per identificare l'unita' immobiliare
     * @return  true: la stringa identifica un'unita' immobiliare in elenco; false: altrimenti
     */
    public boolean contains(String nome){
        return unita.containsKey(nome);
    }

    /**
     * Rimuove l'unita' immobiliare identificata dal nome passato, se presente.
     * @param nome  stringa per identificare l'unita' immobiliare da eliminare
     */
    public void remove(String nome){
        unita.remove(nome);
    }

    /**
     * Rimuove l'unita' immobiliare identificata dall'istanza passata, se presente.
     * @param ca    istanza dell'unita' immobiliare da rimuovere
     */
    public void remove(UnitaImmobiliare ca){
        if (contains(ca.getNome()))
            unita.remove(ca.getNome());
    }

    /**
     * Aggiunge l'unita' immobiliare passata all'elenco se non presente.
     * @param ca    istanza dell'unita' immobiliare da aggiungere
     */
    public void add(UnitaImmobiliare ca){
        unita.put(ca.getNome(), ca);
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        unita.forEach((k, v) -> {
            buffer.append(v.toString() + "\n");
        });
        return StringUtil.removeLast(buffer.toString());
    }
}
