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

    public void esegui() {
        parametri.forEach(p -> modalita.setParametro(p));
        attuatore.setModoOp(modalita);
    }
}
