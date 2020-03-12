package domotix.model.io.datilocali;

import domotix.model.util.Costanti;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.NotDirectoryException;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe che implementa il pattern Singleton per la gestione dei percorsi dei file locali in cui sono salvati i dati del programma.
 * Si intende in questo modo centralizzare la generazione dei percorsi e le codifiche delle chiavi identificative.
 * Aggiungere qui la gestione di percorsi per eventuali nuove entita' salvate.
 *
 * @author paolopasqua
 * @see LetturaDatiLocali
 */
public class PercorsiFile {

    private static PercorsiFile instance = null;

    public static PercorsiFile getInstance() {
        if (instance == null)
            instance = new PercorsiFile();
        return instance;
    }

    private PercorsiFile() {
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

    public void controllaStruttura() throws NotDirectoryException {
        controllaCartella(Costanti.PERCORSO_CARTELLA_DATI);

        controllaCartella(Costanti.PERCORSO_CARTELLA_CATEGORIE_SENSORI);
        //controllo l'esistenza di una cartella per categoria (in modo cioe' che non vi sia una categoria come file senza la propria cartella)
        for (String s : getNomiCategorieSensori()) {
            controllaCartellaEntita(getCartellaCategoriaSensore(s));
            controllaCartella(getCartellaInformazioneRilevabile(s)); //controllo esistenza di una cartella informazioni rilevabile all'interno di ciascuna cartella
            //le informazioni rilevabili sono gestite come singoli file contenuti nella cartella sopra
        }

        controllaCartella(Costanti.PERCORSO_CARTELLA_CATEGORIE_ATTUATORI);
        //controllo l'esistenza di una cartella per categoria (in modo cioe' che non vi sia una categoria come file senza la propria cartella)
        for (String s : getNomiCategorieAttuatori()) {
            controllaCartellaEntita(getCartellaCategoriaAttuatore(s));
            controllaCartella(getCartellaModalita(s)); //controllo esistenza di una cartella modalita all'interno di ciascuna cartella
            //modalita sono gestite come singoli file contenuti nella cartella sopra
        }

        controllaCartella(Costanti.PERCORSO_CARTELLA_UNITA_IMMOB);
        //controllo l'esistenza di una cartella per unita (in modo cioe' che non vi sia una unita come file senza la propria cartella)
        for (String s : getNomiUnitaImmobiliare()) {
            controllaCartellaEntita(getCartellaUnitaImmobiliare(s));
            controllaCartella(getCartellaStanze(s)); //controllo esistenza di una cartella stanze all'interno di ciascuna cartella
            //stanze sono gestite come singoli file contenuti nella cartella sopra
            controllaCartella(getCartellaArtefatti(s)); //controllo esistenza di una cartella artefatti all'interno di ciascuna cartella
            //artefatti sono gestite come singoli file contenuti nella cartella sopra
        }

        controllaCartella(Costanti.PERCORSO_CARTELLA_SENSORI); //sensori gestiti come singoli file contenuti nella cartella
        controllaCartella(Costanti.PERCORSO_CARTELLA_ATTUATORI); //attuatori gestiti come singoli file contenuti nella cartella
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
        return Costanti.PERCORSO_CARTELLA_CATEGORIE_SENSORI + File.separator + cat;
    }

    /**
     * Ritorna una lista di nomi delle entita' presenti nei file memorizzati
     *
     * @return  Lista di nomi delle entita' presenti
     * @see domotix.model.bean.device.CategoriaSensore
     */
    public List<String> getNomiCategorieSensori() {
        return getNomiCartella(Costanti.PERCORSO_CARTELLA_CATEGORIE_SENSORI);
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
        return Costanti.PERCORSO_CARTELLA_CATEGORIE_ATTUATORI + File.separator + cat;
    }

    /**
     * Ritorna una lista di nomi delle entita' presenti nei file memorizzati
     *
     * @return  Lista di nomi delle entita' presenti
     * @see domotix.model.bean.device.CategoriaAttuatore
     */
    public List<String> getNomiCategorieAttuatori() {
        return getNomiCartella(Costanti.PERCORSO_CARTELLA_CATEGORIE_ATTUATORI);
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
        return Costanti.PERCORSO_CARTELLA_UNITA_IMMOB + File.separator + unita;
    }

    /**
     * Ritorna una lista di nomi delle entita' presenti nei file memorizzati
     *
     * @return  Lista di nomi delle entita' presenti
     * @see domotix.model.bean.UnitaImmobiliare
     */
    public List<String> getNomiUnitaImmobiliare() {
        return getNomiCartella(Costanti.PERCORSO_CARTELLA_UNITA_IMMOB);
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
        return Costanti.PERCORSO_CARTELLA_SENSORI;
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
        return Costanti.PERCORSO_CARTELLA_ATTUATORI;
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

}
