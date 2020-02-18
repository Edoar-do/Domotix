package domotix.model.io.datilocali;

import domotix.model.bean.UnitaImmobiliare;
import domotix.model.bean.device.*;
import domotix.model.bean.system.Artefatto;
import domotix.model.bean.system.Stanza;
import domotix.model.io.RimozioneDatiSalvatiAdapter;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import java.io.File;
import java.nio.file.NotDirectoryException;

/**
 * Classe che implementa l'interfaccia RimozioneDatiSalvati per definire un meccanismo di rimozione dei dati su file memorizzati
 * localmente sulla macchina di esecuzione del programma.
 * Si sviluppa una struttura ad albero nel FileSystem del SistemaOperativo posizionata nella cartella dell'applicazione (attualmente equivale ad
 * una cartella contenuta nella cartella utente).
 *
 * @author paolopasqua
 * @see domotix.model.io.RimozioneDatiSalvati
 */
public class RimozioneDatiLocali extends RimozioneDatiSalvatiAdapter {

    private static RimozioneDatiLocali _instance = null;

    public static RimozioneDatiLocali getInstance() throws NotDirectoryException, ParserConfigurationException, TransformerConfigurationException {
        if (_instance == null)
            _instance = new RimozioneDatiLocali();
        return _instance;
    }

    private RimozioneDatiLocali() throws NotDirectoryException, ParserConfigurationException, TransformerConfigurationException {
        //test esistenza struttura dati
        PercorsiFile.getInstance().controllaStruttura();
    }

    /**
     * Rimuove ricorsivamente un file o una directory. Elimina quindi nel caso di una directory tutto il contenuto
     * prima di proseguire.
     * @param f File da cui proseguire ricorsivamente
     * @return  true se tutte le rimozioni sono andate a buon fine; false altrimenti
     */
    private boolean rimuoviRicorsivo(File f) {
        boolean ret = true;

        //se directory elimino il contenuto
        if (f.isDirectory()) {
            File[] files = f.listFiles();
            for (File file : files) {
                ret = ret && rimuoviRicorsivo(file);
            }
        }

        //cancello il file o la directory vuota
        ret = ret && f.delete();

        return ret;
    }

    @Override
    public void rimuoviCategoriaSensore(String cat) throws Exception {
        String percorso = PercorsiFile.getInstance().getCartellaCategoriaSensore(cat);
        File f = new File(percorso);
        rimuoviRicorsivo(f);
    }

    @Override
    public void rimuoviCategoriaAttuatore(String cat) throws Exception {
        String percorso = PercorsiFile.getInstance().getCartellaCategoriaAttuatore(cat);
        File f = new File(percorso);
        rimuoviRicorsivo(f);
    }

    @Override
    public void rimuoviModalita(String modalita, String cat) throws Exception {
        String percorso = PercorsiFile.getInstance().getPercorsoModalita(modalita, cat);
        File f = new File(percorso);
        rimuoviRicorsivo(f);
    }

    @Override
    public void rimuoviUnitaImmobiliare(String unita) throws Exception {
        String percorso = PercorsiFile.getInstance().getCartellaUnitaImmobiliare(unita);
        File f = new File(percorso);
        rimuoviRicorsivo(f);
    }

    @Override
    public void rimuoviStanza(String stanza, String unita) throws Exception {
        String percorso = PercorsiFile.getInstance().getPercorsoStanza(stanza, unita);
        File f = new File(percorso);
        rimuoviRicorsivo(f);
    }

    @Override
    public void rimuoviArtefatto(String artefatto, String unita) throws Exception {
        String percorso = PercorsiFile.getInstance().getPercorsoArtefatto(artefatto, unita);
        File f = new File(percorso);
        rimuoviRicorsivo(f);
    }

    @Override
    public void rimuoviSensore(String sensore) throws Exception {
        String percorso = PercorsiFile.getInstance().getPercorsoSensore(sensore);
        File f = new File(percorso);
        rimuoviRicorsivo(f);
    }

    @Override
    public void rimuoviAttuatore(String attuatore) throws Exception {
        String percorso = PercorsiFile.getInstance().getAttuatore(attuatore);
        File f = new File(percorso);
        rimuoviRicorsivo(f);
    }
}
