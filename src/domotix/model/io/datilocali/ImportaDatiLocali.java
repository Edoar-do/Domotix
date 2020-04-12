package domotix.model.io.datilocali;

import domotix.model.bean.UnitaImmobiliare;
import domotix.model.bean.device.CategoriaAttuatore;
import domotix.model.bean.device.CategoriaSensore;
import domotix.model.io.ImportaDatiAdapter;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import java.nio.file.NotDirectoryException;
import java.util.List;

/**
 * Classe che implementa l'interfaccia ImportaDati per definire un meccanismo di caricamento dei dati da file di libreria
 * presenti sulla macchina di esecuzione del programma.
 * Si sviluppa una struttura ad albero nel FileSystem del SistemaOperativo posizionata nella cartella dell'applicazione (attualmente equivale ad
 * una cartella contenuta nella cartella utente).
 *
 * @author paolopasqua
 * @see domotix.model.io.ImportaDati
 */
public class ImportaDatiLocali extends ImportaDatiAdapter {

    private static ImportaDatiLocali _instance = null;

    public static ImportaDatiLocali getInstance() throws NotDirectoryException, ParserConfigurationException, TransformerConfigurationException {
        if (_instance == null)
            _instance = new ImportaDatiLocali();
        return _instance;
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
        return super.storicizzaCategoriaSensore(nome);
    }

    @Override
    public boolean storicizzaCategoriaAttuatore(String nome) throws Exception {
        return super.storicizzaCategoriaAttuatore(nome);
    }

    @Override
    public boolean storicizzaUnitaImmobiliare(String nome) throws Exception {
        return super.storicizzaUnitaImmobiliare(nome);
    }
}
