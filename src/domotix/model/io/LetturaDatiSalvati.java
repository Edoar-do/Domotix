package domotix.model.io;

import domotix.model.bean.UnitaImmobiliare;
import domotix.model.bean.device.*;
import domotix.model.bean.regole.Antecedente;
import domotix.model.bean.regole.Azione;
import domotix.model.bean.regole.Conseguente;
import domotix.model.bean.regole.Regola;
import domotix.model.bean.system.Artefatto;
import domotix.model.bean.system.Stanza;
import domotix.model.io.datilocali.LetturaDatiLocali;

import java.util.List;

/**
 * Interfaccia per l'implementazione di strutture per il caricamento dei dati.
 * Si intende semplificare l'aggiunta di un'eventuale alternativa per i meccanismi di caricamento dati.
 *
 * A livello logico, per le connessioni tra le entità, si hanno delle precedenze di lettura da rispettare:
 *  -   Categorie dei Sensori
 *  -   Categorie degli Attuatori
 *  -   Unita immobiliari
 *  -   Azioni Programmate
 *  Effettuate queste prime letture, e' possibile ri-effettuare la lettura di una qualsiasi entita' senza problemi.
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
 *  -   Azioni Programmate
 *  Se queste precedenze non vengono rispettate si puo' incorrere in errori logici, come ad esempio un sensore la cui categoria
 *  non e' ancora stata letta.
 *
 * @author paolopasqua
 */
public interface LetturaDatiSalvati {

    /**
     * Unica istanza del meccanismo di salvataggio e caricamento implementato.
     * Riferirsi a questo metodo all'interno del programma in modo da non dover sostituire alcuna chiamata in caso di cambiamenti interni.
     *
     * @return  unica istanza accessibile
     * @throws Exception    Eccezione lanciata per diverse circostanze relative al meccanismo implementato.
     */
    static LetturaDatiSalvati getInstance() throws Exception { return LetturaDatiLocali.getInstance(); }

    /**
     * Ritorna il nome di tutte le CategoriaSensore presenti nei dati memorizzati.
     *
     * @return  Lista di tutti i nomi delle istanze nei dati memorizzati.
     * @see CategoriaSensore
     */
    List<String> getNomiCategorieSensori();

    /**
     * Ritorna il nome di tutte le InfoRilevabile presenti nei dati memorizzati.
     *
     * @return  Lista di tutti i nomi delle istanze nei dati memorizzati.
     * @see InfoRilevabile
     */
    List<String> getNomiInformazioniRilevabili(String categoriaSensore);

    /**
     * Ritorna il nome di tutte le CategoriaAttuatore presenti nei dati memorizzati.
     *
     * @return  Lista di tutti i nomi delle istanze nei dati memorizzati.
     * @see CategoriaAttuatore
     */
    List<String> getNomiCategorieAttuatori();

    /**
     * Ritorna il nome di tutte le Modalita presenti nei dati memorizzati.
     *
     * @return  Lista di tutti i nomi delle istanze nei dati memorizzati.
     * @see Modalita
     */
    List<String> getNomiModalita(String categoriaAttuatore);

    /**
     * Ritorna il nome di tutte le UnitaImmobiliare presenti nei dati memorizzati.
     *
     * @return  Lista di tutti i nomi delle istanze nei dati memorizzati.
     * @see UnitaImmobiliare
     */
    List<String> getNomiUnitaImmobiliare();

    /**
     * Ritorna il nome di tutte le Stanza presenti nei dati memorizzati.
     *
     * @return  Lista di tutti i nomi delle istanze nei dati memorizzati.
     * @see Stanza
     */
    List<String> getNomiStanze(String unitaImmobiliare);

    /**
     * Ritorna il nome di tutte le Artefatto presenti nei dati memorizzati.
     *
     * @return  Lista di tutti i nomi delle istanze nei dati memorizzati.
     * @see Artefatto
     */
    List<String> getNomiArtefatti(String unitaImmobiliare);

