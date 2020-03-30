package domotix.model.bean.regole;

import domotix.model.ElencoAzioniProgrammate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Classe che rappresenta il conseguente di una regola.
 * @author andrea
 */
public class Conseguente {
    private List<Azione> azioni;

    public Conseguente() {
        this.azioni = new ArrayList<>();
    }

    public boolean contieneAttuatore(String nome) {
        for (Azione a : azioni) {
            if (a.contieneAttuatore(nome)) return true;
        }
        return false;
    }

    /**
     * Metodo di aggiunta di un'azione al conseguente.
     * @param azione Azione da aggiungere.
     */
    public void addAzione(Azione azione) {
        this.azioni.add(azione);
    }

    public List<Azione> getAzioni() {
        return azioni;
    }

    /**
     * Metodo che esegue gli assegnamenti del conseguente.
     */
    public void esegui() {
        for (Azione a : azioni) {
            if (a.getStart() == null) a.esegui();
            else ElencoAzioniProgrammate.getInstance().add(a);
        }
    }

    @Override
    public String toString() {
        return azioni.stream().map(a -> a.toString()).collect(Collectors.joining(", "));
    }
}
