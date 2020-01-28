package domotix.logicUtil;

import java.io.InputStream;
import java.io.PrintStream;

/*
Questa classe rappresenta un menu testuale generico a piu' voci
Si suppone che la voce per uscire sia sempre associata alla scelta 0 
e sia presentata in fondo al menu

*/
public class MyMenu
{
    final private static String CORNICE = "--------------------------------";
    final private static String VOCE_USCITA = "0\tEsci";
    final private static String RICHIESTA_INSERIMENTO = "Digita il numero dell'opzione desiderata > ";

    private String titolo;
    private String [] voci;

    public MyMenu (String titolo, String [] voci)
    {
        this.titolo = titolo;
        this.voci = voci;
    }

    public int scegli (InputStream in, PrintStream out) {
        InputDati.setIn(in);
        InputDati.setOut(out);
        stampaMenu(out);
        return InputDati.leggiIntero(RICHIESTA_INSERIMENTO, 0, voci.length);
    }

    public int scegli () {
        return scegli(System.in, System.out);
    }

    public void stampaMenu (PrintStream out) {
        out.println(CORNICE);
        out.println(titolo);
        out.println(CORNICE);
        for (int i=0; i<voci.length; i++)
        {
            out.println( (i+1) + "\t" + voci[i]);
        }
        out.println();
        out.println(VOCE_USCITA);
        out.println();
    }

    public void stampaMenu () {
        stampaMenu(System.out);
    }
}

