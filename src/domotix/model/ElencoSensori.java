package domotix.model;

import domotix.model.bean.UnitaImmobiliare;
import domotix.model.bean.device.Dispositivo;
import domotix.model.bean.device.Sensore;
import domotix.model.bean.device.SensoreOrologio;
import domotix.model.util.SommarioDispositivi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Classe di accesso al Model per i Sensori salvati. Implementa l'interfaccia SommarioDispositivi in quanto l'elenco
 * e' popolato in automatico con le modifiche dei sensori collegati a stanze/artefatti.
 * Implementa il modello Singleton per avere un'unica istanza di questa classe.
 *
 * @see Sensore
 * @see SommarioDispositivi
 * @see domotix.model.bean.system.Stanza
 * @see domotix.model.bean.system.Artefatto
 */
public class ElencoSensori extends SommarioDispositivi {

    private static ElencoSensori instance = null;

    /**
     * Recupera l'unica istanza dell'elenco.
     * @return  Unica istanza dell'elenco.
     */
    public static ElencoSensori getInstance() {
        if (instance == null)
            instance = new ElencoSensori();
        return instance;
    }

    private ElencoSensori() {
        super();
        elaboraAggiunta(SensoreOrologio.getInstance());
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
