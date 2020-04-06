package domotix.model.bean.regole;

import domotix.model.bean.device.Attuatore;
import domotix.model.bean.device.Modalita;
import domotix.model.bean.device.Parametro;

import java.time.LocalTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;


/**
 * Calsse che rappresenta un'azione che puo' comparire nel conseguente di
 * una regola.
 * @author andrea
 */
public class Azione {
    private Attuatore attuatore;
    private Modalita modalita;
    private List<Parametro> parametri;
    private LocalTime start;

    public Azione(Attuatore attuatore, Modalita modalita, List<Parametro> parametri, LocalTime start) {
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

    public LocalTime getStart() {
        return start;
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

    @Override
    public boolean equals(Object obj) {
        if (super.equals(obj))  return true;

        if (obj instanceof Azione) {
            Azione azione = (Azione)obj;
            AtomicBoolean esito = new AtomicBoolean(true);

            esito.set(this.attuatore.equals(azione.attuatore));
            esito.set(esito.get() && this.modalita.equals(azione.modalita));
            esito.set(esito.get() && this.getParametri().size() == azione.getParametri().size());
            this.getParametri().forEach(parametro -> {
                esito.set(esito.get() && azione.getParametri().contains(parametro));
            });
            esito.set(esito.get() && this.getStart().equals(azione.getStart()));

            return esito.get();
        }

        return false;
    }

}
