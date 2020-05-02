package domotix.controller.io.visitor;

import domotix.controller.io.visitor.Visitable;

/**
 * @author Edoardo Coppola
 * Questa interfaccia ha il compito di fornire i metodi per visitare una data entità del progetto al fine di carpirne le informazioni utili alla sua rappresentazione
 * @see Visitable altra interfaccia per la realizzazione del pattern GoF Visitor
 */
public interface Visitor {

    /**
     * Metodo che consente di visitare una data unità immobiliare visitabile
     * @param unita da visitare
     * @return una forma della rappresentazione dell'unià
     */
    Object visitaUnita(Visitable unita);

    /**
     * Metodo che consente di visitare una data stanza visitabile
     * @param stanza da visitare
     * @return una forma della rappresentazione della stanza
     */
    Object visitaStanza(Visitable stanza);

    /**
     * Metodo che consente di visitare un dato artefatto visitabile
     * @param artefatto da visitare
     * @return una forma della rappresentazione dell'artefatto
     */
    Object visitaArtefatto(Visitable artefatto);

    /**
     * Metodo che consente di visitare una data categoria di sensori visitabile
     * @param categoriaSensori da visitare
     * @return una forma della rappresentazione della categoria di sensori
     */
    Object visitaCategoriaSensori(Visitable categoriaSensori);

    /**
     * Metodo che consente di visitare una data categoria di attuatori visitabile
     * @param categoriaAttuatori da visitare
     * @return una forma della rappresentazione della categoria di attuatori
     */
    Object visitaCategoriaAttuatori(Visitable categoriaAttuatori);

    /**
     * Metodo che consente di visitare un dato sensore visitabile
     * @param sensore da visitare
     * @return una forma della rappresentazione del sensore
     */
    Object visitaSensore(Visitable sensore);

    /**
     * Metodo che consente di visitare un dato attuatore visitabile
     * @param attuatore da visitare
     * @return una forma della rappresentazione dell'attuatore
     */
    Object visitaAttuatore(Visitable attuatore);

}
