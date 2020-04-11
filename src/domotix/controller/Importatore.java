package domotix.controller;

import domotix.model.ElencoCategorieAttuatori;
import domotix.model.ElencoCategorieSensori;
import domotix.model.ElencoUnitaImmobiliari;
import domotix.model.bean.UnitaImmobiliare;
import domotix.model.bean.device.CategoriaAttuatore;
import domotix.model.bean.device.CategoriaSensore;

import java.util.ArrayList;

/**
 * Classe che realizza la logica Controller per le funzionalità di importazione di unità immobiliari, categorie di attuatori e sensori da una libreria esterna
 * @author Edoardo Coppola
 */
public class Importatore {

    private static final String CATEGORIA_PRE_ESISTENTE = "La categoria %s non è stata importata in quanto e' presente una categoria omonima in elenco";
    private static final String IMPORT_CAT_SENS_OK = "Importazione delle categorie di sensori terminato con successo";
    private static final String IMPORT_CAT_ATT_OK = "Importazione delle categorie di attuatori terminato con successo";
    private static final String IMPORT_UNITA_OK = "Importazione delle unita' immobiliari terminata con successo ";
    private static final String UNITA_PRE_ESISTENTE = "L'unita' immobiliare %s non è stata importata in quanto e' presenta un'unita' immobiliare omonima in elenco";
    private static final String NOT_IMPORT_CAT_SENS_YET = "Importazione unita' immobiliari impossibile. E' necessario importare prima le categorie di sensori";
    private static final String NOT_IMPORT_CAT_ATT_YET = "Importazione unita' immobiliari impossibile. E' necessario importare prima le categorie di attuatori";

    //i seguenti boolean sono per verificare il rispetto delle precedenze di importazione: categorie -> unità. Non viceversa
    private static boolean importazioneCategorieSensoriAvvenuta = false;
    private static boolean importazioneCategorieAttuatoriAvvenuta = false;

    /**
     * Metodo che realizza l'importazione di unita' immobiliari da una libreria estrerna
     * Le unita' importate sono già provviste di stanze, artefatti, sensori, attuatori e regole
     * @return esiti dell'importazione delle unita'
     */
    public static ArrayList<String> importaUnitaImmobiliari(){
        ArrayList<String> esiti = new ArrayList<>();
        if(!importazioneCategorieSensoriAvvenuta) esiti.add(NOT_IMPORT_CAT_SENS_YET);
        if(!importazioneCategorieAttuatoriAvvenuta) esiti.add(NOT_IMPORT_CAT_ATT_YET);
        if(importazioneCategorieAttuatoriAvvenuta && importazioneCategorieSensoriAvvenuta) {
            UnitaImmobiliare unita;
            String[] nomiUnitaDaImportare = ImportaDati.getInstance().getNomiUnitaImmobiliari();
            for (String nomeUnita : nomiUnitaDaImportare) {
                if(ElencoUnitaImmobiliari.getInstance().contains(nomeUnita)){
                    esiti.add(String.format(UNITA_PRE_ESISTENTE, nomeUnita));
                }else{
                    unita = ImportaDati.getInstance().leggiUnitaImmobiliare(nomeUnita);
                    ElencoUnitaImmobiliari.getInstance().add(unita);
                    ImportaDati.getInstance().storicizzaUnitaImmobiliare(nomeUnita);
                }
            }
        }
        if(esiti.isEmpty()) esiti.add(IMPORT_UNITA_OK);
        return esiti;
    }

    /**
     * Metodo che realizza l'importazione di categorie di sensori da una libreria esterna
     * @return esiti dell'importazione
     */
    public static ArrayList<String> importaCategorieSensori(){
        ArrayList<String> esiti = new ArrayList<>();
        CategoriaSensore categoria;
        String[] nomiCategorieSensoriDaImportare = ImportaDati.getInstance().getNomiCategorieSensori();
        for (String nomeCategoria : nomiCategorieSensoriDaImportare){
            if(ElencoCategorieSensori.getInstance().contains(nomeCategoria)){ //già presente -> aggiungo un esito_errore
                esiti.add(String.format(CATEGORIA_PRE_ESISTENTE, nomeCategoria));
            }else{ //non presente - nuova -> fetch + add + move
                categoria = ImportaDati.getInstance().leggiCategoriaSensore(nomeCategoria);
                ElencoCategorieSensori.getInstance().add(categoria);
                ImportaDati.getInstance().storicizzaCategoriaSensore(nomeCategoria);
            }
        }
        if(esiti.isEmpty()) esiti.add(IMPORT_CAT_SENS_OK);
        importazioneCategorieSensoriAvvenuta = true;
        return esiti;
    }

    /**
     * Metodo che realizza l'importazione di categorie di attuatori da una libreria esterna
     * @return esiti dell'importazione
     */
    public static ArrayList<String> importaCategorieAttuatori(){
        ArrayList<String> esiti = new ArrayList<>();
        CategoriaAttuatore categoria;
        String[] nomiCategorieAttuatoriDaImportare = ImportaDati.getInstance().getNomiCategorieAttuatori();
        for (String nomeCategoria : nomiCategorieAttuatoriDaImportare){
            if(ElencoCategorieAttuatori.getInstance().contains(nomeCategoria)){ //già presente -> aggiungo un esito_errore
                esiti.add(String.format(CATEGORIA_PRE_ESISTENTE, nomeCategoria));
            }else{ //non presente - nuova -> fetch + add + move
                categoria = ImportaDati.getInstance().leggiCategoriaAttuatore(nomeCategoria);
                ElencoCategorieAttuatori.getInstance().add(categoria);
                ImportaDati.getInstance().storicizzaCategoriaAttuatore(nomeCategoria);
            }
        }
        if(esiti.isEmpty()) esiti.add(IMPORT_CAT_ATT_OK);
        importazioneCategorieAttuatoriAvvenuta = true;
        return esiti;
    }
}
