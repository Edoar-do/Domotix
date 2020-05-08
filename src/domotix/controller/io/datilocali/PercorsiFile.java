package domotix.controller.io.datilocali;

import java.io.File;
import java.nio.file.NotDirectoryException;
import java.util.ArrayList;
import java.util.List;

import domotix.controller.io.Costanti;

/**
 * Classe che implementa il pattern Singleton per la gestione dei percorsi dei file locali in cui sono salvati i dati del programma.
 * Si intende in questo modo centralizzare la generazione dei percorsi e le codifiche delle chiavi identificative.
 * Aggiungere qui la gestione di percorsi per eventuali nuove entita' salvate.
 * Inoltre, i percorsi generati possono avere piu' sorgenti destinatarie tramite l'impostazione relativa. Questo per poter accedere
 * ai file presenti anche in libreria e libreria gia' importata.
 * La sorgente di default e' SORGENTE_DATI, ovvero i dati effettivi di programma. Per coerenza con l'utilizzo esterno di lettura,
 * questa deve essere la sorgente sempre impostata al termine delle procedure sulle altre sorgenti.
 *
 * @author paolopasqua
 * @see LetturaDatiLocali
 */
public class PercorsiFile {

    public enum SORGENTE {
        DATI,
        LIBRERIA,
        LIBRERIA_IMPORTATA
    }

    //sorgente verso cui generare i percorsi
    private SORGENTE sorgente;

    public PercorsiFile(SORGENTE sorgente) {
        this.sorgente = sorgente;
    }

    /**
     * Metodo unico che data una cartella ritorna una lista contenente il nome di tutti i file contenuti
     * @param pathCartella  percorso della cartella di cui eseguire la lettura
     * @return  Lista dei nomi dei file contenuti nella cartella
     */
    private List<String> getNomiCartella(String pathCartella) {
        ArrayList<String> ret = new ArrayList<>();
        File cartella = new File(pathCartella);

        for(File f : cartella.listFiles()) {
            ret.add(f.getName());
        }

        return ret;
    }

    /**
     * Metodo che controlla la cartella indicata dal percorso in senso di esistenza, altrimenti viene creata.
     * Nel caso esista un file con lo stesso percorso viene lanciata un'eccezione.
     *
     * @param percorso  stringa con percorso da controllare
     * @throws NotDirectoryException    percorso esistente come file
     */
    private void controllaCartella(String percorso) throws NotDirectoryException {
        //Controlla l'esistenza di una cartella della struttura dati indicata dal percorso
        //Se non esiste --> la creo
        //Se esiste come file --> eccezione NotDirectoryException

        File cartella = new File(percorso);
        if (!cartella.exists()) {
            cartella.mkdirs();
        }
        if (!cartella.isDirectory()) {
            throw new NotDirectoryException(this.getClass().getName() + ": " + percorso + " esiste come file.");
        }
    }

    /**
     * Controlla che il percorso indicato sia una cartella
     * @param percorso  stringa con percorso da controllare
     * @throws NotDirectoryException    percorso esiste non come cartella
     */
    private void controllaCartellaEntita(String percorso) throws NotDirectoryException {
        //Controlla se esiste la cartella relativa ad una entita' (categoriaSensore, unitaImmob, ...) indicata dal percorso
        //Se non esiste --> eccezione
        //Se esiste come file --> eccezione

        File cartella = new File(percorso);
        /* tecnicamente impossibile --> ricavo i nomi presenti dalla lettura dei file dentro ad una cartella...
        if (!cartella.exists()) {
            throw new FileNotFoundException(this.getClass().getName() + ": " + percorso + " rilevato ma non esistente.");
        }
        */
        if (!cartella.isDirectory()) {
            throw new NotDirectoryException(this.getClass().getName() + ": " + percorso + " esiste come file.");
        }
    }

