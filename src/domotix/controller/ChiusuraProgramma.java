package domotix.controller;

import domotix.model.bean.UnitaImmobiliare;
import domotix.model.bean.device.CategoriaAttuatore;
import domotix.model.bean.device.CategoriaSensore;
import domotix.controller.io.RimozioneDatiSalvati;
import domotix.controller.io.ScritturaDatiSalvati;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Classe per le operazioni finali del programma.
 * Ovvero, la scrittura dei dati in uso con la gestione degli errori e la richiesta all'utente di come procedere se presenti.
 *
 * @author paolopasqua
 */
public class ChiusuraProgramma {

    private ScritturaDatiSalvati scritturaDati = null;
    private RimozioneDatiSalvati rimozioneDati = null;
    private Recuperatore recuperatore = null;

    private List<String> erroriScrittura = null;
    private Map<String, Class> istanzeErrori = null;

    /**
     * Costruttore della classe.
     * 
     * @param scritturaDati istanza del controllore ScritturaDatiSalvati
     * @param recuperatore  istanza del controllore Recuperatore
     */
    public ChiusuraProgramma(ScritturaDatiSalvati scritturaDati, RimozioneDatiSalvati rimozioneDati, Recuperatore recuperatore) {
        this.scritturaDati = scritturaDati;
        this.rimozioneDati = rimozioneDati;
        this.recuperatore = recuperatore;

        this.erroriScrittura = new ArrayList<String>();
        this.istanzeErrori = new HashMap<String, Class>();
    }

    /**
     * Metodo di esecuzione delle operazioni di chiusura del programma, con verifica della scrittura e richiesta all'utente di come agire in caso di errore.
     *
     * @return false se in caso di errore non chiudere il programma; true se il programma puo' terminare.
     */
    public boolean chiudi() {
        boolean esito = false;

        if (erroriScrittura.isEmpty())
            esito = salvaDati();
        else {
            Map<String, Class> istanze = new HashMap<String, Class>();
            istanze.putAll(this.istanzeErrori);
            esito = risalvaDati(istanze);
        }

        return esito;
    }

    /**
     * Ritorna una lista di errori avvenuti durante l'ultima scrittura
     * @return  lista di stringhe contenenti il messaggio d'errore
     */
    public List<String> getErroriScrittura() {
        return erroriScrittura;
    }

    /**
     * Rimuove tutti gli errori presentati in una scrittura precedente
     */
    public void pulisciErrori() {
        this.erroriScrittura.clear();
        this.istanzeErrori.clear();
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
        
        this.pulisciErrori();

        //Salvo categorie sensori logiche presenti
        this.recuperatore.getCategorieSensore().forEach(categoriaSensore -> {
            try {
                this.scritturaDati.salva(categoriaSensore);
            } catch (Exception e) {
                this.appendSalvataggio(e.getMessage());
                this.istanzeErrori.put(categoriaSensore.getNome(), categoriaSensore.getClass());
                result.set(false);
            }
        });
        //Salvo categorie attuatori logiche presenti
        this.recuperatore.getCategorieAttuatore().forEach(categoriaAttuatore -> {
            try {
                this.scritturaDati.salva(categoriaAttuatore);
            } catch (Exception e) {
                this.appendSalvataggio(e.getMessage());
                this.istanzeErrori.put(categoriaAttuatore.getNome(), categoriaAttuatore.getClass());
                result.set(false);
            }
        });
        //Salvo unita immobiliare logiche presenti
        this.recuperatore.getListaUnita().forEach(unitaImmobiliare -> {
            try {
                this.scritturaDati.salva(unitaImmobiliare);
            } catch (Exception e) {
                this.appendSalvataggio(e.getMessage());
                this.istanzeErrori.put(unitaImmobiliare.getNome(), unitaImmobiliare.getClass());
                result.set(false);
            }
        });
        //Salvo Azioni programmate logiche presenti
        this.recuperatore.getCoppieIdAzione().forEach((id, azione) -> {
            try {
                this.scritturaDati.salva(id, azione);
            } catch (Exception e) {
                this.appendSalvataggio(e.getMessage());
                this.istanzeErrori.put(id, azione.getClass());
                result.set(false);
            }
        });
        //Sincronizzo le entita' logiche con quelle fisiche (elimino i dati salvati cancellati logicamente)
        try {
            this.rimozioneDati.sincronizzaCategorieSensore(this.recuperatore.getCategorieSensore());
        } catch (Exception e) {
            this.appendRimozione(e.getMessage());
            result.set(false);
        }
        try {
            this.rimozioneDati.sincronizzaCategorieAttuatore(this.recuperatore.getCategorieAttuatore());
        } catch (Exception e) {
            this.appendRimozione(e.getMessage());
            result.set(false);
        }
        try {
            this.rimozioneDati.sincronizzaUnitaImmobiliari(this.recuperatore.getListaUnita());
        } catch (Exception e) {
            this.appendRimozione(e.getMessage());
            result.set(false);
        }
        try {
            this.rimozioneDati.sincronizzaSensori(this.recuperatore.getSensori());
        } catch (Exception e) {
            this.appendRimozione(e.getMessage());
            result.set(false);
        }
        try {
            this.rimozioneDati.sincronizzaAttuatori(this.recuperatore.getAttuatori());
        } catch (Exception e) {
            this.appendRimozione(e.getMessage());
            result.set(false);
        }
        try {
            this.rimozioneDati.sincronizzaAzioniProgrammate(this.recuperatore.getCoppieIdAzione());
        } catch (Exception e) {
            this.appendRimozione(e.getMessage());
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
    private boolean risalvaDati(Map<String, Class> istanzeErrori) {
        AtomicBoolean result = new AtomicBoolean(true);

        this.pulisciErrori();

        istanzeErrori.forEach((key, value) -> {
            try {
                if (value.equals(CategoriaSensore.class)) {
                    this.scritturaDati.salva(this.recuperatore.getCategoriaSensore(key));
                }
                else if (value.equals(CategoriaAttuatore.class)) {
                    this.scritturaDati.salva(this.recuperatore.getCategoriaAttuatore(key));
                }
                else if (value.equals(UnitaImmobiliare.class)) {
                    this.scritturaDati.salva(this.recuperatore.getUnita(key));
                }
                else {
                    throw new IllegalArgumentException(this.getClass().getName() + ": istanza " + key + " e' di tipo " + value.getName() + " non gestito nel risalvataggio");
                }
            } catch (Exception e) {
                this.appendSalvataggio(e.getMessage());
                this.istanzeErrori.put(key, value);
                result.set(false);
            }
        });

        return result.get();
    }

    private void appendSalvataggio(String errore) {
        this.erroriScrittura.add("SALVATAGGIO DATO LOGICO: " + errore);
    }

    private void appendRimozione(String errore) {
        this.erroriScrittura.add("RIMOZIONE DATO FISICO: " + errore);
    }
}
