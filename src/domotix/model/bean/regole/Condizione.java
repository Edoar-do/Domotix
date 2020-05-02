package domotix.model.bean.regole;

import domotix.model.bean.device.Sensore;

import java.util.ArrayList;

/**
 * Classe che rappresenta una condizione che puo' apparire nell'antecedente di
 * una regola.
 * @author andrea
 */
public class Condizione implements Visitable {
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

    private boolean checkOperatore(String op) {
        // si potrebbe fare un array / enum con gli operatori ammissibili comunque
        return MAGGIORE.equals(op) || MAGGIORE_UGUALE.equals(op) || MINORE.equals(op) || MINORE_UGUALE.equals(op) || "=".equals(op);
    }

    private boolean areNumeriche(Object valSinistro, Object valDestro) {
        return (valSinistro instanceof Number) && (valDestro instanceof Number);
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

    /**
     * Metodo che valuta la condizione.
     * @return Il valore della condizione.
     */
    public boolean valuta() {
        Object valSinistro = sinistra.getValore();
        Object valDestro = destra.getValore();

        //Test su dati numerici
        if (Number.class.isAssignableFrom(valSinistro.getClass()) &&
                Number.class.isAssignableFrom(valDestro.getClass())) {
            double numSinistra = ((Number)valSinistro).doubleValue();
            double numDestra = ((Number)valDestro).doubleValue();

            switch (operatore) {
                case MAGGIORE:
                    return numSinistra > numDestra;
                case MAGGIORE_UGUALE:
                    return numSinistra >= numDestra;
                case MINORE:
                    return numSinistra < numDestra;
                case MINORE_UGUALE:
                    return numSinistra <= numDestra;
                case UGUALE:
                    return numSinistra == numDestra;
            }
        }

        //test su dati non numerici
        if (operatore.equals(UGUALE)) {
            //entrambi gli equals in or per consentire ai possibili equals di classi figlie con controlli diversi
            return valSinistro.equals(valDestro) || valDestro.equals(valSinistro);
        }

        return false;
    }

    public boolean contieneSensore(String nome) {
        if (sinistra instanceof InfoVariabile) {
            return ((InfoVariabile) sinistra).getSensore().getNome().equals(nome);
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return sinistra.toString() + " " + operatore + " " + destra.toString();
    }

    public Sensore[] getSensori(){
        ArrayList<Sensore> sensori = new ArrayList<>();
        if(sinistra instanceof InfoVariabile)
            sensori.add(((InfoVariabile) sinistra).getSensore());
        if(destra instanceof  InfoVariabile)
            sensori.add(((InfoVariabile) destra).getSensore());
        return sensori.toArray(new Sensore[0]);
    }

    @Override
    public Object fattiVisitare(Visitor v) {
        return v.visitaCondizione(this);
    }
}
