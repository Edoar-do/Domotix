package domotix.model.bean.regole;

import domotix.model.visitor.Visitable;

/**
 * Interfaccia per identificare un elemento che puo' apparire in una condizione
 * all'interno dell'antecedente di una regola
 * @author andrea
 */
public interface InfoSensoriale extends Visitable {
    // TODO: rimuovi dalle classi che implementano questa classe l'"implements Visitable" perche' e' inutile
    /**
     * Metodo che recupera il valore dell'InfoSensoriale
     * @return Il valore dell'informazione sensoriale
     */
    public Object getValore();
}
