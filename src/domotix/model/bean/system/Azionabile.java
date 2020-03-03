package domotix.model.bean.system;

import domotix.model.bean.device.Attuatore;

/**
 * Interfaccia per identificare un oggetto "azionabile", ovvero a cui collegare un attuatore in modo da monitorarne uno stato.
 *
 * @author paolopasqua
 * @see Attuatore
 */
public interface Azionabile {
    /**
     * Aggiunge un attuatore
     * @param attuatore   istanza di Attuatore da aggiungere
     * @return  true: aggiunta andata a buon fine; false: altrimenti
     */
    boolean addAttuatore(Attuatore attuatore);

    /**
     * Rimuove un attuatore
     * @param attuatore   istanza di Attuatore da rimuovere
     */
    void removeAttuatore(Attuatore attuatore);

    /**
     * Rimuove un attuatore dal nome indicato
     * @param nomeAttuatore   stringa per identificare il Attuatore da rimuovere
     */
    void removeAttuatore(String nomeAttuatore);

    /**
     * Ritorna l'elenco di attuatori collegati.
     * @return  array di Attuatore
     */
    Attuatore[] getAttuatori();
}
