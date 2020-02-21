package domotix.controller;

import domotix.logicUtil.MyMenu;
import domotix.model.ElencoCategorieAttuatori;
import domotix.model.ElencoCategorieSensori;
import domotix.model.ElencoUnitaImmobiliari;
import domotix.model.bean.UnitaImmobiliare;
import domotix.model.bean.device.CategoriaAttuatore;
import domotix.model.bean.device.CategoriaSensore;
import domotix.model.gestioneerrori.LogErrori;
import domotix.model.gestioneerrori.StoreIstanzeErrori;
import domotix.model.io.LetturaDatiSalvati;
import domotix.model.io.RimozioneDatiSalvati;
import domotix.model.io.ScritturaDatiSalvati;
import domotix.view.menus.MenuErroreApertura;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Classe di una sola istanza per le operazioni iniziali del programma.
 * Ovvero, la lettura dei dati iniziali con la gestione degli errori e la richiesta all'utente di come procedere nel caso
 * e la scrittura dei dati in uso con la gestione degli errori e la richiesta all'utente di come procedere se presenti.
 *
 * @author paolopasqua
 */
public class OperazioniIniziali {

    private static OperazioniIniziali instance = null;

    /**
     * Recupera la unica istanza della classe
     * @return  unica istanza della classe
     */
    public static OperazioniIniziali getInstance() {
        if (instance == null)
            instance = new OperazioniIniziali();
        return instance;
    }

    private OperazioniIniziali() { }

    /**
     * Metodo di esecuzione delle operazioni di apertura del programma, con verifica della lettura e richiesta all'utente di come agire in caso di errore.
     *
     * @return  false se in caso di errore l'utente sceglie di uscire dal programma; true se il programma puo' aprirsi.
     */
    public boolean apri() {
        boolean esito = popolaDati();

        if (!esito) {
            List<String> errori = LogErrori.getInstance().popAll();
            int scelta = MenuErroreApertura.avvia(errori);
            return scelta == MenuErroreApertura.CONTINUA; //false: esci dal programma; true: entra nel programma
        }

        return true;
    }

    /**
     * Controlla l'esistenza dell'unita immobiliare base, ovvero la presenza di almeno un'unita' immobiliare.
     * Questo perche' vi deve essere una sola (o almeno una) unita immobiliare, la quale viene generata vuota con
     * un nome scolpito all'avvio del programma nel caso non sia gia' presente.
     *
     * @return  true: esiste almeno un'unita immobiliare; false: l'elenco e' vuoto
     */
    public boolean controlloEsistenzaUnita() {
        return !ElencoUnitaImmobiliari.getInstance().getUnita().isEmpty();
    }

    /**
     * Genera l'unita' base che deve esistere per il funzionamento del programma.
     *
     * @return  istanza di una nuova unita immobiliare inizializzata con i dati di default del programma
     */
    public UnitaImmobiliare generaUnitaBase() {
        UnitaImmobiliare unita = new UnitaImmobiliare(UnitaImmobiliare.NOME_UNITA_DEFAULT);
        return unita;
    }

    /**
     * Metodo per popolare il model del programma con i dati salvati.
     * Gestisce gli errori di lettura singolarmente per entita' letta, in modo da lasciare all'utente la scelta se proseguire
     * con l'apertura del programma anche se non tutto e' stato caricato.
     * In caso di errore popola lo stack di messaggi di errore LogErrori (svutato ad inizio metodo) e svuota lo stack di istanze in
     * errore StoreIstanzeErrori per sincronia degli stack.
     * In questo modo si possono riportare all'utente i messaggi di errore.
     *
     * @return  true non vi sono stati errori in lettura; false altrimenti
     * @see LogErrori
     * @see StoreIstanzeErrori
     */
    private boolean popolaDati() {
        AtomicBoolean result = new AtomicBoolean(true);

        LogErrori.getInstance().clear();
        StoreIstanzeErrori.getInstance().clear(); //ripulisco per sincronizzazione degli stack

        //popolo le categorie sensori
        try {
            LetturaDatiSalvati.getInstance().getNomiCategorieSensori().forEach(s -> {
                try {
                    ElencoCategorieSensori.getInstance().add(LetturaDatiSalvati.getInstance().leggiCategoriaSensore(s));
                } catch (Exception e) {
                    LogErrori.getInstance().put(e.getMessage());
                    result.set(false);
                }
            });
        } catch (Exception e) {
            LogErrori.getInstance().put(e.getMessage());
            result.set(false);
        }

        //popolo le categorie attuatori
        try {
            LetturaDatiSalvati.getInstance().getNomiCategorieAttuatori().forEach(s -> {
                try {
                    ElencoCategorieAttuatori.getInstance().add(LetturaDatiSalvati.getInstance().leggiCategoriaAttuatore(s));
                } catch (Exception e) {
                    LogErrori.getInstance().put(e.getMessage());
                    result.set(false);
                }
            });
        } catch (Exception e) {
            LogErrori.getInstance().put(e.getMessage());
            result.set(false);
        }

        //popolo le unita immobiliari
        try {
            LetturaDatiSalvati.getInstance().getNomiUnitaImmobiliare().forEach(s -> {
                try {
                    ElencoUnitaImmobiliari.getInstance().add(LetturaDatiSalvati.getInstance().leggiUnitaImmobiliare(s));
                } catch (Exception e) {
                    LogErrori.getInstance().put(e.getMessage());
                    result.set(false);
                }
            });
        } catch (Exception e) {
            LogErrori.getInstance().put(e.getMessage());
            result.set(false);
        }

        return result.get();
    }

}
