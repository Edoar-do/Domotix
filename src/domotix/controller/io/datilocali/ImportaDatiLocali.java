package domotix.controller.io.datilocali;

import domotix.model.ElencoCategorieAttuatori;
import domotix.model.ElencoCategorieSensori;
import domotix.model.ElencoUnitaImmobiliari;
import domotix.model.bean.UnitaImmobiliare;
import domotix.model.bean.device.CategoriaAttuatore;
import domotix.model.bean.device.CategoriaSensore;
import domotix.controller.io.ImportaDatiAdapter;

import java.io.File;
import java.nio.file.NotDirectoryException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Classe che implementa l'interfaccia ImportaDati per definire un meccanismo di caricamento dei dati da file di libreria
 * presenti sulla macchina di esecuzione del programma.
 * Si sviluppa una struttura ad albero nel FileSystem del SistemaOperativo posizionata nella cartella dell'applicazione (attualmente equivale ad
 * una cartella contenuta nella cartella utente).
 *
 * @author paolopasqua
 * @see domotix.controller.io.ImportaDati
 */
public class ImportaDatiLocali extends ImportaDatiAdapter {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    private static ImportaDatiLocali _instance = null;

    public static ImportaDatiLocali getInstance() throws NotDirectoryException {
        if (_instance == null)
            _instance = new ImportaDatiLocali();
        return _instance;
    }

    private ImportaDatiLocali() throws NotDirectoryException {
        PercorsiFile.getInstance().controllaStruttura();
    }

    @Override
    public List<String> getNomiCategorieSensori() {
        List<String> retVal = null;
        PercorsiFile.getInstance().setSorgente(PercorsiFile.SORGENTE_LIBRERIA);
        retVal = PercorsiFile.getInstance().getNomiCategorieSensori();
        PercorsiFile.getInstance().setSorgente(PercorsiFile.SORGENTE_DATI);
        return retVal;
    }

    @Override
    public List<String> getNomiCategorieAttuatori() {
        List<String> retVal = null;
        PercorsiFile.getInstance().setSorgente(PercorsiFile.SORGENTE_LIBRERIA);
        retVal = PercorsiFile.getInstance().getNomiCategorieAttuatori();
        PercorsiFile.getInstance().setSorgente(PercorsiFile.SORGENTE_DATI);
        return retVal;
    }

    @Override
    public List<String> getNomiUnitaImmobiliare() {
        List<String> retVal = null;
        PercorsiFile.getInstance().setSorgente(PercorsiFile.SORGENTE_LIBRERIA);
        retVal = PercorsiFile.getInstance().getNomiUnitaImmobiliare();
        PercorsiFile.getInstance().setSorgente(PercorsiFile.SORGENTE_DATI);
        return retVal;
    }

    @Override
    public CategoriaSensore leggiCategoriaSensore(String nome) throws Exception {
        CategoriaSensore retVal = null;
        PercorsiFile.getInstance().setSorgente(PercorsiFile.SORGENTE_LIBRERIA);
        retVal = LetturaDatiLocali.getInstance().leggiCategoriaSensore(nome);
        PercorsiFile.getInstance().setSorgente(PercorsiFile.SORGENTE_DATI);
        return retVal;
    }

    @Override
    public CategoriaAttuatore leggiCategoriaAttuatore(String nome) throws Exception {
        CategoriaAttuatore retVal = null;
        PercorsiFile.getInstance().setSorgente(PercorsiFile.SORGENTE_LIBRERIA);
        retVal = LetturaDatiLocali.getInstance().leggiCategoriaAttuatore(nome);
        PercorsiFile.getInstance().setSorgente(PercorsiFile.SORGENTE_DATI);
        return retVal;
    }

    @Override
    public UnitaImmobiliare leggiUnitaImmobiliare(String nome) throws Exception {
        UnitaImmobiliare retVal = null;
        PercorsiFile.getInstance().setSorgente(PercorsiFile.SORGENTE_LIBRERIA);
        retVal = LetturaDatiLocali.getInstance().leggiUnitaImmobiliare(nome);
        PercorsiFile.getInstance().setSorgente(PercorsiFile.SORGENTE_DATI);
        return retVal;
    }

    @Override
    public boolean storicizzaCategoriaSensore(String nome) throws Exception {
        PercorsiFile.getInstance().setSorgente(PercorsiFile.SORGENTE_LIBRERIA);
        String percorsoAttuale = PercorsiFile.getInstance().getCartellaCategoriaSensore(nome);
        PercorsiFile.getInstance().setSorgente(PercorsiFile.SORGENTE_LIBRERIA_IMPORTATA);
        String percorsoFinale = PercorsiFile.getInstance().getCartellaCategoriaSensore(getNomeTimestamp(nome)); //recupero il nome della cartella con il timestamp

        //tento la storicizzazione facile: rinominare la cartella per spostarli
        boolean esito = storicizzaFiles(percorsoAttuale, percorsoFinale);

        if (!esito) { //problema di sicurezza con il S.O. --> salvo nuovamente e cancello il vecchio
            CategoriaSensore cat = ElencoCategorieSensori.getInstance().getCategoria(nome); //sto storicizzando, quindi e' stata aggiunta
            if (cat == null) throw new IllegalArgumentException("ImportaDati.storicizzaCategoriaSensore(): categoria da storicizzare " + nome + " non corrisponde ad alcuna categoria in elenco.");

            PercorsiFile.getInstance().setSorgente(PercorsiFile.SORGENTE_LIBRERIA_IMPORTATA);
            ScritturaDatiLocali.getInstance().salva(cat);
            percorsoAttuale = PercorsiFile.getInstance().getCartellaCategoriaSensore(nome);
            esito = storicizzaFiles(percorsoAttuale, percorsoFinale); //rinomino solamente la cartella

            if (esito) //se ancora fallisce il rinomino allora elimino i file appena creati in libreria importata per evitare errori futuri (stesso nome)
                PercorsiFile.getInstance().setSorgente(PercorsiFile.SORGENTE_LIBRERIA);
            RimozioneDatiLocali.getInstance().rimuoviCategoriaSensore(nome);
        }

        PercorsiFile.getInstance().setSorgente(PercorsiFile.SORGENTE_DATI);
        return esito;
    }

