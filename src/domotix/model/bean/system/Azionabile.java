package domotix.model.bean.system;

import domotix.model.bean.device.Attuatore;

public interface Azionabile {
    boolean addAttuatore(Attuatore attuatore);
    void removeAttuatore(Attuatore attuatore);
    void removeAttuatore(String nomeAttuatore);
    Attuatore[] getAttuatori();
}
