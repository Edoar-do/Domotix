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
import domotix.view.menus.MenuErroreChiusura;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Classe di una sola istanza per le operazioni finali del programma.
 * Ovvero, la scrittura dei dati in uso con la gestione degli errori e la richiesta all'utente di come procedere se presenti.
 *
 * @author paolopasqua
 */
public class OperazioniFinali {

    private static OperazioniFinali instance = null;

    /**
     * Recupera la unica istanza della classe
     * @return  unica istanza della classe
     */
    public static OperazioniFinali getInstance() {
        if (instance == null)
            instance = new OperazioniFinali();
        return instance;
    }

    private OperazioniFinali() { }

    /**
     * Metodo di esecuzione delle operazioni di chiusura del programma, con verifica della scrittura e richiesta all'utente di come agire in caso di errore.
     *
     * @return false se in caso di errore non chiudere il programma; true se il programma puo' terminare.
     */
    public boolean chiudi() {
        boolean esito = salvaDati();

        //finche' non ho un esito positivo del salvataggio (o utente sceglie altre azioni)
        while(!esito) {
            List<String> errori = LogErrori.getInstance().popAll();
            //Chiedo all'utente se mantenere il programma aperto (1), ritentare il salvataggio (2) o uscire senza salvare (0)
            int scelta = MenuErroreChiusura.avvia(errori);
            switch (scelta) {
                case MenuErroreChiusura.ESCI:
                    return true;
                case MenuErroreChiusura.ANNULLA_CHIUSURA:
                    return false;
                case MenuErroreChiusura.RITENTA_SALVATAGGIO:
                    esito = risalvaDati(StoreIstanzeErrori.getInstance().popAll());
            }
        }

        return true;
    }

    /**
     * Metodo per salvare il model del programma su dati salvati.
     * Gestisce gli errori di scrittura singolarmente per entita' salvata, in modo da lasciare all'utente la scelta se proseguire
     * con la chiusura del programma anche se non tutto e' stato salvato. Pertanto e' possibile uscire senza salvare tutto.
     * In caso di errore popola lo stack di messaggi di errore LogErrori (svutato ad inizio metodo) e lo stack di istanze in
     * errore StoreIstanzeErrori (svutato anch'esso).
     * In questo modo si possono riportare all'utente i messaggi di errore e tentare il salvataggio delle sole istanze in errore
     * se così scelto dall'utente.
     *
     * @return  true se non vi sono stati errori in scrittura; false altrimenti
     * @see LogErrori
     * @see StoreIstanzeErrori
     */
    private boolean salvaDati() {
        AtomicBoolean result = new AtomicBoolean(true);

        LogErrori.getInstance().clear();
        StoreIstanzeErrori.getInstance().clear();

        List<String> nomiDatiSalvati = null;

        //Recupero nomi categorie sensori salvate
        try {
            nomiDatiSalvati = LetturaDatiSalvati.getInstance().getNomiCategorieSensori();
        } catch (Exception e) {
            LogErrori.getInstance().put(e.getMessage());
            result.set(false);
        }
        List<String> catSensSalvati = nomiDatiSalvati;
        //Salvo categorie sensori logiche presenti
        ElencoCategorieSensori.getInstance().getCategorie().forEach(categoriaSensore -> {
            try {
                ScritturaDatiSalvati.getInstance().salva(categoriaSensore);
                catSensSalvati.remove(categoriaSensore.getNome());
            } catch (Exception e) {
                LogErrori.getInstance().put(e.getMessage());
                StoreIstanzeErrori.getInstance().put(categoriaSensore.getNome(), categoriaSensore.getClass());
                result.set(false);
            }
        });

        //Recupero nomi categorie attuatori salvate
        try {
            nomiDatiSalvati = LetturaDatiSalvati.getInstance().getNomiCategorieAttuatori();
        } catch (Exception e) {
            LogErrori.getInstance().put(e.getMessage());
            result.set(false);
        }
        List<String> catAttSalvati = nomiDatiSalvati;
        //Salvo categorie attuatori logiche presenti
        ElencoCategorieAttuatori.getInstance().getCategorie().forEach(categoriaAttuatore -> {
            try {
                ScritturaDatiSalvati.getInstance().salva(categoriaAttuatore);
                catAttSalvati.remove(categoriaAttuatore.getNome());
            } catch (Exception e) {
                LogErrori.getInstance().put(e.getMessage());
                StoreIstanzeErrori.getInstance().put(categoriaAttuatore.getNome(), categoriaAttuatore.getClass());
                result.set(false);
            }
        });

        //Recupero nomi unita immobiliare salvate
        try {
            nomiDatiSalvati = LetturaDatiSalvati.getInstance().getNomiUnitaImmobiliare();
        } catch (Exception e) {
            LogErrori.getInstance().put(e.getMessage());
            result.set(false);
        }
        List<String> unitaImmobSalvati = nomiDatiSalvati;
        //Salvo unita immobiliare logiche presenti
        ElencoUnitaImmobiliari.getInstance().getUnita().forEach(unitaImmobiliare -> {
            try {
                ScritturaDatiSalvati.getInstance().salva(unitaImmobiliare);
                unitaImmobSalvati.remove(unitaImmobiliare.getNome());
            } catch (Exception e) {
                LogErrori.getInstance().put(e.getMessage());
                StoreIstanzeErrori.getInstance().put(unitaImmobiliare.getNome(), unitaImmobiliare.getClass());
                result.set(false);
            }
        });

        //RIMOZIONE ENTITA' CANCELLATE LOGICAMENTE

        unitaImmobSalvati.forEach( nomeUnita -> {
            try {
                RimozioneDatiSalvati.getInstance().rimuoviUnitaImmobiliare(nomeUnita);
            } catch (Exception e) {
                LogErrori.getInstance().put(e.getMessage());
                result.set(false);
            }
        });

        catAttSalvati.forEach( catAtt -> {
            try {
                RimozioneDatiSalvati.getInstance().rimuoviCategoriaAttuatore(catAtt);
            } catch (Exception e) {
                LogErrori.getInstance().put(e.getMessage());
                result.set(false);
            }
        });

        catSensSalvati.forEach( cattSens -> {
            try {
                RimozioneDatiSalvati.getInstance().rimuoviCategoriaSensore(cattSens);
            } catch (Exception e) {
                LogErrori.getInstance().put(e.getMessage());
                result.set(false);
            }
        });

        return result.get();
    }

