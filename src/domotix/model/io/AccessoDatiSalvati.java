package domotix.model.io;

import domotix.model.bean.UnitaImmobiliare;
import domotix.model.bean.device.*;
import domotix.model.bean.system.Artefatto;
import domotix.model.bean.system.Stanza;
import domotix.model.io.datilocali.DatiLocali;

import java.util.List;

/**
 * Interfaccia per l'implementazione di strutture per il salvataggio e il caricamento dei dati.
 * Si intende semplificare l'aggiunta di un'eventuale alternativa per i meccanismi di salvataggio e caricamento dati.
 *
 * A livello logico, per le connessioni tra le entità, si hanno delle precedenze di lettura da rispettare:
 *  -   Categorie dei Sensori
 *  -   Categorie degli Attuatori
 *  -   Unita immobiliari
 *  Effettuate queste prime letture, e' possibile ri-effettuare la lettura di una qualsiasi entita' senza problemi.
 *  Ciascuna delle letture sopra comporta la lettura delle entita' da cui dipendono, infatti la sequenza completa di lettura e':
 *  -   Categorie dei Sensori
 *  -   Categorie degli Attuatori
 *      -   Modalita' della categoria
 *  -   Unita immobiliare
 *      -   Stanze
 *          -   Sensori
 *          -   Attuatori
 *          -   Artefatti
 *              -   Sensori
 *              -   Attuatori
 *  Se queste precedenze non vengono rispettate si puo' incorrere in errori logici, come ad esempio un sensore la cui categoria
 *  non e' ancora stata letta.
 *
 * @author paolopasqua
 */
public interface AccessoDatiSalvati {

    /**
     * Unica istanza del meccanismo di salvataggio e caricamento implementato.
     * Riferirsi a questo metodo all'interno del programma in modo da non dover sostituire alcuna chiamata in caso di cambiamenti interni.
     *
     * @return  unica istanza accessibile
     * @throws Exception    Eccezione lanciata per diverse circostanze relative al meccanismo implementato.
     */
    static AccessoDatiSalvati getInstance() throws Exception { return DatiLocali.getInstance(); }

    /**
     * Lettura di tutte le CategorieSensori presenti nei dati memorizzati.
     *
     * @return  Lista di tutte le istanze presenti nei dati memorizzati.
     * @throws Exception    Eccezione lanciata per diverse circostanze interne.
     * @see CategoriaSensore
     */
    List<CategoriaSensore> leggiCategorieSensori() throws Exception;

    /**
     * Lettura di una singola CategorieSensori presente nei dati memorizzati identificata dal nome.
     *
     * @param nome  stringa identificativa dell'istanza da leggere
     * @return  istanza presente nei dati memorizzati.
     * @throws Exception    Eccezione lanciata per diverse circostanze interne.
     * @see CategoriaSensore
     */
    CategoriaSensore leggiCategoriaSensore(String nome) throws Exception;

    /**
     * Lettura di tutte le CategoriaAttuatore presenti nei dati memorizzati.
     *
     * @return  Lista di tutte le istanze presenti nei dati memorizzati.
     * @throws Exception    Eccezione lanciata per diverse circostanze interne.
     * @see CategoriaAttuatore
     */
    List<CategoriaAttuatore> leggiCategorieAttuatori() throws Exception;

    /**
     * Lettura di una singola CategorieAttuatore presente nei dati memorizzati identificata dal nome.
     *
     * @param nome  stringa identificativa dell'istanza da leggere
     * @return  istanza presente nei dati memorizzati.
     * @throws Exception    Eccezione lanciata per diverse circostanze interne.
     * @see CategoriaAttuatore
     */
    CategoriaAttuatore leggiCategoriaAttuatore(String nome) throws Exception;

    /**
     * Lettura di una singola Modalita presente nei dati memorizzati identificata dal nome e categoria indicata.
     *
     * @param nome  stringa identificativa dell'istanza da leggere
     * @param categoria stringa identificativa della categoria cui la modalita riferisce
     * @return  istanza presente nei dati memorizzati.
     * @throws Exception    Eccezione lanciata per diverse circostanze interne.
     * @see Modalita
     */
    Modalita leggiModalita(String nome, String categoria) throws Exception;

    /**
     * Lettura di tutte le UnitaImmobiliare presenti nei dati memorizzati.
     *
     * @return  Lista di tutte le istanze presenti nei dati memorizzati.
     * @throws Exception    Eccezione lanciata per diverse circostanze interne.
     * @see UnitaImmobiliare
     */
    List<UnitaImmobiliare> leggiUnitaImmobiliare() throws Exception;

    /**
     * Lettura di una singola UnitaImmobiliare presente nei dati memorizzati identificata dal nome.
     *
     * @param nome  stringa identificativa dell'istanza da leggere
     * @return  istanza presente nei dati memorizzati.
     * @throws Exception    Eccezione lanciata per diverse circostanze interne.
     * @see UnitaImmobiliare
     */
    UnitaImmobiliare leggiUnitaImmobiliare(String nome) throws Exception;

