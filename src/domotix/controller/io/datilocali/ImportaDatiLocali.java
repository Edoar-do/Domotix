package domotix.controller.io.datilocali;

import domotix.model.bean.UnitaImmobiliare;
import domotix.model.bean.device.CategoriaAttuatore;
import domotix.model.bean.device.CategoriaSensore;
import domotix.controller.io.ImportaDatiAdapter;
import domotix.controller.io.LetturaDatiSalvati;

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

    private PercorsiFile generatoreLibreria = null;
    private PercorsiFile generatoreLibreriaImportata = null;
    private LetturaDatiSalvati lettoreLibreria = null;

    public ImportaDatiLocali(PercorsiFile generatoreLibreria, PercorsiFile generatoreLibreriaImportata, LetturaDatiSalvati lettoreLibreria) throws NotDirectoryException {
        this.generatoreLibreria = generatoreLibreria;
        this.generatoreLibreriaImportata = generatoreLibreriaImportata;
        this.lettoreLibreria = lettoreLibreria;
    }

    @Override
    public List<String> getNomiCategorieSensori() {
        List<String> retVal = null;
        retVal = generatoreLibreria.getNomiCategorieSensori();
        return retVal;
    }

    @Override
    public List<String> getNomiCategorieAttuatori() {
        List<String> retVal = null;
        retVal = generatoreLibreria.getNomiCategorieAttuatori();
        return retVal;
    }

    @Override
    public List<String> getNomiUnitaImmobiliare() {
        List<String> retVal = null;
        retVal = generatoreLibreria.getNomiUnitaImmobiliare();
        return retVal;
    }

    @Override
    public CategoriaSensore leggiCategoriaSensore(String nome) throws Exception {
        CategoriaSensore retVal = null;
        retVal = lettoreLibreria.leggiCategoriaSensore(nome);
        return retVal;
    }

    @Override
    public CategoriaAttuatore leggiCategoriaAttuatore(String nome) throws Exception {
        CategoriaAttuatore retVal = null;
        retVal = lettoreLibreria.leggiCategoriaAttuatore(nome);
        return retVal;
    }

    @Override
    public UnitaImmobiliare leggiUnitaImmobiliare(String nome) throws Exception {
        UnitaImmobiliare retVal = null;
        retVal = lettoreLibreria.leggiUnitaImmobiliare(nome);
        return retVal;
    }

    @Override
    public boolean storicizzaCategoriaSensore(String nome) throws Exception {
        String percorsoAttuale = generatoreLibreria.getCartellaCategoriaSensore(nome);
        String percorsoFinale = generatoreLibreriaImportata.getCartellaCategoriaSensore(getNomeTimestamp(nome)); //recupero il nome della cartella con il timestamp

        //tento la storicizzazione facile: rinominare la cartella per spostarli
        boolean esito = storicizzaFiles(percorsoAttuale, percorsoFinale);

        // if (!esito) { //problema di sicurezza con il S.O. --> salvo nuovamente e cancello il vecchio
        //     CategoriaSensore cat = ElencoCategorieSensori.getInstance().getCategoria(nome); //sto storicizzando, quindi e' stata aggiunta
        //     if (cat == null) throw new IllegalArgumentException("ImportaDati.storicizzaCategoriaSensore(): categoria da storicizzare " + nome + " non corrisponde ad alcuna categoria in elenco.");

        //     PercorsiFile.getInstance().setSorgente(PercorsiFile.SORGENTE_LIBRERIA_IMPORTATA);
        //     ScritturaDatiLocali.getInstance().salva(cat);
        //     percorsoAttuale = PercorsiFile.getInstance().getCartellaCategoriaSensore(nome);
        //     esito = storicizzaFiles(percorsoAttuale, percorsoFinale); //rinomino solamente la cartella

        //     if (esito) //se ancora fallisce il rinomino allora elimino i file appena creati in libreria importata per evitare errori futuri (stesso nome)
        //         PercorsiFile.getInstance().setSorgente(PercorsiFile.SORGENTE_LIBRERIA);
        //     RimozioneDatiLocali.getInstance().rimuoviCategoriaSensore(nome);
        // }

        return esito;
    }

    @Override
    public boolean storicizzaCategoriaAttuatore(String nome) throws Exception {
        String percorsoAttuale = generatoreLibreria.getCartellaCategoriaAttuatore(nome);
        String percorsoFinale = generatoreLibreriaImportata.getCartellaCategoriaAttuatore(getNomeTimestamp(nome)); //recupero il nome della cartella con il timestamp

        //tento la storicizzazione facile: rinominare la cartella per spostarli
        boolean esito = storicizzaFiles(percorsoAttuale, percorsoFinale);

        // if (!esito) { //problema di sicurezza con il S.O. --> salvo nuovamente e cancello il vecchio
        //     CategoriaAttuatore cat = ElencoCategorieAttuatori.getInstance().getCategoria(nome); //sto storicizzando, quindi e' stata aggiunta
        //     if (cat == null) throw new IllegalArgumentException("ImportaDati.storicizzaCategoriaAttuatore(): categoria da storicizzare " + nome + " non corrisponde ad alcuna categoria in elenco.");

        //     PercorsiFile.getInstance().setSorgente(PercorsiFile.SORGENTE_LIBRERIA_IMPORTATA);
        //     ScritturaDatiLocali.getInstance().salva(cat);
        //     percorsoAttuale = PercorsiFile.getInstance().getCartellaCategoriaAttuatore(nome);
        //     esito = storicizzaFiles(percorsoAttuale, percorsoFinale); //rinomino solamente la cartella

        //     if (esito) //se ancora fallisce il rinomino allora elimino i file appena creati in libreria importata per evitare errori futuri (stesso nome)
        //         PercorsiFile.getInstance().setSorgente(PercorsiFile.SORGENTE_LIBRERIA);
        //     RimozioneDatiLocali.getInstance().rimuoviCategoriaAttuatore(nome);
        // }

        return esito;
    }

    @Override
    public boolean storicizzaUnitaImmobiliare(String nome) throws Exception {
        String percorsoAttuale = generatoreLibreria.getCartellaUnitaImmobiliare(nome);
        String percorsoFinale = generatoreLibreriaImportata.getCartellaUnitaImmobiliare(getNomeTimestamp(nome)); //recupero il nome della cartella con il timestamp

        //tento la storicizzazione facile: rinominare la cartella per spostarli
        boolean esito = storicizzaFiles(percorsoAttuale, percorsoFinale);

        // if (!esito) { //problema di sicurezza con il S.O. --> salvo nuovamente e cancello il vecchio
        //     UnitaImmobiliare unita = ElencoUnitaImmobiliari.getInstance().getUnita(nome); //sto storicizzando, quindi e' stata aggiunta
        //     if (unita == null) throw new IllegalArgumentException("ImportaDati.storicizzaUnitaImmobiliare(): unita da storicizzare " + nome + " non corrisponde ad alcuna unita in elenco.");

        //     PercorsiFile.getInstance().setSorgente(PercorsiFile.SORGENTE_LIBRERIA_IMPORTATA);
        //     ScritturaDatiLocali.getInstance().salva(unita);
        //     percorsoAttuale = PercorsiFile.getInstance().getCartellaUnitaImmobiliare(nome);
        //     esito = storicizzaFiles(percorsoAttuale, percorsoFinale); //rinomino solamente la cartella

        //     if (esito) //se ancora fallisce il rinomino allora elimino i file appena creati in libreria importata per evitare errori futuri (stesso nome)
        //         PercorsiFile.getInstance().setSorgente(PercorsiFile.SORGENTE_LIBRERIA);
        //     RimozioneDatiLocali.getInstance().rimuoviUnitaImmobiliare(nome);
        // }

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
