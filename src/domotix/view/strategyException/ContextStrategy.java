package domotix.view.strategyException;

/**
 * Classe context del pattern strategy
 * @author Edoardo Coppola
 */
public class ContextStrategy {
    private ExceptionStrategy exceptionStrategy;

    /**
     * Costruttore della classe
     * @param exceptionStrategy
     */
    public ContextStrategy(ExceptionStrategy exceptionStrategy){
        this.exceptionStrategy = exceptionStrategy;
    }

    /**
     * Metodo per l'esecuzione runtime di una routine di gestione di un'eccezione verificatasi
     * @param e eccezione verificatasi
     */
    public void executeStrategy(Exception e){ System.out.println(exceptionStrategy.doExceptionRuotine(e));}
}
