package domotix.model;

import domotix.model.visitor.Visitable;
import domotix.model.visitor.Visitor;
import domotix.model.bean.UnitaImmobiliare;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Classe di accesso al Model per le UnitaImmobiliari salvate.
 *
 * @see UnitaImmobiliare
 */
public class ElencoUnitaImmobiliari implements Visitable {

    private Map<String, UnitaImmobiliare> unita;

    public ElencoUnitaImmobiliari() {
        unita = new HashMap<>();
    }

    /**
     * Recupera tutte le unita' salvate in elenco
     * @return  array di unita' contenute
     */
    public List<UnitaImmobiliare> getUnita(){
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
        if (contains(nome)) {
            unita.get(nome).distruggi();
            unita.remove(nome);
        }
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
    public Object fattiVisitare(Visitor v) {
        return v.visitaElencoUnitaImmobiliari(this);
    }
}
