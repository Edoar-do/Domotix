package domotix.model.bean.regole;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Classe che rappresenta una delle regole periodicamente eseguite.
 * @author andrea
 */
public class Regola {
    private String id; //UUID
    private boolean stato;
    private Antecedente antecedente;
    private Conseguente conseguente;

    public Regola(boolean stato, Antecedente antecedente, Conseguente conseguente) {
        this.id = UUID.randomUUID().toString();
        this.stato = stato;
        this.antecedente = antecedente;
        this.conseguente = conseguente;
    }

    public Regola() {
        this(false, null, new Conseguente());
    }

    /**
     * Metodo di aggiunta di un'azione al conseguente
     * @param azione Azione da aggiungere
     */
    public void addAzione(Azione azione) {
        this.conseguente.addAzione(azione);
    }

    /**
     * Metodo di aggiunta di una condizione all'antecedente
     * @param condizione La condizione da aggiungere
     */
    public void addCondizone(Condizione condizione) {
        if (antecedente == null) {
            antecedente = new Antecedente(condizione);
        } else {
            antecedente.addCondizione(condizione);
        }
    }

    /**
     * Metodo di aggiunta di un operatore pendente tra condizioni dell'antecedente
     * @param op Operatore logico di collegamento tra condizioni
     */
    public void addOperatore(String op) {
        if (antecedente == null) {
            throw new IllegalArgumentException("Non si possono aggiungere operazioni quando non sono state inserite condizioni");
        } else {
            antecedente.addOperatore(op);
        }
    }

    /**
     * Metodo di aggiunta di una condizione all'antecedente.
     * L'operatore logico e' appeso all'antecedente che e' in coda prima della chiamata del metodo.
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
            conseguente.esegui();
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

    /**
     * Metodo che restituisce l'antecedente
     * @return L'antecedente
     */
    public Antecedente getAntecedente() {
        return this.antecedente;
    }

    /**
     * Metodo che restituisce il conseguente
     * @return Il conseguente
     */
    public Conseguente getConseguente() {
        return conseguente;
    }

    @Override
    public String toString() {
        String antstr = antecedente == null ? "true" : antecedente.toString();
        String consstr = conseguente.toString();
        return "if " + antstr + " then " + consstr;
    }
}
