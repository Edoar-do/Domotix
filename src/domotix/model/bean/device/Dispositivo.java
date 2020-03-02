package domotix.model.bean.device;
/** @author Edoardo Coppola */
public abstract class Dispositivo {
    private String nome;
    private boolean stato;
    private int numeroAssociazioni;

    /**
     * Recupera il nome del Dispositivo
     * @return Nome del dispositivo
     */
    public String getNome(){
        return nome;
    }

    /**
     * Imposta il nome del Dispositivo
     * @param nome Nuovo nome del dispositivo
     */
    public void setNome(String nome){
        this.nome = nome;
    }

    /**
     * Recupera lo stato del Dispositivo
     * @return Stato del dispositivo
     */
    public boolean getStato(){
        return stato;
    }

    /**
     * Imposta lo stato del Dispositivo
     * @param stato Nuovo stato del dispositivo
     */
    public void setStato(boolean stato){
        this.stato = stato;
    }

    /**
     * Recupera il numero di Sistemi associati al Dispositivo
     * @return Il numero di Sistemi associati
     */
    public int getNumeroAssociazioni() {
        return numeroAssociazioni;
    }

    /**
     * Incrementa il numero di Sistemi associati al Dispositivo
     */
    public void incrNumAssociazioni(){
        this.numeroAssociazioni++;
    }

    /**
     * Decremeta il numero di Sistemi associati al Dispositivo
     */
    public void decrNumAssociazioni(){
        this.numeroAssociazioni--;
    }
}
