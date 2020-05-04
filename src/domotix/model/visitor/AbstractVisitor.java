package domotix.model.visitor;

/**
 * @author  Edoardo Coppola, andrea
 * Questa classe astratta ha lo scopo di mediare fra L'interfaccia Visitor e le classi concrete che, estendendo questa classe, ne implementeranno i metodi
 * Funge da adattatore fra l'interfaccia e la classe concreta che ne implementa i soli metodi utili
 * @see Visitor
 */
public abstract class AbstractVisitor implements Visitor {

    public Object visitaStanza(Visitable stanza) {
        return null;
    }

    public Object visitaArtefatto(Visitable artefatto) {
        return null;
    }

    public Object visitaCategoriaSensore(Visitable categoriaSensori) {
        return null;
    }

    public Object visitaCategoriaAttuatore(Visitable categoriaAttuatori) {
        return null;
    }

    public Object visitaSensore(Visitable sensore) {
        return null;
    }

    public Object visitaAttuatore(Visitable attuatore) {
        return null;
    }

    public Object visitaSensoreOrologio(Visitable sensoreOrologio) {
        return null;
    }

    public Object visitaAntecedente(Visitable antecedente) {
        return null;
    }

    public Object visitaCondizione(Visitable condizione) {
        return null;
    }

    public Object visitaInfoCostante(Visitable infoCostante) {
        return null;
    }

    public Object visitaInfoTemporale(Visitable infoTemporale) {
        return null;
    }

    public Object visitaElencoDispositivi(Visitable elencoDispositivi) {
        return null;
    }

    public Object visitaSommarioDispositivi(Visitable sommarioDispositivi) {
        return null;
    }

    public Object visitaUnitaImmobiliare(Visitable unitaImmobiliare) {
        return null;
    }

    public Object visitaRegola(Visitable regola) {
        return null;
    }

    public Object visitaParametro(Visitable parametro) {
        return null;
    }

    public Object visitaModalita(Visitable modalita) {
        return null;
    }

    public Object visitaInfoRilevabile(Visitable infoRilevabile) {
        return null;
    }

    public Object visitaConseguente(Visitable conseguente) {
        return null;
    }

    public Object visitaInfoVariabile(Visitable infoVariabile) {
        return null;
    }

    public Object visitaAzione(Visitable azione) {
        return null;
    }

    public Object visitaElencoUnitaImmobiliari(Visitable elencoUnitaImmobiliari) {
        return null;
    }

}



