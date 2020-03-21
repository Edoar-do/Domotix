package domotix.model.bean.regole;

import domotix.model.bean.device.Attuatore;
import domotix.model.bean.device.Modalita;
import domotix.model.bean.device.Parametro;

import java.util.List;

public class Azione {
    private Attuatore attuatore;
    private Modalita modalita;
    private List<Parametro> parametri;

    public Azione(Attuatore attuatore, Modalita modalita, List<Parametro> parametri) {
        this.attuatore = attuatore;
        this.modalita = modalita;
        this.parametri = parametri;
    }

    private void checkParametri() {
        parametri.forEach(p -> {
            if (!modalita.containsParametro(p.getNome())) {
                throw new IllegalArgumentException("Parametro " + p.getNome() + " non presente in modalita' " + modalita.getNome());
            }
        });
    }

    public void esegui() {
        parametri.forEach(p -> modalita.setNuovoParametro(p));
        attuatore.setModoOp(modalita);
    }
}
