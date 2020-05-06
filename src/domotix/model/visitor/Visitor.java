package domotix.model.visitor;

/**
 * @author Edoardo Coppola
 * Questa interfaccia ha il compito di fornire i metodi per visitare una data entità del progetto al fine di carpirne le informazioni utili alla sua rappresentazione
 * @see Visitable altra interfaccia per la realizzazione del pattern GoF Visitor
 */
public interface Visitor {

    // TODO: rimuovi metodi inutili (cioe' quelli inutilizzati)

    /**
     * Metodo che consente di visitare una data stanza visitabile
     *
     * @param stanza da visitare
     * @return una forma della rappresentazione della stanza
     */
    Object visitaStanza(Visitable stanza);

    /**
     * Metodo che consente di visitare un dato artefatto visitabile
     *
     * @param artefatto da visitare
     * @return una forma della rappresentazione dell'artefatto
     */
    Object visitaArtefatto(Visitable artefatto);

    /**
     * Metodo che consente di visitare una data categoria di sensori visitabile
     *
     * @param categoriaSensori da visitare
     * @return una forma della rappresentazione della categoria di sensori
     */
    Object visitaCategoriaSensore(Visitable categoriaSensori);

    /**
     * Metodo che consente di visitare una data categoria di attuatori visitabile
     *
     * @param categoriaAttuatori da visitare
     * @return una forma della rappresentazione della categoria di attuatori
     */
    Object visitaCategoriaAttuatore(Visitable categoriaAttuatori);

    /**
     * Metodo che consente di visitare un dato sensore visitabile
     *
     * @param sensore da visitare
     * @return una forma della rappresentazione del sensore
     */
    Object visitaSensore(Visitable sensore);

    /**
     * Metodo che consente di visitare un dato attuatore visitabile
     *
     * @param attuatore da visitare
     * @return una forma della rappresentazione dell'attuatore
     */
    Object visitaAttuatore(Visitable attuatore);

    Object visitaSensoreOrologio(Visitable sensoreOrologio);

    Object visitaAntecedente(Visitable antecedente);

    Object visitaCondizione(Visitable condizione);

    Object visitaInfoCostante(Visitable infoCostante);

    Object visitaInfoTemporale(Visitable infoTemporale);


    /**
     * Metodo che consente di visitare una data unità immobiliare visitabile
     *
     * @param unitaImmobiliare da visitare
     * @return una forma della rappresentazione dell'unià
     */
    Object visitaUnitaImmobiliare(Visitable unitaImmobiliare);

    Object visitaRegola(Visitable regola);

    Object visitaParametro(Visitable parametro);

    Object visitaModalita(Visitable modalita);

    Object visitaInfoRilevabile(Visitable infoRilevabile);

    Object visitaConseguente(Visitable conseguente);

    Object visitaInfoVariabile(Visitable infoVariabile);

    Object visitaAzione(Visitable azione);
}
