package domotix.controller;

import domotix.model.bean.UnitaImmobiliare;
import domotix.model.bean.device.CategoriaAttuatore;
import domotix.model.bean.device.CategoriaSensore;
import domotix.controller.io.ImportaDati;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe che realizza la logica Controller per le funzionalità di importazione di unità immobiliari, categorie di attuatori e sensori da una libreria esterna
 * @author Edoardo Coppola
 */
public class Importatore {
    private Modificatore modificatore;
    private ImportaDati importaDati;
    private Recuperatore recuperatore;
    
    public Importatore(Modificatore modificatore, ImportaDati importaDati, Recuperatore recuperatore) {
        this.importaDati = importaDati;
        this.modificatore = modificatore;
        this.recuperatore = recuperatore;
    }

    /**
     * Metodo che realizza l'importazione di unita' immobiliari da una libreria estrerna
     * Le unita' importate sono già provviste di stanze, artefatti, sensori, attuatori e regole
     * @return esiti dell'importazione delle unita'
     */
    public List<String> importaUnitaImmobiliari(){
        List<String> esiti = new ArrayList<>();
        UnitaImmobiliare unita;
        String[] nomiUnitaDaImportare;
        try { nomiUnitaDaImportare = importaDati.getNomiUnitaImmobiliare().toArray(new String[0]);
        } catch (Exception e) {
            return null;
        }
        for (String nomeUnita : nomiUnitaDaImportare) {
            if (recuperatore.getListaUnita().stream().map((u) -> u.getNome()).anyMatch((n) -> n.equals(nomeUnita))) {
                esiti.add(nomeUnita);
            } else{
                try {
                    unita = importaDati.leggiUnitaImmobiliare(nomeUnita);
                    modificatore.aggiungiUnitaImmobiliare(unita);
                    try{
                        importaDati.storicizzaUnitaImmobiliare(nomeUnita);
                    }catch(Exception e){
                        esiti.add(nomeUnita);
                        modificatore.rimuoviUnitaImmobiliare(nomeUnita);
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
    public List<String> importaCategorieSensori(){
        List<String> esiti = new ArrayList<>();
        CategoriaSensore categoria;
        String[] nomiCategorieSensoriDaImportare;
        try{ nomiCategorieSensoriDaImportare = importaDati.getNomiCategorieSensori().toArray(new String[0]);
        }catch(Exception e){
                    return null; //chiudo tutto perché non parte proprio l'importazione
        }
        for (String nomeCategoria : nomiCategorieSensoriDaImportare){
            if(recuperatore.getCategorieSensore().stream().map((c) -> c.getNome()).anyMatch(n -> n.equals(nomeCategoria))){ //già presente -> aggiungo un esito_errore
                esiti.add(nomeCategoria);
            }else{ //non presente - nuova -> fetch + add + move
                try {
                    categoria = importaDati.leggiCategoriaSensore(nomeCategoria);
                    modificatore.aggiungiCategoriaSensore(categoria);
                    try {
                        importaDati.storicizzaCategoriaSensore(nomeCategoria);
                    } catch (Exception e) {
                        esiti.add(nomeCategoria);
                        modificatore.rimuoviCategoriaSensore(nomeCategoria); //rimuovo ciò che di sbagliato ho aggiunto a causa della mancata storicizzazione
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
    public List<String> importaCategorieAttuatori(){
        List<String> esiti = new ArrayList<>();
        CategoriaAttuatore categoria;
        String[] nomiCategorieAttuatoriDaImportare;
        try {
            nomiCategorieAttuatoriDaImportare = importaDati.getNomiCategorieAttuatori().toArray(new String[0]);
        }catch (Exception e){
            return null;
        }
        for (String nomeCategoria : nomiCategorieAttuatoriDaImportare){
            if(recuperatore.getCategorieAttuatore().stream().map((c) -> c.getNome()).anyMatch(n -> n.equals(nomeCategoria))) { //già presente -> aggiungo un esito_errore
                esiti.add(nomeCategoria);
            }else{ //non presente - nuova -> fetch + add + move
                try {
                    categoria = importaDati.leggiCategoriaAttuatore(nomeCategoria);
                    modificatore.aggiungiCategoriaAttuatore(categoria);
                    try {
                        importaDati.storicizzaCategoriaAttuatore(nomeCategoria);
                    } catch (Exception e) {
                        esiti.add(nomeCategoria);
                        modificatore.rimuoviCategoriaAttuatore(nomeCategoria);  //rimuovo quanto di sbagliato ho appena aggiunto a causa della mancata storicizzazione
                    }
                } catch (Exception e) {
                    esiti.add(nomeCategoria);
                }
            }
        }
        return esiti;
    }
}