    /**
     * Ritorna il nome di tutte le Sensore presenti nei dati memorizzati.
     *
     * @return  Lista di tutti i nomi delle istanze nei dati memorizzati.
     * @see Sensore
     */
    List<String> getNomiSensori();

    /**
     * Ritorna il nome di tutte le Attuatore presenti nei dati memorizzati.
     *
     * @return  Lista di tutti i nomi delle istanze nei dati memorizzati.
     * @see Attuatore
     */
    List<String> getNomiAttuatori();

    /**
     * Ritorna il nome di tutte le Regole presenti nei dati memorizzati.
     *
     * @return  Lista di tutti i nomi delle istanze nei dati memorizzati.
     * @see Regola
     */
    List<String> getNomiRegole(String unita);

    /**
     * Ritorna l'id di tutte le Azioni programmate presenti nei dati memorizzati.
     *
     * @return  Lista di tutti i nomi delle istanze nei dati memorizzati.
     * @see domotix.model.ElencoAzioniProgrammate
     * @see domotix.model.bean.regole.Azione
     */
    List<String> getIdAzioniProgrammate();


    /**
     * Lettura di tutte le CategorieSensori presenti nei dati memorizzati.
     *
     * @return  Lista di tutte le istanze presenti nei dati memorizzati.
     * @throws Exception    Eccezione lanciata per diverse circostanze interne.
     * @see CategoriaSensore
     */
    List<CategoriaSensore> leggiCategorieSensori() throws Exception;

    /**
     * Lettura di una singola CategorieSensori presente nei dati memorizzati identificata dal nome.
     *
     * @param nome  stringa identificativa dell'istanza da leggere
     * @return  istanza presente nei dati memorizzati.
     * @throws Exception    Eccezione lanciata per diverse circostanze interne.
     * @see CategoriaSensore
     */
    CategoriaSensore leggiCategoriaSensore(String nome) throws Exception;

    /**
     * Lettura di una singola InfoRilevabile presente nei dati memorizzati identificata dal nome e categoria indicata.
     *
     * @param nome  stringa identificativa dell'istanza da leggere
     * @param categoria stringa identificativa della categoria cui la modalita riferisce
     * @return  istanza presente nei dati memorizzati.
     * @throws Exception    Eccezione lanciata per diverse circostanze interne.
     * @see InfoRilevabile
     */
    InfoRilevabile leggiInfoRilevabile(String nome, String categoria) throws Exception;

    /**
     * Lettura di tutte le CategoriaAttuatore presenti nei dati memorizzati.
     *
     * @return  Lista di tutte le istanze presenti nei dati memorizzati.
     * @throws Exception    Eccezione lanciata per diverse circostanze interne.
     * @see CategoriaAttuatore
     */
    List<CategoriaAttuatore> leggiCategorieAttuatori() throws Exception;

    /**
     * Lettura di una singola CategorieAttuatore presente nei dati memorizzati identificata dal nome.
     *
     * @param nome  stringa identificativa dell'istanza da leggere
     * @return  istanza presente nei dati memorizzati.
     * @throws Exception    Eccezione lanciata per diverse circostanze interne.
     * @see CategoriaAttuatore
     */
    CategoriaAttuatore leggiCategoriaAttuatore(String nome) throws Exception;

    /**
     * Lettura di una singola Modalita presente nei dati memorizzati identificata dal nome e categoria indicata.
     *
     * @param nome  stringa identificativa dell'istanza da leggere
     * @param categoria stringa identificativa della categoria cui la modalita riferisce
     * @return  istanza presente nei dati memorizzati.
     * @throws Exception    Eccezione lanciata per diverse circostanze interne.
     * @see Modalita
     */
    Modalita leggiModalita(String nome, String categoria) throws Exception;

    /**
     * Lettura di tutte le UnitaImmobiliare presenti nei dati memorizzati.
     *
     * @return  Lista di tutte le istanze presenti nei dati memorizzati.
     * @throws Exception    Eccezione lanciata per diverse circostanze interne.
     * @see UnitaImmobiliare
     */
    List<UnitaImmobiliare> leggiUnitaImmobiliare() throws Exception;