    /**
     * Controlla la struttura dei file utili al funzionamento del programma.
     * Se struttura non esistente, la crea.
     * Se qualcosa esistente non dovesse essere corretto, viene segnalato tramite eccezione
     * @throws NotDirectoryException    qualche percorso interno esiste non come cartella
     */
    public void controllaStruttura() throws NotDirectoryException {
        /* CONTROLLO CATEGORIE SENSORI */
        controllaCartella(getCartellaCategorieSensore());
        //controllo l'esistenza di una cartella per categoria (in modo cioe' che non vi sia una categoria come file senza la propria cartella)
        for (String s : getNomiCategorieSensori()) {
            controllaCartellaEntita(getCartellaCategoriaSensore(s));
            controllaCartella(getCartellaInformazioneRilevabile(s)); //controllo esistenza di una cartella informazioni rilevabile all'interno di ciascuna cartella
            //le informazioni rilevabili sono gestite come singoli file contenuti nella cartella sopra
        }

        /* CONTROLLO CATEGORIE ATTUATORI */
        controllaCartella(getCartellaCategorieAttuatore());
        //controllo l'esistenza di una cartella per categoria (in modo cioe' che non vi sia una categoria come file senza la propria cartella)
        for (String s : getNomiCategorieAttuatori()) {
            controllaCartellaEntita(getCartellaCategoriaAttuatore(s));
            controllaCartella(getCartellaModalita(s)); //controllo esistenza di una cartella modalita all'interno di ciascuna cartella
            //modalita sono gestite come singoli file contenuti nella cartella sopra
        }

        /* CONTROLLO UNITA IMMOBILIARI */
        controllaCartella(getCartellaUnitaImmobiliari());
        //controllo l'esistenza di una cartella per unita (in modo cioe' che non vi sia una unita come file senza la propria cartella)
        for (String s : getNomiUnitaImmobiliare()) {
            controllaCartellaEntita(getCartellaUnitaImmobiliare(s));
            controllaCartella(getCartellaStanze(s)); //controllo esistenza di una cartella stanze all'interno di ciascuna cartella
            //stanze sono gestite come singoli file contenuti nella cartella sopra
            controllaCartella(getCartellaArtefatti(s)); //controllo esistenza di una cartella artefatti all'interno di ciascuna cartella
            //artefatti sono gestite come singoli file contenuti nella cartella sopra
            controllaCartella(getCartellaRegole(s));
            //regole sono gestite come singoli file contenuti nella cartella sopra
        }

        /* CONTROLLO SENSORI */
        controllaCartella(getCartellaSensori()); //sensori gestiti come singoli file contenuti nella cartella
        /* CONTROLLO ATTUATORI */
        controllaCartella(getCartellaAttuatori()); //attuatori gestiti come singoli file contenuti nella cartella
        /* CONTROLLO AZIONI PROGRAMMATE */
        if (sorgente == SORGENTE.DATI) //in libreria non viene gestita l'entita' azione programmabile
            controllaCartella(getCartellaAzioniProgrammabili()); //azioni programamte gestite come singoli file contenuti nella cartella
    }

    /**
     * Ritorna l'attuale sorgente impostata
     * @return  intero che identifica attraverso le costanti la sorgente
     */
    public SORGENTE getSorgente() {
        return sorgente;
    }

    /**
     * Imposta una nuova sorgente presso cui generare i percorsi. Questo e' valido solamente se il numero e'
     * identificato da un'apposita costante.
     * @param sorgente intero che identifica la sorgente attraverso le apposite costanti
     */
    public void setSorgente(SORGENTE sorgente) {
        this.sorgente = sorgente;
    }

    /**
     * Ritorna la stringa contentente il percorso alla cartella della sorgente file impostata
     * @return  stringa contenente il percorso alla sorgente
     */
    public String getPercorsoSorgente() {
        switch(this.sorgente) {
            case DATI:
                return Costanti.PERCORSO_CARTELLA_DATI;
            case LIBRERIA:
                return Costanti.PERCORSO_CARTELLA_LIBRERIA;
            case LIBRERIA_IMPORTATA:
                return Costanti.PERCORSO_CARTELLA_LIBRERIA_IMPORTATA;
        }
        return "";
    }