    /**
     * Metodo per tentare il ri-salvataggio delle istanze andate in errore nel tentativo precedente.
     * Gestisce gli errori di scrittura singolarmente per entita' salvata, in modo da poter ripetere lo stesso meccanismo in
     * caso di errori persistenti.
     * In caso di errore popola lo stack di messaggi di errore LogErrori (svutato ad inizio metodo) e lo stack di istanze in
     * errore StoreIstanzeErrori (svutato anch'esso), come per il primo salvataggio.
     *
     * @param istanzeErrori elenco di istanze precedentemente andate in errore e da ri-processare
     * @return  true se non vi sono stati errori di scrittura; false altrimenti
     * @see LogErrori
     * @see StoreIstanzeErrori
     */
    private boolean risalvaDati(Set<Map.Entry<String, Class>> istanzeErrori) {
        AtomicBoolean result = new AtomicBoolean(true);

        LogErrori.getInstance().clear();
        StoreIstanzeErrori.getInstance().clear();

        istanzeErrori.forEach(istanza -> {
            try {
                if (istanza.getValue().equals(CategoriaSensore.class)) {
                    ScritturaDatiSalvati.getInstance().salva(ElencoCategorieSensori.getInstance().getCategoria(istanza.getKey()));
                }
                else if (istanza.getValue().equals(CategoriaAttuatore.class)) {
                    ScritturaDatiSalvati.getInstance().salva(ElencoCategorieAttuatori.getInstance().getCategoria(istanza.getKey()));
                }
                else if (istanza.getValue().equals(UnitaImmobiliare.class)) {
                    ScritturaDatiSalvati.getInstance().salva(ElencoUnitaImmobiliari.getInstance().getUnita(istanza.getKey()));
                }
                else {
                    throw new IllegalArgumentException(this.getClass().getName() + ": istanza " + istanza.getKey() + " e' di tipo " + istanza.getValue().getName() + " non gestito nel risalvataggio");
                }
            } catch (Exception e) {
                LogErrori.getInstance().put(e.getMessage());
                StoreIstanzeErrori.getInstance().put(istanza.getKey(), istanza.getValue());
                result.set(false);
            }
        });

        return result.get();
    }
}
