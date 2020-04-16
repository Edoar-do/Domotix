package domotix.model.bean.device;

import domotix.controller.util.StringUtil;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.regex.Pattern;

/**
 * Classe per rappresentare il sensore speciale orologio di sistema.
 * Eredita dalla classe Sensore in modo che possa essere facilmente utilizzato nelle procedure
 * attualmente in opera (selezione a video, elaborazione nelle condizioni, ...)
 * Utilizza la classe LocalTime per il recupero dell'ora corrente.
 *
 * @author paolopasqua
 */
public class SensoreOrologio extends Sensore {

    public static final DateTimeFormatter TIME_FORMATTER =  DateTimeFormatter.ofPattern("HH.mm");
    public static final int MINUTI_IN_UN_GIORNO = 1440; //24 ore * 60 minuti
    public static final int MINUTI_IN_UN_ORA = 60;
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
     * Converte il tempo passato in un intero che rappresenta i minuti dalla mezzanotte
     * @param time  tempo da convertire
     * @return  intero per i minuti dalla mezzanotte che rappresentano l'ora indicata
     */
    public static int getTempo(LocalTime time) {
        return time.getHour() * 60 + time.getMinute();
    }

    /**
     * Converte il tempo passato come numero minuti dalla mezzanotte in un'istanza di LocalTime
     * @param time  intero rappresentante i minuti dalla mezzanotte
     * @return  istanza di LocalTime
     * @throws java.time.DateTimeException  Eccezione lanciata in caso non siano rispettati i formati
     */
    public static LocalTime getTempo(int time) {
        int ore = time / MINUTI_IN_UN_ORA;
        int minuti = time % MINUTI_IN_UN_ORA;
        return LocalTime.of(ore, minuti);
    }

    /**
     * Converte il tempo passato come numero double dove la parte intera rappresenta le ore e quella decimale i minuti
     * in un'istanza di LocalTime
     * @param time  double rappresnetante un'orario
     * @return  istanza di LocalTime
     * @throws java.time.DateTimeException  Eccezione lanciata in caso non siano rispettati i formati
     */
    public static LocalTime getTempo(double time) {
        String orarioStringa = String.valueOf(time);
        int ore = Integer.parseInt(orarioStringa.split(Pattern.quote("."))[0]);
        int minuti = Integer.parseInt(orarioStringa.split(Pattern.quote("."))[1]);
        return LocalTime.of(ore, minuti);
    }

    /**
     * Ritorna i minuti che mancano alla mezzanotte dal tempo indicato.
     * @param time  tempo da confrontare
     * @return  intero per i minuti mancanti per raggiungere la mezzanotte
     */
    public static int getMinutiAllaMezzanotte(LocalTime time) {
        return getMinutiAllaMezzanotte(getTempo(time));
    }

    /**
     * Ritorna i minuti che mancano alla mezzanotte dal tempo indicato.
     * @param time  tempo da confrontare
     * @return  intero per i minuti mancanti per raggiungere la mezzanotte
     */
    public static int getMinutiAllaMezzanotte(int time) {
        return MINUTI_IN_UN_GIORNO - time;
    }

    /**
     * Ritorna l'ora indicata dal parametro in formato  HH.mm  stampabile come stringa
     * @return  stringa con l'ora indicata
     */
    public static String getValoreStampabile(LocalTime time) {
        return TIME_FORMATTER.format(time);
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
        return SensoreOrologio.getTempo(getValoreTempo());
    }

    /**
     * Ritorna l'ora corrente in formato HH.mm stampabile come stringa
     * @return  stringa con l'ora corrente
     */
    public String getValoreStampabile() {
        return getValoreStampabile(getValoreTempo());
    }

    /**
     * Ritorna l'ora corrente in un'istanza di LocalTime
     * @return  istanza di LocalTime per l'ora corrente
     */
    public LocalTime getValoreTempo() {
        return LocalTime.now();
    }

    /**
     * Controlla se il tempo indicato viene prima dell'ora corrente
     * @param time  tempo da confrontare
     * @return  true: il tempo indicato viene prima dell'ora attuale; false: altrimenti
     */
    public boolean isPrima(LocalTime time) {
        return time.isBefore(getValoreTempo());
    }


    /**
     * Controlla se il tempo indicato viene dopo dell'ora corrente
     * @param time  tempo da confrontare
     * @return  true: il tempo indicato viene dopo dell'ora attuale; false: altrimenti
     */
    public boolean isDopo(LocalTime time) {
        return time.isAfter(getValoreTempo());
    }

    /**
     * Ritorna i minuti che mancano alla mezzanotte dall'ora attuale.
     * @return  intero per i minuti mancanti per raggiungere la mezzanotte
     */
    public int getMinutiAllaMezzanotte() {
        return SensoreOrologio.getMinutiAllaMezzanotte(getValoreTempo());
    }

    /**
     * Ritorna il numero di minuti tra l'ora corrente e l'ora subito successiva indicata come parametro.
     * Questo significa che se l'ora corrente e' 06.30 e il tempo passato fosse 8.00 allora viene ritornato
     * 90 minuti; mentre se il tempo indicato fosse 06.00 allora si riferirebbe al giorno seguente e quindi
     * si ha 407 minuti (cioe' 23 ore e mezza).
     * @param time  tempo da confrontare
     * @return  intero per i minuti tra l'ora corrente e l'ora indicata
     */
    public int getMinutiDifferenza(LocalTime time) {
        int tempo = getTempo(time);

        if (isDopo(time)) {
            //ora indicata e' dopo l'attuale, quindi ritorno la differenza dei minuti
            return tempo - getValore();
        }
        else {
            return tempo + getMinutiAllaMezzanotte();
        }
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
     * Il valore ritornato e' il numero di minuti dalla mezzanotte e rappresenta l'ora nel momento in cui e' stato chiamato
     * il metodo
     * @return  mappa contenente la coppia (NOME_INFO_RILEVABILE_OROLOGIO , intero per i minuti dalla mezzanotte)
     */
    @Override
    public HashMap<String, Object> getValori() {
        super.setValore(NOME_INFO_RILEVABILE_OROLOGIO, getValore()); //set current time
        return super.getValori();
    }

    /**
     * Ritorna una HashMap contenente il valore rilevabile tempo per il SensoreOrologio.
     * Il valore ritornato e' il numero di minuti dalla mezzanotte e rappresenta l'ora nel momento in cui e' stato chiamato
     * il metodo
     * @return  intero per i minuti dalla mezzanotte per il momento corrente
     */
    @Override
    public Object getValore(String nomeInfo) throws IllegalArgumentException { //cast a double fatto da me
        super.setValore(NOME_INFO_RILEVABILE_OROLOGIO, getValore());
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
