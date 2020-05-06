package domotix.model;

import domotix.model.bean.device.Attuatore;
import domotix.model.bean.device.Dispositivo;
import domotix.model.bean.device.Sensore;
import domotix.model.util.SommarioDispositivi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Classe di accesso al Model per gli Attuatori salvati. Implementa l'interfaccia SommarioDispositivi in quanto l'elenco
 * e' popolato in automatico con le modifiche degli attuatori collegati a stanze/artefatti.
 *
 * @see Attuatore
 * @see SommarioDispositivi
 * @see domotix.model.bean.system.Stanza
 * @see domotix.model.bean.system.Artefatto
 */
public class ElencoAttuatori extends SommarioDispositivi {

    public ElencoAttuatori() {
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
