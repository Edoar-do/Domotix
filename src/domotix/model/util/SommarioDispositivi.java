package domotix.model.util;

import domotix.model.bean.device.Dispositivo;

import java.util.HashMap;
import java.util.Map;

public class SommarioDispositivi implements ObserverList<Dispositivo> {
    private Map<String, Dispositivo> elenco;

    public SommarioDispositivi() {
        this.elenco = new HashMap<>();
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

    @Override
    public void elaboraRimozione(Dispositivo dato) {
        //lato osservatore

        //controllo presenza
        if (elenco.containsKey(dato.getNome())) {
            //rimuovo solo se il numero di associazioni e' zero
            if (dato.getNumeroAssociazioni() == 0)
                elenco.remove(dato.getNome());
        }
    }

    @Override
    public void elaboraAggiunta(Dispositivo dato) {
        //lato osservatore

        //se gia' contenuto allora non eseguo nulla
        if (!elenco.containsKey(dato.getNome())) {
            elenco.put(dato.getNome(), dato);
        }
    }
}
