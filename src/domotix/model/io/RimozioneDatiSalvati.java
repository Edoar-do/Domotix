package domotix.model.io;

import domotix.model.bean.UnitaImmobiliare;
import domotix.model.bean.device.*;
import domotix.model.bean.regole.Regola;
import domotix.model.bean.system.Artefatto;
import domotix.model.bean.system.Stanza;
import domotix.model.io.datilocali.RimozioneDatiLocali;

import java.util.List;

/**
 * Interfaccia per l'implementazione di strutture per la rimozione dei dati.
 * Si intende semplificare l'aggiunta di un'eventuale alternativa per i meccanismi di rimozione dei dati salvati.
 *
 * A livello logico, per le connessioni tra le entità, si hanno delle precedenze di rimozione:
 *  -   Sensori / Attuatori
 *  -   Artefatti
 *  -   Stanze
 *  -   Unita immobiliari
 *  -   Categorie dei Sensori
 *  -   Categorie degli Attuatori
 *
 *  Le rimozioni comportano a catena la cancellazione le entita' ad essa legate:
 *  -   Categorie dei Sensori
 *      -   InfoRilevabile della categoria
 *  -   Categorie degli Attuatori
 *      -   Modalita' della categoria
 *  -   Unita immobiliare
 *      -   Stanze
 *          -   Sensori
 *          -   Attuatori
 *          -   Artefatti
 *          -   Regole
 *  -   Sensori
 *  -   Attuatori
 *
 * @author paolopasqua
 */
public interface RimozioneDatiSalvati {
    /**
     * Unica istanza del meccanismo di salvataggio e caricamento implementato.
     * Riferirsi a questo metodo all'interno del programma in modo da non dover sostituire alcuna chiamata in caso di cambiamenti interni.
     *
     * @return  unica istanza accessibile
     * @throws Exception    Eccezione lanciata per diverse circostanze relative al meccanismo implementato.
     */
    static RimozioneDatiSalvati getInstance() throws Exception { return RimozioneDatiLocali.getInstance(); }

    /**
     * Cancellazione nei dati memorizzati di una singola istanza di CategoriaSensore.
     *
     * @param cat  istanza da salvare
     * @throws Exception    Eccezione lanciata per diverse circostanze interne.
     * @see CategoriaSensore
     */
    void rimuoviCategoriaSensore(String cat) throws Exception;

    /**
     * Cancellazione nei dati memorizzati di una singola istanza di InfoRilevabile.
     *
     * @param info istanza da rimuovere
     * @param cat  stringa identificativa cui l'informazione rilevabile riferisce
     * @throws Exception    Eccezione lanciata per diverse circostanze interne.
     * @see InfoRilevabile
     */
    void rimuoviInfoRilevabile(String info, String cat) throws Exception;

    /**
     * Cancellazione nei dati memorizzati di una singola istanza di CategoriaAttuatore.
     *
     * @param cat  istanza da salvare
     * @throws Exception    Eccezione lanciata per diverse circostanze interne.
     * @see CategoriaAttuatore
     */
    void rimuoviCategoriaAttuatore(String cat) throws Exception;

    /**
     * Cancellazione nei dati memorizzati di una singola istanza di Modalita.
     *
     * @param modalita istanza da salvare
     * @param cat  stringa identificativa cui la modalita riferisce
     * @throws Exception    Eccezione lanciata per diverse circostanze interne.
     * @see Modalita
     */
    void rimuoviModalita(String modalita, String cat) throws Exception;

    /**
     * Cancellazione nei dati memorizzati di una singola istanza di UnitaImmobiliare.
     *
     * @param unita  istanza da salvare
     * @throws Exception    Eccezione lanciata per diverse circostanze interne.
     * @see UnitaImmobiliare
     */
    void rimuoviUnitaImmobiliare(String unita) throws Exception;

    /**
     * Cancellazione nei dati memorizzati di una singola istanza di Stanza.
     *
     * @param stanza istanza da salvare
     * @param unita  stringa identificativa cui la stanza riferisce
     * @throws Exception    Eccezione lanciata per diverse circostanze interne.
     * @see Stanza
     */
    void rimuoviStanza(String stanza, String unita) throws Exception;

    /**
     * Cancellazione nei dati memorizzati di una singola istanza di Artefatto.
     *
     * @param artefatto istanza da salvare
     * @param unita  stringa identificativa cui l'artefatto riferisce
     * @throws Exception    Eccezione lanciata per diverse circostanze interne.
     * @see Stanza
     */
    void rimuoviArtefatto(String artefatto, String unita) throws Exception;

    /**
     * Cancellazione nei dati memorizzati di una singola istanza di Sensore.
     *
     * @param sensore  istanza da salvare
     * @throws Exception    Eccezione lanciata per diverse circostanze interne.
     * @see Sensore
     */
    void rimuoviSensore(String sensore) throws Exception;

    /**
     * Cancellazione nei dati memorizzati di una singola istanza di Attuatore.
     *
     * @param attuatore  istanza da salvare
     * @throws Exception    Eccezione lanciata per diverse circostanze interne.
     * @see Attuatore
     */
    void rimuoviAttuatore(String attuatore) throws Exception;

    /**
     * Cancellazione nei dati memorizzati di una singola istanza di Regola                                                                                                                                                                                                                                                                                                                                                                                                                                                   .
     *
     * @param idRegola  istanza da rimuovere
     * @throws Exception    Eccezione lanciata per diverse circostanze interne.
     * @see domotix.model.bean.regole.Regola
     */
    void rimuoviRegola(String idRegola, String unita) throws Exception;


