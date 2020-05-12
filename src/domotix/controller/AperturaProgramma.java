package domotix.controller;

import domotix.model.bean.regole.Azione;
import domotix.controller.io.LetturaDatiSalvati;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Classe per le operazioni iniziali del programma.
 * Ovvero, la lettura dei dati iniziali con la gestione degli errori e la richiesta all'utente di come procedere nel caso
 * e la scrittura dei dati in uso con la gestione degli errori e la richiesta all'utente di come procedere se presenti.
 *
 * @author paolopasqua
 */
public class AperturaProgramma {

    //private ArrayList<String> azioniConflitto;
    private LetturaDatiSalvati letturaDati = null;
    private Modificatore modificatore = null;

    private List<String> erroriLettura = null;

    /**
     * Costruttore della classe.
     *
     * @param letturaDati   istanza di LetturaDatiSalvati per leggere i dati
     * @param modificatore  istanza di Modificatore per inserire le istanze lette
     */
    public AperturaProgramma(LetturaDatiSalvati letturaDati, Modificatore modificatore) {
        this.letturaDati = letturaDati;
        this.modificatore = modificatore;

        erroriLettura = new ArrayList<>();
    }

    /**
     * Metodo di esecuzione delle operazioni di apertura del programma.
     *
     * @return  false se vi sono errori di lettura; true se il programma puo' aprirsi.
     */
    public boolean apri() {
        return popolaDati();
    }

    /**
     * Recupera la lista di errori di lettura popolata durante le procedure.
     * Se la lista e' vuota allora non vi sono stati errori.
     * 
     * @return  lista di stringhe contenenti i messaggi di errore
     */
    public List<String> getErroriLettura() {
        return this.erroriLettura;
    }

    /**
     * Metodo per popolare il model del programma con i dati salvati.
     * Gestisce gli errori di lettura singolarmente per entita' letta, in modo da lasciare all'utente la scelta se proseguire
     * con l'apertura del programma anche se non tutto e' stato caricato.
     * In caso di errore popola la lista interna di messaggi di errore (svutata ad inizio metodo)
     * In questo modo si possono riportare all'utente i messaggi di errore.
     *
     * @return  true non vi sono stati errori in lettura; false altrimenti
     */
    private boolean popolaDati() {
        AtomicBoolean result = new AtomicBoolean(true);

        this.erroriLettura.clear();

        //popolo le categorie sensori
        this.letturaDati.getNomiCategorieSensori().forEach(s -> {
            try {
                this.modificatore.aggiungiCategoriaSensore(this.letturaDati.leggiCategoriaSensore(s));
            } catch (Exception e) {
                this.erroriLettura.add(e.getMessage());
                result.set(false);
            }
        });
        // try {
            
        // } catch (Exception e) {
        //     this.erroriLettura.add(e.getMessage());
        //     result.set(false);
        // }

        //popolo le categorie attuatori
        this.letturaDati.getNomiCategorieAttuatori().forEach(s -> {
            try {
                this.modificatore.aggiungiCategoriaAttuatore(this.letturaDati.leggiCategoriaAttuatore(s));
            } catch (Exception e) {
                this.erroriLettura.add(e.getMessage());
                result.set(false);
            }
        });
        // try {
        // } catch (Exception e) {
        //     this.erroriLettura.add(e.getMessage());
        //     result.set(false);
        // }

        //popolo le unita immobiliari
        this.letturaDati.getNomiUnitaImmobiliare().forEach(s -> {
            try {
                this.modificatore.aggiungiUnitaImmobiliare(this.letturaDati.leggiUnitaImmobiliare(s));
            } catch (Exception e) {
                this.erroriLettura.add(e.getMessage());
                result.set(false);
            }
        });
        // try {
        // } catch (Exception e) {
        //     this.erroriLettura.add(e.getMessage());
        //     result.set(false);
        // }

        //popolo le azioni programmate
        this.letturaDati.getIdAzioniProgrammate().forEach(s -> {
            try {
                Azione a = this.letturaDati.leggiAzioneProgrammata(s);
                this.modificatore.aggiungiAzioneProgrammata(s, a);
            } catch (Exception e) {
                this.erroriLettura.add(e.getMessage());
                result.set(false);
            }
        });
        // try {
        // } catch (Exception e) {
        //     this.erroriLettura.add(e.getMessage());
        //     result.set(false);
        // }

        return result.get();
    }

}
