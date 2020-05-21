package domotix.model.bean.regole;

import domotix.model.visitor.Visitable;

/**
 * Interfaccia per identificare un elemento che puo' apparire in una condizione
 * all'interno dell'antecedente di una regola
 * @author andrea
 */
public interface InfoSensoriale extends Visitable {
    /**
     * Metodo che recupera il valore dell'InfoSensoriale
     * @return Il valore dell'informazione sensoriale
     */
    public Object getValore();
}
