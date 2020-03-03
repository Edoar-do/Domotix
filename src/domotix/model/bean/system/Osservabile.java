package domotix.model.bean.system;

import domotix.model.bean.device.Sensore;

/**
 * Interfaccia per identificare un oggetto "osservabile", ovvero a cui collegare un sensore in modo da monitorarne uno stato.
 *
 * @author paolopasqua
 * @see Sensore
 */
public interface Osservabile {
    /**
     * Aggiunge un sensore
     * @param sensore   istanza di Sensore da aggiungere
     * @return  true: aggiunta andata a buon fine; false: altrimenti
     */
    boolean addSensore(Sensore sensore);

    /**
     * Rimuove un sensore
     * @param sensore   istanza di Sensore da rimuovere
     */
    void removeSensore(Sensore sensore);

    /**
     * Rimuove un sensore dal nome indicato
     * @param nomeSensore   stringa per identificare il Sensore da rimuovere
     */
    void removeSensore(String nomeSensore);

    /**
     * Ritorna l'elenco di sensori collegati.
     * @return  array di Sensore
     */
    Sensore[] getSensori();
}
