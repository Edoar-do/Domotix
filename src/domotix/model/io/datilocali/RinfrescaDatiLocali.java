package domotix.model.io.datilocali;

import domotix.model.ElencoSensori;
import domotix.model.bean.device.InfoRilevabile;
import domotix.model.bean.device.Sensore;
import domotix.model.io.LetturaDatiSalvati;
import domotix.model.io.RinfrescaDatiAdapter;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import java.nio.file.NotDirectoryException;

/**
 * Classe che implementa l'interfaccia RinfrescaDati per definire un meccanismo di ri-lettura dei dati su file memorizzati
 * localmente sulla macchina di esecuzione del programma.
 * Si sviluppa una struttura ad albero nel FileSystem del SistemaOperativo posizionata nella cartella dell'applicazione (attualmente equivale ad
 * una cartella contenuta nella cartella utente).
 *
 * @author paolopasqua
 * @see domotix.model.io.RinfrescaDati
 */
public class RinfrescaDatiLocali extends RinfrescaDatiAdapter {

    private static RinfrescaDatiLocali _instance = null;

    public static RinfrescaDatiLocali getInstance() throws NotDirectoryException, ParserConfigurationException, TransformerConfigurationException {
        if (_instance == null)
            _instance = new RinfrescaDatiLocali();
        return _instance;
    }

    private RinfrescaDatiLocali() throws NotDirectoryException, ParserConfigurationException, TransformerConfigurationException {
        //test esistenza struttura dati
        PercorsiFile.getInstance().controllaStruttura();
    }

    @Override
    public void rinfrescaSensori(ElencoSensori elenco) throws Exception {
        for (Sensore s : elenco.getDispositivi()) {
            Sensore letto = LetturaDatiSalvati.getInstance().leggiSensore(s.getNome());
            for (String nomeInfo : s.getValori().keySet()) {
                s.setValore(nomeInfo, letto.getValore(nomeInfo));
            }
        }
    }
}
