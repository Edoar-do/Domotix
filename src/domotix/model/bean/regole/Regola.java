package domotix.model.bean.regole;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Classe che rappresenta una delle regole periodicamente eseguite.
 * @author andrea
 */
public class Regola {
    private String id; //UUID
    private boolean stato;
    private Antecedente antecedente;
    private List<Azione> conseguente;

    public Regola(boolean stato, Antecedente antecedente, List<Azione> conseguente) {
        this.id = UUID.randomUUID().toString();
        this.stato = stato;
        this.antecedente = antecedente;
        this.conseguente = conseguente;
    }

    public Regola() {
        this(false, null, new ArrayList<>());
    }

    /**
     * Metodo di aggiunta di un'azione al conseguente
     * @param azione Azione da aggiungere
     */
    public void addAzione(Azione azione) {
        this.conseguente.add(azione);
    }

    /**
     * Metodo per settare la prima condizione dell'antecedente.
     * Se non viene fissato, l'antecedente e' supposto sempre true.
     * @param antecedente Antecedente iniziale
     */
    public void initAntecedente(Antecedente antecedente) {
        this.antecedente = antecedente;
    }

    /**
     * Metodo per settare la prima condizione dell'antecedente.
     * Se non viene fissato, l'antecedente e' supposto sempre true.
     * @param condizione La prima condizione dell'antecedente
     */
    public void initAntecedente(Condizione condizione) {
        this.initAntecedente(new Antecedente(condizione));
    }

    /**
     * Metodo di aggiunta di una condizione all'antecedente
     * @param operatore Operatore logico di collegamento tra condizioni
     * @param condizione La condizione da aggiungere
     */
    public void addCondizione(String operatore, Condizione condizione) {
        this.antecedente.addCondizione(operatore, condizione);
    }

    /**
     * Metodo che viene eseguito se la regola e' attiva.
     * Viene valutato l'antecedente. Se risulta true, viene eseguito il conseguente.
     */
    public void esegui() {
        if (!stato) return;

        // antecedente == null equivale a dire condizione sempre true
        if (antecedente == null || antecedente.valuta()) {
            conseguente.forEach(a -> a.esegui());
        }
    }

    /**
     * Metodo che restituisce l'ID della regola.
     * @return L'ID
     */
    public String getId() {
        return id;
    }

    /**
     * Metodo che restituisce lo stato.
     * @return True se attivo, false altrimenti
     */
    public boolean getStato() {
        return stato;
    }

    /**
     * Metodo per impostare il nuovo stato
     * @param stato Nuovo stato
     */
    public void setStato(boolean stato) {
        this.stato = stato;
    }

    @Override
    public String toString() {
        String antstr = antecedente == null ? "true" : antecedente.toString();
        String consstr = conseguente.stream().map(a -> a.toString()).collect(Collectors.joining(", "));
        return "if " + antstr + " then " + consstr;
    }
}
