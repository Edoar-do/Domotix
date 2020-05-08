package domotix.controller.io;

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
     * Rinfresca gli attributi per ciascun Sensore contenuto nel model.
     *
     * @throws Exception    eventuale eccezione lanciata per diverse circostanze interne.
     * @see domotix.model.bean.device.Sensore
     */
    void rinfrescaSensori() throws Exception;

}
