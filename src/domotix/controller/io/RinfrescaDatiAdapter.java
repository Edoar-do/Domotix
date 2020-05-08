package domotix.controller.io;

import java.util.List;

import domotix.model.bean.device.Sensore;

/**
 * Classe che implementa l'interfaccia RinfrescaDati come base per le implementazioni complesse.
 * Si intende rendere semplice la retrocompatibilita' per i cambiamenti dell'interfaccia.
 *
 * @author paolopasqua
 */
public class RinfrescaDatiAdapter implements RinfrescaDati {
    @Override
    public void rinfrescaSensori(List<Sensore> elenco) throws Exception {

    }
}
