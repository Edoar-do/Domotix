package domotix.model.bean.regole;

import domotix.controller.io.visitor.Visitable;
import domotix.controller.io.visitor.Visitor;
import domotix.model.ElencoAzioniProgrammate;
import domotix.model.bean.device.Attuatore;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Classe che rappresenta il conseguente di una regola.
 * @author andrea
 */
public class Conseguente implements Visitable {
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
            else if (!ElencoAzioniProgrammate.getInstance().contains(a))
                ElencoAzioniProgrammate.getInstance().add(a);
        }
    }

    /**
     * Metodo che ritorna gli attuatori di tutte le azioni del conseguente
     * @return un array degli attuatori del conseguente
     */
    public Attuatore[] getAttuatori(){
        ArrayList<Attuatore> attuatori = new ArrayList<>();
        for (Azione a: azioni) {
            Attuatore att = a.getAttuatore();
            attuatori.add(att);
        }
        return attuatori.toArray(new Attuatore[0]);
    }

    @Override
    public Object fattiVisitare(Visitor v) {
        return v.visitaConseguente(this);
    }
}
