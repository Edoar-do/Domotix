package domotix.view.menus;

/**
 * @author Edoardo Coppola
 * Classe che contiene pressoché tutte le costanti utilizzate nelle diverse classi del modulo View
 */
public class ViewConstants {
    public static final String SALVA_ED_ESCI = "Salva ed Esci";
    public static final String INDIETRO = "Indietro";
    public static final String UNITA_IMMOBILIARI_ESISTENTI = "Unità Immobiliari: ";
    public static final String GUIDA_IN_LINEA = " Consultare la guida in linea per maggiori informazioni";
    public static final String SUCCESSO_INSERIMENTO_REGOLA = "Regola id: %s inserita con successo";
    public static final String SUCCESSO_INSERIMENTO_COMPONENTE = "Condizione componente l'antecedente inserita con successo";
    public static final String SUCCESSO_RIMOZIONE_REGOLA = "Regola rimossa correttamente";
    public static final String ERRORE_INSERIMENTO_REGOLA = "Inserimento regola fallito." + GUIDA_IN_LINEA;
    public static final String ERRORE_INSERIMENTO_COMPONENTE_ANTECEDENTE = "Inserimento della condizione componente l'antecedente fallita." + GUIDA_IN_LINEA;
    public static final String ERRORE_INSERIMENTO_OP_LOGICO = "Errore nell'inserimento dell'operatore logico." + GUIDA_IN_LINEA;
    public static final String ERRORE_RIMOZIONE_REGOLA = "Rimozione della regola fallita." + GUIDA_IN_LINEA;
    public static final String SCELTA_LHS = "Scegli la variabile sensoriale da valutare: ";
    public static final String SCELTA_RELOP = "Scegli un operatore relazionale (se l'informazione rilevata dal sensore è scalare la scelta sara' automatica)";
    public static final String SCELTA_RHS = "Scegli la seconda variabile sensoriale da confrontare oppure inserisci una costante";
    public static final String ANTECEDENTE_SI_NO = "Desideri inserire un'antecedente? (Se scegli no verrà assegnato il valore TRUE di default)";
    public static final String RELOP_NECESSARIO = "Operatore relazionale necessario";
    public static final String LHS_NECESSARIO = "Variabile sensoriale necessaria!";
    public static final String RHS_NECESSARIO = "Variabile sensoriale o costante necessaria";
    public static final String RHS_IS_COSTANTE = "Vuoi inserire un valore costante numerico per completare la condizione? ";
    public static final String INSERIMENTO_COSTANTE_RHS = "Inserisci un valore numerico: ";
    public static final String CONTINUARE_CON_ANTECEDENTE = "Desideri continuare con la costruzione dell'antecedente? ";
    public static final String LOGIC_OP_NECESSARIO = "Operatore logico necessario!";
    public static final String ZERO_SENSORI = "Impossibile creare una nuova regola perche' non sono presenti sensori nell'unita'. Il manutentore deve prima crearne";
    public static final String INFO_DEL_SENSORE = "Informazione rilevabili dal sensore %s: ";
    public static final String SENSORI_UNITA = "Sensori presenti nell'unita' %s: ";
    public static final String[] OPERATORI_LOGICI = {"AND", "OR"};
    public static final String[] OPERATORI_RELAZIONALI = {"<", ">", "<=", ">=", "="};
    public static final String REGOLE_UNITA = "Elenco delle regole nell'unita %s";
    public static final String SUCCESSO_INSERIMENTO_AZIONE = "Azione del conseguente inserita con successo";
    public static final String ERRORE_INSERIMENTO_AZIONE = "Inserimento azione del conseguente fallito. " + GUIDA_IN_LINEA;
    public static final String ZERO_ATTUATORI = "Impossibile creare una nuova regola perché non sono presenti attuatori nell'unita'. Il manutentore deve prima crearne";
    public static final String CONTINUARE_CON_CONSEGUENTE = "Desideri continuare con la costruzione del conseguente? ";
    public static final String ALMENO_UNA_AZIONE = "E' necessario inserire almeno un'azione per il conseguente!";
    public static final String LHS_ATT_NECESSARIO = "E' necessario specificare l'attuatore per un'azione!";
    public static final String RHS_ATT_NECESSARIO = "E' necessario specificare una modalita' da assegnare all'attuatore!";
    public static final String ELENCO_MODALITA_ATTUATORE = "Elenco di tutte le modalità dell'attuatore: ";
    public static final String STRINGA_COST = "Vuoi inserire una stringa costante come secondo termine della condizione? (se no allora sceglierai una variabile sensoriale): ";
    public static final String INSERIMENTO_STRINGA_COSTANTE_RHS = "Inserisci una stringa costante come secondo termine della condizione: ";
    public static final String ATTUATORI_UNITA = "Attuatori presenti nell'unita'%s: ";
    public static final String SUCCESSO_INSERIMENTO_LHS = "Lhs della condizione inserito con successo";
    public static final String SUCCESSO_INSERIMENTO_REL_OP = "Operatore relazionale della condizione inserito con successo";
    public static final String COSTRUZIONE_CONSEGUENTE = "Costruisci il conseguente inserendone le azioni: ";
    public static final String INSERIMENTO_RHS_ORA = "Inserisci un orario di riferimento nel formato ora.minuto: ";
    public static final String ERRORE_VALIDITA_ORARIO = "Errore! L'orario inserito non è valido";
    public static final String CONDIZIONE_START = "Desideri inserire un istante di inizio 'start' dell'azione? ";
    public static final String ERRORE_CAMBIO_STATO_REGOLA = "Cambio di stato della regola fallito. " + GUIDA_IN_LINEA;
    public static final String SUCCESSO_DISATTIVAZIONE = "Regola disattivata correttamente";
    public static final String SUCCESSO_ATTIVAZIONE = "Regola attivata correttamente";
    public static final String SOSPENSIONE = "La regola non era attivabile a causa di alcuni suoi dispositivi spenti. E' stata quindi sospesa fino alla riaccensione di tali dispositivi";
    public static final String REGOLE_UNITA_ATTIVE_O_DISATTIVE = "Regole dell'unita' in stato 'Attiva' o 'Disattiva'. ATTENZIONE: le regole in stato 'Sospesa' non appaiono in elenco";
    public static final String INSERIMENTO_STANZA_FALLITO = "L'inserimento della stanza è fallito. " + GUIDA_IN_LINEA;
    public static final String SUCCESSO_INSERIMENTO_STANZA = "Inserimento stanza avvenuto con successo.";
    public static final String SUCCESSO_RIMOZIONE_STANZA = "Rimozione stanza avvenuta con successo";
    public static final String ERRORE_RIMOZIONE_STANZA = "Rimozione stanza fallita. "  + GUIDA_IN_LINEA;
    public static final String INSERIMENTO_NOME_STANZA = "Inserisci il nome della stanza da aggiungere all'unità immobiliare: ";
    public static final String ELENCO_STANZE = "Elenco delle stanze presenti nell'unità: ";
    public static final String SUCCESSO_CAMBIO_STATO = "Stato cambiato con successo ";
    public static final String ERRORE_CAMBIO_STATO = "Cambiamento dello stato fallito per ";
    public static final String SUCCESSO_CAMBIO_STATO_S = SUCCESSO_CAMBIO_STATO + "al sensore selezionato";
    public static final String SUCCESSO_CAMBIO_STATO_A = SUCCESSO_CAMBIO_STATO + "all'attuatore selezionato";
    public static final String ERRORE_CAMBIO_STATO_S = ERRORE_CAMBIO_STATO + "il sensore selezionato. " + GUIDA_IN_LINEA;
    public static final String ERRORE_CAMBIO_STATO_A = ERRORE_CAMBIO_STATO + "l'attuatore selezionato. " + GUIDA_IN_LINEA;
    public static final String ERRORE_INSERIMENTO_SENSORE = "Inserimento del sensore fallito. " + GUIDA_IN_LINEA;
    public static final String ERRORE_INSERIMENTO_ATTUATORE = "Inserimento dell'attuatore fallito. " + GUIDA_IN_LINEA;
    public static final String ERRORE_INSERIMENTO_ARTEFATTO = "Inserimento dell'artefatto fallito. " + GUIDA_IN_LINEA;
    public static final String INSERIMENTO_NOME_SENSORE_STANZA = "Inserisci il nome del sensore da aggiungere alla stanza: ";
    public static final String INSERIMENTO_NOME_ATTUATORE_STANZA = "Inserisci il nome dell'attuatore da aggiungere alla stanza: ";
    public static final String INSERIMENTO_NOME_ARTEFATTO_STANZA = "Inserisci il nome dell'artefatto da aggiungere alla stanza: ";
    public static final String ELENCO_CATEGORIE_SENSORI = "Elenco delle categorie di sensore esistenti: ";
    public static final String ELENCO_CATEGORIE_ATTUATORI = "Elenco delle categorie di attuatore esistenti: ";
    public static final String ELENCO_SENSORI_STANZA = "Elenco dei sensori della stanza: ";
    public static final String ELENCO_ATTUATORI_STANZA = "Elenco degli attuatori della stanza: ";
    public static final String ELENCO_ARTEFATTI_STANZA = "Elenco degli artefatti presenti nella stanza: ";
    public static final String SUCCESSO_INSERIMENTO_SENSORE = "Sensore aggiunto con successo";
    public static final String SUCCESSO_INSERIMENTO_ATTUATORE = "Attuatore inserito con successo";
    public static final String SUCCESSO_RIMOZIONE_ATTUATORE = "Attuatore rimosso con successo";
    public static final String SUCCESSO_RIMOZIONE_ARTEFATTO = "Artefatto rimosso con successo";
    public static final String SUCCESSO_RIMOZIONE_SENSORE = "Sensore rimosso con successo";
    public static final String ERRORE_RIMOZIONE_SENSORE = "Rimozione sensore fallita. " + GUIDA_IN_LINEA;
    public static final String ERRORE_RIMOZIONE_ATTUATORE = "Rimozione dell'attuatore fallita. " + GUIDA_IN_LINEA;
    public static final String SUCCESSO_INSERIMENTO_ARTEFATTO = "Artefatto inserito con successo";
    public static final String ERRORE_RIMOZIONE_ARTEFATTO = "Rimozione dell'artefatto fallita. " + GUIDA_IN_LINEA;
    public static final String TITOLO_AGGIUNTA_SENSORE_STANZA = "Inserisci un nuovo sensore o collega un sensore già esistente alla stanza attuale: ";
    public static final String[] AZIONI_AGGIUNTA_SENSORE_STANZA = {"Inserisci un nuovo sensore nella stanza", "Collega un sensore esistente alla stanza"};
    public static final String TITOLO_AGGIUNTA_ATTUATORE_STANZA = "Inserisci un nuovo attuatore o collega un attuatore già esistente alla stanza attuale: ";
    public static final String[] AZIONI_AGGIUNTA_ATTUATORE_STANZA = {"Inserisci un nuovo attuatore nella stanza", "Collega un attuatore esistente alla stanza"};
    public static final String ELENCO_SENSORI_COLLEGABILI = "Elenco dei sensori collegabili: ";
    public static final String ELENCO_ATTUATORI_COLLEGABILI = "Elenco dei attuatori collegabili: ";
    public static final String ATTENZIONE_NO_CATEGORIA_SENSORE = "Attenzione! Creare prima una categoria sensore";
    public static final String ATTENZIONE_NO_CATEGORIA_ATTUATORE = "Attenzione! Creare prima una categoria attuatore";
    public static final String MODALITA_OPERATIVE = "Modalità Operative impostabili all'attuatore: ";
    public static final String SUCCESSO_SETTAGGIO_MODALITA = "Modalità operativa %s impostata con successo";
    public static final String FALLIMENTO_SETTAGGIO_MODALITA = "Impostazione modalità operativa %s fallita. " + GUIDA_IN_LINEA;
    public static final String ELENCO_ARTEFATTI = "Elenco degli artefatti presenti nella stanza: ";
    public static final String ELENCO_ATTUATORI_ARTEFATTO = "Attuatori associati all'artefatto: ";
    public static final String INSERIMENTO_NOME_SENSORE_ARTEFATTO = "Inserisci il nome del sensore da aggiungere all'artefatto: ";
    public static final String INSERIMENTO_NOME_ATTUATORE_ARTEFATTO = "Inserisci il nome dell'attuatore da aggiungere all'artefatto: ";
    public static final String ELENCO_SENSORI_ARTEFATTTO = "Sensori dell'artefatto: ";
    public static final String NESSUNA_AZIONE_PROGRAMMATA = "Nessuna azione programmata";
    public static final String SUCCESSO_INSERIMENTO_UNITA = "L'inserimento della nuova unità immobiliare è avvenuto con successo";
    public static final String ERRORE_INSERIMENTO_UNITA = "L'inserimento della nuova unità immobiliare è fallito. " + GUIDA_IN_LINEA;
    public static final String SUCCESSO_RIMOZIONE_UNITA = "Rimozione dell'unità immobiliare scelta avvenuta con successo";
    public static final String ERRORE_RIMOZIONE_UNITA = "Rimozione dell'unità immobiliare scelta fallita. " + GUIDA_IN_LINEA;
    public static final String NOMI_UNITA_IMMOBILIARI_ESISTENTI = "Unità Immobiliare esistenti: ";
    public static final String NOME_NUOVA_UNITA = "Inserisci il nome della nuova unità immobiliare da aggiungere: ";
    public static final String INTRO_IMPORT_UNITA = "L'importazione delle unita' immobiliari di libreria è stata selezionata. Di seguito apparirà l'esito dell'importazione: ";
    public static final String IMPORT_UNITA_OK = "Importazione delle unita' immobiliari terminata con successo ";
    public static final String UNITA_NON_IMPORTATA = "L'unita' immobiliare %s non è stata importata. " + GUIDA_IN_LINEA;
    public static final String IMPORT_FAILED = "Importazione delle unita' immobiliari fallita completamente";
    public static final String INSERIMENTO_CATEGORIA_SENSORE = "Inserisci un nome per la nuova categoria di sensore";
    public static final String INSERIMENTO_TESTO_LIBERO = "Inserisci un testo libero di descrizione del sensore";
    public static final String CATEGORIE_ESISTENTI_SENSORI = "Elenco delle categorie di sensori esistenti: ";
    public static final String ERRORE_INSERIMENTO = "Inserimento della categoria fallito. " + GUIDA_IN_LINEA ;
    public static final String SUCCESSO_INSERIMENTO = "Inserimento della categoria avvenuto con successo";
    public static final String SUCCESSO_RIMOZIONE_CATEGORIA = "Rimozione categoria avvenuta con successo";
    public static final String ERRORE_RIMOZIONE_CATEGORIA = "Rimozione delle categoria fallita. " + GUIDA_IN_LINEA;
    public static final String TERMINATORE = "-q";
    public static final String SUCCESSO_INSERIMENTO_INFO = "L'informazione rilevabile è stata inserita con successo";
    public static final String ERRORE_INSERIMENTO_INFO = "L'inserimento dell'informazione rilevabile è fallito. " + GUIDA_IN_LINEA;
    public static final String INSERIMENTO_NOME_INFO = "Inserisci il nome dell'informazione rilevabile (inserire " + TERMINATORE + " per terminare) ";
    public static final String ALMENO_UNA_INFO = "Deve essere presente almeno un'informazione rilevabile per la categoria di sensori creata!";
    public static final String IS_NUMERICA = "L'informazione %s è numerica? ";
    public static final String INTRO_IMPORT_CAT_SENS = "L'importazione delle categorie di sensori di libreria è stata selezionata. Di seguito apparirà l'esito dell'importazione: ";
    public static final String CATEGORIA_NON_IMPORTATA = "La categoria %s non è stata importata. " + GUIDA_IN_LINEA;
    public static final String IMPORT_CAT_SENS_OK = "Importazione delle categorie di sensori terminato con successo";
    public static final String IMPORT_FAILED_SENS_CAT = "Importazione delle categorie di sensori fallita completamente";
    public static final String INSERIMENTO_CATEGORIA_ATTUATORE = "Inserisci un nome per la nuova categoria di attuatore";
    public static final String INSERIMENTO_TESTO_LIBERO_ATT = "Inserisci un testo libero di descrizione dell'attuatore";
    public static final String CATEGORIE_ESISTENTI_ATTUATORI = "Elenco delle categorie di sensori esistenti: ";
    public static final String ERRORE_INSERIMENTO_MODALITA = "Inserimento della modalità fallito. " + GUIDA_IN_LINEA;
    public static final String INSERIMENTO_NOME_MODALITA_OPERATIVA = "Inserisci un nome per la nuova modalita' operativa (inserire " + TERMINATORE + " per terminare)";
    public static final String INSERIMENTO_SUCCESSO = "Inserimento della categoria avvenuto con successo";
    public static final String INSERIMENTO_SUCCESSO_MODALITA = "Inserimento della modalità avvenuta con successo";
    public static final String ERRORE_MODALITA_DEFAULT_MANCANTE = "Inserire almeno la modalita' di default!";
    public static final String IS_PARAMATRICA = "La modalità operativa %s è parametrica? ";
    public static final String INSERIMENTO_NOME_PARAMETRO = "Inserisci un nome per il parametro della modalità operativa parametrica (inserire " + TERMINATORE + " per terminare)";
    public static final String SUCCESSO_INSERIMENTO_PARAMETRO = "Parametro inserito con successo";
    public static final String ERRORE_ALMENO_UN_PARAMETRO = "Almeno un parametro deve essere inserito nella modalita' parametrica";
    public static final String ERRORE_INSERIMENTO_PARAMETRO = "L'inserimento del parametro è fallito. " + GUIDA_IN_LINEA;
    public static final String INSERIMENTO_VALORE_PARAMETRO = "Inserisci il valore desiderato per il parametro %s : ";
    public static final String INTRO_IMPORT_CAT_ATT = "L'importazione delle categorie di attuatori di libreria è stata selezionata. Di seguito apparirà l'esito dell'importazione: ";
    public static final String IMPORT_CAT_ATT_OK = "Importazione delle categorie di attuatori terminato con successo";
    public static final String IMPORT_FAILED_ATT_CAT = "Importazione delle categorie di attuatori fallita completamente";
}