    /**
     * Genera il percorso del file specifico per un'entita' CategoriaSensore identificata dalla stringa passata.
     * @param cat   identificativo stringa dell'entita'
     * @return  Percorso al file dove risiedono i dati locali relativi all'entita'
     * @see domotix.model.bean.device.CategoriaSensore
     */
    public String getPercorsoCategoriaSensore(String cat) {
        return getCartellaCategoriaSensore(cat) + File.separator + cat;
    }

    /**
     * Genera il percorso della cartella contenente i dati relativi all'entita' CategoriaSensore identificata dalla stringa passata
     * @param cat identificativo stringa dell'entita'
     * @return  Percorso della cartella dove risiedono i dati locali relativi all'entita'
     * @see domotix.model.bean.device.CategoriaSensore
     */
    public String getCartellaCategoriaSensore(String cat) {
        return getCartellaCategorieSensore() + File.separator + cat;
    }

    /**
     * Genera il percorso della cartella contenente tutte le CategoriaSensore presenti
     * @return  Percorso alla cartella dove risiedono i dati locali relativi all'entita'
     * @see domotix.model.bean.device.CategoriaSensore
     */
    public String getCartellaCategorieSensore() {
        return getPercorsoSorgente() + File.separator + Costanti.NOME_CARTELLA_CATEGORIE_SENSORI;
    }

    /**
     * Ritorna una lista di nomi delle entita' presenti nei file memorizzati
     *
     * @return  Lista di nomi delle entita' presenti
     * @see domotix.model.bean.device.CategoriaSensore
     */
    public List<String> getNomiCategorieSensori() {
        return getNomiCartella(getCartellaCategorieSensore());
    }



    /**
     * Genera il percorso del file specifico per un'entita' InfoRilevabile identificata dalle stringhe passata.
     * @param infoRilevabile  identificativo stringa dell'informazione rilevabile
     * @param cat   identificativo stringa della categoria inerente all'informazione rilevabile
     * @return  Percorso al file dove risiedono i dati locali relativi all'entita'
     * @see domotix.model.bean.device.Modalita
     */
    public String getPercorsoInformazioneRilevabile(String infoRilevabile, String cat) {
        return getCartellaInformazioneRilevabile(cat) + File.separator + infoRilevabile;
    }

    /**
     * Genera il percorso della cartella contenente i dati relativi all'entita' InfoRilevabile identificata dalla stringa passata
     * @param cat   identificativo stringa della categoria inerente alla modalita
     * @return  Percorso della cartella dove risiedono i dati locali relativi all'entita'
     * @see domotix.model.bean.device.Modalita
     */
    public String getCartellaInformazioneRilevabile(String cat) {
        return getCartellaCategoriaSensore(cat) + File.separator + Costanti.NOME_CARTELLA_INFO_RILEVABILE;
    }

    /**
     * Ritorna una lista di nomi delle entita' presenti nei file memorizzati
     *
     * @return  Lista di nomi delle entita' presenti
     * @see domotix.model.bean.device.Modalita
     */
    public List<String> getNomiInformazioniRilevabili(String categoriaSensore) {
        return getNomiCartella(getCartellaInformazioneRilevabile(categoriaSensore));
    }



    /**
     * Genera il percorso del file specifico per un'entita' CategoriaAttuatore identificata dalla stringa passata.
     * @param cat   identificativo stringa dell'entita'
     * @return  Percorso al file dove risiedono i dati locali relativi all'entita'
     * @see domotix.model.bean.device.CategoriaAttuatore
     */
    public String getPercorsoCategoriaAttuatore(String cat) {
        return getCartellaCategoriaAttuatore(cat) + File.separator + cat;
    }

