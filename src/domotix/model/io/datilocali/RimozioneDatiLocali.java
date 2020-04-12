package domotix.model.io.datilocali;

import domotix.model.bean.UnitaImmobiliare;
import domotix.model.bean.device.*;
import domotix.model.bean.regole.Azione;
import domotix.model.bean.regole.Regola;
import domotix.model.bean.system.Artefatto;
import domotix.model.bean.system.Stanza;
import domotix.model.io.RimozioneDatiSalvati;
import domotix.model.io.RimozioneDatiSalvatiAdapter;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import java.io.File;
import java.nio.file.NotDirectoryException;
import java.util.List;
import java.util.Map;

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
    public void rimuoviInfoRilevabile(String info, String cat) throws Exception {
        String percorso = PercorsiFile.getInstance().getPercorsoInformazioneRilevabile(info, cat);
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

    @Override
    public void rimuoviRegola(String idRegola, String unita) throws Exception {
        String percorso = PercorsiFile.getInstance().getPercorsoRegola(idRegola, unita);
        File f = new File(percorso);
        rimuoviRicorsivo(f);
    }

    @Override
    public void rimuoviAzioneProgrammata(String id) throws Exception {
        String percorso = PercorsiFile.getInstance().getPercorsoAzioneProgrammabile(id);
        File f = new File(percorso);
        rimuoviRicorsivo(f);
    }

    @Override
    public void sincronizzaCategorieSensore(List<CategoriaSensore> entita) throws Exception {
        List<String> nomiDati = PercorsiFile.getInstance().getNomiCategorieSensori();

        //rimuovo le entita logiche presenti
        for (CategoriaSensore categoriaSensore : entita) {
            nomiDati.remove(categoriaSensore.getNome());
            sincronizzaInfoRilevabile(categoriaSensore); //se entita logica presente allora sincronizzo le componenti
        }
        //elaboro i nomi rimasti
        for (String catSens : nomiDati) {
            rimuoviCategoriaSensore(catSens);
        }
    }

    @Override
    public void sincronizzaInfoRilevabile(CategoriaSensore entita) throws Exception {
        List<String> nomiDati = PercorsiFile.getInstance().getNomiInformazioniRilevabili(entita.getNome());

        //rimuovo le entita logiche presenti
        for (InfoRilevabile info : entita.getInformazioniRilevabili()) {
            nomiDati.remove(info.getNome());
        }
        //elaboro i nomi rimasti
        for (String info : nomiDati) {
            rimuoviInfoRilevabile(info, entita.getNome());
        }
    }

    @Override
    public void sincronizzaCategorieAttuatore(List<CategoriaAttuatore> entita) throws Exception {
        List<String> nomiDati = PercorsiFile.getInstance().getNomiCategorieAttuatori();

        //rimuovo le entita logiche presenti
        for (CategoriaAttuatore catAtt : entita) {
            nomiDati.remove(catAtt.getNome());
            sincronizzaModalita(catAtt); //se entita logica presente allora sincronizzo le componenti
        }
        //elaboro i nomi rimasti
        for (String catAtt : nomiDati) {
            rimuoviCategoriaAttuatore(catAtt);
        }
    }

    @Override
    public void sincronizzaModalita(CategoriaAttuatore entita) throws Exception {
        List<String> nomiDati = PercorsiFile.getInstance().getNomiModalita(entita.getNome());

        //rimuovo le entita logiche presenti
        for (Modalita modalita : entita.getElencoModalita()) {
            nomiDati.remove(modalita.getNome());
        }
        //elaboro i nomi rimasti
        for (String modalita : nomiDati) {
            rimuoviModalita(modalita, entita.getNome());
        }
    }

    @Override
    public void sincronizzaUnitaImmobiliari(List<UnitaImmobiliare> entita) throws Exception {
        List<String> nomiDati = PercorsiFile.getInstance().getNomiUnitaImmobiliare();

        //rimuovo le entita logiche presenti
        for (UnitaImmobiliare unita : entita) {
            nomiDati.remove(unita.getNome());
            sincronizzaStanze(unita); //se entita logica presente allora sincronizzo le componenti
            sincronizzaArtefatti(unita); //se entita logica presente allora sincronizzo le componenti
            sincronizzaRegole(unita); //se entita logica presente allora sincronizzo le componenti
        }
        //elaboro i nomi rimasti
        for (String unita : nomiDati) {
            rimuoviUnitaImmobiliare(unita);
        }
    }

    @Override
    public void sincronizzaStanze(UnitaImmobiliare entita) throws Exception {
        List<String> nomiDati = PercorsiFile.getInstance().getNomiStanze(entita.getNome());

        //rimuovo le entita logiche presenti
        for (Stanza stanza : entita.getStanze()) {
            nomiDati.remove(stanza.getNome());
        }
        //elaboro i nomi rimasti
        for (String stanza : nomiDati) {
            rimuoviStanza(stanza, entita.getNome());
        }
    }

    @Override
    public void sincronizzaArtefatti(UnitaImmobiliare entita) throws Exception {
        List<String> nomiDati = PercorsiFile.getInstance().getNomiArtefatti(entita.getNome());

        //rimuovo le entita logiche presenti
        for (Stanza stanza : entita.getStanze()) {
            for (Artefatto artefatto : stanza.getArtefatti()) {
                nomiDati.remove(artefatto.getNome());
            }
        }
        //elaboro i nomi rimasti
        for (String artefatto : nomiDati) {
            rimuoviArtefatto(artefatto, entita.getNome());
        }
    }

    @Override
    public void sincronizzaSensori(List<Sensore> entita) throws Exception {
        List<String> nomiDati = PercorsiFile.getInstance().getNomiSensori();

        //rimuovo le entita logiche presenti
        for (Sensore sensore : entita) {
            nomiDati.remove(sensore.getNome());
        }
        //elaboro i nomi rimasti
        for (String sensore : nomiDati) {
            rimuoviSensore(sensore);
        }
    }

    @Override
    public void sincronizzaAttuatori(List<Attuatore> entita) throws Exception {
        List<String> nomiDati = PercorsiFile.getInstance().getNomiAttuatori();

        //rimuovo le entita logiche presenti
        for (Attuatore attuatore : entita) {
            nomiDati.remove(attuatore.getNome());
        }
        //elaboro i nomi rimasti
        for (String attuatore : nomiDati) {
            rimuoviAttuatore(attuatore);
        }
    }

    @Override
    public void sincronizzaRegole(UnitaImmobiliare entita) throws Exception {
        List<String> nomiDati = PercorsiFile.getInstance().getNomiRegola(entita.getNome());

        //rimuovo le entita logiche presenti
        for (Regola regola : entita.getRegole()) {
            nomiDati.remove(regola.getId());
        }
        //elaboro i nomi rimasti
        for (String regola : nomiDati) {
            rimuoviRegola(regola, entita.getNome());
        }
    }

    @Override
    public void sincronizzaAzioniProgrammate(Map<String, Azione> entita) throws Exception {
        List<String> nomiDati = PercorsiFile.getInstance().getNomiAzioniProgramamte();

        //rimuovo le entita logiche presenti
        for (String id : entita.keySet()) {
            nomiDati.remove(id);
        }
        //elaboro i nomi rimasti
        for (String id : nomiDati) {
            rimuoviAzioneProgrammata(id);
        }
    }
}
