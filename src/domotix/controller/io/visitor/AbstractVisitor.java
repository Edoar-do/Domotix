package domotix.controller.io.visitor;

/**
 * @author  Edoardo Coppola
 * Questa classe astratta ha lo scopo di mediare fra L'interfaccia Visitor e le classi concrete che, estendendo questa classe, ne implementeranno i metodi
 * Funge da adattatore fra l'interfaccia e la classe concreta che ne implementa i soli metodi utili
 * @see Visitor
 */
public abstract class AbstractVisitor implements Visitor {

    public Object visitaUnita(Visitable unita){ return null; }

    public Object visitaStanza(Visitable stanza){ return null; }

    public Object visitaArtefatto(Visitable artefatto){ return null; }

    public Object visitaCategoriaSensori(Visitable categoriaSensori){ return null; }

    public Object visitaCategoriaAttuatori(Visitable categoriaAttuatori){ return null; }

    public Object visitaSensore(Visitable sensore){ return null; }

    public Object visitaAttuatore(Visitable attuatore){ return null; }

}



