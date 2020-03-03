package domotix.model;

import domotix.model.bean.device.CategoriaSensore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe di accesso al Model per le CategoriaSensore salvate.
 * Implementa il modello Singleton per avere un'unica istanza di questa classe.
 *
 * @see CategoriaSensore
 */
public class ElencoCategorieSensori {
    private static ElencoCategorieSensori instance;
    private HashMap<String, CategoriaSensore> categorie;

    /**
     * Recupera l'unica istanza dell'elenco.
     * @return  Unica istanza dell'elenco.
     */
    public static ElencoCategorieSensori getInstance() {
        if (instance == null)
            instance = new ElencoCategorieSensori();
        return instance;
    }

    private ElencoCategorieSensori() {
        categorie = new HashMap<>();
    }

    /**
     * Recupera l'istanza di CategoriaSensore identificata dal nome indicato dal parametro.
     * @param nome  stringa per identificare la categoria
     * @return  istanza della categoria se presente; null altrimenti
     */
    public CategoriaSensore getCategoria(String nome) { return categorie.get(nome); }

    /**
     * Recupera tutte le categorie salvate in elenco
     * @return  array di categorie contenute
     */
    public ArrayList<CategoriaSensore> getCategorie(){
        return new ArrayList<>(categorie.values());
    }

    /**
     * Verifica se presente la categoria identificata dal nome.
     * @param nome  stringa per identificare la categoria sensore
     * @return  true: la stringa identifica una categoria sensore in elenco; false: altrimenti
     */
    public boolean contains(String nome){
        return categorie.containsKey(nome);
    }

    /**
     * Rimuove la categoria sensore identificata dal nome passato, se presente.
     * @param nome  stringa per identificare la categoria sensore da eliminare
     */
    public void remove(String nome){
        categorie.remove(nome);
    }

    /**
     * Rimuove la categoria sensore identificata dall'istanza passata, se presente.
     * @param cs    istanza della categoria sensore da rimuovere
     */
    public void remove(CategoriaSensore cs){
        for (Map.Entry<String, CategoriaSensore> entry : categorie.entrySet()){
            if(entry.getValue().getNome().equalsIgnoreCase(cs.getNome()))
                categorie.remove(entry.getKey());
        }
    }

    /**
     * Aggiunge la categoria sensore passata all'elenco se non presente.
     * @param cs    istanza della categoria sensore da aggiungere
     */
    public void add(CategoriaSensore cs){
        categorie.put(cs.getNome(), cs);
    }
}
