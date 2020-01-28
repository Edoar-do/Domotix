package domotix.model;

import domotix.model.bean.UnitaImmobiliare;
import domotix.model.bean.device.Dispositivo;
import domotix.model.bean.device.Sensore;
import domotix.model.util.SommarioDispositivi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class ElencoSensori extends SommarioDispositivi {

    private static ElencoSensori instance = null;

    public static ElencoSensori getInstance() {
        if (instance == null)
            instance = new ElencoSensori();
        return instance;
    }

    private ElencoSensori() {
        super();
    }

    @Override
    public Sensore getDispositivo(String key) {
        return (Sensore) super.getDispositivo(key);
    }

    @Override
    public Sensore[] getDispositivi() {
        return Arrays.copyOf(super.getDispositivi(), super.getDispositivi().length, Sensore[].class);
    }

    @Override
    public void elaboraAggiunta(Dispositivo dato) {
        if (dato instanceof Sensore)
            super.elaboraAggiunta(dato);
    }
}