    /**
     * Genera il percorso della cartella contenente i dati relativi all'entita' CategoriaAttuatore identificata dalla stringa passata
     * @param cat identificativo stringa dell'entita'
     * @return  Percorso della cartella dove risiedono i dati locali relativi all'entita'
     * @see domotix.model.bean.device.CategoriaAttuatore
     */
    public String getCartellaCategoriaAttuatore(String cat) {
        return getCartellaCategorieAttuatore() + File.separator + cat;
    }

    /**
     * Genera il percorso della cartella contenente tutte le CategoriaAttuatore presenti
     * @return  Percorso alla cartella dove risiedono i dati locali relativi all'entita'
     * @see domotix.model.bean.device.CategoriaAttuatore
     */
    public String getCartellaCategorieAttuatore() {
        return getPercorsoSorgente() + File.separator + Costanti.NOME_CARTELLA_CATEGORIE_ATTUATORI;
    }

    /**
     * Ritorna una lista di nomi delle entita' presenti nei file memorizzati
     *
     * @return  Lista di nomi delle entita' presenti
     * @see domotix.model.bean.device.CategoriaAttuatore
     */
    public List<String> getNomiCategorieAttuatori() {
        return getNomiCartella(getCartellaCategorieAttuatore());
    }



    /**
     * Genera il percorso del file specifico per un'entita' Modalita identificata dalle stringhe passata.
     * @param modalita  identificativo stringa della modalita'
     * @param cat   identificativo stringa della categoria inerente alla modalita
     * @return  Percorso al file dove risiedono i dati locali relativi all'entita'
     * @see domotix.model.bean.device.Modalita
     */
    public String getPercorsoModalita(String modalita, String cat) {
        return getCartellaModalita(cat) + File.separator + modalita;
    }

    /**
     * Genera il percorso della cartella contenente i dati relativi all'entita' Modalita identificata dalla stringa passata
     * @param cat   identificativo stringa della categoria inerente alla modalita
     * @return  Percorso della cartella dove risiedono i dati locali relativi all'entita'
     * @see domotix.model.bean.device.Modalita
     */
    public String getCartellaModalita(String cat) {
        return getCartellaCategoriaAttuatore(cat) + File.separator + Costanti.NOME_CARTELLA_MODALITA;
    }

    /**
     * Ritorna una lista di nomi delle entita' presenti nei file memorizzati
     *
     * @return  Lista di nomi delle entita' presenti
     * @see domotix.model.bean.device.Modalita
     */
    public List<String> getNomiModalita(String categoriaAttuatore) {
        return getNomiCartella(getCartellaModalita(categoriaAttuatore));
    }



    /**
     * Genera il percorso del file specifico per un'entita' UnitaImmobiliare identificata dalla stringa passata.
     * @param unita   identificativo stringa dell'entita'
     * @return  Percorso al file dove risiedono i dati locali relativi all'entita'
     * @see domotix.model.bean.UnitaImmobiliare
     */
    public String getPercorsoUnitaImmobiliare(String unita) {
        return getCartellaUnitaImmobiliare(unita) + File.separator + unita;
    }

    /**
     * Genera il percorso della cartella contenente i dati relativi all'entita' UnitaImmobiliare identificata dalla stringa passata
     * @param unita   identificativo stringa della categoria inerente alla modalita
     * @return  Percorso della cartella dove risiedono i dati locali relativi all'entita'
     * @see domotix.model.bean.UnitaImmobiliare
     */
    public String getCartellaUnitaImmobiliare(String unita) {
        return getCartellaUnitaImmobiliari() + File.separator + unita;
    }

    /**
     * Genera il percorso della cartella contenente tutte le UnitaImmobiliare presenti
     * @return  Percorso alla cartella dove risiedono i dati locali relativi all'entita'
     * @see domotix.model.bean.UnitaImmobiliare
     */
    public String getCartellaUnitaImmobiliari() {
        return getPercorsoSorgente() + File.separator + Costanti.NOME_CARTELLA_UNITA_IMMOB;
    }

