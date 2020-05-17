package domotix.model;

import domotix.model.bean.UnitaImmobiliare;
import domotix.model.bean.device.*;
import domotix.model.bean.regole.Azione;
import domotix.model.bean.system.Artefatto;
import domotix.model.bean.system.Stanza;

import java.util.List;

/**
 * Interfaccia che rappresenta il singolo accesso al model.
 * In questo modo si ha una sola istanza da passare, dove utilizzato il model.
 */
public interface Model {
    /**
     * Ritorna la lista delle unità contenute.
     * @return  lista di istanze di unità immobiliare
     */
    List<UnitaImmobiliare> getListaUnita();
    /**
     * Ritorna la stanza identificata
     * @param stanza    identificativo stanza
     * @param unita identificativo unita
     * @return  istanza della stanza
     */
    Stanza getStanza(String stanza, String unita);
    /**
     * Ritorna la lista delle categorie sensore
     * @return  lista di istanze di categoria sensore
     */
    List<CategoriaSensore> getCategorieSensore();
    /**
     * Ritorna la lista delle categorie attuatore
     * @return  lista di istanze di categoria attuatore
     */
    List<CategoriaAttuatore> getCategorieAttuatore();
    /**
     * Ritorna l'artefatto identificato
     * @param artefatto identificativo artefatto
     * @param stanza    identificativo stanza
     * @param unita identificativo unita
     * @return  istanza dell'artefatto
     */
    Artefatto getArtefatto(String artefatto, String stanza, String unita);
    /**
     * Ritorna l'unita immobiliare identificato
     * @param unita identificativo unita
     * @return  istanza dell'unita
     */
    UnitaImmobiliare getUnita(String unita);
    /**
     * Genera l'unita' base che deve esistere per il funzionamento del programma.
     *
     * @return  istanza di una nuova unita immobiliare inizializzata con i dati di default del programma
     */
    UnitaImmobiliare generaUnitaBase();
    /**
     * Ritorna il sensore orologio
     * @return  istanza del sensore orologio
     */
    SensoreOrologio getOrologio();
    /**
     * Ritorna il sensore identificato
     * @param sensore   identificativo sensore
     * @return  istanza del sensore
     */
    Sensore getSensore(String sensore);
    /**
     * Ritorna la lista di sensori contenuti
     * @return  lista di sensori
     */
    List<Sensore> getSensori();
    /**
     * Ritorna l'attuatore identificato
     * @param attuatore identificativo attuatore
     * @return  istanza dell'attuatore
     */
    Attuatore getAttuatore(String attuatore);
    /**
     * Ritorna la lista di attuatori contenuti
     * @return  lista di attuatori
     */
    List<Attuatore> getAttuatori();
    /**
     * Ritorna l'azione programmata identificata
     * @param id    identificativo azione
     * @return  istanza dell'azione
     */
    Azione getAzioneProgrammata(String id);
    /**
     * Ritorna la lista di azioni programmate
     * @return lista di istanze di azioni
     */
    List<Azione> getAzioniProgrammate();
    /**
     * Ritorna la lista di id di azioni programmate
     * @return lista di istanze di azioni
     */
    List<String> getIdAzioniProgrammate();
    /**
     * Ritorna la categoria attuatore identificata
     * @param nomeCategoriaAttuatore    identificativo categoria attuatore
     * @return  istanza della categoria attuatore
     */
    CategoriaAttuatore getCategoriaAttuatore(String nomeCategoriaAttuatore);
    /**
     * Ritorna la categoria sensore identificata
     * @param nomeCategoriaSensore  identificativo categoria sensore
     * @return  istanza della categoria sensore
     */
    CategoriaSensore getCategoriaSensore(String nomeCategoriaSensore);

    void addUnita(UnitaImmobiliare unitaImmobiliare);

    void removeUnita(String unitaImmobiliare);

    void addCategoriaSensore(CategoriaSensore cs);

    void removeCategoriaSensore(String cs);

    void addCategoriaAttuatore(CategoriaAttuatore ca);

    void removeCategoriaAttuatore(String ca);

    void removeAzioneProgrammata(String idAzione);

    void addAzioneProgrammata(Azione a);
}
