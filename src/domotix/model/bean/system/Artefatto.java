package domotix.model.bean.system;

import domotix.model.visitor.Visitable;
import domotix.model.visitor.Visitor;

public class Artefatto extends Sistema implements Visitable {

    private String unitaOwner;

    public Artefatto(String nome) {
        super(nome);
        this.unitaOwner = null;
    }

    /**
     * Metodo che imposta il riferimento all'UnitaImmobiliare di appartenenza.
     * @param unitaOwner Il nome dell'UnitaImmobiliare
     */
    public void setUnitaOwner(String unitaOwner) {
        this.unitaOwner = unitaOwner;
    }

    /**
     * Metodo che ritorna il nome dell'UnitaImmobiliare di appartenenza.
     * @return Il nome dell'UnitaImmobiliare
     */
    public String getUnitaOwner() {
        return unitaOwner;
    }

    @Override
    public Object fattiVisitare(Visitor v) {
        return v.visitaArtefatto(this);
    }
}