    @Override
    public boolean storicizzaCategoriaAttuatore(String nome) throws Exception {
        PercorsiFile.getInstance().setSorgente(PercorsiFile.SORGENTE_LIBRERIA);
        String percorsoAttuale = PercorsiFile.getInstance().getCartellaCategoriaAttuatore(nome);
        PercorsiFile.getInstance().setSorgente(PercorsiFile.SORGENTE_LIBRERIA_IMPORTATA);
        String percorsoFinale = PercorsiFile.getInstance().getCartellaCategoriaAttuatore(getNomeTimestamp(nome)); //recupero il nome della cartella con il timestamp

        //tento la storicizzazione facile: rinominare la cartella per spostarli
        boolean esito = storicizzaFiles(percorsoAttuale, percorsoFinale);
        if (!esito) { //problema di sicurezza con il S.O. --> salvo nuovamente e cancello il vecchio
            CategoriaAttuatore cat = ElencoCategorieAttuatori.getInstance().getCategoria(nome); //sto storicizzando, quindi e' stata aggiunta
            if (cat == null) throw new IllegalArgumentException("ImportaDati.storicizzaCategoriaAttuatore(): categoria da storicizzare " + nome + " non corrisponde ad alcuna categoria in elenco.");

            PercorsiFile.getInstance().setSorgente(PercorsiFile.SORGENTE_LIBRERIA_IMPORTATA);
            ScritturaDatiLocali.getInstance().salva(cat);
            percorsoAttuale = PercorsiFile.getInstance().getCartellaCategoriaAttuatore(nome);
            esito = storicizzaFiles(percorsoAttuale, percorsoFinale); //rinomino solamente la cartella

            if (esito) //se ancora fallisce il rinomino allora elimino i file appena creati in libreria importata per evitare errori futuri (stesso nome)
                PercorsiFile.getInstance().setSorgente(PercorsiFile.SORGENTE_LIBRERIA);
            RimozioneDatiLocali.getInstance().rimuoviCategoriaAttuatore(nome);
        }

        PercorsiFile.getInstance().setSorgente(PercorsiFile.SORGENTE_DATI);
        return esito;
    }

    @Override
    public boolean storicizzaUnitaImmobiliare(String nome) throws Exception {
        PercorsiFile.getInstance().setSorgente(PercorsiFile.SORGENTE_LIBRERIA);
        String percorsoAttuale = PercorsiFile.getInstance().getCartellaUnitaImmobiliare(nome);
        PercorsiFile.getInstance().setSorgente(PercorsiFile.SORGENTE_LIBRERIA_IMPORTATA);
        String percorsoFinale = PercorsiFile.getInstance().getCartellaUnitaImmobiliare(getNomeTimestamp(nome)); //recupero il nome della cartella con il timestamp

        //tento la storicizzazione facile: rinominare la cartella per spostarli
        boolean esito = storicizzaFiles(percorsoAttuale, percorsoFinale);
        if (!esito) { //problema di sicurezza con il S.O. --> salvo nuovamente e cancello il vecchio
            UnitaImmobiliare unita = ElencoUnitaImmobiliari.getInstance().getUnita(nome); //sto storicizzando, quindi e' stata aggiunta
            if (unita == null) throw new IllegalArgumentException("ImportaDati.storicizzaUnitaImmobiliare(): unita da storicizzare " + nome + " non corrisponde ad alcuna unita in elenco.");

            PercorsiFile.getInstance().setSorgente(PercorsiFile.SORGENTE_LIBRERIA_IMPORTATA);
            ScritturaDatiLocali.getInstance().salva(unita);
            percorsoAttuale = PercorsiFile.getInstance().getCartellaUnitaImmobiliare(nome);
            esito = storicizzaFiles(percorsoAttuale, percorsoFinale); //rinomino solamente la cartella

            if (esito) //se ancora fallisce il rinomino allora elimino i file appena creati in libreria importata per evitare errori futuri (stesso nome)
                PercorsiFile.getInstance().setSorgente(PercorsiFile.SORGENTE_LIBRERIA);
            RimozioneDatiLocali.getInstance().rimuoviUnitaImmobiliare(nome);
        }

        PercorsiFile.getInstance().setSorgente(PercorsiFile.SORGENTE_DATI);
        return esito;
    }

    private String getNomeTimestamp(String nome) {
        return LocalDateTime.now().format(DATE_TIME_FORMATTER) + "_" + nome;
    }

    private boolean storicizzaFiles(String percorsoAttuale, String percorsoFinale) {
        File attuale = new File(percorsoAttuale);
        File finale = new File(percorsoFinale);
        boolean esito = true;

        try {
            esito = attuale.renameTo(finale);
        }
        catch (SecurityException ex) {
            esito = false;
        }

        return esito;
    }
}