    /**
     * Ritorna una lista di nomi delle entita' presenti nei file memorizzati
     *
     * @return  Lista di nomi delle entita' presenti
     * @see domotix.model.bean.UnitaImmobiliare
     */
    public List<String> getNomiUnitaImmobiliare() {
        return getNomiCartella(getCartellaUnitaImmobiliari());
    }



    /**
     * Genera il percorso del file specifico per un'entita' Stanza identificata dalle stringhe passate.
     * @param stanza   identificativo stringa della stanza
     * @param unita     identificativo stringa dell'unita' immobiliare contenente la stanza
     * @return  Percorso al file dove risiedono i dati locali relativi all'entita'
     * @see domotix.model.bean.system.Stanza
     */
    public String getPercorsoStanza(String stanza, String unita) {
        //return Costanti.PERCORSO_CARTELLA_STANZE + File.separator + componiNomeStanza(stanza, unita);
        return getCartellaStanze(unita) + File.separator + stanza;
    }

    /**
     * Genera il percorso della cartella contenente le entita' Stanza appartenente alla unita immobiliare indicata
     * @param unita     identificativo stringa dell'unita' immobiliare contenente la stanza
     * @return  Percorso della cartella dove risiedono i dati locali relativi all'entita'
     * @see domotix.model.bean.system.Stanza
     */
    public String getCartellaStanze(String unita) {
        return getCartellaUnitaImmobiliare(unita) + File.separator + Costanti.NOME_CARTELLA_STANZE;
    }

    /**
     * Ritorna una lista di nomi delle entita' presenti nei file memorizzati
     *
     * @return  Lista di nomi delle entita' presenti
     * @see domotix.model.bean.system.Stanza
     */
    public List<String> getNomiStanze(String unitaImmobiliare) {
         return getNomiCartella(getCartellaStanze(unitaImmobiliare));
    }



    /**
     * Genera il percorso del file specifico per un'entita' Artefatto identificata dalle stringhe passate.
     * @param artefatto   identificativo stringa della stanza
     * @param unita     identificativo stringa dell'unita' immobiliare contenente l'artefatto'
     * @return  Percorso al file dove risiedono i dati locali relativi all'entita'
     * @see domotix.model.bean.system.Artefatto
     */
    public String getPercorsoArtefatto(String artefatto, String unita) {
        return getCartellaArtefatti(unita) + File.separator + artefatto;
    }

    /**
     * Genera il percorso della cartella contenente le entita' Artefatto identificata dalla stringa passata
     * @param unita     identificativo stringa dell'unita' immobiliare contenente l'artefatto'
     * @return  Percorso della cartella dove risiedono i dati locali relativi all'entita'
     * @see domotix.model.bean.system.Artefatto
     */
    public String getCartellaArtefatti(String unita) {
        return getCartellaUnitaImmobiliare(unita) + File.separator + Costanti.NOME_CARTELLA_ARTEFATTI;
    }

    /**
     * Ritorna una lista di nomi delle entita' presenti nei file memorizzati
     *
     * @return  Lista di nomi delle entita' presenti
     * @see domotix.model.bean.system.Artefatto
     */
    public List<String> getNomiArtefatti(String unitaImmobiliare) {
        return getNomiCartella(getCartellaArtefatti(unitaImmobiliare));
    }



    /**
     * Genera il percorso del file specifico per un'entita' Sensore identificata dalla stringa passata.
     * @param sensore   identificativo stringa del sensore
     * @return  Percorso al file dove risiedono i dati locali relativi all'entita'
     * @see domotix.model.bean.device.Sensore
     */
    public String getPercorsoSensore(String sensore) {
        return getCartellaSensori() + File.separator + sensore;
    }

    /**
     * Genera il percorso della cartella contenente le entita' Sensore
     * @return  Percorso della cartella dove risiedono i dati locali relativi all'entita'
     * @see domotix.model.bean.device.Sensore
     */
    public String getCartellaSensori() {
        return getPercorsoSorgente() + File.separator + Costanti.NOME_CARTELLA_SENSORI;
    }

