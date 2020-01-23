package domotix.model;

import domotix.model.bean.UnitaImmobiliare;

import java.util.ArrayList;
import java.util.HashMap;

public class ElencoUnitaImmobiliari {

    private static ElencoUnitaImmobiliari instance = null;
    private HashMap<String, UnitaImmobiliare> unita;

    public static ElencoUnitaImmobiliari getInstance() {
        if (instance == null)
            instance = new ElencoUnitaImmobiliari();
        return instance;
    }

    private ElencoUnitaImmobiliari() {
        unita = new HashMap<>();
    }

    public ArrayList<UnitaImmobiliare> getUnita(){
        return new ArrayList<>(unita.values());
    }

    public UnitaImmobiliare getUnita(String nome) {
        return unita.get(nome);
    }

    public boolean contains(String nome){
        return unita.containsKey(nome);
    }

    public void remove(String nome){
        unita.remove(nome);
    }

    public void remove(UnitaImmobiliare ca){
        if (contains(ca.getNome()))
            unita.remove(ca.getNome());
    }

    public void add(UnitaImmobiliare ca){
        unita.put(ca.getNome(), ca);
    }
}
