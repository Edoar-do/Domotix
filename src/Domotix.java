import domotix.controller.OperazioniFinali;
import domotix.controller.OperazioniIniziali;

public class Domotix {

    public static void main(String ...args) {
        boolean esegui = OperazioniIniziali.getInstance().apri();

        while (esegui) {

            //TODO inizia menu

            esegui = !OperazioniFinali.getInstance().chiudi();
        }
    }

}
