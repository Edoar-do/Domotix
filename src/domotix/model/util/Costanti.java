package domotix.model.util;

import java.io.File;

/**
 * Classe per la definizione di costanti di utilita' nel programma.
 * Si intende centralizzare la localizzazione delle costanti, in modo da individuar velocemente quella ricercata e il relativo valore.
 * Aggiungere qui le costanti di utilita' da definire.
 *
 * @author paolopasqua
 */
public final class Costanti {
    private Costanti() {}

    /*
    Costanti utilizzate per la costruzione dei percorsi in cui trovare i dati locali del programma
     */
    public static final String PERCORSO_RADICE_UTENTE = System.getProperty("user.home");
    public static final String PERCORSO_CARTELLA_APP = PERCORSO_RADICE_UTENTE + File.separator + ".DOMOTIX";
    public static final String PERCORSO_CARTELLA_DATI = PERCORSO_CARTELLA_APP + File.separator + "dati";
    public static final String PERCORSO_CARTELLA_CATEGORIE_SENSORI = PERCORSO_CARTELLA_DATI + File.separator + "categorie_sensori";
    public static final String NOME_CARTELLA_INFO_RILEVABILE = "info_rilevabile";
    public static final String PERCORSO_CARTELLA_CATEGORIE_ATTUATORI = PERCORSO_CARTELLA_DATI + File.separator + "categorie_attuatori";
    //public static final String PERCORSO_CARTELLA_MODALITA = PERCORSO_CARTELLA_DATI + File.separator + "modalita";
    public static final String NOME_CARTELLA_MODALITA = "modalita";
    public static final String PERCORSO_CARTELLA_UNITA_IMMOB = PERCORSO_CARTELLA_DATI + File.separator + "unita_immob";
    //public static final String PERCORSO_CARTELLA_STANZE = PERCORSO_CARTELLA_DATI + File.separator + "stanze";
    //public static final String PERCORSO_CARTELLA_ARTEFATTI = PERCORSO_CARTELLA_DATI + File.separator + "artefatti";
    public static final String NOME_CARTELLA_STANZE = "stanze";
    public static final String NOME_CARTELLA_ARTEFATTI = "artefatti";
    public static final String NOME_CARTELLA_REGOLE = "regole";
    public static final String PERCORSO_CARTELLA_SENSORI = PERCORSO_CARTELLA_DATI + File.separator + "sensori";
    public static final String PERCORSO_CARTELLA_ATTUATORI = PERCORSO_CARTELLA_DATI + File.separator + "attuatori";

    /*
    Costanti utilizzate dai lettori e scrittori XML per il salvataggio e lettura dei dati locali
     */
    public static final String NODO_XML_CATEGORIA_SENSORE = "categoria_sensore";
    public static final String NODO_XML_CATEGORIA_SENSORE_NOME = "nome";
    public static final String NODO_XML_CATEGORIA_SENSORE_TESTOLIBERO = "testolibero";
    public static final String NODO_XML_CATEGORIA_SENSORE_INFORILEVABILE = "informazione_rilevabile";
    public static final String NODO_XML_INFORILEVABILE = "informazione_rilevabile";
    public static final String NODO_XML_INFORILEVABILE_NOME = "nome";
    public static final String NODO_XML_INFORILEVABILE_NUMERICA = "numerica";
    public static final String NODO_XML_CATEGORIA_ATTUATORE = "categoria_attuatore";
    public static final String NODO_XML_CATEGORIA_ATTUATORE_NOME = "nome";
    public static final String NODO_XML_CATEGORIA_ATTUATORE_TESTOLIBERO = "testolibero";
    public static final String NODO_XML_CATEGORIA_ATTUATORE_MODALITA = "modalita";
    public static final String NODO_XML_MODALITA = "modalita";
    public static final String NODO_XML_MODALITA_NOME = "nome";
    public static final String NODO_XML_MODALITA_PARAMETRO = "parametro";
    public static final String NODO_XML_MODALITA_PARAMETRO_NOME = "nome";
    public static final String NODO_XML_MODALITA_PARAMETRO_VALORE = "valore";
    public static final String NODO_XML_UNITA_IMMOB = "unita_immob";
    public static final String NODO_XML_UNITA_IMMOB_NOME = "nome";
    public static final String NODO_XML_UNITA_IMMOB_STANZA = "stanza";
    public static final String NODO_XML_STANZA = "stanza";
    public static final String NODO_XML_STANZA_NOME = "nome";
    public static final String NODO_XML_STANZA_UNITA_IMMOB = "unita_immobiliare";
    public static final String NODO_XML_STANZA_SENSORE = "sensore";
    public static final String NODO_XML_STANZA_ATTUATORE = "attuatore";
    public static final String NODO_XML_STANZA_ARTEFATTO = "artefatto";
    public static final String NODO_XML_ARTEFATTO = "artefatto";
    public static final String NODO_XML_ARTEFATTO_NOME = "nome";
    public static final String NODO_XML_ARTEFATTO_UNITA_IMMOB = "unita_immobiliare";
    public static final String NODO_XML_ARTEFATTO_SENSORE = "sensore";
    public static final String NODO_XML_ARTEFATTO_ATTUATORE = "attuatore";
    public static final String NODO_XML_SENSORE = "sensore";
    public static final String NODO_XML_SENSORE_NOME = "nome";
    public static final String NODO_XML_SENSORE_STATO = "stato";
    public static final String NODO_XML_SENSORE_CATEGORIA = "categoria";
    public static final String NODO_XML_SENSORE_NOME_VALORE = "nome_info";
    public static final String NODO_XML_SENSORE_VALORE = "valore";
    public static final String NODO_XML_ATTUATORE = "attuatore";
    public static final String NODO_XML_ATTUATORE_NOME = "nome";
    public static final String NODO_XML_ATTUATORE_STATO = "stato";
    public static final String NODO_XML_ATTUATORE_CATEGORIA = "categoria";
    public static final String NODO_XML_ATTUATORE_MODALITA = "modalita";
    public static final String NODO_XML_REGOLA = "regola";
    public static final String NODO_XML_REGOLA_ID = "id";
    public static final String NODO_XML_REGOLA_STATO = "stato";
    public static final String NODO_XML_ANTECEDENTE = "antecedente";
    public static final String NODO_XML_ANTECEDENTE_OPLOGICO = "op_logico";
    public static final String NODO_XML_CONDIZIONE = "condizione";
    public static final String NODO_XML_CONDIZIONE_POSIZIONE = "posizione";
    public static final String NODO_XML_CONDIZIONE_SINISTRA = "sinistra";
    public static final String NODO_XML_CONDIZIONE_DESTRA = "destra";
    public static final String NODO_XML_CONDIZIONE_OPERATORE = "operatore";
    public static final String NODO_XML_INFO_SENSORIALE = "info_sensoriale";
    public static final String NODO_XML_INFO_SENSORIALE_SENSORE = "sensore";
    public static final String NODO_XML_INFO_SENSORIALE_INFO_RILEV = "info_rilevabile";
    public static final String NODO_XML_INFO_SENSORIALE_COSTANTE = "valore";
    public static final String NODO_XML_CONSEGUENTE = "conseguente";
    public static final String NODO_XML_AZIONE = "azione";
    public static final String NODO_XML_AZIONE_ATTUATORE = "attuatore";
    public static final String NODO_XML_AZIONE_MODALITA = "modalita";
    public static final String NODO_XML_AZIONE_PARAMETRO = "parametro";




}
