package domotix.model;

import domotix.model.bean.device.CategoriaSensore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ElencoCategorieSensori {
    private static ElencoCategorieSensori instance;
    private HashMap<String, CategoriaSensore> categorie;

    public static ElencoCategorieSensori getInstance() {
        if (instance == null)
            instance = new ElencoCategorieSensori();
        return instance;
    }

    private ElencoCategorieSensori() {
        categorie = new HashMap<>();
    }

    public ArrayList<CategoriaSensore> getCategorie(){
        return new ArrayList<>(categorie.values());
    }

    public boolean contains(String nome){
        return categorie.containsKey(nome);
    }

    public void remove(String nome){
        categorie.remove(nome);
    }

    public void remove(CategoriaSensore cs){
        for (Map.Entry<String, CategoriaSensore> entry : categorie.entrySet()){
            if(entry.getValue().getNome().equalsIgnoreCase(cs.getNome()))
                categorie.remove(entry.getKey());
        }
    }

    public void add(CategoriaSensore cs){
        categorie.put(cs.getNome(), cs);
    }
}
