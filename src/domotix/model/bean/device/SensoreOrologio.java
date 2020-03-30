package domotix.model.bean.device;

import domotix.controller.util.StringUtil;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

/**
 * Classe per rappresentare il sensore speciale orologio di sistema.
 * Eredita dalla classe Sensore in modo che possa essere facilmente utilizzato nelle procedure
 * attualmente in opera (selezione a video, elaborazione nelle condizioni, ...)
 * Utilizza la classe LocalTime per il recupero dell'ora corrente.
 *
 * @author paolopasqua
 */
public class SensoreOrologio extends Sensore {

    public static final String NOME_CATEGORIA_OROLOGIO = "orologio_sistema";
    public static final String NOME_SENSORE_OROLOGIO = NOME_CATEGORIA_OROLOGIO;
    public static final String NOME_INFO_RILEVABILE_OROLOGIO = "tempo";
    private static final InfoRilevabile INFO_RILEVABILE_OROLOGIO = new InfoRilevabile(NOME_INFO_RILEVABILE_OROLOGIO, true);
    private static final CategoriaSensore CATEGORIA_SENSORE_OROLOGIO = new CategoriaSensore(NOME_CATEGORIA_OROLOGIO, "", INFO_RILEVABILE_OROLOGIO);

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
        super(NOME_SENSORE_OROLOGIO, CATEGORIA_SENSORE_OROLOGIO);
    }

    /**
     * Ritorna il valore del sensore, ovvero i minuti del tempo corrente dalla mezzanotte.
     * @return  intero che rappresenta il numero di minuti dalla mezzanotte
     */
    @Override
    @SuppressWarnings("deprecation")
    public int getValore() {
        LocalTime now = getValoreTempo();
        return now.getHour() * 60 + now.getMinute();
    }

    /**
     * Ritorna l'ora corrente in formato HH.mm stampabile come stringa
     * @return  stringa con l'ora corrente
     */
    public String getValoreStampabile() {
        DateTimeFormatter dtf =  DateTimeFormatter.ofPattern("HH.mm");
        return dtf.format(getValoreTempo());
    }

    /**
     * Ritorna l'ora corrente in un'istanza di LocalTime
     * @return  istanza di LocalTime per l'ora corrente
     */
    public LocalTime getValoreTempo() {
        return LocalTime.now();
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

    /**
     * Ritorna una HashMap contenente il valore rilevabile tempo per il SensoreOrologio.
     * Il valore ritornato e' del tipo LocalTime e rappresenta l'ora nel momento in cui e' stato chiamato
     * il metodo
     * @return  istanza di LocalTime per il momento corrente
     */
    @Override
    public HashMap<String, Object> getValori() {
        super.setValore(NOME_INFO_RILEVABILE_OROLOGIO, getValoreTempo()); //set current time
        return super.getValori();
    }

    /**
     * Ritorna una HashMap contenente il valore rilevabile tempo per il SensoreOrologio.
     * Il valore ritornato e' del tipo LocalTime e rappresenta l'ora nel momento in cui e' stato chiamato
     * il metodo
     * @return  istanza di LocalTime per il momento corrente
     */
    @Override
    public Object getValore(String nomeInfo) throws IllegalArgumentException {
        super.setValore(NOME_INFO_RILEVABILE_OROLOGIO, getValoreTempo()); //set current time
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
        buffer.append("\n" + StringUtil.indent(NOME_INFO_RILEVABILE_OROLOGIO + " = " + getValoreStampabile()));
        return buffer.toString();
    }
}
