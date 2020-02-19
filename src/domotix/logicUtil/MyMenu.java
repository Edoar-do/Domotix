package domotix.logicUtil;

import java.io.InputStream;
import java.io.PrintStream;

/*
Questa classe rappresenta un menu testuale generico a piu' voci
Si suppone che la voce per uscire sia sempre associata alla scelta 0 
e sia presentata in testa al menu

*/

/** @author Edoardo Coppola*/
public class MyMenu
{
    final private static String CORNICE = "--------------------------------";
    final private static String VOCE_INDIETRO = "0\tIndietro";
    final private static String RICHIESTA_INSERIMENTO = "Digita il numero dell'opzione desiderata > ";
    final private static String VOCE_SALVA_ESCI = "Salva ed esci";

    private String titolo;
    private String [] voci;

    public MyMenu (String titolo, String [] voci)
    {
        this.titolo = titolo;
        this.voci = voci;
    }

    /*
    public int scegli (InputStream in, PrintStream out, Boolean backOrExit) {
        InputDati.setIn(in);
        InputDati.setOut(out);
        stampaMenu(out, backOrExit);
        return InputDati.leggiIntero(RICHIESTA_INSERIMENTO, 0, voci.length);
    }

    public int scegli (boolean backOrExit) {
        return scegli(System.in, System.out, backOrExit);
    }

    public void stampaMenu (PrintStream out, boolean backOrExit) {
        out.println(CORNICE);
        out.println(titolo);
        out.println(CORNICE);

        if(backOrExit){
            out.println();
            out.println(VOCE_INDIETRO);
            out.println();
        }else{
            out.println();
            out.println(VOCE_SALVA_ESCI);
            out.println();
        }


        for (int i=0; i<voci.length; i++)
        {
            out.println( (i+1) + "\t" + voci[i]);
        }

    }

    public void stampaMenu () {
        stampaMenu(System.out);
    }*/

    public int scegli (boolean backOrExit)
    {
        stampaMenu(backOrExit);
        return InputDati.leggiIntero(RICHIESTA_INSERIMENTO, 0, voci.length);
    }

    public void stampaMenu (boolean backOrExit)
    {
        System.out.println(CORNICE);
        System.out.println(titolo);
        System.out.println(CORNICE);
        System.out.println();
        if(backOrExit)
            System.out.println(VOCE_INDIETRO);
        else
            System.out.print(VOCE_SALVA_ESCI);
        for (int i=0; i<voci.length; i++)
        {
            System.out.println( (i+1) + "\t" + voci[i]);
        }

    }
}

