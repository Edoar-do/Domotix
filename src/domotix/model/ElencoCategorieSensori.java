package domotix.model;

import domotix.model.bean.device.CategoriaSensore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Classe di accesso al Model per le CategoriaSensore salvate.
 *
 * @see CategoriaSensore
 */
public class ElencoCategorieSensori {
    private Map<String, CategoriaSensore> categorie;

    public ElencoCategorieSensori() {
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
    public List<CategoriaSensore> getCategorie(){
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
