package domotix.view.strategyException;

/**
 * Interfaccia strategy per l'omonimo pattern che fornisce una routine per le eccezioni verificabili
 * @author Edoardo Coppola
 */
public interface ExceptionStrategy {

    /**
     * Metodo per la routine di gestione di una eccezione
     * @param e eccezione da gestire
     */
    String doExceptionRuotine(Exception e);
}
