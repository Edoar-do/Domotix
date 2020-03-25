package domotix.model.io;

import domotix.model.bean.UnitaImmobiliare;
import domotix.model.bean.device.*;
import domotix.model.bean.regole.*;
import domotix.model.bean.system.Artefatto;
import domotix.model.bean.system.Stanza;
import domotix.model.io.datilocali.ScritturaDatiLocali;

/**
 * Interfaccia per l'implementazione di strutture per il salvataggio dei dati.
 * Si intende semplificare l'aggiunta di un'eventuale alternativa per i meccanismi di salvataggio dati.
 *
 * A livello logico, non vi sono precedenze a livello di scrittura se viene salvato tutto.
 * La sequenza completa di scrittura e':
 *  -   Categorie dei Sensori
 *      -   InfoRilevabile
 *  -   Categorie degli Attuatori
 *      -   Modalita' della categoria
 *  -   Unita immobiliare
 *      -   Stanze
 *          -   Sensori
 *          -   Attuatori
 *          -   Artefatti
 *              -   Sensori
 *              -   Attuatori
 *
 * @author paolopasqua
 */
public interface ScritturaDatiSalvati {

    /**
     * Unica istanza del meccanismo di salvataggio e caricamento implementato.
     * Riferirsi a questo metodo all'interno del programma in modo da non dover sostituire alcuna chiamata in caso di cambiamenti interni.
     *
     * @return  unica istanza accessibile
     * @throws Exception    Eccezione lanciata per diverse circostanze relative al meccanismo implementato.
     */
    static ScritturaDatiSalvati getInstance() throws Exception { return ScritturaDatiLocali.getInstance(); }

    /**
     * Scrittura/Sovrascrittura nei dati memorizzati di una singola istanza di CategoriaSensore.
     *
     * @param cat  istanza da salvare
     * @throws Exception    Eccezione lanciata per diverse circostanze interne.
     * @see CategoriaSensore
     */
    void salva(CategoriaSensore cat) throws Exception;

    /**
     * Scrittura/Sovrascrittura nei dati memorizzati di una singola istanza di InfoRilevabile.
     *
     * @param info istanza da salvare
     * @param cat  stringa identificativa cui la InfoRilevabile riferisce
     * @throws Exception    Eccezione lanciata per diverse circostanze interne.
     * @see Modalita
     */
    void salva(InfoRilevabile info, String cat) throws Exception;

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

    /**
     * Scrittura/Sovrascrittura nei dati memorizzati di una singola istanza di Regola.
     *
     * @param regola  istanza da salvare
     * @throws Exception    Eccezione lanciata per diverse circostanze interne.
     * @see Regola
     */
    void salva(Regola regola, String unita) throws Exception;

}
