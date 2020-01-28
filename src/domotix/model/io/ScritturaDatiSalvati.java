package domotix.model.io;

import domotix.model.bean.UnitaImmobiliare;
import domotix.model.bean.device.*;
import domotix.model.bean.system.Artefatto;
import domotix.model.bean.system.Stanza;
import domotix.model.io.datilocali.ScritturaDatiLocali;

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