    /**
     * Lettura di una singola UnitaImmobiliare presente nei dati memorizzati identificata dal nome.
     *
     * @param nome  stringa identificativa dell'istanza da leggere
     * @return  istanza presente nei dati memorizzati.
     * @throws Exception    Eccezione lanciata per diverse circostanze interne.
     * @see UnitaImmobiliare
     */
    UnitaImmobiliare leggiUnitaImmobiliare(String nome) throws Exception;

    /**
     * Lettura di una singola Stanza presente nei dati memorizzati identificata dal nome e l'unita immobiliare in cui e' contenuta.
     *
     * @param nome  stringa identificativa dell'istanza da leggere
     * @param unitaImmob stringa identificativa dell'unita immobiliare cui la modalita riferisce
     * @return  istanza presente nei dati memorizzati.
     * @throws Exception    Eccezione lanciata per diverse circostanze interne.
     * @see Stanza
     */
    Stanza leggiStanza(String nome, String unitaImmob) throws Exception;

    /**
     * Lettura di un singolo Artefatto presente nei dati memorizzati identificata dal nome e l'unita immobiliare in cui e' contenuta.
     *
     * @param nome  stringa identificativa dell'istanza da leggere
     * @param unitaImmob stringa identificativa dell'unita immobiliare cui la modalita riferisce
     * @return  istanza presente nei dati memorizzati.
     * @throws Exception    Eccezione lanciata per diverse circostanze interne.
     * @see Artefatto
     */
    Artefatto leggiArtefatto(String nome, String unitaImmob) throws Exception;

    /**
     * Lettura di tutte le istanze Sensore presenti nei dati memorizzati.
     *
     * @return  lista di istanze presente nei dati memorizzati.
     * @throws Exception    Eccezione lanciata per diverse circostanze interne.
     * @see Sensore
     */
    List<Sensore> leggiSensori() throws Exception;

    /**
     * Lettura di un singolo Sensore presente nei dati memorizzati identificato dal nome.
     *
     * @param nome  stringa identificativa dell'istanza da leggere
     * @return  istanza presente nei dati memorizzati.
     * @throws Exception    Eccezione lanciata per diverse circostanze interne.
     * @see Sensore
     */
    Sensore leggiSensore(String nome) throws Exception; //nome gia' composto con nome sensore e categoria

    /**
     * Lettura di tutte le istanze di Attuatore presenti nei dati memorizzati.
     *
     * @return  lista di istanze presente nei dati memorizzati.
     * @throws Exception    Eccezione lanciata per diverse circostanze interne.
     * @see Attuatore
     */
    List<Attuatore> leggiAttuatori() throws Exception;

    /**
     * Lettura di un singolo Attuatore presente nei dati memorizzati identificato dal nome.
     *
     * @param nome  stringa identificativa dell'istanza da leggere
     * @return  istanza presente nei dati memorizzati.
     * @throws Exception    Eccezione lanciata per diverse circostanze interne.
     * @see Attuatore
     */
    Attuatore leggiAttuatore(String nome) throws Exception; //nome gia' composto con nome attuatore e categoria

    /**
     * Lettura di una singola Regola presente nei dati memorizzati identificata dal nome.
     *
     * @param idRegola  stringa identificativa dell'istanza da leggere
     * @return  istanza presente nei dati memorizzati.
     * @throws Exception    Eccezione lanciata per diverse circostanze interne.
     * @see Regola
     */
    Regola leggiRegola(String idRegola, String unita) throws Exception;

    /**
     * Lettura di una singola Azione programmata presente nei dati memorizzati identificata dal nome.
     *
     * @param id  stringa identificativa dell'istanza da leggere
     * @return  istanza presente nei dati memorizzati.
     * @throws Exception    Eccezione lanciata per diverse circostanze interne.
     * @see domotix.model.ElencoAzioniProgrammate
     * @see Azione
     */
    Azione leggiAzioneProgrammata(String id) throws Exception;

}
