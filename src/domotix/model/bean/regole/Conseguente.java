package domotix.model.bean.regole;

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

    /**
     * Metodo di aggiunta di un'azione al conseguente.
     * @param azione Azione da aggiungere.
     */
    public void addAzione(Azione azione) {
        this.azioni.add(azione);
    }

    /**
     * Metodo che esegue gli assegnamenti del conseguente.
     */
    public void esegui() {
        azioni.forEach(a -> a.esegui());
    }

    @Override
    public String toString() {
        return azioni.stream().map(a -> a.toString()).collect(Collectors.joining(", "));
    }
}