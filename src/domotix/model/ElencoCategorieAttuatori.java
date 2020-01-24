package domotix.model;

import domotix.model.bean.device.CategoriaAttuatore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** @author Edoardo Coppola */
public class ElencoCategorieAttuatori {

    private static ElencoCategorieAttuatori instance;
    private HashMap<String, CategoriaAttuatore> categorie;

    public static ElencoCategorieAttuatori getInstance() {
        if (instance == null)
            instance = new ElencoCategorieAttuatori();
        return instance;
    }

    private ElencoCategorieAttuatori() {
        categorie = new HashMap<>();
    }

    public CategoriaAttuatore getCategoria(String nome) {
        return categorie.get(nome);
    }

    public ArrayList<CategoriaAttuatore> getCategorie(){
        return new ArrayList<>(categorie.values());
    }

    public boolean contains(String nome){
        return categorie.containsKey(nome);
    }

    public void remove(String nome){
        categorie.remove(nome);
    }

    public void remove(CategoriaAttuatore ca){
        for (Map.Entry<String, CategoriaAttuatore> entry : categorie.entrySet()){
            if(entry.getValue().getNome().equalsIgnoreCase(ca.getNome()))
                categorie.remove(entry.getKey());
        }
    }

    public void add(CategoriaAttuatore ca){
        categorie.put(ca.getNome(), ca);
    }

}
