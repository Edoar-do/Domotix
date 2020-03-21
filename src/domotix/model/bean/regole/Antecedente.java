package domotix.model.bean.regole;

/**
 * Classe che rappresenta l'antecedente di una regola.
 * @author andrea
 */
public class Antecedente {
    private Condizione condizione;
    private String operatoreLogico;
    private Antecedente prossimoAntecedente; // una sorta di linked list

    /**
     * Costruttore della classe.
     * @param condizione La prima condizione dell'antecedente
     */
    public Antecedente(Condizione condizione) {
        this.condizione = condizione;
        this.operatoreLogico = null;
        this.prossimoAntecedente = null;
    }

    /**
     * Metodo per aggiungere una condizione all'antecedente.
     * @param nuovoOperatore Operatore logico di collegamento tra condizioni.
     * @param nuovaCondizione Nuova condizione
     */
    public void addCondizione(String nuovoOperatore, Condizione nuovaCondizione) {
        if (!checkOperatore(nuovoOperatore)) {
            throw new IllegalArgumentException("Operatore logico " + nuovoOperatore + " non valido.");
        }
        Antecedente current = this;
        while (current.prossimoAntecedente != null) current = current.prossimoAntecedente;
        current.operatoreLogico = nuovoOperatore;
        current.prossimoAntecedente = new Antecedente(condizione);
    }

    private boolean checkOperatore(String nuovoOperatore) {
        // si potrebbe fare un array / enum con gli operatori ammissibili comunque
        return nuovoOperatore.equals("||") || nuovoOperatore.equals("&&");
    }

    /**
     * Metodo che valuta il valore dell'espressione antecedente.
     * @return Il valore dell'espressione.
     */
    public boolean valuta() {
        return orExpr(this);
    }

    private boolean orExpr(Antecedente corrente) {
        boolean sinistro = andExpr(corrente);
        while (corrente.operatoreLogico.equals("||")) {
            boolean destro = andExpr(corrente.prossimoAntecedente);
            sinistro = sinistro || destro;
            corrente = corrente.prossimoAntecedente;
        }
        return sinistro;
    }

    private boolean andExpr(Antecedente corrente) {
        boolean sinistro = corrente.condizione.valuta();
        while (corrente.operatoreLogico.equals("&&")) {
            boolean destro = corrente.prossimoAntecedente.condizione.valuta();
            sinistro = sinistro && destro;
            corrente = corrente.prossimoAntecedente;
        }
        return sinistro;
    }

    @Override
    public String toString() {
        String lhs = condizione.toString();
        String opstr = operatoreLogico == null ? "" : " " + operatoreLogico + " ";
        String rhs = prossimoAntecedente == null ? "" : prossimoAntecedente.toString();
        return lhs + opstr + rhs;
    }
}
