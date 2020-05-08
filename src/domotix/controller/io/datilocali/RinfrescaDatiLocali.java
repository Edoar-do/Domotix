package domotix.controller.io.datilocali;

import domotix.model.bean.device.Sensore;
import domotix.controller.io.LetturaDatiSalvati;
import domotix.controller.io.RinfrescaDatiAdapter;

import java.util.List;

/**
 * Classe che implementa l'interfaccia RinfrescaDati per definire un meccanismo di ri-lettura dei dati su file memorizzati
 * localmente sulla macchina di esecuzione del programma.
 * Si sviluppa una struttura ad albero nel FileSystem del SistemaOperativo posizionata nella cartella dell'applicazione (attualmente equivale ad
 * una cartella contenuta nella cartella utente).
 *
 * @author paolopasqua
 * @see domotix.controller.io.RinfrescaDati
 */
public class RinfrescaDatiLocali extends RinfrescaDatiAdapter {

    private LetturaDatiSalvati lettore = null;

    private RinfrescaDatiLocali(LetturaDatiSalvati lettore) {
        this.lettore = lettore;
    }

    @Override
    public void rinfrescaSensori(List<Sensore> elenco) throws Exception {
        for (Sensore s : elenco) {
            Sensore letto = this.lettore.leggiSensore(s.getNome());
            for (String nomeInfo : s.getValori().keySet()) {
                s.setValore(nomeInfo, letto.getValore(nomeInfo));
            }
        }
    }
}
