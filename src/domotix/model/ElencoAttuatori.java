package domotix.model;

import domotix.model.bean.device.Attuatore;
import domotix.model.bean.device.Dispositivo;
import domotix.model.bean.device.Sensore;
import domotix.model.util.SommarioDispositivi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class ElencoAttuatori extends SommarioDispositivi {

    private static ElencoAttuatori instance = null;

    public static ElencoAttuatori getInstance() {
        if (instance == null)
            instance = new ElencoAttuatori();
        return instance;
    }

    private ElencoAttuatori() {
        super();
    }

    @Override
    public Attuatore getDispositivo(String key) {
        return (Attuatore) super.getDispositivo(key);
    }

    @Override
    public Attuatore[] getDispositivi() {
        return Arrays.copyOf(super.getDispositivi(), super.getDispositivi().length, Attuatore[].class);
    }

    @Override
    public void elaboraAggiunta(Dispositivo dato) {
        if (dato instanceof Attuatore)
            super.elaboraAggiunta(dato);
    }
}
