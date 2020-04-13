package domotix.controller;

import domotix.model.ElencoCategorieAttuatori;
import domotix.model.ElencoCategorieSensori;
import domotix.model.ElencoUnitaImmobiliari;
import domotix.model.bean.UnitaImmobiliare;
import domotix.model.bean.device.CategoriaAttuatore;
import domotix.model.bean.device.CategoriaSensore;
import domotix.model.io.ImportaDati;

import java.util.ArrayList;

/**
 * Classe che realizza la logica Controller per le funzionalità di importazione di unità immobiliari, categorie di attuatori e sensori da una libreria esterna
 * @author Edoardo Coppola
 */
public class Importatore {

    /**
     * Metodo che realizza l'importazione di unita' immobiliari da una libreria estrerna
     * Le unita' importate sono già provviste di stanze, artefatti, sensori, attuatori e regole
     * @return esiti dell'importazione delle unita'
     */
    public static ArrayList<String> importaUnitaImmobiliari(){
        ArrayList<String> esiti = new ArrayList<>();
        UnitaImmobiliare unita;
        String[] nomiUnitaDaImportare;
        try { nomiUnitaDaImportare = ImportaDati.getInstance().getNomiUnitaImmobiliare().toArray(new String[0]);
        } catch (Exception e) {
            return null;
        }
        for (String nomeUnita : nomiUnitaDaImportare) {
            if (ElencoUnitaImmobiliari.getInstance().contains(nomeUnita)) {
                esiti.add(nomeUnita);
            } else{
                try {
                    unita = ImportaDati.getInstance().leggiUnitaImmobiliare(nomeUnita);
                    ElencoUnitaImmobiliari.getInstance().add(unita);
                    try{
                        ImportaDati.getInstance().storicizzaUnitaImmobiliare(nomeUnita);
                    }catch(Exception e){
                        esiti.add(nomeUnita);
                        ElencoUnitaImmobiliari.getInstance().remove(nomeUnita);
                    }
                }catch(Exception e){
                    esiti.add(nomeUnita);
                }
            }
        }
        return esiti;
    }

    /**
     * Metodo che realizza l'importazione di categorie di sensori da una libreria esterna
     * @return esiti dell'importazione
     */
    public static ArrayList<String> importaCategorieSensori(){
        ArrayList<String> esiti = new ArrayList<>();
        CategoriaSensore categoria;
        String[] nomiCategorieSensoriDaImportare;
        try{ nomiCategorieSensoriDaImportare = ImportaDati.getInstance().getNomiCategorieSensori().toArray(new String[0]);
        }catch(Exception e){
                    return null; //chiudo tutto perché non parte proprio l'importazione
        }
        for (String nomeCategoria : nomiCategorieSensoriDaImportare){
            if(ElencoCategorieSensori.getInstance().contains(nomeCategoria)){ //già presente -> aggiungo un esito_errore
                esiti.add(nomeCategoria);
            }else{ //non presente - nuova -> fetch + add + move
                try {
                    categoria = ImportaDati.getInstance().leggiCategoriaSensore(nomeCategoria);
                    ElencoCategorieSensori.getInstance().add(categoria);
                    try {
                        ImportaDati.getInstance().storicizzaCategoriaSensore(nomeCategoria);
                    } catch (Exception e) {
                        esiti.add(nomeCategoria);
                        ElencoCategorieSensori.getInstance().remove(nomeCategoria); //rimuovo ciò che di sbagliato ho aggiunto a causa della mancata storicizzazione
                    }
                } catch (Exception e) {
                    esiti.add(nomeCategoria);
                }
            }
        }
        return esiti;
    }

    /**
     * Metodo che realizza l'importazione di categorie di attuatori da una libreria esterna
     * @return esiti dell'importazione
     */
    public static ArrayList<String> importaCategorieAttuatori(){
        ArrayList<String> esiti = new ArrayList<>();
        CategoriaAttuatore categoria;
        String[] nomiCategorieAttuatoriDaImportare;
        try {
            nomiCategorieAttuatoriDaImportare = ImportaDati.getInstance().getNomiCategorieAttuatori().toArray(new String[0]);
        }catch (Exception e){
            return null;
        }
        for (String nomeCategoria : nomiCategorieAttuatoriDaImportare){
            if(ElencoCategorieAttuatori.getInstance().contains(nomeCategoria)){ //già presente -> aggiungo un esito_errore
                esiti.add(nomeCategoria);
            }else{ //non presente - nuova -> fetch + add + move
                try {
                    categoria = ImportaDati.getInstance().leggiCategoriaAttuatore(nomeCategoria);
                    ElencoCategorieAttuatori.getInstance().add(categoria);
                    try {
                        ImportaDati.getInstance().storicizzaCategoriaAttuatore(nomeCategoria);
                    } catch (Exception e) {
                        esiti.add(nomeCategoria);
                        ElencoCategorieAttuatori.getInstance().remove(nomeCategoria); //rimuovo quanto di sbagliato ho appena aggiunto a causa della mancata storicizzazione
                    }
                } catch (Exception e) {
                    esiti.add(nomeCategoria);
                }
            }
        }
        return esiti;
    }
}
