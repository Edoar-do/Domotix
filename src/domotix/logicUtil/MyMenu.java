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

    final private static String RICHIESTA_INSERIMENTO = "Digita il numero dell'opzione desiderata > ";

    private String titolo;
    private String [] voci;

    public MyMenu (String titolo, String [] voci)
    {
        this.titolo = titolo;
        this.voci = voci;
    }

    public int scegli (String voceUscita)
    {
        stampaMenu(voceUscita);
        return InputDati.leggiIntero(RICHIESTA_INSERIMENTO, 0, voci.length);
    }

    public void stampaMenu (String voceUscita)
    {
        System.out.println(CORNICE);
        System.out.println(titolo);
        System.out.println(CORNICE);
        System.out.println();
        System.out.print("\t" + voceUscita);
        for (int i=0; i<voci.length; i++)
        {
            System.out.println( (i+1) + "\t" + voci[i]);
        }

    }

    public void scegli(){
        scegli("Esci");
    }
}