    /**
     * Confronta tutte le CategoriaSensore presenti nei dati salvati con la lista indicata.
     * Tutti quelli assenti nella lista parametro saranno eliminati per mantenere sincronizzati i dati
     * salvati con le entita' logiche.
     *
     * @param entita    lista di entita' logiche presenti
     * @throws Exception    Eccezione lanciata per diverse circostanze interne.
     * @see CategoriaSensore
     */
    void sincronizzaCategorieSensore(List<CategoriaSensore> entita) throws Exception;

    /**
     * Confronta tutte le InfoRilevabile di CategoriaSensore presenti nei dati salvati con la lista indicata.
     * Tutti quelli assenti nella lista parametro saranno eliminati per mantenere sincronizzati i dati
     * salvati con le entita' logiche.
     *
     * @param entita    istanza di CategoriaSensore di cui effettuare la sincronizzazione delle InfoRilevabili
     * @throws Exception    Eccezione lanciata per diverse circostanze interne.
     * @see CategoriaSensore
     * @see InfoRilevabile
     */
    void sincronizzaInfoRilevabile(CategoriaSensore entita) throws Exception;

    /**
     * Confronta tutte le CategoriaAttuatore presenti nei dati salvati con la lista indicata.
     * Tutti quelli assenti nella lista parametro saranno eliminati per mantenere sincronizzati i dati
     * salvati con le entita' logiche.
     *
     * @param entita    lista di entita' logiche presenti
     * @throws Exception    Eccezione lanciata per diverse circostanze interne.
     * @see CategoriaAttuatore
     */
    void sincronizzaCategorieAttuatore(List<CategoriaAttuatore> entita) throws Exception;

    /**
     * Confronta tutte le Modalita di CategoriaAttuatore presenti nei dati salvati con la lista indicata.
     * Tutti quelli assenti nella lista parametro saranno eliminati per mantenere sincronizzati i dati
     * salvati con le entita' logiche.
     *
     * @param entita    istanza di CategoriaAttuatore di cui effettuare la sincronizzazione delle Modalita
     * @throws Exception    Eccezione lanciata per diverse circostanze interne.
     * @see CategoriaAttuatore
     * @see Modalita
     */
    void sincronizzaModalita(CategoriaAttuatore entita) throws Exception;

    /**
     * Confronta tutte le UnitaImmobiliari presenti nei dati salvati con la lista indicata.
     * Tutti quelli assenti nella lista parametro saranno eliminati per mantenere sincronizzati i dati
     * salvati con le entita' logiche.
     *
     * @param entita    lista di entita' logiche presenti
     * @throws Exception    Eccezione lanciata per diverse circostanze interne.
     * @see UnitaImmobiliare
     */
    void sincronizzaUnitaImmobiliari(List<UnitaImmobiliare> entita) throws Exception;

    /**
     * Confronta tutte le Stanza di UnitaImmobiliare presenti nei dati salvati con la lista indicata.
     * Tutti quelli assenti nella lista parametro saranno eliminati per mantenere sincronizzati i dati
     * salvati con le entita' logiche.
     *
     * @param entita    istanza di UnitaImmobiliare di cui effettuare la sincronizzazione delle Stanze
     * @throws Exception    Eccezione lanciata per diverse circostanze interne.
     * @see UnitaImmobiliare
     * @see Stanza
     */
    void sincronizzaStanze(UnitaImmobiliare entita) throws Exception;

    /**
     * Confronta tutte i Artefatto di UnitaImmobiliare presenti nei dati salvati con la lista indicata.
     * Tutti quelli assenti nella lista parametro saranno eliminati per mantenere sincronizzati i dati
     * salvati con le entita' logiche.
     *
     * @param entita    istanza di UnitaImmobiliare di cui effettuare la sincronizzazione dei Artefatto
     * @throws Exception    Eccezione lanciata per diverse circostanze interne.
     * @see Stanza
     * @see Artefatto
     */
    void sincronizzaArtefatti(UnitaImmobiliare entita) throws Exception;

    /**
     * Confronta tutte i Sensore presenti nei dati salvati con la lista indicata.
     * Tutti quelli assenti nella lista parametro saranno eliminati per mantenere sincronizzati i dati
     * salvati con le entita' logiche.
     *
     * @param entita    lista di entita' logiche presenti
     * @throws Exception    Eccezione lanciata per diverse circostanze interne.
     * @see Sensore
     */
    void sincronizzaSensori(List<Sensore> entita) throws Exception;

    /**
     * Confronta tutte i Attuatore presenti nei dati salvati con la lista indicata.
     * Tutti quelli assenti nella lista parametro saranno eliminati per mantenere sincronizzati i dati
     * salvati con le entita' logiche.
     *
     * @param entita    lista di entita' logiche presenti
     * @throws Exception    Eccezione lanciata per diverse circostanze interne.
     * @see Attuatore
     */
    void sincronizzaAttuatori(List<Attuatore> entita) throws Exception;

    /**
     * Confronta tutte le Regola presenti nei dati salvati con la lista indicata.
     * Tutti quelli assenti nella lista parametro saranno eliminati per mantenere sincronizzati i dati
     * salvati con le entita' logiche.
     *
     * @param entita    lista di entita' logiche presenti
     * @throws Exception    Eccezione lanciata per diverse circostanze interne.
     * @see domotix.model.bean.regole.Regola
     */
    void sincronizzaRegole(UnitaImmobiliare entita) throws Exception;
}
