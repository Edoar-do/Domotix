package domotix.view;

/*
Questa classe rappresenta un menu testuale generico a piu' voci
Si suppone che la voce per uscire sia sempre associata alla scelta 0 
e sia presentata in testa al menu

*/

/** @author Edoardo Coppola*/
public class MyMenu
{
    final private static String CORNICE_CHAR = "-";

    final private static String RICHIESTA_INSERIMENTO = "Digita il numero dell'opzione desiderata > ";

    private String cornice;
    private String titolo;
    private String sottotitolo;
    private String [] voci;

    public MyMenu (String titolo, String [] voci) {
        this(titolo, "", voci);
    }

    public MyMenu (String titolo, String sottotitolo, String [] voci)
    {
        setTitolo(titolo);
        this.sottotitolo = sottotitolo;
        this.voci = voci;
    }

    private void impostaCornice() {
        cornice = CORNICE_CHAR.repeat(Math.max(titolo.length(), componiSottotitolo().length()) + 1);
    }

    private String componiSottotitolo() {
        return "    " + (sottotitolo == null ? "" : sottotitolo);
    }

    public void setSottotitolo(String sottotitolo) {
        this.sottotitolo = sottotitolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public void setVoci(String[] voci) {
        this.voci = voci;
    }

    public int scegli (String voceUscita)
    {
        stampaMenu(voceUscita);
        return InputDati.leggiIntero(RICHIESTA_INSERIMENTO, 0, voci.length);
    }

    public void stampaMenu (String voceUscita)
    {
        impostaCornice();
        System.out.println(cornice);
        System.out.println(titolo);
        if (sottotitolo != null && !sottotitolo.trim().isEmpty())
            System.out.println(componiSottotitolo());
        System.out.println(cornice);
        System.out.println();
        System.out.println("0" + "\t" + voceUscita);
        for (int i=0; i<voci.length; i++)
        {
            System.out.println( (i+1) + "\t" + voci[i]);
        }

    }

    public int scegli(){
        return scegli("Esci");
    }
}

