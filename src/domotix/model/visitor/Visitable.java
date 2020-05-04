package domotix.model.visitor;

/**
 * @author Edoardo Coppola
 * Interfaccia che rende possibile la visita degli oggetti che la implementano al fine di carpirne informazioni utili alla loro rappresentazione
 * @see Visitor
 */
public interface Visitable {

    /**
     * Metodo che consente la visita ad un oggetto da parte di un oggetto Visitor
     * @param v visitor che visita l'oggetta da visitare
     * @return ciò che ritorna la vista da parte del visitor
     */
    Object fattiVisitare(Visitor v);
}
