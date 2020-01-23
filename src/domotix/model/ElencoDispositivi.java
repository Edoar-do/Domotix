package domotix.model;

import domotix.model.bean.device.Dispositivo;

import java.util.HashMap;
import java.util.Map;

public class ElencoDispositivi {
    private Map<String, Dispositivo> elenco;

    public ElencoDispositivi(Map<String, Dispositivo> elencoIniziale) {
        elenco = elencoIniziale;
    }

    public ElencoDispositivi() {
        elenco = new HashMap<>();
    }

    public Dispositivo getDispositivo(String key) {
        return elenco.get(key);
    }

    public Dispositivo[] getDispositivi() {
        return elenco.values().toArray(new Dispositivo[0]);
    }

    public boolean contains(String key) {
        return elenco.get(key) != null;
    }

    public void remove(String key, boolean decrementa) {
        Dispositivo dispositivo = elenco.get(key);
        if (dispositivo != null) {
            dispositivo.decrNumAssociazioni();
            elenco.remove(key, decrementa);
        }
    }

    public void remove(Dispositivo dispositivo, boolean dectementa) {
        this.remove(dispositivo.getNome(), dectementa);
    }

    public void remove(Dispositivo dispositivo) {
        this.remove(dispositivo.getNome(), false);
    }

    public boolean add(String key, Dispositivo dispositivo, boolean incrementa) {
        if (this.contains(key)) {
            return false;
        }
        if (incrementa) {
            dispositivo.incrNumAssociazioni();
        }
        elenco.put(key, dispositivo);
        return true;
    }

    public boolean add(Dispositivo dispositivo, boolean incrementa) {
        return this.add(dispositivo.getNome(), dispositivo, incrementa);
    }

    public boolean add(Dispositivo dispositivo) {
        return this.add(dispositivo, false);
    }

    public boolean add(String key, Dispositivo dispositivo) {
        return this.add(key, dispositivo, false);
    }
}
