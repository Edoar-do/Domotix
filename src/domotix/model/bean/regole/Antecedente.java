package domotix.model.bean.regole;

/**
 * Classe che rappresenta l'antecedente di una regola.
 * @author andrea
 */
public class Antecedente {
    private Condizione condizione;
    private String operatoreLogico;
    private Antecedente prossimoAntecedente; // una sorta di linked list

    public static final String OPERATORE_OR = "||";
    public static final String OPERATORE_AND = "&&";

    /**
     * Costruttore della classe.
     * @param condizione La prima condizione dell'antecedente
     */
    public Antecedente(Condizione condizione) {
        this.condizione = condizione;
        this.operatoreLogico = null;
        this.prossimoAntecedente = null;
    }

    public boolean contieneSensore(String nome) {
        Antecedente corrente = this;
        while (corrente != null) {
            if (corrente.condizione.contieneSensore(nome)) return true;
            corrente = corrente.prossimoAntecedente;
        }
        return false;
    }

    public Condizione getCondizione() {
        return condizione;
    }

    public String getOperatoreLogico() {
        return operatoreLogico;
    }

    public Antecedente getProssimoAntecedente() {
        return prossimoAntecedente;
    }

    /**
     * Ritorna se l'antecedente e' "ultimo" nel senso che non ha condizioni legate con un operatore logico.
     * @return  true: e' ultimo; false altrimenti
     */
    public boolean isLast() {
        return operatoreLogico == null || prossimoAntecedente == null;
    }

    /**
     * Metodo per aggiungere un operatore (pendente) di collegamento all'ultima condizione.
     * @param nuovoOperatore Operatore logico di collegamento tra condizioni.
     */
    public void addOperatore(String nuovoOperatore) {
        if (!checkOperatore(nuovoOperatore)) {
            throw new IllegalArgumentException("Operatore logico " + nuovoOperatore + " non valido.");
        }
        Antecedente current = this;
        while (current.prossimoAntecedente != null) current = current.prossimoAntecedente;
        current.operatoreLogico = nuovoOperatore;
    }

    /**
     * Metodo per aggiungere una condizione all'antecedente.
     * @param nuovaCondizione Nuova condizione
     */
    public void addCondizione(Condizione nuovaCondizione) {
        Antecedente current = this;
        while (current.prossimoAntecedente != null) current = current.prossimoAntecedente;
        current.prossimoAntecedente = new Antecedente(nuovaCondizione);
    }

    /**
     * Metodo per aggiungere una condizione all'antecedente.
     * L'operatore logico e' appeso all'antecedente che e' in coda prima della chiamata del metodo.
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
        current.prossimoAntecedente = new Antecedente(nuovaCondizione);
    }

    /**
     * Metodo per aggiungere un antecedente a quello attuale.
     * L'operatore logico e' appeso all'antecedente che e' in coda prima della chiamata del metodo.
     * @param antecedente   antecedente da aggiungere
     */
    public void addAntecedente(Antecedente antecedente) {
        Antecedente current = this;
        while (current.prossimoAntecedente != null) current = current.prossimoAntecedente;
        current.prossimoAntecedente = antecedente;
    }

    /**
     * Metodo per aggiungere un antecedente a quello attuale.
     * L'operatore logico e' appeso all'antecedente che e' in coda prima della chiamata del metodo.
     * @param nuovoOperatore    operatore logico di collegamento tra condizioni
     * @param antecedente   antecedente da aggiungere
     */
    public void addAntecedente(String nuovoOperatore, Antecedente antecedente) {
        if (!checkOperatore(nuovoOperatore)) {
            throw new IllegalArgumentException("Operatore logico " + nuovoOperatore + " non valido.");
        }
        Antecedente current = this;
        while (current.prossimoAntecedente != null) current = current.prossimoAntecedente;
        current.operatoreLogico = nuovoOperatore;
        current.prossimoAntecedente = antecedente;
    }

    private boolean checkOperatore(String nuovoOperatore) {
        // si potrebbe fare un array / enum con gli operatori ammissibili comunque
        return OPERATORE_OR.equals(nuovoOperatore) || OPERATORE_AND.equals(nuovoOperatore);
    }

    /**
     * Metodo che valuta il valore dell'espressione antecedente.
     * @return Il valore dell'espressione.
     */
    public boolean valuta() {
        return orExpr(this);
    }

    private boolean orExpr(Antecedente corrente) {
        if (corrente.prossimoAntecedente != null && corrente.operatoreLogico == null) {
            throw new IllegalArgumentException("Condizione pendente");
        }

        boolean sinistro = andExpr(corrente);
        while (OPERATORE_OR.equals(corrente.operatoreLogico)) {
            if (corrente.prossimoAntecedente == null) {
                throw new IllegalArgumentException("Operatore pendente: " + corrente.operatoreLogico);
            }
            boolean destro = andExpr(corrente.prossimoAntecedente);
            sinistro = sinistro || destro;
            corrente = corrente.prossimoAntecedente;
        }
        return sinistro;
    }

    private boolean andExpr(Antecedente corrente) {
        if (corrente.prossimoAntecedente != null && corrente.operatoreLogico == null) {
            throw new IllegalArgumentException("Condizione pendente");
        }

        boolean sinistro = corrente.condizione.valuta();
        while (OPERATORE_AND.equals(corrente.operatoreLogico)) {
            if (corrente.prossimoAntecedente == null) {
                throw new IllegalArgumentException("Operatore pendente: " + corrente.operatoreLogico);
            }
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
