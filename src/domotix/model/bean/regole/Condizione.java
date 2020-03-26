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

    public static final String MINORE_UGUALE = "<=";
    public static final String MINORE = "<";
    public static final String UGUALE = "=";
    public static final String MAGGIORE = ">";
    public static final String MAGGIORE_UGUALE = ">=";

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

        if (!UGUALE.equals(operatore) && !areNumeriche(valSinistro, valDestro)) {
            return false;
        }
        return true;
    }

    public InfoSensoriale getSinistra() {
        return sinistra;
    }

    public String getOperatore() {
        return operatore;
    }

    public InfoSensoriale getDestra() {
        return destra;
    }

    private boolean checkOperatore(String op) {
        // si potrebbe fare un array / enum con gli operatori ammissibili comunque
        return MAGGIORE.equals(op) || MAGGIORE_UGUALE.equals(op) || MINORE.equals(op) || MINORE_UGUALE.equals(op) || "=".equals(op);
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
            case MAGGIORE:
                return (Double) valSinistro > (Double) valDestro;
            case MAGGIORE_UGUALE:
                return (Double) valSinistro >= (Double) valDestro;
            case MINORE:
                return (Double) valSinistro < (Double) valDestro;
            case MINORE_UGUALE:
                return (Double) valSinistro <= (Double) valDestro;
            case UGUALE:
                return valSinistro.equals(valDestro);
        }
        return false;
    }

    @Override
    public String toString() {
        return sinistra.toString() + " " + operatore + " " + destra.toString();
    }
}
