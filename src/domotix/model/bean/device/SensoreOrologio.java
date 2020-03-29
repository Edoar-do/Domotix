package domotix.model.bean.device;

import domotix.controller.util.StringUtil;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

public class SensoreOrologio extends Sensore {

    private static final String NOME_CATEGORIA = "orologio_sistema";
    private static final String NOME_SENSORE = NOME_CATEGORIA;
    private static final String NOME_INFO_RILEVABILE = "tempo";
    private static final InfoRilevabile INFO_RILEVABILE = new InfoRilevabile(NOME_INFO_RILEVABILE, true);
    private static final CategoriaSensore CATEGORIA_SENSORE = new CategoriaSensore(NOME_CATEGORIA, "", INFO_RILEVABILE);

    private static SensoreOrologio instance = null;

    /**
     * Recupera l'unica istanza del sensore orologio.
     * @return  Unica istanza dell'elenco.
     */
    public static SensoreOrologio getInstance() {
        if (instance == null)
            instance = new SensoreOrologio();
        return instance;
    }

    /**
     * Costruttore della classe
     */
    private SensoreOrologio() {
        super(NOME_SENSORE, CATEGORIA_SENSORE);
    }

    /**
     * Ritorna il valore del sensore, ovvero i minuti del tempo corrente dalla mezzanotte.
     * @return  intero che rappresenta il numero di minuti dalla mezzanotte
     */
    @Override
    @SuppressWarnings("deprecation")
    public int getValore() {
        LocalTime now = LocalTime.now();
        return now.getHour() * 60 + now.getMinute();
    }

    /**
     * Ritorna l'ora corrente in formato HH.mm stampabile come stringa
     * @return  stringa con l'ora corrente
     */
    public String getValoreStampabile() {
        DateTimeFormatter dtf =  DateTimeFormatter.ofPattern("HH.mm");
        return dtf.format(LocalTime.now());
    }

    /**
     * Useless method.
     * @param nuovoValore no meaning
     */
    @Override
    @Deprecated
    public void setValore(int nuovoValore) {
        //nothing to do
    }

    @Override
    public HashMap<String, Object> getValori() {
        super.setValore(NOME_INFO_RILEVABILE, getValore()); //set current time
        return super.getValori();
    }

    @Override
    public Object getValore(String nomeInfo) throws IllegalArgumentException {
        super.setValore(NOME_INFO_RILEVABILE, getValore()); //set current time
        return super.getValore(nomeInfo);
    }

    /**
     * Useless method.
     * @param nomeInfo no meaning
     * @param nuovoValore no meaning
     * @return always true
     */
    @Override
    @Deprecated
    public boolean setValore(String nomeInfo, Object nuovoValore) {
        //nothing to do
        return true;
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(getNome());
        buffer.append("\n" + StringUtil.indent(NOME_INFO_RILEVABILE + " = " + getValoreStampabile()));
        return buffer.toString();
    }
}
