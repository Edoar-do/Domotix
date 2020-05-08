package domotix.model.visitor;

/**
 * @author Edoardo Coppola, andrea , paolopasqua
 *         Questa classe astratta ha lo scopo di mediare
 *         fra L'interfaccia Visitor e le classi concrete che, estendendo questa
 *         classe, ne implementeranno i metodi Funge da adattatore fra
 *         l'interfaccia e la classe concreta che ne implementa i soli metodi
 *         utili
 * @see Visitor
 */
public abstract class AbstractVisitor implements Visitor {
    
    @Override
    public Object visita(Visitable visitable) {
        return visitable.fattiVisitare(this);
    }

    @Override
    public Object visitaStanza(Visitable stanza) {
        return null;
    }

    @Override
    public Object visitaArtefatto(Visitable artefatto) {
        return null;
    }

    @Override
    public Object visitaCategoriaSensore(Visitable categoriaSensori) {
        return null;
    }

    @Override
    public Object visitaCategoriaAttuatore(Visitable categoriaAttuatori) {
        return null;
    }

    @Override
    public Object visitaSensore(Visitable sensore) {
        return null;
    }

    @Override
    public Object visitaAttuatore(Visitable attuatore) {
        return null;
    }

    @Override
    public Object visitaSensoreOrologio(Visitable sensoreOrologio) {
        return null;
    }

    @Override
    public Object visitaAntecedente(Visitable antecedente) {
        return null;
    }

    @Override
    public Object visitaCondizione(Visitable condizione) {
        return null;
    }

    @Override
    public Object visitaInfoCostante(Visitable infoCostante) {
        return null;
    }

    @Override
    public Object visitaInfoTemporale(Visitable infoTemporale) {
        return null;
    }

    public Object visitaElencoDispositivi(Visitable elencoDispositivi) {
        return null;
    }

    public Object visitaSommarioDispositivi(Visitable sommarioDispositivi) {
        return null;
    }

    @Override
    public Object visitaUnitaImmobiliare(Visitable unitaImmobiliare) {
        return null;
    }

    @Override
    public Object visitaRegola(Visitable regola) {
        return null;
    }

    @Override
    public Object visitaParametro(Visitable parametro) {
        return null;
    }

    @Override
    public Object visitaModalita(Visitable modalita) {
        return null;
    }

    @Override
    public Object visitaInfoRilevabile(Visitable infoRilevabile) {
        return null;
    }

    @Override
    public Object visitaConseguente(Visitable conseguente) {
        return null;
    }

    @Override
    public Object visitaInfoVariabile(Visitable infoVariabile) {
        return null;
    }

    @Override
    public Object visitaAzione(Visitable azione) {
        return null;
    }

    public Object visitaElencoUnitaImmobiliari(Visitable elencoUnitaImmobiliari) {
        return null;
    }

    @Override
    public Object visitaInfoSensoriale(Visitable infoSensoriale) {
        return null;
    }


}



