package domotix.controller.util;

/**
 * Classe di utilita' per funzioni su stringhe
 */
public class StringUtil {
    /**
     * Indenta la stringa passata
     * @param s stringa da indentare
     * @return  stringa indentata
     */
    public static String indent(String s) {
        return s.replaceAll("(?m)^", "\t");
    }

    /**
     * Indenta la stringa passata
     * @param s stringa da indentare
     * @param n numero di volte da indentare
     * @return  stringa indentata
     */
    public static String indent(String s, int n) {
        if (n <= 0) return s;
        for (int i = 0; i < n; i++) {
            s = indent(s);
        }
        return s;
    }

    /**
     * Rimuove gli ultimi n caratteri da una stringa
     * @param s stringa da elaborare
     * @param n numero di caratteri da rimuovere dal fondo
     * @return  stringa elaborata
     */
    public static String removeLast(String s, int n) {
        if (n <= 0 || n > s.length()) return s;
        return s.substring(0, s.length() - n);
    }

    /**
     * Rimuove l'ultimo carattere da una stringa
     * @param s stringa da elaborare
     * @return  sgtringa elaborata
     */
    public static String removeLast(String s) {
        return removeLast(s, 1);
    }

    /**
     * Compone un nome per un sensore/attuatore ponendo un carattere underscore tra le stringhe indicate.
     * @param fantasia  nome di fantasia per sensore/attuatore
     * @param categoria nome di categoria del sensore/attuatore
     * @return  nome composto
     */
    public static String componiNome(String fantasia, String categoria) {
        return fantasia + "_" + categoria;
    }

    /**
     * Compone il percorso per i sottotitoli dei menu
     * @param base  nome iniziale
     * @param figli nome dei figli da collegare
     * @return  percorso composto
     */
    public static String componiPercorso(String base, String ...figli) {
        String ret = base;
        for (String s : figli){
            ret += " > " + s;
        }
        return ret;
    }

    /**
     * Genera una stringa composta dalla ripetizione della stringa passata per n volte
     * @param s stringa da ripetere
     * @param n numero di volte da ripetere
     * @return  stringa generata
     */
    public static String repeat(String s, int n) {
        if (n <= 0 || s == null || s.equals("")) return "";

        String ns = "";
        for (int i = 0; i < n; i++)
            ns += s;

        return ns;
    }
}
