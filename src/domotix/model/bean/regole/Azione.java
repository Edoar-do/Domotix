package domotix.model.bean.regole;

import domotix.model.bean.device.Attuatore;
import domotix.model.bean.device.Modalita;
import domotix.model.bean.device.Parametro;

import java.util.List;


/**
 * Calsse che rappresenta un'azione che puo' comparire nel conseguente di
 * una regola.
 * @author andrea
 */
public class Azione {
    private Attuatore attuatore;
    private Modalita modalita;
    private List<Parametro> parametri;

    public Azione(Attuatore attuatore, Modalita modalita, List<Parametro> parametri) {
        this.attuatore = attuatore;
        this.modalita = modalita;
        this.parametri = parametri;
        checkParametri();
    }

    private void checkParametri() {
        parametri.forEach(p -> {
            if (!modalita.containsParametro(p.getNome())) {
                throw new IllegalArgumentException("Parametro " + p.getNome() + " non presente in modalita' " + modalita.getNome());
            }
        });
    }

    /**
     * Metodo che esegue l'azione (i.e. l'assegnamento).
     */
    public void esegui() {
        parametri.forEach(p -> modalita.setNuovoParametro(p));
        attuatore.setModoOp(modalita);
    }

    @Override
    public String toString() {
        String parstr = parametri.size() > 0 ? parametri.toString() : "";
        return attuatore.getNome() + " := " + modalita.getNome() + parstr;
    }
}
