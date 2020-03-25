package domotix.model.io;

import domotix.model.ElencoSensori;
import domotix.model.io.datilocali.RinfrescaDatiLocali;

/**
 *  Interfaccia per l'implementazione di strutture per il "rinfresco" dei dati, ovvero la ri-lettura di
 *  attributi di determinate entita' per avere valori aggiornati e sincronizzati con i dati salvati.
 *  Si intende semplificare l'aggiunta di un'eventuale alternativa per i meccanismi di caricamento dati.
 *
 *  A livello logico il rinfresco dei dati coinvolge ora solo un attributo di un'entita':
 *      -   valore rilevato dalla classe Sensore
 *
 *  Pertanto si limita l'implementazione all'elenco sopra.
 *
 * @author paolopasqua
 */
public interface RinfrescaDati {

    /**
     * Unica istanza del meccanismo di salvataggio e caricamento implementato.
     * Riferirsi a questo metodo all'interno del programma in modo da non dover sostituire alcuna chiamata in caso di cambiamenti interni.
     *
     * @return  unica istanza accessibile
     * @throws Exception    Eccezione lanciata per diverse circostanze relative al meccanismo implementato.
     */
    static RinfrescaDati getInstance() throws Exception { return RinfrescaDatiLocali.getInstance(); }

    /**
     * Rinfresca gli attributi per ciascun Sensore contenuto in ElencoSensori passato.
     *
     * @param elenco    elenco di da elaborare (gestito come singleton, percio' unica istanza)
     * @throws Exception    eventuale eccezione lanciata per diverse circostanze interne.
     * @see domotix.model.bean.device.Sensore
     * @see ElencoSensori
     */
    void rinfrescaSensori(ElencoSensori elenco) throws Exception;

}
