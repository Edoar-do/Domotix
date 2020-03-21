package domotix.model.bean.regole;

public class Condizione {
    private InfoSensoriale sinistra;
    private String operatore;
    private InfoSensoriale destra;

    public Condizione(InfoSensoriale sinistra, String operatore, InfoSensoriale destra) {
        this.sinistra = sinistra;
        this.operatore = operatore;
        this.destra = destra;
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
            default:
                // exception
        }
        return false;
    }
}
