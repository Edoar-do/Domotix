package domotix.model.bean.regole;

import domotix.model.visitor.Visitable;
import domotix.model.visitor.Visitor;

/**
 * Classe che rappresenta un InfoSensoriale costante
 * @author andrea
 */
public class InfoCostante implements Visitable, InfoSensoriale {
    private Object info;

    public InfoCostante(Object info) {
        this.info = info;
    }

    public Object getInfo() {
        return info;
    }

    @Override
    public Object getValore() {
        return this.info;
    }

    @Override
    public Object fattiVisitare(Visitor v) {
        return v.visitaInfoCostante(this);
    }
}
