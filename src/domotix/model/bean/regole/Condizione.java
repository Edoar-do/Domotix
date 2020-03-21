package domotix.model.bean.regole;

public class Condizione {
    private InfoSensoriale sinistra;
    private String operatore;
    private InfoSensoriale destra;

    public Condizione(InfoSensoriale sinistra, String operatore, InfoSensoriale destra) {
        this.sinistra = sinistra;
        this.operatore = operatore;
        this.destra = destra;
        if (!checkOperatore(operatore)) {
            throw new IllegalArgumentException("Operatore " + operatore + " non ammesso.");
        }
    }

    private boolean checkOperatore(String op) {
        // si potrebbe fare un array / enum con gli operatori ammissibili comunque
        return op.equals(">") || op.equals(">=") || op.equals("<") || op.equals("<=") || op.equals("=");
    }

    private boolean areNumeriche(Object valSinistro, Object valDestro) {
        return (valSinistro instanceof Number) && (valDestro instanceof Number);
    }

    public boolean valuta() {
        Object valSinistro = sinistra.getValore();
        Object valDestro = destra.getValore();

        if (operatore != "=" && !areNumeriche(valSinistro, valDestro)) {
            throw new IllegalArgumentException("Operatore " + operatore + "applicabile solo su valori numerici.");
        }
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
