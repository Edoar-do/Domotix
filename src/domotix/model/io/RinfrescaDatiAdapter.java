package domotix.model.io;

import domotix.model.ElencoSensori;

/**
 * Classe che implementa l'interfaccia RinfrescaDati come base per le implementazioni complesse.
 * Si intende rendere semplice la retrocompatibilita' per i cambiamenti dell'interfaccia.
 *
 * @author paolopasqua
 */
public class RinfrescaDatiAdapter implements RinfrescaDati {
    @Override
    public void rinfrescaSensori(ElencoSensori elenco) throws Exception {

    }
}
