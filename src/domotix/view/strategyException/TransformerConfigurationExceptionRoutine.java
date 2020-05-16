package domotix.view.strategyException;
import static domotix.view.ViewConstants.ASSISTENZA;

/**
 * Classe che realizza la routine di gestione in caso di TransformerConfigurationException
 * @author Edoardo Coppola
 * @see ExceptionStrategy
 */
public class TransformerConfigurationExceptionRoutine implements ExceptionStrategy {


    private static final String ERRORE_CARICAMENTO_STRUMENTI_LETTURA_SCRITTURA = "Si è verificato un errore durante il caricamento degli strumenti per la lettura e la scrittura dei dati salvati"
            + ASSISTENZA;

    @Override
    public void doExceptionRuotine(Exception e) {
        System.out.println(ERRORE_CARICAMENTO_STRUMENTI_LETTURA_SCRITTURA);
    }
}