    /**
     * Ritorna una lista di nomi delle entita' presenti nei file memorizzati
     *
     * @return  Lista di nomi delle entita' presenti
     * @see domotix.model.bean.device.Sensore
     */
    public List<String> getNomiSensori() {
        return getNomiCartella(getCartellaSensori());
    }



    /**
     * Genera il percorso del file specifico per un'entita' Attuatore identificata dalla stringa passata.
     * @param attuatore   identificativo stringa dell'attuatore
     * @return  Percorso al file dove risiedono i dati locali relativi all'entita'
     * @see domotix.model.bean.device.Attuatore
     */
    public String getAttuatore(String attuatore) {
        return getCartellaAttuatori() + File.separator + attuatore;
    }

    /**
     * Genera il percorso della cartella contenente le entita' Attuatore
     * @return  Percorso della cartella dove risiedono i dati locali relativi all'entita'
     * @see domotix.model.bean.device.Attuatore
     */
    public String getCartellaAttuatori() {
        return getPercorsoSorgente() + File.separator + Costanti.NOME_CARTELLA_ATTUATORI;
    }

    /**
     * Ritorna una lista di nomi delle entita' presenti nei file memorizzati
     *
     * @return  Lista di nomi delle entita' presenti
     * @see domotix.model.bean.device.Attuatore
     */
    public List<String> getNomiAttuatori() {
        return getNomiCartella(getCartellaAttuatori());
    }



    /**
     * Genera il percorso del file specifico per un'entita' Regola identificata dalle stringhe passate.
     * @param idRegola   identificativo stringa della regola
     * @return  Percorso al file dove risiedono i dati locali relativi all'entita'
     * @see domotix.model.bean.regole.Regola
     */
    public String getPercorsoRegola(String idRegola, String unita) {
        return getCartellaRegole(unita) + File.separator + idRegola;
    }

    /**
     * Genera il percorso della cartella contenente le entita' Regole
     * @return  Percorso della cartella dove risiedono i dati locali relativi all'entita'
     * @see domotix.model.bean.regole.Regola
     */
    public String getCartellaRegole(String unita) {
        return getCartellaUnitaImmobiliare(unita) + File.separator + Costanti.NOME_CARTELLA_REGOLE;
    }

    /**
     * Ritorna una lista di nomi delle entita' presenti nei file memorizzati
     *
     * @return  Lista di nomi delle entita' presenti
     * @see domotix.model.bean.regole.Regola
     */
    public List<String> getNomiRegola(String unita) {
        return getNomiCartella(getCartellaRegole(unita));
    }



    /**
     * Genera il percorso del file specifico per un'entita' Azione programmata identificata dalle stringhe passate.
     * @param id   identificativo stringa della regola
     * @return  Percorso al file dove risiedono i dati locali relativi all'entita'
     * @see domotix.model.ElencoAzioniProgrammate
     * @see domotix.model.bean.regole.Azione
     */
    public String getPercorsoAzioneProgrammabile(String id) {
        return getCartellaAzioniProgrammabili() + File.separator + id;
    }

    /**
     * Genera il percorso della cartella contenente le entita' Azioni programmate
     * @return  Percorso della cartella dove risiedono i dati locali relativi all'entita'
     * @see domotix.model.ElencoAzioniProgrammate
     * @see domotix.model.bean.regole.Azione
     */
    public String getCartellaAzioniProgrammabili() {
        return getPercorsoSorgente() + File.separator + Costanti.NOME_CARTELLA_AZIONI_PROGRAMMATE;
    }

    /**
     * Ritorna una lista di nomi delle entita' presenti nei file memorizzati
     *
     * @return  Lista di nomi delle entita' presenti
     * @see domotix.model.ElencoAzioniProgrammate
     * @see domotix.model.bean.regole.Azione
     */
    public List<String> getNomiAzioniProgramamte() {
        return getNomiCartella(getCartellaAzioniProgrammabili());
    }

}
