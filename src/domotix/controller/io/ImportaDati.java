package domotix.controller.io;

import domotix.model.bean.UnitaImmobiliare;
import domotix.model.bean.device.CategoriaAttuatore;
import domotix.model.bean.device.CategoriaSensore;

import java.util.List;

/**
 * Interfaccia per l'implementazione di strutture per l'importazione dei dati.
 * Si intende semplificare l'aggiunta di un'eventuale alternativa per i meccanismi di importazione dati.
 *
 * A livello logico, per le connessioni tra le entità, si hanno delle precedenze di lettura da rispettare:
 *  -   Categorie dei Sensori
 *  -   Categorie degli Attuatori
 *  -   Unita immobiliari
 *
 *  Ciascuna delle letture sopra comporta la lettura delle entita' da cui dipendono, infatti la sequenza completa di lettura e':
 *  -   Categorie dei Sensori
 *      -   InfoRilevabile della categoria
 *  -   Categorie degli Attuatori
 *      -   Modalita' della categoria
 *  -   Unita immobiliare
 *      -   Stanze
 *          -   Sensori
 *          -   Attuatori
 *          -   Artefatti
 *              -   Sensori
 *              -   Attuatori
 *          -   Regole
 *  Se queste precedenze non vengono rispettate si puo' incorrere in errori logici, come ad esempio un sensore la cui categoria
 *  non e' ancora stata letta.
 *
 * @author paolopasqua
 */
public interface ImportaDati {

    /**
     * Ritorna il nome di tutte le CategoriaSensore presenti nella libreria.
     *
     * @return  Lista di tutti i nomi delle istanze nella libreria.
     * @see CategoriaSensore
     */
    List<String> getNomiCategorieSensori();

    /**
     * Ritorna il nome di tutte le CategoriaAttuatore presenti nella libreria.
     *
     * @return  Lista di tutti i nomi delle istanze nella libreria.
     * @see CategoriaAttuatore
     */
    List<String> getNomiCategorieAttuatori();

    /**
     * Ritorna il nome di tutte le UnitaImmobiliare presenti nella libreria.
     *
     * @return  Lista di tutti i nomi delle istanze nella libreria.
     * @see UnitaImmobiliare
     */
    List<String> getNomiUnitaImmobiliare();

    /**
     * Lettura di una singola CategorieSensore presente nella librearia identificata dal nome.
     *
     * @param nome  stringa identificativa dell'istanza da leggere
     * @return  istanza presente nella libreria.
     * @throws Exception    Eccezione lanciata per diverse circostanze interne.
     * @see CategoriaSensore
     */
    CategoriaSensore leggiCategoriaSensore(String nome) throws Exception;

    /**
     * Lettura di una singola CategorieAttuatore presente nella libreria identificata dal nome.
     *
     * @param nome  stringa identificativa dell'istanza da leggere
     * @return  istanza presente nella libreria.
     * @throws Exception    Eccezione lanciata per diverse circostanze interne.
     * @see CategoriaAttuatore
     */
    CategoriaAttuatore leggiCategoriaAttuatore(String nome) throws Exception;

    /**
     * Lettura di una singola UnitaImmobiliare presente nella libreria identificata dal nome.
     *
     * @param nome  stringa identificativa dell'istanza da leggere
     * @return  istanza presente nella libreria.
     * @throws Exception    Eccezione lanciata per diverse circostanze interne.
     * @see UnitaImmobiliare
     */
    UnitaImmobiliare leggiUnitaImmobiliare(String nome) throws Exception;

    /**
     * Spostamento di una singola CategorieSensore presente nella librearia identificata dal nome nello storico dei dati importati.
     *
     * @param nome  stringa identificativa dell'istanza da leggere
     * @return  istanza presente nella libreria.
     * @throws Exception    Eccezione lanciata per diverse circostanze interne.
     * @see CategoriaSensore
     */
    boolean storicizzaCategoriaSensore(String nome) throws  Exception;

    /**
     * Spostamento di una singola CategoriaAttuatore presente nella librearia identificata dal nome nello storico dei dati importati.
     *
     * @param nome  stringa identificativa dell'istanza da leggere
     * @return  istanza presente nella libreria.
     * @throws Exception    Eccezione lanciata per diverse circostanze interne.
     * @see CategoriaAttuatore
     */
    boolean storicizzaCategoriaAttuatore(String nome) throws  Exception;


    /**
     * Spostamento di una singola UnitaImmobiliare presente nella librearia identificata dal nome nello storico dei dati importati.
     *
     * @param nome  stringa identificativa dell'istanza da leggere
     * @return  istanza presente nella libreria.
     * @throws Exception    Eccezione lanciata per diverse circostanze interne.
     * @see UnitaImmobiliare
     */
    boolean storicizzaUnitaImmobiliare(String nome) throws  Exception;
}
