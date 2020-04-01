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
        esegui(null);
    }

    /**
     * Metodo che esegue gli assegnamenti del conseguente e, nel caso di azioni da pianificare, utilizza l'id della regola
     * indicato come parametro come identificativo (questo e' altamente consigliato per evitare la proliferazione di piu'
     * pianificazioni per la stessa azione)
     * @param idRegola  identificativo della regola che esegue il conseguente
     */
    public void esegui(String idRegola) {
        for (Azione a : azioni) {
            if (a.getStart() == null) a.esegui();
            else if (idRegola == null || idRegola.trim().isEmpty() )
                ElencoAzioniProgrammate.getInstance().add(a);
            else
                ElencoAzioniProgrammate.getInstance().add(idRegola, a);
        }
    }

    @Override
    public String toString() {
        return azioni.stream().map(a -> a.toString()).collect(Collectors.joining(", "));
    }
}