    /**
     * Lettura di una singola Stanza presente nei dati memorizzati identificata dal nome e l'unita immobiliare in cui e' contenuta.
     *
     * @param nome  stringa identificativa dell'istanza da leggere
     * @param unitaImmob stringa identificativa dell'unita immobiliare cui la modalita riferisce
     * @return  istanza presente nei dati memorizzati.
     * @throws Exception    Eccezione lanciata per diverse circostanze interne.
     * @see Stanza
     */
    Stanza leggiStanza(String nome, String unitaImmob) throws Exception;

    /**
     * Lettura di un singolo Artefatto presente nei dati memorizzati identificata dal nome e l'unita immobiliare in cui e' contenuta.
     *
     * @param nome  stringa identificativa dell'istanza da leggere
     * @param unitaImmob stringa identificativa dell'unita immobiliare cui la modalita riferisce
     * @return  istanza presente nei dati memorizzati.
     * @throws Exception    Eccezione lanciata per diverse circostanze interne.
     * @see Artefatto
     */
    Artefatto leggiArtefatto(String nome, String unitaImmob) throws Exception;

    /**
     * Lettura di un singolo Sensore presente nei dati memorizzati identificato dal nome.
     *
     * @param nome  stringa identificativa dell'istanza da leggere
     * @return  istanza presente nei dati memorizzati.
     * @throws Exception    Eccezione lanciata per diverse circostanze interne.
     * @see Sensore
     */
    Sensore leggiSensore(String nome) throws Exception; //nome gia' composto con nome sensore e categoria

    /**
     * Lettura di un singolo Attuatore presente nei dati memorizzati identificato dal nome.
     *
     * @param nome  stringa identificativa dell'istanza da leggere
     * @return  istanza presente nei dati memorizzati.
     * @throws Exception    Eccezione lanciata per diverse circostanze interne.
     * @see Attuatore
     */
    Attuatore leggiAttuatore(String nome) throws Exception; //nome gia' composto con nome attuatore e categoria


    /**
     * Scrittura/Sovrascrittura nei dati memorizzati di una singola istanza di CategoriaSensore.
     *
     * @param cat  istanza da salvare
     * @throws Exception    Eccezione lanciata per diverse circostanze interne.
     * @see CategoriaSensore
     */
    void salva(CategoriaSensore cat) throws Exception;

    /**
     * Scrittura/Sovrascrittura nei dati memorizzati di una singola istanza di CategoriaAttuatore.
     *
     * @param cat  istanza da salvare
     * @throws Exception    Eccezione lanciata per diverse circostanze interne.
     * @see CategoriaAttuatore
     */
    void salva(CategoriaAttuatore cat) throws Exception;

    /**
     * Scrittura/Sovrascrittura nei dati memorizzati di una singola istanza di Modalita.
     *
     * @param modalita istanza da salvare
     * @param cat  stringa identificativa cui la modalita riferisce
     * @throws Exception    Eccezione lanciata per diverse circostanze interne.
     * @see Modalita
     */
    void salva(Modalita modalita, String cat) throws Exception;

    /**
     * Scrittura/Sovrascrittura nei dati memorizzati di una singola istanza di UnitaImmobiliare.
     *
     * @param unita  istanza da salvare
     * @throws Exception    Eccezione lanciata per diverse circostanze interne.
     * @see UnitaImmobiliare
     */
    void salva(UnitaImmobiliare unita) throws Exception;

    /**
     * Scrittura/Sovrascrittura nei dati memorizzati di una singola istanza di Stanza.
     *
     * @param stanza istanza da salvare
     * @param unita  stringa identificativa cui la stanza riferisce
     * @throws Exception    Eccezione lanciata per diverse circostanze interne.
     * @see Stanza
     */
    void salva(Stanza stanza, String unita) throws Exception;

    /**
     * Scrittura/Sovrascrittura nei dati memorizzati di una singola istanza di Artefatto.
     *
     * @param artefatto istanza da salvare
     * @param unita  stringa identificativa cui l'artefatto riferisce
     * @throws Exception    Eccezione lanciata per diverse circostanze interne.
     * @see Stanza
     */
    void salva(Artefatto artefatto, String unita) throws Exception;

    /**
     * Scrittura/Sovrascrittura nei dati memorizzati di una singola istanza di Sensore.
     *
     * @param sensore  istanza da salvare
     * @throws Exception    Eccezione lanciata per diverse circostanze interne.
     * @see Sensore
     */
    void salva(Sensore sensore) throws Exception;

    /**
     * Scrittura/Sovrascrittura nei dati memorizzati di una singola istanza di Attuatore.
     *
     * @param attuatore  istanza da salvare
     * @throws Exception    Eccezione lanciata per diverse circostanze interne.
     * @see Attuatore
     */
    void salva(Attuatore attuatore) throws Exception;

}
