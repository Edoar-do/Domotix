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
    public void doExceptionRuotine(Exception e) {
        System.out.println(DATI_INCOERENTI);
    }
}
