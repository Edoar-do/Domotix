package domotix.model.bean.regole;

public class Antecedente {
    private Condizione condizione;
    private String operatoreLogico;
    private Antecedente prossimoAntecedente; // una sorta di linked list

    public Antecedente(Condizione condizione) {
        this.condizione = condizione;
        this.operatoreLogico = null;
        this.prossimoAntecedente = null;
    }

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
