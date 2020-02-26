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

    /**
     * Costruisce un menu senza sottotiolo
     * @param titolo del menu
     * @param voci del menu
     */
    public MyMenu (String titolo, String [] voci) {
        this(titolo, "", voci);
    }

    /**
     * Costruisce un menu con sottotitolo
     * @param titolo del menu
     * @param sottotitolo del menu
     * @param voci del menu
     */
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

    /**
     * Imposta il sottotiolo con un nuovo sottotitolo passato
     * @param sottotitolo passato e da impostare
     */
    public void setSottotitolo(String sottotitolo) {
        this.sottotitolo = sottotitolo;
    }

    /**
     * Imposta il titolo del menu ad un nuovo titolo passato
     * @param titolo passato da impostare come nuovo titolo
     */
    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    /**
     * Imposta le voci del menu ad un nuovo array di voci
     * @param voci nuove da impostare nel menu
     */
    public void setVoci(String[] voci) {
        this.voci = voci;
    }

    /**
     * Stampa il menu una volta specficata la voce di uscita dallo stesso e consente la scelta di una voce
     * @param voceUscita voce del menu che porta alla sua chiusura
     * @return l'indice della voce scelta nel menu
     */
    public int scegli (String voceUscita)
    {
        stampaMenu(voceUscita);
        return InputDati.leggiIntero(RICHIESTA_INSERIMENTO, 0, voci.length);
    }

    /**
     * Stampa il menu comprensivo della sua voce di uscita in una formattazione che vede il titolo e il sottotitolo eventuale fra due cornici
     * e sotto le voci del menu a partire dalla voce 0 che è sempre la voce di uscita
     * @param voceUscita voce di uscita del menu
     */
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

    /**
     * Per retrocompatibilità
     * @return l'indice della scelta effettuata nel menu
     */
    public int scegli(){
        return scegli("Esci");
    }
}

