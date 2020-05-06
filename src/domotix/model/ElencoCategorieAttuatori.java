package domotix.model;

import domotix.model.bean.device.CategoriaAttuatore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * Classe di accesso al Model per le CategoriaAttuatore salvate.
 *
 * @see CategoriaAttuatore
 */
public class ElencoCategorieAttuatori {

    private Map<String, CategoriaAttuatore> categorie;

    public ElencoCategorieAttuatori() {
        categorie = new HashMap<>();
    }

    /**
     * Recupera l'istanza di CategoriaAttuatore identificata dal nome indicato dal parametro.
     * @param nome  stringa per identificare la categoria
     * @return  istanza della categoria se presente; null altrimenti
     */
    public CategoriaAttuatore getCategoria(String nome) {
        return categorie.get(nome);
    }

    /**
     * Recupera tutte le categorie salvate in elenco
     * @return  array di categorie contenute
     */
    public List<CategoriaAttuatore> getCategorie(){
        return new ArrayList<>(categorie.values());
    }

    /**
     * Verifica se presente la categoria identificata dal nome.
     * @param nome  stringa per identificare la categoria attuatore
     * @return  true: la stringa identifica una categoria attuatore in elenco; false: altrimenti
     */
    public boolean contains(String nome){
        return categorie.containsKey(nome);
    }

    /**
     * Rimuove la categoria attuatore identificata dal nome passato, se presente.
     * @param nome  stringa per identificare la categoria attuatore da eliminare
     */
    public void remove(String nome){
        categorie.remove(nome);
    }

    /**
     * Rimuove la categoria attuatore identificata dall'istanza passata, se presente.
     * @param ca    istanza della categoria attuatore da rimuovere
     */
    public void remove(CategoriaAttuatore ca){
        for (Map.Entry<String, CategoriaAttuatore> entry : categorie.entrySet()){
            if(entry.getValue().getNome().equalsIgnoreCase(ca.getNome()))
                categorie.remove(entry.getKey());
        }
    }

    /**
     * Aggiunge la categoria attuatore passata all'elenco se non presente.
     * @param ca    istanza della categoria attuatore da aggiungere
     */
    public void add(CategoriaAttuatore ca){
        categorie.put(ca.getNome(), ca);
    }

}
