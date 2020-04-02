package domotix.controller;

import domotix.model.*;
import domotix.model.bean.UnitaImmobiliare;
import domotix.model.bean.device.CategoriaAttuatore;
import domotix.model.bean.device.CategoriaSensore;
import domotix.model.gestioneerrori.LogErrori;
import domotix.model.gestioneerrori.StoreIstanzeErrori;
import domotix.model.io.RimozioneDatiSalvati;
import domotix.model.io.ScritturaDatiSalvati;
import domotix.view.menus.MenuErroreChiusura;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

        //Salvo categorie sensori logiche presenti
        ElencoCategorieSensori.getInstance().getCategorie().forEach(categoriaSensore -> {
            try {
                ScritturaDatiSalvati.getInstance().salva(categoriaSensore);
            } catch (Exception e) {
                LogErrori.getInstance().put(e.getMessage());
                StoreIstanzeErrori.getInstance().put(categoriaSensore.getNome(), categoriaSensore.getClass());
                result.set(false);
            }
        });
        //Salvo categorie attuatori logiche presenti
        ElencoCategorieAttuatori.getInstance().getCategorie().forEach(categoriaAttuatore -> {
            try {
                ScritturaDatiSalvati.getInstance().salva(categoriaAttuatore);
            } catch (Exception e) {
                LogErrori.getInstance().put(e.getMessage());
                StoreIstanzeErrori.getInstance().put(categoriaAttuatore.getNome(), categoriaAttuatore.getClass());
                result.set(false);
            }
        });
        //Salvo unita immobiliare logiche presenti
        ElencoUnitaImmobiliari.getInstance().getUnita().forEach(unitaImmobiliare -> {
            try {
                ScritturaDatiSalvati.getInstance().salva(unitaImmobiliare);
            } catch (Exception e) {
                LogErrori.getInstance().put(e.getMessage());
                StoreIstanzeErrori.getInstance().put(unitaImmobiliare.getNome(), unitaImmobiliare.getClass());
                result.set(false);
            }
        });
        //Salvo Azioni programmate logiche presenti
        ElencoAzioniProgrammate.getInstance().getCoppieIdAzione().forEach((id, azione) -> {
            try {
                ScritturaDatiSalvati.getInstance().salva(id, azione);
            } catch (Exception e) {
                LogErrori.getInstance().put(e.getMessage());
                StoreIstanzeErrori.getInstance().put(id, azione.getClass());
                result.set(false);
            }
        });
        //Sincronizzo le entita' logiche con quelle fisiche (elimino i dati salvati cancellati logicamente)
        try {
            RimozioneDatiSalvati.getInstance().sincronizzaCategorieSensore(ElencoCategorieSensori.getInstance().getCategorie());
        } catch (Exception e) {
            LogErrori.getInstance().put(e.getMessage());
            result.set(false);
        }
        try {
            RimozioneDatiSalvati.getInstance().sincronizzaCategorieAttuatore(ElencoCategorieAttuatori.getInstance().getCategorie());
        } catch (Exception e) {
            LogErrori.getInstance().put(e.getMessage());
            result.set(false);
        }
        try {
            RimozioneDatiSalvati.getInstance().sincronizzaUnitaImmobiliari(ElencoUnitaImmobiliari.getInstance().getUnita());
        } catch (Exception e) {
            LogErrori.getInstance().put(e.getMessage());
            result.set(false);
        }
        try {
            RimozioneDatiSalvati.getInstance().sincronizzaSensori(Stream.of(ElencoSensori.getInstance().getDispositivi()).collect(Collectors.toList()));
        } catch (Exception e) {
            LogErrori.getInstance().put(e.getMessage());
            result.set(false);
        }
        try {
            RimozioneDatiSalvati.getInstance().sincronizzaAttuatori(Stream.of(ElencoAttuatori.getInstance().getDispositivi()).collect(Collectors.toList()));
        } catch (Exception e) {
            LogErrori.getInstance().put(e.getMessage());
            result.set(false);
        }
        try {
            RimozioneDatiSalvati.getInstance().sincronizzaAzioniProgrammate(ElencoAzioniProgrammate.getInstance().getCoppieIdAzione());
        } catch (Exception e) {
            LogErrori.getInstance().put(e.getMessage());
            result.set(false);
        }

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
