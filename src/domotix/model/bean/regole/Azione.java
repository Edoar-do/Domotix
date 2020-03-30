package domotix.model.bean.regole;

import domotix.model.bean.device.Attuatore;
import domotix.model.bean.device.Modalita;
import domotix.model.bean.device.Parametro;

import java.time.LocalDateTime;
import java.time.ZoneId;
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
    private LocalDateTime start;

    public Azione(Attuatore attuatore, Modalita modalita, List<Parametro> parametri, LocalDateTime start) {
        this.attuatore = attuatore;
        this.modalita = modalita;
        this.parametri = parametri;
        checkParametri();
        this.start = start;
    }

    public Azione(Attuatore attuatore, Modalita modalita, List<Parametro> parametri) {
        this(attuatore, modalita, parametri, null);
    }

    private void checkParametri() {
        parametri.forEach(p -> {
            if (!modalita.containsParametro(p.getNome())) {
                throw new IllegalArgumentException("Parametro " + p.getNome() + " non presente in modalita' " + modalita.getNome());
            }
        });
    }

    public boolean contieneAttuatore(String nome) {
        return attuatore.getNome().equals(nome);
    }

    public Attuatore getAttuatore() {
        return attuatore;
    }

    public Modalita getModalita() {
        return modalita;
    }

    public List<Parametro> getParametri() {
        return parametri;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public long getEpochStart() {
        return start.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
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
        String timestr = start == null ? "" : ", start := " + start.toString(); // todo
        return attuatore.getNome() + " := " + modalita.getNome() + parstr + timestr;
    }
}
