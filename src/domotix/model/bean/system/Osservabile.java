package domotix.model.bean.system;

import domotix.model.bean.device.Sensore;

public interface Osservabile {
    boolean addSensore(Sensore sensore);
    void removeSensore(Sensore sensore);
    void removeSensore(String nomeSensore);
    Sensore[] getSensori();
}
