package domotix.model.bean.device;

/**
 * Classe per la rappresentazione di un parametro di una Modalita' per le CategoriaAttuatore.
 *
 * @author paolopasqua
 * @see Modalita
 * @see CategoriaAttuatore
 */
public class Parametro {

    private String nome;
    private double valore;

    /**
     * Costruttore della classe
     * @param nome  stringa contenente il nome univoco a livello di classe
     * @param valore    valore numerico del parametro
     */
    public Parametro(String nome, double valore) {
        this.nome = nome;
        this.valore = valore;
    }

    /**
     * Recupera il nome del parametro
     * @return  stringa contenente il nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * Recupera il valore del parametro
     * @return  numero decimale come valore del parametro
     */
    public double getValore() {
        return valore;
    }

    @Override
    public String toString() {
        return nome + ": " + String.format("%.2d", valore);
    }
}
