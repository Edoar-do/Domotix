package domotix.view.strategyException;
import static domotix.view.ViewConstants.ASSISTENZA;

/**
 * Classe che realizza la routine di gestione in caso di NotDirectoryException
 * @author Edoardo Coppola
 * @see ExceptionStrategy
 */
public class NotDirectoryExceptionRoutine implements ExceptionStrategy {
     private static final String DATI_INCOERENTI = "I dati salvati sono incoerenti. E' impossibile eseguire l'applicazione." + ASSISTENZA;

    @Override
    public String doExceptionRuotine(Exception e) {
        return DATI_INCOERENTI;
    }
}
