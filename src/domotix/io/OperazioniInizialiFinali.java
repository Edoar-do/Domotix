package domotix.io;

import domotix.logicUtil.MyMenu;
import domotix.model.ElencoCategorieAttuatori;
import domotix.model.ElencoCategorieSensori;
import domotix.model.ElencoUnitaImmobiliari;
import domotix.model.bean.UnitaImmobiliare;
import domotix.model.bean.device.CategoriaAttuatore;
import domotix.model.bean.device.CategoriaSensore;
import domotix.model.io.LetturaDatiSalvati;
import domotix.model.io.ScritturaDatiSalvati;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Classe di una sola istanza per le operazioni iniziali e finali del programma.
 * Ovvero, la lettura dei dati iniziali con la gestione degli errori e la richiesta all'utente di come procedere nel caso
 * e la scrittura dei dati in uso con la gestione degli errori e la richiesta all'utente di come procedere se presenti.
 *
 * @author paolopasqua
 */
public class OperazioniInizialiFinali {

    private static OperazioniInizialiFinali instance = null;

    /**
     * Recupera la unica istanza della classe
     * @return  unica istanza della classe
     */
    public static OperazioniInizialiFinali getInstance() {
        if (instance == null)
            instance = new OperazioniInizialiFinali();
        return instance;
    }

    private OperazioniInizialiFinali() { }

    /**
     * Metodo di esecuzione delle operazioni di apertura del programma, con verifica della lettura e richiesta all'utente di come agire in caso di errore.
     *
     * @param in InputStream su cui eseguire le letture per le scelte utente
     * @param out PrintStream su cui far visualizzare all'utente il contenuto
     * @return  false se in caso di errore l'utente sceglie di uscire dal programma; true se il programma puo' aprirsi.
     */
    public boolean apri(InputStream in, PrintStream out) {
        boolean esito = popolaDati();

        if (!esito) {
            out.println("Errore/i in lettura dati salvati:");
            List<String> errori = LogErrori.getInstance().popAll();
            if (errori.isEmpty())
                out.println("errore sconosciuto");
            else
                errori.forEach(s -> out.println(s));

            MyMenu erroreApertura = new MyMenu("Proseguire con l'apertura del programma?", new String[]{"Continua"});
            return erroreApertura.scegli(in, out) == 1; //false: esci dal programma; true: entra nel programma
        }

        return true;
    }

    /**
     * Metodo di esecuzione delle operazioni di chiusura del programma, con verifica della scrittura e richiesta all'utente di come agire in caso di errore.
     *
     * @param in InputStream su cui eseguire le letture per le scelte utente
     * @param out PrintStream su cui far visualizzare all'utente il contenuto
     * @return false se in caso di errore non chiudere il programma; true se il programma puo' terminare.
     */
    public boolean chiudi(InputStream in, PrintStream out) {
        boolean esito = salvaDati();

        //finche' non ho un esito positivo del salvataggio (o utente sceglie altre azioni)
        while(!esito) {
            out.println("Errore/i in salvataggio dati salvati:");
            List<String> errori = LogErrori.getInstance().popAll();
            if (errori.isEmpty())
                out.println("errore sconosciuto");
            else
                errori.forEach(s -> out.println(s));

            //Chiedo all'utente se mantenere il programma aperto (1), ritentare il salvataggio (2) o uscire senza salvare (0)
            MyMenu erroreChiusura = new MyMenu("Proseguire con l'apertura del programma?", new String[]{"Annulla chiusura", "Ritenta salvataggio"});
            switch (erroreChiusura.scegli(in, out)) {
                case 0:
                    return true;
                case 1:
                    return false;
                case 2:
                    esito = risalvaDati(StoreIstanzeErrori.getInstance().popAll());
            }
        }

        return true;
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

        ElencoCategorieSensori.getInstance().getCategorie().forEach(categoriaSensore -> {
            try {
                ScritturaDatiSalvati.getInstance().salva(categoriaSensore);
            } catch (Exception e) {
                LogErrori.getInstance().put(e.getMessage());
                StoreIstanzeErrori.getInstance().put(categoriaSensore.getNome(), categoriaSensore.getClass());
                result.set(false);
            }
        });

        ElencoCategorieAttuatori.getInstance().getCategorie().forEach(categoriaAttuatore -> {
            try {
                ScritturaDatiSalvati.getInstance().salva(categoriaAttuatore);
            } catch (Exception e) {
                LogErrori.getInstance().put(e.getMessage());
                StoreIstanzeErrori.getInstance().put(categoriaAttuatore.getNome(), categoriaAttuatore.getClass());
                result.set(false);
            }
        });

        ElencoUnitaImmobiliari.getInstance().getUnita().forEach(unitaImmobiliare -> {
            try {
                ScritturaDatiSalvati.getInstance().salva(unitaImmobiliare);
            } catch (Exception e) {
                LogErrori.getInstance().put(e.getMessage());
                StoreIstanzeErrori.getInstance().put(unitaImmobiliare.getNome(), unitaImmobiliare.getClass());
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
