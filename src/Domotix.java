import domotix.io.OperazioniInizialiFinali;

public class Domotix {

    public static void main(String ...args) {
        boolean esegui = OperazioniInizialiFinali.getInstance().apri(System.in, System.out);

        while (esegui) {

            //TODO inizia menu

            esegui = !OperazioniInizialiFinali.getInstance().chiudi(System.in, System.out);
        }
    }

}
