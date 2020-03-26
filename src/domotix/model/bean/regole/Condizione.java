package domotix.model.bean.regole;

/**
 * Classe che rappresenta una condizione che puo' apparire nell'antecedente di
 * una regola.
 * @author andrea
 */
public class Condizione {
    private InfoSensoriale sinistra;
    private String operatore;
    private InfoSensoriale destra;

    /**
     * Costruttore della classe.
     * @param sinistra InfoSensoriale che compare sul lato sinistro dell'espressione
     * @param operatore Operatore relazionale
     * @param destra InfoSensoriale che compare sul lato destro dell'espressione
     * @throws IllegalArgumentException Eccezione lanciata nel caso che l'operatore non sia tra quelli supportati
     */
    public Condizione(InfoSensoriale sinistra, String operatore, InfoSensoriale destra) {
        this.sinistra = sinistra;
        this.operatore = operatore;
        this.destra = destra;
        if (!checkOperatore(operatore)) {
            throw new IllegalArgumentException("Operatore " + operatore + " non ammesso.");
        }

        if (!checkOperatoreNumerico()) {
            throw new IllegalArgumentException("Operatore " + operatore + "applicabile solo su valori numerici.");
        }
    }

    private boolean checkOperatoreNumerico() {
        Object valSinistro = sinistra.getValore();
        Object valDestro = destra.getValore();

        if (operatore != "=" && !areNumeriche(valSinistro, valDestro)) {
            return false;
        }
        return true;
    }

    private boolean checkOperatore(String op) {
        // si potrebbe fare un array / enum con gli operatori ammissibili comunque
        return ">".equals(op) || ">=".equals(op) || "<".equals(op) || "<=".equals(op) || "=".equals(op);
    }

    private boolean areNumeriche(Object valSinistro, Object valDestro) {
        return (valSinistro instanceof Number) && (valDestro instanceof Number);
    }

    /**
     * Metodo che valuta la condizione.
     * @return Il valore della condizione.
     */
    public boolean valuta() {
        Object valSinistro = sinistra.getValore();
        Object valDestro = destra.getValore();

        switch (operatore) {
            case ">":
                return (Double) valSinistro > (Double) valDestro;
            case ">=":
                return (Double) valSinistro >= (Double) valDestro;
            case "<":
                return (Double) valSinistro < (Double) valDestro;
            case "<=":
                return (Double) valSinistro <= (Double) valDestro;
            case "=":
                return valSinistro.equals(valDestro);
        }
        return false;
    }

    @Override
    public String toString() {
        return sinistra.toString() + " " + operatore + " " + destra.toString();
    }
}
